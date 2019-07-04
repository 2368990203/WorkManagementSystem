package team.work.core.service.impl;

import team.work.utils.base.TServiceImpl;
import team.work.utils.bean.Where;
import team.work.utils.convert.W;
import team.work.core.mapper.SysUserSystemMapper;
import team.work.core.model.SysUserSystem;
import team.work.core.service.ISysUserSystemService;
import org.springframework.stereotype.Service;
import team.work.core.mapper.SysUserSystemMapper;
import team.work.core.model.SysUserSystem;
import team.work.utils.base.TServiceImpl;
import team.work.utils.bean.Where;
import team.work.utils.convert.W;

import java.util.List;


@Service
public class SysUserSystemService extends TServiceImpl<SysUserSystemMapper, SysUserSystem> implements ISysUserSystemService {
    /**************************CURD begin******************************/
    // 创建
    @Override
    public SysUserSystem createUserSystem(SysUserSystem model) {
        if (this.insert(model))
            return this.selectById(model.getId());
        return null;
    }

    // 删除
    @Override
    public Boolean deleteUserSystem(Object ids, String reviser) {
        return this.delete(ids, reviser);
    }

    // 修改
    @Override
    public SysUserSystem updateUserSystem(SysUserSystem model) {
        if (this.update(model))
            return this.selectById(model.getId());
        return null;
    }

    // 查询
    @Override
    public List<SysUserSystem> findByIds(Object ids) {
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

}
