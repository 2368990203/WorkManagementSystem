var type = 1, index = 1, size = 15, title = ' ';
var videoDetailData = [], onlineCourseList = [], catalogueList = [];
var id = getQueryString('id');
var chapter = getQueryString('chapter');

$(function () {
    if (checkAuth()) {
        return false;
    }
    videoDetail.getCatalogue();
    videoDetail.getInfo();

})


var videoDetail = {
    getInfo: function () {
        var param = {url: baseModule.videoListApi + '/Detail/' + id, async: false};
        var request = ajax.get(param);
        request.done(function (d) {

            videoDetailData = d.result;
            onlineCourseList = d.result.onlineCourseList;
            var coursedata = onlineCourseList[0];
            document.title = "网课学习>" + coursedata.title + ">课程目录";
            $("#location").empty();
            $("#location").html("<a href='/videoList'>网课学习</a>><a href='onlineCourseDetail?id=" + coursedata.id + "'>" + coursedata.title + "</a>" + "><a href='#'>课程目录</a>");
            render.Info();
        })
    },
    getCatalogue: function () {
        var param = {url: baseModule.onlineCourseCatalogueApi + id, async: false};
        var request = ajax.get(param);
        request.done(function (d) {
            catalogueList = d.result;
            render.Catalogue();

        })

    },


}
var render = {
    Info: function () {
        var template = doT.template($("#videoDetail-template").text());//获取的模板
        $('#videoDetail').html(template(onlineCourseList));//模板装入数据
    },
    Catalogue: function () {
        var template = doT.template($("#catalogue-template").text());//获取的模板
        $('#catalogue').html(template(catalogueList));//模板装入数据
        if (chapter != null && chapter != "") {
            var obj = document.getElementById(chapter);
            var $obj = $(obj);
            $obj.addClass("current").next("div.menu_body").slideToggle(300).siblings("div.menu_body").slideUp("slow");
            $obj.siblings().removeClass("current");
            window.location.hash = "#" + chapter;
        } else {
            $("#catalogue h3.menu_head:eq(0)").addClass("current").next("div.menu_body").slideToggle(300).siblings("div.menu_body").slideUp("slow");
        }
        $("#catalogue h3.menu_head").click(function () {
            $(this).addClass("current").next("div.menu_body").slideToggle(300).siblings("div.menu_body").slideUp("slow");
            $(this).siblings().removeClass("current");
        });
    },
};

function getQueryString(key) {
    var reg = new RegExp("(^|&)" + key + "=([^&]*)(&|$)");
    var result = window.location.search.substr(1).match(reg);
    return result ? decodeURIComponent(result[2]) : null;
}