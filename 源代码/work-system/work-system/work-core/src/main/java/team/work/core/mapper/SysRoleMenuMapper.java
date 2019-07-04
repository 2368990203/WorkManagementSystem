package team.work.core.mapper;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import team.work.core.model.SysRoleMenu;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenu> {
    //查询目录
    @Select("SELECT menuId  FROM sys_role_menu  where 1=1 and isDel=0 and roleId= ${roleId}  ")
    List<JSONObject> queryRoleMenus(@Param("roleId") String roleId);
}
