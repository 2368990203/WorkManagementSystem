var index = 1, size = 6, key = '', totalPage = 0, totalCount = 0;
var baseData = [], pageData = [];
var messageData = [], messageId = '';
var optId = '';

var config = {
    form: '../../form/_userMessage.html',
    title: '',
};

$(function () {

    if (auth.check(this)) {
        //自适应
        view.initHeight();
        $(window).resize(function () {
            view.initHeight();
        });
        message.get();
        optId = url.getUrlParam("id");
        if (optId != "" && optId != null) {
            if (auth.checkFun('message.read'))
                return false;
            message.read(null, optId);
        }
        setInterval(message.get(), 60000);

    }


});

var message = {
    get: function () {
        var param = {url: baseModule.userMessageApi + '/' + index + '-' + size + '-' + key};
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
    read: function (event, id) {

        if (event != null) {
            if (auth.refuse(event))
                return false;
            messageId = getId(event);
        }
        if (event == null) {
            messageId = id;
        }

        var param = {url: baseModule.userMessageApi + '/info/' + messageId};
        var request = ajax.get(param);
        request.done(function (d) {
            optId = messageId;
            var messageData = d.result;
            openLay({url: config.form, fun: "message.delete();", title: config.title, enter: "删除"});
            var template = doT.template($("#message-template").text());
            // $("#opt-dialog-enter").hide();
            $('#message').html(template(messageData));
            if (messageData.readStatus != 1) {
                var param = {url: baseModule.userMessageApi + '/read/' + messageId};
                var request = ajax.put(param);
                request.done(function (d) {
                    message.get();
                })

            }
        })

    },
    delete: function () {
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
                var request = ajax.delete({url: baseModule.messageApi + '/' + optId});
                request.done(function (d) {
                    closeLay();
                    tips.ok(d.message);
                    message.get();

                    var indexId = url.getUrlParam("id");
                    if (indexId != "" && indexId != null) {
                        var request = ajax.get({url: baseModule.indexMessageListApi});

                        request.done(function (d) {
                            var messageData = d.result;
                            var count = parseInt(messageData.count);
                            var messageListData = messageData.messages;
                            if (count > 0) {
                                parent.$('#messageCount').html(count);
                            }
                            var template = doT.template($("#message-index-template").text());//获取的模板
                            parent.$('#message-list').html(template(messageListData));//模板装入数据

                            if (messageListData.length >= 3) {
                                parent.$('#message-list').append(' <li class="dropdown-menu-footer text-center">' +
                                    '<a href="#" onclick="readMore()">查看全部消息</a>' +
                                    ' </li>');
                            }

                        })


                    }

                    /*
                    */


                })
            }
        );
    }
};

var render = {
    page: function () {
        var template = doT.template($("#message-list-template").text());//获取的模板
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
    }, url: function (_type) {
        switch (parseInt(_type)) {
            case 0:
                return "#";
            case 1 :
                return "../../my/core/schedule";
            case 2 :
                return "../../my/core/meetingSchedule";
            case 3 :
                return "../../my/core/exam";
            case 4 :
                return "../../my/core/application";
            case 5 :
                return "../../my/core/report";
            default:
                return "#";
        }
    }
    , detailUrl: function (_type) {
        switch (parseInt(_type)) {
            case 0:
                return "#";
            case 1 :
                return "../../my/core/schedule?id=";
            case 2 :
                return "../../my/core/meetingSchedule?id=";
            case 3 :
                return "../../my/core/exam?id=";
            case 4 :
                return "../../my/core/application?id=";
            case 5 :
                return "../../my/core/report?id=";
            default:
                return "#";
        }
    }


};

