package com.whli.jee.core.web.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * ajax跨域访问
 * @author whli
 * @version 1.0
 * @desc
 * @date 2018/5/19  17:35
 */
@WebFilter(filterName="CorsFilter",urlPatterns="/*")
public class CorsFilter implements Filter{
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("*********************************过滤器初始化**************************");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
        response.addHeader("Access-Control-Allow-Headers", "x-requested-with,Content-Type,Authorization,Accept, Origin, XRequestedWith, LastModified, token");
        response.addHeader("Access-Control-Max-Age", "3600");//60 min
        System.out.println("*********************************过滤器被使用**************************");
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        System.out.println("*********************************过滤器被销毁**************************");
    }
}
