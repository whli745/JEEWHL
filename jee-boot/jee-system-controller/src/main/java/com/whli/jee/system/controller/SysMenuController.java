package com.whli.jee.system.controller;

import com.whli.jee.core.util.WebUtils;
import com.whli.jee.core.web.controller.BaseController;
import com.whli.jee.core.web.entity.BaseTree;
import com.whli.jee.core.web.service.IBaseService;
import com.whli.jee.system.entity.SysMenu;
import com.whli.jee.system.service.ISysMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 系统资源
 * @author whli
 * @version 1.0
 * @since 1.0
 */
@RestController
@RequestMapping(value = "/system/sysMenu")
@Api(description = "系统菜单API")
public class SysMenuController extends BaseController<SysMenu> {

    @Autowired
    private ISysMenuService sysMenuService;

    @Override
    public IBaseService<SysMenu> getService() {
        return sysMenuService;
    }

    /**
     * 根据名称查询
     * @param entity
     * @param req
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/findByName")
    @ApiOperation("根据系统菜单名称查询")
    public SysMenu findByName(@RequestBody SysMenu entity, HttpServletRequest req) throws Exception {
        return sysMenuService.findByName(entity.getName());
    }


    /**
     * 根据父ID查询菜单
     *
     * @return
     */
    @PostMapping(value = "/findByTree")
    @ApiOperation("根据角色ID树形展示菜单")
    public List<BaseTree> findByTree(@RequestBody SysMenu entity, HttpServletRequest req) throws Exception {
        return sysMenuService.findByTree(entity.getRoleId());
    }

    /**
     * 根据登录用户及其角色查询菜单
     *
     * @param entity
     * @param rq
     * @return
     */
    @PostMapping(value = "/menuTree")
    @ApiOperation("查询登录用户的授权菜单")
    public List<SysMenu> menuTree(@RequestBody SysMenu entity, HttpServletRequest rq) throws Exception {
        //获取父菜单
        List<SysMenu> sysMenus = sysMenuService.findMenusByUserId(WebUtils.getLoginUserId(), entity.getParentId());
        return sysMenus;
    }

    /**
     * 根据登录用户及其角色查询菜单
     *
     * @param entity
     * @param rq
     * @return
     */
    @PostMapping(value = "/getButtons")
    @ApiOperation("查询菜单拥有的按钮")
    public List<SysMenu> getButtons(@RequestBody SysMenu entity, HttpServletRequest rq) throws Exception {
        //获取父菜单
        List<SysMenu> buttons = sysMenuService.findButtonsByParentUrlAndUserId(WebUtils.getLoginUserId(), entity.getHref());
        return buttons;
    }

    /**
     * 查询同级别下是否存在同一序号
     * @param entity
     * @return
     */
    @PostMapping(value = "/findByParentIdAndSort")
    @ApiOperation("查询同级别下是否存在相同序号")
    public SysMenu findByParentIdAndSort(@RequestBody SysMenu entity){
        return sysMenuService.findByParentIdAndSort(entity);
    }
}

