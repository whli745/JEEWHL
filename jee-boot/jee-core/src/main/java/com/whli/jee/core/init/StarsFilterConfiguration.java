package com.whli.jee.core.init;

import com.whli.jee.core.web.filter.CorsFilter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author whli
 * @version 1.0
 * @desc SpringBoot配置类
 * @date 2018/5/19  16:45
 */
@Configuration//标明这个是SpringBoot的配置类，可以通过类配置代替原有的XML配置方式
@EnableAutoConfiguration //启动bean自动注入
public class StarsFilterConfiguration extends SpringBootServletInitializer {
    /*FilterRegistrationBean 用来配置urlpattern 来确定哪些路径触发filter */
    @Bean
    public FilterRegistrationBean someFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(authFilter());
        registration.addUrlPatterns("/*");
        registration.setOrder(1);
        return registration;
    }

    /*使用annotation tag来取代<bean></bean>*/
    @Bean
    public CorsFilter authFilter() {
        return new CorsFilter();
    }
}