var categoryData = [],allData = [];
var superId = 0,categoryCode = '';
var categorySelectData = [],selectParentData;
var config = {
    form:'../../form/_category.html',
    title:'基础数据管理'
};

$(function () {
    if(auth.check(this)) {
        categoryCode = url.getUrlParam('code');
        //自适应
        view.initHeight();
        $(window).resize(function () {
            view.initHeight();
        });
        category.get();
    }
})

//业务相关
var category = {
    get:function () {
        var param = {url:globalModule.categoryApi + '/'+categoryCode};
        var request = ajax.get(param);
        request.done(function (d){
            if((typeof d.result.data) != 'undefined'){
                categoryData = d.result.data;
                superId = d.result.id;
                categorySelectData = [{"id":superId,"name":"父级"}];
                for(var i=0;i<categoryData.length;i++){
                    categorySelectData.push(categoryData[i]);
                }
                render.category();
                opt.format();
            }
        })
    },
    create:function (event) {
        if(auth.refuse(event))
            return false;
        openLay({url:config.form,fun:'opt.create();',title:config.title});
        if(getData(event,'data-type') == 2){
            optId = getId(event);
            var tempData = [];
            tempData.push(opt.getById());
            render.categorySelectData(tempData);
        }else {
            render.categorySelectData(categorySelectData);
        }
    },
    update:function (event) {
        if(auth.refuse(event))
            return false;
        optId = getId(event);
        openLay({url:config.form,fun:'opt.update();',title:config.title});
        form.set(opt.getById());
        $('#category-select-view').hide();
    },
    delete:function (event) {
        if(auth.refuse(event))
            return false;
        optId = getId(event);
        tips.confirm({message:'是否要删除这条数据？',fun:"opt.delete();"});
    }
}
//操作相关
var opt = {
    create:function () {
        var data = form.get("#opt-form");
        if(form.verify(data))
            return false;
        data['superId'] = superId;
        var param = {url:globalModule.categoryApi,data:data};
        var request = ajax.post(param);
        request.done(function (d) {
            tips.done(d);
            category.get();
        })
    },
    update:function () {
        var data = form.get("#opt-form");
        data['superId'] = superId;
        data['id'] = optId;
        var param = {url:globalModule.categoryApi,data:data};
        var request = ajax.put(param);
        request.done(function (d) {
            tips.ok(d.message);
            closeLay();
            category.get();
        })
    },
    delete:function () {
        var request = ajax.delete({url:globalModule.categoryApi+'/'+optId});
        request.done(function (d) {
            tips.ok(d.message);
            category.get();
        })
    },
    format:function () {
        //分3次读取数据
        for(var i=0;i<categoryData.length;i++){
            var d1st = {
                "id":categoryData[i].id,
                "name":categoryData[i].name,
                "code":categoryData[i].code,
                "parentId":categoryData[i].parentId
            };
            allData.push(d1st);
            var s1st = categoryData[i].sub;
            for(var j=0;j<s1st.length;j++){
                var d2nd = {
                    "id":s1st[j].id,
                    "name":s1st[j].name,
                    "code":s1st[j].code,
                    "parentId":s1st[j].parentId
                };
                allData.push(d2nd);
                var s2nd = s1st[j].sub;
                for(var k=0;k<s2nd.length;k++){
                    var d3th = {
                        "id":s2nd[k].id,
                        "name":s2nd[k].name,
                        "code":s2nd[k].code,
                        "parentId":s2nd[k].parentId
                    };
                    allData.push(d3th);
                }
            }
        }
    },
    getById:function () {
        var model = {};
        for(var i=0;i<allData.length;i++){
            if(allData[i].id == optId){
                model = allData[i];
                break;
            }
        }
        return model;
    }
}
//数据渲染
var render = {
    category:function () {
        var tmpl = doT.template($("#category-tmpl").text());
        $('#data-view-list').html(tmpl(categoryData));
        render.tree();
    },
    categorySelectData:function (_data) {
        var tmpl = doT.template($("#category-select-tmpl").text());
        $('#category-parent').html(tmpl(_data));
    },
    tree:function () {
        $('.tree-folder-header .tree-folder-name').click(function () {
            var status = getData(this,'data-status');
            var nextObj = $(this).parent().next(".tree-folder-sub");
            if(status == 0){
                $(this).find('i').removeClass('fa-angle-double-down');
                $(this).find('i').addClass('fa-angle-double-up');
                $(this).attr('data-status',1);
                nextObj.hide();
            }else{
                $(this).find('i').removeClass('fa-angle-double-up');
                $(this).find('i').addClass('fa-angle-double-down');
                $(this).attr('data-status',0);
                nextObj.show();
            }
        })
    }
}
//视图界面
var view = {
    initHeight:function () {
        $('#data-view').css('height',(parent.adaptable().h)-80);
    }
}


