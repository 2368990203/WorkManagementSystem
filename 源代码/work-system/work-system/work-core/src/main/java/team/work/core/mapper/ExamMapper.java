package team.work.core.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import team.work.core.model.Exam;
import org.apache.ibatis.annotations.Update;
import team.work.core.model.Exam;

import java.util.List;

public interface ExamMapper extends BaseMapper<Exam> {

    @Select("SELECT a.*,FROM_UNIXTIME(startTime) startTimeStr,FROM_UNIXTIME(endTime) endTimeStr " +
              "FROM exam a " +
            "JOIN (SELECT id from exam where isDel = 0 ${where} " +
            ")b ON a.id=b.id order by a.releaseStatus,a.startTime desc LIMIT #{index}, #{size}")
    List<JSONObject> getPage(@Param("where") String where,
                             @Param("index") int index,
                             @Param("size") int size);

    @Select("select count(1) total from exam where isDel = 0 ${where} ")
    JSONObject getPageCount(@Param("where") String where);

    @Select("SELECT a.*,FROM_UNIXTIME(a.createTime) createTimeStr,FROM_UNIXTIME(startTime) startTimeStr,FROM_UNIXTIME(endTime) endTimeStr " +
            "FROM exam a where 1=1 and isDel=0 ${where}  " +
            "order by a.releaseStatus,a.startTime desc")
    List<JSONObject> queryAll(@Param("where") String where);


    @Select("SELECT a.*,FROM_UNIXTIME(startTime) startTimeStr,FROM_UNIXTIME(endTime) endTimeStr FROM exam a where 1=1 and isDel=0 and id = ${id}  order by createTime desc")
    JSONObject queryById(@Param("id") String id);


    @Update("update exam_candidate set status = 1 , score = null ,reviseTime=0 where examId = ${examId} and number = ${number}")
    int updateExamCandidate(@Param("examId") String examId,@Param("number") String number);

    @Update("update exam_record set isDel=1 where examId = ${examId} and number = ${number}")
    int deleteExamRecord(@Param("examId") String examId,@Param("number") String number);

}
