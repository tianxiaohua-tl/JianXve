package edu.uzz.springboot.configer;

import edu.uzz.springboot.filter.MyInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyMvcConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
//过滤器
// 第一个参数是要访问的url，第二个是目的模板文件路径。
// 这是路由跳转的功能，仅仅是解析模板，并不执行任何的计算
        registry.addViewController("/login").setViewName("/login");
        registry.addViewController("/admin/user/user/login.html").setViewName("/login");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
// 一个*：只匹配字符，不匹配路径（/）
// 两个**：匹配字符，和路径（/）
        registry.addInterceptor(new MyInterceptor()).addPathPatterns("/**");
        registry.addInterceptor(new MyInterceptor()).addPathPatterns("/admin/**");
    }
}