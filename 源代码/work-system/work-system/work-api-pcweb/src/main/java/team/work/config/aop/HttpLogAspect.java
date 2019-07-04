package team.work.config.aop;

import com.alibaba.fastjson.JSONObject;
import team.work.core.model.SysOperationRecord;
import team.work.core.service.impl.SysOperationRecordService;
import team.work.core.tps.CacheKit;
import team.work.utils.convert.V;
import team.work.utils.kit.TimeKit;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;


@Aspect
@Component
public class HttpLogAspect {
    protected static org.slf4j.Logger logger = LoggerFactory.getLogger(HttpLogAspect.class);

    @Autowired
    public CacheKit cacheKit;

    @Autowired
    private SysOperationRecordService sysOperationRecordService;


    @Pointcut("execution(public * team.work.api..*.*(..))")
    public void Aop() {
    }

    @Before("Aop()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {

    }

    @AfterReturning(returning = "ret", pointcut = "Aop()")
    public void doAfterReturning(JoinPoint joinPoint, Object ret) throws Throwable {

        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String token = request.getHeader("token");

        //控制器名称
        String controlCode = joinPoint.getSignature().getDeclaringTypeName();
        //控制器注解
        Api control = (Api) joinPoint.getSignature().getDeclaringType().getAnnotation(Api.class);

        //方法名称
        String methodName = joinPoint.getSignature().getName();

        //方法注解
        Class[] parameterTypes = ((MethodSignature) joinPoint.getSignature()).getMethod().getParameterTypes();
        Method method = joinPoint.getTarget().getClass().getMethod(methodName, parameterTypes);
        ApiOperation function = method.getAnnotation(ApiOperation.class);

        //拦截的方法参数
        JSONObject info = new JSONObject();
        String[] argNames = ((MethodSignature) joinPoint.getSignature()).getParameterNames();
        for (int i = 0; i < argNames.length; i++) {
            info.put(argNames[i], joinPoint.getArgs()[i]);
        }
        int status = 0;
        // 处理完请求，返回内容
        if (ret instanceof ResponseEntity) {
            ResponseEntity<Map<String, Object>> res = (ResponseEntity<Map<String, Object>>) ret;
            status = res.getStatusCode().value();
        }


        if (methodName.indexOf("get") == -1 && methodName.indexOf("create") == -1) {//不存临时答题记录
            //token判断
            boolean flag = false;
            if (!V.isEmpty(token)) {
                if (!V.isEmpty(cacheKit.getUserId(token))) {
                    String userid = cacheKit.getUserId(token);
                    String controlName = control.value();
                    if (controlName.indexOf("_") != -1) {
                        controlName = controlName.substring(controlName.indexOf("_") + 1);
                    }
                    SysOperationRecord sysOperationRecord = new SysOperationRecord();
                    sysOperationRecord.setCreator(userid);
                    sysOperationRecord.setCreateTime((int) TimeKit.getTimestamp());
                    sysOperationRecord.setIpAddr(getIpAddr(request));
                    sysOperationRecord.setControl(controlName);
                    sysOperationRecord.setFunction(function.value());
                    sysOperationRecord.setServer(1);
                    if (status == 200) {
                        status = 0;
                    } else {
                        status = 1;
                    }
                    sysOperationRecord.setStatus(status);
                    if(methodName.indexOf("save")==-1) {
                        sysOperationRecord.setParameter(info.toJSONString());
                    }
                    sysOperationRecordService.createSysOperationRecord(sysOperationRecord);
                    flag = true;
                }
            }
            if (!flag) {
                System.out.println("**************************************************************************************");
                logger.info("token : " + token);
                logger.info("IP : " + getIpAddr(request));
                logger.info("controlCode:" + controlCode);
                logger.info("methodName:" + methodName);
                logger.info("info:" + info);
                logger.info("status: " + status);
            }

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
