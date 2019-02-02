package com.whli.jee.oa.service.impl;

import com.whli.jee.core.exception.BusinessException;
import com.whli.jee.core.util.CollectionUtils;
import com.whli.jee.oa.entity.BasProcess;
import com.whli.jee.oa.service.IBasProcessService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipInputStream;

/**
 * <p>流程管理</p>
 * @author whli
 * @date 2019/1/25 14:09
 */
@Service(value = "basProcessService")
public class BasProcessServiceImpl implements IBasProcessService {

    @Autowired
    private RepositoryService repositoryService;

    @Override
    public List findByPage(BasProcess entity) {
        List<ProcessDefinition> processes = new ArrayList<ProcessDefinition>();
        List processList = new ArrayList();

        int firstResult = (entity.getCurrentPage()-1)*entity.getPageSize();
        int maxResult = entity.getPageSize();
        ProcessDefinitionQuery query = repositoryService.createProcessDefinitionQuery();
        if (StringUtils.isNotEmpty(entity.getKey()) && StringUtils.isNotEmpty(entity.getName())){
            processes = query.processDefinitionKeyLike(entity.getKey())
                    .processDefinitionNameLike(entity.getName())
                    .orderByProcessDefinitionVersion().desc()
                    .latestVersion().listPage(firstResult,maxResult);
        }else if (StringUtils.isNotEmpty(entity.getKey())){
            processes = query.processDefinitionKeyLike(entity.getKey())
                    .orderByProcessDefinitionVersion().desc()
                    .latestVersion().listPage(firstResult,maxResult);
        }else if (StringUtils.isNotEmpty(entity.getName())){
            processes = query.processDefinitionNameLike(entity.getName())
                    .orderByProcessDefinitionVersion().desc()
                    .latestVersion().listPage(firstResult,maxResult);
        }

        processes = query.latestVersion().listPage(firstResult,maxResult);
        return transToBasProcess(processes);
    }

    @Override
    public List findAll() {
        List<ProcessDefinition> processes = repositoryService.createProcessDefinitionQuery()
                .latestVersion().list();
        return transToBasProcess(processes);
    }

    @Override
    public int getCount(BasProcess entity) {
        ProcessDefinitionQuery query = repositoryService.createProcessDefinitionQuery();
        if (StringUtils.isNotEmpty(entity.getKey()) && StringUtils.isNotEmpty(entity.getName())){
            return new Long(query.processDefinitionKeyLike(entity.getKey())
                    .processDefinitionNameLike(entity.getName())
                    .latestVersion().count()).intValue();
        }else if (StringUtils.isNotEmpty(entity.getKey())){
            return new Long(query.processDefinitionKeyLike(entity.getKey())
                    .latestVersion().count()).intValue();
        }else if (StringUtils.isNotEmpty(entity.getName())){
            return new Long(query.processDefinitionNameLike(entity.getName()).count()).intValue();
        }
        return new Long(query.latestVersion().count()).intValue();
    }

    @Override
    public int deleteProcess(BasProcess entity) {
        int rows = 0;
        // 先使用流程定义的key查询流程定义，查询出所有的版本
        List<ProcessDefinition> processDefinitions = repositoryService.createProcessDefinitionQuery()
                                        .processDefinitionKey(entity.getKey())
                                        .list();// 使用流程定义的key查询
        if (CollectionUtils.isNotNullOrEmpty(processDefinitions)){
            for (ProcessDefinition processDefinition : processDefinitions){
                repositoryService.deleteDeployment(processDefinition.getDeploymentId(),true);
                rows +=1;
            }
        }
        return rows;
    }

    @Override
    public int importExcel(String name,String key,InputStream stream) {
        ZipInputStream zipInputStream = new ZipInputStream(stream);
        Deployment deployment = repositoryService.createDeployment()
                                                .name(name)
                                                .addZipInputStream(zipInputStream)
                                                .deploy();
        if (deployment != null){
            return 1;
        }

        return 0;
    }

    @Override
    public void findResourceByDeployment(String processDefinitionId, String resourceType, HttpServletResponse response) {
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .processDefinitionId(processDefinitionId).singleResult();

        String resourceName = "";
        if (resourceType.equals("image")) {
            resourceName = processDefinition.getDiagramResourceName();
        } else if (resourceType.equals("xml")) {
            resourceName = processDefinition.getResourceName();
        }
        InputStream resourceAsStream = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(),
                resourceName);
        byte[] b = new byte[1024];
        int len = -1;
        try {
            while ((len = resourceAsStream.read(b, 0, 1024)) != -1) {
                response.getOutputStream().write(b, 0, len);
            }
        } catch (IOException e) {
            throw new BusinessException("查询资源文件失败：",e);
        }
    }

    @Override
    public int activateProcessDefinition(BasProcess entity) {
        if (entity == null || com.whli.jee.core.util.StringUtils.isNullOrBlank(entity.getId())){
            throw new BusinessException("请选择需要激活的流程！");
        }
        repositoryService.activateProcessDefinitionById(entity.getId(),true,null);
        return 1;
    }

    @Override
    public int suspendProcessDefinition(BasProcess entity) {
        if (entity == null || com.whli.jee.core.util.StringUtils.isNullOrBlank(entity.getId())){
            throw new BusinessException("请选择需要挂起的流程！");
        }
        repositoryService.suspendProcessDefinitionById(entity.getId(),true,null);
        return 1;
    }

    private List transToBasProcess(List<ProcessDefinition> processes){
        List processList = new ArrayList();
        for (ProcessDefinition pro : processes) {
            BasProcess processModel = new BasProcess();
            processModel.setDeploymentId(pro.getDeploymentId());
            processModel.setId(pro.getId());
            processModel.setKey(pro.getKey());
            processModel.setResourceName(pro.getResourceName());
            processModel.setVersion(pro.getVersion());
            processModel.setName(pro.getName());
            processModel.setDiagramResourceName(pro
                    .getDiagramResourceName());
            processList.add(processModel);
        }
        return processList;
    }
}
