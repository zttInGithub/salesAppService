package com.hrtp.system.config;

import com.hrtp.salesAppService.webservice.IGmmsService;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MyWebServiceConfig
 * description TODO
 * create by lxj 2018/8/28
 **/
@Configuration
public class MyWebServiceConfig {
    @Value("${admAppIp}")
    private String admAppIp;
    @Bean("gsClient")
    public IGmmsService wsService() {
        try {
            JaxWsProxyFactoryBean proxy = new JaxWsProxyFactoryBean();
            proxy.setServiceClass(Class.forName("com.hrtp.salesAppService.webservice.IGmmsService"));
            proxy.setAddress(admAppIp+"/AdmApp/service/gmmsService");
            return (IGmmsService)proxy.create();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new BeanCreationException("创建wsService失败");
        }
    }
}
