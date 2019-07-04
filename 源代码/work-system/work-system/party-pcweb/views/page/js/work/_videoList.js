var type = 1, index = 1, size = 5, title = ' ';
var videoListData = [];

$(function () {
    if (checkAuth()) {
        return false;
    }
    videoList.get(index, size);
})


var videoList = {
    get: function (index, size) {
        var param = {url: baseModule.videoListApi + '/' + index + '-' + size + '-' + title};
        var request = ajax.get(param);
        request.done(function (d) {
            videoListData = d.result.data;
            render.page();
            if (videoListData.length != 0 && d.result.totalPage > 1) {
                //currentPage:当前所在第几页;pageSize:每页多少条数据;totalPage:总共多少页;totalCount:总共多少条数据
                page.init(d.result.currentPage, d.result.pageSize, d.result.totalPage, d.result.totalCount);
            }else {
                $('#pagination').empty();
            }
        })
    },


}
var render = {
    page: function () {
        var template = doT.template($("#videoList-template").text());//获取的模板
        $('#videoList').html(template(videoListData));//模板装入数据
    }
};


// 5分页
var page = {
    init: function (_currentPage, _pageSize, _totalPage, _totalCount) {
        new myPagination({
            id: 'pagination',
            curPage: _currentPage, //当前页码
            pageTotal: _totalPage, //总页数
            pageAmount: _pageSize,  //每页多少条
            dataTotal: _totalCount, //总共多少条数据
            // pageSize: size, //可选,分页个数
            //showPageTotalFlag:true, //是否显示数据统计
            showSkipInputFlag: true, //是否支持跳转
            getPage: function (page) {
                //获取当前页数
                index = page;
                videoList.get(index, size);
            }
        })
    }
}
