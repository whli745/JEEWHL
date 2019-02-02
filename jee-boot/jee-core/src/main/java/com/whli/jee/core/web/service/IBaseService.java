package com.whli.jee.core.web.service;


import com.whli.jee.core.page.Page;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.InputStream;
import java.util.List;

/**
 * 通用service
 * Created by whli on 2016/12/20.
 */
public interface IBaseService<T> {
    /**
     * 创建
     **/
    @Transactional
    public int add(T entity);

    /**
     * 批量创建
     * @param entities
     * @return
     */
    @Transactional
    public int addMore(List<T> entities);

    /**
     * 更新
     **/
    @Transactional
    public int update(T entity);

    /**
     * 批量更新
     * @param entities
     * @return
     */
    @Transactional
    public int updateMore(List<T> entities);

    /**
     * 删除
     **/
    @Transactional
    public void delete(T entity);

    /**
     * 批量删除
     * @param entity
     * @return
     */
    @Transactional
    public void deleteMore(T entity);

    /**
     * 根据主键查询
     **/
    public T findByPK(String id);

    /**
     * 根据主键查询
     **/
    public List<T> findByPKs(List<String> ids);

    /**
     * 根据编码查询
     * @param No
     * @return
     */
    public T findByNo(String No);

    /**
     * 根据名称查询
     * @param name
     * @return
     */
    public T findByName(String name);

    /**
     * 分页查询
     **/
    public Page<T> findByPage(T entity, Page<T> page);

    /**
     * 查询所有
     */
    public List<T> findAll(T entity);

    /**
     * 导出Excel
     * @param response
     */
    @Transactional
    public void exportExcel(T entity, HttpServletResponse response);

    /**
     * 导入Excel
     * @return
     */
    @Transactional
    public int importExcel(File file);

    /**
     * 导入Excel
     * @return
     */
    @Transactional
    public int importExcel(InputStream stream);

    /**
     * 导入模板
     * @param response
     */
    @Transactional
    public void exportTemplate(T entity, HttpServletResponse response);
}
