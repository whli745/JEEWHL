package com.whli.jee.system.service.impl;

import com.whli.jee.core.web.dao.IBaseDao;
import com.whli.jee.core.web.service.impl.BaseServiceImpl;
import com.whli.jee.system.dao.ISysAreaDao;
import com.whli.jee.system.entity.SysArea;
import com.whli.jee.system.service.ISysAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author whli
 * @version 1.0
 * @since 1.0
 * */
@Service("sysAreaService")
public class SysAreaServiceImpl extends BaseServiceImpl<SysArea> implements ISysAreaService {

	@Autowired
	private ISysAreaDao sysAreaDao;

	@Override
	public IBaseDao<SysArea> getDao() {
		return sysAreaDao;
	}

	@Override
	public SysArea findByParentIdAndSort(SysArea entity) {
		return sysAreaDao.findByParentIdAndSort(entity);
	}
}
