package team.work.api.unified;

import com.alibaba.fastjson.JSONObject;
import com.sun.management.OperatingSystemMXBean;
import team.work.core.model.SysOperationRecord;
import team.work.core.model.SysUser;
import team.work.core.model.SysUserRole;
import team.work.core.service.impl.SysOperationRecordService;
import team.work.core.service.impl.SysRoleService;
import team.work.core.service.impl.SysUserRoleService;
import team.work.core.service.impl.SysUserService;
import team.work.core.tps.CacheKit;
import team.work.doc.SysUserLock;
import team.work.api.BaseApi;
import team.work.utils.convert.J;
import team.work.utils.convert.R;
import team.work.utils.convert.S;
import team.work.utils.convert.V;
import team.work.utils.kit.MD5Kit;

import team.work.utils.kit.TimeKit;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import team.work.doc.SysUserLock;

import javax.servlet.http.HttpServletRequest;
import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;


@Api(value = "系统授权管理")
@RestController
@RequestMapping("/v1/unified")
@CrossOrigin   //跨域服务注解
public class UserApi extends BaseApi {

    @Autowired
    private SysRoleService roleService;
    @Autowired
    public SysUserService userService;

    @Autowired
    private SysUserRoleService userRoleService;

    @Autowired
    private SysOperationRecordService sysOperationRecordService;

    @GetMapping("/user-token/{token}")
    @ApiOperation(value = "根据用户token读取数据")
    public Object getUserInfoByToken(@PathVariable("token") String token) {
        String info = cacheKit.getVal(token);
        if (V.isEmpty(info))
            return R.ok(new JSONObject());
        return R.ok(J.s2j(info));
    }

    @PostMapping("/user-login-out")
    @ApiOperation(value = "用户退出系统")
    public Object loginOut(@RequestHeader("token") String token) {
        JSONObject user = cacheKit.user(token);
        if (user == null) {
            return R.ok("退出成功");
        } else {
            // 操作日记初始化
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = attributes.getRequest();
            SysOperationRecord sysOperationRecord = new SysOperationRecord();
            String ip = getIpAddr(request);
            sysOperationRecord.setIpAddr(ip);
            sysOperationRecord.setCreator(user.getString("id"));
            sysOperationRecord.setCreateTime((int) TimeKit.getTimestamp());
            sysOperationRecord.setControl("系统授权管理");
            sysOperationRecord.setFunction("用户退出系统");
            sysOperationRecord.setServer(adminServer);
            sysOperationRecord.setStatus(0);
            sysOperationRecordService.createSysOperationRecord(sysOperationRecord);
            cacheKit.deleteVal(token);
            return R.ok("退出成功");
        }
    }

    @PutMapping("/user-cache")
    @ApiOperation(value = "用户更新缓存")
    public Object cacheUpdate(@RequestHeader("token") String token) {
        JSONObject user = cacheKit.user(token);
        if (V.isEmpty(user))
            return R.error(403, "登录状态已经失效");

        if (user.getString("role").length() == 0)
            return R.error(403, "角色信息异常");

        SysUserRole ur = userRoleService.findByUserId(user.getString("id"));
        user.put("auth", roleService.getAuthInfoByRoleId(ur.getRoleId()));
        user.put("role", ur.getRoleId());

        updateCacheUser(token, user);
        return R.ok(user);
    }

    @PostMapping("/user-lock-system/{code}")
    @ApiOperation(value = "用户锁定系统")
    public Object lockSystem(@PathVariable("code") String code,
                             @RequestHeader("token") String token) {
        return R.ok("当前系统桌面已经被锁定", lock(token, code));
    }

    @PutMapping("/user-lock-system/{code}")
    @ApiOperation(value = "用户解锁系统")
    public Object unlockSystem(@PathVariable("code") String code,
                               @RequestBody() SysUserLock model,
                               @RequestHeader("token") String token) {
        //先判断用户是否开通了这个子系统
        JSONObject user = cacheKit.user(token);
        SysUser u = userService.selectById(user.getString("id"));
        if (u == null)
            return R.error(403, "登录状态已经失效");

        //密码处理
        String pwd = MD5Kit.encode(model.getPassword() + u.getSalt());
        if (!V.isEqual(pwd, u.getPassword()))
            return R.error("您输入的密码错误");

        user.put("lock", 0);
        updateCacheUser(token, user);
        return R.ok("当前系统桌面解锁成功", user);
    }

    //桌面锁定
    private JSONObject lock(String token, String code) {
        JSONObject user = cacheKit.user(token);
        String lock = S.apppend("lock-", user.getString("id"));
//        if(!RedisKit_bak.getJedis().exists(lock)){
//            RedisKit_bak.getJedis().sadd(lock,code);
//        }
        user.put("lock", 1);
        updateCacheUser(token, user);
        return user;
    }

    /*@GetMapping("/system-info")
    @ApiOperation(value = "读取服务器状态")
    public Object GetSystemInfo(){
        return R.ok(info());
    }
    // 获取内存使用率
    private JSONObject info(){
        OperatingSystemMXBean osmxb = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        // 总的物理内存+虚拟内存
        long totalvirtualMemory = osmxb.getTotalSwapSpaceSize();
        // 剩余的物理内存
        long freePhysicalMemorySize = osmxb.getFreePhysicalMemorySize();
        Double compare = (Double)(1 - freePhysicalMemorySize * 1.0 / totalvirtualMemory) * 100;

        Runtime r = Runtime.getRuntime();
        Properties props = System.getProperties();

        JSONObject info = new JSONObject();
        info.put("os",props.getProperty("os.name"));
        info.put("arch",props.getProperty("os.arch"));
        info.put("version",props.getProperty("os.version"));
        info.put("per",compare.intValue());
        JSONObject jvm = new JSONObject();
        jvm.put("total",r.totalMemory()/102400);
        jvm.put("free",r.freeMemory()/102400);
        jvm.put("cpu",r.availableProcessors());
        jvm.put("version",props.getProperty("java.version"));
        info.put("jvm",jvm);
        return info;
    }*/
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
