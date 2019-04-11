package com.whli.jee.oa.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.whli.jee.core.exception.BusinessException;
import com.whli.jee.oa.entity.BasModel;
import com.whli.jee.oa.service.IBasModelService;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ModelQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

/**
 * <p>模型管理</p>
 * @author whli
 * @date 2019/1/16 10:42
 */
@Service(value = "basModelService")
public class BasModelServiceImpl implements IBasModelService {

    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public List findByPage(BasModel entity){
        int firstResult = (entity.getCurrentPage()-1)*entity.getPageSize();
        int maxResult = entity.getPageSize();
        ModelQuery query = repositoryService.createModelQuery();
        if (StringUtils.isNotEmpty(entity.getKey()) && StringUtils.isNotEmpty(entity.getName())){
            return query.modelKey(entity.getKey())
                    .modelNameLike(entity.getName())
                    .orderByCreateTime().desc()
                    .listPage(firstResult,maxResult);
        }else if (StringUtils.isNotEmpty(entity.getKey())){
            return query.modelKey(entity.getKey())
                    .orderByCreateTime().desc()
                    .listPage(firstResult,maxResult);
        }else if (StringUtils.isNotEmpty(entity.getName())){
            return query.modelNameLike(entity.getName())
                    .orderByCreateTime().desc()
                    .listPage(firstResult,maxResult);
        }
        return query.orderByCreateTime().desc().listPage(firstResult,maxResult);
    }

    @Override
    public List findAll() {
        return repositoryService.createModelQuery().orderByCreateTime().desc().list();
    }

    @Override
    public int getCount(BasModel entity) {
        ModelQuery query = repositoryService.createModelQuery();
        if (StringUtils.isNotEmpty(entity.getKey()) && StringUtils.isNotEmpty(entity.getName())){

            return new Long(query.modelKey(entity.getKey()).modelNameLike(entity.getName()).count()).intValue();
        }else if (StringUtils.isNotEmpty(entity.getKey())){
            return new Long(query.modelKey(entity.getKey()).count()).intValue();
        }else if (StringUtils.isNotEmpty(entity.getName())){
            return new Long(query.modelName(entity.getName()).count()).intValue();
        }
        return new Long(query.count()).intValue();
    }

    @Override
    public String addModel(BasModel entity) {
        try {
            //初始化一个空模型
            Model model = repositoryService.newModel();

            ObjectNode modelNode = objectMapper.createObjectNode();
            modelNode.put(ModelDataJsonConstants.MODEL_NAME, entity.getName());
            modelNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, entity.getDescription());
            modelNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);

            model.setName(entity.getDescription());
            model.setKey(entity.getKey());
            model.setMetaInfo(modelNode.toString());

            repositoryService.saveModel(model);
            String id = model.getId();

            //完善ModelEditorSource
            ObjectNode editorNode = objectMapper.createObjectNode();
            editorNode.put("id", "canvas");
            editorNode.put("resourceId", "canvas");
            ObjectNode stencilSetNode = objectMapper.createObjectNode();
            stencilSetNode.put("namespace",
                    "http://b3mn.org/stencilset/bpmn2.0#");
            editorNode.set("stencilset", stencilSetNode);
            repositoryService.addModelEditorSource(id,editorNode.toString().getBytes("utf-8"));
            return id;
        } catch (Exception e) {
            throw new BusinessException("创建流程模型失败：",e);
        }
    }

    @Override
    public int deleteModel(BasModel entity) {
        try {
            repositoryService.deleteModel(entity.getId());
            return  1;
        } catch (Exception e) {
            throw new RuntimeException("删除模型失败："+e.getMessage());
        }
    }

    @Override
    public int deployModel(BasModel entity) {
        try {
            //获取模型
            Model modelData = repositoryService.getModel(entity.getId());
            byte[] bytes = repositoryService.getModelEditorSource(modelData.getId());

            if (bytes == null) {
                throw new BusinessException("模型数据为空，请先设计流程并成功保存，再进行发布。");
            }

            JsonNode modelNode = new ObjectMapper().readTree(bytes);

            BpmnModel bpmnModel = new BpmnJsonConverter().convertToBpmnModel(modelNode);
            if(bpmnModel.getProcesses().size()==0){
                throw new BusinessException("数据模型不符要求，请至少设计一条主线流程。");
            }
            byte[] bpmnBytes = new BpmnXMLConverter().convertToXML(bpmnModel);

            ByteArrayInputStream in = new ByteArrayInputStream(bpmnBytes);

            //发布流程
            String processName = modelData.getName();
            if (!StringUtils.endsWith(processName, ".bpmn20.xml")){
                processName += ".bpmn20.xml";
            }

            Deployment deployment = repositoryService.createDeployment()
                    .name(modelData.getName())
                    .addInputStream(processName, in)
                    .deploy();
            modelData.setDeploymentId(deployment.getId());
            repositoryService.saveModel(modelData);
            return 1;
        } catch (IOException e) {
            throw new BusinessException("发布流程模型失败：",e);
        }
    }
}
