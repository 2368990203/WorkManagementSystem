package team.work.core.service;

import java.util.List;
import team.work.utils.bean.Where;
import team.work.core.model.Dictionary;
import team.work.core.model.Dictionary;
import team.work.utils.bean.Where;


public interface IDictionaryService {
   /******************* CURD ********************/
   // 创建
   Dictionary createDictionary(Dictionary model);
   // 删除
   Boolean deleteDictionary(Object ids,String reviser);
   // 修改
   Dictionary updateDictionary(Dictionary model);
   // 查询
   List<Dictionary> findByIds(Object ids);
   //查询byName
   List<Dictionary> findByName(Object name);
   // 属于
   Boolean exist(List<Where> w);
   // 查询一个id是否存在
   Boolean existId(Object id);
   /******************* CURD ********************/

}
