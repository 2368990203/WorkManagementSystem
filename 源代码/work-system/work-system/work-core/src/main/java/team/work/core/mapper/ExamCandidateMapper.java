package team.work.core.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import team.work.core.model.ExamCandidate;

import java.util.List;

public interface ExamCandidateMapper extends BaseMapper<ExamCandidate> {


    // fixme:批量插入
    @Insert("INSERT INTO exam_candidate (id,number,examId,creator,createTime)VALUES " +
            "${values} ; ")
    int insertRows(@Param("values") String values);

    //reviseTime
    @Select("SELECT *,FROM_UNIXTIME(submitTime) submitTimeStr, " +
            "FROM_UNIXTIME(startTime) startTimeStr," +
            "FROM_UNIXTIME(endTime) endTimeStr " +
            "FROM v_exam_candidate " +
            "WHERE 1=1  ${where}  ORDER BY status ASC, startTime DESC  LIMIT #{index}, #{size} ")
    List<JSONObject> getPage(@Param("where") String where,
                             @Param("index") int index,
                             @Param("size") int size);

    @Select("select count(1) total from v_exam_candidate where 1=1 ${where} ")
    JSONObject getPageCount(@Param("where") String where);

    @Select("SELECT *,FROM_UNIXTIME(startTime) startTimeStr," +
            "FROM_UNIXTIME(endTime) endTimeStr," +
            "FROM_UNIXTIME(submitTime) submitTimeStr from v_exam_candidate where 1=1  ${where}  order by releaseStatus asc, startTime desc")
    List<JSONObject> queryAll(@Param("where") String where);

    @Select("SELECT number  FROM exam_candidate   where 1=1 and isDel=0 and examId =  ${examId} ")
    String[] queryStudentsByExamId(@Param("examId") String examId);

    @Select("SELECT COUNT(1) count FROM exam_candidate WHERE `markStatus` = 1 ${where} ")
    Integer queryCount(@Param("where") String where);


    @Select("SELECT a.id,a.examId,a.status,a.examStatus,a.name examName,u.number,u.name,academyName,a.score,a.remark,a.ipAddr,myClass,FROM_UNIXTIME(a.submitTime) submitTimeStr   " +
            "FROM v_exam_candidate a join (select id from v_exam_candidate where 1=1 ${exam_where} )b on a.id = b.id  " +
            " join (select number,name,academyName,academyCode,myClass from v_user_info where isDel=0 ${user_where} ) u on  a.number=u.number where 1=1  order by releaseStatus asc,startTime desc,examId,a.number   LIMIT #{index}, #{size}")
    List<JSONObject> getPageGrade_userSelect(@Param("exam_where") String exam_where,
                                             @Param("user_where") String user_where,
                                             @Param("index") int index,
                                             @Param("size") int size);


    @Select("select count(1) total FROM v_exam_candidate a " +
            "join ( select id from   v_exam_candidate where 1=1 ${exam_where} )b on a.id=b.id " +
            " join (" +
            "SELECT `u`.`name` AS `name`,`u`.`number` AS `number`,`udi`.`academyCode` AS `academyCode`,`udi`.`myClass` AS `myClass`,`ai`.`name` AS `academyName`   " +
            "FROM`user_base_info` `u`   " +
            "JOIN ( SELECT id FROM `user_base_info` WHERE  isDel = 0   ) ubi ON  u.id =  ubi.id   " +
            "JOIN (SELECT number,academyCode,myClass FROM   `user_detail_info`   WHERE   isDel = 0  ) udi ON  u.number =  udi.number   " +
            "JOIN (SELECT code,name from academy_info where isDel=0) ai ON  `udi`.`academyCode` =  `ai`.`code`   " +
            "where isDel = 0   ${ubi_where})u on  a.number=u.number where 1=1 order by releaseStatus asc,startTime desc,examId,u.number ")
    JSONObject getPageGradeCountUserSelect(@Param("exam_where") String exam_where, @Param("ubi_where") String ubi_where);

    @Select("SELECT   a.id,  a.STATUS, a.examid,a.examStatus, a.NAME examName,   roomName,  u.number, u.NAME,  u.academyName,  a.score,  myClass,  FROM_UNIXTIME( a.submitTime ) submitTimeStr  FROM   v_exam_candidate a  " +
            " join (SELECT id from v_exam_candidate where 1 = 1   ${exam_where}   order by releaseStatus asc,startTime desc,examId,number LIMIT #{index}, #{size}  )b on  a .id =b.id  " +
            " join (" +
            "SELECT `u`.`name` AS `name`,`u`.`number` AS `number`,`udi`.`academyCode` AS `academyCode`,`udi`.`myClass` AS `myClass`,`ai`.`name` AS `academyName`   " +
            "FROM`user_base_info` `u`   " +
            "JOIN ( SELECT id FROM `user_base_info` WHERE  isDel = 0  ) ubi ON  u.id =  ubi.id   " +
            "JOIN (SELECT number,academyCode,myClass FROM   `user_detail_info`    WHERE   isDel = 0 ) udi ON  u.number =  udi.number   " +
            "JOIN (SELECT code,name from academy_info where isDel=0) ai ON  `udi`.`academyCode` =  `ai`.`code`   " +
            "where isDel = 0 )u " +
            "on a.number=u.number " +
            " where 1=1 order by releaseStatus asc,startTime desc,examId,u.number  ")
    List<JSONObject> getPageGradeExamSelect(@Param("exam_where") String exam_where,
                                            @Param("index") int index,
                                            @Param("size") int size);


    @Select("select count(1) total FROM v_exam_candidate a " +
            "join ( select id from   v_exam_candidate where 1=1 ${exam_where} )b on a.id=b.id  where 1=1  ")
    JSONObject getPageGradeCountExamSelect(@Param("exam_where") String exam_where);


    @Select("SELECT   a.id,  a.STATUS, a.examid,a.examStatus, a.NAME examName,   roomName,  u.number, u.NAME,  u.academyName,  a.score,myClass,  FROM_UNIXTIME( a.submitTime ) submitTimeStr  FROM   v_exam_candidate a  " +
            " join (SELECT id from v_exam_candidate where 1 = 1   ${exam_where}  order by releaseStatus asc,startTime desc,number   )b on  b .id =a.id  " +
            " join (" +
            "SELECT `u`.`name` AS `name`,`u`.`number` AS `number`,`udi`.`academyCode` AS `academyCode`,`udi`.`myClass` AS `myClass`,`ai`.`name` AS `academyName`   " +
            "FROM`user_base_info` `u`   " +
            "JOIN ( SELECT id FROM `user_base_info` WHERE  isDel = 0  ) ubi ON  u.id =  ubi.id   " +
            "JOIN (SELECT number,academyCode,myClass FROM   `user_detail_info`    WHERE   isDel = 0 ) udi ON  u.number =  udi.number   " +
            "JOIN (SELECT code,name from academy_info where isDel=0) ai ON  `udi`.`academyCode` =  `ai`.`code`   " +
            "where isDel = 0 )u " +
            "on a.number=u.number " +
            " where 1=1 order by releaseStatus asc,startTime desc,u.number   ")
    List<JSONObject> queryAllGrade(@Param("exam_where") String exam_where);

    @Select("SELECT   a.id,  a.STATUS, a.examid,a.examStatus, a.NAME examName,   roomName,  u.number, u.NAME,  u.academyName,  a.score, myClass,  FROM_UNIXTIME( a.submitTime ) submitTimeStr  FROM   v_exam_candidate a  " +
            " join (SELECT id from v_exam_candidate where 1 = 1   ${exam_where}  order by  releaseStatus asc, startTime desc,number   )b on  b .id =a.id  " +
            " join (" +
            "SELECT `u`.`name` AS `name`,`u`.`number` AS `number`,`udi`.`academyCode` AS `academyCode`,`udi`.`myClass` AS `myClass`,`ai`.`name` AS `academyName`   " +
            "FROM`user_base_info` `u`   " +
            "JOIN ( SELECT id FROM `user_base_info` WHERE  isDel = 0  ) ubi ON  u.id =  ubi.id   " +
            "JOIN (SELECT number,academyCode,myClass FROM   `user_detail_info`    WHERE   isDel = 0 ) udi ON  u.number =  udi.number   " +
            "JOIN (SELECT code,name from academy_info where isDel=0) ai ON  `udi`.`academyCode` =  `ai`.`code`   " +
            "where isDel = 0  ${ubi_where})u " +
            "on a.number=u.number " +
            " where 1=1 order by releaseStatus asc,startTime desc,u.number   ")
    List<JSONObject> queryAllGradeUser(@Param("exam_where") String exam_where,
                                       @Param("ubi_where") String ubi_where);


    @Select("SELECT rank FROM(  " +
            "            SELECT b.*,   " +
            "            CASE WHEN @rowtotal = score THEN   @rownum   " +
            "            WHEN @rowtotal := score THEN   @rownum :=@rownum + 1   " +
            "            WHEN @rowtotal = 0 THEN   @rownum :=@rownum + 1   " +
            "            END AS rank    " +
            "            FROM (SELECT a.number,a.score from v_exam_candidate a where 1 = 1  and examId = ${examId}  order by score desc " +
            ")b,(SELECT @rownum := 0 ,@rowtotal := NULL)r   " +
            "            )c  WHERE number= ${number}  ")
    int getRank(@Param("examId") String examId, @Param("number") String number);

    @Select("SELECT a.*,FROM_UNIXTIME(startTime) startTimeStr,FROM_UNIXTIME(endTime) endTimeStr,FROM_UNIXTIME(submitTime) submitTimeStr from v_exam_candidate a where 1 = 1  ${where}  order by releaseStatus asc, startTime desc  ")
    List<JSONObject> queryPersonExamAll(@Param("where") String where);



}
