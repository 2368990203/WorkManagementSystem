package team.work.core.service;

import com.alibaba.fastjson.JSONObject;
import team.work.utils.bean.Where;
import team.work.core.model.SysGlobalConfig;
import team.work.core.model.SysGlobalConfig;
import team.work.utils.bean.Where;

import java.util.List;


public interface ISysGlobalConfigService {
   /******************* CURD ********************/
   // 创建
   SysGlobalConfig createGlobalConfig(SysGlobalConfig model);
   // 删除
   Boolean deleteGlobalConfig(Object ids, String reviser);
   // 修改
   SysGlobalConfig updateGlobalConfig(SysGlobalConfig model);
   // 查询
   List<SysGlobalConfig> findByIds(Object ids);
   // 属于
   Boolean exist(List<Where> w);
   Boolean existSystemId(Object systemId);
   // 查询一个id是否存在
   Boolean existId(Object id);
   /******************* CURD ********************/

   Boolean config(List<SysGlobalConfig> models);
   JSONObject getSetting();
}
