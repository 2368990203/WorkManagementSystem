package team.work.core.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import team.work.core.model.SysTpsConfig;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import team.work.core.model.SysTpsConfig;


public interface SysTpsConfigMapper extends BaseMapper<SysTpsConfig> {
    @Update("delete from sys_tps_config where isDel=0 and code=#{code}")
    Boolean deleteByCode(@Param("code") String code);
}
