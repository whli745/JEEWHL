package com.whli.jee.system.service.impl;

import com.whli.jee.core.web.dao.IBaseDao;
import com.whli.jee.core.web.entity.BaseTree;
import com.whli.jee.core.web.service.impl.BaseServiceImpl;
import com.whli.jee.system.dao.ISysMenuDao;
import com.whli.jee.system.dao.ISysRoleMenuDao;
import com.whli.jee.system.entity.SysMenu;
import com.whli.jee.system.entity.SysRoleMenu;
import com.whli.jee.system.service.ISysMenuService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author whli
 * @version 1.0
 * @since 1.0
 */
@Service("sysMenuService")
public class SysMenuServiceImpl extends BaseServiceImpl<SysMenu> implements ISysMenuService {

    @Autowired
    private ISysMenuDao sysMenuDao;
    @Autowired
    private ISysRoleMenuDao sysRoleMenuDao;

    @Override
    public IBaseDao<SysMenu> getDao() {
        return sysMenuDao;
    }

    @Override
    public int save(SysMenu entity) {
        int rows = super.save(entity);
        if ("TAB".equals(entity.getTarget())){
            List<SysMenu> buttons = new ArrayList<SysMenu>();
            //新增按钮
            SysMenu addBtn = new SysMenu();
            addBtn.setParentId(entity.getId());
            addBtn.setEnable(1);
            addBtn.setHref("btn_add");
            addBtn.setSort(1);
            addBtn.setName("新增");
            addBtn.setTarget("BUTTON");
            buttons.add(addBtn);
            //修改按钮
            SysMenu updateBtn = new SysMenu();
            updateBtn.setParentId(entity.getId());
            updateBtn.setEnable(1);
            updateBtn.setHref("btn_edit");
            updateBtn.setSort(2);
            updateBtn.setName("编辑");
            updateBtn.setTarget("BUTTON");
            buttons.add(updateBtn);
            //删除按钮
            SysMenu delBtn = new SysMenu();
            delBtn.setParentId(entity.getId());
            delBtn.setEnable(1);
            delBtn.setHref("btn_delete");
            delBtn.setSort(3);
            delBtn.setName("删除");
            delBtn.setTarget("BUTTON");
            buttons.add(delBtn);
            super.saveMore(buttons);
        }

        return rows;
    }

    @Override
    public void delete(SysMenu entity) {
        super.delete(entity);
        List<SysMenu> menus = sysMenuDao.listByParentId(entity.getId());
        if (CollectionUtils.isNotEmpty(menus)){
            for (SysMenu menu : menus){
                delete(menu);
            }
        }
    }

    @Override
    public void deleteMore(SysMenu entity) {
        List<SysMenu> menus = sysMenuDao.listByPKs(entity.getIds());
        if (CollectionUtils.isNotEmpty(menus)){
            for (SysMenu menu : menus){
                delete(menu);
            }
        }
    }

    /**
     * 根据登录用户查询菜单
     *
     * @param userId
     * @param parentId
     * @return
     */
    @Override
    public List<SysMenu> listMenusByUserIdAndParentId(String userId, String parentId) {
        List<SysMenu> menus = null;
        if (StringUtils.isNotBlank(userId)) {
            menus = sysMenuDao.listMenusByUserIdAndParentId(userId, parentId);
            addChild(userId, menus);
        }
        return menus;
    }

    private void addChild(String userId, List<SysMenu> parents) {
        for (int i = 0, m = parents.size(); i < m; i++) {
            List<SysMenu> childrens = sysMenuDao.listMenusByUserIdAndParentId(userId, parents.get(i).getId());
            if (CollectionUtils.isNotEmpty(childrens)) {
                parents.get(i).setMenus(childrens);
                addChild(userId, childrens);
            }
        }
    }

    /**
     * 查询登录用户的按钮
     * @param userId
     * @param parentUrl
     * @return
     */
    @Override
    public List<SysMenu> listButtonsByUserIdAndParentUrl(String userId, String parentUrl) {
        List<SysMenu> menus = null;
        if (StringUtils.isNotBlank(userId)) {
            menus = sysMenuDao.listButtonsByUserIdAndParentUrl(userId, parentUrl);
        }
        return menus;
    }

    /**
     * 以树结构展示菜单
     * @param roleId
     * @return
     */
    @Override
    public List<BaseTree> listMenuTreesByRoleId(String roleId) {
        List<BaseTree> trees = new ArrayList<BaseTree>();

        List<SysMenu> sysMenus = sysMenuDao.listByParentId("");
        if (CollectionUtils.isNotEmpty(sysMenus)) {
            for (SysMenu sysMenu : sysMenus) {
                BaseTree tree = new BaseTree();
                tree.setId(sysMenu.getId());
                tree.setPid(sysMenu.getParentId());
                tree.setName(sysMenu.getName());
                trees.add(tree);
                addChildrenNode(tree,roleId);
            }
        }
        return trees;
    }

    /**
     * 解析父菜单的子项
     * @param parent
     * @param roleId
     */
    private void addChildrenNode(BaseTree parent,String roleId){
        parent.setOpen(true);
        if(StringUtils.isNotBlank(roleId)){
            SysRoleMenu roleMenu = sysRoleMenuDao.getByRoleIdAndMenuId(roleId,parent.getId());
            if (roleMenu != null){
                parent.setChecked(true);
            }
        }

        List<SysMenu> childMenus = sysMenuDao.listByParentId(parent.getId());
        if (CollectionUtils.isNotEmpty(childMenus)) {
            parent.setIsParent(true);
            for (SysMenu childMenu : childMenus){
                BaseTree childTree = new BaseTree();
                childTree.setId(childMenu.getId());
                childTree.setPid(childMenu.getParentId());
                childTree.setName(childMenu.getName());
                addChildrenNode(childTree,  roleId);
                parent.getChildren().add(childTree);
            }
        }
    }

    /**
     * 查询同级别下是否存在相同序号
     * @param entity
     * @return
     */
    @Override
    public SysMenu getByParentIdAndSort(SysMenu entity){
        return sysMenuDao.getByParentIdAndSort(entity);
    }
}
