package com.whli.jee.core.web.dao;

import com.whli.jee.core.page.Page;
import com.whli.jee.core.web.entity.BaseEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 通用dao
 * Created by whli on 2016/12/19.
 */
public interface IBaseDao<T extends BaseEntity> {
    /**
     * 增加
     **/
    int save(@Param("entity") T entity);

    /**
     * 更新
     **/
    int update(@Param("entity") T entity);

    /**
     * 删除
     **/
    void delete(@Param("id") String id);

    /**
     * 批量删除
     * @param ids
     * @return
     */
    void deleteMore(@Param("ids") List<String> ids);

    /**
     * 根据主键查询
     **/
    T getByPK(@Param("id") String id);

    /**
     * 根据主键查询
     **/
    List<T> listByPKs(@Param("ids") List<String> ids);

    /**
     * 根据编码查询
     * @param no
     * @return
     */
    T getByNo(@Param("no") String no);

    /**
     * 根据名称查询
     * @param name
     * @return
     */
    T getByName(@Param("name") String name);

    /**
     * 分页查询
     **/
    List<T> listByPage(@Param("entity") T entity, @Param("page") Page<T> page);

    /**
     * 查询所有
     */
    List<T> listAll(@Param("entity") T entity);
}
