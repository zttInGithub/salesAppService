package com.hrtp.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.hrtp.salesAppService.service.BillAgentInfoService;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
@Configuration
@Component
public class MyJedisPool {

	private JedisPool pool = null;

	private static Logger log = LoggerFactory.getLogger(MyJedisPool.class);

	//Redis服务器IP
	@Value("${redis_host}")
	private String host ;
	//Redis的端口号
	@Value("${redis_port}")
	private int port ;
	//访问密码
	@Value("${redis_passwd}")
	private String auth ;

	//可用连接实例的最大数目，默认值为8；
	//如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
	@Value("${jedis_maxactive}")
	private int max_active ;

	//控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8。
	@Value("${jedis_maxidle}")
	private int max_idle ;

	//等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException；
	@Value("${jedis_maxwait}")
	private int max_wait ;
	@Value("${jedis_timeout}")
	private int timeOut;
	// 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
	@Value("${jedis_test_no_borrow}")
	private boolean test_no_borrow;

	/**
	 * 构建redis连接池
	 * 
	 * @param ip
	 * @param port
	 * @return JedisPool
	 */
	@Bean
	public JedisPool makeJedisPool() {
		if (pool == null) {
			log.info("jedisPool初始化...");
			// jedispool为null则初始化，
			JedisPoolConfig config = new JedisPoolConfig();
			// 控制一个pool可分配多少个jedis实例，通过pool.getResource()来获取；

			// 如果赋值为-1，则表示不限制；如果pool已经分配了maxTotal个jedis实例，则此时pool的状态为exhausted(耗尽）.
			config.setMaxTotal(max_active);
			// 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例
			config.setMaxIdle(max_idle);
			// 表示当borrow(引入)一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException；
			config.setMaxWaitMillis(max_wait);
			// 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
			config.setTestOnBorrow(test_no_borrow);
			pool = new JedisPool(config, host, port, timeOut, auth);
		}
		return pool;
	}

}
