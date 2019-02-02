package com.whli.jee.core.web.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by whli on 2017/12/8.
 */
public class BaseTree implements Serializable{
	private String id;  	//菜单id
    private String pid;	//菜单父id
    private String name;	//菜单名
    private String url;		//菜单连接url
    private boolean isParent; //此菜单是否为父菜单
    private boolean checked;  //是否选中
    private boolean open; //是否展开
    private List<BaseTree> children = new ArrayList<BaseTree>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean getIsParent() {
        return isParent;
    }

    public void setIsParent(boolean isParent) {
        this.isParent = isParent;
    }

    public boolean getChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public Boolean getOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public List<BaseTree> getChildren() {
        return children;
    }

    public void setChildren(List<BaseTree> children) {
        this.children = children;
    }
}