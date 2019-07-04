
var express = require('express');
var router = express.Router();

//公共功能

// //首页
// router.get('/',function (req,res) {
//     res.render('page/pcwebIndex');
// });
//首页
router.get('/',function (req,res) {
    res.render('page/ejs/examList');
});

//消息列表
router.get('/message',function (req,res) {
    res.render('page/ejs/message');
});
//消息详情
router.get('/messageDetail',function (req,res) {
    res.render('page/ejs/messageDetail');
});


//登录
router.get('/login',function (req,res) {
    res.render('page/pcwebLogin');
});


//登录
router.get('/loginTest',function (req,res) {
    res.render('page/pcwebLoginTest');
});

//找回密码
router.get('/forgetPassword',function (req,res) {
    res.render('page/forgetPassword');
});

//新闻列表
router.get('/newsList',function (req,res) {
    res.render('page/ejs/newsList');
});

//新闻详情
router.get('/newsDetail',function (req,res) {
    res.render('page/ejs/newsDetail');
});

//通知公告列表
router.get('/noticeList',function (req,res) {
    res.render('page/ejs/noticeList');
});

//通知公告详情
router.get('/noticeDetail',function (req,res) {
    res.render('page/ejs/noticeDetail');
});

//入党流程
router.get('/enterProcess',function (req,res) {
    res.render('page/ejs/enterProcess');
});


//授权功能
//作业试卷列表
router.get('/examList',function (req,res) {
    res.render('page/ejs/examList');
});

//作业
router.get('/exam',function (req,res) {
    res.render('page/ejs/exam');
});

//作业须知
router.get('/examTips',function (req,res) {
    res.render('page/ejs/examTips');
});


//作业答题记录
router.get('/examRecord',function (req,res) {
    res.render('page/ejs/examRecord');
});

//入党申请书模板
router.get('/applicationTemplate',function (req,res) {
    res.render('page/ejs/applicationTemplate');
});

//入党申请书上传管理
router.get('/applicationManage',function (req,res) {
    res.render('page/ejs/applicationManage');
});


//集中学习列表
router.get('/courseArrangementList',function (req,res) {
    res.render('page/ejs/courseArrangementList');
});

//集中学习详情
router.get('/courseArrangementDetail',function (req,res) {
    res.render('page/ejs/courseArrangementDetail');
});

//个人信息详情
router.get('/userInfo',function (req,res) {
    res.render('page/ejs/userInfo');
});


//个人信息修改
router.get('/updateUserInfo',function (req,res) {
    res.render('page/ejs/updateUserInfo');
});



//思想汇报模板
router.get('/thoughReportTemplate',function (req,res) {
    res.render('page/ejs/thoughReportTemplate');
});

//思想汇报管理
router.get('/thoughReportManage',function (req,res) {
    res.render('page/ejs/thoughReportManage');
});

//思想报告
router.get('/thoughReportList',function (req,res) {
    res.render('page/ejs/thoughReportList');
});


//考察报告
router.get('/surveyRecordList',function (req,res) {
    res.render('page/ejs/surveyRecordList');
});

//考察报告详情
router.get('/surveyRecordDetail',function (req,res) {
    res.render('page/ejs/surveyRecordDetail');
});

//谈话记录
router.get('/chatList',function (req,res) {
    res.render('page/ejs/chatList');
});

//谈话记录详情
router.get('/chatDetail',function (req,res) {
    res.render('page/ejs/chatDetail');
});


//网络课程详情
router.get('/onlineCourseDetail',function (req,res) {
    res.render('page/ejs/onlineCourseDetail');
});

//网络课程章节详情
router.get('/onlineCourseSection',function (req,res) {
    res.render('page/ejs/onlineCourseSection');
});

//网络课程列表
router.get('/videoList',function (req,res) {
    res.render('page/ejs/videoList');
});

//会议列表管理
router.get('/meetingList',function (req,res) {
    res.render('page/ejs/meetingList');
});

//会议详情管理
router.get('/meetingDetail',function (req,res) {
    res.render('page/ejs/meetingDetail');
});


module.exports = router;
