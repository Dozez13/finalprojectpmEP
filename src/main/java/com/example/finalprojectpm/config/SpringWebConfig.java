package com.example.finalprojectpm.config;

import com.example.finalprojectpm.web.interceptor.CharacterEncodingInterceptor;
import com.example.finalprojectpm.web.interceptor.SessionAdminInterceptor;
import com.example.finalprojectpm.web.interceptor.SessionUserInterceptor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@EnableWebMvc //<mvc:annotation-driven />
@Configuration
@ComponentScan({ "com.example.finalprojectpm" })
public class SpringWebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new CharacterEncodingInterceptor()).addPathPatterns("/*");
        registry.addInterceptor(new SessionUserInterceptor()).addPathPatterns("/user/*","/admin/*");
        registry.addInterceptor(new SessionAdminInterceptor()).addPathPatterns("/admin/*");
    }
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.jsp("/WEB-INF/views/", ".jsp");
    }


}