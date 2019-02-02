package com.whli.jee.system.entity;


import com.whli.jee.core.web.entity.BaseEntity;

import java.util.List;

/**
 * 系统菜单资源
 *
 * @author whli
 * @version 1.0
 * @since 1.0
 */
public class SysMenu extends BaseEntity{
    private String parentId;  //父级编号
    private String name;  //名称
    private Integer sort;  //排序
    private String href;  //链接
    private String target;  //目标
    private String icon;  //图标
    private String remark;  //备注信息
    private Integer enable;  //删除标记

    //前端查询条件，无关数据库字段
    private List<SysMenu> menus;
    private String roleId;

    public void setParentId(String value) {
        this.parentId = value;
    }

    public String getParentId() {
        return this.parentId;
    }

    public void setName(String value) {
        this.name = value;
    }

    public String getName() {
        return this.name;
    }

    public void setSort(Integer value) {
        this.sort = value;
    }

    public Integer getSort() {
        return this.sort;
    }

    public void setHref(String value) {
        this.href = value;
    }

    public String getHref() {
        return this.href;
    }

    public void setTarget(String value) {
        this.target = value;
    }

    public String getTarget() {
        return this.target;
    }

    public void setIcon(String value) {
        this.icon = value;
    }

    public String getIcon() {
        return this.icon;
    }

    public void setRemark(String value) {
        this.remark = value;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setEnable(Integer value) {
        this.enable = value;
    }

    public Integer getEnable() {
        return this.enable;
    }

    public List<SysMenu> getMenus() {
        return menus;
    }

    public void setMenus(List<SysMenu> menus) {
        this.menus = menus;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
}
