var express = require('express');
var pcweb = express();
var public = require('./routes/public');
//var oos = require('./routes/oos');
pcweb.set('view engine','ejs');
pcweb.use(express.static('public'));
pcweb.use(express.static('inc'));
pcweb.use(express.static('test'));
pcweb.use(express.static(__dirname+'/views'));

pcweb.use('/', public);


//pcweb.use('/oos', oos);


pcweb.listen(9192);
module.exports = pcweb;