package com.hrtp.salesAppService.controller;

import com.hrtp.salesAppService.entity.appEntity.RespEntity;
import com.hrtp.system.costant.ReturnCode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

/**
 * AdviceController
 * description 全局处理
 * create by lxj 2018/8/20
 **/
@ControllerAdvice
public class AdviceController {
	private static  Logger logger = LogManager.getLogger(AdviceController.class);

    @ResponseBody
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public RespEntity maxFileExceptionHandler(MaxUploadSizeExceededException ex){
        logger.error(ex);
        return new RespEntity(ReturnCode.FALT,"单文件（除视频）上传不能超过512Kb，总文件不能超过20Mb");
    }


    @ResponseBody
    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    public RespEntity httpMediaTypeNotAcceptableException(HttpMediaTypeNotAcceptableException ex){
        logger.error(ex);
        return new RespEntity(ReturnCode.FALT,"访问URL资源错误");
    }
    /**
     * 全局异常捕捉处理
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public RespEntity exceptionHandler(Exception ex){
    	logger.error(ex);
        return new RespEntity(ReturnCode.FALT,"系统处理失败,请稍后重试");
    }
}
