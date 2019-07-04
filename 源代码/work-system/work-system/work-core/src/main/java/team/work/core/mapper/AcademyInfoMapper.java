package team.work.core.mapper;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import team.work.core.model.AcademyInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


public interface AcademyInfoMapper extends BaseMapper<AcademyInfo> {
    @Select("select count(1) total from academy_info where 1=1 and isDel=0 ${where} ")
    JSONObject getPageCount(@Param("where") String where);

    @Select("SELECT a.* FROM  academy_info  a " +
            "JOIN (SELECT id from  academy_info  where  1=1  and isDel=0 ${where} " +
            ")b ON a.id=b.id order by a.createTime desc LIMIT #{index}, #{size} ")
    List<JSONObject> getPage(@Param("where") String where,
                             @Param("index") int index,
                             @Param("size") int size);


    @Select("SELECT a.campus FROM  academy_info a " +
           "where 1=1 ${where} ")
    List<JSONObject> getCampusByCode(@Param("where") String where);

    @Select("SELECT a.* FROM academy_info a where 1=1 and isDel=0 ${where}  order by createTime desc")
    List<JSONObject> queryAll(@Param("where") String where);

    @Select("SELECT a.* FROM academy_info a where 1=1 and isDel=0 and name = '${name}' order by createTime desc")
    JSONObject queryByName(@Param("name") String name);

    @Select("SELECT a.* FROM v_user_depart a where 1=1 and isDel=0 ${where}")
    List<JSONObject> queryUserDepart(@Param("where") String where);


}
