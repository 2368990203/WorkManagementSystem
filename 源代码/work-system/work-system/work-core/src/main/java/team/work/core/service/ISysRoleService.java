package team.work.core.service;

import com.alibaba.fastjson.JSONObject;

import team.work.utils.bean.Where;
import team.work.core.model.SysRole;
import team.work.core.model.SysRole;
import team.work.utils.bean.Where;

import java.util.List;


public interface ISysRoleService {
   /******************* CURD ********************/
   // 创建
   SysRole createRole(SysRole model);
   // 删除
   Boolean deleteRole(Object ids, String reviser);
   // 修改
   SysRole updateRole(SysRole model);
   // 查询
   List<SysRole> findByIds(Object ids);
   // 属于
   Boolean exist(List<Where> w);
   // 查询一个id是否存在
   Boolean existId(Object id);
   /******************* CURD ********************/

   void initDefault(String systemId);
   SysRole getDefaultRole(String systemId);
   List<JSONObject> getAuthTreeByRoleId(String roleId);
   List<JSONObject> getAuthInfoByRoleId(String roleId);
   Boolean configRole(String roleId,List<JSONObject> menuList,List<JSONObject> btnsList);

   List<JSONObject> getRoleInfo();


}
