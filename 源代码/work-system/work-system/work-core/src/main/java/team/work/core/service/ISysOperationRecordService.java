package team.work.core.service;

import team.work.core.model.SysOperationRecord;
import team.work.utils.bean.Where;
import team.work.core.model.SysOperationRecord;
import team.work.utils.bean.Where;

import java.util.List;


public interface ISysOperationRecordService {
   /******************* CURD ********************/
   // 创建
   SysOperationRecord createSysOperationRecord(SysOperationRecord model);
   // 删除
   Boolean deleteSysOperationRecord(Object ids, String reviser);
   // 修改
   SysOperationRecord updateSysOperationRecord(SysOperationRecord model);
   // 查询
   List<SysOperationRecord> findByIds(Object ids);
   // 属于
   Boolean exist(List<Where> w);
   // 查询一个id是否存在
   Boolean existId(Object id);
   /******************* CURD ********************/

}
