package team.work.core.service;

import java.util.List;
import team.work.utils.bean.Where;
import team.work.core.model.Exam;
import team.work.core.model.Exam;
import team.work.utils.bean.Where;


public interface IExamService {
   /******************* CURD ********************/
   // 创建
   Exam createExam(Exam model);
   // 删除
   Boolean deleteExam(Object ids,String reviser);
   // 修改
   Exam updateExam(Exam model);
   // 查询
   List<Exam> findByIds(Object ids);
   // 属于
   Boolean exist(List<Where> w);
   // 查询一个id是否存在
   Boolean existId(Object id);
   /******************* CURD ********************/

}
