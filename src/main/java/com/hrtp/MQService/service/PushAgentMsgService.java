package com.hrtp.MQService.service;

import com.hrtp.MQService.common.PushCliParam;
import com.hrtp.MQService.entity.PushMsgEntity;
import com.hrtp.MQService.entity.model.PushMessage;
import com.hrtp.MQService.util.PushMsgUtil;
import com.hrtp.MQService.util.StringUtil;
import com.hrtp.salesAppService.entity.appEntity.RespEntity;
import com.hrtp.system.costant.ReturnCode;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;


/**
 * PushAgentMsgService
 * description
 * create class by lxj 2019/1/11
 **/
@Service
public class PushAgentMsgService extends CommonPushService {

    public RespEntity sendToAgent(PushMsgEntity req) {
        PushCliParam appinfo = PushCliParam.getByAgentId(req.getAgentId());
        PushMsgUtil.sendMsgCommon(req.getRegistId(),null,appinfo.appkey().toLowerCase(),appinfo.mastersecret()
                .toLowerCase(),req.getContent(),req.getTitle(),"ZYB_PUSH_MSG");

        for (String unno : req.getRegistId()) {
            PushMessage model = new PushMessage();
            model.setAgentId(unno);
            model.setContent(req.getContent());
            model.setMsgType(null);
            model.setTime(new Date());
            model.setTitle(req.getTitle());
            model.setStatus(0);
            model.setMsgType(req.getMsgType());
            save(model);
        }
        return new RespEntity(ReturnCode.SUCCESS,"推送成功！");
    }

    public RespEntity sendToAgentUnLvl(PushMsgEntity req) throws IOException, SQLException {
        if(StringUtils.isEmpty(req.getUnLvl())){
            return new RespEntity(ReturnCode.FALT,"机构级别不可为空");
        }
        if(StringUtils.isEmpty(req.getContent())){
            return new RespEntity(ReturnCode.FALT,"推送内容不可为空");
        }
        if(StringUtils.isEmpty(req.getTitle())){
            return new RespEntity(ReturnCode.FALT,"推送标题不可为空");
        }
        PushCliParam appinfo = PushCliParam.getByAgentId(req.getAgentId());
        String unnos = StringUtil.ClobToString(super.msgRespository.getUnnoByLvl(req.getUnLvl()).get(0));
        String[] regists = unnos.split(",");

        PushMsgUtil.sendMsgCommon(regists,null,appinfo.appkey().toLowerCase(),appinfo.mastersecret()
                .toLowerCase(),req.getContent(),req.getTitle(),"ZYB_PUSH_MSG");
        for (String unno : regists) {
            PushMessage model = new PushMessage();
            model.setAgentId(unno);
            model.setContent(req.getContent());
            model.setMsgType(null);
            model.setTime(new Date());
            model.setTitle(req.getTitle());
            model.setStatus(0);
            model.setMsgType(req.getMsgType());
            save(model);
        }

        return new RespEntity(ReturnCode.SUCCESS,"推送成功！");
    }
}
