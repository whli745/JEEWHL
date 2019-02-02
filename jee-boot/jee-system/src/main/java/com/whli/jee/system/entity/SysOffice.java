package com.whli.jee.system.entity;

import com.whli.jee.core.web.entity.BaseEntity;

import java.util.List;

/**
 *	部门或组织
 * @author whli
 * @version 1.0
 * @since 1.0
 * */
public class SysOffice extends BaseEntity implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	private String parentId;  //父级编号
	private String name;  //名称
	private Long sort;  //排序
	private String type;  //机构类型
	private String master;  //负责人
	private String phone;  //电话
	private String fax;  //传真
	private String email;  //邮箱
	private String deputyPerson;  //副负责人
	private String remark;  //备注信息
	private Integer enable;  //删除标记

	public void setParentId(String value){
		this.parentId=value;
	}
	public String getParentId(){
		return this.parentId;
	}
	public void setName(String value){
		this.name=value;
	}
	public String getName(){
		return this.name;
	}
	public void setSort(Long value){
		this.sort=value;
	}
	public Long getSort(){
		return this.sort;
	}
	public void setType(String value){
		this.type=value;
	}
	public String getType(){
		return this.type;
	}
	public void setMaster(String value){
		this.master=value;
	}
	public String getMaster(){
		return this.master;
	}
	public void setPhone(String value){
		this.phone=value;
	}
	public String getPhone(){
		return this.phone;
	}
	public void setFax(String value){
		this.fax=value;
	}
	public String getFax(){
		return this.fax;
	}
	public void setEmail(String value){
		this.email=value;
	}
	public String getEmail(){
		return this.email;
	}
	public void setDeputyPerson(String value){
		this.deputyPerson=value;
	}
	public String getDeputyPerson(){
		return this.deputyPerson;
	}
	public void setRemark(String value){
		this.remark=value;
	}
	public String getRemark(){
		return this.remark;
	}
	public void setEnable(Integer value){
		this.enable=value;
	}
	public Integer getEnable(){
		return this.enable;
	}
}
