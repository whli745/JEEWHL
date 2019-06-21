package com.whli.jee.system.controller;

import com.whli.jee.core.web.controller.BaseController;
import com.whli.jee.core.web.entity.ResponseBean;
import com.whli.jee.core.web.service.IBaseService;
import com.whli.jee.system.entity.SysUser;
import com.whli.jee.system.entity.SysUserRole;
import com.whli.jee.system.service.ISysUserRoleService;
import com.whli.jee.system.service.ISysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>用户管理</p>
 * @author whli
 * @version 1.0
 */
@RestController
@RequestMapping(value = "/system/sysUser")
@Api(description = "系统用户API")
public class SysUserController extends BaseController<SysUser> {

    @Autowired
    private ISysUserService sysUserService;
    @Autowired
    private ISysUserRoleService sysUserRoleService;


    @Override
    public IBaseService<SysUser> getService() {
        return sysUserService;
    }

    /**
     * 根据编码查询entity
     *
     * @return
     */
    @PostMapping(value = "/getByNo")
    @ApiOperation("根据用户登录名查询")
    public SysUser getByNo(@RequestBody SysUser entity, HttpServletRequest req) throws Exception {
        return sysUserService.getByNo(entity.getLoginName());
    }

    /**
     * 由邮箱查询用户
     * @param entity
     * @return
     */
    @PostMapping(value = "/getByEmail")
    @ApiOperation("根据用户邮箱查询")
    public SysUser getByEmail(@RequestBody SysUser entity, HttpServletRequest req) throws Exception {
        return sysUserService.getByEmail(entity.getEmail());
    };

    /**
     * 由手机号查询用户
     * @param entity
     * @return
     */
    @PostMapping(value = "/getByPhone")
    @ApiOperation("根据用户联系方式查询")
    public SysUser getByPhone(@RequestBody SysUser entity, HttpServletRequest req) throws Exception {
        return sysUserService.getByPhone(entity.getPhone());
    };

    /**
     * 根据名称查询entity
     *
     * @return
     */
    @PostMapping(value = "/getByName")
    @ApiOperation("根据用户姓名查询")
    public SysUser getByName(@RequestBody SysUser entity, HttpServletRequest req) throws Exception {
        return sysUserService.getByName(entity.getName());
    }

    /**
     * 根据名称查询entity
     *
     * @return
     */
    @PostMapping(value = "/getByLoginNameOrEmailOrPhone")
    @ApiOperation("根据用户名或邮箱或联系方式查询")
    public SysUser getByLoginNameOrEmailOrPhone(@RequestBody SysUser entity, HttpServletRequest req) throws Exception {
        return sysUserService.getByLoginNameOrEmailOrPhone(entity.getLoginName());
    }


    /**
     * 授权用户角色
     *
     * @param entity
     * @return
     */
    @PostMapping(value = "/grantRolesByUser")
    @ApiOperation("给用户授权角色")
    public ResponseBean grantRolesByUser(@RequestBody SysUser entity, HttpServletRequest req) throws Exception {
        ResponseBean responseBean = new ResponseBean();
        int rows = sysUserService.grantRolesByUser(entity.getId(),entity.getRoleIds());
        if (rows > 0){
            responseBean.setCode("0");
            responseBean.setMessage("授权用户成功！");
        }
        return responseBean;
    }

    /**
     * 根据用户删除对应角色关系
     * @param entity
     * @param req
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/deleteRolesByUser")
    @ApiOperation("删除用户的角色")
    public ResponseBean deleteRolesByUser(@RequestBody SysUserRole entity, HttpServletRequest req) throws Exception {
        ResponseBean responseBean = new ResponseBean();
        sysUserRoleService.deleteRolesByUser(entity);
        responseBean.setCode("0");
        responseBean.setMessage("删除角色成功！");
        return responseBean;
    }

    /**
     * 更新entity
     *
     * @return
     */
    @PostMapping(value = "/resetPassword")
    @ApiOperation("重置用户密码")
    public ResponseBean resetPassword(@RequestBody SysUser entity, HttpServletRequest req) throws Exception {
        ResponseBean responseBean = new ResponseBean();
        int rows = sysUserService.resetPassword(entity);
        if (rows > 0) {
            responseBean.setCode("0");
            responseBean.setMessage("重置密码成功！");
        }
        return responseBean;
    }
}

