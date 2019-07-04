package team.work.core.service.impl;

import com.alibaba.fastjson.JSONObject;
import team.work.utils.base.TServiceImpl;
import team.work.utils.bean.Page;
import team.work.utils.bean.Where;
import team.work.utils.convert.F;
import team.work.utils.convert.S;
import team.work.utils.convert.V;
import team.work.utils.convert.W;
import team.work.core.mapper.SysCategoryMapper;
import team.work.core.model.SysCategory;
import team.work.core.service.ISysCategoryService;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Service;
import team.work.core.model.SysCategory;
import team.work.utils.base.TServiceImpl;
import team.work.utils.bean.Page;
import team.work.utils.bean.Where;
import team.work.utils.convert.F;
import team.work.utils.convert.S;
import team.work.utils.convert.V;
import team.work.utils.convert.W;

import java.util.*;


@Service
public class SysCategoryService extends TServiceImpl<SysCategoryMapper, SysCategory> implements ISysCategoryService {
    /**************************CURD begin******************************/
    // 创建
    @Override
    public SysCategory createCategory(SysCategory model) {
        if (this.insert(model))
            return this.selectById(model.getId());
        return null;
    }

    // 删除
    @Override
    public Boolean deleteCategory(Object ids, String reviser) {
        return this.delete(ids, reviser);
    }

    // 修改
    @Override
    public SysCategory updateCategory(SysCategory model) {
        if (this.update(model))
            return this.selectById(model.getId());
        return null;
    }

    // 查询
    @Override
    public List<SysCategory> findByIds(Object ids) {
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
    public Page page(int index, int size, String parentId, String query) {
        if (query.length() > 0)
            query = S.apppend(" and (name like '%", query, "%' or code like '%", query, "%') ");
        query = S.apppend(query, " and parentId='", parentId, "' ");
        int pageSize = size;
        // 总记录数
        JSONObject row = baseMapper.getPageCount(query);
        int totalCount = row.getInteger("total");
        if (totalCount == 0)
            return new Page(new ArrayList<JSONObject>(), pageSize, 0, 0, 1);
        index = index < 0 ? 1 : index;
        int limit = (index - 1) * pageSize;
        List<JSONObject> categorys = baseMapper.getPage(query, limit, pageSize);
        int totalPage = totalCount % pageSize == 0 ? totalCount / pageSize : (totalCount / pageSize) + 1;
        int currentPage = index;

        return new Page((categorys.size() == 0 ? categorys : F.f2l(categorys, "id")),
                pageSize, totalCount, totalPage, currentPage);
    }

    @Override
    public JSONObject getCategoryByCode(String code, int level) {
        JSONObject data = new JSONObject();
        if (!checkCode(code))
            return data;
        //先检查是否存在这个code
        where = W.f(
                W.and("code", "eq", code),
                W.and("parentId", "eq", 0),
                W.field("id")
        );
        List<SysCategory> parent = this.query(where);
        String parentId = "";
        if (parent.size() == 0) {
            SysCategory model = new SysCategory();
            model.setName(code);
            model.setCode(code);
            this.insert(model);
            parentId = model.getId();
        } else {
            parentId = parent.get(0).getId();
        }
        List<SysCategory> categories = getCategoryBySuperId(parentId);

        data.put("id", parentId);
        data.put("code", code);
        data.put("data", formatCategory(categories, parentId, level));

        return data;
    }

    private Map<String, List<SysCategory>> getByparentId(List<SysCategory> categories, String parentId) {
        Map<String, List<SysCategory>> map = new HashMap<String, List<SysCategory>>();
        List<SysCategory> temp = new ArrayList<>();
        Iterator<SysCategory> category = categories.iterator();
        while (category.hasNext()) {
            SysCategory model = category.next();
            if (V.isEqual(model.getParentId(), parentId)) {
                temp.add(model);
                category.remove();
            }
        }
        map.put("data", temp);
        map.put("categories", categories);
        return map;
    }

    //数据最多支持3级
    private List<JSONObject> formatCategory(List<SysCategory> categories, String parentId, int level) {
        List<JSONObject> data1st = new ArrayList<>();

        Map<String, List<SysCategory>> mGrand = getByparentId(categories, parentId);
        categories = mGrand.get("categories");
        List<SysCategory> grands = mGrand.get("data");

        for (SysCategory grand : grands) {
            Map<String, List<SysCategory>> mFather = getByparentId(categories, grand.getId());
            categories = mFather.get("categories");
            List<SysCategory> fathers = mFather.get("data");

            JSONObject jGrand = new JSONObject();
            jGrand.put("id", grand.getId());
            jGrand.put("name", grand.getName());
            jGrand.put("code", grand.getCode());
            jGrand.put("parentId", grand.getParentId());
            List<JSONObject> data2st = new ArrayList<>();
            if (level == 2) {
                for (SysCategory father : fathers) {
                    Map<String, List<SysCategory>> mSub = getByparentId(categories, father.getId());
                    categories = mSub.get("categories");
                    List<SysCategory> subs = mSub.get("data");

                    JSONObject jFather = new JSONObject();
                    jFather.put("id", father.getId());
                    jFather.put("name", father.getName());
                    jFather.put("code", father.getCode());
                    jFather.put("parentId", father.getId());

                    List<JSONObject> data3st = new ArrayList<>();
                    for (SysCategory sub : subs) {
                        JSONObject jSub = new JSONObject();
                        jSub.put("id", sub.getId());
                        jSub.put("name", sub.getName());
                        jSub.put("code", sub.getCode());
                        jSub.put("parentId", father.getParentId());
                        data3st.add(jSub);
                    }
                    jFather.put("sub", data3st);
                    data2st.add(jFather);
                }
                jGrand.put("sub", data2st);
            }
            data1st.add(jGrand);
        }
        return data1st;
    }

    private Boolean checkCode(String code) {
        List<SysCategory> categories = getSuperCategory();
        if (categories.size() == 0)
            return false;
        String[] arr = new String[categories.size() + 1];
        for (int i = 0; i < categories.size(); i++) {
            arr[i] = categories.get(i).getCode();
        }
        return ArrayUtils.contains(arr, code);
    }

    @Override
    public List<SysCategory> getCategoryByParentId(String parentId) {
        where = W.f(
                W.and("parentId", "eq", parentId),
                W.order("code", "asc")
        );
        return this.query(where);
    }

    @Override
    public List<SysCategory> getCategoryBySuperId(String superId) {
        where = W.f(
                W.and("superId", "eq", superId),
                W.order("code", "asc")
        );
        return this.query(where);
    }

    @Override
    public List<SysCategory> getSuperCategory() {
        where = W.f(
                W.and("superId", "eq", 0),
                W.and("parentId", "eq", 0),
                W.order("code", "asc")
        );
        return this.query(where);
    }

    @Override
    public List<SysCategory> getOrganizational() {
        return null;
    }

}
