var index = 1, size = 12, totalPage = 0, totalCount = 0;
var baseData = [], pageData = [], optionData = [];
var optId = '';
var name = '', type = '';

var config = {
    form: '../../form/_question.html',
    title: '题库管理',
    import: '../../form/_questionImport.html',
    importTitle: '导入题库',
};

$(function () {
    if (auth.check(this)) {
        //自适应
        view.initHeight();
        $(window).resize(function () {
            view.initHeight();
        });
        question.get();


    }
});

var question = {
    get: function () {
        var param = {url: baseModule.questionApi + '/details/' + index + '-' + size + '-' + type + '-' + name};
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
    select: function (event) {
        name = $.trim($('#select-name').val()).replace('-', '');
        type = $.trim($('#select-type').val()).replace('-', '');
        index = 1;
        question.get();
    },

    make: function (event) {
        if (auth.refuse(event))
            return false;
        openLay({url: config.form, fun: 'opt.make();', title: config.title});
        render.trueOrFalse();
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
        downFile('../../model/questionImport.xls');

    },

    deleteQuestionAndOptions: function (event) {
        // if (auth.refuse(event))
        //     return false;
        optId = getId(event);
        tips.confirm({message: '是否要删除题目和选项？', fun: "opt.deleteQuestionAndOptions();"});
    },

    update: function (event) {
        if (auth.refuse(event))
            return false;
        optId = getId(event);//获取当前id的值
        openLay({url: config.form, fun: "opt.update();", title: config.title});
        var model = result.get(pageData, optId);

        editor.set('#answerKeys', model['answerKeys']);

        //渲染选项
        var type = model['type'];

        optionData = [];
        optionData = model['options'];

        switch (parseInt(type)) {
            case 1:
                render.trueOrFalse(optionData);
                break;
            case 2 :
                render.singleChoice(optionData);
                break;
            case 3 :
                render.multipleChoice(optionData);
                break;
            case 4 :
                render.gapFilling(optionData);
                break;
            case 5 :
                render.subjective();
                break;
        }

        form.set(model);
    },

    changeType: function (index) {
        switch (parseInt(index)) {
            case 1:
                render.trueOrFalse();
                break;
            case 2 :
                render.singleChoice();
                break;
            case 3 :
                render.multipleChoice();
                break;
            case 4 :
                render.gapFilling();
                break;
            case 5:
                render.subjective();//客观题
        }

    },
};

var render = {
    page: function () {
        var template = doT.template($("#question-template").text());//获取的模板
        $('#item-list').html(template(pageData));//模板装入数据
    },

    trueOrFalse: function () {
        var template = doT.template($("#true-or-false").text());
        $('#options').html(template());
    },
    trueOrFalse: function (optionData) {
        var template = doT.template($("#true-or-false").text());
        $('#options').html(template(optionData));
    },

    singleChoice: function () {
        var template = doT.template($("#single-choice").text());
        $('#options').html(template());
    },
    singleChoice: function (optionData) {
        var template = doT.template($("#single-choice").text());
        $('#options').html(template(optionData));
    },

    multipleChoice: function () {
        var template = doT.template($("#multiple-choice").text());
        $('#options').html(template());
    },
    multipleChoice: function (optionData) {
        var template = doT.template($("#multiple-choice").text());
        $('#options').html(template(optionData));
    },

    gapFilling: function () {
        var template = doT.template($("#gap-filling").text());
        $('#options').html(template());
    },
    gapFilling: function (optionData) {
        var template = doT.template($("#gap-filling").text());
        $('#options').html(template(optionData));
    },
    //客观题
    subjective: function () {
        $('#options').html("");
    },
};

var opt = {
    // create: function () {
    //     var data = form.get("#opt-form");
    //     if (form.verify(data))
    //         return false;
    //
    //     var param = {url: baseModule.questionApi, data: data};
    //     var request = ajax.post(param);
    //     request.done(function (d) {
    //         tips.done(d);
    //         pageData.push(d.result);
    //         totalCount = totalCount + 1;
    //         page.init(totalPage, totalCount);
    //         render.page();
    //     })
    // },


    make: function () {
        $.fn.serializeObject = function () {
            var o = {};
            var a = this.serializeArray();
            $.each(a, function () {
                if (o[this.name]) {
                    if (!o[this.name].push) {
                        o[this.name] = [o[this.name]];
                    }
                    o[this.name].push(this.value || '');
                } else {
                    // console.log(this.name + " | " + this.value);
                    //optionInfo、optionNumber封装为数组
                    if (this.name == 'optionInfo' || this.name == 'optionNumber' || this.name == 'answerNumber') {
                        o[this.name] = [this.value] || '';//this.value改为[this.value]
                    } else {
                        o[this.name] = this.value || '';
                    }
                }
            });
            return o;
        };


        var json_data = [];
        var forms = $("#opt-form");
        //form的序列化
        for (var i = 0; i < forms.length; i++) {
            var opt = $(forms[i]).serializeObject();
            if (opt['type'] != 1 &&opt['type'] != 5) {
                for (var j = 0; j < opt['optionInfo'].length; j++) {
                    if (opt["optionInfo"][j] == "") {
                        tips.warning("选项不能留空");
                        return false;
                    }
                }
            }
            json_data.push(opt);
        }
        data = json_data[0];


        if (data['questionName'] == "") {
            tips.warning("请输入题目");
            return false;
        }
        if (data['type'] != 4 && data['type'] != 5 && typeof data['answerNumber'] == "undefined") {
            tips.warning("请选择正确答案");
            return false;
        }
        tips.info("正在校验数据中，请稍候！");
        //校验内容是否存在外部图片，如有则自动替换
        data['answerKeys'] = replaceOutUrl(data['answerKeys']);


        var param = {url: baseModule.questionApi + '/make', data: data};
        var request = ajax.post(param);
        request.done(function (d) {
            // tips.done(d);
            tips.doneQuestion(d);
            pageData.push(d.result);
            totalCount = totalCount + 1;
            page.init(totalPage, totalCount);
            question.get();
            render.page();
            editor.set('#answerKeys', "");

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


        checkImportStatus(baseModule.questionApi + "/importStatus/" + batch);

        importFile("file", baseModule.questionApi + '/import/' + batch,
            function importComplete(evt) {
                $('#opt-dialog-cancel').attr("disabled", false);
                $('#file').attr("disabled", false);
                $('#opt-dialog-enter').attr("disabled", false);
                importCompleteShow(evt);

                question.get();
            });


    },
    // delete: function () {
    //     var request = ajax.delete({url: baseModule.questionApi + '/' + optId});
    //     request.done(function (d) {
    //         tips.ok(d.message);
    //         pageData = result.delete(pageData, optId);
    //         render.page();
    //         totalCount = totalCount - 1;
    //         page.init(totalPage, totalCount);
    //     })
    // },

    deleteQuestionAndOptions: function () {

        var request = ajax.delete({url: baseModule.questionApi + '/delete/' + optId});
        request.done(function (d) {
            tips.ok(d.message);
            pageData = result.delete(pageData, optId);
            render.page();
            totalCount = totalCount - 1;
            page.init(totalPage, totalCount);
        })
    },

    update: function () {
        // var data = form.get("#opt-form");
        // if (form.verify(data))
        //     return false;
        // data['id'] = optId;
        $.fn.serializeObject = function () {
            var o = {};
            var a = this.serializeArray();
            $.each(a, function () {
                if (o[this.name]) {
                    if (!o[this.name].push) {
                        o[this.name] = [o[this.name]];
                    }
                    o[this.name].push(this.value || '');
                } else {
                    //optionInfo、optionNumber封装为数组
                    if (this.name == 'optionInfo' || this.name == 'optionNumber' || this.name == 'answerNumber') {
                        o[this.name] = [this.value] || '';//this.value改为[this.value]
                    } else {
                        o[this.name] = this.value || '';
                    }
                }
            });
            return o;
        };

        var json_data = [];
        var forms = $("#opt-form");
        //form的序列化
        for (var i = 0; i < forms.length; i++) {
            json_data.push($(forms[i]).serializeObject());
        }

        data = json_data[0];
        data['questionId'] = optId;

        if (data['questionName'] == "") {
            tips.warning("请输入题目");
            return false;
        }
        if (data['type'] != 4 && data['type'] != 5 && typeof data['answerNumber'] == "undefined") {
            tips.warning("请选择正确答案");
            return false;
        }
        tips.info("正在校验数据中，请稍候！");
        //校验内容是否存在外部图片，如有则自动替换
        data['answerKeys'] = replaceOutUrl(data['answerKeys']);

        // console.log(data);
        // return false;

        var param = {url: baseModule.questionApi + '/update', data: data};
        var request = ajax.put(param);
        request.done(function (d) {
            tips.ok(d.message);
            //更新对象
            pageData = result.update(pageData, d.result, 'id');
            question.get();
            editor.set('#answerKeys', "");

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
            id: 'question-page',
            callback: function (api) {
                index = api.getCurrent();
                question.get();
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
    question.get();
};

var helper = {
    type: function (_type) {
        switch (parseInt(_type)) {
            case 1:
                return "判断";
            case 2:
                return "单选";
            case 3:
                return "多选";
            case 4:
                return "填空";
            case 5:
                return "客观题"

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



