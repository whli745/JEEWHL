package com.whli.jee.system.service.impl;

import com.whli.jee.core.exception.BusinessException;
import com.whli.jee.core.util.BeanUtils;
import com.whli.jee.core.web.dao.IBaseDao;
import com.whli.jee.core.web.service.impl.BaseServiceImpl;
import com.whli.jee.system.dao.ISysDictDao;
import com.whli.jee.system.entity.SysDict;
import com.whli.jee.system.service.ISysDictService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author whli
 * @version 1.0
 * @since 1.0
 */
@Service("sysDictService")
public class SysDictServiceImpl extends BaseServiceImpl<SysDict> implements ISysDictService {

    @Autowired
    private ISysDictDao sysDictDao;

    @Override
    public IBaseDao<SysDict> getDao() {
        return sysDictDao;
    }

    @Override
    public List<SysDict> listByParentValue(String value) {
        if (StringUtils.isBlank(value)) {
            throw new BusinessException("-02060201", "父字典值不能为空！");
        }
        return sysDictDao.listByParentValue(value);
    }

    @Override
    public SysDict getByParentIdAndSort(SysDict entity) {
        return sysDictDao.getByParentIdAndSort(entity);
    }

    @Override
    public SysDict getByValue(String value) {
        if (StringUtils.isBlank(value)) {
            throw new BusinessException("请选择需要查询的数据！");
        }
        SysDict entity = sysDictDao.getByValue(value);
        if (BeanUtils.isNull(entity)) {
            throw new BusinessException("查询的数据不存在或已删除！");
        }
        return entity;
    }
}