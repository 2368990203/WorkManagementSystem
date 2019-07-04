var index = 1, size = 6, key = '', totalPage = 0, totalCount = 0;
var baseData = [], pageData = [], examSelectData = [], questionNumber = [];
var optId = '';

var config = {
    form: '../../form/_examQuestion.html',
    title: '组卷管理',
    read: '../../form/_examQuestionRead.html',
    readTitle: '题目详情',
    examRandom: '../../form/_examRandom.html',
};

$(function () {
    if (auth.check(this)) {
        //自适应
        view.initHeight();
        $(window).resize(function () {
            view.initHeight();
        });
        examQuestion.get();
        examQuestion.getExamSelect();
    }
});

var examQuestion = {
    get: function () {
        // var param = {url: baseModule.examQuestionApi + '/' + index + '-' + size + '-' + key};
        var param = {url: baseModule.examQuestionApi + '/' + index + '-' + size + '-' + key};
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

    getExamSelect: function () {
        //获取所有作业列表
        var param = {url: baseModule.examApi + "/all/" + 1};//2-已发布
        var request = ajax.get(param);
        request.done(function (d) {
            examSelectData = d.result;
            render.examSelect();
        });
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
        tips.confirm({message: '是否要删除作业和题目关系数据？', fun: "opt.delete();"});

    },

    update: function (event) {
        if (auth.refuse(event))
            return false;
        optId = getId(event);//获取当前id的值
        openLay({url: config.form, fun: "opt.update();", title: config.title});
        var model = result.get(pageData, optId);

        form.set(model);
    },

    randomExam: function () {
        openLay({url: config.examRandom, fun: "opt.randomExam();", title: config.title});

        examQuestion.getExamSelect();
        // render.examSelect();

        //获取渲染题库的题目数量
        var param = {url: baseModule.examQuestionApi + "/type"};
        var request = ajax.get(param);
        request.done(function (d) {
            questionNumber = d.result;
        });


    },

    examInfo: function () {
        var examId = $('select#examId').val();

        if (examId == " ") {
            $('#examInfo').html("");
            return false;
        }

        var request = $.ajax({
            type: "GET",
            url: baseModule.examApi + '/info/' + examId,
            // contentType: "json",
            headers: header,
            // dataType: 'json',
        });
        request.done(function (d) {
            var examData = d.result;
            render.examInfo(examData);
        })
    },

    select: function () {
        key = $('#select-paper').val();
        examQuestion.get();
    },


    read: function (event) {
        // if (auth.refuse(event))
        //     return false;
        optId = getId(event);//获取当前id的值

        openLay({url: config.read, title: config.readTitle});
        $('#opt-dialog-enter').hide();

        var model = result.get(pageData, optId);
        var type = model['type'];
        var questionId = model['questionId'];

        //通过问题获取选项
        var param = {url: baseModule.optionsApi + "/questionId/" + questionId};
        var request = ajax.get(param);
        request.done(function (d) {
            //按类型渲染选项
            //正确答案用颜色标出
            var optionData = d.result;
            // console.log(optionData);

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
            }
        });
        model['type'] = helper.type(model['type']);
        form.set(model);
        model['type'] = type;
        $('#answerKeys').html(model['answerKeys'])
    },


};

var render = {
    page: function () {
        var template = doT.template($("#examQuestion-template").text());//获取的模板
        $('#item-list').html(template(pageData));//模板装入数据
    },
    examSelect: function () {
        var template = doT.template($("#exam-select-template").text());
        $('#examId').html(template(examSelectData));

        $('#select-paper').html(template(examSelectData));
        $('#select-paper option[value=" "]').html("全部作业")
    },
    examInfo: function (examInfoData) {
        var template = doT.template($("#exam-info-template").text());
        $('#examInfo').html(template(examInfoData));
        $("#trueOrFalseNumberQuestion").html(questionNumber['trueOrFalse']);
        $("#singleNumberQuestion").html(questionNumber['single']);
        $("#multipleNumberQuestion").html(questionNumber['multiple']);
        $("#gapNumberQuestion").html(questionNumber['gap']);
        // var allQuestion = questionNumber['trueOrFalse']+questionNumber['single']+questionNumber['multiple']+questionNumber['gap'];
        // $("#allQuestion").html(allQuestion);
    },

    trueOrFalse: function (optionData) {
        var template = doT.template($("#true-or-false").text());
        $('#options').html(template(optionData));
    },

    singleChoice: function (optionData) {
        var template = doT.template($("#single-choice").text());
        $('#options').html(template(optionData));
    },

    multipleChoice: function (optionData) {
        var template = doT.template($("#multiple-choice").text());
        $('#options').html(template(optionData));
    },

    gapFilling: function (optionData) {
        var template = doT.template($("#gap-filling").text());
        $('#options').html(template(optionData));
    },

};

var opt = {
    create: function () {
        var data = form.get("#opt-form");
        if (form.verify(data))
            return false;

        var param = {url: baseModule.examQuestionApi, data: data};
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
        var request = ajax.delete({url: baseModule.examQuestionApi + '/' + optId});
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

        var param = {url: baseModule.examQuestionApi, data: data};
        var request = ajax.put(param); //加一条记录
        request.done(function (d) {
            tips.ok(d.message);
            //更新对象
            pageData = result.update(pageData, d.result, 'id');
            examQuestion.get();
            closeLay();
        })
    },

    randomExam: function () {
        var data = form.get("#opt-form");
        if (form.verify(data))
            return false;
        var examId = data['examId'];
        if (examId == "") {
            tips.warning("请选择作业");
            return false;
        }
        // console.log(data);

        var param = {url: baseModule.examQuestionApi + '/random', data: data};
        var request = ajax.put(param);
        request.done(function (d) {
            tips.ok(d.message);
            //更新对象
            examQuestion.get();
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
            id: 'examQuestion-page',
            callback: function (api) {
                index = api.getCurrent();
                examQuestion.get();
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
    examQuestion.get();
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
