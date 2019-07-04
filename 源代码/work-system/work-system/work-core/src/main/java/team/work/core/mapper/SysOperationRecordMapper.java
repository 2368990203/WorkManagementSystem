package team.work.core.mapper;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import team.work.core.model.SysOperationRecord;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import team.work.core.model.SysOperationRecord;

import java.util.List;


public interface SysOperationRecordMapper extends BaseMapper<SysOperationRecord> {



    @Select("SELECT u.number,u.name,a.server,a.control,a.function,a.ipAddr,a.status, " +
            "FROM_UNIXTIME(createTime) createTimeStr FROM sys_operation_record a " +
            "JOIN (SELECT id from sys_operation_record where 1=1 ${where} " +
            "order by createTime desc LIMIT #{index}, #{size})b ON a.id=b.id " +
            "left join (select number,name,id from v_user_info) u on u.id =a.creator " +
            "order by createTime desc  ")
    List<JSONObject> getPage(@Param("where") String where,
                             @Param("index") int index,
                             @Param("size") int size);

    @Select("select count(1) total from sys_operation_record where 1=1 ${where} ")
    JSONObject getPageCount(@Param("where") String where);


    @Select("SELECT u.number,u.name,a.server,a.control,a.function,a.ipAddr,a.status, " +
            "FROM_UNIXTIME(createTime) createTimeStr FROM sys_operation_record a " +
            "JOIN (SELECT id from sys_operation_record where 1=1 ${where} order by createTime desc  )b ON a.id=b.id " +
            "left join (select number,name,id from v_user_info) u on u.id =a.creator order by createTime desc ")
    List queryAll(@Param("where") String where);
}
