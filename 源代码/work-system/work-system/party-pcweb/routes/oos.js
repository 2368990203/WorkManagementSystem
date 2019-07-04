

var express = require('express');

var router = express.Router();
var mutipart= require('connect-multiparty');

var mutipartMiddeware = mutipart();

var Minio = require('minio');

var minioClient = new Minio.Client({
    endPoint: 'oos.gxun.club',
    port: 9000,
    useSSL: false,
    accessKey: 'XJMFSJNOFVI7WVMBKKQX',
    secretKey: 'AWEMqR2yPzXkkPJP7XMDB61bhnH4UBicbumk3Tx2'
});
router.get('/test',function (req,res) {
    res.render('page/oos-test');
});

var minioBucket ='test';
router.use(mutipart({uploadDir:'./tmp'}));

//这里就是接受form表单请求的接口路径，请求方式为post。
router.post('/upload',mutipartMiddeware,function (req,res) {
    //这里打印可以看到接收到文件的信息。
    var data = JSON.stringify(req.files.myfile);
    var name =req.files.myfile.name;
    var path =req.files.myfile.path;
    var uploadStr='';
    minioClient.fPutObject(minioBucket, name, path, function(err, etag) {
        if((err==null||err=="null")&&(etag!=null&&etag!="null")){
            uploadStr='upload success! <br>etag:'+etag+'<br>';
            var result =[];
            var assets = [];
            var objectsStream = minioClient.listObjects(minioBucket, '', true)
            objectsStream.on('data', function(obj) {
                result .push(JSON.stringify(obj));
                var publicUrl = minioClient.protocol + '//' + minioClient.host + ':' + minioClient.port + '/' + minioBucket + '/' + obj.name
                assets.push(publicUrl);
            });
            objectsStream.on('error', function(e) {
                res.send(uploadStr+'get error!<br> error:'+e);

            });
            objectsStream.on('end', function(e) {
                res.send(uploadStr+'get success!<br> obj:<br>'+result+'<br>assets:<br>'+assets);

            });
        }else{
            res.send('upload error!<br> error:'+err);

        }
    })


});



module.exports = router;
