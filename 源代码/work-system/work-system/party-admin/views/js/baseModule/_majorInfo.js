var index = 1, size = 6, name = '', totalPage = 0, totalCount = 0;
var baseData = [], pageData = [], academyData = [], campus = '';
var config = {
    form: '../../form/_majorInfo.html',
    // import:'../../form/_work_course_import.html',
    title: '专业信息管理',
    importTitle: "专业信息导入",
    import: '../../form/_majorImport.html',
    importTitle: '导入专业',
}


$(function () {
    //自适应
    if (auth.check(this)) {
        view.initHeight();
        $(window).resize(function () {
            view.initHeight();
        });
        //majorInfo.getAccedemy();
        majorInfo.get();
    }
})


var majorInfo = {


    get: function () {
        var param = {url: baseModule.majorInfoApi + '/' + index + '-' + size + '-' + name};
        var request = ajax.get(param);
        request.done(function (d) {
            pageData = d.result.data;
            //console.log(pageData);
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
    getAcademy: function () {
        var param = {url: baseModule.academyInfoApi, async: false,loading:false};
        var request = ajax.get(param);
        request.done(function (d) {
            academyData = d.result;
            // console.log(academyData)
            render.academySelect();


        })

    },


    create: function (event) {
        if (auth.refuse(event))
            return false;
        openLay({url: config.form, fun: 'opt.create();', title: config.title});

        // optId = getId(event);
        // var model = result.get(pageData, optId);
        // $("#campus").html(helper.campus(model['campus']));//调用helper方法传值

        this.getAcademy();

    },
    import: function (event) {
        if (auth.refuse(event))
            return false;
        openLay({url: config.import, fun: 'opt.import();', title: config.importTitle, enter: "导入"});

    },
    exportError: function () {
        $("#error-table").tableExport({type: 'excel', fileName: 'exportError'});
    },
    importModel: function (event) {
        if (auth.refuse(event))
            return false;
        downFile('../../model/majorImport.xls');

    },
    delete: function (event) {
        if (auth.refuse(event))
            return false;
        optId = getId(event);
        tips.confirm({message: '是否要删除专业数据？', fun: "opt.delete();"});
    },
    update: function (event) {
        if (auth.refuse(event))
            return false;
        optId = getId(event);
        openLay({url: config.form, fun: "opt.update();", title: config.title});
        this.getAcademy();
        var model = result.get(pageData, optId);

        //$('#academyCode').val(model.academyCode);
        // if (model['campus'] == 1) {
        //     model['campus'] = "东校区";
        // } else if (model['campus'] == 2) {
        //     model['campus'] = "西校区";
        // } else if (model['campus'] == 3) {
        //     model['campus'] = "武鸣校区";
        // }
        // console.log(model)
        form.set(model);
    },

    //查询
    select: function (event) {
        name = $.trim($('#name').val());
        index = 1;
        majorInfo.get();
    },

    getCampus: function () {
        //var index = $('#academyCode').selectedIndex();
        var academyCode = $("#academyCode option:selected").val();
        var param = {url: baseModule.academyInfoApi + '/campus/' + academyCode,loading:false};
        var reqest = ajax.get(param);
        reqest.done(function (d) {
            academyData = d.result;
            if (academyData[0].campus == 1) {
                $('#campus').val("东校区");
            } else if (academyData[0].campus == 2) {
                $('#campus').val("西校区");
            } else if (academyData[0].campus == 3) {
                $('#campus').val("武鸣校区");
            }
        })
    },
}


// 数据渲染
var render = {
    create: function () {

        //收集数据
        var data = form.get("#opt-form");
        if (form.verify(data))
            return false;
        log.d(data)
    },
    page: function () {
        var template = doT.template($("#major-template").text());
        $('#item-list').html(template(pageData));
    },


    academySelect: function () {
        var template = doT.template($("#academy-select-template").text());
        $('#academyCode').html(template(academyData));
    }

}

// 数据操作
var opt = {
    create: function () {
        var data = form.get("#opt-form");
        if (form.verify(data))
            return false;
        data['id'] = optId;
        if (data['campus'] == "东校区") {
            data['campus'] = 1;
        } else if (data['campus'] == "西校区") {
            data['campus'] = 2;
        } else if (data['campus'] == "武鸣校区") {
            data['campus'] = 3;
        }

        var param = {url: baseModule.majorInfoApi, data: data};
        var request = ajax.post(param);
        request.done(function (d) {
            tips.done(d);
            majorInfo.get();
            closeLay();


        })
    },
    import: function () {

        if (!xls_check("file")) {
            return false;
        }
        //console.log(data);debugger;
        $('#opt-dialog-cancel').attr("disabled", true);
        $('#file').attr("disabled", true);
        $('#opt-dialog-enter').attr("disabled", true);
        $('#todoBar').empty();
        $('#todoPer').empty();
        $('#mark').empty();
        $('#res').empty();


        var batch = getToken();

        initImportStatus();

        checkImportStatus(baseModule.majorInfoApi + "/importStatus/" + batch);

        importFile("file", baseModule.majorInfoApi + '/import/' + batch,
            function importComplete(evt) {
                $('#opt-dialog-cancel').attr("disabled", false);
                $('#file').attr("disabled", false);
                $('#opt-dialog-enter').attr("disabled", false);
                importCompleteShow(evt);
                majorInfo.get();
            });


    },
    delete: function () {
        var request = ajax.delete({url: baseModule.majorInfoApi + '/' + optId});
        request.done(function (d) {
            tips.ok(d.message);
            majorInfo.get();
            closeLay();


        })
    },
    update: function () {
        var data = form.get("#opt-form");
        if (form.verify(data))
            return false;
        data['id'] = optId;
        if (data['campus'] == "东校区") {
            data['campus'] = 1;
        } else if (data['campus'] == "西校区") {
            data['campus'] = 2;
        } else if (data['campus'] == "武鸣校区") {
            data['campus'] = 3;
        }
        var param = {url: baseModule.majorInfoApi, data: data};
        var request = ajax.put(param);
        request.done(function (d) {
            tips.ok(d.message);

            majorInfo.get();
            closeLay();

        })

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
            id: 'major-page',
            callback: function (api) {
                index = api.getCurrent();
                majorInfo.get();
            }
        });
        if (_pageSize > 0)
            $('.pages').show();
    }
}

function pageChange(event) {
    size = $(event).val();
    index = 1;
    majorInfo.get();
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

    campus: function (_campus) {
        switch (parseInt(_campus)) {
            case 1 :
                return "东校区";
            case 2:
                return "西校区";
            case 3:
                return "武鸣校区"
        }
    },

    status: function (_status) {
        switch (parseInt(_status)) {
            case 0 :
                return "有效";
            case 1 :
                return "无效";
        }
    }
}
