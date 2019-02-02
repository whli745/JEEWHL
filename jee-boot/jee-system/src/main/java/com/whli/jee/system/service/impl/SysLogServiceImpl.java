package com.whli.jee.system.service.impl;

import com.whli.jee.core.web.dao.IBaseDao;
import com.whli.jee.core.web.service.impl.BaseServiceImpl;
import com.whli.jee.system.dao.ISysLogDao;
import com.whli.jee.system.entity.SysLog;
import com.whli.jee.system.service.ISysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author whli
 * @version 1.0
 * @since 1.0
 * */
@Service("sysLogService")
public class SysLogServiceImpl extends BaseServiceImpl<SysLog> implements ISysLogService {

	@Autowired
	private ISysLogDao sysLogDao;

	@Override
	public IBaseDao<SysLog> getDao() {
		return sysLogDao;
	}
}
