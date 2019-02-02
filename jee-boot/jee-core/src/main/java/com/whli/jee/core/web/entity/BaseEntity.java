package com.whli.jee.core.web.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 通用entity类
 * Created by whli on 2018/1/10.
 */
public class BaseEntity implements Serializable {
    //分页相关
    private Integer currentPage;
    private Integer pageSize ;

    private String id;  //编号
    private String createBy;  //创建者
    private Date createDate;  //创建时间
    private String updateBy;  //更新者
    private Date updateDate;  //更新时间
    private Integer version; //版本

    //批量操作ID集合条件
    private List<String> ids;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public List<String> getIds() {
        return ids;
    }

    public void setIds(List<String> ids) {
        this.ids = ids;
    }
}
