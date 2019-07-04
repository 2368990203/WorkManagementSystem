package team.work.utils.constant;

import java.util.HashMap;
import java.util.Map;


public class Table {
    public static Map<String,Integer> BUSINESS = getTable();

    private static Map<String,Integer> getTable(){
        Map<String,Integer> map = new HashMap<String,Integer>();
        map.put("sys_user_token",102);//用户Token关联表
        map.put("sys_user_role",103);//用户对应角色关联表
        map.put("sys_user_resources",104);//用户资源文件
        map.put("sys_user_department",105);//用户部门所属关系

        map.put("sys_role",110);//角色表
        map.put("sys_role_menu",111);//角色菜单表
        map.put("sys_role_auth",112);//角色菜单权限表
        map.put("sys_category",120);//类型表

        map.put("sys_menu", 150);//权限菜单表
        map.put("sys_menu_auth", 151);//权限菜单按钮权限表

        map.put("sys_subsystem",198);//子系统
        map.put("sys_auto_code",199);//代码生成记录

        map.put("sys_tps_config",200);//系统第三方配置项
        map.put("sys_global_config",201);//系统基本配置
        map.put("sys_operation_record",202);//系统操作记录


        map.put("user_base_info",301);//用户基础信息表


        map.put("dictionary",308);//数据字典表

        map.put("message",354);//消息提醒表

        map.put("message_user",355);//消息提醒用户关系表

        map.put("exam_room",356);//试卷考场关系表


        //作业管理系统
        map.put("sys_user", 100);//系统用户表
        map.put("student", 302);//学生表
        map.put("teacher", 303);//教师表
        map.put("course",304);//班级表
        map.put("teach_schedule",305);//授课安排表
        map.put("question",306);//题库表
        map.put("options",307);//选项表
        map.put("exam",308);//试卷表
        map.put("exam_question",309);//试卷与题目关系表
        map.put("exam_candidate",310);//试卷与学生关系表
        map.put("exam_record",311);//答题记录表
        map.put("academy_info",312);//学院信息表
        map.put("major_info",313);//专业信息表
        map.put("classes",314);//班级表

        return map;
    }
}

