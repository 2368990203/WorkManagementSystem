package team.work.core.service.impl;

import org.springframework.stereotype.Service;
import team.work.core.mapper.SysAutoCodeMapper;
import team.work.core.model.SysAutoCode;
import team.work.core.service.ISysAutoCodeService;
import team.work.utils.base.TServiceImpl;
import team.work.utils.bean.Where;

import java.util.List;


@Service
public class SysAutoCodeService extends TServiceImpl<SysAutoCodeMapper, SysAutoCode> implements ISysAutoCodeService {
    /**************************CURD begin******************************/
    // 创建
    @Override
    public SysAutoCode createAutoCode(SysAutoCode model) {
        if (this.insert(model))
            return this.selectById(model.getId());
        return null;
    }

    // 删除
    @Override
    public Boolean deleteAutoCode(Object ids, String reviser) {
        return this.delete(ids, reviser);
    }

    // 修改
    @Override
    public SysAutoCode updateAutoCode(SysAutoCode model) {
        if (this.update(model))
            return this.selectById(model.getId());
        return null;
    }

    // 查询
    @Override
    public List<SysAutoCode> findByIds(Object ids) {
        return this.selectByIds(ids);
    }

    // 属于
    @Override
    public Boolean exist(List<Where> w) {
        return this.query(w).size() > 0;
    }

    /**************************CURD end********************************/

}
