package com.whli.jee.system.service.impl;

import com.whli.jee.core.exception.BusinessException;
import com.whli.jee.core.util.BeanUtils;
import com.whli.jee.core.web.dao.IBaseDao;
import com.whli.jee.core.web.service.impl.BaseServiceImpl;
import com.whli.jee.system.dao.ISysAreaDao;
import com.whli.jee.system.entity.SysArea;
import com.whli.jee.system.service.ISysAreaService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author whli
 * @version 1.0
 * @since 1.0
 */
@Service("sysAreaService")
public class SysAreaServiceImpl extends BaseServiceImpl<SysArea> implements ISysAreaService {

    @Autowired
    private ISysAreaDao sysAreaDao;

    @Override
    public IBaseDao<SysArea> getDao() {
        return sysAreaDao;
    }

    @Override
    public SysArea getByParentIdAndSort(SysArea entity) {
        return sysAreaDao.getByParentIdAndSort(entity);
    }

    @Override
    public SysArea getByCode(String code) {
        if (StringUtils.isBlank(code)) {
            throw new BusinessException("请选择需要查询的数据！");
        }
        SysArea entity = sysAreaDao.getByCode(code);
        if (BeanUtils.isNull(entity)) {
            throw new BusinessException("查询的数据不存在或已删除！");
        }
        return entity;
    }
}
