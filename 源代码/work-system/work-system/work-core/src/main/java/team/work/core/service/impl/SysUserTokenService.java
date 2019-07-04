package team.work.core.service.impl;

import com.alibaba.fastjson.JSONObject;
import team.work.core.mapper.SysUserTokenMapper;
import team.work.core.model.SysUserToken;
import team.work.core.service.ISysUserTokenService;
import team.work.utils.base.TServiceImpl;
import team.work.utils.bean.Where;
import team.work.utils.convert.F;
import team.work.utils.convert.J;
import team.work.utils.convert.W;
import team.work.utils.kit.IdKit;
import team.work.utils.kit.TimeKit;
import org.springframework.stereotype.Service;
import team.work.core.model.SysUserToken;
import team.work.utils.base.TServiceImpl;
import team.work.utils.bean.Where;
import team.work.utils.convert.F;
import team.work.utils.convert.J;
import team.work.utils.convert.W;
import team.work.utils.kit.IdKit;
import team.work.utils.kit.TimeKit;

import java.util.List;


@Service
public class SysUserTokenService extends TServiceImpl<SysUserTokenMapper, SysUserToken> implements ISysUserTokenService {
    /**************************CURD begin******************************/
    // 创建
    @Override
    public SysUserToken createUserToken(SysUserToken model) {
        //判断是否已经存在数据
        where = W.f(
                W.and("userId", "eq", model.getUserId()),
                W.and("server", "eq", model.getServer()),
                W.and("type", "eq", model.getType())

        );
        List<SysUserToken> uts = this.query(where);
        if (uts.size() > 0) {
            SysUserToken ut = uts.get(0);
            ut.setType(model.getType());
            ut.setToken(model.getToken());

            this.update(ut);
        } else {
            JSONObject ut = new JSONObject();
            ut.put("id", IdKit.getId(SysUserToken.class));
            ut.put("reviseTime", TimeKit.getTimestamp());
            ut.put("userId", model.getUserId());
            ut.put("token", model.getToken());
            ut.put("type", model.getType());
            ut.put("server", model.getServer());

            this.insert(J.o2m(ut, SysUserToken.class));
        }
        return null;
    }

    // 删除
    @Override
    public Boolean deleteUserToken(Object ids, String reviser) {
        return this.delete(ids, reviser);
    }

    // 修改
    @Override
    public SysUserToken updateUserToken(SysUserToken model) {
        if (this.update(model))
            return this.selectById(model.getId());
        return null;
    }

    // 查询
    @Override
    public List<SysUserToken> findByIds(Object ids) {
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
    //全查
    public JSONObject queryUserIdByToken(String type, String token) {
        JSONObject obj = baseMapper.queryUserIdByToken(type, token);
        return F.f2j(obj, "id", "token");
    }


}
