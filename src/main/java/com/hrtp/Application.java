package com.hrtp;

import com.hrtp.MQService.util.SpringContextUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@EnableAutoConfiguration
@SpringBootApplication
//@ComponentScan(basePackages = {"com.hrtp.salesAppService.*","com.hrtp.system.*"})
public class Application {
    public static void main(String[] args) {
        ConfigurableApplicationContext app = SpringApplication.run(Application.class, args);
        SpringContextUtil.setApplicationContext(app);
    }
}
