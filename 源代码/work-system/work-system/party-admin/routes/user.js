var express = require('express');
var router = express.Router();


router.get('/message',function (req,res) {
    res.render('userModule/userMessage');
});


router.get('/todo',function (req,res) {
    res.render('userModule/userTodo');
});
module.exports = router;