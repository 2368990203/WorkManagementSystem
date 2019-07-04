package team.work.core.mapper;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import team.work.core.model.SysRole;
import team.work.core.model.SysRoleAuth;
import team.work.core.model.SysRoleMenu;
import org.apache.ibatis.annotations.*;
import team.work.core.model.SysRole;
import team.work.core.model.SysRoleAuth;
import team.work.core.model.SysRoleMenu;

import java.util.List;


public interface SysRoleMapper extends BaseMapper<SysRole> {
    @Update("update sys_role set isDefault=0 where isDel=0 and systemId=#{systemId}")
    Boolean initDefault(@Param("systemId") String systemId);

    @Delete(" delete from sys_role_menu where id in (${sql}) " )
    int deleteRoleMenus(@Param("sql") String sql);

    @Delete(" delete from sys_role_auth where id in (${sql}) " )
    int deleteRoleAuths(@Param("sql") String sql);

    @Insert("INSERT INTO sys_role_menu (id,roleId,menuId)VALUES " +
            "${values}  ")
    int insertRoleMenus(@Param("values") String values);

    @Insert("INSERT INTO sys_role_auth (id,roleId,authId)VALUES " +
            "${values}  ")
    int insertRoleAuths(@Param("values") String values);

    //查询角色菜单信息
    @Select("select id,menuId from sys_role_menu where isDel=0 and roleId=#{roleId}")
    List<SysRoleMenu> getRoleMenuByRoleId(@Param("roleId") String roleId);

    //查询角色权限信息
    @Select("select id,authId from sys_role_auth where isDel=0 and roleId=#{roleId}")
    List<SysRoleAuth> getRoleAuthByRoleId(@Param("roleId") String roleId);

    @Select("SELECT a.id roleId,b.`name` systemName,systemId,a.`name` roleName,isDefault FROM sys_role a " +
            "LEFT join sys_subsystem b on a.systemId=b.id " +
            "where a.isDel=0 ORDER BY systemId,isDefault DESC")
    List<JSONObject> getRoleInfo();

    @Select("SELECT d.value,r.*,u.* FROM sys_user u JOIN sys_user_role ur ON u.id =ur.userId AND u.isDel = 0 AND ur.isDel = 0 " +
            "JOIN sys_role r ON r.id=ur.roleId AND r.isDel = 0 AND u.id = ${userId} JOIN dictionary d ON d.id = r.visible AND d.isDel = 0 ")
    List<JSONObject> getUserRoleByUserId(@Param("userId") String userId);

}
