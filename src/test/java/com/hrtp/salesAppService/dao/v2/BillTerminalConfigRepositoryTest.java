//package com.hrtp.salesAppService.dao.v2;
//
//import com.alibaba.fastjson.JSONObject;
//import com.hrtp.Application;
//import com.hrtp.salesAppService.dao.v2.BillTerminalConfigRepository;
//import com.hrtp.salesAppService.entity.ormEntity.v2.BillTerminalConfigModel;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.List;
//
///**
// * BillTerminalConfigRepositoryTest
// * <p>
// * This is description
// * </p>
// *
// * @author xuegangliu 2019/07/10
// * @since 1.8
// **/
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes={Application.class})
//public class BillTerminalConfigRepositoryTest {
//    private final Logger logger = LoggerFactory.getLogger(this.getClass());
//
//    @Autowired
//    private BillTerminalConfigRepository billTerminalConfigRepository;
//
//    @Test
//    public void test(){
//        List list = billTerminalConfigRepository.findAll();
//        logger.error("{}",list.size());
//    }
//
//    @Test
//    public void updateTest(){
//        Integer t = billTerminalConfigRepository.updateSelfBillTerminalInfoByStartAndEnd("110002",1,"110001","90000022","90000023");
//        logger.error("{}",t);
//    }
//
//    @Test
//    public void tt(){
////        BillTerminalConfigModel t = billTerminalConfigRepository.findById(-1).isPresent()?billTerminalConfigRepository.findById(-1).get():null;
//        BillTerminalConfigModel t = billTerminalConfigRepository.findById(1).isPresent()?billTerminalConfigRepository.findById(1).get():null;
//        logger.error("{}", JSONObject.toJSON(t));
//        t.setStatus(2);
//        t.setBtcId(null);
//        BillTerminalConfigModel a=billTerminalConfigRepository.save(t);
//        logger.error("{}",JSONObject.toJSON(a));
//
//    }
//}
