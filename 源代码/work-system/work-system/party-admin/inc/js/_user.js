var userData = [];var roleData = [];
var userForm = '../../form/_user.html';
var userTitle = '用户管理';
var userId;
var index = 1, size = 12, key='';
var userStatus;
var user = {
    create:function (event) {
        if(auth.refuse(event))
            return false;
        openLay({url:userForm,fun:'createUser();',title:userTitle});
    },
    update:function (event) {
        if(auth.refuse(event))
            return false;
        userId = getId(event);
        var model = result.get(userData,userId);
        openInfoLay({url:'../../form/_user_info.html',fun:'updateUser();',title:'用户信息'});
        form.set(model.info);
    },
    delete:function (event) {
        if(auth.refuse(event))
            return false;
        userId = getId(event);
        tips.confirm({message:'是否要删除这条数据？',fun:"deleteUser();"});
    },
    status:function (event) {
        if(auth.refuse(event))
            return false;
        userId = getId(event);
        userStatus = getData(event,'data-status');
        var status = userStatus == 1 ? '冻结' : '恢复';
        tips.confirm({message:'确定要'+status+'该用户状态？',fun:"initStatus();",enter:'确定'});
    },
    random:function () {
        var pwd = string.random(8);
        $("input[name='password']").val(pwd);
    },
    info:function (event) {
        userId = getId(event);
        var model = result.get(userData,userId);
        openInfoLay({url:'../../form/_user_info.html',title:'用户信息',hideEnter:true});
        form.set(model.info);
        $('#opt-form input').attr('disabled','disabled');
    },
    token:function (event) {
        var token = getId(event);
        getByToken(token);

    },
    select:function (event) {
        key = $.trim($('#select-key').val());
        index = 1;
        getUser();
    },
    initPwd:function (event) {
        if(auth.refuse(event))
            return false;
        userId = getId(event);
        tips.confirm({message:'确定要重置该用户的登录密码？',fun:"initPwd();",enter:'重置'});
    },
    setAuth:function (event) {
        if(auth.refuse(event))
            return false;
        userId = getId(event);
        initAuth();
    },
    changeRole:function (event) {
        if(auth.refuse(event))
            return false;
        userId = getId(event);

        // var model = result.get(userData,userId);
        openLay({url:'../../form/_change_role.html',fun:'changeUserRole();',title:'修改角色'});
        getAllRole();
        // form.set(model.info);
    },
}

function changeUserRole() {
    var data = form.get("#opt-form");
    if(form.verifyPlus(data))
        return false;
    var roleId =data['roleId'];

    var param = {url:userModule.userRoleChangeApi+'/'+userId + '-' + roleId };
    var request = ajax.put(param);
    request.done(function (d) {
        tips.ok(d.message);
        closeLay();
    })
    getUser();
    getAllRole();
}

function getAllRole() {
    var param = {url:toolModule.getRoleListApi+'/' + " "};
    var request = ajax.get(param);
    request.done(function (d) {
        roleData = d.result;
        var template = doT.template($("#roleId-template").text());
        $('#roleId').html(template(roleData));
    })
}

function getByToken(_token) {
    var param = {url:userModule.userTokenApi+'/'+_token};
    var request = ajax.get(param);
    request.done(function (d) {
        var data = d.result;
        openInfoLay({url:'../../form/_user_token_info.html',title:'Token信息',hideEnter:true});
        $('#token-info').html('<pre>'+JSON.stringify(data,null,1)+'</pre>');
    })
}
$(function () {
    if(auth.check(this)) {
        getUser();
    }
})

function initAuth() {
    var param = {url:userModule.userAuthApi+'/'+userId};
    var request = ajax.get(param);
    request.done(function (d) {
        var data = d.result;
        openLay({url:'../../form/_system_auth.html',fun:'setAuth();',title:'子系统配置'});
        var tmpl = doT.template($('#system-auth-tmpl').text());
        $("#system-auth-list").html(tmpl(data));
        renderIChenk();
    })
}
function setAuth() {
    var auths = [];
    $("input[name='auth']:checkbox").each(function(){
        if(true == $(this).is(':checked')){
            var tempId = '#role'+ $(this).attr('data-id');
            var roleId = $(tempId).val();
            var auth = {
                    "userId":userId,
                    "systemId":$(this).attr('data-id'),
                    "roleId":roleId
                };
            auths.push(auth);
        }
    });
    log.d(auths)
    var param = {url:userModule.userAuthApi+'/'+userId,data:auths};
    var request = ajax.put(param);
    request.done(function (d) {
        tips.ok(d.message);
        closeLay();
    })
}
function initStatus() {
    userStatus = userStatus == 1 ? 0 : 1;
    var param = {url:userModule.userStatusApi+'/'+userId+'-'+userStatus};
    var request = ajax.put(param);
    request.done(function (d) {
        tips.ok("用户状态调整成功");
        getUser();
    })
}
function initPwd() {
    var param = {url:userModule.userPwdApi+'/'+userId};
    var request = ajax.put(param);
    request.done(function (d) {
        tips.ok("密码重置成功请牢记新密码 : "+d.result.password+"");
        closeLay();
    })
}
function renderList() {
    var tmpl = doT.template($('#user-tmpl').text());
    $("#user-list").html(tmpl(userData));
    renderTip();
    renderIChenk();
}
//读取
function getUser() {
    var param = {url:userModule.userApi+'/'+index+'-'+size+'-'+key};
    var request = ajax.get(param);
    request.done(function (d) {
        userData = d.result.data;
        renderList();
        initPage(d.result.totalPage,d.result.totalCount);
    })
}
// 创建
function createUser() {
    var data = form.get("#opt-form");
    if(form.verify(data))
        return false;
    data = {"attr":data,"loginName":data.loginName,"password":data.password};
    var param = {url:userModule.userApi,data:data};
    var request = ajax.post(param);
    request.done(function (d) {
        tips.done(d);
        getUser();
    })
}
// 修改
function updateUser() {
    var data = form.get("#opt-form");
    if(form.verify(data))
        return false;
    var param = {url:userModule.userApi+'/'+userId,data:data};
    var request = ajax.put(param);
    request.done(function (d) {
        tips.ok(d.message);
        closeInfoLay();
        getUser();
    })
}
// 删除
function deleteUser() {
    var request = ajax.delete({url:userModule.userApi+'/'+userId});
    request.done(function (d) {
        tips.ok(d.message);
        userData = json.delete(userData,userId);
        renderList();
    })
}

//状态显示
function format_status(_status) {
    return _status == 1 ? '正常' : '冻结';
}


function initPage(_pageSize,_total) {
    $('.list-page').pagination({
        pageCount:_pageSize,
        current:index,
        jump:true,
        coping:true,
        homePage:'首页',
        endPage:'末页',
        prevContent:'上页',
        nextContent:'下页',
        pageSize:size,
        totalCount:_total,
        callback:function(api){
            index = api.getCurrent();
            getUser();
        }
    });
    if(_pageSize>0)
        $('.list-page').show();
}
function pageChange(event){
    size = $(event).val();
    index = 1;
    getUser();
}