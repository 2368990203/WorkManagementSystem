package team.work.utils.convert;

import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class F {
    public static String s(String format,Object... str){
        return String.format(format,str);
    }

    public static List<JSONObject> f2l(List<JSONObject> objects,String... fields){
        if(objects.size() > 0){
            for(JSONObject object:objects){
                for(String field:fields){
                    object.put(field,object.getString(field));
                }
            }
            return objects;
        }else {
            return new ArrayList<>();
        }
    }

    public static List<JSONObject> g2l(List<JSONObject> objects,String... fields){
        List<JSONObject> temp = new ArrayList<>();
        if(objects.size() > 0){
            for(JSONObject object:objects){
                JSONObject o = new JSONObject();
                for(String field:fields){
                    o.put(field,object.get(field));
                }
                temp.add(o);
            }
            return temp;
        }else {
            return new ArrayList<>();
        }
    }

    public static JSONObject f2j(JSONObject objects,String... fields){
        if(!V.isEmpty(objects)) {
            for (String field : fields) {
                if (!V.isEmpty(objects.getString(field))) {
                    objects.put(field, objects.getString(field));
                }
            }
            return objects;
        }else{
            return null;
        }
    }

    public static JSONObject g2j(JSONObject objects,String... fields){
        if(!V.isEmpty(objects)) {
            JSONObject o = new JSONObject();
            for (String field : fields) {
                if (!V.isEmpty(objects.getString(field))) {
                    o.put(field, objects.getString(field));
                }
            }
            return o;
        }else{
            return null;
        }
    }

}
