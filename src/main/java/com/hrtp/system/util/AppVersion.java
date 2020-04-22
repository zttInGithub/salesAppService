package com.hrtp.system.util;

import cn.jiguang.common.utils.StringUtils;
/**
 * 判断发出当前请求的APP版本号是否是version2之前的历史版本
 * @author YQ
 */
public class AppVersion {
	//历史版本返回true
	public static boolean is_version_great_than(String version,String version2) {
		boolean result = true;
		if(version2.equals(version.trim()))//如果相同直接跳出
			return false;
		if (!StringUtils.isEmpty(version) && version.indexOf(".") >= 0) {
			String[] version_num = version.split("\\."); //必须转义
			String[] cur_num = version2.split("\\.");
			try{           
				for (int i = 0; i < 3; i++) {
					if (Integer.valueOf(cur_num[i]) < Integer.valueOf(version_num[i])) { //当有一节大于就跳出
						result = false;
						break;
					}
				}
			}catch(Exception e){}
		}
		return result;
	}

}
