package com.whli.jee.system.service;

import com.whli.jee.core.web.entity.BaseTree;
import com.whli.jee.core.web.service.IBaseService;
import com.whli.jee.system.entity.SysMenu;

import java.util.List;

/**
 * @author whli
 * @version 1.0
 * @since 1.0
 */
public interface ISysMenuService extends IBaseService<SysMenu> {
    /**
     * 根据登录用户查询菜单
     *
     * @param userId
     * @param parentId
     * @return
     */
    List<SysMenu> listMenusByUserIdAndParentId(String userId, String parentId);

    /**
     * 根据登录用户查询按钮
     * @param userId
     * @param parentUrl
     * @return
     */
    List<SysMenu> listButtonsByUserIdAndParentUrl(String userId, String parentUrl);


    /**
     * @Desc 根据角色以树形式展示菜单
     * @Author whli
     * @Version 1.0
     * @Date 2018/6/3 15:19
     * @Params [parentId]
     * @Return
     */
    List<BaseTree> listMenuTreesByRoleId(String roleId);

    /**
     * 查询同级别下是否存在同序号
     * @param entity
     * @return
     */
    SysMenu getByParentIdAndSort(SysMenu entity);
}
