package com.hrtp.MQService.util;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.audience.AudienceTarget;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import com.hrtp.MQService.common.PushCliParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PushMsgUtil {
	private static final Logger logger = LoggerFactory.getLogger(PushMsgUtil.class);
	private static String push_env=SpringContextUtil.getPropValue("PUSH_ENV");
//	private PushMsgUtil(){
//		this.push_env = SpringContextUtil.getPropValue("PUSH_ENV");
//	}

	/**
	 * 消息推送
	 */
	public static boolean sendMsgByRegistrationId(String appKey, String masterSecret, String registrationId,
			String message, String title, String type, Map<String, String> extras) {
		logger.info("发送推送消息=========registrationId:" + registrationId);
		try {
			ClientConfig clientConfig = ClientConfig.getInstance();
			JPushClient jpushClient = new JPushClient(masterSecret, appKey, null, clientConfig);

			PushPayload payload = PushPayload.newBuilder().setPlatform(Platform.android_ios())
					.setAudience(Audience.newBuilder().addAudienceTarget(AudienceTarget.registrationId(registrationId)).build())
					.setNotification(Notification.newBuilder().setAlert(message)
							.addPlatformNotification(
									AndroidNotification.newBuilder().addExtras(extras).setTitle(title).build())
							.addPlatformNotification(IosNotification.newBuilder()
									.setContentAvailable(true).setMutableContent(true).incrBadge(1).addExtras(extras)
									.build())
							.build())
					.setOptions(Options.newBuilder().setApnsProduction(Boolean.parseBoolean(push_env)).build())
					.build();
			PushResult settlementResult = jpushClient.sendPush(payload);
			logger.info("推送返回结果：" + settlementResult.getResponseCode() + ":" + settlementResult);
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
	 * 消息app内推送alians
	 */
	public static boolean sendMsgByAlians(String appKey, String masterSecret, String alians, String message,
			String title, String type, Map<String, String> extras) {
		logger.info("发送推送消息=========alians:" + alians);
		try {
			ClientConfig clientConfig = ClientConfig.getInstance();
			JPushClient jpushClient = new JPushClient(masterSecret, appKey, null, clientConfig);

			PushPayload payload = PushPayload.newBuilder().setPlatform(Platform.android_ios())
					.setAudience(Audience.newBuilder().addAudienceTarget(AudienceTarget.alias(alians)).build())
					.setMessage(Message.newBuilder().setMsgContent(message).setTitle(title).addExtras(extras).build())
					//.setOptions(Options.newBuilder().setApnsProduction(Boolean.parseBoolean(ConfParams.getValueByKey("PUSH_ENV"))).build())
					.build();
			PushResult settlementResult = jpushClient.sendPush(payload);
			logger.info("推送返回结果：" + settlementResult.getResponseCode() + ":" + settlementResult);
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
	 * 消息app内推送registionid
	 */
	public static boolean sendMsgByRegistionId(String appKey, String masterSecret, String registionid, String message,
			String title, String type, Map<String, String> extras) {
		logger.info("发送推送消息=========registionid:" + registionid);
		try {
			ClientConfig clientConfig = ClientConfig.getInstance();
			JPushClient jpushClient = new JPushClient(masterSecret, appKey, null, clientConfig);

			PushPayload payload = PushPayload.newBuilder().setPlatform(Platform.android_ios())
					.setAudience(Audience.newBuilder().addAudienceTarget(AudienceTarget.registrationId(registionid)).build())
					.setMessage(Message.newBuilder().setMsgContent(message).setTitle(title).addExtras(extras).build())
					//.setOptions(Options.newBuilder().setApnsProduction(Boolean.parseBoolean(ConfParams.getValueByKey("PUSH_ENV"))).build())
					.build();
			PushResult settlementResult = jpushClient.sendPush(payload);
			logger.info("推送返回结果：" + settlementResult.getResponseCode() + ":" + settlementResult);
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
	 * 消息推送 通用方法/单个或批量
	 * @param uniqueId 用户唯一标识
	 * @param tag 用户所属标签
	 * @param message 需要推送信息
	 * @param title 推送标题
	 * @param type 附加信息-类型
	 * @return
	 */
	public static boolean sendMsgCommon(String[] uniqueId, String tag,String appkey,String masterkey, String message,
										String title, String type) {
		Map<String, String> extras = new HashMap<>();
		extras.put("type", type);
		try {
			ClientConfig clientConfig = ClientConfig.getInstance();
			JPushClient jpushClient = new JPushClient(masterkey, appkey, null, clientConfig);
			List<String[]> lists = getBash(uniqueId);
			for(int i= 0; i < lists.size();i ++){
                PushPayload payload = PushPayload.newBuilder()
                        .setPlatform(Platform.android_ios())
                        .setAudience(Audience.newBuilder().addAudienceTarget(AudienceTarget.alias(lists.get(i))).build())
                        .setNotification(Notification.newBuilder().setAlert(message).addPlatformNotification(AndroidNotification.newBuilder().addExtras(extras).setTitle(title).build())
                                .addPlatformNotification(IosNotification.newBuilder().setContentAvailable(true).setMutableContent(true).incrBadge(1)
                                        .addExtras(extras).build())
                                .build())
                        .setOptions(Options.newBuilder().setApnsProduction(Boolean.parseBoolean(push_env)).build())
                        .build();
                PushResult settlementResult = jpushClient.sendPush(payload);
                logger.info("总"+lists.size()+"第"+(i+1)+"次，推送返回结果(公共) - " + settlementResult.getResponseCode() + ":" +
                        settlementResult);
            }
		} catch (APIConnectionException e) {
			logger.error("推送出现异常：" + e.getMessage());
			return false;
		} catch (APIRequestException e) {
			logger.error("推送出现异常：" + e.getErrorCode());
			return false;
		}
		return true;
	}

	private static List<String[]> getBash(String[] registIds){
		ArrayList<String[]> list = new ArrayList<>();
		int max = 1000;
		int batcount = (int) Math.ceil((double) registIds.length / max);
        for (int i = 0; i < batcount;i++) {
            int start = i * max;
            int end = (i+1) * max;
            if (i == batcount - 1) {
                end = registIds.length;
            }
            String newregisids[] = new String[end-start];
            int tem = 0;
            for (int j = start; j < end; j ++) {
                newregisids[tem] = registIds[j];
                tem ++;
            }
            list.add(newregisids);
        }
		return list;
	}

	public static void main(String[] args) {
//	    String [] ids = new String[]{"1","2","3","4","5","6","7","8","9","10","11","12","13","14"};
//	    String [] ids = new String[]{"1","2","3","4"};
//        List<String[]> bash = getBash(ids);
//        System.out.println(bash);

        // sendMsgAll("", "", "测试推送内容666", "测试推送66", "1");
		HashMap<String, String> map = new HashMap<>();
		map.put("type", "exit_login");
		String message = "您的手机号正在其他设备登录，此设备自动退出登录。";
//				 boolean b = sendMsgByRegistrationId(AppSecurityInfo.MiaoDaoApp.APPKEY.toString(),AppSecurityInfo.MiaoDaoApp.MASTERSECRET.toString(),
//				 "13165ffa4e22b7fa562",message,"title","exit_login", map);
		PushCliParam appinfo = PushCliParam.getByAgentId("10006");
		String [] unnos = new String[10001];
		for (int i = 0; i < unnos.length;i ++) {
			if (i % 1000 == 0) {
				unnos[i] = "121000";
			} else {
				unnos[i] = "12"+i;
			}

		}
		boolean b = sendMsgCommon(unnos,null,appinfo.appkey(), appinfo.mastersecret(), "ceshiceshi", "titletitle",
				"exit");
//				boolean b = sendMsgByAlians(AppSecurityInfo.MiaoDaoApp.APPKEY.toString(),
//						AppSecurityInfo.MiaoDaoApp.MASTERSECRET.toString(), "F2CA49E3B13743B4B3B6CEFA0E4F39CC", message,
//						"title", "exit_login", map);
	}
}