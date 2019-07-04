var code = 'oss';
var oss = {
    config:function(event){
        if(auth.refuse(event))
            return false;
        createConfig();
    },
    test:function (event) {
        var param = {url:tpsModule.ossApi+'/sts'};
        var request = ajax.get(param);
        request.done(function (d) {
            tips.ok(d.message);
        })
    }
}
$(function(){
    if(auth.check(this)) {
        getConfig();
    }
})

function getConfig() {
    var param = {url:tpsModule.configApi+'/'+code};
    var request = ajax.get(param);
    request.done(function (d) {
        form.set(d.result);
    })
}
// 创建
function createConfig() {
    var data = form.get("#opt-form");
    if(form.verify(data))
        return false;
    var param = {url:tpsModule.optApi+'/'+code,data:json.o2av(data,code)};
    var request = ajax.put(param);
    request.done(function (d) {
        tips.ok(d.message);
    })
}
