package team.work.core.service;

import com.alibaba.fastjson.JSONObject;
import team.work.utils.bean.Where;
import team.work.core.model.SysMenuAuth;
import team.work.core.model.SysMenuAuth;
import team.work.utils.bean.Where;

import java.util.List;


public interface ISysMenuAuthService {
   /******************* CURD ********************/
   // 创建
   SysMenuAuth createMenuAuth(SysMenuAuth model);
   // 删除
   Boolean deleteMenuAuth(Object ids, String reviser);
   // 修改
   SysMenuAuth updateMenuAuth(SysMenuAuth model);
   // 查询
   List<SysMenuAuth> findByIds(Object ids);
   // 属于
   Boolean exist(List<Where> w);
   // 查询一个id是否存在
   Boolean existId(Object id);
   /******************* CURD ********************/
   //查询列表信息
   List<JSONObject> queryList(String where);
   //查询权限信息
   List<JSONObject> queryAuths(String where);
}
