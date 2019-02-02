package com.whli.jee.system.dao;

import com.whli.jee.core.web.dao.IBaseDao;
import com.whli.jee.system.entity.SysRole;
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
public interface ISysRoleDao extends IBaseDao<SysRole> {

    /**
     * 根据用户查询授权角色
     * @param userId
     * @return
     */
    public List<SysRole> findRolesByUserId(@Param("userId") String userId);
}
