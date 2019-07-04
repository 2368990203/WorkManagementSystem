package team.work.core.service;

import java.util.List;
import team.work.utils.bean.Where;
import team.work.core.model.ExamQuestion;
import team.work.core.model.ExamQuestion;
import team.work.utils.bean.Where;


public interface IExamQuestionService {
   /******************* CURD ********************/
   // 创建
   ExamQuestion createExamQuestion(ExamQuestion model);
   // 删除
   Boolean deleteExamQuestion(Object ids,String reviser);
   // 修改
   ExamQuestion updateExamQuestion(ExamQuestion model);
   // 查询
   List<ExamQuestion> findByIds(Object ids);
   // 属于
   Boolean exist(List<Where> w);
   // 查询一个id是否存在
   Boolean existId(Object id);
   /******************* CURD ********************/

}
