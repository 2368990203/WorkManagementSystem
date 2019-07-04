package team.work.core.service;

import java.util.List;
import team.work.utils.bean.Where;
import team.work.core.model.TeachSchedule;

public interface ITeachScheduleService {
   /******************* CURD ********************/ 
   // 创建 
   TeachSchedule createTeachSchedule(TeachSchedule model); 
   // 删除 
   Boolean deleteTeachSchedule(Object ids,String reviser);
   // 修改 
   TeachSchedule updateTeachSchedule(TeachSchedule model); 
   // 查询 
   List<TeachSchedule> findByIds(Object ids);
   // 属于 
   Boolean exist(List<Where> w); 
   // 查询一个id是否存在 
   Boolean existId(Object id); 
   /******************* CURD ********************/ 
 
}