var userData = [], passwordData = [], rolesData = [], tagsData = [];
var problemData = [];
var index = 1, size = 12, key = '', loginName = '';
var config = {
    title: '修改密码',
}


function validCheckPwd() {
    var pwd = document.getElementById('password');
    var checkPwd = document.getElementById('passwordCheck');


    if (pwd.value != checkPwd.value) {
        // tips.warning("密码长度应该在 6 - 16 位");
        $('#checkshow').show();

        return false;
    }
    $('#checkshow').hide();


    return true;
}


function validOldPwd() {
    var oldPwd = document.getElementById('oldPassword');

    if (oldPwd.value != "" && (oldPwd.value.length > 16 || oldPwd.value.length < 6)) {
        // tips.warning("密码长度应该在 6 - 16 位");
        $('#oldshow').show();
        return false;
    }
    $('#oldshow').hide();

    return true;
}


$(function () {
    //自适应
    $('#oldPassword').val('');
    $('#password').val('');
    $('#passwordCheck').val('');


    if (auth.check(this)) {
        view.initHeight();
        $(window).resize(function () {
            view.initHeight();
        });
    }
});

var updatePassword = {

    update: function () {

        if (!validOldPwd())
            return false;
        if (!validCheckPwd())
            return false;
        var data = form.get("#opts-form");
        if (form.verify(data))
            return false;


        var param = {url: commonModule.getPublicKeyApi, async: false};
        var request = ajax.get(param);
        request.done(function (d) {
            var key = d.result;
            var Encrypt = new JSEncrypt();
            Encrypt.setPublicKey(key);
            data['oldPassword'] = Encrypt.encrypt(data['oldPassword']);
            data['password'] = Encrypt.encrypt(data['password']);
            data['passwordCheck'] = Encrypt.encrypt(data['passwordCheck']);

        })

        var param = {url: userModule.updatePasswordApi + '/', data: data};
        var request = ajax.post(param);
        request.done(function (d) {
            tips.ok(d.message);
            updatePassword.reset();
        })
    },


    reset: function () {
        $("input[name='oldPassword']").val("");
        $("input[name='password']").val("");
        $("input[name='passwordCheck']").val("");
    }
}

var opt = {}


// 渲染
var render = {}

// 视图界面
var view = {
    initHeight: function () {
        $('.data-view').css('height', (parent.adaptable().h) - 80);
        $('.date-table').css('height', (parent.adaptable().h) - 180);
        size = Math.floor(((parent.adaptable().h) - 180) / 40);
    }
}


