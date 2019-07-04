var type = "1";
var templateData = [];


$(function () {


    applicationTemplate.get();

    jQuery(document).ready(function ($) {
        $("a.jquery-word-export").click(function (event) {
            if(templateData.length == 0){
                    return false;
            }
            $("#template").wordExport("入党申请书");
        });

    });

    function down(event) {
        if(templateData.length == 0){
               return false;
        }
        $("#template").wordExport("入党申请书");
    }
});


var applicationTemplate = {
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