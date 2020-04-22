package com.hrtp.salesAppService.controller;

import com.hrtp.salesAppService.entity.appEntity.BillAgentInfoEntity;
import com.hrtp.salesAppService.entity.appEntity.MessageDetaliEntity;
import com.hrtp.salesAppService.entity.appEntity.MessageEntity;
import com.hrtp.salesAppService.entity.appEntity.ReqEntity;
import com.hrtp.salesAppService.entity.appEntity.RespEntity;
import com.hrtp.salesAppService.entity.appEntity.UnnoNoticeEntity;
import com.hrtp.salesAppService.service.HomePageService;
import com.hrtp.system.costant.ReturnCode;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * HomePageController
 * description
 * create by lxj 2018/8/24
 **/
@RestController
@RequestMapping("/app/homePage")
public class HomePageController {
	private static final Logger logger = LoggerFactory.getLogger(HomePageController.class);
	@Autowired
	private HomePageService homePageService;

	/**
	 * 首页查询公告
	 * @param noticeEntity
	 * @return
	 */
	@RequestMapping("/getAnnounceList")
	@ResponseBody
	public RespEntity getAnnounceList(@RequestBody UnnoNoticeEntity noticeEntity){
		if (logger.isInfoEnabled()) {
			logger.info("首页查询公告==>参数{}",noticeEntity);
		}
		RespEntity rs = homePageService.getAnnounceList(noticeEntity);
		if (logger.isInfoEnabled()) {
			logger.info("返回参数{}",rs);
		}
		return rs;
	}

	/**
	 * 首页查看广告
	 * @return
	 */
	@RequestMapping("/getBannerList")
	@ResponseBody
	public RespEntity getBannerList(@RequestBody BillAgentInfoEntity reqEntity){
		if (logger.isInfoEnabled()) {
			logger.info("首页查询广告==>参数{}",reqEntity);
		}
		RespEntity rs = homePageService.getBannerList(reqEntity);
		if (logger.isInfoEnabled()) {
			logger.info("返回参数{}",rs);
		}
		return rs;
	}

	/**
	 * 首页消息列表
	 * @return
	 */
	@RequestMapping("/getNoticeList")
	@ResponseBody
	public RespEntity getNoticeList(@RequestBody MessageEntity reqEntity){
		if (logger.isInfoEnabled()) {
			logger.info("首页查询消息列表==>参数{}",reqEntity);
		}
		RespEntity rs = homePageService.getNoticeList(reqEntity);
		if (logger.isInfoEnabled()) {
			logger.info("返回参数{}",rs);
		}
		return rs;
	}
	/**
	 *版本信息
	 * @return
	 */
	@RequestMapping("/getVersion")
	@ResponseBody
	public RespEntity getVersion(@RequestBody ReqEntity reqEntity){
		if (logger.isInfoEnabled()) {
			logger.info("查询版本信息==>参数{}",reqEntity);
		}
		RespEntity rs = homePageService.getVersion(reqEntity);
		if (logger.isInfoEnabled()) {
			logger.info("返回参数{}",rs);
		}
		return rs;
	}
	/**
	 *修改消息状态
	 * @return
	 */
	@RequestMapping("/changeMsgStatus")
	@ResponseBody
	public RespEntity changeMsgStatus(@RequestBody MessageDetaliEntity reqEntity){
		if (logger.isInfoEnabled()) {
			logger.info("修改消息状态==>参数{}",reqEntity);
		}
		RespEntity rs = homePageService.updateMsgStatus(reqEntity);
		if (logger.isInfoEnabled()) {
			logger.info("返回参数{}",rs);
		}
		return rs;
	}

	/**
	 * 查询今日和昨日的交易、分润、激活数量、新增商户数等信息
	 * @param int flag,String unno
	 * @return
	 * @author yq
	 */
	@RequestMapping("/queryAll")
	@ResponseBody
	public RespEntity queryAll(@RequestBody MessageDetaliEntity reqEntity){
		if (logger.isInfoEnabled()) {
			logger.info("首页查询请求参数{}",reqEntity);
		}
		RespEntity rs = homePageService.queryAll(reqEntity);
		if (logger.isInfoEnabled()) {
			logger.info("首页查询返回参数{}",rs);
		}
		return rs;
	}

	/**
	 * 交易类型查询
	 * @param int flag,String unno,String merType
	 * @return
	 * @author yq
	 */
	@RequestMapping("/queryType")
	@ResponseBody
	public RespEntity queryType(@RequestBody MessageDetaliEntity reqEntity){
		if (logger.isInfoEnabled()) {
			logger.info("交易类型查询请求参数{}",reqEntity);
		}
		List<Map<String, String>> list = homePageService.queryType(reqEntity);
		RespEntity rs = new RespEntity();
		rs.setReturnCode(ReturnCode.SUCCESS);
		rs.setReturnMsg("查询成功");
		rs.setReturnBody(list);
		if (logger.isInfoEnabled()) {
			logger.info("交易类型查询返回参数{}",rs);
		}
		return rs;
	}

	/**
	 * 交易查询  -老
	 * @param int flag,String unno,String merType
	 * @return
	 * @author yq
	 */
	@RequestMapping("/queryTxn")
	@ResponseBody
	public RespEntity queryTxn(@RequestBody MessageDetaliEntity reqEntity){
		if (logger.isInfoEnabled()) {
			logger.info("交易查询请求参数{}",reqEntity);
		}
		RespEntity rs = homePageService.queryTxn(reqEntity);
		if (logger.isInfoEnabled()) {
			logger.info("交易查询返回参数{}",rs);
		}
		return rs;
	}

	/**
	 * 分润查询
	 * @param int flag,String unno,String merType
	 * @return
	 * @author yq
	 */
	@RequestMapping("/queryProfit")
	@ResponseBody
	public RespEntity queryProfit(@RequestBody MessageDetaliEntity reqEntity){
		if (logger.isInfoEnabled()) {
			logger.info("分润查询请求参数{}",reqEntity);
		}
		RespEntity rs = homePageService.queryProfit(reqEntity);
		if (logger.isInfoEnabled()) {
			logger.info("分润查询返回参数{}",rs);
		}
		return rs;
	}

	/**
	 * 交易、分润图查询
	 * @param int flag,String unno,String merType
	 * @return
	 * @author yq
	 */
	@RequestMapping("/queryTxnProfit")
	@ResponseBody
	public RespEntity queryTxnProfit(@RequestBody MessageDetaliEntity reqEntity){
		if (logger.isInfoEnabled()) {
			logger.info("交易分润图查询请求参数{}",reqEntity);
		}
		RespEntity rs = homePageService.queryTxnProfit(reqEntity);
		if (logger.isInfoEnabled()) {
			logger.info("交易分润图查询返回参数{}",rs);
		}
		return rs;
	}
	
	/**
	 * 月激活数汇总
	 * @param int flag,String unno,String merType
	 * @return
	 * @author yq
	 */
	@RequestMapping("/queryCashBack")
	@ResponseBody
	public RespEntity queryCashBack(@RequestBody MessageDetaliEntity reqEntity){
		if (logger.isInfoEnabled()) {
			logger.info("设备激活数汇总查询请求参数{}",reqEntity);
		}
		RespEntity rs = homePageService.queryCashBack(reqEntity);
		if (logger.isInfoEnabled()) {
			logger.info("设备激活数量汇总查询返回参数{}",rs);
		}
		return rs;
	}

	/**
	 * 交易总金额/分润时间线
	 * @param int flag,String unno,String merType，String sOrU
	 * @return
	 * @author yq
	 */
	@RequestMapping("/queryDay")
	@ResponseBody
	public RespEntity queryDay(@RequestBody MessageDetaliEntity reqEntity){
		if (logger.isInfoEnabled()) {
			logger.info("时间线"+reqEntity.getsOrU()+"查询请求参数{}",reqEntity);
		}
		int a = reqEntity.getsOrU();
		if(a!=1||a!=2||a!=3||a!=4){
			RespEntity rs = new  RespEntity();
			rs.setReturnCode(ReturnCode.FALT);
			rs.setReturnMsg("参数有误！");
		}
		RespEntity rs = homePageService.queryDay(reqEntity);
		if (logger.isInfoEnabled()) {
			logger.info("时间线"+reqEntity.getsOrU()+"查询返回参数{}",rs);
		}
		return rs;
	}

	/**
	 * 激活终端数时间线
	 * @param int flag,String unno,String merType，String sOrU
	 * @return
	 * @author yq
	 */
	@RequestMapping("/queryCashBackDay")
	@ResponseBody
	public RespEntity queryCashBackDay(@RequestBody MessageDetaliEntity reqEntity){
		if (logger.isInfoEnabled()) {
			logger.info("激活终端数时间线请求参数{}",reqEntity);
		}
		RespEntity rs = homePageService.queryCashBackDay(reqEntity);
		if (logger.isInfoEnabled()) {
			logger.info("激活终端数时间线请求返回参数{}",rs);
		}
		return rs;
	}
	
	/**
	 * 查询下级机构名称、机构号和交易金额汇总，分润金额汇总
	 * @param String unno，int flag,String merType,
	 * @return
	 * @author yq
	 */
	@RequestMapping("/queryUnnoCashBack")
	@ResponseBody
	public RespEntity queryUnnoCashBack(@RequestBody MessageDetaliEntity reqEntity){
		if (logger.isInfoEnabled()) {
			logger.info("查询下级代理激活终端数请求参数{}",reqEntity);
		}
		RespEntity rs = homePageService.queryUnnoCashBack(reqEntity);
		if (logger.isInfoEnabled()) {
			logger.info("查询下级代理激活终端数返回参数{}",rs);
		}
		return rs;
	}
	
	/**
	 * 查询下级机构名称、机构号和交易金额汇总，分润金额汇总
	 * @param String unno，int flag,String merType,
	 * @return
	 * @author yq
	 */
	@RequestMapping("/queryUnno")
	@ResponseBody
	public RespEntity queryUnno(@RequestBody MessageDetaliEntity reqEntity){
		if (logger.isInfoEnabled()) {
			logger.info("查询下级代理信息请求参数{}",reqEntity);
		}
		RespEntity rs = homePageService.queryUnno(reqEntity);
		if (logger.isInfoEnabled()) {
			logger.info("查询下级代理信息返回参数{}",rs);
		}
		return rs;
	}
	
	/**
	 * 交易查询  -新
	 * @param int flag,String unno,String merType
	 * @return
	 * @author yq
	 */
	@RequestMapping("/queryTxnNew")
	@ResponseBody
	public RespEntity queryTxnNew(@RequestBody MessageDetaliEntity reqEntity){
		if (logger.isInfoEnabled()) {
			logger.info("交易查询请求参数{}",reqEntity);
		}
		RespEntity rs = homePageService.queryTxnNew(reqEntity);
		if (logger.isInfoEnabled()) {
			logger.info("交易查询返回参数{}",rs);
		}
		return rs;
	}

}
