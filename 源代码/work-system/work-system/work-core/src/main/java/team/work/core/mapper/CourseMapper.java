package team.work.core.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import team.work.core.model.Course;

import java.util.List;
public interface CourseMapper extends BaseMapper<Course> {

    @Select("SELECT a.* " + 
              "FROM course a " + 
            "JOIN (SELECT id from course where isDel = 0 ${where} LIMIT #{index}, #{size} " + 
            ")b ON a.id=b.id order by a.createTime asc  ") 
    List<JSONObject> getPage(@Param("where") String where, 
                             @Param("index") int index, 
                             @Param("size") int size); 

    @Select("select count(1) total from course where isDel = 0 ${where} ") 
    JSONObject getPageCount(@Param("where") String where); 

    @Select("SELECT a.* FROM course a where 1=1 and isDel=0 ${where}  order by createTime desc")
    List<JSONObject> queryAll(@Param("where") String where);
}