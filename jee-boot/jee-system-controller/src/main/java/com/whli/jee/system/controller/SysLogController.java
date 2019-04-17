package com.whli.jee.system.controller;

import com.whli.jee.core.web.controller.BaseController;
import com.whli.jee.core.web.service.IBaseService;
import com.whli.jee.system.entity.SysLog;
import com.whli.jee.system.service.ISysLogService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author whli
 * @version 1.0
 * @since 1.0
 */
@RestController
@RequestMapping(value = "/system/sysLog")
@Api(description = "系统日志API")
public class SysLogController extends BaseController<SysLog> {

    @Autowired
    private ISysLogService sysLogService;

    @Override
    public IBaseService<SysLog> getService() {
        return sysLogService;
    }
}

