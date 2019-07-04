package team.work.core.service;

import team.work.core.model.UserInfo;
import team.work.utils.bean.Page;
import team.work.utils.bean.Where;
import team.work.core.model.UserInfo;
import team.work.utils.bean.Page;
import team.work.utils.bean.Where;

import java.util.List;


public interface IUserInfoService {
   /******************* CURD ********************/
   // 创建
   UserInfo createUserInfo(UserInfo model);
   // 删除
   Boolean deleteUserInfo(Object ids, String reviser);
   // 修改
   UserInfo updateUserInfo(UserInfo model);
   // 查询
   List<UserInfo> findByIds(Object ids);
   // 属于
   Boolean exist(List<Where> w);
   // 查询一个id是否存在
   Boolean existId(Object id);
   /******************* CURD ********************/
   public Page page(int index, int pageSize, String w);

}
