var index = 1, size = 6, id = '';
// var type = "2";//2-会议管理
var meetingDetailData = [];

$(function () {
    if (checkAuth()) {
        return false;
    }

    id = url.getUrlParam("meetingId");
    meetingDetail.get();
})

var meetingDetail = {
    get: function () {
        var param = {url: baseModule.meetingApi + '/MeetingDetail/' + id};
        var request = ajax.get(param);
        request.done(function (d) {
            meetingDetailData = d.result;
            document.title = "会议安排>" + meetingDetailData[0].topic + ">会议详情";
            $(".location").empty();
            $(".location").html("<a href=\"meetingList\"  style=\"color:#2B2B2B;text-decoration-line: none\" >会议安排</a>> <a href=\"#\">" + meetingDetailData[0].topic + "</a>");
            render.list();
        })
    },
    vacate: function (event) {
        var scheduleId = getId(event);
        $("#confirm").attr("data-id", scheduleId);
        $("#vacate").fadeIn();

    },

    vacateDetail: function (event) {
        var scheduleId = getId(event);
        $("#confirm").attr("data-id", scheduleId);
        $("#vacateDetail").fadeIn();
        $("#vacateReason").text(meetingDetailData[0].reason);
        var vacateStatus = meetingDetailData[0].vacateStatus;
        $("#status").text(helper.vacateStatus(vacateStatus));
        if (vacateStatus == 2) {
            $("#result").text("请假结果：" + helper.result(meetingDetailData[0].result));
            $("#opinion").html("批假意见：" + meetingDetailData[0].opinion);
        }
    },

    confirm: function (event) {
        var scheduleId = getId(event);
        var reason = $("#reason").val();

        if (reason == "" || reason.length == 0) {
            tips.warning("请输入请假事由");
            return false;
        }

        var param = {url: baseModule.vacateApi + '/meeting/' + scheduleId + '-' + reason};
        var request = ajax.post(param);
        request.done(function (d) {
            tips.ok("提交成功");
            $(".popUp").fadeOut();
        });
    },

    Close: function () {
        $(".popUp").fadeOut();
    }


}
var render = {
    list: function () {
        var template = doT.template($("#meeting-details-template").text());
        $('#meeting-details').html(template(meetingDetailData));
    }
};
var helper = {
    status: function (_status) {
        switch (parseInt(_status)) {
            case 1 :
                return "未发布";
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

// var tool = {
//     getUrlParms(name) {
//         var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
//         var r = window.location.search.substr(1).match(reg);
//         if (r != null)
//             return unescape(r[2]);
//         return null;
//     },
// }



