package com.whli.jee.job.service.impl;

import com.whli.jee.core.web.dao.IBaseDao;
import com.whli.jee.core.web.service.impl.BaseServiceImpl;
import com.whli.jee.job.dao.IJobLogDao;
import com.whli.jee.job.entity.JobLog;
import com.whli.jee.job.service.IJobLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * <em>类或方法作用描述</em>
 * @author whli
 * @version 2018/9/4 9:26
 * */
@Service("jobLogService")
public class JobLogServiceImpl extends BaseServiceImpl<JobLog> implements IJobLogService {

	@Autowired
	private IJobLogDao jobLogDao;

	@Override
	public IBaseDao<JobLog> getDao() {
		return jobLogDao;
	}
}
