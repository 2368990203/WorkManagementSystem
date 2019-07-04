var server_root = getRootPath();

function getRootPath() {

    var host = window.location.host;
    var origin = window.location.origin;
    var root = '/';
    if (!origin) {
        origin = window.location.protocol + "//" + window.location.hostname + (window.location.port ? ':' + window.location.port : '');
    }
    switch (host) {
        case "gxmddj.gxun.edu.cn":
        case "10.240.16.116":
            root = origin + '/';
            break;
        case "mengwp.gxun.edu.cn":
            root = origin + '/party/';
            break;
        default:
            root = origin + '/';
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
    verify: function (_object) {
        var v = false;
        for (var o in _object) {
            var obj1 = $("form input[name='" + o + "']");
            var obj2 = $("form select[name='" + o + "']");
            var obj3 = $("form textarea[name='" + o + "']");
            var obj;
            if (obj1.length > 0) {
                obj = obj1;
            }
            if (obj2.length > 0) {
                obj = obj2;
            }
            if (obj3.length > 0) {
                obj = obj3;
            }

            var required = obj.attr('required');
            var required2 = obj2.attr('required');
            var required3 = obj3.attr('required');


            if (required == 'required' || required2 == 'required' || required3 == 'required') {
                if (_object[o].length == 0) {
                    tips.warning(obj.attr('placeholder'));
                    obj.focus();
                    v = true;
                    break;
                }
            }

            if (_object[o].length != 0) {
                v = !checkRegular(obj[0], true);
                if (v) {
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
        var closeOnConfirm = _object.closeOnConfirm;
        swal({
                title: _object.message,
                type: "warning",
                showCancelButton: true,
                text: _object.text,
                confirmButtonColor: ((typeof color) == "undefined") ? "#DD6B55" : color,
                confirmButtonText: ((typeof enter) == 'undefined') ? '删除' : enter,
                cancelButtonText: ((typeof cancel) == 'undefined') ? "容我再想想" : cancel,
                closeOnConfirm: (typeof closeOnConfirm) == 'undefined' ? false : true,
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
    refuse: function (event) {
        var fun = $(event).attr('onclick');

        if ((typeof fun) == 'undefined') {
            fun = $(event).attr('onchange');
        }
        fun = fun.replace('(this)', '');
        fun = fun.replace(';', '');
        var btnAuth = store.get(cache_user_key + '-btn-auth');
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

function saveOurURL(url) {
    tips.ok("正在校验数据中，请稍候！");

    var source_url = "";
    var param = {url: toolModule.savePicUrlApi + "/" + getEncodeUrl(url) + '/', async: false};

    var request = ajax.get(param);
    request.done(function (d) {
        var urldata = d.result.data;
        source_url = urldata.source_url.replace("http:", "");

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


function createTree(url, treeId, write, type, serverdata) {
//function createTree(url, treeId, serverdata,check) {
    // url 下载地址 treeId 绑定div  serverdata 加载数据  write可写状态  type类型 多选true/单选false
    var typeStr = "";
    if (type == false) {
        typeStr = "radio";
    } else if (type == true) {
        typeStr = "checkbox";
    }
    var zTree; //用于保存创建的树节点
    var setting = { //设置
        check: {
            enable: true,
            chkStyle: typeStr,  //单选框
            chkboxType: {"Y": "s", "N": "ps"}
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
                var parentNode = treeNode.getParentNode();
                var treeObj = $.fn.zTree.getZTreeObj(treeId);
                if (treeNode.checked == false) {
                    if (parentNode != null) {
                        treeObj.checkNode(parentNode, false, false);
                    }
                }
                if (treeNode.checked == true) {
                    if (parentNode != null) {
                        var childnodes = parentNode.children;
                        var num = 0;
                        for (var i = 0; i < childnodes.length; i++) {
                            if (childnodes[i].checked == true) {
                                num++;
                            }
                        }
                        if (num == childnodes.length) {
                            treeObj.checkNode(parentNode, true, false);
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
        // zTree.setChkDisabled(write,true,true);
        if (serverdata != "" && serverdata != null) {
            var rangeAray = JSON.parse(serverdata);
            console.log(rangeAray);
            for (var i = 0; i < rangeAray.length; i++) {
                var node = zTree.getNodeByParam("id", rangeAray[i].id, null);
                zTree.checkNode(node, true, false);
            }
        }
        if (!write) {
            var nodes = zTree.getNodes();
            if (nodes.length > 0) {
                for (var i = 0; i < nodes.length; i++) {
                    zTree.setChkDisabled(nodes[i], true, true, true);
                }
            }
        }


    })
    request.error(function (d) {
        alert("创建树失败!");
    })

}

function getCheckedNodesToString(treeId) {
    var treeObj = $.fn.zTree.getZTreeObj(treeId);
    var nodes = treeObj.getCheckedNodes(true);
    var rangeArray = [];
    if (0 === nodes.length) {
        alert("请选择发布范围!");
        return false;
    } else {
        for (var i = 0; i < nodes.length; i++) {
            var rangeObj = {};
            rangeObj['id'] = nodes[i].id;
            rangeObj['name'] = nodes[i].name;
            rangeObj['code'] = nodes[i].code;
            rangeObj['type'] = nodes[i].type;
            rangeObj['parentId'] = nodes[i].parentId;
            rangeArray.push(rangeObj);
        }
        return JSON.stringify(rangeArray);
    }
}

//检测权限，没有返回true，有则返回false
function checkAuth() {
    if ((typeof store_user_info) == 'undefined') {
        swal({
                title: "",
                text: '请先登录',
                type: "warning",
                showCancelButton: true,
                cancelButtonText: "取消",
                confirmButtonColor: "#DD6B55",
                confirmButtonText: "确定",
                closeOnConfirm: false
            },
            function (isConfirm) {
                if (isConfirm) {
                    store.clear();
                    window.location.href = server_root + 'login/?url=' + location.href;
                } else {
                    history.back();
                }
            }
        );
        return true;
    } else {
        return false;
    }

}

//未登录验证转跳加参数
function checkAuth_local(s, t) {
    if ((typeof store_user_info) == 'undefined') {
        // swal({
        //         title: "",
        //         text: '请先登录',
        //         type: "warning",
        //         showCancelButton: true,
        //         cancelButtonText: "取消",
        //         confirmButtonColor: "#DD6B55",
        //         confirmButtonText: "确定",
        //         closeOnConfirm: false
        //     },
        //     function (isConfirm) {
        //         if (isConfirm) {
        //             store.clear();
        //             window.location.href = server_root+'login/?url=' + location.href;
        //         } else {
        //             window.location.href = server_root + local;
        //         }
        //     }
        window.location.href = server_root + s;
        // );
        return true;
    } else {
        window.location.href = server_root + t;
        return false;
    }

}

function existNumber(event) {
    var number = event.value;
    var tipsIdValue = getData(event, "tips-id");
    var tipsId = $("#" + tipsIdValue);
    if (number == "") {
        tipsId.html("请输入学号");
        return false;
    }
    tipsId.html("");
    var flag = false;
    $.ajax({
        type: "GET",
        url: toolModule.checkUserApi + number,
        cache: false,
        headers: header,
        async: false,
        success: function (d) {
            // console.log(d.message)
            flag = true;
            return true;
        },
        error: function (d) {
            tipsId.html(d.responseJSON.message);
            flag = false;
            return false;
        }
    });
    return flag;
}

function getData(event, attr) {
    return $(event).attr(attr);
}

function checkRegular(event, flag) {
    var data = event.value;
    var showflag;
    if (flag != null) {
        showflag = flag;
    } else {
        showflag = false;
    }

    var type = getData(event, "data-type");
    var regular = getData(event, "data-regular");
    var length = getData(event, "data-length");
    var tipsIdValue = getData(event, "tips-id");
    var tipsId = $("#" + tipsIdValue);

    //每次校验前置空提示栏
    if (tipsIdValue != null) {
        if (tipsId != null) {
            tipsId.html("");
        }
    }

    if (length != null && length != "") {
        if (data.length > parseInt(length)) {
            showTips(tipsIdValue, "长度不能超过" + length + "!", showflag);
            return false;
        }
    }

    // debugger;

    if (type != null && type != "") {
        switch (type) {
            case "regnumber":
                var pattern = /^\d{8}$/;//教职工号
                var pattern2 = /^\d{12}$/;//学号
                var pattern3 = /^\d{13}$/;//研究生学号

                if (pattern.test(data) || pattern2.test(data) || pattern3.test(data)) {
                    if (existNumber(event)) {
                        showTips(tipsIdValue, "学号/工号已存在！", showflag);
                        return false;
                    } else {
                        return true;
                    }
                    ;
                } else {
                    showTips(tipsIdValue, "学号/工号输入有误！", showflag);
                    return false;
                }
            case "number":
                var pattern = /^\d{8}$/;//教职工号
                var pattern2 = /^\d{12}$/;//学号
                var pattern3 = /^\d{13}$/;//研究生学号

                if (pattern.test(data) || pattern2.test(data) || pattern3.test(data)) {
                    if (existNumber(event)) {
                        return true;
                    } else {
                        showTips(tipsIdValue, "学号/工号不存在！", showflag);
                        return false;
                    }
                    ;
                } else {
                    showTips(tipsIdValue, "学号/工号输入有误！", showflag);
                    return false;
                }
            case "date":
                var pattern = /^\d{4}(\-|\/|.)\d{1,2}\1\d{1,2}$/;
                if (data != null && data != "") {
                    if (pattern.test(data)) {
                        return true;
                    } else {
                        showTips(tipsIdValue, "日期输入有误！", showflag);
                        return false;
                    }
                } else {
                    if (showflag) {
                        showTips(tipsIdValue, "没有输入日期时间！", showflag);
                    } else {
                        return true;
                    }
                }
            case "datetime":
                var pattern = /^\d{4}(\-|\/|.)\d{1,2}\1\d{1,2}(\ )([0-1][0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])$/;

                if (data != null && data != "") {
                    if (pattern.test(data)) {
                        return true;
                    } else {
                        showTips(tipsIdValue, "日期时间输入有误！", showflag);
                        return false;
                    }
                } else {
                    if (showflag) {
                        showTips(tipsIdValue, "没有输入日期时间！", showflag);
                    } else {
                        return true;
                    }
                }
            case "phone":
                var pattern = /^0?(13|14|15|17|18|19)[0-9]{9}$/;
                if (pattern.test(data)) {
                    return true;
                } else {
                    showTips(tipsIdValue, "手机输入有误！", showflag);
                    return false;
                }
            case "contact":
                var phone = /^0?(13|14|15|17|18|19)[0-9]{9}$/;
                var tel = /^[0-9-()（）]{7,18}$/;

                if (phone.test(data) || tel.test(data)) {
                    return true;
                } else {
                    showTips(tipsIdValue, "联系方式输入有误！", showflag);
                    return false;
                }
            case "email":
                var pattern = /^\w[-\w.+]*@([A-Za-z0-9][-A-Za-z0-9]+\.)+[A-Za-z]{2,14}$/;
                if (pattern.test(data)) {
                    return true;
                } else {
                    showTips(tipsIdValue, "Email输入有误！", showflag);
                    return false;
                }
            case "qq":
                var pattern = /^[1-9]([0-9]{5,11})$/;
                if (pattern.test(data)) {
                    return true;
                } else {
                    showTips(tipsIdValue, "QQ输入有误！", showflag);
                    return false;
                }
            case "postcode":
                var pattern = /^\d{6}$/;
                if (pattern.test(data)) {
                    return true;
                } else {
                    showTips(tipsIdValue, "邮政编码输入有误！", showflag);
                    return false;
                }
            case "loginName":
                var pattern = /^([A-Za-z0-9]{5,20})$/;//管理员账号
                var pattern2 = /^\d{8}$/;//教职工号
                var pattern3 = /^\d{12}$/;//学号
                var pattern4 = /^\d{13}$/;//研究生学号

                if (pattern.test(data) || pattern2.test(data) || pattern3.test(data) || pattern4.test(data)) {
                    return true;
                } else {
                    showTips(tipsIdValue, "登录名输入有误！", showflag);
                    return false;
                }
            case "stringcode":
                var pattern = /^[A-Za-z0-9]+$/;
                if (pattern.test(data)) {
                    return true;
                } else {
                    showTips(tipsIdValue, "必须为字母或数字！", showflag);
                    return false;
                }
            case "intcode":
                var pattern = /^[0-9]+$/;
                if (pattern.test(data)) {
                    return true;
                } else {
                    showTips(tipsIdValue, "必须为数字！", showflag);
                    return false;
                }
            case "float":
                var pattern = /^[1-9]\d*.\d*|0.\d*[1-9]\d*$/;
                if (pattern.test(data)) {
                    return true;
                } else {
                    showTips(tipsIdValue, "必须为浮点数！", showflag);
                    return false;
                }

            case "name":
                var pattern = /^([\u4e00-\u9fa5\·]{1,20})$/;
                if (pattern.test(data)) {
                    return true;
                } else {
                    showTips(tipsIdValue, "姓名输入有误！", showflag);
                    return false;
                }
            case "idcard":
                var pattern = /^(\d{6})(\d{4})(\d{2})(\d{2})(\d{3})([0-9]|X)$/;
                if (pattern.test(data)) {
                    return true;
                } else {
                    showTips(tipsIdValue, "身份证输入有误！", showflag);
                    return false;
                }
            case "class":
                var pattern = /^([\u4e00-\u9fa5\0-9]{1,20})$/;
                if (pattern.test(data)) {
                    return true;
                } else {
                    showTips(tipsIdValue, "班级输入有误！", showflag);
                    return false;
                }
            case "password":
                var pattern = /^[\w_-]{6,20}$/;
                if (pattern.test(data)) {
                    return true;
                } else {
                    showTips(tipsIdValue, "密码必须为6-20位数字、字母或者‘_-’！", showflag);
                    return false;
                }
            case "chinese":
                var pattern = /^([\u4e00-\u9fa5]+)$/;
                if (pattern.test(data)) {
                    return true;
                } else {
                    showTips(tipsIdValue, "输入有误,必须为中文！", showflag);
                    return false;
                }
            case "address":
                var pattern = /^([\u4e00-\u9fa5\0-9]+)$/;
                if (pattern.test(data)) {
                    return true;
                } else {
                    showTips(tipsIdValue, "输入有误,必须为中文或数字！", showflag);
                    return false;
                }
            case "version":
                var pattern = /^\V\d+(.\d+)*$/;
                var pattern2 = /^\v\d+(.\d+)*$/;

                if (pattern.test(data) || pattern2.test(data)) {
                    return true;
                } else {
                    showTips(tipsIdValue, "版本号必须为Vx.x.x.x（x为数字）！", showflag);
                    return false;
                }
            case "regular":
                if (regular != null) {
                    var str = regular.replace("'", "\'").replace("\\", "\\\\");
                    var reg = new RegExp(str, 'g');
                    if (reg.test(data)) {
                        return true;
                    } else {
                        showTips(tipsIdValue, "输入有误！", showflag);
                        return false;
                    }
                } else {
                    return false;
                }
            default:
                return true;

        }
        return false;
    } else {
        return true;
    }


}

function showTips(tipsIdValue, str, flag) {
    var tipsId = $("#" + tipsIdValue);

    //每次校验前置空提示栏
    if (tipsIdValue != null) {
        if (tipsId != null) {
            tipsId.html(str);
            if (flag) {
                tips.error(str);
            }
        } else {
            tips.error(str);
        }
    } else {
        tips.error(str);
    }
}

