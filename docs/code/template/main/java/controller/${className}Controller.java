<#assign className = table.className>
<#assign classNameLower = className?uncap_first>
package ${basepackage}.controller;

import com.whli.jee.core.page.Page;
import com.whli.jee.core.web.entity.ResponseBean;
import com.whli.jee.core.web.controller.BaseController;
import ${basepackage}.entity.${className};
import ${basepackage}.service.I${className}Service;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 <#include "/java_description.include">
 */
@RestController
@RequestMapping(value="/${namespace}/${classNameLower}")
@Api(description = "")
public class ${className}Controller extends BaseController<${className}>{

	@Autowired
	private I${className}Service ${classNameLower}Service;

    /**
     * 分页查询
     * @param entity
	 * @param req
     * @return
	 * @throws Exception
     */
    @PostMapping(value = "/findByPage")
	@ApiOperation("")
	@Override
    public ResponseBean findByPage(@RequestBody ${className} entity, HttpServletRequest req) throws Exception {
        ResponseBean responseBean = new ResponseBean();
        if (entity != null) {
            Page<${className}> page = new Page<${className}>(entity.getCurrentPage(),entity.getPageSize());
            page = ${classNameLower}Service.findByPage(entity, page);
            responseBean.setCount(page.getTotal());
            responseBean.setResults(page.getPageRecords());
        }
        return responseBean;
    }

    /**
     * 添加entity
     * @param entity
	 * @param req
     * @return
	 * @throws Exception
     */
    @PostMapping(value = "/add")
	@ApiOperation("")
	@Override
    public ResponseBean add(@RequestBody ${className} entity, HttpServletRequest req) throws Exception {
        ResponseBean responseBean = new ResponseBean();
        int rows = ${classNameLower}Service.add(entity);
        if (rows > 0) {
            responseBean.setResults(true);
            responseBean.setMessage("");
        }
        return responseBean;
    }

    /**
     * 更新entity
     * @param entity
	 * @param req
     * @return
	 * @throws Exception
     */
    @PostMapping(value = "/update")
	@ApiOperation("")
	@Override
    public ResponseBean update(@RequestBody ${className} entity, HttpServletRequest req) throws Exception {
        ResponseBean responseBean = new ResponseBean();
        int rows = ${classNameLower}Service.update(entity);
        if (rows > 0) {
            responseBean.setResults(true);
            responseBean.setMessage("");
        }
        return responseBean;
    }

    /**
     * 删除entity
     * @param entity
	 * @param req
     * @return
	 * @throws Exception
     */
    @PostMapping(value = "/delete")
	@ApiOperation("")
	@Override
    public ResponseBean delete(@RequestBody ${className} entity, HttpServletRequest req)
        throws Exception {
        ResponseBean responseBean = new ResponseBean();
        ${classNameLower}Service.deleteMore(entity);
        responseBean.setResults(true);
        responseBean.setMessage( "");
        return responseBean;
    }

	/**
	 * 根据主键查询entity
	 * @param entity
	 * @param req
	 * @return
	 * @throws Exception
	 */
    @PostMapping(value = "/findByPK")
	@ApiOperation("")
	@Override
	public ${className} findByPK(@RequestBody ${className} entity, HttpServletRequest req) throws Exception{
		return ${classNameLower}Service.findByPK(entity.getId());
	}
	
	/**
     * 根据编码查询
     * @param entity
     * @param req
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/findByNo")
	@ApiOperation("")
    @Override
    public ${className} findByNo(@RequestBody ${className} entity, HttpServletRequest req) throws Exception {
        return null;
    }

    /**
     * 根据名称查询
     * @param entity
     * @param req
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/findByName")
	@ApiOperation("")
    @Override
    public ${className} findByName(@RequestBody ${className} entity, HttpServletRequest req) throws Exception {
        return null;
    }

    /**
     * 查询所有数据
     *
     * @param entity
	 * @param req
     * @return
	 * @throws Exception
     */
    @PostMapping(value = "/findAll")
	@ApiOperation("")
	@Override
    public List<${className}> findAll(@RequestBody ${className} entity, HttpServletRequest req) throws Exception {
        return ${classNameLower}Service.findAll(entity);
    }
	
	@ApiIgnore
    @Override
    public void exportExcel(SysRole entity, HttpServletResponse response) throws Exception {

    }

    @ApiIgnore
    @Override
    public ResponseBean importExcel(@RequestParam(value = "uploadFile", required = false) MultipartFile file) throws Exception {
        return null;
    }

    @ApiIgnore
    @Override
    public void exportTemplate(SysRole entity, HttpServletResponse response) throws Exception {

    }
}

