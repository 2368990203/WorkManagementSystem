var express = require('express');
var router = express.Router();

//章节管理
router.get('/core/chapterSection',function (req,res) {
    res.render('baseModule/chapterSection');
});

//谈话记录管理
router.get('/core/chatRecord',function (req,res) {
    res.render('baseModule/chatRecord');
});
//考察记录管理
router.get('/core/surveyRecordChat',function (req,res) {
    res.render('baseModule/surveyRecordChat');
});
//会议请假
router.get('/core/meetingVacate',function (req,res) {
    res.render('baseModule/meetingVacate');
});
//课程请假
router.get('/core/vacate',function (req,res) {
    res.render('baseModule/vacate');
});
//会议安排
router.get('/core/meetingSchedule',function (req,res) {
    res.render('baseModule/meetingSchedule');
});
//入党申请书管理
router.get('/core/application',function (req,res) {
    res.render('baseModule/application');
});
//课程安排
router.get('/core/schedule',function (req,res) {
    res.render('baseModule/schedule');
});
//修改密码
router.get('/core/updatePassword',function (req,res) {
    res.render('userModule/updatePassword');
});
//查看个人信息
router.get('/core/personalInfo',function (req,res) {
    res.render('userModule/personalInfo');
});

//数据字典
router.get('/core/dictionary',function (req,res) {
    res.render('sysModule/dictionary');
});

//操作记录
router.get('/core/operationRecord',function (req,res) {
    res.render('sysModule/operationRecord');
});

router.get('/core/news',function (req,res) {//对应前端路由
    res.render('newsModule/news');//对应ejs文件
});

router.get('/core/newsTopic',function (req,res) {//对应前端路由
    res.render('newsModule/newsTopic');//对应ejs文件
});

router.get('/core/newsType',function (req,res) {//对应前端路由
    res.render('newsModule/newsType');//对应ejs文件
});

//学院信息管理
router.get('/core/academyInfo',function (req,res) {//对应前端路由
    res.render('baseModule/academyInfo');//对应ejs文件
});

//专业信息
router.get('/core/majorInfo',function (req,res) {//对应前端路由
    res.render('baseModule/majorInfo');//对应ejs文件
});
//用户管理
router.get('/core/userManage',function (req,res) {//人员管理对应前端菜单权限配置的路由
    res.render('userModule/userManage');//对应ejs文件
});
//用户信息
router.get('/core/userInfo',function (req,res) {//人员管理对应前端菜单权限配置的路由
    res.render('userModule/userInfo');//对应ejs文件
});
router.get('/core/template',function (req,res) {//模板管理对应前端菜单权限配置的路由
    res.render('baseModule/template');//对应ejs文件
});

router.get('/core/report',function (req,res) {//思想汇报管理对应前端菜单权限配置的路由
    res.render('baseModule/report');//对应ejs文件
});
router.get('/core/courseAttendance',function (req,res) {//课程考勤管理对应前端菜单权限配置的路由
    res.render('baseModule/courseAttendance');//对应ejs文件
});

router.get('/core/notice',function (req,res) {//通知公告对应前端菜单权限配置的路由
    res.render('baseModule/notice');//对应ejs文件
});

router.get('/core/meetingAttendance',function (req,res) {
    res.render('baseModule/meetingAttendance');
});

router.get('/core/growthRecord',function (req,res) {
    res.render('baseModule/growthRecord');
});

router.get('/core/note',function (req,res) {
    res.render('sysModule/note');
});

router.get('/core/message',function (req,res) {
    res.render('sysModule/message');
});


router.get('/core/nationInfo',function (req,res) {
    res.render('baseModule/nationInfo');
});

router.get('/core/question',function (req,res) {
    res.render('baseModule/question');
});
router.get('/core/noticeReview',function (req,res) {
    res.render('baseModule/noticeReview');//通知公告评论点赞
});

router.get('/core/exam',function (req,res) {
    res.render('baseModule/exam');
});

router.get('/core/exam_question',function (req,res) {
    res.render('baseModule/examQuestion');
});

router.get('/core/examRoom',function (req,res) {
    res.render('baseModule/examRoom');
});
router.get('/core/examStudent',function (req,res) {
    res.render('baseModule/examStudent');
});

router.get('/core/onlineCourse',function (req,res) {
    res.render('baseModule/onlineCourse');
});

router.get('/core/examPaperGenerate',function (req,res) {
    res.render('baseModule/examPaperGenerate');
});

router.get('/core/branch',function (req,res) {
    res.render('baseModule/branch');
});

//联络员管理
router.get('/core/linkUser',function (req,res) {
    res.render('baseModule/linkUser');
});


//作业管理系统
//班级管理
router.get('/core/classes',function (req,res) {
    res.render('baseModule/classes');
});

//课程管理
router.get('/core/course',function (req,res) {
    res.render('baseModule/course');
});


//学生管理
router.get('/core/student',function (req,res) {
    res.render('userModule/student');
});
//教师管理
router.get('/core/teacher',function (req,res) {
    res.render('userModule/teacher');
});


//授课管理
router.get('/core/teachSchedule',function (req,res) {
    res.render('baseModule/teachSchedule');
});

module.exports = router;
