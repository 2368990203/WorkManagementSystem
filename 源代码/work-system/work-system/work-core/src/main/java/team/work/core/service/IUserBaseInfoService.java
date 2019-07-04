package team.work.core.service;

import java.util.List;
import team.work.utils.bean.Where;
import team.work.core.model.UserBaseInfo;
import team.work.core.model.UserBaseInfo;
import team.work.utils.bean.Where;


public interface IUserBaseInfoService {
   /******************* CURD ********************/
   // 创建
   UserBaseInfo createUserBaseInfo(UserBaseInfo model);
   // 删除
   Boolean deleteUserBaseInfo(Object ids,String reviser);
   // 修改
   UserBaseInfo updateUserBaseInfo(UserBaseInfo model);
   // 查询
   List<UserBaseInfo> findByIds(Object ids);
   // 属于
   Boolean exist(List<Where> w);
   // 查询一个id是否存在
   Boolean existId(Object id);
   /******************* CURD ********************/

}
