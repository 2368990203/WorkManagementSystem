package team.work.core.mapper;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import team.work.core.model.SysMenu;
import team.work.core.model.SysMenuAuth;
import team.work.core.model.SysUser;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import team.work.core.model.SysMenu;
import team.work.core.model.SysMenuAuth;
import team.work.core.model.SysUser;

import java.util.List;


public interface SysUserMapper extends BaseMapper<SysUser> {
    @Select("SELECT a.id, a.loginName, a.`status`, a.number, a.phone,sr.name roleName,sr.id roleId,academyName, " +
            "ubi.name realName, a.createTime regTime,IFNULL(a.loginTime,0) loginTime " +
            "FROM sys_user a  " +
            "JOIN (select id from sys_user where isDel=0 ${user_where}) b  ON a.id = b.id " +
            "JOIN ( select userId,roleId from sys_user_role where isDel=0 ${role_where})  sur ON a.id = sur.userId " +
            "JOIN ( select id,name from sys_role where isDel=0 ) sr ON sur.roleId = sr.id " +
            "JOIN ( select number,name,academyCode,academyName from v_user_info where isDel=0 ${info_where}) ubi ON ubi.number = a.number " +
            "ORDER BY a.createTime desc limit #{index}, #{size}"
            )
    List<JSONObject> getPage( @Param("user_where") String user_where,
                              @Param("role_where") String role_where,
                              @Param("info_where") String info_where,
                             @Param("index") int index,
                             @Param("size") int size);

    @Select("SELECT a.id, a.loginName, a.`status`, a.number, a.phone,sr.name roleName,sr.id roleId,academyName, " +
            "ubi.name realName, a.createTime regTime,IFNULL(a.loginTime,0) loginTime " +
            "FROM sys_user a  " +
            "JOIN (select id from sys_user where isDel=0 ${user_where} ORDER BY createTime desc limit #{index}, #{size}) b  ON a.id = b.id " +
            "JOIN ( select userId,roleId from sys_user_role where isDel=0 )  sur ON a.id = sur.userId " +
            "JOIN ( select id,name from sys_role where isDel=0 ) sr ON sur.roleId = sr.id " +
            "JOIN ( select number,name,academyCode,academyName from v_user_info where isDel=0) ubi ON ubi.number = a.number " +
            "ORDER BY a.createTime desc "
    )
    List<JSONObject> getPage_onlyUser( @Param("user_where") String user_where,
                              @Param("index") int index,
                              @Param("size") int size);

    @Select("select count(1) total from sys_user a " +
            "JOIN ( select id from sys_user where isDel=0 ${user_where}) b  ON a.id = b.id " +
            "JOIN ( select userId,roleId  from sys_user_role where isDel=0 ${role_where})  sur ON a.id = sur.userId ")
    JSONObject getPageCount_noInfo(  @Param("user_where") String user_where,
                              @Param("role_where") String role_where);
    @Select("select count(1) total from sys_user a " +
            "JOIN ( select id from sys_user where isDel=0 ${user_where}) b  ON a.id = b.id " +
            "JOIN ( select number,name,academyCode,academyName from v_user_info  where isDel=0 ${info_where}) ubi ON ubi.number = a.number ")
    JSONObject getPageCount_noRole(  @Param("user_where") String user_where,
                              @Param("info_where") String info_where);

    @Select("select count(1) total from sys_user a " +
            "JOIN ( select id from sys_user where isDel=0 ${user_where}) b  ON a.id = b.id ")
    JSONObject getPageCount_default(  @Param("user_where") String user_where);

    @Select("select count(1) total from sys_user a " +
            "JOIN ( select id from sys_user where isDel=0 ${user_where} ) b  ON a.id = b.id " +
            "JOIN ( select userId,roleId  from sys_user_role where isDel=0  ${role_where})  sur ON a.id = sur.userId " +
            "JOIN ( select number,name,academyCode,academyName from v_user_info  where isDel=0  ${info_where}) ubi ON ubi.number = a.number ")
    JSONObject getPageCount(@Param("user_where") String user_where,
                            @Param("role_where") String role_where,
                            @Param("info_where") String info_where);

    //读取配置子系统权限
    @Select("SELECT a.id systemId,a.name systemName,IFNULL(b.id,0) `check`,IFNULL(c.name,'') roleName FROM sys_subsystem a " +
            "left join sys_user_role b on a.id=b.systemId and b.userId=#{userId} " +
            "left join sys_role c on c.id = b.roleId " +
            "where a.isDel=0 ")
    List<JSONObject> getSubSystemByUserId(@Param("userId") String userId);

    @Select("SELECT a.* FROM sys_menu a " +
            "INNER JOIN sys_role_menu b on a.id = b.menuId " +
            "INNER JOIN sys_user_role c on b.roleId=c.roleId and c.userId=#{userId} " +
            "where a.isDel=0 " +
            "order by a.sort asc")
    List<SysMenu> getRoleMenuByUserId(@Param("userId") String userId);

    @Select("SELECT d.* FROM sys_menu_auth d  " +
            "INNER JOIN sys_menu a on d.menuId=a.id " +
            "INNER JOIN sys_role_menu b on a.id=b.menuId " +
            "INNER JOIN sys_user_role c on b.roleId=c.roleId and c.userId=#{userId} " +
            "where d.isDel=0 " +
            "order by d.sort asc")
    List<SysMenuAuth> getRoleAuthByUserId(@Param("userId") String userId);
}
