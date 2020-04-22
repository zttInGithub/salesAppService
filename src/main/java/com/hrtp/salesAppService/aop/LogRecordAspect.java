package com.hrtp.salesAppService.aop;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;

import static org.apache.commons.codec.CharEncoding.UTF_8;

/**
 * LogRecordAspect
 * description 请求环绕切面
 * create class by lxj 2019/4/2
 **/
@Aspect
@Configuration
public class LogRecordAspect {
	private static final Logger LOGGER = LoggerFactory.getLogger(LogRecordAspect.class);
	//配置不需要打印日志的文件上传接口
	private static final String[] AUTH_WHITELIST = {
			"/SalesAppService/app/merchTask/upmerAccountInfo",
			"/SalesAppService/app/merThreeUpload/saveMerThreeUpload",
			"/SalesAppService/app/merchManager/microMerch",
			"/SalesAppService/app/merchTask/upmerBasicInfo",
			"/SalesAppService/app/mistake/updateMistake2Info",
			"/SalesAppService/app/backOrder/updateBackOrderInfo",
			"/SalesAppService/app/merTwoUpload/saveMerTwoUpload",
			"/SalesAppService/app/myInfo/updateTxnInfo",
			"/SalesAppService/app/merchantUpdateTer/saveMerchantUpdateTer"
    };
	
	@Pointcut("execution (* com.hrtp.salesAppService.controller..*.*(..))")
	public void excudeService() {
	}

	@Before("excudeService()")
	public void exBefore(JoinPoint pjp) {
		RequestAttributes ra = RequestContextHolder.getRequestAttributes();
		ServletRequestAttributes sra = (ServletRequestAttributes) ra;
		HttpServletRequest request = sra.getRequest();
		long startTime = System.currentTimeMillis();
		request.setAttribute("startTime", startTime);
		String method = request.getMethod();
		String uri = request.getRequestURI();
		String queryString = request.getQueryString();
		Object[] args = pjp.getArgs();
		String params = "";
		try {
			if (args.length > 0) {
				if ("POST".equals(method)) {
					Object object = args[0];
					if(!Arrays.asList(AUTH_WHITELIST).contains(uri))
						params = JSON.toJSONString(object);
				} else if ("GET".equals(method)) {
					params = queryString;
				}
				if(params!=null) {
					params = URLDecoder.decode(params, UTF_8);
				}
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		LOGGER.error(Thread.currentThread().getName()+"请求：==>requestMethod:{},uri:{},params:{}", method, uri, params);
	}

	@Around("excudeService()")
	public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
		RequestAttributes ra = RequestContextHolder.getRequestAttributes();
		ServletRequestAttributes sra = (ServletRequestAttributes) ra;
		HttpServletRequest request = sra.getRequest();
		long endTime = System.currentTimeMillis();
		String method = request.getMethod();
		String uri = request.getRequestURI();
		Object result = pjp.proceed();
		long startTime = (long) request.getAttribute("startTime");
		LOGGER.error(Thread.currentThread().getName()+"返回：==>requestMethod:{},uri:{},responseBody:{},elapsed:{}ms.", method, uri,JSON.toJSONString(result,
				SerializerFeature.WriteMapNullValue), (endTime - startTime));
		return result;
	}

	@After("excudeService()")
	public void exAfter(JoinPoint joinPoint) {
	}
}