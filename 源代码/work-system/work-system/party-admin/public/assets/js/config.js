var cache_user_key = "admin-user";
var debug = true;
var optId = 0;              // 当前正在操作的主ID
var opt2ndId = 0,opt3rdId = 0,opt4thId = 0;           // 当前正在操作的ID
var btnAuth = [],ossFile = [];

var style = {
    low:'?x-oss-process=style/oss-web-admin-low',
    mid:'?x-oss-process=style/oss-web-admin-mid',
    hd:'?x-oss-process=style/oss-web-admin-hd'
}