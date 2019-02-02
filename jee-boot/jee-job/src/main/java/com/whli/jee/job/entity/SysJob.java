package com.whli.jee.job.entity;

import com.whli.jee.core.web.entity.BaseEntity;

import java.sql.Timestamp;
import java.util.Date;

/**
 * <p>类功能<p>
 *
 * @author whli
 * @version 2018/12/24 10:04
 */
public class SysJob extends BaseEntity {

    private String jobName; //任务名称
    private String jobGroup; //任务分组
    private String jobClass;//任务执行方法
    private String cronExpression; // cron 表达式
    private String jobDescription; //任务描述
    private Long startTime;
    private Long prevTime;
    private Long nextTime;
    private String state; //状态

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobGroup() {
        return jobGroup;
    }

    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }

    public String getJobClass() {
        return jobClass;
    }

    public void setJobClass(String jobClass) {
        this.jobClass = jobClass;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getPrevTime() {
        return prevTime;
    }

    public void setPrevTime(Long prevTime) {
        this.prevTime = prevTime;
    }

    public Long getNextTime() {
        return nextTime;
    }

    public void setNextTime(Long nextTime) {
        this.nextTime = nextTime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
