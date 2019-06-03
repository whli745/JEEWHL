package com.whli.jee.system.controller;

import com.whli.jee.core.web.controller.BaseController;
import com.whli.jee.core.web.entity.ResponseBean;
import com.whli.jee.core.web.service.IBaseService;
import com.whli.jee.system.entity.SysRole;
import com.whli.jee.system.service.ISysRoleMenuService;
import com.whli.jee.system.service.ISysRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author whli
 * @version 1.0
 * @since 1.0
 */
@RestController
@RequestMapping(value = "/system/sysRole")
@Api(description = "系统角色API")
public class SysRoleController extends BaseController<SysRole> {

    @Autowired
    private ISysRoleService sysRoleService;
    @Autowired
    private ISysRoleMenuService sysRoleMenuService;

    @Override
    public IBaseService<SysRole> getService() {
        return sysRoleService;
    }

    /**
     * 根据编码查询entity
     * @return
     */
    @PostMapping(value = "/getByNo")
    @ApiOperation("根据角色编码查询")
    public SysRole getByNo(@RequestBody SysRole entity, HttpServletRequest req) throws Exception {
        return sysRoleService.getByNo(entity.getNo());
    }

    /**
     * 根据名称查询entity
     * @return
     */
    @PostMapping(value = "/getByName")
    @ApiOperation("根据角色名称查询")
    public SysRole getByName(@RequestBody SysRole entity, HttpServletRequest req) throws Exception {
        return sysRoleService.getByName(entity.getName());
    }

    /**
     * 授权角色菜单
     * @return
     */
    @PostMapping(value = "/grantMenusByRole")
    @ApiOperation("给角色授权菜单")
    public ResponseBean grantMenusByRole(@RequestBody SysRole entity, HttpServletRequest req) throws Exception {
        ResponseBean responseBean = new ResponseBean();
        int rows = sysRoleMenuService.grantMenusByRole(entity.getId(),entity.getMenuIds());
        if (rows > 0){
            responseBean.setSucceed(true);
            responseBean.setMessage("授权菜单成功！");
        }
        return responseBean;
    }

    @PostMapping(value = "/listByUserId")
    @ApiOperation("查询登录用户拥有的角色")
    public ResponseBean listByUserId(@RequestBody SysRole entity, HttpServletRequest req) throws Exception{
        ResponseBean responseBean = new ResponseBean();
        List<SysRole> roles = sysRoleService.listByUserId(entity.getUserId());
        if (CollectionUtils.isNotEmpty(roles)){
            responseBean.setSucceed(true);
            responseBean.setResults(roles);
        }
        return responseBean;
    }
}

