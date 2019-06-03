package com.whli.jee.system.service;

import com.whli.jee.core.web.service.IBaseService;
import com.whli.jee.system.entity.SysUser;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 *
 * @author whli
 * @version 1.0
 * @since 1.0
 * */
public interface ISysUserService extends IBaseService<SysUser> {

    /**
     * @Desc 用户登录
     * @Author whli
     * @Version 1.0
     * @Date 2018/6/3 0:19
     * @Params [loginName, password]
     * @Return java.lang.String
     */
    SysUser login(String loginName, String password);

    /**
     * @Desc 用户退出登录
     * @Author whli
     * @Version 1.0
     * @Date 2018/6/3 0:20
     * @Params [token]
     * @Return java.lang.String
     */
    boolean logout(HttpServletRequest request);

    /**
     * 由登录用户名查询用户
     * @param loginName
     * @return
     */
    SysUser getByLoginNameOrEmailOrPhone(String loginName);


    /**
     * 由邮箱查询用户
     * @param email
     * @return
     */
    SysUser getByEmail(String email);

    /**
     * 由邮箱查询用户
     * @param phone
     * @return
     */
    SysUser getByPhone(String phone);

    /**
     * @Desc 授权用户角色权限
     * @Author whli
     * @Version 1.0
     * @Date 2018/6/3 0:13
     * @Params [userId, roleIds]
     * @Return void
     */
    @Transactional
    int grantRolesByUser(String userId, List<String> roleIds);

    /**
     * 重置密码
     * @param entity
     * @return
     */
    @Transactional
    int resetPassword(SysUser entity);
}
