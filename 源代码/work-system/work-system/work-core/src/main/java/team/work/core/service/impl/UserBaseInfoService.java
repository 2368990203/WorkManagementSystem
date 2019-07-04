package team.work.core.service.impl;

import com.alibaba.fastjson.JSONObject;
import team.work.core.mapper.UserBaseInfoMapper;
import team.work.core.model.UserBaseInfo;
import team.work.core.service.IUserBaseInfoService;
import team.work.utils.base.TServiceImpl;
import team.work.utils.bean.Page;
import team.work.utils.convert.F;
import team.work.utils.convert.V;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import team.work.utils.convert.W;
import team.work.utils.bean.Where;
import org.springframework.transaction.annotation.Transactional;
import team.work.core.model.UserBaseInfo;
import team.work.utils.base.TServiceImpl;
import team.work.utils.bean.Page;
import team.work.utils.bean.Where;
import team.work.utils.convert.F;
import team.work.utils.convert.V;
import team.work.utils.convert.W;


@Service
public class UserBaseInfoService extends TServiceImpl<UserBaseInfoMapper, UserBaseInfo> implements IUserBaseInfoService {
    /**************************CURD begin******************************/
    // 创建
    @Override
    @Transactional
    public UserBaseInfo createUserBaseInfo(UserBaseInfo model) {
        if (this.insert(model))
            return this.selectById(model.getId());
        return null;
    }

    // 删除
    @Override
    public Boolean deleteUserBaseInfo(Object ids, String reviser) {
        return this.delete(ids, reviser);
    }

    // 修改
    @Override
    public UserBaseInfo updateUserBaseInfo(UserBaseInfo model) {
        if (this.update(model))
            return this.selectById(model.getId());
        return null;
    }

    // 查询
    @Override
    public List<UserBaseInfo> findByIds(Object ids) {
        return this.selectByIds(ids);
    }

    public List<UserBaseInfo> findByNumber(String number) {
        where = W.f(
                W.and("number", "eq", number)
        );

        List<UserBaseInfo> userDetailInfoList = this.query(where);
        if (userDetailInfoList == null || userDetailInfoList.size() == 0)
            return null;
        return userDetailInfoList;
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
    //分页查
    public Page page(int index, int pageSize, String w) {
        // 总记录数
        JSONObject row = new JSONObject();
        if(V.isEmpty(w)){
            row = baseMapper.getPageCount_default( );
        }else{
            row = baseMapper.getPageCount(w);
        }



        int totalCount = row.getInteger("total");

        if (totalCount == 0)
            return new Page(new ArrayList<JSONObject>(), pageSize, 0, 0, 1);
        // 分页数据
        index = index < 0 ? 1 : index;
        int limit = (index - 1) * pageSize;
        int totalPage = totalCount % pageSize == 0 ? totalCount / pageSize : (totalCount / pageSize) + 1;
        int currentPage = index;

        List<JSONObject> grades = baseMapper.getPage(w, limit, pageSize);

        return new Page(F.f2l(grades, "id"), pageSize, totalCount, totalPage, currentPage);
    }

    //全查
    public List<JSONObject> queryAll(String where) {
        List<JSONObject> list = baseMapper.queryAll(where);
        return F.f2l(list, "id", "creator", "reverse");
    }

    //根据学号查用户全部信息
    public JSONObject queryUserByNumber(String number) {
        JSONObject list = baseMapper.queryUserByNumber(number);
        return F.f2j(list, "id", "creator", "reverse");
    }

    public Boolean existNumber(String number) {
        where = W.f(
                W.and("number", "eq", number.trim()),
                W.and("isDel", "eq", "0")
        );
        List<UserBaseInfo> sysUsers = this.query(where);
        if (sysUsers.size() == 0)
            return false;
        return true;
    }

    public Boolean existPhone(String phone) {
        where = W.f(
                W.and("phone", "eq", phone.trim()),
                W.and("isDel", "eq", "0")
        );
        List<UserBaseInfo> sysUsers = this.query(where);
        if (sysUsers.size() == 0)
            return false;
        return true;
    }


    public Boolean existIdcard(String idcard) {
        where = W.f(
                W.and("idcard", "eq", idcard.trim()),
                W.and("isDel", "eq", "0")
        );
        List<UserBaseInfo> sysUsers = this.query(where);
        if (sysUsers.size() == 0)
            return false;
        return true;
    }


}
