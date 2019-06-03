package com.whli.jee.core.web.controller;

import com.whli.jee.core.anotation.AuthorPermit;
import com.whli.jee.core.web.entity.BaseEntity;
import com.whli.jee.core.web.entity.ResponseBean;
import com.whli.jee.core.web.service.IBaseImportAndExportService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * <p>类或方法描述</p>
 *
 * @author whli
 * @version 1.0.0
 * @date 2019/6/3 11:11
 */
public abstract class BaseImportAndExportController<T extends BaseEntity> extends BaseController<T>{

    public abstract IBaseImportAndExportService<T> getService();

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
