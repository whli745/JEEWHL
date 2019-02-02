package com.whli.jee.system.dao;

import com.whli.jee.core.web.dao.IBaseDao;
import com.whli.jee.system.entity.SysDict;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * @author whli
 * @version 1.0
 * @since 1.0
 * */
@Repository
public interface ISysDictDao extends IBaseDao<SysDict> {

    /**
     * 根据父字典值查询子字典
     * @param value
     * @return
     */
    public List<SysDict> findByParentValue(@Param("value") String value);

    /**
     * 根据父ID及排序查询菜单
     * @param entity
     * @return
     */
    public SysDict findByParentIdAndSort(@Param("entity") SysDict entity);
}
