package team.work.core.mapper;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import team.work.core.model.SysCategory;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import team.work.core.model.SysCategory;

import java.util.List;


public interface SysCategoryMapper extends BaseMapper<SysCategory> {
    @Select("SELECT a.id,a.name,a.code,a.parentId,a.createTime,a.reviseTime,c.val creator,d.val reviser FROM sys_category a " +
            "JOIN (SELECT id from sys_category where isDel=0 ${where} ORDER BY createTime desc,`code` asc LIMIT #{index}, #{size}) b " +
            "on a.id=b.id " +
            "left join sys_user_info c on c.userId=a.creator and c.attr='trueName' "+
            "left join sys_user_info d on d.userId=a.reviser and d.attr='trueName' where a.isDel=0")
    List<JSONObject> getPage(@Param("where") String where,
                             @Param("index") int index,
                             @Param("size") int size);

    @Select("select count(1) total from sys_category where isDel=0 ${where} ORDER BY createTime desc,`code` asc ")
    JSONObject getPageCount(@Param("where") String where);


    @Select("SELECT a.id,a.name,a.code,a.parentId,a.createTime,a.reviseTime,c.val creator,d.val reviser FROM sys_category a " +
            "left join sys_user_info c on c.userId=a.creator and c.attr='trueName' "+
            "left join sys_user_info d on d.userId=a.reviser and d.attr='trueName' "+
            " where a.id=#{id} and a.isDel=0 ")
    List<JSONObject> getOne(@Param("id") String id);

}
