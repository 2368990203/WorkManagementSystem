
var roleData,visibleData;
var roleForm = '../../form/_role.html';
var roleTitle = '角色管理';
var roleId = 0;

$(function () {
    if(auth.check(this)) {
        dictionary.getVisible();
        role.get();
    }
})

var dictionary = {
    getVisible:function () {
        var param = {url:toolModule.getDicFieldNameApi+'/'+"userRole"};
        var request = ajax.get(param);
        request.done(function (d) {
            visibleData = d.result;//字典数据
            renderVisible();
        });
    }
};

var role = {
    create:function (event) {
        if(auth.refuse(event))
            return false;
        openLay({url:roleForm,fun:'createRole();',title:roleTitle});
        renderVisible();
    },
    update:function (event) {
        if(auth.refuse(event))
            return false;
        roleId = getId(event);
        openLay({url:roleForm,fun:"updateRole();",title:roleTitle});
        var model = result.get(roleData,roleId);
        renderVisible();
        form.set(model);
        $('#isDefault').val(model.isDefault);
    },
    delete:function (event) {
        if(auth.refuse(event))
            return false;
        roleId = getId(event);
        tips.confirm({message:'是否要删除这条数据？',fun:"deleteRole();"});
    },
    get:function () {
        var param = {url:roleModule.roleApi};
        var request = ajax.get(param);
        request.done(function (d) {
            roleData = d.result;
            renderRole();
        })
    },
    auth:function (event) {
        if(auth.refuse(event))
            return false;
        roleId = getId(event);
        getAuth();

    }
}

function renderRole() {
    var tmpl = doT.template($("#role-tmpl").text());
    $('#role-list').html(tmpl(roleData));
    renderTip();
}

function renderVisible() {
    var tmpl = doT.template($("#visible-tmpl").text());
    $('#visible').html(tmpl(visibleData));
}


function createRole() {
    var data = form.get("#opt-form");
    if(form.verify(data))
        return false;
    if($("#visible").val() == ""){
        tips.warning("请选择数据可见范围");
        return false;
    }
    var param = {url:roleModule.roleApi,data:data};
    var request = ajax.post(param);
    request.done(function (d) {
        tips.done(d);
        // roleData.push(d.result);
        // renderRole();
        role.get();
    })
}
function updateRole() {
    var data = form.get("#opt-form");
    if(form.verify(data))
        return false;
    if($("#visible").val() == ""){
        tips.warning("请选择数据可见范围");
        return false;
    }
    data['id'] = roleId;
    var param = {url:roleModule.roleApi,data:data};
    var request = ajax.put(param);
    request.done(function (d) {
        tips.ok(d.message);
        //更新roleData中的对象
        //roleData = result.update(roleData,d.result,'id');
        closeLay();
        //renderRole();
        role.get();
    })
}
function deleteRole() {
    var request = ajax.delete({url:roleModule.roleApi+'/'+roleId});
    request.done(function (d) {
        tips.ok(d.message);
        roleData = result.delete(roleData,roleId);
        renderRole();
    })
}


//读取子系统角色权限信息
function getAuth() {

    openLay({url:'../../form/_auth.html',fun:"configRole();",title:'权限配置'});
    createAuthsTree(); //创建权限树

}


//创建权限列表
function createAuthsTree() {
    // url 下载地址 treeId 绑定div

    var zTree; //用于保存创建的树节点
    var setting = { //设置
        check: {
            enable: true,
            chkStyle: "checkbox",  //单选框
            chkboxType: {"Y": "ps", "N": "ps"},
            chkDisabledInherit :true
        },
        view: {
            showLine: false, //显示辅助线
            dblClickExpand: false,
            showIcon:false,
            expandSpeed:0,
        },
        data: {
            simpleData: {
                enable: true,
                idKey: "id",
                pIdKey: "parentId",
                rootPId: 0
            }
        },
        callback: {
            onClick: function zTreeOnClick(event, treeId, treeNode) {
                //点击对象时自动选中或取消选中
               // debugger;
                var treeObj = $.fn.zTree.getZTreeObj(treeId);
                if (treeNode.checked == false) {
                    treeObj.checkNode(treeNode, true, false);
                    return false;
                }
                if (treeNode.checked == true) {
                    treeObj.checkNode(treeNode, false, false);
                    return false;
                }
            }
        },
    };

    var param = {url: roleModule.roleApi+'/'+roleId};
    var request = ajax.get(param);
    request.done(function (d) {
        var data = d.result;
        zTree = $.fn.zTree.init($("#authTree"), setting, data); //创建树
        zTree.expandAll(true);
        var nodes = zTree.getNodesByParam("check", 1, null);
        for (var i=0, l=nodes.length; i < l; i++) {
            zTree.checkNode(nodes[i], true, false);
        }

    })
    request.error(function (d) {
        alert("创建树失败!");
    })

}

function configRole() {
    var menus = [];
    var btns = [];
    var treeObj = $.fn.zTree.getZTreeObj("authTree");
    var nodes = treeObj.getCheckedNodes(true);

        for (var i = 0; i < nodes.length; i++) {
            if(nodes[i].type=="menu"){
                var menu = {
                    menuId:nodes[i].id,
                };
                menus.push(menu);
            }else  if(nodes[i].type=="auth"){
               var btn = {
                    authId:nodes[i].id,
                }
                btns.push(btn);
            }
        }
    data = {"roleId":roleId,"menu":menus,"btns":btns};
    var param = {url:roleModule.authApi,data:data};
    var request = ajax.post(param);
    request.done(function (d) {
        tips.ok(d.message);
        closeLay();
    })

}