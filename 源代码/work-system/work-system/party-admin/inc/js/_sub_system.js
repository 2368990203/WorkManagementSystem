var subsystemData = [];
var subsystemForm = '../../form/_subsystem.html';
var subsystemTitle = '子系统管理';
var subsystemId;

var subsystem = {
    create:function (event) {
        if(auth.refuse(event))
            return false;
        openLay({url:subsystemForm,fun:'createSubsystem();',title:subsystemTitle});
    },
    update:function (event) {
        if(auth.refuse(event))
            return false;
        subsystemId = getId(event);
        openLay({url:subsystemForm,fun:"updateSubsystem();",title:subsystemTitle});
        var model = result.get(subsystemData,subsystemId);
        form.set(model);
        var obj = $("form select[name='status']");
        obj.val(model.status);
    },
    delete:function (event) {
        if(auth.refuse(event))
            return false;
        subsystemId = getId(event);
        tips.confirm({message:'是否要删除这条数据？',fun:"deleteSubsystem();"});
    },
    status:function (event) {
        if(auth.refuse(event))
            return false;
        var status = $(event).val();
        subsystemId = getId(event);
        statusSubsystem(status);
    }
}

$(function () {
    if(auth.check(this)) {
        getSubsystem();
    }
})
function renderSubsystem() {
    var tmpl = doT.template($('#subsystem-tmpl').text());
    $("#subsystem-list").html(tmpl(subsystemData));
    renderTip();
}
//读取
function getSubsystem() {
    var param = {url:subsystemModule.optApi};
    var request = ajax.get(param);
    request.done(function (d) {
        subsystemData = d.result;
        renderSubsystem(d.result);
    })
}
// 创建
function createSubsystem() {
    var data = form.get("#opt-form");
    if(form.verify(data))
        return false;
    var param = {url:subsystemModule.optApi,data:data};
    var request = ajax.post(param);
    request.done(function (d) {
        tips.done(d);
        subsystemData.push(d.result);
        subsystemData = json.desc(subsystemData,'createTime');
        renderSubsystem(subsystemData);
    })
}
// 修改
function updateSubsystem() {
    var data = form.get("#opt-form");
    if(form.verify(data))
        return false;
    data['id'] = subsystemId;
    var param = {url:subsystemModule.optApi,data:data};
    var request = ajax.put(param);
    request.done(function (d) {
        tips.ok(d.message);
        closeLay();
        subsystemData = json.update(subsystemData,d.result);
        renderSubsystem(subsystemData);
    })
}
// 删除
function deleteSubsystem() {
    var request = ajax.delete({url:subsystemModule.optApi+'/'+subsystemId});
    request.done(function (d) {
        tips.ok(d.message);
        subsystemData = json.delete(subsystemData,subsystemId);
        renderSubsystem(subsystemData);
    })
}
//状态调整
function statusSubsystem(_status) {
    var data = {status:_status,id:subsystemId};
    var param = {url:subsystemModule.statusApi,data:data};
    var request = ajax.put(param);
    request.done(function (d) {
        tips.ok(d.message);
        closeLay();
        subsystemData = json.update(subsystemData,d.result);
        renderSubsystem(subsystemData);
    }).fail(function () {
        renderSubsystem(subsystemData);
    })
}
//状态显示
function format_status(_status) {
    return _status == 1 ? '正常' : (_status == 0 ? '停用' : '维护中');
}