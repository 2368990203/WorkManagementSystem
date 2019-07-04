

var fileSavepath = "qihsoft-1251217219.cosgz.myqcloud.com";

var path = (window.location.host).indexOf('localhost') >= 0 || (window.location.host).indexOf('127.0.0.1') >= 0?
  // '//localhost:9191' :'/api/'; //'http://10.1.151.88:9291';
    '//localhost:9191' :'http://119.29.91.109:9191';

var commonV1 = path + '/v1/common';   //公共路由
var sysV1 = path + '/v1/sys';        //系统路由
var baseV1 = path + '/v1/base';      //基础路由
var unifiedV1 = path + '/v1/unified';        //统一管理api
var toolV1 = path + '/v1/tool';        //系统工具api

/*********************工具路由************************/
var toolModule = {
    savePicUrlApi: toolV1+ '/savePicUrl',
    checkUserApi:  toolV1+'/user/exist/',
    pictureApi:toolV1 + '/picture',//照片管理

}

/*********************上传路由************************/
var uploadModule = {
    baseApi: unifiedV1 + '/oss/resources',
    fileApi: unifiedV1 + '/oss/resources/file',
    base64Api: path + '/v1/upload/base64'
}

/*********************系统相关配置路由************************/
var globalModule = {
    subsystemApi: sysV1 + '/config-subsystem',
    optApi: sysV1 + '/config/global',
    categoryApi: sysV1 + '/category',
}

/*********************第三方配置路由************************/
var tpsModule = {
    configApi: unifiedV1 + '/config',
    ossApi: unifiedV1 + '/oss',
    optApi: sysV1 + '/config/tps',
    cosApi: commonV1 + '/cos',
}

/*********************用户模块路由*************************/
var userModule = {
    lock: unifiedV1 + '/user-lock-system/sys-config',   //操作界面锁
    loginOutApi: unifiedV1 + '/user-login-out',         //退出系统
    cacheUpdateApi: unifiedV1 + '/user-cache',
    userApi: sysV1 + '/user',
    userTokenApi: unifiedV1 + '/user-token',
    userPwdApi: sysV1 + '/user-pwd',
    userStatusApi: sysV1 + '/user-status',
    userAuthApi: sysV1 + '/user-system',
    userRoleChangeApi: sysV1 + '/change-role',
}

/*********************公共路由*************************/
var commonModule = {
    getPublicKeyApi: commonV1 + '/getPublicKey',         //公钥
    verifyCodeApi: commonV1 + '/verify-code',         //验证码
    sysLoginApi: commonV1 + '/login',                      //登录
    sysAuthApi: unifiedV1 + '/system' ,                    //读取子系统权限
    departApi: sysV1 + '/depart',   //读取组织机构
    indexNoticeListApi:commonV1 + '/index/notice',//首页公告
    indexNewsListApi:commonV1 + '/index/news',//首页新闻
    indexVideoListApi:commonV1 + '/index',//首页网课
    indexMessageListApi:baseV1+ '/message/index',//首页消息
    sysVerifyCodeApi: commonV1 + '/verify_code',//获取手机验证码
    sysForgetApi: commonV1 + '/forget',//忘记密码
}

/*********************基础数据************************/
var baseModule = {
    messageListApi:baseV1+ '/message/',//首页消息
    userApi: baseV1+ '/user',
    userInfoApi: baseV1 + '/userInfo',//对应api路径
    userBaseInfoApi: baseV1 + '/userBaseInfo',//用户基本信息
    userDetailInfoApi: baseV1 + '/userDetailInfo',//用户详细信息
    userDetailApi:baseV1+'/userDetailInfo',//用户详情
    branchInfoApi: baseV1 + '/branchInfo',//党支部信息
    majorInfoApi: baseV1 + '/majorInfo',//专业信息
    academyInfoApi: baseV1 + '/academyInfo',//学院信息
    nationInfoApi: baseV1 + '/nationInfo',//民族信息

    examQuestionApi:baseV1 + '/examQuestion',//作业
    examInfoApi:baseV1 + '/exam/info',//作业详情
    examListApi:baseV1 + '/exam/depart',//作业详情
    examRecordApi:baseV1 + '/examRecord',//答题记录
    examCandidateApi:baseV1+'/examCandidate',//考试
    examCodeApi:baseV1+'/examCandidate/checkUnsealCode',//考试启封码

    hitsApi:commonV1 + '/news',//新闻列表
    newsListApi:commonV1 + '/news',//新闻列表
    newsDetailsApi:commonV1+ '/news',//新闻详情
    newsLinkApi:commonV1 + '/news/getLink/',//新闻上下页
    noticeListApi:commonV1 + '/notice',//通知公告
    noticeDetailApi:commonV1 + '/notice',//通知详情
    noticeLinkApi:commonV1+ '/notice/getLink/',//通知上下页
    templateApi:commonV1 + '/template',//模板获取



    reportApi:baseV1+'/report',//思想汇报列表
    linkUserApi:baseV1+'/linkUser',//入党培养联系人
    applicationApi:baseV1 + '/application',//入党申请书提交管理

    meetingApi:baseV1 + '/meeting',//会议管理
    courseArrangement:baseV1 + '/Schedule',//集中学习
    courseArrangementDetail:baseV1 + '/meeting',//集中学习
    vacateApi:baseV1 + '/vacate',//请假


    onlineCourseApi:baseV1 + '/onlineCourse',//网络课程
    videoListApi:baseV1 +'/onlineCourse',//网课列表
    onlineCourseCatalogueApi:baseV1 +'/onlineCourse/getCatalogue/',//网课章节目录
    courseSectionApi:baseV1 +'/section/getById/',//网课章节列表
    courseSectionBarApi:baseV1 +'/section/getBarById/',//网课章节导航
    courseSectionPageApi:baseV1 +'/section/getLinkById/',//网课章节页码
    sectionNoteSaveApi:baseV1 +'/sectionUser',//网课进度存储
    sectionNoteGetApi:baseV1 +'/sectionUser/getNote/',//网课进度查询

    surveyRecordGetPageListApi:baseV1+'/survey_record_chat_user/',//考察谈话列表
    surveyRecordGetUserListApi:baseV1+'/survey_record_chat_user/list/',//考察谈话列表
    surveyRecordGetUserInfoApi:baseV1+'/survey_record_chat_user/info/',//考察谈话信息



}
/*********************辅助管理路由*************************/
/*var otherModule = {
    codeApi: otherV1 + '/code',
    systemApi: unifiedV1 + '/system-info',
}*/


/*********************权限模块路由*************************/
/*var authModule = {
    moduleApi: sysV1 + '/menu-module',               //权限模块
    menuApi: sysV1 + '/menu',                        //权限菜单
    subApi: sysV1 + '/sub-menu',                     //子菜单
    subsystemApi: sysV1 + '/menu-subsystem',         //子系统
    subAuthApi: sysV1 + '/sub-menu-auth',            //子菜单权限
}*/
/*********************子系统模块路由*************************/
/*var subsystemModule = {
    optApi: sysV1 + '/subsystem',
    statusApi: sysV1 + '/subsystem-status',
}*/

/*********************角色管理路由*************************/
/*var roleModule = {
    roleApi: sysV1 + '/role',
    subsystemApi: sysV1 + '/role-subsystem',
    authApi: sysV1 + '/role-auth',
}*/

/*********************工作台************************/

/*
var myModule = {
    classApi: sysV1 + '/my-class',
    studentApi: sysV1 + '/my-student',
    articleApi: sysV1 + '/article',
    categoryApi: sysV1 + '/category',
}*/
