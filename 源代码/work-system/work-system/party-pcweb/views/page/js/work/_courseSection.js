var id = getQueryString('id');
var courseId = "", courseName = "";
var pageData = {};
$(function () {
    if (checkAuth()) {
        return false;
    }

    //渲染导航栏
    courseSectionDetail.bar();
    //渲染章节信息
    courseSectionDetail.getInfo();
    //渲染页面信息
    courseSectionDetail.getPageInfo();

})


var courseSectionDetail = {
    bar: function () {//获取导航栏
        var param = {url: baseModule.courseSectionBarApi + id};
        var request = ajax.get(param);
        request.done(function (d) {
            var section = d.result;
            //渲染location 位置div
            $("#location").empty();
            document.title = section.courseName + ">" + section.chaperName + ">" + section.name + ">章节详情";
            courseId = section.courseId;
            courseName = section.courseName;
            $("#location").html("<a href='/videoList'>网课学习</a>>" + "<a href='onlineCourseDetail?id=" + section.courseId + "'>" + section.courseName + "</a>" + ">" +
                "<a href='onlineCourseDetail?id=" + section.courseId + "&chapter=" + section.chaperId + "'>" + section.chaperName + "</a>" + ">" +
                "<a href='onlineCourseSection?id=" + section.id + "'>" + section.name + "</a>" + "><a href='#'>章节详情</a>");
            //渲染title标题div

            $("#title").empty();
            $("#title").html("<h2>" + section.name + "</h2>");


        })
    },
    getInfo: function () {//获取章节信息，反作弊校验
        var param = {url: baseModule.courseSectionApi + id};
        var request = ajax.get(param);
        request.done(function (d) {
            var section = d.result;

            $("#content").empty();//将内容div初始化

            //根据类型渲染章节信息 type =1 为图文 type =2为视频
            if (section.type == 1) {//渲染图文章节
                var min = 0, sec = 0;
                var flag = true;

                //渲染章节详情
                $("#content").html(section.content);

                //根据文章长度获取存储进度的时间，目前为一秒50字
                var readTime = parseInt(section.content.length / 50);
                var readMin = parseInt(readTime / 60);
                var readSec = parseInt(readTime % 60);

                //获取用户章节进度
                var param = {url: baseModule.sectionNoteGetApi + section.id};
                var request = ajax.post(param);
                request.done(function (d) {
                    var noteData = d.result;
                    if (noteData != null && noteData != "") {
                        flag = false;//已完成，不进行反作弊校验
                        $("#tips").empty();
                        $("#tips").html("本节学习完成，可以继续学习其他章节或课程！");
                        courseSectionDetail.getPageInfo();//渲染上下章信息

                    } else {

                        //图文章节反作弊开始
                        //记录用户停留时间
                        window.setInterval(function () {
                            sec++;
                            if (sec == 60) {
                                min++;
                            }
                            if (sec < readTime) {
                                $("#tips").empty();
                                $("#tips").html("当前浏览时间：<font color='red'>" + min + "分" + (sec % 60) + "秒</font>，需要浏览<font color='red'>" + readMin + "分" + readSec + "秒</font>方可提交进度,完成本章节学习！");

                            }
                        }, 1000);

                        //监听用户是否浏览到最下面
                        window.onscroll = function () {
                            if (flag) {
                                //监听事件内容
                                if ((getScrollHeight() == getWindowHeight() + getDocumentTop()) && flag) {
                                    if (sec < readTime) {
                                        //  tips.info("当前浏览时间："+min+"分"+(sec%60)+"秒，需要浏览"+readMin+"分"+readSec+"秒方可提交进度！");
                                    }
                                }
                            }
                        }

                        //当用户停留时间达到进度时间时自动提交
                        window.setTimeout(function () {
                            var json = {};
                            json.sectionCode = section.id;
                            json.isFinish = 2;
                            json.note = "100";
                            json.learnPercent = 100;
                            var param = {url: baseModule.sectionNoteSaveApi, data: json};
                            var request = ajax.post(param);
                            request.done(function (d) {
                                //  console.log(d);
                                var result = d.result;
                                var status = result.status;
                                if (status == 0) {
                                    // console.log("进度记录成功！");
                                    courseSectionDetail.getPageInfo();//渲染上下章信息
                                    // window.location.reload();
                                    $("#tips").empty();
                                    $("#tips").html("本节学习完成，可以继续学习其他章节或课程！");
                                    tips.ok("本节学习完成，可以继续学习其他章节或课程！");
                                    flag = false;//已完成，不进行反作弊校验
                                } else {
                                    //  console.log("进度记录异常！");
                                }
                            })
                            return false;

                        }, (readTime * 1000));

                        //未完成时离开界面会进行提示

                        window.onbeforeunload = function (e) {
                            e = e || window.event;

                            // 兼容IE8和Firefox 4之前的版本
                            if (e) {
                                if ((sec < readTime) && flag) {
                                    e.returnValue = "当前浏览时间：" + min + "分" + (sec % 60) + "秒，需要浏览" + readMin + "分" + readSec + "秒方可提交进度奥，确定离开此页面吗？";
                                }
                            }

                            // Chrome, Safari, Firefox 4+, Opera 12+ , IE 9+
                            if ((sec < readTime) && flag) {
                                return "当前浏览时间：" + min + "分" + (sec % 60) + "秒，需要浏览" + readMin + "分" + readSec + "秒方可提交进度奥，确定离开此页面吗,？";
                            }
                        };


                        //图文章节反作弊结束


                    }
                })

            } else if (section.type == 2) {//渲染视频章节
                var flag = true;
                //渲染章节详情
                $("#content").html(section.content);
                var json = {};
                json.sectionCode = section.id;
                json.isFinish = 1;

                //屏蔽视频右键菜单
                $('#video').bind('contextmenu', function () {
                    return false;
                });

                //视频插件初始化设置
                var dp = new DPlayer({
                    container: document.getElementById('video'),//包含视频的元素：video
                    autoplay: false,//自动播放：默认禁止
                    hotkey: false,//热键:默认禁止
                    theme: '#FADFA3',//主题色：默认
                    loop: false,//视频循环播放:默认禁止
                    preload: 'auto',//预加载，可选值: 'none', 'metadata', 'auto'
                    lang: 'zh-cn',//语言，可选值: 'en', 'zh-cn', 'zh-tw'
                    logo: '../login/images/soft.png',//logo照片
                    mutex: true,//一个页面只允许一个播放
                    volume: 0.7,//音量大小设置
                    contextmenu: [//设置右键菜单  link为链接，click为点击信息
                        {
                            text: '作业管理系统',
                            link: '../'
                        },
                        {
                            text: ' Copyright@启航训练营',
                            link: '#'
                        },
                        /*  {
                              text: '关于我们',
                              click:function(e){e.infoPanel.triggle()
                          }},*/

                    ],
                    video: {
                        //pic:'url',//视频封面url
                        //thumbnails:'url',//视频缩略图url
                        url: section.url,//视频url
                        type: 'auto',//视频类型，可选值: 'auto', 'hls', 'flv', 'dash', 'webtorrent', 'normal' 或其他自定义类型
                    },

                });

                //隐藏页面全屏
                $('#dplayer-full-in-icon').empty();
                //屏蔽留言框
                document.getElementById('dplayer-controller-mask').className = "";


                var last = 0;
                //获取视频章节进度
                var param = {url: baseModule.sectionNoteGetApi + section.id};
                var request = ajax.post(param);
                request.done(function (d) {
                    var noteData = d.result;
                    if (noteData != null && noteData != "") {
                        dp.video.currentTime = parseInt(noteData.note);//同步播放器进度
                        last = parseInt(noteData.note);
                        if (noteData.isFinish == 2) {
                            // window.location.reload();
                            courseSectionDetail.getPageInfo();//渲染上下章信息
                            $("#tips").empty();
                            $("#tips").html("本节学习完成，可以继续学习其他章节或课程！");
                            flag = false;//已完成，不进行反作弊校验
                        } else {
                            videoCheck();
                        }
                    } else {
                        videoCheck();
                    }
                })


                //视频反作弊校验算法
                function videoCheck() {
                    //隐藏设置栏
                    $('#dplayer-setting').empty();
                    //隐藏进度条
                    $('#dplayer-bar-wrap').empty();
                    document.getElementById('dplayer-bar-wrap').className = "";
                    var video = dp.video;
                    video.controls = false; //屏蔽设置栏

                    var min = '', sec = '', readMin = '', readSec = '', readTime = '';


                    //监听视频进度更新函数
                    video.ontimeupdate = function () {
                        var current = video.currentTime;
                        var duration = video.duration;
                        readTime = duration;
                        min = parseInt(current / 60);
                        sec = parseInt(current % 60);
                        readMin = parseInt(duration / 60);
                        readSec = parseInt(duration % 60);


                        $("#tips").empty();
                        $("#tips").html("当前播放时间：<font color='red'>" + min + "分" + (sec % 60) + "秒</font>，需要播放<font color='red'>" + readMin + "分" + readSec + "秒</font>方可提交进度,完成本章节学习！");


                        //视频进度提交函数
                        //1、每30秒提交一次进度
                        //2、视频完成时提交进度
                        //3、当用户向前推动进度条时，不会向后台记录进度
                        //4、当用户向后推动进度条时，自动回滚进度，不会向后台记录进度
                        //5、刷新界面时，自动同步进度
                        if ((parseInt(current + 1) % 30 == 0 || video.ended) && flag && (current - last > 0.0)) {
                            json.note = video.currentTime;
                            if (video.ended) {
                                json.isFinish = 2;
                            } else {
                                json.isFinish = 1;
                            }
                            var percent = (video.currentTime / video.duration) * 100;
                            json.learnPercent = percent.toFixed(2);
                            var param = {url: baseModule.sectionNoteSaveApi, data: json};
                            var request = ajax.post(param);
                            request.done(function (d) {
                                //   console.log(d);
                                var result = d.result;
                                var status = result.status;
                                if (status == 0) {
                                    //  console.log("进度记录成功！");
                                } else {
                                    //  console.log("进度记录异常！");
                                }

                                if (json.isFinish == 2) {
                                    //  window.location.reload();
                                    courseSectionDetail.getPageInfo();
                                    $("#tips").empty();
                                    $("#tips").html("本节学习完成，可以继续学习其他章节或课程！");
                                    tips.ok("本节学习完成，可以继续学习其他章节或课程！");
                                    flag = false;//已完成，不进行反作弊校验
                                }
                            })
                            sleep(1000);//因视频进度更新为微秒为单位，提交进度后休眠1秒后再执行反作弊校验，否则会多次提交进度
                        }

                        //当每次播放时间大于进度时间2秒时，视为异常，自动回滚
                        if (current - last > 2) {
                            video.currentTime = last;
                        } else if (current - last > 0.0) {
                            last = current;
                        }

                        //休眠函数，该时间内执行函数永真
                        function sleep(delay) {
                            var start = (new Date()).getTime();
                            while ((new Date()).getTime() - start < delay) {
                                continue;
                            }
                        }

                    };

                    window.onbeforeunload = function (e) {
                        e = e || window.event;
                        // 兼容IE8和Firefox 4之前的版本
                        if (e) {
                            if (sec < readTime) {
                                e.returnValue = "当前浏览时间：" + min + "分" + (sec % 60) + "秒，需要播放" + readMin + "分" + readSec + "秒方可提交进度奥，确定离开此页面吗？";
                            }
                        }

                        // Chrome, Safari, Firefox 4+, Opera 12+ , IE 9+
                        if (sec < readTime) {
                            return "当前浏览时间：" + min + "分" + (sec % 60) + "秒，需要需要播放" + readMin + "分" + readSec + "秒方可提交进度奥，确定离开此页面吗,？";
                        }
                    };

                    var div = document.getElementById('video');

                    video.addEventListener('play', function () {

                        //监听视频区域的鼠标移动时间，鼠标离开视频区域自动停止，鼠标返回时自动播放
                        div.addEventListener("mouseleave", function () {
                            if (!video.paused) {
                                video.pause();
                            }
                        });
                        /*  div.addEventListener("mouseover", function () {
                               if (video.paused) {
                                   video.play();
                               }
                           });*/


                    });

                }


            }

        })
    },
    getPageInfo: function () {//获取上下章
        //获取上下章信息
        var param = {url: baseModule.courseSectionPageApi + id};
        var request = ajax.get(param);
        request.done(function (d) {
            var page = d.result;
            var pageUpButton = "", pageDownButton = "";
            var pageCurrent = page.pageCurrent;
            if (pageCurrent != "" && pageCurrent != null) {
                var pageUp = page.pageUp;
                var pageDown = page.pageDown;


                //  上一章节不显示
                if (pageCurrent.sectionStart != 1) {
                    if (pageUp.sectionName.length > 25) {
                        pageUp.sectionName = pageUp.sectionName.substr(0, 25) + "...";
                    }
                    pageUpButton = "<a href='/onlineCourseSection?id=" + pageUp.sectionId + "'style=\"color: #2B2B2B;\">上一节：" + pageUp.sectionName + "</a>&emsp;";
                } else {
                    if (pageCurrent.chapterStart != 1) {
                        if (pageUp.chapterName.length > 25) {
                            pageUp.chapterName = pageUp.chapterName.substr(0, 25) + "...";
                        }
                        //上一章最后一节
                        pageUpButton = "<a href='/onlineCourseSection?id=" + pageUp.sectionId + "'style=\"color: #2B2B2B;\">上一章：【" + pageUp.chapterName + "】</a>&emsp;";
                    } else {
                        //当前页为第一章第一节,故没有上一章
                    }
                }


                if (pageCurrent.sectionEnd != 1) {
                    //  $("#tips").html("本节学习完成，开启下一节学习！");
                    if (pageDown.sectionName.length > 25) {
                        pageDown.sectionName = pageDown.sectionName.substr(0, 25) + "...";
                    }
                    pageDownButton = "<a href='/onlineCourseSection?id=" + pageDown.sectionId + "' style=\"color: #2B2B2B;\">下一节：" + pageDown.sectionName + "</a>&emsp; ";
                } else {
                    if (pageCurrent.chapterEnd != 1) {
                        if (pageDown.chapterName.length > 25) {
                            pageDown.chapterName = pageDown.chapterName.substr(0, 25) + "...";
                        }
                        // $("#tips").html("学习完成，当前章节为本章最后一节，开启下一章节学习！");
                        //下一章第一节
                        pageDownButton = "<a href='/onlineCourseSection?id=" + pageDown.sectionId + "'style=\"color: #2B2B2B;\">下一章：【" + pageDown.chapterName + "】</a>&emsp;";
                    } else {

                        //当前页为最后一章最后一节,故没有下一章
                        //   $("#tips").html("学习完成，当前章节为本课程最后一节，可返回目录查看其他课程！");

                        pageDownButton = "<a href='onlineCourseDetail?id=" + courseId + "' style=\"color: #2B2B2B;\">返回目录</a>&emsp;";
                    }
                }

                $("#pageButton").empty();

                //渲染上下节按钮
                $("#pageButton").html(
                    "<span style='float: left'>" + pageUpButton + "</span>" +
                    "<span  style='float: right'>" + pageDownButton + "</span>"
                );
            }
        })
    },
}


//根据参数获取值
function getQueryString(key) {
    var reg = new RegExp("(^|&)" + key + "=([^&]*)(&|$)");
    var result = window.location.search.substr(1).match(reg);
    return result ? decodeURIComponent(result[2]) : null;
}


//获取文档高度
function getDocumentTop() {
    var scrollTop = 0, bodyScrollTop = 0, documentScrollTop = 0;
    if (document.body) {
        bodyScrollTop = document.body.scrollTop;
    }
    if (document.documentElement) {
        documentScrollTop = document.documentElement.scrollTop;
    }
    scrollTop = (bodyScrollTop - documentScrollTop > 0) ? bodyScrollTop : documentScrollTop;
    return scrollTop;
}

//获取可视窗口高度
function getWindowHeight() {
    var windowHeight = 0;
    if (document.compatMode == "CSS1Compat") {
        windowHeight = document.documentElement.clientHeight;
    } else {
        windowHeight = document.body.clientHeight;
    }
    return windowHeight;
}


//获取滚动条滚动高度
function getScrollHeight() {
    var scrollHeight = 0, bodyScrollHeight = 0, documentScrollHeight = 0;
    if (document.body) {
        bodyScrollHeight = document.body.scrollHeight;
    }
    if (document.documentElement) {
        documentScrollHeight = document.documentElement.scrollHeight;
    }
    scrollHeight = (bodyScrollHeight - documentScrollHeight > 0) ? bodyScrollHeight : documentScrollHeight;
    return scrollHeight;
}
