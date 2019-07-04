var lastTaskId, cosKit, cosConfig = {};
var taskReady = function (taskId) {
    lastTaskId = taskId;
};

var successCallBack = function (result) {
};
var errorCallBack = function (result) {
    result = result || {};
    console.log('request error:', result && result.message);
    loading.hide();
    tips.error('上传失败：'+result && result.message);
};
var progressCallBack = function (curr, sha1) {
    var sha1CheckProgress = ((sha1 * 100).toFixed(2) || 100) ;
    var uploadProgress = ((curr || 0) * 100).toFixed(2) ;
    if(uploadProgress==0&&sha1CheckProgress>0){
        var checkMsg = '文件校验:' + sha1CheckProgress + '%.';
        if(sha1CheckProgress==100){
            checkMsg = '文件校验:完成.'
        }
        tips.ok( checkMsg);
    }else if(uploadProgress>0){
        var uploadMsg = '上传进度:' + uploadProgress + '%.';
        if(uploadProgress==100){
            uploadMsg = '上传成功';
        }
        tips.ok(uploadMsg);
    }
    ;
};


var cos = {
    init:function () {
        var param = {url:tpsModule.cosApi};
        var request = ajax.get(param);
        request.done(function (d) {
            cosConfig = d.result;
            if(cosConfig.bucket!="qihsoft"){
                tips.error("上传插件初始化异常！");
                return false;
            }else{
            cosKit = new CosCloud({
                appid: cosConfig.appId, // APPID 必填参数
                bucket: cosConfig.bucket, // bucketName 必填参数
                region: cosConfig.region, // 地域信息 必填参数 华南地区填gz 华东填sh 华北填tj
                getAppSign: function (callback) {//获取签名 必填参数
                    var param = {url:tpsModule.cosApi+'/sign',async:false};
                    var request = ajax.get(param);
                    request.done(function (appData) {
                        var result = appData.result;
                        callback(result.sign);
                    });
                },
                getAppSignOnce: function (callback) {//单次签名，参考上面的注释即可
                    var param = {url:tpsModule.cosApi+'/sign',async:false};
                    var request = ajax.get(param);
                    request.done(function (appSignData) {
                        var result = appSignData.result;
                        callback(result.sign);
                    });
                }
            });
            }
        })
    },
    uploadFile:function (_path, _file, _success) {
        loading.show();
        //console.log(cosKit);
        cosKit.uploadFile(_success, errorCallBack, progressCallBack,
            cosConfig.bucket, _path, _file, 0, taskReady); //insertOnly==0 表示允许覆盖文件 1表示不允许
    },

    fileName:function (_path) {
        var t = 'http://qihsoft-1251217219.cosgz.myqcloud.com';
        var reg = new RegExp(t,'g'); //创建正则RegExp对象
        return _path.replace(reg,'');
    }
}