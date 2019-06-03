package com.whli.jee.core.web.service.impl;

import com.whli.jee.core.web.entity.BaseEntity;
import com.whli.jee.core.web.service.IBaseImportAndExportService;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.InputStream;

/**
 * <p>类或方法描述</p>
 *
 * @author whli
 * @version 1.0.0
 * @date 2019/6/3 11:09
 */
public abstract class BaseImportAndExportServiceImpl<T extends BaseEntity> extends BaseServiceImpl<T> implements IBaseImportAndExportService<T> {

    /**
     * 导出Excel
     * @param response
     */
    @Override
    public void exportExcel(T entity, HttpServletResponse response) {

    }

    /**
     * 导入Excel
     * @param file
     * @return
     */
    @Override
    public int importExcel(File file) {
        return 0;
    }

    /**
     * 导入Excel
     * @param stream
     * @return
     */
    @Override
    public int importExcel(InputStream stream) {
        return 0;
    }

    /**
     * 导入模板
     * @param response
     */
    @Override
    public void exportTemplate(T entity, HttpServletResponse response) {

    }
}
