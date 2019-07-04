package team.work.utils.base;

import com.alibaba.fastjson.JSONObject;
import team.work.utils.bean.Where;
import team.work.utils.constant.Global;
import team.work.utils.convert.J;
import team.work.utils.convert.V;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import team.work.utils.bean.Where;
import team.work.utils.constant.Global;
import team.work.utils.convert.J;
import team.work.utils.convert.V;

import java.util.*;


public class TApi {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    public List<Where> wheres = null;


    /**
     * 格式化对象删除多余属性
     * creator\createTime\reviser\reviseTime\isDel
     * */
    public JSONObject fm1(Object object){
        JSONObject o = J.o2j(object);
        o.remove("creator");
        o.remove("createTime");
        o.remove("reviser");
        o.remove("reviseTime");
        o.remove("isDel");
        return o;
    }
    public List<JSONObject> fl1(Object objects){
        List<JSONObject> jsons = J.o2l(objects);
        for(JSONObject object:jsons){
            object.remove("creator");
            object.remove("createTime");
            object.remove("reviser");
            object.remove("reviseTime");
            object.remove("isDel");
        }
        return jsons;
    }
    /**
     * 格式化对象删除多余属性
     * reviser\reviseTime\isDel
     * */
    public JSONObject fm2(Object object){
        JSONObject o = J.o2j(object);
        o.remove("reviser");
        o.remove("reviseTime");
        o.remove("isDel");
        return o;
    }
    public List<JSONObject> fl2(Object objects){
        List<JSONObject> jsons = J.o2l(objects);
        for(JSONObject object:jsons){
            object.remove("reviser");
            object.remove("reviseTime");
            object.remove("isDel");
        }
        return jsons;
    }

    /**
     * 格式化用户信息 ==> attr
     * */
    public List<JSONObject> formUserInfo(Object object){
        List<JSONObject> userInfos = new ArrayList<JSONObject>();
        Map<String, Object> map = J.o2m(object);
        //避免字段缺失
        for(String field: Global.USER_INFO){
            if(map.get(field) == null)
                map.put(field,"");
        }
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            JSONObject userInfo = new JSONObject();
            userInfo.put("attr",entry.getKey());
            userInfo.put("val",entry.getValue().toString().trim());
            userInfos.add(userInfo);
        }
        return userInfos;
    }
    public List<JSONObject> formUserInfo(){
        List<JSONObject> userInfos = new ArrayList<JSONObject>();
        Map<String, Object> map = new HashMap<>();
        //避免字段缺失
        for(String field: Global.USER_INFO){
            if(map.get(field) == null)
                map.put(field,"");
        }
        return userInfos;
    }

    public List<JSONObject> getByType(List<JSONObject> list, String type){
        List<JSONObject> objects = new ArrayList<>();
        if(list.size() >0){
            for(JSONObject object:list){
                if(V.isEqual(object.getString("type"),type)){
                    objects.add(object);
                }
            }
        }
        return objects;
    }
}
