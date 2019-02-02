package com.whli.jee.system.entity;

import com.whli.jee.core.web.entity.BaseEntity;

import java.util.List;

/**
 * 系统角色
 * @author whli
 * @version 1.0
 * @since 1.0
 * */
public class SysRole extends BaseEntity implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	private String name;  //角色名称
	private String no;  //英文名称
	private String remark;  //备注信息
	private Integer enable;  //是否启用

	//前端查询条件
	private List<String> menuIds;
	private String userId;

	public void setName(String value){
		this.name=value;
	}
	public String getName(){
		return this.name;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
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

	public List<String> getMenuIds() {
		return menuIds;
	}

	public void setMenuIds(List<String> menuIds) {
		this.menuIds = menuIds;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
