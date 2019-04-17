package com.whli.jee.system.controller;

import com.whli.jee.core.web.controller.BaseController;
import com.whli.jee.core.web.service.IBaseService;
import com.whli.jee.system.entity.SysDict;
import com.whli.jee.system.service.ISysDictService;
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
 * @author whli
 * @version 1.0
 * @since 1.0
 */
@RestController
@RequestMapping(value = "/system/sysDict")
@Api(description = "系统字典API")
public class SysDictController extends BaseController<SysDict> {

    @Autowired
    private ISysDictService sysDictService;

    @Override
    public IBaseService<SysDict> getService() {
        return sysDictService;
    }

    /**
     * 根据编码查询
     * @param entity
     * @param req
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/findByValue")
    @ApiOperation("根据字典值查询")
    public SysDict findByValue(@RequestBody SysDict entity, HttpServletRequest req) throws Exception {
        return sysDictService.findByValue(entity.getValue());
    }

    /**
     * 根据名称查询
     * @param entity
     * @param req
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/findByName")
    @ApiOperation("根据字典名称查询")
    public SysDict findByName(@RequestBody SysDict entity, HttpServletRequest req) throws Exception {
        return sysDictService.findByName(entity.getName());
    }

    /**
     * 依据父字典值查询子字典
     *
     * @return
     */
    @PostMapping(value = "/findByParentValue")
    @ApiOperation("根据父字典值查询")
    public List<SysDict> findByParentValue(@RequestBody SysDict entity, HttpServletRequest req) throws Exception {
        return sysDictService.findByParentValue(entity.getValue());
    }

    /**
     * 查询同级别下是否存在同一序号
     * @param entity
     * @return
     */
    @PostMapping(value = "/findByParentIdAndSort")
    @ApiOperation("查询同级别下序号是否存在")
    public SysDict findByParentIdAndSort(@RequestBody SysDict entity){
        return sysDictService.findByParentIdAndSort(entity);
    }
}

