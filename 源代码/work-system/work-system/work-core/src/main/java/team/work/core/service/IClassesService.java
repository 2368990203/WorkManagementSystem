package team.work.core.service;

import java.util.List;
import team.work.utils.bean.Where;
import team.work.core.model.Classes;

public interface IClassesService {
   /******************* CURD ********************/ 
   // 创建 
   Classes createClasses(Classes model); 
   // 删除 
   Boolean deleteClasses(Object ids,String reviser);
   // 修改 
   Classes updateClasses(Classes model); 
   // 查询 
   List<Classes> findByIds(Object ids);
   // 属于 
   Boolean exist(List<Where> w); 
   // 查询一个id是否存在 
   Boolean existId(Object id); 
   /******************* CURD ********************/ 
 
}