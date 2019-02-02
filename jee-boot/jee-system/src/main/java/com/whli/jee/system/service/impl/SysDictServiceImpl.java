package com.whli.jee.system.service.impl;

import com.whli.jee.core.exception.BusinessException;
import com.whli.jee.core.util.StringUtils;
import com.whli.jee.core.web.dao.IBaseDao;
import com.whli.jee.core.web.service.impl.BaseServiceImpl;
import com.whli.jee.system.dao.ISysDictDao;
import com.whli.jee.system.entity.SysDict;
import com.whli.jee.system.service.ISysDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 * @author whli
 * @version 1.0
 * @since 1.0
 * */
@Service("sysDictService")
public class SysDictServiceImpl extends BaseServiceImpl<SysDict> implements ISysDictService {

	@Autowired
	private ISysDictDao sysDictDao;

	@Override
	public IBaseDao<SysDict> getDao() {
		return sysDictDao;
	}

	public List<SysDict> findByParentValue(String value) {
		if(StringUtils.isNullOrBlank(value)){
			throw  new BusinessException("-02060201","父字典值不能为空！");
		}
		return sysDictDao.findByParentValue(value);
	}

	@Override
	public SysDict findByParentIdAndSort(SysDict entity) {
		return sysDictDao.findByParentIdAndSort(entity);
	}
}