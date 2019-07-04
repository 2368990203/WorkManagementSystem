var index = 1, size = 6, key = '', totalPage = 0, totalCount = 0;
var baseData = [], pageData = [],courseData = [];
var optId = '';

var config = {
    form: '/form/_teachSchedule.html',
    title: '教师授课管理',
};

$(function () {
    //自适应
    view.initHeight();
    $(window).resize(function () {
        view.initHeight();
    });
    teachSchedule.get();
});

var teachSchedule = {
    get: function () {
        var param = {url: baseModule.teachScheduleApi + '/' + index + '-' + size + '-' + key,sync:false};
        var request = ajax.get(param);
        request.done(function (d) {
            pageData = d.result.data;
            render.page();
            totalPage = d.result.totalPage;
            totalCount = d.result.totalCount;
            if (d.result.totalPage>1) {
                  page.init(d.result.totalPage, d.result.totalCount);
            }else{
                 $('.list-page').empty();
            }
            auth.show();
        });
    },

    getCourse: function () {
        var courseNumber = $("#courseNumber option:selected").val();
        var param = {url: baseModule.courseApi, async: false};
        var reqest = ajax.get(param);
        reqest.done(function (d) {
            courseData = d.result;
            render.courseSelect();
        })
    },

    create: function (event) {
        if (auth.refuse(event))
            return false;
        openLay({url: config.form, fun: 'opt.create();', title: config.title});
        this.getCourse();
        createTree(toolModule.getAcademyCatalogueApi+"/"+4, "#classId", true, false);//4-到班级
        createTree(toolModule.getAcademyCatalogueApi+"/"+3, "#teacherNumber", true, false);//

    },

    delete: function (event) {
        if (auth.refuse(event))
            return false;
        optId = getId(event);
        tips.confirm({message: '是否要删除教师授课数据？', fun: "opt.delete();"});

    },

    update: function (event) {
        if (auth.refuse(event))
            return false;
        optId = getId(event);//获取当前id的值
        openLay({url: config.form, fun: "opt.update();", title: config.title});
        var model = result.get(pageData, optId);
        console.log(model);
        this.getCourse();

        var chkClass = [{"id":model["classId"]}];
        var chkTeacher = [{"id":model["teacherNumber"]}];

        createTree(toolModule.getAcademyCatalogueApi+"/"+4, "#classId", true, false,JSON.stringify(chkClass));//4-到班级
        createTree(toolModule.getAcademyCatalogueApi+"/"+3, "#teacherNumber", true, false,JSON.stringify(chkTeacher));//3-到教师
        form.set(model);


    },
};

var render = {
    page: function () {
        var template = doT.template($("#teachSchedule-template").text());//获取的模板
        $('#item-list').html(template(pageData));//模板装入数据
    },
    courseSelect: function () {
        var template = doT.template($("#course-select-template").text());
        $('#courseNumber').html(template(courseData));
    },
};

var opt = {
    create: function () {
        var data = form.get("#opt-form");

        if (form.verify(data))
            return false;

        var idArr = getCheckedNodesArr("classId",'id',"class");
        if(idArr == null||idArr.length==0){
            tips.warning("请正确选择授课班级");
            return false;
        }
        data['classId'] = idArr[0]["id"];

        var tnArr = getCheckedNodesArr("teacherNumber",'teacherNumber',"teacher");
        if(tnArr == null || tnArr.length==0){
            tips.warning("请正确选择授课教师");
            return false;
        }
        data['teacherNumber'] = tnArr[0]['teacherNumber'];

        var param = {url: baseModule.teachScheduleApi, data: data};
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
        var request = ajax.delete({url: baseModule.teachScheduleApi + '/' + optId});
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


        var idArr = getCheckedNodesArr("classId",'id',"class");
        if(idArr == null||idArr.length==0){
            tips.warning("请正确选择授课班级");
            return false;
        }
        data['classId'] = idArr[0]["id"];

        var tnArr = getCheckedNodesArr("teacherNumber",'teacherNumber',"teacher");
        if(tnArr == null || tnArr.length==0){
            tips.warning("请正确选择授课教师");
            return false;
        }
        data['teacherNumber'] = tnArr[0]['teacherNumber'];

        var param = {url: baseModule.teachScheduleApi, data: data};
        var request = ajax.put(param); //加一条记录
        request.done(function (d) {
            tips.ok(d.message);
            //更新对象
            pageData = result.update(pageData, d.result, 'id');
            teachSchedule.get();
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
            id: 'teachSchedule-page',
            callback: function (api) {
                index = api.getCurrent();
                teachSchedule.get();
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
    teachSchedule.get();
};

var helper = {

};

var tool = {
    translate:function (model) {
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
