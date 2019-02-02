package com.whli.jee.job.controller;

import com.whli.jee.core.page.Page;
import com.whli.jee.core.web.controller.BaseController;
import com.whli.jee.core.web.entity.ResponseBean;
import com.whli.jee.job.entity.SysJob;
import com.whli.jee.job.service.ISysJobService;
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
    @PostMapping("/findByPage")
    @ApiOperation("分页查询定时任务")
    public ResponseBean findByPage(@RequestBody SysJob entity, HttpServletRequest req) throws Exception {
        ResponseBean responseBean = new ResponseBean();
        if (entity != null) {
            Page<SysJob> page = new Page<SysJob>(entity.getCurrentPage(),entity.getPageSize());
            page = sysJobService.findByPage(entity,page);
            responseBean.setCount(page.getTotal());
            responseBean.setResults(page.getPageRecords());
        }
        return responseBean;
    }

    @PostMapping("/triggerJob")
    @ApiOperation("立即执行定时任务")
    public ResponseBean triggerJob(@RequestBody SysJob entity, HttpServletRequest req) throws Exception {
        ResponseBean responseBean = new ResponseBean();
        Boolean rows = sysJobService.triggerJob(entity);
        if (rows) {
            responseBean.setResults(true);
            responseBean.setMessage("立即执行定时任务成功！");
        }
        return responseBean;
    }

    @Override
    @PostMapping("/add")
    @ApiOperation("增加定时任务")
    public ResponseBean add(@RequestBody SysJob entity, HttpServletRequest req) throws Exception {
        ResponseBean responseBean = new ResponseBean();
        int rows = sysJobService.add(entity);
        if (rows > 0) {
            responseBean.setResults(true);
            responseBean.setMessage("新增定时任务成功！");
        }
        return responseBean;
    }

    @Override
    @PostMapping("/update")
    @ApiOperation("修改定时任务")
    public ResponseBean update(@RequestBody SysJob entity, HttpServletRequest req) throws Exception {
        ResponseBean responseBean = new ResponseBean();
        int rows = sysJobService.update(entity);
        if (rows > 0) {
            responseBean.setResults(true);
            responseBean.setMessage("修改定时任务成功！");
        }
        return responseBean;
    }

    @Override
    @PostMapping("/delete")
    @ApiOperation("删除定时任务")
    public ResponseBean delete(@RequestBody SysJob entity, HttpServletRequest req) throws Exception {
        ResponseBean responseBean = new ResponseBean();
        sysJobService.delete(entity);
        responseBean.setResults(true);
        responseBean.setMessage("删除定时任务成功！");
        return responseBean;
    }

    @ApiIgnore
    @Override
    public SysJob findByPK(@RequestBody SysJob entity, HttpServletRequest req) throws Exception {
        return null;
    }

    @Override
    public List<SysJob> findByPKs(SysJob entity, HttpServletRequest req) throws Exception {
        return null;
    }

    @ApiIgnore
    @Override
    public SysJob findByNo(@RequestBody SysJob entity, HttpServletRequest req) throws Exception {
        return null;
    }

    @ApiIgnore
    @Override
    public SysJob findByName(@RequestBody SysJob entity, HttpServletRequest req) throws Exception {
        return sysJobService.findByName(entity.getJobName());
    }

    @ApiIgnore
    @Override
    public List<SysJob> findAll(@RequestBody SysJob entity, HttpServletRequest req) throws Exception {
        return null;
    }

    @ApiIgnore
    @Override
    public void exportExcel(SysJob entity, HttpServletResponse response) throws Exception {

    }

    @ApiIgnore
    @Override
    public ResponseBean importExcel(@RequestParam(value = "uploadFile",required = false) MultipartFile file) throws Exception {
        return null;
    }

    @ApiIgnore
    @Override
    public void exportTemplate(SysJob entity, HttpServletResponse response) throws Exception {

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
            responseBean.setResults(true);
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
            responseBean.setResults(true);
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
            responseBean.setResults(true);
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
            responseBean.setResults(true);
            responseBean.setMessage("暂停定时任务成功！");
        }
        return responseBean;
    }
}
