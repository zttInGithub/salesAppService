//package com.hrtp.salesAppService.dao.v2;
//
//import com.alibaba.fastjson.JSONObject;
//import com.hrtp.Application;
//import com.hrtp.salesAppService.dao.v2.HrtUnnoCostRepository;
//import com.hrtp.salesAppService.entity.ormEntity.v2.HrtUnnoCostModel;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.List;
//import java.util.Optional;
//
///**
// * HrtUnnoCostRepositoryTest
// * <p>
// * This is description
// *
// * @author xuegangliu 2019/07/09
// * @since 1.8
// **/
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes={Application.class})
//public class HrtUnnoCostRepositoryTest {
//    private final Logger logger = LoggerFactory.getLogger(this.getClass());
//
//    @Autowired
//    private HrtUnnoCostRepository hrtUnnoCostRepository;
//
//    @Test
//    public void findByIdTest(){
//        Optional<HrtUnnoCostModel> o = hrtUnnoCostRepository.findById(43);
//        logger.error("{}", JSONObject.toJSON(o));
//    }
//
//    @Test
//    public void findSelfAllByUnnoTest(){
//        List<HrtUnnoCostModel> o = hrtUnnoCostRepository.findSelfAllByUnno("121000");
//        logger.error("{}", JSONObject.toJSON(o));
//    }
//
//    @Test
//    public void findSelfUnnoLvlByUnnoTest(){
//        logger.error("110000,lvl={}",hrtUnnoCostRepository.findSelfUnnoLvlByUnno("110000"));
//        logger.error("111000,lvl={}",hrtUnnoCostRepository.findSelfUnnoLvlByUnno("111000"));
//        logger.error("xxxxxx,lvl={}",hrtUnnoCostRepository.findSelfUnnoLvlByUnno("xxxxxx"));
//    }
//}
