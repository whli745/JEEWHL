package com.whli.jee.system.controller;

import com.whli.jee.core.anotation.AuthorPermit;
import com.whli.jee.core.web.entity.ResponseBean;
import com.whli.jee.system.entity.SysUser;
import com.whli.jee.system.service.ISysUserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>类或方法描述</p>
 *
 * @author whli
 * @date 2019/1/14 15:09
 */
@RestController
public class LoginController {
    @Autowired
    private ISysUserService sysUserService;

    /**
     * @Desc 用户登录
     * @Author whli
     * @Version 1.0
     * @Date 2018/6/3 10:19
     * @Params [username, password]
     * @Return com.whli.jee.core.web.entity.ResponseBean
     */
    @AuthorPermit
    @PostMapping(value = "/login")
    @ApiOperation("用户登录")
    public ResponseBean login(String username, String password) throws Exception{
        ResponseBean responseBean = new ResponseBean();
        SysUser loginUser = sysUserService.login(username,password);
        responseBean.setResults(loginUser);
        return responseBean;
    }

    /**
     * @Desc 用户退出登录
     * @Author whli
     * @Version 1.0
     * @Date 2018/6/3 10:19
     * @Params
     * @Return
     */
    @AuthorPermit
    @PostMapping(value = "/logout")
    @ApiOperation("用户退出")
    public ResponseBean logout(HttpServletRequest request) throws Exception{
        ResponseBean responseBean = new ResponseBean();
        boolean flag = sysUserService.logout(request);
        responseBean.setResults(flag);
        return responseBean;
    }
}
