var express = require('express');
var router = express.Router();

router.get('/organizational',function (req,res) {
    res.render('base/organizational');
});
router.get('/subject',function (req,res) {
    res.render('base/subject');
});

module.exports = router;