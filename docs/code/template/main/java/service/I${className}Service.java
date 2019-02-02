<#assign className = table.className>
<#assign classNameLower = className?uncap_first> 
package ${basepackage}.service;

import com.whli.jee.core.web.service.IBaseService;
import ${basepackage}.entity.${className};

/**
 <#include "/java_description.include">
 */
public interface I${className}Service extends IBaseService<${className}>{
	
}
