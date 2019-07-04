var detailData = [], nationData = [], academyData = [], majorData = [], personalData = [], branchData = [];
var optId = '';
var index = 1, size = 12, key = '';


$(function () {
    if (checkAuth()) {
        return false;
    }
    personalBaseInfo.get();
    var user = store.get(cache_user_key);
    if (user != undefined) {
        var loginName = user.loginName;
        $('#login-name').html(loginName);
    }
});

var personalBaseInfo = {
    get: function () {
        var param = {url: baseModule.userDetailApi};
        var request = ajax.get(param);
        request.done(function (d) {
            personalData = d.userBaseInfo;
            detailData = d.userDetailInfo;
            render.page();
            personalBaseInfo.getNation();

            renderDetail.page();
            personalDetailInfo.getAcademy();
            personalBaseInfo.getMajorByAcademyCode();
            personalBaseInfo.getBranchByAcademyCode();

        })
    },
    getSyncByAcademyCode: function () {
        personalBaseInfo.getMajorByAcademyCode();
        personalBaseInfo.getBranchByAcademyCode();

    },

    getMajorByAcademyCode: function () {
        var academyCode = $('#academyCode').val();
        academyCode = academyCode || detailData['academyCode'];
        if (academyCode == undefined || academyCode == "")
            return;
        var param = {url: baseModule.majorInfoApi + "/majors/" + academyCode};
        var request = ajax.get(param);
        request.done(function (d) {
            majorData = d.result;
            renderDetail.majorSelect();
        })
    },

    getBranchByAcademyCode: function () {
        var academyCode = $('#academyCode').val();
        /* if(academyCode.length == 0){
             $("#majorCode").html("<option value='' selected='selected'>请选择专业</option>");
         }*/
        academyCode = academyCode || detailData['academyCode'];
        if (academyCode == undefined || academyCode == "")
            return;
        var param = {url: baseModule.branchInfoApi + "/academyCode/" + academyCode};
        var request = ajax.get(param);
        request.done(function (d) {
            branchData = d.result;
            renderDetail.branchSelect();
        })
    },

    getNation: function () {
        var param = {url: baseModule.nationInfoApi};
        var request = ajax.get(param);
        request.done(function (d) {
            nationData = d.result;
            render.nationSelect();
            $('#nationCode').val(personalData['nationCode']);
        })
    },


    update: function (event) {
        if (auth.refuse(event))
            return false;
        opt.update();
    },
}


var personalDetailInfo = {


    getAcademy: function () {
        var param = {url: baseModule.academyInfoApi};
        var reqest = ajax.get(param);
        reqest.done(function (d) {
            academyData = d.result;
            renderDetail.academySelect();
            if (detailData['academyCode'] != "")
                $('#academyCode').val(detailData['academyCode']);
        })

    },

    getMajor: function () {
        var academyCode = $('#academyCode').val();
        academyCode = academyCode || detailData['academyCode'];
        if (academyCode == undefined || academyCode == "")
            return;
        var param = {url: baseModule.majorInfoApi + "/majors/" + academyCode};
        var request = ajax.get(param);
        request.done(function (d) {
            majorData = d.result;
            renderDetail.majorSelect();
        })

    },
    update: function (event) {

        if (auth.refuse(event))
            return false;
        optDetail.update();
    },

}

var opt = {
    update: function () {

        var data = form.get("#opts-form");
        if (form.verify(data))
            return false;

        if ($('#nationCode').val() == "") {
            tips.warning("请选择民族");
            return false;
        }

        if (data['idCard'].length != 15 && data['idCard'].length != 18) {
            tips.warning("身份证长度不正确，请重新输入")
            return false;
        }

        data['id'] = optId;

        data['birthday'] = time.date2timestamp(data['birthday']) / 1000;
        // console.log(data)
        var param = {url: baseModule.userBaseInfoApi, data: data, async: false};
        var request = ajax.put(param);
        request.done(function (d) {
            optDetail.update();

        })
    },


}
var optDetail = {
    update: function () {
        var data = form.get("#opt-form");
        if (form.verify(data))
            return false;

        if ($('#academyCode').val() == "") {
            tips.warning("请选择学院");
            return false;
        }
        data['id'] = optId;

        if ($('#majorCode').val() == "") {
            tips.warning("请选择专业");
            return false;
        }

        // data['surveyTime'] = time.date2timestamp(data['surveyTime']);
        var param = {url: baseModule.userDetailInfoApi, data: data, async: false};
        var request = ajax.put(param);
        request.done(function (d) {
            tips.ok(d.message);
            //更新对象
            personalBaseInfo.get();
            var user = store.get(cache_user_key);
            if (user != undefined) {
                var loginName = user.loginName;
                $('#login-name').html(loginName);
            }
        })
    },
}

function allUpdate() {
    var isOk = verifyData();
    if (isOk) {
        opt.update();
    }
}

function verifyData() {
    var baseData = form.get("#opts-form");
    if (form.verify(baseData))
        return false;

    var DetailData = form.get("#opt-form");
    if (form.verify(DetailData))
        return false;

    return true;
}


// 渲染
var render = {
    page: function () {
        var template = doT.template($("#baseInfo-template").text());//获取的模板
        $('#personalBaseInfo').html(template(personalData));//模板装入数据
        $("#nationality").val(personalData['nationality'] || "");
        // personalBaseInfo.getNation();

        // $('#birthdayStr').val(time.timestamp2shortdate(personalData['birthday']));

        var number = personalData['number'];
        if (number != '') {
            $("#number").attr("readonly", true);
        }
        var idCard = personalData['idCard'];
        // console.log(idCard == "")
        if (idCard != null && idCard != '') {
            $("#idCard").attr("readonly", true);
        }
        if (personalData['birthday'] == null) {
            $('#birthdayStr').val("");
        } else {
            $('#birthdayStr').val(time.timestamp2shortdate(personalData['birthday']));
        }

        laydate.render({
            elem: "#birthdayStr"
        });
    },
    nationSelect: function () {
        var template = doT.template($("#nation-template").text());
        $('#nationCode').html(template(nationData));
    }
};

var renderDetail = {
    page: function () {
        var template = doT.template($("#detailInfo-template").text());
        $('#personalDetailInfo').html(template(detailData));
        $("#myClass").val(detailData['myClass'] || "");
        $("#studyStatus").val(detailData['studyStatus'] + "" || "");
        personalDetailInfo.getAcademy();
        // personalDetailInfo.getAcademy();
    },
    academySelect: function () {
        var template = doT.template($("#academy-template").text());
        $('#academyCode').html(template(academyData));
    },

    majorSelect: function () {
        var template = doT.template($("#major-template").text());
        $('#majorCode').html(template(majorData));

        if (detailData['majorCode']) {
            for (var i = 0; i < majorData.length; i++) {
                if (majorData[i].code == detailData['majorCode']) {
                    $('#majorCode').val(detailData['majorCode']);
                }
            }
        }
    },
    branchSelect: function () {
        var template = doT.template($("#depart-template").text());

        $('#departCode').html(template(branchData));

        if (detailData['departCode']) {
            for (var i = 0; i < branchData.length; i++) {
                if (branchData[i].code == detailData['departCode']) {
                    $('#departCode').val(detailData['departCode']);
                }
            }
        }
    },
}



