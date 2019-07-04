package team.work.core.service;

import com.alibaba.fastjson.JSONObject;
import team.work.utils.bean.Where;
import team.work.core.model.SysMenu;
import team.work.core.model.SysMenu;
import team.work.utils.bean.Where;

import java.util.List;


public interface ISysMenuService {

    // 创建
    SysMenu createMenu(SysMenu model);
    // 修改
    SysMenu updateMenu(SysMenu model);
    //判断名称是否存在
    Boolean existModuleName(SysMenu model);
    //读取模块列表
    List<SysMenu> getModules();
    // 删除模块
    Boolean deleteModule(Object ids, String reviser);
    //重命名模块名称
    SysMenu renameModule(SysMenu model);
    //重新排序模块顺序sort
    List<SysMenu> sortModules(List<SysMenu> models);

    // 查询一个id是否存在
    Boolean existId(Object id);
    // 属于
    Boolean exist(List<Where> w);

    //读取菜单
    List<JSONObject> getMenu(Object parentId);

    //读取系统下所有权限
    List<JSONObject> getAllMenu();


    //查询菜单树
    List<JSONObject> queryList(String where);

    //查询菜单详情
    List<JSONObject> queryMenus(String where);

    //删除子菜单和按钮权限
    Boolean deleteSubMenu(Object ids,String reviser);


}
