var examId = '';

$(function () {
    if (checkAuth()) {
    }

    $('body').scroll(function () {//开始监听滚动条
        //获取当前滚动条高度
        var topp = $('body').scrollTop();
        //用于调试 弹出当前滚动条高度
        //alert(topp);
        //判断如果滚动条大于90则弹出 "ok"
        if (topp > 105) {
            $('.nr_right_record').css({'top': '5px'});
        } else if (topp < 105) {
            $('.nr_right_record').css({'top': '105px'});
        }
    });
});


function getQueryString(key) {
    var reg = new RegExp("(^|&)" + key + "=([^&]*)(&|$)");
    var result = window.location.search.substr(1).match(reg);
    return result ? decodeURIComponent(result[2]) : null;
}

$(function () {
    //获取地址栏Id
    examId = getQueryString('examId');
    var param = {url: baseModule.examRecordApi + '/record/' + examId};
    var request = ajax.get(param);
    request.done(function (d) {
        //得分信息
        var candidate = d.result.candidate;
        $('#score-card').html(candidate[0]['score'] + "分");

        $('#test_title').html("<a href='/examList' style='color:#2B2B2B'>作业</a>" + "><a href='#' style='color:#2B2B2B'>" + candidate[0]['examName'] + "</a>>作业回顾");
        document.title = "作业>" + candidate[0]['examName'] + ">作业回顾";
        // 单选题
        var singleChoiceData = d.result.singleChoice;
        //以order值来排序题号
        if(singleChoiceData!=null&&singleChoiceData.length>0) {
            singleChoiceData = json.asc(singleChoiceData, "ordered");
        }
        for (var i=0; i < singleChoiceData.length; i++) {
            singleChoiceData[i]["options"] = json.asc(singleChoiceData[i]["options"],"ordered");
        }

        // 多选题
        var multipleChoiceData = d.result.multipleChoice;
        if(multipleChoiceData!=null&&multipleChoiceData.length>0) {
            multipleChoiceData = json.asc(multipleChoiceData, "ordered");
        }

        for (var i=0; i <  multipleChoiceData.length; i++) {
            multipleChoiceData[i]["options"] = json.asc(multipleChoiceData[i]["options"],"ordered");
        }

        //判断题
        var trueOrFalseData = d.result.trueOrFalse;
        if(trueOrFalseData!=null&&trueOrFalseData.length>0) {
            trueOrFalseData = json.asc(trueOrFalseData, "ordered");
        }

        //填空题
        var gapFillingData = d.result.gapFilling;
        if(gapFillingData!=null&&gapFillingData.length>0){
            gapFillingData = json.asc(gapFillingData,"ordered");
        }
        if(singleChoiceData.length+multipleChoiceData.length+trueOrFalseData.length+gapFillingData.length==0){
            tips.info("考试回顾获取异常，请尝试刷新网页更新缓存！")
        }

        //客观题
        var subjectiveData = d.result.subjective;
        if (subjectiveData != null && subjectiveData.length > 0) {
            subjectiveData = json.asc(subjectiveData, "order");
        }

        var template = doT.template($("#single-template").text());
        $('#single-choice').html(template(singleChoiceData));
        template = doT.template($("#single-card-template").text());
        $('#single-card').html(template(singleChoiceData));


        template = doT.template($("#multiple-template").text());
        $('#multiple-choice').html(template(multipleChoiceData));
        template = doT.template($("#multiple-card-template").text());
        $('#multiple-card').html(template(multipleChoiceData));


        template = doT.template($("#true-or-false-template").text());
        $('#true-or-false').html(template(trueOrFalseData));

        template = doT.template($("#true-or-false-card-template").text());
        $('#true-or-false-card').html(template(trueOrFalseData));


        template = doT.template($("#gap-filling-template").text());
        $('#gap-filling').html(template(gapFillingData));

        template = doT.template($("#gap-filling-card-template").text());
        $('#gap-filling-card').html(template(gapFillingData));


        template = doT.template($("#subjective-template").text());
        $('#subjective').html(template(subjectiveData));

        template = doT.template($("#subjective-card-template").text());
        console.log(subjectiveData)
        $('#subjective-card').html(template(subjectiveData));
    })
});
