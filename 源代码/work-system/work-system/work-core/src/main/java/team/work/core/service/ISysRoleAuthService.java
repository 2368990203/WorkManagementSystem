package team.work.core.service;

import com.alibaba.fastjson.JSONObject;
import team.work.utils.bean.Where;
import team.work.core.model.SysRoleAuth;
import team.work.core.model.SysRoleAuth;
import team.work.utils.bean.Where;

import java.util.List;


public interface ISysRoleAuthService {
   /******************* CURD ********************/
   // 创建
   SysRoleAuth createRoleAuth(SysRoleAuth model);
   // 删除
   Boolean deleteRoleAuth(Object ids, String reviser);
   // 修改
   SysRoleAuth updateRoleAuth(SysRoleAuth model);
   // 查询
   List<SysRoleAuth> findByIds(Object ids);
   // 属于
   Boolean exist(List<Where> w);
   // 查询一个id是否存在
   Boolean existId(Object id);
   /******************* CURD ********************/
   //查询列表信息
   List<JSONObject> queryRoleAuths(String roleId);

}
