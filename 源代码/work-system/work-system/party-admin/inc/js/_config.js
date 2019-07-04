

var global = {
    get:function () {
        var param = {url:globalModule.optApi};
        var request = ajax.get(param);
        request.done(function (d) {
            form.set(d.result);
        })
    },
    config:function (event) {
        if(auth.refuse(event))
            return false;
        var data = form.get("#opt-form");
        if(form.verify(data))
            return false;
        var param = {url:globalModule.optApi,data:json.o2av(data)};
        var request = ajax.put(param);
        request.done(function (d) {
            tips.ok(d.message);
        })
    }
}
$(function () {
    if(auth.check(this)) {
        global.get();
        initConfigFormHeight();
        $(window).resize(function () {
            initConfigFormHeight();
        });
    }
})
function initConfigFormHeight() {
    $('.config-form').css('height',(parent.adaptable().h)-92);
}

