package com.whli.jee.system.entity;

import com.whli.jee.core.web.entity.BaseEntity;

import java.util.List;

/**
 *	用户-角色关系
 * @author whli
 * @version 1.0
 * @since 1.0
 * */
public class SysUserRole extends BaseEntity implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	private String userId;  //用户编号
	private String roleId;  //角色编号

	//前端传入条件

	private List<String> roleIds;

	public void setUserId(String value){
		this.userId=value;
	}
	public String getUserId(){
		return this.userId;
	}
	public void setRoleId(String value){
		this.roleId=value;
	}
	public String getRoleId(){
		return this.roleId;
	}

	public List<String> getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(List<String> roleIds) {
		this.roleIds = roleIds;
	}
}
