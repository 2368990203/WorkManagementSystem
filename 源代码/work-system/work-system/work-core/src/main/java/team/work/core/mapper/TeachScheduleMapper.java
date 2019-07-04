package team.work.core.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import team.work.core.model.TeachSchedule;

import java.util.List;
public interface TeachScheduleMapper extends BaseMapper<TeachSchedule> {

    @Select("SELECT a.* " +
              "FROM v_teach_schedule a " +
            "JOIN (SELECT id from v_teach_schedule where 1=1 ${where} LIMIT #{index}, #{size} " +
            ")b ON a.id=b.id order by a.createTime asc  ")
    List<JSONObject> getPage(@Param("where") String where,
                             @Param("index") int index,
                             @Param("size") int size);

    @Select("select count(1) total from v_teach_schedule where 1=1 ${where} ")
    JSONObject getPageCount(@Param("where") String where);

    @Select("SELECT a.* FROM v_teach_schedule a where 1=1  ${where}  order by createTime desc")
    List<JSONObject> queryAll(@Param("where") String where);
}
