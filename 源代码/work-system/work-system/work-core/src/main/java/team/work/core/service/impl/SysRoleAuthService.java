package team.work.core.service.impl;

import com.alibaba.fastjson.JSONObject;
import team.work.utils.base.TServiceImpl;
import team.work.utils.bean.Where;
import team.work.utils.convert.F;
import team.work.utils.convert.W;
import team.work.core.mapper.SysRoleAuthMapper;
import team.work.core.model.SysRoleAuth;
import team.work.core.service.ISysRoleAuthService;
import org.springframework.stereotype.Service;
import team.work.core.mapper.SysRoleAuthMapper;
import team.work.core.model.SysRoleAuth;
import team.work.utils.base.TServiceImpl;
import team.work.utils.bean.Where;
import team.work.utils.convert.F;
import team.work.utils.convert.W;

import java.util.List;


@Service
public class SysRoleAuthService extends TServiceImpl<SysRoleAuthMapper, SysRoleAuth> implements ISysRoleAuthService {
    /**************************CURD begin******************************/
    // 创建
    @Override
    public SysRoleAuth createRoleAuth(SysRoleAuth model) {
        if (this.insert(model))
            return this.selectById(model.getId());
        return null;
    }

    // 删除
    @Override
    public Boolean deleteRoleAuth(Object ids, String reviser) {
        return this.delete(ids, reviser);
    }

    // 修改
    @Override
    public SysRoleAuth updateRoleAuth(SysRoleAuth model) {
        if (this.update(model))
            return this.selectById(model.getId());
        return null;
    }

    // 查询
    @Override
    public List<SysRoleAuth> findByIds(Object ids) {
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

    /**************************CURD end********************************/
    //查询列表信息
    @Override
    public List<JSONObject> queryRoleAuths(String roleId) {
        List<JSONObject> list = baseMapper.queryRoleAuths(roleId);
        return F.f2l(list, "authId");
    }

}
