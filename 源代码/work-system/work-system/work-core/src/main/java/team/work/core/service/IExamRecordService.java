package team.work.core.service;

import java.util.List;
import team.work.utils.bean.Where;
import team.work.core.model.ExamRecord;
import team.work.core.model.ExamRecord;
import team.work.utils.bean.Where;


public interface IExamRecordService {
   /******************* CURD ********************/
   // 创建
   ExamRecord createExamRecord(ExamRecord model);
   // 删除
   Boolean deleteExamRecord(Object ids,String reviser);
   // 修改
   ExamRecord updateExamRecord(ExamRecord model);
   // 查询
   List<ExamRecord> findByIds(Object ids);
   // 属于
   Boolean exist(List<Where> w);
   // 查询一个id是否存在
   Boolean existId(Object id);
   /******************* CURD ********************/

}
