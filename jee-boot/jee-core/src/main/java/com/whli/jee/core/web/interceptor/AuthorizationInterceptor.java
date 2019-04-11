package com.whli.jee.core.web.interceptor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.whli.jee.core.anotation.AuthorPermit;
import com.whli.jee.core.constant.SysConstants;
import com.whli.jee.core.util.WebUtils;
import com.whli.jee.core.web.entity.ResponseBean;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Desc 自定义Spring MVC登录验证拦截器
 * @Author whli
 * @Version 1.0
 * @Date 2018/5/29 16:12
 */
public class AuthorizationInterceptor extends HandlerInterceptorAdapter{
    /*
     * @Desc 进行登录检查
     * @Author whli
     * @Version 1.0
     * @Date 2018/5/29 16:14
     * @Params [request, response, handler]
     * @Return boolean
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;

        //验证是否swagger API
        if ("swaggerResources".equals(handlerMethod.getMethod().getName()) || "getDocumentation".equals(handlerMethod.getMethod().getName())) {
            return true;
        }

        //无须验证直接登录
        AuthorPermit permit = handlerMethod.getMethodAnnotation(AuthorPermit.class);
        if(permit != null){
            return true;
        }

        //获取前台传递的token
        String token = request.getHeader(SysConstants.AUTHORIZATION);

        if (StringUtils.isNotBlank(token)){
            getReturnJson(response);
            return false;
        }

        if (!WebUtils.isExistsToken(token)){
            getReturnJson(response);
            return false;
        }

        String loginUserId = WebUtils.getLoginUserId();
        if (StringUtils.isNotBlank(loginUserId)){
            getReturnJson(response);
            return false;
        }

        return true;
    }

    private void getReturnJson(HttpServletResponse response) throws JsonProcessingException {
        ResponseBean responseBean = new ResponseBean();
        responseBean.setCode(SysConstants.TOKEN_FAIL_CODE);
        responseBean.setMessage(SysConstants.TOKEN_FAIL_MESSAGE);
        WebUtils.returnJson(response, new ObjectMapper().writeValueAsString(responseBean));
    }
}
