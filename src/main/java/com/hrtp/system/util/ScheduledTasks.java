package com.hrtp.system.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.hrtp.salesAppService.dao.BackOrderRepository;
import com.hrtp.salesAppService.dao.MerchManagerRepository;
import com.hrtp.salesAppService.dao.MistakeDao;
import com.hrtp.salesAppService.dao.MistakeRepository;
import com.hrtp.salesAppService.dao.PushMessageRepository;
import com.hrtp.salesAppService.entity.ormEntity.BackOrderModel;
import com.hrtp.salesAppService.entity.ormEntity.MerchManagerModel;
import com.hrtp.salesAppService.entity.ormEntity.MistakeModel;
import com.hrtp.salesAppService.entity.ormEntity.PushMessage;
import com.hrtp.salesAppService.service.ProcService;

@Component
@Configurable
@EnableScheduling
public class ScheduledTasks {
	@Value("${isMaster}")
	private String isMaster;
	@Autowired
	private MistakeDao mistakeDao;
	@Autowired
	private MistakeRepository mistakeRepository;
	@Autowired
	private PushMessageRepository pushMessageRepository;
	@Autowired
	private MerchManagerRepository merchManagerRepository;
	@Autowired
	private BackOrderRepository backOrderRepository;
	@Autowired
	private ProcService  procRepst;
	private static  Logger logger = LogManager.getLogger(ScheduledTasks.class);
    private static final SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");

    
   
	@Scheduled(cron="${tsCron}")
	public void execProc() {
		logger.info("主备机标识"+isMaster);
		if("true".equals(isMaster)) {
			logger.info("定时任务-存储过程-开始");
			execProcForDate("");
			logger.info("定时任务-存储过程-结束");
		}else {
			logger.info("非主机，不执行定时任务");
		}
		
	}
	
	@Scheduled(fixedRate = 1000 * 60 * 10)
    public void execTask(){
		logger.info("主备机标识"+isMaster);
		if("true".equals(isMaster)) {
			logger.info("定时任务-回复退单调单推送-开始");
			updateTaskForMistake();
			logger.info("定时任务-回复退单调单推送-结束");
		}else {
			logger.info("非主机，不执行推送任务");
		}
    }
	 
	private void execProcForDate(String transDate) {
		logger.info("transDate == "+ transDate);
		String result = "";
		logger.info("proc_pg_merchinfo_sum开始执行");
		result = procRepst.callStore("proc_pg_merchinfo_sum", transDate);
		logger.info("proc_pg_merchinfo_sum执行结束执行结果==" + result);
		
		logger.info("proc_pg_unnocashback_sum开始执行");
		result = procRepst.callStore("proc_pg_unnocashback_sum", transDate);
		logger.info("proc_pg_unnocashback_sum执行结束执行结果==" + result);
		
		logger.info("proc_pg_unnomdao_fenrun开始执行");
		result = procRepst.callStore("proc_pg_unnomdao_fenrun", transDate);
		logger.info("proc_pg_unnomdao_fenrun执行结束执行结果==" + result);
		
		logger.info("proc_pg_unnopos_fenrun开始执行");
		result = procRepst.callStore("proc_pg_unnopos_fenrun", transDate);
		logger.info("proc_pg_unnopos_fenrun执行结束执行结果==" + result);
		
		
		logger.info("proc_pg_unnoqpay_fenrun开始执行");
		result = procRepst.callStore("proc_pg_unnoqpay_fenrun", transDate);
		logger.info("proc_pg_unnoqpay_fenrun执行结束执行结果==" + result);
		
		logger.info("proc_pg_unnorepay_fenrun开始执行");
		result = procRepst.callStore("proc_pg_unnorepay_fenrun", transDate);
		logger.info("proc_pg_unnorepay_fenrun执行结束执行结果==" + result);
		
		logger.info("proc_pg_unnoscan_fenrun开始执行");
		result = procRepst.callStore("proc_pg_unnoscan_fenrun", transDate);
		logger.info("proc_pg_unnoscan_fenrun执行结束执行结果==" + result);
		
		logger.info("proc_pg_unnosyt_fenrun开始执行");
		result = procRepst.callStore("proc_pg_unnosyt_fenrun", transDate);
		logger.info("proc_pg_unnosyt_fenrun执行结束执行结果==" + result);
		
		logger.info("proc_pg_unnoplus_fenrun开始执行");
		result = procRepst.callStore("proc_pg_unnoplus_fenrun", transDate);
		logger.info("proc_pg_unnoplus_fenrun执行结束执行结果==" + result);
		
		logger.info("proc_pg_unnotxnfrun_sum开始执行");
		result = procRepst.callStore("proc_pg_unnotxnfrun_sum", transDate);
		logger.info("proc_pg_unnotxnfrun_sum执行结束执行结果==" + result);
		
	}
	
	
	
	/**
	 * 定时任务1：回复退单调单推送
	 */
	private void updateTaskForMistake() {
		//查询回复，调单回复
		String sql = "select a.dpid,a.mid from check_dispatchOrder a,bill_merchantinfo b "
				+ "where a.mid=b.mid and a.pushstatus =0";
		List<Object[]> list = mistakeDao.queryPidOrg(sql);
		for (Object[] obj : list) {
			//保存推送信息
			PushMessage pushMessage = new PushMessage();
			MistakeModel model = mistakeRepository.getOne(obj[0]+"");
			MerchManagerModel managerModel = merchManagerRepository.findMerByMid(obj[1]+"");
			String unno = managerModel.getUnno();
			String msg = "";//消息内容
			String title = "";//消息标题
			logger.info("回复退单调单推送，unno:"+unno+",orderType:"+model.getOrderType());
			if("2".equals(model.getOrderType())) {//调单回复
				msg = "卡号末尾为"+model.getCardNo().substring(model.getCardNo().length()-5, model.getCardNo().length())+"的持卡人对在商户【"+managerModel.getMid()+"】"
					+ "于"+model.getTransDate().substring(0, 4)+"年"+model.getTransDate().substring(4, 6)+""
					+ "月"+model.getTransDate().substring(6, 8)+"日消费的"+model.getSamt()+"元的交易进行调单，"
					+ "请在最终回复日期("+model.getFinalDate().substring(0, 4)+"年"+model.getFinalDate().substring(4, 6)+""
					+ "月"+model.getFinalDate().substring(6, 8)+")内回复"+managerModel.getRname()+"、"+managerModel.getBaddr()+""
					+ "、"+managerModel.getContactPhone()+"、"+managerModel.getContactPerson()+"及交易签购单，逾期将不予处理!";
				title = "调单回复";
				PutMsgUtil.sendMsgCommon(unno, null, msg, title, "2");
				pushMessage.setMsgType("2");
				model.setPushStatus(1);//已推送
			}else if("3".equals(model.getOrderType())) {
				//查询回复
				msg = "卡号末尾为"+model.getCardNo().substring(model.getCardNo().length()-5, model.getCardNo().length())+"的持卡人对在商户【"+managerModel.getMid()+"】"
					+ "于"+model.getTransDate().substring(0, 4)+"年"+model.getTransDate().substring(4, 6)+""
					+ "月"+model.getTransDate().substring(6, 8)+"日消费的"+model.getSamt()+"元的交易进行查询，"
					+ "请在最终回复日期("+model.getFinalDate().substring(0, 4)+"年"+model.getFinalDate().substring(4, 6)+""
					+ "月"+model.getFinalDate().substring(6, 8)+")内回复"+managerModel.getRname()+"、"+managerModel.getBaddr()+""
					+ "、"+managerModel.getContactPhone()+"、"+managerModel.getContactPerson()+"等信息，逾期将不予处理!";
				title = "查询回复";
				PutMsgUtil.sendMsgCommon(unno, null, msg, title, "1");
				pushMessage.setMsgType("1");
				model.setPushStatus(1);//已推送
			}else if("4".equals(model.getOrderType())) {
				//欺诈交易回复
				msg = "卡号末尾为"+model.getCardNo().substring(model.getCardNo().length()-5, model.getCardNo().length())+"的持卡人对在商户【"+managerModel.getMid()+"】"
					+ "于"+model.getTransDate().substring(0, 4)+"年"+model.getTransDate().substring(4, 6)+""
					+ "月"+model.getTransDate().substring(6, 8)+"日消费的"+model.getSamt()+"元的交易涉及欺诈交易，"
					+ "请在最终回复日期("+model.getFinalDate().substring(0, 4)+"年"+model.getFinalDate().substring(4, 6)+""
					+ "月"+model.getFinalDate().substring(6, 8)+")内回复"+managerModel.getRname()+"、"+managerModel.getBaddr()+""
					+ "、"+managerModel.getContactPhone()+"、"+managerModel.getContactPerson()+"及交易签购单，逾期将不予处理!";
				title = "欺诈交易回复";
				PutMsgUtil.sendMsgCommon(unno, null, msg, title, "1");
				pushMessage.setMsgType("4");
				model.setPushStatus(1);//已推送
			}
			pushMessage.setAgentId(unno);
			pushMessage.setContent(msg);
			pushMessage.setTime(new Date());
			pushMessage.setTitle(title);
			pushMessage.setStatus(0);
			pushMessageRepository.save(pushMessage);
			//更新为已推送
			mistakeRepository.save(model);
		}
		//退单
		String sql1 = "select a.roid,a.mid from check_reOrder a,bill_merchantinfo b where a.mid=b.mid and a.pushstatus is null";
		List<Object[]> list1 = mistakeDao.queryPidOrg(sql1);
		for (Object[] obj : list1) {
			BackOrderModel model = backOrderRepository.getOne(obj[0]+"");
			MerchManagerModel managerModel = merchManagerRepository.findMerByMid(obj[1]+"");
			String unno = managerModel.getUnno();
			String msg = "卡号末尾为"+model.getCardPan().substring(model.getCardPan().length()-5, model.getCardPan().length())+"的持卡人已在"+model.getTxnday().substring(0, 4)+""
					+ "年"+model.getTxnday().substring(4, 6)+"月"+model.getTxnday().substring(6, 8)+"日"
					+ "对在商户【"+managerModel.getMid()+"】消费的"+model.getSamt()+"元的交易退单，请在25天内上传请款资料，逾期将不能请款!";
			String title = "退单管理";
			logger.info("退单推送，unno:"+unno);
			PutMsgUtil.sendMsgCommon(unno, null, msg, title, "3");
			//保存推送信息
			PushMessage pushMessage = new PushMessage();
			pushMessage.setAgentId(unno);
			pushMessage.setContent(msg);
			pushMessage.setMsgType(null);
			pushMessage.setTime(new Date());
			pushMessage.setTitle(title);
			pushMessage.setStatus(0);
			pushMessage.setMsgType("3");
			pushMessageRepository.save(pushMessage);
			model.setPushStatus(1);
			//更新为已推送
			backOrderRepository.save(model);
		}
	}
//    //每1分钟执行一次
//    @Scheduled(cron = "0 */1 *  * * * ")
//    public void reportCurrentByCron(){
//        System.out.println ("Scheduling Tasks Examples By Cron: The time is now " + dateFormat ().format (new Date ()));
//    }
}
