package com.whli.jee.job.entity;

import com.whli.jee.core.web.entity.BaseEntity;
import java.util.List;

/**
 *
 * <em>类或方法作用描述</em>
 * @author whli
 * @version 2018/9/4 9:26
 * */
public class JobLog extends BaseEntity implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	private String jobName;  //任务名称
	private String jobGroup;  //任务组
	private String jobClass;  //类名
	private java.util.Date dateTime;  //运行时间
	private Long duration;  //运行时长
	private String msg;  //msg

	public void setJobName(String jobName){
		this.jobName = jobName;
	}
	public String getJobName(){
		return this.jobName;
	}
	public void setJobGroup(String jobGroup){
		this.jobGroup = jobGroup;
	}
	public String getJobGroup(){
		return this.jobGroup;
	}
	public void setJobClass(String jobClass){
		this.jobClass = jobClass;
	}
	public String getJobClass(){
		return this.jobClass;
	}
	public void setDateTime(java.util.Date dateTime){
		this.dateTime = dateTime;
	}
	public java.util.Date getDateTime(){
		return this.dateTime;
	}
	public void setDuration(Long duration){
		this.duration = duration;
	}
	public Long getDuration(){
		return this.duration;
	}
	public void setMsg(String msg){
		this.msg = msg;
	}
	public String getMsg(){
		return this.msg;
	}
}
