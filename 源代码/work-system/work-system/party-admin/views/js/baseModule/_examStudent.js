var academyData = [], examData = [], pageData = [];
var totalPage = 0, totalCount = 0;
var optId = '', optNumber = '', optExamId = '';
var index = 1, size = 10, academyCode = '', examId = '', number = '';

var config = {
    title: '学生作业管理',
    correct: '../../form/_examCorrect.html',
    correctTitle: '作业批改',
    selectExampleTitle: '选择作业范例',
};

$(function () {
    //自适应
    if (auth.check(this)) {
        view.initHeight();
        $(window).resize(function () {
            view.initHeight();
        });
        var key = url.getUrlParam("key");
        if (key != null && key != "") {
            loading.show();
            examId = key;
        }
        examStudent.get();
        // examStudent.getAcademy();
        examStudent.getAllExamSelect();

    }

});

var examStudent = {
    get: function () {
        var param = {url: baseModule.examApi + '/grade/' + index + '-' + size + '/examid-' + examId + '/academyCode-' + academyCode + '/number-' + number};
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
            loading.hide();

        })
    },
    select: function () {
        //条件查询 fixme 添加学院查询
        examId = $.trim($('#examId').val());
        // academyCode = $.trim($('#academyCode').val());
        number = $.trim($('#number').val());

        index = 1;
        examStudent.get();

    },
    export: function () {
        //fixme:暂用前端导出
        examId = $.trim($('#examId').val());
        academyCode = $.trim($('#academyCode').val());
        number = $.trim($('#number').val());

        var param = {url: baseModule.examApi + '/grade/all/examid-' + examId + '/academyCode-' + academyCode + '/number-' + number};
        // var param = {url: baseModule.examApi + '/grade/all/examid-' + examId   + '/number-'+number};
        var request = ajax.get(param);
        request.done(function (d) {
            var gradeData = d.result;
            var template = doT.template($("#examStudent-export-template").text());//获取的模板
            $('#examStudent-list-hide').html(template(gradeData));//模板装入数据

            $("td.td-number").attr("data-tableexport-msonumberformat", "\\@");
            $("td.td-score").attr("data-tableexport-msonumberformat", "\\@");
            $("td.td-time").attr("data-tableexport-msonumberformat", "\\@");

            $("#examStudent-table-hide").show();
            $("#examStudent-table-hide").tableExport({type: 'excel', fileName: 'grade', tableName: '成绩'});
            $("#examStudent-list-hide").empty();
            $("#examStudent-table-hide").hide();
        })
        // examStudent.get();
    },
    getAllExamSelect: function () {
        //获取所有作业列表
        var param = {url: baseModule.examApi + "/all/selectList"};
        var request = ajax.get(param);
        request.done(function (d) {
            examData = d.result;
            render.examSelect();
            if (examId != null && examId != "") {
                $('#examId').val(examId);
            }
        });
    },
    getAcademy: function () {
        var param = {url: toolModule.getAcademyListApi};
        var reqest = ajax.get(param);
        reqest.done(function (d) {
            academyData = d.result;
            render.academySelect();
        })

    },
    // sync: function (event) {
    //     if (auth.refuse(event))
    //         return false;
    //     optId = getId(event);
    //     var model = result.get(pageData, optId);
    //     optNumber = model.number;
    //     optExamId = model.examId;
    //     tips.confirm({message: '是否要重置该学生缓存？', fun: "opt.sync();", enter: "重置"});
    // },

    returned: function (event) {
        if (auth.refuse(event))
            return false;
        optId = getId(event);
        var model = result.get(pageData, optId);
        optNumber = model.number;
        optExamId = model.examId;
        tips.confirm({message: '是否要退回该学生作业？', fun: "opt.returned();", enter: "退回重做"});
    },

    //批改作业渲染
    correct: function (event) {
        if (auth.refuse(event))
            return false;
        optId = getId(event);//获取当前id的值 = examId

        var model = result.get(pageData, optId);
        model['studentInfo'] = model['grade'] + "级" + model['majorName'] + model['classNo']
            + "班  -  " + model['studentName'];

        openLay({
            url: config.correct, title: config.correctTitle + "：" + model['studentInfo'],
            enter: "确认批改", fun: "opt.correct(2)",
            enter2: "暂存", enter2Fun: "opt.correct(1)",enter2Hidden:false
        });

        var examId = model['examId'];
        var studentNumber = model['number'];

        //通过examId和学号获取选项
        var param = {url: baseModule.examRecordApi + "/record/" + examId + "-" + studentNumber};
        var request = ajax.get(param);
        request.done(function (d) {
            //按类型渲染选项
            //正确答案用颜色标出
            var singleListData = d.result.singleChoice;
            var multipleListData = d.result.multipleChoice;
            var tFListData = d.result.trueOrFalse;
            var gapListData = d.result.gapFilling;
            var subjectiveListData = d.result.subjective;

            render.singleChoiceList(singleListData);
            render.multipleChoiceList(multipleListData);
            render.trueOrFalseList(tFListData);
            render.gapFillingList(gapListData);
            render.subjectiveList(subjectiveListData);
        });

        var typeNum = model['type'];
        model['type'] = helper.type(model["type"]);

        form.set(model);
        model['type'] = typeNum;

        $('#answerKeys').html(model['answerKeys']);
    },

    //选择作业范例
    selectExample: function (event) {
        if (auth.refuse(event))
            return false;
        optId = getId(event);//获取当前id的值 = examId

        var model = result.get(pageData, optId);
        model['studentInfo'] = model['grade'] + "级" + model['majorName'] + model['classNo']
            + "班  -  " + model['studentName'];

        openLay({
            url: config.correct, title: config.selectExampleTitle + "：" + model['studentInfo'],
            enter: "确认", fun: "opt.correct(2)",
        });

        var examId = model['examId'];
        var studentNumber = model['number'];

        //通过examId和学号获取选项
        var param = {url: baseModule.examRecordApi + "/record/" + examId + "-" + studentNumber};
        var request = ajax.get(param);
        request.done(function (d) {
            //按类型渲染选项
            //正确答案用颜色标出
            var singleListData = d.result.singleChoice;
            var multipleListData = d.result.multipleChoice;
            var tFListData = d.result.trueOrFalse;
            var gapListData = d.result.gapFilling;
            var subjectiveListData = d.result.subjective;

            render.singleChoiceList(singleListData);
            render.multipleChoiceList(multipleListData);
            render.trueOrFalseList(tFListData);
            render.gapFillingList(gapListData);
            render.subjectiveList(subjectiveListData);
        });

        var typeNum = model['type'];
        model['type'] = helper.type(model["type"]);

        form.set(model);
        model['type'] = typeNum;

        $('#answerKeys').html(model['answerKeys']);
    },


    checkScore: function (event) {
        var id = getId(event);
        var maxScore = getData(event, "max-score");
        var inputScore = $(event).val();
        if (parseInt(inputScore) < 0) {
            $("#tips_" + id).html("得分不能小于0")
        } else if (parseInt(inputScore) > parseInt(maxScore)) {
            $("#tips_" + id).html("得分不能大于该题分值")
        } else {
            $("#tips_" + id).html("")
        }
    }

};


var opt = {
    // sync: function () {
    //     var request = ajax.put({url: baseModule.examApi + '/syncStudentStatus/' + optExamId + '-' + optNumber});
    //     request.done(function (d) {
    //         tips.ok(d.message);
    //         closeLay();
    //         examStudent.get();
    //
    //     })
    // },

    correct: function (saveType) {

        $.fn.serializeObject = function () {
            var o = {};
            var a = this.serializeArray();
            $.each(a, function () {
                //o[this.name]已定义
                if (o[this.name]) {
                    if (!o[this.name].push) {
                        o[this.name] = [o[this.name]];
                    }
                    o[this.name].push(this.value || '');
                    //未定义
                } else {
                    if (this.name.indexOf("isExample") != -1 || this.name.indexOf("subjectiveScore") != -1
                        || this.name.indexOf("remark") != -1|| this.name.indexOf("recordId") != -1) {
                        o[this.name] = [this.value] || '';//this.value改为[this.value]
                    } else {
                        o[this.name] = this.value || '';
                    }
                }
            });
            return o;
        };

        var json_data = [];
        var forms = $("#correct-form");
        //form的序列化
        for (var i = 0; i < forms.length; i++) {
            json_data.push($(forms[i]).serializeObject());
        }

        var data = {};
        var markObj = {};
        var tmp = json_data[0];

        for (var i = 0; i < tmp['recordId'].length; i++) {
            var recordId = tmp['recordId'][i];
            var d = {
                "remark": tmp['remark'][i],
                "subjectiveScore": tmp['subjectiveScore'][i] || "0",
                "isExample": "2"
            };
            for (var j = 0; tmp['isExample'] && j < tmp['isExample'].length; j++) {
                var example = tmp['isExample'][j];
                if (example == recordId) {
                    d['isExample'] = "1";
                    break;
                }
            }
            markObj[recordId] = d;
        }

        data['subjectiveMark'] = markObj;
        data['number'] = tmp['number'];
        data['examId'] = tmp['examId'];
        data['type'] = saveType+"";

        var param = {url: baseModule.examQuestionApi + "/markSubjective", data: data};
        var request = ajax.put(param);
        request.done(function (d) {
            tips.ok(d.message);
            examStudent.get();
            closeLay();
        })


    },

    returned: function () {
        var request = ajax.put({url: baseModule.examApi + '/returnedExam/' + optExamId + '-' + optNumber});
        request.done(function (d) {
            tips.ok(d.message);
            closeLay();
            examStudent.get();
        })
    },
};


// 渲染
var render = {
    page: function () {
        var template = doT.template($("#examStudent-template").text());//获取的模板
        $('#examStudent-list').html(template(pageData));//模板装入数据

    },
    academySelect: function () {

        var template = doT.template($("#academy-select-template").text());
        $('#academyCode').html(template(academyData));


    },
    examSelect: function () {
        var template = doT.template($("#exam-select-template").text());
        $('#examId').html(template(examData));
    },


    singleChoiceList: function (listData) {
        var template = doT.template($("#choice-list-template").text());
        $('#single-list').html(template(listData));
    },

    multipleChoiceList: function (listData) {
        var template = doT.template($("#choice-list-template").text());
        $('#multiple-list').html(template(listData));
    },

    trueOrFalseList: function (listData) {
        var template = doT.template($("#choice-list-template").text());
        $('#true-or-false-list').html(template(listData));
    },

    gapFillingList: function (listData) {
        var template = doT.template($("#gap-list-template").text());
        $('#gap-list').html(template(listData));
    },
    subjectiveList: function (listData) {
        var template = doT.template($("#subjective-list-template").text());
        $('#subjective-list').html(template(listData));

        //富文本渲染
        for (var i = 0; i < listData.length; i++) {
            editor.render("#" + listData[i].id, 100);
        }
    },

}


// 视图界面
var view = {
    initHeight: function () {
        $('.data-view').css('height', (parent.adaptable().h) - 80);
        $('.date-table').css('height', (parent.adaptable().h) - 180);
        size = Math.floor(((parent.adaptable().h) - 180) / 40);
    }
}

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
            pageArray: [size, 12, 24, 48],
            totalCount: _total,
            id: 'greade-page',
            callback: function (api) {
                index = api.getCurrent();
                examStudent.get();
            }
        });
        if (_pageSize > 0)
            $('.pages').show();
    }
};

function pageChange(event) {
    size = $(event).val();
    index = 1;
    examStudent.get();
};

var helper = {
    status: function (_status) {
        switch (parseInt(_status)) {
            case 0:
                return "未开始";
            case 1:
                return "进行中";
            case 2:
                return "已结束";
        }
    },
    markStatus: function (_markStatus) {
        switch (parseInt(_markStatus)) {
            case 1:
                return "未批改";
            case 2:
                return "已批改";
        }
    },
    type: function (_type) {
        switch (parseInt(_type)) {
            case 1:
                return "课外作业";
            case 2:
                return "期中作业";
            case 3:
                return "期末作业";
        }
    },
}
