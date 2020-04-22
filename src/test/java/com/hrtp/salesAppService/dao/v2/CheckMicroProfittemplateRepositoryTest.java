//package com.hrtp.salesAppService.dao.v2;
//
//import com.alibaba.fastjson.JSONObject;
//import com.hrtp.Application;
//import com.hrtp.salesAppService.dao.v2.CheckMicroProfittemplateRepository;
//import com.hrtp.salesAppService.entity.ormEntity.v2.CheckMicroProfittemplateModel;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.math.BigDecimal;
//import java.util.Date;
//import java.util.List;
//import java.util.Map;
//
///**
// * CheckMicroProfittemplateRepositoryTest
// * <p>
// * This is description
// *
// * @author xuegangliu 2019/07/09
// * @since 1.8
// **/
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes={Application.class})
//public class CheckMicroProfittemplateRepositoryTest {
//
//    private final Logger logger = LoggerFactory.getLogger(this.getClass());
//
//    @Autowired
//    private CheckMicroProfittemplateRepository checkMicroProfittemplateRepository;
//
//    @Test
//    public void findAllTest(){
//        List<CheckMicroProfittemplateModel> list = checkMicroProfittemplateRepository.findAll();
//        logger.error("{}",list.size());
//    }
//
//    @Test
//    public void findPlusProfitAllByUnnoTest(){
//        List<CheckMicroProfittemplateModel> o = checkMicroProfittemplateRepository.findPlusProfitAllByUnno("112007");
//        logger.error("{}", JSONObject.toJSON(o.get(0)));
//    }
//
////    @Test
////    public void saveTest(){
////        CheckMicroProfittemplateModel MicroModel = new CheckMicroProfittemplateModel();
////        MicroModel.setUnno("999999");
////        MicroModel.setMerchantType(6);
//////        MicroModel.setProfitType(1);
////        MicroModel.setProfitRule(22);
////        BigDecimal t= new BigDecimal(0.56);
////        MicroModel.setStartAmount(0.56);
////        MicroModel.setEndAmount(0.56);
////        MicroModel.setRuleThreshold(0.56);
////        MicroModel.setProfitPercent(0.56);
////        MicroModel.setProfitPercent1(0.56);
////        MicroModel.setCashRate(0.56);
////        MicroModel.setCashAmt(0.56);
////        MicroModel.setCashAmt1(0.56);
////        MicroModel.setCashAmt2(0.56);
////        MicroModel.setCreditBankRate(0.56);
////        MicroModel.setScanRate(0.56);
////        MicroModel.setScanRate1(0.56);
////        MicroModel.setScanRate2(0.56);
////        MicroModel.setCloudQuickRate(0.56);
////        MicroModel.setMatainDate(new Date());
////        MicroModel.setTempName("111234");
////        MicroModel.setMatainUserId(12345);
////        MicroModel.setMatainType("A");
////        MicroModel.setSettMethod("000000");
////        CheckMicroProfittemplateModel MicroModel1 = checkMicroProfittemplateRepository.save(MicroModel);
////        logger.error("{}",MicroModel.getAptId());
////    }
//
//    @Test
//    public void saveCheckUnitProfitemplateTest(){
//        Integer t = checkMicroProfittemplateRepository.saveCheckUnitProfitemplate("123900",1481,789);
//        logger.error("{}",t);
//    }
//
//    @Test
//    public void getProfitIsPhoneInfoTest(){
//        Map map = checkMicroProfittemplateRepository.getProfitUnnoInfo(4,"850001");
//        logger.error("{}",JSONObject.toJSONString(map));
//    }
//}
