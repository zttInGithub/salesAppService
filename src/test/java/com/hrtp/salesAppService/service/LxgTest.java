//package com.hrtp.salesAppService.service;
//
//import com.alibaba.fastjson.JSONObject;
//import com.hrtp.Application;
//import com.hrtp.salesAppService.entity.appEntity.LoginReqEntity;
//import com.hrtp.salesAppService.entity.appEntity.ReqEntity;
//import com.hrtp.salesAppService.entity.appEntity.RespEntity;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes={Application.class})
//public class LxgTest {
//
//    private static final Logger logger = LoggerFactory.getLogger(LxgTest.class);
//
//    @Autowired
//    UserService userService;
//    @Autowired
//    HomePageService homePageService;
//
//
//    @Test
//    public void testLogin(){
//        LoginReqEntity t = new LoginReqEntity();
//        t.setLoginName("01382799");
//        t.setPassword("hrt1234");
//        t.setAgentID("1");
//        t.setAppVersion("1");
//        t.setOs("android");
//        t.setOsVersion("1");
//        logger.error("req:{}",JSONObject.toJSON(t).toString());
//        RespEntity respEntity= userService.doLogin(t);
//        logger.error("res:{}",JSONObject.toJSON(respEntity).toString());
//    }
//
//    @Test
//    public void testVerson(){
//        ReqEntity params = new ReqEntity();
//        params.setOs("ios");
//        System.out.println(JSONObject.toJSON(homePageService.getVersion(params)));
//    }
//
//}
