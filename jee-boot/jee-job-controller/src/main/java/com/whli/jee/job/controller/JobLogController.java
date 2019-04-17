package com.whli.jee.job.controller;

import com.whli.jee.core.web.controller.BaseController;
import com.whli.jee.core.web.service.IBaseService;
import com.whli.jee.job.entity.JobLog;
import com.whli.jee.job.service.IJobLogService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * <em>类或方法作用描述</em>
 * @author whli
 * @version 2018/9/4 9:26
 * */
@RestController
@RequestMapping(value="/scheduler/jobLog")
@Api(description = "定时任务日志API")
public class JobLogController extends BaseController<JobLog>{

	@Autowired
	private IJobLogService jobLogService;

    @Override
    public IBaseService<JobLog> getService() {
        return jobLogService;
    }

}

