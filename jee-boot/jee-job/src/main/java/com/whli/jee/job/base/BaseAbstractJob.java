package com.whli.jee.job.base;

import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.Date;

import com.whli.jee.job.entity.JobLog;
import com.whli.jee.job.service.IJobLogService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>类功能<p>
 *
 * @author whli
 * @version 2018/12/24 14:45
 */
public abstract class BaseAbstractJob implements Job, Serializable {
	private static final long serialVersionUID = -8163583464122658854L;

	@Autowired
    private IJobLogService jobLogService;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        String name = context.getTrigger().getJobKey().getName();
        String group = context.getTrigger().getJobKey().getGroup();
        String className = this.getClass().getName();
        Date startTime = new Date();
        JobLog jobLog = new JobLog();
        jobLog.setJobName(name);
        jobLog.setJobGroup(group);
        jobLog.setJobClass(className);
        jobLog.setDateTime(startTime);

        logger.info("【"+name+"】 任务运行开始！");
        try {
            boolean flag = processJob();
            if (flag){
                logger.info("【"+name+"】 任务运行成功！");
                jobLog.setMsg("【"+name+"】 任务运行成功！");
            }
        }catch (Exception e){
            logger.info("【"+name+"】 任务运行失败：",e);
            StringWriter stringWriter = new StringWriter();
            e.printStackTrace(new PrintWriter(stringWriter));
            jobLog.setMsg("【"+name+"】 任务运行失败："+stringWriter.toString());
        }
        logger.info("【"+name+"】 任务运行结束！");
        Date endTime =new Date();
        logger.info("总耗时(秒)："+((endTime.getTime()-startTime.getTime())/1000));
        jobLog.setDuration((endTime.getTime()-startTime.getTime())/1000);
        jobLogService.add(jobLog);
    }

    public abstract boolean processJob();
}
