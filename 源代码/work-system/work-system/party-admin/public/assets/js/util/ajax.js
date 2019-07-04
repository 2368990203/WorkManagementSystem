
var store_user_info = store.get(cache_user_key);

var server_root =getRootPath();

function getRootPath() {
    if(server_root!=null&&server_root!=""){
        return server_root;
    }else {
        var host = window.location.host;
        var origin=window.location.origin;
        var root = '/';
        if (!origin) {
            origin = window.location.protocol + "//" + window.location.hostname + (window.location.port ? ':' + window.location.port: '');
        }
        switch (host) {
            case "gxmddj.gxun.edu.cn":
            case "10.240.16.116":
            case "mengwp.gxun.edu.cn":
                root =origin+ '/party-admin/';
                break;
            default:
                root = origin+'/';
                break;
        }

        return root;
    }
}


var header = {
    "Content-Type": "application/json; charset=utf-8",
    "token":(typeof store_user_info) == 'undefined' ? '' : store_user_info.token,
    "Access-Control-Allow-Origin":"*"
};
var ajax = {
    send:function (request) {
        return send(request);
    },
    get:function (request) {
        request.method = 'get';
        return send(request);
    },
    post:function (request) {
        request.method = 'post';
        return send(request);
    },
    delete:function (request) {
        request.method = 'delete';
        return send(request);
    },
    put:function (request) {
        request.method = 'put';
        return send(request);
    },
    info:function (request) {
        request.method = 'info';
        return send(request);
    }
}

function send(request) {
    return $.ajax({
        type : request.method,
        url : request.url,
        data: ajaxEmpty(request.data),
        async: typeof(request.async) == 'undefined' ? 'true' : request.async,
        contentType: "json",
        cache: false,
        headers: header,
        dataType:'json',
        beforeSend: function (xhr) {
            //是否显示加载图片，默认加载
            if(typeof(request.loading) == 'undefined' ||request.loading == true) {
                  loading.show();
            }
        },
        statusCode: {
            200: function() {
                loading.hide();
            }
        },
        error:function(e){
            loading.hide();
            var res = e.responseJSON;
            var status = e.status;
            switch (status){
                case 9527:
                    swal({
                            title: res.message,
                            type: "error",
                            confirmButtonColor: "#DD6B55",
                            confirmButtonText: "知道了",
                            closeOnConfirm: false,
                            closeOnCancel: false
                        },
                        function(isConfirm){
                            try{
                                parent.exitSys();
                            }catch (e){
                                exitSys();
                            }
                        });
                    break;
                case 502:
                    swal({
                            title: '网站正在临时维护，请稍候重新尝试！',
                            type: "info",
                            showCancelButton: false,
                            confirmButtonColor: "#DD6B55",
                            confirmButtonText: "确定",
                            closeOnConfirm: false
                        }
                    );
                    break;
                case 403:
                    swal({
                            title: "您无权访问当前页面",
                            text: "登录超时或认证信息已经超时请重新登录!",
                            type: "error",
                            confirmButtonColor: "#DD6B55",
                            confirmButtonText: "知道了",
                            closeOnConfirm: false,
                            closeOnCancel: false
                        },
                        function(isConfirm){
                            try{
                                parent.exitSys();
                            }catch (e){
                                exitSys();
                            }
                        });
                    break;
                case 406:
                    //暂时屏蔽锁屏错误
                   // parent.lockDialog();
                   // store.set(cache_user_key,res.result);
                    swal({
                            title: '请求数据有误，请重新尝试！',
                            type: "error",
                            showCancelButton: false,
                            confirmButtonColor: "#DD6B55",
                            confirmButtonText: "确定",
                            closeOnConfirm: false
                        }
                    );
                    break;
                case 411:
                    swal({
                            title: '缓存数据异常，请重新登录！',
                            type: "error",
                            showCancelButton: false,
                            confirmButtonColor: "#DD6B55",
                            confirmButtonText: "确定",
                            closeOnConfirm: false
                        },
                        function(){
                            store.clear();
                            try{
                                parent.exitSys();
                            }catch (e){
                                store.clear();
                                window.location.href = server_root;
                            }
                        }
                    );
                    break;
                default:
                    tips.error(res.message);
            }

        }
    });
}
function ajaxEmpty(value) {
    if(typeof(value)=="undefined")
        return "";
    if(typeof(value)=="object")
        return JSON.stringify(value);
    return value;
}
