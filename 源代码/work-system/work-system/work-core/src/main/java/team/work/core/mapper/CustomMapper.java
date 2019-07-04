package team.work.core.mapper;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


public interface CustomMapper extends BaseMapper<JSONObject> {



    @Select("select * from v_user_info WHERE id=#{id} and isDel=0")
    JSONObject getUserById(@Param("id") String id);

    @Select("SELECT * from v_message_user WHERE number= #{number}  "      )
    JSONObject  getUserByNumber(@Param("number") String number);

    @Select("SELECT token openId from sys_user_token WHERE userId =#{userId}  and type=#{type} and isDel =0  "      )
    JSONObject  getOpenIdByIdAndType(@Param("userId") String userId,@Param("type") String type);


}
