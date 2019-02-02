package com.whli.jee.system.dao;

import com.whli.jee.core.web.dao.IBaseDao;
import com.whli.jee.system.entity.SysRoleMenu;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author whli
 * @version 1.0
 * @since 1.0
 * */
@Repository
public interface ISysRoleMenuDao extends IBaseDao<SysRoleMenu> {
    /**
     * 根据角色删除对应菜单关系
     * @param roleId
     */
    public void deleteByRole(@Param("roleId") String roleId);

    /**
     *  根据角色及菜单查询对应关系
     * @param roleId
     * @param menuId
     * @return
     */
    public SysRoleMenu findByRoleIdAndMenuId(@Param("roleId") String roleId, @Param("menuId") String menuId);
}
