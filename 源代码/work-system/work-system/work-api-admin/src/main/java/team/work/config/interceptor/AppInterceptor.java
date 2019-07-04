package team.work.config.interceptor;

import com.alibaba.fastjson.JSONObject;
import team.work.core.tps.CacheKit;
import team.work.utils.convert.J;
import team.work.utils.convert.V;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by htc on 2017/9/9.
 * 第二层
 * token认证拦截
 * token有效性 --> token认证
 */
@Component
@Order(2)
public class AppInterceptor implements HandlerInterceptor {
    @Autowired
    private CacheKit cacheKit;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        String token = request.getHeader("token");
        String path = request.getServletPath();
        String method = request.getMethod();
        response.setHeader("Content-type", "text/html;charset=UTF-8");


        //业务处理
        JSONObject user = cacheKit.user(token);
        if (path.indexOf("/common/app/") < 0) {
            if (V.isEmpty(user)) {
                response.setStatus(403);
                response.getWriter().write(J.s2r("token 口令认证失败 !"));
                return false;
            }
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
