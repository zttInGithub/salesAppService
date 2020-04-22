package com.hrtp.threads;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hrtp.salesAppService.entity.ormEntity.PushMessage;
import com.hrtp.system.util.PutMsgUtil;

/**
 * 向极光推送发送数据
 * @author YQ
 */
public class ReceiveRepayBDThread extends Thread{

    private static final Log log = LogFactory.getLog(ReceiveRepayBDThread.class);

    private String unno;
    private Integer type;
    private String modelName;
     
	public ReceiveRepayBDThread(String modelName,String unno,int type){
        this.type = type;
    	this.unno = unno;
    	this.modelName = modelName;
    }
    
    public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getUnno() {
		return unno;
	}
	public void setUnno(String unno) {
		this.unno = unno;
	}
	public String getModelName() {
		return modelName;
	}
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	
	@Override
    public void run() {
		String message = "";
		String title = "";
		if(type==1){//修改模板
			title = "模板修改通知";
			message = "您的上级已经成功为您修改了"+ modelName +"分润模板，请打开APP，在”分润模板管理“—”下月生效模板“里进行查看，该模板将在下月生效。";
		}else if(type==2){//新增模板
			title = "模板新增通知";
			message = "您的上级已经成功为您设置了"+ modelName +"分润模板，请打开APP，在”分润模板管理“—”我的分润模板“里进行查看，该模板已经生效。";
		}
		if(!"".equals(message) && !"".equals(title)){
			log.info("模板变更请求极光推送参数====>unno:"+unno+",message:"+message);
			PutMsgUtil.sendMsgCommon(unno, null, message, title, "2");
		}
    }
}
