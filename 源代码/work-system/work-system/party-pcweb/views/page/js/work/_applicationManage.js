var type = "1", newFileName = '', fileURL = '', lastFileURL = '', fileURL = '';
var firstApprovalData = [], recheckApprovalData = [], applicationData = [], pictureData = [];
var newFileName = '', imageId = '';
var docSuffixs = ["doc", "docx", "pdf", "wps"];
var imgSuffixs = ["png", "jpg", "jpeg", "bmp"];

$(function () {
    if (checkAuth()) {
        return false;
    }
    applicationManage.getLinkUser();
    applicationManage.get();
    cos.init();



});


var applicationManage = {

    getLinkUser: function () {
        var param = {url: baseModule.linkUserApi};
        var request = ajax.get(param);
        request.done(function (d) {
            var result = d.result;
            var flag = result.flag;
            var data = result.data;
            //初次打开界面判断

            if (flag) {
                $("#enter-btn").attr("style", "display:none");
                $("#link-number").val(data.linkNumber);
                $("#link-number").attr("readonly", true);
            } else {
                if (!window.sessionStorage.getItem('storge')) {
                    window.sessionStorage.setItem('storge', 'true');
                    window.location.href = 'applicationTemplate';
                } else {
                    $("#link-user").fadeIn();
                }
            }

    });
    },

    get: function () {
        var param = {url: baseModule.applicationApi};
        var request = ajax.get(param);
        request.done(function (d) {
            var resultData = d.result;
            var flag = resultData.flag;
            if (flag) {
                firstApprovalData = resultData.firstApproval;
                recheckApprovalData = resultData.recheckApproval;
                applicationData = resultData.application;
                pictureData = resultData.picture;

                // lastFileURL = applicationData.lastFileURL,
                //     $("#lastFile").attr("href", lastFileURL);
                fileURL = applicationData.fileURL,
                    $("#lastFile").attr("href", fileURL);
                render.page();
            }

        });
    },

    downloadFile: function (event) {

        // var link = document.createElement('a');
        // link.setAttribute("download", "");
        // link.href = lastFileURL;
        // link.click();

    },

    uploadFile: function (event) {
        $("#submitFile").click();
    },

    selectFileChange: function () {
        var file = $("#submitFile").val();
        var pos = file.lastIndexOf("\\");
        var fileName = file.substring(pos + 1);

        $("#fileName").text(fileName);

        $("#submitBtn").attr("style", "display:block");

    },
    uploadFileChange: function () {

        var _file = $("#submitFile")[0].files[0];
        newFileName = _file.name;
        if (docSuffixs.indexOf(getSuffix(newFileName.toLowerCase())) < 0) {
            tips.warning("请上传正确的文件格式:" + docSuffixs.toString())
            return false;
        }

        applicationManage.loading(true);

        newFileName = getSuffix(newFileName) + new Date().getTime() + "." + getSuffix(newFileName);//修改文件名

        var path = 'partySystem/picture/' + newFileName;

        cos.uploadFile(path, _file, uploadFileSuccess);
    },

    clickPicBtn: function (picId) {
        $("#" + picId).click();
    },
    isConfirm: function () {
        tips.confirm({
            message: "提交后不可再次上传图片，确定吗?",
            enter: "提交",
            color: "#389fc3",
            fun: "applicationManage.confirmRecheck()"
        });
    },

    confirmRecheck: function () {
        var param = {url: baseModule.applicationApi + "/isConfirm/" + recheckApprovalData.id};
        var request = ajax.post(param);
        request.done(function (d) {
            tips.ok(d.message);
            applicationManage.get();
        })
    },


    uploadPic: function (picId) {


        var _file = $("#" + picId)[0].files[0];

        newFileName = _file.name;

        if (imgSuffixs.indexOf(getSuffix(newFileName.toLowerCase())) < 0) {
            tips.warning("请上传正确的图片格式:" + imgSuffixs.toString())
            return false;
        }


        newFileName = getSuffix(newFileName) + new Date().getTime() + "." + getSuffix(newFileName);//修改文件名


        // var path = 'partySystem/' + newFile.name;
        var path = 'partySystem/upload/picture/' + newFileName;

        imageId = picId;
        cos.uploadFile(path, _file, this.uploadImageSuccess);

    },
    uploadImageSuccess: function (result) {
        loading.hide();
        var data = result.data;
        var access_url = data.source_url.replace("http:", "").replace("https:", "");
        var path = data.resource_path;

        // videoData['url'] = access_url;

        $('#' + imageId + 'Show').attr('src', access_url);
        $('#' + imageId + 'Show').attr('data-path', cos.fileName(access_url));
        $('#' + imageId + 'Show').attr('style', "display:block;float: left;");

        var pictureData = {};

        //tableId是复审记录的id,问题;第一次提交图片时的复审记录还没有
        //解决方案：1-初审通过后后台直接插入一条复审记录[使用]
        //          2-提交图片前先进行查询复审记录，没有则创建，有则跳过[舍弃]
        pictureData['tableId'] = recheckApprovalData.id;
        pictureData['path'] = path;
        pictureData['name'] = newFileName;
        pictureData['artworkURL'] = access_url;
        pictureData['describe'] = "入党申请书复审照片";

        var param = {url: toolModule.pictureApi, data: pictureData};
        var request = ajax.post(param);
        request.done(function (d) {
            applicationManage.get();
            // $("#picId").val(d.result.artworkURL);
        })
    },


    checkFirstOpinion: function (event) {
        $("#opinionMessage").html(firstApprovalData.opinion || "意见未填写");
        <!--初审意见弹窗js-->
        $(".btn_opinon").click(function () {
            $("#opinionModal").fadeIn();
        });
        $(".close").click(function () {
            $("#opinionModal").fadeOut();
        });

    },

    checkSecodOpinion: function (event) {
        $("#opinionMessage").html(recheckApprovalData.opinion || "意见未填写");
        <!--初审意见弹窗js-->
        $(".btn_opinon").click(function () {
            $("#opinionModal").fadeIn();
        });
        $(".close").click(function () {
            $("#opinionModal").fadeOut();
        });
    },

    loading: function (wait) {
        if (wait) {
            $('#uploadText').html("上传中...");
        } else {
            $('#uploadText').html("重新上传");
        }
    },
    deletePic: function (event) {

        if (recheckApprovalData.result == 1) {
            //已审核完毕，限制删除
            return false;
        }

        var deleteId = $(event).attr("data-id");

        var param = {url: toolModule.pictureApi + "/" + deleteId};
        var request = ajax.delete(param);
        request.done(function (d) {
            applicationManage.get();
            // $("#picId").val(d.result.artworkURL);
        })
    },
    writeLinkUser: function () {
        $("#link-user").fadeIn();
    },

    linkUser: function (event) {
        var userTips = $("#tips").html();
        if (userTips.length != 0) {
            tips.error(userTips);
            return false;
        }
        var linkNumber = $("#link-number").val().trim();

        var data = {"linkNumber": linkNumber};
        var param = {url: baseModule.linkUserApi, data: data};
        var request = ajax.post(param);
        request.done(function (d) {
            tips.ok(d.message);
            $("#link-user").fadeOut();
            applicationManage.getLinkUser();
            applicationManage.get();
        })
    },

};


var uploadFileSuccess = function (result) {

    $("#submitBtn").attr("style", "display:none");
    // loading.hide();
    var data = result.data;
    var access_url = data.source_url.replace("http:", "").replace("https:", "");

    var docData = {};

    docData['fileURL'] = access_url;


    var param = {url: baseModule.applicationApi, data: docData};
    var request = ajax.post(param);
    request.done(function (d) {
        applicationManage.loading(false);
        tips.ok(d.message);
        applicationManage.get();
    });
};


var render = {
    page: function () {
        // $('#oldFileText').html(applicationData.content);
        // $('#firstOpinionText').html(firstApprovalData.opinion || "");

        //判断申请书现在到了哪个阶段，按流程给用户显示哪些可用功能
        if (firstApprovalData !== {}) {

            // $("#lastFile").attr("display","block");

            $("#lastFile").html("<button onclick=\"applicationManage.downloadFile(this)\">下载</button>");

            $("#uploadText").html('重新上传');
            tool.setImg("wordsPic", "finish.png");
            tool.setSchedule("wordsSchedule", "connecting");
            tool.setImg("firstPic", "underway.png");
            // tool.setSchedule("firstCheckSchedule","connecting");
            if (firstApprovalData.status === 1) {//1-待审/未审
                $("#status-div").attr("style", 'display:block');
                $("#status").text(helper.status(firstApprovalData.status));
            }
            if (firstApprovalData.status === 2) {//2-已审

                //显示审核状态
                $("#status-div").attr("style", 'display:block');
                $("#status").text(helper.status(firstApprovalData.status));

                //显示审核结果
                var result = helper.result(firstApprovalData.result);
                $("#result-div").attr("style", 'display:block');
                $("#result").text(result);

                // $("#result").html("");
                //显示初审审核意见按钮
                $("#firstOpinion").attr("style", 'display:block');
                //显示初审意见
                // $("#firstOpnionMessage").text(firstApprovalData.opinion || "意见未填写");

                if (firstApprovalData.result == 1) {
                    //初审标记打勾, 初审结果通过才打勾

                    // $("#uploadText").attr("disabled","disabled");
                    $("#uploadText").attr("style", "display:none");

                    tool.setImg("firstPic", "finish.png");

                    //亮线：初审-提交完成
                    tool.setSchedule("firstCheckSchedule", "connecting");
                    //显示照片“上传”的按钮
                    $("#selectPicBtn").attr("style", "display:block");

                    if (pictureData.length == 0) {
                        tool.setImg("writePic", "underway.png");
                    } else if (pictureData.length != 0) {
                        $("#confirmRecheck").attr("style", "display:block");
                        tool.setImg("writePic", "finish.png");
                        tool.setImg("recheckPic", "underway.png");
                        // $("#recheckResult").attr("style", 'display:block');
                        //循环渲染图片
                        render.images();

                        if (recheckApprovalData.isConfirm == 2) {
                            $("#confirmRecheck").attr("style", "display:none");
                            $("#selectPicBtn").attr("style", "display:none");
                            //移除图片右上角的删除
                            $(".imgDeleteBtn").remove();
                        }


                        //有图片说明已经提交审核，显示审核状态域
                        tool.setSchedule("picSchedule", "connecting");
                        $("#recheckStatus-div").attr("style", "display:block");
                        //显示状态值
                        $("#recheckStatus").html(helper.status(recheckApprovalData.status));

                        if (recheckApprovalData.status == 2) {
                            //显示审核状态
                            $("#recheck-result-div").attr("style", 'display:block');
                            $("#result-div").attr("style", 'display:block');
                            // console.log(recheckApprovalData.result)
                            $("#recheckResult").text(helper.result(recheckApprovalData.result));

                            if (recheckApprovalData.status == 2) {
                                $("#recheckBtn").attr("style", "display: block");
                                if (recheckApprovalData.result == 1) {
                                    tool.setImg("recheckPic", "finish.png");
                                    tool.setSchedule("picSchedule", "connecting");
                                    tool.setSchedule("recheckSchedule", "connecting");
                                    //复审图片上传按钮隐藏
                                    $("#selectPicBtn").attr("style", "display:none");
                                    //移除图片右上角的删除
                                    $(".imgDeleteBtn").remove();

                                    //亮：提交完成 ，上传照片通过才亮
                                    tool.setImg("finishAll", "finish.png");
                                }
                            }
                        }
                    }


                }


            }
        }
    },

    images: function () {
        var template = doT.template($("#img-list-template").text());
        $('#imgs').html(template(pictureData));

        for (var i = 0; i < pictureData.length; i++) {
            $('#applicationImg' + i).fancybox({
                helpers: {
                    title: {
                        type: 'over'
                    }
                }
            });
        }
    },

    alwaysShowImg: function () {

        for (var i = 0; i < pictureData.length; i++) {
            $('#applicationImg' + i).attr("style", "display:block;");
        }
        this.images();
        //如复审已通过，移除图片的删除按钮
        if (recheckApprovalData.result == 1 || recheckApprovalData.isConfirm == 2) {
            $(".imgDeleteBtn").remove();
        }
    }

}

var tool = {
    setImg: function (_id, img) {
        $("#" + _id).attr("src", 'page/images/' + img);
    },

    setSchedule: function (_id, connecting) {
        $("#" + _id).attr("class", connecting);
    }
};


var helper = {
    result: function (_result) {
        switch (parseInt(_result)) {
            case 1:
                return "通过";
            case 2:
                return "不通过";
            default:
                return "";
        }
    },

    status: function (_status) {
        switch (parseInt(_status)) {
            case 1:
                return "未审核";
            case 2:
                return "已审核";
            default:
                return "待审核";
        }
    }
};
