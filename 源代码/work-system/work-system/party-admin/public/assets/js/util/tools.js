

var server_root =getRootPath();

function getRootPath() {

    var host = window.location.host;
    var origin=window.location.origin;
    var root = '/';
    if (!origin) {
        origin = window.location.protocol + "//" + window.location.hostname + (window.location.port ? ':' + window.location.port: '');
    }
    switch (host) {
        case "gxmddj.gxun.edu.cn":
        case "10.240.16.116":
        case "mengwp.gxun.edu.cn":
            root =origin+ '/party-admin/';
            break;
        default:
            root = origin+'/';
            break;
    }

    return root;
}


/**
 * url处理函数
 * */



var url = {
    getUrlParam: function (_key) {
        var reg = new RegExp("(^|&)" + _key + "=([^&]*)(&|$)"); // 构造一个含有目标参数的正则表达式对象
        var r = window.location.search.substr(1).match(reg); // 匹配目标参数
        if (r != null)
            return unescape(r[2]);
        return ""; // 返回参数值
    }
}

/**
 * 时间日期处理函数
 * */
var time = {
    // 日期格式转时间戳
    date2timestamp: function (_date) {
        var timestamp = Date.parse(new Date(_date.replace(/-/g, '/')));
        return timestamp / 1000;
    },

    //只有年月日
    timestamp2shortdate: function (_date) {
        var date = new Date(parseInt(_date) * 1000);
        var short_date = date.getFullYear() + "-" + (date.getMonth() + 1) + "-" + date.getDate();
        return short_date;
    },
    //
    timestamp2time: function (_timestamp) {
        if (_timestamp > 0) {
            var d = new Date(_timestamp * 1000);    // 根据时间戳生成的时间对象
            var date = (d.getFullYear()) + "-" +
                string.time((d.getMonth() + 1)) + "-" +
                string.time(d.getDate()) + " " +
                string.time(d.getHours()) + ":" +
                string.time(d.getMinutes()) + ":" +
                string.time(d.getSeconds());
            return date;
        } else {
            return '';
        }
    },
    checkDate: function (_date) {
        var result = _date.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/);
        if (result == null)
            return false;
        return true;
    }
}

/**
 * form处理函数
 * */
var form = {
    disabled: function () {
        $('input').attr('disabled', 'disabled');
        $('select').attr('disabled', 'disabled');
        $('textarea').attr('disabled', 'disabled');

    },
    readonly: function () {
        $('input').attr('readonly', 'readonly');
        $('select').attr('readonly', 'readonly');
        $('textarea').attr('readonly', 'readonly');

    },
    get: function (_form) {
        var obj = $(_form).serializeArray();
        return form.convertArray(obj);
    },
    convertArray: function (_obj) {
        var v = {};
        for (var i in _obj) {
            if (_obj[i].name != '__VIEWSTATE') {
                if (typeof (v[_obj[i].name]) == 'undefined')
                    v[_obj[i].name] = $.trim(_obj[i].value);
                else
                    v[_obj[i].name] += "," + $.trim(_obj[i].value);
            }
        }
        return v;
    },
    /**
     *  重写校验方法，能校验input的值是否符合要求
     * @param _object
     * @returns {boolean}
     */
    verify: function (_object) {
        var v = false;
        for (var o in _object) {
            var obj1 = $("form input[name='" + o + "']");
            var obj2 = $("form select[name='" + o + "']");
            var obj3 = $("form textarea[name='" + o + "']");
            var obj;
            if(obj1.length>0){
                obj=obj1;
            }
            if(obj2.length>0){
                obj=obj2;
            }
            if(obj3.length>0){
                obj=obj3;
            }

            var required = obj.attr('required');
            var required2 = obj2.attr('required');
            var required3 = obj3.attr('required');


            if (required == 'required'||required2 == 'required'||required3 == 'required') {
                if (_object[o].length == 0) {
                    tips.warning(obj.attr('placeholder'));
                    obj.focus();
                    v = true;
                    break;
                }
            }

            if (_object[o].length != 0) {
                v =  !checkRegular(obj[0],true);
                if(v){
                    break;
                }
            }


        }
        return v;
    },
    /**
     *  重写校验方法，能同时校验input的空值和select没有选数值
     * @param _object
     * @returns {boolean}
     */
    verifyPlus: function (_object) {
        var v = false;
        for (var o in _object) {
            var obj = $("form input[name='" + o + "']");
            var obj2 = $("form select[name='" + o + "']");
            var required = obj.attr('required');
            var required2 = obj2.attr('required');
            if (required == 'required' || required2 == 'required') {
                if (_object[o].length == 0) {
                    tips.warning(obj.attr('placeholder'));
                    obj.focus();
                    v = true;
                    break;
                }
                if ($('#' + o + '').val() == "*") {
                    tips.warning(obj2.attr('placeholder'));
                    obj2.focus();
                    v = true;
                    break;
                }
            }
        }
        return v;
    },
    set: function (_object) {
        for (var o in _object) {
            var obj = $("form input[name='" + o + "']");
            obj.val(_object[o]);
        }
        for (var o in _object) {
            var obj = $("form textarea[name='" + o + "']");
            obj.val(_object[o]);
        }
        for (var o in _object) {
            var obj = $("form select[name='" + o + "']");
            obj.val(_object[o]);
        }
        for (var o in _object) {
            var obj = $("form p[name='" + o + "']");
            obj.html(_object[o]);
        }
        //通过id渲染日期格式的数据
        for (var o in _object) {
            if (o.indexOf("Str") != -1) {
                var obj = $("form input[id='" + o + "']");
                obj.val(_object[o]);
            }
        }
        for (var o in _object) {
            var obj = $("img[id='" + o + "']");
            obj.attr('src', _object[o]);
        }
    }
}

/**
 * 字符串处理函数
 * */
var string = {
    random: function (_len) {
        _len = _len || 32;
        var $chars = 'ABCDEFGHJKMNPQRSTWXYZ2345678'; // 默认去掉了容易混淆的字符oOLl,9gq,Vv,Uu,I1
        var maxPos = $chars.length;
        var ran = '';
        for (var i = 0; i < _len; i++) {
            ran += $chars.charAt(Math.floor(Math.random() * maxPos));
        }
        return ran.toLowerCase();
    },
    cut: function (_str, _len) {
        var len = _str.length;
        if (len > _len) {
            _str = _str.substring(0, _len) + '...';
        }
        return _str;
    },
    time: function (_str) {
        _str = _str.toString();
        if (_str.length == 1) {
            return '0' + _str;
        } else {
            return _str;
        }
    }
}

/**
 * 缓存处理函数
 * */
var cache = {
    set: function (_key, _val) {
        store.set(_key, _val);
    },
    get: function (_key) {
        store.get(_key);
    },
    clear: function () {
        store.clear();
    }
}

/**
 * base64处理函数
 * */
var base64 = {
    enc: function (_val) {
        $.base64.utf8encode = true;
        return $.base64.btoa(_val);
    },
    dec: function (_val) {
        $.base64.utf8encode = true;
        return $.base64.atob(_val, true);
    }
}

/**
 * 加载进度条
 * */
var loading = {
    show: function () {
        $('#loading-lay').show();
    },
    hide: function () {
        $('#loading-lay').hide();
    }
}

/**
 * 对话框
 * */
var tips = {
    ok: function (_message) {
        swal('', _message, "success");
    },
    info: function (_message) {
        swal('', _message, "info");
    },
    error: function (_message) {
        swal('', _message, "error");
    },
    warning: function (_message) {
        swal('', _message, "warning");
    },
    done: function (_object) {
        swal({
            title: _object.message,
            text: "是否要继续操作?",
            type: "success",
            showCancelButton: true,
            confirmButtonColor: "#DD6B55",
            confirmButtonText: "继续操作",
            cancelButtonText: "关闭窗口",
            closeOnConfirm: true,
            closeOnCancel: true
        }, function (isConfirm) {
            if (isConfirm) {
                var input = $('input');
                input.val('');
                if (input.attr('type') == 'number') {
                    $('input').val(0);
                }
                $('select').selectedIndex = 1;
                $('textarea').val('');
                $('img').attr('src', '');
                $('img').attr('data-path', '');
            } else {
                closeLay();
            }
        });
    },
    //htc
    doneQuestion: function (_object) {
        swal({
            title: _object.message,
            text: "是否要继续添加?",
            type: "success",
            showCancelButton: true,
            confirmButtonColor: "#DD6B55",
            confirmButtonText: "继续添加",
            cancelButtonText: "关闭窗口",
            closeOnConfirm: true,
            closeOnCancel: true
        }, function (isConfirm) {
            if (isConfirm) {
                $('input:text').val('');
                $("input[name='answerNumber']").prop("checked", false);
                $('textarea').val('');
            } else {
                closeLay();
            }
        });
    },
    confirm: function (_object) {
        var enter = _object.enter;
        var cancel = _object.cancel;
        var color = _object.color;
        swal({
                title: _object.message,
                type: "warning",
                text: _object.text||"",
                showCancelButton: true,
                confirmButtonColor: ((typeof color) == "undefined") ? "#DD6B55" : color,
                confirmButtonText: ((typeof enter) == 'undefined') ? '删除' : enter,
                cancelButtonText: ((typeof cancel) == 'undefined') ? "容我再想想" : cancel,
                closeOnConfirm: false
            },
            function () {
                setTimeout(_object.fun, 20);
            }
        );
    }
}

/**
 * 从一个列表对象中取出一个对象
 * */
var result = {
    get: function (_obj, _id) {
        var o;
        for (var i = 0; i < _obj.length; i++) {
            if (_obj[i].id == _id) {
                o = _obj[i]
            }
        }
        return o;
    },

    getByAttr: function (_obj, _attr, _val) {
        var o;
        for (var i = 0; i < _obj.length; i++) {
            if (_obj[i][_attr] == _val) {
                o = _obj[i]
            }
        }
        return o;
    },

    find: function (_obj, _field, _val) {
        var o;
        for (var i = 0; i < _obj.length; i++) {
            if (_obj[i][_field] == _val) {
                o = _obj[i]
            }
        }
        return o;
    },
    update: function (_obj1, _obj2, _field) {
        var o = [];
        $.each(_obj1, function (i, obj) {
            if (obj[_field] == _obj2[_field]) {
                o.push(_obj2);
            } else {
                o.push(obj);
            }
        });
        return o;
    },
    delete: function (_obj, _id) {
        var o = [];
        $.each(_obj, function (i, obj) {
            if (obj['id'] != _id) {
                o.push(obj);
            }
        });
        return o;
    }
}

/**
 * 动态设置html的title
 * */
var title = {
    set: function (_title) {
        Title.change(_title);
        // Title.animation("marquee");
    }
}

/**
 * 认证
 * */
var auth = {
    check: function (event) {
        var url="",backUrl="";
        var urlAuth = store.get(cache_user_key+'-url-auth');
        if(urlAuth==undefined||urlAuth==null||urlAuth==""){
            store.clear();
            window.location.href =server_root;
        }

        var check = 0;
        if(event!=""&&event!=null){

            url =event.URL;
            url ='/'+url.replace(server_root,'');
            url =url.replace('http://','').replace('https://','');
            url = url.substr(url.indexOf('/'));
            if( url.indexOf('?')!=-1){
                url= url.substr(0,url.indexOf('?'));
            }
            if( url.indexOf('#')!=-1){
                url= url.substr(0,url.indexOf('#'));
            }
            backUrl=event.referrer.replace('http://','').replace('https://','');
            backUrl = backUrl.substr(backUrl.indexOf('/'));
            check=0;
        }


        if(url!=null&&url!=""){
        for (var i = 0; i < urlAuth.length; i++) {
            if (urlAuth[i] == url) {
                check = 1;
                break;
            }
        }}
        if (check == 0) {
            swal({
                    title: "您没有这个权限操作",
                    type: "warning",
                    showCancelButton: false,
                    confirmButtonColor: "#DD6B55",
                    confirmButtonText: "确定",
                    closeOnConfirm: false
                },
                function(){
                  if(backUrl==null||backUrl==""||backUrl=="/sys"||backUrl=='/'||backUrl=='/'){
                      backUrl=server_root+'sys/welcome';
                  }
                    window.location.href = backUrl;
                }
            );
            return false;
        } else {
            return true;
        }
        return false;
    },
    refuse: function (event) {
        var fun = $(event).attr('onclick');

        if ((typeof fun) == 'undefined') {
            fun = $(event).attr('onchange');
        }
        fun = fun.replace('(this)', '');
        fun = fun.replace(';', '');
        var btnAuth = store.get(cache_user_key+'-btn-auth');
        var check = 0;
        for (var i = 0; i < btnAuth.length; i++) {
            if (btnAuth[i].fun == fun) {
                check = 1;
                break;
            }
        }
        if (check == 0) {
            tips.warning("您没有这个权限操作");
            return true;
        } else {
            return false;
        }
        return false;
    },

    show: function () {

        var funAuth = store.get(cache_user_key+'-fun-auth');
        // console.log(funAuth);//所有可用方法

        //移除列表外的按钮(新增等)
        var outBtnList = $('.ibox-tools').find('button');
        // console.log(outBtnList);
        if(outBtnList!=null&&outBtnList!="") {
            for (var i = 0; i < outBtnList.length; i++) {
                var fun = outBtnList[i];
                fun = getOnclickFunName(fun);
                // console.log(funAuth.indexOf(fun) + " " +fun);
                if (funAuth.indexOf(fun) === -1) {
                    $(outBtnList[i]).remove();//移除button
                }
            }
        }
        //移除列表"操作"按钮内的按钮
        var onclickList = $('.dropdown-menu').find('li').find("a");
        // console.log(onclickList);//本列表页所有a标签
        var funs =new Array();//存放本列表页所有"操作"按钮名
        for (var m = 0; m < onclickList.length; m++) {
            var fun = onclickList[m];
            if(fun == undefined){
                continue;
            }
            fun = getOnclickFunName(fun);
            // console.log(funs.indexOf(fun))
            if(funs.indexOf(fun) === -1){
                funs[funs.length] = fun;
            }
        }
        // console.log(funs);

        var authFun = new Array();//用户可用的按钮,保留不移除
        for (var j = 0; j < funs.length; j++) {
            if (funAuth.indexOf(funs[j]) !== -1) {
                authFun[authFun.length] = funs[j];
            }
        }
        // console.log(authFun);

        for (var k = 0; k < onclickList.length; k++) {
            var fun = onclickList[k];
            fun = getOnclickFunName(fun);
            if(authFun.indexOf(fun) == -1){
                $(onclickList[k]).parent().remove();//移除li
            }
        }

        if($(".dropdown-menu li").length == 0){
            $(".btn-group").remove();
        }
    },

    checkFun : function (fun) {
        var btnAuth = store.get(cache_user_key+'-btn-auth');
        var check = 0;
        for (var i = 0; i < btnAuth.length; i++) {
            if (btnAuth[i].fun == fun) {
                check = 1;
                break;
            }
        }
        if (check == 0) {
            tips.warning("您没有这个权限操作");
            return true;
        } else {
            return false;
        }
        return false;

    }
}

/**
 * Json对象排序
 * */
var json = {
    desc: function (_array, _key) {
        return _array.sort(function (a, b) {
            var x = a[_key];
            var y = b[_key];
            return ((x > y) ? -1 : ((x < y) ? 1 : 0));
        });
    },
    asc: function (_array, _key) {
        return _array.sort(function (a, b) {
            var x = a[_key];
            var y = b[_key];
            return ((x < y) ? -1 : ((x > y) ? 1 : 0));
        });
    },
    update: function (_a, _b) {
        for (var i = 0; i < _a.length; i++) {
            if (_a[i].id == _b.id) {
                _a.splice(i, 1, _b);
            }
        }
        return _a;
    },
    delete: function (_a, _id) {
        for (var i = 0; i < _a.length; i++) {
            if (_a[i].id == _id) {
                _a.splice(i, 1);
            }
        }
        return _a;
    },
    o2av: function (_obj, _code, _field) {
        var data = [];
        if ((typeof _code) != 'undefined') {
            var field = "code";
            if ((typeof _field) != 'undefined') {
                field = _field;
            }
            for (var key in _obj) {
                var av = {"attr": key, "val": _obj[key]};
                av[field] = _code
                data.push(av);
            }
        } else {
            for (var key in _obj) {
                var av = {"attr": key, "val": _obj[key]};
                data.push(av);
            }
        }
        return data;
    }
}

function getSuffix(_fileName) {
    var suffix = 'sb';
    var item = _fileName.toLowerCase().split('.');
    if (item.length > 0) {
        suffix = item[(item.length - 1)];
    }
    return suffix;
}

function getEncodeUrl(url) {
    url = url.replace(/\//g, "*1");
    url = url.replace(/\:/g, "*2");
    url = url.replace(/\?/g, "*3");
    url = url.replace(/\=/g, "*4");
    url = url.replace(/\&/g, "*5");
    url = url.replace(/\./g, "*6");
    return url;
}

function getOnclickFunName(fun) {
    var fun = $(fun).attr('onclick');

    if ((typeof fun) == 'undefined') {
        fun = $(fun).attr('onchange');
    }
    if(fun!=""&&fun!=null) {
        fun = fun.replace('(this)', '');
        fun = fun.replace(';', '');
        return fun;
    }
}

function saveOurURL(url) {
    tips.ok("正在校验数据中，请稍候！");

    var source_url = "";
    var param = {url: toolModule.savePicUrlApi + "/" + getEncodeUrl(url) + '/', async: false};

    var request = ajax.get(param);
    request.done(function (d) {
        var urldata = d.result;
        source_url = urldata.source_url.replace("http:","").replace("https:","");

    })
    return source_url;
}

function replaceOutUrl(content) {
    content.replace(/\bsrc\b\s*=\s*[\'\"]?([^\'\"]*)[\'\"]?/g, function (match, capture) {
        if (capture.indexOf(fileSavepath) == -1) {
            var url = saveOurURL(capture);
            content = content.replace(new RegExp(capture, 'g'), url);
        }
    });
    return content;
}

function createTree(url, treeId,write,type, serverdata) {
//function createTree(url, treeId, serverdata,check) {
    // url 下载地址 treeId 绑定div  serverdata 加载数据  write可写状态  type类型 多选true/单选false
    var typeStr="";
    if (type == false) {
        typeStr ="radio";
    }else if (type ==true){
        typeStr ="checkbox";
    }
    var zTree; //用于保存创建的树节点
    var setting = { //设置
        check: {
            enable: true,
            chkStyle: typeStr,  //单选框
            chkboxType: {"Y": "s", "N": "ps"},
            chkDisabledInherit :true,
            radioType:"all"
        },
        view: {
            showLine: true, //显示辅助线
            dblClickExpand: true,
        },
        data: {
            simpleData: {
                enable: true,
                idKey: "id",
                pIdKey: "parentId",
                rootPId: 0
            }
        },
        callback: {
            onCheck: function zTreeOnCheck(event, treeId, treeNode) {
                    //子节点取消时同步取消父节点
                    var parentNode =treeNode.getParentNode();
                    var treeObj = $.fn.zTree.getZTreeObj(treeId);
                    if(treeNode.checked==false){
                        if(parentNode !=null){
                            treeObj.checkNode(parentNode, false, false);
                        }
                    }布置班级
                    if(treeNode.checked==true){
                        if(parentNode !=null){
                            var childnodes =parentNode.children;
                            var  num=0;
                            for(var i = 0; i < childnodes.length; i++) {
                                if(childnodes[i].checked==true){
                                    num++;
                                }
                            }
                            if (num==childnodes.length){
                                treeObj.checkNode(parentNode, true,false);
                            }
                        }

                    }


                }
        },
    };

    var param = {url: url};
    var request = ajax.get(param);
    request.done(function (d) {
        var data = d.result;
        zTree = $.fn.zTree.init($(treeId), setting, data); //创建树

        var node=zTree.getNodeByParam("parentId", 0);
        zTree.selectNode(node);
        zTree.expandNode(node, true, false, false);

        if (serverdata != "" && serverdata != null) {
            var rangeAray = JSON.parse(serverdata);
            for (var i = 0; i < rangeAray.length; i++) {
                var node = zTree.getNodeByParam("id", rangeAray[i].id, null);
                if(node!=null) {
                    zTree.checkNode(node, true, false);
                }
            }
        }


        if(!write){
            var nodes = zTree.getNodes();
            if (nodes.length>0) {
                for (var i = 0; i < nodes.length; i++) {
                   zTree.setChkDisabled(nodes[i],true,true,true);
                }
            }
        }



    })
    request.error(function (d) {
        alert("创建树失败!");
    })

}

function getCheckedNodesArr(treeId, value,type) {
    var treeObj = $.fn.zTree.getZTreeObj(treeId);
    if(treeObj == null){
        return null;
    }
    var nodes = treeObj.getCheckedNodes(true);
    var rangeArray = [];
    if (0 === nodes.length) {
        //tips.warning("请选择发布范围!");
        return null;
    }else {
        for (var i = 0; i < nodes.length; i++) {
            if(type==nodes[i].type){
                var rangeObj = {};
                rangeObj[value] = nodes[i][value];
                rangeObj['id'] = nodes[i].id;
                rangeObj['name'] = nodes[i].name;
                rangeObj['code'] = nodes[i].code;
                rangeObj['type'] = nodes[i].type;
                rangeObj['parentId'] = nodes[i].parentId;
                rangeArray.push(rangeObj);
            }
        }
        return rangeArray;
    }

}

function getData(event,attr) {
    return $(event).attr(attr);
}


function existNumber(event) {
    var number = event.value;
    var tipsIdValue = getData(event,"tips-id");
    var tipsId = $("#"+tipsIdValue);
    if(number == ""){
        tipsId.html("请输入学号/工号");
        return false;
    }
    tipsId.html("");
    var flag =false;
    $.ajax({
        type: "GET",
        url:  toolModule.checkUserApi + number,
        cache: false,
        headers: header,
        async:false,
        success:function (d) {
            // console.log(d.message)
            tipsId.html('');
            flag =true;
            return true;
        },
        error:function (d) {
            tipsId.html('学号/工号不存在！');
            flag =false;
            return false;
        }
    });
    return flag;
}

function notexistNumber(event) {
    var number = event.value;
    var tipsIdValue = getData(event,"tips-id");
    var tipsId = $("#"+tipsIdValue);
    if(number == ""){
        tipsId.html("请输入学号/工号");
        return false;
    }
    var flag =true;
    $.ajax({
        type: "GET",
        url:  toolModule.checkUserApi + number,
        cache: false,
        headers: header,
        async:false,
        success:function (d) {

            tipsId.html('学号/工号已存在！');
            flag =false;
            return false;
        },
        error:function (d) {
            tipsId.html('');
            flag =true;
            return true;
        }
    });
    return flag;
}


function checkRegular(event,flag) {
    var data=event.value ;
    var showflag;
    if(flag!=null){
        showflag=flag;
    }else{
        showflag=false;
    }

    var type = getData(event,"data-type");
    var  regular= getData(event,"data-regular");
    var length = getData(event,"data-length");
    var tipsIdValue = getData(event,"tips-id");
    var tipsId = $("#"+tipsIdValue);

    //每次校验前置空提示栏
    if(tipsIdValue!=null){
        if(tipsId!=null) {
            tipsId.html("");
        }
    }

    if(length!=null&&length!="") {
        if(data.length>parseInt(length)) {
            showTips(tipsIdValue,"长度不能超过" + length + "!",showflag);
            return false;
        }
    }

   // debugger;

    if(type!=null&&type!=""){
        switch (type) {
            case "regnumber":
                var pattern = /^\d{8}$/;//教职工号
                var pattern2 = /^\d{12}$/;//学号
                var pattern3 = /^\d{13}$/;//研究生学号

                if( pattern.test(data)||pattern2.test(data)||pattern3.test(data)){
                    if(notexistNumber(event)){
                        return true;
                    }else{
                        showTips(tipsIdValue,"学号/工号已存在！",showflag);
                        return false;
                    };
                }else{
                    showTips(tipsIdValue,"学号/工号输入有误！",showflag);
                    return false;
                }
            case "number":
                var pattern = /^\d{8}$/;//教职工号
                var pattern2 = /^\d{12}$/;//学号
                var pattern3 = /^\d{13}$/;//研究生学号

                if( pattern.test(data)||pattern2.test(data)||pattern3.test(data)){
                    if(existNumber(event)){
                        return true;
                    }else{
                        showTips(tipsIdValue,"学号/工号不存在！",showflag);
                        return false;
                    };
                }else{
                    showTips(tipsIdValue,"学号/工号输入有误！",showflag);
                    return false;
                }
            case "date":
                var pattern = /^\d{4}(\-|\/|.)\d{1,2}\1\d{1,2}$/;
                if(data!=null&&data!=""){
                if( pattern.test(data)) {
                    return true;
                }else {
                    showTips(tipsIdValue,"日期输入有误！",showflag);
                    return false;
                }
                }else{
                    if(showflag){
                        showTips(tipsIdValue,"没有输入日期时间！",showflag);
                    }else {
                        return true;
                    }
                }
            case "datetime":
                var pattern = /^\d{4}(\-|\/|.)\d{1,2}\1\d{1,2}(\ )([0-1][0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])$/;

                if(data!=null&&data!=""){
                    if( pattern.test(data)) {
                        return true;
                    }else {
                        showTips(tipsIdValue,"日期时间输入有误！",showflag);
                        return false;
                    }
                }else{
                    if(showflag){
                        showTips(tipsIdValue,"没有输入日期时间！",showflag);
                    }else {
                        return true;
                    }
                }
            case "phone":
                var pattern = /^0?(13|14|15|17|18|19)[0-9]{9}$/;
                if( pattern.test(data)) {
                    return true;
                }else {
                    showTips(tipsIdValue,"手机输入有误！",showflag);
                    return false;
                }
            case "contact":
                var phone = /^0?(13|14|15|17|18|19)[0-9]{9}$/;
                var tel = /^[0-9-()（）]{7,18}$/;

                if(phone.test(data)||tel.test(data)) {
                    return true;
                }else {
                    showTips(tipsIdValue,"联系方式输入有误！",showflag);
                    return false;
                }
            case "email":
                var pattern = /^\w[-\w.+]*@([A-Za-z0-9][-A-Za-z0-9]+\.)+[A-Za-z]{2,14}$/;
                if(pattern.test(data)) {
                    return true;
                }else {
                    showTips(tipsIdValue,"Email输入有误！",showflag);
                    return false;
                }
            case "qq":
                var pattern = /^[1-9]([0-9]{5,11})$/;
                if(pattern.test(data)) {
                    return true;
                }else {
                    showTips(tipsIdValue,"QQ输入有误！",showflag);
                    return false;
                }
            case "postcode":
                var pattern = /^\d{6}$/;
                if(pattern.test(data)) {
                    return true;
                }else {
                    showTips(tipsIdValue,"邮政编码输入有误！",showflag);
                    return false;
                }
            case "loginName":
                var pattern = /^([A-Za-z0-9]{5,20})$/;//管理员账号

                if( pattern.test(data)){
                    return true;
                }else {
                    showTips(tipsIdValue,"必须为5-20位字母或数字！",showflag);
                    return false;
                }
            case "stringcode":
                var pattern = /^[A-Za-z0-9]+$/;
                if(pattern.test(data)) {
                    return true;
                }else {
                    showTips(tipsIdValue,"必须为字母或数字！",showflag);
                    return false;
                }
            case "intcode":
                var pattern = /^[0-9]+$/;
                if(pattern.test(data)) {
                    return true;
                }else {
                    showTips(tipsIdValue,"必须为数字！",showflag);
                    return false;
                }
            case "float":
                var pattern = /^[1-9]\d*.\d*|0.\d*[1-9]\d*$/;
                if(pattern.test(data)) {
                    return true;
                }else {
                    showTips(tipsIdValue,"必须为浮点数！",showflag);
                    return false;
                }

            case "name":
                var pattern = /^([\u4e00-\u9fa5\·]{1,20})$/;
                if(pattern.test(data)) {
                    return true;
                }else {
                    showTips(tipsIdValue,"姓名输入有误！",showflag);
                    return false;
                }
            case "idcard":
                var pattern = /^(\d{6})(\d{4})(\d{2})(\d{2})(\d{3})([0-9]|X)$/;
                if(pattern.test(data)) {
                    return true;
                }else {
                    showTips(tipsIdValue,"身份证输入有误！",showflag);
                    return false;
                }
            case "class":
                var pattern = /^([\u4e00-\u9fa5\0-9]{1,20})$/;
                if(pattern.test(data)) {
                    return true;
                }else {
                    showTips(tipsIdValue,"班级输入有误！",showflag);
                    return false;
                }
            case "password":
                var pattern = /^[\w_-]{6,20}$/;
                if(pattern.test(data)) {
                    return true;
                }else {
                    showTips(tipsIdValue,"密码必须为6-20位数字、字母或者‘_-’！",showflag);
                    return false;
                }
            case "chinese":
                var pattern = /^([\u4e00-\u9fa5]+)$/;
                if(pattern.test(data)) {
                    return true;
                }else {
                    showTips(tipsIdValue,"输入有误,必须为中文！",showflag);
                    return false;
                }
            case "address":
                var pattern = /^([\u4e00-\u9fa5\0-9]+)$/;
                if(pattern.test(data)) {
                    return true;
                }else {
                    showTips(tipsIdValue,"输入有误,必须为中文或数字！",showflag);
                    return false;
                }
            case "version":
                var pattern = /^\V\d+(.\d+)*$/;
                var pattern2 = /^\v\d+(.\d+)*$/;

                if (pattern.test(data)||pattern2.test(data)) {
                    return true;
                } else {
                    showTips(tipsIdValue, "版本号必须为Vx.x.x.x（x为数字）！", showflag);
                    return false;
                }
            case "regular":
                if(regular!=null){
                var str=regular.replace("'","\'").replace("\\","\\\\");
                var reg = new RegExp(str,'g');
                if( reg.test(data)){
                    return true;
                }else{
                    showTips(tipsIdValue,"输入有误！",showflag);
                    return false;
                }}else{
                    return false;
                }
            default:
                return true;

        }
        return false;
    }else{
        return true;
    }


}

function  showTips(tipsIdValue,str,flag) {
    var tipsId = $("#"+tipsIdValue);

    //每次校验前置空提示栏
    if(tipsIdValue!=null){
        if(tipsId!=null) {
            tipsId.html(str);
            if(flag){
                tips.error(str);
            }
        }else{
            tips.error(str);
        }
    }else {
        tips.error(str);
    }
}

function getToken(){
    var str = "",
        arr = ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'];

    for(var i=0; i<32; i++){
        pos = Math.round(Math.random() * (arr.length-1));
        str += arr[pos];
    }
    return str;
}

function xls_check(feid) { //自己添加的文件后缀名的验证
    var xls = document.getElementById(feid);
    return /.(xls|xlsx)$/.test(xls.value.toLowerCase())?true:(function() {
        tips.info('导入格式仅支持xls、xlsx格式。');
        return false;
    })();
}
function initImportStatus() {

    var progressBar = document.getElementById("todoBar");
    var percentageDiv = document.getElementById("todoPer");
    var markDiv = document.getElementById("mark");
    progressBar.max = 0;
    progressBar.value = 0;
    percentageDiv.innerHTML =  '0%';
    markDiv.innerHTML='';
    ;
}





function checkImportStatus( url) {
    if(store.get(cache_user_key+'_ImportStatus_url')==null) {
        store.set(cache_user_key + '_ImportStatus_url', url);
    }else{
        url =store.get(cache_user_key+'_ImportStatus_url');
    }
    var importXhr = new XMLHttpRequest();  // XMLHttpRequest 对象
    importXhr.open("post",url, true); //post方式，url为服务器请求地址，true 该参数规定请求是否异步处理。
    importXhr.setRequestHeader("token", (typeof store_user_info) == 'undefined' ? '' : store_user_info.token);
    importXhr.setRequestHeader("Access-Control-Allow-Origin", "*");
    importXhr.onload = uploadCheckComplete; //请求完成
    importXhr.send(); //开始上传，发送form数据
}


//上传成功响应
function uploadCheckComplete(evt) {

    var url = "";
    if(store.get(cache_user_key+'_ImportStatus_url')!=null) {
        url =store.get(cache_user_key+'_ImportStatus_url');
    }
    //服务断接收完文件返回的结果
    var progressBar = document.getElementById("todoBar");
    var percentageDiv = document.getElementById("todoPer");
    var markDiv = document.getElementById("mark");


    var status =evt.target.status;
    if(status==404){
        markDiv.innerHTML="，处理状态：网络繁忙，请稍候。";
        return false;
    }


    if(status!=200&&status!=404){
        var   data =  JSON.parse(evt.target.responseText)
        markDiv.innerHTML="，处理状态："+data.message+"。";
        tips.error(data.message);
        return false;
    }

    var   data =  JSON.parse(evt.target.responseText)


    var result =data.result;

    var str="";
    if(result.status==0||result.status=="0"){
        str="未开始";
    }else  if(result.status==1||result.status=="1"){
        str="处理成功";
    }else  if(result.status==2||result.status=="2"){
        str="处理异常";
    }

    var total=1,now=0;
    if(result.total!=null&&result.total!=0){
        total=result.total;
    }
    if(result.now!=null){
        now=result.now;
    }

    progressBar.max = total;
    progressBar.value = now;
    percentageDiv.innerHTML = Math.round(now / total * 100) + "%";


    var timer =setInterval(checkImportStatus(url), 2000 * 1);

   if(result.res==2) {
        //  debugger;
        markDiv.innerHTML="，当前操作记录："+now+"，处理状态："+str+"。";
    }else if(result.res==1) {
        //  debugger;
       window.clearInterval(timer);
        store.remove(cache_user_key+'_ImportStatus_url');
        markDiv.innerHTML="，全部导入完成，等待操作结果返回。";
    }

}



function  importFile(fileid,url, importComplete ) {

    var fileObj = document.getElementById(fileid).files[0]; // js 获取文件对象
    var form = new FormData(); // FormData 对象
    form.append("file", fileObj); // 文件对象

    var importXhr = new XMLHttpRequest();  // XMLHttpRequest 对象
    importXhr.open("post", url, true); //post方式，url为服务器请求地址，true 该参数规定请求是否异步处理。

    importXhr.setRequestHeader("token", (typeof store_user_info) == 'undefined' ? '' : store_user_info.token);
    importXhr.setRequestHeader("Access-Control-Allow-Origin", "*");

    importXhr.upload.onprogress = progressFunction;//【上传进度调用方法实现】
    importXhr.upload.onloadstart = function(){//上传开始执行方法
        ot = new Date().getTime();   //设置上传开始时间
        oloaded = 0;//设置上传开始时，以上传的文件大小为0
    };

    importXhr.onload = importComplete; //请求完成

    importXhr.send(form); //开始上传，发送form数据

}


//上传进度实现方法，上传过程中会频繁调用该方法
function progressFunction(evt) {
    var progressBar = document.getElementById("progressBar");
    var percentageDiv = document.getElementById("percentage");
    // event.total是需要传输的总字节，event.loaded是已经传输的字节。如果event.lengthComputable不为真，则event.total等于0
    if (evt.lengthComputable) {//
        progressBar.max = evt.total;
        progressBar.value = evt.loaded;
        percentageDiv.innerHTML = Math.round(evt.loaded / evt.total * 100) + "%";
    }
    var time = document.getElementById("time");
    var nt = new Date().getTime();//获取当前时间
    var pertime = (nt-ot)/1000; //计算出上次调用该方法时到现在的时间差，单位为s
    ot = new Date().getTime(); //重新赋值时间，用于下次计算
    var perload = evt.loaded - oloaded; //计算该分段上传的文件大小，单位b
    oloaded = evt.loaded;//重新赋值已上传文件大小，用以下次计算
    //上传速度计算
    var speed = perload/pertime;//单位b/s
    var bspeed = speed;
    var units = 'b/s';//单位名称
    if(speed/1024>1){
        speed = speed/1024;
        units = 'k/s';
    }
    if(speed/1024>1){
        speed = speed/1024;
        units = 'M/s';
    }
    speed = speed.toFixed(1);
    //剩余时间
    var resttime = ((evt.total-evt.loaded)/bspeed).toFixed(1);
    time.innerHTML = '，速度：'+speed+units+'，剩余时间：'+resttime+'s';
    if(bspeed==0) time.innerHTML = '上传已取消';
}




//上传成功
function  importCompleteShow(evt) {
    store.remove(cache_user_key+'_ImportStatus_url');

    var resDiv = document.getElementById("res");
    var markDiv = document.getElementById("mark");

    var status =evt.target.status;
    if(status==404){
        resDiv.innerHTML="处理状态：网络繁忙，请稍候。";
        return false;
    }


    if(status!=200&&status!=404){
        var   data =  JSON.parse(evt.target.responseText)
        resDiv.innerHTML=data.message;
        tips.error(data.message);
        return false;
    }

    var   data =  JSON.parse(evt.target.responseText);


    var result =data.result;

    markDiv.innerHTML="，全部导入完成，等待操作结果返回。";

    var errcount =parseInt(result.errcount);
    var truecount =parseInt(result.truecount);
    var total =parseInt(result.total);

    var progressBar = document.getElementById("todoBar");
    var percentageDiv = document.getElementById("todoPer");
    progressBar.max = 1;
    progressBar.value =  1;
    percentageDiv.innerHTML =  "100%";
    // debugger;
    $('#opt-dialog-enter').hide();
    $('#opt-dialog-enter').empty();
    $('#error-list').empty();

    if(result.success) {
        resDiv.innerHTML="导入成功"+truecount+"条记录！";
        tips.ok("导入成功"+truecount+"条记录！");
    }else{
        if(errcount>0) {
            resDiv.innerHTML="导入成功"+truecount+"条记录,有"+errcount+"条数据未导入！";
            var listData=result.errlist;
            var template = doT.template($("#error-template").text());
            $('#error-list').html(template(listData));
            $('#error-body').show();
            tips.error("导入成功"+truecount+"条记录,有"+errcount+"条数据未导入！");
        }else{
            resDiv.innerHTML="导入成功"+truecount+"条记录！";
            tips.ok("导入成功"+truecount+"条记录！");
        }
    }
}


function downFile(url) {
    var $form = $('<form method="GET"></form>');
    $form.attr('action', url);
    $form.appendTo($('body'));
    $form.submit();

}
