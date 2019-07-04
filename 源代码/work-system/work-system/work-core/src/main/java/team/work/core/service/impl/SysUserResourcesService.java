package team.work.core.service.impl;

import com.alibaba.fastjson.JSONObject;
import team.work.utils.base.TServiceImpl;
import team.work.utils.bean.Page;
import team.work.utils.bean.Where;
import team.work.utils.convert.J;
import team.work.utils.convert.W;
import team.work.core.mapper.SysUserResourcesMapper;
import team.work.core.model.SysUserResources;
import team.work.core.service.ISysUserResourcesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.work.core.model.SysUserResources;
import team.work.utils.base.TServiceImpl;
import team.work.utils.bean.Page;
import team.work.utils.bean.Where;
import team.work.utils.convert.J;
import team.work.utils.convert.W;

import java.util.ArrayList;
import java.util.List;


@Service
public class SysUserResourcesService extends TServiceImpl<SysUserResourcesMapper, SysUserResources> implements ISysUserResourcesService {

    @Autowired
    private SysGlobalConfigService globalConfigService;

    /**************************CURD begin******************************/
    // 创建
    @Override
    public SysUserResources createUserResources(SysUserResources model) {
        if (this.insert(model))
            return this.selectById(model.getId());
        return null;
    }

    // 删除
    @Override
    public Boolean deleteUserResources(Object ids, String reviser) {
        return this.delete(ids, reviser);
    }

    // 修改
    @Override
    public SysUserResources updateUserResources(SysUserResources model) {
        if (this.update(model))
            return this.selectById(model.getId());
        return null;
    }

    // 查询
    @Override
    public List<SysUserResources> findByIds(Object ids) {
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
    @Transactional
    @Override
    public JSONObject getUserResources(String systemId, String userId) {
        //先判断是否初始化
        where = W.f(
                W.and("systemId", "eq", systemId),
                W.and("userId", "eq", userId),
                W.and("isFile", "eq", 0),
                W.order("createTime", "desc")
        );
        List<SysUserResources> resources = this.query(where);
        //读取系统配置
        JSONObject config = globalConfigService.getSetting();

        if (resources.size() == 0) {
            //初始化用户数据
            SysUserResources ur = new SysUserResources();
            ur.setSystemId(systemId);
            ur.setUserId(userId);
            ur.setIsFile(0);
            ur.setMaxFolder(config.getInteger("fileFolder"));
            ur.setMaxSize(config.getInteger("fileCapacity") * 1024);
            ur.setMaxFileSize(config.getInteger("fileMax"));
            this.insert(ur);
            // 创建临时文件夹
            SysUserResources tempFolder = new SysUserResources();
            tempFolder.setSystemId(systemId);
            tempFolder.setUserId(userId);
            tempFolder.setIsFile(0);
            tempFolder.setFolder("临时文件");
            tempFolder.setParentId(systemId);
            this.insert(tempFolder);

            resources.add(this.selectById(ur.getId()));
            resources.add(this.selectById(tempFolder.getId()));
        }
        //数据拆分
        List<JSONObject> folder = new ArrayList<>();
        SysUserResources model = new SysUserResources();
        for (SysUserResources resource : resources) {
            if (resource.getParentId().length() > 2) {
                SysUserResources temp = new SysUserResources();
                temp.setId(resource.getId());
                temp.setFolder(resource.getFolder());
                temp.setCreateTime(resource.getCreateTime());
                folder.add(J.o2j(temp));
            } else {
                model = resource;
            }
        }

        JSONObject oss = new JSONObject();
        oss.put("id", model.getId());
        oss.put("fileFolder", model.getMaxFolder());
        oss.put("fileCapacity", model.getMaxSize());
        oss.put("fileMax", model.getMaxFileSize());
        oss.put("fileLen", config.getInteger("fileLen"));
        oss.put("basePath", config.getString("basePath"));
        oss.put("useCapacity", model.getSize());


        JSONObject result = new JSONObject();
        result.put("folder", folder);
        result.put("info", oss);
        return result;
    }

    @Override
    public Page page(int index, int pageSize, String parentId, String whereStr) {
        if (whereStr.length() > 0)
            whereStr = " and name like '%" + whereStr + "%' ";
        // 总记录数
        JSONObject row = baseMapper.getPageCount(parentId, whereStr);
        int totalCount = row.getInteger("total");
        if (totalCount == 0)
            return new Page(new ArrayList<JSONObject>(), pageSize, 0, 0, 1);

        // 分页数据
        index = index < 0 ? 1 : index;
        int limit = (index - 1) * pageSize;
        List<SysUserResources> urs = baseMapper.getPage(parentId, whereStr, limit, pageSize);
        int totalPage = totalCount % pageSize == 0 ? totalCount / pageSize : (totalCount / pageSize) + 1;
        int currentPage = index;
        if (urs.size() == 0)
            return new Page(new ArrayList<SysUserResources>(), pageSize, totalCount, totalPage, currentPage);

        return new Page(J.o2l(urs), pageSize, totalCount, totalPage, currentPage);
    }

    @Transactional
    @Override
    public Boolean createFile(SysUserResources model) {
        JSONObject object = getUserResources(model.getSystemId(), model.getUserId());
        JSONObject info = J.o2j(object.get("info"));

        //更新主表
        SysUserResources main = new SysUserResources();
        main.setId(info.getString("id"));
        int size = model.getSize() + info.getInteger("useCapacity");
        main.setSize(size);
        this.update(main);
        this.insert(model);
        return true;
    }

    @Transactional
    @Override
    public Boolean deleteFile(Object ids, String systemId, String userId, int total) {
        JSONObject object = getUserResources(systemId, userId);
        JSONObject info = J.o2j(object.get("info"));
        List<SysUserResources> urs = this.selectByIds(ids);

        SysUserResources main = new SysUserResources();
        main.setId(info.getString("id"));
        int use = info.getInteger("useCapacity") - total;
        int size = use < 0 ? 0 : use;
        main.setSize(size);
        this.update(main);
        this.delete(ids, userId);
        return true;
    }


}
