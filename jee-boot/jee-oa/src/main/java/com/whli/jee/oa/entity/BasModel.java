package com.whli.jee.oa.entity;

import org.activiti.engine.impl.persistence.entity.ModelEntityImpl;

/**
 * <p>类或方法描述</p>
 *
 * @author whli
 * @date 2019/1/16 10:39
 */
public class BasModel extends ModelEntityImpl {
    //分页相关
    private Integer currentPage;
    private Integer pageSize ;

    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
}
