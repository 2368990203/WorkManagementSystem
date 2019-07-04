var type = "2";
var templateData = [];


$(function () {
    // if (checkAuth()) {
    //     return false;
    // }
    thoughReportTemplate.get();

    jQuery(document).ready(function ($) {
        $("a.jquery-word-export").click(function (event) {
            if(templateData.length == 0){
                console.log("暂无入党申请书模板可供下载");
                return false;
            }
            $("#template").wordExport("思想汇报");
        });

    });
});


var thoughReportTemplate = {
    get: function () {
        var param = {url: baseModule.templateApi + '/' + type};
        var request = ajax.get(param);
        request.done(function (d) {
            templateData = d.result;
            if (templateData.length != 0) {
                $('#template').html(templateData[0]['detail']);
            }
        })
    },

}