package team.work.core.service;

import java.util.List;
import team.work.utils.bean.Where;
import team.work.core.model.ExamCandidate;
import team.work.core.model.ExamCandidate;
import team.work.utils.bean.Where;


public interface IExamCandidateService {
   /******************* CURD ********************/
   // 创建
   ExamCandidate createExamCandidate(ExamCandidate model);
   // 删除
   Boolean deleteExamCandidate(Object ids,String reviser);
   // 修改
   ExamCandidate updateExamCandidate(ExamCandidate model) throws Exception;
   // 查询
   List<ExamCandidate> findByIds(Object ids);
   // 属于
   Boolean exist(List<Where> w);
   // 查询一个id是否存在
   Boolean existId(Object id);
   /******************* CURD ********************/

}
