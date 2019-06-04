package com.whli.jee.core.web.service.impl;


import com.whli.jee.core.page.Page;
import com.whli.jee.core.util.BeanUtils;
import com.whli.jee.core.util.WebUtils;
import com.whli.jee.core.web.dao.IBaseDao;
import com.whli.jee.core.web.entity.BaseEntity;
import com.whli.jee.core.web.service.IBaseService;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

/**
 * 通用service实现
 * Created by whli on 2017/10/29.
 */
public abstract class BaseServiceImpl<T extends BaseEntity> implements IBaseService<T> {


    public abstract IBaseDao<T> getDao();

    /**
     * 增加
     * @param entity
     * @return
     */
    @Override
    public int save(T entity){
        entity.setId(BeanUtils.getUUID());
        entity.setCreateBy(WebUtils.getLoginName());
        entity.setCreateDate(new Date());
        return getDao().save(entity);
    }

    /**
     * 批量增加
     * @param entities
     * @return
     */
    @Override
    public int saveMore(List<T> entities){
        entities.stream().forEach(entity -> {
            entity.setId(BeanUtils.getUUID());
            entity.setCreateBy(WebUtils.getLoginName());
            entity.setCreateDate(new Date());
            getDao().save(entity);
        });
        return 1;
    }

    /**
     * 更新
     * @param entity
     * @return
     */
    @Override
    public int update(T entity){
        entity.setUpdateBy(WebUtils.getLoginName());
        entity.setUpdateDate(new Date());
        return getDao().update(entity);
    }

    /**
     * 批量更新
     * @param entities
     * @return
     */
    @Override
    public int updateMore(List<T> entities){
        entities.stream().forEach(entity -> {
            entity.setUpdateBy(WebUtils.getLoginName());
            entity.setUpdateDate(new Date());
            getDao().update(entity);
        });

        return 1;
    }

    /**
     * 删除
     * @param entity
     */
    @Override
    public void delete(T entity){
        getDao().delete(entity.getId());
    }

    /**
     * 批量删除
     * @param entity
     * @return
     */
    @Override
    public void deleteMore(T entity){
        getDao().deleteMore(entity.getIds());
    }

    /**
     * 主键id查询
     * @param id
     * @return
     */
    @Override
    public T getByPK(String id){
        return getDao().getByPK(id);
    }

    @Override
    public List<T> listByPKs(List<String> ids) {
        return getDao().listByPKs(ids);
    }

    @Override
    public T getByNo(String no) {
        return getDao().getByNo(no);
    }

    @Override
    public T getByName(String name) {
        return getDao().getByName(name);
    }

    /**
     * 分页查询
     * @param entity
     * @param page
     * @return
     */
    @Override
    public Page<T> listByPage(T entity, Page<T> page){
        List<T> records = getDao().listByPage(entity,page);
        if (page == null){
            page = new Page<T>();
        }
        page.setPageRecords(records);
        return page;
    }

    /**
     * 查询所有
     * @param entity
     * @return
     */
    @Override
    public List<T> listAll(T entity){
        return getDao().listAll(entity);
    }

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
