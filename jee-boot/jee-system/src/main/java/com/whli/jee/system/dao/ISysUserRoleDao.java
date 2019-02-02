package com.whli.jee.system.dao;

import com.whli.jee.core.web.dao.IBaseDao;
import com.whli.jee.system.entity.SysUserRole;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author whli
 * @version 1.0
 * @since 1.0
 * */
@Repository
public interface ISysUserRoleDao extends IBaseDao<SysUserRole> {

    /**
     * 根据角色删除对应菜单关系
     * @param entity
     */
    public void deleteByUser(@Param("entity") SysUserRole entity);
}
