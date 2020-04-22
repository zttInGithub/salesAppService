package com.hrtp.redis;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
@Component
public class JedisSource {
	private static  Logger logger = LogManager.getLogger(JedisSource.class);

	@Autowired
	private JedisPool myJedisPool;

	/**
	 * 获取数据
	 * @param key
	 * @return
	 */
	public String get(String key) {
		String value = null;
		Jedis jedis = null;
		try {
			jedis = myJedisPool.getResource();
			value = jedis.get(key);
		} catch (Exception e) {
			// TODO: handle exception
			// 释放redis对象
			myJedisPool.returnBrokenResource(jedis);
			logger.error("fail to get data from jedis ", e);
			return "fail";
		} finally {
			// 返还到连接池
			myJedisPool.returnResource(jedis);
		}
		return value;
	}

	/**
	 * 给key赋值，并生命周期设置为seconds
	 * @param key
	 * @param seconds
	 *            生命周期 秒为单位
	 * @param value
	 */
	public boolean setx(String key, int seconds, String value) {
		Jedis jedis = null;
		try {
			jedis = myJedisPool.getResource();
			jedis.setex(key, seconds, value);
		} catch (Exception e) {
			// 释放redis对象
			myJedisPool.returnBrokenResource(jedis);
			logger.error("fail to set key and seconds", e);
			return false;
		} finally {
			// 返还到连接池
			myJedisPool.returnResource(jedis);
		}
		return true;
	}
	/**
	 * 根据key值来删除已经存在的key-value;
	 * @param key
	 * @return
	 */
	public int removex(String key) {
		int temp = 0;
		Jedis jedis = null;
		try {
			jedis = myJedisPool.getResource();
			temp = jedis.del(key).intValue();
		} catch (Exception e) {
			// TODO: handle exception
			myJedisPool.returnBrokenResource(jedis);
			logger.error("fail to delete the key-value according to the key", e);
			return -1;
		} finally {
			//返回redis实例
			myJedisPool.returnResource(jedis);
		}
		return temp;
	}


	/**
	 * 判断key是否存在
	 */
	public boolean exists(String key) {
		Jedis jedis = null;
		boolean flag = false;
		try {
			jedis = myJedisPool.getResource();
			flag = jedis.exists(key);
		} catch (Exception e) {
			// 释放redis对象
			myJedisPool.returnBrokenResource(jedis);
			logger.error("fail to set key and seconds", e);
			return false;
		} finally {
			// 返还到连接池
			myJedisPool.returnResource(jedis);
		}
		return flag;
	}

	/**
	 * 选择存储位置
	 */
	public void selectx(int a) {
		Jedis jedis = null;
		boolean flag = false;
		try {
			jedis = myJedisPool.getResource();
			jedis.select(a);
		} catch (Exception e) {
			// 释放redis对象
			myJedisPool.returnBrokenResource(jedis);
			logger.error("fail to set key and seconds", e);
		} finally {
			// 返还到连接池
			myJedisPool.returnResource(jedis);
		}
	}
	
	/**
	 * 读取map中的key
	 */
	public String queryMapKey(String a,String b) {
		Jedis jedis = null;
		boolean flag = false;
		String body = "";
		try {
			jedis = myJedisPool.getResource();
			List<String> result = jedis.hmget(a, b);
			body = result.get(0);
		} catch (Exception e) {
			// 释放redis对象
			myJedisPool.returnBrokenResource(jedis);
			logger.error("fail to set key and seconds", e);
		} finally {
			// 返还到连接池
			myJedisPool.returnResource(jedis);
		}
		return body;
	}
}
