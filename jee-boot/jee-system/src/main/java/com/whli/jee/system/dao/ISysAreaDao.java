package com.whli.jee.system.dao;

import com.whli.jee.core.web.dao.IBaseDao;
import com.whli.jee.system.entity.SysArea;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author whli
 * @version 1.0
 * @since 1.0
 * */
@Repository
public interface ISysAreaDao extends IBaseDao<SysArea> {

    /**
     * 根据父ID及排序查询菜单
     * @param entity
     * @return
     */
    public SysArea findByParentIdAndSort(@Param("entity") SysArea entity);
}
