package com.whli.jee.system.entity;

import com.whli.jee.core.web.entity.BaseEntity;

import java.util.List;

/**
 *	城市或区域
 * @author whli
 * @version 1.0
 * @since 1.0
 * */
public class SysArea extends BaseEntity implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	private String parentId;  //父级编号
	private String name;  //名称
	private Long sort;  //排序
	private String code;  //区域编码
	private String type;  //区域类型
	private String remark;  //备注信息
	private Integer enable;  //是否启用

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
	public void setCode(String value){
		this.code=value;
	}
	public String getCode(){
		return this.code;
	}
	public void setType(String value){
		this.type=value;
	}
	public String getType(){
		return this.type;
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
