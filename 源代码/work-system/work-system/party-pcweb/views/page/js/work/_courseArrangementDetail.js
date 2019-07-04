var index = 1, size = 6, id = '';
// var type = "2";//2-会议管理
var courseArrangementDetailData = [];

$(function () {
    if (checkAuth()) {
        return false;
    }

    id = url.getUrlParam("id");
    courseArrangementDetail.get();
})

var courseArrangementDetail = {
    get: function () {
        var param = {url: baseModule.courseArrangementDetail + '/MeetingDetail/' + id};
        var request = ajax.get(param);
        request.done(function (d) {
            courseArrangementDetailData = d.result;
            document.title = "课程安排>" + courseArrangementDetailData[0].topic + ">课程详情";
            $(".location").empty();
            $(".location").html("<a href='/courseArrangementList' style='color:#2B2B2B;text-decoration-line: none' >课程安排</a>><a href='#' style='color:#2B2B2B;text-decoration-line: none'>" + courseArrangementDetailData[0].topic + "</a> ");
            render.list();
        })
    },

    vacate: function (event) {
        var scheduleId = getId(event);
        $("#confirm").attr("data-id", scheduleId);
        $("#vacate").fadeIn();

    }

    , vacateDetail: function (event) {
        var scheduleId = getId(event);
        $("#confirm").attr("data-id", scheduleId);
        $("#vacateDetail").fadeIn();
        $("#vacateReason").html(courseArrangementDetailData[0].reason);
        var vacateStatus = courseArrangementDetailData[0].vacateStatus;
        $("#status").html(helper.vacateStatus(vacateStatus));
        if (vacateStatus == 2) {
            $("#result").text("请假结果：" + helper.result(courseArrangementDetailData[0].result));
            $("#opinion").html("批假意见：" + courseArrangementDetailData[0].opinion);
        }
    }

    , confirm: function (event) {
        // this.Close();
        //
        // return false;

        var scheduleId = getId(event);
        var reason = $("#reason").val();
        if (reason == "" || reason.length == 0) {
            tips.warning("请输入请假事由");
            return false;
        }
        var param = {url: baseModule.vacateApi + '/course/' + scheduleId + '-' + reason};
        var request = ajax.post(param);
        request.done(function (d) {
            $(".popUp").fadeOut();
            tips.ok("提交成功");
            courseArrangementDetail.get();
        });
    }

    , Close: function () {
        $(".popUp").fadeOut();

    }


}
var render = {
    list: function () {
        var template = doT.template($("#courseArrangement-details-template").text());
        $('#courseArrangement-details').html(template(courseArrangementDetailData));
    }
};
var helper = {
    status: function (_status) {
        switch (parseInt(_status)) {
            case 1 :
                return "未发布";//不显示未发布
            case 2 :
                return "未开始";
            case 3 :
                return "进行中";
            case 4 :
                return "已取消";
            case 5 :
                return "已结束";
            default:
                return "";
        }
    },

    result: function (_result) {
        switch (parseInt(_result)) {
            case 1 :
                return "同意";
            case 2 :
                return "不同意";
            default:
                return "未批假";
        }
    },
    vacateStatus: function (_vacateStatus) {
        switch (parseInt(_vacateStatus)) {
            case 1 :
                return "未批假";
            case 2 :
                return "已批假";
            default:
                return "";
        }
    },
    attendance: function (_status) {
        switch (parseInt(_status)) {
            case 1 :
                return "未签到";
            case 2 :
                return "已签到";
            case 3 :
                return "已请假";
            default:
                return "未签到";
        }
    },
}



