package com.hrtp.salesAppService.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;
import java.util.Map;

/**
 * FileCheckAspect
 * description
 * create by lxj 2018/8/30
 **/
@Aspect
@Component
public class FileCheckAspect {
    @Pointcut("@annotation(com.hrtp.system.annotation.FileCheck)")
    public void fileCheckPointCut() {
    }

    private long maxFileSize;
    private long maxRequestSize;

    private FileCheckAspect(@Value("${salesapp.multipart.max-file-size}") String maxFileSize,@Value("${salesapp" +
            ".multipart.max-request-size}") String maxRequestSize){
        this.maxFileSize = parseSize(maxFileSize);
        this.maxRequestSize = parseSize(maxRequestSize);
    }

    @Before("fileCheckPointCut()")
    public void judgeService(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        HttpServletRequest request = null;
        for (Object obj : args) {
            if (obj instanceof StandardMultipartHttpServletRequest) {
                request = (StandardMultipartHttpServletRequest) obj;
            } else {
                throw new RuntimeException();
            }
        }
        long totalSize = 0;
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession()
                .getServletContext());
        if (multipartResolver.isMultipart(request)) {
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            for (Map.Entry<String, MultipartFile> entry : multiRequest.getFileMap().entrySet()) {
                long size = entry.getValue().getSize();
                if (size > maxFileSize) throw new MaxUploadSizeExceededException(maxFileSize);
                totalSize += size;
            }
        }
        if (totalSize > maxRequestSize) throw new MaxUploadSizeExceededException(maxRequestSize);
    }

    private long parseSize(String size) {
        Assert.hasLength(size, "Size must not be empty");
        size = size.toUpperCase(Locale.ENGLISH);
        if (size.endsWith("KB")) {
            return Long.valueOf(size.substring(0, size.length() - 2)) * 1024L;
        } else {
            return size.endsWith("MB") ? Long.valueOf(size.substring(0, size.length() - 2)) * 1024L * 1024L : Long
                    .valueOf(size);

        }
    }
}
