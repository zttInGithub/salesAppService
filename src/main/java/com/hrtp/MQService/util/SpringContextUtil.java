package com.hrtp.MQService.util;

import org.springframework.context.ApplicationContext;

/**
 * SpringContextUtil
 * description 获取springcontext上下文工具
 * create class by lxj 2019/1/11
 **/
public class SpringContextUtil {

    private static ApplicationContext applicationContext;

    /**
     * 获取上下文
     * @return
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 设置上下文，除启动类，禁止使用
     * @param applicationContext
     */
    public static void setApplicationContext(ApplicationContext applicationContext) {
        SpringContextUtil.applicationContext = applicationContext;
    }

    /**
     * 通过名字获取上下文中的bean
     * @param name
     * @return
     */
    public static Object getBean(String name){
        return applicationContext.getBean(name);
    }

    /**
     * 通过类型获取上下文中的bean
     * @param requiredType
     * @return
     */
    public static Object getBean(Class<?> requiredType){
        return applicationContext.getBean(requiredType);
    }

    /**获取配置文件变量名
     * @param env
     * @return
     */
    public static String getPropValue(String env){
        return applicationContext.getEnvironment().getProperty(env);
    }
}