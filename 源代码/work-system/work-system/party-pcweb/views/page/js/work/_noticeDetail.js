var type = "article", index = '1', size = '6', title = ' ', id = '3183589030699234000';
var noticeDetailData = [];

$(function () {
    id = url.getUrlParam("id");
    noticeDetail.get();
    noticeDetail.getHits();
    noticeDetail.getPageInfo();

});


var noticeDetail = {
    get: function () {
        var param = {url: baseModule.noticeDetailApi + '/details/' + id};
        var request = ajax.get(param);
        request.done(function (d) {
            noticeDetailData = d.result;
            document.title = "通知公告>" + noticeDetailData[0].title + ">公告详情";
            $(".location").empty();
            $(".location").html("<a href=\"/noticeList\"  style=\"color:#2B2B2B;text-decoration-line: none\" >通知公告</a>><a href=\"#\" style=\"color:#2B2B2B;text-decoration-line: none\">" + noticeDetailData[0].title + "</a>");
            render.list();
        })
    },
    getHits: function () {
        var param = {url: baseModule.hitsApi + '/' + id + '/notice'};
        var request = ajax.put(param);
        request.done(function (d) {
            var resultData = d.message;
            //  newsListData = resultData.data;
            // console.log(resultData);
            // render.list();
        })
    },
    getPageInfo: function () {//获取上下章
        var param = {url: baseModule.noticeLinkApi + id};
        var request = ajax.get(param);
        request.done(function (d) {
            //console.log(d);
            var page = d.result;
            var pageUpButton = "", pageDownButton = "", pageCurrentButton = "";
            var pageCurrent = page.pageCurrent;
            if (pageCurrent != "" && pageCurrent != null) {
                var pageUp = page.pageUp;
                var pageDown = page.pageDown;

                if (pageCurrent.start != 1) {
                    if (pageUp.title.length > 25) {
                        pageUp.title = pageUp.title.substr(0, 25) + "...";
                    }
                    pageUpButton = "<a href='../noticeDetail?id=" + pageUp.id + "' style='color:black;text-decoration-line: none;' >上一篇：" + pageUp.title + "</a>&emsp;";
                } else {
                    //  pageUpButton = "上一篇：当前公告为第一篇！&emsp;";
                }

                if (pageCurrent.end != 1) {
                    if (pageDown.title.length > 25) {
                        pageDown.title = pageDown.title.substr(0, 25) + "...";
                    }
                    pageDownButton = "<a href='../noticeDetail?id=" + pageDown.id + "' style='color:black;text-decoration-line: none;'>下一篇：" + pageDown.title + "</a>&emsp;<br>";
                } else {
                    // pageDownButton = "下一篇：当前公告为最后一篇！&emsp;";
                }


                $("#pageButton").empty();
                $("#pageButton").html(
                    "<span style='float: left'>" + pageUpButton + "</span>" +
                    "<span  style='float: right'>" + pageDownButton + "</span>"
                );
            }
        })
    },


}
var render = {
    list: function () {
        var template = doT.template($("#notice-details-template").text());
        $('#notice-details').html(template(noticeDetailData));
    }
};

