var index = 1, size = 6, id = '';
// var type = "2";//2-会议管理
var surveyDetailData = [];

$(function () {
    if (checkAuth()) {
        return false;
    }
    id = url.getUrlParam("surveyId");
    surveyDetail.get();
})

var surveyDetail = {
    get: function () {
        var param = {url: baseModule.surveyRecordGetUserInfoApi + id};
        var request = ajax.get(param);
        request.done(function (d) {
            surveyDetailData = d.result;
            document.title = "考察谈话>考察记录>" + (helper.stage(surveyDetailData[0].stage) + "第" + surveyDetailData[0].count + "次" + helper.type(surveyDetailData[0].type)) + ">考察详情";
            $(".location").empty();
            $(".location").html("<a href='/surveyRecordList'>考察记录</a>><a href='#'>" + (helper.stage(surveyDetailData[0].stage) + "第" + surveyDetailData[0].count + "次" + helper.type(surveyDetailData[0].type)) + "</a>");
            render.list();
        })
    },


}
var render = {
    list: function () {
        var template = doT.template($("#survey-details-template").text());
        $('#survey-details').html(template(surveyDetailData));
    }
};


var helper = {
    type: function (_type) {
        if (_type == 1) {
            return "考察";
        }
        if (_type == 2) {
            return "谈话";
        }
    },

    stage: function (_stage) {
        switch (parseInt(_stage)) {
            case 1:
                return "积极分子";
            case 2:
                return "发展对象";
            case 3:
                return "预备党员";
        }
    },
}


