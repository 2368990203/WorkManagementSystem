var examData = [], userData = {};

$(function () {
    if (checkAuth()) {
        return false;
    }

    id = url.getUrlParam("examId");
    if (null == id || "" == id) {
        tips.info("试卷不存在！");
    } else {
        exam.get();
    }
   /* var count = 0;
    document.addEventListener('visibilitychange', function () {
        if (document.visibilityState == 'hidden') {
            count++;
            normal_title = document.title;
            document.title = '(づ￣ 3￣)づ' + ":" + count;
        } else {
            document.title = normal_title;
        }
    });*/

})

var exam = {
    get: function () {
        var param = {url: baseModule.examInfoApi + '/' + id};
        var request = ajax.get(param);
        request.done(function (d) {
            examData = d.result;
            //console.log(examData);
            document.title = "作业>" + examData['examName'] + ">须知";
            render.info();
            exam.showUser();
        })
    },
    showUser: function () {
        var object = store.get(cache_user_key);
        userData = object.info;
        render.user();
    }
}
var render = {
    info: function () {
        var template = doT.template($("#examTips-template").text());
        $('#examTips').html(template(examData));
    },
    user: function () {
        var template = doT.template($("#user-template").text());
        $('#user').html(template(userData));
    }
};


var helper = {
    status: function (_status) {
        switch (parseInt(_status)) {
            case 0:
                return "未开始";
            case 1:
                return "进行中";
            case 2:
                return "已结束";
        }
    },
    type: function (_type) {
        switch (parseInt(_type)) {
            case 1:
                return "课外作业";
            case 2:
                return "期中作业";
            case 3:
                return "期末作业";
        }
    }, length: function (_length) {
        switch (parseInt(_length)) {
            case 0:
                return "不限时间";
            default:
                return _length+"分钟";
        }
    },
};


