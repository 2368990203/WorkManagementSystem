var index = 1, size = 6, key = '', totalPage = 0, totalCount = 0;
var baseData = [], pageData = [];
var messageData = [];
var optId = '';

$(function () {
    if (auth.check(this)) {
        //自适应
        view.initHeight();
        $(window).resize(function () {
            view.initHeight();
        });
        message.get();
        setInterval(message.get(), 60000);

    }
});

var message = {
    get: function () {
        var param = {url: baseModule.approvalListApi + '/todo/' + index + '-' + size + '-' + key + '/'};
        var request = ajax.get(param);
        request.done(function (d) {
            pageData = d.result.data;
            render.page();
            totalPage = d.result.totalPage;
            totalCount = d.result.totalCount;
            if (d.result.totalPage > 1) {
                page.init(d.result.totalPage, d.result.totalCount);
            } else {
                $('.list-page').empty();
            }
            auth.show();
        })
    },
};

var render = {
    page: function () {
        var template = doT.template($("#message-template").text());//获取的模板
        $('#item-list').html(template(pageData));//模板装入数据
    },
};

var page = {
    init: function (_pageSize, _total) {
        $('.list-page').pagination({
            pageCount: _pageSize,
            current: index,
            jump: true,
            coping: true,
            homePage: '首页',
            endPage: '末页',
            prevContent: '上页',
            nextContent: '下页',
            pageSize: size,
            pageArray: [6, 12, 24, 48],
            totalCount: _total,
            id: 'message-page',
            callback: function (api) {
                index = api.getCurrent();
                message.get();
            }
        });
        if (_pageSize > 0)
            $('.pages').show();
    }
};

var view = {
    initHeight: function () {
        $('.data-view').css('height', (parent.adaptable().h) - 80);
        $('.date-table').css('height', (parent.adaptable().h) - 180);
        size = Math.floor(((parent.adaptable().h) - 180) / 40);
    }
};


function pageChange(event) {
    size = $(event).val();
    index = 1;
    message.get();
};

var helper = {
    stage: function (_stage) {
        switch (parseInt(_stage)) {
            case 0 :
                return "审核";
            case 1 :
                return "初审";
            case 2 :
                return "复审";
            default:
                return "其他";
        }
    },
    approvalType: function (_approvalType) {
        switch (parseInt(_approvalType)) {
            case 1 :
                return "入党申请书";
            case 2 :
                return "思想汇报";
            case 3 :
                return "考察记录";
            case 4 :
                return "谈话记录";
            case 5 :
                return "个人信息";
            case 6 :
                return "课程请假";
            case 7 :
                return "会议请假";
            default:
                return "其他";
        }
    },
    approvalUrl: function (_approvalType) {
        switch (parseInt(_approvalType)) {
            case 1 :
                return "/my/core/application";
            case 2 :
                return "/my/core/report";
            case 3 :
                return "/my/core/surveyRecordChat";
            case 4 :
                return "/my/core/chatRecord";
            case 5 :
                return "/my/core/userManage";
            case 6 :
                return "/my/core/vacate";
            case 7 :
                return "/my/core/meetingVacate";

            default:
                return "#";
        }
    },
};