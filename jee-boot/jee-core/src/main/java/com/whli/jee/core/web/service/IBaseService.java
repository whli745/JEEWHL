package com.whli.jee.core.web.service;


import com.whli.jee.core.page.Page;
import com.whli.jee.core.web.entity.BaseEntity;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.InputStream;
import java.util.List;

/**
 * 通用service
 * Created by whli on 2016/12/20.
 */
public interface IBaseService<T extends BaseEntity> {
    /**
     * 创建
     **/
    @Transactional
    int save(T entity);

    /**
     * 批量创建
     * @param entities
     * @return
     */
    @Transactional
    int saveMore(List<T> entities);

    /**
     * 更新
     **/
    @Transactional
    int update(T entity);

    /**
     * 批量更新
     * @param entities
     * @return
     */
    @Transactional
    int updateMore(List<T> entities);

    /**
     * 删除
     **/
    @Transactional
    void delete(T entity);

    /**
     * 批量删除
     * @param entity
     * @return
     */
    @Transactional
    void deleteMore(T entity);

    /**
     * 根据主键查询
     **/
    T getByPK(String id);

    /**
     * 根据主键查询
     **/
    List<T> listByPKs(List<String> ids);

    /**
     * 根据编码查询
     * @param no
     * @return
     */
    T getByNo(String no);

    /**
     * 根据名称查询
     * @param name
     * @return
     */
    T getByName(String name);

    /**
     * 分页查询
     **/
    Page<T> listByPage(T entity, Page<T> page);

    /**
     * 查询所有
     */
    List<T> listAll(T entity);

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
