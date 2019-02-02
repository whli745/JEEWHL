package com.whli.jee.system.service.impl;

import com.whli.jee.core.exception.BusinessException;
import com.whli.jee.core.util.CollectionUtils;
import com.whli.jee.core.util.StringUtils;
import com.whli.jee.core.web.dao.IBaseDao;
import com.whli.jee.core.web.service.impl.BaseServiceImpl;
import com.whli.jee.system.dao.ISysRoleMenuDao;
import com.whli.jee.system.entity.SysRoleMenu;
import com.whli.jee.system.service.ISysRoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author whli
 * @version 1.0
 * @since 1.0
 */
@Service("sysRoleMenuService")
public class SysRoleMenuServiceImpl extends BaseServiceImpl<SysRoleMenu> implements ISysRoleMenuService {

    @Autowired
    private ISysRoleMenuDao sysRoleMenuDao;

    @Override
    public IBaseDao<SysRoleMenu> getDao() {
        return sysRoleMenuDao;
    }

    @Override
    public int grantByRole(String roleId, List<String> menuIds) {
        if (StringUtils.isNullOrBlank(roleId)) {
            throw new BusinessException("-02060601", "请选择需要授权的角色！");
        }

        List<SysRoleMenu> sysRoleMenus = new ArrayList<SysRoleMenu>();
        if (CollectionUtils.isNotNullOrEmpty(menuIds)){
            sysRoleMenuDao.deleteByRole(roleId);//根据角色删除权限

            for (String menuId : menuIds){
                SysRoleMenu sysRoleMenu = new SysRoleMenu();
                sysRoleMenu.setRoleId(roleId);
                sysRoleMenu.setMenuId(menuId);
                sysRoleMenus.add(sysRoleMenu);
            }
        }

        return addMore(sysRoleMenus);
    }
}
