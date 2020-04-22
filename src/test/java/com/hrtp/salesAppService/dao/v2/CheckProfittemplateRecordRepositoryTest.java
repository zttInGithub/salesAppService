//package com.hrtp.salesAppService.dao.v2;
//
//import com.alibaba.fastjson.JSONObject;
//import com.hrtp.Application;
//import com.hrtp.salesAppService.dao.v2.CheckProfittemplateRecordRepository;
//import com.hrtp.salesAppService.entity.appEntity.v2.CommonReqEntity;
//import com.hrtp.salesAppService.entity.ormEntity.v2.CheckProfittemplateRecordModel;
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
//
///**
// * CheckProfittemplateRecordRepositoryTest
// * <p>
// * This is description
// * </p>
// *
// * @author xuegangliu 2019/07/10
// * @since 1.8
// **/
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes={Application.class})
//public class CheckProfittemplateRecordRepositoryTest {
//    private final Logger logger = LoggerFactory.getLogger(this.getClass());
//
//    @Autowired
//    private CheckProfittemplateRecordRepository checkProfittemplateRepository;
//
//    @Test
//    public void findByUnnoEqualsAndRebateTypeTest(){
//        CheckProfittemplateRecordModel o = checkProfittemplateRepository.findByUnnoEqualsAndRebateType("111000",22);
//        logger.error("{}", JSONObject.toJSON(o));
//    }
//
//    @Test
//    public void saveTest11(){
//        CheckProfittemplateRecordModel t = new CheckProfittemplateRecordModel();
//        BigDecimal a = new BigDecimal(2.5);
//        BigDecimal c = new BigDecimal(0.0055);
//        BigDecimal b = new BigDecimal(0.85);
//        t.setActCash(a);
//        t.setUnno("110060");
//        t.setCash1(a);
//        t.setCash2(a);
//        t.setCashStatus(0);
//        t.setCreateDate(new Date());
//        t.setCreateUser("testUser");
//        t.setStartDate(new Date());
//        t.setEndDate(new Date());
//        t.setProfitPercent(b);
//        t.setRate1(c);
//        t.setRate2(c);
//        t.setRebateType(22);
//        t.setTempname("110060-tmp");
//        t.setTxnCash(a);
//        checkProfittemplateRepository.save(t);
//
//    }
//
//    @Test
//    public void saveTest(){
//        CheckProfittemplateRecordModel t = new CheckProfittemplateRecordModel();
//        BigDecimal a = new BigDecimal(2.5);
//        BigDecimal c = new BigDecimal(0.0055);
//        BigDecimal b = new BigDecimal(0.85);
//        t.setActCash(a);
//        t.setUnno("110060");
//        t.setCash1(a);
//        t.setCash2(a);
//        t.setCashStatus(0);
//        t.setCreateDate(new Date());
//        t.setCreateUser("testUser");
//        t.setStartDate(new Date());
//        t.setEndDate(new Date());
//        t.setProfitPercent(b);
//        t.setRate1(c);
//        t.setRate2(c);
//        t.setRebateType(22);
//        t.setTempname("110060-tmp");
//        t.setTxnCash(a);
//        List t1 = checkProfittemplateRepository.findAll();
//        logger.error("{}",t1.size());
//        checkProfittemplateRepository.save(t);
//        t1 = checkProfittemplateRepository.findAll();
//        logger.error("{}",t1.size());
//        if(t1.size() == 0) return;
//        CheckProfittemplateRecordModel s1 = (CheckProfittemplateRecordModel)t1.get(0);
//        logger.error("modify start:{}",s1.getRate1());
//        s1.setRate1(new BigDecimal("0.0038"));
//        checkProfittemplateRepository.saveAndFlush(s1);
//        logger.error("modify end:{}",checkProfittemplateRepository.findById(s1.getCprId()).get().getRate1());
//
//    }
//
//    @Test
//    public void testSaveNew(){
//        JSONObject jsonObject = JSONObject.parseObject("{\n" +
//                "      \"cprId\": 51,\n" +
//                "      \"unno\": \"110060\",\n" +
//                "      \"rebateType\": 22,\n" +
//                "      \"profitPercent\": 0.9,\n" +
//                "      \"status\": 1,\n" +
//                "      \"tempname\": \"aa\",\n" +
//                "      \"rate1\": 0.0038,\n" +
//                "      \"cash1\": 1,\n" +
//                "      \"createUser\": \"1\",\n" +
//                "      \"createDate\": \"2019-07-22 00:00:00\",\n" +
//                "      \"startDate\": \"2019-07-22 12:00:00\",\n" +
//                "      \"endDate\": \"2019-07-22 12:00:00\",\n" +
//                "      \"rate2\": 0.0038,\n" +
//                "      \"cash2\": 1,\n" +
//                "      \"actCash\": 1,\n" +
//                "      \"txnCash\": 1,\n" +
//                "      \"cashStatus\": 1,\n" +
//                "      \"remark\": \"1\",\n" +
//                "      \"bankRate\": 0.0038,\n" +
//                "      \"cloudRate\": 0.0038,\n" +
//                "      \"repayRate\": 0.0038,\n" +
//                "      \"cashRatio\": 0.9,\n" +
//                "      \"cashSwitch\": 1,\n" +
//                "      \"agentId\": \"10006\",\n" +
//                "      \"type\": 1\n" +
//                "    }");
//        CommonReqEntity commonReqEntity = new CommonReqEntity();
//        commonReqEntity.setUnno("123000");
//        commonReqEntity.setAgentId("10006");
//        commonReqEntity.setUserId(131);
//        CheckProfittemplateRecordModel history = new CheckProfittemplateRecordModel();
//        history.setUnno(commonReqEntity.getUnno());
//        history.setCash1(jsonObject.getBigDecimal("cash1"));
//        history.setCash2(jsonObject.getBigDecimal("cash2"));
//        history.setRate1(jsonObject.getBigDecimal("rate1"));
//        history.setRate2(jsonObject.getBigDecimal("rate2"));
//        history.setCloudRate(jsonObject.getBigDecimal("cloudRate"));
//        history.setRepayRate(jsonObject.getBigDecimal("repayRate"));
//        history.setBankRate(jsonObject.getBigDecimal("bankRate"));
//        history.setProfitPercent(jsonObject.getBigDecimal("cash1"));
////        history.setCashRatio(jsonObject.getBigDecimal("cashRatio"));
////        history.setCashSwitch(jsonObject.getInteger("cashSwitch"));
//        history.setStartDate(new Date());
//        history.setType(1);
//        history.setRemark("123");
//        history.setAgentId(commonReqEntity.getAgentId());
//        history.setTxnCash(new BigDecimal(0));
//        history.setStatus(1);
//        history.setActCash(new BigDecimal(0));
//        history.setEndDate(new Date());
//        history.setCreateUser(commonReqEntity.getUserId()+"");
//        history.setTempname("A");
//        history.setRebateType(jsonObject.getInteger("rebateType"));
//        history.setCreateDate(new Date());
//        history.setCashStatus(1);
//        CheckProfittemplateRecordModel a=checkProfittemplateRepository.save(history);
//        logger.error("{}",a.getCprId());
//    }
//
//}
