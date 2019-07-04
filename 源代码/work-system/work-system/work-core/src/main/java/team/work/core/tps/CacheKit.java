package team.work.core.tps;

import com.alibaba.fastjson.JSONObject;
import team.work.utils.convert.J;
import team.work.utils.convert.S;
import team.work.utils.convert.V;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import team.work.utils.convert.J;
import team.work.utils.convert.V;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author Taller
 * @create 2017-12-01 9:56
 */
@Component
public class CacheKit {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    /*
    // 获取所有登陆用户
    public List<JSONObject> getAllLoginUsers() {
        List<JSONObject> list = new ArrayList<>();

        Set<String> keys = stringRedisTemplate.keys("*");
        for (String key : keys) {
            if (key.length() == 32) {
                String obj = stringRedisTemplate.opsForValue().get(key);
                if (obj.indexOf("role") != -1) {
                    JSONObject userObj = JSONObject.parseObject(obj);
                    JSONObject infoObj = userObj.getJSONObject("info");

                    infoObj.put("token", key);
                    list.add(infoObj);
                }
            }
        }

        return list;
    }

    // 获取所有登陆令牌
    public List<JSONObject> getAllLoginToken() {
        List<JSONObject> list = new ArrayList<>();

        Set<String> keys = stringRedisTemplate.keys("*");
        for (String key : keys) {
            if (key.length() == 32) {
                String obj = stringRedisTemplate.opsForValue().get(key);
                if (obj.indexOf("role") != -1) {
                    JSONObject infoObj = new JSONObject();

                    infoObj.put("token", key);
                    list.add(infoObj);
                }
            }
        }

        return list;
    }*/


    // 字符串设置
    public Boolean setVal(String key, String val, int times) {
        try {
            if (times > 0) {
                stringRedisTemplate.opsForValue().set(key, val, times, TimeUnit.SECONDS);
            } else {
                times = 604800;
                stringRedisTemplate.opsForValue().set(key, val, times, TimeUnit.SECONDS);
                // stringRedisTemplate.opsForValue().set(key, val);
            }

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // 字符串读取
    public String getVal(String key) {
        String val = "";
        try {
            val = stringRedisTemplate.opsForValue().get(key);
            val = val == null ? "" : val;
        } catch (Exception ex) {
        }
        return val;
    }

    // 字符串删除
    public void deleteVal(String key) {
        stringRedisTemplate.delete(key);
    }

    // 多个字符串删除
    public void deleteVals(String delkeys) {
        Set<String> keys = stringRedisTemplate.keys(delkeys);
        for (String key : keys) {
            stringRedisTemplate.delete(key);
        }
    }


    public JSONObject user(String key) {
        if (!V.isEmpty(getVal(key))) {
            return J.s2j(getVal(key));
        } else {
            return null;
        }
    }

    public String getUserId(String key) {
        if (!V.isEmpty(user(key))) {
            return user(key).getString("id");
        } else {
            return null;
        }
    }

    public String getName(String key) {
        JSONObject userObj = user(key);
        if (!V.isEmpty(userObj)) {
            String name = userObj.getString("name");
            if (!V.isEmpty(name)) {
                return name;
            } else {
                JSONObject info = userObj.getJSONObject("info");
                if (!V.isEmpty(info)) {
                    String info_name = info.getString("name");
                    if (!V.isEmpty(info_name)) {
                        return info_name;
                    } else {
                        return null;
                    }
                } else {
                    return null;
                }
            }
        } else {
            return null;
        }
    }

    public String getNumberByCache(String key) {
        if (!V.isEmpty(user(key))) {
            return user(key).getString("number");
        } else {
            return null;
        }
    }


    // 对象设置
    public void setObject(String key, Object object, int times) {
        ValueOperations<String, Object> operations = redisTemplate.opsForValue();
        if (times > 0) {
            operations.set(key, object, times, TimeUnit.SECONDS);
        } else {
            times = 604800;
            operations.set(key, object, times, TimeUnit.SECONDS);
            //operations.set(key, object);
        }
    }

    // 对象读取
    public JSONObject getJSONObject(String key) {
        JSONObject object = null;
        ValueOperations<String, Object> operations = redisTemplate.opsForValue();
        try {
            object = J.o2j(operations.get(key));
        } catch (Exception e) {
        }
        return object;
    }

    // 对象读取
    public Object getObject(String key) {
        Object object = null;
        ValueOperations<String, Object> operations = redisTemplate.opsForValue();
        try {
            object = operations.get(key);
        } catch (Exception e) {
        }
        return object;
    }

    // 删除对象
    public void deleteObject(String key) {
        redisTemplate.delete(key);
    }


    // 多个对象删除
    public void deleteObjects(String delkeys) {
        Set<String> keys = redisTemplate.keys(delkeys);
        for (String key : keys) {
            redisTemplate.delete(key);
        }
    }


}
