package com.whli.jee.job.service.impl;

import com.whli.jee.core.exception.BusinessException;
import com.whli.jee.core.util.DateUtils;
import com.whli.jee.core.web.dao.IBaseDao;
import com.whli.jee.core.web.service.impl.BaseServiceImpl;
import com.whli.jee.job.dao.ISysJobDao;
import com.whli.jee.job.entity.SysJob;
import com.whli.jee.job.service.ISysJobService;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;

/**
 * <p>定时任务<p>
 *
 * @author whli
 * @version 2018/12/24 11:17
 */
@Service("sysJobService")
public class SysJobServiceImpl extends BaseServiceImpl<SysJob> implements ISysJobService {
	
    @Autowired
    private ISysJobDao taskDao;

    @Autowired
    @Qualifier(value = "scheduler")
    private Scheduler scheduler;

    @Override
    public IBaseDao<SysJob> getDao() {
        return taskDao;
    }

    @Override
    public int add(SysJob entity) {
        String jobName = entity.getJobName(),
                jobGroup = entity.getJobGroup(),
                cronExpression = entity.getCronExpression(),
                jobDescription = entity.getJobDescription();
        try{
            if (checkExists(jobName, jobGroup)) {
                throw new BusinessException(String.format("Job已经存在, jobName:{%s},jobGroup:{%s}", jobName, jobGroup));
            }
            TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);
            JobKey jobKey = JobKey.jobKey(jobName, jobGroup);

            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression).withMisfireHandlingInstructionDoNothing();
            CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(triggerKey).withDescription(jobDescription).withSchedule(scheduleBuilder).build();

            Class<? extends org.quartz.Job> clazz = (Class<? extends org.quartz.Job>) Class.forName(entity.getJobClass());
            JobDetail jobDetail = JobBuilder.newJob(clazz).withIdentity(jobKey).withDescription(jobDescription).build();
            scheduler.scheduleJob(jobDetail, trigger);
            return 1;
        }catch (Exception e){
            throw new BusinessException("类名不存在或执行表达式错误",e);
        }
    }

    @Override
    public int update(SysJob entity) {
        String jobName = entity.getJobName(),
                jobGroup = entity.getJobGroup(),
                cronExpression = entity.getCronExpression(),
                jobDescription = entity.getJobDescription(),
                createTime = DateUtils.dateToString(new Date(), DateUtils.DEFAULT_YYYY_MM_DD_HH_MM_SS);

        try {
            if (!checkExists(jobName, jobGroup)) {
                throw new BusinessException(String.format("Job不存在, jobName:{%s},jobGroup:{%s}", jobName, jobGroup));
            }
            TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);
            JobKey jobKey = new JobKey(jobName, jobGroup);
            CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression).withMisfireHandlingInstructionDoNothing();
            CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity(triggerKey).withDescription(createTime).withSchedule(cronScheduleBuilder).build();

            JobDetail jobDetail = scheduler.getJobDetail(jobKey);
            jobDetail.getJobBuilder().withDescription(jobDescription);
            HashSet<Trigger> triggerSet = new HashSet<>();
            triggerSet.add(cronTrigger);

            scheduler.scheduleJob(jobDetail, triggerSet, true);
            return 1;
        } catch (SchedulerException e) {
            throw new BusinessException("类名不存在或执行表达式错误",e);
        }
    }

    @Override
    public void delete(SysJob entity) {
        TriggerKey triggerKey = TriggerKey.triggerKey(entity.getJobName(), entity.getJobGroup());
        try {
            if (checkExists(entity.getJobName(), entity.getJobGroup())) {
                scheduler.pauseTrigger(triggerKey); //停止触发器
                scheduler.unscheduleJob(triggerKey); //移除触发器
            }
        } catch (SchedulerException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    /**
     * 验证JOB是否存在
     *
     * @param jobName
     * @param jobGroup
     * @throws SchedulerException
     */
    private boolean checkExists(String jobName, String jobGroup) throws SchedulerException {
        TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);
        return scheduler.checkExists(triggerKey);
    }

    @Override
    public boolean triggerJob(SysJob entity) {
        try {
            String jobName = entity.getJobName(),
                    jobGroup = entity.getJobGroup();
            JobKey jobKey = new JobKey(jobName, jobGroup);
            scheduler.triggerJob(jobKey);
            return true;
        } catch (SchedulerException e) {
            throw new BusinessException("立即执行JOB失败：",e);
        }
    }

    @Override
    public boolean resumeJob(SysJob entity) {
        try {
            scheduler.resumeJob(JobKey.jobKey(entity.getJobName(), entity.getJobGroup()));
            return true;
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public boolean pauseJob(SysJob entity) {
        TriggerKey triggerKey = TriggerKey.triggerKey(entity.getJobName(), entity.getJobGroup());
        try {
            if (checkExists(entity.getJobName(), entity.getJobGroup())) {
                scheduler.pauseTrigger(triggerKey); //停止触发器
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public boolean resumeAll() {
        try {
            scheduler.resumeAll();
            return true;
        } catch (SchedulerException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public boolean pauseAll() {
        try {
            scheduler.pauseAll();
            return true;
        } catch (SchedulerException e) {
            throw new BusinessException(e.getMessage());
        }
    }
}
