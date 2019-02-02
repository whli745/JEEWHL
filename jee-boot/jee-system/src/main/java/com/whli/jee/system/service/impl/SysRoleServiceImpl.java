package com.whli.jee.system.service.impl;

import com.whli.jee.core.util.StringUtils;
import com.whli.jee.core.web.dao.IBaseDao;
import com.whli.jee.core.web.service.impl.BaseServiceImpl;
import com.whli.jee.system.dao.ISysRoleDao;
import com.whli.jee.system.entity.SysRole;
import com.whli.jee.system.service.ISysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("sysRoleService")
public class SysRoleServiceImpl extends BaseServiceImpl<SysRole> implements ISysRoleService {

	@Autowired
	private ISysRoleDao sysRoleDao;

	@Override
	public IBaseDao<SysRole> getDao() {
		return sysRoleDao;
	}

	public List<SysRole> findRolesByUserId(String userId) {
		List<SysRole> roles = null;
		if(StringUtils.isNotNullOrBlank(userId)){
			roles = sysRoleDao.findRolesByUserId(userId);
		}
		return roles;
	}
}
