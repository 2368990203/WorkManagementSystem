package team.work.core.service;

import team.work.utils.bean.Where;
import team.work.core.model.SysUserToken;
import team.work.core.model.SysUserToken;
import team.work.utils.bean.Where;

import java.util.List;


public interface ISysUserTokenService {
   /******************* CURD ********************/
   // 创建
   SysUserToken createUserToken(SysUserToken model);
   // 删除
   Boolean deleteUserToken(Object ids, String reviser);
   // 修改
   SysUserToken updateUserToken(SysUserToken model);
   // 查询
   List<SysUserToken> findByIds(Object ids);
   // 属于
   Boolean exist(List<Where> w);
   // 查询一个id是否存在
   Boolean existId(Object id);
   /******************* CURD ********************/


}
