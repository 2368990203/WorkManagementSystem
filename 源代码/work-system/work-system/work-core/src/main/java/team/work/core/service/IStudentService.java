package team.work.core.service;

import java.util.List;
import team.work.utils.bean.Where;
import team.work.core.model.Student;

public interface IStudentService {
   /******************* CURD ********************/ 
   // 创建 
   Student createStudent(Student model); 
   // 删除 
   Boolean deleteStudent(Object ids,String reviser);
   // 修改 
   Student updateStudent(Student model); 
   // 查询 
   List<Student> findByIds(Object ids);
   // 属于 
   Boolean exist(List<Where> w); 
   // 查询一个id是否存在 
   Boolean existId(Object id); 
   /******************* CURD ********************/ 
 
}