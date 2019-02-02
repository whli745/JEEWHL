package com.whli.jee.system.service;

import com.whli.jee.core.web.service.IBaseService;
import com.whli.jee.system.entity.SysDict;

import java.util.List;

/**
 *
 * @author whli
 * @version 1.0
 * @since 1.0
 * */
public interface ISysDictService extends IBaseService<SysDict> {
    /**
     * 根据父字典值查询子字典
     * @param value
     * @return
     */
    public List<SysDict> findByParentValue(String value);

    /**
     * 根据父ID及排序查询菜单
     * @param entity
     * @return
     */
    public SysDict findByParentIdAndSort(SysDict entity);
}
