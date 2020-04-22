package com.hrtp.salesAppService.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.hrtp.salesAppService.dao.MerchManagerRepository;
import com.hrtp.salesAppService.dao.MerchantTerminalInfoRepository;
import com.hrtp.salesAppService.dao.TerminalInfoRepository;
import com.hrtp.salesAppService.dao.TodoRepository;
import com.hrtp.salesAppService.entity.appEntity.MerchantTerminalInfoEntity;
import com.hrtp.salesAppService.entity.appEntity.RespEntity;
import com.hrtp.salesAppService.entity.appEntity.TerminalInfoEntity;
import com.hrtp.salesAppService.entity.ormEntity.MerchManagerModel;
import com.hrtp.salesAppService.entity.ormEntity.MerchantTerminalInfoModel;
import com.hrtp.salesAppService.entity.ormEntity.TerminalInfoModel;
import com.hrtp.salesAppService.entity.ormEntity.TodoModel;
import com.hrtp.system.costant.ReturnCode;
import com.hrtp.system.util.HttpXmlClient;

@Service
public class MerchantTerminalInfoService {
	@Autowired
	private MerchantTerminalInfoRepository merchantTerminalInfoRepository;
	@Autowired
	private TerminalInfoRepository terminalInfoRepository;
	@Autowired
	private MerchManagerRepository merchManagerRepository;
	@Autowired
	private TodoRepository todoRepository;
	@Value("${admAppIp}")
	private  String admAppIp;
	
	public RespEntity saveMerTerInfo(MerchantTerminalInfoEntity mtie) {
		RespEntity rs = new RespEntity();
		//查询商户
		MerchManagerModel merchManagerModel = merchManagerRepository.findMerByMid(mtie.getMid());
		if(merchManagerModel==null){
			rs.setReturnCode("未查询到商户信息，请确认后再试");
			rs.setReturnCode(ReturnCode.FALT);
			return rs;
		}
		//查询设备
		TerminalInfoModel terminalInfoModel = terminalInfoRepository.findTerminalInfoByTermID(mtie.getTid());
		if(terminalInfoModel==null){
			rs.setReturnCode("未查询到设备信息，请确认后再试");
			rs.setReturnCode(ReturnCode.FALT);
			return rs;
		}
		terminalInfoModel.setUsedConfirmDate(new Date());
		terminalInfoModel.setStatus(2);
		terminalInfoRepository.save(terminalInfoModel);
		
		//请求综合，判断当前入网TUSN是否重复
		Map<String,String> map2 = new HashMap<String, String>();
		map2.put("sn", mtie.getSn());
		String post = HttpXmlClient.post(admAppIp+"/AdmApp/bank/bankTermAcc_querySnIfExist.action", map2);
		Integer flag = (Integer) ((Map<String, Object>) JSONObject.parse(post)).get("obj");
		if(flag==1){
			rs.setReturnCode(ReturnCode.FALT);
			rs.setReturnMsg("TUSN已被使用");
			return rs;
		}
		//保存中间表信息
		MerchantTerminalInfoModel mtim = new MerchantTerminalInfoModel();
		BeanUtils.copyProperties(mtie, mtim);
		mtim.setMaintainType("A");
		mtim.setApproveStatus("Z");
		mtim.setMaintainDate(new Date());
		mtim.setStatus(2);
		MerchantTerminalInfoModel model = merchantTerminalInfoRepository.save(mtim);
		//判断商户状态，确认是增机还是挂终端
		if("Z".equals(merchManagerModel.getApproveStatus())) {
			merchManagerModel.setMaintainUid(mtie.getMaintainUid());
			merchManagerModel.setApproveDate(new Date());
			merchManagerModel.setApproveStatus("W");//如果是待添加终端的商户，则修改状态为C
			merchManagerRepository.save(merchManagerModel);
			
			List<TodoModel> todoList = todoRepository.queryTodo(merchManagerModel.getBmid().toString(),0,"bill_merchant");
			if(todoList == null || todoList.size() == 0){
				//添加待办
				TodoModel todo = new TodoModel();
				todo.setUnNo("110003");
				todo.setBatchJobNo(merchManagerModel.getBmid().toString());
				todo.setMsgSendUnit(mtie.getUnno());
				todo.setMsgSender(mtie.getMaintainUid());
				todo.setMsgTopic("待审批商户入网报单");
				todo.setMsgSendTime(new Date());
				todo.setTranCode("10420");	//菜单代码
				todo.setBizType("bill_merchant");
				todo.setStatus(0);			//0-未办，1-已办
				todoRepository.save(todo);
			}
		}else {
			//添加待办
			TodoModel todo = new TodoModel();
			todo.setUnNo("110003");
			todo.setBatchJobNo(model.getBmtid().toString());
			todo.setMsgSendUnit(mtie.getUnno());
			todo.setMsgSender(mtie.getMaintainUid());
			todo.setMsgTopic("待审批商户增机申请");
			todo.setMsgSendTime(new Date());
			todo.setTranCode("10430");	//菜单代码
			todo.setBizType("bill_terminal");
			todo.setStatus(0);			//0-未办，1-已办
			todoRepository.save(todo);
		}
		rs.setReturnMsg("提交成功");
		rs.setReturnCode(ReturnCode.SUCCESS);
		return rs;
	}
	public RespEntity findTerInfo(MerchantTerminalInfoEntity mtie) {
		RespEntity rs = new RespEntity();
		//查询商户下的所有终端
		List<MerchantTerminalInfoModel> terInfo = merchantTerminalInfoRepository.findTerInfo(mtie.getMid());
		
		rs.setReturnMsg("提交成功");
		rs.setReturnCode(ReturnCode.SUCCESS);
		rs.setReturnBody(terInfo);
		return rs;
	}
}
