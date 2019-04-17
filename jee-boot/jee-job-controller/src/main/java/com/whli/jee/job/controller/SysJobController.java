package com.whli.jee.job.controller;

import com.whli.jee.core.web.controller.BaseController;
import com.whli.jee.core.web.entity.ResponseBean;
import com.whli.jee.core.web.service.IBaseService;
import com.whli.jee.job.entity.SysJob;
import com.whli.jee.job.service.ISysJobService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>类功能<p>
 *
 * @author whli
 * @version 2018/12/24 14:04
 */
@RestController
@RequestMapping("/scheduler/job")
@Api(description = "定时任务API")
public class SysJobController extends BaseController<SysJob>{
    @Autowired
    private ISysJobService sysJobService;

    @Override
    public IBaseService<SysJob> getService() {
        return sysJobService;
    }

    @PostMapping("/triggerJob")
    @ApiOperation("立即执行定时任务")
    public ResponseBean triggerJob(@RequestBody SysJob entity, HttpServletRequest req) throws Exception {
        ResponseBean responseBean = new ResponseBean();
        Boolean rows = sysJobService.triggerJob(entity);
        if (rows) {
            responseBean.setSucceed(true);
            responseBean.setMessage("立即执行定时任务成功！");
        }
        return responseBean;
    }

    /**
     * 开始任务
     * @param entity
     * @return
     */
    @PostMapping("/resume")
    @ApiOperation("恢复定时任务")
    public ResponseBean resumeJob(@RequestBody SysJob entity, HttpServletRequest req) throws Exception {
        ResponseBean responseBean = new ResponseBean();
        Boolean rows = sysJobService.resumeJob(entity);
        if (rows != null && rows) {
            responseBean.setSucceed(true);
            responseBean.setMessage("恢复定时任务成功！");
        }
        return responseBean;
    }

    /**
     * 开始任务
     * @param entity
     * @return
     */
    @PostMapping("/resumeAll")
    @ApiOperation("恢复所有任务")
    public ResponseBean resumeAllJob(@RequestBody SysJob entity, HttpServletRequest req) throws Exception {
        ResponseBean responseBean = new ResponseBean();
        Boolean rows = sysJobService.resumeAll();
        if (rows != null && rows) {
            responseBean.setSucceed(true);
            responseBean.setMessage("恢复定时任务成功！");
        }
        return responseBean;
    }

    /**
     * 暂停任务
     * @param entity
     * @return
     */
    @PostMapping("/pause")
    @ApiOperation("暂停定时任务")
    public ResponseBean pauseJob(@RequestBody SysJob entity, HttpServletRequest req) throws Exception {
        ResponseBean responseBean = new ResponseBean();
        Boolean rows = sysJobService.pauseJob(entity);
        if (rows != null && rows) {
            responseBean.setSucceed(true);
            responseBean.setMessage("暂停定时任务成功！");
        }
        return responseBean;
    }

    /**
     * 暂停任务
     * @param entity
     * @return
     */
    @PostMapping("/pauseAll")
    @ApiOperation("暂停所有任务")
    public ResponseBean pauseAllTask(@RequestBody SysJob entity, HttpServletRequest req) throws Exception {
        ResponseBean responseBean = new ResponseBean();
        Boolean rows = sysJobService.pauseAll();
        if (rows != null && rows) {
            responseBean.setSucceed(true);
            responseBean.setMessage("暂停定时任务成功！");
        }
        return responseBean;
    }
}
