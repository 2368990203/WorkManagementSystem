package team.work.core.service.impl;

import com.alibaba.fastjson.JSONObject;
import team.work.core.tps.CacheKit;
import team.work.utils.base.TServiceImpl;
import team.work.utils.bean.Where;
import team.work.utils.kit.IdKit;

import team.work.utils.convert.J;
import team.work.utils.convert.S;
import team.work.utils.convert.V;
import team.work.utils.convert.W;
import team.work.utils.constant.Global;
import team.work.core.mapper.SysTpsConfigMapper;
import team.work.core.model.SysTpsConfig;
import team.work.core.service.ISysTpsConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.work.core.model.SysTpsConfig;
import team.work.core.tps.CacheKit;
import team.work.utils.base.TServiceImpl;
import team.work.utils.bean.Where;
import team.work.utils.constant.Global;
import team.work.utils.convert.J;
import team.work.utils.convert.S;
import team.work.utils.convert.V;
import team.work.utils.convert.W;
import team.work.utils.kit.IdKit;

import java.util.List;


@Service
public class SysTpsConfigService extends TServiceImpl<SysTpsConfigMapper, SysTpsConfig> implements ISysTpsConfigService {
    @Autowired
    private CacheKit cacheKit;

    /**************************CURD begin******************************/
    // 创建
    @Override
    public SysTpsConfig createTpsConfig(SysTpsConfig model) {
        if (this.insert(model))
            return this.selectById(model.getId());
        return null;
    }

    // 删除
    @Override
    public Boolean deleteTpsConfig(Object ids, String reviser) {
        return this.delete(ids, reviser);
    }

    // 修改
    @Override
    public SysTpsConfig updateTpsConfig(SysTpsConfig model) {
        if (this.update(model))
            return this.selectById(model.getId());
        return null;
    }

    // 查询
    @Override
    public List<SysTpsConfig> findByIds(Object ids) {
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

    @Transactional
    @Override
    public Boolean config(String code, List<SysTpsConfig> models) {
        baseMapper.deleteByCode(code);
        String redisKey = S.apppend("CacheConstant-", code);
        if (models.size() > 0) {
            for (SysTpsConfig model : models) {
                model.setId(IdKit.getId(SysTpsConfig.class));
                this.insert(model);
            }
        }
        cacheKit.setVal(redisKey, J.o2s(getData(code)), 0);
        return true;
    }

    @Override
    public JSONObject getByCode(String code) {
        String redisKey = S.apppend("CacheConstant-", code);
        String redisVal = null;
        try {
            redisVal = cacheKit.getVal(redisKey);
        } catch (Exception ex) {
        }

        if (redisVal.length() == 0) {
            JSONObject model = getData(code);
            cacheKit.setVal(redisKey, J.o2s(model), 0);
            return model;
        } else {
            return J.s2j(redisVal);
        }
    }

    public JSONObject syncByCode(String code) {
        String redisKey = S.apppend("CacheConstant-", code);

        JSONObject model = getData(code);
        cacheKit.setVal(redisKey, J.o2s(model), 0);
        return model;

    }

    /**************************CURD end********************************/

    private JSONObject getData(String code) {
        JSONObject model = getConfig(code);
        where = W.f(
                W.and("code", "eq", code)
        );
        List<SysTpsConfig> sysTpsConfigs = this.query(where);
        if (sysTpsConfigs.size() > 0) {
            for (SysTpsConfig tps : sysTpsConfigs) {
                model.put(tps.getAttr(), tps.getVal());
            }
        }
        return model;
    }

    //初始化数据
    private JSONObject getConfig(String code) {
        JSONObject object = new JSONObject();
        String global[] = new String[0];
        if (V.isEqual(code, "oss")) {
            global = Global.OSS;
        }
        if (global.length > 0) {
            object = J.arr2j(global);
        }
        return object;
    }


}
