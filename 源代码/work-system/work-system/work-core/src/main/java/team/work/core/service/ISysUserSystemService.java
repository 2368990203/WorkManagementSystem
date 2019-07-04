package team.work.core.service;

import team.work.utils.bean.Where;
import team.work.core.model.SysUserSystem;
import team.work.core.model.SysUserSystem;
import team.work.utils.bean.Where;

import java.util.List;


public interface ISysUserSystemService {
   /******************* CURD ********************/
   // 创建
   SysUserSystem createUserSystem(SysUserSystem model);
   // 删除
   Boolean deleteUserSystem(Object ids, String reviser);
   // 修改
   SysUserSystem updateUserSystem(SysUserSystem model);
   // 查询
   List<SysUserSystem> findByIds(Object ids);
   // 属于
   Boolean exist(List<Where> w);
   // 查询一个id是否存在
   Boolean existId(Object id);
   /******************* CURD ********************/

}
