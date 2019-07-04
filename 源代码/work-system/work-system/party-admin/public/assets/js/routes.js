

// var fileSavepath = "qihsoft-1251217219.cosgz.myqcloud.com";

var path = (window.location.host).indexOf('localhost') >= 0 || (window.location.host).indexOf('127.0.0.1') >= 0?
    // '//localhost:9091' :'/admin-api/'; //'http://10.1.151.88:9291';
    '//localhost:9091' :'http://119.29.91.109:9091';

var commonV1 = path + '/v1/common';   //公共路由
var sysV1 = path + '/v1/sys';        //系统路由
var baseV1 = path + '/v1/base';      //基础路由
var otherV1 = path + '/v1/other';    //辅助路由
var unifiedV1 = path + '/v1/unified';        //统一管理api
var toolV1 = path+ '//v1/tool';        //系统工具api

/*********************工具路由************************/
var toolModule = {
    getDicFieldNameApi: toolV1 + '/getDicFieldName',//获取字典信息

    getRoleListApi: toolV1 + '/getRoleList',//获取角色信息

    getAcademyListApi: toolV1 + '/getAcademyList',//获取学院信息
    getMajorListApi:toolV1+'/getMajorList',//获取专业信息
    getAcademyCatalogueApi:toolV1+'/getAcademyCatalogue',//获取学院组织架构信息

    // savePicUrlApi: toolV1+ '/savePicUrl',//存储外网图片
    // pictureApi:toolV1+ '/picture',//图片管理
    // checkPicApi:toolV1+'/checkPicture',//检测图片识别码
    // videoApi:toolV1 + '/video',//视频管理
    // checkVideoApi:toolV1+'/checkVideo',//检测视频识别码

    checkUserApi:  toolV1+'/user/exist/',



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
    dictionaryApi: sysV1 + '/dictionary',
}

/*********************第三方配置路由************************/
var tpsModule = {
    configApi: unifiedV1 + '/config',
    ossApi: unifiedV1 + '/oss',
    optApi: sysV1 + '/config/tps',
    cosApi: commonV1 + '/cos',
}

/*********************辅助管理路由*************************/
var otherModule = {
    codeApi: otherV1 + '/code',
    frontCodeApi: otherV1 + '/front_code',
    systemApi: unifiedV1 + '/system-info',
}
/*********************公共路由*************************/
var commonModule = {
    verifyCodeApi: commonV1 + '/verify-code',         //验证码
    verifyCodePicApi: commonV1 + '/verify-code/jpg',         //验证码
    getPublicKeyApi: commonV1 + '/getPublicKey',         //公钥
    sysLoginApi: commonV1 + '/login',                      //登录
    sysAuthApi: unifiedV1 + '/system',                    //读取子系统权限
    sysVerifyCodeApi: commonV1 + '/verify_code',//获取手机验证码
    sysForgetApi: commonV1 + '/forget',//忘记密码

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
    updatePasswordApi: sysV1 + '/updatePassword',

    userInfoApi: sysV1 + '/userInfo',

}
/*********************权限模块路由*************************/
var authModule = {
    moduleApi: sysV1 + '/menu-module',               //权限模块
    menuApi: sysV1 + '/menu',                        //权限菜单
    subApi: sysV1 + '/sub-menu',                     //子菜单
    subsystemApi: sysV1 + '/menu-subsystem',         //子系统
    subAuthApi: sysV1 + '/sub-menu-auth',            //子菜单权限
}
/*********************子系统模块路由*************************/
var subsystemModule = {
    optApi: sysV1 + '/subsystem',
    statusApi: sysV1 + '/subsystem-status',
}

/*********************角色管理路由*************************/
var roleModule = {
    roleApi: sysV1 + '/role',
    subsystemApi: sysV1 + '/role-subsystem',
    authApi: sysV1 + '/role-auth',
}
/*********************基础数据************************/
var baseModule = {
    linkUserApi:baseV1 + '/linkUser',

    sysOperationRecordApi:baseV1 + '/sysOperationRecord',
    chatRecordApi:baseV1 + '/survey_record_chat',
    articleTopicApi: baseV1 + '/articleTopic',//对应api路径
    articleApi: baseV1 + '/article',//对应api路径
    majorInfoApi: baseV1 + '/majorInfo',//对应api路径
    academyInfoApi: baseV1 + '/academyInfo',//对应api路径
    branchApi:baseV1+'/branch',
    nationInfoApi:baseV1 + '/nationInfo',

    articleTypeApi: baseV1 + '/articleType',//对应api路径
    userManageApi:sysV1 + '/user',//对应api路径
    userInfoApi: sysV1+ '/userInfo',//对应api路径

    templateManageApi:baseV1 + '/template',//模板管理对应api路径
    reportManageApi:baseV1 + '/report',//思想汇报管理对应api路径
    noteApi:baseV1+'/note',//短信管理
    messageApi:baseV1+'/message',//短信管理
    userMessageApi:baseV1+'/message/user',//短信管理
    indexMessageListApi:baseV1+ '/message/user/index',//首页消息
    approvalListApi:baseV1+ '/approval',//待办事项/审核列表


    scheduleApi:baseV1 + '/schedule',

    onlineCourseApi:baseV1 + '/onlineCourse',//网络课程管理
    onlineCourseUserApi:baseV1 + '/onlineCourseUser',//网络课程管理
    chapterUserApi: baseV1 + '/chapterUser',
    sectionApi: baseV1 + '/section',
    sectionUserApi: baseV1 + '/sectionUser',
    chapterApi: baseV1 + '/chapter',

    getPictureURLApi:baseV1+'/getPictureURL',//网课图片api路径
    applicationApi:baseV1 + '/application',//对应api路径
    attendanceApi:baseV1+'/attendance',//课程考勤管理
    noticeManageApi:baseV1 + '/notice',//通知公告管理对应api路径
    meetingAttendanceApi:baseV1 + '/attendance',//会议考勤对应api路径
    meetingScheduleApi:baseV1 + '/schedule',
    vacateApi:baseV1 + '/vacate',
    meetingVacateApi:baseV1 + '/vacate',
    surveyRecordChatApi:baseV1 + '/survey_record_chat',
    growthRecordApi:baseV1 + '/growthRecord',
    examRoomApi :baseV1+'/examRoom',
    NoticeReviewApi:baseV1+'/_noticeReview',


    //作业管理系统
    classesApi:baseV1+"/classes",
    courseApi:baseV1+"/course",
    studentApi:baseV1+"/student",
    teacherApi:baseV1+"/teacher",
    teachScheduleApi:baseV1+"/teachSchedule",
    examApi:baseV1+'/exam',
    questionApi:baseV1+'/question',
    optionsApi:baseV1+'/options',
    examQuestionApi:baseV1+'/examQuestion',
    examRecordApi:baseV1+'/examRecord',
    examCandidateApi:baseV1+'/examCandidate',



};
