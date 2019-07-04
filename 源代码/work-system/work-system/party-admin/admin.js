var express = require('express');
var admin = express();
var auth = require('./routes/auth');
var public = require('./routes/public');
var base = require('./routes/base');
var user = require('./routes/user');
// var desktop = require('./routes/desktop');
var my = require('./routes/my-base');

admin.set('view engine','ejs');
admin.use(express.static('public'));
admin.use(express.static('inc'));
admin.use(express.static('test'));
admin.use(express.static(__dirname+'/views'));

admin.use('/sys', auth);
admin.use('/base', base);
admin.use('/user', user);
admin.use('/', public);
admin.use('/my', my);
// admin.use('/', desktop);

admin.listen(9092);
