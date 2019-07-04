var examId = '', markFlag = false;


function getQueryString(key) {
    var reg = new RegExp("(^|&)" + key + "=([^&]*)(&|$)");
    var result = window.location.search.substr(1).match(reg);
    return result ? decodeURIComponent(result[2]) : null;
}

$(function () {

    if (checkAuth()) {
        return false;
    }

    //获取地址栏Id
    examId = getQueryString('examId');
    if (null == examId || "" == examId) {
        tips.error("试卷不存在");
    }
    document.onkeydown = function (e) {
        if (e.keyCode === 116) {
            return false;
        }
        if (e.keyCode === 9) {
            return false;
        }
        if (e.keyCode === 13) {
            return false;
        }
        if (e.keyCode === 27) {
            return false;
        }
    };


    var param = {url: baseModule.examQuestionApi + '/exam/' + examId};
    var request = ajax.get(param);
    request.success(function (d) {
        // 单选题
        var singleChoiceData = d.result.singleChoice;
        //以order值来排序题号
        if (singleChoiceData != null && singleChoiceData.length > 0) {
            singleChoiceData = json.asc(singleChoiceData, "order");
        }

        for (var i = 0; i < singleChoiceData.length; i++) {
            singleChoiceData[i]["options"] = json.asc(singleChoiceData[i]["options"], "order");
        }
        var template = doT.template($("#single-template").text());
        $('#single-choice').html(template(singleChoiceData));
        template = doT.template($("#single-card-template").text());
        $('#single-card').html(template(singleChoiceData));

        // 多选题
        var multipleChoiceData = d.result.multipleChoice;
        if (multipleChoiceData != null && multipleChoiceData.length > 0) {
            multipleChoiceData = json.asc(multipleChoiceData, "order");
        }

        for (var i = 0; i < multipleChoiceData.length; i++) {
            multipleChoiceData[i]["options"] = json.asc(multipleChoiceData[i]["options"], "order");
        }

        template = doT.template($("#multiple-template").text());
        $('#multiple-choice').html(template(multipleChoiceData));
        template = doT.template($("#multiple-card-template").text());
        $('#multiple-card').html(template(multipleChoiceData));

        //判断题
        var trueOrFalseData = d.result.trueOrFalse;
        if (trueOrFalseData != null && trueOrFalseData.length > 0) {
            trueOrFalseData = json.asc(trueOrFalseData, "order");
        }
        template = doT.template($("#true-or-false-template").text());
        $('#true-or-false').html(template(trueOrFalseData));

        template = doT.template($("#true-or-false-card-template").text());
        $('#true-or-false-card').html(template(trueOrFalseData));

        //填空题
        var gapFillingData = d.result.gapFilling;
        if (gapFillingData != null && gapFillingData.length > 0) {
            gapFillingData = json.asc(gapFillingData, "order");
        }
        template = doT.template($("#gap-filling-template").text());
        $('#gap-filling').html(template(gapFillingData));

        template = doT.template($("#gap-filling-card-template").text());
        $('#gap-filling-card').html(template(gapFillingData));


        //客观题
        var subjectiveData = d.result.subjective;
        if (subjectiveData != null && subjectiveData.length > 0) {
            subjectiveData = json.asc(subjectiveData, "order");
        }
        template = doT.template($("#subjective-template").text());
        $('#subjective').html(template(subjectiveData));

        template = doT.template($("#subjective-card-template").text());
        $('#subjective-card').html(template(subjectiveData));

        editor.render(".subjective");
        //富文本
        for (var i = 0; i < subjectiveData.length; i++) {
            var subjectiveId = $('#' + subjectiveData[i].questionId);
            editor.set(subjectiveId, "");


            //当富文本内容改变change时保存到store
            subjectiveId.on('summernote.change', function () {
                var questionId = this.id;

                var questionEvent = $("#" + questionId);

                var exam = store.get(examId);
                if (exam == undefined || exam == {}) {
                    exam = {};//未存在，初始化
                }

                var subjective = exam["subjective"];
                if (subjective == undefined) {
                    subjective = {};
                }
                var subjectiveCheck = subjective[questionId];
                if (subjectiveCheck == undefined) {
                    subjectiveCheck = {};
                }
                // 富文本的内容
                var contentVal = questionEvent.val();

                subjective[questionId] = contentVal;

                exam["subjective"] = subjective;
                store.set(examId, exam);

                if (contentVal != undefined && contentVal.trim() != "") {
                    var cardLi = $('a[href=#' + questionId + ']');
                    if (!cardLi.hasClass('hasBeenAnswer')) {
                        cardLi.addClass('hasBeenAnswer');
                    }
                }else{
                    var cardLi = $('a[href=#' + questionId + ']');
                    if (cardLi.hasClass('hasBeenAnswer')) {
                        cardLi.removeClass('hasBeenAnswer')
                    }
                }
            });

        }


        render.historyChoice();


        //考试倒计时
        var examInfo = d.result.examInfo;
        var seconds = parseInt(examInfo[0]['duration']);//秒
        var minutes = parseInt(seconds / 60);//分
        var hours = parseInt(minutes / 60);//时

        var name = examInfo[0]['name'];
        $('#test_name').html("<h2> " + name + "</h2>");
        document.title = "作业>" + name;


        var timeStr = "";//格式  时：分：秒
        timeStr += hours;//小时
        timeStr += ":";
        timeStr += minutes % 60;//分钟
        timeStr += ":";
        timeStr += seconds % 60;//秒

        $("#duration-card").text(timeStr);
        $("#duration-foot").text(timeStr);
        start();
        startSaveTimer();


        //考试倒计时
        $('time').countDown({
            with_separators: false
        });
        $('.alt-1').countDown({
            css_class: 'countdown-alt-1'
        });
        $('.alt-2').countDown({
            css_class: 'countdown-alt-2'
        });

    })

    request.error(function (d) {
        markFlag = true;
        var res = d.responseJSON;
        swal({
                title: "",
                text: res.message,
                type: "error",
                showCancelButton: false,
                confirmButtonColor: "#DD6B55",
                confirmButtonText: "确定",
                closeOnConfirm: true,
                closeOnCancel: true,
                allowEscapeKey: false,


            },
            function () {
                window.location.href = server_root + "examList";
            }
        );
        return false;
    })
    //未完成时离开界面会进行提示

    window.onbeforeunload = function (e) {
        if (!markFlag) {
            e = e || window.event;

            // 兼容IE8和Firefox 4之前的版本
            if (e) {
                e.returnValue = "当前正在进行考试作答，确定离开此页面吗？";

            }

            // Chrome, Safari, Firefox 4+, Opera 12+ , IE 9+
            return "当前正在进行考试作答，确定离开此页面吗？";
        }

    };


});

var data = {};

function commitPaper(timeOut) {

    var unFinish = false;

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
                if (this.name.indexOf("MultipleAnswer") != -1 || this.name.indexOf("gapAnswer") != -1) {
                    o[this.name] = [this.value] || '';//this.value改为[this.value]
                } else {
                    o[this.name] = this.value || '';
                }
            }
        });
        return o;
    };

    var json_data = [];
    var forms = $("#exam-form");
    //form的序列化
    for (var i = 0; i < forms.length; i++) {
        json_data.push($(forms[i]).serializeObject());
    }

    var model = json_data[0];
    var tFArr = {};
    var singleArr = {};
    var multipleArr = {};
    var gapArr = {};
    var subjectiveArr = {};

    var i = 0;
    var j = 0;
    var k = 0;
    var m = 0;
    var n = 0;
    for (var variable in model) {
        if (variable.indexOf("multipleQuestionId") != -1) {
            //questionId和optionId一起传
            //通过后缀绑定question和option
            var questionName = variable;
            var optionIds = "MultipleAnswer" + variable.split("multipleQuestionId")[1]
            multipleArr[i] = {};
            multipleArr[i]['questionId'] = [model[questionName]];
            multipleArr[i]['optionIds'] = model[optionIds] || [""];
            // multipleArr[i]['ordered'] = [model['question_' + multipleArr[i]['questionId']]];
            i++;

            if (typeof model[optionIds] == "undefined") {
                unFinish = true;
            }
        } else if (variable.indexOf("singleQuestionId") != -1) {
            var questionName = variable;
            var optionId = "SingleAnswer" + variable.split("singleQuestionId")[1];
            singleArr[j] = {};

            singleArr[j]['questionId'] = model[questionName];
            singleArr[j]['optionId'] = model[optionId] || "";
            // singleArr[j]['ordered'] = model['question_' + singleArr[j]['questionId']];
            j++;

            if (typeof model[optionId] == "undefined") {
                unFinish = true;
            }

        } else if (variable.indexOf("TFQuestionId") != -1) {
            // tFArr[k] = model[variable];
            var questionName = variable;
            var optionId = "TFAnswer" + variable.split("TFQuestionId")[1];
            tFArr[k] = {};
            tFArr[k]['questionId'] = model[questionName];
            tFArr[k]['optionId'] = model[optionId] || "";
            // tFArr[k]['ordered'] = model['question_' + tFArr[k]['questionId']];
            k++;

            if (typeof model[optionId] == "undefined") {
                unFinish = true;
            }
        } else if (variable.indexOf("gapQuestionId") != -1) {

            var questionName = variable;
            var gapInput = "gapAnswer" + variable.split("gapQuestionId")[1];
            gapArr[m] = {};
            gapArr[m]['questionId'] = [model[variable]];
            gapArr[m]['gapInput'] = model[gapInput] || [""];
            // gapArr[m]['ordered'] = [model['question_' + gapArr[m]['questionId']]];
            m++;

            if (model[gapInput] == "") {
                unFinish = true;
            }
        }else if (variable.indexOf("subjectiveQuestionId") != -1) {

            var questionName = variable;
            var subjectiveInput = "subjectiveAnswer" + variable.split("subjectiveQuestionId")[1];
            subjectiveArr[n] = {};
            subjectiveArr[n]['questionId'] = model[variable];
            subjectiveArr[n]['gapInput'] = model[subjectiveInput] || "";
            // subjectiveArr[n]['ordered'] = model['question_' + subjectiveArr[n]['questionId']];
            n++;

            if (model[subjectiveInput] == "") {
                unFinish = true;
            }
        }
    }

    data['examId'] = examId;
    data['trueFalseAnswer'] = tFArr;
    data['singleAnswer'] = singleArr;
    data['multipleAnswer'] = multipleArr;
    data['gapAnswer'] = gapArr;
    data['subjectiveAnswer'] = subjectiveArr;



    if (timeOut) {
        toMark();
    } else if (unFinish) {
        tips.confirm({
            message: "您有未完成题目，是否确定交卷？一经提交将无法继续作答。",
            enter: "交卷",
            fun: "toMark()",
            color: "#389fc3",
            closeOnConfirm: true,
            closeOnCancel: true
        });
    } else {
        tips.confirm({
            message: "您是否确定交卷？一经提交将无法继续作答。",
            enter: "确定",
            fun: "toMark()",
            color: "#389fc3",
            closeOnConfirm: true,
            closeOnCancel: true
        });
    }
}

var timer;


function start() {
    timer = setInterval(consoleFunc, 1000);
}

var tipsFlag = false;

function consoleFunc() {
    if (markFlag) {
        clearInterval(timer);
    }

    var h1 = parseInt($('.hh-1').html());
    var h2 = parseInt($('.hh-2').html());
    var m1 = parseInt($('.mm-1').html());
    var m2 = parseInt($('.mm-2').html());
    var s1 = parseInt($('.ss-1').html());
    var s2 = parseInt($('.ss-2').html());

    if (m1 == 0 && m2 == 5 && s1 == 0 && s2 == 0 && !tipsFlag) {
        tips.info("离考试结束还有五分钟，请尽快答题，倒计时结束时将会自动交卷！");
        tipsFlag = true;
    }
    if ((h1 + h2 + m1 + m2 + s1 + s2) == 0) {
        commitPaper(true);
        clearInterval(timer)
    }
}

//定时任务，存储答题记录到redis


var redis_timer;

var redis_time = 1000 * 60 * 10;//10分钟提交一次服务器

function startSaveTimer() {
    redis_timer = setInterval(function () {
        var examData = store.get(examId);

        // console.log(examData);
        if (examData == undefined || examData == "{}" || examData == {} || examData.length == 0) {
            return false;
        }
        examData['examId'] = examId;
        var param = {url: baseModule.examQuestionApi + '/history', data: examData};
        var request = ajax.post(param);
        request.success(function (d) {
        });

    }, redis_time);
}

function toMark() {
    waiting(true);
    markFlag = true;
    clearInterval(redis_timer);
    clearInterval(timer);

    console.log(data);

    var param = {url: baseModule.examQuestionApi + '/mark', data: data};
    var request = ajax.put(param);
    request.success(function (d) {
        var scoreStr = d.result;
        waiting(false);
        var score = parseFloat(scoreStr);
        var showStr = "";
        if (score < 60) {
            showStr = "<span style='color:#d33030;font-size: 100px'>" + score + "</span>";
        } else {
            showStr = "<span style='color:#31D884;font-size: 100px'>" + score + "</span>";
        }

        // swal({
        //         title: "答题结束，本次得分",
        //         text: showStr,
        //         html: true,
        //         showCancelButton: false,
        //         confirmButtonColor: "#31D884",
        //         confirmButtonText: "确定",
        //         closeOnConfirm: true,
        //         closeOnCancel: true,
        //         allowEscapeKey: false,
        //         customClass: "examTips",
        //
        //     },
        //     function () {
                window.location.href = server_root + "examList";
        //     }
        // );
    });
    request.error(function (d) {
        var res = d.responseJSON;

        swal({
                title: "",
                text: res.message,
                type: "error",
                showCancelButton: false,
                confirmButtonColor: "#DD6B55",
                confirmButtonText: "确定",
                closeOnConfirm: true,
                closeOnCancel: true,
                allowEscapeKey: false,

            },
            function () {
                window.location.href = server_root + "examList";
            }
        );
        return false;

    })


}

var $Commit = $('#commit_paper');

function waiting(flag) {
    if (flag) {
        $Commit.val('评卷中...')
    } else {
        $Commit.val('交卷')
    }
}


// 5分页
var page = {
    init: function (_currentPage, _pageSize, _totalPage, _totalCount) {
        new myPagination({
            id: 'pagination',
            curPage: _currentPage, //当前页码
            pageTotal: _totalPage, //总页数
            pageAmount: _pageSize,  //每页多少条
            dataTotal: _totalCount, //总共多少条数据
            // pageSize: size, //可选,分页个数
            //showPageTotalFlag:true, //是否显示数据统计
            showSkipInputFlag: true, //是否支持跳转
            getPage: function (page) {
                //获取当前页数
                index = page;
                courseList.get(index, size);
            }
        })
    }
};

//存储答题记录到本地
function clickAnswer(event) {
    var optionId = $(event).attr("id");
    var dataType = $(event).attr("type-data");//data-type:1-判断，2-单选，3-多选，4-填空

    var questionId = $(event).closest('.test_content_nr_main').closest('li').attr('id'); // 得到题目ID

    var exam = store.get(examId) || {};//试卷作为主键，区分不同试卷
    var singleChoice = {};//存储选择类的题目答案，包括：单选，判断
    var multiChoice = {};//存储选择类的题目答案，包括：多选
    var gapChoice = {};//存储选择类的题目答案，包括：填空，客观题

    singleChoice = exam["singleChoice"] || {};
    multiChoice = exam['multiChoice'] || {};
    gapChoice = exam['gapChoice'] || {};

    if (dataType == 1 || dataType == 2) {//单选和判断直接使用key覆盖即可
        singleChoice[questionId] = optionId;
    } else if (dataType == 3) {
        if (multiChoice == undefined) {
            multiChoice = {};
        }
        var multiQuestionCheck = multiChoice[questionId];
        if (multiQuestionCheck == undefined) {
            multiQuestionCheck = [];
        }
        //复选框是否被选中
        var isChecked = $("#" + optionId).is(":checked");// ??会被点击两次??以最后一次点击为准
        //选中答案，将选项id加到选择数组里
        if (isChecked) {
            if (getIndex(multiQuestionCheck, optionId) == false) {
                multiQuestionCheck[multiQuestionCheck.length] = optionId;
            }
        } else { //取消选择，从选组里删除
            //找到一个值为optionId的下标，删除optionId的元素
            multiQuestionCheck.splice(getIndex(multiQuestionCheck, optionId), 1);
        }
        multiChoice[questionId] = multiQuestionCheck;

    } else if (dataType == 4) {//4-填空
        if (gapChoice == undefined) {
            gapChoice = {};
        }
        var gapChoiceCheck = gapChoice[questionId];
        if (gapChoiceCheck == undefined) {
            gapChoiceCheck = {};
        }
        // 空格的内容
        var gapVal = $("#" + optionId).val();
        gapChoiceCheck[optionId] = gapVal;
        gapChoice[questionId] = gapChoiceCheck;
    } else if (dataType == 5) {//5-客观题
        //已重写change事件
    }


    exam["singleChoice"] = singleChoice;
    exam["multiChoice"] = multiChoice;
    exam["gapChoice"] = gapChoice;
    store.set(examId, exam);

    var cardLi = $('a[href=#' + questionId + ']'); // 根据题目ID找到对应答题卡
    // 设置已答题
    if (!cardLi.hasClass('hasBeenAnswer')) {
        cardLi.addClass('hasBeenAnswer');
    }
}

//通过元素值找到元素在数组中的位置
function getIndex(arr, value) {
    var i = arr.length;
    while (i--) {
        if (arr[i] === value) {
            return i;
        }
    }
    return false;
}

function checkAnswer(event) {
    var dataType = $(event).attr("type-data");//data-type:1-判断，2-单选，3-多选，4-填空,5-客观题
    if (dataType == 4 || dataType == 5) {//因为填空题内容不能通过点击事件获取，所以如果是填空题，再转发
        clickAnswer(event);
    }

    if (event.value == "" || event.value == null) {
        var examId = $(event).closest('.test_content_nr_main').closest('li').attr('id'); // 得到题目ID
        var cardLi = $('a[href=#' + examId + ']'); // 根据题目ID找到对应答题卡
        // 设置已答题
        if (cardLi.hasClass('hasBeenAnswer')) {
            cardLi.removeClass('hasBeenAnswer')
        }
    }
}

function checkBoxAnswer(event) {
    if ($(":checkbox[name=" + event.name + "]:checked").size() == 0) {
        var examId = $(event).closest('.test_content_nr_main').closest('li').attr('id'); // 得到题目ID
        var cardLi = $('a[href=#' + examId + ']'); // 根据题目ID找到对应答题卡
        // 设置已答题
        if (cardLi.hasClass('hasBeenAnswer')) {
            cardLi.removeClass('hasBeenAnswer')
        }
    }
}


var render = {
    // 渲染历史答题记录和答题卡
    historyChoice: function () {
        var exam = store.get(examId);
        if (exam == undefined || exam == "{}" || exam == {} || exam.length == 0) {
            var flag = false;
            var param = {url: baseModule.examQuestionApi + '/history/' + examId, async: false};
            var request = ajax.get(param);
            request.success(function (d) {
                exam = d.result;
                flag = exam.flag;
            });
            if (!flag) {
                return false;
            }
        }
        //单选+判断
        var singleChoice = exam["singleChoice"];
        for (var questionId in singleChoice) {

            $("#" + singleChoice[questionId]).attr("checked", true);
            var cardLi = $('a[href=#' + questionId + ']'); // 根据题目ID找到对应答题卡
            // 设置已答题
            if (!cardLi.hasClass('hasBeenAnswer')) {
                cardLi.addClass('hasBeenAnswer');
            }
        }
        //多选
        var multiChoice = exam["multiChoice"];
        for (var questionId in multiChoice) {
            var checkAnswer = multiChoice[questionId];
            if (checkAnswer == undefined || checkAnswer.length == 0) {
                continue;
            }
            for (var i = 0; i < checkAnswer.length; i++) {
                $("#" + checkAnswer[i]).attr("checked", true)
            }

            var cardLi = $('a[href=#' + questionId + ']');
            if (!cardLi.hasClass('hasBeenAnswer')) {
                cardLi.addClass('hasBeenAnswer');
            }
        }

        //填空
        var gapChoice = exam["gapChoice"];
        for (var questionId in gapChoice) {
            var gapAnswers = gapChoice[questionId];
            var isFinish = true;//如果空格内容有一个是空串""，那么不渲染答题卡
            for (var optionId in gapAnswers) {
                var gapVal = gapAnswers[optionId];
                if (gapVal == undefined || gapVal == "") {
                    isFinish = false;
                }
                $("#" + optionId).val(gapVal);
            }
            // 还有的空为空，不渲染答题卡，标记为未完成
            if (isFinish) {
                var cardLi = $('a[href=#' + questionId + ']');
                if (!cardLi.hasClass('hasBeenAnswer')) {
                    cardLi.addClass('hasBeenAnswer');
                }
            }
        }


        //客观

        var subjective = exam["subjective"];
        for (var questionId in subjective) {
            var subjectiveVal = subjective[questionId];
            var isFinish = true;//如果空格内容有一个是空串""，那么不渲染答题卡
            if (subjectiveVal == undefined || subjectiveVal.trim() == "") {
                isFinish = false;
            }
            editor.set("#" + questionId, subjectiveVal);

            // 还有的空为空，不渲染答题卡，标记为未完成
            if (isFinish) {
                var cardLi = $('a[href=#' + questionId + ']');
                if (!cardLi.hasClass('hasBeenAnswer')) {
                    cardLi.addClass('hasBeenAnswer');
                }
            }
        }

    },
};



