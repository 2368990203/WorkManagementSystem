package team.work.core.service;

import java.util.List;
import team.work.utils.bean.Where;
import team.work.core.model.Teacher;

public interface ITeacherService {
   /******************* CURD ********************/ 
   // 创建 
   Teacher createTeacher(Teacher model); 
   // 删除 
   Boolean deleteTeacher(Object ids,String reviser);
   // 修改 
   Teacher updateTeacher(Teacher model); 
   // 查询 
   List<Teacher> findByIds(Object ids);
   // 属于 
   Boolean exist(List<Where> w); 
   // 查询一个id是否存在 
   Boolean existId(Object id); 
   /******************* CURD ********************/ 
 
}