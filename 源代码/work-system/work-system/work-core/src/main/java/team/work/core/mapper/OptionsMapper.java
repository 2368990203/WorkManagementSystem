package team.work.core.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import team.work.core.model.Options;

import java.util.List;

public interface OptionsMapper extends BaseMapper<Options> {

    @Select("SELECT a.* " +
            "FROM options a " +
            "JOIN (SELECT id from options where isDel = 0 ${where} " +
            "LIMIT #{index}, #{size})b ON a.id=b.id order by a.createTime asc ")
    List<JSONObject> getPage(@Param("where") String where,
                             @Param("index") int index,
                             @Param("size") int size);

    @Select("select count(1) total from options where isDel = 0 ${where} ")
    JSONObject getPageCount(@Param("where") String where);

    @Select("SELECT a.* FROM options a where 1=1 and isDel=0 ${where}  order by createTime desc")
    List<JSONObject> queryAll(@Param("where") String where);


    @Select("SELECT id,questionId,optionInfo,optionNumber,isAnswer FROM options a where 1=1 and isDel=0 ${where}  order by optionNumber asc, createTime desc")
    List<Options> queryByQuestionId(@Param("where") String where);

    @Select("SELECT id,questionId,optionInfo,optionNumber FROM options a where 1=1 and isDel=0 ${where}  order by optionNumber asc, createTime desc")
    List<Options> queryPCByQuestionId(@Param("where") String where);

    @Select("DELETE FROM `options` WHERE 1=1 ${where} ")
    List<Options> deleteByQuestionId(@Param("where") String where);


    @Select("SELECT a.id FROM options a where 1=1 and isDel=0 ${where}  order by optionNumber asc, createTime desc")
    List<String> queryTrueByQuestionId(@Param("where") String where);

}


