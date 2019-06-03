package com.whli.jee.system.dao;

import com.whli.jee.core.web.dao.IBaseDao;
import com.whli.jee.system.entity.SysUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author whli
 * @version 1.0
 * @since 1.0
 * */
@Repository
public interface ISysUserDao extends IBaseDao<SysUser> {

    /**
     * 由登录用户名查询用户
     * @param loginName
     * @return
     */
    SysUser getByLoginNameOrEmailOrPhone(@Param("loginName") String loginName);

    /**
     * 由邮箱查询用户
     * @param email
     * @return
     */
    SysUser getByEmail(@Param("email") String email);

    /**
     * 由邮箱查询用户
     * @param phone
     * @return
     */
    SysUser getByPhone(@Param("phone") String phone);

    /**
     * 重置密码
     * @param id
     * @param password
     * @return
     */
    int resetPassword(@Param("id") String id, @Param("password") String password);
}
