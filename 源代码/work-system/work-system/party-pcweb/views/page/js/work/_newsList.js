var type = "1", index = '1', size = '6', title = ' ';
var newsListData = [], picturesData = [];


$(function () {
    newsList.get(index, size);

})


var newsList = {
    get: function (index, size) {
        var param = {url: baseModule.newsListApi + '/list/' + index + '-' + size + '-' + title};
        var request = ajax.get(param);
        request.done(function (d) {
            var resultData = d.result;
            newsListData = resultData.data;
            render.list();
            if (newsListData.length != 0 && d.result.totalPage > 1) {
                //currentPage:当前所在第几页;pageSize:每页多少条数据;totalPage:总共多少页;totalCount:总共多少条数据
                page.init(d.result.currentPage, d.result.pageSize, d.result.totalPage, d.result.totalCount);
            }else {
                $('#pagination').empty();
            }
        })
    },
    getPictures: function () {
        var param = {url: baseModule.newsPicturesApi + '/' + id};
        var request = ajax.get(param);
        request.done(function (d) {
            picturesData = d.result.pictureId;
            render.page();   //图片界面渲染
        })
    },


}
var render = {
    list: function () {
        var template = doT.template($("#news-list-template").text());
        $('#news-list').html(template(newsListData));
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
                newsList.get(index, size);
            }
        })
    }
}
