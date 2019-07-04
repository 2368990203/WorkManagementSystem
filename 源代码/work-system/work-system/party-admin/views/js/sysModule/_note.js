var index = 1, size = 6, key = '', totalPage = 0, totalCount = 0;
var baseData = [], pageData = [];
var messageData = [], messageId = '';
var optId = '';

var config = {
    form: '../../form/_note.html',
    form_read: '../../form/_note_read.html',

    title: '短信详细信息',
};

$(function () {
    if (auth.check(this)) {
        //自适应
        view.initHeight();
        $(window).resize(function () {
            view.initHeight();
        });
        message.get();
    }
});

var message = {
    get: function () {
        var param = {url: baseModule.noteApi + '/' + index + '-' + size + '-' + key};
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
    read: function (event) {
        if (auth.refuse(event))
            return false;
        messageId = getId(event);
        openLay({url: config.form_read, fun: "", title: config.title, enter: "确认"});
        $("#opt-dialog-enter").hide();
        var model = result.get(pageData, messageId);

        model['sendTime'] = time.timestamp2shortdate(model['sendTime']);//时间
        $("#sendTime").html(model['sendTime']);
        form.set(model);

        $("#detail").html(model['detail']);

        $("#sendStatus").html(helper.sendStatus(model['sendStatus']));//调用helper
        $("#type").html(helper.type(model['type']));//调用helper

        //   message.get();    //刷新修改后的数据
    },
    //查询
    select: function (event) {
        key = $.trim($('#select-key').val());
        index = 1;
        message.get();
    },
    send: function (event) {
        if (auth.refuse(event))
            return false;
        openLay({url: config.form, fun: 'opt.send();', title: "短信推送管理"});
        createTree(toolModule.getAcademyCatalogueApi, "#rangeTree", true, true); //创建

    },
};

var render = {
    page: function () {
        var template = doT.template($("#message-template").text());//获取的模板
        $('#item-list').html(template(pageData));//模板装入数据
    },
};

var opt = {
    send: function () {
        var data = form.get("#opt-form");
        if (form.verify(data))
            return false;
        var range = getCheckedNodesArr("rangeTree");
        if (range == "" || range == null) {
            return false;
        }
        data['range'] = range;
        var param = {url: baseModule.noteApi, data: data};
        var request = ajax.post(param);
        request.done(function (d) {
            tips.done(d);
            var taskid = d.result;
            var param = {url: baseModule.noteApi + '/send/' + taskid};
            var request = ajax.post(param);
            request.done(function (d) {
                tips.done(d);
                message.get();

            })

        })
    },
    close: function () {   //关闭按钮
        closeLay();
    }
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
    sendStatus: function (_sendStatus) {
        switch (parseInt(_sendStatus)) {
            case 1:
                return "未发送";
            case 2:
                return "已发送";
            default:
                return "已发送";

        }
    },
    type: function (_type) {
        switch (parseInt(_type)) {
            case 0:
                return "通知";
            case 1:
                return "验证码";
            case 2:
                return "学习安排修改";
            case 3:
                return "学习安排取消";
            case 4:
                return "批假结果";
            default:
                return "通知";
        }
    },
};

var tool = {
    translate: function (model) {
        var data = [];
        for (var variable in model) {
            data[variable] = model[variable];
            //判断helper里是否存在该函数，存在则执行转换
            if (typeof eval('helper.' + variable) == 'function')
                model[variable] = eval('helper.' + variable + '(' + model[variable] + ')');
        }
        form.set(model);
        //恢复回转换前数据
        for (var variable in data) {
            model[variable] = data[variable];
        }
    }
};
