package com.whli.jee.system.controller;

import com.whli.jee.core.web.controller.BaseController;
import com.whli.jee.core.web.service.IBaseService;
import com.whli.jee.system.entity.SysOffice;
import com.whli.jee.system.service.ISysOfficeService;
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
@RequestMapping(value = "/system/sysOffice")
@Api(description = "组织架构API")
public class SysOfficeController extends BaseController<SysOffice> {

    @Autowired
    private ISysOfficeService sysOfficeService;

    @Override
    public IBaseService<SysOffice> getService() {
        return sysOfficeService;
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
    public SysOffice findByName(@RequestBody SysOffice entity, HttpServletRequest req) throws Exception {
        return sysOfficeService.findByName(entity.getName());
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

