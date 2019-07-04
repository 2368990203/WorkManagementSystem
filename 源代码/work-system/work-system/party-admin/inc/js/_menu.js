/********************基础配置 begin***********************/

var moduleData;
var moduleForm = '../../form/_module.html';
var moduleTitle = '模块权限管理';
var parentId = -1;
var module = {
    create:function () {
        openLay({url:moduleForm,fun:'createModule();',title:moduleTitle});
    },
    update:function (_id) {
        parentId = _id;
        openLay({url:moduleForm,fun:"updateModule();",title:moduleTitle});
        form.set(result.get(moduleData,parentId));
    },
    delete:function (_id) {
        parentId = _id;
        tips.confirm({message:'是否要删除这条数据？',fun:"deleteModule();"});
    }
}

var menuData;
var menuForm = '../../form/_menu.html';
var menuTitle = '菜单管理';
var menuId;
var menu = {
    create:function () {
        openLay({url:menuForm,fun:'createMenu();',title:menuTitle});
        var model = moduleData[$('#module-list').find('.active').attr('data-index')];
        var obj = $("form select[name='parentId']");
        renderSelect();
        obj.val(model.id);
    },
    update:function (_id) {
        menuId = _id;
        openLay({url:menuForm,fun:'updateMenu();',title:menuTitle});
        var model = result.get(menuData,_id);
        form.set(model);
        renderSelect();
        var obj = $("form select[name='parentId']");
        obj.val(model.parentId);
        var fa_class = 'fa '+ model.icon;
        $('#form-icon').removeClass();
        $('#form-icon').addClass(fa_class);
    },
    delete:function (_id) {
        menuId = _id;
        tips.confirm({message:'是否要删除这条数据？',fun:"deleteMenu();"});
    }
}


var subForm = '../../form/_sub_menu.html';
var menuTitle = '子菜单管理';
var subId;
var sub = {
    create:function (_id) {
        menuId = _id;
        openLay({url:subForm,fun:'createSubMenu();',title:menuTitle});
        var obj = $("form select[name='parentId']");
        renderSubSelect();
        obj.val(_id);
    },
    update:function (_pid,_id) {
        subId = _id;
        openLay({url:subForm,fun:'updateSubMenu();',title:menuTitle});

        var list = result.get(menuData,_pid);
        var model = result.get(list.sub,subId)
        form.set(model);
        // 下拉选择
        renderSubSelect();
        var obj = $("form select[name='parentId']");
        obj.val(model.parentId);
        // 图标
        var fa_class = 'fa '+ model.icon;
        $('#form-icon').removeClass();
        $('#form-icon').addClass(fa_class);
    },
    delete:function (_id) {
        subId = _id;
        tips.confirm({message:'是否要删除这条数据？',fun:"deleteSubMenu();"});
    }
}

var authForm = '../../form/_sub_menu_auth.html';
var authTitle = '功能配置';
var authId = 0;
var authPid = 0;
var auth = {
    check: function (event,checkUrl) {
        var url="",backUrl="";

        if(event.URL!=""&&event.URL!=null){
            url =event.URL;
            url ='/'+url.replace(server_root,'');
            url =url.replace('http://','').replace('https://','');
            url = url.substr(url.indexOf('/'));
            backUrl=event.referrer.replace('http://','').replace('https://','');
            backUrl = backUrl.substr(backUrl.indexOf('/'));

        }
        if(checkUrl!=""&&checkUrl!=null){
            url =url.replace(server_root,'');
            url=checkUrl;
        }

       // debugger;
        var urlAuth = store.get(cache_user_key+'-url-auth');

        if(urlAuth==undefined||urlAuth==null||urlAuth==""){
            store.clear();
            window.location.href =server_root;
            return false;
        }

        var check = 0;
        if(url!=null&&url!=""){
            for (var i = 0; i < urlAuth.length; i++) {
                if (urlAuth[i] == url) {
                    check = 1;
                    break;
                }
            }}
        if (check == 0) {
            swal({
                    title: "您没有这个权限操作",
                    type: "warning",
                    showCancelButton: false,
                    confirmButtonColor: "#DD6B55",
                    confirmButtonText: "确定",
                    closeOnConfirm: false
                },
                function(){
                    if(backUrl==null||backUrl==""||backUrl=="/sys"||backUrl=='/'||backUrl=='/'){
                        backUrl=server_root+'sys/welcome';
                    }
                    window.location.href = backUrl;
                }
            );
            return false;
        } else {
            return true;
        }
        return false;
    },
    create:function (_pid,_id) {
        var list = result.get(menuData,_pid);
        var model = result.get(list.sub,_id)
        openLay({url:authForm,fun:'createAuth();',title:authTitle});
        $('#sub-menu-name').html(model.name);
        authPid = _id;
    },
    update:function (_pid,_sid,_id) {
        authId = _id;
        openLay({url:authForm,fun:'updateAuth();',title:authTitle});
        var list = result.get(menuData,_pid);
        var sub = result.get(list.sub,_sid);
        var model = result.get(sub.btns,_id);
        form.set(model);
        $('#sub-menu-name').html(sub.name);
        authPid = _id;
    },
    delete:function (_id) {
        authId = _id;
        tips.confirm({message:'是否要删除这条数据？',fun:"deleteAuth();"});
    }
}

/********************基础配置 end************************/

/*********************权限 begin***********************/
function createAuth(){
    var data = form.get("#opt-form");
    if(form.verify(data))
        return false;
    data['menuId'] = authPid;
    var param = {url:authModule.subAuthApi,data:data};
    var request = ajax.post(param);
    request.done(function (d) {
        tips.done(d);
        getMenu();
    })
}
function deleteAuth() {
    var request = ajax.delete({url:authModule.subAuthApi+'/'+authId});
    request.done(function (d) {
        tips.ok(d.message);
        getMenu();
    })
}
function updateAuth() {
    var data = form.get("#opt-form");
    if(form.verify(data))
        return false;
    data['id'] = authId;
    var param = {url:authModule.subAuthApi,data:data};
    var request = ajax.put(param);
    request.done(function (d) {
        tips.ok(d.message);
        closeLay();
        getMenu();
    })
}
/*********************权限 end***********************/


/********************初始化 begin***********************/
$(function () {
    if(auth.check(this)) {
   // parent.setTitle("系统权限菜单功能管理");
    initMenuHeight();
    $(window).resize(function(){
        initMenuHeight();
    });
    getModule();
    }
})

function initMenuHeight() {
    $('#menu-list .panel-body').css('height',(parent.adaptable().h)-115);
    $('#module-list').css('height',(parent.adaptable().h)-120);
}
/********************初始化 end************************/


/*********************子级系统 begin**********************/

function renderSubSelect() {
    var tmpl = doT.template($("#module-select-tepl").text());
    $('#menu-select').html(tmpl(menuData));
}
// 创建子菜单
function createSubMenu() {
    var data = form.get("#opt-form");
    if(form.verify(data))
        return false;
    var param = {url:authModule.subApi,data:data};
    var request = ajax.post(param);
    request.done(function (d) {
        tips.done(d);
        menuId = d.result.parentId;
        getMenu();
    })
}
// 删除子菜单
function deleteSubMenu() {
    var request = ajax.delete({url:authModule.subApi+'/'+subId});
    request.done(function (d) {
        tips.ok(d.message);
        getModule(parentId);
    })
}
// 修改子菜单
function updateSubMenu() {
    var data = form.get("#opt-form");
    if(form.verify(data))
        return false;
    data['id'] = subId;

    var param = {url:authModule.menuApi,data:data};
    var request = ajax.put(param);
    request.done(function (d) {
        tips.ok(d.message);
        closeLay();
        getModule(parentId);
    })
}
/*********************子级菜单 end************************/


/*********************顶级菜单 begin**********************/
// 渲染模块下拉选择
function renderSelect() {
    var select = doT.template($("#module-select-tepl").text());
    $('#module-select').html(select(moduleData));
}
// 创建顶级菜单
function createMenu() {
    var data = form.get("#opt-form");
    if(form.verify(data))
        return false;
    var param = {url:authModule.menuApi,data:data};
    var request = ajax.post(param);
    request.done(function (d) {
        tips.done(d);
        parentId = d.result.parentId;
        getMenu();
    })
}
// 修改顶级菜单
function updateMenu() {
    var data = form.get("#opt-form");
    if(form.verify(data))
        return false;
    data['id'] = menuId;
    var param = {url:authModule.menuApi,data:data};
    var request = ajax.put(param);
    request.done(function (d) {
        tips.ok(d.message);
        closeLay();
        getModule(parentId);
    })
}
// 删除顶级菜单
function deleteMenu() {
    var request = ajax.delete({url:authModule.moduleApi+'/'+menuId});
    request.done(function (d) {
        tips.ok(d.message);
        getModule(parentId);
    })
}
//读取顶级菜单
function getMenu() {
    if(parentId == -1){
        renderMenu([]);
        return false;
    }
    var param = {url:authModule.menuApi+'/'+parentId};
    var request = ajax.get(param);
    request.done(function (d) {
        renderMenu(d.result);
    })
}
// 渲染菜单
function renderMenu(_data) {
    menuData = _data;
    var menu = doT.template($("#menu-tmpl").text());
    $("#menu-list-item").html(menu(menuData));
    renderTip();
}
/*********************顶级菜单 end************************/


/*********************模块业务逻辑 begin**********************/
//权限列表事件
function eventModule(_index) {
    //事件渲染
    $('#module-list .list-group-item').click(function () {
        var checked = $(this).hasClass('active');
        if(!checked){
            $('#module-list .list-group-item').removeClass('active');
            $('#module-list .list-group-item .badges').addClass('hide');
            $(this).addClass('active');
            $(this).find('.badges').removeClass('hide');
            parentId = moduleData[$(this).attr('data-index')].id;
            getMenu();
        }
    })
    //选中
    if(_index >= 0){
        $('#module-list .list-group-item').removeClass('active');
        $('#module-list .list-group-item .badges').addClass('hide');
        $('#module-list .list-group-item').eq(_index).addClass('active');
        $('#module-list .list-group-item').eq(_index).find('.badges').removeClass('hide');
        try{
            parentId = moduleData[_index].id;
            getMenu();
        }catch(e){}

    }
}
//读取模块
function getModule(_id) {
    var param = {url:authModule.moduleApi};
    var request = ajax.get(param);
    request.done(function (d) {
        renderModule(d.result,_id);
        if(d.result.length == 0){
            parentId = -1;
            getMenu();
        }
    })
}
// 渲染模块数据
function renderModule(_data,_id) {
    moduleData = _data;
    var module = doT.template($("#module-tmpl").text());
    $("#module-list").html(module(moduleData));
    var index = 0;
    for(var i=0;i<moduleData.length;i++){
        if(moduleData[i].id == _id){
            index = i;
            break;
        }
    }
    eventModule(index);
}
// 创建模块
function createModule() {
    var data = form.get("#opt-form");
    if(form.verify(data))
        return false;
    data["parentId"] = 0;
    var param = {url:authModule.moduleApi,data:data};
    var request = ajax.post(param);
    request.done(function (d) {
        tips.done(d);
        parentId = d.result.id;
        getModule(parentId);
    })
}
// 修改模块
function updateModule() {
    var data = form.get("#opt-form");
    if(form.verify(module))
        return false;
    data['id'] = parentId;
    data["parentId"] = 0;
    var param = {url:authModule.moduleApi,data:data};
    var request = ajax.put(param);
    request.done(function (d) {
        tips.ok(d.message);
        closeLay();
        getModule(parentId);
    })
}
// 删除模块
function deleteModule() {
    var request = ajax.delete({url:authModule.moduleApi+'/'+parentId});
    request.done(function (d) {
        tips.ok(d.message);
        getModule();
    })
}
/*********************模块业务逻辑 end***********************/

