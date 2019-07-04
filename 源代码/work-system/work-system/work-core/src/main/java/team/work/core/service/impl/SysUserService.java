package team.work.core.service.impl;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import team.work.core.mapper.SysUserMapper;
import team.work.core.model.*;
import team.work.core.service.ISysUserService;
import team.work.utils.base.TServiceImpl;
import team.work.utils.bean.Page;
import team.work.utils.bean.Where;
import team.work.utils.convert.*;
import team.work.utils.kit.IdKit;
import team.work.utils.kit.MD5Kit;

import java.util.ArrayList;
import java.util.List;

@Service
public class SysUserService extends TServiceImpl<SysUserMapper, SysUser> implements ISysUserService {
    @Autowired
    private UserBaseInfoService userInfoService;
    @Autowired
    private SysRoleService roleService;
    @Autowired
    private SysUserRoleService userRoleService;
    @Autowired
    private UserBaseInfoService userBaseInfoService;
    @Autowired
    private StudentService studentService;

    @Transactional
    @Override
    public Object createUser(JSONObject object) {
        String userId = IdKit.getId(SysUser.class);
        object.put("id", userId);
        SysUser sysUser = J.o2m(object, SysUser.class);
        // 用户主表
        this.insert(sysUser);
        // 用户信息表
        //    Object info = userInfoService.optInfo(object);
//        if(!V.isEmpty(object.getString("systemId"))) {
        String roleId = object.getString("roleId");
        if (V.isEmpty(roleId)) {//如果没有选择角色权限，则选择默认
            //读取默认角色Id
            SysRole sysRole = roleService.getDefaultRole(object.getString("systemId"));
            roleId = sysRole.getId();
        }
        // 用户角色关系
        SysUserRole ur = new SysUserRole();
        ur.setUserId(userId);
        ur.setRoleId(roleId);
//            ur.setSystemId(object.getString("systemId"));
        ur.setSystemId("0");
        userRoleService.createUserRole(ur);
//        }
        return findByLoginName(sysUser.getLoginName());
    }

    // 属于
    @Override
    public Boolean exist(List<Where> w) {
        w.add(new Where("1"));
        return this.query(w).size() > 0;
    }

    @Autowired
    public DataSourceTransactionManager transactionManager;

    public JSONObject importUser(JSONObject object) {

        JSONObject res = new JSONObject();
        boolean flag = false;

        String salt = S.randomNum();
        String pwdStr = "";
        if (V.isEmpty(object.getString("pwd"))) {
            pwdStr = "gxun" + object.getString("number").substring(object.getString("number").length() - 6);
        } else {
            pwdStr = object.getString("pwd");
        }
        String pwd = MD5Kit.encode(pwdStr + salt);
        if (V.isEmpty(this.findByUserName(object.getString("number")))) {
            JSONObject model = new JSONObject();
            model.put("loginName", object.getString("number"));
            model.put("password", pwd);
            model.put("salt", salt);
            model.put("number", object.getString("number"));
            if (V.isEmpty(object.getString("number"))) {
                flag = false;
                res.put("flag", flag);
                res.put("info", "学号/工号为空！");
                return res;
            }
            Boolean exist = this.exist(W.f(
                    W.and("number", "eq", object.getString("number")),
                    W.and("isDel", "eq", "0"))
            );
            if (exist) {
                flag = false;
                res.put("flag", flag);
                res.put("info", "该学号/工号已存在！");
                return res;
            }
            //2.获取事务定义
            DefaultTransactionDefinition def = new DefaultTransactionDefinition();
            //3.设置事务隔离级别，开启新事务
            def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
            //4.获得事务状态
            TransactionStatus transactionStatus = transactionManager.getTransaction(def);

            if (object.getString("phone") != null && object.getString("phone") != "") {

                Boolean phoneExist = this.exist(W.f(
                        W.and("phone", "eq", object.getString("phone")),
                        W.and("isDel", "eq", "0"))
                );
                if (phoneExist) {
                    flag = false;
                    res.put("flag", flag);
                    res.put("info", "该手机号已存在！");
                    transactionManager.rollback(transactionStatus);
                    return res;
                }
                model.put("phone", object.getString("phone"));

            }
            model.put("type", object.getInteger("type"));
            model.put("creator", object.getString("userid"));
            model.put("roleId", object.getString("roleId"));


            model = (JSONObject) this.createUser(model);
            if (model == null) {
                flag = false;
                res.put("flag", flag);
                res.put("info", "用户创建失败！");
                transactionManager.rollback(transactionStatus);
                return res;
            } else {
                String id = model.getString("id");
                //新增到个人信息表
                Boolean baseExist = userBaseInfoService.exist(W.f(
                        W.and("number", "eq", object.getString("number")),
                        W.and("isDel", "eq", "0"))
                );
                if (baseExist) {
                    flag = false;
                    res.put("flag", flag);
                    res.put("info", "该学号/工号已存在！");
                    transactionManager.rollback(transactionStatus);
                    return res;
                }
                UserBaseInfo userBaseInfo = new UserBaseInfo();
                userBaseInfo.setId(id);
                userBaseInfo.setCreator(object.getString("userid"));
                userBaseInfo.setName(object.getString("name"));
                userBaseInfo.setNumber(object.getString("number"));
                userBaseInfo.setType(object.getInteger("type"));
                userBaseInfo.setNationality(1);

                if (object.getString("phone") != null || object.getString("phone") != "") {
                    userBaseInfo.setPhone(object.getString("phone"));
                }

                if (object.getString("idCard") != null || object.getString("idCard") != "") {
                    userBaseInfo.setIdCard(object.getString("idCard"));
                }
                userBaseInfoService.createUserBaseInfo(userBaseInfo);

                flag = true;
                res.put("flag", flag);
                res.put("info", "用户表新增成功");
                transactionManager.commit(transactionStatus);
                return res;
            }
        } else {
            flag = false;
            res.put("flag", flag);
            res.put("info", "该学号/工号已存在！");
            return res;
        }

    }

    @Transactional
    @Override
    public Object updateUser(JSONObject object) {
        SysUser sysUser = J.o2m(object, SysUser.class);
        this.update(sysUser);
        if (V.isEmpty(sysUser.getLoginName()))
            return this.selectByIds(sysUser.getId());
        return findByLoginName(sysUser.getLoginName());
    }

    // 修改
    @Override
    public SysUser updateSysUser(SysUser model) {
        if (this.update(model))
            return this.selectById(model.getId());
        return null;
    }

    // 查询
    @Override
    public List<SysUser> findByIds(Object ids) {
        return this.selectByIds(ids);
    }

    @Override
    public Object findByLoginName(String loginName) {
        where = W.f(
                W.and("loginName", "eq", loginName)
        );
        List<SysUser> sysUsers = this.query(where);
        if (sysUsers.size() == 0)
            return null;
        SysUser sysUser = sysUsers.get(0);
        if (sysUser.getStatus() == 0)
            return sysUser;

        String userId = sysUser.getId();

        //数据整合
        JSONObject u = new JSONObject();
        u.put("id", userId);
        u.put("loginName", sysUser.getLoginName());
        u.put("createTime", sysUser.getCreateTime());
        u.put("status", sysUser.getStatus());
        u.put("password", sysUser.getPassword());
        u.put("salt", sysUser.getSalt());
        u.put("number", sysUser.getLoginName());
        List<JSONObject> infos =new ArrayList<>();

        Integer type = sysUser.getType();
        if(type==0){//管理员

        }else if(type==1){//教师

        }else if(type == 2){//学生
            infos = studentService.queryAll("and number='" + loginName + "'");

        }


        if (!V.isEmpty(infos)) {
            JSONObject info = infos.get(0);
            u.put("info", info);
        }
        u.put("name", loginName);

//        u.put("phone", sysUser.getPhone());
//        u.put("errorCount", sysUser.getErrorCount());
//        u.put("unlockTime", sysUser.getUnlockTime());
//        u.put("verifyCode", sysUser.getVerifyCode());
        return u;
    }


    public Object findByUserId(String userid) {

        List<SysUser> sysUsers = this.findByIds(userid);
        if (sysUsers.size() == 0)
            return null;
        SysUser sysUser = sysUsers.get(0);
        if (sysUser.getStatus() == 0)
            return sysUser;

        JSONObject info = new JSONObject();
        String userId = sysUser.getId();


        //数据整合
        JSONObject u = new JSONObject();
        u.put("id", userId);
        u.put("loginName", sysUser.getLoginName());
        u.put("createTime", sysUser.getCreateTime());
        u.put("status", sysUser.getStatus());
//        u.put("number", sysUser.getNumber());
        List<JSONObject> infos = userInfoService.queryAll("and id='" + userId + "'");
        if (!V.isEmpty(infos)) {
            u.put("info", infos.get(0));
        }
        // 加入角色权限
        SysUserRole userRole = userRoleService.findByUserId(userId);
        u.put("role", userRole);

        u.put("sysUser", sysUser);
//        u.put("phone", sysUser.getPhone());
//        u.put("errorCount", sysUser.getErrorCount());
//        u.put("unlockTime", sysUser.getUnlockTime());
//        u.put("verifyCode", sysUser.getVerifyCode());
        return u;
    }

    @Override
    public Boolean existLoginName(String loginName) {
        where = W.f(
                W.and("loginName", "eq", loginName),
                W.and("isDel", "eq", "0")
        );
        List<SysUser> sysUsers = this.query(where);
        if (sysUsers.size() == 0)
            return false;
        return true;
    }


    public Boolean existNumber(String number) {
        where = W.f(
                W.and("number", "eq", number.trim()),
                W.and("isDel", "eq", "0")
        );
        List<SysUser> sysUsers = this.query(where);
        if (sysUsers.size() == 0)
            return false;
        return true;
    }

    public Boolean existPhone(String phone) {
        where = W.f(
                W.and("phone", "eq", phone.trim()),
                W.and("isDel", "eq", "0")
        );
        List<SysUser> sysUsers = this.query(where);
        if (sysUsers.size() == 0)
            return false;
        return true;
    }


    //htc
    public SysUser findByPhone(String phone) {
        where = W.f(
                W.and("phone", "eq", phone)
        );
        List<SysUser> sysUsers = this.query(where);
        if (sysUsers.size() == 0)
            return null;
        SysUser sysUser = sysUsers.get(0);
        return sysUser;
    }

    //htc
    public SysUser findByUserName(String loginName) {
        where = W.f(
                W.and("loginName", "eq", loginName)
        );
        List<SysUser> sysUsers = this.query(where);
        if (sysUsers.size() == 0)
            return null;
        SysUser sysUser = sysUsers.get(0);
        return sysUser;
    }


    //htc
    public SysUser findByNumber(String number) {
        where = W.f(
                W.and("number", "eq", number)
        );
        List<SysUser> sysUsers = this.query(where);
        if (sysUsers.size() == 0)
            return null;
        SysUser sysUser = sysUsers.get(0);
        return sysUser;
    }


    @Override
    public Page page(int index, int size, String userWhere, String roleWhere, String infoWhere) {


        int pageSize = size;
        // 总记录数

        JSONObject row = new JSONObject();
        Boolean userFlag = V.isEmpty(userWhere);
        Boolean roleFlag = V.isEmpty(roleWhere);
        Boolean infoFlag = V.isEmpty(infoWhere);

        if (roleFlag && infoFlag) {
            row = baseMapper.getPageCount_default(userWhere);
        } else {
            if (!roleFlag && !infoFlag) {
                row = baseMapper.getPageCount(userWhere, roleWhere, infoWhere);
            } else if (roleFlag) {
                row = baseMapper.getPageCount_noRole(userWhere, infoWhere);
            } else if (infoFlag) {
                row = baseMapper.getPageCount_noInfo(userWhere, roleWhere);
            }
        }
        int totalCount = row.getInteger("total");
        if (totalCount == 0)
            return new Page(new ArrayList<JSONObject>(), pageSize, 0, 0, 1);
        // 分页数据
        index = index < 0 ? 1 : index;
        int limit = (index - 1) * pageSize;
        List<JSONObject> users = new ArrayList<>();
        if (roleFlag && infoFlag) {
            users = baseMapper.getPage_onlyUser(userWhere, limit, pageSize);
        } else {
            users = baseMapper.getPage(userWhere, roleWhere, infoWhere, limit, pageSize);
        }
        int totalPage = totalCount % pageSize == 0 ? totalCount / pageSize : (totalCount / pageSize) + 1;
        int currentPage = index;
        if (users.size() == 0)
            return new Page(new ArrayList<JSONObject>(), pageSize, totalCount, totalPage, currentPage);

       /* String userIds = L.getAttrs(users, "id");
        //整合info
        where = W.f(
                W.and("userId", "in", userIds)
        );
        for (JSONObject user : users) {
            List<JSONObject> infos = userInfoService.queryAll("and number='" + user.getString("number") + "'");
            if (!V.isEmpty(infos)) {
                user.put("info", infos.get(0));

            }
        }*/
        return new Page(F.f2l(users, "id", "roleId", "academyCode"), pageSize, totalCount, totalPage, currentPage);
    }

    @Override
    public Boolean existId(Object id) {
        where = W.f(
                W.and("id", "eq", id),
                W.field("1")
        );
        return this.query(where).size() > 0;
    }

    @Override
    public Boolean deleteUser(Object ids, String reviser) {
        return this.delete(ids, reviser);
    }

    @Override
    public List<JSONObject> getSubSystemByUserId(String userId) {
        List<JSONObject> objects = baseMapper.getSubSystemByUserId(userId);
        if (objects.size() > 0)
            objects = F.f2l(objects, "systemId");
        return objects;
    }

    @Transactional
    @Override
    public Boolean initPwd(JSONObject object) {
        SysUser sysUser = J.o2m(object, SysUser.class);
        this.update(sysUser);
        return true;
    }

    @Override
    public List<JSONObject> getUserAuth(String userId) {
        List<SysMenu> menus = baseMapper.getRoleMenuByUserId(userId);
        // 找父级parentId=0
        if (menus.size() == 0)
            return new ArrayList<>();
        List<SysMenuAuth> auths = baseMapper.getRoleAuthByUserId(userId);

        List<JSONObject> list = new ArrayList<JSONObject>();
        List<SysMenu> modules = new ArrayList<>();

        List<SysMenu> parents = new ArrayList<>();
        List<JSONObject> subs = new ArrayList<>();
        // 取模块
        for (SysMenu m1 : menus) {
            if (V.isEqual(m1.getParentId(), "0")) {
                modules.add(m1);
            }
        }
        // 取父级
        for (SysMenu module : modules) {
            for (SysMenu menu : menus) {
                if (V.isEqual(menu.getParentId(), module.getId())) {
                    parents.add(menu);
                }
            }
        }
        // 取子级
        for (SysMenu parent : parents) {
            for (SysMenu menu : menus) {
                if (V.isEqual(menu.getParentId(), parent.getId())) {
                    JSONObject sub = new JSONObject();
                    sub.put("id", menu.getId());
                    sub.put("name", menu.getName());
                    sub.put("url", menu.getUrl());
                    sub.put("icon", menu.getIcon());
                    sub.put("code", menu.getCode());
                    sub.put("sort", menu.getSort());
                    sub.put("parentId", menu.getParentId());
                    sub.put("hide", menu.getHide());
                    List<JSONObject> btns = new ArrayList<>();
                    if (auths.size() > 0) {
                        for (SysMenuAuth auth : auths) {
                            if (V.isEqual(auth.getMenuId(), menu.getId())) {
                                JSONObject btn = new JSONObject();
                                btn.put("id", auth.getId());
                                btn.put("name", auth.getName());
                                btn.put("fun", auth.getFun());
                                btn.put("menuId", auth.getMenuId());
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

        for (SysMenu module : modules) {
            //模块
            JSONObject mModule = new JSONObject();
            mModule.put("id", module.getId());
            mModule.put("url", "/");
            mModule.put("name", module.getName());
            mModule.put("icon", module.getIcon());
            mModule.put("sort", module.getSort());
            mModule.put("parentId", module.getParentId());
            List<JSONObject> ps = new ArrayList<JSONObject>();
            if (parents.size() > 0) {
                for (SysMenu p : parents) {
                    if (V.isEqual(p.getParentId(), module.getId())) {
                        //父级
                        JSONObject parent = new JSONObject();
                        parent.put("id", p.getId());
                        parent.put("url", "/");
                        parent.put("name", p.getName());
                        parent.put("icon", p.getIcon());
                        parent.put("sort", p.getSort());
                        parent.put("parentId", p.getParentId());

                        List<JSONObject> mSub = new ArrayList<JSONObject>();
                        if (subs.size() > 0) {
                            for (JSONObject sub : subs) {
                                if (V.isEqual(sub.getString("parentId"), p.getId())) {
                                    mSub.add(sub);
                                }
                            }
                        }
                        parent.put("sub", mSub);
                        ps.add(parent);
                    }
                }
            }
            mModule.put("module", ps);
            list.add(mModule);
        }
        return list;
    }


}
