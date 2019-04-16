package com.whli.jee.system.controller;

import com.whli.jee.core.page.Page;
import com.whli.jee.core.util.BeanUtils;
import com.whli.jee.core.util.WebUtils;
import com.whli.jee.core.web.controller.BaseController;
import com.whli.jee.core.web.entity.BaseTree;
import com.whli.jee.core.web.entity.ResponseBean;
import com.whli.jee.system.entity.SysMenu;
import com.whli.jee.system.service.ISysMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    /**
     * 分页查询
     *
     * @param entity
     * @return
     */
    @PostMapping(value = "/findByPage")
    @ApiOperation("分页查询系统菜单")
    @Override
    public ResponseBean findByPage(@RequestBody SysMenu entity, HttpServletRequest req) throws Exception {
        ResponseBean responseBean = new ResponseBean();
        if (BeanUtils.isNotNull(entity)){
            if (StringUtils.isBlank(entity.getParentId())){
                entity.setParentId("");
            }
            Page<SysMenu> page = new Page<SysMenu>(entity.getCurrentPage(),entity.getPageSize());
            page = sysMenuService.findByPage(entity, page);

            responseBean.setSucceed(true);
            responseBean.setCount(page.getTotal());
            responseBean.setResults(page.getPageRecords());
        }
        return responseBean;
    }

    /**
     * 添加entity
     * @param entity
     * @return
     */
    @PostMapping(value = "/add")
    @ApiOperation("增加菜单")
    @Override
    public ResponseBean add(@RequestBody SysMenu entity, HttpServletRequest req) throws Exception {
        ResponseBean responseBean = new ResponseBean();
        entity.setEnable(1);
        int rows = sysMenuService.add(entity);
        if (rows > 0) {
            responseBean.setSucceed(true);
            responseBean.setMessage( "新增系统资源成功！");
        }
        return responseBean;
    }

    /**
     * 更新entity
     * @param entity
     * @return
     */
    @PostMapping(value = "/update")
    @ApiOperation("修改菜单")
    @Override
    public ResponseBean update(@RequestBody SysMenu entity, HttpServletRequest req) throws Exception {
        ResponseBean responseBean = new ResponseBean();
        int rows = sysMenuService.update(entity);
        if (rows > 0) {
            responseBean.setSucceed(true);
            responseBean.setMessage("修改系统资源成功！");
        }
        return responseBean;
    }

    /**
     * 删除entity
     * @param entity
     * @return
     */
    @PostMapping(value = "/delete")
    @ApiOperation("删除菜单")
    @Override
    public ResponseBean delete(@RequestBody SysMenu entity, HttpServletRequest req) throws Exception {
        ResponseBean responseBean = new ResponseBean();
        sysMenuService.deleteMore(entity);
        responseBean.setSucceed(true);
        responseBean.setMessage( "删除系统资源成功！");
        return responseBean;
    }

    /**
     * 根据主键查询entity
     *
     * @param entity
     * @return
     */
    @PostMapping(value = "/findByPK")
    @ApiOperation("根据ID查询菜单")
    @Override
    public SysMenu findByPK(@RequestBody SysMenu entity, HttpServletRequest req) throws Exception {
        return sysMenuService.findByPK(entity.getId());
    }

    /**
     * 根据主键查询entity
     *
     * @param entity
     * @return
     */
    @PostMapping(value = "/findByPKs")
    @ApiOperation("根据ID查询菜单")
    @Override
    public List<SysMenu> findByPKs(SysMenu entity, HttpServletRequest req) throws Exception {
        return sysMenuService.findByPKs(entity.getIds());
    }

    /**
     * 根据编码查询
     * @param entity
     * @param req
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/findByNo")
    @ApiIgnore
    @Override
    public SysMenu findByNo(@RequestBody SysMenu entity, HttpServletRequest req) throws Exception {
        return null;
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
    @Override
    public SysMenu findByName(@RequestBody SysMenu entity, HttpServletRequest req) throws Exception {
        return sysMenuService.findByName(entity.getName());
    }

    /**
     * 查询所有数据
     *
     * @param entity
     * @return
     */
    @PostMapping(value = "/findAll")
    @ApiOperation("查询所有系统菜单")
    @Override
    public List<SysMenu> findAll(@RequestBody SysMenu entity, HttpServletRequest req) throws Exception {
        return sysMenuService.findAll(entity);
    }

    @ApiIgnore
    @Override
    public void exportExcel(SysMenu entity, HttpServletResponse response) throws Exception {

    }

    @ApiIgnore
    @Override
    public ResponseBean importExcel(@RequestParam(value = "uploadFile", required = false) MultipartFile file) throws Exception {
        return null;
    }

    @ApiIgnore
    @Override
    public void exportTemplate(SysMenu entity, HttpServletResponse response) throws Exception {

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

