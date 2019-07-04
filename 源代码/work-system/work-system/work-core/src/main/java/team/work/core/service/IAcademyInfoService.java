package team.work.core.service;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import team.work.utils.bean.Where;
import team.work.utils.bean.Page;
import team.work.core.model.AcademyInfo;
import team.work.core.model.AcademyInfo;
import team.work.utils.bean.Page;
import team.work.utils.bean.Where;


public interface IAcademyInfoService {
   /******************* CURD ********************/
   // 创建
   AcademyInfo createAcademyInfo(AcademyInfo model);
   // 删除
   Boolean deleteAcademyInfo(Object ids,String reviser);
   // 修改
   AcademyInfo updateAcademyInfo(AcademyInfo model);
   // 查询
   List<AcademyInfo> findByIds(Object ids);
   // 属于
   Boolean exist(List<Where> w);
   // 查询一个id是否存在
   Boolean existId(Object id);
   /******************* CURD ********************/
   public Page page(int index, int pageSize, String w);

   public List<JSONObject> getCampusByCode(String w);

}
