package team.work.utils.convert;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * Created by htc on 2017/9/7.
 * JSON对象操作类
 */
public class J {
    public static String o2s(Object object){
        return JSONObject.toJSONString(object);
    }
    public static JSONObject o2j(Object object){
        return JSONObject.parseObject(o2s(object));
    }
    public static <T> T o2m(Object object,Class<T> cls){
        return JSONObject.parseObject(o2s(object),cls);
    }
    public static List<JSONObject> o2l(Object object){
        return JSONObject.parseArray(o2s(object),JSONObject.class);
    }
    public static <T> List<T> o2l(Object object,Class<T> cls){
        return JSONObject.parseArray(o2s(object),cls);
    }

    public static JSONObject s2j(String json){
        return JSONObject.parseObject(json);
    }
    public static <T> T s2m(String json,Class<T> cls){
        return JSONObject.parseObject(json,cls);
    }
    public static List<JSONObject> s2l(String json){
        return JSONObject.parseArray(json,JSONObject.class);
    }
    public static <T> List<T> s2l(String json,Class<T> cls){
        return JSONObject.parseArray(json,cls);
    }

    public static Map<String,Object> o2m(Object object){
        return JSON.parseObject(o2s(object), Map.class);
    }
    public static Map<String,Object> s2m(String json){
        return JSON.parseObject(json, Map.class);
    }

    public static String s2r(String message){
        JSONObject object = new JSONObject();
        object.put("message",message);
        object.put("result",new JSONObject());
        return J.o2s(object);
    }
    public static String s2r(String message,Object o){
        JSONObject object = new JSONObject();
        object.put("message",message);
        object.put("result",o);
        return J.o2s(object);
    }

    public static JSONObject arr2j(String arr[]){
        JSONObject object = new JSONObject();
        for(String info: arr){
            object.put(info,"");
        }
        return object;
    }
}
