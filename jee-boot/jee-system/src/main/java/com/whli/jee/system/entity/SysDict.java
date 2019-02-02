package com.whli.jee.system.entity;

import com.whli.jee.core.web.entity.BaseEntity;

/**
 * 数据字典
 * @author whli
 * @version 1.0
 * @since 1.0
 * */
public class SysDict extends BaseEntity implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	private String parentId;  //父级编号
	private String value;  //数据值
	private Integer sort;
	private String name;  //标签名
	private String remark;  //备注信息
	private Integer enable;  //删除标记
	private Integer edit; //前端可编辑

	public void setParentId(String value){
		this.parentId=value;
	}
	public String getParentId(){
		return this.parentId;
	}
	public void setValue(String value){
		this.value=value;
	}
	public String getValue(){
		return this.value;
	}
	public void setName(String value){
		this.name=value;
	}
	public String getName(){
		return this.name;
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

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Integer getEdit() {
		return edit;
	}

	public void setEdit(Integer edit) {
		this.edit = edit;
	}
}
