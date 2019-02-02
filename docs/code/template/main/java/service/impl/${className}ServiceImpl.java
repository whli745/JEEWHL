<#assign className = table.className>
<#assign classNameLower = className?uncap_first> 
package ${basepackage}.service.impl;

import com.whli.jee.core.web.dao.IBaseDao;
import com.whli.jee.core.web.service.impl.BaseServiceImpl;
import ${basepackage}.dao.I${className}Dao;
import ${basepackage}.entity.${className};
import ${basepackage}.service.I${className}Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 <#include "/java_description.include">
 */
@Service("${classNameLower}Service")
public class ${className}ServiceImpl extends BaseServiceImpl<${className}> implements I${className}Service {

	@Autowired
	private I${className}Dao ${classNameLower}Dao;

	@Override
	public IBaseDao<${className}> getDao() {
		return ${classNameLower}Dao;
	}
}
