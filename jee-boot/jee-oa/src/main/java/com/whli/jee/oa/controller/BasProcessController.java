package com.whli.jee.oa.controller;

import com.whli.jee.core.web.entity.ResponseBean;
import com.whli.jee.oa.entity.BasProcess;
import com.whli.jee.oa.service.IBasProcessService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>流程定义控制层</p>
 * @author whli
 * @date 2019/1/25 15:33
 */
@RestController
@RequestMapping(value = "/activiti/basProcess")
public class BasProcessController {

    @Autowired
    private IBasProcessService processService;

    /**
     * 分页查询流程模型
     * @return
     * @throws Exception
     */
    @PostMapping("/findByPage")
    public ResponseBean findByPage(@RequestBody BasProcess entity) throws Exception{
        ResponseBean responseBean = new ResponseBean();
        int count = processService.getCount(entity);
        List processes = processService.findByPage(entity);
        if (processes != null && processes.size() > 0){
            responseBean.setCount(count);
            responseBean.setResults(processes);
        }
        return responseBean;
    }

    /**
     * 查询所有流程模型
     * @return
     * @throws Exception
     */
    @PostMapping("/findAll")
    public ResponseBean findAll() throws Exception{
        ResponseBean responseBean = new ResponseBean();
        List<BasProcess> processes = processService.findAll();
        if (processes != null && processes.size() > 0){
            responseBean.setResults(processes);
        }
        return responseBean;
    }

    /**
     * 删除流程定义
     * @param entity
     * @return
     */
    @PostMapping("/deleteProcess")
    public ResponseBean deleteProcess(@RequestBody BasProcess entity) throws Exception{
        ResponseBean responseBean = new ResponseBean();
        int rows = processService.deleteProcess(entity);
        if (rows > 0){
            responseBean.setResults(true);
            responseBean.setMessage("删除流程定义成功！");
        }
        return responseBean;
    }

    @PostMapping(value = "/importProcess")
    @ApiOperation("使用ZIP部署流程")
    public ResponseBean importProcess(@RequestParam(value = "name", required = false) String name,
                                      @RequestParam(value = "key", required = false) String key,
                                      @RequestParam(value = "uploadFile", required = false) MultipartFile file) throws Exception {
        ResponseBean responseBean = new ResponseBean();

        if (StringUtils.isNotBlank(name) && StringUtils.isNotBlank(key) && file != null){
            int rows = processService.importExcel(name,key,file.getInputStream());
            if (rows > 0) {
                responseBean.setResults(true);
                responseBean.setMessage("导入流程定义成功！");
            }
        }
        return responseBean;
    }

    @GetMapping(value = "/findResource")
    public void findResourceByDeployment(@RequestParam("processDefinitionId") String processDefinitionId,
                                         @RequestParam("resourceType") String resourceType,
                                         HttpServletResponse response){
        processService.findResourceByDeployment(processDefinitionId,resourceType,response);
    }

    /**
     * 激活流程定义
     * @param entity
     * @return
     */
    @PostMapping(value = "/activate")
    public ResponseBean activateProcessDefinition(@RequestBody BasProcess entity) throws Exception{
        ResponseBean responseBean = new ResponseBean();
        int rows = processService.activateProcessDefinition(entity);
        if (rows > 0){
            responseBean.setResults(true);
            responseBean.setMessage("激活流程成功！");
        }
        return responseBean;
    }

    /**
     * 挂起流程定义
     * @param entity
     * @return
     */
    @PostMapping(value = "/suspend")
    public ResponseBean suspendProcessDefinition(@RequestBody BasProcess entity) throws Exception{
        ResponseBean responseBean = new ResponseBean();
        int rows = processService.suspendProcessDefinition(entity);
        if (rows > 0){
            responseBean.setResults(true);
            responseBean.setMessage("挂起流程成功！");
        }
        return responseBean;
    }
}
