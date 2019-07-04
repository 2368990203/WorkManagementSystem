package team.work.core.mapper;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import team.work.core.model.UserInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import team.work.core.model.UserInfo;

import java.util.List;


public interface UserInfoMapper extends BaseMapper<UserInfo> {
        @Select("select count(1) total from user_info where 1=1 and isDel=0 ${where} ")
        JSONObject getPageCount(@Param("where") String where);

        @Select("SELECT a.* FROM user_info a " +
                "JOIN (SELECT id from user_info where  1=1  and isDel=0 ${where} " +
                "LIMIT #{index}, #{size})b ON a.id=b.id order by a.createTime desc ")
        List<JSONObject> getPage(@Param("where") String where,
                                 @Param("index") int index,
                                 @Param("size") int size);

}
