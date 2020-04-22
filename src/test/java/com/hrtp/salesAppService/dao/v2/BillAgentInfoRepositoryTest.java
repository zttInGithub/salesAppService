//package com.hrtp.salesAppService.dao.v2;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import com.hrtp.Application;
//import com.hrtp.salesAppService.dao.BillAgentInfoRepository;
//import com.hrtp.salesAppService.entity.ormEntity.BillAgentInfoModel;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.List;
//
///**
// * BillAgentInfoRepositoryTest
// * <p>
// * This is description
// *
// * @author xuegangliu 2019/07/09
// * @since 1.8
// **/
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes={Application.class})
//public class BillAgentInfoRepositoryTest {
//    private final Logger logger = LoggerFactory.getLogger(this.getClass());
//
//    @Autowired
//    private BillAgentInfoRepository billAgentInfoRepository;
//    @Autowired
//    private HrtUnnoCostRepository hrtUnnoCostRepository;
//
//    @Test
//    public void testPage(){
//        List<String> unnos=hrtUnnoCostRepository.findAllSubUnnoByUnno("111000");
//        Pageable pageable = PageRequest.of(0, 10);
////        Page<BillAgentInfoModel>  list = billAgentInfoRepository.findByRowsByUnnosAndPage(unnos.size()==0?new String[]{"ZZ"}:unnos.toArray(new String[0]),pageable);
//        Page<BillAgentInfoModel>  list = billAgentInfoRepository.findByRowsByUnnosAndAgentNameAndPage(unnos.size()==0?new String[]{"ZZ"}:unnos.toArray(new String[0]),"lxg%",pageable);
////        billAgentInfoRepository.findAll(pageable);
//        logger.error("{}", JSONObject.toJSONString(list));
//        logger.error("{}", billAgentInfoRepository.findAll().size());
//    }
//
////    @Test
////    public void test1(){
////        List<BillAgentInfoModel> list = billAgentInfoRepository.findAllByAgentNameEquals("渠道商12345");
////        logger.error("{}",list.size());
////    }
//}
