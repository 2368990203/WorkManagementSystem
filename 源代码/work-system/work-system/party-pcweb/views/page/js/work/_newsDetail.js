var type = "article", index = '1', size = '6', title = ' ', id = '123456';
var newsDetailData = [];

$(function () {
    id = url.getUrlParam("id");
    newsDetail.get();
    newsDetail.getHits();
    newsDetail.getPageInfo()
});


var newsDetail = {
    get: function () {
        var param = {url: baseModule.newsDetailsApi + '/details/' + id};
        var request = ajax.get(param);
        request.done(function (d) {
            var resultData = d.result;
            newsDetailData = d.result;
            document.title = "时事新闻>" + newsDetailData[0].title + ">新闻详情";
            $(".location").empty();
            $(".location").html("<a href=\"/newsList\"  style=\"color:#2B2B2B;text-decoration-line: none\" >时事新闻</a>><a href=\"#\" style=\"color:#2B2B2B;text-decoration-line: none\">" + newsDetailData[0].title + "</a> </div>");
            render.list();
        })
    },
    getHits: function () {
        var param = {url: baseModule.hitsApi + '/' + id + '/article'};
        var request = ajax.put(param);
        request.done(function (d) {
            var resultData = d.message;
            //  newsListData = resultData.data;
            // console.log(resultData);
            // render.list();
        })
    },
    getPageInfo: function () {//获取上下章
        var param = {url: baseModule.newsLinkApi + id};
        var request = ajax.get(param);
        request.done(function (d) {

            var page = d.result;
            var pageUpButton = "", pageDownButton = "";
            var pageCurrent = page.pageCurrent;
            if (pageCurrent != "" && pageCurrent != null) {
                var pageUp = page.pageUp;
                var pageDown = page.pageDown;


                if (pageCurrent.start != 1) {
                    if (pageUp.title.length > 25) {
                        pageUp.title = pageUp.title.substr(0, 25) + "...";
                    }
                    pageUpButton = "<a href='../newsDetail?id=" + pageUp.id + "' style='color:black;text-decoration-line: none;'>上一篇：" + pageUp.title + "</a>&emsp;";
                } else {
                    //  pageUpButton = "上一篇：当前新闻为第一篇！&emsp;";
                }

                if (pageCurrent.end != 1) {
                    if (pageDown.title.length > 25) {
                        pageDown.title = pageDown.title.substr(0, 25) + "...";
                    }
                    pageDownButton = "<a href='../newsDetail?id=" + pageDown.id + "'style='color:black;text-decoration-line: none;'>下一篇：" + pageDown.title + "</a>&emsp;<br>";
                } else {
                    //  pageDownButton = "下一篇：当前新闻为最后一篇！&emsp;";
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
        var template = doT.template($("#news-details-template").text());
        $('#news-details').html(template(newsDetailData));
    }
};


// var tool = {
//     getUrlParms(name){
//     var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
//     var r = window.location.search.substr(1).match(reg);
//     if(r!=null)
//         return unescape(r[2]);
//     return null;
// },
// }