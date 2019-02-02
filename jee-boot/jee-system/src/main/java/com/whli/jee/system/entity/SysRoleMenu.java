package com.whli.jee.system.entity;

import com.whli.jee.core.web.entity.BaseEntity;

/**
 *	角色-资源关系
 * @author whli
 * @version 1.0
 * @since 1.0
 * */
public class SysRoleMenu extends BaseEntity implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	private String roleId;  //角色编号
	private String menuId;  //菜单编号

	public void setRoleId(String value){
		this.roleId=value;
	}
	public String getRoleId(){
		return this.roleId;
	}
	public void setMenuId(String value){
		this.menuId=value;
	}
	public String getMenuId(){
		return this.menuId;
	}
}
