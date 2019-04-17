<#assign className = table.className>
<#assign classNameLower = className?uncap_first>
package ${basepackage}.controller;

import com.whli.jee.core.web.controller.BaseController;
import com.whli.jee.core.web.service.IBaseService;
import ${basepackage}.entity.${className};
import ${basepackage}.service.I${className}Service;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 <#include "/java_description.include">
 */
@RestController
@RequestMapping(value="/${namespace}/${classNameLower}")
@Api(description = "")
public class ${className}Controller extends BaseController<${className}>{

	@Autowired
	private I${className}Service ${classNameLower}Service;
	
	@Override
    public IBaseService<SysUser> getService() {
        return ${classNameLower}Service;
    }
}

