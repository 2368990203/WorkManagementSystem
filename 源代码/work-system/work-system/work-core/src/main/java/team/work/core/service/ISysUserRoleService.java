package team.work.core.service;

import team.work.utils.bean.Where;
import team.work.core.model.SysUserRole;
import team.work.core.model.SysUserRole;
import team.work.utils.bean.Where;

import java.util.List;


public interface ISysUserRoleService {
   /******************* CURD ********************/
   // 创建
   SysUserRole createUserRole(SysUserRole model);
   // 删除
   Boolean deleteUserRole(Object ids, String reviser);
   // 修改
   SysUserRole updateUserRole(SysUserRole model);
   // 查询
   List<SysUserRole> findByIds(Object ids);
   // 属于
   Boolean exist(List<Where> w);
   // 查询一个id是否存在
   Boolean existId(Object id);
   /******************* CURD ********************/
   //判断用户是否开通了子系统
   SysUserRole findByUserId(String userId);
   Boolean deleteByUserIdAndSystemId(String userId);
   Boolean setUserRole(String userId, List<SysUserRole> models);
   Boolean initSystem(String systemId, String userId);
}
