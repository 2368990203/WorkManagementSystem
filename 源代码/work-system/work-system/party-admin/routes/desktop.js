var express = require('express');
var router = express.Router();

router.get('/',function (req,res) {
    res.render('main');
});
router.get('/desktop',function (req,res) {
    res.render('desktop');
});

module.exports = router;