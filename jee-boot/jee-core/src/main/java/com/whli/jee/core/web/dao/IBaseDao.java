package com.whli.jee.core.web.dao;

import com.whli.jee.core.page.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 通用dao
 * Created by whli on 2016/12/19.
 */
public interface IBaseDao<T> {
    /**
     * 增加
     **/
    public int add(@Param("entity") T entity);

    /**
     * 更新
     **/
    public int update(@Param("entity") T entity);

    /**
     * 删除
     **/
    public void delete(@Param("id") String id);

    /**
     * 批量删除
     * @param ids
     * @return
     */
    public void deleteMore(@Param("ids") List<String> ids);

    /**
     * 根据主键查询
     **/
    public T findByPK(@Param("id") String id);

    /**
     * 根据主键查询
     **/
    public List<T> findByPKs(@Param("ids") List<String> ids);

    /**
     * 根据编码查询
     * @param No
     * @return
     */
    public T findByNo(@Param("no") String No);

    /**
     * 根据名称查询
     * @param name
     * @return
     */
    public T findByName(@Param("name") String name);

    /**
     * 分页查询
     **/
    public List<T> findByPage(@Param("entity") T entity, @Param("page") Page<T> page);

    /**
     * 查询所有
     */
    public List<T> findAll(@Param("entity") T entity);
}
