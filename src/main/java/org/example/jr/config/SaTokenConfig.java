package org.example.jr.config;

/**
 * @author xingxing
 * @date 2024/7/24 15:44:07
 * @apiNote
 */
import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.stp.StpUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SaTokenConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 使用 SaInterceptor 并配置登录验证逻辑
        registry.addInterceptor(new SaInterceptor(handler -> {
                    // 登录认证：只有登录之后才能进入该接口
                    StpUtil.checkLogin();
                }))
                .addPathPatterns("/**") // 拦截的路由
                .excludePathPatterns("/JR/userLogin", "/JR/userRegister","/JR/userlogout","/JR/useRespwd"); // 放行的路由
    }
}
