package team.work.core.service;

import java.util.List;
import team.work.utils.bean.Where;
import team.work.core.model.MajorInfo;
import team.work.core.model.MajorInfo;
import team.work.utils.bean.Where;


public interface IMajorInfoService {
   /******************* CURD ********************/
   // 创建
   MajorInfo createMajorInfo(MajorInfo model);
   // 删除
   Boolean deleteMajorInfo(Object ids,String reviser);
   // 修改
   MajorInfo updateMajorInfo(MajorInfo model);
   // 查询
   List<MajorInfo> findByIds(Object ids);
   // 属于
   Boolean exist(List<Where> w);
   // 查询一个id是否存在
   Boolean existId(Object id);
   /******************* CURD ********************/

}
