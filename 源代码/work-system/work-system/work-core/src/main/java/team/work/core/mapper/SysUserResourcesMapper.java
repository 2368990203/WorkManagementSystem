package team.work.core.mapper;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import team.work.core.model.SysUserResources;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import team.work.core.model.SysUserResources;

import java.util.List;


public interface SysUserResourcesMapper extends BaseMapper<SysUserResources> {
    @Select("SELECT a.id,a.size,a.fullPath,a.createTime,a.`name`,a.type FROM sys_user_resources a " +
            "JOIN (SELECT id FROM sys_user_resources " +
            "where isDel=0 and isFile=1 and parentId=#{parentId} ${where} " +
            "ORDER BY createTime DESC LIMIT #{index}, #{size}) b ON a.id=b.id")
    List<SysUserResources> getPage(@Param("parentId") String parentId,
                                   @Param("where") String where,
                                   @Param("index") int index,
                                   @Param("size") int size);

    @Select("SELECT count(1) total FROM sys_user_resources " +
            "where isDel=0 and isFile=1 and parentId=#{parentId} ${where} ORDER BY createTime desc")
    JSONObject getPageCount(@Param("parentId") String parentId, @Param("where") String where);

}
