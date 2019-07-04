package team.work.core.service.impl;

import com.alibaba.fastjson.JSONObject;
import team.work.utils.base.TServiceImpl;
import team.work.utils.bean.Where;
import team.work.utils.convert.*;
import team.work.core.mapper.SysMenuMapper;
import team.work.core.model.SysMenu;
import team.work.core.model.SysMenuAuth;
import team.work.core.service.ISysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.work.core.mapper.SysMenuMapper;
import team.work.core.model.SysMenu;
import team.work.core.model.SysMenuAuth;
import team.work.utils.base.TServiceImpl;
import team.work.utils.bean.Where;
import team.work.utils.convert.*;

import java.util.ArrayList;
import java.util.List;


@Service
public class SysMenuService extends TServiceImpl<SysMenuMapper, SysMenu> implements ISysMenuService {

    @Autowired
    private SysMenuAuthService menuAuthService;

    /******************* 权限模块 ********************/
    // 创建模块
    @Override
    public SysMenu createMenu(SysMenu model) {
        if (this.insert(model))
            return this.selectById(model.getId());
        return null;
    }

    @Override
    public SysMenu updateMenu(SysMenu model) {
        if (this.update(model))
            return this.selectById(model.getId());
        return null;
    }

    //读取模块列表
    @Override
    public List<SysMenu> getModules() {
        where = W.f(
                W.and("parentId", "eq", 0),
                W.order("sort", "asc")
        );
        return this.query(where);
    }

    //判断模块名称是否存在
    @Override
    public Boolean existModuleName(SysMenu model) {
        where = W.f(
                W.and("parentId", "eq", 0),
                W.and("name", "eq", model.getName()),
                W.field("1")
        );
        return this.query(where).size() > 0;
    }

    // 删除模块
    @Override
    public Boolean deleteModule(Object ids, String reviser) {
        return this.delete(ids, reviser);
    }

    //重命名模块名称
    @Override
    public SysMenu renameModule(SysMenu model) {
        if (this.update(model))
            return this.selectById(model.getId());
        return null;
    }

    //重新排序模块顺序sort
    @Transactional
    @Override
    public List<SysMenu> sortModules(List<SysMenu> models) {
        List<String> ids = new ArrayList<String>();
        for (SysMenu model : models) {
            ids.add(model.getId());
            this.update(model);
        }
        return this.selectByIds(String.join(",", ids));
    }


    /******************* 权限模块 ********************/


    /******************* 公共函数 ********************/
    @Override
    public Boolean existId(Object id) {
        where = W.f(
                W.and("id", "eq", id),
                W.field("1")
        );
        return this.query(where).size() > 0;
    }

    @Override
    public Boolean exist(List<Where> w) {
        w.add(new Where("1"));
        return this.query(w).size() > 0;
    }

    @Override
    public List<JSONObject> getMenu(Object parentId) {
        //读取父级所有菜单
        where = W.f(
                W.and("parentId", "eq", parentId),
                W.order("sort", "asc")
        );
        List<SysMenu> parentSysMenus = this.query(where);
        List<SysMenu> subSysMenus = new ArrayList<SysMenu>();
        List<SysMenuAuth> auths = new ArrayList<>();

        List<JSONObject> list = new ArrayList<JSONObject>();

        if (parentSysMenus.size() > 0) {
            String parentIds = L.getAttrs(parentSysMenus, "id");
            //读取父级下所有子菜单
            where = W.f(
                    W.and("parentId", "in", parentIds),
                    W.order("sort", "asc")
            );
            subSysMenus = this.query(where);

            String subMenuIds = L.getAttrs(subSysMenus, "id");
            //子菜单权限
            where = W.f(
                    W.and("menuId", "in", subMenuIds),
                    W.and("createTime", "asc")
            );
            auths = menuAuthService.query(where);
            list = formatMenu(parentSysMenus, subSysMenus, auths);
        }
        //读取菜单按钮信息
        return list;
    }

    //读取子系统下所有权限
    @Override
    public List<JSONObject> getAllMenu() {

        //模块
        where = W.f(
                W.and("parentId", "eq", 0),
                W.order("sort", "asc")
        );
        List<SysMenu> modules = this.query(where);

        List<SysMenu> sysMenus = new ArrayList<>();
        List<SysMenu> subSysMenus = new ArrayList<>();
        List<SysMenuAuth> auths = new ArrayList<>();

        if (modules.size() > 0) {
            String moduleIds = L.getAttrs(modules, "id");
            //父级菜单
            where = W.f(
                    W.and("parentId", "in", moduleIds),
                    W.order("sort", "asc")
            );
            sysMenus = this.query(where);

            if (sysMenus.size() > 0) {
                String menuIds = L.getAttrs(sysMenus, "id");
                //子集菜单
                where = W.f(
                        W.and("parentId", "in", menuIds),
                        W.order("sort", "asc")
                );
                subSysMenus = this.query(where);
                String subMenuIds = L.getAttrs(subSysMenus, "id");
                //子菜单权限
                where = W.f(
                        W.and("menuId", "in", subMenuIds),
                        W.and("createTime", "asc")
                );
                auths = menuAuthService.query(where);
            }
        }
        //数据整合
        return formatMenu(modules, sysMenus, subSysMenus, auths);
    }

    //数据整合
    private List<JSONObject> formatMenu(List<SysMenu> modules,
                                        List<SysMenu> sysMenus,
                                        List<SysMenu> subSysMenus,
                                        List<SysMenuAuth> auths) {
        List<JSONObject> list = new ArrayList<JSONObject>();
        if (modules.size() > 0) {
            for (SysMenu module : modules) {
                //模块
                JSONObject mModule = new JSONObject();
                mModule.put("id", module.getId());
                mModule.put("url", "/");
                mModule.put("name", module.getName());
                mModule.put("icon", module.getIcon());
                mModule.put("sort", module.getSort());
                mModule.put("parentId", module.getParentId());

                List<SysMenu> mSysMenus = new ArrayList<>();
                List<SysMenu> mSubSysMenus = new ArrayList<>();
                if (sysMenus.size() > 0) {
                    for (SysMenu sysMenu : sysMenus) {
                        if (V.isEqual(sysMenu.getParentId(), module.getId())) {
                            mSysMenus.add(sysMenu);
                            for (SysMenu sub : subSysMenus) {
                                if (V.isEqual(sub.getParentId(), sysMenu.getId())) {
                                    mSubSysMenus.add(sub);
                                }
                            }
                        }
                    }
                    mModule.put("module", formatMenu(mSysMenus, mSubSysMenus, auths));
                    list.add(mModule);
                }
            }
        }

        return list;
    }

    private List<JSONObject> formatMenu(List<SysMenu> sysMenus,
                                        List<SysMenu> subSysMenus,
                                        List<SysMenuAuth> auths) {
        List<JSONObject> list = new ArrayList<JSONObject>();
        if (sysMenus.size() > 0) {
            for (SysMenu sysMenu : sysMenus) {
                //父级
                JSONObject parent = new JSONObject();
                parent.put("id", sysMenu.getId());
                parent.put("url", "/");
                parent.put("name", sysMenu.getName());
                parent.put("icon", sysMenu.getIcon());
                parent.put("sort", sysMenu.getSort());
                parent.put("parentId", sysMenu.getParentId());
                //子集
                List<JSONObject> subs = new ArrayList<JSONObject>();

                if (subSysMenus.size() > 0) {
                    for (SysMenu s : subSysMenus) {
                        if (V.isEqual(s.getParentId(), sysMenu.getId())) {
                            JSONObject sub = new JSONObject();
                            sub.put("id", s.getId());
                            sub.put("name", s.getName());
                            sub.put("url", s.getUrl());
                            sub.put("icon", s.getIcon());
                            sub.put("code", s.getCode());
                            sub.put("sort", s.getSort());
                            sub.put("parentId", s.getParentId());
                            sub.put("hide", s.getHide());

                            List<JSONObject> btns = new ArrayList<>();
                            if (auths.size() > 0) {
                                for (SysMenuAuth auth : auths) {
                                    if (V.isEqual(auth.getMenuId(), s.getId())) {
                                        JSONObject btn = new JSONObject();
                                        btn.put("id", auth.getId());
                                        btn.put("name", auth.getName());
                                        btn.put("menuId", auth.getMenuId());
                                        btn.put("parentId", auth.getMenuId());
                                        btn.put("fun", auth.getFun());
                                        btn.put("code", auth.getCode());
                                        btns.add(btn);
                                    }
                                }
                            }
                            sub.put("btns", btns);
                            //权限按钮
                            subs.add(sub);
                        }
                    }
                }
                parent.put("sub", subs);
                list.add(parent);
            }
        }
        return list;
    }

    //查询菜单树
    @Override
    public List<JSONObject> queryList(String where) {
        List<JSONObject> list = baseMapper.queryList(where);
        return F.f2l(list, "id", "parentId");
    }

    //查询菜单信息
    @Override
    public List<JSONObject> queryMenus(String roleId) {
        List<JSONObject> list = baseMapper.queryMenus(roleId);
        return F.f2l(list, "id", "parentId");
    }

    //删除子菜单和按钮权限
    @Transactional
    @Override
    public Boolean deleteSubMenu(Object ids, String reviser) {
        if (this.delete(ids, reviser)) {
            String wKey = S.apppend(" and menuId  in (", ids, ") ");
            List<JSONObject> menuAuths = menuAuthService.queryList(wKey);

            String authIds = "";
            int size = menuAuths.size();
            for (int i = 0; i < size; i++) {
                authIds += menuAuths.get(i).getString("id");
                if (i + 1 != size) {
                    authIds += ",";
                }
            }
            if(authIds!=null&&authIds.length()>0){
            if (menuAuthService.delete(authIds, reviser)) {
                return true;
            } else {
                return false;
            }}else{
                return  true;
            }
        } else {
            return false;

        }

    }


}
