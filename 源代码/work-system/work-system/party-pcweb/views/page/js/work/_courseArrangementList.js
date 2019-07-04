var index = 1, size = 6;
var listData = [];

$(function () {
    if (checkAuth()) {
        return false;
    }
    courseList.get(index, size);
});

var courseList = {
    get: function (index, size) {
        var param = {url: baseModule.courseArrangement + '/ScheduleList/' + index + '-' + size};
        var request = ajax.get(param);
        request.done(function (d) {
            listData = d.result.data;
            render.list();
            if (listData.length != 0 && d.result.totalPage > 1) {
                //currentPage:当前所在第几页;pageSize:每页多少条数据;totalPage:总共多少页;totalCount:总共多少条数据
                page.init(d.result.currentPage, d.result.pageSize, d.result.totalPage, d.result.totalCount);
            }else {
                $('#pagination').empty();
            }
        })
    },
};


var render = {
    list: function () {
        var template = doT.template($("#courseArrangement-list-template").text());
        $('#courseArrangement-list').html(template(listData));
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
                courseList.get(index, size);
            }
        })
    }
}
