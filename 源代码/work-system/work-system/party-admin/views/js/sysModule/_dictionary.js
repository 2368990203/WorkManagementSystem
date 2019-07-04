var index = 1, size = 10, key = '', totalPage = 0, totalCount = 0;
var baseData = [], pageData = [];
var config = {
    form: '../../form/_dictionary.html',
    title: '数据字典',
}

$(function () {
    if (auth.check(this)) {
        //1自适应（默认加载方法）
        view.initHeight();
        $(window).resize(function () {
            view.initHeight();
        });
        dictionary.get();
    }
})
//2
var dictionary = {
    get: function () {
        var param = {url: globalModule.dictionaryApi + '/' + index + '-' + size + '-' + key};
        var request = ajax.get(param);
        request.done(function (d) {   //
            pageData = d.result.data;//测试数据
            render.page();
            totalPage = d.result.totalPage;//总页数
            totalCount = d.result.totalCount;//总记录数
            if (d.result.totalPage > 1) {
                page.init(d.result.totalPage, d.result.totalCount);
            } else {
                $('.list-page').empty();
            }
            auth.show();
        })
    },

    create: function (event) {
        if (auth.refuse(event))
            return false;
        openLay({url: config.form, fun: 'opt.create();', title: config.title});
    },
    delete: function (event) {
        if (auth.refuse(event))
            return false;
        optId = getId(event);
        // console.log("id:" + optId);
        tips.confirm({message: '是否要删除数据字典数据？', fun: "opt.delete();"});
    },
    update: function (event) {
        if (auth.refuse(event))
            return false;
        optId = getId(event);//获取当前id的值
        openLay({url: config.form, fun: "opt.update();", title: config.title});//打开的窗口
        var model = result.get(pageData, optId);
        form.set(model);
    }
}

// 3数据渲染
var render = {
    // create:function () {
    //     var data = form.get("#opt-form");
    //     if(form.verify(data))
    //         return false;
    //     log.d(data)
    // },
    page: function () {
        var template = doT.template($("#dictionary-template").text());//获取的模板
        $('#item-list').html(template(pageData));//模板装入数据
    },
}

// 4数据操作
var opt = {
    create: function () {
        var data = form.get("#opt-form");
        if (form.verify(data))
            return false;

        var param = {url: globalModule.dictionaryApi, data: data};
        var request = ajax.post(param);
        request.done(function (d) {
            tips.done(d);
            pageData.push(d.result);
            totalCount = totalCount + 1;
            page.init(totalPage, totalCount);
            render.page();
        })
    },
    delete: function () {
        var request = ajax.delete({url: globalModule.dictionaryApi + '/' + optId});
        request.done(function (d) {
            tips.ok(d.message);
            pageData = result.delete(pageData, optId);
            render.page();
            totalCount = totalCount - 1;
            page.init(totalPage, totalCount);
        })
    },
    update: function () {
        var data = form.get("#opt-form");
        if (form.verify(data))
            return false;
        data['id'] = optId;
        var param = {url: globalModule.dictionaryApi, data: data};
        var request = ajax.put(param); //加一条记录
        request.done(function (d) {
            tips.ok(d.message);
            //更新对象
            pageData = result.update(pageData, d.result, 'id');
            closeLay();
            render.page(); //更新数据后重新渲染
        })
    },
    close: function () {   //关闭按钮
        closeLay();
    }
}

// 5分页
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
            pageArray: [10, 20, 30, 40],
            totalCount: _total,
            id: 'course-page',
            callback: function (api) {
                index = api.getCurrent();
                dictionary.get();
            }
        });
        if (_pageSize > 0)
            $('.pages').show();
    }
}


// 6视图界面
var view = {
    initHeight: function () {
        $('.data-view').css('height', (parent.adaptable().h) - 80);
        $('.date-table').css('height', (parent.adaptable().h) - 180);
        size = Math.floor(((parent.adaptable().h) - 180) / 40);
    }
}

function pageChange(event) {
    size = $(event).val();
    index = 1;
    dictionary.get();
}

var helper = {
    status: function (_status) {
        switch (parseInt(_status)) {
            case 1 :
                return "启用";
            case 2 :
                return "禁用";
        }
    }
}