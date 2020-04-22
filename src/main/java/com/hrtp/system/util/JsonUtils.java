package com.hrtp.system.util;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Modifier;
import java.util.Enumeration;
import java.util.Map;

/**
 * JsonUtils
 * description json自定义工具类
 * create by lxj 2018/8/27
 **/
public class JsonUtils {

    public static Object mulTipartRequest2Bean(HttpServletRequest request, Object obj) throws Exception {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());
        if (multipartResolver.isMultipart(request)) {
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            BeanUtils.populate(obj, multiRequest.getParameterMap());
            return mapToObject(multiRequest.getFileMap(), obj);
        } else {
            // 目前只扩展到文件和表单form-data
            throw new RuntimeException("form-data转化异常");
        }
    }

    public static<T> T mulTipartRequest2BeanX(HttpServletRequest request, Class<T> clazz) throws Exception {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());
        if (multipartResolver.isMultipart(request)) {
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            Object obj = clazz.newInstance();
            BeanUtils.populate(obj, multiRequest.getParameterMap());
            return mapToObjectX(multiRequest.getFileMap(), obj, clazz);
        } else {
            // 目前只扩展到文件和表单form-data
            throw new RuntimeException("form-data转化异常");
        }
    }

    public static Object mapToObject(Map<String, MultipartFile> map, Object obj) throws Exception {
        if (map == null) return null;
        java.lang.reflect.Field[] fields = obj.getClass().getDeclaredFields();
        for (java.lang.reflect.Field field : fields) {
            int mod = field.getModifiers();
            if(Modifier.isStatic(mod) || Modifier.isFinal(mod) || StringUtils.isEmpty(map.get(field.getName()))){
                continue;
            }
            field.setAccessible(true);
            field.set(obj, map.get(field.getName()));
        }
        return obj;
    }

    public static<T> T mapToObjectX(Map<String, MultipartFile> map, Object obj,Class<T> tClass) throws Exception {
        if (map == null) return null;
        java.lang.reflect.Field[] fields = obj.getClass().getDeclaredFields();
        for (java.lang.reflect.Field field : fields) {
            int mod = field.getModifiers();
            if(Modifier.isStatic(mod) || Modifier.isFinal(mod) || StringUtils.isEmpty(map.get(field.getName()))){
                continue;
            }
            field.setAccessible(true);
            field.set(obj, map.get(field.getName()));
        }
        return (T)obj;
    }

    public static <T> T request2Bean(HttpServletRequest request, Class<T> clazz){
        try{
            T bean = clazz.newInstance();
            Enumeration e = request.getParameterNames();
            while(e.hasMoreElements()){
                String name = (String) e.nextElement();  //username=aaa password=123
                String value = request.getParameter(name);
                BeanUtils.setProperty(bean,name,value);
            }
            return bean;
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}