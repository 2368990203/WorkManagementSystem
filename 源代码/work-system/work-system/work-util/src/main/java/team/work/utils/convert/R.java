package team.work.utils.convert;

import com.alibaba.fastjson.JSONObject;
import team.work.utils.kit.StatusKit;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import team.work.utils.kit.StatusKit;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by htc on 2017/9/7.
 * 统一返回对象
 */
public class R extends HashMap<String,Object> {

    public static ResponseEntity error(){
        return error(500,"server error");
    }
    public static ResponseEntity error(String message){
        return error(400,message);
    }
    public static ResponseEntity error(int status, String message){
        return error(StatusKit.http(status),message,new JSONObject());
    }
    public static ResponseEntity error(HttpStatus status, String message, Object object){
        R r = new R();
        r.put("message",message);
        r.put("result",object);
        return new ResponseEntity<Map<String,Object>>(r,status);
    }

    public static ResponseEntity ok(String message){
        return ok(message,new JSONObject());
    }
    public static ResponseEntity ok(Object object){
        return ok("操作成功",object);
    }
    public static ResponseEntity ok(String message, Object object){
        R r = new R();
        r.put("message",message);
        r.put("result",object);
        return new ResponseEntity<Map<String,Object>>(r, HttpStatus.OK);
    }
    public R put(String key,Object val){
        super.put(key,val);
        return this;
    }
}

