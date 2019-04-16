package com.whli.jee.system.controller;

import com.whli.jee.core.page.Page;
import com.whli.jee.core.util.BeanUtils;
import com.whli.jee.core.web.controller.BaseController;
import com.whli.jee.core.web.entity.ResponseBean;
import com.whli.jee.system.entity.SysDict;
import com.whli.jee.system.service.ISysDictService;
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

    /**
     * 分页查询
     *
     * @param entity
     * @return
     */
    @PostMapping(value = "/findByPage")
    @ApiOperation("分页查询字典")
    @Override
    public ResponseBean findByPage(@RequestBody SysDict entity, HttpServletRequest req) throws Exception {
        ResponseBean responseBean = new ResponseBean();
        if (BeanUtils.isNotNull(entity)){
            if (StringUtils.isBlank(entity.getParentId())){
                entity.setParentId("");
            }
            Page<SysDict> page = new Page<SysDict>(entity.getCurrentPage(),entity.getPageSize());
            page = sysDictService.findByPage(entity, page);

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
    @ApiOperation("增加字典")
    @Override
    public ResponseBean add(@RequestBody SysDict entity, HttpServletRequest req) throws Exception {
        ResponseBean responseBean = new ResponseBean();
        entity.setEnable(1);
        int rows = sysDictService.add(entity);
        if (rows > 0) {
            responseBean.setSucceed(true);
            responseBean.setMessage("新增数据字典成功！");
        }
        return responseBean;
    }

    /**
     * 更新entity
     *
     * @return
     */
    @PostMapping(value = "/update")
    @ApiOperation("修改字典")
    @Override
    public ResponseBean update(@RequestBody SysDict entity, HttpServletRequest req) throws Exception {
        ResponseBean responseBean = new ResponseBean();
        int rows = sysDictService.update(entity);
        if (rows > 0) {
            responseBean.setSucceed(true);
            responseBean.setMessage("修改数据字典成功！");
        }
        return responseBean;
    }

    /**
     * 删除entity
     *
     * @return
     */
    @PostMapping(value = "/delete")
    @ApiOperation("删除字典")
    @Override
    public ResponseBean delete(@RequestBody SysDict entity, HttpServletRequest req) throws Exception {
        ResponseBean responseBean = new ResponseBean();
        sysDictService.deleteMore(entity);
        responseBean.setSucceed(true);
        responseBean.setMessage("删除数据字典成功！");
        return responseBean;
    }

    /**
     * 根据主键查询entity
     *
     * @return
     */
    @PostMapping(value = "/findByPK")
    @ApiOperation("根据字典ID查询")
    @Override
    public SysDict findByPK(@RequestBody SysDict entity, HttpServletRequest req) throws Exception {
        return sysDictService.findByPK(entity.getId());
    }

    @PostMapping(value = "/findByPKs")
    @ApiOperation("根据字典ID查询")
    @Override
    public List<SysDict> findByPKs(SysDict entity, HttpServletRequest req) throws Exception {
        return sysDictService.findByPKs(entity.getIds());
    }

    /**
     * 根据编码查询
     * @param entity
     * @param req
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/findByNo")
    @ApiOperation("根据字典值查询")
    @Override
    public SysDict findByNo(@RequestBody SysDict entity, HttpServletRequest req) throws Exception {
        return sysDictService.findByNo(entity.getValue());
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
    @Override
    public SysDict findByName(@RequestBody SysDict entity, HttpServletRequest req) throws Exception {
        return sysDictService.findByName(entity.getName());
    }

    /**
     * 查询所有数据
     *
     * @param entity
     * @return
     */
    @PostMapping(value = "/findAll")
    @ApiOperation("查询所有字典")
    @Override
    public List<SysDict> findAll(@RequestBody SysDict entity, HttpServletRequest req) throws Exception {
        return sysDictService.findAll(entity);
    }

    @ApiIgnore
    @Override
    public void exportExcel(SysDict entity, HttpServletResponse response) throws Exception {

    }

    @ApiIgnore
    @Override
    public ResponseBean importExcel(@RequestParam(value = "uploadFile", required = false) MultipartFile file) throws Exception {
        return null;
    }

    @ApiIgnore
    @Override
    public void exportTemplate(SysDict entity, HttpServletResponse response) throws Exception {

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

