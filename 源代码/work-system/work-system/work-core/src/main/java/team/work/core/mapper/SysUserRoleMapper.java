package team.work.core.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import team.work.core.model.SysUserRole;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import team.work.core.model.SysUserRole;


public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {
    @Update("delete from sys_user_role where isDel=0 " +
            "and userId=#{userId} ")
    Boolean deleteByUserIdAndSystemId(@Param("userId") String userId);
}
