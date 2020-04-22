package com.hrtp.salesAppService.controller.v2;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.hrtp.salesAppService.entity.appEntity.RespEntity;
import com.hrtp.salesAppService.entity.appEntity.v2.CommonReqEntity;
import com.hrtp.salesAppService.service.v2.PlusService;
import com.hrtp.system.costant.ReturnCode;
import com.hrtp.system.costant.SysParam;
import com.hrtp.system.util.AppVersion;

/**
 * PlusProfitTmpController
 * <p>
 * This is description
 *
 * @author xuegangliu 2019/07/08
 * @since 1.8
 **/
@RestController
@RequestMapping(value = "/app/profit")
public class PlusProfitTmpController extends BaseController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private PlusService plusService;

	/**
	 * 产品名称列表(上级为该代理分配的产品模板)
	 * @param commonReqEntity
	 */
	@RequestMapping("/getProfitProd")
	public RespEntity getProfitProd(@RequestBody CommonReqEntity commonReqEntity){
		if(checkParamsHashNull(commonReqEntity.getUnno(),commonReqEntity.getUnLvl(),commonReqEntity.getUserId())){
			return new RespEntity(ReturnCode.FALT,"缺少请求参数");
		}
		int a = commonReqEntity.getAppVersion() == null || AppVersion.is_version_great_than(commonReqEntity.getAppVersion(),"2.2.7")?1:2;
		List result = plusService.getProfitProdList(commonReqEntity,a);
		return new RespEntity(ReturnCode.SUCCESS,"查询产品名称列表成功",result);
	}

    //==========================淘汰接口===============================
	/**
	 * 我的分润模板
	 * @param commonReqEntity
	 * @return
	 */
	@RequestMapping("/getProfittemplate")
	public RespEntity getProfittemplate(@RequestBody CommonReqEntity commonReqEntity){
		return new RespEntity(ReturnCode.FALT,"请升级到最新版本的APP进行设置，或在平台上进行设置。");
	}

	/**
	 * 我的分润模板记录
	 * @param commonReqEntity
	 * @return
	 */
	@RequestMapping("/getProfittemplateRecord")
	public RespEntity getProfittemplateRecordInfo(@RequestBody CommonReqEntity commonReqEntity){
		return new RespEntity(ReturnCode.FALT,"请升级到最新版本的APP进行设置，或在平台上进行设置。");
	}

	/**
	 * 下级代理分润模板
	 * @param commonReqEntity
	 * @return
	 */
	@RequestMapping("/getSubUnnoCostList")
	public RespEntity getSubUnnoCostList(@RequestBody CommonReqEntity commonReqEntity){
		return new RespEntity(ReturnCode.FALT,"请升级到最新版本的APP进行设置，或在平台上进行设置。");
	}

	/**
	 * 获取下级的最小产品成本限制
	 * @param commonReqEntity
	 * @return
	 */
	@RequestMapping("/getSubSubMinCost")
	public RespEntity getSubSubMinCost(@RequestBody CommonReqEntity commonReqEntity){
		return new RespEntity(ReturnCode.FALT,"请升级到最新版本的APP进行设置，或在平台上进行设置。");
	}

	/**
	 * 下级代理模板设置
	 * @param commonReqEntity
	 * @return
	 */
	@RequestMapping("/saveSubUnnoCost")
	public RespEntity saveSubUnnoCost(@RequestBody CommonReqEntity commonReqEntity){
		return new RespEntity(ReturnCode.FALT,"请升级到最新版本的APP进行设置，或在平台上进行设置。");
	}
	//========================淘汰接口===============================
	
	//========================我的分润模板============================
	/**
	 * 我的分润模板  -新
	 * @param commonReqEntity
	 * @return
	 * @author YQ    
	 **/
	@RequestMapping("/getProfittemplateNew")
	public RespEntity getProfittemplateNew(@RequestBody CommonReqEntity commonReqEntity){
		if(checkParamsHashNull(commonReqEntity.getUnno(),commonReqEntity.getUnLvl(),commonReqEntity.getAgentId())){
			return new RespEntity(ReturnCode.FALT,"缺少请求参数");
		}
		try{
			List<Map<String,Object>> listMap = plusService.getCurrentUnnoCostNew(commonReqEntity);
			if(listMap.size()>0){
				return new RespEntity(ReturnCode.SUCCESS,"查询我的分润模板成功",listMap);
			}else{
				return new RespEntity(ReturnCode.FALT,"未查询到分润模板信息",listMap);
			}
		}catch(Exception e){
			return new RespEntity(ReturnCode.FALT,"APP暂不支持该活动类型");
		}
	}

	//===================下月生效分润模板==========================
	/**
	 * 下月生效分润模板
	 * @param commonReqEntity
	 * @return
	 * @author YQ    
	 **/
	@RequestMapping("/getProfittemplateNextMonth")
	public RespEntity getProfittemplateNextMonth(@RequestBody CommonReqEntity commonReqEntity){
		if(checkParamsHashNull(commonReqEntity.getUnno(),commonReqEntity.getUnLvl(),commonReqEntity.getAgentId())){
			return new RespEntity(ReturnCode.FALT,"缺少请求参数");
		}
		if(commonReqEntity.getUnLvl()<=2){
			return new RespEntity(ReturnCode.FALT,"运营中心和一代不请求该接口");
		}
		List<Map<String,Object>> listMap = plusService.getCurrentUnnoCostNextMonth(commonReqEntity);
		if(listMap.size()>0){
			return new RespEntity(ReturnCode.SUCCESS,"查询下月生效分润模板成功",listMap);
		}else{
			return new RespEntity(ReturnCode.SUCCESS,"未查询到下月生效分润模板信息，返回当前模板信息",plusService.getCurrentUnnoCostNew(commonReqEntity));
		}
	}

	//=========================历史分润模板记录==================================
	/**
	 * 历史分润模板记录 -新
	 * @param commonReqEntity
	 * @return
	 * @author YQ
	 */
	@RequestMapping("/getProfittemplateRecordNew")
	public RespEntity getProfittemplateRecordNew(@RequestBody CommonReqEntity commonReqEntity){
		if(checkParamsHashNull(commonReqEntity.getUnno(),commonReqEntity.getAgentId(),commonReqEntity.getUnLvl())){
			return new RespEntity(ReturnCode.FALT,"缺少请求参数");
		}
		if(commonReqEntity.getUnLvl()<=2){
			return new RespEntity(ReturnCode.FALT,"一代及以上代理不能请求此接口");
		}
		List<Map<String, Object>> list = plusService.getCurrentUnnoRecordCostNew(commonReqEntity);
		if(list.size()>0){
			return new RespEntity(ReturnCode.SUCCESS,"查询我的分润模板记录成功",list);
		}else{
			return new RespEntity(ReturnCode.FALT,"未查询到我的分润模板记录信息",list);
		}
	}
	
	//=================下级代理分润模板展示==========================
	/**
	 * 下级代理分润模板展示--2.2.5之前老版本不支持特殊活动模板
	 * @author YQ
	 */
	@RequestMapping("/queryProfittemplateRecordByUnno")
	public RespEntity queryProfittemplateRecordByUnno(@RequestBody CommonReqEntity commonReqEntity){
		if(checkParamsHashNull(commonReqEntity.getUnno(),commonReqEntity.getUpperUnno(),commonReqEntity.getAgentId(),commonReqEntity.getUnLvl())){
			return new RespEntity(ReturnCode.FALT,"缺少请求参数");
		}
		String agentId = commonReqEntity.getAgentId();
		if(!agentId.equals(SysParam.AGENTID_SYT) && 
				!SysParam.AGENTID_PLUS.equals(agentId) && !SysParam.AGENTID_MD.equals(agentId))
			return new RespEntity(ReturnCode.FALT,"请升级至最新版本APP即可以设置该模板");
		List<Map<String, Object>> list = plusService.queryProfittemplateRecordByUnno(commonReqEntity);
		if(list.size()>0){
			return new RespEntity(ReturnCode.SUCCESS,"查询下级分润模板记录成功",list);
		}else{
			return new RespEntity(ReturnCode.FALT,"未查询到下级分润模板记录信息",list);
		}
	}
	
	/**
	 * 下级代理分润模板展示--2.2.5版本特殊活动
	 * @author YQ
	 */
	@RequestMapping("/queryProfittemplateRecordByUnnoFor2_5")
	public RespEntity queryProfittemplateRecordByUnnoFor2_5(@RequestBody CommonReqEntity commonReqEntity){
		if(checkParamsHashNull(commonReqEntity.getUnno(),commonReqEntity.getUpperUnno(),commonReqEntity.getAgentId(),commonReqEntity.getUnLvl())){
			return new RespEntity(ReturnCode.FALT,"缺少请求参数");
		}
		List<Map<String, Object>> list = plusService.queryProfittemplateRecordByUnnoFor2_7(1,commonReqEntity);//1为秒到老接口
		if(list.size()>0){
			return new RespEntity(ReturnCode.SUCCESS,"查询下级分润模板记录成功",list);
		}else{
			return new RespEntity(ReturnCode.FALT,"未查询到下级分润模板记录信息",list);
		}
	}
	
	/**
	 * 下级代理分润模板展示--2.2.6版本:秒到扫码拆分
	 * @author YQ
	 */
	@RequestMapping("/queryProfittemplateRecordByUnnoFor2_6")
	public RespEntity queryProfittemplateRecordByUnnoFor2_6(@RequestBody CommonReqEntity commonReqEntity){
		if(checkParamsHashNull(commonReqEntity.getUnno(),commonReqEntity.getUpperUnno(),commonReqEntity.getAgentId(),commonReqEntity.getUnLvl())){
			return new RespEntity(ReturnCode.FALT,"缺少请求参数");
		}
		List<Map<String, Object>> list = plusService.queryProfittemplateRecordByUnnoFor2_7(2,commonReqEntity);
		if(list.size()>0){
			return new RespEntity(ReturnCode.SUCCESS,"查询下级分润模板记录成功",list);
		}else{
			return new RespEntity(ReturnCode.FALT,"未查询到下级分润模板记录信息",list);
		}
	}
	
	/**
	 * 下级代理分润模板展示--2.2.7版本:收银台活动拆分
	 * @author YQ
	 */
	@RequestMapping("/queryProfittemplateRecordByUnnoFor2_7")
	public RespEntity queryProfittemplateRecordByUnnoFor2_7(@RequestBody CommonReqEntity commonReqEntity){
		if(checkParamsHashNull(commonReqEntity.getUnno(),commonReqEntity.getUpperUnno(),commonReqEntity.getAgentId())){
			return new RespEntity(ReturnCode.FALT,"缺少请求参数");
		}
		List<Map<String, Object>> list = plusService.queryProfittemplateRecordByUnnoFor2_7(2,commonReqEntity);
		if(list.size()>0){
			return new RespEntity(ReturnCode.SUCCESS,"查询下级分润模板记录成功",list);
		}else{
			return new RespEntity(ReturnCode.FALT,"未查询到下级分润模板记录信息",list);
		}
	}
	
	//=======================下级代理分润模板更新===========================
	/**
	 * 下级代理分润模板更新  2.2.5及历史版本（秒到、收银台模板不可用）
	 * @author YQ
	 */
	@RequestMapping("/updteProfittemplateRecordByUnno")
	public RespEntity updateProfittemplateRecordByUnno(@RequestBody CommonReqEntity commonReqEntity){
		if(commonReqEntity.getAgentId().equals(SysParam.AGENTID_MD) || commonReqEntity.getAgentId().equals(SysParam.AGENTID_SYT))
			return new RespEntity(ReturnCode.FALT,"请升级到最新版本的APP进行设置，或在平台上进行设置。");
		RespEntity rs = updateProfittemplateRecordByUnnoFor2_7(commonReqEntity);
		return rs;
	}
	
	/**
	 * 下级代理分润模板更新  2.2.6（收银台活动拆分）
	 * @author YQ
	 */
	@RequestMapping("/updteProfittemplateRecordByUnnoFor2_6")
	public RespEntity updateProfittemplateRecordByUnnoFor2_6(@RequestBody CommonReqEntity commonReqEntity){
		if(commonReqEntity.getAgentId().equals(SysParam.AGENTID_SYT))
			return new RespEntity(ReturnCode.FALT,"请升级到最新版本的APP进行设置，或在平台上进行设置。");
		RespEntity rs = updateProfittemplateRecordByUnnoFor2_7(commonReqEntity);
		return rs;
	}
		
	/**
	 * 下级代理分润模板更新  2.2.7
	 * @author YQ
	 */
	@RequestMapping("/updteProfittemplateRecordByUnnoFor2_7")
	public RespEntity updateProfittemplateRecordByUnnoFor2_7(@RequestBody CommonReqEntity commonReqEntity){
		if(checkParamsHashNull(commonReqEntity.getUnLvl(),commonReqEntity.getUnno(),commonReqEntity.getAgentId(),commonReqEntity.getUpperUnno())){
			return new RespEntity(ReturnCode.FALT,"缺少请求参数");
		}
		if(commonReqEntity.getUnLvl()<=1){
			return new RespEntity(ReturnCode.FALT,"一代以上机构不可以请求该接口");
		}
		int result = 2;
		try{
			result = plusService.updateProfittemplateRecord(commonReqEntity);
		}catch(Exception e){
			e.printStackTrace();
			log.error(commonReqEntity.getUnno()+"模板变更出错了："+e);
		}
		if(result==1){
			return new RespEntity(ReturnCode.FALT,"您设置的成本值不符合规则，请重新设置。");
		}else if(result==0){
			return new RespEntity(ReturnCode.SUCCESS,"费率变更成功");
		}else{
			return new RespEntity(ReturnCode.FALT,"您本级分润模板有误，请联系上级代理维护后，再进行下级代理分润模板设置。");
		}
	}
	
	//=================终端刷卡费率修改=======================
	/**
	 * 根据SN返回能否修改
	 */
	@RequestMapping("/querySNForUpdateProfit")
	public RespEntity querySNForUpdateProfit(@RequestBody CommonReqEntity commonReqEntity){
		if(checkParamsHashNull(commonReqEntity.getUnno(),commonReqEntity.getStartTermid())){
			return new RespEntity(ReturnCode.FALT,"缺少请求参数");
		}
		//判断截至终端是否为空
		if(commonReqEntity.getEndTermid()==null || commonReqEntity.getEndTermid().equals(""))
			commonReqEntity.setEndTermid(commonReqEntity.getStartTermid());
		//校验SN是否为当前机构下，以及区间内SN数量是否小于10台
		String msg = plusService.checkSNForUpdateProfit(1,commonReqEntity);
		if(msg.equals("1")){	
			List<Map<String,String>> list = plusService.querySNForUpdateProfit(commonReqEntity);
			if(list.size()<1)
				return new RespEntity(ReturnCode.FALT,"未查询到范围内的设备");
			return new RespEntity(ReturnCode.SUCCESS,"",list);
		}
		return new RespEntity(ReturnCode.FALT,msg);
	}

	/**
	 * 根据SN返回是否为同一类活动，如果是返回修改格式
	 */
	@RequestMapping("/querySNForActivityUpdateProfit")
	public RespEntity querySNForActivityUpdateProfit(@RequestBody CommonReqEntity commonReqEntity){
		if(checkParamsHashNull(commonReqEntity.getUnno(),commonReqEntity.getSnList())){
			return new RespEntity(ReturnCode.FALT,"缺少请求参数");
		}
		//校验SN是否为当前机构下，以及区间内SN数量是否小于10台
		String msg = plusService.checkSNForUpdateProfit(2,commonReqEntity);
		if(msg.equals("1")){	
			String array[] = commonReqEntity.getSnList().split(",");
			String resultSn = "";
			for(int i = 0;i<array.length;i++){
				if(i==0){
					resultSn = "'"+array[i]+"'";
				}else{
					resultSn += ",'"+array[i]+"'";
				}
			}
			Map<String,String> map = plusService.querySNForActivityUpdateProfit(resultSn,commonReqEntity.getUnno());
			if(map.get("code").equals("00")){
				List<Map<String,String>> list = new ArrayList<Map<String,String>>();
				list.add(map);
				return new RespEntity(ReturnCode.SUCCESS,"查询成功",list);
			}
			return new RespEntity(ReturnCode.FALT,map.get("msg"));
		}
		return new RespEntity(ReturnCode.FALT,msg);
	}

	/**
	 * 普通下拉框变更
	 */
	@RequestMapping("/querySelectUpdateProfit")
	public RespEntity querySelectUpdateProfit(){
		List<Map<String,String>> list = plusService.querySelectUpdateProfit();
		return new RespEntity(ReturnCode.SUCCESS,"",list);
	}

	/**
	 * 极速版下拉框变更-费率
	 */
	@RequestMapping("/querySelectUpdateRate")
	public RespEntity querySelectUpdateRate(){
		List<Map<String,String>> list = plusService.querySelectUpdateRate();
		return new RespEntity(ReturnCode.SUCCESS,"",list);
	}

	/**
	 * 极速版下拉框变更-手续费
	 */
	@RequestMapping("/querySelectUpdateCash")
	public RespEntity querySelectUpdateCash(@RequestBody CommonReqEntity commonReqEntity){
		if(checkParamsHashNull(commonReqEntity.getJisuCash())){
			return new RespEntity(ReturnCode.FALT,"缺少请求参数");
		}
		List<Map<String,String>> list = plusService.querySelectUpdateCash(commonReqEntity);
		return new RespEntity(ReturnCode.SUCCESS,"",list);
	}

	/**
	 * 特殊活动
	 */
	@RequestMapping("/querySelectUpdateSpecial")
	public RespEntity querySelectUpdateSpecial(@RequestBody CommonReqEntity commonReqEntity){
		if(checkParamsHashNull(commonReqEntity.getUnno(),commonReqEntity.getRebateType())){
			return new RespEntity(ReturnCode.FALT,"缺少请求参数");
		}
		Map<String,String> map = plusService.querySelectUpdateSpecial(commonReqEntity);
		return new RespEntity(ReturnCode.SUCCESS,"",map);
	}
	
	/**
	 * 特殊活动押金范围
	 */
	@RequestMapping("/querySelectUpdateSpecialDeposIt")
	public RespEntity querySelectUpdateSpecialDeposIt(@RequestBody CommonReqEntity commonReqEntity){
		if(checkParamsHashNull(commonReqEntity.getUnno(),commonReqEntity.getRebateType())){
			return new RespEntity(ReturnCode.FALT,"缺少请求参数");
		}
		Map<String,String> map = plusService.querySelectUpdateSpecialDeposit(commonReqEntity);
		return new RespEntity(ReturnCode.SUCCESS,"",map);
	}

	/**
	 * 根据SN修改费率
	 */
	@RequestMapping("/updateSnProfit")
	public RespEntity updateSnProfit(@RequestBody CommonReqEntity commonReqEntity){
		if(checkParamsHashNull(commonReqEntity.getUnno(),commonReqEntity.getUserId(),commonReqEntity.getSnList(),
				commonReqEntity.getCostList().getCash(),commonReqEntity.getCostList().getRate())){
			return new RespEntity(ReturnCode.FALT,"缺少请求参数");
		}
		System.out.print(commonReqEntity.toString());
		//校验SN是否为当前机构下，以及区间内SN数量是否小于10台
		String msg = plusService.checkSNForUpdateProfit(2,commonReqEntity);
		if(msg.equals("1")){	
			String array[] = commonReqEntity.getSnList().split(",");
			String resultSn = "";
			for(int i = 0;i<array.length;i++){
				if(i==0){
					resultSn = "'"+array[i]+"'";
				}else{
					resultSn += ",'"+array[i]+"'";
				}
			}
			Map<String,String> map = plusService.querySNForActivityUpdateProfit(resultSn,commonReqEntity.getUnno());
			if(map.get("code").equals("00")){
				//获取格式类型
				String type = map.get("type");
				if(type.equals("3")){//特殊活动类型
					//获取活动类型
					commonReqEntity.setRebateType(Integer.parseInt(map.get("rebateType")));
					Map<String,String> map1 = plusService.querySelectUpdateSpecial(commonReqEntity);
					//判断费率和手续费是否在取值范围内
					if(commonReqEntity.getCostList().getRate().compareTo(new BigDecimal(map1.get("SPECIALRATE1")))==-1
							||commonReqEntity.getCostList().getRate().compareTo(new BigDecimal(map1.get("SPECIALRATE2")))==1
							||commonReqEntity.getCostList().getCash().compareTo(new BigDecimal(map1.get("SPECIALAMT1")))==-1
							||commonReqEntity.getCostList().getCash().compareTo(new BigDecimal(map1.get("SPECIALAMT2")))==1){
						return new RespEntity(ReturnCode.FALT,"刷卡费率变更失败:不在允许范围内");
					}
					//特殊活动是否为可以修改押金的活动类型
					/**if(commonReqEntity.getDeposit() != null && !commonReqEntity.getDeposit().equals("")){
						Map<String,String> mapDeposit = plusService.querySelectUpdateSpecialDeposit(commonReqEntity);
						if(mapDeposit.size()<1)
							return new RespEntity(ReturnCode.FALT,"刷卡费率变更失败:当前活动不允许修改押金金额");
					}**/
				}else if(type.equals("1")){//普通活动类型
					//判断是否为活动90
					if(map.get("rebateType").equals("90")){
						if(plusService.isActive90(1,commonReqEntity).equals("0"))
							return new RespEntity(ReturnCode.FALT,"设备类型与活动模板不匹配，请重新选择");
					}else{
						if(plusService.isActive90(2,commonReqEntity).equals("0"))
							return new RespEntity(ReturnCode.FALT,"设备类型与活动模板不匹配，请重新选择");
					}
				}else{
					//极速版活动类型校验
					if(plusService.checkJisu(commonReqEntity).equals("0"))
						return new RespEntity(ReturnCode.FALT,"设备类型与活动模板不匹配，请重新选择");
				}
				commonReqEntity.setSnList(resultSn);
				boolean flag = plusService.updateSnProfit(commonReqEntity);
				if(flag)
					return new RespEntity(ReturnCode.SUCCESS,"费率修改成功");
				return new RespEntity(ReturnCode.FALT,"刷卡费率变更失败");
			}
			return new RespEntity(ReturnCode.FALT,map.get("msg"));
		}
		return new RespEntity(ReturnCode.FALT,msg);
	}
	//====================================================
	
}
