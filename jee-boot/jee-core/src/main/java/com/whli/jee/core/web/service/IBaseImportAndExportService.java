package com.whli.jee.core.web.service;

import com.whli.jee.core.web.entity.BaseEntity;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.InputStream;

/**
 * <p>导入导出Excel服务</p>
 * @author whli
 * @version 1.0.0
 * @date 2019/6/3 11:07
 */
public interface IBaseImportAndExportService<T extends BaseEntity> extends IBaseService<T>{
    /**
     * 导出Excel
     * @param response
     */
    @Transactional
    void exportExcel(T entity, HttpServletResponse response);

    /**
     * 导入Excel
     * @return
     */
    @Transactional
    int importExcel(File file);

    /**
     * 导入Excel
     * @return
     */
    @Transactional
    int importExcel(InputStream stream);

    /**
     * 导入模板
     * @param response
     */
    @Transactional
    void exportTemplate(T entity, HttpServletResponse response);
}
