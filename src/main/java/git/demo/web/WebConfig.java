package git.demo.web;

import git.demo.web.interceptor.FileInterceptor;
import git.demo.web.interceptor.UrlInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {


    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(new UrlInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/","/member/join","/member/login","/logout","/css/**",
                        "/error", "/file/upload" , "/error-*", "/api/members/**", "/book/**");


        registry.addInterceptor(new FileInterceptor())
                .order(2)
                .addPathPatterns("/file/upload" , "/book/**");

    }
}
