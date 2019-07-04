package team.work.core.service;

import java.util.List;
import team.work.utils.bean.Where;
import team.work.core.model.Options;
import team.work.core.model.Options;
import team.work.utils.bean.Where;


public interface IOptionsService {
   /******************* CURD ********************/
   // 创建
   Options createOptions(Options model);
   // 删除
   Boolean deleteOptions(Object ids,String reviser);
   // 修改
   Options updateOptions(Options model);
   // 查询
   List<Options> findByIds(Object ids);
   // 属于
   Boolean exist(List<Where> w);
   // 查询一个id是否存在
   Boolean existId(Object id);
   /******************* CURD ********************/

}
