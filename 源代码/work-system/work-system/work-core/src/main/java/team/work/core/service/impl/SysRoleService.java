package team.work.core.service.impl;

import com.alibaba.fastjson.JSONObject;
import team.work.core.model.*;
import team.work.utils.base.TServiceImpl;
import team.work.utils.bean.Where;
import team.work.utils.convert.*;
import team.work.utils.kit.IdKit;
import team.work.core.mapper.SysRoleMapper;
import team.work.core.service.ISysRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.work.core.model.SysRole;
import team.work.core.model.SysRoleAuth;
import team.work.core.model.SysRoleMenu;
import team.work.utils.base.TServiceImpl;
import team.work.utils.bean.Where;
import team.work.utils.convert.J;
import team.work.utils.convert.V;
import team.work.utils.convert.W;
import team.work.utils.kit.IdKit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class SysRoleService extends TServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {
    @Autowired
    private SysMenuService menuService;
    @Autowired
    private SysMenuAuthService menuAuthService;
    @Autowired
    private SysRoleMenuService roleMenuService;
    @Autowired
    private SysRoleAuthService roleAuthService;

    /**************************CURD begin******************************/
    // 创建
    @Override
    public SysRole createRole(SysRole model) {
        //先处理 isDefault
        if (model.getIsDefault() == 1) {
            initDefault(model.getSystemId());
        }
        if (this.insert(model))
            return this.selectById(model.getId());
        return null;
    }

    // 删除
    @Override
    public Boolean deleteRole(Object ids, String reviser) {
        return this.delete(ids, reviser);
    }

    // 修改
    @Override
    public SysRole updateRole(SysRole model) {
        //先处理 isDefault
        if (model.getIsDefault() == 1) {
            initDefault(model.getSystemId());
        }
        if (this.update(model))
            return this.selectById(model.getId());
        return null;
    }

    // 查询
    @Override
    public List<SysRole> findByIds(Object ids) {
        return this.selectByIds(ids);
    }

    // 属于
    @Override
    public Boolean exist(List<Where> w) {
        w.add(new Where("1"));
        return this.query(w).size() > 0;
    }

    // 查询一个id是否存在
    @Override
    public Boolean existId(Object id) {
        where = W.f(
                W.and("id", "eq", id),
                W.field("1")
        );
        return this.query(where).size() > 0;
    }

    @Override
    public void initDefault(String systemId) {
        baseMapper.initDefault(systemId);
    }

    @Override
    public SysRole getDefaultRole(String systemId) {
        where = W.f(
//                W.and("systemId","eq",systemId),
                W.and("isDefault", "eq", 1)
        );
        List<SysRole> sysRoles = this.query(where);
        return sysRoles.size() > 0 ? sysRoles.get(0) : null;
    }


    /**************************CURD end********************************/

    //获取权限树
    //Updated by Liwen on 2019/02/13
    @Override
    public List<JSONObject> getAuthTreeByRoleId(String roleId) {

        List<JSONObject> auths = new ArrayList<JSONObject>();

        //获取角色权限
        List<JSONObject> rms = roleMenuService.queryRoleMenus(roleId);
        List<JSONObject> ras = roleAuthService.queryRoleAuths(roleId);

        //获取菜单
        List<JSONObject> menus = menuService.queryList("");
        for (JSONObject menu : menus) {
            menu.put("type", "menu");
            menu.put("check", 0);
            if (rms.size() > 0) {
                for (JSONObject rm : rms) {
                    if (V.isEqual(rm.getString("menuId"), menu.getString("id"))) {
                        menu.put("check", 1);
                    }
                }
            }
            auths.add(menu);
        }

        List<JSONObject> menuAuths = menuAuthService.queryList("");
        for (JSONObject menuAuth : menuAuths) {
            menuAuth.put("type", "auth");
            menuAuth.put("check", 0);
            if (ras.size() > 0) {
                for (JSONObject ra : ras) {
                    if (V.isEqual(menuAuth.getString("id"), ra.getString("authId"))) {
                        menuAuth.put("check", 1);
                    }
                }
            }
            auths.add(menuAuth);
        }
        return auths;
    }

    //获取权限信息
    //Updated by Liwen on 2019/02/14
    @Override
    public List<JSONObject> getAuthInfoByRoleId(String roleId) {

        //获取角色菜单
        List<JSONObject> menus = menuService.queryMenus(roleId);
        List<JSONObject> auths = menuAuthService.queryAuths(roleId);

        List<JSONObject> list = new ArrayList<JSONObject>();
        List<JSONObject> modules = new ArrayList<>();

        List<JSONObject> parents = new ArrayList<>();
        List<JSONObject> subs = new ArrayList<>();

        // 取模块
        for (JSONObject m1 : menus) {
            if (V.isEqual(m1.getString("parentId"), "0")) {
                modules.add(m1);
            }
        }
        // 取父级
        for (JSONObject module : modules) {
            for (JSONObject menu : menus) {
                if (V.isEqual(menu.getString("parentId"), module.getString("id"))) {
                    parents.add(menu);
                }
            }
        }
        // 取子级
        for (JSONObject parent : parents) {
            for (JSONObject subMenu : menus) {
                if (V.isEqual(subMenu.getString("parentId"), parent.getString("id"))) {
                    List<JSONObject> btns = new ArrayList<>();
                    if (auths.size() > 0) {
                        for (JSONObject auth : auths) {
                            if (V.isEqual(auth.getString("menuId"), subMenu.getString("id"))) {
                                btns.add(auth);
                            }
                        }
                    }
                    subMenu.put("btns", btns);
                    //权限按钮
                    subs.add(subMenu);
                }
            }
        }

        for (JSONObject module : modules) {
            //模块
            module.put("url", "/");
            List<JSONObject> ps = new ArrayList<JSONObject>();
            if (parents.size() > 0) {
                for (JSONObject parent : parents) {
                    if (V.isEqual(parent.getString("parentId"), module.getString("id"))) {
                        //父级
                        parent.put("url", "/");
                        List<JSONObject> mSub = new ArrayList<JSONObject>();
                        if (subs.size() > 0) {
                            for (JSONObject sub : subs) {
                                if (V.isEqual(sub.getString("parentId"), parent.getString("id"))) {
                                    mSub.add(sub);
                                }
                            }
                        }
                        parent.put("sub", mSub);
                        ps.add(parent);
                    }
                }
            }
            module.put("module", ps);
            list.add(module);
        }
        return list;
    }


    //更改权限配置
    //Updated by Liwen on 2019/02/12
    /*
    处理逻辑：
    1、格式化前端提交菜单和权限配置信息
    2、获取数据库的菜单和权限配置信息
    3、将前端提交数据和数据库数据进行比对
    4、得到添加和删除数据列表
    5、进行批量添加或删除操作
     */
    @Transactional
    @Override
    public Boolean configRole(String roleId, List<JSONObject> menuList, List<JSONObject> btnsList) {

        //获取配置信息
        List<SysRoleMenu> menus = J.o2l(menuList, SysRoleMenu.class);
        List<SysRoleAuth> auths = J.o2l(btnsList, SysRoleAuth.class);

        //获取数据库角色菜单信息
        List<SysRoleMenu> oldMenus = baseMapper.getRoleMenuByRoleId(roleId);
        //获取数据库角色权限信息
        List<SysRoleAuth> oldAuths = baseMapper.getRoleAuthByRoleId(roleId);

        //比对角色菜单数据  实现批量添加删除处理
        JSONObject difMenus = getDiffrent(menus, oldMenus);
        if (!V.isEmpty(difMenus.get("add"))) {
            List<SysRoleMenu> addMenus = (List<SysRoleMenu>) difMenus.get("add");
            //进行批量添加操作
            if (addMenus.size() > 0) {
                String values = "";
                int i = 1;
                for (SysRoleMenu menu : addMenus) {
                    menu.setId(IdKit.getId(SysRoleMenu.class));
                    String value = "";
                    value += "('" + menu.getId() + "',";
                    value += "'" + roleId + "',";
                    value += "'" + menu.getMenuId() + "')";
                    if (i != addMenus.size()) {
                        value += ",";
                    }
                    i++;
                    values += value;
                }
                baseMapper.insertRoleMenus(values);
            }
        }
        if (!V.isEmpty(difMenus.get("del"))) {
            List<SysRoleMenu> delMenus = (List<SysRoleMenu>) difMenus.get("del");
            //进行批量删除操作
            if (delMenus.size() > 0) {
                String values = "";
                int i = 1;
                for (SysRoleMenu menu : delMenus) {
                    String value = "";
                    value += "'" + menu.getId() + "'";
                    if (i != delMenus.size()) {
                        value += ",";
                    }
                    i++;
                    values += value;
                }
                baseMapper.deleteRoleMenus(values);
            }
        }

        //比对角色权限数据  实现批量添加删除处理
        JSONObject difAuths = getDiffrent(auths, oldAuths);
        if (!V.isEmpty(difAuths.get("add"))) {
            List<SysRoleAuth> addAuths = (List<SysRoleAuth>) difAuths.get("add");
            //进行批量添加操作
            if (addAuths.size() > 0) {
                String values = "";
                int i = 1;
                for (SysRoleAuth auth : addAuths) {
                    auth.setId(IdKit.getId(SysRoleAuth.class));
                    String value = "";
                    value += "('" + auth.getId() + "',";
                    value += "'" + roleId + "',";
                    value += "'" + auth.getAuthId() + "')";
                    if (i != addAuths.size()) {
                        value += ",";
                    }
                    i++;
                    values += value;
                }
                baseMapper.insertRoleAuths(values);
            }
        }
        if (!V.isEmpty(difAuths.get("del"))) {
            List<SysRoleAuth> delAuths = (List<SysRoleAuth>) difAuths.get("del");
            //进行批量删除操作
            if (delAuths.size() > 0) {
                String values = "";
                int i = 1;
                for (SysRoleAuth auth : delAuths) {
                    String value = "";
                    value += "'" + auth.getId() + "'";
                    if (i != delAuths.size()) {
                        value += ",";
                    }
                    i++;
                    values += value;
                }
                baseMapper.deleteRoleAuths(values);
            }
        }
        return true;
    }

    @Override
    public List<JSONObject> getRoleInfo() {
        return baseMapper.getRoleInfo();
    }


    public List<JSONObject> getUserRoleByUserId(String userId) {
        return baseMapper.getUserRoleByUserId(userId);
    }

    //比对数据库和用户提交权限 增加删除内容
    private <T> JSONObject getDiffrent(List<T> newObjs, List<T> oldObjs) {
        JSONObject obj = new JSONObject();

        //判断初始权限添加
        if (V.isEmpty(oldObjs)) {
            obj.put("add", newObjs);
            obj.put("del", null);
            return obj;
        }

        //判断清空所有权限
        if (V.isEmpty(newObjs)) {
            obj.put("add", null);
            obj.put("del", oldObjs);
            return obj;
        }

        List<T> addObjs = new ArrayList<T>();
        List<T> delObjs = new ArrayList<T>();

        Map<String, T> addMap = new HashMap<String, T>(oldObjs.size());
        Map<String, T> delMap = new HashMap<String, T>(newObjs.size());

        //比对增加数据
        for (T t : oldObjs) {
            addMap.put(t.toString(), t);
        }
        for (T t : newObjs) {
            T check = addMap.get(t.toString());
            if (check == null) {
                addObjs.add(t);
            }
        }

        //比对删除数据
        for (T t : newObjs) {
            delMap.put(t.toString(), t);
        }
        for (T t : oldObjs) {
            T check = delMap.get(t.toString());
            if (check == null) {
                delObjs.add(t);
            }
        }

        obj.put("add", addObjs);
        obj.put("del", delObjs);
        return obj;

    }

}
