package team.work.utils.base;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import team.work.utils.convert.V;
import team.work.utils.kit.TimeKit;
import team.work.utils.bean.Where;
import team.work.utils.kit.IdKit;
import team.work.utils.bean.Where;
import team.work.utils.convert.V;
import team.work.utils.kit.IdKit;
import team.work.utils.kit.TimeKit;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TServiceImpl <M extends BaseMapper<T>, T> extends ServiceImpl<M, T> {
    public List<Where> where;
    /**
     * 新增数据 覆盖父类方法
     * @param t 泛型对象
     * 自动填充 id、creator、createTime、version
     * */
    public boolean insert(T t) {
        return super.insert(addModel(t));
    }
    /**
     * 删除数据 重写父类方法
     * @param ids 格式：12、12，13，14
     * 自动填充reviser、reviseTime
     * */
    public boolean delete(Object ids,String reviser){
        if(V.isEmpty(ids)||V.isEmpty(ids.toString())){
            return  false;
        }
        JSONObject object = new JSONObject();
        object.put("isDel",1);
        object.put("reviser",reviser);
        EntityWrapper<T> ew = new EntityWrapper<T>();
        ew.in("id",ids.toString());
        return super.update(updateModel(JSON.parseObject(object.toJSONString(), (Class<T>) this.currentModelClass())), ew);
    }
    /**
     * 修改数据 覆盖父类方法
     * @param t 泛型对象
     * 自动填充reviser、reviseTime
     * */
    public boolean update(T t){
        JSONObject object = T2Jobj(t);
        if(V.isEmpty(object.get("id"))){
            return  false;
        }
        EntityWrapper<T> ew = new EntityWrapper<T>();
        ew.where("id={0} and isDel=0",object.get("id"));
        return super.update(updateModel(t), ew);
    }
    /**
     * 获取一条或多条数据
     * @param ids 格式：12、12，13，14
     * */
    public List<T> selectByIds(Object ids){
        if(V.isEmpty(ids)||V.isEmpty(ids.toString())){
            return  null;
        }
        EntityWrapper<T> ew = new EntityWrapper<T>();
        ew.in("id",ids.toString()).eq("isDel",0);
        return super.selectList(ew);
    }
    /**
     * 分页获取数据
     * @param index 页码
     *        size  页长度
     *        where 查询条件json字符串
     * */
    public Page<T> page(int index, int size, List<Where> where){
        EntityWrapper<T> ew = new EntityWrapper<T>();
        if(where.size() >0){
            ew = createWhere(where);
        }else {
            ew.orderBy("createTime",false);
        }
        ew.eq("isDel",0);
        return super.selectPage(
                new Page<T>(index,size),
                ew
        );
    }


    /**
     * 乐观锁业务
     * */
    public Boolean isLock(T t){
        JSONObject object = T2Jobj(t);
        if(checkLock(t)){
            EntityWrapper<T> ew = new EntityWrapper<T>();
            ew.where("id={0}",object.get("id"));
            JSONObject obj = JSON.parseObject(JSONObject.toJSONString(super.selectOne(ew)));
            if(object.get("version").equals(obj.get("version"))){
                return true;
            }else {
                return false;
            }

        }
        return true;
    }

    /**
     * 泛型转JsonObject
     * */
    public JSONObject T2Jobj(T t){
        return JSON.parseObject(JSONObject.toJSONString(t));
    }
    /**
     * 新增实体类转型
     * */
    private T addModel(T t){
        JSONObject object = T2Jobj(t);
        Map<String,Object> map = new HashMap<String,Object>();
        Long vt = TimeKit.getTimestamp();
        //如果没有配置自定义id则自动生成
        if(object.get("id") == null || object.get("id").equals(0) || object.get("id").equals("")){
            map.put("id", getId(t));
        }
        // 创建时间
        map.put("createTime", vt);
        //如果开启了乐观锁 --> 版本更新
        if(checkLock(t)){
            map.put("version", vt);
        }
        for (Map.Entry<String,Object> entry : map.entrySet()) {
            object.put(entry.getKey(),entry.getValue());
        }
        return JSON.parseObject(object.toJSONString(), (Class<T>) t.getClass());
    }
    /**
     * 修改实体类转型
     * */
    private T updateModel(T t){
        JSONObject object = T2Jobj(t);
        Map<String,Object> map = new HashMap<String,Object>();
        Long vt = TimeKit.getTimestamp();
        // 修改时间
        map.put("reviseTime", vt);
        //如果开启了乐观锁 --> 版本更新
        if(checkLock(t)){
            map.put("version", vt);
        }
        for (Map.Entry<String,Object> entry : map.entrySet()) {
            object.put(entry.getKey(),entry.getValue());
        }
        return JSON.parseObject(object.toJSONString(), (Class<T>) t.getClass());
    }

    /**
     * 通过实体类名称读取业务id
     * */
    private int getBusinessIdForModel(T t){
//        String tableName = t.getClass().getSimpleName().toLowerCase();
//        String[] tableList = contextProperties.getTableName();
//        int index = 0;
//        for(int i=0;i<tableList.length;i++){
//            if(tableName.equals(tableList[i])){
//                index = Integer.parseInt(contextProperties.getBusinessId()[i]);
//                break;
//            }
//        }
        return 0;
    }
    /**
     * 生成全局id
     * */
    private String getId(T t){

        return IdKit.getId(t.getClass());
    }

    /**
     * 检查要更新的数据是否开启了乐观锁
     * */
    private boolean checkLock(T t){
        boolean check = false;
        Field[] fields = t.getClass().getDeclaredFields();
        for (int j = 0; j < fields.length; j++) {
            String name = (fields[j].getName()).toLowerCase();
            if(name.equals("version")){
                check = true;
                break;
            }
        }
        return check;
    }

    /**
     * 一般查询查询(不带分页)
     * */
    public List<T> query(List<Where> where){
        return super.selectList(createWhere(where));
    }
//    public List<JSONObject> query(String where) {
//        List<JSONObject> list = baseMapper.query(where);
//        return F.f2l(list,"id","creator","reverse");
//    }

    /**
     * 构造查询条件
     * */
    private EntityWrapper<T> createWhere(List<Where> where){
        EntityWrapper<T> ew = new EntityWrapper<T>();
        ew.eq("isDel",0);
        int len = where.size();
        for(int i=0;i<len;i++) {
            Where w = where.get(i);
            if (w.getOpt().toLowerCase().equals("like")) {
                ew.like(w.getAttr(),w.getVal().toString());
                //ew.and(w.getAttr() + " like '%{0}%'", w.getVal().toString());
            }
            if (w.getOpt().toLowerCase().equals("in")) {
                ew.in(w.getAttr(), w.getVal().toString());
            }
            if (w.getOpt().toLowerCase().equals("eq")) {
                ew.eq(w.getAttr(), w.getVal());
            }
            // <>
            if (w.getOpt().toLowerCase().equals("ne")) {
                ew.ne(w.getAttr(), w.getVal());
            }
            // >
            if (w.getOpt().toLowerCase().equals("gt")) {
                ew.gt(w.getAttr(), w.getVal());
            }
            // >=
            if (w.getOpt().toLowerCase().equals("ge")) {
                ew.ge(w.getAttr(), w.getVal());
            }
            // <
            if (w.getOpt().toLowerCase().equals("lt")) {
                ew.lt(w.getAttr(), w.getVal());
            }
            // <=
            if (w.getOpt().toLowerCase().equals("le")) {
                ew.le(w.getAttr(), w.getVal());
            }
            if (w.getOpt().toLowerCase().equals("notin")) {
                ew.notIn(w.getAttr(), w.getVal().toString());
            }
            if (w.getOpt().toLowerCase().equals("between")) {
                Object[] objs = w.getVal().toString().split("-");
                ew.between(w.getAttr(), objs[0], objs[1]);
            }
            if (w.getOpt().toLowerCase().equals("order")) {
                ew.orderBy(w.getAttr(), w.getVal().toString().toLowerCase().equals("asc") ? true : false);
            }
            if(w.getOpt().toLowerCase().equals("field")){
                ew.setSqlSelect(w.getVal().toString());
            }
        }

        return ew;
    }

}
