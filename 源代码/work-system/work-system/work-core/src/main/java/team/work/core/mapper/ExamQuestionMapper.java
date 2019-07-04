package team.work.core.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import team.work.core.model.ExamQuestion;

import java.util.List;

public interface ExamQuestionMapper extends BaseMapper<ExamQuestion> {

    @Select("SELECT a.* " +
              "FROM v_exam_question a " +
            "JOIN (SELECT id from v_exam_question where isDel = 0 ${where} " +
            "LIMIT #{index}, #{size})b ON a.id=b.id order by a.createTime asc ")
    List<JSONObject> getPage(@Param("where") String where,
                             @Param("index") int index,
                             @Param("size") int size);

    @Select("select count(1) total from exam_question where isDel = 0 ${where} ")
    JSONObject getPageCount(@Param("where") String where);

    @Select("SELECT a.* FROM v_exam_question a where 1=1 and isDel=0 ${where}  order by questionId asc")
    List<JSONObject> queryAll(@Param("where") String where);

    @Select("SELECT id,questionId,questionName,type,score FROM v_exam_question a where 1=1 and isDel=0 ${where}  order by questionId asc")
    List<JSONObject> queryPCQuestion(@Param("where") String where);

    @Select("SELECT score FROM v_exam_question a where 1=1 and isDel=0 and   examId = '${examId}' and questionId = '${questionId}' ")
    Double queryPCQuestionScore(@Param("examId") String examId,@Param("questionId") String questionId);

    @Select("SELECT questionId FROM v_exam_question a where 1=1 and isDel=0 and examId = '${examId}' ")
    String[] queryPCQuestionIds(@Param("examId") String examId);
}
