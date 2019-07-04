package team.work.core.mapper;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import team.work.core.model.SysRoleAuth;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


public interface SysRoleAuthMapper extends BaseMapper<SysRoleAuth> {
    //查询目录
    @Select("SELECT authId  FROM sys_role_auth  where 1=1 and isDel=0 and roleId= ${roleId}  ")
    List<JSONObject> queryRoleAuths(@Param("roleId") String roleId);
}
