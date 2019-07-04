package team.work.core.service;

import team.work.core.model.SysAutoCode;
import team.work.utils.bean.Where;
import team.work.core.model.SysAutoCode;
import team.work.utils.bean.Where;


import java.util.List;


public interface ISysAutoCodeService {
   /******************* CURD ********************/
   // 创建
   SysAutoCode createAutoCode(SysAutoCode model);
   // 删除
   Boolean deleteAutoCode(Object ids, String reviser);
   // 修改
   SysAutoCode updateAutoCode(SysAutoCode model);
   // 查询
   List<SysAutoCode> findByIds(Object ids);
   // 属于
   Boolean exist(List<Where> w);
   /******************* CURD ********************/

}
