package team.work.api;

import com.alibaba.fastjson.JSONObject;
import team.work.core.tps.CacheKit;
import team.work.utils.base.TApi;
import team.work.utils.kit.IdKit;
import team.work.utils.kit.OSKit;
import team.work.utils.kit.TimeKit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import team.work.utils.convert.F;
import team.work.utils.convert.J;
import team.work.utils.convert.S;
import team.work.utils.convert.V;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.Map;

/**
 * @author Taller
 * @create 2017-12-01 10:54
 */
public class BaseApi extends TApi {
    @Autowired
    public CacheKit cacheKit;
//    @Autowired
//    public CustomService customService;
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
        String uid = F.s("ump-admin-%s-user", user.getString("id"));
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


    public String getNumberByCache(String token) {
        String json = cacheKit.getVal(token);
        JSONObject object = J.s2j(json);
        if (object != null) {
            return object.getString("number");
        } else {
            return null;
        }
    }


    public String getName(String token) {
        String json = cacheKit.getVal(token);
        JSONObject object = J.s2j(json);
        if (object != null) {
                String name =object.getString("name");
                if (!V.isEmpty(name)) {
                    return  name;
                }else{
                    JSONObject info = object.getJSONObject("info");
                    if (!V.isEmpty(info)) {
                        String info_name = info.getString("name");
                        if (!V.isEmpty(info_name)) {
                            return  info_name;
                        }else{
                            return null;
                        }
                    }else{
                        return null;
                    }
                }
        } else {
            return null;
        }
    }

    public String getUserIdByCache(String token) {
        String json = cacheKit.getVal(token);
        JSONObject object = J.s2j(json);
        if (object != null) {
            return object.getString("id");
        } else {
            return null;
        }
    }


    public String getAcademyCodeByCache(String token) {
        String json = cacheKit.getVal(token);
        JSONObject object = J.s2j(json);
        if (object != null) {
            return object.getString("academyCode");
        } else {
            return null;
        }
    }


    public String getDepartCodeByCache(String token) {
        String json = cacheKit.getVal(token);
        JSONObject object = J.s2j(json);
        if (object != null) {
            return object.getString("departCode");
        } else {
            return null;
        }
    }


    public Integer getVisibleByCache(String token) {
        String json = cacheKit.getVal(token);
        JSONObject object = J.s2j(json);
        if (object != null) {
            return object.getInteger("visible");
        } else {
            return null;
        }
    }


    public Integer getTypeByCache(String token) {
        String json = cacheKit.getVal(token);
        JSONObject object = J.s2j(json);
        if (object != null) {
            return object.getInteger("type");
        } else {
            return null;
        }
    }

    public String getLoginNumberCache(String token) {
        String json = cacheKit.getVal(token);
        JSONObject object = J.s2j(json);
        if (object != null) {
            return object.getString("loginNumber");
        } else {
            return null;
        }
    }



    public Boolean checkUserDataAuth(String token, String number) {
        boolean flag = false;
        //获取数据可见性
        Integer visible = getVisibleByCache(token);
        if (V.isEmpty(visible)) {
            flag = false;
        }

        if (visible == 1) {//1-全部（组织部）
            //继续操作
            flag = true;

        } else if (visible == 2) {//2-院级
            String academyCode = getAcademyCodeByCache(token);
            if (V.isEmpty(academyCode)) {
                flag = false;
            }
//            flag = customService.checkUserByAcademyCode(academyCode, number);

        } else if (visible == 3) {//3-个人(班主任、联络员)
            //获取学号/工号
            String loginNumber = getLoginNumberCache(token);
            if (V.isEmpty(loginNumber)) {
                flag = false;
            }
//            flag = customService.checkUserByLinkNumber(loginNumber, number);

        } else {
            flag = false;
        }

        return flag;


    }


    public String uploadFile(HttpServletRequest req) {
        MultipartHttpServletRequest multiReq = (MultipartHttpServletRequest) req;
        //  System.out.println(multiReq.getMultiFileMap().toString());

        //  System.out.println(multiReq.getFileNames().toString());
        // 获取上传文件的路径
        String uploadFilePath = multiReq.getFile("file").getOriginalFilename();
        //    System.out.println("uploadFlePath:" + uploadFilePath);
        // 截取上传文件的文件名
       /* String uploadFileName = uploadFilePath.substring(
                uploadFilePath.lastIndexOf('\\') + 1, uploadFilePath.indexOf('.'));*/

        Boolean linux = OSKit.isLinux();
        String time = TimeKit.formatNow("yyyyMMdd");

        // 截取上传文件的后缀
        String uploadFileSuffix = uploadFilePath.substring(
                uploadFilePath.indexOf('.') + 1, uploadFilePath.length());


        String rootpath = getClass().getResource("/").getPath();
        try {
            rootpath = java.net.URLDecoder.decode(rootpath, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (linux) {
            rootpath = rootpath.replace("file:", "");
            rootpath = rootpath.replace("\\", "/");

        } else {
            rootpath = rootpath.substring(1);
            rootpath = rootpath.replace("/", "\\");
        }

        String filepath = S.apppend(rootpath, File.separator, "upload", File.separator, time, File.separator, S.getToken(), ".", uploadFileSuffix);


        System.out.println(filepath);

        File savedFile = new File(filepath);
        if (!savedFile.getParentFile().exists()) {
            savedFile.getParentFile().mkdirs();
        }

        try {

            boolean isCreateSuccess = savedFile.createNewFile(); // 是否创建文件成功
            MultipartFile file = multiReq.getFile("file");
            if (isCreateSuccess) {      //将文件写入
                //第一种
                file.transferTo(savedFile);
            }

            return filepath;


        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    public JSONObject getImportStatusObj(String batch) {
        JSONObject obj = new JSONObject();


        if (V.isEmpty(cacheKit.getVal(batch + "total")) || V.isEmpty(cacheKit.getVal(batch + "now")) || V.isEmpty(cacheKit.getVal(batch + "res")) || V.isEmpty(cacheKit.getVal(batch + "status"))) {
            obj.put("total", 0);
            obj.put("now", 0);
            obj.put("res", 0);
            obj.put("status", 0);

        } else {
            obj.put("res", Integer.parseInt(cacheKit.getVal(batch + "res")));
            obj.put("status", Integer.parseInt(cacheKit.getVal(batch + "status")));
            obj.put("now", Integer.parseInt(cacheKit.getVal(batch + "now")));

            if (Integer.parseInt(cacheKit.getVal(batch + "res")) == 1) {
                obj.put("total", Integer.parseInt(cacheKit.getVal(batch + "now")));
                cacheKit.deleteVal(batch + "total");
                cacheKit.deleteVal(batch + "now");
                cacheKit.deleteVal(batch + "res");
                cacheKit.deleteVal(batch + "status");
            } else {
                obj.put("total", Integer.parseInt(cacheKit.getVal(batch + "total")));
            }

        }
        return obj;

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


