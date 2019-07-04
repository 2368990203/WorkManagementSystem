/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50640
Source Host           : localhost:3306
Source Database       : work_system

Target Server Type    : MYSQL
Target Server Version : 50640
File Encoding         : 65001

Date: 2019-07-01 22:25:34
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `academy_info`
-- ----------------------------
DROP TABLE IF EXISTS `academy_info`;
CREATE TABLE `academy_info` (
  `id` bigint(20) NOT NULL,
  `code` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '学院代码',
  `name` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '学院名称',
  `status` tinyint(1) DEFAULT NULL COMMENT '状态:0-有效,1-无效',
  `campus` tinyint(1) DEFAULT NULL COMMENT '所在校区:1-东校区,2-西校区，3-武鸣校区',
  `creator` bigint(20) DEFAULT '0' COMMENT '创建人',
  `createTime` int(11) unsigned DEFAULT '0' COMMENT '创建时间',
  `reviser` bigint(20) DEFAULT '0' COMMENT '修改人',
  `reviseTime` int(11) unsigned DEFAULT '0' COMMENT '修改时间',
  `isDel` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除状态:0-有效，1-已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='312 学院信息表';

-- ----------------------------
-- Records of academy_info
-- ----------------------------
INSERT INTO `academy_info` VALUES ('3051990607559285498', '024', '相思湖学院', '0', '2', '1003866568974705484', '1554948449', '0', '0', '0');
INSERT INTO `academy_info` VALUES ('3051991603172351484', '021', '国际教育学院', '0', '1', '1003866568974705484', '1554948448', '0', '0', '0');
INSERT INTO `academy_info` VALUES ('3051998596487126600', '011', '东南亚语言文化学院', '0', '1', '1003866568974705484', '1554948447', '0', '0', '0');
INSERT INTO `academy_info` VALUES ('3051998602322223761', '015', '信息科学与工程学院', '0', '1', '1003866568974705484', '1554948448', '0', '0', '0');
INSERT INTO `academy_info` VALUES ('3051999600321864727', '017', '管理学院', '0', '1', '1003866568974705484', '1554948448', '0', '0', '0');
INSERT INTO `academy_info` VALUES ('3052999604817208689', '018', '艺术学院', '0', '2', '1003866568974705484', '1554948448', '0', '0', '0');
INSERT INTO `academy_info` VALUES ('3053991599568692728', '005', '法学院', '0', '1', '1003866568974705484', '1554948447', '0', '0', '0');
INSERT INTO `academy_info` VALUES ('3053998593521827295', '009', '文学院', '0', '1', '1003866568974705484', '1554948447', '0', '0', '0');
INSERT INTO `academy_info` VALUES ('3054998597020759168', '010', '外国语学院', '0', '1', '1003866568974705484', '1554948447', '0', '0', '0');
INSERT INTO `academy_info` VALUES ('3055991595044230987', '006', '民族学与社会学学院', '0', '1', '1003866568974705484', '1554948447', '0', '0', '0');
INSERT INTO `academy_info` VALUES ('3055996608283964728', '019', '传媒学院', '0', '2', '1003866568974705484', '1554948448', '0', '0', '0');
INSERT INTO `academy_info` VALUES ('3055997604082324141', '023', '预科教育学院', '0', '2', '1003866568974705484', '1554948449', '0', '0', '0');
INSERT INTO `academy_info` VALUES ('3055999591636943846', '003', '商学院', '0', '1', '1003866568974705484', '1554948446', '0', '0', '0');
INSERT INTO `academy_info` VALUES ('3056214229059682045', '888', '测试学院', '0', '1', '1003866568974705484', '1558336762', '0', '0', '0');
INSERT INTO `academy_info` VALUES ('3057814705785803052', '999', '党委组织部', '0', '1', '1003866568974705484', '1558004998', '0', '0', '0');
INSERT INTO `academy_info` VALUES ('3057991591926520523', '012', '理学院', '0', '1', '1003866568974705484', '1554948447', '0', '0', '0');
INSERT INTO `academy_info` VALUES ('3057992595103705882', '004', '中英学院', '0', '1', '1003866568974705484', '1554948447', '0', '0', '0');
INSERT INTO `academy_info` VALUES ('3057995598133633367', '002', '马克思主义学院', '0', '1', '1003866568974705484', '1554948446', '0', '0', '0');
INSERT INTO `academy_info` VALUES ('3057995603738957564', '020', '东盟学院', '0', '2', '1003866568974705484', '1554948448', '0', '0', '0');
INSERT INTO `academy_info` VALUES ('3058990592660676004', '001', '政治与公共管理学院', '0', '1', '1003866568974705484', '1554948446', '0', '0', '0');
INSERT INTO `academy_info` VALUES ('3058996600404871486', '013', '化学化工学院', '0', '2', '1003866568974705484', '1554948448', '0', '0', '0');
INSERT INTO `academy_info` VALUES ('3058998607624145008', '022', '人民武装学院', '0', '1', '1003866568974705484', '1554948449', '0', '0', '0');
INSERT INTO `academy_info` VALUES ('3059992599967783560', '008', '体育与健康科学学院', '0', '2', '1003866568974705484', '1554948447', '0', '0', '0');
INSERT INTO `academy_info` VALUES ('3059995609884076194', '016', '软件与信息安全学院', '0', '1', '1003866568974705484', '1554948448', '0', '0', '0');
INSERT INTO `academy_info` VALUES ('3059997591519806722', '007', '教育科学学院', '0', '1', '1003866568974705484', '1554948447', '0', '0', '0');
INSERT INTO `academy_info` VALUES ('3059998609861058623', '014', '海洋与生物技术学院', '0', '2', '1003866568974705484', '1554948448', '0', '0', '0');

-- ----------------------------
-- Table structure for `classes`
-- ----------------------------
DROP TABLE IF EXISTS `classes`;
CREATE TABLE `classes` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `majorCode` varchar(20) NOT NULL COMMENT '所属专业：对应专业信息表里的代码：major.code',
  `grade` varchar(20) DEFAULT NULL COMMENT '年级',
  `classNo` varchar(20) DEFAULT NULL COMMENT '班号',
  `creator` bigint(20) DEFAULT '0' COMMENT '创建人 用户Id 对应base_user.id',
  `createTime` int(10) unsigned DEFAULT '0' COMMENT '创建时间',
  `reviser` bigint(20) DEFAULT '0' COMMENT '修改人',
  `reviseTime` int(10) unsigned DEFAULT '0' COMMENT '修改时间',
  `isDel` tinyint(1) DEFAULT '0' COMMENT '删除状态:0-未删除,1-已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3146559739763331386 DEFAULT CHARSET=utf8 COMMENT='314 班级信息表';

-- ----------------------------
-- Records of classes
-- ----------------------------
INSERT INTO `classes` VALUES ('1', '5803', '2016', '02', '1002518130371308851', '0', '1002518130371308851', '1561596617', '0');
INSERT INTO `classes` VALUES ('3144346423419700189', '5801', '2017', '1', '1002518130371308851', '1561705902', '0', '0', '0');
INSERT INTO `classes` VALUES ('3145547732462601489', '5801', '2016', '01', '1002518130371308851', '1561516673', '1002518130371308851', '1561553854', '0');
INSERT INTO `classes` VALUES ('3145549273460622240', '2206', '2017', '3', '1002518130371308851', '1561516564', '1002518130371308851', '1561519230', '0');
INSERT INTO `classes` VALUES ('3146559739763331385', '5307', '2020', '02', '1002518130371308851', '1561519296', '1002518130371308851', '1561559135', '0');

-- ----------------------------
-- Table structure for `course`
-- ----------------------------
DROP TABLE IF EXISTS `course`;
CREATE TABLE `course` (
  `id` bigint(20) NOT NULL,
  `number` varchar(20) COLLATE utf8_bin NOT NULL COMMENT '课程号',
  `name` varchar(20) COLLATE utf8_bin NOT NULL COMMENT '课程名',
  `credit` float(4,2) DEFAULT NULL COMMENT '学分',
  `creator` bigint(20) DEFAULT '0' COMMENT '创建人',
  `createTime` int(11) unsigned DEFAULT '0' COMMENT '创建时间',
  `reviser` bigint(20) DEFAULT '0' COMMENT '修改人',
  `reviseTime` int(11) unsigned DEFAULT '0' COMMENT '修改时间',
  `isDel` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除状态:0-有效，1-已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='304 课程信息表';

-- ----------------------------
-- Records of course
-- ----------------------------
INSERT INTO `course` VALUES ('3044613817576442143', 'BC12345', 'java', '2.00', '1002518130371308851', '1561533619', '1002518130371308851', '1561534253', '0');
INSERT INTO `course` VALUES ('3045612563516098680', 'AB1234', '移动开发', '2.50', '1002518130371308851', '1561533561', '1002518130371308851', '1561533598', '0');
INSERT INTO `course` VALUES ('3046621577215518597', 'BC1234', 'UML', '3.00', '1002518130371308851', '1561534277', '0', '0', '0');

-- ----------------------------
-- Table structure for `dictionary`
-- ----------------------------
DROP TABLE IF EXISTS `dictionary`;
CREATE TABLE `dictionary` (
  `id` bigint(20) NOT NULL,
  `name` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '名称',
  `fieldName` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '字段名',
  `rank` int(5) DEFAULT NULL COMMENT '排序',
  `value` varchar(50) COLLATE utf8_bin DEFAULT '' COMMENT '值',
  `explain` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '说明',
  `status` int(1) DEFAULT NULL COMMENT '状态:1-启用，2-禁用',
  `code` varchar(5) COLLATE utf8_bin DEFAULT NULL COMMENT '代码',
  `creator` bigint(20) DEFAULT '0' COMMENT '创建人',
  `createTime` int(11) unsigned DEFAULT '0' COMMENT '创建时间',
  `reviser` bigint(20) DEFAULT '0' COMMENT '修改人',
  `reviseTime` int(11) unsigned DEFAULT '0' COMMENT '修改时间',
  `isDel` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除状态:0-有效，1-已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='402 数据字典表';

-- ----------------------------
-- Records of dictionary
-- ----------------------------
INSERT INTO `dictionary` VALUES ('1', '登录次数', 'errorCount', '1', '4', '限制登录的错误次数', '1', '1', '1003866568974705484', '0', '1003866568974705484', '1549557295', '0');
INSERT INTO `dictionary` VALUES ('2', '锁定时间', 'lockTime', '2', '10', '达到登录错误次数上限后账号被锁定的时间，单位：分钟', '1', '2', '1004558574236073817', '0', '1004558574236073817', '1536761686', '0');
INSERT INTO `dictionary` VALUES ('3083036257555822812', '全部', 'userRole', '5', '1', '菜单功能模块的查看和操作权限：1-组织部可见全部', '1', '5', '1003866568974705484', '1543035543', '1003866568974705484', '1543040300', '0');
INSERT INTO `dictionary` VALUES ('3083163434674366087', '不可见', 'userRole', '8', '4', '学生权限，原则上不可进入后台查看数据', '1', '8', '1003866568974705484', '1543065865', '1003866568974705484', '1543074561', '0');

-- ----------------------------
-- Table structure for `exam`
-- ----------------------------
DROP TABLE IF EXISTS `exam`;
CREATE TABLE `exam` (
  `id` bigint(20) NOT NULL COMMENT 'id',
  `courseNumber` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `name` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '作业名称',
  `teacherNumber` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '发布作业的教师工号',
  `classId` bigint(20) DEFAULT NULL COMMENT '被布置的班级',
  `status` tinyint(1) DEFAULT '0' COMMENT '考试状态：0-未开始,1-已开始,2-已结束',
  `releaseStatus` tinyint(1) DEFAULT '1' COMMENT '发布状态：1-未发布,2-已发布',
  `publishStatus` tinyint(1) DEFAULT '1' COMMENT '1-未公开(学生不可查阅成绩)，2-公开(可查阅)',
  `type` tinyint(1) DEFAULT NULL COMMENT '作业类型：1-课外作业，2-期中作业，3-期末作业',
  `score` float(5,2) DEFAULT NULL COMMENT '试卷总分值',
  `trueOrFalse` float(5,2) DEFAULT NULL COMMENT '判断题总分值',
  `single` float(5,2) DEFAULT NULL COMMENT '单选题总分值',
  `multiple` float(5,2) DEFAULT NULL COMMENT '多选题总分值',
  `gap` float(5,2) DEFAULT NULL COMMENT '填空题总分值',
  `subjective` float(5,2) DEFAULT NULL COMMENT '主观题分值',
  `startTime` int(11) DEFAULT NULL COMMENT '开始时间',
  `endTime` int(11) DEFAULT NULL COMMENT '截止时间',
  `duration` int(3) DEFAULT '0' COMMENT '考试时长:单位分钟',
  `creator` bigint(20) DEFAULT '0' COMMENT '创建人',
  `createTime` int(11) unsigned DEFAULT '0' COMMENT '创建时间',
  `reviser` bigint(20) DEFAULT '0' COMMENT '修改人',
  `reviseTime` int(11) unsigned DEFAULT '0' COMMENT '修改时间',
  `isDel` tinyint(1) unsigned DEFAULT '0' COMMENT '删除状态:0-有效，1-已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='308 作业表';

-- ----------------------------
-- Records of exam
-- ----------------------------

-- ----------------------------
-- Table structure for `exam_candidate`
-- ----------------------------
DROP TABLE IF EXISTS `exam_candidate`;
CREATE TABLE `exam_candidate` (
  `id` bigint(20) NOT NULL,
  `number` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '学生学号',
  `examId` bigint(20) DEFAULT NULL COMMENT '作业id',
  `score` float(5,2) DEFAULT NULL COMMENT '作业得分',
  `status` tinyint(1) DEFAULT '1' COMMENT '作业状态：1-未考，2-已考',
  `markStatus` tinyint(1) DEFAULT '1' COMMENT '老师批改客观题状态:1-未批改，2-已批改',
  `creator` bigint(20) DEFAULT '0' COMMENT '创建人',
  `createTime` int(11) unsigned DEFAULT '0' COMMENT '创建时间',
  `reviser` bigint(20) DEFAULT '0' COMMENT '修改人',
  `reviseTime` int(11) unsigned DEFAULT '0' COMMENT '修改时间',
  `isDel` tinyint(1) DEFAULT '0' COMMENT '删除状态:0-有效，1-已删除',
  PRIMARY KEY (`id`),
  KEY `number` (`number`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='310 作业和学生关系表';

-- ----------------------------
-- Records of exam_candidate
-- ----------------------------

-- ----------------------------
-- Table structure for `exam_question`
-- ----------------------------
DROP TABLE IF EXISTS `exam_question`;
CREATE TABLE `exam_question` (
  `id` bigint(20) NOT NULL COMMENT 'id',
  `questionId` bigint(20) DEFAULT NULL COMMENT '题目id:对应题库表id',
  `examId` bigint(20) DEFAULT NULL COMMENT '作业id:对应作业表exam.id',
  `score` float(4,2) DEFAULT NULL COMMENT '分值：该题所占分值',
  `creator` bigint(20) DEFAULT '0' COMMENT '创建人',
  `createTime` int(11) unsigned DEFAULT '0' COMMENT '创建时间',
  `reviser` bigint(20) DEFAULT '0' COMMENT '修改人',
  `reviseTime` int(11) unsigned DEFAULT '0' COMMENT '修改时间',
  `isDel` tinyint(1) unsigned DEFAULT '0' COMMENT '删除状态:0-有效，1-已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='309 作业和题目关系表';

-- ----------------------------
-- Records of exam_question
-- ----------------------------

-- ----------------------------
-- Table structure for `exam_record`
-- ----------------------------
DROP TABLE IF EXISTS `exam_record`;
CREATE TABLE `exam_record` (
  `id` bigint(20) NOT NULL,
  `examId` bigint(20) DEFAULT NULL COMMENT 'examId：对应exam表id',
  `questionId` bigint(20) DEFAULT NULL COMMENT 'questionId：对应question表id',
  `number` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '考生学号',
  `scores` float(5,2) DEFAULT NULL COMMENT '该题得分',
  `situation` tinyint(2) DEFAULT NULL COMMENT '答对情况：1-答对，2-答错；3-不完全对',
  `answerContent` longtext COLLATE utf8_bin COMMENT '选择项/填空的内容',
  `isExample` tinyint(1) DEFAULT '2' COMMENT '是否范例：1-是，2-否',
  `remark` longtext COLLATE utf8_bin COMMENT '教师点评',
  `creator` bigint(20) DEFAULT '0' COMMENT '创建人',
  `createTime` int(11) unsigned DEFAULT '0' COMMENT '创建时间',
  `reviser` bigint(20) DEFAULT '0' COMMENT '修改人',
  `reviseTime` int(11) unsigned DEFAULT '0' COMMENT '修改时间',
  `isDel` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除状态:0-有效，1-已删除',
  PRIMARY KEY (`id`),
  KEY `number` (`number`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='311 学生答题记录表';

-- ----------------------------
-- Records of exam_record
-- ----------------------------

-- ----------------------------
-- Table structure for `major_info`
-- ----------------------------
DROP TABLE IF EXISTS `major_info`;
CREATE TABLE `major_info` (
  `id` bigint(20) NOT NULL COMMENT '主键ID',
  `code` varchar(20) CHARACTER SET utf8 NOT NULL COMMENT '专业代码',
  `name` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '专业名称',
  `academyCode` varchar(20) CHARACTER SET utf8 NOT NULL COMMENT '所属学院：对应学院信息表里的代码：code',
  `status` tinyint(1) DEFAULT '0' COMMENT '状态：0-有效,1-无效',
  `creator` bigint(20) DEFAULT '0' COMMENT '创建人 用户Id 对应base_user.id',
  `createTime` int(10) unsigned DEFAULT '0' COMMENT '创建时间',
  `reviser` bigint(20) DEFAULT '0' COMMENT '修改人',
  `reviseTime` int(10) unsigned DEFAULT '0' COMMENT '修改时间',
  `isDel` tinyint(1) unsigned DEFAULT '0' COMMENT '删除状态:0-有效,1-已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='313 专业信息表';

-- ----------------------------
-- Records of major_info
-- ----------------------------
INSERT INTO `major_info` VALUES ('3061870728229040380', '5601', '法学', '005', '0', '1003866568974705484', '1555157093', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3061870807911706692', '5304', '法律事务(东南亚方向)', '021', '0', '1003866568974705484', '1555157112', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3061871723755961327', '2800', '体育学类', '008', '0', '1003866568974705484', '1555157094', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3061871757733800286', '070101', '基础数学（研）', '012', '0', '1003866568974705484', '1555157100', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3061871789549013198', '3111', '视觉传达设计', '018', '0', '1003866568974705484', '1555157109', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3061871813575429168', '6306', '印度尼西亚语（国家基地班）', '011', '0', '1003866568974705484', '1555157115', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3061872763164649707', '5901', '生物技术', '014', '0', '1003866568974705484', '1555157104', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3061872778188030287', '2605', '自动化', '015', '0', '1003866568974705484', '1555157105', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3061872814664820531', '6303', '老挝语（国家基地班）', '011', '0', '1003866568974705484', '1555157114', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3061874763754360567', '5904', '食品与发酵', '014', '0', '1003866568974705484', '1555157103', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3061874766508949670', '5905', '生物教育', '014', '0', '1003866568974705484', '1555157104', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3061874771063962798', '5804', '信息安全', '016', '0', '1003866568974705484', '1555157106', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3061875810031162611', '6316', '印度尼西亚语', '011', '0', '1003866568974705484', '1555157116', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3061875815545700019', '3502', '普通民族预科班', '023', '0', '1003866568974705484', '1555157114', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3061877798932817522', '3114', '美术设计', '018', '0', '1003866568974705484', '1555157110', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3061878740071322174', '2311', '汉语国际教育（越南）', '009', '0', '1003866568974705484', '1555157098', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3061878783386700289', '310401', '美术学（国画方向）', '018', '0', '1003866568974705484', '1555157108', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3062870732962658903', '2305', '中国少数民族语言文学', '009', '0', '1003866568974705484', '1555157097', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3062870781670508881', '2209', '工商管理（专升本）', '017', '0', '1003866568974705484', '1555157107', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3062871791513871802', '1507', '中国语言文化', '021', '0', '1003866568974705484', '1555157111', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3062871808842080530', '5307', '应用印尼语', '021', '0', '1003866568974705484', '1555157113', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3062872743517381513', '2404', '翻译', '010', '0', '1003866568974705484', '1555157099', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3062873724576126561', '5600', '法学类', '005', '0', '1003866568974705484', '1555157093', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3062873733680833867', '2803', '社会体育指导与管理', '008', '0', '1003866568974705484', '1555157095', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3062873750903184814', '2504', '金属材料工程', '012', '0', '1003866568974705484', '1555157101', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3062873760819396803', '2710', '高分子材料与工程', '013', '0', '1003866568974705484', '1555157103', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3062873790681281944', '620201', '策划与制作方向', '019', '0', '1003866568974705484', '1555157110', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3062874728172473363', '2905', '人类学', '006', '0', '1003866568974705484', '1555157094', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3062874768898408579', '2706', '环境工程', '013', '0', '1003866568974705484', '1555157103', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3062874774356611121', '2204', '档案学', '017', '0', '1003866568974705484', '1555157106', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3062875785376264646', '2207', '工商管理类', '017', '0', '1003866568974705484', '1555157107', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3062875808477227139', '5308', '应用法语', '021', '0', '1003866568974705484', '1555157113', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3062876728399503524', '2801', '体育教育', '008', '0', '1003866568974705484', '1555157095', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3062878108162704040', '2505', '金融数学', '012', '0', '1003866568974705484', '1555157662', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3062879753754896498', '2406', '英语（教育方向）', '010', '0', '1003866568974705484', '1555157100', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3063664498688720401', '888', '测试导入专业', '999', '0', '1005664655405204857', '1557491871', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3063870712580221801', '2103', '公共政策学', '001', '0', '1003866568974705484', '1555157091', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3063870804904162806', '3501', '少数民族预科班', '023', '0', '1003866568974705484', '1555157114', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3063871781007460270', '310402', '美术学（油画方向）', '018', '0', '1003866568974705484', '1555157108', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3063871804152609314', '1510', '国际商务（东南亚经贸旅游泰语方向）', '021', '0', '1003866568974705484', '1555157111', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3063872743732827904', '2312', '汉语言文学（师范方向）', '009', '0', '1003866568974705484', '1555157098', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3063873719236335922', '2104', '国际事务与国际关系', '001', '0', '1003866568974705484', '1555157091', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3063873771171721329', '2600', '计算机类', '015', '0', '1003866568974705484', '1555157104', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3063873781709038723', '3104', '美术学', '018', '0', '1003866568974705484', '1555157108', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3063874118123499044', '5801', '软件工程', '016', '0', '1003866568974705484', '1555157664', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3063874716022694366', '21002', '公共事业管理', '001', '0', '1003866568974705484', '1555157090', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3063875812757901892', '6314', '缅甸语', '011', '0', '1003866568974705484', '1555157116', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3063876796988511183', '6203', '编辑出版学', '019', '0', '1003866568974705484', '1555157111', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3063877777890310325', '2613', '通信工程（铁道通信与信息化）', '015', '0', '1003866568974705484', '1555157105', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3063877818912044128', '6302', '越南语（国家基地班）', '011', '0', '1003866568974705484', '1555157114', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3063878743876043601', '2403', '法语', '010', '0', '1003866568974705484', '1555157099', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3063878747115584206', '2308', '中国少数民族语言文学（壮）', '009', '0', '1003866568974705484', '1555157097', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3063879710352603729', '21001', '行政管理', '001', '0', '1003866568974705484', '1555157090', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3063879806794130529', '1511', '国际商务（东南亚经贸旅游越南语方向）', '021', '0', '1003866568974705484', '1555157112', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3064870760142133456', '2702', '化学', '013', '0', '1003866568974705484', '1555157102', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3064870802629858060', '5302', '国际商务(越南方向)', '021', '0', '1003866568974705484', '1555157112', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3064870808201544321', '5306', '应用越南语', '021', '0', '1003866568974705484', '1555157113', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3064871744794056451', '2309', '中国少数民族语言文学（瑶）', '009', '0', '1003866568974705484', '1555157098', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3064872717059545169', '5204', '金融学', '003', '0', '1003866568974705484', '1555157092', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3064872748432390366', '2310', '汉语国际教育（印尼）', '009', '0', '1003866568974705484', '1555157098', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3064872775708497002', '2201', '工商管理', '017', '0', '1003866568974705484', '1555157106', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3064873718450562390', '5203', '会计学', '003', '0', '1003866568974705484', '1555157092', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3064873725128435113', '5502', '教育学', '007', '0', '1003866568974705484', '1555157094', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3064873784288337388', '3107', '舞蹈学', '018', '0', '1003866568974705484', '1555157109', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3064873792299606300', '3112', '环境设计', '018', '0', '1003866568974705484', '1555157109', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3064873806418709204', '1512', '国际商务（东南亚经贸旅游印尼语方向）', '021', '0', '1003866568974705484', '1555157112', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3064875801553233902', '5305', '应用泰国语', '021', '0', '1003866568974705484', '1555157112', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3064878736734652210', '2302', '汉语言文学', '009', '0', '1003866568974705484', '1555157096', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3064878740596781887', '240201', '英语教育', '010', '0', '1003866568974705484', '1555157099', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3064879729193374268', '2902', '历史学', '006', '0', '1003866568974705484', '1555157093', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3064879749066228966', '2314', '汉语言文学（专升本）', '009', '0', '1003866568974705484', '1555157098', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3064879770862394873', '2601', '电子信息类', '015', '0', '1003866568974705484', '1555157104', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3064879789291254888', '2210', '旅游管理（专升本）', '017', '0', '1003866568974705484', '1555157107', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3064879809963397366', '5311', '工商管理留学生实验班', '021', '0', '1003866568974705484', '1555157113', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3064879812390247808', '6315', '柬埔寨语', '011', '0', '1003866568974705484', '1555157116', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3065871712831006700', '5202', '市场营销', '003', '0', '1003866568974705484', '1555157092', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3065871769583785726', '2704', '化学类', '013', '0', '1003866568974705484', '1555157102', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3065872738300952878', '2304', '汉语言文学（秘书方向）', '009', '0', '1003866568974705484', '1555157096', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3065872744407725244', '2313', '汉语言文学（写作方向）', '009', '0', '1003866568974705484', '1555157098', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3065872819111174383', '6313', '老挝语', '011', '0', '1003866568974705484', '1555157115', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3065873756271351204', '081203', '计算机应用技术（研）', '012', '0', '1003866568974705484', '1555157101', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3065874755146728413', '2405', '法语（专升本）', '010', '0', '1003866568974705484', '1555157100', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3065874769539136080', '2707', '化工与制药类', '013', '0', '1003866568974705484', '1555157103', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3065874773543864125', '2602', '电子信息工程', '015', '0', '1003866568974705484', '1555157105', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3065875802325473544', '5310', '应用外语', '021', '0', '1003866568974705484', '1555157113', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3065876796247007682', '6205', '新闻学', '019', '0', '1003866568974705484', '1555157111', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3065877772823446807', '5802', '信息管理与信息系统', '016', '0', '1003866568974705484', '1555157106', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3065878730682343164', '280201', '体育产业', '008', '0', '1003866568974705484', '1555157095', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3065878820500368329', '6319', '泰语（专升本）', '011', '0', '1003866568974705484', '1555157116', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3065878827880100031', '6401', '会计学(中英合作项目)', '004', '0', '1003866568974705484', '1555157117', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3065879113035173303', '2611', '计算机科学与技术', '015', '0', '1003866568974705484', '1555157664', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3065879721516084320', '2901', '民族学', '006', '0', '1003866568974705484', '1555157093', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3065879723832290082', '2903', '社会学', '006', '0', '1003866568974705484', '1555157093', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3065879778452391104', '5803', '信息管理与信息系统（与广西经干院联合培养）', '016', '0', '1003866568974705484', '1555157106', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3065879818300745352', '6304', '缅甸语（国家基地班）', '011', '0', '1003866568974705484', '1555157115', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3066870742231312091', '240202', '英语翻译', '010', '0', '1003866568974705484', '1555157099', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3066870745483041000', '230702', '汉语国际教育印尼语班', '009', '0', '1003866568974705484', '1555157097', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3066870814855883183', '6311', '泰语', '011', '0', '1003866568974705484', '1555157115', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3066871711693888077', '5205', '电子商务', '003', '0', '1003866568974705484', '1555157092', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3066872711906421569', '2105', '中国共产党历史', '001', '0', '1003866568974705484', '1555157091', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3066872764073496701', '2712', '环境工程（专升本）', '013', '0', '1003866568974705484', '1555157103', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3066872796420795442', '6201', '播音与主持艺术', '019', '0', '1003866568974705484', '1555157110', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3066872809278971180', '5303', '国际商务(印尼方向)', '021', '0', '1003866568974705484', '1555157112', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3066873756256266000', '2503', '物理学', '012', '0', '1003866568974705484', '1555157101', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3066873818232931881', '6307', '马来语（国家基地班）', '011', '0', '1003866568974705484', '1555157115', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3066874711282987564', '2101', '政治学与行政学', '001', '0', '1003866568974705484', '1555157091', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3066874766880686381', '5902', '海洋科学', '014', '0', '1003866568974705484', '1555157104', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3066875794879341592', '6206', '新闻传播学类', '019', '0', '1003866568974705484', '1555157111', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3066875806636295699', '3201', '国防教育与管理', '022', '0', '1003866568974705484', '1555157113', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3066877746983211203', '2402', '英语', '010', '0', '1003866568974705484', '1555157099', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3066878737089543091', '2804', '体育教育（专升本）', '008', '0', '1003866568974705484', '1555157096', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3066879723841179397', '2910', '社会学类', '006', '0', '1003866568974705484', '1555157094', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3066879756061524346', '2408', '英语（翻译方向）', '010', '0', '1003866568974705484', '1555157100', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3067870754456034273', '070102', '计算数学（研）', '012', '0', '1003866568974705484', '1555157100', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3067870764436966498', '2711', '中药制药', '013', '0', '1003866568974705484', '1555157103', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3067871715664222563', '21003', '城市管理', '001', '0', '1003866568974705484', '1555157091', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3067872782004906951', '3102', '艺术设计', '018', '0', '1003866568974705484', '1555157107', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3067873738038039734', '2802', '社会体育', '008', '0', '1003866568974705484', '1555157095', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3067873743356261900', '2316', '汉语国际教育（泰语）', '009', '0', '1003866568974705484', '1555157099', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3067873798591034840', '311202', '环境设计（景观与建筑方向）', '018', '0', '1003866568974705484', '1555157109', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3067873818208403042', '6301', '泰语（国家基地班）', '011', '0', '1003866568974705484', '1555157114', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3067874734668237363', '2303', '对外汉语', '009', '0', '1003866568974705484', '1555157096', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3067878722868975208', '5602', '知识产权', '005', '0', '1003866568974705484', '1555157093', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3067879746728120300', '2315', '秘书学（专升本）', '009', '0', '1003866568974705484', '1555157098', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3067879760524407891', '5903', '生物技术（专升本）', '014', '0', '1003866568974705484', '1555157104', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3067879786017164875', '310302', '音乐表演（器乐方向）', '018', '0', '1003866568974705484', '1555157108', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3068870095115107610', '280202', '体育保健康复', '008', '0', '1003866568974705484', '1555157659', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3068870719190078889', '5201', '国际经济与贸易', '003', '0', '1003866568974705484', '1555157092', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3068871787384022276', '310301', '音乐表演（声乐方向）', '018', '0', '1003866568974705484', '1555157108', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3068872713667936165', '2100', '公共管理类', '001', '0', '1002518130371308851', '1555157090', '1002518130371308851', '1561596528', '0');
INSERT INTO `major_info` VALUES ('3068872724945171270', '5208', '税收学', '003', '0', '1003866568974705484', '1555157093', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3068872786916672281', '3110', '艺术类（含艺术设计和美术学）', '018', '0', '1003866568974705484', '1555157109', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3068872797621056392', '6204', '传播学', '019', '0', '1003866568974705484', '1555157111', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3068873731864675610', '230701', '汉语国际教育泰语班', '009', '0', '1003866568974705484', '1555157097', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3068874721507183412', '2904', '社会工作', '006', '0', '1003866568974705484', '1555157094', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3068874723482892906', '5501', '应用心理学', '007', '0', '1003866568974705484', '1555157094', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3068874738241537011', '2307', '汉语国际教育', '009', '0', '1003866568974705484', '1555157097', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3068874771018316159', '2205', '人力资源管理', '017', '0', '1003866568974705484', '1555157106', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3068874792550770818', '3115', '环境设计（专升本）', '018', '0', '1003866568974705484', '1555157110', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3068875778280456230', '2603', '通信工程', '015', '0', '1003866568974705484', '1555157105', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3068875815945085255', '6305', '柬埔寨语（国家基地班）', '011', '0', '1003866568974705484', '1555157115', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3068876736602193118', '2306', '秘书学', '009', '0', '1003866568974705484', '1555157097', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3068877781637240852', '3103', '音乐表演', '018', '0', '1003866568974705484', '1555157108', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3068877797279314792', '3113', '设计学类', '018', '0', '1003866568974705484', '1555157110', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3068877807014482689', '5301', '国际商务(泰国方向)', '021', '0', '1003866568974705484', '1555157112', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3068877808268637608', '3202', '公共事业管理（国防教育与管理培养模块）', '022', '0', '1003866568974705484', '1555157114', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3068878755941259547', '2501', '数学与应用数学', '012', '0', '1003866568974705484', '1555157101', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3068878791336404068', '620202', '剪辑与包装方向', '019', '0', '1003866568974705484', '1555157111', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3068878810475648177', '6312', '越南语', '011', '0', '1003866568974705484', '1555157115', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3068879736009724637', '230202', '师范方向', '009', '0', '1003866568974705484', '1555157096', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3068879755414592524', '2407', '英语（商务方向）', '010', '0', '1003866568974705484', '1555157100', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3068879823152090830', '6320', '越南语（专升本）', '011', '0', '1003866568974705484', '1555157116', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3069870716553735982', '2106', '政治学类', '001', '0', '1003866568974705484', '1555157092', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3069870750423524862', '2507', '数学类', '012', '0', '1003866568974705484', '1555157102', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3069870765179864603', '2709', '制药工程', '013', '0', '1003866568974705484', '1555157103', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3069871788016991167', '2208', '人力资源管理（专升本）', '017', '0', '1003866568974705484', '1555157107', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3069871790962493647', '311201', '环境设计（室内设计方向）', '018', '0', '1003866568974705484', '1555157109', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3069871791049941049', '6202', '广播电视编导', '019', '0', '1003866568974705484', '1555157110', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3069872763954256523', '2703', '应用化学', '013', '0', '1003866568974705484', '1555157102', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3069872769228514240', '2705', '化学工程与工艺', '013', '0', '1003866568974705484', '1555157102', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3069874112890515920', '2604', '网络工程', '015', '0', '1003866568974705484', '1555157663', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3069874736377586035', '230201', '写作方向', '009', '0', '1003866568974705484', '1555157096', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3069874738020339138', '280203', '休闲体育', '008', '0', '1003866568974705484', '1555157095', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3069875778721953224', '2206', '旅游管理', '017', '0', '1003866568974705484', '1555157107', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3069876711936107686', '2102', '中国革命史与中国共产党党史', '001', '0', '1003866568974705484', '1555157091', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3069876813680095432', '6317', '马来语', '011', '0', '1003866568974705484', '1555157116', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3069877752771409748', '2506', '光电信息科学与工程', '012', '0', '1003866568974705484', '1555157102', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3069877753322256209', '070104', '应用数学（研）', '012', '0', '1003866568974705484', '1555157101', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3069877776835349710', '2606', '物联网工程', '015', '0', '1003866568974705484', '1555157105', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3069877780632419561', '3105', '产品设计', '018', '0', '1003866568974705484', '1555157108', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3069878756624722792', '2502', '信息与计算科学', '012', '0', '1003866568974705484', '1555157101', '0', '0', '0');
INSERT INTO `major_info` VALUES ('3069879729312644065', '5206', '物流管理', '003', '0', '1003866568974705484', '1555157092', '0', '0', '0');

-- ----------------------------
-- Table structure for `message`
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message` (
  `id` bigint(20) NOT NULL,
  `number` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '用户学号/工号',
  `name` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '姓名',
  `server` tinyint(1) DEFAULT NULL COMMENT '目的服务器：0：后台 1：PC/微信',
  `type` tinyint(1) DEFAULT NULL COMMENT '通知类型：\r\n0：系统通知\r\n1：课程安排\r\n2：会议安排\r\n3：考试安排\r\n4：入党申请\r\n5：思想汇报\r\n6：其他',
  `topic` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '主题',
  `content` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '内容',
  `sendTime` int(11) DEFAULT NULL COMMENT '发送时间',
  `linkId` bigint(20) DEFAULT NULL COMMENT '关联类型表id --备用字段',
  `creator` bigint(20) DEFAULT '0' COMMENT '创建人',
  `createTime` int(11) unsigned DEFAULT '0' COMMENT '创建时间',
  `reviser` bigint(20) DEFAULT '0' COMMENT '修改人',
  `reviseTime` int(11) unsigned DEFAULT '0' COMMENT '修改时间',
  `isDel` tinyint(1) unsigned DEFAULT '0' COMMENT '删除状态:0-有效，1-已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='401 消息提醒表';

-- ----------------------------
-- Records of message
-- ----------------------------

-- ----------------------------
-- Table structure for `message_user`
-- ----------------------------
DROP TABLE IF EXISTS `message_user`;
CREATE TABLE `message_user` (
  `id` bigint(20) NOT NULL,
  `messageId` bigint(20) DEFAULT NULL COMMENT '消息ID',
  `number` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '用户学号/工号',
  `readStatus` tinyint(1) DEFAULT '2' COMMENT '阅读状态：1已读 2未读',
  `readTime` int(11) DEFAULT NULL COMMENT '阅读时间',
  `creator` bigint(20) DEFAULT '0' COMMENT '创建人',
  `createTime` int(11) unsigned DEFAULT '0' COMMENT '创建时间',
  `reviser` bigint(20) DEFAULT '0' COMMENT '修改人',
  `reviseTime` int(11) unsigned DEFAULT '0' COMMENT '修改时间',
  `isDel` tinyint(1) unsigned DEFAULT '0' COMMENT '删除状态:0-有效，1-已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='403 消息提醒用户关系表';

-- ----------------------------
-- Records of message_user
-- ----------------------------
INSERT INTO `message_user` VALUES ('3551047124337379133', '3544926393081744387', '99999907', '1', '1559489730', '1005550613410546171', '1559489730', '0', '0', '0');
INSERT INTO `message_user` VALUES ('3551645102035121124', '3541342277575091412', '99999908', '1', '1559632777', '1003557619014524955', '1559632777', '0', '0', '0');
INSERT INTO `message_user` VALUES ('3551879448104472551', '3549484673194713235', '115677878787', '1', '1559211334', '1002452912731172245', '1559211334', '0', '0', '0');
INSERT INTO `message_user` VALUES ('3551945592257419378', '3547180882502812924', '99999905', '1', '1559465048', '1004556610513150427', '1559465048', '0', '0', '0');
INSERT INTO `message_user` VALUES ('3552006159872877162', '3542927398074164467', '99999906', '1', '1559479485', '1008552619993506384', '1559479485', '0', '0', '0');
INSERT INTO `message_user` VALUES ('3552965008825141390', '3546922067287749529', '99999902', '1', '1559469913', '1007558617232708403', '1559469913', '0', '0', '0');
INSERT INTO `message_user` VALUES ('3553214654644055822', '3542926069281740522', '99999904', '1', '1559529198', '1001552618054593683', '1559529198', '0', '0', '0');
INSERT INTO `message_user` VALUES ('3553346413292032918', '3548925395085947685', '05310001', '1', '1559560135', '1001126020349084081', '1559560135', '0', '0', '0');
INSERT INTO `message_user` VALUES ('3553941781875838926', '3544485675171542402', '99999905', '1', '1559466525', '1004556610513150427', '1559466525', '0', '0', '0');
INSERT INTO `message_user` VALUES ('3553958841768846211', '3548921396074363779', '99999902', '1', '1559467254', '1007558617232708403', '1559467254', '0', '0', '0');
INSERT INTO `message_user` VALUES ('3553962653843048754', '3544926390071554076', '99999904', '1', '1559470068', '1001552618054593683', '1559470068', '0', '0', '0');
INSERT INTO `message_user` VALUES ('3554649543286662575', '3544486674154766193', '99999908', '1', '1559632882', '1003557619014524955', '1559632882', '0', '0', '0');
INSERT INTO `message_user` VALUES ('3554658023260276339', '3545426998394449205', '99999908', '1', '1559633951', '1003557619014524955', '1559633951', '0', '0', '0');
INSERT INTO `message_user` VALUES ('3554956708929624190', '3543926064298943828', '99999901', '1', '1559467220', '1006557615807680089', '1559467220', '0', '0', '0');
INSERT INTO `message_user` VALUES ('3555215189592803900', '3544927399084944685', '99999909', '1', '1559529323', '1006551612390202619', '1559529323', '0', '0', '0');
INSERT INTO `message_user` VALUES ('3555463805300496712', '3546464246954523338', '117583010132', '1', '1558874535', '1001449863554908723', '1558874535', '0', '0', '0');
INSERT INTO `message_user` VALUES ('3555647500810545602', '3543184882608288925', '99999908', '1', '1559633111', '1003557619014524955', '1559633111', '0', '0', '0');
INSERT INTO `message_user` VALUES ('3555941312113466743', '3541929391079364779', '99999905', '1', '1559464503', '1004556610513150427', '1559464503', '0', '0', '0');
INSERT INTO `message_user` VALUES ('3555964473433982529', '3548924069288741527', '99999903', '1', '1559470027', '1004556613623773675', '1559470027', '0', '0', '0');
INSERT INTO `message_user` VALUES ('3555989862755609729', '3543426992400023125', '99999905', '1', '1559475840', '1004556610513150427', '1559475840', '0', '0', '0');
INSERT INTO `message_user` VALUES ('3556185661071618533', '3542928392083749378', '99999908', '1', '1559523478', '1003557619014524955', '1559523478', '0', '0', '0');
INSERT INTO `message_user` VALUES ('3556212661123438699', '3543927066293947828', '99999908', '1', '1559530153', '1003557619014524955', '1559530153', '0', '0', '0');
INSERT INTO `message_user` VALUES ('3556378924032357167', '3549186881497231017', '117583010132', '1', '1559566931', '1001449863554908723', '1559566931', '0', '0', '0');
INSERT INTO `message_user` VALUES ('3556941644890791167', '3548928062289554222', '99999905', '1', '1559464823', '1004556610513150427', '1559464823', '0', '0', '0');
INSERT INTO `message_user` VALUES ('3557213368384869357', '3545186880546752968', '99999903', '1', '1559530082', '1004556613623773675', '1559530082', '0', '0', '0');
INSERT INTO `message_user` VALUES ('3557653298673429099', '3544181705174281048', '99999908', '1', '1559633777', '1003557619014524955', '1559633777', '0', '0', '0');
INSERT INTO `message_user` VALUES ('3557962121543362762', '3548929398079558073', '99999903', '1', '1559469942', '1004556613623773675', '1559469942', '0', '0', '0');
INSERT INTO `message_user` VALUES ('3558377926022167885', '3543925391073361762', '117583010132', '1', '1559566930', '1001449863554908723', '1559566930', '0', '0', '0');
INSERT INTO `message_user` VALUES ('3558379917499898495', '3542348274464041519', '117583010132', '1', '1559566928', '1001449863554908723', '1559566928', '0', '0', '0');
INSERT INTO `message_user` VALUES ('3558949929654208023', '3541922399074558073', '99999901', '1', '1559466558', '1006557615807680089', '1559466558', '0', '0', '0');
INSERT INTO `message_user` VALUES ('3559389306920967219', '3541347276615220760', '05310002', '1', '1559569168', '1002120020392654082', '1559569168', '0', '0', '0');
INSERT INTO `message_user` VALUES ('3559945059662387356', '3549186709072627740', '99999905', '1', '1559464681', '1004556610513150427', '1559464681', '0', '0', '0');
INSERT INTO `message_user` VALUES ('3559965468515083557', '3547180882502812924', '99999903', '1', '1559470024', '1004556613623773675', '1559470024', '0', '0', '0');

-- ----------------------------
-- Table structure for `options`
-- ----------------------------
DROP TABLE IF EXISTS `options`;
CREATE TABLE `options` (
  `id` bigint(20) NOT NULL,
  `questionId` bigint(20) DEFAULT NULL COMMENT '题目id:对应题库表id',
  `optionInfo` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '选项内容',
  `optionNumber` tinyint(1) DEFAULT NULL COMMENT '选项号：1-A,2-B,3-C,4-D,5-E',
  `isAnswer` tinyint(1) DEFAULT NULL COMMENT '是否正确选项：1-是,2-不是',
  `creator` bigint(20) DEFAULT '0' COMMENT '创建人',
  `createTime` int(11) unsigned DEFAULT '0' COMMENT '创建时间',
  `reviser` bigint(20) DEFAULT '0' COMMENT '修改人',
  `reviseTime` int(11) unsigned DEFAULT '0' COMMENT '修改时间',
  `isDel` tinyint(1) unsigned DEFAULT '0' COMMENT '删除状态:0-有效，1-已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='307 选项表';

-- ----------------------------
-- Records of options
-- ----------------------------

-- ----------------------------
-- Table structure for `picture`
-- ----------------------------
DROP TABLE IF EXISTS `picture`;
CREATE TABLE `picture` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '图片名称',
  `artworkURL` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '原图URL',
  `thumbnailURL` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '缩略图URL',
  `path` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '原图路径',
  `describe` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '图片描述',
  `creator` bigint(20) DEFAULT '0' COMMENT '创建人',
  `createTime` int(11) unsigned DEFAULT '0' COMMENT '创建时间',
  `reviser` bigint(20) DEFAULT '0' COMMENT '修改人',
  `reviseTime` int(11) unsigned DEFAULT '0' COMMENT '修改时间',
  `isDel` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除状态:0-有效，1-已删除',
  `tableId` bigint(20) unsigned DEFAULT '0' COMMENT '主表id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='405 图片表';

-- ----------------------------
-- Records of picture
-- ----------------------------

-- ----------------------------
-- Table structure for `question`
-- ----------------------------
DROP TABLE IF EXISTS `question`;
CREATE TABLE `question` (
  `id` bigint(20) NOT NULL,
  `questionName` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '题目名称',
  `type` tinyint(1) DEFAULT NULL COMMENT '类型：1-判断,2-单选,3-多选,4-填空,5-简答题',
  `answerKeys` longtext COLLATE utf8_bin COMMENT '答案解析/参考答案',
  `creator` bigint(20) DEFAULT '0' COMMENT '创建人',
  `createTime` int(11) unsigned DEFAULT '0' COMMENT '创建时间',
  `reviser` bigint(20) DEFAULT '0' COMMENT '修改人',
  `reviseTime` int(11) unsigned DEFAULT '0' COMMENT '修改时间',
  `isDel` tinyint(1) unsigned DEFAULT '0' COMMENT '删除状态:0-有效，1-已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='306 题干表';

-- ----------------------------
-- Records of question
-- ----------------------------

-- ----------------------------
-- Table structure for `student`
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student` (
  `id` bigint(20) NOT NULL,
  `name` varchar(20) COLLATE utf8_bin NOT NULL COMMENT '姓名',
  `number` varchar(20) COLLATE utf8_bin NOT NULL COMMENT '学号',
  `classId` bigint(20) DEFAULT NULL COMMENT '所属班级id:classes.id',
  `sex` tinyint(1) DEFAULT NULL COMMENT '性别：1-女,2-男',
  `creator` bigint(20) DEFAULT '0' COMMENT '创建人',
  `createTime` int(11) unsigned DEFAULT '0' COMMENT '创建时间',
  `reviser` bigint(20) DEFAULT '0' COMMENT '修改人',
  `reviseTime` int(11) unsigned DEFAULT '0' COMMENT '修改时间',
  `isDel` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除状态:0-有效，1-已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='302 学生信息表';

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES ('3022533875784790643', '戚淇俊', '116263000105', '3145547732462601489', '2', '1002518130371308851', '1561989729', '0', '0', '0');
INSERT INTO `student` VALUES ('3023535236441435152', '黄廷程', '116263000328', '3145547732462601489', '2', '1002518130371308851', '1561990293', '0', '0', '0');
INSERT INTO `student` VALUES ('3023539663518858178', '韦杨琳', '116263000140', '3145547732462601489', '1', '1002518130371308851', '1561989916', '0', '0', '0');
INSERT INTO `student` VALUES ('3024535593645721927', '梁艺翔', '116263000313', '3145547732462601489', '2', '1002518130371308851', '1561989902', '0', '0', '0');
INSERT INTO `student` VALUES ('3027535537323950412', '李嘉晖', '116263000111', '3145547732462601489', '1', '1002518130371308851', '1561989886', '0', '0', '0');

-- ----------------------------
-- Table structure for `sys_auto_code`
-- ----------------------------
DROP TABLE IF EXISTS `sys_auto_code`;
CREATE TABLE `sys_auto_code` (
  `id` bigint(20) NOT NULL COMMENT '主键ID',
  `name` varchar(200) NOT NULL COMMENT '表名称',
  `code` longtext COMMENT '生成的代码',
  `type` int(1) DEFAULT '1' COMMENT '类型：1-后台，2-前端',
  `creator` bigint(20) DEFAULT '0' COMMENT '创建人',
  `createTime` int(11) unsigned DEFAULT '0' COMMENT '创建时间',
  `reviser` bigint(20) DEFAULT '0' COMMENT '修改人',
  `reviseTime` int(11) unsigned DEFAULT '0' COMMENT '修改时间',
  `isDel` tinyint(1) unsigned DEFAULT '0' COMMENT '删除状态:0-有效，1-已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='199 代码生成记录';

-- ----------------------------
-- Records of sys_auto_code
-- ----------------------------
INSERT INTO `sys_auto_code` VALUES ('1993164407755428443', 'exam', '{\"service\":\"package com.xtaller.party.core.service.impl;\\n\\nimport com.alibaba.fastjson.JSONObject;\\nimport com.xtaller.party.core.mapper.ExamMapper;\\nimport com.xtaller.party.core.model.Exam;\\nimport com.xtaller.party.core.service.IExamService;\\nimport com.xtaller.party.utils.base.TServiceImpl;\\nimport com.xtaller.party.utils.bean.Page;\\nimport com.xtaller.party.utils.convert.F;\\nimport org.springframework.stereotype.Service;\\n\\nimport java.util.ArrayList;\\nimport java.util.List;\\nimport com.xtaller.party.utils.convert.W;\\nimport com.xtaller.party.utils.bean.Where;\\n/**\\n * Created by Party on 2019/05/31\\n */\\n@Service\\npublic class ExamService extends TServiceImpl<ExamMapper,Exam> implements IExamService {\\n   /**************************CURD begin******************************/ \\n   // 创建\\n   @Override\\n   public Exam createExam(Exam model) {\\n       if(this.insert(model))\\n           return this.selectById(model.getId());\\n       return null;\\n   }\\n \\n   // 删除\\n   @Override\\n   public Boolean deleteExam(Object ids,String reviser) {\\n       return this.delete(ids,reviser);\\n   }\\n \\n   // 修改\\n   @Override\\n   public Exam updateExam(Exam model) {\\n       if(this.update(model))\\n           return this.selectById(model.getId());\\n       return null;\\n   }\\n \\n   // 查询\\n   @Override\\n   public List<Exam> findByIds(Object ids) {\\n       return this.selectByIds(ids);\\n   }\\n \\n   // 属于\\n   @Override\\n   public Boolean exist(List<Where> w) {\\n       w.add(new Where(\\\"1\\\"));\\n       return this.query(w).size() > 0;\\n   }\\n \\n   // 查询一个id是否存在\\n   @Override\\n   public Boolean existId(Object id) {\\n       where = W.f(\\n               W.and(\\\"id\\\",\\\"eq\\\",id),\\n               W.field(\\\"1\\\")\\n       );\\n       return this.query(where).size() > 0;\\n   }\\n \\n   /**************************CURD end********************************/ \\n    //分页查\\n    public Page page(int index, int pageSize, String w) {\\n        // 总记录数\\n        JSONObject row = baseMapper.getPageCount(w);\\n        int totalCount = row.getInteger(\\\"total\\\");\\n        if(totalCount == 0)\\n            return new Page(new ArrayList<JSONObject>(),pageSize,0,0,1);\\n        // 分页数据\\n        index = index < 0 ? 1:index;\\n        int limit = (index - 1) * pageSize;\\n        int totalPage = totalCount % pageSize == 0 ? totalCount/pageSize : (totalCount/pageSize)+1;\\n        int currentPage = index;\\n\\n        List<JSONObject> grades = baseMapper.getPage(w,limit,pageSize);\\n\\n            return new Page(F.f2l(grades,\\\"id\\\"), pageSize, totalCount, totalPage, currentPage);\\n    }\\n\\n    //全查\\n    public List<JSONObject> queryAll(String where) {\\n        List<JSONObject> list = baseMapper.queryAll(where);\\n        return F.f2l(list,\\\"id\\\",\\\"creator\\\",\\\"reverse\\\");\\n    }\\n\\n}\",\"create\":\"package com.xtaller.party.doc;\\n\\nimport com.fasterxml.jackson.annotation.JsonIgnoreProperties;\\nimport com.fasterxml.jackson.annotation.JsonInclude;\\nimport io.swagger.annotations.ApiModel;\\nimport io.swagger.annotations.ApiModelProperty;\\n\\n/**\\n * Created by Party on 2019/05/31\\n */\\n@JsonInclude(JsonInclude.Include.NON_NULL)\\n@JsonIgnoreProperties({ \\\"handler\\\",\\\"hibernateLazyInitializer\\\" })\\n@ApiModel(value =\\\"试卷新增\\\")\\npublic class ExamCreate {\\n    @ApiModelProperty(value = \\\"试卷名称\\\")\\n    private String name;\\n    @ApiModelProperty(value = \\\"考试状态\\\")\\n    private Integer status;\\n    @ApiModelProperty(value = \\\"发布状态\\\")\\n    private Integer releaseStatus;\\n    @ApiModelProperty(value = \\\"试卷类型\\\")\\n    private Integer type;\\n    @ApiModelProperty(value = \\\"考试方式\\\")\\n    private Integer way;\\n    @ApiModelProperty(value = \\\"试卷总分值\\\")\\n    private String score;\\n    @ApiModelProperty(value = \\\"判断题总分值\\\")\\n    private String trueOrFalse;\\n    @ApiModelProperty(value = \\\"单选题总分值\\\")\\n    private String single;\\n    @ApiModelProperty(value = \\\"多选题总分值\\\")\\n    private String multiple;\\n    @ApiModelProperty(value = \\\"填空题总分值\\\")\\n    private String gap;\\n    @ApiModelProperty(value = \\\"开始时间\\\")\\n    private Integer startTime;\\n    @ApiModelProperty(value = \\\"结束时间\\\")\\n    private Integer endTime;\\n    @ApiModelProperty(value = \\\"考试时长\\\")\\n    private Integer duration;\\n    @ApiModelProperty(value = \\\"发布范围\\\")\\n    private String range;\\n    @ApiModelProperty(value = \\\"是否发短信\\\")\\n    private Integer isNote;\\n    @ApiModelProperty(value = \\\"是否发推送\\\")\\n    private Integer isPush;\\n    @ApiModelProperty(value = \\\"是否组卷\\\")\\n    private Integer isMake;\\n\\n    public String getName() { \\n        return name;\\n    } \\n\\n    public void setName(String name) { \\n        this.name = name;\\n    } \\n\\n    public Integer getStatus() { \\n        return status;\\n    } \\n\\n    public void setStatus(Integer status) { \\n        this.status = status;\\n    } \\n\\n    public Integer getReleaseStatus() { \\n        return releaseStatus;\\n    } \\n\\n    public void setReleaseStatus(Integer releaseStatus) { \\n        this.releaseStatus = releaseStatus;\\n    } \\n\\n    public Integer getType() { \\n        return type;\\n    } \\n\\n    public void setType(Integer type) { \\n        this.type = type;\\n    } \\n\\n    public Integer getWay() { \\n        return way;\\n    } \\n\\n    public void setWay(Integer way) { \\n        this.way = way;\\n    } \\n\\n    public String getScore() { \\n        return score;\\n    } \\n\\n    public void setScore(String score) { \\n        this.score = score;\\n    } \\n\\n    public String getTrueOrFalse() { \\n        return trueOrFalse;\\n    } \\n\\n    public void setTrueOrFalse(String trueOrFalse) { \\n        this.trueOrFalse = trueOrFalse;\\n    } \\n\\n    public String getSingle() { \\n        return single;\\n    } \\n\\n    public void setSingle(String single) { \\n        this.single = single;\\n    } \\n\\n    public String getMultiple() { \\n        return multiple;\\n    } \\n\\n    public void setMultiple(String multiple) { \\n        this.multiple = multiple;\\n    } \\n\\n    public String getGap() { \\n        return gap;\\n    } \\n\\n    public void setGap(String gap) { \\n        this.gap = gap;\\n    } \\n\\n    public Integer getStartTime() { \\n        return startTime;\\n    } \\n\\n    public void setStartTime(Integer startTime) { \\n        this.startTime = startTime;\\n    } \\n\\n    public Integer getEndTime() { \\n        return endTime;\\n    } \\n\\n    public void setEndTime(Integer endTime) { \\n        this.endTime = endTime;\\n    } \\n\\n    public Integer getDuration() { \\n        return duration;\\n    } \\n\\n    public void setDuration(Integer duration) { \\n        this.duration = duration;\\n    } \\n\\n    public String getRange() { \\n        return range;\\n    } \\n\\n    public void setRange(String range) { \\n        this.range = range;\\n    } \\n\\n    public Integer getIsNote() { \\n        return isNote;\\n    } \\n\\n    public void setIsNote(Integer isNote) { \\n        this.isNote = isNote;\\n    } \\n\\n    public Integer getIsPush() { \\n        return isPush;\\n    } \\n\\n    public void setIsPush(Integer isPush) { \\n        this.isPush = isPush;\\n    } \\n\\n    public Integer getIsMake() { \\n        return isMake;\\n    } \\n\\n    public void setIsMake(Integer isMake) { \\n        this.isMake = isMake;\\n    } \\n\\n}\\n\",\"update\":\"package com.xtaller.party.doc;\\n\\nimport com.fasterxml.jackson.annotation.JsonIgnoreProperties;\\nimport com.fasterxml.jackson.annotation.JsonInclude;\\nimport io.swagger.annotations.ApiModel;\\nimport io.swagger.annotations.ApiModelProperty;\\n\\n/**\\n * Created by Party on 2019/05/31\\n */\\n@JsonInclude(JsonInclude.Include.NON_NULL)\\n@JsonIgnoreProperties({ \\\"handler\\\",\\\"hibernateLazyInitializer\\\" })\\n@ApiModel(value =\\\"试卷修改\\\")\\npublic class ExamUpdate {\\n    @ApiModelProperty(value = \\\"id\\\")\\n    private String id;\\n    @ApiModelProperty(value = \\\"试卷名称\\\")\\n    private String name;\\n    @ApiModelProperty(value = \\\"考试状态\\\")\\n    private Integer status;\\n    @ApiModelProperty(value = \\\"发布状态\\\")\\n    private Integer releaseStatus;\\n    @ApiModelProperty(value = \\\"试卷类型\\\")\\n    private Integer type;\\n    @ApiModelProperty(value = \\\"考试方式\\\")\\n    private Integer way;\\n    @ApiModelProperty(value = \\\"试卷总分值\\\")\\n    private String score;\\n    @ApiModelProperty(value = \\\"判断题总分值\\\")\\n    private String trueOrFalse;\\n    @ApiModelProperty(value = \\\"单选题总分值\\\")\\n    private String single;\\n    @ApiModelProperty(value = \\\"多选题总分值\\\")\\n    private String multiple;\\n    @ApiModelProperty(value = \\\"填空题总分值\\\")\\n    private String gap;\\n    @ApiModelProperty(value = \\\"开始时间\\\")\\n    private Integer startTime;\\n    @ApiModelProperty(value = \\\"结束时间\\\")\\n    private Integer endTime;\\n    @ApiModelProperty(value = \\\"考试时长\\\")\\n    private Integer duration;\\n    @ApiModelProperty(value = \\\"发布范围\\\")\\n    private String range;\\n    @ApiModelProperty(value = \\\"是否发短信\\\")\\n    private Integer isNote;\\n    @ApiModelProperty(value = \\\"是否发推送\\\")\\n    private Integer isPush;\\n    @ApiModelProperty(value = \\\"是否组卷\\\")\\n    private Integer isMake;\\n\\n    public String getId() { \\n        return id;\\n    } \\n\\n    public void setId(String id) { \\n        this.id = id;\\n    } \\n\\n    public String getName() { \\n        return name;\\n    } \\n\\n    public void setName(String name) { \\n        this.name = name;\\n    } \\n\\n    public Integer getStatus() { \\n        return status;\\n    } \\n\\n    public void setStatus(Integer status) { \\n        this.status = status;\\n    } \\n\\n    public Integer getReleaseStatus() { \\n        return releaseStatus;\\n    } \\n\\n    public void setReleaseStatus(Integer releaseStatus) { \\n        this.releaseStatus = releaseStatus;\\n    } \\n\\n    public Integer getType() { \\n        return type;\\n    } \\n\\n    public void setType(Integer type) { \\n        this.type = type;\\n    } \\n\\n    public Integer getWay() { \\n        return way;\\n    } \\n\\n    public void setWay(Integer way) { \\n        this.way = way;\\n    } \\n\\n    public String getScore() { \\n        return score;\\n    } \\n\\n    public void setScore(String score) { \\n        this.score = score;\\n    } \\n\\n    public String getTrueOrFalse() { \\n        return trueOrFalse;\\n    } \\n\\n    public void setTrueOrFalse(String trueOrFalse) { \\n        this.trueOrFalse = trueOrFalse;\\n    } \\n\\n    public String getSingle() { \\n        return single;\\n    } \\n\\n    public void setSingle(String single) { \\n        this.single = single;\\n    } \\n\\n    public String getMultiple() { \\n        return multiple;\\n    } \\n\\n    public void setMultiple(String multiple) { \\n        this.multiple = multiple;\\n    } \\n\\n    public String getGap() { \\n        return gap;\\n    } \\n\\n    public void setGap(String gap) { \\n        this.gap = gap;\\n    } \\n\\n    public Integer getStartTime() { \\n        return startTime;\\n    } \\n\\n    public void setStartTime(Integer startTime) { \\n        this.startTime = startTime;\\n    } \\n\\n    public Integer getEndTime() { \\n        return endTime;\\n    } \\n\\n    public void setEndTime(Integer endTime) { \\n        this.endTime = endTime;\\n    } \\n\\n    public Integer getDuration() { \\n        return duration;\\n    } \\n\\n    public void setDuration(Integer duration) { \\n        this.duration = duration;\\n    } \\n\\n    public String getRange() { \\n        return range;\\n    } \\n\\n    public void setRange(String range) { \\n        this.range = range;\\n    } \\n\\n    public Integer getIsNote() { \\n        return isNote;\\n    } \\n\\n    public void setIsNote(Integer isNote) { \\n        this.isNote = isNote;\\n    } \\n\\n    public Integer getIsPush() { \\n        return isPush;\\n    } \\n\\n    public void setIsPush(Integer isPush) { \\n        this.isPush = isPush;\\n    } \\n\\n    public Integer getIsMake() { \\n        return isMake;\\n    } \\n\\n    public void setIsMake(Integer isMake) { \\n        this.isMake = isMake;\\n    } \\n\\n}\\n\",\"model\":\"package com.xtaller.party.core.model;\\n\\nimport com.baomidou.mybatisplus.activerecord.Model;\\nimport com.baomidou.mybatisplus.annotations.TableName;\\nimport com.xtaller.party.utils.kit.IdKit;\\nimport java.io.Serializable;\\n\\n/**\\n * Created by Party on 2019/05/31\\n */\\n@SuppressWarnings(\\\"serial\\\")\\n@TableName(value = \\\"exam\\\")\\npublic class Exam extends Model<Exam> {\\n  private String id = IdKit.getId(this.getClass());\\n  private String name;\\n  private Integer status;\\n  private Integer releaseStatus;\\n  private Integer type;\\n  private Integer way;\\n  private String score;\\n  private String trueOrFalse;\\n  private String single;\\n  private String multiple;\\n  private String gap;\\n  private Integer startTime;\\n  private Integer endTime;\\n  private Integer duration;\\n  private String range;\\n  private Integer isNote;\\n  private Integer isPush;\\n  private Integer isMake;\\n  private String creator;\\n  private Integer createTime;\\n  private String reviser;\\n  private Integer reviseTime;\\n  private Integer isDel;\\n\\n  public String getId() { \\n      return id;\\n  } \\n  public void setId(String id) { \\n      this.id = id;\\n  } \\n  public String getName() { \\n      return name;\\n  } \\n  public void setName(String name) { \\n      this.name = name;\\n  } \\n  public Integer getStatus() { \\n      return status;\\n  } \\n  public void setStatus(Integer status) { \\n      this.status = status;\\n  } \\n  public Integer getReleaseStatus() { \\n      return releaseStatus;\\n  } \\n  public void setReleaseStatus(Integer releaseStatus) { \\n      this.releaseStatus = releaseStatus;\\n  } \\n  public Integer getType() { \\n      return type;\\n  } \\n  public void setType(Integer type) { \\n      this.type = type;\\n  } \\n  public Integer getWay() { \\n      return way;\\n  } \\n  public void setWay(Integer way) { \\n      this.way = way;\\n  } \\n  public String getScore() { \\n      return score;\\n  } \\n  public void setScore(String score) { \\n      this.score = score;\\n  } \\n  public String getTrueOrFalse() { \\n      return trueOrFalse;\\n  } \\n  public void setTrueOrFalse(String trueOrFalse) { \\n      this.trueOrFalse = trueOrFalse;\\n  } \\n  public String getSingle() { \\n      return single;\\n  } \\n  public void setSingle(String single) { \\n      this.single = single;\\n  } \\n  public String getMultiple() { \\n      return multiple;\\n  } \\n  public void setMultiple(String multiple) { \\n      this.multiple = multiple;\\n  } \\n  public String getGap() { \\n      return gap;\\n  } \\n  public void setGap(String gap) { \\n      this.gap = gap;\\n  } \\n  public Integer getStartTime() { \\n      return startTime;\\n  } \\n  public void setStartTime(Integer startTime) { \\n      this.startTime = startTime;\\n  } \\n  public Integer getEndTime() { \\n      return endTime;\\n  } \\n  public void setEndTime(Integer endTime) { \\n      this.endTime = endTime;\\n  } \\n  public Integer getDuration() { \\n      return duration;\\n  } \\n  public void setDuration(Integer duration) { \\n      this.duration = duration;\\n  } \\n  public String getRange() { \\n      return range;\\n  } \\n  public void setRange(String range) { \\n      this.range = range;\\n  } \\n  public Integer getIsNote() { \\n      return isNote;\\n  } \\n  public void setIsNote(Integer isNote) { \\n      this.isNote = isNote;\\n  } \\n  public Integer getIsPush() { \\n      return isPush;\\n  } \\n  public void setIsPush(Integer isPush) { \\n      this.isPush = isPush;\\n  } \\n  public Integer getIsMake() { \\n      return isMake;\\n  } \\n  public void setIsMake(Integer isMake) { \\n      this.isMake = isMake;\\n  } \\n  public String getCreator() { \\n      return creator;\\n  } \\n  public void setCreator(String creator) { \\n      this.creator = creator;\\n  } \\n  public Integer getCreateTime() { \\n      return createTime;\\n  } \\n  public void setCreateTime(Integer createTime) { \\n      this.createTime = createTime;\\n  } \\n  public String getReviser() { \\n      return reviser;\\n  } \\n  public void setReviser(String reviser) { \\n      this.reviser = reviser;\\n  } \\n  public Integer getReviseTime() { \\n      return reviseTime;\\n  } \\n  public void setReviseTime(Integer reviseTime) { \\n      this.reviseTime = reviseTime;\\n  } \\n  public Integer getIsDel() { \\n      return isDel;\\n  } \\n  public void setIsDel(Integer isDel) { \\n      this.isDel = isDel;\\n  } \\n  @Override\\n  protected Serializable pkVal() {\\n      return id;\\n  }\\n}\\n\",\"mapper\":\"package com.xtaller.party.core.mapper;\\n\\nimport com.baomidou.mybatisplus.mapper.BaseMapper;\\nimport com.alibaba.fastjson.JSONObject;\\nimport org.apache.ibatis.annotations.Param;\\nimport org.apache.ibatis.annotations.Select;\\nimport com.xtaller.party.core.model.Exam;\\n\\nimport java.util.List;\\n/**\\n * Created by Party on 2019/05/31\\n */\\npublic interface ExamMapper extends BaseMapper<Exam> {\\n\\n    @Select(\\\"SELECT a.* \\\" + \\n            \\\",FROM_UNIXTIME(startTime) startTimeStr  \\\" +\\n            \\\",FROM_UNIXTIME(endTime) endTimeStr  \\\" +\\n              \\\"FROM exam a \\\" + \\n            \\\"JOIN (SELECT id from exam where isDel = 0 ${where} \\\" + \\n            \\\")b ON a.id=b.id order by a.createTime asc LIMIT #{index}, #{size} \\\") \\n    List<JSONObject> getPage(@Param(\\\"where\\\") String where, \\n                             @Param(\\\"index\\\") int index, \\n                             @Param(\\\"size\\\") int size); \\n\\n    @Select(\\\"select count(1) total from exam where isDel = 0 ${where} \\\") \\n    JSONObject getPageCount(@Param(\\\"where\\\") String where); \\n\\n    @Select(\\\"SELECT a.* FROM exam a where 1=1 and isDel=0 ${where}  order by createTime desc\\\")\\n    List<JSONObject> queryAll(@Param(\\\"where\\\") String where);\\n}\",\"iService\":\"package com.xtaller.party.core.service;\\n\\nimport java.util.List;\\nimport com.xtaller.party.utils.bean.Where;\\nimport com.xtaller.party.core.model.Exam;\\n\\n/**\\n* Created by Party on 2019/05/31\\n*/\\npublic interface IExamService {\\n   /******************* CURD ********************/ \\n   // 创建 \\n   Exam createExam(Exam model); \\n   // 删除 \\n   Boolean deleteExam(Object ids,String reviser);\\n   // 修改 \\n   Exam updateExam(Exam model); \\n   // 查询 \\n   List<Exam> findByIds(Object ids);\\n   // 属于 \\n   Boolean exist(List<Where> w); \\n   // 查询一个id是否存在 \\n   Boolean existId(Object id); \\n   /******************* CURD ********************/ \\n \\n}\",\"Api\":\"package com.xtaller.party.api.admin;\\n\\nimport com.alibaba.fastjson.JSONObject;\\nimport com.xtaller.party.api.BaseApi;\\nimport com.xtaller.party.api.BaseApi;\\nimport com.xtaller.party.core.model.Exam;\\nimport com.xtaller.party.core.service.impl.ExamService;\\nimport com.xtaller.party.doc.ExamCreate;\\nimport com.xtaller.party.doc.ExamUpdate;\\nimport com.xtaller.party.utils.bean.Page;\\nimport com.xtaller.party.utils.convert.R;\\nimport com.xtaller.party.utils.convert.S;\\nimport com.xtaller.party.utils.convert.V;\\nimport com.xtaller.party.utils.convert.W;\\nimport io.swagger.annotations.Api;\\nimport io.swagger.annotations.ApiOperation;\\nimport org.springframework.beans.factory.annotation.Autowired;\\nimport org.springframework.web.bind.annotation.*;\\n\\nimport java.util.HashMap;\\nimport java.util.List;\\nimport java.util.Map;\\n\\n/**\\n * Created by party on 2019/05/31\\n */\\n@Api(value = \\\"50_试卷管理\\\")\\n@RestController\\n@RequestMapping(\\\"/v1/base\\\")\\npublic class ExamApi extends BaseApi {\\n    @Autowired\\n    private ExamService examService;\\n\\n    @PostMapping(\\\"/exam\\\")\\n    @ApiOperation(value = \\\"试卷新增\\\")\\n    public Object createExam(@RequestBody ExamCreate object,\\n                              @RequestHeader(\\\"token\\\") String token){\\n        String userId = getUserIdByCache(token);   \\n\\n        //映射对象\\n        Exam model = o2c(object,token, Exam.class);\\n        //数据校验\\n        JSONObject check = V.checkEmpty(verify(),object);\\n        if(check.getBoolean(\\\"check\\\"))\\n        return R.error(check.getString(\\\"message\\\"));\\n\\n        //TODO 写校验重复的条件\\n        //Boolean exist = examService.exist(W.f(\\n        //        W.and(\\\"code\\\",\\\"eq\\\",model.getCode()),\\n        //        W.and(\\\"isDel\\\",\\\"eq\\\",\\\"0\\\"))\\n        //);\\n        //if(exist)\\n        //    return R.error(\\\"代码已经存在请更换一个代码\\\");\\n\\n        model = examService.createExam(model);\\n        if(model == null)\\n            return R.error(\\\"新增失败\\\");\\n        return R.ok(\\\"新增成功\\\",fm2(model));\\n    }\\n\\n   @PutMapping(\\\"/exam\\\")\\n   @ApiOperation(value = \\\"修改试卷\\\")\\n   public Object updateExam(@RequestBody ExamUpdate object,\\n                             @RequestHeader(\\\"token\\\") String token){\\n        String userId = getUserIdByCache(token);\\n        //映射对象\\n        Exam model = o2c(object,token, Exam.class);\\n        //数据校验\\n        JSONObject check = V.checkEmpty(updateVerify(),object);\\n        if(check.getBoolean(\\\"check\\\"))\\n            return R.error(check.getString(\\\"message\\\"));\\n              Exam data= examService.selectById(model.getId());\\n      \\n             if(data==null){  \\n                  return R.error(\\\"该信息不存在，无法修改\\\"); \\n             } \\n        //TODO 写校验重复的条件\\n        //   if(!model.getCode().equals(data.getCode())) {   \\n        //Boolean exist = examService.exist(W.f(\\n        //        W.and(\\\"code\\\",\\\"eq\\\",model.getCode()),\\n        //        W.and(\\\"isDel\\\",\\\"eq\\\",\\\"0\\\"))\\n        //);\\n        //if(exist)\\n        //    return R.error(\\\"代码已经存在请更换一个代码\\\");\\n\\n        //     }    \\n        model.setReviser(userId);\\n        model = examService.updateExam(model);\\n        if(model == null)\\n            return R.error(\\\"修改失败\\\");\\n        return R.ok(\\\"修改成功\\\",fm2(model));\\n    }\\n\\n    @DeleteMapping(\\\"/exam/{id}\\\")\\n    @ApiOperation(value = \\\"试卷删除\\\")\\n    public Object deleteExam(@PathVariable(\\\"id\\\") String id,\\n                                                  @RequestHeader(\\\"token\\\") String token){\\n        if(!examService.existId(id))\\n            return R.error(\\\"Id数据异常\\\");\\n\\n        if(examService.deleteExam(id, cacheKit.getUserId(token)))\\n            return R.ok(\\\"删除成功\\\");\\n        return R.error(\\\"删除失败\\\");\\n    }\\n\\n    @GetMapping(\\\"/exam/{index}-{size}-{key}\\\")\\n    @ApiOperation(value = \\\"读取试卷分页列表\\\")\\n    public Object getExam(@PathVariable(\\\"index\\\") int index,\\n                              @PathVariable(\\\"size\\\") int size,\\n                              @PathVariable(\\\"key\\\") String key,\\n                              @RequestHeader(\\\"token\\\") String token) {\\n        String wKey = \\\"\\\";\\n        if (!V.isEmpty(key))\\n        //FIXME 修改id为需要查询的字段\\n            wKey = S.apppend(\\\" and id like \'%\\\", key, \\\"%\' \\\");\\n        return R.ok(examService.page(index, size, wKey));\\n    }\\n\\n    @GetMapping(\\\"/exam\\\")\\n    @ApiOperation(value = \\\"读取试卷所有列表\\\")\\n    public Object getAllExam(@RequestHeader(\\\"token\\\") String token) {\\n        return R.ok(examService.queryAll(\\\"\\\"));\\n    }\\n\\n    private Map<String, String> verify() {\\n        Map<String, String> verify = new HashMap<>();\\n        verify.put(\\\"name\\\", \\\"请输入试卷名称\\\");\\n        verify.put(\\\"status\\\", \\\"请输入考试状态\\\");\\n        verify.put(\\\"releaseStatus\\\", \\\"请输入发布状态\\\");\\n        verify.put(\\\"type\\\", \\\"请输入试卷类型\\\");\\n        verify.put(\\\"way\\\", \\\"请输入考试方式\\\");\\n        verify.put(\\\"score\\\", \\\"请输入试卷总分值\\\");\\n        verify.put(\\\"trueOrFalse\\\", \\\"请输入判断题总分值\\\");\\n        verify.put(\\\"single\\\", \\\"请输入单选题总分值\\\");\\n        verify.put(\\\"multiple\\\", \\\"请输入多选题总分值\\\");\\n        verify.put(\\\"gap\\\", \\\"请输入填空题总分值\\\");\\n        verify.put(\\\"startTime\\\", \\\"请输入开始时间\\\");\\n        verify.put(\\\"endTime\\\", \\\"请输入结束时间\\\");\\n        verify.put(\\\"duration\\\", \\\"请输入考试时长\\\");\\n        verify.put(\\\"range\\\", \\\"请输入发布范围\\\");\\n        verify.put(\\\"isNote\\\", \\\"请输入是否发短信\\\");\\n        verify.put(\\\"isPush\\\", \\\"请输入是否发推送\\\");\\n        verify.put(\\\"isMake\\\", \\\"请输入是否组卷\\\");\\n        return verify;\\n    }\\n\\n    private Map<String, String> updateVerify() {\\n        Map<String, String> verify = new HashMap<>();\\n        verify.put(\\\"name\\\", \\\"请输入试卷名称\\\");\\n        verify.put(\\\"status\\\", \\\"请输入考试状态\\\");\\n        verify.put(\\\"releaseStatus\\\", \\\"请输入发布状态\\\");\\n        verify.put(\\\"type\\\", \\\"请输入试卷类型\\\");\\n        verify.put(\\\"way\\\", \\\"请输入考试方式\\\");\\n        verify.put(\\\"score\\\", \\\"请输入试卷总分值\\\");\\n        verify.put(\\\"trueOrFalse\\\", \\\"请输入判断题总分值\\\");\\n        verify.put(\\\"single\\\", \\\"请输入单选题总分值\\\");\\n        verify.put(\\\"multiple\\\", \\\"请输入多选题总分值\\\");\\n        verify.put(\\\"gap\\\", \\\"请输入填空题总分值\\\");\\n        verify.put(\\\"startTime\\\", \\\"请输入开始时间\\\");\\n        verify.put(\\\"endTime\\\", \\\"请输入结束时间\\\");\\n        verify.put(\\\"duration\\\", \\\"请输入考试时长\\\");\\n        verify.put(\\\"range\\\", \\\"请输入发布范围\\\");\\n        verify.put(\\\"isNote\\\", \\\"请输入是否发短信\\\");\\n        verify.put(\\\"isPush\\\", \\\"请输入是否发推送\\\");\\n        verify.put(\\\"isMake\\\", \\\"请输入是否组卷\\\");\\n        return verify;\\n    }\\n\\n}\\n\"}', '1', '0', '1559279037', '0', '0', '0');
INSERT INTO `sys_auto_code` VALUES ('1993180011422370152', 'exam_candidate', '{\"service\":\"package com.xtaller.party.core.service.impl;\\n\\nimport com.alibaba.fastjson.JSONObject;\\nimport com.xtaller.party.core.mapper.ExamCandidateMapper;\\nimport com.xtaller.party.core.model.ExamCandidate;\\nimport com.xtaller.party.core.service.IExamCandidateService;\\nimport com.xtaller.party.utils.base.TServiceImpl;\\nimport com.xtaller.party.utils.bean.Page;\\nimport com.xtaller.party.utils.convert.F;\\nimport org.springframework.stereotype.Service;\\n\\nimport java.util.ArrayList;\\nimport java.util.List;\\nimport com.xtaller.party.utils.convert.W;\\nimport com.xtaller.party.utils.bean.Where;\\n/**\\n * Created by Party on 2019/05/28\\n */\\n@Service\\npublic class ExamCandidateService extends TServiceImpl<ExamCandidateMapper,ExamCandidate> implements IExamCandidateService {\\n   /**************************CURD begin******************************/ \\n   // 创建\\n   @Override\\n   public ExamCandidate createExamCandidate(ExamCandidate model) {\\n       if(this.insert(model))\\n           return this.selectById(model.getId());\\n       return null;\\n   }\\n \\n   // 删除\\n   @Override\\n   public Boolean deleteExamCandidate(Object ids,String reviser) {\\n       return this.delete(ids,reviser);\\n   }\\n \\n   // 修改\\n   @Override\\n   public ExamCandidate updateExamCandidate(ExamCandidate model) {\\n       if(this.update(model))\\n           return this.selectById(model.getId());\\n       return null;\\n   }\\n \\n   // 查询\\n   @Override\\n   public List<ExamCandidate> findByIds(Object ids) {\\n       return this.selectByIds(ids);\\n   }\\n \\n   // 属于\\n   @Override\\n   public Boolean exist(List<Where> w) {\\n       w.add(new Where(\\\"1\\\"));\\n       return this.query(w).size() > 0;\\n   }\\n \\n   // 查询一个id是否存在\\n   @Override\\n   public Boolean existId(Object id) {\\n       where = W.f(\\n               W.and(\\\"id\\\",\\\"eq\\\",id),\\n               W.field(\\\"1\\\")\\n       );\\n       return this.query(where).size() > 0;\\n   }\\n \\n   /**************************CURD end********************************/ \\n    //分页查\\n    public Page page(int index, int pageSize, String w) {\\n        // 总记录数\\n        JSONObject row = baseMapper.getPageCount(w);\\n        int totalCount = row.getInteger(\\\"total\\\");\\n        if(totalCount == 0)\\n            return new Page(new ArrayList<JSONObject>(),pageSize,0,0,1);\\n        // 分页数据\\n        index = index < 0 ? 1:index;\\n        int limit = (index - 1) * pageSize;\\n        int totalPage = totalCount % pageSize == 0 ? totalCount/pageSize : (totalCount/pageSize)+1;\\n        int currentPage = index;\\n\\n        List<JSONObject> grades = baseMapper.getPage(w,limit,pageSize);\\n\\n            return new Page(F.f2l(grades,\\\"id\\\"), pageSize, totalCount, totalPage, currentPage);\\n    }\\n\\n    //全查\\n    public List<JSONObject> queryAll(String where) {\\n        List<JSONObject> list = baseMapper.queryAll(where);\\n        return F.f2l(list,\\\"id\\\",\\\"creator\\\",\\\"reverse\\\");\\n    }\\n\\n}\",\"create\":\"package com.xtaller.party.doc;\\n\\nimport com.fasterxml.jackson.annotation.JsonIgnoreProperties;\\nimport com.fasterxml.jackson.annotation.JsonInclude;\\nimport io.swagger.annotations.ApiModel;\\nimport io.swagger.annotations.ApiModelProperty;\\n\\n/**\\n * Created by Party on 2019/05/28\\n */\\n@JsonInclude(JsonInclude.Include.NON_NULL)\\n@JsonIgnoreProperties({ \\\"handler\\\",\\\"hibernateLazyInitializer\\\" })\\n@ApiModel(value =\\\"试卷和考生关系新增\\\")\\npublic class ExamCandidateCreate {\\n    @ApiModelProperty(value = \\\"考生学号\\\")\\n    private String number;\\n    @ApiModelProperty(value = \\\"试卷Id\\\")\\n    private String examId;\\n    @ApiModelProperty(value = \\\"考场Id\\\")\\n    private String roomId;\\n    @ApiModelProperty(value = \\\"考试得分\\\")\\n    private String score;\\n    @ApiModelProperty(value = \\\"考试状态\\\")\\n    private Integer status;\\n    @ApiModelProperty(value = \\\"备注\\\")\\n    private String remark;\\n    @ApiModelProperty(value = \\\"提交IP\\\")\\n    private String ipAddr;\\n\\n    public String getNumber() { \\n        return number;\\n    } \\n\\n    public void setNumber(String number) { \\n        this.number = number;\\n    } \\n\\n    public String getExamId() { \\n        return examId;\\n    } \\n\\n    public void setExamId(String examId) { \\n        this.examId = examId;\\n    } \\n\\n    public String getRoomId() { \\n        return roomId;\\n    } \\n\\n    public void setRoomId(String roomId) { \\n        this.roomId = roomId;\\n    } \\n\\n    public String getScore() { \\n        return score;\\n    } \\n\\n    public void setScore(String score) { \\n        this.score = score;\\n    } \\n\\n    public Integer getStatus() { \\n        return status;\\n    } \\n\\n    public void setStatus(Integer status) { \\n        this.status = status;\\n    } \\n\\n    public String getRemark() { \\n        return remark;\\n    } \\n\\n    public void setRemark(String remark) { \\n        this.remark = remark;\\n    } \\n\\n    public String getIpAddr() { \\n        return ipAddr;\\n    } \\n\\n    public void setIpAddr(String ipAddr) { \\n        this.ipAddr = ipAddr;\\n    } \\n\\n}\\n\",\"update\":\"package com.xtaller.party.doc;\\n\\nimport com.fasterxml.jackson.annotation.JsonIgnoreProperties;\\nimport com.fasterxml.jackson.annotation.JsonInclude;\\nimport io.swagger.annotations.ApiModel;\\nimport io.swagger.annotations.ApiModelProperty;\\n\\n/**\\n * Created by Party on 2019/05/28\\n */\\n@JsonInclude(JsonInclude.Include.NON_NULL)\\n@JsonIgnoreProperties({ \\\"handler\\\",\\\"hibernateLazyInitializer\\\" })\\n@ApiModel(value =\\\"试卷和考生关系修改\\\")\\npublic class ExamCandidateUpdate {\\n    @ApiModelProperty(value = \\\"\\\")\\n    private String id;\\n    @ApiModelProperty(value = \\\"考生学号\\\")\\n    private String number;\\n    @ApiModelProperty(value = \\\"试卷Id\\\")\\n    private String examId;\\n    @ApiModelProperty(value = \\\"考场Id\\\")\\n    private String roomId;\\n    @ApiModelProperty(value = \\\"考试得分\\\")\\n    private String score;\\n    @ApiModelProperty(value = \\\"考试状态\\\")\\n    private Integer status;\\n    @ApiModelProperty(value = \\\"备注\\\")\\n    private String remark;\\n    @ApiModelProperty(value = \\\"提交IP\\\")\\n    private String ipAddr;\\n\\n    public String getId() { \\n        return id;\\n    } \\n\\n    public void setId(String id) { \\n        this.id = id;\\n    } \\n\\n    public String getNumber() { \\n        return number;\\n    } \\n\\n    public void setNumber(String number) { \\n        this.number = number;\\n    } \\n\\n    public String getExamId() { \\n        return examId;\\n    } \\n\\n    public void setExamId(String examId) { \\n        this.examId = examId;\\n    } \\n\\n    public String getRoomId() { \\n        return roomId;\\n    } \\n\\n    public void setRoomId(String roomId) { \\n        this.roomId = roomId;\\n    } \\n\\n    public String getScore() { \\n        return score;\\n    } \\n\\n    public void setScore(String score) { \\n        this.score = score;\\n    } \\n\\n    public Integer getStatus() { \\n        return status;\\n    } \\n\\n    public void setStatus(Integer status) { \\n        this.status = status;\\n    } \\n\\n    public String getRemark() { \\n        return remark;\\n    } \\n\\n    public void setRemark(String remark) { \\n        this.remark = remark;\\n    } \\n\\n    public String getIpAddr() { \\n        return ipAddr;\\n    } \\n\\n    public void setIpAddr(String ipAddr) { \\n        this.ipAddr = ipAddr;\\n    } \\n\\n}\\n\",\"model\":\"package com.xtaller.party.core.model;\\n\\nimport com.baomidou.mybatisplus.activerecord.Model;\\nimport com.baomidou.mybatisplus.annotations.TableName;\\nimport com.xtaller.party.utils.kit.IdKit;\\nimport java.io.Serializable;\\n\\n/**\\n * Created by Party on 2019/05/28\\n */\\n@SuppressWarnings(\\\"serial\\\")\\n@TableName(value = \\\"exam_candidate\\\")\\npublic class ExamCandidate extends Model<ExamCandidate> {\\n  private String id = IdKit.getId(this.getClass());\\n  private String number;\\n  private String examId;\\n  private String roomId;\\n  private String score;\\n  private Integer status;\\n  private String remark;\\n  private String ipAddr;\\n  private String creator;\\n  private Integer createTime;\\n  private String reviser;\\n  private Integer reviseTime;\\n  private Integer isDel;\\n\\n  public String getId() { \\n      return id;\\n  } \\n  public void setId(String id) { \\n      this.id = id;\\n  } \\n  public String getNumber() { \\n      return number;\\n  } \\n  public void setNumber(String number) { \\n      this.number = number;\\n  } \\n  public String getExamId() { \\n      return examId;\\n  } \\n  public void setExamId(String examId) { \\n      this.examId = examId;\\n  } \\n  public String getRoomId() { \\n      return roomId;\\n  } \\n  public void setRoomId(String roomId) { \\n      this.roomId = roomId;\\n  } \\n  public String getScore() { \\n      return score;\\n  } \\n  public void setScore(String score) { \\n      this.score = score;\\n  } \\n  public Integer getStatus() { \\n      return status;\\n  } \\n  public void setStatus(Integer status) { \\n      this.status = status;\\n  } \\n  public String getRemark() { \\n      return remark;\\n  } \\n  public void setRemark(String remark) { \\n      this.remark = remark;\\n  } \\n  public String getIpAddr() { \\n      return ipAddr;\\n  } \\n  public void setIpAddr(String ipAddr) { \\n      this.ipAddr = ipAddr;\\n  } \\n  public String getCreator() { \\n      return creator;\\n  } \\n  public void setCreator(String creator) { \\n      this.creator = creator;\\n  } \\n  public Integer getCreateTime() { \\n      return createTime;\\n  } \\n  public void setCreateTime(Integer createTime) { \\n      this.createTime = createTime;\\n  } \\n  public String getReviser() { \\n      return reviser;\\n  } \\n  public void setReviser(String reviser) { \\n      this.reviser = reviser;\\n  } \\n  public Integer getReviseTime() { \\n      return reviseTime;\\n  } \\n  public void setReviseTime(Integer reviseTime) { \\n      this.reviseTime = reviseTime;\\n  } \\n  public Integer getIsDel() { \\n      return isDel;\\n  } \\n  public void setIsDel(Integer isDel) { \\n      this.isDel = isDel;\\n  } \\n  @Override\\n  protected Serializable pkVal() {\\n      return id;\\n  }\\n}\\n\",\"mapper\":\"package com.xtaller.party.core.mapper;\\n\\nimport com.baomidou.mybatisplus.mapper.BaseMapper;\\nimport com.alibaba.fastjson.JSONObject;\\nimport org.apache.ibatis.annotations.Param;\\nimport org.apache.ibatis.annotations.Select;\\nimport com.xtaller.party.core.model.ExamCandidate;\\n\\nimport java.util.List;\\n/**\\n * Created by Party on 2019/05/28\\n */\\npublic interface ExamCandidateMapper extends BaseMapper<ExamCandidate> {\\n\\n    @Select(\\\"SELECT a.* \\\" + \\n              \\\"FROM exam_candidate a \\\" + \\n            \\\"JOIN (SELECT id from exam_candidate where isDel = 0 ${where} \\\" + \\n            \\\")b ON a.id=b.id order by a.createTime asc LIMIT #{index}, #{size} \\\") \\n    List<JSONObject> getPage(@Param(\\\"where\\\") String where, \\n                             @Param(\\\"index\\\") int index, \\n                             @Param(\\\"size\\\") int size); \\n\\n    @Select(\\\"select count(1) total from exam_candidate where isDel = 0 ${where} \\\") \\n    JSONObject getPageCount(@Param(\\\"where\\\") String where); \\n\\n    @Select(\\\"SELECT a.* FROM exam_candidate a where 1=1 and isDel=0 ${where}  order by createTime desc\\\")\\n    List<JSONObject> queryAll(@Param(\\\"where\\\") String where);\\n}\",\"iService\":\"package com.xtaller.party.core.service;\\n\\nimport java.util.List;\\nimport com.xtaller.party.utils.bean.Where;\\nimport com.xtaller.party.core.model.ExamCandidate;\\n\\n/**\\n* Created by Party on 2019/05/28\\n*/\\npublic interface IExamCandidateService {\\n   /******************* CURD ********************/ \\n   // 创建 \\n   ExamCandidate createExamCandidate(ExamCandidate model); \\n   // 删除 \\n   Boolean deleteExamCandidate(Object ids,String reviser);\\n   // 修改 \\n   ExamCandidate updateExamCandidate(ExamCandidate model); \\n   // 查询 \\n   List<ExamCandidate> findByIds(Object ids);\\n   // 属于 \\n   Boolean exist(List<Where> w); \\n   // 查询一个id是否存在 \\n   Boolean existId(Object id); \\n   /******************* CURD ********************/ \\n \\n}\",\"Api\":\"package com.xtaller.party.api.admin;\\n\\nimport com.alibaba.fastjson.JSONObject;\\nimport com.xtaller.party.api.BaseApi;\\nimport com.xtaller.party.api.BaseApi;\\nimport com.xtaller.party.core.model.ExamCandidate;\\nimport com.xtaller.party.core.service.impl.ExamCandidateService;\\nimport com.xtaller.party.doc.ExamCandidateCreate;\\nimport com.xtaller.party.doc.ExamCandidateUpdate;\\nimport com.xtaller.party.utils.bean.Page;\\nimport com.xtaller.party.utils.convert.R;\\nimport com.xtaller.party.utils.convert.S;\\nimport com.xtaller.party.utils.convert.V;\\nimport com.xtaller.party.utils.convert.W;\\nimport io.swagger.annotations.Api;\\nimport io.swagger.annotations.ApiOperation;\\nimport org.springframework.beans.factory.annotation.Autowired;\\nimport org.springframework.web.bind.annotation.*;\\n\\nimport java.util.HashMap;\\nimport java.util.List;\\nimport java.util.Map;\\n\\n/**\\n * Created by party on 2019/05/28\\n */\\n@Api(value = \\\"52_试卷和考生关系管理\\\")\\n@RestController\\n@RequestMapping(\\\"/v1/base\\\")\\npublic class ExamCandidateApi extends BaseApi {\\n    @Autowired\\n    private ExamCandidateService examCandidateService;\\n\\n    @PostMapping(\\\"/examCandidate\\\")\\n    @ApiOperation(value = \\\"试卷和考生关系新增\\\")\\n    public Object createExamCandidate(@RequestBody ExamCandidateCreate object,\\n                              @RequestHeader(\\\"token\\\") String token){\\n        String userId = getUserIdByCache(token);   \\n\\n        //映射对象\\n        ExamCandidate model = o2c(object,token, ExamCandidate.class);\\n        //数据校验\\n        JSONObject check = V.checkEmpty(verify(),object);\\n        if(check.getBoolean(\\\"check\\\"))\\n        return R.error(check.getString(\\\"message\\\"));\\n\\n        //TODO 写校验重复的条件\\n        //Boolean exist = examCandidateService.exist(W.f(\\n        //        W.and(\\\"code\\\",\\\"eq\\\",model.getCode()),\\n        //        W.and(\\\"isDel\\\",\\\"eq\\\",\\\"0\\\"))\\n        //);\\n        //if(exist)\\n        //    return R.error(\\\"代码已经存在请更换一个代码\\\");\\n\\n        model = examCandidateService.createExamCandidate(model);\\n        if(model == null)\\n            return R.error(\\\"新增失败\\\");\\n        return R.ok(\\\"新增成功\\\",fm2(model));\\n    }\\n\\n   @PutMapping(\\\"/examCandidate\\\")\\n   @ApiOperation(value = \\\"修改试卷和考生关系\\\")\\n   public Object updateExamCandidate(@RequestBody ExamCandidateUpdate object,\\n                             @RequestHeader(\\\"token\\\") String token){\\n        String userId = getUserIdByCache(token);\\n        //映射对象\\n        ExamCandidate model = o2c(object,token, ExamCandidate.class);\\n        //数据校验\\n        JSONObject check = V.checkEmpty(updateVerify(),object);\\n        if(check.getBoolean(\\\"check\\\"))\\n            return R.error(check.getString(\\\"message\\\"));\\n              ExamCandidatedata= examCandidateService.selectById(model.getId());\\n      \\n             if(data==null){  \\n                  return R.error(\\\"该信息不存在，无法修改\\\"); \\n             } \\n        //TODO 写校验重复的条件\\n        //   if(!model.getCode().equals(data.getCode())) {   \\n        //Boolean exist = examCandidateService.exist(W.f(\\n        //        W.and(\\\"code\\\",\\\"eq\\\",model.getCode()),\\n        //        W.and(\\\"isDel\\\",\\\"eq\\\",\\\"0\\\"))\\n        //);\\n        //if(exist)\\n        //    return R.error(\\\"代码已经存在请更换一个代码\\\");\\n\\n        //     }    \\n        model.setReviser(userId);\\n        model = examCandidateService.updateExamCandidate(model);\\n        if(model == null)\\n            return R.error(\\\"修改失败\\\");\\n        return R.ok(\\\"修改成功\\\",fm2(model));\\n    }\\n\\n    @DeleteMapping(\\\"/examCandidate/{id}\\\")\\n    @ApiOperation(value = \\\"试卷和考生关系删除\\\")\\n    public Object deleteExamCandidate(@PathVariable(\\\"id\\\") String id,\\n                                                  @RequestHeader(\\\"token\\\") String token){\\n        if(!examCandidateService.existId(id))\\n            return R.error(\\\"Id数据异常\\\");\\n\\n        if(examCandidateService.deleteExamCandidate(id, cacheKit.getUserId(token)))\\n            return R.ok(\\\"删除成功\\\");\\n        return R.error(\\\"删除失败\\\");\\n    }\\n\\n    @GetMapping(\\\"/examCandidate/{index}-{size}-{key}\\\")\\n    @ApiOperation(value = \\\"读取试卷和考生关系分页列表\\\")\\n    public Object getExamCandidate(@PathVariable(\\\"index\\\") int index,\\n                              @PathVariable(\\\"size\\\") int size,\\n                              @PathVariable(\\\"key\\\") String key,\\n                              @RequestHeader(\\\"token\\\") String token) {\\n        String wKey = \\\"\\\";\\n        if (!V.isEmpty(key))\\n        //FIXME 修改id为需要查询的字段\\n            wKey = S.apppend(\\\" and id like \'%\\\", key, \\\"%\' \\\");\\n        return R.ok(examCandidateService.page(index, size, wKey));\\n    }\\n\\n    @GetMapping(\\\"/examCandidate\\\")\\n    @ApiOperation(value = \\\"读取试卷和考生关系所有列表\\\")\\n    public Object getAllExamCandidate(@RequestHeader(\\\"token\\\") String token) {\\n        return R.ok(examCandidateService.queryAll(\\\"\\\"));\\n    }\\n\\n    private Map<String, String> verify() {\\n        Map<String, String> verify = new HashMap<>();\\n        verify.put(\\\"number\\\", \\\"请输入考生学号\\\");\\n        verify.put(\\\"examId\\\", \\\"请输入试卷Id\\\");\\n        verify.put(\\\"roomId\\\", \\\"请输入考场Id\\\");\\n        verify.put(\\\"score\\\", \\\"请输入考试得分\\\");\\n        verify.put(\\\"status\\\", \\\"请输入考试状态\\\");\\n        verify.put(\\\"remark\\\", \\\"请输入备注\\\");\\n        verify.put(\\\"ipAddr\\\", \\\"请输入提交IP\\\");\\n        return verify;\\n    }\\n\\n    private Map<String, String> updateVerify() {\\n        Map<String, String> verify = new HashMap<>();\\n        verify.put(\\\"number\\\", \\\"请输入考生学号\\\");\\n        verify.put(\\\"examId\\\", \\\"请输入试卷Id\\\");\\n        verify.put(\\\"roomId\\\", \\\"请输入考场Id\\\");\\n        verify.put(\\\"score\\\", \\\"请输入考试得分\\\");\\n        verify.put(\\\"status\\\", \\\"请输入考试状态\\\");\\n        verify.put(\\\"remark\\\", \\\"请输入备注\\\");\\n        verify.put(\\\"ipAddr\\\", \\\"请输入提交IP\\\");\\n        return verify;\\n    }\\n\\n}\\n\"}', '1', '0', '1559044579', '0', '0', '0');
INSERT INTO `sys_auto_code` VALUES ('1993857203981687260', 'sys_user', '{\"service\":\"package com.xtaller.party.core.service.impl;\\n\\nimport com.alibaba.fastjson.JSONObject;\\nimport com.xtaller.party.core.mapper.SysUserMapper;\\nimport com.xtaller.party.core.model.SysUser;\\nimport com.xtaller.party.core.service.ISysUserService;\\nimport com.xtaller.party.utils.base.TServiceImpl;\\nimport com.xtaller.party.utils.bean.Page;\\nimport com.xtaller.party.utils.convert.F;\\nimport org.springframework.stereotype.Service;\\n\\nimport java.util.ArrayList;\\nimport java.util.List;\\nimport com.xtaller.party.utils.convert.W;\\nimport com.xtaller.party.utils.bean.Where;\\n/**\\n * Created by Party on 2019/06/02\\n */\\n@Service\\npublic class SysUserService extends TServiceImpl<SysUserMapper,SysUser> implements ISysUserService {\\n   /**************************CURD begin******************************/ \\n   // 创建\\n   @Override\\n   public SysUser createSysUser(SysUser model) {\\n       if(this.insert(model))\\n           return this.selectById(model.getId());\\n       return null;\\n   }\\n \\n   // 删除\\n   @Override\\n   public Boolean deleteSysUser(Object ids,String reviser) {\\n       return this.delete(ids,reviser);\\n   }\\n \\n   // 修改\\n   @Override\\n   public SysUser updateSysUser(SysUser model) {\\n       if(this.update(model))\\n           return this.selectById(model.getId());\\n       return null;\\n   }\\n \\n   // 查询\\n   @Override\\n   public List<SysUser> findByIds(Object ids) {\\n       return this.selectByIds(ids);\\n   }\\n \\n   // 属于\\n   @Override\\n   public Boolean exist(List<Where> w) {\\n       w.add(new Where(\\\"1\\\"));\\n       return this.query(w).size() > 0;\\n   }\\n \\n   // 查询一个id是否存在\\n   @Override\\n   public Boolean existId(Object id) {\\n       where = W.f(\\n               W.and(\\\"id\\\",\\\"eq\\\",id),\\n               W.field(\\\"1\\\")\\n       );\\n       return this.query(where).size() > 0;\\n   }\\n \\n   /**************************CURD end********************************/ \\n    //分页查\\n    public Page page(int index, int pageSize, String w) {\\n        // 总记录数\\n        JSONObject row = baseMapper.getPageCount(w);\\n        int totalCount = row.getInteger(\\\"total\\\");\\n        if(totalCount == 0)\\n            return new Page(new ArrayList<JSONObject>(),pageSize,0,0,1);\\n        // 分页数据\\n        index = index < 0 ? 1:index;\\n        int limit = (index - 1) * pageSize;\\n        int totalPage = totalCount % pageSize == 0 ? totalCount/pageSize : (totalCount/pageSize)+1;\\n        int currentPage = index;\\n\\n        List<JSONObject> grades = baseMapper.getPage(w,limit,pageSize);\\n\\n            return new Page(F.f2l(grades,\\\"id\\\"), pageSize, totalCount, totalPage, currentPage);\\n    }\\n\\n    //全查\\n    public List<JSONObject> queryAll(String where) {\\n        List<JSONObject> list = baseMapper.queryAll(where);\\n        return F.f2l(list,\\\"id\\\",\\\"creator\\\",\\\"reverse\\\");\\n    }\\n\\n}\",\"create\":\"package com.xtaller.party.doc;\\n\\nimport com.fasterxml.jackson.annotation.JsonIgnoreProperties;\\nimport com.fasterxml.jackson.annotation.JsonInclude;\\nimport io.swagger.annotations.ApiModel;\\nimport io.swagger.annotations.ApiModelProperty;\\n\\n/**\\n * Created by Party on 2019/06/02\\n */\\n@JsonInclude(JsonInclude.Include.NON_NULL)\\n@JsonIgnoreProperties({ \\\"handler\\\",\\\"hibernateLazyInitializer\\\" })\\n@ApiModel(value =\\\"用户新增\\\")\\npublic class SysUserCreate {\\n    @ApiModelProperty(value = \\\"登录名\\\")\\n    private String loginName;\\n    @ApiModelProperty(value = \\\"用户密码\\\")\\n    private String password;\\n    @ApiModelProperty(value = \\\"密码混淆盐\\\")\\n    private Integer salt;\\n    @ApiModelProperty(value = \\\"用户状态\\\")\\n    private Integer status;\\n    @ApiModelProperty(value = \\\"用户类型\\\")\\n    private Integer type;\\n    @ApiModelProperty(value = \\\"学号/工号\\\")\\n    private String number;\\n    @ApiModelProperty(value = \\\"手机号\\\")\\n    private String phone;\\n    @ApiModelProperty(value = \\\"手机验证码\\\")\\n    private String verifyCode;\\n    @ApiModelProperty(value = \\\"错误次数\\\")\\n    private Integer errorCount;\\n    @ApiModelProperty(value = \\\"解锁时间\\\")\\n    private Integer unlockTime;\\n    @ApiModelProperty(value = \\\"最后登录IP\\\")\\n    private String loginIp;\\n    @ApiModelProperty(value = \\\"最后登录时间\\\")\\n    private Integer loginTime;\\n\\n    public String getLoginName() { \\n        return loginName;\\n    } \\n\\n    public void setLoginName(String loginName) { \\n        this.loginName = loginName;\\n    } \\n\\n    public String getPassword() { \\n        return password;\\n    } \\n\\n    public void setPassword(String password) { \\n        this.password = password;\\n    } \\n\\n    public Integer getSalt() { \\n        return salt;\\n    } \\n\\n    public void setSalt(Integer salt) { \\n        this.salt = salt;\\n    } \\n\\n    public Integer getStatus() { \\n        return status;\\n    } \\n\\n    public void setStatus(Integer status) { \\n        this.status = status;\\n    } \\n\\n    public Integer getType() { \\n        return type;\\n    } \\n\\n    public void setType(Integer type) { \\n        this.type = type;\\n    } \\n\\n    public String getNumber() { \\n        return number;\\n    } \\n\\n    public void setNumber(String number) { \\n        this.number = number;\\n    } \\n\\n    public String getPhone() { \\n        return phone;\\n    } \\n\\n    public void setPhone(String phone) { \\n        this.phone = phone;\\n    } \\n\\n    public String getVerifyCode() { \\n        return verifyCode;\\n    } \\n\\n    public void setVerifyCode(String verifyCode) { \\n        this.verifyCode = verifyCode;\\n    } \\n\\n    public Integer getErrorCount() { \\n        return errorCount;\\n    } \\n\\n    public void setErrorCount(Integer errorCount) { \\n        this.errorCount = errorCount;\\n    } \\n\\n    public Integer getUnlockTime() { \\n        return unlockTime;\\n    } \\n\\n    public void setUnlockTime(Integer unlockTime) { \\n        this.unlockTime = unlockTime;\\n    } \\n\\n    public String getLoginIp() { \\n        return loginIp;\\n    } \\n\\n    public void setLoginIp(String loginIp) { \\n        this.loginIp = loginIp;\\n    } \\n\\n    public Integer getLoginTime() { \\n        return loginTime;\\n    } \\n\\n    public void setLoginTime(Integer loginTime) { \\n        this.loginTime = loginTime;\\n    } \\n\\n}\\n\",\"update\":\"package com.xtaller.party.doc;\\n\\nimport com.fasterxml.jackson.annotation.JsonIgnoreProperties;\\nimport com.fasterxml.jackson.annotation.JsonInclude;\\nimport io.swagger.annotations.ApiModel;\\nimport io.swagger.annotations.ApiModelProperty;\\n\\n/**\\n * Created by Party on 2019/06/02\\n */\\n@JsonInclude(JsonInclude.Include.NON_NULL)\\n@JsonIgnoreProperties({ \\\"handler\\\",\\\"hibernateLazyInitializer\\\" })\\n@ApiModel(value =\\\"用户修改\\\")\\npublic class SysUserUpdate {\\n    @ApiModelProperty(value = \\\"主键ID\\\")\\n    private String id;\\n    @ApiModelProperty(value = \\\"登录名\\\")\\n    private String loginName;\\n    @ApiModelProperty(value = \\\"用户密码\\\")\\n    private String password;\\n    @ApiModelProperty(value = \\\"密码混淆盐\\\")\\n    private Integer salt;\\n    @ApiModelProperty(value = \\\"用户状态\\\")\\n    private Integer status;\\n    @ApiModelProperty(value = \\\"用户类型\\\")\\n    private Integer type;\\n    @ApiModelProperty(value = \\\"学号/工号\\\")\\n    private String number;\\n    @ApiModelProperty(value = \\\"手机号\\\")\\n    private String phone;\\n    @ApiModelProperty(value = \\\"手机验证码\\\")\\n    private String verifyCode;\\n    @ApiModelProperty(value = \\\"错误次数\\\")\\n    private Integer errorCount;\\n    @ApiModelProperty(value = \\\"解锁时间\\\")\\n    private Integer unlockTime;\\n    @ApiModelProperty(value = \\\"最后登录IP\\\")\\n    private String loginIp;\\n    @ApiModelProperty(value = \\\"最后登录时间\\\")\\n    private Integer loginTime;\\n\\n    public String getId() { \\n        return id;\\n    } \\n\\n    public void setId(String id) { \\n        this.id = id;\\n    } \\n\\n    public String getLoginName() { \\n        return loginName;\\n    } \\n\\n    public void setLoginName(String loginName) { \\n        this.loginName = loginName;\\n    } \\n\\n    public String getPassword() { \\n        return password;\\n    } \\n\\n    public void setPassword(String password) { \\n        this.password = password;\\n    } \\n\\n    public Integer getSalt() { \\n        return salt;\\n    } \\n\\n    public void setSalt(Integer salt) { \\n        this.salt = salt;\\n    } \\n\\n    public Integer getStatus() { \\n        return status;\\n    } \\n\\n    public void setStatus(Integer status) { \\n        this.status = status;\\n    } \\n\\n    public Integer getType() { \\n        return type;\\n    } \\n\\n    public void setType(Integer type) { \\n        this.type = type;\\n    } \\n\\n    public String getNumber() { \\n        return number;\\n    } \\n\\n    public void setNumber(String number) { \\n        this.number = number;\\n    } \\n\\n    public String getPhone() { \\n        return phone;\\n    } \\n\\n    public void setPhone(String phone) { \\n        this.phone = phone;\\n    } \\n\\n    public String getVerifyCode() { \\n        return verifyCode;\\n    } \\n\\n    public void setVerifyCode(String verifyCode) { \\n        this.verifyCode = verifyCode;\\n    } \\n\\n    public Integer getErrorCount() { \\n        return errorCount;\\n    } \\n\\n    public void setErrorCount(Integer errorCount) { \\n        this.errorCount = errorCount;\\n    } \\n\\n    public Integer getUnlockTime() { \\n        return unlockTime;\\n    } \\n\\n    public void setUnlockTime(Integer unlockTime) { \\n        this.unlockTime = unlockTime;\\n    } \\n\\n    public String getLoginIp() { \\n        return loginIp;\\n    } \\n\\n    public void setLoginIp(String loginIp) { \\n        this.loginIp = loginIp;\\n    } \\n\\n    public Integer getLoginTime() { \\n        return loginTime;\\n    } \\n\\n    public void setLoginTime(Integer loginTime) { \\n        this.loginTime = loginTime;\\n    } \\n\\n}\\n\",\"model\":\"package com.xtaller.party.core.model;\\n\\nimport com.baomidou.mybatisplus.activerecord.Model;\\nimport com.baomidou.mybatisplus.annotations.TableName;\\nimport com.xtaller.party.utils.kit.IdKit;\\nimport java.io.Serializable;\\n\\n/**\\n * Created by Party on 2019/06/02\\n */\\n@SuppressWarnings(\\\"serial\\\")\\n@TableName(value = \\\"sys_user\\\")\\npublic class SysUser extends Model<SysUser> {\\n  private String id = IdKit.getId(this.getClass());\\n  private String loginName;\\n  private String password;\\n  private Integer salt;\\n  private Integer status;\\n  private Integer type;\\n  private String number;\\n  private String phone;\\n  private String verifyCode;\\n  private Integer errorCount;\\n  private Integer unlockTime;\\n  private String loginIp;\\n  private Integer loginTime;\\n  private String creator;\\n  private Integer createTime;\\n  private String reviser;\\n  private Integer reviseTime;\\n  private Integer isDel;\\n\\n  public String getId() { \\n      return id;\\n  } \\n  public void setId(String id) { \\n      this.id = id;\\n  } \\n  public String getLoginName() { \\n      return loginName;\\n  } \\n  public void setLoginName(String loginName) { \\n      this.loginName = loginName;\\n  } \\n  public String getPassword() { \\n      return password;\\n  } \\n  public void setPassword(String password) { \\n      this.password = password;\\n  } \\n  public Integer getSalt() { \\n      return salt;\\n  } \\n  public void setSalt(Integer salt) { \\n      this.salt = salt;\\n  } \\n  public Integer getStatus() { \\n      return status;\\n  } \\n  public void setStatus(Integer status) { \\n      this.status = status;\\n  } \\n  public Integer getType() { \\n      return type;\\n  } \\n  public void setType(Integer type) { \\n      this.type = type;\\n  } \\n  public String getNumber() { \\n      return number;\\n  } \\n  public void setNumber(String number) { \\n      this.number = number;\\n  } \\n  public String getPhone() { \\n      return phone;\\n  } \\n  public void setPhone(String phone) { \\n      this.phone = phone;\\n  } \\n  public String getVerifyCode() { \\n      return verifyCode;\\n  } \\n  public void setVerifyCode(String verifyCode) { \\n      this.verifyCode = verifyCode;\\n  } \\n  public Integer getErrorCount() { \\n      return errorCount;\\n  } \\n  public void setErrorCount(Integer errorCount) { \\n      this.errorCount = errorCount;\\n  } \\n  public Integer getUnlockTime() { \\n      return unlockTime;\\n  } \\n  public void setUnlockTime(Integer unlockTime) { \\n      this.unlockTime = unlockTime;\\n  } \\n  public String getLoginIp() { \\n      return loginIp;\\n  } \\n  public void setLoginIp(String loginIp) { \\n      this.loginIp = loginIp;\\n  } \\n  public Integer getLoginTime() { \\n      return loginTime;\\n  } \\n  public void setLoginTime(Integer loginTime) { \\n      this.loginTime = loginTime;\\n  } \\n  public String getCreator() { \\n      return creator;\\n  } \\n  public void setCreator(String creator) { \\n      this.creator = creator;\\n  } \\n  public Integer getCreateTime() { \\n      return createTime;\\n  } \\n  public void setCreateTime(Integer createTime) { \\n      this.createTime = createTime;\\n  } \\n  public String getReviser() { \\n      return reviser;\\n  } \\n  public void setReviser(String reviser) { \\n      this.reviser = reviser;\\n  } \\n  public Integer getReviseTime() { \\n      return reviseTime;\\n  } \\n  public void setReviseTime(Integer reviseTime) { \\n      this.reviseTime = reviseTime;\\n  } \\n  public Integer getIsDel() { \\n      return isDel;\\n  } \\n  public void setIsDel(Integer isDel) { \\n      this.isDel = isDel;\\n  } \\n  @Override\\n  protected Serializable pkVal() {\\n      return id;\\n  }\\n}\\n\",\"mapper\":\"package com.xtaller.party.core.mapper;\\n\\nimport com.baomidou.mybatisplus.mapper.BaseMapper;\\nimport com.alibaba.fastjson.JSONObject;\\nimport org.apache.ibatis.annotations.Param;\\nimport org.apache.ibatis.annotations.Select;\\nimport com.xtaller.party.core.model.SysUser;\\n\\nimport java.util.List;\\n/**\\n * Created by Party on 2019/06/02\\n */\\npublic interface SysUserMapper extends BaseMapper<SysUser> {\\n\\n    @Select(\\\"SELECT a.* \\\" + \\n            \\\",FROM_UNIXTIME(unlockTime) unlockTimeStr  \\\" +\\n            \\\",FROM_UNIXTIME(loginTime) loginTimeStr  \\\" +\\n              \\\"FROM sys_user a \\\" + \\n            \\\"JOIN (SELECT id from sys_user where isDel = 0 ${where} \\\" + \\n            \\\")b ON a.id=b.id order by a.createTime asc LIMIT #{index}, #{size} \\\") \\n    List<JSONObject> getPage(@Param(\\\"where\\\") String where, \\n                             @Param(\\\"index\\\") int index, \\n                             @Param(\\\"size\\\") int size); \\n\\n    @Select(\\\"select count(1) total from sys_user where isDel = 0 ${where} \\\") \\n    JSONObject getPageCount(@Param(\\\"where\\\") String where); \\n\\n    @Select(\\\"SELECT a.* FROM sys_user a where 1=1 and isDel=0 ${where}  order by createTime desc\\\")\\n    List<JSONObject> queryAll(@Param(\\\"where\\\") String where);\\n}\",\"iService\":\"package com.xtaller.party.core.service;\\n\\nimport java.util.List;\\nimport com.xtaller.party.utils.bean.Where;\\nimport com.xtaller.party.core.model.SysUser;\\n\\n/**\\n* Created by Party on 2019/06/02\\n*/\\npublic interface ISysUserService {\\n   /******************* CURD ********************/ \\n   // 创建 \\n   SysUser createSysUser(SysUser model); \\n   // 删除 \\n   Boolean deleteSysUser(Object ids,String reviser);\\n   // 修改 \\n   SysUser updateSysUser(SysUser model); \\n   // 查询 \\n   List<SysUser> findByIds(Object ids);\\n   // 属于 \\n   Boolean exist(List<Where> w); \\n   // 查询一个id是否存在 \\n   Boolean existId(Object id); \\n   /******************* CURD ********************/ \\n \\n}\",\"Api\":\"package com.xtaller.party.api.admin;\\n\\nimport com.alibaba.fastjson.JSONObject;\\nimport com.xtaller.party.api.BaseApi;\\nimport com.xtaller.party.api.BaseApi;\\nimport com.xtaller.party.core.model.SysUser;\\nimport com.xtaller.party.core.service.impl.SysUserService;\\nimport com.xtaller.party.doc.SysUserCreate;\\nimport com.xtaller.party.doc.SysUserUpdate;\\nimport com.xtaller.party.utils.bean.Page;\\nimport com.xtaller.party.utils.convert.R;\\nimport com.xtaller.party.utils.convert.S;\\nimport com.xtaller.party.utils.convert.V;\\nimport com.xtaller.party.utils.convert.W;\\nimport io.swagger.annotations.Api;\\nimport io.swagger.annotations.ApiOperation;\\nimport org.springframework.beans.factory.annotation.Autowired;\\nimport org.springframework.web.bind.annotation.*;\\n\\nimport java.util.HashMap;\\nimport java.util.List;\\nimport java.util.Map;\\n\\n/**\\n * Created by party on 2019/06/02\\n */\\n@Api(value = \\\"100_用户管理\\\")\\n@RestController\\n@RequestMapping(\\\"/v1/base\\\")\\npublic class SysUserApi extends BaseApi {\\n    @Autowired\\n    private SysUserService sysUserService;\\n\\n    @PostMapping(\\\"/sysUser\\\")\\n    @ApiOperation(value = \\\"用户新增\\\")\\n    public Object createSysUser(@RequestBody SysUserCreate object,\\n                              @RequestHeader(\\\"token\\\") String token){\\n        String userId = getUserIdByCache(token);   \\n\\n        //映射对象\\n        SysUser model = o2c(object,token, SysUser.class);\\n        //数据校验\\n        JSONObject check = V.checkEmpty(verify(),object);\\n        if(check.getBoolean(\\\"check\\\"))\\n        return R.error(check.getString(\\\"message\\\"));\\n\\n        //TODO 写校验重复的条件\\n        //Boolean exist = sysUserService.exist(W.f(\\n        //        W.and(\\\"code\\\",\\\"eq\\\",model.getCode()),\\n        //        W.and(\\\"isDel\\\",\\\"eq\\\",\\\"0\\\"))\\n        //);\\n        //if(exist)\\n        //    return R.error(\\\"代码已经存在请更换一个代码\\\");\\n\\n        model = sysUserService.createSysUser(model);\\n        if(model == null)\\n            return R.error(\\\"新增失败\\\");\\n        return R.ok(\\\"新增成功\\\",fm2(model));\\n    }\\n\\n   @PutMapping(\\\"/sysUser\\\")\\n   @ApiOperation(value = \\\"修改用户\\\")\\n   public Object updateSysUser(@RequestBody SysUserUpdate object,\\n                             @RequestHeader(\\\"token\\\") String token){\\n        String userId = getUserIdByCache(token);\\n        //映射对象\\n        SysUser model = o2c(object,token, SysUser.class);\\n        //数据校验\\n        JSONObject check = V.checkEmpty(updateVerify(),object);\\n        if(check.getBoolean(\\\"check\\\"))\\n            return R.error(check.getString(\\\"message\\\"));\\n              SysUser data= sysUserService.selectById(model.getId());\\n      \\n             if(data==null){  \\n                  return R.error(\\\"该信息不存在，无法修改\\\"); \\n             } \\n        //TODO 写校验重复的条件\\n        //   if(!model.getCode().equals(data.getCode())) {   \\n        //Boolean exist = sysUserService.exist(W.f(\\n        //        W.and(\\\"code\\\",\\\"eq\\\",model.getCode()),\\n        //        W.and(\\\"isDel\\\",\\\"eq\\\",\\\"0\\\"))\\n        //);\\n        //if(exist)\\n        //    return R.error(\\\"代码已经存在请更换一个代码\\\");\\n\\n        //     }    \\n        model.setReviser(userId);\\n        model = sysUserService.updateSysUser(model);\\n        if(model == null)\\n            return R.error(\\\"修改失败\\\");\\n        return R.ok(\\\"修改成功\\\",fm2(model));\\n    }\\n\\n    @DeleteMapping(\\\"/sysUser/{id}\\\")\\n    @ApiOperation(value = \\\"用户删除\\\")\\n    public Object deleteSysUser(@PathVariable(\\\"id\\\") String id,\\n                                                  @RequestHeader(\\\"token\\\") String token){\\n        if(!sysUserService.existId(id))\\n            return R.error(\\\"Id数据异常\\\");\\n\\n        if(sysUserService.deleteSysUser(id, cacheKit.getUserId(token)))\\n            return R.ok(\\\"删除成功\\\");\\n        return R.error(\\\"删除失败\\\");\\n    }\\n\\n    @GetMapping(\\\"/sysUser/{index}-{size}-{key}\\\")\\n    @ApiOperation(value = \\\"读取用户分页列表\\\")\\n    public Object getSysUser(@PathVariable(\\\"index\\\") int index,\\n                              @PathVariable(\\\"size\\\") int size,\\n                              @PathVariable(\\\"key\\\") String key,\\n                              @RequestHeader(\\\"token\\\") String token) {\\n        String wKey = \\\"\\\";\\n        if (!V.isEmpty(key))\\n        //FIXME 修改id为需要查询的字段\\n            wKey = S.apppend(\\\" and id like \'%\\\", key, \\\"%\' \\\");\\n        return R.ok(sysUserService.page(index, size, wKey));\\n    }\\n\\n    @GetMapping(\\\"/sysUser\\\")\\n    @ApiOperation(value = \\\"读取用户所有列表\\\")\\n    public Object getAllSysUser(@RequestHeader(\\\"token\\\") String token) {\\n        return R.ok(sysUserService.queryAll(\\\"\\\"));\\n    }\\n\\n    private Map<String, String> verify() {\\n        Map<String, String> verify = new HashMap<>();\\n        verify.put(\\\"loginName\\\", \\\"请输入登录名\\\");\\n        verify.put(\\\"password\\\", \\\"请输入用户密码\\\");\\n        verify.put(\\\"salt\\\", \\\"请输入密码混淆盐\\\");\\n        verify.put(\\\"status\\\", \\\"请输入用户状态\\\");\\n        verify.put(\\\"type\\\", \\\"请输入用户类型\\\");\\n        verify.put(\\\"number\\\", \\\"请输入学号/工号\\\");\\n        verify.put(\\\"phone\\\", \\\"请输入手机号\\\");\\n        verify.put(\\\"verifyCode\\\", \\\"请输入手机验证码\\\");\\n        verify.put(\\\"errorCount\\\", \\\"请输入错误次数\\\");\\n        verify.put(\\\"unlockTime\\\", \\\"请输入解锁时间\\\");\\n        verify.put(\\\"loginIp\\\", \\\"请输入最后登录IP\\\");\\n        verify.put(\\\"loginTime\\\", \\\"请输入最后登录时间\\\");\\n        return verify;\\n    }\\n\\n    private Map<String, String> updateVerify() {\\n        Map<String, String> verify = new HashMap<>();\\n        verify.put(\\\"loginName\\\", \\\"请输入登录名\\\");\\n        verify.put(\\\"password\\\", \\\"请输入用户密码\\\");\\n        verify.put(\\\"salt\\\", \\\"请输入密码混淆盐\\\");\\n        verify.put(\\\"status\\\", \\\"请输入用户状态\\\");\\n        verify.put(\\\"type\\\", \\\"请输入用户类型\\\");\\n        verify.put(\\\"number\\\", \\\"请输入学号/工号\\\");\\n        verify.put(\\\"phone\\\", \\\"请输入手机号\\\");\\n        verify.put(\\\"verifyCode\\\", \\\"请输入手机验证码\\\");\\n        verify.put(\\\"errorCount\\\", \\\"请输入错误次数\\\");\\n        verify.put(\\\"unlockTime\\\", \\\"请输入解锁时间\\\");\\n        verify.put(\\\"loginIp\\\", \\\"请输入最后登录IP\\\");\\n        verify.put(\\\"loginTime\\\", \\\"请输入最后登录时间\\\");\\n        return verify;\\n    }\\n\\n}\\n\"}', '1', '0', '1559444212', '0', '1559444486', '0');
INSERT INTO `sys_auto_code` VALUES ('1994903215334982819', 'teach_schedule', '{\"js\":\"var index = 1, size = 6, key = \'\', totalPage = 0, totalCount = 0;\\nvar baseData = [], pageData = [];\\nvar optId = \'\';\\n\\nvar config = {\\n    form: \'/form/_teachSchedule.html\',\\n    title: \'教师授课管理\',\\n};\\n\\n$(function () {\\n    //自适应\\n    view.initHeight();\\n    $(window).resize(function () {\\n        view.initHeight();\\n    });\\n    teachSchedule.get();\\n});\\n\\nvar teachSchedule = {\\n    get: function () {\\n        var param = {url: baseModule.teachScheduleApi + \'/\' + index + \'-\' + size + \'-\' + key};\\n        var request = ajax.get(param);\\n        request.done(function (d) {\\n            pageData = d.result.data;\\n            render.page();\\n            totalPage = d.result.totalPage;\\n            totalCount = d.result.totalCount;\\n            if (d.result.totalPage>1) {\\n                  page.init(d.result.totalPage, d.result.totalCount);\\n            }else{\\n                 $(\'.list-page\').empty();\\n            }\\n        })\\n    },\\n\\n    create: function (event) {\\n        if (auth.refuse(event))\\n            return false;\\n        openLay({url: config.form, fun: \'opt.create();\', title: config.title});\\n    },\\n\\n    delete: function (event) {\\n        if (auth.refuse(event))\\n            return false;\\n        optId = getId(event);\\n        tips.confirm({message: \'是否要删除教师授课数据？\', fun: \\\"opt.delete();\\\"});\\n\\n    },\\n\\n    update: function (event) {\\n        if (auth.refuse(event))\\n            return false;\\n        optId = getId(event);//获取当前id的值\\n        openLay({url: config.form, fun: \\\"opt.update();\\\", title: config.title});\\n        var model = result.get(pageData, optId);\\n\\n        form.set(model);\\n    },\\n};\\n\\nvar render = {\\n    page: function () {\\n        var template = doT.template($(\\\"#teachSchedule-template\\\").text());//获取的模板\\n        $(\'#item-list\').html(template(pageData));//模板装入数据\\n    },\\n};\\n\\nvar opt = {\\n    create: function () {\\n        var data = form.get(\\\"#opt-form\\\");\\n        if (form.verify(data))\\n            return false;\\n\\n        var param = {url: baseModule.teachScheduleApi, data: data};\\n        var request = ajax.post(param);\\n        request.done(function (d) {\\n            tips.done(d);\\n            pageData.push(d.result);\\n            totalCount = totalCount + 1;\\n            page.init(totalPage, totalCount);\\n            render.page();\\n        })\\n    },\\n    delete: function () {\\n        var request = ajax.delete({url: baseModule.teachScheduleApi + \'/\' + optId});\\n        request.done(function (d) {\\n            tips.ok(d.message);\\n            pageData = result.delete(pageData, optId);\\n            render.page();\\n            totalCount = totalCount - 1;\\n            page.init(totalPage, totalCount);\\n        })\\n    },\\n    update: function () {\\n        var data = form.get(\\\"#opt-form\\\");\\n        if (form.verify(data))\\n            return false;\\n        data[\'id\'] = optId;\\n\\n        var param = {url: baseModule.teachScheduleApi, data: data};\\n        var request = ajax.put(param); //加一条记录\\n        request.done(function (d) {\\n            tips.ok(d.message);\\n            //更新对象\\n            pageData = result.update(pageData, d.result, \'id\');\\n            teachSchedule.get();\\n            closeLay();\\n        })\\n    },\\n    close: function () {   //关闭按钮\\n        closeLay();\\n    }\\n};\\n\\nvar page = {\\n    init: function (_pageSize, _total) {\\n        $(\'.list-page\').pagination({\\n            pageCount: _pageSize,\\n            current: index,\\n            jump: true,\\n            coping: true,\\n            homePage: \'首页\',\\n            endPage: \'末页\',\\n            prevContent: \'上页\',\\n            nextContent: \'下页\',\\n            pageSize: size,\\n            pageArray: [6, 12, 24, 48],\\n            totalCount: _total,\\n            id: \'teachSchedule-page\',\\n            callback: function (api) {\\n                index = api.getCurrent();\\n                teachSchedule.get();\\n            }\\n        });\\n        if (_pageSize > 0)\\n            $(\'.pages\').show();\\n    }\\n};\\n\\nvar view = {\\n    initHeight: function () {\\n        $(\'.data-view\').css(\'height\', (parent.adaptable().h) - 80);\\n        $(\'.date-table\').css(\'height\', (parent.adaptable().h) - 180);\\n        size = Math.floor(((parent.adaptable().h) - 180) / 40);\\n    }\\n};\\n\\n\\nfunction pageChange(event) {\\n    size = $(event).val();\\n    index = 1;\\n    teachSchedule.get();\\n};\\n\\nvar helper = {\\n};\\n\\nvar tool = {\\n    translate:function (model) {\\n        var data = [];\\n        for (var variable in model) {\\n            data[variable] = model[variable];\\n            //判断helper里是否存在该函数，存在则执行转换\\n            if (typeof eval(\'helper.\' + variable) == \'function\')\\n                model[variable] = eval(\'helper.\' + variable + \'(\' + model[variable] + \')\');\\n        }\\n        form.set(model);\\n        //恢复回转换前数据\\n        for (var variable in data) {\\n            model[variable] = data[variable];\\n        }\\n    }\\n};\",\"html\":\"<div class=\\\"table-responsive\\\">\\n    <form id=\\\"opt-form\\\">\\n        <table class=\\\"table table-bordered\\\">\\n            <tr>\\n                <td class=\\\"form-item-title\\\">\\n                    课程号\\n                </td>\\n                <td>\\n                    <input required type=\\\"text\\\" name=\\\"courseNumber\\\" id=\\\"courseNumber\\\"  tips-id = \\\"courseNumber-tips\\\"  data-length=\\\"20\\\" maxlength=\\\"20\\\"  onblur=\\\"checkRegular(this);\\\"  class=\\\"form-control\\\"\\n                           placeholder=\\\"请输入课程号\\\">\\n                </td>\\n                <td class=\\\"form-item-remark\\\" > <span id = \\\"courseNumber-tips\\\"></span></td>\\n            </tr>\\n            <tr>\\n                <td class=\\\"form-item-title\\\">\\n                    授课班级id\\n                </td>\\n                <td>\\n                    <input required type=\\\"text\\\" name=\\\"classId\\\" id=\\\"classId\\\"  tips-id = \\\"classId-tips\\\"  data-length=\\\"20\\\" maxlength=\\\"20\\\"  onblur=\\\"checkRegular(this);\\\"  class=\\\"form-control\\\"\\n                           placeholder=\\\"请输入授课班级id\\\">\\n                </td>\\n                <td class=\\\"form-item-remark\\\" > <span id = \\\"classId-tips\\\"></span></td>\\n            </tr>\\n            <tr>\\n                <td class=\\\"form-item-title\\\">\\n                    教师工号\\n                </td>\\n                <td>\\n                    <input required type=\\\"text\\\" name=\\\"teacherNumber\\\" id=\\\"teacherNumber\\\"  tips-id = \\\"teacherNumber-tips\\\"  data-length=\\\"20\\\" maxlength=\\\"20\\\"  onblur=\\\"checkRegular(this);\\\"  class=\\\"form-control\\\"\\n                           placeholder=\\\"请输入教师工号\\\">\\n                </td>\\n                <td class=\\\"form-item-remark\\\" > <span id = \\\"teacherNumber-tips\\\"></span></td>\\n            </tr>\\n        </table>\\n    </form>\\n</div>\\n<script>\\n</script>\",\"ejs\":\"<% include ../inc/header.ejs%>\\n<style>\\n    .date-table {\\n        overflow-y: auto;\\n    }\\n</style>\\n\\n<% include ../inc/body.ejs%>\\n<!-- html主体代码 start -->\\n<div class=\\\"row\\\">\\n    <div class=\\\"col-sm-12\\\">\\n        <div class=\\\"ibox float-e-margins\\\">\\n            <div class=\\\"ibox-title\\\" style=\\\"padding-top: 3px !important;\\\">\\n                <div style=\\\"width: 140px; display: block;float: left;padding-top: 10px;\\\">\\n                    教师授课列表\\n                </div>\\n\\n                <div class=\\\"ibox-tools\\\">\\n                    <button style=\\\"margin-top: 8px;\\\" class=\\\"btn btn-primary btn-xs\\\" type=\\\"button\\\"\\n                            onclick=\\\"teachSchedule.create(this);\\\">\\n                        <i class=\\\"fa fa-plus\\\"></i>&nbsp;新增\\n                    </button>\\n                </div>\\n\\n            </div>\\n            <div class=\\\"ibox-content data-view\\\" style=\\\"overflow-y: auto;\\\">\\n                <div class=\\\"col-sm-12\\\">\\n                    <div class=\\\" col-sm-12 date-table\\\">\\n                        <table class=\\\"table table-striped\\\">\\n                            <thead>\\n                            <tr>\\n                                <th>序号</th>\\n                                <th>课程号</th>\\n                                <th>授课班级id</th>\\n                                <th>教师工号</th>\\n                                <th></th>\\n                            </tr>\\n                            </thead>\\n                            <tbody id=\\\"item-list\\\">\\n\\n                            </tbody>\\n                        </table>\\n                    </div>\\n                    <div class=\\\"col-sm-12 pages M-box3 list-page\\\">\\n                    </div>\\n                </div>\\n\\n            </div>\\n        </div>\\n    </div>\\n</div>\\n<!-- html主体代码 end -->\\n<!-- 管理的教师授课模板 -->\\n<script id=\\\"teachSchedule-template\\\" type=\\\"text/x-dot-template\\\">\\n    {{ if(it.length == 0) { }}\\n    暂无数据\\n    {{ }else{ }}\\n    {{ for(var i=0;i < it.length;i++){ }}\\n    <tr>\\n        <td>\\n            {{=(i+1)}}\\n        </td>\\n        <td>\\n            {{= it[i].courseNumber}}\\n        </td>\\n        <td>\\n            {{= it[i].classId}}\\n        </td>\\n        <td>\\n            {{= it[i].teacherNumber}}\\n        </td>\\n        <td>\\n            <div class=\\\"btn-group\\\">\\n                <button data-toggle=\\\"dropdown\\\" class=\\\"btn btn-primary btn-xs dropdown-toggle\\\">操作 <span\\n                            class=\\\"caret\\\"></span>\\n                </button>\\n                <ul class=\\\"dropdown-menu\\\">\\n                    <li>\\n                        <a href=\\\"#\\\" data-id=\\\"{{= it[i].id}}\\\" onclick=\\\"teachSchedule.update(this);\\\">修改</a>\\n                    </li>\\n                    <li>\\n                        <a href=\\\"#\\\" data-id=\\\"{{= it[i].id}}\\\" onclick=\\\"teachSchedule.delete(this);\\\">删除</a>\\n                    </li>\\n                </ul>\\n            </div>\\n        </td>\\n    </tr>\\n    {{ } }}\\n    {{ } }}\\n</script>\\n<% include ../inc/js.ejs %>\\n\\n<!-- 私有脚本 start -->\\n<script src=\\\"/js/baseModule/_teachSchedule.js\\\"></script>\\n<!-- 私有脚本 end -->\\n<% include ../inc/footer.ejs %>\"}', '2', '0', '1561600473', '0', '1561602331', '0');
INSERT INTO `sys_auto_code` VALUES ('1995183054863127282', 'exam_room', '{\"js\":\"var index = 1, size = 6, key = \'\', totalPage = 0, totalCount = 0;\\nvar baseData = [], pageData = [];\\nvar optId = \'\';\\n\\nvar config = {\\n    form: \'/form/_examRoom.html\',\\n    title: \'考场管理\',\\n};\\n\\n$(function () {\\n    //自适应\\n    view.initHeight();\\n    $(window).resize(function () {\\n        view.initHeight();\\n    });\\n    examRoom.get();\\n});\\n\\nvar examRoom = {\\n    get: function () {\\n        var param = {url: baseModule.examRoomApi + \'/\' + index + \'-\' + size + \'-\' + key};\\n        var request = ajax.get(param);\\n        request.done(function (d) {\\n            pageData = d.result.data;\\n            render.page();\\n            totalPage = d.result.totalPage;\\n            totalCount = d.result.totalCount;\\n            if (d.result.totalPage>1) {\\n                  page.init(d.result.totalPage, d.result.totalCount);\\n            }else{\\n                 $(\'.list-page\').empty();\\n            }\\n        })\\n    },\\n\\n    create: function (event) {\\n        if (auth.refuse(event))\\n            return false;\\n        openLay({url: config.form, fun: \'opt.create();\', title: config.title});\\n    },\\n\\n    delete: function (event) {\\n        if (auth.refuse(event))\\n            return false;\\n        optId = getId(event);\\n        tips.confirm({message: \'是否要删除考场数据？\', fun: \\\"opt.delete();\\\"});\\n\\n    },\\n\\n    update: function (event) {\\n        if (auth.refuse(event))\\n            return false;\\n        optId = getId(event);//获取当前id的值\\n        openLay({url: config.form, fun: \\\"opt.update();\\\", title: config.title});\\n        var model = result.get(pageData, optId);\\n\\n        form.set(model);\\n    },\\n};\\n\\nvar render = {\\n    page: function () {\\n        var template = doT.template($(\\\"#examRoom-template\\\").text());//获取的模板\\n        $(\'#item-list\').html(template(pageData));//模板装入数据\\n    },\\n};\\n\\nvar opt = {\\n    create: function () {\\n        var data = form.get(\\\"#opt-form\\\");\\n        if (form.verify(data))\\n            return false;\\n\\n        var param = {url: baseModule.examRoomApi, data: data};\\n        var request = ajax.post(param);\\n        request.done(function (d) {\\n            tips.done(d);\\n            pageData.push(d.result);\\n            totalCount = totalCount + 1;\\n            page.init(totalPage, totalCount);\\n            render.page();\\n        })\\n    },\\n    delete: function () {\\n        var request = ajax.delete({url: baseModule.examRoomApi + \'/\' + optId});\\n        request.done(function (d) {\\n            tips.ok(d.message);\\n            pageData = result.delete(pageData, optId);\\n            render.page();\\n            totalCount = totalCount - 1;\\n            page.init(totalPage, totalCount);\\n        })\\n    },\\n    update: function () {\\n        var data = form.get(\\\"#opt-form\\\");\\n        if (form.verify(data))\\n            return false;\\n        data[\'id\'] = optId;\\n\\n        var param = {url: baseModule.examRoomApi, data: data};\\n        var request = ajax.put(param); //加一条记录\\n        request.done(function (d) {\\n            tips.ok(d.message);\\n            //更新对象\\n            pageData = result.update(pageData, d.result, \'id\');\\n            examRoom.get();\\n            closeLay();\\n        })\\n    },\\n    close: function () {   //关闭按钮\\n        closeLay();\\n    }\\n};\\n\\nvar page = {\\n    init: function (_pageSize, _total) {\\n        $(\'.list-page\').pagination({\\n            pageCount: _pageSize,\\n            current: index,\\n            jump: true,\\n            coping: true,\\n            homePage: \'首页\',\\n            endPage: \'末页\',\\n            prevContent: \'上页\',\\n            nextContent: \'下页\',\\n            pageSize: size,\\n            pageArray: [6, 12, 24, 48],\\n            totalCount: _total,\\n            id: \'examRoom-page\',\\n            callback: function (api) {\\n                index = api.getCurrent();\\n                examRoom.get();\\n            }\\n        });\\n        if (_pageSize > 0)\\n            $(\'.pages\').show();\\n    }\\n};\\n\\nvar view = {\\n    initHeight: function () {\\n        $(\'.data-view\').css(\'height\', (parent.adaptable().h) - 80);\\n        $(\'.date-table\').css(\'height\', (parent.adaptable().h) - 180);\\n    }\\n};\\n\\n\\nfunction pageChange(event) {\\n    size = $(event).val();\\n    index = 1;\\n    examRoom.get();\\n};\\n\\nvar helper = {\\n    isUnseal: function (_isUnseal) {\\n        switch (parseInt(_isUnseal)) {\\n             case 0:\\n                 return \\\"否\\\";\\n             case 1:\\n                 return \\\"是\\\";\\n         }\\n    },\\n};\\n\\nvar tool = {\\n    translate:function (model) {\\n        var data = [];\\n        for (var variable in model) {\\n            data[variable] = model[variable];\\n            //判断helper里是否存在该函数，存在则执行转换\\n            if (typeof eval(\'helper.\' + variable) == \'function\')\\n                model[variable] = eval(\'helper.\' + variable + \'(\' + model[variable] + \')\');\\n        }\\n        form.set(model);\\n        //恢复回转换前数据\\n        for (var variable in data) {\\n            model[variable] = data[variable];\\n        }\\n    }\\n};\",\"html\":\"<div class=\\\"table-responsive\\\">\\n    <form id=\\\"opt-form\\\">\\n        <table class=\\\"table table-bordered\\\">\\n            <tr>\\n                <td class=\\\"form-item-title\\\">\\n                    试卷Id\\n                </td>\\n                <td>\\n                    <input required type=\\\"text\\\" name=\\\"examId\\\" id=\\\"examId\\\"  tips-id = \\\"examId-tips\\\"  data-length=\\\"19\\\" maxlength=\\\"19\\\"  onblur=\\\"checkRegular(this);\\\"  class=\\\"form-control\\\"\\n                           placeholder=\\\"请输入试卷Id\\\">\\n                </td>\\n                <td class=\\\"form-item-remark\\\" > <span id = \\\"examId-tips\\\"></span></td>\\n            </tr>\\n            <tr>\\n                <td class=\\\"form-item-title\\\">\\n                    是否设定启封码\\n                </td>\\n                <td>\\n                    <select required name=\\\"isUnseal\\\" id=\\\"isUnseal\\\" tips-id = \\\"isUnseal-tips\\\" data-length=\\\"3\\\"  onblur=\\\"checkRegular(this);\\\" placeholder=\\\"请输入是否设定启封码\\\" class=\\\"form-control\\\"> \\n                    <option value=\\\"0\\\">否</option>\\n                    <option value=\\\"1\\\">是</option>\\n                    </select>\\n                </td>\\n                <td class=\\\"form-item-remark\\\" > <span id = \\\"isUnseal-tips\\\"></span></td>\\n            </tr>\\n            <tr>\\n                <td class=\\\"form-item-title\\\">\\n                    名称\\n                </td>\\n                <td>\\n                    <input required type=\\\"text\\\" name=\\\"name\\\" id=\\\"name\\\"  tips-id = \\\"name-tips\\\"  data-length=\\\"255\\\" maxlength=\\\"20\\\"  onblur=\\\"checkRegular(this);\\\"  class=\\\"form-control\\\"\\n                           placeholder=\\\"请输入名称\\\">\\n                </td>\\n                <td class=\\\"form-item-remark\\\" > <span id = \\\"name-tips\\\"></span></td>\\n            </tr>\\n            <tr>\\n                <td class=\\\"form-item-title\\\">\\n                    监考员\\n                </td>\\n                <td>\\n                    <input required type=\\\"text\\\" name=\\\"monitor\\\" id=\\\"monitor\\\"  tips-id = \\\"monitor-tips\\\"  data-length=\\\"255\\\" maxlength=\\\"20\\\"  onblur=\\\"checkRegular(this);\\\"  class=\\\"form-control\\\"\\n                           placeholder=\\\"请输入监考员\\\">\\n                </td>\\n                <td class=\\\"form-item-remark\\\" > <span id = \\\"monitor-tips\\\"></span></td>\\n            </tr>\\n            <tr>\\n                <td class=\\\"form-item-title\\\">\\n                    启封码\\n                </td>\\n                <td>\\n                    <input required type=\\\"text\\\" name=\\\"unsealCode\\\" id=\\\"unsealCode\\\"  tips-id = \\\"unsealCode-tips\\\"  data-length=\\\"255\\\" maxlength=\\\"20\\\"  onblur=\\\"checkRegular(this);\\\"  class=\\\"form-control\\\"\\n                           placeholder=\\\"请输入启封码\\\">\\n                </td>\\n                <td class=\\\"form-item-remark\\\" > <span id = \\\"unsealCode-tips\\\"></span></td>\\n            </tr>\\n        </table>\\n    </form>\\n</div>\\n<script>\\n</script>\",\"ejs\":\"<% include ../inc/header.ejs%>\\n<style>\\n    .date-table {\\n        overflow-y: auto;\\n    }\\n</style>\\n\\n<% include ../inc/body.ejs%>\\n<!-- html主体代码 start -->\\n<div class=\\\"row\\\">\\n    <div class=\\\"col-sm-12\\\">\\n        <div class=\\\"ibox float-e-margins\\\">\\n            <div class=\\\"ibox-title\\\" style=\\\"padding-top: 3px !important;\\\">\\n                <div style=\\\"width: 140px; display: block;float: left;padding-top: 10px;\\\">\\n                    考场列表\\n                </div>\\n\\n                <div class=\\\"ibox-tools\\\">\\n                    <button style=\\\"margin-top: 8px;\\\" class=\\\"btn btn-primary btn-xs\\\" type=\\\"button\\\"\\n                            onclick=\\\"examRoom.create(this);\\\">\\n                        <i class=\\\"fa fa-plus\\\"></i>&nbsp;新增\\n                    </button>\\n                </div>\\n\\n            </div>\\n            <div class=\\\"ibox-content data-view\\\" style=\\\"overflow-y: auto;\\\">\\n                <div class=\\\"col-sm-12\\\">\\n                    <div class=\\\" col-sm-12 date-table\\\">\\n                        <table class=\\\"table table-striped\\\">\\n                            <thead>\\n                            <tr>\\n                                <th>序号</th>\\n                                <th>试卷Id</th>\\n                                <th>是否设定启封码</th>\\n                                <th>名称</th>\\n                                <th>监考员</th>\\n                                <th>启封码</th>\\n                                <th></th>\\n                            </tr>\\n                            </thead>\\n                            <tbody id=\\\"item-list\\\">\\n\\n                            </tbody>\\n                        </table>\\n                    </div>\\n                    <div class=\\\"col-sm-12 pages M-box3 list-page\\\">\\n                    </div>\\n                </div>\\n\\n            </div>\\n        </div>\\n    </div>\\n</div>\\n<!-- html主体代码 end -->\\n<!-- 管理的考场模板 -->\\n<script id=\\\"examRoom-template\\\" type=\\\"text/x-dot-template\\\">\\n    {{ if(it.length == 0) { }}\\n    暂无数据\\n    {{ }else{ }}\\n    {{ for(var i=0;i < it.length;i++){ }}\\n    <tr>\\n        <td>\\n            {{=(i+1)}}\\n        </td>\\n        <td>\\n            {{= it[i].examId}}\\n        </td>\\n        <td>\\n            {{= helper.isUnseal(it[i].isUnseal) }}\\n        </td>\\n        <td>\\n            {{= it[i].name}}\\n        </td>\\n        <td>\\n            {{= it[i].monitor}}\\n        </td>\\n        <td>\\n            {{= it[i].unsealCode}}\\n        </td>\\n        <td>\\n            <div class=\\\"btn-group\\\">\\n                <button data-toggle=\\\"dropdown\\\" class=\\\"btn btn-primary btn-xs dropdown-toggle\\\">操作 <span\\n                            class=\\\"caret\\\"></span>\\n                </button>\\n                <ul class=\\\"dropdown-menu\\\">\\n                    <li>\\n                        <a href=\\\"#\\\" data-id=\\\"{{= it[i].id}}\\\" onclick=\\\"examRoom.update(this);\\\">修改</a>\\n                    </li>\\n                    <li>\\n                        <a href=\\\"#\\\" data-id=\\\"{{= it[i].id}}\\\" onclick=\\\"examRoom.delete(this);\\\">删除</a>\\n                    </li>\\n                </ul>\\n            </div>\\n        </td>\\n    </tr>\\n    {{ } }}\\n    {{ } }}\\n</script>\\n<% include ../inc/js.ejs %>\\n\\n<!-- 私有脚本 start -->\\n<script src=\\\"/js/baseModule/_examRoom.js\\\"></script>\\n<!-- 私有脚本 end -->\\n<% include ../inc/footer.ejs %>\"}', '2', '0', '1559044586', '0', '1559044626', '0');
INSERT INTO `sys_auto_code` VALUES ('1995568393832400970', 'course', '{\"js\":\"var index = 1, size = 6, key = \'\', totalPage = 0, totalCount = 0;\\nvar baseData = [], pageData = [];\\nvar optId = \'\';\\n\\nvar config = {\\n    form: \'/form/_course.html\',\\n    title: \'课程信息管理\',\\n};\\n\\n$(function () {\\n    //自适应\\n    view.initHeight();\\n    $(window).resize(function () {\\n        view.initHeight();\\n    });\\n    course.get();\\n});\\n\\nvar course = {\\n    get: function () {\\n        var param = {url: baseModule.courseApi + \'/\' + index + \'-\' + size + \'-\' + key};\\n        var request = ajax.get(param);\\n        request.done(function (d) {\\n            pageData = d.result.data;\\n            render.page();\\n            totalPage = d.result.totalPage;\\n            totalCount = d.result.totalCount;\\n            if (d.result.totalPage>1) {\\n                  page.init(d.result.totalPage, d.result.totalCount);\\n            }else{\\n                 $(\'.list-page\').empty();\\n            }\\n        })\\n    },\\n\\n    create: function (event) {\\n        if (auth.refuse(event))\\n            return false;\\n        openLay({url: config.form, fun: \'opt.create();\', title: config.title});\\n    },\\n\\n    delete: function (event) {\\n        if (auth.refuse(event))\\n            return false;\\n        optId = getId(event);\\n        tips.confirm({message: \'是否要删除课程信息数据？\', fun: \\\"opt.delete();\\\"});\\n\\n    },\\n\\n    update: function (event) {\\n        if (auth.refuse(event))\\n            return false;\\n        optId = getId(event);//获取当前id的值\\n        openLay({url: config.form, fun: \\\"opt.update();\\\", title: config.title});\\n        var model = result.get(pageData, optId);\\n\\n        form.set(model);\\n    },\\n};\\n\\nvar render = {\\n    page: function () {\\n        var template = doT.template($(\\\"#course-template\\\").text());//获取的模板\\n        $(\'#item-list\').html(template(pageData));//模板装入数据\\n    },\\n};\\n\\nvar opt = {\\n    create: function () {\\n        var data = form.get(\\\"#opt-form\\\");\\n        if (form.verify(data))\\n            return false;\\n\\n        var param = {url: baseModule.courseApi, data: data};\\n        var request = ajax.post(param);\\n        request.done(function (d) {\\n            tips.done(d);\\n            pageData.push(d.result);\\n            totalCount = totalCount + 1;\\n            page.init(totalPage, totalCount);\\n            render.page();\\n        })\\n    },\\n    delete: function () {\\n        var request = ajax.delete({url: baseModule.courseApi + \'/\' + optId});\\n        request.done(function (d) {\\n            tips.ok(d.message);\\n            pageData = result.delete(pageData, optId);\\n            render.page();\\n            totalCount = totalCount - 1;\\n            page.init(totalPage, totalCount);\\n        })\\n    },\\n    update: function () {\\n        var data = form.get(\\\"#opt-form\\\");\\n        if (form.verify(data))\\n            return false;\\n        data[\'id\'] = optId;\\n\\n        var param = {url: baseModule.courseApi, data: data};\\n        var request = ajax.put(param); //加一条记录\\n        request.done(function (d) {\\n            tips.ok(d.message);\\n            //更新对象\\n            pageData = result.update(pageData, d.result, \'id\');\\n            course.get();\\n            closeLay();\\n        })\\n    },\\n    close: function () {   //关闭按钮\\n        closeLay();\\n    }\\n};\\n\\nvar page = {\\n    init: function (_pageSize, _total) {\\n        $(\'.list-page\').pagination({\\n            pageCount: _pageSize,\\n            current: index,\\n            jump: true,\\n            coping: true,\\n            homePage: \'首页\',\\n            endPage: \'末页\',\\n            prevContent: \'上页\',\\n            nextContent: \'下页\',\\n            pageSize: size,\\n            pageArray: [6, 12, 24, 48],\\n            totalCount: _total,\\n            id: \'course-page\',\\n            callback: function (api) {\\n                index = api.getCurrent();\\n                course.get();\\n            }\\n        });\\n        if (_pageSize > 0)\\n            $(\'.pages\').show();\\n    }\\n};\\n\\nvar view = {\\n    initHeight: function () {\\n        $(\'.data-view\').css(\'height\', (parent.adaptable().h) - 80);\\n        $(\'.date-table\').css(\'height\', (parent.adaptable().h) - 180);\\n        size = Math.floor(((parent.adaptable().h) - 180) / 40);\\n    }\\n};\\n\\n\\nfunction pageChange(event) {\\n    size = $(event).val();\\n    index = 1;\\n    course.get();\\n};\\n\\nvar helper = {\\n};\\n\\nvar tool = {\\n    translate:function (model) {\\n        var data = [];\\n        for (var variable in model) {\\n            data[variable] = model[variable];\\n            //判断helper里是否存在该函数，存在则执行转换\\n            if (typeof eval(\'helper.\' + variable) == \'function\')\\n                model[variable] = eval(\'helper.\' + variable + \'(\' + model[variable] + \')\');\\n        }\\n        form.set(model);\\n        //恢复回转换前数据\\n        for (var variable in data) {\\n            model[variable] = data[variable];\\n        }\\n    }\\n};\",\"html\":\"<div class=\\\"table-responsive\\\">\\n    <form id=\\\"opt-form\\\">\\n        <table class=\\\"table table-bordered\\\">\\n            <tr>\\n                <td class=\\\"form-item-title\\\">\\n                    学分\\n                </td>\\n                <td>\\n                    <input required type=\\\"text\\\" name=\\\"credit\\\" id=\\\"credit\\\"  tips-id = \\\"credit-tips\\\"  data-length=\\\"7\\\" maxlength=\\\"7\\\"  onblur=\\\"checkRegular(this);\\\"  class=\\\"form-control\\\"\\n                           placeholder=\\\"请输入学分\\\">\\n                </td>\\n                <td class=\\\"form-item-remark\\\" > <span id = \\\"credit-tips\\\"></span></td>\\n            </tr>\\n            <tr>\\n                <td class=\\\"form-item-title\\\">\\n                    课程号\\n                </td>\\n                <td>\\n                    <input required type=\\\"text\\\" name=\\\"number\\\" id=\\\"number\\\"  tips-id = \\\"number-tips\\\"  data-length=\\\"20\\\" maxlength=\\\"20\\\"  onblur=\\\"checkRegular(this);\\\"  class=\\\"form-control\\\"\\n                           placeholder=\\\"请输入课程号\\\">\\n                </td>\\n                <td class=\\\"form-item-remark\\\" > <span id = \\\"number-tips\\\"></span></td>\\n            </tr>\\n            <tr>\\n                <td class=\\\"form-item-title\\\">\\n                    课程名\\n                </td>\\n                <td>\\n                    <input required type=\\\"text\\\" name=\\\"name\\\" id=\\\"name\\\"  tips-id = \\\"name-tips\\\"  data-length=\\\"20\\\" maxlength=\\\"20\\\"  onblur=\\\"checkRegular(this);\\\"  class=\\\"form-control\\\"\\n                           placeholder=\\\"请输入课程名\\\">\\n                </td>\\n                <td class=\\\"form-item-remark\\\" > <span id = \\\"name-tips\\\"></span></td>\\n            </tr>\\n        </table>\\n    </form>\\n</div>\\n<script>\\n</script>\",\"ejs\":\"<% include ../inc/header.ejs%>\\n<style>\\n    .date-table {\\n        overflow-y: auto;\\n    }\\n</style>\\n\\n<% include ../inc/body.ejs%>\\n<!-- html主体代码 start -->\\n<div class=\\\"row\\\">\\n    <div class=\\\"col-sm-12\\\">\\n        <div class=\\\"ibox float-e-margins\\\">\\n            <div class=\\\"ibox-title\\\" style=\\\"padding-top: 3px !important;\\\">\\n                <div style=\\\"width: 140px; display: block;float: left;padding-top: 10px;\\\">\\n                    课程信息列表\\n                </div>\\n\\n                <div class=\\\"ibox-tools\\\">\\n                    <button style=\\\"margin-top: 8px;\\\" class=\\\"btn btn-primary btn-xs\\\" type=\\\"button\\\"\\n                            onclick=\\\"course.create(this);\\\">\\n                        <i class=\\\"fa fa-plus\\\"></i>&nbsp;新增\\n                    </button>\\n                </div>\\n\\n            </div>\\n            <div class=\\\"ibox-content data-view\\\" style=\\\"overflow-y: auto;\\\">\\n                <div class=\\\"col-sm-12\\\">\\n                    <div class=\\\" col-sm-12 date-table\\\">\\n                        <table class=\\\"table table-striped\\\">\\n                            <thead>\\n                            <tr>\\n                                <th>序号</th>\\n                                <th>学分</th>\\n                                <th>课程号</th>\\n                                <th>课程名</th>\\n                                <th></th>\\n                            </tr>\\n                            </thead>\\n                            <tbody id=\\\"item-list\\\">\\n\\n                            </tbody>\\n                        </table>\\n                    </div>\\n                    <div class=\\\"col-sm-12 pages M-box3 list-page\\\">\\n                    </div>\\n                </div>\\n\\n            </div>\\n        </div>\\n    </div>\\n</div>\\n<!-- html主体代码 end -->\\n<!-- 管理的课程信息模板 -->\\n<script id=\\\"course-template\\\" type=\\\"text/x-dot-template\\\">\\n    {{ if(it.length == 0) { }}\\n    暂无数据\\n    {{ }else{ }}\\n    {{ for(var i=0;i < it.length;i++){ }}\\n    <tr>\\n        <td>\\n            {{=(i+1)}}\\n        </td>\\n        <td>\\n            {{= it[i].credit}}\\n        </td>\\n        <td>\\n            {{= it[i].number}}\\n        </td>\\n        <td>\\n            {{= it[i].name}}\\n        </td>\\n        <td>\\n            <div class=\\\"btn-group\\\">\\n                <button data-toggle=\\\"dropdown\\\" class=\\\"btn btn-primary btn-xs dropdown-toggle\\\">操作 <span\\n                            class=\\\"caret\\\"></span>\\n                </button>\\n                <ul class=\\\"dropdown-menu\\\">\\n                    <li>\\n                        <a href=\\\"#\\\" data-id=\\\"{{= it[i].id}}\\\" onclick=\\\"course.update(this);\\\">修改</a>\\n                    </li>\\n                    <li>\\n                        <a href=\\\"#\\\" data-id=\\\"{{= it[i].id}}\\\" onclick=\\\"course.delete(this);\\\">删除</a>\\n                    </li>\\n                </ul>\\n            </div>\\n        </td>\\n    </tr>\\n    {{ } }}\\n    {{ } }}\\n</script>\\n<% include ../inc/js.ejs %>\\n\\n<!-- 私有脚本 start -->\\n<script src=\\\"/js/baseModule/_course.js\\\"></script>\\n<!-- 私有脚本 end -->\\n<% include ../inc/footer.ejs %>\"}', '2', '0', '1561520407', '0', '1561521697', '0');
INSERT INTO `sys_auto_code` VALUES ('1996657467384483152', 'teacher', '{\"js\":\"var index = 1, size = 6, key = \'\', totalPage = 0, totalCount = 0;\\nvar baseData = [], pageData = [];\\nvar optId = \'\';\\n\\nvar config = {\\n    form: \'/form/_teacher.html\',\\n    title: \'教师信息管理\',\\n};\\n\\n$(function () {\\n    //自适应\\n    view.initHeight();\\n    $(window).resize(function () {\\n        view.initHeight();\\n    });\\n    teacher.get();\\n});\\n\\nvar teacher = {\\n    get: function () {\\n        var param = {url: baseModule.teacherApi + \'/\' + index + \'-\' + size + \'-\' + key};\\n        var request = ajax.get(param);\\n        request.done(function (d) {\\n            pageData = d.result.data;\\n            render.page();\\n            totalPage = d.result.totalPage;\\n            totalCount = d.result.totalCount;\\n            if (d.result.totalPage>1) {\\n                  page.init(d.result.totalPage, d.result.totalCount);\\n            }else{\\n                 $(\'.list-page\').empty();\\n            }\\n        })\\n    },\\n\\n    create: function (event) {\\n        if (auth.refuse(event))\\n            return false;\\n        openLay({url: config.form, fun: \'opt.create();\', title: config.title});\\n    },\\n\\n    delete: function (event) {\\n        if (auth.refuse(event))\\n            return false;\\n        optId = getId(event);\\n        tips.confirm({message: \'是否要删除教师信息数据？\', fun: \\\"opt.delete();\\\"});\\n\\n    },\\n\\n    update: function (event) {\\n        if (auth.refuse(event))\\n            return false;\\n        optId = getId(event);//获取当前id的值\\n        openLay({url: config.form, fun: \\\"opt.update();\\\", title: config.title});\\n        var model = result.get(pageData, optId);\\n\\n        form.set(model);\\n    },\\n};\\n\\nvar render = {\\n    page: function () {\\n        var template = doT.template($(\\\"#teacher-template\\\").text());//获取的模板\\n        $(\'#item-list\').html(template(pageData));//模板装入数据\\n    },\\n};\\n\\nvar opt = {\\n    create: function () {\\n        var data = form.get(\\\"#opt-form\\\");\\n        if (form.verify(data))\\n            return false;\\n\\n        var param = {url: baseModule.teacherApi, data: data};\\n        var request = ajax.post(param);\\n        request.done(function (d) {\\n            tips.done(d);\\n            pageData.push(d.result);\\n            totalCount = totalCount + 1;\\n            page.init(totalPage, totalCount);\\n            render.page();\\n        })\\n    },\\n    delete: function () {\\n        var request = ajax.delete({url: baseModule.teacherApi + \'/\' + optId});\\n        request.done(function (d) {\\n            tips.ok(d.message);\\n            pageData = result.delete(pageData, optId);\\n            render.page();\\n            totalCount = totalCount - 1;\\n            page.init(totalPage, totalCount);\\n        })\\n    },\\n    update: function () {\\n        var data = form.get(\\\"#opt-form\\\");\\n        if (form.verify(data))\\n            return false;\\n        data[\'id\'] = optId;\\n\\n        var param = {url: baseModule.teacherApi, data: data};\\n        var request = ajax.put(param); //加一条记录\\n        request.done(function (d) {\\n            tips.ok(d.message);\\n            //更新对象\\n            pageData = result.update(pageData, d.result, \'id\');\\n            teacher.get();\\n            closeLay();\\n        })\\n    },\\n    close: function () {   //关闭按钮\\n        closeLay();\\n    }\\n};\\n\\nvar page = {\\n    init: function (_pageSize, _total) {\\n        $(\'.list-page\').pagination({\\n            pageCount: _pageSize,\\n            current: index,\\n            jump: true,\\n            coping: true,\\n            homePage: \'首页\',\\n            endPage: \'末页\',\\n            prevContent: \'上页\',\\n            nextContent: \'下页\',\\n            pageSize: size,\\n            pageArray: [6, 12, 24, 48],\\n            totalCount: _total,\\n            id: \'teacher-page\',\\n            callback: function (api) {\\n                index = api.getCurrent();\\n                teacher.get();\\n            }\\n        });\\n        if (_pageSize > 0)\\n            $(\'.pages\').show();\\n    }\\n};\\n\\nvar view = {\\n    initHeight: function () {\\n        $(\'.data-view\').css(\'height\', (parent.adaptable().h) - 80);\\n        $(\'.date-table\').css(\'height\', (parent.adaptable().h) - 180);\\n        size = Math.floor(((parent.adaptable().h) - 180) / 40);\\n    }\\n};\\n\\n\\nfunction pageChange(event) {\\n    size = $(event).val();\\n    index = 1;\\n    teacher.get();\\n};\\n\\nvar helper = {\\n    sex: function (_sex) {\\n        switch (parseInt(_sex)) {\\n             case 1:\\n                 return \\\"女\\\";\\n             case 2:\\n                 return \\\"男\\\";\\n         }\\n    },\\n};\\n\\nvar tool = {\\n    translate:function (model) {\\n        var data = [];\\n        for (var variable in model) {\\n            data[variable] = model[variable];\\n            //判断helper里是否存在该函数，存在则执行转换\\n            if (typeof eval(\'helper.\' + variable) == \'function\')\\n                model[variable] = eval(\'helper.\' + variable + \'(\' + model[variable] + \')\');\\n        }\\n        form.set(model);\\n        //恢复回转换前数据\\n        for (var variable in data) {\\n            model[variable] = data[variable];\\n        }\\n    }\\n};\",\"html\":\"<div class=\\\"table-responsive\\\">\\n    <form id=\\\"opt-form\\\">\\n        <table class=\\\"table table-bordered\\\">\\n            <tr>\\n                <td class=\\\"form-item-title\\\">\\n                    所属学院id\\n                </td>\\n                <td>\\n                    <input required type=\\\"text\\\" name=\\\"academyId\\\" id=\\\"academyId\\\"  tips-id = \\\"academyId-tips\\\"  data-length=\\\"19\\\" maxlength=\\\"19\\\"  onblur=\\\"checkRegular(this);\\\"  class=\\\"form-control\\\"\\n                           placeholder=\\\"请输入所属学院id\\\">\\n                </td>\\n                <td class=\\\"form-item-remark\\\" > <span id = \\\"academyId-tips\\\"></span></td>\\n            </tr>\\n            <tr>\\n                <td class=\\\"form-item-title\\\">\\n                    性别\\n                </td>\\n                <td>\\n                    <select required name=\\\"sex\\\" id=\\\"sex\\\" tips-id = \\\"sex-tips\\\" data-length=\\\"3\\\"  onblur=\\\"checkRegular(this);\\\" placeholder=\\\"请输入性别\\\" class=\\\"form-control\\\"> \\n                    <option value=\\\"1\\\">女</option>\\n                    <option value=\\\"2\\\">男</option>\\n                    </select>\\n                </td>\\n                <td class=\\\"form-item-remark\\\" > <span id = \\\"sex-tips\\\"></span></td>\\n            </tr>\\n            <tr>\\n                <td class=\\\"form-item-title\\\">\\n                    姓名\\n                </td>\\n                <td>\\n                    <input required type=\\\"text\\\" name=\\\"name\\\" id=\\\"name\\\"  tips-id = \\\"name-tips\\\"  data-length=\\\"20\\\" maxlength=\\\"20\\\"  onblur=\\\"checkRegular(this);\\\"  class=\\\"form-control\\\"\\n                           placeholder=\\\"请输入姓名\\\">\\n                </td>\\n                <td class=\\\"form-item-remark\\\" > <span id = \\\"name-tips\\\"></span></td>\\n            </tr>\\n            <tr>\\n                <td class=\\\"form-item-title\\\">\\n                    工号\\n                </td>\\n                <td>\\n                    <input required type=\\\"text\\\" name=\\\"number\\\" id=\\\"number\\\"  tips-id = \\\"number-tips\\\"  data-length=\\\"20\\\" maxlength=\\\"20\\\"  onblur=\\\"checkRegular(this);\\\"  class=\\\"form-control\\\"\\n                           placeholder=\\\"请输入工号\\\">\\n                </td>\\n                <td class=\\\"form-item-remark\\\" > <span id = \\\"number-tips\\\"></span></td>\\n            </tr>\\n        </table>\\n    </form>\\n</div>\\n<script>\\n</script>\",\"ejs\":\"<% include ../inc/header.ejs%>\\n<style>\\n    .date-table {\\n        overflow-y: auto;\\n    }\\n</style>\\n\\n<% include ../inc/body.ejs%>\\n<!-- html主体代码 start -->\\n<div class=\\\"row\\\">\\n    <div class=\\\"col-sm-12\\\">\\n        <div class=\\\"ibox float-e-margins\\\">\\n            <div class=\\\"ibox-title\\\" style=\\\"padding-top: 3px !important;\\\">\\n                <div style=\\\"width: 140px; display: block;float: left;padding-top: 10px;\\\">\\n                    教师信息列表\\n                </div>\\n\\n                <div class=\\\"ibox-tools\\\">\\n                    <button style=\\\"margin-top: 8px;\\\" class=\\\"btn btn-primary btn-xs\\\" type=\\\"button\\\"\\n                            onclick=\\\"teacher.create(this);\\\">\\n                        <i class=\\\"fa fa-plus\\\"></i>&nbsp;新增\\n                    </button>\\n                </div>\\n\\n            </div>\\n            <div class=\\\"ibox-content data-view\\\" style=\\\"overflow-y: auto;\\\">\\n                <div class=\\\"col-sm-12\\\">\\n                    <div class=\\\" col-sm-12 date-table\\\">\\n                        <table class=\\\"table table-striped\\\">\\n                            <thead>\\n                            <tr>\\n                                <th>序号</th>\\n                                <th>所属学院id</th>\\n                                <th>性别</th>\\n                                <th>姓名</th>\\n                                <th>工号</th>\\n                                <th></th>\\n                            </tr>\\n                            </thead>\\n                            <tbody id=\\\"item-list\\\">\\n\\n                            </tbody>\\n                        </table>\\n                    </div>\\n                    <div class=\\\"col-sm-12 pages M-box3 list-page\\\">\\n                    </div>\\n                </div>\\n\\n            </div>\\n        </div>\\n    </div>\\n</div>\\n<!-- html主体代码 end -->\\n<!-- 管理的教师信息模板 -->\\n<script id=\\\"teacher-template\\\" type=\\\"text/x-dot-template\\\">\\n    {{ if(it.length == 0) { }}\\n    暂无数据\\n    {{ }else{ }}\\n    {{ for(var i=0;i < it.length;i++){ }}\\n    <tr>\\n        <td>\\n            {{=(i+1)}}\\n        </td>\\n        <td>\\n            {{= it[i].academyId}}\\n        </td>\\n        <td>\\n            {{= helper.sex(it[i].sex) }}\\n        </td>\\n        <td>\\n            {{= it[i].name}}\\n        </td>\\n        <td>\\n            {{= it[i].number}}\\n        </td>\\n        <td>\\n            <div class=\\\"btn-group\\\">\\n                <button data-toggle=\\\"dropdown\\\" class=\\\"btn btn-primary btn-xs dropdown-toggle\\\">操作 <span\\n                            class=\\\"caret\\\"></span>\\n                </button>\\n                <ul class=\\\"dropdown-menu\\\">\\n                    <li>\\n                        <a href=\\\"#\\\" data-id=\\\"{{= it[i].id}}\\\" onclick=\\\"teacher.update(this);\\\">修改</a>\\n                    </li>\\n                    <li>\\n                        <a href=\\\"#\\\" data-id=\\\"{{= it[i].id}}\\\" onclick=\\\"teacher.delete(this);\\\">删除</a>\\n                    </li>\\n                </ul>\\n            </div>\\n        </td>\\n    </tr>\\n    {{ } }}\\n    {{ } }}\\n</script>\\n<% include ../inc/js.ejs %>\\n\\n<!-- 私有脚本 start -->\\n<script src=\\\"/js/baseModule/_teacher.js\\\"></script>\\n<!-- 私有脚本 end -->\\n<% include ../inc/footer.ejs %>\"}', '2', '0', '1561541882', '0', '1561605412', '0');
INSERT INTO `sys_auto_code` VALUES ('1999658679516114825', 'student', '{\"js\":\"var index = 1, size = 6, key = \'\', totalPage = 0, totalCount = 0;\\nvar baseData = [], pageData = [];\\nvar optId = \'\';\\n\\nvar config = {\\n    form: \'/form/_student.html\',\\n    title: \'学生信息管理\',\\n};\\n\\n$(function () {\\n    //自适应\\n    view.initHeight();\\n    $(window).resize(function () {\\n        view.initHeight();\\n    });\\n    student.get();\\n});\\n\\nvar student = {\\n    get: function () {\\n        var param = {url: baseModule.studentApi + \'/\' + index + \'-\' + size + \'-\' + key};\\n        var request = ajax.get(param);\\n        request.done(function (d) {\\n            pageData = d.result.data;\\n            render.page();\\n            totalPage = d.result.totalPage;\\n            totalCount = d.result.totalCount;\\n            if (d.result.totalPage>1) {\\n                  page.init(d.result.totalPage, d.result.totalCount);\\n            }else{\\n                 $(\'.list-page\').empty();\\n            }\\n        })\\n    },\\n\\n    create: function (event) {\\n        if (auth.refuse(event))\\n            return false;\\n        openLay({url: config.form, fun: \'opt.create();\', title: config.title});\\n    },\\n\\n    delete: function (event) {\\n        if (auth.refuse(event))\\n            return false;\\n        optId = getId(event);\\n        tips.confirm({message: \'是否要删除学生信息数据？\', fun: \\\"opt.delete();\\\"});\\n\\n    },\\n\\n    update: function (event) {\\n        if (auth.refuse(event))\\n            return false;\\n        optId = getId(event);//获取当前id的值\\n        openLay({url: config.form, fun: \\\"opt.update();\\\", title: config.title});\\n        var model = result.get(pageData, optId);\\n\\n        form.set(model);\\n    },\\n};\\n\\nvar render = {\\n    page: function () {\\n        var template = doT.template($(\\\"#student-template\\\").text());//获取的模板\\n        $(\'#item-list\').html(template(pageData));//模板装入数据\\n    },\\n};\\n\\nvar opt = {\\n    create: function () {\\n        var data = form.get(\\\"#opt-form\\\");\\n        if (form.verify(data))\\n            return false;\\n\\n        var param = {url: baseModule.studentApi, data: data};\\n        var request = ajax.post(param);\\n        request.done(function (d) {\\n            tips.done(d);\\n            pageData.push(d.result);\\n            totalCount = totalCount + 1;\\n            page.init(totalPage, totalCount);\\n            render.page();\\n        })\\n    },\\n    delete: function () {\\n        var request = ajax.delete({url: baseModule.studentApi + \'/\' + optId});\\n        request.done(function (d) {\\n            tips.ok(d.message);\\n            pageData = result.delete(pageData, optId);\\n            render.page();\\n            totalCount = totalCount - 1;\\n            page.init(totalPage, totalCount);\\n        })\\n    },\\n    update: function () {\\n        var data = form.get(\\\"#opt-form\\\");\\n        if (form.verify(data))\\n            return false;\\n        data[\'id\'] = optId;\\n\\n        var param = {url: baseModule.studentApi, data: data};\\n        var request = ajax.put(param); //加一条记录\\n        request.done(function (d) {\\n            tips.ok(d.message);\\n            //更新对象\\n            pageData = result.update(pageData, d.result, \'id\');\\n            student.get();\\n            closeLay();\\n        })\\n    },\\n    close: function () {   //关闭按钮\\n        closeLay();\\n    }\\n};\\n\\nvar page = {\\n    init: function (_pageSize, _total) {\\n        $(\'.list-page\').pagination({\\n            pageCount: _pageSize,\\n            current: index,\\n            jump: true,\\n            coping: true,\\n            homePage: \'首页\',\\n            endPage: \'末页\',\\n            prevContent: \'上页\',\\n            nextContent: \'下页\',\\n            pageSize: size,\\n            pageArray: [6, 12, 24, 48],\\n            totalCount: _total,\\n            id: \'student-page\',\\n            callback: function (api) {\\n                index = api.getCurrent();\\n                student.get();\\n            }\\n        });\\n        if (_pageSize > 0)\\n            $(\'.pages\').show();\\n    }\\n};\\n\\nvar view = {\\n    initHeight: function () {\\n        $(\'.data-view\').css(\'height\', (parent.adaptable().h) - 80);\\n        $(\'.date-table\').css(\'height\', (parent.adaptable().h) - 180);\\n        size = Math.floor(((parent.adaptable().h) - 180) / 40);\\n    }\\n};\\n\\n\\nfunction pageChange(event) {\\n    size = $(event).val();\\n    index = 1;\\n    student.get();\\n};\\n\\nvar helper = {\\n    sex: function (_sex) {\\n        switch (parseInt(_sex)) {\\n             case 1:\\n                 return \\\"女\\\";\\n             case 2:\\n                 return \\\"男\\\";\\n         }\\n    },\\n};\\n\\nvar tool = {\\n    translate:function (model) {\\n        var data = [];\\n        for (var variable in model) {\\n            data[variable] = model[variable];\\n            //判断helper里是否存在该函数，存在则执行转换\\n            if (typeof eval(\'helper.\' + variable) == \'function\')\\n                model[variable] = eval(\'helper.\' + variable + \'(\' + model[variable] + \')\');\\n        }\\n        form.set(model);\\n        //恢复回转换前数据\\n        for (var variable in data) {\\n            model[variable] = data[variable];\\n        }\\n    }\\n};\",\"html\":\"<div class=\\\"table-responsive\\\">\\n    <form id=\\\"opt-form\\\">\\n        <table class=\\\"table table-bordered\\\">\\n            <tr>\\n                <td class=\\\"form-item-title\\\">\\n                    所属班级id\\n                </td>\\n                <td>\\n                    <input required type=\\\"text\\\" name=\\\"classId\\\" id=\\\"classId\\\"  tips-id = \\\"classId-tips\\\"  data-length=\\\"19\\\" maxlength=\\\"19\\\"  onblur=\\\"checkRegular(this);\\\"  class=\\\"form-control\\\"\\n                           placeholder=\\\"请输入所属班级id\\\">\\n                </td>\\n                <td class=\\\"form-item-remark\\\" > <span id = \\\"classId-tips\\\"></span></td>\\n            </tr>\\n            <tr>\\n                <td class=\\\"form-item-title\\\">\\n                    性别\\n                </td>\\n                <td>\\n                    <select required name=\\\"sex\\\" id=\\\"sex\\\" tips-id = \\\"sex-tips\\\" data-length=\\\"3\\\"  onblur=\\\"checkRegular(this);\\\" placeholder=\\\"请输入性别\\\" class=\\\"form-control\\\"> \\n                    <option value=\\\"1\\\">女</option>\\n                    <option value=\\\"2\\\">男</option>\\n                    </select>\\n                </td>\\n                <td class=\\\"form-item-remark\\\" > <span id = \\\"sex-tips\\\"></span></td>\\n            </tr>\\n            <tr>\\n                <td class=\\\"form-item-title\\\">\\n                    姓名\\n                </td>\\n                <td>\\n                    <input required type=\\\"text\\\" name=\\\"name\\\" id=\\\"name\\\"  tips-id = \\\"name-tips\\\"  data-length=\\\"20\\\" maxlength=\\\"20\\\"  onblur=\\\"checkRegular(this);\\\"  class=\\\"form-control\\\"\\n                           placeholder=\\\"请输入姓名\\\">\\n                </td>\\n                <td class=\\\"form-item-remark\\\" > <span id = \\\"name-tips\\\"></span></td>\\n            </tr>\\n            <tr>\\n                <td class=\\\"form-item-title\\\">\\n                    学号\\n                </td>\\n                <td>\\n                    <input required type=\\\"text\\\" name=\\\"number\\\" id=\\\"number\\\"  tips-id = \\\"number-tips\\\"  data-length=\\\"20\\\" maxlength=\\\"20\\\"  onblur=\\\"checkRegular(this);\\\"  class=\\\"form-control\\\"\\n                           placeholder=\\\"请输入学号\\\">\\n                </td>\\n                <td class=\\\"form-item-remark\\\" > <span id = \\\"number-tips\\\"></span></td>\\n            </tr>\\n        </table>\\n    </form>\\n</div>\\n<script>\\n</script>\",\"ejs\":\"<% include ../inc/header.ejs%>\\n<style>\\n    .date-table {\\n        overflow-y: auto;\\n    }\\n</style>\\n\\n<% include ../inc/body.ejs%>\\n<!-- html主体代码 start -->\\n<div class=\\\"row\\\">\\n    <div class=\\\"col-sm-12\\\">\\n        <div class=\\\"ibox float-e-margins\\\">\\n            <div class=\\\"ibox-title\\\" style=\\\"padding-top: 3px !important;\\\">\\n                <div style=\\\"width: 140px; display: block;float: left;padding-top: 10px;\\\">\\n                    学生信息列表\\n                </div>\\n\\n                <div class=\\\"ibox-tools\\\">\\n                    <button style=\\\"margin-top: 8px;\\\" class=\\\"btn btn-primary btn-xs\\\" type=\\\"button\\\"\\n                            onclick=\\\"student.create(this);\\\">\\n                        <i class=\\\"fa fa-plus\\\"></i>&nbsp;新增\\n                    </button>\\n                </div>\\n\\n            </div>\\n            <div class=\\\"ibox-content data-view\\\" style=\\\"overflow-y: auto;\\\">\\n                <div class=\\\"col-sm-12\\\">\\n                    <div class=\\\" col-sm-12 date-table\\\">\\n                        <table class=\\\"table table-striped\\\">\\n                            <thead>\\n                            <tr>\\n                                <th>序号</th>\\n                                <th>所属班级id</th>\\n                                <th>性别</th>\\n                                <th>姓名</th>\\n                                <th>学号</th>\\n                                <th></th>\\n                            </tr>\\n                            </thead>\\n                            <tbody id=\\\"item-list\\\">\\n\\n                            </tbody>\\n                        </table>\\n                    </div>\\n                    <div class=\\\"col-sm-12 pages M-box3 list-page\\\">\\n                    </div>\\n                </div>\\n\\n            </div>\\n        </div>\\n    </div>\\n</div>\\n<!-- html主体代码 end -->\\n<!-- 管理的学生信息模板 -->\\n<script id=\\\"student-template\\\" type=\\\"text/x-dot-template\\\">\\n    {{ if(it.length == 0) { }}\\n    暂无数据\\n    {{ }else{ }}\\n    {{ for(var i=0;i < it.length;i++){ }}\\n    <tr>\\n        <td>\\n            {{=(i+1)}}\\n        </td>\\n        <td>\\n            {{= it[i].classId}}\\n        </td>\\n        <td>\\n            {{= helper.sex(it[i].sex) }}\\n        </td>\\n        <td>\\n            {{= it[i].name}}\\n        </td>\\n        <td>\\n            {{= it[i].number}}\\n        </td>\\n        <td>\\n            <div class=\\\"btn-group\\\">\\n                <button data-toggle=\\\"dropdown\\\" class=\\\"btn btn-primary btn-xs dropdown-toggle\\\">操作 <span\\n                            class=\\\"caret\\\"></span>\\n                </button>\\n                <ul class=\\\"dropdown-menu\\\">\\n                    <li>\\n                        <a href=\\\"#\\\" data-id=\\\"{{= it[i].id}}\\\" onclick=\\\"student.update(this);\\\">修改</a>\\n                    </li>\\n                    <li>\\n                        <a href=\\\"#\\\" data-id=\\\"{{= it[i].id}}\\\" onclick=\\\"student.delete(this);\\\">删除</a>\\n                    </li>\\n                </ul>\\n            </div>\\n        </td>\\n    </tr>\\n    {{ } }}\\n    {{ } }}\\n</script>\\n<% include ../inc/js.ejs %>\\n\\n<!-- 私有脚本 start -->\\n<script src=\\\"/js/baseModule/_student.js\\\"></script>\\n<!-- 私有脚本 end -->\\n<% include ../inc/footer.ejs %>\"}', '2', '0', '1561541217', '0', '1561541546', '0');

-- ----------------------------
-- Table structure for `sys_category`
-- ----------------------------
DROP TABLE IF EXISTS `sys_category`;
CREATE TABLE `sys_category` (
  `id` bigint(20) NOT NULL COMMENT '主键ID',
  `name` varchar(50) DEFAULT '' COMMENT '类型名称',
  `icon` varchar(200) DEFAULT '' COMMENT '图标',
  `code` varchar(200) DEFAULT '' COMMENT '类型编码',
  `parentId` bigint(20) DEFAULT '0' COMMENT '父级Id',
  `superId` bigint(20) DEFAULT '0' COMMENT '最顶级Id',
  `creator` bigint(20) DEFAULT '0' COMMENT '创建人',
  `createTime` int(11) unsigned DEFAULT '0' COMMENT '创建时间',
  `reviser` bigint(20) DEFAULT '0' COMMENT '修改人',
  `reviseTime` int(11) unsigned DEFAULT '0' COMMENT '修改时间',
  `isDel` smallint(1) unsigned DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `parentId` (`parentId`) USING BTREE,
  KEY `superId` (`superId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='120 类型表 ';

-- ----------------------------
-- Records of sys_category
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_global_config`
-- ----------------------------
DROP TABLE IF EXISTS `sys_global_config`;
CREATE TABLE `sys_global_config` (
  `id` bigint(20) NOT NULL COMMENT '主键ID',
  `systemId` bigint(20) DEFAULT '0' COMMENT '子系统id sys_subsystem.id',
  `attr` varchar(50) DEFAULT '' COMMENT '配置属性',
  `val` text COMMENT '配置值',
  `creator` bigint(20) DEFAULT '0' COMMENT '创建人',
  `createTime` int(11) unsigned DEFAULT '0' COMMENT '创建时间',
  `reviser` bigint(20) DEFAULT '0' COMMENT '修改人',
  `reviseTime` int(11) unsigned DEFAULT '0' COMMENT '修改时间',
  `isDel` smallint(1) unsigned DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `systemId` (`systemId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='201 系统基础配置项';

-- ----------------------------
-- Records of sys_global_config
-- ----------------------------
INSERT INTO `sys_global_config` VALUES ('2012881479558146998', '0', 'jpushKey', '57c0ac9368644fca0bfb4046', '0', '1513435928', '0', '0', '0');
INSERT INTO `sys_global_config` VALUES ('2012885471054389833', '0', 's-am-s3', '11:30:00', '0', '1513435929', '0', '0', '0');
INSERT INTO `sys_global_config` VALUES ('2013880479544800436', '0', 't-am-s1', '08:00:00', '0', '1513435928', '0', '0', '0');
INSERT INTO `sys_global_config` VALUES ('2013883477290215838', '0', 'websiteName', '1', '0', '1513435928', '0', '0', '0');
INSERT INTO `sys_global_config` VALUES ('2013885473802368578', '0', 'statisticalCode', '7', '0', '1513435928', '0', '0', '0');
INSERT INTO `sys_global_config` VALUES ('2013886474563212602', '0', 't-pm-s1', '13:30:00', '0', '1513435929', '0', '0', '0');
INSERT INTO `sys_global_config` VALUES ('2013887472293537806', '0', 'h5-url', 'http://111.230.18.227:8800/mobile_view/article.html', '0', '1513435928', '0', '0', '0');
INSERT INTO `sys_global_config` VALUES ('2013888472063211123', '0', 'smsId', '1400050493', '0', '1513435928', '0', '0', '0');
INSERT INTO `sys_global_config` VALUES ('2013888472809727594', '0', 's-am-s2', '08:30:00', '0', '1513435929', '0', '0', '0');
INSERT INTO `sys_global_config` VALUES ('2014883479047196556', '0', 'copyright', 'adsasd4sdadsad', '0', '1513435928', '0', '0', '0');
INSERT INTO `sys_global_config` VALUES ('2014887473349842853', '0', 's-pm-s4', '18:00:00', '0', '1513435929', '0', '0', '0');
INSERT INTO `sys_global_config` VALUES ('2015880478314798723', '0', 't-pm-s4', '23:00:00', '0', '1513435929', '0', '0', '0');
INSERT INTO `sys_global_config` VALUES ('2015881478791212326', '0', 'jpushSecret', '2fb08aa3d9d1dbb18b434b15', '0', '1513435928', '0', '0', '0');
INSERT INTO `sys_global_config` VALUES ('2016880473320948974', '0', 't-am-s4', '13:00:00', '0', '1513435929', '0', '0', '0');
INSERT INTO `sys_global_config` VALUES ('2016883470092505601', '0', 't-pm-s3', '17:30:00', '0', '1513435929', '0', '0', '0');
INSERT INTO `sys_global_config` VALUES ('2016883474565269652', '0', 's-am-s1', '08:00:00', '0', '1513435929', '0', '0', '0');
INSERT INTO `sys_global_config` VALUES ('2016885472319876366', '0', 'smsKey', 'e60db5458945569227f59b2523cd5320', '0', '1513435928', '0', '0', '0');
INSERT INTO `sys_global_config` VALUES ('2017887473545310721', '0', 'seoDescription', '6', '0', '1513435928', '0', '0', '0');
INSERT INTO `sys_global_config` VALUES ('2017887478093378921', '0', 's-pm-s3', '17:00:00', '0', '1513435929', '0', '0', '0');
INSERT INTO `sys_global_config` VALUES ('2017887479813352553', '0', 's-pm-s2', '14:30:00', '0', '1513435929', '0', '0', '0');
INSERT INTO `sys_global_config` VALUES ('2018883471296845793', '0', 'seoKeywords', '5', '0', '1513435928', '0', '0', '0');
INSERT INTO `sys_global_config` VALUES ('2018883475852616588', '0', 't-pm-s2', '14:10:00', '0', '1513435929', '0', '0', '0');
INSERT INTO `sys_global_config` VALUES ('2018884470796723617', '0', 'icp', '3', '0', '1513435928', '0', '0', '0');
INSERT INTO `sys_global_config` VALUES ('2018885473551456983', '0', 'websiteTitle', '2', '0', '1513435928', '0', '0', '0');
INSERT INTO `sys_global_config` VALUES ('2019880472051502525', '0', 't-am-s3', '12:00:00', '0', '1513435929', '0', '0', '0');
INSERT INTO `sys_global_config` VALUES ('2019884473054060866', '0', 'readPath', 'http://cloudsystem-1255473973.cosgz.myqcloud.com/', '0', '1513435928', '0', '0', '0');
INSERT INTO `sys_global_config` VALUES ('2019885474537538482', '0', 's-pm-s1', '14:00:00', '0', '1513435929', '0', '0', '0');
INSERT INTO `sys_global_config` VALUES ('2019886476810230883', '0', 't-am-s2', '09:10:00', '0', '1513435929', '0', '0', '0');
INSERT INTO `sys_global_config` VALUES ('2019888474309842761', '0', 's-am-s4', '12:00:00', '0', '1513435929', '0', '0', '0');

-- ----------------------------
-- Table structure for `sys_menu`
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `id` bigint(20) NOT NULL COMMENT '主键ID',
  `name` varchar(200) DEFAULT '' COMMENT '菜单名称',
  `url` varchar(200) DEFAULT '' COMMENT '对应菜单路由',
  `code` varchar(200) DEFAULT '' COMMENT '对应接口函数',
  `icon` varchar(200) DEFAULT '' COMMENT '图标',
  `parentId` bigint(20) DEFAULT '0' COMMENT '父级Id',
  `systemId` bigint(20) DEFAULT '0' COMMENT '子系统id sys_subsystem.id',
  `type` varchar(255) DEFAULT NULL COMMENT '菜单类型',
  `status` smallint(1) unsigned DEFAULT '1' COMMENT '状态 1有效 0无效',
  `hide` tinyint(1) DEFAULT '0' COMMENT '是否隐藏 0显示 1隐藏',
  `sort` int(6) unsigned DEFAULT '1' COMMENT '排序方式 数字越小越靠前',
  `creator` bigint(20) DEFAULT '0' COMMENT '创建人',
  `createTime` int(11) unsigned DEFAULT '0' COMMENT '创建时间',
  `reviser` bigint(20) DEFAULT '0' COMMENT '修改人',
  `reviseTime` int(11) unsigned DEFAULT '0' COMMENT '修改时间',
  `isDel` smallint(1) unsigned DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `parentId` (`parentId`) USING BTREE,
  KEY `systemId` (`systemId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='150 权限菜单表 ';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES ('1501370696573288143', '学生作业关系管理', '/my/core/examCandidate', 'ExamCandidateApi.get', '', '1503172858973826665', '0', 'subMenu', '1', '1', '7', '1009069242458231540', '1561952970', '1009069242458231540', '1561953205', '0');
INSERT INTO `sys_menu` VALUES ('1501584336747852962', '前端代码生成', '/sys/front_code/', 'ToFrontEndCodeApi.get', 'fa-file-pdf-o', '1504271716499778177', '0', 'subMenu', '1', '0', '3', '1003866568974705484', '1534584576', '1003866568974705484', '1558083535', '0');
INSERT INTO `sys_menu` VALUES ('1501613001821269135', '课程信息', '/my/core/course', 'CourseApi.get', 'fa-calendar', '1502578895889978168', '0', 'subMenu', '1', '0', '0', '1002518130371308851', '1561532951', '0', '0', '0');
INSERT INTO `sys_menu` VALUES ('1502118213610571763', '操作日记', '/my/core/operationRecord', 'SysOperationRecordApi.getSysOperationRecord', 'fa-television', '1508622161153824054', '0', 'subMenu', '1', '0', '5', '1008604263574962166', '1544247414', '1003866568974705484', '1544335771', '0');
INSERT INTO `sys_menu` VALUES ('1502305695171419173', '待办事项', '/user/todo/', 'ApprovalApi.getToDoApproval', 'fa-tasks', '1508682483856378091', '0', 'subMenu', '1', '0', '1', '1003866568974705484', '1545723579', '1003866568974705484', '1545728581', '0');
INSERT INTO `sys_menu` VALUES ('1502388861979864126', '班级管理', '/my/core/classes', 'ClassesApi.get', 'fa-list-ol', '1503566409477762965', '0', 'subMenu', '1', '0', '3', '1002518130371308851', '1561477605', '1002518130371308851', '1561477653', '0');
INSERT INTO `sys_menu` VALUES ('1502578895889978168', '课程管理', '', '', 'fa-calendar-check-o', '1507647620884801891', '0', 'menu', '1', '0', '5', '1002518130371308851', '1561521957', '1002518130371308851', '1561533085', '0');
INSERT INTO `sys_menu` VALUES ('1503172858973826665', '作业管理', '', '', 'fa-pencil-square-o', '1507647620884801891', '0', 'menu', '1', '0', '3', '1003866568974705484', '1536394774', '1002518130371308851', '1561517159', '0');
INSERT INTO `sys_menu` VALUES ('1503566409477762965', '组织管理', '', '', 'fa-industry', '1507647620884801891', '0', 'menu', '1', '0', '2', '1003866568974705484', '1528619836', '1002518130371308851', '1561517146', '0');
INSERT INTO `sys_menu` VALUES ('1503907724282130056', '授课安排', '/my/core/teachSchedule', 'TeachScheduleApi.get', 'fa-calendar-plus-o', '1502578895889978168', '0', 'subMenu', '1', '0', '1', '1002518130371308851', '1561602023', '1002518130371308851', '1561602034', '0');
INSERT INTO `sys_menu` VALUES ('1504271716499778177', '资源管理', '', '', 'fa-keyboard-o', '1508639258116985267', '0', 'menu', '1', '0', '1', '1008888888888888888', '1506138709', '1003866568974705484', '1558083540', '0');
INSERT INTO `sys_menu` VALUES ('1504394852215031919', '学院管理', '/my/core/academyInfo', 'AcademyInfoApi.get', 'fa-university', '1503566409477762965', '0', 'subMenu', '1', '0', '1', '1003866568974705484', '1528815924', '1002518130371308851', '1561477755', '0');
INSERT INTO `sys_menu` VALUES ('1504561318050689468', '查看个人信息', '/my/core/personalInfo', 'SysUserApi.getPersonalInfo', 'fa-cogs', '1508682483856378091', '0', 'subMenu', '1', '0', '3', '1003866568974705484', '1535771181', '1002518130371308851', '1561948393', '0');
INSERT INTO `sys_menu` VALUES ('1505626962836820724', '菜单配置', '/sys/menu/', 'SysAuthApi.get', 'fa-object-ungroup', '1508622161153824054', '0', 'subMenu', '1', '0', '1', '1008888888888888888', '1505744422', '1008604263574962166', '1544244875', '0');
INSERT INTO `sys_menu` VALUES ('1506309144385244910', '消息中心', '/user/message/', 'MessageApi.getIndex', 'fa-inbox', '1508682483856378091', '0', 'subMenu', '1', '0', '2', '1003866568974705484', '1545723686', '1003866568974705484', '1556283785', '0');
INSERT INTO `sys_menu` VALUES ('1506684717783011305', '修改密码', '/my/core/updatePassword', 'updatePasswordApi.get', 'fa-asterisk', '1508682483856378091', '0', 'subMenu', '1', '0', '4', '1007530354790652881', '1533893491', '1003866568974705484', '1545723429', '0');
INSERT INTO `sys_menu` VALUES ('1506781977159534714', '答题记录管理', '/my/core/examRecord', 'ExamRecordApi.get', '', '1503172858973826665', '0', 'subMenu', '1', '1', '5', '1002518130371308851', '1561810701', '0', '0', '0');
INSERT INTO `sys_menu` VALUES ('1507017698831654964', '作业批改', '/my/core/examQuestion', 'ExamQuestionApi.get', '', '1503172858973826665', '0', 'subMenu', '1', '1', '6', '1002518130371308851', '1561866184', '0', '0', '0');
INSERT INTO `sys_menu` VALUES ('1507166151911181043', '角色管理', '/sys/role/', 'SysRoleApi.get', 'fa-hand-rock-o', '1508622161153824054', '0', 'subMenu', '1', '0', '0', '1008888888888888888', '1506349813', '1008604263574962166', '1544244862', '0');
INSERT INTO `sys_menu` VALUES ('1507246536601955290', '专业管理', '/my/core/majorInfo', 'MajorInfoApi.getMajorInfo', 'fa-black-tie', '1503566409477762965', '0', 'subMenu', '1', '0', '2', '1003866568974705484', '1530927282', '1002518130371308851', '1561477760', '0');
INSERT INTO `sys_menu` VALUES ('1507647620884801891', '数据中心', '', '', '', '0', '0', 'module', '1', '0', '3', '1008888888888888888', '1509803657', '0', '0', '0');
INSERT INTO `sys_menu` VALUES ('1507841542104492241', '题库管理', '/my/core/question', 'QuestionApi.get', 'fa-file-text-o', '1503172858973826665', '0', 'subMenu', '1', '0', '1', '1004558574236073817', '1535360680', '1003866568974705484', '1551946057', '0');
INSERT INTO `sys_menu` VALUES ('1507864990655592991', '账号管理', '', '', 'fa-users', '1507647620884801891', '0', 'menu', '1', '0', '1', '1005211393475807387', '1517245742', '1002518130371308851', '1561517172', '0');
INSERT INTO `sys_menu` VALUES ('1508622161153824054', '系统管理', '', '', 'fa-cubes', '1508626008299626692', '0', 'menu', '1', '0', '1', '1008888888888888888', '1505744233', '0', '0', '0');
INSERT INTO `sys_menu` VALUES ('1508626008299626692', '系统配置', '', '', '', '0', '1986514272259991655', 'module', '1', '0', '98', '1008888888888888888', '1505744194', '1005211393475807387', '1517243698', '0');
INSERT INTO `sys_menu` VALUES ('1508639258116985267', '辅助功能', '', '', '', '0', '1986514272259991655', 'module', '1', '0', '99', '1008888888888888888', '1505747354', '1003866568974705484', '1558083543', '0');
INSERT INTO `sys_menu` VALUES ('1508657911837197184', '学生管理', '/my/core/student', 'StudentApi.get', 'fa-users', '1507864990655592991', '0', 'subMenu', '1', '0', '0', '1002518130371308851', '1561541749', '1002518130371308851', '1561542135', '0');
INSERT INTO `sys_menu` VALUES ('1508667099921872494', '数据字典', '/my/core/dictionary', 'DictionaryApi.get', 'fa-object-group', '1508622161153824054', '0', 'subMenu', '1', '0', '2', '1007530354790652881', '1533886669', '1008604263574962166', '1544244893', '0');
INSERT INTO `sys_menu` VALUES ('1508682483856378091', '个人信息', '', '', 'fa-graduation-cap', '1507647620884801891', '0', 'menu', '1', '0', '0', '1007530354790652881', '1533893438', '1003866568974705484', '1533897887', '0');
INSERT INTO `sys_menu` VALUES ('1508928998786471827', '教师管理', '/my/core/teacher', 'TeacherApi.get', 'fa-graduation-cap', '1507864990655592991', '0', 'subMenu', '1', '0', '0', '1002518130371308851', '1561605666', '0', '0', '0');
INSERT INTO `sys_menu` VALUES ('1509271907441429456', '后台代码生成', '/sys/code/', '/', 'fa-file-code-o', '1504271716499778177', '0', 'subMenu', '1', '0', '2', '1008888888888888888', '1506138993', '1003866568974705484', '1558083538', '0');
INSERT INTO `sys_menu` VALUES ('1509334110809051917', '表单代码生成', '/sys/component/', '/', 'fa-clipboard', '1504271716499778177', '0', 'subMenu', '1', '0', '1', '1008888888888888888', '1506153347', '1003866568974705484', '1558083530', '0');
INSERT INTO `sys_menu` VALUES ('1509401261013647928', '组卷管理', '/my/core/examPaperGenerate', 'ExamQuestionApi.customExam', 'fa-calendar-plus-o', '1503172858973826665', '0', 'subMenu', '1', '0', '3', '1004558574236073817', '1535972632', '1003866568974705484', '1551946051', '0');
INSERT INTO `sys_menu` VALUES ('1509477538412941603', '作业信息', '/my/core/exam', 'ExamApi.get', 'fa-list-alt', '1503172858973826665', '0', 'subMenu', '1', '0', '2', '1004558574236073817', '1535512310', '1002518130371308851', '1561533685', '0');
INSERT INTO `sys_menu` VALUES ('1509708127445078684', '学生管理', '/my/core/examStudent', 'ExamApi.getExamGrade', 'fa-user-secret', '1503172858973826665', '0', 'subMenu', '1', '0', '4', '1003866568974705484', '1551778082', '1002518130371308851', '1561810717', '0');

-- ----------------------------
-- Table structure for `sys_menu_auth`
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu_auth`;
CREATE TABLE `sys_menu_auth` (
  `id` bigint(20) NOT NULL COMMENT '主键ID',
  `name` varchar(200) DEFAULT '' COMMENT '按钮名称',
  `menuId` bigint(20) NOT NULL COMMENT '对应权限子菜单 sys_menu.id',
  `fun` varchar(200) DEFAULT '' COMMENT '对应前端函数',
  `code` varchar(200) DEFAULT '' COMMENT '对应接口函数',
  `icon` varchar(200) DEFAULT '' COMMENT '图标',
  `sort` int(6) unsigned DEFAULT '1' COMMENT '排序方式 数字越小越靠前',
  `creator` bigint(20) DEFAULT '0' COMMENT '创建人',
  `createTime` int(11) unsigned DEFAULT '0' COMMENT '创建时间',
  `reviser` bigint(20) DEFAULT '0' COMMENT '修改人',
  `reviseTime` int(11) unsigned DEFAULT '0' COMMENT '修改时间',
  `isDel` smallint(1) unsigned DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `menuId` (`menuId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='151 权限菜单按钮权限表';

-- ----------------------------
-- Records of sys_menu_auth
-- ----------------------------
INSERT INTO `sys_menu_auth` VALUES ('1511105116590581166', '分类数据权限配置', '1507166151911181043', 'role.authCategoryData', 'SysRoleApi.configAuthData', '', '1', '1003866568974705484', '1523263696', '0', '0', '0');
INSERT INTO `sys_menu_auth` VALUES ('1511296728208385603', '导入题库状态', '1507841542104492241', 'question.import', 'QuestionApi.getImportStatus', '', '1', '1003866568974705484', '1554542211', '1003866568974705484', '1554697165', '0');
INSERT INTO `sys_menu_auth` VALUES ('1511399583454320967', '新增', '1504394852215031919', 'academyInfo.create', 'AcademyInfoApi.createAcademyInfo', '', '1', '1003866568974705484', '1528816338', '1003866568974705484', '1529499560', '0');
INSERT INTO `sys_menu_auth` VALUES ('1511399948470918101', '修改', '1504394852215031919', 'academyInfo.update', 'AcademyInfoApi.updateAcademyInfo', '', '1', '1003866568974705484', '1528816423', '0', '0', '0');
INSERT INTO `sys_menu_auth` VALUES ('1511472841079878032', '修改', '1509477538412941603', 'exam.update', 'ExamApi.updateExam', '', '1', '1004558574236073817', '1535512386', '0', '0', '0');
INSERT INTO `sys_menu_auth` VALUES ('1511666918643014325', '修改', '1508667099921872494', 'dictionary.update', 'DictionaryApi.updateDictionary', '', '1', '1007530354790652881', '1533886863', '0', '0', '0');
INSERT INTO `sys_menu_auth` VALUES ('1511851446932894720', '查询', '1507246536601955290', 'majorInfo.read', 'MajorInfoApi.readMajorInfo', '', '1', '1006981336245857148', '1535363278', '0', '0', '0');
INSERT INTO `sys_menu_auth` VALUES ('1511909480534229558', '修改', '1503907724282130056', 'teachSchedule.update', 'TeachScheduleApi.updateTeachSchedule', '', '1', '1002518130371308851', '1561602445', '0', '0', '0');
INSERT INTO `sys_menu_auth` VALUES ('1511923168390969898', '新增', '1508928998786471827', 'teacher.create', 'TeacherApi.createTeacher', '', '1', '1002518130371308851', '1561605705', '0', '0', '0');
INSERT INTO `sys_menu_auth` VALUES ('1512132996922988578', '手动组卷', '1509477538412941603', 'exam.redirectExamPaperGenerate', '前端方法', '', '1', '1008604263574962166', '1559272978', '0', '0', '0');
INSERT INTO `sys_menu_auth` VALUES ('1512190440451160782', '发布', '1509477538412941603', 'exam.release', 'ExamApi.releaseExam', '', '1', '1004558574236073817', '1535682521', '0', '0', '0');
INSERT INTO `sys_menu_auth` VALUES ('1512292258929414625', '导入题库', '1507841542104492241', 'question.import', 'QuestionApi.Import', '', '1', '1003866568974705484', '1554542100', '1003866568974705484', '1554697173', '0');
INSERT INTO `sys_menu_auth` VALUES ('1512307645287162841', '删除子菜单', '1505626962836820724', '/', 'SysAuthApi.deleteSubMenu', '', '1', '1008888888888888888', '1507099281', '0', '0', '0');
INSERT INTO `sys_menu_auth` VALUES ('1512366497721742954', '阅读', '1506309144385244910', 'message.read', 'MessageApi.createMessageUser', '', '1', '1003866568974705484', '1549551341', '0', '0', '0');
INSERT INTO `sys_menu_auth` VALUES ('1512368785119214681', '详情', '1506309144385244910', 'message.read', 'MessageApi.getMessageById', '', '1', '1003866568974705484', '1549551410', '0', '0', '0');
INSERT INTO `sys_menu_auth` VALUES ('1512486829107729414', '修改个人信息', '1506684717783011305', '/', 'SysUserApi.updatePersonalDetailInfo', '', '1', '1003504241965893326', '1535514287', '1002518130371308851', '1561945964', '0');
INSERT INTO `sys_menu_auth` VALUES ('1512610952998061038', '删除', '1501613001821269135', 'course.delete', 'CourseApi.deleteCourse', '', '1', '1002518130371308851', '1561533177', '0', '0', '0');
INSERT INTO `sys_menu_auth` VALUES ('1513243750874386174', '删除', '1507246536601955290', 'majorInfo.delete', 'MajorInfoApi.deleteMajorInfo', '', '1', '1003866568974705484', '1530927336', '0', '0', '0');
INSERT INTO `sys_menu_auth` VALUES ('1513308915669521848', '删除权限模块', '1505626962836820724', '/', 'SysAuthApi.deleteModule', '', '1', '1008888888888888888', '1507099105', '0', '0', '0');
INSERT INTO `sys_menu_auth` VALUES ('1513370219115781197', '公布成绩', '1509477538412941603', 'exam.publish', 'ExamApi.publishExam', '', '1', '1002518130371308851', '1561950948', '0', '0', '0');
INSERT INTO `sys_menu_auth` VALUES ('1513479955774283785', '删除', '1509477538412941603', 'exam.delete', 'ExamApi.deleteExam', '', '1', '1004558574236073817', '1535512411', '0', '0', '0');
INSERT INTO `sys_menu_auth` VALUES ('1513498276260015361', '查询', '1509708127445078684', 'examStudent.select', 'ExamApi.getExamGrade', '', '1', '1003866568974705484', '1551967899', '1008604263574962166', '1559638251', '0');
INSERT INTO `sys_menu_auth` VALUES ('1513652207811494295', '修改', '1508657911837197184', 'student.update', 'StudentApi.updateStudent', '', '1', '1002518130371308851', '1561542296', '0', '0', '0');
INSERT INTO `sys_menu_auth` VALUES ('1513842179456569649', '删除', '1507166151911181043', 'role.delete', 'SysRoleApi.deleteRole', '', '1', '1008888888888888888', '1509849803', '0', '0', '0');
INSERT INTO `sys_menu_auth` VALUES ('1513901584990123347', '删除', '1503907724282130056', 'teachSchedule.delete', 'TeachScheduleApi.deleteTeachSchedule', '', '1', '1002518130371308851', '1561602469', '0', '0', '0');
INSERT INTO `sys_menu_auth` VALUES ('1514138091786165514', '考生管理', '1509477538412941603', 'exam.redirectExamStudent', '前端方法', '', '1', '1008604263574962166', '1559273002', '0', '0', '0');
INSERT INTO `sys_menu_auth` VALUES ('1514148561064635474', '重置缓存', '1509477538412941603', 'exam.sync', 'ExamApi.syncExamCache', '', '1', '1003866568974705484', '1558322063', '1002518130371308851', '1561806200', '0');
INSERT INTO `sys_menu_auth` VALUES ('1514191295470040079', '查看', '1509477538412941603', 'exam.read', 'ExamApi.read', '', '1', '1004558574236073817', '1535682960', '1004558574236073817', '1535859651', '0');
INSERT INTO `sys_menu_auth` VALUES ('1514301494033370322', '创建子菜单', '1505626962836820724', '/', 'SysAuthApi.createSubMenu', '', '1', '1008888888888888888', '1507099244', '0', '0', '0');
INSERT INTO `sys_menu_auth` VALUES ('1514302579924918575', '修改子菜单', '1505626962836820724', '/', 'SysAuthApi.updateSubMenu', '', '1', '1008888888888888888', '1507099263', '0', '0', '0');
INSERT INTO `sys_menu_auth` VALUES ('1514304953938976647', '删除功能权限', '1505626962836820724', '/', 'SysAuthApi.deleteSubMenuAuth', '', '1', '1008888888888888888', '1507099354', '0', '0', '0');
INSERT INTO `sys_menu_auth` VALUES ('1514498869634078560', '导出', '1509708127445078684', 'examStudent.export', 'ExamApi.exportExamGrade', '', '1', '1003866568974705484', '1551968041', '1008604263574962166', '1559638257', '0');
INSERT INTO `sys_menu_auth` VALUES ('1514654741822001627', '修改', '1507166151911181043', 'role.update', 'SysRoleApi.updateRole', '', '1', '1008888888888888888', '1509806070', '0', '0', '0');
INSERT INTO `sys_menu_auth` VALUES ('1514760221276716784', '退回重做', '1509477538412941603', 'examStudent.returned', 'ExamApi.returnedExam', '', '1', '1002518130371308851', '1561806231', '0', '0', '0');
INSERT INTO `sys_menu_auth` VALUES ('1515302786258432919', '创建功能权限', '1505626962836820724', '/', 'SysAuthApi.createSubMenuAuth', '', '1', '1008888888888888888', '1507099313', '0', '0', '0');
INSERT INTO `sys_menu_auth` VALUES ('1515378397107670515', '保存作业', '1509401261013647928', 'examPaperGenerate.save', 'ExamQuestionApi.customExam', '', '1', '1003866568974705484', '1549555131', '1002518130371308851', '1561707380', '0');
INSERT INTO `sys_menu_auth` VALUES ('1515388293914856523', '新增', '1502388861979864126', 'classes.create', 'ClassesApi.createClasses', '', '1', '1002518130371308851', '1561477705', '0', '0', '0');
INSERT INTO `sys_menu_auth` VALUES ('1515388629940769839', '修改', '1502388861979864126', 'classes.update', 'ClassesApi.updateClasses', '', '1', '1002518130371308851', '1561477786', '0', '0', '0');
INSERT INTO `sys_menu_auth` VALUES ('1515610859668195684', '修改', '1501613001821269135', 'course.update', 'CourseApi.updateCourse', '', '1', '1002518130371308851', '1561533153', '0', '0', '0');
INSERT INTO `sys_menu_auth` VALUES ('1515651703524301786', '新增', '1508657911837197184', 'student.create', 'StudentApi.createStudent', '', '1', '1002518130371308851', '1561542177', '0', '0', '0');
INSERT INTO `sys_menu_auth` VALUES ('1515682946906118637', '修改密码', '1506684717783011305', 'updatePassword', 'SysUserApi.updatePassword', '', '1', '1007530354790652881', '1533893547', '0', '0', '0');
INSERT INTO `sys_menu_auth` VALUES ('1515700561270231947', '修改', '1507841542104492241', 'question.update', 'QuestionApi.updateQuestionAndOptions', '', '1', '1003866568974705484', '1551539530', '1003866568974705484', '1551541236', '0');
INSERT INTO `sys_menu_auth` VALUES ('1515841633041392598', '创建', '1507841542104492241', 'question.make', 'QuestionApi.makeQuestions', '', '1', '1004558574236073817', '1535361176', '0', '0', '0');
INSERT INTO `sys_menu_auth` VALUES ('1516247701028082137', '修改', '1507246536601955290', 'majorInfo.update', 'MajorInfoApi.updateMajorInfo', '', '1', '1003866568974705484', '1530927324', '0', '0', '0');
INSERT INTO `sys_menu_auth` VALUES ('1516301309324957365', '修改菜单', '1505626962836820724', '/', 'SysAuthApi.updateMenu', '', '1', '1008888888888888888', '1507099198', '0', '0', '0');
INSERT INTO `sys_menu_auth` VALUES ('1516302231202139435', '创建菜单', '1505626962836820724', '/', 'SysAuthApi.createMenu', '', '1', '1008888888888888888', '1507099181', '0', '0', '0');
INSERT INTO `sys_menu_auth` VALUES ('1516560932860421177', '修改个人基本信息', '1504561318050689468', 'personalBaseInfo.update', 'SysUserApi.updatePersonalBaseInfo', '', '1', '1003866568974705484', '1535771330', '1003866568974705484', '1536199407', '0');
INSERT INTO `sys_menu_auth` VALUES ('1516617713430785704', '新增', '1501613001821269135', 'course.create', 'CourseApi.createCourse', '', '1', '1002518130371308851', '1561533119', '0', '0', '0');
INSERT INTO `sys_menu_auth` VALUES ('1516653651919418377', '创建', '1507166151911181043', 'role.create', 'SysRoleApi.createRole', '', '1', '1008888888888888888', '1509806048', '0', '0', '0');
INSERT INTO `sys_menu_auth` VALUES ('1516653844923331523', '权限配置', '1507166151911181043', 'role.auth', 'SysRoleApi.configAuth', '', '1', '1008888888888888888', '1509806093', '0', '0', '0');
INSERT INTO `sys_menu_auth` VALUES ('1516665636010326987', '新增', '1508667099921872494', 'dictionary.create', 'DictionaryApi.createDictionary', '', '1', '1007530354790652881', '1533886796', '0', '0', '0');
INSERT INTO `sys_menu_auth` VALUES ('1516925380164154939', '删除', '1508928998786471827', 'teacher.delete', 'TeacherApi.deleteTeacher', '', '1', '1002518130371308851', '1561605757', '0', '0', '0');
INSERT INTO `sys_menu_auth` VALUES ('1516927303731718162', '修改', '1508928998786471827', 'teacher.update', 'TeacherApi.updateTeacher', '', '1', '1002518130371308851', '1561605740', '0', '0', '0');
INSERT INTO `sys_menu_auth` VALUES ('1517141773239623195', '重置', '1509708127445078684', 'examStudent.sync', 'ExamApi.syncExamStudentStatus', '', '1', '1003866568974705484', '1558083455', '1008604263574962166', '1559638265', '0');
INSERT INTO `sys_menu_auth` VALUES ('1517247624612134200', '新增', '1507246536601955290', 'majorInfo.create', 'MajorInfoApi.createMajorInfo', '', '1', '1003866568974705484', '1530927305', '0', '0', '0');
INSERT INTO `sys_menu_auth` VALUES ('1517301371484882061', '删除菜单', '1505626962836820724', '/', 'SysAuthApi.deleteMenu', '', '1', '1008888888888888888', '1507099215', '0', '0', '0');
INSERT INTO `sys_menu_auth` VALUES ('1517309768662822200', '创建权限模块', '1505626962836820724', 'module.create', 'SysAuthApi.createModule', '', '1', '1008888888888888888', '1507099070', '0', '0', '0');
INSERT INTO `sys_menu_auth` VALUES ('1517387802743252348', '删除', '1502388861979864126', 'classes.delete', 'ClassesApi.deleteClasses', '', '1', '1002518130371308851', '1561477828', '0', '0', '0');
INSERT INTO `sys_menu_auth` VALUES ('1517473735500020610', '新增', '1509477538412941603', 'exam.create', 'ExamApi.createExam', '', '1', '1004558574236073817', '1535512358', '0', '0', '0');
INSERT INTO `sys_menu_auth` VALUES ('1517484633641690745', '更改个人基本信息', '1506684717783011305', '/', 'SysUserApi.updatePersonalBaseInfo', '', '1', '1003504241965893326', '1535514242', '0', '0', '0');
INSERT INTO `sys_menu_auth` VALUES ('1517564024797596801', '预览', '1509477538412941603', 'exam.preview', 'ExamQuestionApi.getQuestionsByTypeff', '', '1', '1003866568974705484', '1544591885', '1003866568974705484', '1549554213', '0');
INSERT INTO `sys_menu_auth` VALUES ('1517907402854662478', '新增', '1503907724282130056', 'teachSchedule.create', 'TeachScheduleApi.createTeachSchedule', '', '1', '1002518130371308851', '1561602424', '0', '0', '0');
INSERT INTO `sys_menu_auth` VALUES ('1517985616579197692', '解锁', '1509708127445078684', 'examStudent.unlock', 'ExamApi.unlockStudent', '', '1', '1008604263574962166', '1558522108', '1008604263574962166', '1559638270', '0');
INSERT INTO `sys_menu_auth` VALUES ('1517988448834782456', '结束作业', '1509477538412941603', 'exam.finish', 'ExamApi.finishExam', '', '1', '1008604263574962166', '1558522067', '1002518130371308851', '1561707369', '0');
INSERT INTO `sys_menu_auth` VALUES ('1518010926465092368', '批改客观题', '1507017698831654964', 'examStudent.correct', 'ExamQuestionApi.markSubjective', '', '1', '1002518130371308851', '1561866240', '0', '0', '0');
INSERT INTO `sys_menu_auth` VALUES ('1518290126729327424', '导入题库模板', '1507841542104492241', 'question.importModel', 'question.importModel', '', '1', '1003866568974705484', '1554541830', '1003866568974705484', '1554697155', '0');
INSERT INTO `sys_menu_auth` VALUES ('1518305883499417207', '修改功能权限', '1505626962836820724', '/', 'SysAuthApi.updateSubMenuAuth', '', '1', '1008888888888888888', '1507099336', '0', '0', '0');
INSERT INTO `sys_menu_auth` VALUES ('1518361992951148561', '删除', '1506309144385244910', 'message.delete', 'MessageApi.deleteMessage', '', '1', '1003866568974705484', '1549551461', '0', '0', '0');
INSERT INTO `sys_menu_auth` VALUES ('1518392137991833745', '删除', '1504394852215031919', 'academyInfo.delete', 'AcademyInfoApi.deleteAcademyInfo', '', '1', '1003866568974705484', '1528816469', '1003866568974705484', '1528974577', '0');
INSERT INTO `sys_menu_auth` VALUES ('1518403935957764821', '获取教师课程', '1503907724282130056', 'teacherSchedule.getCourseSelect', 'TeacherScheduleApi.getScheduleByTeacher', '', '1', '1009069242458231540', '1561959463', '0', '0', '0');
INSERT INTO `sys_menu_auth` VALUES ('1518485979224755690', '查看', '1502118213610571763', 'sysOperationRecord.read', '无', '', '1', '1003866568974705484', '1544335811', '0', '0', '0');
INSERT INTO `sys_menu_auth` VALUES ('1518650298633639122', '删除', '1508657911837197184', 'student.delete', 'StudentApi.deleteStudent', '', '1', '1002518130371308851', '1561542319', '0', '0', '0');
INSERT INTO `sys_menu_auth` VALUES ('1518841846097792326', '删除', '1507841542104492241', 'question.deleteQuestionAndOptions', 'QuestionApi.deleteQuestionAndOptions', '', '1', '1004558574236073817', '1535361228', '1004558574236073817', '1535361245', '0');
INSERT INTO `sys_menu_auth` VALUES ('1519306011252205686', '修改权限模块', '1505626962836820724', '/', 'SysAuthApi.updateModule', '', '1', '1008888888888888888', '1507099130', '0', '0', '0');
INSERT INTO `sys_menu_auth` VALUES ('1519667147594322891', '删除', '1508667099921872494', 'dictionary.delete', 'DictionaryApi.deleteDictionary', '', '1', '1007530354790652881', '1533886920', '0', '0', '0');
INSERT INTO `sys_menu_auth` VALUES ('1519786308572044205', '获取学生答题记录', '1506781977159534714', 'examStudent.correct', 'ExamRecordApi.getRecord', '', '1', '1002518130371308851', '1561810779', '0', '0', '0');

-- ----------------------------
-- Table structure for `sys_operation_record`
-- ----------------------------
DROP TABLE IF EXISTS `sys_operation_record`;
CREATE TABLE `sys_operation_record` (
  `id` bigint(20) NOT NULL COMMENT '主键ID',
  `server` smallint(1) DEFAULT NULL COMMENT '服务器: 0-后台,1-PC端 ,2-微信端',
  `control` varchar(255) DEFAULT '' COMMENT '模块',
  `function` varchar(255) DEFAULT '' COMMENT '功能',
  `ipAddr` varchar(255) DEFAULT NULL COMMENT 'IP地址',
  `status` smallint(1) unsigned DEFAULT '0' COMMENT '操作状态: 0失败 1成功',
  `parameter` longtext COMMENT '传入参数',
  `creator` bigint(20) DEFAULT '0' COMMENT '创建人',
  `createTime` int(11) unsigned DEFAULT '0' COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='111 系统操作记录表';

-- ----------------------------
-- Records of sys_operation_record
-- ----------------------------
INSERT INTO `sys_operation_record` VALUES ('2021531394673066840', '0', '系统授权管理', '用户统一登录', '171.36.194.241', '0', null, '1008537303443192206', '1561990330');
INSERT INTO `sys_operation_record` VALUES ('2021537564421770355', '0', '系统授权管理', '用户统一登录', '171.36.194.241', '0', null, '1008538307815030873', '1561990609');
INSERT INTO `sys_operation_record` VALUES ('2022530687276260642', '0', '系统授权管理', '用户统一登录', '171.36.194.241', '0', null, '1002518130371308851', '1561989683');
INSERT INTO `sys_operation_record` VALUES ('2022539767741788444', '0', '系统授权管理', '用户统一登录', '171.36.194.241', '0', null, '1002518130371308851', '1561990657');
INSERT INTO `sys_operation_record` VALUES ('2023538633622582565', '0', '系统授权管理', '用户退出系统', '171.36.194.241', '0', null, '1009069242458231540', '1561989672');
INSERT INTO `sys_operation_record` VALUES ('2023539550254017946', '0', '系统授权管理', '用户统一登录', '171.36.194.241', '0', null, '1002518130371308851', '1561990846');
INSERT INTO `sys_operation_record` VALUES ('2024536725413105824', '0', '系统授权管理', '用户退出系统', '171.36.194.241', '0', null, '1008538307815030873', '1561990646');
INSERT INTO `sys_operation_record` VALUES ('2026534613078210044', '0', '教师信息管理', '修改教师信息', '171.36.194.241', '0', '{\"object\":{\"academyId\":\"3059995609884076194\",\"id\":\"3032537496504559933\",\"name\":\"梁老师\",\"number\":\"20190313\",\"sex\":2},\"token\":\"685f5fa715204ba598d09da610ec0028\"}', '1002518130371308851', '1561990143');
INSERT INTO `sys_operation_record` VALUES ('2027535334699390982', '0', '系统授权管理', '用户退出系统', '171.36.194.241', '0', null, '1002518130371308851', '1561990315');
INSERT INTO `sys_operation_record` VALUES ('2028539823896908471', '0', '角色统一管理', '角色权限配置', '171.36.194.241', '0', '{\"object\":{\"btns\":[{\"authId\":\"1515682946906118637\"},{\"authId\":\"1517484633641690745\"},{\"authId\":\"1512486829107729414\"},{\"authId\":\"1515651703524301786\"},{\"authId\":\"1513652207811494295\"},{\"authId\":\"1518650298633639122\"},{\"authId\":\"1511923168390969898\"},{\"authId\":\"1516927303731718162\"},{\"authId\":\"1516925380164154939\"},{\"authId\":\"1511399583454320967\"},{\"authId\":\"1511399948470918101\"},{\"authId\":\"1518392137991833745\"},{\"authId\":\"1517247624612134200\"},{\"authId\":\"1516247701028082137\"},{\"authId\":\"1513243750874386174\"},{\"authId\":\"1511851446932894720\"},{\"authId\":\"1515388293914856523\"},{\"authId\":\"1515388629940769839\"},{\"authId\":\"1517387802743252348\"},{\"authId\":\"1515841633041392598\"},{\"authId\":\"1518841846097792326\"},{\"authId\":\"1515700561270231947\"},{\"authId\":\"1518290126729327424\"},{\"authId\":\"1512292258929414625\"},{\"authId\":\"1511296728208385603\"},{\"authId\":\"1517473735500020610\"},{\"authId\":\"1511472841079878032\"},{\"authId\":\"1513479955774283785\"},{\"authId\":\"1512190440451160782\"},{\"authId\":\"1514191295470040079\"},{\"authId\":\"1517564024797596801\"},{\"authId\":\"1514148561064635474\"},{\"authId\":\"1517988448834782456\"},{\"authId\":\"1512132996922988578\"},{\"authId\":\"1514138091786165514\"},{\"authId\":\"1514760221276716784\"},{\"authId\":\"1513370219115781197\"},{\"authId\":\"1515378397107670515\"},{\"authId\":\"1513498276260015361\"},{\"authId\":\"1514498869634078560\"},{\"authId\":\"1517141773239623195\"},{\"authId\":\"1517985616579197692\"},{\"authId\":\"1519786308572044205\"},{\"authId\":\"1518010926465092368\"},{\"authId\":\"1516617713430785704\"},{\"authId\":\"1515610859668195684\"},{\"authId\":\"1512610952998061038\"},{\"authId\":\"1517907402854662478\"},{\"authId\":\"1511909480534229558\"},{\"authId\":\"1513901584990123347\"},{\"authId\":\"1518403935957764821\"}],\"menu\":[{\"menuId\":\"1507647620884801891\"},{\"menuId\":\"1508682483856378091\"},{\"menuId\":\"1506684717783011305\"},{\"menuId\":\"1507864990655592991\"},{\"menuId\":\"1508657911837197184\"},{\"menuId\":\"1508928998786471827\"},{\"menuId\":\"1503566409477762965\"},{\"menuId\":\"1504394852215031919\"},{\"menuId\":\"1507246536601955290\"},{\"menuId\":\"1502388861979864126\"},{\"menuId\":\"1503172858973826665\"},{\"menuId\":\"1507841542104492241\"},{\"menuId\":\"1509477538412941603\"},{\"menuId\":\"1509401261013647928\"},{\"menuId\":\"1509708127445078684\"},{\"menuId\":\"1506781977159534714\"},{\"menuId\":\"1507017698831654964\"},{\"menuId\":\"1501370696573288143\"},{\"menuId\":\"1502578895889978168\"},{\"menuId\":\"1501613001821269135\"},{\"menuId\":\"1503907724282130056\"}],\"roleId\":\"1108075601261217576\"}}', '1002518130371308851', '1561990672');
INSERT INTO `sys_operation_record` VALUES ('2029530523950733750', '0', '系统授权管理', '用户退出系统', '171.36.194.241', '0', null, '1008537303443192206', '1561990598');

-- ----------------------------
-- Table structure for `sys_role`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` bigint(20) NOT NULL COMMENT '主键ID',
  `systemId` bigint(20) DEFAULT '0' COMMENT '子系统id sys_subsystem.id',
  `name` varchar(200) DEFAULT '' COMMENT '角色名称',
  `visible` bigint(2) DEFAULT NULL COMMENT '数据可见范围:从数据字典的userRole读取',
  `level` int(6) unsigned DEFAULT '1' COMMENT '预留',
  `isDefault` int(1) DEFAULT '0' COMMENT '默认角色 1默认 ',
  `remark` varchar(500) DEFAULT '' COMMENT '备注信息',
  `creator` bigint(20) DEFAULT '0' COMMENT '创建人',
  `createTime` int(11) unsigned DEFAULT '0' COMMENT '创建时间',
  `reviser` bigint(20) DEFAULT '0' COMMENT '修改人',
  `reviseTime` int(11) unsigned DEFAULT '0' COMMENT '修改时间',
  `isDel` smallint(1) unsigned DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `systemId` (`systemId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='110 角色表';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1101627895069947927', '0', '教师', '3083036257555822812', '1', '0', '管理授课班级的课程考试', '1006859249531205083', '1541506955', '1002518130371308851', '1561901409', '0');
INSERT INTO `sys_role` VALUES ('1102857530959322768', '0', '开发管理员', '3083036257555822812', '1', '0', '最高权限', '1005211393475807387', '1517243487', '1002518130371308851', '1561533213', '0');
INSERT INTO `sys_role` VALUES ('1105163704291618811', '0', '学生', '3083163434674366087', '1', '1', '仅拥有前台用户权限，不允许进入后台管理系统，只能在前台提交并查看作业', '1003866568974705484', '1543065929', '1002518130371308851', '1561533459', '0');
INSERT INTO `sys_role` VALUES ('1108075601261217576', '0', '学校管理员', '3083036257555822812', '1', '0', '管理课程，学生教师的账号等', '1003866568974705484', '1543046592', '1002518130371308851', '1561533295', '0');

-- ----------------------------
-- Table structure for `sys_role_auth`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_auth`;
CREATE TABLE `sys_role_auth` (
  `id` bigint(20) NOT NULL COMMENT '主键ID',
  `roleId` bigint(20) DEFAULT '0' COMMENT '角色Id sys_role.id',
  `authId` bigint(20) DEFAULT '0' COMMENT '权限菜单Id sys_menu_auth.id',
  `creator` bigint(20) DEFAULT '0' COMMENT '创建人',
  `createTime` int(11) unsigned DEFAULT '0' COMMENT '创建时间',
  `reviser` bigint(20) DEFAULT '0' COMMENT '修改人',
  `reviseTime` int(11) unsigned DEFAULT '0' COMMENT '修改时间',
  `isDel` smallint(1) unsigned DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `roleId` (`roleId`) USING BTREE,
  KEY `authId` (`authId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='112 角色菜单权限表 ';

-- ----------------------------
-- Records of sys_role_auth
-- ----------------------------
INSERT INTO `sys_role_auth` VALUES ('1085990654685514638', '1066799877117007990', '1108904089566416182', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1085990658670748411', '1066799877117007990', '1116183263583904248', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1087998651686122026', '1066799877117007990', '1115180610444557500', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1121157825354456812', '1101627895069947927', '1517564024797596801', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1121381851273239964', '1102857530959322768', '1515388629940769839', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1121382371330486468', '1102857530959322768', '1515388293914856523', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1121403033978876048', '1101627895069947927', '1518403935957764821', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1121440755225755938', '1108075601261217576', '1513652207811494295', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1121446750268696974', '1108075601261217576', '1513243750874386174', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1121446751331607539', '1108075601261217576', '1514191295470040079', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1121447753290669491', '1108075601261217576', '1517387802743252348', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1121449754377740883', '1108075601261217576', '1517985616579197692', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1121495905594454123', '1102857530959322768', '1514498869634078560', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1121670920479904677', '1108081433929767622', '1514548501334558405', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1121674927877926895', '1108081433929767622', '1517951148512892552', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1121675920323510125', '1108081433929767622', '1515378397107670515', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1121675921951056323', '1108081433929767622', '1511877616647101609', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1121675928088078755', '1108081433929767622', '1517872672630194420', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1121678929292543604', '1108081433929767622', '1517564024797596801', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1121679922995605763', '1108081433929767622', '1516872011623734889', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1121679926185736049', '1108081433929767622', '1512190024996391042', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1121679927191494699', '1108081433929767622', '1515841633041392598', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1121679928814004334', '1108081433929767622', '1515682946906118637', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1121690288195163527', '1109678436734865319', '1511851446932894720', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1121693927514702379', '1109678436734865319', '1515841633041392598', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1121695729875269881', '1109678436734865319', '1515295774919843969', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1121695923555656415', '1109678436734865319', '1511472841079878032', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1121697286213137049', '1109678436734865319', '1515334844584654170', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1121697814443389474', '1109678436734865319', '1511399583454320967', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1121698067801843002', '1109678436734865319', '1513320940664175807', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1121702798452101929', '1109678436734865319', '1519775048979435048', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1121704790513636871', '1109678436734865319', '1511536182529321586', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1121705610293263092', '1109678436734865319', '1515700561270231947', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1121706791342411324', '1109678436734865319', '1519830440025635168', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1121706791805032847', '1109678436734865319', '1515879300095235801', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1121712425235788630', '1109678436734865319', '1511105116590581166', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1121717422438100228', '1109678436734865319', '1517341126687794026', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1121717426267143766', '1109678436734865319', '1519306011252205686', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1121718156073626677', '1109678436734865319', '1515197321662732864', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1121743439909842616', '1102857530959322768', '1512486829107729414', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1121744438297727587', '1102857530959322768', '1515378397107670515', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1121749435211839505', '1102857530959322768', '1517473735500020610', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1121764051328653846', '1102857530959322768', '1514760221276716784', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1121922419632456508', '1102857530959322768', '1516925380164154939', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1121982703126784404', '1102857530959322768', '1517985616579197692', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1122133141051414247', '1102857530959322768', '1512132996922988578', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1122154822364038720', '1101627895069947927', '1514148561064635474', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1122155827374626635', '1101627895069947927', '1515378397107670515', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1122440752296860808', '1108075601261217576', '1518841846097792326', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1122443753376554572', '1108075601261217576', '1517141773239623195', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1122444751362162961', '1108075601261217576', '1513498276260015361', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1122445757329221927', '1108075601261217576', '1513479955774283785', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1122447757243524153', '1108075601261217576', '1516925380164154939', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1122670921319565161', '1108081433929767622', '1515191938980447384', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1122670927499484585', '1108081433929767622', '1512778401085702226', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1122671921913868932', '1108081433929767622', '1513690509488260468', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1122671929174963838', '1108081433929767622', '1516197739384799087', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1122673929291591645', '1108081433929767622', '1513268620993990135', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1122674920924493892', '1108081433929767622', '1516295352684899716', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1122674924660452048', '1108081433929767622', '1517210778915887680', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1122674927708196782', '1108081433929767622', '1516364081428890481', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1122675920250842918', '1108081433929767622', '1518548521482103787', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1122675965101921215', '1109085039008802338', '1515295774919843969', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1122676962055590562', '1109085039008802338', '1513563142810720128', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1122677920799036814', '1108081433929767622', '1516560932860421177', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1122677925202321963', '1108081433929767622', '1515197321662732864', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1122678920813440999', '1108081433929767622', '1515427516146336557', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1122679920807428427', '1108081433929767622', '1513563142810720128', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1122679924309177567', '1108081433929767622', '1519539587975359848', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1122692935728228883', '1109678436734865319', '1517484633641690745', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1122695815450160686', '1109678436734865319', '1518392137991833745', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1122696923638145887', '1109678436734865319', '1511533656442186067', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1122697816497912423', '1109678436734865319', '1518760317255648955', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1122698068661230668', '1109678436734865319', '1513953502903096000', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1122702791875538327', '1109678436734865319', '1513884504492656572', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1122702797768295112', '1109678436734865319', '1517875932783250311', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1122703440627239848', '1109678436734865319', '1514607640683885230', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1122703798299273985', '1109678436734865319', '1518419551340240348', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1122705447787615405', '1109678436734865319', '1517525825443942800', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1122705792655046903', '1109678436734865319', '1511877616647101609', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1122705795783266637', '1109678436734865319', '1514875264487344520', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1122706795375163068', '1109678436734865319', '1513979970760512701', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1122707444657598971', '1109678436734865319', '1515097064400101189', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1122707795536798694', '1109678436734865319', '1513530384892390519', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1122709791542386604', '1109678436734865319', '1517578215546619240', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1122709796351999242', '1109678436734865319', '1518975108436972615', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1122712422367805056', '1109678436734865319', '1511666918643014325', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1122715422184446983', '1109678436734865319', '1516653651919418377', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1122717424224197729', '1109678436734865319', '1513842179456569649', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1122719151051656153', '1109678436734865319', '1512190024996391042', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1122741436106973901', '1102857530959322768', '1511851446932894720', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1122744434089200682', '1102857530959322768', '1516247701028082137', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1122744437877291183', '1102857530959322768', '1516560932860421177', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1122746432178523427', '1102857530959322768', '1514302579924918575', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1122747434207083859', '1102857530959322768', '1514304953938976647', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1122747437185912030', '1102857530959322768', '1512307645287162841', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1122783422237580375', '1102857530959322768', '1519786308572044205', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1122917532103387326', '1109678436734865319', '1518716744027835283', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1123150823347068204', '1101627895069947927', '1517473735500020610', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1123159823381013249', '1101627895069947927', '1519786308572044205', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1123295247540025246', '1102857530959322768', '1511296728208385603', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1123440752354587050', '1108075601261217576', '1514138091786165514', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1123443750293058103', '1108075601261217576', '1515700561270231947', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1123444757236146545', '1108075601261217576', '1518650298633639122', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1123444757397329790', '1108075601261217576', '1515610859668195684', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1123446751299663493', '1108075601261217576', '1515841633041392598', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1123670812547758175', '1101627895069947927', '1517484633641690745', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1123670928054710629', '1108081433929767622', '1514875264487344520', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1123670928218851820', '1108081433929767622', '1519846059977845126', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1123671920026168198', '1108081433929767622', '1519870214990634933', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1123672924266422823', '1108081433929767622', '1519257922842628365', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1123672925821589249', '1108081433929767622', '1517484633641690745', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1123672925861771649', '1108081433929767622', '1517578215546619240', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1123673921521038012', '1108081433929767622', '1513523991544232197', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1123674965079565085', '1109085039008802338', '1517484633641690745', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1123675967008266926', '1109085039008802338', '1512366497721742954', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1123676920349878240', '1108081433929767622', '1511416738900128648', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1123676926068474275', '1108081433929767622', '1517247624612134200', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1123676927252608569', '1108081433929767622', '1513479955774283785', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1123676963030823356', '1109085039008802338', '1516560932860421177', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1123677929131218096', '1108081433929767622', '1518567176878041346', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1123678817535368566', '1101627895069947927', '1515682946906118637', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1123679926151934260', '1108081433929767622', '1514336740633120916', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1123682022772057215', '1109085039008802338', '1513690509488260468', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1123683817971025184', '1108075601261217576', '1515682946906118637', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1123684022752890390', '1109085039008802338', '1519950658280046649', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1123692818486326513', '1109678436734865319', '1513762124283157540', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1123695283185383312', '1109678436734865319', '1516247701028082137', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1123696062817428914', '1109678436734865319', '1516329028428057643', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1123697064719958928', '1109678436734865319', '1517951148512892552', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1123702794468681835', '1109678436734865319', '1518052144080761449', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1123702795841178194', '1109678436734865319', '1517884299742415449', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1123702799817629763', '1109678436734865319', '1516875581496520823', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1123703794351244321', '1109678436734865319', '1517210778915887680', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1123704791831398973', '1109678436734865319', '1517872672630194420', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1123704799426745793', '1109678436734865319', '1519754958193390283', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1123706795567155826', '1109678436734865319', '1518577546404533650', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1123707799737922983', '1109678436734865319', '1514873154268236689', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1123711150015901410', '1109678436734865319', '1516194294927259890', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1123711425387581271', '1109678436734865319', '1519667147594322891', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1123715428293502885', '1109678436734865319', '1517301371484882061', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1123716425357416447', '1109678436734865319', '1516665636010326987', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1123740432224851078', '1102857530959322768', '1511666918643014325', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1123741435205692242', '1102857530959322768', '1518305883499417207', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1123742431892079406', '1102857530959322768', '1515682946906118637', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1123744436067479528', '1102857530959322768', '1516653651919418377', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1123746435246589242', '1102857530959322768', '1512190440451160782', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1123803489925844923', '1102857530959322768', '1515805409184344918', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1123805483936043222', '1102857530959322768', '1516802681466519405', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1123806485922844922', '1102857530959322768', '1515809050043965658', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1123908629179134602', '1102857530959322768', '1517907402854662478', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1123956422081519080', '1102857530959322768', '1515952368661007614', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1124156820330871897', '1101627895069947927', '1512292258929414625', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1124440756344191443', '1108075601261217576', '1517988448834782456', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1124443758401102009', '1108075601261217576', '1518403935957764821', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1124444754407914707', '1108075601261217576', '1513901584990123347', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1124444755343994144', '1108075601261217576', '1514148561064635474', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1124445752347386752', '1108075601261217576', '1512132996922988578', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1124670926060859886', '1108081433929767622', '1516247701028082137', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1124670927002751017', '1108081433929767622', '1513297118927710113', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1124671927987219156', '1108081433929767622', '1512857577191186987', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1124672921273810433', '1108081433929767622', '1518266195816684322', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1124673920426372726', '1108081433929767622', '1513979970760512701', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1124673920853192724', '1108081433929767622', '1513530384892390519', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1124673920971638242', '1108081433929767622', '1516878564860229424', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1124674925374844765', '1108081433929767622', '1518834176321931015', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1124674927470515066', '1108081433929767622', '1515097064400101189', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1124674929555594445', '1108081433929767622', '1512795547244890442', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1124675928169526177', '1108081433929767622', '1515334844584654170', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1124675929629508008', '1108081433929767622', '1511235774966542562', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1124676921933648157', '1108081433929767622', '1519325512662888374', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1124676922901085727', '1108081433929767622', '1512693315348807596', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1124676924364467166', '1108081433929767622', '1515413905108911320', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1124676927571173364', '1108081433929767622', '1517525825443942800', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1124679926778453904', '1108081433929767622', '1518361992951148561', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1124679926779061293', '1108081433929767622', '1512368785119214681', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1124680027793643120', '1109085039008802338', '1515489042124438088', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1124680230626305091', '1109678436734865319', '1512366497721742954', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1124681025809226040', '1109085039008802338', '1515074690360054207', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1124682025782441821', '1109085039008802338', '1519325512662888374', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1124683025808411345', '1109085039008802338', '1512495679860330860', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1124683026766477309', '1109085039008802338', '1511693026700148877', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1124684817970213482', '1108075601261217576', '1517484633641690745', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1124687021743696083', '1109085039008802338', '1513953502903096000', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1124690727860689960', '1109678436734865319', '1516292845542844364', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1124693065681205186', '1109678436734865319', '1519950658280046649', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1124693935690862753', '1109678436734865319', '1513563142810720128', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1124701441847331658', '1109678436734865319', '1511235774966542562', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1124701444827362139', '1109678436734865319', '1511214913454380441', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1124701794604901562', '1109678436734865319', '1516295352684899716', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1124703790526218788', '1109678436734865319', '1512531304488758569', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1124706440801396612', '1109678436734865319', '1518213078025546000', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1124706449862113879', '1109678436734865319', '1512234552336945008', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1124707798635268689', '1109678436734865319', '1513885491801902817', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1124711424396979881', '1109678436734865319', '1511492688487162102', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1124712157208657092', '1109678436734865319', '1518266195816684322', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1124713421343023846', '1109678436734865319', '1514304953938976647', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1124713423191831595', '1109678436734865319', '1514654741822001627', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1124713426409749095', '1109678436734865319', '1511345980225181026', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1124714157227424313', '1109678436734865319', '1513268620993990135', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1124716428288119284', '1109678436734865319', '1516301309324957365', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1124719151988353986', '1109678436734865319', '1518567176878041346', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1124740438072429471', '1102857530959322768', '1517247624612134200', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1124744433160133819', '1102857530959322768', '1514301494033370322', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1124744436132580386', '1102857530959322768', '1519306011252205686', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1124746437196302644', '1102857530959322768', '1515302786258432919', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1124749439079053430', '1102857530959322768', '1514654741822001627', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1124909623185525204', '1102857530959322768', '1513901584990123347', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1124957422060745861', '1102857530959322768', '1513950903000646329', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1125155825373422324', '1101627895069947927', '1514138091786165514', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1125156827337678594', '1101627895069947927', '1518841846097792326', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1125446757315639017', '1108075601261217576', '1511296728208385603', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1125447753336809842', '1108075601261217576', '1517564024797596801', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1125448756301446717', '1108075601261217576', '1512292258929414625', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1125449750399325793', '1108075601261217576', '1516617713430785704', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1125612229912518688', '1102857530959322768', '1512610952998061038', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1125670922457540546', '1108081433929767622', '1514607640683885230', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1125670924039114145', '1108081433929767622', '1511399948470918101', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1125670925106601611', '1108081433929767622', '1514761582183753120', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1125670926930070802', '1108081433929767622', '1518014015290949961', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1125670927127576138', '1108081433929767622', '1513762124283157540', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1125670927170917781', '1108081433929767622', '1514333959140730371', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1125671923011524232', '1108081433929767622', '1511399583454320967', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1125672928501878192', '1108081433929767622', '1517770978884142953', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1125672929885506801', '1108081433929767622', '1511693026700148877', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1125673924316129519', '1108081433929767622', '1511533656442186067', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1125674810553337088', '1101627895069947927', '1512486829107729414', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1125674924403205898', '1108081433929767622', '1518975108436972615', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1125674925153188616', '1108081433929767622', '1516194294927259890', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1125675961118516121', '1109085039008802338', '1513297118927710113', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1125676921431760329', '1108081433929767622', '1516891002397440042', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1125677929768918042', '1108081433929767622', '1514762169986162391', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1125678920808861080', '1108081433929767622', '1517413275957919123', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1125679921588563965', '1108081433929767622', '1518213078025546000', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1125688815981402796', '1108075601261217576', '1512486829107729414', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1125691920608985066', '1109678436734865319', '1516852580506200413', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1125693063795259086', '1109678436734865319', '1516323672557434840', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1125697928625564976', '1109678436734865319', '1517564024797596801', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1125700447695530017', '1109678436734865319', '1517770978884142953', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1125702445686950107', '1109678436734865319', '1512778401085702226', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1125710156297738480', '1109678436734865319', '1519590257215497316', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1125712150034680638', '1109678436734865319', '1516197739384799087', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1125714423248364543', '1109678436734865319', '1517309768662822200', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1125716420304890495', '1109678436734865319', '1514301494033370322', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1125740437159753203', '1102857530959322768', '1517301371484882061', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1125742430152365609', '1102857530959322768', '1516301309324957365', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1125742437195061285', '1102857530959322768', '1518841846097792326', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1125743436100220259', '1102857530959322768', '1511105116590581166', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1125745432259976847', '1102857530959322768', '1514191295470040079', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1125745433055454954', '1102857530959322768', '1511399583454320967', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1125745434097597294', '1102857530959322768', '1513243750874386174', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1125924419635458504', '1102857530959322768', '1516927303731718162', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1126015738490444497', '1101019589492230553', '1514498869634078560', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1126130143053419248', '1102857530959322768', '1514138091786165514', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1126151826385010242', '1101627895069947927', '1518010926465092368', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1126152827338875890', '1101627895069947927', '1518290126729327424', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1126153825375814935', '1101627895069947927', '1514498869634078560', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1126158822366234024', '1101627895069947927', '1512132996922988578', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1126158825381016245', '1101627895069947927', '1517985616579197692', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1126159820337874893', '1101627895069947927', '1515700561270231947', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1126441752311834323', '1108075601261217576', '1517473735500020610', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1126442752242335845', '1108075601261217576', '1516927303731718162', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1126443751393716409', '1108075601261217576', '1511909480534229558', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1126445758366965661', '1108075601261217576', '1515378397107670515', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1126446752387134487', '1108075601261217576', '1519786308572044205', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1126448750225751935', '1108075601261217576', '1515651703524301786', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1126611226913510689', '1102857530959322768', '1515610859668195684', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1126612227915314373', '1102857530959322768', '1516617713430785704', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1126653736313568781', '1102857530959322768', '1515651703524301786', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1126655456293893522', '1102857530959322768', '1513652207811494295', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1126670928015774586', '1108081433929767622', '1514873154268236689', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1126671924850948370', '1108081433929767622', '1519950658280046649', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1126672926967194581', '1108081433929767622', '1516329028428057643', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1126673923641676837', '1108081433929767622', '1512234075483267189', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1126673924775692253', '1108081433929767622', '1519775048979435048', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1126673927386628981', '1108081433929767622', '1519830440025635168', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1126675923144559651', '1108081433929767622', '1518760317255648955', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1126675928837979856', '1108081433929767622', '1512486829107729414', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1126676920558200842', '1108081433929767622', '1512741992334030771', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1126676927684616872', '1108081433929767622', '1515354866330669391', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1126677921596343184', '1108081433929767622', '1511214913454380441', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1126677923606723798', '1108081433929767622', '1515074690360054207', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1126678927148602707', '1108081433929767622', '1511196852431320154', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1126692811469553297', '1109678436734865319', '1514761582183753120', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1126695281219743449', '1109678436734865319', '1514336740633120916', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1126695936687478140', '1109678436734865319', '1516560932860421177', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1126696813478935900', '1109678436734865319', '1518761920748266402', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1126698286237722960', '1109678436734865319', '1514333959140730371', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1126698923527092989', '1109678436734865319', '1518841846097792326', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1126698924544060504', '1109678436734865319', '1517473735500020610', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1126703794386540670', '1109678436734865319', '1516891002397440042', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1126704446709116928', '1109678436734865319', '1515489042124438088', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1126708445755057960', '1109678436734865319', '1512741992334030771', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1126708790480467041', '1109678436734865319', '1517413275957919123', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1126708795685597339', '1109678436734865319', '1512857577191186987', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1126708798373417147', '1109678436734865319', '1515354866330669391', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1126709445631810762', '1109678436734865319', '1512600078217270089', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1126712423396350481', '1109678436734865319', '1516538073156634276', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1126748430098831640', '1102857530959322768', '1513842179456569649', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1126749435276750066', '1102857530959322768', '1517564024797596801', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1126914530158334368', '1109678436734865319', '1512714257464857841', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1126924412639257207', '1102857530959322768', '1511923168390969898', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1127150822365031729', '1101627895069947927', '1517988448834782456', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1127151826334677592', '1101627895069947927', '1515841633041392598', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1127154825343268509', '1101627895069947927', '1513479955774283785', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1127159820346260503', '1101627895069947927', '1511472841079878032', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1127385852272233969', '1102857530959322768', '1517387802743252348', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1127443750392525092', '1108075601261217576', '1512610952998061038', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1127445756398524097', '1108075601261217576', '1517907402854662478', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1127446752309245403', '1108075601261217576', '1518290126729327424', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1127446759373354271', '1108075601261217576', '1514498869634078560', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1127448752351779360', '1108075601261217576', '1514760221276716784', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1127448758394320792', '1108075601261217576', '1518010926465092368', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1127449759266302365', '1108075601261217576', '1516247701028082137', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1127449759368969665', '1108075601261217576', '1513370219115781197', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1127670922093022705', '1108081433929767622', '1511851446932894720', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1127670923842561764', '1108081433929767622', '1513953502903096000', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1127671922910919980', '1108081433929767622', '1515578445201500163', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1127671927974586190', '1108081433929767622', '1516292845542844364', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1127674921049696052', '1108081433929767622', '1518392137991833745', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1127674927223488786', '1108081433929767622', '1517548377244152094', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1127675923018138623', '1108081433929767622', '1515294323830970769', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1127675928848619811', '1108081433929767622', '1512531304488758569', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1127675967017840837', '1109085039008802338', '1512368785119214681', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1127676818518390048', '1101627895069947927', '1516560932860421177', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1127676921790277162', '1108081433929767622', '1518052144080761449', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1127676925712783698', '1108081433929767622', '1516363218349826895', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1127676925749143829', '1108081433929767622', '1519754958193390283', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1127676927512453109', '1108081433929767622', '1515489042124438088', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1127676927894338078', '1108081433929767622', '1511573138816617550', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1127676928418981111', '1108081433929767622', '1513975834666249830', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1127679925959614674', '1108081433929767622', '1513320940664175807', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1127679929944020760', '1108081433929767622', '1516323672557434840', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1127684026757087690', '1109085039008802338', '1518953878278400125', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1127687026795839438', '1109085039008802338', '1512795547244890442', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1127690938739611485', '1109678436734865319', '1512486829107729414', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1127691725895842794', '1109678436734865319', '1513297118927710113', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1127691929582016545', '1109678436734865319', '1512190440451160782', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1127694921651925106', '1109678436734865319', '1515378397107670515', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1127696063758505357', '1109678436734865319', '1512693315348807596', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1127698063767094261', '1109678436734865319', '1513690509488260468', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1127700790493044963', '1109678436734865319', '1515427516146336557', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1127701444730286756', '1109678436734865319', '1519741624350848973', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1127703445878691783', '1109678436734865319', '1512234075483267189', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1127703446666176883', '1109678436734865319', '1514548501334558405', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1127704796611480478', '1109678436734865319', '1518014015290949961', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1127709790338026727', '1109678436734865319', '1518834176321931015', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1127709795364831233', '1109678436734865319', '1512495679860330860', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1127711424213610805', '1109678436734865319', '1516653844923331523', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1127712154148739537', '1109678436734865319', '1517545432419309693', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1127714426344634232', '1109678436734865319', '1518305883499417207', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1127716156242409837', '1109678436734865319', '1519539587975359848', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1127719150091607194', '1109678436734865319', '1517197557364528486', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1127719427448493830', '1109678436734865319', '1518485979224755690', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1127740438126191770', '1102857530959322768', '1513308915669521848', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1127741433148973994', '1102857530959322768', '1516302231202139435', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1127742437052841560', '1102857530959322768', '1511399948470918101', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1127744436112619861', '1102857530959322768', '1517309768662822200', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1127745431223616722', '1102857530959322768', '1511472841079878032', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1127745433887683793', '1102857530959322768', '1513563142810720128', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1127919539130553155', '1109678436734865319', '1513716124935043754', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1128156823376816939', '1101627895069947927', '1517141773239623195', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1128158828340267505', '1101627895069947927', '1512190440451160782', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1128159821378625637', '1101627895069947927', '1514760221276716784', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1128294243538443334', '1102857530959322768', '1512292258929414625', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1128445756243339843', '1108075601261217576', '1511923168390969898', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1128650458290892526', '1102857530959322768', '1518650298633639122', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1128670923243018648', '1108081433929767622', '1511472841079878032', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1128670927463124450', '1108081433929767622', '1512600078217270089', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1128671923866535284', '1108081433929767622', '1518953878278400125', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1128672922263990165', '1108081433929767622', '1512190440451160782', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1128673961081151003', '1109085039008802338', '1512486829107729414', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1128675924233061697', '1108081433929767622', '1517545432419309693', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1128675924764670682', '1108081433929767622', '1512366497721742954', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1128676925112196523', '1108081433929767622', '1518761920748266402', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1128677923040136713', '1108081433929767622', '1517875932783250311', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1128677926283963681', '1108081433929767622', '1516852580506200413', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1128678929200703566', '1108081433929767622', '1517197557364528486', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1128678967037433749', '1109085039008802338', '1518361992951148561', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1128679929217467213', '1108081433929767622', '1518841846097792326', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1128685027778863919', '1109085039008802338', '1512693315348807596', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1128690062691980402', '1109678436734865319', '1518953878278400125', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1128690926607590457', '1109678436734865319', '1514191295470040079', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1128693937719441661', '1109678436734865319', '1515682946906118637', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1128695728907432706', '1109678436734865319', '1515294323830970769', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1128699289170009708', '1109678436734865319', '1517247624612134200', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1128700797690189246', '1109678436734865319', '1516872011623734889', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1128701795406577961', '1109678436734865319', '1516363218349826895', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1128703445834945049', '1109678436734865319', '1515074690360054207', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1128705442768833181', '1109678436734865319', '1512795547244890442', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1128705792866958405', '1109678436734865319', '1512889417900931509', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1128706795361574154', '1109678436734865319', '1513975834666249830', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1128707791750700208', '1109678436734865319', '1519870214990634933', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1128709794302851896', '1109678436734865319', '1511416738900128648', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1128709798322432804', '1109678436734865319', '1515413905108911320', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1128710424426716610', '1109678436734865319', '1516531181082881845', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1128713158187680571', '1109678436734865319', '1519257922842628365', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1128713427272725679', '1109678436734865319', '1516302231202139435', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1128714422319665711', '1109678436734865319', '1512307645287162841', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1128714424339257625', '1109678436734865319', '1515302786258432919', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1128716424252758156', '1109678436734865319', '1513308915669521848', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1128717151279955261', '1109678436734865319', '1515191938980447384', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1128718156996132205', '1109678436734865319', '1511196852431320154', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1128718426317275109', '1109678436734865319', '1514302579924918575', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1128718429412134704', '1109678436734865319', '1513664084018511885', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1128743432184679688', '1102857530959322768', '1515841633041392598', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1128743435216479462', '1102857530959322768', '1516665636010326987', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1128744433908452001', '1102857530959322768', '1517484633641690745', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1128744439234006329', '1102857530959322768', '1513479955774283785', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1128908622184338907', '1102857530959322768', '1511909480534229558', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1128950423079126470', '1102857530959322768', '1511957177213614901', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1129018036286685663', '1102857530959322768', '1518010926465092368', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1129025707869019397', '1102011144780302808', '1514498869634078560', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1129148018878184234', '1102857530959322768', '1517141773239623195', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1129151828379811939', '1101627895069947927', '1513498276260015361', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1129155821334871893', '1101627895069947927', '1511296728208385603', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1129155826357454807', '1101627895069947927', '1514191295470040079', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1129294242501474817', '1102857530959322768', '1518290126729327424', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1129316895913218083', '1102857530959322768', '1515700561270231947', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1129372329070163154', '1101627895069947927', '1513370219115781197', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1129441754271884280', '1108075601261217576', '1511851446932894720', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1129441758271085588', '1108075601261217576', '1515388293914856523', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1129443756324027627', '1108075601261217576', '1511472841079878032', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1129444754240525159', '1108075601261217576', '1511399583454320967', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1129446750254919765', '1108075601261217576', '1518392137991833745', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1129446754321416238', '1108075601261217576', '1512190440451160782', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1129447757241721451', '1108075601261217576', '1511399948470918101', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1129447758256111069', '1108075601261217576', '1517247624612134200', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1129448759281275885', '1108075601261217576', '1515388629940769839', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1129498900596455121', '1102857530959322768', '1513498276260015361', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1129670926675836656', '1108081433929767622', '1512495679860330860', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1129671924720360603', '1108081433929767622', '1515757455370911795', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1129671926079684149', '1108081433929767622', '1516875581496520823', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1129672920080444799', '1108081433929767622', '1513243750874386174', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1129672924873356558', '1108081433929767622', '1518577546404533650', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1129672963096542609', '1109085039008802338', '1516292845542844364', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1129672966129904738', '1109085039008802338', '1515294323830970769', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1129673921637084925', '1108081433929767622', '1512234552336945008', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1129673927105049275', '1108081433929767622', '1512889417900931509', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1129673962069985173', '1109085039008802338', '1515682946906118637', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1129674924235430732', '1108081433929767622', '1517473735500020610', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1129674926948661720', '1108081433929767622', '1513885491801902817', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1129675921128621189', '1108081433929767622', '1513884504492656572', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1129675926270573089', '1108081433929767622', '1514191295470040079', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1129676925009185679', '1108081433929767622', '1514873102352762727', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1129676925822834600', '1108081433929767622', '1511536182529321586', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1129677920533620920', '1108081433929767622', '1519741624350848973', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1129677921068104232', '1108081433929767622', '1515879300095235801', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1129677921327156081', '1108081433929767622', '1519590257215497316', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1129677926102650660', '1108081433929767622', '1517884299742415449', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1129678927986163104', '1108081433929767622', '1515295774919843969', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1129679921335102031', '1108081433929767622', '1518419551340240348', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1129681021803223042', '1109085039008802338', '1512234075483267189', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1129686235646276615', '1109678436734865319', '1518361992951148561', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1129687020763282002', '1109085039008802338', '1517951148512892552', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1129687237638889003', '1109678436734865319', '1512368785119214681', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1129692065779673178', '1109678436734865319', '1519325512662888374', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1129695065744920444', '1109678436734865319', '1511693026700148877', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1129696282185770921', '1109678436734865319', '1513243750874386174', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1129696924572427632', '1109678436734865319', '1513479955774283785', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1129699818448770084', '1109678436734865319', '1511399948470918101', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1129704799386805751', '1109678436734865319', '1516364081428890481', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1129705443728696849', '1109678436734865319', '1513523991544232197', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1129705792440526001', '1109678436734865319', '1514762169986162391', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1129706794577744730', '1109678436734865319', '1511573138816617550', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1129708791673010420', '1109678436734865319', '1516878564860229424', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1129708797597320640', '1109678436734865319', '1515578445201500163', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1129708798714954464', '1109678436734865319', '1514873102352762727', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1129709798417968570', '1109678436734865319', '1515757455370911795', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1129715153151513756', '1109678436734865319', '1518548521482103787', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1129717156117763013', '1109678436734865319', '1517548377244152094', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1129740431292161245', '1102857530959322768', '1518485979224755690', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1129743430233242681', '1102857530959322768', '1519667147594322891', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1129743431061236164', '1102857530959322768', '1518392137991833745', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_auth` VALUES ('1129746433088445048', '1102857530959322768', '1516653844923331523', '0', '0', '0', '0', '0');

-- ----------------------------
-- Table structure for `sys_role_menu`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `id` bigint(20) NOT NULL COMMENT '主键ID',
  `roleId` bigint(20) DEFAULT '0' COMMENT '角色Id sys_role.id',
  `menuId` bigint(20) DEFAULT '0' COMMENT '权限菜单Id sys_menu.id',
  `creator` bigint(20) DEFAULT '0' COMMENT '创建人',
  `createTime` int(11) unsigned DEFAULT '0' COMMENT '创建时间',
  `reviser` bigint(20) DEFAULT '0' COMMENT '修改人',
  `reviseTime` int(11) unsigned DEFAULT '0' COMMENT '修改时间',
  `isDel` smallint(1) unsigned DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `roleId` (`roleId`) USING BTREE,
  KEY `menuId` (`menuId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='111 角色菜单表';

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES ('1111029707674087403', '1102011144780302808', '1503172858973826665', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1111405036947701210', '1101627895069947927', '1503907724282130056', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1111443750174423283', '1108075601261217576', '1501613001821269135', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1111445758152256462', '1108075601261217576', '1503172858973826665', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1111525996465870847', '1101627895069947927', '1507647620884801891', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1111670924017895270', '1108081433929767622', '1503172858973826665', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1111670926030866792', '1108081433929767622', '1509477538412941603', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1111671923924816880', '1108081433929767622', '1508663827218802839', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1111671964565661691', '1109085039008802338', '1508682483856378091', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1111674818073998121', '1101627895069947927', '1504561318050689468', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1111674925153696918', '1108081433929767622', '1501674718353314968', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1111674968592026823', '1109085039008802338', '1506309144385244910', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1111675920156305300', '1108081433929767622', '1508238825523965639', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1111679925002507663', '1108081433929767622', '1503855821191961307', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1111683022280120346', '1109085039008802338', '1507135257693038084', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1111687029270741737', '1109085039008802338', '1508238825523965639', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1111692928212713487', '1109678436734865319', '1503172858973826665', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1111695923246076611', '1109678436734865319', '1509401261013647928', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1111696935374909344', '1109678436734865319', '1504561318050689468', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1111701792080810860', '1109678436734865319', '1505577121588346035', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1111706447352798395', '1109678436734865319', '1507210837224495582', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1111706792028647226', '1109678436734865319', '1504416329645237833', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1111782427203424552', '1102857530959322768', '1506781977159534714', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1112153821274762030', '1101627895069947927', '1507841542104492241', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1112157825276950338', '1101627895069947927', '1509708127445078684', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1112178814173849969', '1102857530959322768', '1509271907441429456', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1112188053426436010', '1102857530959322768', '1503566409477762965', '0', '1549986826', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1112377938977012239', '1101627895069947927', '1501370696573288143', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1112441752170220988', '1108075601261217576', '1501370696573288143', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1112442754167032680', '1108075601261217576', '1507017698831654964', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1112445758141868858', '1108075601261217576', '1504394852215031919', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1112446755169835374', '1108075601261217576', '1506781977159534714', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1112447757130671553', '1108075601261217576', '1503566409477762965', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1112449751158251465', '1108075601261217576', '1502388861979864126', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1112529105054568036', '1108075601261217576', '1507647620884801891', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1112670925955362328', '1108081433929767622', '1509291772769250000', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1112672966642367472', '1109085039008802338', '1503566409477762965', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1112674923091781353', '1108081433929767622', '1506772393205714217', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1112676944510449782', '1109678436734865319', '1502305695171419173', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1112676961601800043', '1109085039008802338', '1504561318050689468', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1112676968574250609', '1109085039008802338', '1502305695171419173', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1112677924120944175', '1108081433929767622', '1501777354157553407', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1112678927187863733', '1108081433929767622', '1504132983994401569', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1112681813467701705', '1108075601261217576', '1506684717783011305', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1112700258404512485', '1102857530959322768', '1509708127445078684', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1112703447286686526', '1109678436734865319', '1506772393205714217', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1112711426820545536', '1109678436734865319', '1509496582324433939', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1112713157624646847', '1109678436734865319', '1504257822033784938', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1112715420864480578', '1109678436734865319', '1504271716499778177', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1112717424802766328', '1109678436734865319', '1505626962836820724', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1112914536806399442', '1109678436734865319', '1501718392153528473', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1113016739326670337', '1101019589492230553', '1509708127445078684', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1113151826272951335', '1101627895069947927', '1507017698831654964', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1113171817167640665', '1102857530959322768', '1508639258116985267', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1113184057882739818', '1102857530959322768', '1507841542104492241', '0', '1549986827', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1113185040276323406', '1102857530959322768', '1504561318050689468', '0', '1549986825', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1113185052329829769', '1102857530959322768', '1504394852215031919', '0', '1549986826', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1113186042292601921', '1102857530959322768', '1507647620884801891', '0', '1549986825', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1113405035948700214', '1101627895069947927', '1502578895889978168', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1113440759139472249', '1108075601261217576', '1508928998786471827', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1113444759130288940', '1108075601261217576', '1507864990655592991', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1113526108068144940', '1108075601261217576', '1508682483856378091', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1113671922930206490', '1108081433929767622', '1507325774436437317', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1113671925875674546', '1108081433929767622', '1506309144385244910', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1113674921838924809', '1108081433929767622', '1507647620884801891', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1113676920140914699', '1108081433929767622', '1507210837224495582', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1113676923231190384', '1108081433929767622', '1501677675234777611', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1113676923990927754', '1108081433929767622', '1501769636463764371', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1113679929225803772', '1108081433929767622', '1507858360286736399', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1113679965611382950', '1109085039008802338', '1506684717783011305', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1113682021275938041', '1109085039008802338', '1501674718353314968', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1113689020258969529', '1109085039008802338', '1501777354157553407', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1113692924239690007', '1109678436734865319', '1509477538412941603', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1113694818174149328', '1109678436734865319', '1501769636463764371', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1113698921224308409', '1109678436734865319', '1507841542104492241', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1113702441313856359', '1109678436734865319', '1501777354157553407', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1113715427877261794', '1109678436734865319', '1509271907441429456', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1113717427799374710', '1109678436734865319', '1507166151911181043', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1113718420829938140', '1109678436734865319', '1506666691476018583', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1114153823267564721', '1101627895069947927', '1503172858973826665', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1114172815162647669', '1102857530959322768', '1504271716499778177', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1114182055784190206', '1102857530959322768', '1507246536601955290', '0', '1549986826', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1114184052008564962', '1102857530959322768', '1507864990655592991', '0', '1549986826', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1114441757130473248', '1108075601261217576', '1508657911837197184', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1114441758153445762', '1108075601261217576', '1507841542104492241', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1114673923196448646', '1108081433929767622', '1502672490870013479', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1114673967654942387', '1109085039008802338', '1509291772769250000', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1114674928024281880', '1108081433929767622', '1507841542104492241', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1114675929219224864', '1108081433929767622', '1505577121588346035', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1114676922865095638', '1108081433929767622', '1502305695171419173', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1114676928279942123', '1108081433929767622', '1503194355914676408', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1114676960540083789', '1109085039008802338', '1507647620884801891', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1114677920963755923', '1108081433929767622', '1504394852215031919', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1114693284914954855', '1109678436734865319', '1507246536601955290', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1114694064331077957', '1109678436734865319', '1508663827218802839', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1114702797038037832', '1109678436734865319', '1509978784312524482', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1114707792109591087', '1109678436734865319', '1507858360286736399', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1114708444348010174', '1109678436734865319', '1503741714553392796', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1114710150589891100', '1109678436734865319', '1501677675234777611', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1115010731285921601', '1101019589492230553', '1507647620884801891', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1115154820272760032', '1101627895069947927', '1509477538412941603', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1115184054868817340', '1102857530959322768', '1509401261013647928', '0', '1549986827', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1115188065838994925', '1102857530959322768', '1502118213610571763', '0', '1549986830', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1115671922084395748', '1108081433929767622', '1509978784312524482', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1115673920073616526', '1108081433929767622', '1504416329645237833', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1115679926947974715', '1108081433929767622', '1503566409477762965', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1115679928841311415', '1108081433929767622', '1508682483856378091', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1115702443365379307', '1109678436734865319', '1508238825523965639', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1115702799051458739', '1109678436734865319', '1504132983994401569', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1116185045833674135', '1102857530959322768', '1508682483856378091', '0', '1549986825', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1116189068427518474', '1102857530959322768', '1508667099921872494', '0', '1549986830', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1116442753147064164', '1108075601261217576', '1507246536601955290', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1116673926101363261', '1108081433929767622', '1506128586679133699', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1116674813082388734', '1101627895069947927', '1506684717783011305', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1116677949493287963', '1109678436734865319', '1508682483856378091', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1116688028269150829', '1109085039008802338', '1503741714553392796', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1116690285925342468', '1109678436734865319', '1503855821191961307', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1116694061343663864', '1109678436734865319', '1507325774436437317', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1116706796021281912', '1109678436734865319', '1501674718353314968', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1116707797012256617', '1109678436734865319', '1506378724784478828', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1116709442306262442', '1109678436734865319', '1506128586679133699', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1116709793068847348', '1109678436734865319', '1502672490870013479', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1116719152642618364', '1109678436734865319', '1503194355914676408', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1117010739309694815', '1101019589492230553', '1503172858973826665', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1117017034261517840', '1102857530959322768', '1507017698831654964', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1117023704649911581', '1102011144780302808', '1507647620884801891', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1117176814170846961', '1102857530959322768', '1501584336747852962', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1117181063450620242', '1102857530959322768', '1507166151911181043', '0', '1549986830', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1117185061412445859', '1102857530959322768', '1508626008299626692', '0', '1549986829', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1117187040522223006', '1102857530959322768', '1506684717783011305', '0', '1549986826', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1117442753169646077', '1108075601261217576', '1509477538412941603', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1117445755163834373', '1108075601261217576', '1509708127445078684', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1117610110832667751', '1102857530959322768', '1501613001821269135', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1117610116830476446', '1102857530959322768', '1502578895889978168', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1117674925047442702', '1108081433929767622', '1509401261013647928', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1117675926055034610', '1108081433929767622', '1506378724784478828', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1117680231294951085', '1109678436734865319', '1506309144385244910', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1117697727602441814', '1109678436734865319', '1509291772769250000', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1117698814164368102', '1109678436734865319', '1504394852215031919', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1117701790043868820', '1109678436734865319', '1507135257693038084', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1117711420898043005', '1109678436734865319', '1501584336747852962', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1117712424776218885', '1109678436734865319', '1508626008299626692', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1117713422879874186', '1109678436734865319', '1509334110809051917', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1117713424849901661', '1109678436734865319', '1508639258116985267', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1117717155602868626', '1109678436734865319', '1503561800195527715', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1117718423832329754', '1109678436734865319', '1502118213610571763', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1118025704693050928', '1102011144780302808', '1509708127445078684', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1118154825279762031', '1101627895069947927', '1509401261013647928', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1118181066986860203', '1102857530959322768', '1508622161153824054', '0', '1549986830', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1118185067931971209', '1102857530959322768', '1505626962836820724', '0', '1549986830', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1118189058396247605', '1102857530959322768', '1509477538412941603', '0', '1549986827', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1118387914107707826', '1102857530959322768', '1502388861979864126', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1118443752172229987', '1108075601261217576', '1502578895889978168', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1118447757187613595', '1108075601261217576', '1503907724282130056', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1118674929912232972', '1108081433929767622', '1507864990655592991', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1118675925129336787', '1108081433929767622', '1503741714553392796', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1118679923887061158', '1108081433929767622', '1504561318050689468', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1118688020243380604', '1109085039008802338', '1508663827218802839', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1118691930399876861', '1109678436734865319', '1506684717783011305', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1118693069324499040', '1109678436734865319', '1507864990655592991', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1118696729590054207', '1109678436734865319', '1503566409477762965', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1118719425817151929', '1109678436734865319', '1508667099921872494', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1118909841102255086', '1102857530959322768', '1503907724282130056', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1118920412607095373', '1102857530959322768', '1508928998786471827', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1119154828275951337', '1101627895069947927', '1506781977159534714', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1119184058363459816', '1102857530959322768', '1503172858973826665', '0', '1549986827', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1119445751160641073', '1108075601261217576', '1509401261013647928', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1119525997471462766', '1101627895069947927', '1508682483856378091', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1119655958402450422', '1102857530959322768', '1508657911837197184', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1119670927897644067', '1108081433929767622', '1506684717783011305', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1119673926253351206', '1108081433929767622', '1504257822033784938', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1119674927179275829', '1108081433929767622', '1507135257693038084', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1119675947465308446', '1109678436734865319', '1507647620884801891', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1119678929977337841', '1108081433929767622', '1507246536601955290', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1119679921244770296', '1108081433929767622', '1503561800195527715', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1119680029242180308', '1109085039008802338', '1507864990655592991', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1119688024272543437', '1109085039008802338', '1507210837224495582', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1119689027251763218', '1109085039008802338', '1506772393205714217', '0', '0', '0', '0', '0');
INSERT INTO `sys_role_menu` VALUES ('1119710420782997100', '1109678436734865319', '1508622161153824054', '0', '0', '0', '0', '0');

-- ----------------------------
-- Table structure for `sys_subsystem`
-- ----------------------------
DROP TABLE IF EXISTS `sys_subsystem`;
CREATE TABLE `sys_subsystem` (
  `id` bigint(20) NOT NULL COMMENT '主键ID',
  `name` varchar(200) NOT NULL COMMENT '子系统名称',
  `code` longtext COMMENT '子系统编码',
  `status` smallint(1) unsigned DEFAULT '1' COMMENT '状态 1正常 0停用 2维护中 3管理员授权模式',
  `creator` bigint(20) DEFAULT '0' COMMENT '创建人',
  `createTime` int(11) unsigned DEFAULT '0' COMMENT '创建时间',
  `reviser` bigint(20) DEFAULT '0' COMMENT '修改人',
  `reviseTime` int(11) unsigned DEFAULT '0' COMMENT '修改时间',
  `isDel` tinyint(1) unsigned DEFAULT '0' COMMENT '删除状态:0-有效，1-已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='198 子系统';

-- ----------------------------
-- Records of sys_subsystem
-- ----------------------------
INSERT INTO `sys_subsystem` VALUES ('123', '12332123', '123', '0', '0', '0', '1008888888888888888', '1506004563', '1');
INSERT INTO `sys_subsystem` VALUES ('1982712646861294586', '123', '333', '1', '1008888888888888888', '1506004700', '1008888888888888888', '1506008228', '1');
INSERT INTO `sys_subsystem` VALUES ('1985404637130343783', 'CRM管理系统', 'admin-crm', '1', '1008888888888888888', '1506408578', '1008888888888888888', '1507877751', '0');
INSERT INTO `sys_subsystem` VALUES ('1986514272259991655', '基础配置管理系统', 'sys-config', '1', '1008888888888888888', '1505718032', '1008888888888888888', '1506763052', '0');
INSERT INTO `sys_subsystem` VALUES ('1989406673646562771', '易商务平台', 'easy-business', '1', '1008888888888888888', '1506408588', '1008888888888888888', '1508295917', '0');

-- ----------------------------
-- Table structure for `sys_tps_config`
-- ----------------------------
DROP TABLE IF EXISTS `sys_tps_config`;
CREATE TABLE `sys_tps_config` (
  `id` bigint(20) NOT NULL COMMENT '主键ID',
  `code` varchar(50) DEFAULT '' COMMENT '第三方配置编码',
  `attr` varchar(50) DEFAULT '' COMMENT '配置属性',
  `val` text COMMENT '配置值',
  `creator` bigint(20) DEFAULT '0' COMMENT '创建人',
  `createTime` int(11) unsigned DEFAULT '0' COMMENT '创建时间',
  `reviser` bigint(20) DEFAULT '0' COMMENT '修改人',
  `reviseTime` int(11) unsigned DEFAULT '0' COMMENT '修改时间',
  `isDel` smallint(1) unsigned DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `code` (`code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='200 系统第三方配置项 ';

-- ----------------------------
-- Records of sys_tps_config
-- ----------------------------
INSERT INTO `sys_tps_config` VALUES ('2001703051525049005', 'oss', 'Region', 'cn-shenzhen', '0', '1507909285', '0', '0', '0');
INSERT INTO `sys_tps_config` VALUES ('2002705059464966705', 'oss', 'RoleArn', 'acs:ram::1008778221902015:role/aliyunosstokengeneratorrole', '0', '1507909285', '0', '0', '0');
INSERT INTO `sys_tps_config` VALUES ('2004700050908729660', 'oss', 'Bucket', 'xtaller', '0', '1507909285', '0', '0', '0');
INSERT INTO `sys_tps_config` VALUES ('2004700053092472348', 'oss', 'AccessKeyID', 'LTAIHvCrb5ei6qFf', '0', '1507909285', '0', '0', '0');
INSERT INTO `sys_tps_config` VALUES ('2004702052294550586', 'oss', 'Host', 'http://xtaller.oss-cn-shenzhen.aliyuncs.com/', '0', '1507909285', '0', '0', '0');
INSERT INTO `sys_tps_config` VALUES ('2005708053279025725', 'oss', 'AccessKeySecret', 'MbLa23BPuu78hlIlqJwQEYwJH7Yz4J', '0', '1507909285', '0', '0', '0');
INSERT INTO `sys_tps_config` VALUES ('2006702057716795689', 'oss', 'Endpoint', 'http://oss-cn-shenzhen.aliyuncs.com', '0', '1507909285', '0', '0', '0');
INSERT INTO `sys_tps_config` VALUES ('2007704052997447008', 'oss', 'Policy', '{\r\n  \"Statement\": [\r\n    {\r\n      \"Action\": [\r\n        \"oss:*\"\r\n      ],\r\n      \"Effect\": \"Allow\",\r\n      \"Resource\": [\"acs:oss:*:*:*\"]\r\n    }\r\n  ],\r\n  \"Version\": \"1\"\r\n}', '0', '1507909285', '0', '0', '0');
INSERT INTO `sys_tps_config` VALUES ('2009708050708642421', 'oss', 'TokenExpireTime', '3600', '0', '1507909285', '0', '0', '0');
INSERT INTO `sys_tps_config` VALUES ('3003620651189132129', 'cos', 'expired', '60', '0', '1513135564', '0', '0', '0');
INSERT INTO `sys_tps_config` VALUES ('3003620653589906997', 'cos', 'host', 'http://qihsoft-1251217219.cosgz.myqcloud.com', '0', '1513135564', '0', '0', '0');
INSERT INTO `sys_tps_config` VALUES ('3003628659176302852', 'cos', 'appId', '1251217219', '0', '1513135564', '0', '0', '0');
INSERT INTO `sys_tps_config` VALUES ('3004626655674627338', 'cos', 'secretKey', 'Ngp5Fsl4GGijkorouoWCoWuHiceXOnDF', '0', '1513135564', '0', '0', '0');
INSERT INTO `sys_tps_config` VALUES ('3007625658421968091', 'cos', 'secretId', 'AKIDQ0Z4X8kIRdgYHuTgfomea4eBmSxUlHTc', '0', '1513135564', '0', '0', '0');
INSERT INTO `sys_tps_config` VALUES ('3007626651920841927', 'cos', 'bucket', 'qihsoft', '0', '1513135564', '0', '0', '0');
INSERT INTO `sys_tps_config` VALUES ('3007628656935669186', 'cos', 'region', 'gz', '0', '1513135564', '0', '0', '0');

-- ----------------------------
-- Table structure for `sys_user`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` bigint(20) NOT NULL COMMENT '主键ID',
  `loginName` varchar(20) DEFAULT '' COMMENT '登录名',
  `password` varchar(200) NOT NULL COMMENT '用户密码',
  `salt` int(6) unsigned DEFAULT '0' COMMENT '密码混淆盐',
  `status` smallint(1) unsigned DEFAULT '1' COMMENT '用户状态：1有效 0无效',
  `type` smallint(1) unsigned DEFAULT '1' COMMENT '用户类型：0-管理员，1-教师，2-学生',
  `loginIp` varchar(255) DEFAULT NULL COMMENT '最后登录IP',
  `loginTime` int(11) DEFAULT NULL COMMENT '最后登录时间',
  `creator` bigint(20) DEFAULT '0' COMMENT '创建人',
  `createTime` int(11) unsigned DEFAULT '0' COMMENT '注册时间',
  `reviser` bigint(20) DEFAULT '0' COMMENT '修改人',
  `reviseTime` int(11) unsigned DEFAULT '0' COMMENT '修改时间',
  `isDel` smallint(1) unsigned DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='301 用户表 ';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1001532437516740815', '20190140', '37d42f9e5a3b8c23208de83f5f934591', '422171', '1', '1', null, null, '1002518130371308851', '1561990101', '0', '0', '0');
INSERT INTO `sys_user` VALUES ('1002518130371308851', 'superadmin', '9a6078c4bea2619f6ae3d1734d94b492', '627027', '1', '0', '171.36.194.241', '1561990846', '1008604263574962166', '1558410174', '1002518130371308851', '1561990846', '0');
INSERT INTO `sys_user` VALUES ('1002531592643723922', '116263000313', 'ba91deba5e7afaa32c91a4c6f6e32ddb', '461473', '1', '2', null, null, '1002518130371308851', '1561989902', '0', '0', '0');
INSERT INTO `sys_user` VALUES ('1002535667589961042', '116263000140', '72c91de336ce3627dd4f6e031151140f', '137996', '1', '2', null, null, '1002518130371308851', '1561989916', '0', '0', '0');
INSERT INTO `sys_user` VALUES ('1003535872914815078', '116263000105', 'a2273ae1f81bb63bae9578e58af791e5', '330063', '1', '2', null, null, '1002518130371308851', '1561989729', '0', '0', '0');
INSERT INTO `sys_user` VALUES ('1005530495579668806', '20190313', '47871c17447971d880b740c7b314430c', '394165', '1', '1', null, null, '1002518130371308851', '1561990116', '0', '0', '0');
INSERT INTO `sys_user` VALUES ('1005530534386061283', '116263000111', 'c3305ea6eee83d756362eab9725cd6d0', '733804', '1', '2', null, null, '1002518130371308851', '1561989886', '0', '0', '0');
INSERT INTO `sys_user` VALUES ('1005535236578456579', '116263000328', 'b3038ae34fccb274f58e639a77b17153', '286150', '1', '2', null, null, '1002518130371308851', '1561990293', '0', '0', '0');
INSERT INTO `sys_user` VALUES ('1008537303443192206', '20190328', '5f9616cac50fe14fb8f4288a21859da3', '764350', '1', '1', '171.36.194.241', '1561990330', '1002518130371308851', '1561990308', '0', '1561990330', '0');
INSERT INTO `sys_user` VALUES ('1008538307815030872', '20190111', '6351b8ca1791508be840f81b1fcb66ba', '415951', '1', '1', null, null, '1002518130371308851', '1561990070', '0', '0', '0');
INSERT INTO `sys_user` VALUES ('1008538307815030873', 'school', '9a6078c4bea2619f6ae3d1734d94b492', '0', '1', '0', '171.36.194.241', '1561990609', '0', '0', '0', '1561990609', '0');
INSERT INTO `sys_user` VALUES ('1009535573132812001', '20190105', 'd7e8fb27e1be56d0c340068ea165787d', '311404', '1', '1', null, null, '1002518130371308851', '1561990133', '0', '0', '0');

-- ----------------------------
-- Table structure for `sys_user_department`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_department`;
CREATE TABLE `sys_user_department` (
  `id` bigint(20) NOT NULL COMMENT '主键ID',
  `systemId` bigint(20) DEFAULT '0' COMMENT '子系统Id sys_subsystem.id',
  `userId` bigint(20) DEFAULT '0' COMMENT '系统用户Id sys_user.id',
  `departmentId` bigint(20) DEFAULT '0' COMMENT '部门Id sys_category.id',
  `creator` bigint(20) DEFAULT '0' COMMENT '创建人',
  `createTime` int(11) unsigned DEFAULT '0' COMMENT '创建时间',
  `reviser` bigint(20) DEFAULT '0' COMMENT '修改人',
  `reviseTime` int(11) unsigned DEFAULT '0' COMMENT '修改时间',
  `isDel` smallint(1) unsigned DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `systemId` (`systemId`) USING BTREE,
  KEY `userId` (`userId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='105 用户部门归属关系';

-- ----------------------------
-- Records of sys_user_department
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_user_resources`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_resources`;
CREATE TABLE `sys_user_resources` (
  `id` bigint(20) NOT NULL COMMENT '主键ID',
  `systemId` bigint(20) DEFAULT '0' COMMENT '子系统Id sys_subsystem.id',
  `userId` bigint(20) DEFAULT '0' COMMENT '系统用户Id sys_user.id',
  `parentId` bigint(20) DEFAULT '0' COMMENT '父级Id',
  `isFile` smallint(1) unsigned DEFAULT '1' COMMENT '1是文件',
  `folder` varchar(10) DEFAULT '' COMMENT '文件夹',
  `maxFolder` smallint(10) DEFAULT '0' COMMENT '最大文件夹数量',
  `path` varchar(500) DEFAULT '' COMMENT '文件路径',
  `fullPath` varchar(500) DEFAULT '' COMMENT '完整路径',
  `name` varchar(500) DEFAULT '' COMMENT '文件名',
  `size` int(11) unsigned DEFAULT '0' COMMENT '容量/文件大小 kb',
  `type` varchar(500) DEFAULT '' COMMENT '文件类型',
  `maxSize` int(11) unsigned DEFAULT '0' COMMENT '最大总容量 kb',
  `maxFileSize` int(11) unsigned DEFAULT '0' COMMENT '最大单文件上传大小 kb',
  `creator` bigint(20) DEFAULT '0' COMMENT '创建人',
  `createTime` int(11) unsigned DEFAULT '0' COMMENT '创建时间',
  `reviser` bigint(20) DEFAULT '0' COMMENT '修改人',
  `reviseTime` int(11) unsigned DEFAULT '0' COMMENT '修改时间',
  `isDel` smallint(1) unsigned DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `systemId` (`systemId`) USING BTREE,
  KEY `userId` (`userId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='104 用户资源文件';

-- ----------------------------
-- Records of sys_user_resources
-- ----------------------------
INSERT INTO `sys_user_resources` VALUES ('1041775861397900548', '1986514272259991655', '1008888888888888888', '1048772779501140561', '1', '', '0', '1008888888888888888/sys-config/tc4k7a4t-代理商个人中心.png', 'http://xtaller.oss-cn-shenzhen.aliyuncs.com/1008888888888888888/sys-config/tc4k7a4t-代理商个人中心.png', '代理商个人中心.png', '98', 'image/png', '0', '0', '1008888888888888888', '1507687512', '1008888888888888888', '1507687796', '1');
INSERT INTO `sys_user_resources` VALUES ('1043324483131978037', '1986514272259991655', '1008888888888888888', '1986514272259991655', '0', '1234', '0', '', '', '', '0', '', '0', '0', '1008888888888888888', '1507819266', '1008888888888888888', '1507819490', '0');
INSERT INTO `sys_user_resources` VALUES ('1044708233823348690', '1986514272259991655', '1008888888888888888', '1048772779501140561', '1', '', '0', '1008888888888888888/sys-config/ef3aagck-免冠彩色照片.jpg', 'http://xtaller.oss-cn-shenzhen.aliyuncs.com/1008888888888888888/sys-config/ef3aagck-免冠彩色照片.jpg', '免冠彩色照片.jpg', '187', 'image/jpeg', '0', '0', '1008888888888888888', '1507909328', '0', '0', '0');
INSERT INTO `sys_user_resources` VALUES ('1045773105177713656', '1986514272259991655', '1008888888888888888', '1048772779501140561', '1', '', '0', '1008888888888888888/sys-config/cfbhdp36-代理商个人中心.png', 'http://xtaller.oss-cn-shenzhen.aliyuncs.com/1008888888888888888/sys-config/cfbhdp36-代理商个人中心.png', '代理商个人中心.png', '98', 'image/png', '0', '0', '1008888888888888888', '1507687806', '0', '0', '0');
INSERT INTO `sys_user_resources` VALUES ('1046494932328520987', '1986514272259991655', '1008888888888888888', '1043324483131978037', '1', '', '0', '1008888888888888888/sys-config/t2hjkw6f-免冠彩色照片.jpg', 'http://xtaller.oss-cn-shenzhen.aliyuncs.com/1008888888888888888/sys-config/t2hjkw6f-免冠彩色照片.jpg', '免冠彩色照片.jpg', '187', 'image/jpeg', '0', '0', '1008888888888888888', '1507858713', '0', '0', '0');
INSERT INTO `sys_user_resources` VALUES ('1046772773177995846', '1986514272259991655', '1008888888888888888', '0', '0', '', '6', '', '', '', '3025', '', '1048576', '2048', '0', '1507687490', '0', '1508208983', '0');
INSERT INTO `sys_user_resources` VALUES ('1046774335718957982', '1986514272259991655', '1008888888888888888', '1048772779501140561', '1', '', '0', '1008888888888888888/sys-config/nqmyxqat-交接文档.docx', 'http://xtaller.oss-cn-shenzhen.aliyuncs.com/1008888888888888888/sys-config/nqmyxqat-交接文档.docx', '交接文档.docx', '1136', 'application/vnd.openxmlformats-officedocument.wordprocessingml.document', '0', '0', '1008888888888888888', '1507687862', '0', '0', '0');
INSERT INTO `sys_user_resources` VALUES ('1046969074953477605', '1986514272259991655', '1008888888888888888', '1048772779501140561', '1', '', '0', '1008888888888888888/sys-config/jp5xp57w-header.gif', 'http://xtaller.oss-cn-shenzhen.aliyuncs.com/1008888888888888888/sys-config/jp5xp57w-header.gif', 'header.gif', '56', 'image/gif', '0', '0', '1008888888888888888', '1508208983', '0', '0', '0');
INSERT INTO `sys_user_resources` VALUES ('1047307959425813777', '1986514272259991655', '1008888888888888888', '1048772779501140561', '1', '', '0', '1008888888888888888/sys-config/nfzmyayf-timg (5).jpeg', 'http://xtaller.oss-cn-shenzhen.aliyuncs.com/1008888888888888888/sys-config/nfzmyayf-timg (5).jpeg', 'timg (5).jpeg', '447', 'image/jpeg', '0', '0', '1008888888888888888', '1507815563', '0', '0', '0');
INSERT INTO `sys_user_resources` VALUES ('1047308942959750320', '1986514272259991655', '1008888888888888888', '1048772779501140561', '1', '', '0', '1008888888888888888/sys-config/8ehqxxwp-timg (4).jpeg', 'http://xtaller.oss-cn-shenzhen.aliyuncs.com/1008888888888888888/sys-config/8ehqxxwp-timg (4).jpeg', 'timg (4).jpeg', '788', 'image/jpeg', '0', '0', '1008888888888888888', '1507815562', '0', '0', '0');
INSERT INTO `sys_user_resources` VALUES ('1048302941555616448', '1986514272259991655', '1008888888888888888', '1048772779501140561', '1', '', '0', '1008888888888888888/sys-config/3ngmta6r-timg (3).jpeg', 'http://xtaller.oss-cn-shenzhen.aliyuncs.com/1008888888888888888/sys-config/3ngmta6r-timg (3).jpeg', 'timg (3).jpeg', '83', 'image/jpeg', '0', '0', '1008888888888888888', '1507815561', '0', '0', '0');
INSERT INTO `sys_user_resources` VALUES ('1048307949160477556', '1986514272259991655', '1008888888888888888', '1048772779501140561', '1', '', '0', '1008888888888888888/sys-config/q5kemswy-timg (2).jpeg', 'http://xtaller.oss-cn-shenzhen.aliyuncs.com/1008888888888888888/sys-config/q5kemswy-timg (2).jpeg', 'timg (2).jpeg', '43', 'image/jpeg', '0', '0', '1008888888888888888', '1507815561', '0', '0', '0');
INSERT INTO `sys_user_resources` VALUES ('1048772779501140561', '1986514272259991655', '1008888888888888888', '1986514272259991655', '0', '临时文件', '0', '', '', '', '0', '', '0', '0', '0', '1507687490', '0', '0', '0');

-- ----------------------------
-- Table structure for `sys_user_role`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` bigint(20) NOT NULL COMMENT '主键ID',
  `userId` bigint(20) DEFAULT '0' COMMENT 'sys_user.id',
  `systemId` bigint(20) DEFAULT '0' COMMENT '子系统id sys_subsystem.id',
  `roleId` bigint(20) DEFAULT '0' COMMENT 'sys_role.id',
  `status` smallint(1) DEFAULT '1' COMMENT '子系统权限 0被冻结 1激活状态',
  `creator` bigint(20) DEFAULT '0' COMMENT '创建人',
  `createTime` int(11) unsigned DEFAULT '0' COMMENT '创建时间',
  `reviser` bigint(20) DEFAULT '0' COMMENT '修改人',
  `reviseTime` int(11) unsigned DEFAULT '0' COMMENT '修改时间',
  `isDel` smallint(1) unsigned DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `userId` (`userId`) USING BTREE,
  KEY `systemId` (`systemId`) USING BTREE,
  KEY `roleId` (`roleId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='103 用户对应角色关联表 ';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('1031536231630568449', '1005535236578456579', '0', '1105163704291618811', '1', '0', '1561990293', '0', '0', '0');
INSERT INTO `sys_user_role` VALUES ('1031538307443198200', '1008537303443192206', '0', '1101627895069947927', '1', '0', '1561990308', '0', '0', '0');
INSERT INTO `sys_user_role` VALUES ('1032512138459196939', '1002518130371308851', '0', '1102857530959322768', '1', '0', '1558410174', '1008604263574962166', '1558410183', '0');
INSERT INTO `sys_user_role` VALUES ('1032535594848863201', '1002531592643723922', '0', '1105163704291618811', '1', '0', '1561989902', '0', '0', '0');
INSERT INTO `sys_user_role` VALUES ('1032537303812037877', '1008538307815030872', '0', '1101627895069947927', '1', '0', '1561990070', '0', '0', '0');
INSERT INTO `sys_user_role` VALUES ('1034532571206910878', '1009535573132812001', '0', '1105163704291618811', '1', '0', '1561990133', '0', '0', '0');
INSERT INTO `sys_user_role` VALUES ('1034535871041847491', '1003535872914815078', '0', '1105163704291618811', '1', '0', '1561989729', '0', '0', '0');
INSERT INTO `sys_user_role` VALUES ('1034537537451972846', '1005530534386061283', '0', '1105163704291618811', '1', '0', '1561989886', '0', '0', '0');
INSERT INTO `sys_user_role` VALUES ('1035536664649882609', '1002535667589961042', '0', '1105163704291618811', '1', '0', '1561989916', '0', '0', '0');
INSERT INTO `sys_user_role` VALUES ('1036530499709881529', '1005530495579668806', '0', '1105163704291618811', '1', '0', '1561990116', '0', '0', '0');
INSERT INTO `sys_user_role` VALUES ('1036530499709881530', '1008538307815030873', '0', '1108075601261217576', '1', '0', '0', '0', '0', '0');
INSERT INTO `sys_user_role` VALUES ('1036535436510741810', '1001532437516740815', '0', '1101627895069947927', '1', '0', '1561990101', '0', '0', '0');

-- ----------------------------
-- Table structure for `sys_user_token`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_token`;
CREATE TABLE `sys_user_token` (
  `id` bigint(20) NOT NULL COMMENT '主键ID',
  `userId` bigint(20) NOT NULL COMMENT '用户ID',
  `token` varchar(200) DEFAULT '' COMMENT '认证令牌',
  `server` tinyint(1) DEFAULT NULL COMMENT '认证服务器',
  `type` varchar(255) DEFAULT NULL COMMENT '认证渠道',
  `creator` bigint(20) DEFAULT '0' COMMENT '创建人',
  `createTime` int(11) unsigned DEFAULT '0' COMMENT '创建时间',
  `reviser` bigint(20) DEFAULT '0' COMMENT '修改人',
  `reviseTime` int(11) unsigned DEFAULT '0' COMMENT '修改时间',
  `isDel` smallint(1) unsigned DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `userId` (`userId`) USING BTREE,
  KEY `token` (`token`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='102 用户Token关联表 ';

-- ----------------------------
-- Records of sys_user_token
-- ----------------------------
INSERT INTO `sys_user_token` VALUES ('1025529187666210715', '1002518130371308851', '39aeea8d7b464924bbac37f8e263448d', '1', 'redis', '0', '1558410902', '0', '1558410902', '0');
INSERT INTO `sys_user_token` VALUES ('1029528392344566883', '1002518130371308851', '498541d820c14603b534898647251a72', '0', 'redis', '0', '1558410952', '0', '1561990846', '0');
INSERT INTO `sys_user_token` VALUES ('1029536391462594009', '1008537303443192206', '12419077108a49c5b69fdbd508bbebba', '0', 'redis', '0', '1561990330', '0', '1561990330', '0');
INSERT INTO `sys_user_token` VALUES ('1029539565137856083', '1008538307815030873', '6c4b8c475f484d68a96e63f071e9f415', '0', 'redis', '0', '1561990608', '0', '1561990608', '0');

-- ----------------------------
-- Table structure for `teacher`
-- ----------------------------
DROP TABLE IF EXISTS `teacher`;
CREATE TABLE `teacher` (
  `id` bigint(20) NOT NULL,
  `name` varchar(20) COLLATE utf8_bin NOT NULL COMMENT '姓名',
  `number` varchar(20) COLLATE utf8_bin NOT NULL COMMENT '工号',
  `academyId` bigint(20) DEFAULT NULL COMMENT '所属学院id:academy.id',
  `sex` tinyint(1) DEFAULT NULL COMMENT '性别：1-女,2-男',
  `creator` bigint(20) DEFAULT '0' COMMENT '创建人',
  `createTime` int(11) unsigned DEFAULT '0' COMMENT '创建时间',
  `reviser` bigint(20) DEFAULT '0' COMMENT '修改人',
  `reviseTime` int(11) unsigned DEFAULT '0' COMMENT '修改时间',
  `isDel` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除状态:0-有效，1-已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='303 教师信息表';

-- ----------------------------
-- Records of teacher
-- ----------------------------
INSERT INTO `teacher` VALUES ('3032537496504559933', '梁老师', '20190313', '3059995609884076194', '2', '1002518130371308851', '1561990116', '1002518130371308851', '1561990143', '0');
INSERT INTO `teacher` VALUES ('3036535307681016448', '李老师', '20190111', '3059995609884076194', '1', '1002518130371308851', '1561990070', '0', '0', '0');
INSERT INTO `teacher` VALUES ('3036536307383285643', '黄老师', '20190328', '3059995609884076194', '2', '1002518130371308851', '1561990308', '0', '0', '0');
INSERT INTO `teacher` VALUES ('3037535572072890443', '戚老师', '20190105', '3059995609884076194', '2', '1002518130371308851', '1561990133', '0', '0', '0');
INSERT INTO `teacher` VALUES ('3039539430450834254', '韦老师', '20190140', '3059995609884076194', '1', '1002518130371308851', '1561990101', '0', '0', '0');

-- ----------------------------
-- Table structure for `teach_schedule`
-- ----------------------------
DROP TABLE IF EXISTS `teach_schedule`;
CREATE TABLE `teach_schedule` (
  `id` bigint(20) NOT NULL,
  `courseNumber` varchar(20) COLLATE utf8_bin NOT NULL COMMENT '课程号',
  `classId` varchar(20) COLLATE utf8_bin NOT NULL COMMENT '授课班级id:classes.id',
  `teacherNumber` varchar(20) COLLATE utf8_bin NOT NULL COMMENT '教师工号',
  `creator` bigint(20) DEFAULT '0' COMMENT '创建人',
  `createTime` int(11) unsigned DEFAULT '0' COMMENT '创建时间',
  `reviser` bigint(20) DEFAULT '0' COMMENT '修改人',
  `reviseTime` int(11) unsigned DEFAULT '0' COMMENT '修改时间',
  `isDel` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除状态:0-有效，1-已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='305 教师授课表';

-- ----------------------------
-- Records of teach_schedule
-- ----------------------------

-- ----------------------------
-- View structure for `v_class_details`
-- ----------------------------
DROP VIEW IF EXISTS `v_class_details`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `v_class_details` AS select `c`.`id` AS `id`,`c`.`majorCode` AS `majorCode`,`c`.`grade` AS `grade`,`c`.`classNo` AS `classNo`,`a`.`name` AS `academyName`,`m`.`name` AS `majorName`,`a`.`campus` AS `campus`,`m`.`academyCode` AS `academyCode`,`a`.`status` AS `academyStatus`,`m`.`status` AS `majorStatus` from ((`classes` `c` join `academy_info` `a`) join `major_info` `m`) where ((`c`.`majorCode` = `m`.`code`) and (`a`.`code` = `m`.`academyCode`) and (`a`.`isDel` = 0) and (`c`.`isDel` = 0) and (`m`.`isDel` = 0) and (`a`.`status` = 0) and (`m`.`status` = 0)) ;

-- ----------------------------
-- View structure for `v_exam_candidate`
-- ----------------------------
DROP VIEW IF EXISTS `v_exam_candidate`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `v_exam_candidate` AS select `ec`.`id` AS `id`,`ec`.`number` AS `number`,`ec`.`examId` AS `examId`,`ec`.`score` AS `score`,`ec`.`status` AS `status`,`ec`.`markStatus` AS `markStatus`,`ec`.`creator` AS `creator`,`ec`.`createTime` AS `createTime`,`ec`.`reviser` AS `reviser`,`ec`.`reviseTime` AS `submitTime`,`ec`.`isDel` AS `isDel`,`e`.`name` AS `examName`,`vs`.`classNo` AS `classNo`,`vs`.`academyName` AS `academyName`,`vs`.`majorName` AS `majorName`,`vs`.`name` AS `studentName`,`vs`.`grade` AS `grade`,`e`.`teacherNumber` AS `teacherNumber`,`e`.`startTime` AS `startTime`,`e`.`endTime` AS `endTime`,`e`.`releaseStatus` AS `releaseStatus`,`e`.`publishStatus` AS `publishStatus`,`e`.`duration` AS `duration`,`e`.`status` AS `examStatus`,`e`.`score` AS `totalScore`,`e`.`type` AS `type` from ((`exam_candidate` `ec` join `exam` `e`) join `v_student_details` `vs`) where ((`ec`.`examId` = `e`.`id`) and (`e`.`classId` = `vs`.`classId`) and (`ec`.`number` = `vs`.`number`) and (`e`.`classId` = `vs`.`classId`)) ;

-- ----------------------------
-- View structure for `v_exam_question`
-- ----------------------------
DROP VIEW IF EXISTS `v_exam_question`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `v_exam_question` AS select `exam`.`name` AS `name`,`exam_question`.`id` AS `id`,`exam_question`.`questionId` AS `questionId`,`exam_question`.`examId` AS `examId`,`exam_question`.`score` AS `score`,`exam_question`.`isDel` AS `isDel`,`exam_question`.`createTime` AS `createTime`,`question`.`questionName` AS `questionName`,`question`.`type` AS `type`,`exam`.`duration` AS `duration`,`question`.`answerKeys` AS `answerKeys` from ((`exam` join `exam_question`) join `question`) where ((`exam`.`id` = `exam_question`.`examId`) and (`question`.`id` = `exam_question`.`questionId`) and (`exam`.`isDel` = 0) and (`exam_question`.`isDel` = 0) and (`question`.`isDel` = 0)) ;

-- ----------------------------
-- View structure for `v_exam_record`
-- ----------------------------
DROP VIEW IF EXISTS `v_exam_record`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `v_exam_record` AS select `exam_record`.`examId` AS `examId`,`exam_record`.`questionId` AS `questionId`,`exam_record`.`number` AS `number`,`exam_question`.`score` AS `score`,`exam_record`.`situation` AS `situation`,`exam_record`.`answerContent` AS `answerContent`,`question`.`questionName` AS `questionName`,`question`.`type` AS `type`,`question`.`answerKeys` AS `answerKeys`,`exam_record`.`id` AS `id`,`exam_question`.`createTime` AS `createTime`,`exam_record`.`scores` AS `scores`,`exam_record`.`remark` AS `remark`,`exam_record`.`isExample` AS `isExample` from ((`exam_record` join `exam_question`) join `question`) where ((`exam_record`.`examId` = `exam_question`.`examId`) and (`exam_record`.`questionId` = `exam_question`.`questionId`) and (`exam_record`.`isDel` = 0) and (`exam_question`.`isDel` = 0) and (`question`.`id` = `exam_record`.`questionId`)) ;

-- ----------------------------
-- View structure for `v_student_details`
-- ----------------------------
DROP VIEW IF EXISTS `v_student_details`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `v_student_details` AS select `v_class_details`.`majorCode` AS `majorCode`,`v_class_details`.`grade` AS `grade`,`v_class_details`.`classNo` AS `classNo`,`v_class_details`.`academyName` AS `academyName`,`v_class_details`.`majorName` AS `majorName`,`v_class_details`.`campus` AS `campus`,`v_class_details`.`academyCode` AS `academyCode`,`v_class_details`.`academyStatus` AS `academyStatus`,`v_class_details`.`majorStatus` AS `majorStatus`,`s`.`name` AS `name`,`s`.`number` AS `number`,`s`.`classId` AS `classId`,`s`.`sex` AS `sex`,`s`.`id` AS `id`,`u`.`status` AS `status`,`u`.`createTime` AS `createTime`,`u`.`loginIp` AS `loginIp`,`u`.`loginTime` AS `loginTime`,from_unixtime(`u`.`loginTime`) AS `loginTimeStr` from ((`v_class_details` join `sys_user` `u`) join `student` `s`) where ((`u`.`isDel` = 0) and (`s`.`isDel` = 0) and (`v_class_details`.`id` = `s`.`classId`) and (`u`.`loginName` = `s`.`number`)) ;

-- ----------------------------
-- View structure for `v_teacher_details`
-- ----------------------------
DROP VIEW IF EXISTS `v_teacher_details`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `v_teacher_details` AS select `t`.`id` AS `id`,`t`.`name` AS `name`,`t`.`number` AS `number`,`t`.`academyId` AS `academyId`,`t`.`sex` AS `sex`,`t`.`creator` AS `creator`,`t`.`createTime` AS `createTime`,`t`.`reviser` AS `reviser`,`t`.`reviseTime` AS `reviseTime`,`t`.`isDel` AS `isDel`,`ai`.`code` AS `code`,`ai`.`name` AS `academyName`,`su`.`loginIp` AS `loginIp`,`su`.`loginTime` AS `loginTime`,`su`.`status` AS `status` from ((`teacher` `t` join `academy_info` `ai`) join `sys_user` `su`) where ((`t`.`academyId` = `ai`.`id`) and (`t`.`isDel` = 0) and (`ai`.`isDel` = 0) and (`su`.`isDel` = 0) and (`su`.`status` = 1) and (`t`.`number` = `su`.`loginName`)) ;

-- ----------------------------
-- View structure for `v_teach_schedule`
-- ----------------------------
DROP VIEW IF EXISTS `v_teach_schedule`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `v_teach_schedule` AS select `ts`.`id` AS `id`,`ts`.`courseNumber` AS `courseNumber`,`ts`.`classId` AS `classId`,`ts`.`teacherNumber` AS `teacherNumber`,`ts`.`creator` AS `creator`,`ts`.`createTime` AS `createTime`,`ts`.`reviser` AS `reviser`,`ts`.`reviseTime` AS `reviseTime`,`ts`.`isDel` AS `isDel`,`t`.`name` AS `teacherName`,`c`.`name` AS `courseName`,`vc`.`classNo` AS `classNo`,`vc`.`grade` AS `grade`,`vc`.`majorName` AS `majorName`,`vc`.`academyName` AS `academyName`,`vc`.`academyCode` AS `academyCode`,`vc`.`majorCode` AS `majorCode` from (((`course` `c` join `teach_schedule` `ts`) join `teacher` `t`) join `v_class_details` `vc`) where ((`c`.`number` = `ts`.`courseNumber`) and (`t`.`number` = `ts`.`teacherNumber`) and (`c`.`isDel` = 0) and (`ts`.`isDel` = 0) and (`t`.`isDel` = 0) and (`ts`.`classId` = `vc`.`id`)) ;

-- ----------------------------
-- Procedure structure for `exam_begin`
-- ----------------------------
DROP PROCEDURE IF EXISTS `exam_begin`;
DELIMITER ;;
CREATE DEFINER=`party`@`%` PROCEDURE `exam_begin`()
BEGIN
UPDATE exam
	SET status = 1
WHERE
	isDel = 0
	and releaseStatus=2 and status not in (1) 
	and UNIX_TIMESTAMP(NOW()) >= startTime and UNIX_TIMESTAMP(NOW())<=endTime;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for `exam_end`
-- ----------------------------
DROP PROCEDURE IF EXISTS `exam_end`;
DELIMITER ;;
CREATE DEFINER=`party`@`%` PROCEDURE `exam_end`()
BEGIN
	UPDATE exam
	SET status = 2
WHERE
  isDel = 0
	and releaseStatus=2 and status not in (2) 
	and UNIX_TIMESTAMP(NOW()) > endTime;	
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for `schedule_begin`
-- ----------------------------
DROP PROCEDURE IF EXISTS `schedule_begin`;
DELIMITER ;;
CREATE DEFINER=`party`@`%` PROCEDURE `schedule_begin`()
BEGIN
UPDATE `schedule`
	SET status = 2
WHERE
	isDel = 0
	and status not in (1,4) 
	and UNIX_TIMESTAMP(NOW()) < startTime;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for `schedule_end`
-- ----------------------------
DROP PROCEDURE IF EXISTS `schedule_end`;
DELIMITER ;;
CREATE DEFINER=`party`@`%` PROCEDURE `schedule_end`()
BEGIN
	UPDATE `schedule`
	SET status = 5
WHERE
  isDel = 0
	and status not in (1,4) 
	and UNIX_TIMESTAMP(NOW()) > endTime;	
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for `schedule_in`
-- ----------------------------
DROP PROCEDURE IF EXISTS `schedule_in`;
DELIMITER ;;
CREATE DEFINER=`party`@`%` PROCEDURE `schedule_in`()
BEGIN
UPDATE `schedule`
	SET status = 3
WHERE
	isDel = 0
	and status not in (1,4) 
	and UNIX_TIMESTAMP(NOW()) > startTime
  and UNIX_TIMESTAMP(NOW()) < endTime;	
END
;;
DELIMITER ;

-- ----------------------------
-- Event structure for `exam_begin`
-- ----------------------------
DROP EVENT IF EXISTS `exam_begin`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` EVENT `exam_begin` ON SCHEDULE EVERY 30 SECOND STARTS '2019-03-18 09:50:24' ON COMPLETION PRESERVE ENABLE DO UPDATE exam  SET exam.status=1 WHERE startTime <= UNIX_TIMESTAMP(CURRENT_TIMESTAMP)
AND `status` = 0
;
;;
DELIMITER ;

-- ----------------------------
-- Event structure for `exam_end`
-- ----------------------------
DROP EVENT IF EXISTS `exam_end`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` EVENT `exam_end` ON SCHEDULE EVERY 30 SECOND STARTS '2019-03-18 09:50:24' ON COMPLETION PRESERVE ENABLE DO UPDATE exam  SET exam.status=2 WHERE UNIX_TIMESTAMP(CURRENT_TIMESTAMP) > endTime 
AND `status` = 1
;
;;
DELIMITER ;

-- ----------------------------
-- Event structure for `schedule_begin`
-- ----------------------------
DROP EVENT IF EXISTS `schedule_begin`;
DELIMITER ;;
CREATE DEFINER=`party`@`%` EVENT `schedule_begin` ON SCHEDULE EVERY 30 SECOND STARTS '2019-03-18 09:50:24' ON COMPLETION PRESERVE ENABLE DO call schedule_begin()
;;
DELIMITER ;

-- ----------------------------
-- Event structure for `schedule_end`
-- ----------------------------
DROP EVENT IF EXISTS `schedule_end`;
DELIMITER ;;
CREATE DEFINER=`party`@`%` EVENT `schedule_end` ON SCHEDULE EVERY 30 SECOND STARTS '2019-03-18 09:50:09' ON COMPLETION PRESERVE ENABLE DO call schedule_end()
;;
DELIMITER ;

-- ----------------------------
-- Event structure for `schedule_in`
-- ----------------------------
DROP EVENT IF EXISTS `schedule_in`;
DELIMITER ;;
CREATE DEFINER=`party`@`%` EVENT `schedule_in` ON SCHEDULE EVERY 30 SECOND STARTS '2019-03-18 09:50:24' ON COMPLETION PRESERVE ENABLE DO call schedule_in()
;;
DELIMITER ;
