package team.work.core.service;

import team.work.utils.bean.Where;
import team.work.core.model.SysSubsystem;
import team.work.core.model.SysSubsystem;
import team.work.utils.bean.Where;

import java.util.List;


public interface ISysSubsystemService {
   /******************* CURD ********************/
   // 创建
   SysSubsystem createSubsystem(SysSubsystem model);
   // 删除
   Boolean deleteSubsystem(Object ids, String reviser);
   // 修改
   SysSubsystem updateSubsystem(SysSubsystem model);
   // 查询
   List<SysSubsystem> findByIds(Object ids);
   // 属于
   Boolean exist(List<Where> w);
   // 查询一个id是否存在
   Boolean existId(Object id);
   /******************* CURD ********************/
   //读取
   List<SysSubsystem> get();

}
