package git.demo.web;

import git.demo.web.interceptor.FileInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new FileInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/","/member/join","/member/login","/logout","/css/**", "/error");
    }


}
