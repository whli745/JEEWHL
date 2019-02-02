package com.whli.jee.system.service.impl;

import com.whli.jee.core.web.dao.IBaseDao;
import com.whli.jee.core.web.service.impl.BaseServiceImpl;
import com.whli.jee.system.dao.ISysOfficeDao;
import com.whli.jee.system.entity.SysOffice;
import com.whli.jee.system.service.ISysOfficeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author whli
 * @version 1.0
 * @since 1.0
 * */
@Service("sysOfficeService")
public class SysOfficeServiceImpl extends BaseServiceImpl<SysOffice> implements ISysOfficeService {

	@Autowired
	private ISysOfficeDao sysOfficeDao;

	@Override
	public IBaseDao<SysOffice> getDao() {
		return sysOfficeDao;
	}

	@Override
	public SysOffice findByParentIdAndSort(SysOffice entity) {
		return sysOfficeDao.findByParentIdAndSort(entity);
	}
}
