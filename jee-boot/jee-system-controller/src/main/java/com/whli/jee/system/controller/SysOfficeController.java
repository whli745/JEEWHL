package com.whli.jee.system.controller;

import com.whli.jee.core.page.Page;
import com.whli.jee.core.web.controller.BaseController;
import com.whli.jee.core.web.entity.ResponseBean;
import com.whli.jee.system.entity.SysOffice;
import com.whli.jee.system.service.ISysOfficeService;
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
@RequestMapping(value = "/system/sysOffice")
@Api(description = "组织架构API")
public class SysOfficeController extends BaseController<SysOffice> {

    @Autowired
    private ISysOfficeService sysOfficeService;

    /**
     * 分页查询
     * @param entity
     * @return
     */
    @PostMapping(value = "/findByPage")
    @ApiOperation("分页查询组织架构")
    @Override
    public ResponseBean findByPage(@RequestBody SysOffice entity,  HttpServletRequest req) throws Exception {
        ResponseBean responseBean = new ResponseBean();
        if (entity != null) {
            Page<SysOffice> page = new Page<SysOffice>(entity.getCurrentPage(),entity.getPageSize());
            page = sysOfficeService.findByPage(entity, page);

            responseBean.setSucceed(true);
            responseBean.setCount(page.getTotal());
            responseBean.setResults(page.getPageRecords());
        }
        return responseBean;
    }

    /**
     * 添加entity
     *
     * @param entity
     * @return
     */
    @PostMapping(value = "/add")
    @ApiOperation("增加组织架构")
    @Override
    public ResponseBean add(@RequestBody SysOffice entity, HttpServletRequest req) throws Exception {
        ResponseBean responseBean = new ResponseBean();
        entity.setEnable(1);
        int rows = sysOfficeService.add(entity);
        if (rows > 0) {
            responseBean.setSucceed(true);
            responseBean.setMessage("新增部门成功");
        }
        return responseBean;
    }

    /**
     * 更新entity
     * @param entity
     * @return
     */
    @PostMapping(value = "/update")
    @ApiOperation("修改组织架构")
    @Override
    public ResponseBean update(@RequestBody SysOffice entity, HttpServletRequest req) throws Exception {
        ResponseBean responseBean = new ResponseBean();
        int rows = sysOfficeService.update(entity);
        if (rows > 0) {
            responseBean.setSucceed(true);
            responseBean.setMessage("修改部门成功！");
        }
        return responseBean;
    }

    /**
     * 删除entity
     * @param entity
     * @return
     */
    @PostMapping(value = "/delete")
    @ApiOperation("删除组织架构")
    @Override
    public ResponseBean delete(@RequestBody SysOffice entity, HttpServletRequest req) throws Exception {
        ResponseBean responseBean = new ResponseBean();
        sysOfficeService.deleteMore(entity);
        responseBean.setSucceed(true);
        responseBean.setMessage("删除部门成功！");
        return responseBean;
    }

    /**
     * 根据主键查询entity
     *
     * @param entity
     * @return
     */
    @PostMapping(value = "/findByPK")
    @ApiOperation("根据ID查询")
    @Override
    public SysOffice findByPK(@RequestBody SysOffice entity, HttpServletRequest req) throws Exception {
        return sysOfficeService.findByPK(entity.getId());
    }

    /**
     * 根据主键查询entity
     *
     * @param entity
     * @return
     */
    @PostMapping(value = "/findByPKs")
    @ApiOperation("根据ID查询")
    @Override
    public List<SysOffice> findByPKs(SysOffice entity, HttpServletRequest req) throws Exception {
        return sysOfficeService.findByPKs(entity.getIds());
    }

    /**
     * 根据父ID查询
     * @param entity
     * @param req
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/findByNo")
    @ApiIgnore
    @Override
    public SysOffice findByNo(@RequestBody SysOffice entity, HttpServletRequest req) throws Exception {
        return null;
    }

    /**
     * 根据名称进行查询
     * @param entity
     * @param req
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/findByName")
    @ApiOperation("根据组织架构名称查询")
    @Override
    public SysOffice findByName(@RequestBody SysOffice entity, HttpServletRequest req) throws Exception {
        return sysOfficeService.findByName(entity.getName());
    }

    /**
     * 查询所有数据
     *
     * @return
     */
    @PostMapping(value = "/findAll")
    @ApiOperation("查询所有组织架构")
    @Override
    public List<SysOffice> findAll(@RequestBody SysOffice entity, HttpServletRequest req) throws Exception {
        return sysOfficeService.findAll(entity);
    }

    @ApiIgnore
    @Override
    public void exportExcel(SysOffice entity, HttpServletResponse response) throws Exception {

    }

    @ApiIgnore
    @Override
    public ResponseBean importExcel(@RequestParam(value = "uploadFile", required = false) MultipartFile file) throws Exception {
        return null;
    }

    @ApiIgnore
    @Override
    public void exportTemplate(SysOffice entity, HttpServletResponse response) throws Exception {

    }

    /**
     * 查询同级别下是否存在同一序号
     * @param entity
     * @return
     */
    @PostMapping(value = "/findByParentIdAndSort")
    @ApiOperation("查询同级别下是否存在相同序号")
    public SysOffice findByParentIdAndSort(@RequestBody SysOffice entity){
        return sysOfficeService.findByParentIdAndSort(entity);
    }
}

