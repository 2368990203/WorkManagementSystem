package team.work.core.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import team.work.core.model.Question;

import java.util.List;

public interface QuestionMapper extends BaseMapper<Question> {

    @Select("SELECT a.* " +
            "FROM question a " +
            "JOIN (SELECT id from question where isDel = 0 ${where} " +
            ")b ON a.id=b.id order by a.createTime desc LIMIT #{index}, #{size} ")
    List<JSONObject> getPage(@Param("where") String where,
                             @Param("index") int index,
                             @Param("size") int size);

    @Select("select count(1) total from question where isDel = 0 ${where} ")
    JSONObject getPageCount(@Param("where") String where);

    @Select("SELECT a.* FROM question a where 1=1 and isDel=0 ${where}  order by createTime desc")
    List<JSONObject> queryAll(@Param("where") String where);

    @Select("SELECT a.* FROM v_questions a where 1=1 ${where}  order by createTime desc")
    List<JSONObject> getQuestions(@Param("where") String where);
}
