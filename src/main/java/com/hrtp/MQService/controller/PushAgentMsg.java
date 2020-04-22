package com.hrtp.MQService.controller;

import com.hrtp.MQService.entity.PushMsgEntity;
import com.hrtp.MQService.service.PushAgentMsgService;
import com.hrtp.salesAppService.controller.BackOrderController;
import com.hrtp.salesAppService.entity.appEntity.RespEntity;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.SQLException;

/**
 * PushAgentMsg
 * description 代理商推送消息
 * create class by lxj 2019/1/11
 **/
@Controller
@RequestMapping("/push")
@Api(value = "消息推送相关的API", description = "消息推送")
public class PushAgentMsg {
    private static Logger logger = LoggerFactory.getLogger(BackOrderController.class);
    @Autowired
    private PushAgentMsgService service;

    /*@ApiImplicitParams({
            @ApiImplicitParam(name = "agentId", value = "产品编号-展业宝(10006)", dataType = "String", paramType = "query",
                    required = true),
            @ApiImplicitParam(name = "content", value = "消息内容", dataType = "String", paramType = "query", required =
                    true),
            @ApiImplicitParam(name = "title", value = "消息标题", dataType = "String", paramType = "query", required =
                    true),
            @ApiImplicitParam(name = "registId", value = "极光注册ID", dataType = "String", paramType = "query",
                    required = true, allowMultiple = true)
    })*/
    @ApiOperation(value = "消息推送接口", notes = "根据极光注册ID推送，展业宝registId：unno", httpMethod = "POST", produces = MediaType
            .APPLICATION_JSON_VALUE)
    @ResponseBody
    @RequestMapping(path = "/pushAgentMsg", method = {RequestMethod.POST})
    public RespEntity pushToAgent(@RequestBody @ApiParam(value = "消息信息", required = true) PushMsgEntity requestEntity) {
        logger.info("调用发送消息--->收到请求,参数{}", requestEntity);
        RespEntity respEntity = service.sendToAgent(requestEntity);
        logger.info("返回<---,参数{}", respEntity);
        return respEntity;
    }

    @ApiOperation(value = "展业宝批量消息推送接口", notes = "批量推送，根据unLvl机构级别推送", httpMethod = "POST", produces = MediaType
            .APPLICATION_JSON_VALUE)
    @ResponseBody
    @RequestMapping(path = "/pushByUnLvl", method = {RequestMethod.POST})
    public RespEntity pushToAgentByUnLvl(@RequestBody @ApiParam(value = "消息信息", required = true) PushMsgEntity
                                                 requestEntity) throws IOException, SQLException {
        logger.info("调用批量发送消息--->收到请求,参数{}", requestEntity);
        RespEntity respEntity = service.sendToAgentUnLvl(requestEntity);
        logger.info("返回<---,参数{}", respEntity);
        return respEntity;
    }
}
