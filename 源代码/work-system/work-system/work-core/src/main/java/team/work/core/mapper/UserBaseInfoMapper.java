package team.work.core.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import team.work.core.model.UserBaseInfo;
import team.work.core.model.UserBaseInfo;

import java.util.List;

public interface UserBaseInfoMapper extends BaseMapper<UserBaseInfo> {

    @Select("SELECT a.* FROM v_user_info a " +
            "JOIN (SELECT id from v_user_info where isDel = 0 ${where} order by createTime desc LIMIT #{index}, #{size}" +
            ")b ON a.id=b.id order by a.createTime desc ")
    List<JSONObject> getPage(@Param("where") String where,
                             @Param("index") int index,
                             @Param("size") int size);

    @Select("select count(1) total from v_user_info where isDel = 0 ${where} ")
    JSONObject getPageCount(@Param("where") String where);

    @Select("select count(1) total from user_base_info where isDel = 0  ")
    JSONObject getPageCount_default();

    @Select("SELECT a.* FROM v_user_info a where 1=1 and isDel=0 ${where}  order by createTime desc")
    List<JSONObject> queryAll(@Param("where") String where);


    @Select("SELECT * from v_user_info where isDel = 0 and number = '${number}'  ")
    JSONObject queryUserByNumber(@Param("number") String number);


}

