var index = 1, size = 6, key = '', totalPage = 0, totalCount = 0;
var baseData = [], pageData = [], examSelectData = [], questionNumber = [];
var optId = '', questionType = ' ', name = ' ';
var gapListData = [], multipleListData = [], singleListData = [], tFListData = [], subjectiveListData = [];
var gapListDataSelect = [], multipleListDataSelect = [], singleListDataSelect = [], tFListDataSelect = [];
var subjectiveDataSelect = [];
var singleScore = 0, multipleScore = 0, gapScore = 0, trueFalseScore = 0, subjectiveScore = 0;
var selectedQuestionIds = [];
var returnData = [];

var config = {
    title: '组卷管理',
    createQuestion: '新增题目',
    selectQuestion: '选择题目',
    questionForm: '../../form/_selectQuestion.html',
    questionBank: '../../form/_questionBank.html',
    addNew: '../../form/_questionAdd.html',
};

$(function () {
    if (auth.check(this)) {
        //自适应
        view.initHeight();
        $(window).resize(function () {
            view.initHeight();
        });
        var key = url.getUrlParam("key");
        if (key != null && key != "") {
            loading.show();
            optId = key;
            examPaperGenerate.examInfo();
        }
        examPaperGenerate.get();
        examPaperGenerate.getExamSelect();
    }
});

var examPaperGenerate = {
    get: function () {
        // var param = {url: baseModule.examQuestionApi + '/exam/' + " "};
        var param = {url: baseModule.questionApi + '/get'};
        var request = ajax.get(param);
        request.done(function (d) {
            // console.log(d.result);
            singleListData = d.result.singleChoice;
            multipleListData = d.result.multipleChoice;
            tFListData = d.result.trueOrFalse;
            gapListData = d.result.gapFilling;
            subjectiveListData = d.result.subjective;
        })
    },


    add: function (event) {

        if ($('#examId').val() == " ") {
            tips.warning("请先选择作业");
            return false;
        }

        var questionType = getData(event, "question-type");
        openLay({
            url: config.addNew,
            fun: "opt.add();",
            title: config.createQuestion + ":" + helper.questionType(questionType) + "题"
        });
        this.changeType(questionType);
    },

    //弹出选题窗口
    selectQuestion: function (event) {

        if ($('#examId').val().trim() == "") {
            tips.warning("请先选择作业");
            return false;
        }

        questionType = getData(event, "question-type");
        openLay({
            url: config.questionBank, fun: "opt.getSelectId();",
            title: config.selectQuestion + "：" + helper.questionType(questionType) + "题",
            enter: "确认"
        });
        this.getQuestionList(questionType);

    },

    getQuestionList: function () {

        var request = $.ajax({
            type: "GET",
            url: baseModule.questionApi + '/list/' + questionType + '-' + name,
            headers: header,
        });


        request.done(function (d) {
            var questionBankData = d.result;
            render.questionList(questionBankData);
        });
        name = " ";
    },

    getExamSelect: function () {
        //获取所有作业列表
        var param = {url: baseModule.examApi + "/all/" + 1};//1-未发布，2-已发布
        var request = ajax.get(param);
        request.done(function (d) {
            examSelectData = d.result;
            render.examSelect();
            if (optId != null && optId != "") {
                $('#examId').val(optId);
            }
        });
    },

    create: function (event) {
        if (auth.refuse(event))
            return false;
        openLay({url: config.form, fun: 'opt.create();', title: config.title});
    },

    examInfo: function () {
        selectedQuestionIds = [];
        // console.log(selectedQuestionIds);
        var examId = $('select#examId').val();
        if (optId != null && optId != "") {
            examId = optId;
        }

        // 初始化为原界面
        var singleTr = $(".single-question");
        for (var i = 1; typeof singleTr != "undefined" && i < singleTr.length; i++) {
            singleTr[i].remove();
        }


        var multipleTr = $(".multiple-question");
        for (var i = 1; typeof multipleTr != "undefined" && i < multipleTr.length; i++) {
            multipleTr[i].remove();
        }

        var tFTr = $(".true-false-question");
        for (var i = 1; typeof tFTr != "undefined" && i < tFTr.length; i++) {
            tFTr[i].remove();
        }

        var gapTr = $(".gap-question");
        for (var i = 1; typeof gapTr != "undefined" && i < gapTr.length; i++) {
            gapTr[i].remove();
        }

        var subjectiveTr = $(".subjective-question");
        for (var i = 1; typeof subjectiveTr != "undefined" && i < subjectiveTr.length; i++) {
            subjectiveTr [i].remove();
        }

        if (examId.trim() == "") {

            $('#type').val(helper.type(""));
            $('#duration').val("");
            $("#all-score").val("");

            $("#true-false-score").html(0);
            $("#single-score").html(0);
            $("#multiple-score").html(0);
            $("#gap-score").html(0);
            $("#subjective-score").html(0);
            return false;
        }

        var request = $.ajax({
            type: "GET",
            // url: baseModule.examApi + '/info/' + examId,
            url: baseModule.examQuestionApi + '/exam/' + examId,
            headers: header,
        });
        request.done(function (d) {
            var examList = d.result.examInfo;
            var examData = examList[0];
            var isMakeStr = examData.isMake;
            if (isMakeStr != null && isMakeStr != "") {
                var isMake = parseInt(isMakeStr);
                if (isMake == 2) {
                    tips.info("当前作业已经组卷，重新组卷将会清空原来组卷信息！若确定要重新组卷请继续操作，若误点请关闭窗口！");
                }
            }
            singleListDataSelect = d.result.singleChoice;

            multipleListDataSelect = d.result.multipleChoice;
            tFListDataSelect = d.result.trueOrFalse;
            gapListDataSelect = d.result.gapFilling;
            subjectiveDataSelect = d.result.subjective;

            render.examInfo(examData);
            loading.hide();
        })
    },

    select: function () {
        key = $('#select-paper').val();
        examQuestion.get();
    },

    queryQuestion: function () {
        name = $('#questionName').val();
        examPaperGenerate.getQuestionList();
    },

    reset: function () {
        $('select#examId').val(" ");
        selectedQuestionIds = [];
        examPaperGenerate.examInfo();
    },

    save: function () {

        // var sum = 0;
        // $('input[name="singleScores"]').each(function () {
        //     sum += parseFloat(this.value);
        // });
        // console.log(sum+" "+singleScore)
        // if(sum != singleOption){
        //     tips.warning("选择题总分值未达到"+singleScore+"分");
        //     return false;
        // }

        if ($('#examId').val() == " ") {
            tips.warning("您未选择作业");
            return false;
        }

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
                    if (this.name == 'singleIds' || this.name == 'multipleIds'
                        || this.name == 'gapIds' || this.name == 'trueFalseIds'
                        || this.name == 'subjectiveIds'
                        || this.name == 'singleScores' || this.name == 'multipleScores'
                        || this.name == 'gapScores' || this.name == 'trueFalseScores'
                        || this.name == 'subjectiveScores'
                    ) {
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

        var data = json_data[0];
        var singleIds = data.singleIds;
        var multipleIds = data.multipleIds;
        var gapIds = data.gapIds;
        var trueFalseIds = data.trueFalseIds;
        var subjectiveIds = data.subjectiveIds;

        if (singleScore != 0) {
            if (singleIds == null || singleIds == "" || singleIds.length < 1) {
                tips.info("单项选择题已设置分数，题目数量不能为空！");
                return false;
            }
            var length = singleIds.length;
            if (!(singleScore % length == 0 || singleScore % length == length / 2)) {
                tips.info("单项选择题单题分数必须为整数或者0.5分结尾！");
                return false;
            }
        }

        if (multipleScore != 0) {
            if (multipleIds == null || multipleIds == "" || multipleIds.length < 1) {
                tips.info("不定项选择题已设置分数，题目数量不能为空！");
                return false;
            }
            var length = multipleIds.length;
            if (!(multipleScore % length == 0 || multipleScore % length == length / 2)) {
                tips.info("不定项选择题单题分数必须为整数或者0.5分结尾！");
                return false;
            }
        }

        if (gapScore != 0) {
            if (gapIds == null || gapIds == "" || gapIds.length < 1) {
                tips.info("填空题已设置分数，题目数量不能为空！");
                return false;
            }
            var length = gapIds.length;
            if (!(gapScore % length == 0 || gapScore % length == length / 2)) {
                tips.info("填空题单题分数必须为整数或者0.5分结尾！");
                return false;
            }
        }

        if (trueFalseScore != 0) {
            if (trueFalseIds == null || trueFalseIds == "" || trueFalseIds.length < 1) {
                tips.info("判断题已设置分数，题目数量不能为空！");
                return false;
            }
            var length = trueFalseIds.length;
            console.log(length);

            if (!(trueFalseScore % length == 0 || trueFalseScore % length == length / 2)) {
                tips.info("判断题单题分数必须为整数或者0.5分结尾！");
                return false;
            }
        }


        if (subjectiveScore != 0) {
            if (subjectiveIds == null || subjectiveIds == "" || subjectiveIds.length < 1) {
                tips.info("客观题已设置分数，题目数量不能为空！");
                return false;
            }
            var length = subjectiveIds.length;
            if (!(subjectiveScore % length == 0 || subjectiveScore % length == length / 2)) {
                tips.info("客观题单题分数必须为整数或者0.5分结尾！");
                return false;
            }
        }


        //  console.log(data);
        if (data['examId'] == "") {
            tips.error("您未选择作业");
            return false;
        }

        var param = {url: baseModule.examQuestionApi + "/custom", data: data};
        var request = ajax.put(param);
        request.done(function (d) {
            tips.done(d);
            examPaperGenerate.reset();
        })

    },


    changeType: function (index) {
        $("form input[id='add-type']").val(index);
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
            case 5 :
                render.subjective();
                break;
        }

    },


    make: function (data) {

        var request = $.ajax({
            type: "POST",
            url: baseModule.questionApi + '/make',
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            headers: header,
            dataType: 'json',
        });

        request.done(function (d) {
            // tips.done(d);
            returnData = d.result;
            examPaperGenerate.get();
            questionType = data['type'];
            id = data['id'];
        });
    },

    //重定向到作业管理
    redirectExam: function (event) {
        if (auth.refuse(event))
            return false;
        optId = getId(event);//获取当前id的值
        window.location.href = server_root + 'my/core/exam';
    },

};

var render = {
    questionList: function (questionBankData) {

        for (var i = 0; i < questionBankData.length; i++) {
            if ($.inArray(questionBankData[i]['id'], selectedQuestionIds) != -1) {
                questionBankData[i]['isCheck'] = true;
            } else {
                questionBankData[i]['isCheck'] = false;
            }
        }

        //  console.log(questionBankData);

        var template = doT.template($("#question-list-template").text());//获取的模板
        $('#question-list').html(template(questionBankData));//模板装入数据

    },
    examSelect: function () {
        var template = doT.template($("#exam-select-template").text());
        // console.log(examSelectData);
        $('#examId').html(template(examSelectData));
    },
    examInfo: function (examInfoData) {
        // var template = doT.template($("#exam-info-template").text());
        singleScore = examInfoData['single'];
        multipleScore = examInfoData['multiple'];
        gapScore = examInfoData['gap'];
        trueFalseScore = examInfoData['trueOrFalse'];
        subjectiveScore = examInfoData['subjective'];

        $('#type').val(helper.type(examInfoData['type']));
        $('#duration').val(examInfoData['duration']);

        if (singleScore == 0) {
            $('#sing-view').css("display", "none");
        } else {
            $('#sing-view').css("display", "block");
        }

        if (multipleScore == 0) {
            $('#multiple-view').css("display", "none");
        } else {
            $('#multiple-view').css("display", "block");
        }

        if (trueFalseScore == 0) {
            $('#true-false-view').css("display", "none");
        } else {
            $('#true-false-view').css("display", "block");
        }
        if (gapScore == 0) {
            $('#gap-view').css("display", "none");
        } else {
            $('#gap-view').css("display", "block");
        }

        if (subjectiveScore == 0) {
            $('#subjective-view').css("display", "none");
        } else {
            $('#subjective-view').css("display", "block");
        }


        $("#true-false-score").html(trueFalseScore);
        $("#single-score").html(singleScore);
        $("#multiple-score").html(multipleScore);
        $("#gap-score").html(gapScore);
        $("#subjective-score").html(subjectiveScore);
        // var allScore = singleScore + multipleScore + gapScore + trueFalseScore;
        var allScore = examInfoData['score'];
        $("#all-score").val(allScore);

        //渲染原题目
        for (var i = 0; i < singleListDataSelect.length; i++) {
            var dat = singleListDataSelect[i];
            questionType = dat['type'];
            opt.selectQuestion({"checkedId": dat['questionId']}, true);
        }

        for (var i = 0; i < multipleListDataSelect.length; i++) {
            var dat = multipleListDataSelect[i];
            questionType = dat['type'];
            opt.selectQuestion({"checkedId": dat['questionId']}, true);
        }

        for (var i = 0; i < tFListDataSelect.length; i++) {
            var dat = tFListDataSelect[i];
            questionType = dat['type'];
            opt.selectQuestion({"checkedId": dat['questionId']}, true);
        }

        for (var i = 0; i < gapListDataSelect.length; i++) {
            var dat = gapListDataSelect[i];
            questionType = dat['type'];
            opt.selectQuestion({"checkedId": dat['questionId']}, true);
        }


        for (var i = 0; i < subjectiveDataSelect.length; i++) {
            var dat = subjectiveDataSelect[i];
            questionType = dat['type'];
            opt.selectQuestion({"checkedId": dat['questionId']}, true);
        }


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

    add: function () {
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
        var forms = $("#opt-add-form");
        //form的序列化
        for (var i = 0; i < forms.length; i++) {
            var opt = $(forms[i]).serializeObject();
            if (opt['type'] != 1 && opt['type'] != 5) {
                for (var j = 0; j < opt['optionInfo'].length; j++) {
                    if (opt["optionInfo"][j] == "") {
                        tips.warning("选项不能留空");
                        return false;
                    }
                }
            }
            json_data.push(opt);
        }
        var data = json_data[0];

        if (data['questionName'] == "") {
            tips.warning("请输入题目");
            return false;
        }
        if (data['type'] != 4 && data['type'] != 5 && typeof data['answerNumber'] == "undefined") {
            tips.warning("请选择正确答案");
            return false;
        }

        var id = '';
        // var param = {url: baseModule.questionApi + '/make', data: data};
        // var request = ajax.post(param);

        //新增题目
        examPaperGenerate.make(data);

        tips.confirm({
            message: '是否添加到该作业？', fun: "opt.addConfirm();",
            enter: "添加", color: "5ABAFF"
        });
        // opt.addConfirm();

        closeLay();

    },

    addConfirm: function () {
        if (opt.selectQuestion({"checkedId": returnData['id'] + ""}, false))
            tips.ok("添加成功");
        else
            tips.error("添加失败");

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

    getSelectId: function () {
        var selectIds = form.get("#select-form");
        opt.selectQuestion(selectIds, false);
    },

    selectQuestion: function (selectIds, isInit) {

        //json判空，是{}则关闭窗口
        if ($.isEmptyObject(selectIds)) {
            closeLay();
            return false;
        }
        // console.log(selectIds);

        selectIds = selectIds['checkedId'].split(",");

        // console.log(selectIds);

        for (var i = 0; selectedQuestionIds.length > 0 && i < selectIds.length; i++) {
            if (selectedQuestionIds.indexOf(selectIds[i]) != -1) {
                var num = $('#' + selectIds[i]).attr('data-num');
                tips.warning("第" + num + "题已被选择");
                return false;
            }
        }

        for (var i = 0; i < selectIds.length; i++) {
            selectedQuestionIds[selectedQuestionIds.length] = selectIds[i];
        }

        //将勾选的所有问题id追加到相应题型尾部
        for (var i = 0; i < selectIds.length; i++) {
            var selectId = selectIds[i] + "";

            //1-判断题
            if (questionType == 1) {
                var tFListDatas = isInit ? tFListDataSelect : tFListData;
                var model = result.getByAttr(tFListDatas, "questionId", selectId);
                var len = $('.true-false-question').length;
                var optionStr = "";
                var options = model['options'];
                for (var j = 0; j < options.length; j++) {
                    optionStr += "<span class=\"option-num\">" + String.fromCharCode(65 + j) + "</span>" + options[j].optionInfo + "<br>";
                }

                $('.true-false-question').eq($('.true-false-question').length - 1).after(
                    '<tr class="true-false-question" class="form-control">'
                    + '<td style="text-align: center;width:30px"><a class="glyphicon trueFalseQuestion glyphicon-trash" aria-hidden="true" >'
                    + '<input type="hidden" name="trueFalseIds" value="' + selectId + '">'
                    + '</a></td>'
                    + '<td class="trueFalseNumber" style="text-align: center"><span class="question-number">' + len + '</span></td>'
                    + '<td>' +
                    '<p name="questionInfo">' + model["questionName"] + '</p>' +
                    optionStr
                    // + '分值：<input type="number" name="trueFalseScores" data-id="true-false-score' + len + '" '
                    // + ' question-type="' + questionType + '" onblur="opt.blurs(this)" value="' + (model["score"] || "0") + '">分'
                    + '&emsp;<span name="true-false-tips" id="true-false-score' + len + '" style="color: red"></span>'
                    + '</td>'
                    + '</tr>'
                )
            } else if (questionType == 2) {

                var singleListDatas = isInit ? singleListDataSelect : singleListData;

                var model = result.getByAttr(singleListDatas, "questionId", selectId);
                var len = $('.single-question').length;

                var optionStr = "";
                var options = model['options'];
                for (var j = 0; j < options.length; j++) {
                    optionStr += "<span class=\"option-num\">" + String.fromCharCode(65 + j) + "</span>" + options[j].optionInfo + "<br>";
                }

                $('.single-question').eq($('.single-question').length - 1).after(
                    '<tr class="single-question" class="form-control">'
                    + '<td style="text-align: center;width:30px"><a class="glyphicon singleQuestion glyphicon-trash" aria-hidden="true" >'
                    + '<input type="hidden" name="singleIds" value="' + selectId + '">'
                    + '</a></td>'
                    + '<td class="singleNumber" style="text-align: center"><span class="question-number">' + len + '</span></td>'
                    + '<td><p name="questionInfo">' + model["questionName"] + '</p>' +
                    optionStr
                    // + '分值：<input type="number" name="singleScores" data-id="single-score' + len + '" '
                    // + '  question-type="' + questionType + '" onblur="opt.blurs(this)"  value="' + (model["score"] || "0") + '">分'
                    + '&emsp;<span name="single-tips" id="single-score' + len + '" style="color: red"></span>'
                    + '</td>'
                    + '</tr>'
                )
            } else if (questionType == 3) {

                var multipleListDatas = isInit ? multipleListDataSelect : multipleListData;

                var model = result.getByAttr(multipleListDatas, "questionId", selectId);
                var len = $('.multiple-question').length;
                // console.log(model);

                var optionStr = "";
                var options = model['options'];
                for (var j = 0; j < options.length; j++) {
                    optionStr += "<span class=\"option-num\">" + String.fromCharCode(65 + j) + "</span>" + options[j].optionInfo + "<br>";
                }

                $('.multiple-question').eq($('.multiple-question').length - 1).after(
                    '<tr class="multiple-question" class="form-control">'
                    + '<td style="text-align: center;width:30px"><a class="glyphicon multipleQuestion glyphicon-trash" aria-hidden="true" >'
                    + '<input type="hidden" name="multipleIds" value="' + selectId + '">'
                    + '</a></td>'
                    + '<td class="multipleNumber" style="text-align: center"><span class="question-number">' + len + '</span></td>'
                    + '<td><p name="questionInfo">' + model["questionName"] + '</p>' +
                    optionStr
                    // + '分值：<input type="number" name="multipleScores" data-id="multiple-score' + len + '" '
                    // + ' question-type="' + questionType + '" onblur="opt.blurs(this)"  value="' + (model["score"] || "0") + '">分'
                    + '&emsp;<span name="multiple-tips" id="multiple-score' + len + '" style="color: red"></span>'
                    + '</td>'
                    + '</tr>'
                )
            } else if (questionType == 4) {
                var gapListDatas = isInit ? gapListDataSelect : gapListData;

                var model = result.getByAttr(gapListDatas, "questionId", selectId);
                var len = $('.gap-question').length;
                var optionStr = "";
                var options = model['options'];
                for (var j = 0; j < options.length; j++) {
                    optionStr += "<span class=\"option-num\">" + String.fromCharCode(65 + j) + "</span>" + options[j].optionInfo + "<br>";
                }

                $('.gap-question').eq($('.gap-question').length - 1).after(
                    '<tr class="gap-question" class="form-control">'
                    + '<td style="text-align: center;width:30px"><a class="glyphicon gapQuestion glyphicon-trash" aria-hidden="true" >'
                    + '<input type="hidden" name="gapIds" value="' + selectId + '">'
                    + '</a></td>'
                    + '<td class="gapNumber" style="text-align: center"><span class="question-number">' + len + '</span></td>'
                    + '<td><p name="questionInfo">' + model["questionName"] + '</p>' +
                    optionStr
                    // + '分值：<input type="number" name="gapScores" data-id="gap-score' + len + '" '
                    // + ' question-type="' + questionType + '" onblur="opt.blurs(this)" value="' + (model["score"] || "0") + '">分'
                    + '&emsp;<span name="gap-tips" id="gap-score' + len + '" style="color: red"></span>'
                    + '</td>'
                    + '</tr>'
                )
            } else if (questionType == 5) {
                var subjectiveListDatas = isInit ? subjectiveDataSelect : subjectiveListData;
                var model = result.getByAttr(subjectiveListDatas, "questionId", selectId);
                var len = $('.subjective-question').length;

                $('.subjective-question').eq($('.subjective-question').length - 1).after(
                    '<tr class="subjective-question" class="form-control">'
                    + '<td style="text-align: center;width:30px"><a class="glyphicon subjectiveQuestion glyphicon-trash" aria-hidden="true" >'
                    + '<input type="hidden" name="subjectiveIds" value="' + selectId + '">'
                    + '</a></td>'
                    + '<td class="subjectiveNumber" style="text-align: center"><span class="question-number">' + len + '</span></td>'
                    + '<td><p name="questionInfo">' + model["questionName"] + '</p>'
                    + '&emsp;<span name="subjective-tips" id="subjective-score' + len + '" style="color: red"></span>'
                    + '</td>'
                    + '</tr>'
                );
            }
            closeLay();
        }


        return true;
    },


    blurs: function (event) {


        var inputVal = $(event).val();
        //统计各题型现设置的分值总和
        var thisId = '#' + getId(event);

        var sum = 0.0;

        var questionType = getData(event, "question-type");

        switch (parseInt(questionType)) {
            case 1:

                $('input[name="trueFalseScores"]').each(function () {
                    sum += parseFloat(this.value);
                });

                if (sum > trueFalseScore) {
                    $(thisId).html("已超过选择题的总分值");
                } else {
                    $("span[name='true-false-tips']").each(function () {
                        $(this).text("");
                    });
                }

                break;
            case 2:

                $('input[name="singleScores"]').each(function () {
                    sum += parseFloat(this.value);
                });

                if (sum > singleScore) {
                    $(thisId).html("已超过单选题的总分值");
                } else {
                    $("span[name='single-tips']").each(function () {
                        $(this).text("");
                    });
                }
                break;
            case 3:

                $('input[name="multipleScores"]').each(function () {
                    sum += parseFloat(this.value);
                });

                if (sum > multipleScore) {
                    $(thisId).html("已超过不定项选择题的总分值");
                } else {
                    $("span[name='multiple-tips']").each(function () {
                        $(this).text("");
                    });
                }

                break;
            case 4:

                $('input[name="gapScores"]').each(function () {
                    sum += parseFloat(this.value);
                });

                if (sum > gapScore) {
                    $(thisId).html("已超过填空题的总分值");
                } else {
                    $("span[name='gap-tips']").each(function () {
                        $(this).text("");
                    });
                }
                break;
        }

        if (inputVal.trim().length == 0) {
            $(thisId).html("*请输入分值");
        }

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
                return "每月一测";
            case 2:
                return "结业考试";
        }
    },
    questionType: function (_questionType) {
        switch (parseInt(_questionType)) {
            case 1:
                return "判断";
            case 2:
                return "单选";
            case 3:
                return "多选";
            case 4:
                return "填空";
            case 5:
                return "客观"

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


$(document).on('click', '.gapQuestion', function () {

    var len = $('.gap-question').length;
    if (len - 1 == 0) {
        return false;
    }

    var id = $(this).children().val() + "";
    selectedQuestionIds.splice($.inArray(id, selectedQuestionIds), 1);

    $(this).parent().parent('.gap-question').remove();

    //重新编号
    $('.gapNumber').each(function (i) {
        $(this).html("<span class=\"question-number\">" + (i + 1) + "</span>");
    });

});


$(document).on('click', '.singleQuestion', function () {

    var len = $('.single-question').length;
    if (len - 1 == 0) {
        return false;
    }

    var id = $(this).children().val() + "";
    selectedQuestionIds.splice($.inArray(id, selectedQuestionIds), 1);

    $(this).parent().parent('.single-question').remove();

    //重新编号
    $('.singleNumber').each(function (i) {
        $(this).html("<span class=\"question-number\">" + (i + 1) + "</span>");
    });

});


$(document).on('click', '.multipleQuestion', function () {

    var len = $('.multiple-question').length;
    if (len - 1 == 0) {
        return false;
    }

    var id = $(this).children().val() + "";
    selectedQuestionIds.splice($.inArray(id, selectedQuestionIds), 1);

    $(this).parent().parent('.multiple-question').remove();

    //重新编号
    $('.multipleNumber').each(function (i) {
        $(this).html("<span class=\"question-number\">" + (i + 1) + "</span>");
    });

});


$(document).on('click', '.trueFalseQuestion', function () {

    var len = $('.true-false-question').length;
    if (len - 1 == 0) {
        return false;
    }

    var id = $(this).children().val() + "";
    selectedQuestionIds.splice($.inArray(id, selectedQuestionIds), 1);

    $(this).parent().parent('.true-false-question').remove();

    //重新编号
    $('.trueFalseNumber').each(function (i) {
        $(this).html("<span class=\"question-number\">" + (i + 1) + "</span>");
    });

});
