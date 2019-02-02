package com.whli.jee.oa.service;

import com.whli.jee.oa.entity.BasProcess;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.List;

/**
 * <p>类或方法描述</p>
 *
 * @author whli
 * @date 2019/1/25 14:09
 */
public interface IBasProcessService {

    /**
     * 分页查询流程定义
     * @return
     */
    public List findByPage(BasProcess entity);

    /**
     * 分页查询流程定义
     * @return
     */
    public List findAll();

    /**
     * 获取所有流程定义的总数
     * @return
     */
    public int getCount(BasProcess entity);

    /**
     * 删除流程定义
     * @param entity
     * @return
     */
    @Transactional
    public int deleteProcess(BasProcess entity);

    /**
     * 导入流程定义
     * @return
     */
    @Transactional
    public int importExcel(String name,String key,InputStream stream);

    /**
     * 查询资源文件
     * @param processDefinitionId
     * @param resourceType
     * @param response
     */
    public void findResourceByDeployment(String processDefinitionId,String resourceType, HttpServletResponse response);

    /**
     * 激活流程定义
     * @param entity
     * @return
     */
    @Transactional
    public int activateProcessDefinition(BasProcess entity);

    /**
     * 挂起流程定义
     * @param entity
     * @return
     */
    @Transactional
    public int suspendProcessDefinition(BasProcess entity);
}
