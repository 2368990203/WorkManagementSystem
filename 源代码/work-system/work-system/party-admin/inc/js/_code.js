var codeData;
var tableName;
var code = {
    create:function (event) {

     openLay({url: '../../form/_code.html', fun: 'opt.create();', title: '后台代码生成'});

        tableName = getData(event,'data-name');

    },
    get:function () {
        var param = {url:otherModule.codeApi};
        var request = ajax.get(param);
        request.done(function (d) {
            codeData = d;
            renderCode(d);
        })
    },
    info:function (event) {
        var index = getData(event,'data-index');
        var type =  getData(event,'data-type');
        var obj = codeData[index].code;
        try{
            var codeHtml = '<pre>'+obj[type]+'</pre>';

            var config = {
                title:'初始['+type+']代码',
                html:codeHtml,
                hideEnter:true
            }
            openInfoLay(config);
        }catch (e){
            tips.warning('请重新生成代码')
        }
    }
}
var opt = {
    create: function () {
        var data = form.get("#opt-form");
        if (form.verify(data))
            return false;
        console.log(data);
        

        var param = {url:otherModule.codeApi+'/'+tableName+'-'+data.type};
        var request = ajax.post(param);
        request.done(function (d) {
            tips.ok(d.message);
            code.get();
            closeLay();

        })

    },

}

$(function () {
    if(auth.check(this)) {
        code.get();
        initHeight();
        $(window).resize(function () {
            initHeight();
        });
    }
});
function initHeight() {
    $('#code-view-list').css('height',(parent.adaptable().h)-33);
}
function renderCode(_data){
    var tmpl = doT.template($('#code-tmpl').text());
    $('#code-list').html(tmpl(_data));
}
