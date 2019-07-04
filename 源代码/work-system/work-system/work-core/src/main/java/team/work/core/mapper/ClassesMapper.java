package team.work.core.mapper;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import team.work.core.model.Classes;

import java.util.List;
public interface ClassesMapper extends BaseMapper<Classes> {

    @Select("SELECT a.* " +
              "FROM v_class_details a " +
            "JOIN (SELECT id from v_class_details where 1=1 ${where} LIMIT #{index}, #{size} " +
            ")b ON a.id=b.id order by a.grade asc  ")
    List<JSONObject> getPage(@Param("where") String where,
                             @Param("index") int index,
                             @Param("size") int size);

    @Select("select count(1) total from v_class_details where 1=1 ${where} ")
    JSONObject getPageCount(@Param("where") String where);

    @Select("SELECT a.* FROM v_class_details a where 1=1  ${where}  order by grade desc")
    List<JSONObject> queryAll(@Param("where") String where);
}
