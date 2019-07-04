package team.work.core.service;

import java.util.List;
import team.work.utils.bean.Where;
import team.work.core.model.Course;

public interface ICourseService {
   /******************* CURD ********************/ 
   // 创建 
   Course createCourse(Course model); 
   // 删除 
   Boolean deleteCourse(Object ids,String reviser);
   // 修改 
   Course updateCourse(Course model); 
   // 查询 
   List<Course> findByIds(Object ids);
   // 属于 
   Boolean exist(List<Where> w); 
   // 查询一个id是否存在 
   Boolean existId(Object id); 
   /******************* CURD ********************/ 
 
}