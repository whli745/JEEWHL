package com.whli.jee.system.service;

import com.whli.jee.core.web.service.IBaseService;
import com.whli.jee.system.entity.SysRole;

import java.util.List;

public interface ISysRoleService extends IBaseService<SysRole> {
    /**
     * 根据用户查询授权角色
     * @param userId
     * @return
     */
    public List<SysRole> findRolesByUserId(String userId);
}
