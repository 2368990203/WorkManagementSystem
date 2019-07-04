var index = 1, size = 6, key = '', totalPage = 0, totalCount = 0;
var baseData = [], pageData = [];
var optId = '', academyCode = '', majorCode = '', classId = '';

var config = {
    form: '/form/_student.html',
    title: '学生信息管理',
};

$(function () {
    //自适应
    view.initHeight();
    $(window).resize(function () {
        view.initHeight();
    });
    student.get();
});

var student = {
    get: function () {
        var param = {url: baseModule.studentApi + '/' + index + '-' + size + '-' + key};
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
        })
    },

    getAcademy: function () {
        var param = {url: baseModule.academyInfoApi, async: false};
        var reqest = ajax.get(param);
        reqest.done(function (d) {
            academyData = d.result;
            render.academySelect();
        })
    },

    getMajor: function () {
        academyCode = $("#academyCode option:selected").val();
        if(academyCode.trim() ==""){
            render.majorSelect([]);
            return false;
        }

        var param = {url: baseModule.majorInfoApi + "/majors/" + academyCode, async: false, loading: false};
        var reqest = ajax.get(param);
        reqest.done(function (d) {
            var majorData = d.result;
            render.majorSelect(majorData);
        })
    },
    getClass: function () {
        majorCode = $("#majorCode option:selected").val();
        if(majorCode.trim() == ""){
            render.classSelect([]);
            return false;
        }
        var param = {url: baseModule.classesApi + "/byMajorCode/" + majorCode, async: false, loading: false};
        var reqest = ajax.get(param);
        reqest.done(function (d) {
            var classData = d.result;
            render.classSelect(classData);
        })
    },
    create: function (event) {
        if (auth.refuse(event))
            return false;
        openLay({url: config.form, fun: 'opt.create();', title: config.title});
        this.getAcademy();
    },

    delete: function (event) {
        if (auth.refuse(event))
            return false;
        optId = getId(event);
        tips.confirm({message: '是否要删除学生信息数据？', fun: "opt.delete();"});

    },

    update: function (event) {
        if (auth.refuse(event))
            return false;
        optId = getId(event);//获取当前id的值
        openLay({url: config.form, fun: "opt.update();", title: config.title});
        var model = result.get(pageData, optId);
        this.getAcademy();
        this.getMajor();
        this.getClass();
        $('#number').attr('readonly', 'readonly');
        form.set(model);
    },
};

var render = {
    page: function () {
        var template = doT.template($("#student-template").text());//获取的模板
        $('#item-list').html(template(pageData));//模板装入数据
    },

    academySelect: function () {
        var template = doT.template($("#academy-select-template").text());
        $('#academyCode').html(template(academyData));
    },

    majorSelect: function (majorData) {
        var template = doT.template($("#major-select-template").text());
        $('#majorCode').html(template(majorData));
    },
    classSelect: function (classData) {
        var template = doT.template($("#class-select-template").text());
        $('#classId').html(template(classData));
    },
};

var opt = {
    create: function () {
        var data = form.get("#opt-form");

        if (form.verify(data))
            return false;

        var param = {url: baseModule.studentApi, data: data};
        var request = ajax.post(param);
        request.done(function (d) {
            tips.done(d);
            student.get();
        })
    },
    delete: function () {
        var request = ajax.delete({url: baseModule.studentApi + '/' + optId});
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

        var param = {url: baseModule.studentApi, data: data};
        var request = ajax.put(param); //加一条记录
        request.done(function (d) {
            tips.ok(d.message);
            //更新对象
            pageData = result.update(pageData, d.result, 'id');
            student.get();
            closeLay();
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
            id: 'student-page',
            callback: function (api) {
                index = api.getCurrent();
                student.get();
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
    student.get();
};

var helper = {
    sex: function (_sex) {
        switch (parseInt(_sex)) {
            case 1:
                return "女";
            case 2:
                return "男";
        }
    },
    status: function (_status) {
        switch (parseInt(_status)) {
            case 1:
                return "正常";
            case 2:
                return "冻结";
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
