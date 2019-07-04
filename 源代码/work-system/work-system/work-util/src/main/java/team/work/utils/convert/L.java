package team.work.utils.convert;

import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class L {

    public static String getAttrs(Object list, String attr){
        List<String> attrs = new ArrayList<String>();
        List<JSONObject> objects = J.o2l(list);
        for(JSONObject object:objects){
            attrs.add(object.getString(attr));
        }
        return String.join(",",attrs);
    }
    public static String getAttrs(List<JSONObject> list, String attr){
        List<String> attrs = new ArrayList<String>();
        for(JSONObject object:list){
            attrs.add(object.getString(attr));
        }
        return String.join(",",attrs);
    }

    public static List<JSONObject> f2o(List<JSONObject> list, String attr){
        for(JSONObject o:list){
            o.put(attr, new JSONObject());
        }
        return list;
    }
    public static List<JSONObject> f2l(List<JSONObject> list, String attr){
        for(JSONObject o:list){
            o.put(attr, new ArrayList<>());
        }
        return list;
    }

    // 创建一个空属性
    public static List<JSONObject> a2l(List<JSONObject> list,Object object, String... attrs){
        for(JSONObject o:list){
            for(String attr:attrs) {
                o.put(attr, object);
            }
        }
        return list;
    }
    public static JSONObject a2o(JSONObject o,Object object, String... attrs){
        for(String attr:attrs) {
            o.put(attr, object);
        }
        return o;
    }
    // 更换属性 / 创建对等属性
    public static List<JSONObject> u2l(List<JSONObject> list, String old, String news){
        for(JSONObject o:list){
            o.put(news, o.getString(old));
        }
        return list;
    }
}
