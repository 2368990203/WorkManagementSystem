package team.work.api;

import com.alibaba.fastjson.JSONObject;
import team.work.core.tps.CacheKit;
import team.work.utils.base.TApi;
import team.work.utils.convert.F;
import team.work.utils.convert.J;
import team.work.utils.convert.V;
import team.work.utils.kit.IdKit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.web.client.RestClientException;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Taller
 * @create 2017-12-01 10:54
 */
public class BaseApi extends TApi {
    @Autowired
    public CacheKit cacheKit;
    @Autowired
    public DataSourceTransactionManager transactionManager;

    public int adminServer = 0;

    public int pcServer = 1;

    public int wechatServer = 2;


    /**
     * 对象转换
     */
    public <T> T o2c(Object o, String token, Class cls) {
        JSONObject object = J.o2j(o);
        // 修改人
        object.put("creator", cacheKit.user(token).get("id"));
        return o2t(object, (Class<T>) cls);
    }

    public <T> T o2u(Object o, String token, Class cls) {
        JSONObject object = J.o2j(o);
        // 修改人
        object.put("reviser", cacheKit.user(token).get("id"));
        return o2t(object, (Class<T>) cls);
    }

    public <T> T o2t(Object o, Class cls) {
        JSONObject object = J.o2j(o);
        //字符串数据处理
        Map<String, Object> map = J.o2m(o);
        Map.Entry entry;
        for (Iterator iterator = map.entrySet().iterator(); iterator.hasNext(); ) {
            entry = (Map.Entry) iterator.next();
            String val = entry.getValue().toString().trim();
            if (val.indexOf("'") >= 0)
                val = val.replaceAll("'", "\'");
            if (val.indexOf("\"") >= 0)
                val = val.replaceAll("\"", "\"");
            object.put(entry.getKey().toString(), val);
        }
        if (object.get("id") == null)
            object.put("id", IdKit.getId(cls));
        return J.o2m(object, (Class<T>) cls);
    }

    /**
     * 后台用户缓存
     *
     * @param
     */
    public Boolean initCacheUser(JSONObject user, String token, int SESSION_TIME) {
        String uid = F.s("ump-pcweb-%s-user", user.getString("id"));
        //判断是否存在uid数据
        String oldToken = cacheKit.getVal(uid);
        if (!cacheKit.setVal(uid, token, 0))
            return false;
        if (!V.isEmpty(oldToken))
            cacheKit.deleteVal(oldToken);

        return cacheKit.setVal(token, J.o2s(user), SESSION_TIME);
    }


    /**
     * 更新缓存
     */
    public Boolean updateCacheUser(String token, JSONObject user) {
        return cacheKit.setVal(token, J.o2s(user), 0);
    }



    public String getUserIdByCache(String token) {
        String json = cacheKit.getVal(token);
        JSONObject object = J.s2j(json);
        return object.getString("id");
    }

    public String getName(String token) {
        String json = cacheKit.getVal(token);
        JSONObject object = J.s2j(json);
        if (object != null) {
            return object.getString("name");
        } else {
            return null;
        }
    }

    public String getNumberByCache(String token) {
        String json = cacheKit.getVal(token);
        JSONObject object = J.s2j(json);
        if (object != null) {
            return object.getString("number");
        } else {
            return null;
        }
    }


    public List<String> getAacademyAndDepartByCache(String token) {
        String json = cacheKit.getVal(token);
        List<String> departs = new ArrayList<>();
        JSONObject object = J.s2j(json);
        if (object != null) {
            JSONObject info = object.getJSONObject("info");
            if (info != null) {
                if (!V.isEmpty(info.getString("academyCode"))) {
                    departs.add(info.getString("academyCode"));
                }
                if (!V.isEmpty(info.getString("departCode"))) {
                    departs.add(info.getString("departCode"));
                }
                return departs;

            } else {
                return null;
            }
        } else {
            return null;
        }
    }



    public String outRestClientException(RestClientException e) {

        String message = e.getMessage();
        String errorcodeStr = message.substring(0, 3);
        String errorcodeTest = "^\\d{3}$";//错误号校验
        Pattern errorcodeReg = Pattern.compile(errorcodeTest);
        Matcher errorcodeMat = errorcodeReg.matcher(errorcodeStr);
        boolean errorcodeFlag = errorcodeMat.matches();
        if (errorcodeFlag) {
            int errcode = Integer.parseInt(errorcodeStr);
            if (errcode == 502) {
                return "学校接口服务器维护中，如有疑问请联系学校网络中心！";
            } else {
                return "学校接口服务器异常，请联系学校网络中心处理！";
            }
        } else {
            return "学校接口服务器异常，请联系学校网络中心处理！";
        }
    }


    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            if ("127.0.0.1".equals(ip) || "0.0.0.0".equals(ip) || "0:0:0:0:0:0:0:1".equals(ip)) {
                // 根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                ip = inet.getHostAddress();
            }
        }

        // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if (ip != null && ip.length() > 15) {
            if (ip.indexOf(",") > 0) {
                ip = ip.substring(0, ip.indexOf(","));
            }
        }
        return ip;
    }
}
