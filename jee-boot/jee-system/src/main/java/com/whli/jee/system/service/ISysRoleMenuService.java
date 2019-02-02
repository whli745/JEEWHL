package com.whli.jee.system.service;

import com.whli.jee.core.web.service.IBaseService;
import com.whli.jee.system.entity.SysRoleMenu;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>角色菜单业务类</p>
 * @author whli
 * @version 1.0
 * @since 1.0
 * */
public interface ISysRoleMenuService extends IBaseService<SysRoleMenu> {

    /**
     *授权角色菜单
     * @param roleId
     * @param menuIds
     * @return
     */
    @Transactional
    public int grantByRole(String roleId, List<String> menuIds);
}
