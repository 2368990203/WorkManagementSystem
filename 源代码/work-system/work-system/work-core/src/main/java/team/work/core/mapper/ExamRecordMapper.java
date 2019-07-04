package team.work.core.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import team.work.core.model.ExamRecord;

import java.util.List;

public interface ExamRecordMapper extends BaseMapper<ExamRecord> {

    @Select("SELECT a.* " +
              "FROM exam_record a " +
            "JOIN (SELECT id from exam_record where isDel = 0 ${where} " +
            "LIMIT #{index}, #{size})b ON a.id=b.id order by a.createTime asc ")
    List<JSONObject> getPage(@Param("where") String where,
                             @Param("index") int index,
                             @Param("size") int size);

    @Select("select count(1) total from exam_record where isDel = 0 ${where} ")
    JSONObject getPageCount(@Param("where") String where);

    @Select("SELECT a.* FROM v_exam_record a where 1=1 ${where}  order by questionId asc")
    List<JSONObject> queryAll(@Param("where") String where);

    // 批量插入
    @Insert("INSERT INTO exam_record (id,number,examId,questionId,situation," +
            "answerContent,creator,scores,createTime)VALUES " +
            "${values} ; ")
    int insertRows(@Param("values") String values) throws Exception;
}
