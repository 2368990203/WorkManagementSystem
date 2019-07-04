package team.work.core.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import team.work.core.model.Student;

import java.util.List;
public interface StudentMapper extends BaseMapper<Student> {

    @Select("SELECT a.* " +
              "FROM v_student_details a " +
            "JOIN (SELECT id from v_student_details where 1=1 ${where} LIMIT #{index}, #{size} " +
            ")b ON a.id=b.id order by a.createTime desc  ")
    List<JSONObject> getPage(@Param("where") String where,
                             @Param("index") int index,
                             @Param("size") int size);

    @Select("select count(1) total from v_student_details where 1=1 ${where} ")
    JSONObject getPageCount(@Param("where") String where);

    @Select("SELECT a.* FROM v_student_details a where 1=1  ${where}  order by createTime desc")
    List<JSONObject> queryAll(@Param("where") String where);
}
