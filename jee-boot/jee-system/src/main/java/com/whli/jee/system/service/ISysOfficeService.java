package com.whli.jee.system.service;

import com.whli.jee.core.web.service.IBaseService;
import com.whli.jee.system.entity.SysDict;
import com.whli.jee.system.entity.SysOffice;

/**
 *
 * @author whli
 * @version 1.0
 * @since 1.0
 * */
public interface ISysOfficeService extends IBaseService<SysOffice> {

    /**
     * 根据父ID及排序查询菜单
     * @param entity
     * @return
     */
    public SysOffice findByParentIdAndSort(SysOffice entity);
}
