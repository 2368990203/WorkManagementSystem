var index = 1, size = 6, title = '', author = '', totalPage = 0, totalCount = 0;
var imageType = '';
var newFileName = '';
var baseData = [], pageData = [], urlData = [];
var noticeData = [], noticeId = '', pictureId = '';
var optId = '';

var config = {
    form: '../../form/_notice_manage.html',
    release: '../../form/_notice_manage.html',
    form_read: '../../form/_notice_read.html',
    title: '通知公告管理',

}

$(function () {
    if (auth.check(this)) {
        //自适应
        view.initHeight();
        $(window).resize(function () {
            view.initHeight();
        });
        notice.get();

    }
})

var notice = {
    get: function () {
        var param = {url: baseModule.noticeManageApi + '/' + index + '-' + size + '-' + title + '-' + author};
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
    getURL: function () {
        var param = {url: baseModule.getPictureURLApi + '/' + id};
        var request = ajax.get(param);
        request.done(function (d) {
            urlData = d.result.artworkURL;
            render.page();   //图片界面渲染
        })
    },

    create: function (event) {
        if (auth.refuse(event))
            return false;
        openLay({url: config.form, fun: 'opt.create();', title: config.title, enter: "保存"});
    },
    delete: function (event) {
        if (auth.refuse(event))
            return false;
        optId = getId(event);
        tips.confirm({message: '是否要删除通知公告？', fun: "opt.delete();"});
    },
    read: function (event) {
        if (auth.refuse(event))
            return false;
        noticeId = getId(event);
        openLay({url: config.form_read, fun: "", title: '通知公告详细信息'});
        $("#opt-dialog-enter").hide();
        var model = result.get(pageData, noticeId);

        $("#content").html(model['content']);
        $("#isReview").html(helper.isReview(model['isReview']));//调用helper方法传值
        $("#isEssence").html(helper.isEssence(model['isEssence']));//调用helper方法传值
        $("#isCheck").html(helper.isCheck(model['isCheck']));//调用helper方法传值
        $("#result").html(helper.result(model['result']));//调用helper方法传值
        $("#status").html(helper.status(model['status']));//调用helper方法传值
        $("#isPublish").html(helper.isPublish(model['isPublish']));//调用helper方法传值


        form.set(model);
        notice.get();    //刷新修改后的数据
    },
    update: function (event) {
        if (auth.refuse(event))
            return false;
        optId = getId(event);
        openLay({url: config.form, fun: "opt.update();", title: config.title, enter: "操作"});
        var model = result.get(pageData, optId);
        //向富文本控件插入初始值，第一个参数对应html页面的id值，第二个参数是初始值
        editor.set('#content', model['content']);
        form.set(model);
        $("#pictureId").val(model.pictureId);
        //   console.log(model.pictureId)
    },
    //查询
    select: function (event) {
        title = $.trim($('#title').val());
        index = 1;
        notice.get();
    },
    release: function (event) {
        if (auth.refuse(event))
            return false;
        optId = getId(event);
        // openLay({url: config.form, fun: "opt.release();", title: config.title});
        tips.confirm({message: '是否要发布该公告？', fun: "opt.release();", color: "#23b7e5", enter: "发布"});

        /* if(auth.refuse(event))
             return false;
         optId = getId(event);//获取当前id的值
         openLay({url:config.release,fun:"opt.release();",title:config.title,enter:"发布"});
         var model = result.get(pageData,optId);
         form.set(model);

         editor.set('#content',model['content']);
       */
    },

}

// 数据渲染
var render = {
    create: function () {
        var data = form.get("#opt-form");
        if (form.verify(data))
            return false;
        log.d(data)
    },
    page: function () {
        // //console.log(pageData);
        var notice = doT.template($("#course-template").text());
        $('#item-list').html(notice(pageData));
    },

}

// 数据操作
var opt = {
    create: function () {
        var data = form.get("#opt-form");
        if (form.verify(data))
            return false;

        //console.log(data);
        tips.info("正在校验数据中，请稍候！");
        //校验内容是否存在外部图片，如有则自动替换
        data['content'] = replaceOutUrl(data['content']);
        data['releaseTime'] = time.date2timestamp(data['releaseTime']);

        //以下为将数据提交到接口的操作
        var param = {url: baseModule.noticeManageApi, data: data};
        var request = ajax.post(param);
        request.done(function (d) {
            tips.done(d);
            notice.get();


        })
    },
    delete: function () {
        var request = ajax.delete({url: baseModule.noticeManageApi + '/' + optId});
        request.done(function (d) {
            tips.ok(d.message);
            notice.get();

        })
    },
    update: function () {
        var data = form.get("#opt-form");
        if (form.verify(data))
            return false;
        data['id'] = optId;
        tips.info("正在校验数据中，请稍候！");
        //校验内容是否存在外部图片，如有则自动替换
        data['content'] = replaceOutUrl(data['content']);
        data['releaseTime'] = time.date2timestamp(data['releaseTime']);


        //以下为将数据提交到接口的操作
        var param = {url: baseModule.noticeManageApi, data: data};
        var request = ajax.put(param);
        request.done(function (d) {
            tips.ok(d.message);
            notice.get();


            closeLay();
        })
    },
    release: function () {
        var param = {url: baseModule.noticeManageApi + "/publish/" + optId};
        var request = ajax.put(param);
        request.done(function (d) {
            tips.ok(d.message);
            notice.get();     //刷新修改后的数据
            closeLay();
        })

    },
    upload: function (file) {
        var _file = document.getElementById(file).files[0];
        var type = _file.type;
        var accept = ["image/gif", "image/jpeg", "image/jpg", "image/png", "image/svg"];
        var flag = false;
        for (var i = 0; i < accept.length; i++) {
            if (accept[i] == type) {
                flag = true;
            }
        }
        if (!flag) {
            tips.error("请上传图片文件！");
            return false;
        }
        newFileName = _file.name;
        newFileName = getSuffix(newFileName) + new Date().getTime() + "." + getSuffix(newFileName);//修改文件名

        var path = 'partySystem/picture/' + newFileName;

        imageType = file;
        cos.uploadFile(path, _file, uploadImageSuccess);
    },

    close: function () {
        closeLay();
    }
}

// 分页
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
            id: 'template-page',
            callback: function (api) {
                index = api.getCurrent();
                notice.get();
            }
        });
        if (_pageSize > 0)
            $('.pages').show();
    }
}

function pageChange(event) {
    size = $(event).val();
    index = 1;
    notice.get();
}

// 视图界面
var view = {
    initHeight: function () {
        $('.data-view').css('height', (parent.adaptable().h) - 80);
        $('.date-table').css('height', (parent.adaptable().h) - 180);
        size = Math.floor(((parent.adaptable().h) - 180) / 40);
    }
}

var helper = {
    isReview: function (_isReview) {
        switch (parseInt(_isReview)) {
            case 1 :
                return "允许";
            case 2 :
                return "不允许";

        }
    },

    isEssence: function (_isEssence) {
        switch (parseInt(_isEssence)) {
            case 1 :
                return "是";
            case 2 :
                return "否";

        }
    },
    isCheck: function (_isCheck) {
        switch (parseInt(_isCheck)) {
            case 1 :
                return "审核";
            case 2 :
                return "不审核";

        }
    },
    result: function (_result) {
        switch (parseInt(_result)) {
            case 1 :
                return "通过";
            case 2 :
                return "不通过";

        }
    },
    status: function (_status) {
        switch (parseInt(_status)) {
            case 1 :
                return "未审";
            case 2 :
                return "已审";

        }
    },
    isPublish: function (_isPublish) {
        switch (parseInt(_isPublish)) {
            case 1 :
                return "未发布";
            case 2 :
                return "已发布";

        }
    }
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
var uploadImageSuccess = function (result) {
    loading.hide();
    var status = result.status;
    var data = result.data;
    var access_url = data.source_url.replace("http:", "").replace("https:", "");
    var path = data.resource_path;

    //$('#'+imageType+'Show').attr('src', access_url);
    //$('#'+imageType+'Show').attr('data-path', cos.fileName(access_url));
    $('#artworkURL').attr('src', access_url);
    $('#artworkURL').attr('data-path', cos.fileName(access_url));

    var pictureData = {};
    pictureData['path'] = path;
    pictureData['name'] = newFileName;
    pictureData['artworkURL'] = access_url;
    pictureData['describe'] = "公告图片";
    // pictureData['tableId'] = "";
    var param = {url: toolModule.pictureApi, data: pictureData};
    var request = ajax.post(param);
    request.done(function (d) {
        $("#pictureId").val(d.result.id);
    })
};
