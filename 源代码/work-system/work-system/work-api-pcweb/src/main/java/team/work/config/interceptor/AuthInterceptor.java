package team.work.config.interceptor;

import com.alibaba.fastjson.JSONObject;
import team.work.core.tps.CacheKit;
import team.work.utils.convert.J;
import team.work.utils.convert.S;
import team.work.utils.convert.V;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by htc on 2017/9/9.
 * 第二层
 * token认证拦截
 * token有效性 --> token认证
 */
@Component
@Order(2)
public class AuthInterceptor implements HandlerInterceptor {
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

        if (V.isEmpty(user)) {
            response.setStatus(403);
            response.getWriter().write(J.s2r("token 口令认证失败 !"));
            return false;
        }

        /****************token认证****************/
        //有效性判断
        if (user.getInteger("status") == 0) {
            response.setStatus(401);
            response.getWriter().write(J.s2r("当前用户状态被锁定无法访问!"));
            return false;
        }
        /**************** 界面锁 ****************/
        if (path.indexOf("user-lock-system") == -1) {
            if(user.getInteger("lock") == 1){
                response.setStatus(406);
                response.getWriter().write(J.s2r("操作界面处于锁定状态!",user));
                return false;
            }
        }

        /**************** 权限认证 ****************/
        String className = getClassName(o);

        if (V.isEqual(method.toLowerCase(), "get")) {
            int i = 0;
            List<JSONObject> subs = getSubs(J.o2l(user.get("auth")));
            String cls[] = className.split("\\.");
            className = S.apppend(cls[0], ".", "get");
            for (JSONObject sub : subs) {
                String code = sub.getString("code");
                if (code.length() > 0) {
                    String item[] = sub.getString("code").split("\\.");
                    code = S.apppend(item[0], ".", "get");
                    if (V.isEqual(code, className)) {
                        i = 1;
                        break;
                    }
                } else {
                    response.setStatus(401);
                    response.getWriter().write(J.s2r("当前页面尚未配置任何权限暂无法进行操作!"));
                    return false;
                }
            }
            if (i == 0) {
                response.setStatus(401);
                response.getWriter().write(J.s2r("当前你没有权限进行此操作!"));
                return false;
            }
        } else {
            List<JSONObject> btns = getBtns(J.o2l(user.get("auth")));

            if (btns.size() == 0) {
                response.setStatus(401);
                response.getWriter().write(J.s2r("当前你没有权限进行此操作!"));
                return false;
            }
            int i = 0;

            for (JSONObject btn : btns) {
                String code = btn.getString("code");
                if (V.isEqual(code, className)) {
                    i = 1;
                    break;
                }
            }
            if (i == 0) {
                response.setStatus(401);
                response.getWriter().write(J.s2r("当前你没有权限进行此操作!"));
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


    //读取所有的btn权限
    private List<JSONObject> getBtns(List<JSONObject> auths) {
        List<JSONObject> btns = new ArrayList<>();
        for (int i = 0; i < auths.size(); i++) {
            List<JSONObject> modules = J.o2l(auths.get(i).get("module"));
            for (int j = 0; j < modules.size(); j++) {
                List<JSONObject> subs = J.o2l(modules.get(j).get("sub"));
                for (int k = 0; k < subs.size(); k++) {
                    List<JSONObject> buttons = J.o2l(subs.get(k).get("btns"));
                    for (int l = 0; l < buttons.size(); l++) {
                        btns.add(buttons.get(l));
                    }
                }
            }
        }
        return btns;
    }

    private List<JSONObject> getSubs(List<JSONObject> auths) {
        List<JSONObject> menus = new ArrayList<>();
        for (int i = 0; i < auths.size(); i++) {
            List<JSONObject> modules = J.o2l(auths.get(i).get("module"));
            for (int j = 0; j < modules.size(); j++) {
                List<JSONObject> subs = J.o2l(modules.get(j).get("sub"));
                for (int k = 0; k < subs.size(); k++) {
                    menus.add(subs.get(k));
                }
            }
        }
        return menus;
    }

    // 获取请求接口函数
    private String getClassName(Object object) {
        JSONObject obj = J.o2j(object);
        JSONObject r = new JSONObject();
        String[] item = obj.getString("shortLogMessage").split("\\.");
        int len = item.length;
        String[] path = item[len - 1].split("#");
        String className = path[0] + "." + path[1].split("\\[")[0];
        return className;
    }
}
