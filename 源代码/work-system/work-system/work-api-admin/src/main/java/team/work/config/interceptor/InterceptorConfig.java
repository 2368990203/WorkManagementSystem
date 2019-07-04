package team.work.config.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by htc on 2017/9/9.
 * 拦截器
 */
@Configuration
public class InterceptorConfig extends WebMvcConfigurerAdapter {
    @Bean
    public TokenInterceptor tokenInterceptor() {
        return new TokenInterceptor();
    }

    @Bean
    public AuthInterceptor authInterceptor() {
        return new AuthInterceptor();
    }

    @Bean
    public AppInterceptor appInterceptor() {
        return new AppInterceptor();
    }

    @Bean
    public UserInterceptor userInterceptor() {
        return new UserInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tokenInterceptor()).addPathPatterns("/**/base/**");
        registry.addInterceptor(authInterceptor()).addPathPatterns("/**/base/**");

        registry.addInterceptor(tokenInterceptor()).addPathPatterns("/**/sys/**");
        registry.addInterceptor(authInterceptor()).addPathPatterns("/**/sys/**");

        registry.addInterceptor(userInterceptor()).addPathPatterns("/**/tool/**");
        registry.addInterceptor(userInterceptor()).addPathPatterns("/**/other/**");
        registry.addInterceptor(userInterceptor()).addPathPatterns("/**/cos**");
        registry.addInterceptor(userInterceptor()).addPathPatterns("/**/cos/**");
        registry.addInterceptor(userInterceptor()).addPathPatterns("/**/upload/**");

        //统一api管理只需要token认证
        // registry.addInterceptor(tokenInterceptor()).addPathPatterns("/**/common/**");
    }
}
