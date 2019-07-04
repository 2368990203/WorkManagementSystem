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
 * Created by htc on 2017/10/13
 * 第一层数据拦截
 */
@Component
@Order(1)
public class UserInterceptor implements HandlerInterceptor {
    @Autowired
    private CacheKit cacheKit;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        String token = request.getHeader("token");
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        //处理404
        if (V.isEqual(request.getServletPath(), "/error")) {
            response.setStatus(404);
            response.getWriter().write(J.s2r("找不到对应的路由"));
            return false;
        }
        //token判断
        if (V.isEmpty(token)) {
            response.setStatus(411);
            response.getWriter().write(J.s2r("token 口令不能为空 !"));
            return false;
        }


        //token有效性
        JSONObject user = cacheKit.user(token);

        if (V.isEmpty(user)) {
            response.setStatus(403);
            response.getWriter().write(J.s2r("token 口令认证失败 !"));
            return false;
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
