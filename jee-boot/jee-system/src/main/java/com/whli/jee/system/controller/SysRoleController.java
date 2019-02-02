package com.whli.jee.system.controller;

import com.whli.jee.core.page.Page;
import com.whli.jee.core.util.CollectionUtils;
import com.whli.jee.core.web.controller.BaseController;
import com.whli.jee.core.web.entity.ResponseBean;
import com.whli.jee.system.entity.SysRole;
import com.whli.jee.system.service.ISysRoleMenuService;
import com.whli.jee.system.service.ISysRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    /**
     * 分页查询
     *
     * @param entity
     * @return
     */
    @PostMapping(value = "/findByPage")
    @ApiOperation("分页查询系统角色")
    @Override
    public ResponseBean findByPage(@RequestBody SysRole entity, HttpServletRequest req) throws Exception {
        ResponseBean responseBean = new ResponseBean();
        if (entity != null) {
            Page<SysRole> page = new Page<SysRole>(entity.getCurrentPage(),entity.getPageSize());
            page = sysRoleService.findByPage(entity, page);
            responseBean.setCount(page.getTotal());
            responseBean.setResults(page.getPageRecords());
        }
        return responseBean;
    }

    /**
     * 添加entity
     *
     * @return
     */
    @PostMapping(value = "/add")
    @ApiOperation("增加系统角色")
    @Override
    public ResponseBean add(@RequestBody SysRole entity, HttpServletRequest req) throws Exception {
        ResponseBean responseBean = new ResponseBean();
        entity.setEnable(1);
        int rows = sysRoleService.add(entity);
        if (rows > 0) {
            responseBean.setResults(true);
            responseBean.setMessage("新增角色成功！");
        }
        return responseBean;
    }

    /**
     * 更新entity
     *
     * @param req
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/update")
    @ApiOperation("修改系统角色")
    @Override
    public ResponseBean update(@RequestBody SysRole entity, HttpServletRequest req) throws Exception {
        ResponseBean responseBean = new ResponseBean();
        int rows = sysRoleService.update(entity);
        if (rows > 0) {
            responseBean.setResults(true);
            responseBean.setMessage("修改角色成功！");
        }
        return responseBean;
    }

    /**
     * 删除entity
     *
     * @return
     */
    @PostMapping(value = "/delete")
    @ApiOperation("删除系统角色")
    @Override
    public ResponseBean delete(@RequestBody SysRole entity, HttpServletRequest req) throws Exception {
        ResponseBean responseBean = new ResponseBean();
        sysRoleService.deleteMore(entity);
        responseBean.setResults(true);
        responseBean.setMessage("删除角色成功！");
        return responseBean;
    }

    /**
     * 根据主键查询entity
     * @return
     */
    @PostMapping(value = "/findByPK")
    @ApiOperation("根据ID查询系统角色")
    @Override
    public SysRole findByPK(@RequestBody SysRole entity, HttpServletRequest req) throws Exception {
        return sysRoleService.findByPK(entity.getId());
    }

    /**
     * 根据主键查询entity
     * @return
     */
    @PostMapping(value = "/findByPKs")
    @ApiOperation("根据ID查询系统角色")
    @Override
    public List<SysRole> findByPKs(SysRole entity, HttpServletRequest req) throws Exception {
        return sysRoleService.findByPKs(entity.getIds());
    }

    /**
     * 根据编码查询entity
     * @return
     */
    @PostMapping(value = "/findByNo")
    @ApiOperation("根据角色编码查询")
    @Override
    public SysRole findByNo(@RequestBody SysRole entity, HttpServletRequest req) throws Exception {
        return sysRoleService.findByNo(entity.getNo());
    }

    /**
     * 根据名称查询entity
     * @return
     */
    @PostMapping(value = "/findByName")
    @ApiOperation("根据角色名称查询")
    @Override
    public SysRole findByName(@RequestBody SysRole entity, HttpServletRequest req) throws Exception {
        return sysRoleService.findByName(entity.getName());
    }

    /**
     * 查询所有数据
     *
     * @return
     */
    @PostMapping(value = "/findAll")
    @ApiOperation("查询所有系统角色")
    @Override
    public List<SysRole> findAll(@RequestBody SysRole entity, HttpServletRequest req) throws Exception {
        return sysRoleService.findAll(entity);
    }

    @ApiIgnore
    @Override
    public void exportExcel(SysRole entity, HttpServletResponse response) throws Exception {

    }

    @ApiIgnore
    @Override
    public ResponseBean importExcel(@RequestParam(value = "uploadFile", required = false) MultipartFile file) throws Exception {
        return null;
    }

    @ApiIgnore
    @Override
    public void exportTemplate(SysRole entity, HttpServletResponse response) throws Exception {

    }

    /**
     * 授权角色菜单
     * @return
     */
    @PostMapping(value = "/grantByRole")
    @ApiOperation("给角色授权菜单")
    public ResponseBean grantByRole(@RequestBody SysRole entity, HttpServletRequest req) throws Exception {
        ResponseBean responseBean = new ResponseBean();
        int rows = sysRoleMenuService.grantByRole(entity.getId(),entity.getMenuIds());
        if (rows > 0){
            responseBean.setResults(true);
            responseBean.setMessage("授权菜单成功！");
        }
        return responseBean;
    }

    @PostMapping(value = "/findByUser")
    @ApiOperation("查询登录用户拥有的角色")
    public ResponseBean findRolesByUserId(@RequestBody SysRole entity, HttpServletRequest req) throws Exception{
        ResponseBean responseBean = new ResponseBean();
        List<SysRole> roles = sysRoleService.findRolesByUserId(entity.getUserId());
        if (CollectionUtils.isNotNullOrEmpty(roles)){
            responseBean.setResults(roles);
        }
        return responseBean;
    }
}

