package team.work.core.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import team.work.core.model.SysGlobalConfig;
import org.apache.ibatis.annotations.Update;
import team.work.core.model.SysGlobalConfig;


public interface SysGlobalConfigMapper extends BaseMapper<SysGlobalConfig> {
    @Update("delete from sys_global_config where isDel=0 ")
    Boolean deleteBySystemId();
}
