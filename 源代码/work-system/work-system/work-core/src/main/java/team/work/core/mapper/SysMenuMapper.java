package team.work.core.mapper;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import team.work.core.model.SysMenu;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;


public interface SysMenuMapper extends BaseMapper<SysMenu> {
    //重新排序
    @Update("update sys_menu set sort=(sort+1) where isDel=0 and sort>=#{sort} and parentId=0")
    Boolean setSort(@Param("sort") int sort);

    //查询目录
    @Select("SELECT id,name,parentId,type menuType  FROM sys_menu  where 1=1 and isDel=0 ${where}  order by sort asc")
    List<JSONObject> queryList(@Param("where") String where);

    //查询菜单详情
    @Select("SELECT * FROM sys_menu  where 1=1 and isDel=0 and id in ( select menuId FROM sys_role_menu where isDel =0 and roleId = ${roleId} ) order by sort asc")
    List<JSONObject> queryMenus(@Param("roleId") String roleId);
}
