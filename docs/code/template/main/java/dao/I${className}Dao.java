<#assign className = table.className>
<#assign classNameLower = className?uncap_first>   
package ${basepackage}.dao;

import com.whli.jee.core.web.dao.IBaseDao;
import ${basepackage}.entity.${className};
import org.springframework.stereotype.Repository;

/**
<#include "/java_description.include">
 */
@Repository
public interface I${className}Dao extends IBaseDao<${className}>{

}
