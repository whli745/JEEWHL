package com.whli.jee.system.controller;

import com.whli.jee.core.web.controller.BaseController;
import com.whli.jee.core.web.service.IBaseService;
import com.whli.jee.system.entity.SysArea;
import com.whli.jee.system.service.ISysAreaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author whli
 * @version 1.0
 * @since 1.0
 */
@RestController
@RequestMapping(value = "/system/sysArea")
@Api(description = "区域API")
public class SysAreaController extends BaseController<SysArea> {

    @Autowired
    private ISysAreaService sysAreaService;

    @Override
    public IBaseService<SysArea> getService() {
        return sysAreaService;
    }


    /**
     * 根据编码查询
     * @param entity
     * @param req
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/findByCode")
    @ApiOperation("根据区域编码查询")
    public SysArea findByCode(@RequestBody SysArea entity, HttpServletRequest req) throws Exception {
        return sysAreaService.findByCode(entity.getCode());
    }

    /**
     * 根据名称查询
     * @param entity
     * @param req
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/findByName")
    @ApiOperation("根据区域名称查询")
    public SysArea findByName(@RequestBody SysArea entity, HttpServletRequest req) throws Exception {
        return sysAreaService.findByName(entity.getName());
    }

    /**
     * 查询同级别下是否存在同一序号
     * @param entity
     * @return
     */
    @PostMapping(value = "/findByParentIdAndSort")
    @ApiOperation("查询同级别下序号是否存在")
    public SysArea findByParentIdAndSort(@RequestBody SysArea entity) throws Exception{
        return sysAreaService.findByParentIdAndSort(entity);
    }
}

