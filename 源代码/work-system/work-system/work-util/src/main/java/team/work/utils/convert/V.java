package team.work.utils.convert;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;


public class V {
    /**
     * 验证是否为空
     * */
    public static Boolean isEmpty(Object field){
        if(field == null)
            return true;
        if(field == "")
            return true;
        if(field.toString().trim().length() == 0)
           return true;
        return false;
    }
    public static Boolean isLength(Object field,int len){
        if(field.toString().trim().length() < len)
            return true;
        return false;
    }

    //验证List是否为空
    public static Boolean isEmpty(List list){
        return list == null || list.size() == 0;
    }

    /**
     * 批量验证是否为空
     * */
    public static JSONObject checkEmpty(Map<String,String> kv, Object object){
        Set<String> keys = kv.keySet();
        JSONObject vObject = J.o2j(object);
        JSONObject result = new JSONObject();
        result.put("check",false);
        for(String key :keys){
            if(V.isEmpty(vObject.get(key))){
                result.put("check",true);
                result.put("message",kv.get(key));
                break;
            }
        }
        return result;
    }
    /**
     * 判断2个字符串相等
     * */
    public static Boolean isEqual(String... val){
        if(StringUtils.equals(val[0],val[1]))
            return true;
        return false;
    }
}
