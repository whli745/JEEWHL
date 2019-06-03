package com.whli.jee.system.service.impl;

import com.whli.jee.core.exception.BusinessException;
import com.whli.jee.core.web.dao.IBaseDao;
import com.whli.jee.core.web.service.impl.BaseServiceImpl;
import com.whli.jee.system.dao.ISysUserRoleDao;
import com.whli.jee.system.entity.SysUserRole;
import com.whli.jee.system.service.ISysUserRoleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author whli
 * @version 1.0
 * @since 1.0
 */
@Service("sysUserRoleService")
public class SysUserRoleServiceImpl extends BaseServiceImpl<SysUserRole> implements ISysUserRoleService {

    @Autowired
    private ISysUserRoleDao sysUserRoleDao;

    @Override
    public IBaseDao<SysUserRole> getDao() {
        return sysUserRoleDao;
    }

    @Override
    public void deleteRolesByUser(SysUserRole entity) {
        if (StringUtils.isBlank(entity.getUserId())) {
            throw new BusinessException("请选择需要删除角色的用户！");
        }
        sysUserRoleDao.deleteRolesByUser(entity);
    }


}
