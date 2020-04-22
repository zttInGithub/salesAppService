package com.hrtp.MQService.service;

import com.hrtp.MQService.dao.PushMsgRespository;
import com.hrtp.MQService.entity.model.PushMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * CommonPushService
 * description 推送公共类
 * create class by lxj 2019/1/11
 **/
@Service
public abstract class CommonPushService {
    @Autowired
    public PushMsgRespository msgRespository;

    public void save(PushMessage model){
        msgRespository.save(model);
    }
}
