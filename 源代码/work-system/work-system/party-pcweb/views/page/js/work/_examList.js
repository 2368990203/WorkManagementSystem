var index = 1, size = 8, examListData = [];
var it = {};


$(function () {
    if (checkAuth()) {
        return false;
    }

    examList.get(index, size);
});


var examList = {
    get: function (index, size) {
        var param = {url: baseModule.examCandidateApi + '/' + index + '-' + size};
        var request = ajax.get(param);
        request.done(function (d) {
            examListData = d.result.data;
            console.log(examListData);
            render.list();
            if (examListData.length != 0 && d.result.totalPage > 1) {
                //currentPage:当前所在第几页;pageSize:每页多少条数据;totalPage:总共多少页;totalCount:总共多少条数据
                page.init(d.result.currentPage, d.result.pageSize, d.result.totalPage, d.result.totalCount);
            } else {
                $('#pagination').empty();
            }
        })
    },

    redirectExamRecord: function (event) {
        var optId = $(event).attr('data-id');
        var status = $(event).attr('data-status');
        // if (parseInt(status) != 2) {
        //     tips.info("请教师阅卷公布成绩后再查看试题解析。");
        //     return false;
        // }fixme 20190630
        window.location.href = 'examRecord?examId=' + optId;
    },
    checkCode: function (event) {
        var examId = $(event).attr('examId');
        var isUnseal = $(event).attr('isUnseal');
        var roomId = $(event).attr('roomId');
        // debugger;
        // examCodeApi
        if (isUnseal != undefined && isUnseal == 1) {
            tips.code({roomId: roomId, examId: examId});
        } else {
            window.location.href = 'examTips?examId=' + examId;
        }

    }

};

function startExam(event) {
    alert("测试");
    var id = $(event).attr('data-id');
    location.href = server_root + "exam?examId=" + id;
}


var render = {
    list: function () {
        var template = doT.template($("#exam-list-template").text());
        $('#exam-list').html(template(examListData));
    }
};


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
                examList.get(index, size);
            }
        })
    }
}

var helper = {
    examStatus: function (_examStatus) {
        switch (parseInt(_examStatus)) {
            case 0:
                return "未开始";
            case 1:
                return "进行中";
            case 2:
                return "已结束";
        }
    },

}
