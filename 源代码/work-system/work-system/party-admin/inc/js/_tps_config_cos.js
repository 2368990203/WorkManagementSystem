var code = 'cos';
var cos = {
    config:function(event){
        if(auth.refuse(event))
            return false;
        createConfig();
    }
}
$(function(){
    if(auth.check(this)) {
        getConfig();
    }
})

function getConfig() {
    var param = {url:tpsModule.optApi+'/'+code};
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
