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
    @PostMapping(value = "/getByName")
    @ApiOperation("根据系统菜单名称查询")
    public SysMenu getByName(@RequestBody SysMenu entity, HttpServletRequest req) throws Exception {
        return sysMenuService.getByName(entity.getName());
    }


    /**
     * 根据父ID查询菜单
     *
     * @return
     */
    @PostMapping(value = "/listMenuTreesByRoleId")
    @ApiOperation("根据角色ID树形展示菜单")
    public List<BaseTree> listMenuTreesByRoleId(@RequestBody SysMenu entity, HttpServletRequest req) throws Exception {
        return sysMenuService.listMenuTreesByRoleId(entity.getRoleId());
    }

    /**
     * 根据登录用户及其角色查询菜单
     *
     * @param entity
     * @param rq
     * @return
     */
    @PostMapping(value = "/listMenusByUserIdAndParentId")
    @ApiOperation("查询登录用户的授权菜单")
    public List<SysMenu> listMenusByUserIdAndParentId(@RequestBody SysMenu entity, HttpServletRequest rq) throws Exception {
        //获取父菜单
        List<SysMenu> sysMenus = sysMenuService.listMenusByUserIdAndParentId(WebUtils.getLoginUserId(), entity.getParentId());
        return sysMenus;
    }

    /**
     * 根据登录用户及其角色查询菜单
     *
     * @param entity
     * @param rq
     * @return
     */
    @PostMapping(value = "/listButtonsByUserIdAndParentUrl")
    @ApiOperation("查询菜单拥有的按钮")
    public List<SysMenu> listButtonsByUserIdAndParentUrl(@RequestBody SysMenu entity, HttpServletRequest rq) throws Exception {
        //获取父菜单
        List<SysMenu> buttons = sysMenuService.listButtonsByUserIdAndParentUrl(WebUtils.getLoginUserId(), entity.getHref());
        return buttons;
    }

    /**
     * 查询同级别下是否存在同一序号
     * @param entity
     * @return
     */
    @PostMapping(value = "/getByParentIdAndSort")
    @ApiOperation("查询同级别下是否存在相同序号")
    public SysMenu getByParentIdAndSort(@RequestBody SysMenu entity){
        return sysMenuService.getByParentIdAndSort(entity);
    }
}

