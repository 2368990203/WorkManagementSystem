package team.work.core.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import team.work.core.model.MajorInfo;
import team.work.core.model.MajorInfo;

import java.util.List;

public interface MajorInfoMapper extends BaseMapper<MajorInfo> {

    @Select("SELECT a.*,b.name academyName "+
            "FROM "+
            "academy_info b,major_info a "+
            "where a.academyCode=b.code and  a.isDel = 0 ${where} "+
            "ORDER BY "+
            "a.createTime ASC "+
            "LIMIT #{index},#{size}")
    List<JSONObject> getPage(@Param("where") String where,
                             @Param("index") int index,
                             @Param("size") int size);

    @Select("SELECT count(1) total  "+
            "FROM "+
            "academy_info b,major_info a "+
            "where a.academyCode=b.code and  a.isDel = 0 ${where} ")
    JSONObject getPageCount(@Param("where") String where);

    @Select("SELECT a.* FROM major_info a where 1=1 and isDel=0 ${where}  order by createTime desc")
    List<JSONObject> queryAll(@Param("where") String where);

    //通过学院代码找学院所有的专业
    @Select("SELECT id,code,name FROM major_info  where 1=1 and isDel=0 and status = 0 ${where}  order by createTime desc")
    List<JSONObject> getMajorByAcademyCode(@Param("where") String where);



}
