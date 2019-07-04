var messageData = {}, id = '';

$(function () {
    if (checkAuth()) {
        return false;
    }
    id = getUrlParms("id");
    if (id != "" && id != null) {
        message.get();
    } else {
        swal({
                title: "该消息提醒不存在！",
                type: "warning",
                showCancelButton: false,
                confirmButtonColor: "#DD6B55",
                confirmButtonText: "确定",
                closeOnConfirm: false
            },
            function () {
                window.location.href = server_root + 'message';
            }
        );
    }
})

var message = {
    get: function () {
        var param = {url: baseModule.messageListApi + 'info/' + id};
        var request = ajax.get(param);
        request.done(function (d) {
            messageData = d.result;

            // console.log(messageData);
            render.list();
            if (messageData.readStatus != 1) {
                var param = {url: baseModule.messageListApi + 'read/' + id};
                var request = ajax.put(param);
            }
            message.getPageInfo();

        })
    },
    delete: function (event) {
        var optId = getId(event);
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
                var request = ajax.delete({url: baseModule.messageListApi + optId});
                request.done(function (d) {
                    swal({
                            title: d.message,
                            type: "success",
                            showCancelButton: false,
                            confirmButtonColor: "#DD6B55",
                            confirmButtonText: "确定",
                            closeOnConfirm: false
                        },
                        function () {
                            window.location.href = server_root + 'message';
                        }
                    );
                })
            }
        );


    }, getPageInfo: function () {//获取上下章
        var param = {url: baseModule.messageListApi + 'page/' + id};
        var request = ajax.get(param);
        request.done(function (d) {

            var page = d.result;
            var pageUpButton = "", pageDownButton = "";
            var pageCurrent = page.pageCurrent;
            if (pageCurrent != "" && pageCurrent != null) {
                var pageUp = page.pageUp;
                var pageDown = page.pageDown;


                if (pageCurrent.start != 1) {

                    pageUpButton = ' <a href="../messageDetail?id=' + pageUp.id + '" class="btn-page page-prev" title="上一条"> <i class="icon-page-prev"></i></a>';
                } else {

                    // pageUpButton = "上一篇：当前新闻为第一篇！&emsp;";
                }

                if (pageCurrent.end != 1) {

                    pageDownButton = ' <a href="../messageDetail?id=' + pageDown.id + '" class="btn-page page-prev" title="下一条"> <i class="icon-page-next"></i></a>';

                } else {
                    //   pageDownButton = "下一篇：当前新闻为最后一篇！&emsp;";
                }


                $("#page").empty();
                $("#page").html(
                    "<span>" + pageUpButton + "</span>" +
                    "<span>" + pageDownButton + "</span>"
                );
            }
        });
    }


};


var render = {
    list: function () {
        var template = doT.template($("#message-template").text());
        $('#message').html(template(messageData));
    }
};


var helper = {
    type: function (_type) {
        switch (parseInt(_type)) {
            case 0 :
                return "系统通知";
            case 1 :
                return "课程安排";
            case 2 :
                return "会议安排";
            case 3 :
                return "作业";
            case 4 :
                return "入党申请书";
            case 5 :
                return "思想汇报";
            default:
                return "其他";
        }
    },
    url: function (_type) {
        switch (parseInt(_type)) {
            case 1 :
                return "../courseArrangementList";
            case 2 :
                return "../meetingList";
            case 3 :
                return "../examList";
            case 4 :
                return "../applicationManage";
            case 5 :
                return "../thoughReportList";
            default:
                return "#";
        }
    },
    detailUrl: function (_type) {
        switch (parseInt(_type)) {
            case 1 :
                return "../courseArrangementDetail?id=";
            case 2 :
                return "../meetingDetail?meetingId=";
            case 3 :
                return "../exam?examId=";
            case 4 :
                return "../applicationManage?=";
            case 5 :
                return "../thoughReportManage?id=";
            default:
                return "#";
        }
    },
};


function getUrlParms(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null)
        return unescape(r[2]);
    return null;
}



