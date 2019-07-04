var index = 1, size = 4, type = 2;
var listData = [];

$(function () {
    if (checkAuth()) {
        return false;
    }
    surveyList.get(index, size);
})

var surveyList = {
    get: function (index, size) {
        var param = {url: baseModule.surveyRecordGetPageListApi + index + '-' + size + '-' + type};
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
        var template = doT.template($("#survey-list-template").text());
        $('#survey-list').html(template(listData));
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


// 5分页
var page = {
    init: function (_currentPage, _pageSize, _totalPage, _totalCount) {
        new myPagination({
            id: 'pagination',
            curPage: _currentPage, //当前页码
            pageTotal: _totalPage, //总页数
            pageAmount: _pageSize,  //每页多少条
            dataTotal: _totalCount, //总共多少条数据
            showSkipInputFlag: true, //是否支持跳转
            getPage: function (page) {
                //获取当前页数
                index = page;
                surveyList.get(index, size);
            }
        })
    }
}


