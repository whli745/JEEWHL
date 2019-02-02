package com.whli.jee.system.entity;

import com.whli.jee.core.web.entity.BaseEntity;

import java.util.List;

/**
 * 系统用户
 *
 * @author whli
 * @version 1.0
 * @since 1.0
 */
public class SysUser extends BaseEntity {
    private String officeId;  //归属部门
    private String loginName;  //登录名
    private String password;  //密码
    private String no;  //工号
    private String name;  //姓名
    private String email;  //邮箱
    private String phone;  //电话
    private String photo;  //用户头像
    private String loginIp;  //最后登陆IP
    private java.util.Date loginDate;  //最后登陆时间
    private String remark;  //备注信息
    private Integer enable;  //删除标记
    private String token;

    //前端查询条件
    private List<String> roleIds;

    public void setOfficeId(String value) {
        this.officeId = value;
    }

    public String getOfficeId() {
        return this.officeId;
    }

    public void setLoginName(String value) {
        this.loginName = value;
    }

    public String getLoginName() {
        return this.loginName;
    }

    public void setPassword(String value) {
        this.password = value;
    }

    public String getPassword() {
        return this.password;
    }

    public void setNo(String value) {
        this.no = value;
    }

    public String getNo() {
        return this.no;
    }

    public void setName(String value) {
        this.name = value;
    }

    public String getName() {
        return this.name;
    }

    public void setEmail(String value) {
        this.email = value;
    }

    public String getEmail() {
        return this.email;
    }

    public void setPhone(String value) {
        this.phone = value;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhoto(String value) {
        this.photo = value;
    }

    public String getPhoto() {
        return this.photo;
    }

    public void setLoginIp(String value) {
        this.loginIp = value;
    }

    public String getLoginIp() {
        return this.loginIp;
    }

    public void setLoginDate(java.util.Date value) {
        this.loginDate = value;
    }

    public java.util.Date getLoginDate() {
        return this.loginDate;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<String> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(List<String> roleIds) {
        this.roleIds = roleIds;
    }
}
