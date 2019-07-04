var index = 1, size = 8;
// var type = "2";//2-会议管理
var listData = [];

$(function () {
    if (checkAuth()) {
        return false;
    }
    messageList.get(index, size);
})

var messageList = {
    get: function (index, size) {
        var param = {url: baseModule.messageListApi + index + '-' + size};
        var request = ajax.get(param);
        request.done(function (d) {
            listData = d.result.data;
            if (listData.length != 0 && d.result.totalPage > 1) {
                //currentPage:当前所在第几页;pageSize:每页多少条数据;totalPage:总共多少页;totalCount:总共多少条数据
                page.init(d.result.currentPage, d.result.pageSize, d.result.totalPage, d.result.totalCount);
            }else {
                $('#pagination').empty();
            }
            render.list();
        })
    },
};


var render = {
    list: function () {
        var template = doT.template($("#message-List-template").text());
        $('#message-List').html(template(listData));
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
                return "系统通知";
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
                messageList.get(index, size);
            }
        })
    }
}


