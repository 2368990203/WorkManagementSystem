var store_user_info = store.get(cache_user_key);
var indexTitle = '作业管理系统';
function setTitle(_title) {
    title.set(indexTitle + _title);
}

function configAuth(){
    var store_user_info = store.get(cache_user_key);
    var auth = store_user_info.auth;

    if(auth==undefined||auth==null||auth==""){
        store.clear();
        window.location.href =server_root;
        return false;
    }

    var btns = [];
    var funs = [];
    var urls = [];
    for (var i = 0; i < auth.length; i++) {
        var modules = auth[i].module;
        for (var j = 0; j < modules.length; j++) {
            var subs = modules[j].sub;
            for (var k = 0; k < subs.length; k++) {
                if(subs[k].url!="/"&&subs[k].url!=""&&subs[k].url!=null){
                    urls.push(subs[k].url);
                }

                var btn = subs[k].btns;
                for (var l = 0; l < btn.length; l++) {
                    funs.push(btn[l]['fun']);
                    btns.push(btn[l]);
                }
            }
        }
    }
    store.set(cache_user_key+'-url-auth', urls);
    store.set(cache_user_key+'-btn-auth', btns);
    store.set(cache_user_key+'-fun-auth', funs);
    //debugger;
    // console.log(auth);
    var evalText = doT.template($("#menu-tmpl").text());
    $("#sys-menu").html(evalText(auth));
    initMenuStyle();
    $(".J_menuItem").on('click', function () {
        var url = $(this).attr('href');
        var title = $(this).attr('data-title');
        $("#J_iframe").attr('src', url);
        (typeof title) != 'undefined' ?
            $('#page-title').html(title) : '';
        return false;
    });
}



//更新缓存
function cacheUpdate() {
    var request = $.ajax({
        type: 'PUT',
        url: userModule.cacheUpdateApi,
        headers: {token: store_user_info.token},
        error: function (e) {
            var obj = e.responseJSON;
            if (e.status == 403) {
                swal({
                        title: obj.message,
                        type: "error",
                        showCancelButton: true,
                        confirmButtonColor: "#DD6B55",
                        confirmButtonText: "确定",
                        closeOnConfirm: false
                    },
                    function () {
                        store.clear();
                        window.location.href = server_root;
                    }
                );
            } else {
                swal('', obj.message, "warning");
            }
        }
    });
    request.done(function (res) {
        store.set(cache_user_key, res.result);
        configAuth();
        window.location.href =server_root;
    })
}



$(function () {
    setTitle('');
    //权限
    if ((typeof store_user_info) == 'undefined') {
        swal({
                title: '缓存数据异常请重新登录',
                type: "error",
                showCancelButton: false,
                confirmButtonColor: "#DD6B55",
                confirmButtonText: "确定",
                closeOnConfirm: false
            },
            function () {
                store.clear();
                window.location.href = server_root;
            }
        );
        return false;
    } else {
        var auth = store_user_info.auth;

        if(auth==undefined||auth==null||auth==""){
            swal({
                    title: '您没有权限访问后台！',
                    type: "error",
                    showCancelButton: false,
                    confirmButtonColor: "#DD6B55",
                    confirmButtonText: "确定",
                    closeOnConfirm: false
                },
                function () {
                    store.clear();
                    window.location.href = server_root;
                    return false;
                }
            );
            return false;
        }

        var btns = [];
        var funs = [];
        var urls = [];
        for (var i = 0; i < auth.length; i++) {
            var modules = auth[i].module;
            for (var j = 0; j < modules.length; j++) {
                var subs = modules[j].sub;
                for (var k = 0; k < subs.length; k++) {
                    if(subs[k].url!="/"&&subs[k].url!=""&&subs[k].url!=null){
                        urls.push(subs[k].url);
                    }
                    var btn = subs[k].btns;
                    for (var l = 0; l < btn.length; l++) {
                        funs.push(btn[l]['fun']);
                        btns.push(btn[l]);
                    }
                }
            }
        }
        store.set(cache_user_key+'-url-auth', urls);
        store.set(cache_user_key+'-btn-auth', btns);
        store.set(cache_user_key+'-fun-auth', funs);

        var evalText = doT.template($("#menu-tmpl").text());
        $("#sys-menu").html(evalText(auth));
        initMenuStyle();
        $(".J_menuItem").on('click', function () {
            var url = $(this).attr('href');
            var title = $(this).attr('data-title');
            $("#J_iframe").attr('src', url);
            (typeof title) != 'undefined' ?
                $('#page-title').html(title) : '';
            return false;
        });


    }
    //欢迎页
    welcome();
    //自适应页面
    autoMenu();
    $(window).resize(function () {
        var  iframe=window.frames['J_iframe'];
        if(iframe!=null){
            if(iframe.autoWin) {
                iframe.autoWin();
            }
        }
        autoMenu();
    });
    initUserInfo();
    //60秒钟刷新一次数据
    // getMessage();
    // setInterval(getMessage,60000);

})


var helper = {
    sendStatus: function (_sendStatus) {
        switch (parseInt(_sendStatus)) {
            case 1:
                return "未发送";
            case 2:
                return "已发送";
            default:
                return "已发送";

        }
    },
    type: function (_type){
        switch (parseInt(_type)){
            case 0 : return "系统通知";
            case 1 : return "课程安排";
            case 2 : return "会议安排";
            case 3 : return "作业";
            case 4 : return "入党申请书";
            case 5 : return "思想汇报";
            default:
                return "系统通知";
        }
    },
    url: function (_type){
        switch (parseInt(_type)) {
            case 0: return "#";
            case 1 : return  server_root+"my/core/schedule";
            case 2 : return  server_root+"my/core/meetingSchedule";
            case 3 : return  server_root+"my/core/exam";
            case 4 : return  server_root+"my/core/application";
            case 5 : return  server_root+"my/core/report";
            default:
                return "#";
        }
    },    detailUrl: function (_type) {
        switch (parseInt(_type)) {
            case 0:
                return "#";
            case 1 :
                return  server_root+"my/core/schedule?id=";
            case 2 :
                return  server_root+"my/core/meetingSchedule?id=";
            case 3 :
                return  server_root+"my/core/exam?id=";
            case 4 :
                return  server_root+"my/core/application?id=";
            case 5 :
                return  server_root+"my/core/report?id=";
            default:
                return "#";
        }
    }
};

function getMessage() {
    var request = $.ajax({
        type: 'get',
        url: baseModule .indexMessageListApi ,
        headers: {token: store_user_info.token},

    });

    request.done(function (d) {
        var messageData= d.result;
        var count =parseInt(messageData.count);
        var  messageListData =messageData.messages;
        if(count>0) {
            $('#messageCount').html(count);
        }
        var template = doT.template($("#message-list-template").text());//获取的模板
        $('#message-list').html(template(messageListData));//模板装入数据

        if (messageListData.length>=3){
            $('#message-list').append(' <li class="dropdown-menu-footer text-center">' +
                '<a href="#" onclick="readMore()">查看全部消息</a>' +
                ' </li>');
        }

    })
}



function readMessage(event) {

    var optId = $(event).attr('data-id');
    $('.collapse').removeClass('in');
    $('.left_but').removeClass('active');
    $('#J_menuItem_0_0_1').click().parents('.collapse').addClass('in').parents('.left_but').addClass('active');
    $('#J_menuItem_0_0_1').focus();
    $('#J_iframe').attr('src', server_root+'user/message/?id='+optId);//临时路径


}

function deleteMessage() {

    swal({
            title: "是否要删除这条消息？",
            type: "warning",
            showCancelButton: true,
            confirmButtonColor: ((typeof color) == "undefined") ? "#DD6B55" : color,
            confirmButtonText: ((typeof enter) == 'undefined') ? '删除' : enter,
            cancelButtonText: ((typeof cancel) == 'undefined') ? "容我再想想" : cancel,
            closeOnConfirm: false
        },
        function () {
            var request = $.ajax({
                type: 'delete',
                url: baseModule.messageApi  +'/'+ optId ,
                headers: {token: store_user_info.token},

            });

            request.done(function (d) {
                closeLay();
                tips.ok(d.message);
                getMessage();
            })
        }
    );

}



function readMore() {
    $('.collapse').removeClass('in');
    $('.left_but').removeClass('active');
    $('#J_menuItem_0_0_1').click().parents('.collapse').addClass('in').parents('.left_but').addClass('active');
    $('#J_menuItem_0_0_1').focus();
    $('#J_iframe').attr('src', server_root+ 'user/message');//临时路径
}

//自适应
function adaptable() {
    var w = $('#J_iframe').width();
    var h = $('#J_iframe').height();
    return {w: w, h: h};
}

function autoMenu() {
    var h = $('#J_iframe').height();
    $('#sys-menu').css('height', h + 2);
}

function initUserInfo() {
    var user = store.get(cache_user_key);
    var info = user.info;
    var role = user.role;
    // $('#user-info-header').attr('src', info.header == '' ? 'assets/images/header.gif' : info.header);
    $('#user-info-header').attr('src', 'assets/images/header.gif');
    $('#user-info-name').html(user.loginName);
    // $('#user-info-role-name').html(info.roleName);
}

//欢迎页
function welcome() {
    $('#J_iframe').attr('src', server_root+ 'my/core/exam');
    $('#page-title').html('系统基本信息')
}

function loginOut() {
    if ((typeof store_user_info) == 'undefined') {
        swal({
                title: '缓存数据异常请重新登录',
                type: "error",
                showCancelButton: false,
                confirmButtonColor: "#DD6B55",
                confirmButtonText: "确定",
                closeOnConfirm: false
            },
            function () {
                store.clear();
                window.location.href = server_root;
            }
        );
        return false;
    }

    swal({
            title:"",
            text: '是否要退出系统？',
            type: "warning",
            showCancelButton: true,
            confirmButtonColor: "#DD6B55",
            confirmButtonText: "退出",
            cancelButtonText: "再等等",
            closeOnConfirm: true
        },
        function () {

            var request = $.ajax({
                type: 'POST',
                url: userModule.loginOutApi,
                headers: {token: store_user_info.token},
                contentType: "application/json; charset=utf-8",
                dataType: "json",
                error: function (e) {
                    var obj = e.responseJSON;
                    swal('', obj.message, "warning");
                }
            });
            request.done(function (res) {
                store.clear();
                window.location.href =server_root;
            })
        }
    );

}

function lock() {
    swal({
            title: '是否要立刻锁定界面',
            type: "warning",
            showCancelButton: true,
            confirmButtonColor: "#DD6B55",
            confirmButtonText: "马上锁定",
            cancelButtonText: "再等等",
            closeOnConfirm: true
        },
        function () {
            if ((typeof store_user_info) == 'undefined') {
                swal({
                        title: '缓存数据异常请重新登录',
                        type: "error",
                        showCancelButton: true,
                        confirmButtonColor: "#DD6B55",
                        confirmButtonText: "确定",
                        closeOnConfirm: false
                    },
                    function () {
                        store.clear();
                        window.location.href =server_root;
                    }
                );
                return false;
            }
            var request = $.ajax({
                type: 'POST',
                url: userModule.lock,
                headers: {token: store_user_info.token},
                contentType: "application/json; charset=utf-8",
                dataType: "json",
                error: function (e) {
                    var obj = e.responseJSON;
                    if (e.status == 403) {
                        swal({
                                title: obj.message,
                                type: "error",
                                showCancelButton: true,
                                confirmButtonColor: "#DD6B55",
                                confirmButtonText: "确定",
                                closeOnConfirm: false
                            },
                            function () {
                                store.clear();
                                window.location.href =server_root;
                            }
                        );
                    }

                }
            });
            request.done(function (res) {
                //  console.log(res)
                lockDialog();
            })
        }
    );
}

function unlock() {
    if ((typeof store_user_info) == 'undefined') {
        swal({
                title: '缓存数据异常请重新登录',
                type: "error",
                showCancelButton: true,
                confirmButtonColor: "#DD6B55",
                confirmButtonText: "确定",
                closeOnConfirm: false
            },
            function () {
                store.clear();
                window.location.href =server_root;
            }
        );
        return false;
    }
    var pwd = $.trim($('#lock-password').val());
    if (pwd.length == 0) {
        swal('', '请输入解锁密码', 'warning');
        return false;
    }
    var data = {password: pwd};

    var request = $.ajax({
        type: 'PUT',
        url: userModule.lock,
        headers: {token: store_user_info.token},
        data: JSON.stringify(data),
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        error: function (e) {
            var obj = e.responseJSON;
            swal('', obj.message, "warning");
        }
    });
    request.done(function (res) {
        store.set(cache_user_key, res.result);
        $('#opt-lock').modal('hide');
        window.location.reload();
    })
}

function lockDialog() {
    $('#opt-lock').modal({
        keyboard: false,
        backdrop: 'static'
    });
}

function closeNav() {
    $('.navbar-right li').removeClass('open');
}

function exitSys() {
    store.clear();
    window.location.href = server_root;
}

