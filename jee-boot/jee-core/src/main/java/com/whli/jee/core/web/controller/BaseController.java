package com.whli.jee.core.web.controller;

import com.whli.jee.core.web.entity.ResponseBean;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>基本控制器<p>
 * @author whli
 * @version 2018/12/20 11:15
 */
public abstract class BaseController<T> {

    /**
     * 分页查询
     * @param entity
     * @param req
     * @return
     * @throws Exception
     */
    public abstract ResponseBean findByPage(T entity, HttpServletRequest req) throws Exception;

    /**
     * 新增
     * @param entity
     * @param req
     * @return
     * @throws Exception
     */
    public abstract ResponseBean add(T entity, HttpServletRequest req) throws Exception;

    /**
     * 修改
     * @param entity
     * @param req
     * @return
     * @throws Exception
     */
    public abstract ResponseBean update(T entity, HttpServletRequest req) throws Exception;

    /**
     * 删除
     * @param entity
     * @param req
     * @return
     * @throws Exception
     */
    public abstract ResponseBean delete(T entity, HttpServletRequest req) throws Exception;

    /**
     * 依据主键查询
     * @param entity
     * @param req
     * @return
     * @throws Exception
     */
    public abstract T findByPK(T entity, HttpServletRequest req) throws Exception;

    /**
     * 依据主键查询
     * @param entity
     * @param req
     * @return
     * @throws Exception
     */
    public abstract List<T> findByPKs(T entity, HttpServletRequest req) throws Exception;

    /**
     * 根据编码查询
     * @param entity
     * @return
     */
    public abstract T findByNo(T entity, HttpServletRequest req) throws Exception;

    /**
     * 根据名称查询
     * @param entity
     * @return
     */
    public abstract T findByName(T entity, HttpServletRequest req) throws Exception;

    /**
     * 查询所有
     * @param entity
     * @param req
     * @return
     * @throws Exception
     */
    public abstract List<T> findAll(T entity, HttpServletRequest req) throws Exception;

    /**
     * 导出Excel
     * @param response
     */
    public abstract void exportExcel(T entity, HttpServletResponse response) throws Exception;

    /**
     * 导入Excel
     * @return
     */
    public abstract ResponseBean importExcel(MultipartFile file) throws Exception;

    /**
     * 导入模板
     * @param response
     */
    public abstract void exportTemplate(T entity, HttpServletResponse response) throws Exception;
}
