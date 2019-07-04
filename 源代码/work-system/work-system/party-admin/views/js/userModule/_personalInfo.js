var detailData = [], nationData = [], academyData = [], majorData = [], branchData = [], personalData = [];
var optId = '';
var index = 1, size = 12, key = '';
var config = {
    title: '查看个人信息',
}


$(function () {
    //自适应
    if (auth.check(this)) {
        view.initHeight();
        $(window).resize(function () {
            view.initHeight();
        });
        personalBaseInfo.get();
    }
});

var personalBaseInfo = {
    get: function () {
        var param = {url: userModule.userInfoApi + "/personal"};
        var request = ajax.get(param);
        request.done(function (d) {
            personalData = d.result;
            console.log(personalData);
            render.page()
        })
    },

    update: function (event) {
        if (auth.refuse(event))
            return false;
        opt.update();
    },

};


// 渲染
var render = {
    page: function () {
        var template = doT.template($("#baseInfo-template").text());//获取的模板
        $('#personalBaseInfo').html(template(personalData));//模板装入数据
    },

};


var helper = {
    sex: function (_sex) {
        switch (parseInt(_sex)) {
            case 1:
                return "女";
            case 2:
                return "男";
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
};


