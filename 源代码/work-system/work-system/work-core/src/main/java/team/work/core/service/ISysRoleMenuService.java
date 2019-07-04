package team.work.core.service;

import com.alibaba.fastjson.JSONObject;
import team.work.utils.bean.Where;
import team.work.core.model.SysRoleMenu;
import team.work.core.model.SysRoleMenu;
import team.work.utils.bean.Where;

import java.util.List;


public interface ISysRoleMenuService {
   /******************* CURD ********************/
   // 创建
   SysRoleMenu createRoleMenu(SysRoleMenu model);
   // 删除
   Boolean deleteRoleMenu(Object ids, String reviser);
   // 修改
   SysRoleMenu updateRoleMenu(SysRoleMenu model);
   // 查询
   List<SysRoleMenu> findByIds(Object ids);
   // 属于
   Boolean exist(List<Where> w);
   // 查询一个id是否存在
   Boolean existId(Object id);
   /******************* CURD ********************/
   //查询列表信息
   List<JSONObject> queryRoleMenus(String roleId);

}
