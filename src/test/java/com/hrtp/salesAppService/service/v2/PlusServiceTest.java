//package com.hrtp.salesAppService.service.v2;
//
//import com.alibaba.fastjson.JSONObject;
//import com.hrtp.Application;
//import com.hrtp.salesAppService.dao.v2.CheckProfittemplateRecordRepository;
//import com.hrtp.salesAppService.entity.appEntity.v2.CommonReqEntity;
//import com.hrtp.salesAppService.entity.appEntity.v2.PlusCost;
//import com.hrtp.salesAppService.entity.ormEntity.v2.CheckProfittemplateRecordModel;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.Date;
//import java.util.List;
//import java.util.Map;
//
///**
// * PlusServiceTest
// * <p>
// * This is description
// *
// * @author xuegangliu 2019/07/09
// * @since 1.8
// **/
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes={Application.class})
//public class PlusServiceTest {
//    private final Logger logger = LoggerFactory.getLogger(this.getClass());
//
//    @Autowired
//    private PlusService plusService;
//
//    @Autowired
//    private CheckProfittemplateRecordRepository recordRepository;
//
//    @Test
//    public void getCurrentUnnoCostTest(){
//        CommonReqEntity commonReqEntity=new CommonReqEntity();
//        commonReqEntity.setAgentId("10000");
//        commonReqEntity.setUnno("b12000");
//        commonReqEntity.setUnLvl(2);
////        commonReqEntity.setRebateType(23);
//        logger.error("{}",JSONObject.toJSON(plusService.getCurrentUnnoCost(commonReqEntity)));
//    }
//
//    @Test
//    public void saveAgentCostTest(){
//        CommonReqEntity commonReqEntity = new CommonReqEntity();
////        String t = JSONObject.toJSONString(plusService.getCurrentUnnoCost(commonReqEntity));
////        commonReqEntity.setCostList(JSONArray.parseArray(t).getJSONObject(0).toJSONString());
//        commonReqEntity.setRebateType(22);
//        commonReqEntity.setUnLvl(2);
//        commonReqEntity.setUnno("b12000");
//        commonReqEntity.setBtcId(123);
//        commonReqEntity.setBuId("-23");
//        plusService.saveAgentCost(commonReqEntity);
//    }
//
//    @Test
//    public void yidaiCostTest(){
//        Map map = plusService.getYiDaiList("122003");
//        logger.info("{}",JSONObject.toJSON(map));
//    }
//
//    @Test
//    public void saveTest1(){
//        CommonReqEntity commonReqEntity = new CommonReqEntity();
//        commonReqEntity.setUnno("123000");
//        commonReqEntity.setSubUnno("850002");
//        commonReqEntity.setAgentId("10006");
//        commonReqEntity.setUserId(131);
//        plusService.save10006Prfoit(commonReqEntity,new JSONObject(),2);
//    }
//
//    @Test
//    public void jsonToJavaTest(){
//        PlusCost plusCost = JSONObject.parseObject("{\n" +
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
//                "    }", PlusCost.class);
//        logger.error("{}",JSONObject.toJSONString(plusCost));
//    }
//
//    @Test
//    public void t(){
//        CommonReqEntity historyReq = new CommonReqEntity();
//        historyReq.setUnLvl(2);
//        historyReq.setUnno("112082");
//        historyReq.setSubUnno("112082");
//        historyReq.setAgentId("10000");
////        historyReq.setRebateType(jsonObject.getInteger("rebateType"));
//        historyReq.setUserId(1);
//        List<Map> historyMap = plusService.getCurrentUnnoCost(historyReq);
//        logger.error("{}",historyMap.size());
//        logger.error("{}",historyMap.get(0));
//        CheckProfittemplateRecordModel t = JSONObject.parseObject(JSONObject.toJSONString(historyMap.get(0)),CheckProfittemplateRecordModel.class);
//        Date date = new Date();
//        t.setUnno(historyReq.getSubUnno());
//        t.setAgentId(historyReq.getAgentId());
//        t.setEndDate(date);
//        t.setCreateUser(historyReq.getUserId()+"");
//        t.setTempname(historyReq.getUnno()+"|"+historyReq.getSubUnno());
//        t.setCreateDate(date);
//        t.setCashStatus(1);
//        recordRepository.save(t);
//        logger.error("{}",JSONObject.toJSONString(JSONObject.parseObject(JSONObject.toJSONString(historyMap.get(0)),CheckProfittemplateRecordModel.class)));
//
//    }
//
//    @Test
//    public void getDefaultUnnoCostTest(){
//        logger.error("{}",JSONObject.toJSONString(plusService.getDefaultUnnoCost()));
//    }
//
//    @Test
//    public void getSubUnnoCostTest(){
//        CommonReqEntity commonReqEntity=new CommonReqEntity();
//        commonReqEntity.setSubUnno("112003");
//        String subSon="112003";
//        logger.error("{}",JSONObject.toJSONString(plusService.getSubUnnoCost(subSon).size()));
//        logger.error("{}",plusService.getSubUnnoCost(subSon));
//
//    }
//
//}
