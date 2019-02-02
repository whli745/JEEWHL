package com.whli.jee.system.dao;

import com.whli.jee.core.web.dao.IBaseDao;
import com.whli.jee.system.entity.SysMenu;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * @author whli
 * @version 1.0
 * @since 1.0
 * */
@Repository
public interface ISysMenuDao extends IBaseDao<SysMenu> {
    /**
     * 根据登录用户查询菜单
     * @param userId
     * @param parentId
     * @return
     */
    public List<SysMenu> findMenusByUserId(@Param("userId") String userId, @Param("parentId") String parentId);

    /**
     * 根据登录用户查询按钮
     * @param userId
     * @param parentUrl
     * @return
     */
    public List<SysMenu> findButtonsByParentUrlAndUserId(@Param("userId") String userId, @Param("parentUrl") String parentUrl);

    /**
     * 根据父ID查询菜单
     * @return
     */
    public List<SysMenu> findByParentId(@Param("parentId") String parentId);

    /**
     * 根据父ID及排序查询菜单
     * @param entity
     * @return
     */
    public SysMenu findByParentIdAndSort(@Param("entity") SysMenu entity);
}
