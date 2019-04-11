package com.whli.jee.core.web.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.NamedThreadLocal;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;

/**
 * <p>类或方法描述</p>
 *
 * @author whli
 * @date 2019/2/12 14:56
 */
public class LogInterceptor implements HandlerInterceptor {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private static final ThreadLocal<Long> START_TIME_THREADLOCAL = new NamedThreadLocal("ThreadLocal StartTime");

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        long beginTime = System.currentTimeMillis();
        START_TIME_THREADLOCAL.set(Long.valueOf(beginTime));
        logger.debug("开始记时: {}  URI: {}",
                new SimpleDateFormat("hh:mm:ss.SSS").format(Long.valueOf(beginTime)),
                request.getRequestURI());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object o, Exception e) throws Exception {
        StringBuilder builder = new StringBuilder();
        String type = e == null ? "Access" : "Exception";
        builder.append(type).append(",ip: ").append(request.getRemoteAddr())
                .append(",user-agent: ")
                .append(request.getHeader("user-agent"))
                .append(",request-uri: ")
                .append(request.getRequestURI())
                .append(request
                        .getParameterMap());

        logger.info(builder.toString());

        long beginTime = ((Long)START_TIME_THREADLOCAL.get()).longValue();
        long endTime = System.currentTimeMillis();
        Runtime runtime = Runtime.getRuntime();

        logger.debug("计时结束：{}  耗时：{} ms,  URI: {}  总内存: {}m  已用内存: {}m",
                new Object[] { new SimpleDateFormat("hh:mm:ss.SSS").format(Long.valueOf(endTime)),
                Long.valueOf(endTime - beginTime),
                request.getRequestURI(),
                Long.valueOf(runtime.totalMemory() / 1024L / 1024L),
                Long.valueOf((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024L / 1024L) });

        START_TIME_THREADLOCAL.remove();
    }
}
