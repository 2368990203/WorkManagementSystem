package team.work.core.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;
import team.work.core.mapper.AcademyInfoMapper;
import team.work.core.model.AcademyInfo;
import team.work.core.service.IAcademyInfoService;
import team.work.utils.base.TServiceImpl;
import team.work.utils.bean.Page;
import team.work.utils.bean.Where;
import team.work.utils.convert.F;
import team.work.utils.convert.S;
import team.work.utils.convert.V;
import team.work.utils.convert.W;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
public class AcademyInfoService extends TServiceImpl<AcademyInfoMapper, AcademyInfo> implements IAcademyInfoService {
    /**************************CURD begin******************************/
    // 创建
    @Override
    public AcademyInfo createAcademyInfo(AcademyInfo model) {
        if (this.insert(model))
            return this.selectById(model.getId());
        return null;
    }

    // 删除
    @Override
    public Boolean deleteAcademyInfo(Object ids, String reviser) {
        return this.delete(ids, reviser);
    }

    // 修改
    @Override
    public AcademyInfo updateAcademyInfo(AcademyInfo model) {
        if (this.update(model))
            return this.selectById(model.getId());
        return null;
    }

    // 查询
    @Override
    public List<AcademyInfo> findByIds(Object ids) {
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
    @Override
    public Page page(int index, int pageSize, String w) {
        // 总记录数
        JSONObject row = baseMapper.getPageCount(w);
        int totalCount = row.getInteger("total");
        if (totalCount == 0)
            return new Page(new ArrayList<JSONObject>(), pageSize, 0, 0, 1);
        // 分页数据
        index = index < 0 ? 1 : index;
        int limit = (index - 1) * pageSize;
        int totalPage = totalCount % pageSize == 0 ? totalCount / pageSize : (totalCount / pageSize) + 1;
        int currentPage = index;

        List<JSONObject> academy_info = baseMapper.getPage(w, limit, pageSize);

        return new Page(F.f2l(academy_info, "id"), pageSize, totalCount, totalPage, currentPage);
    }

    @Override
    public List<JSONObject> getCampusByCode(String w) {
        List<JSONObject> list = baseMapper.getCampusByCode(w);
        return F.f2l(list, "campus");
    }


    //全查
    public List<JSONObject> queryAll(String where) {
        List<JSONObject> list = baseMapper.queryAll(where);
        return F.f2l(list, "id", "creator", "reverse");
    }

    //根据名字查
    public JSONObject queryByName(String name) {
        JSONObject obj = baseMapper.queryByName(name);

        return F.f2j(obj, "id", "campus");
    }

    //根据组织机构获取用户列表
    public List<JSONObject> queryUserByRange(JSONArray rangeArray) {
        //List<String> number=new ArrayList<>();
        List<JSONObject> number = new ArrayList<>();

        for (int i = 0; i < rangeArray.size(); i++) {
            JSONObject range = rangeArray.getJSONObject(i);
            String title = range.getString("code");
            String wKey = "";
            if (range.getString("type").equals("academy")) {
                if (!V.isEmpty(title))
                    //FIXME 修改id为需要查询的字段
                    wKey = S.apppend(" and academyCode = '", title, "' ");
                List<JSONObject> list = baseMapper.queryUserDepart(wKey);
                for (JSONObject obj : list) {
                    // number.add(obj.getString("number"));
                    number.add(obj);
                }
            } else if (range.getString("type").equals("branch")) {
                if (!V.isEmpty(title))
                    //FIXME 修改id为需要查询的字段
                    wKey = S.apppend(" and departCode = '", title, "' ");
                List<JSONObject> list = baseMapper.queryUserDepart(wKey);
                for (JSONObject obj : list) {
                    // number.add(obj.getString("number"));
                    number.add(obj);
                }
            }
        }
        Set set = new HashSet();
        List<JSONObject> listNew = new ArrayList<>();
        set.addAll(number);
        listNew.addAll(set);

        return F.f2l(listNew, "number");
    }

    //根据组织机构判断用户是否有权限
    public boolean checkUserByRange(JSONArray rangeArray, JSONObject user) {
        boolean flag = false;
        for (int i = 0; i < rangeArray.size(); i++) {
            JSONObject range = rangeArray.getJSONObject(i);
            String title = range.getString("code");
            if (range.getString("type").equals("academy")) {
                if (title.equals(user.getString("academyCode"))) {
                    flag = true;
                }
            } else if (range.getString("type").equals("branch")) {
                if (title.equals(user.getString("departCode"))) {
                    flag = true;
                }
            }
        }

        return flag;
    }

    //根据学院代码获取用户列表
    public List<JSONObject> queryUserByAcademyCode(String academyCode) {

        String wKey = "";
        if (!V.isEmpty(academyCode)) {
            wKey = S.apppend(" and academyCode = '", academyCode, "' ");
        }
        List<JSONObject> list = baseMapper.queryUserDepart(wKey);

        return F.f2l(list, "number");
    }


    //根据学院代码判断用户是否有权限
    public boolean checkUserByAcademyCode(String academyCode, String number) {
        if (V.isEmpty(academyCode) || V.isEmpty(number)) {
            return false;
        } else {
            String wkey = S.apppend(" and academyCode = '", academyCode, "' " + " and number = '", number, "' ");

            List<JSONObject> list = baseMapper.queryUserDepart(wkey);

            if (V.isEmpty(list)) {
                return false;
            } else {
                return true;
            }

        }

    }

}
