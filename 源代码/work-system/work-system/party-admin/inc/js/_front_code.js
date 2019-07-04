var codeData;
var tableName;

var generate = {
    form: '../../form/_front_code.html',//表单地址
    title: '前端代码管理',
}

$(function () {
    if(auth.check(this)) {
        frontCode.get();
        initHeight();
        $(window).resize(function () {
            initHeight();
        });
    }
});
var frontCode = {
    create: function (event) {
        tableName = getData(event, 'data-name');
        // if (auth.refuse(event))
        //     return false;
        // optId = getId(event);//获取当前id的值
        openLay({url: generate.form, fun: 'opt.create();', title: generate.title});
        $('#tableName').val(tableName);
    },
    get: function () {
        var param = {url: otherModule.frontCodeApi};
        var request = ajax.get(param);
        request.done(function (d) {
            codeData = d;
            renderCode(d);
        })
    },
    info: function (event) {
        var index = getData(event, 'data-index');
        var type = getData(event, 'data-type');
        var obj = codeData[index].code;
        try {
            var codeHtml = '<xmp>' + obj[type] + '</xmp>';

            var config = {
                title: '初始[' + type + ']代码',
                html: codeHtml,
                hideEnter: true
            };
            openInfoLay(config);
        } catch (e) {
            tips.warning('请重新生成代码')
        }
    },
    changeCheck: function (event) {
        var name = getData(event, 'name');
        var checked = getData(event, 'checked');

        var pathName = "";
        var pathValue = "";
        if (name == "htmlSelect") {
            pathName = "htmlPath";
            pathValue = "/party-admin/views/test/form/"
        }
        else if (name == "jsSelect") {
            pathName = "jsPath";
            pathValue = "/party-admin/views/test/js/";
        }
        else if (name == "ejsSelect") {
            pathName = "ejsPath";
            pathValue = "/party-admin/views/test/ejs/"
        }


        if (checked == "checked") {
            $("input:checkbox[name=" + name + "]").attr("checked", false);
            $("#" + pathName).attr("readonly", true);
            $("#" + pathName).val("");
        } else {
            $("input:checkbox[name=" + name + "]").attr("checked", true);
            $("#" + pathName).attr("readonly", false);
            $("#" + pathName).val(pathValue);
        }
    }
};


var opt = {
    create: function () {
        var data = form.get("#opt-form");
        if (form.verify(data))
            return false;
        data['table'] = tableName;
        console.log(data);
        var param = {url: otherModule.frontCodeApi, data: data};
        var request = ajax.post(param);
        request.done(function (d) {
            tips.ok(d.message);
            frontCode.get();
            closeLay();
        })
    },
    close: function () {
        closeLay();
    }
}

function initHeight() {
    $('#code-view-list').css('height', (parent.adaptable().h) - 33);
}

function renderCode(_data) {
    var tmpl = doT.template($('#code-tmpl').text());
    $('#code-list').html(tmpl(_data));
}
