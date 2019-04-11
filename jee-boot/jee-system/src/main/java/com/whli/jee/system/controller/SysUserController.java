package com.whli.jee.system.controller;

import com.whli.jee.core.anotation.AuthorPermit;
import com.whli.jee.core.page.Page;
import com.whli.jee.core.web.controller.BaseController;
import com.whli.jee.core.web.entity.ResponseBean;
import com.whli.jee.system.entity.SysUser;
import com.whli.jee.system.entity.SysUserRole;
import com.whli.jee.system.service.ISysUserRoleService;
import com.whli.jee.system.service.ISysUserService;
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

    /**
     * 分页查询
     *
     * @param entity
     * @return
     */
    @PostMapping(value = "/findByPage")
    @ApiOperation("分页查询用户")
    @Override
    public ResponseBean findByPage(@RequestBody SysUser entity, HttpServletRequest req) throws Exception {
        ResponseBean responseBean = new ResponseBean();
        if (entity != null) {
            Page<SysUser> page = new Page<SysUser>(entity.getCurrentPage(),entity.getPageSize());
            page = sysUserService.findByPage(entity,page);

            responseBean.setSucceed(true);
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
    @ApiOperation("新增用户")
    @Override
    public ResponseBean add(@RequestBody SysUser entity, HttpServletRequest req) throws Exception {
        ResponseBean responseBean = new ResponseBean();
        int rows = sysUserService.add(entity);
        if (rows > 0) {
            responseBean.setSucceed(true);
            responseBean.setMessage("新增用户成功！");
        }
        return responseBean;
    }

    /**
     * 更新entity
     *
     * @return
     */
    @PostMapping(value = "/update")
    @ApiOperation("修改用户")
    @Override
    public ResponseBean update(@RequestBody SysUser entity, HttpServletRequest req) throws Exception {
        ResponseBean responseBean = new ResponseBean();
        int rows = sysUserService.update(entity);
        if (rows > 0) {
            responseBean.setSucceed(true);
            responseBean.setMessage("修改用户成功！");
        }
        return responseBean;
    }

    /**
     * 删除entity
     *
     * @return
     */
    @PostMapping(value = "/delete")
    @ApiOperation("删除用户")
    @Override
    public ResponseBean delete(@RequestBody SysUser entity, HttpServletRequest req) throws Exception {
        ResponseBean responseBean = new ResponseBean();
        sysUserService.deleteMore(entity);
        responseBean.setSucceed(true);
        responseBean.setMessage("删除用户成功！");
        return responseBean;
    }

    /**
     * 根据主键查询entity
     *
     * @return
     */
    @PostMapping(value = "/findByPK")
    @ApiOperation("根据用户ID查询")
    @Override
    public SysUser findByPK(@RequestBody SysUser entity, HttpServletRequest req) throws Exception {
        return sysUserService.findByPK(entity.getId());
    }

    /**
     * 根据主键查询entity
     *
     * @return
     */
    @PostMapping(value = "/findByPKs")
    @ApiOperation("根据用户ID查询")
    @Override
    public List<SysUser> findByPKs(SysUser entity, HttpServletRequest req) throws Exception {
        return sysUserService.findByPKs(entity.getIds());
    }

    /**
     * 根据编码查询entity
     *
     * @return
     */
    @PostMapping(value = "/findByNo")
    @ApiOperation("根据用户登录名查询")
    @Override
    public SysUser findByNo(@RequestBody SysUser entity, HttpServletRequest req) throws Exception {
        return sysUserService.findByNo(entity.getLoginName());
    }

    /**
     * 由邮箱查询用户
     * @param entity
     * @return
     */
    @PostMapping(value = "/findByEmail")
    @ApiOperation("根据用户登录名查询")
    public SysUser findByEmail(@RequestBody SysUser entity, HttpServletRequest req) throws Exception {
        return sysUserService.findByEmail(entity.getEmail());
    };

    /**
     * 由手机号查询用户
     * @param entity
     * @return
     */
    @PostMapping(value = "/findByPhone")
    @ApiOperation("根据用户登录名查询")
    public SysUser findByPhone(@RequestBody SysUser entity, HttpServletRequest req) throws Exception {
        return sysUserService.findByPhone(entity.getPhone());
    };

    /**
     * 根据名称查询entity
     *
     * @return
     */
    @PostMapping(value = "/findByName")
    @ApiOperation("根据用户姓名查询")
    @Override
    public SysUser findByName(@RequestBody SysUser entity, HttpServletRequest req) throws Exception {
        return sysUserService.findByName(entity.getName());
    }

    /**
     * 根据名称查询entity
     *
     * @return
     */
    @PostMapping(value = "/findByLoginNameOrEmailOrPhone")
    @ApiOperation("根据用户姓名查询")
    public SysUser findByLoginNameOrEmailOrPhone(@RequestBody SysUser entity, HttpServletRequest req) throws Exception {
        return sysUserService.findByLoginNameOrEmailOrPhone(entity.getLoginName());
    }

    /**
     * 查询所有数据
     *
     * @return
     */
    @PostMapping(value = "/findAll")
    @ApiOperation("查找所有用户")
    @Override
    public List<SysUser> findAll(@RequestBody SysUser entity, HttpServletRequest req) throws Exception {
        return sysUserService.findAll(entity);
    }

    @ApiIgnore
    @Override
    public void exportExcel(SysUser entity, HttpServletResponse response) throws Exception {

    }

    @PostMapping(value = "/importExcel")
    @AuthorPermit
    @ApiIgnore
    @Override
    public ResponseBean importExcel(@RequestParam(value = "uploadFile", required = false) MultipartFile file) throws Exception {
        ResponseBean responseBean = new ResponseBean();
        if (file != null){
            int rows = sysUserService.importExcel(file.getInputStream());
            if (rows > 0) {
                responseBean.setSucceed(true);
                responseBean.setMessage("导入用户成功！");
            }
        }
        return responseBean;
    }

    @GetMapping(value = "/exportTemplate")
    @AuthorPermit
    @ApiIgnore
    @Override
    public void exportTemplate(SysUser entity,HttpServletResponse response) throws Exception{
        sysUserService.exportTemplate(entity,response);
    }

    /**
     * 授权用户角色
     *
     * @param entity
     * @return
     */
    @PostMapping(value = "/grantUser")
    @ApiOperation("给用户授权角色")
    public ResponseBean grantUser(@RequestBody SysUser entity, HttpServletRequest req) throws Exception {
        ResponseBean responseBean = new ResponseBean();
        int rows = sysUserService.grantByUser(entity.getId(),entity.getRoleIds());
        if (rows > 0){
            responseBean.setSucceed(true);
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
    @PostMapping(value = "/deleteRoleByUser")
    @ApiOperation("删除用户的角色")
    public ResponseBean deleteRoleByUser(@RequestBody SysUserRole entity, HttpServletRequest req) throws Exception {
        ResponseBean responseBean = new ResponseBean();
        sysUserRoleService.deleteByUser(entity);
        responseBean.setSucceed(true);
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
            responseBean.setSucceed(true);
            responseBean.setMessage("重置密码成功！");
        }
        return responseBean;
    }
}

