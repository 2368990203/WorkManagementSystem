package team.work.core.mapper;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import team.work.core.model.SysUserToken;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import team.work.core.model.SysUserToken;

import java.util.List;


public interface SysUserTokenMapper extends BaseMapper<SysUserToken> {
    @Select("SELECT a.* FROM sys_user_token a where 1=1 and isDel=0 and type= '${type}' and token= '${token}' ")
    JSONObject queryUserIdByToken(@Param("type") String type,@Param("token") String token);
}
