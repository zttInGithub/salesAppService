package com.hrtp.system.util;

import java.util.HashMap;
import java.util.Map;

import com.hrtp.MQService.util.SpringContextUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hrtp.system.config.PushParamConfig;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.audience.AudienceTarget;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;

public class PutMsgUtil {
	private static Logger logger = LoggerFactory.getLogger(PutMsgUtil.class);
	private static String push_env=SpringContextUtil.getPropValue("PUSH_ENV");
//	private PutMsgUtil(){
//		this.push_env = SpringContextUtil.getPropValue("PUSH_ENV");
//	}

	/**
	 * 消息推送 通用方法
	 * @param uniqueId 用户唯一标识
	 * @param tag 用户所属标签
	 * @param message 需要推送信息
	 * @param title 推送标题
	 * @param type 附加信息-类型
	 * @return
	 */
	public static boolean sendMsgCommon(String uniqueId, String tag, String message, String title, String type) {
	    logger.error("是否推送生产环境push_env:[{}]",push_env);
		Map<String, String> extras = new HashMap<>();
		extras.put("type", type);

		String appKey = PushParamConfig.appKey;
		String masterSecret = PushParamConfig.masterSecret;

		try {
			ClientConfig clientConfig = ClientConfig.getInstance();
			JPushClient jpushClient = new JPushClient(masterSecret, appKey, null, clientConfig);

			PushPayload payload = PushPayload.newBuilder()
			.setPlatform(Platform.android_ios())
			.setAudience(Audience.newBuilder()
					.addAudienceTarget(AudienceTarget.alias(uniqueId)).build())
			.setNotification(Notification.newBuilder().setAlert(message)
					.addPlatformNotification(
							AndroidNotification.newBuilder().addExtras(extras).setTitle(title).build())
					.addPlatformNotification(IosNotification.newBuilder()//.setSound("HYBSuccess.wav")
						.setContentAvailable(true).setMutableContent(true).incrBadge(1)
							// .disableBadge()
							.addExtras(extras).build())
					.build())
			.setOptions(Options.newBuilder().setApnsProduction(Boolean.parseBoolean(push_env)).build())
			.build();
			PushResult settlementResult = jpushClient.sendPush(payload);
			logger.info("推送返回结果(公共) - " + settlementResult.getResponseCode() + ":" + settlementResult);
		} catch (APIConnectionException e) {
			logger.error("推送出现异常：" + e.getMessage());
			return false;
		} catch (APIRequestException e) {
			logger.error("推送出现异常：" + e.getErrorCode());
			return false;
		}
		return true;
	}
	/**
	 * 消息推送 所有
	 * @param message 需要推送信息
	 * @param title 推送标题
	 * @param type 附加信息-类型
	 * @return
	 */
	public static boolean sendMsgAll(String message, String title, String type) {
		Map<String, String> extras = new HashMap<>();
		extras.put("type", type);

		// 向用户发送极光推送
		String appKey = PushParamConfig.appKey;
		String masterSecret = PushParamConfig.masterSecret;

		try {
			ClientConfig clientConfig = ClientConfig.getInstance();
			JPushClient jpushClient = new JPushClient(masterSecret, appKey, null, clientConfig);

			PushPayload payload = PushPayload.alertAll(message);
			PushResult settlementResult = jpushClient.sendPush(payload);
			logger.info("推送返回结果 - " + settlementResult.getResponseCode() + ":" + settlementResult);
		} catch (APIConnectionException e) {
			logger.error("推送出现异常：" + e.getMessage());
			return false;
		} catch (APIRequestException e) {
			logger.error("推送出现异常：" + e.getErrorCode());
			return false;
		}
		return true;
	}
    public static void main(String[] args) {
//	   sendMsgAll( "测试推送内容666", "测试推送66", "1");
		PutMsgUtil.sendMsgCommon("153078", null, "测试推送内容666", "测试推送66", "2");
	}
}
