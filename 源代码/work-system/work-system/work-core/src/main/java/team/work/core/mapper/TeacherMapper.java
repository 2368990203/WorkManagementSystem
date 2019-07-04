package team.work.core.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import team.work.core.model.Teacher;

import java.util.List;
public interface TeacherMapper extends BaseMapper<Teacher> {

    @Select("SELECT a.* " +
              "FROM v_teacher_details a " +
            "JOIN (SELECT id from v_teacher_details where 1=1 ${where} LIMIT #{index}, #{size} " +
            ")b ON a.id=b.id order by a.createTime asc  ")
    List<JSONObject> getPage(@Param("where") String where,
                             @Param("index") int index,
                             @Param("size") int size);

    @Select("select count(1) total from v_teacher_details where 1=1 ${where} ")
    JSONObject getPageCount(@Param("where") String where);

    @Select("SELECT a.* FROM v_teacher_details a where 1=1 ${where}  order by createTime desc")
    List<JSONObject> queryAll(@Param("where") String where);
}
