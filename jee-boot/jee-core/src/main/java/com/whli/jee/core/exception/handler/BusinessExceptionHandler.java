package com.whli.jee.core.exception.handler;

import com.whli.jee.core.exception.BusinessException;
import com.whli.jee.core.web.entity.ResponseBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by whli on 2018/1/18.
 */
@ControllerAdvice
public class BusinessExceptionHandler {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResponseBean defaultErrorHandler(Exception e, HttpServletRequest req){
        ResponseBean responseBean = new ResponseBean();

        if(e instanceof DataIntegrityViolationException){ //数据库异常
            String msg = e.getMessage();
            if(msg.indexOf("Cannot delete or update a parent row: a foreign key constraint fails") > 0){
                responseBean.setCode("-10001");
                responseBean.setMessage("数据被引用，不能删除！");
            } else {
                responseBean.setCode("-10002");
                responseBean.setMessage("数据库异常！！");
            }

        }else if (e instanceof BusinessException) {  //自定义异常
            BusinessException applicationException = (BusinessException)e;
            //返回前台
            responseBean.setCode(applicationException.getErrorCode());
            responseBean.setMessage(applicationException.getMessage());

        } else { //其它异常
            //返回前台
            responseBean.setCode("-10000");
            responseBean.setMessage("应用错误，请联系管理员！"+e.getMessage());
        }

        logger.error("com.whli.jee.core.exception.handler.BusinessExceptionHandler.defaultErrorHandler bug:{}",e);
        return responseBean;
    }
}
