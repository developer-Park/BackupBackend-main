package ca.sait.backup.config;

import ca.sait.backup.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Interceptor configuration
 *
 * Access without permission url    /api/v1/pub/
 * need login to visit url    /api/v1/pri/
 */

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {


    @Bean
    LoginInterceptor loginInterceptor(){
        return new LoginInterceptor();
    }






    @Override
    public void addInterceptors(InterceptorRegistry registry) {


        /**
         * 拦截全部路径，这个跨域需要放在最上面
         * Intercept all paths, this cross domain needs to be placed at the top
         */


        registry.addInterceptor(loginInterceptor()).addPathPatterns("/api/v1/pri/*/*/**")
                //不拦截哪些路径   斜杠一定要加
                .excludePathPatterns("/api/v1/pri/user/login","/api/v1/pri/user/register");


        WebMvcConfigurer.super.addInterceptors(registry);

    }
}
