package com.whli.jee.system.controller;

import com.whli.jee.core.page.Page;
import com.whli.jee.core.web.controller.BaseController;
import com.whli.jee.core.web.entity.ResponseBean;
import com.whli.jee.system.entity.SysArea;
import com.whli.jee.system.service.ISysAreaService;
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
@RequestMapping(value = "/system/sysArea")
@Api(description = "区域API")
public class SysAreaController extends BaseController<SysArea> {

    @Autowired
    private ISysAreaService sysAreaService;

    /**
     * 分页查询
     *
     * @param entity
     * @return
     */
    @PostMapping(value = "/findByPage")
    @ApiOperation("分页查询区域")
    @Override
    public ResponseBean findByPage(@RequestBody SysArea entity, HttpServletRequest req) throws Exception {
        ResponseBean responseBean = new ResponseBean();
        if (entity != null) {
            Page<SysArea> page = new Page<SysArea>(entity.getCurrentPage(),entity.getPageSize());
            page = sysAreaService.findByPage(entity, page);

            responseBean.setSucceed(true);
            responseBean.setCount(page.getTotal());
            responseBean.setResults(page.getPageRecords());
        }
        return responseBean;
    }

    /**
     * 添加entity
     * @return
     */
    @PostMapping(value = "/add")
    @ApiOperation("增加区域")
    @Override
    public ResponseBean add(@RequestBody SysArea entity, HttpServletRequest req) throws Exception {
        ResponseBean responseBean = new ResponseBean();
        entity.setEnable(1);
        int rows = sysAreaService.add(entity);
        if (rows > 0) {
            responseBean.setSucceed(true);
            responseBean.setMessage("新增区域成功！");
        }
        return responseBean;
    }

    /**
     * 更新entity
     * @return
     */
    @PostMapping(value = "/update")
    @ApiOperation("修改区域")
    @Override
    public ResponseBean update(@RequestBody SysArea entity, HttpServletRequest req) throws Exception {
        ResponseBean responseBean = new ResponseBean();
        int rows = sysAreaService.update(entity);
        if (rows > 0) {
            responseBean.setSucceed(true);
            responseBean.setMessage("修改区域成功！");
        }
        return responseBean;
    }

    /**
     * 删除entity
     * @return
     */
    @PostMapping(value = "/delete")
    @ApiOperation("删除区域")
    @Override
    public ResponseBean delete(@RequestBody SysArea entity, HttpServletRequest req) throws Exception {
        ResponseBean responseBean = new ResponseBean();
        sysAreaService.deleteMore(entity);
        responseBean.setSucceed(true);
        responseBean.setMessage( "删除区域成功！");
        return responseBean;
    }

    /**
     * 根据主键查询entity
     * @return
     */
    @PostMapping(value = "/findByPK")
    @ApiOperation("根据区域ID查询")
    @Override
    public SysArea findByPK(@RequestBody SysArea entity, HttpServletRequest req) throws Exception {
        return sysAreaService.findByPK(entity.getId());
    }

    /**
     * 根据主键查询entity
     * @return
     */
    @PostMapping(value = "/findByPKs")
    @ApiOperation("根据区域ID查询")
    @Override
    public List<SysArea> findByPKs(SysArea entity, HttpServletRequest req) throws Exception {
        return sysAreaService.findByPKs(entity.getIds());
    }

    /**
     * 根据编码查询
     * @param entity
     * @param req
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/findByNo")
    @ApiOperation("根据区域编码查询")
    @Override
    public SysArea findByNo(@RequestBody SysArea entity, HttpServletRequest req) throws Exception {
        return sysAreaService.findByNo(entity.getCode());
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
    @Override
    public SysArea findByName(@RequestBody SysArea entity, HttpServletRequest req) throws Exception {
        return sysAreaService.findByName(entity.getName());
    }

    /**
     * 查询所有数据
     *
     * @param entity
     * @return
     */
    @PostMapping(value = "/findAll")
    @ApiOperation("查询所有区域")
    @Override
    public List<SysArea> findAll(@RequestBody SysArea entity, HttpServletRequest req) throws Exception {
        return sysAreaService.findAll(entity);
    }

    @ApiIgnore
    @Override
    public void exportExcel(SysArea entity, HttpServletResponse response) throws Exception {

    }

    @ApiIgnore
    @Override
    public ResponseBean importExcel(@RequestParam(value = "uploadFile", required = false) MultipartFile file) throws Exception {
        return null;
    }

    @ApiIgnore
    @Override
    public void exportTemplate(SysArea entity, HttpServletResponse response) throws Exception {

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

