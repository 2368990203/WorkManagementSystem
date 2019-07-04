var type = "2", index = '1', size = '6';
var thoughReportData = [];
var docSuffixs = ["doc", "docx", "pdf", "wps"];


$(function () {
    if (checkAuth()) {
        return false;
    }
    thoughReportList.getLinkUser();
    thoughReportList.get(index, size);
    cos.init();

});


var thoughReportList = {
        getLinkUser: function () {
            var param = {url: baseModule.linkUserApi};
            var request = ajax.get(param);
            request.done(function (d) {
                var result = d.result;
                var flag = result.flag;
                var data = result.data;

                if (flag) {
                    $("#enter-btn").attr("style", "display:none");
                    $("#link-number").val(data.linkNumber);
                    $("#link-number").attr("readonly", true);
                } else {
                    if (!window.sessionStorage.getItem('storge_1')) {
                        window.sessionStorage.setItem('storge_1', 'true');
                        window.location.href = 'thoughReportTemplate';
                    }else{
                        $("#link-user").fadeIn();
                    }
                }
            });
        },

        get: function (index, size) {
            var param = {url: baseModule.reportApi + '/list/' + index + '-' + size};
            var request = ajax.get(param);
            request.done(function (d) {
                var resultData = d.result;
                thoughReportData = resultData.data;
                // console.log(thoughReportData);
                render.list();
                if (thoughReportData.length != 0 && d.result.totalPage > 1) {
                    //currentPage:当前所在第几页;pageSize:每页多少条数据;totalPage:总共多少页;totalCount:总共多少条数据
                    page.init(d.result.currentPage, d.result.pageSize, d.result.totalPage, d.result.totalCount);
                }else {
                    $('#pagination').empty();
                }
            })
        },

        createReport: function () {

            var _file = $("#submitFile")[0].files[0];
            var newFileName = _file.name;

            if (docSuffixs.indexOf(getSuffix(newFileName.toLowerCase())) < 0) {
                tips.warning("请上传正确的文件格式:" + docSuffixs.toString())
                return false;
            }

            newFileName = getSuffix(newFileName) + new Date().getTime() + "." + getSuffix(newFileName);//修改文件名


            var path = 'partySystem/upload/doc/' + newFileName;

            cos.uploadFile(path, _file, this.uploadFileSuccess);

        },

        uploadFileSuccess: function (result) {
            var data = result.data;
            var access_url = data.source_url.replace("http:", "").replace("https:", "");

            var data = {};
            data['type'] = $("#type").val();
            data['fileURL'] = access_url;
            // console.log(data);

            var param = {url: baseModule.reportApi + "/firstCreate", data: data};
            var request = ajax.post(param);
            request.done(function (d) {
                tips.ok(d.message);
                $('#create-report').fadeOut();
                thoughReportList.get(1, size);
            });
        },

        selectFile: function () {
            $("#submitFile").click();
        },

        changeFile: function () {
            var file = $("#submitFile").val();
            var pos = file.lastIndexOf("\\");
            var fileName = file.substring(pos + 1);

            $("#fileName").text(fileName);

            $("#submitBtn").attr("style", "display:block");
        }
        ,
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
                thoughReportList.getLinkUser();
                thoughReportList.get();
            })
        },
    }
;


var render = {
    list: function () {
        var template = doT.template($("#report-list-template").text());
        $('#report-list').html(template(thoughReportData));
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
                return "未出结果";
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
    },

    approvalStage: function (_approvalStage) {
        switch (parseInt(_approvalStage)) {
            case 1:
                return "初审";
            case 2:
                return "复审";
            default:
                return "";
        }
    }
};


// 5分页
var page = {
    init: function (_currentPage, _pageSize, _totalPage, _totalCount) {
        new myPagination({
            id: 'pagination',
            curPage: _currentPage, //当前页码
            pageTotal: _totalPage === 0 ? 1 : _currentPage, //总页数,总页数为0时显示1页
            pageAmount: _pageSize,  //每页多少条
            dataTotal: _totalCount, //总共多少条数据
            // pageSize: size, //可选,分页个数
            //showPageTotalFlag:true, //是否显示数据统计
            showSkipInputFlag: true, //是否支持跳转
            getPage: function (page) {
                //获取当前页数
                index = page;
                thoughReportList.get(index, size);
            }
        })
    }
}
