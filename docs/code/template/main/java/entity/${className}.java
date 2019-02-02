<#assign className = table.className>
<#assign classNameLower = className?uncap_first> 
package ${basepackage}.entity;

import com.whli.jee.core.web.entity.BaseEntity;
import java.util.List;

/**
 <#include "/java_description.include">
 */
public class ${className} extends BaseEntity implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	<#list table.columns as column>
		<#if !column.pk && column.columnNameLower != 'createBy'
		&& column.columnNameLower != 'createDate' && column.columnNameLower != 'updateBy'
		&& column.columnNameLower != 'updateDate' && column.columnNameLower != 'version'>
	private ${column.javaType} ${column.columnNameLower};  //${column.columnAlias!}
		</#if>
	</#list>

	<#list table.columns as column>
		<#if !column.pk && column.columnNameLower != 'createBy'
				&& column.columnNameLower != 'createDate' && column.columnNameLower != 'updateBy'
				&& column.columnNameLower != 'updateDate' && column.columnNameLower != 'version'>
	public void set${column.columnName}(${column.javaType} ${column.columnNameLower}){
		this.${column.columnNameLower} = ${column.columnNameLower};
	}
	public ${column.javaType} get${column.columnName}(){
		return this.${column.columnNameLower};
	}
		</#if>
	</#list>
}
