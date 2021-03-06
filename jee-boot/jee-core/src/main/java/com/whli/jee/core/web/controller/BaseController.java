package com.whli.jee.core.web.controller;

import com.whli.jee.core.anotation.AuthorPermit;
import com.whli.jee.core.page.Page;
import com.whli.jee.core.web.entity.BaseEntity;
import com.whli.jee.core.web.entity.ResponseBean;
import com.whli.jee.core.web.service.IBaseService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>基本控制器<p>
 * @author whli
 * @version 2018/12/20 11:15
 */
public abstract class BaseController<T extends BaseEntity> {


    public abstract IBaseService<T> getService();

    /**
     * 分页查询
     * @param entity
     * @param req
     * @return
     * @throws Exception
     */
    @PostMapping("/listByPage")
    @ApiOperation("分页查询")
    public ResponseBean listByPage(@RequestBody T entity, HttpServletRequest req) throws Exception{
        ResponseBean responseBean = new ResponseBean();
        if (entity != null) {
            Page<T> page = new Page<T>(entity.getCurrentPage(),entity.getPageSize());
            page = getService().listByPage(entity,page);

            responseBean.setSucceed(true);
            responseBean.setCount(page.getTotal());
            responseBean.setResults(page.getPageRecords());
        }
        return responseBean;
    };

    /**
     * 新增
     * @param entity
     * @param req
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/save")
    @ApiOperation("新增")
    public ResponseBean save(@RequestBody T entity, HttpServletRequest req) throws Exception{
        ResponseBean responseBean = new ResponseBean();
        int rows = getService().save(entity);
        if (rows > 0) {
            responseBean.setSucceed(true);
            responseBean.setResults("1");
            responseBean.setMessage("新增成功！");
        }
        return responseBean;
    };

    /**
     * 修改
     * @param entity
     * @param req
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/update")
    @ApiOperation("修改")
    public ResponseBean update(@RequestBody T entity, HttpServletRequest req) throws Exception{
        ResponseBean responseBean = new ResponseBean();
        int rows = getService().update(entity);
        if (rows > 0) {
            responseBean.setSucceed(true);
            responseBean.setResults("1");
            responseBean.setMessage("更新成功！");
        }
        return responseBean;
    };

    /**
     * 删除
     * @param entity
     * @param req
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/delete")
    @ApiOperation("删除")
    public ResponseBean delete(@RequestBody T entity, HttpServletRequest req) throws Exception{
        ResponseBean responseBean = new ResponseBean();
        getService().deleteMore(entity);
        responseBean.setSucceed(true);
        responseBean.setResults("1");
        responseBean.setMessage("删除成功！");
        return responseBean;
    };

    /**
     * 依据主键查询
     * @param entity
     * @param req
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/getByPK")
    @ApiOperation("根据主键查询")
    public T getByPK(@RequestBody T entity, HttpServletRequest req) throws Exception{
        return getService().getByPK(entity.getId());
    };

    /**
     * 依据主键查询
     * @param entity
     * @param req
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/listByPKs")
    @ApiOperation("根据多个主键查询")
    public List<T> listByPKs(@RequestBody T entity, HttpServletRequest req) throws Exception{
        return getService().listByPKs(entity.getIds());
    };

    /**
     * 查询所有
     * @param entity
     * @param req
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/listAll")
    @ApiOperation("查找所有结果（可带参数）")
    public List<T> listAll(@RequestBody T entity, HttpServletRequest req) throws Exception{
        return getService().listAll(entity);
    };

    /**
     * 导出Excel
     * @param response
     */
    @GetMapping(value = "/exportExcel")
    @AuthorPermit
    @ApiOperation("导出Excel")
    public void exportExcel(T entity, HttpServletResponse response) throws Exception{
        getService().exportExcel(entity,response);
    };

    /**
     * 导入Excel
     * @return
     */
    @PostMapping(value = "/importExcel")
    @AuthorPermit
    @ApiOperation("导入Excel")
    public ResponseBean importExcel(@RequestParam(value = "uploadFile", required = false) MultipartFile file) throws Exception{
        ResponseBean responseBean = new ResponseBean();
        if (file != null){
            int rows = getService().importExcel(file.getInputStream());
            if (rows > 0) {
                responseBean.setSucceed(true);
                responseBean.setResults("1");
                responseBean.setMessage("导入成功！");
            }
        }
        return responseBean;
    };

    /**
     * 导入模板
     * @param response
     */
    @GetMapping(value = "/exportTemplate")
    @AuthorPermit
    @ApiOperation("导出模板")
    public void exportTemplate(T entity, HttpServletResponse response) throws Exception{
        getService().exportTemplate(entity,response);
    };
}
