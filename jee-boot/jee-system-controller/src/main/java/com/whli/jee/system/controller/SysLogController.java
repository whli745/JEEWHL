package com.whli.jee.system.controller;

import com.whli.jee.core.page.Page;
import com.whli.jee.core.web.controller.BaseController;
import com.whli.jee.core.web.entity.ResponseBean;
import com.whli.jee.system.entity.SysLog;
import com.whli.jee.system.service.ISysLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
@RequestMapping(value = "/system/sysLog")
@Api(description = "系统日志API")
public class SysLogController extends BaseController<SysLog> {

    @Autowired
    private ISysLogService sysLogService;

    /**
     * 分页查询
     * @param entity
     * @return
     */
    @PostMapping(value = "/findByPage")
    @ApiOperation("分页查询系统日志")
    @Override
    public ResponseBean findByPage(@RequestBody SysLog entity,HttpServletRequest req) throws Exception {
        ResponseBean responseBean = new ResponseBean();
        if (entity != null) {
            Page<SysLog> page = new Page<SysLog>(entity.getCurrentPage(),entity.getPageSize());
            page = sysLogService.findByPage(entity, page);

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
    @ApiIgnore
    @Override
    public ResponseBean add(@RequestBody SysLog entity, HttpServletRequest req) throws Exception {
        ResponseBean responseBean = new ResponseBean();
        int rows = sysLogService.add(entity);
        if (rows > 0) {
            responseBean.setSucceed(true);
            responseBean.setMessage("新增操作日志成功！");
        }
        return responseBean;
    }

    /**
     * 更新entity
     *
     * @param entity
     * @return
     */
    @PostMapping(value = "/update")
    @ApiIgnore
    @Override
    public ResponseBean update(@RequestBody SysLog entity, HttpServletRequest req) throws Exception {
        ResponseBean responseBean = new ResponseBean();
        int rows = sysLogService.update(entity);
        if (rows > 0) {
            responseBean.setSucceed(true);
            responseBean.setMessage("修改操作日志成功！");
        }
        return responseBean;
    }

    /**
     * 删除entity
     *
     * @param entity
     * @return
     */
    @PostMapping(value = "/delete")
    @ApiIgnore
    @Override
    public ResponseBean delete(@RequestBody SysLog entity, HttpServletRequest req) {
        ResponseBean responseBean = new ResponseBean();
        sysLogService.deleteMore(entity);
        responseBean.setSucceed(true);
        responseBean.setMessage("删除操作日志成功！");
        return responseBean;
    }

    /**
     * 根据主键查询entity
     *
     * @param entity
     * @return
     */
    @PostMapping(value = "/findByPK")
    @ApiIgnore
    @Override
    public SysLog findByPK(@RequestBody SysLog entity, HttpServletRequest req) throws Exception {
        return sysLogService.findByPK(entity.getId());
    }

    @Override
    public List<SysLog> findByPKs(SysLog entity, HttpServletRequest req) throws Exception {
        return null;
    }

    /**
     * 根据主键查询entity
     *
     * @param entity
     * @return
     */
    @PostMapping(value = "/findByNo")
    @ApiIgnore
    @Override
    public SysLog findByNo(SysLog entity, HttpServletRequest req) throws Exception {
        return null;
    }

    /**
     * 根据主键查询entity
     *
     * @param entity
     * @return
     */
    @PostMapping(value = "/findByName")
    @ApiIgnore
    @Override
    public SysLog findByName(SysLog entity, HttpServletRequest req) throws Exception {
        return null;
    }

    /**
     * 查询所有数据
     *
     * @param entity
     * @return
     */
    @PostMapping(value = "/findAll")
    @ApiIgnore
    @Override
    public List<SysLog> findAll(@RequestBody SysLog entity, HttpServletRequest req) throws Exception {
        return sysLogService.findAll(entity);
    }

    @ApiIgnore
    @Override
    public void exportExcel(SysLog entity, HttpServletResponse response) throws Exception {

    }

    @ApiIgnore
    @Override
    public ResponseBean importExcel(MultipartFile file) throws Exception {
        return null;
    }

    @ApiIgnore
    @Override
    public void exportTemplate(SysLog entity, HttpServletResponse response) throws Exception {

    }
}

