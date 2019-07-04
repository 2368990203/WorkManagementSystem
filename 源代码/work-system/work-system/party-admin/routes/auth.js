

var express = require('express');
var router = express.Router();

//首页
router.get('/',function (req,res) {
    res.render('index');
});
//菜单管理
router.get('/menu',function (req,res) {
    res.render('menu');
});
//子系统管理
router.get('/subsystem',function (req,res) {
    res.render('subsystem');
});
//欢迎页
router.get('/welcome',function (req,res) {
    res.render('welcome');
});
//代码生成器
router.get('/code',function (req,res) {
    res.render('code');
});
//前端代码生成器
router.get('/front_code',function (req,res) {
    res.render('front_code');
});
//表单模板构建器
router.get('/component',function (req,res) {
    res.render('component');
});
//组织机构管理  lw180716新增
router.get('/depart',function (req,res) {
    res.render('depart');
});
//角色管理
router.get('/role',function (req,res) {
    res.render('role');
});
// 用户管理
router.get('/user',function (req,res) {
    res.render('user');
});
//OSS配置
router.get('/oss',function (req,res) {
    res.render('oss');
});

//COS配置
router.get('/cos',function (req,res) {
    res.render('cos');
});

//系统基础配置
router.get('/config',function (req,res) {
    res.render('config');
});
//核心数据配置
router.get('/base-category',function (req,res) {
    res.render('base-category');
});
//基础数据配置
router.get('/category',function (req,res) {
    res.render('category');
});
module.exports = router;
