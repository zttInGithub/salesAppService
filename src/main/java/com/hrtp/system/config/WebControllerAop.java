package com.hrtp.system.config;

import com.alibaba.fastjson.JSONObject;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;

/**
 * WebControllerAop
 * description Controller前置后置切面
 * create by lxj 2018/8/21
 **/

public class WebControllerAop {
    private static Logger logger = LoggerFactory.getLogger(WebControllerAop.class);

    @Pointcut("execution(* com.hrtp.salesAppService.controller..*.*(..))")
    public void executeService() {

    }

    //    /**
//     * 前置通知，方法调用前被调用
//     *
//     * @param joinPoint
//     */
//    @Before("executeService()")
//    public void doBeforeAdvice(JoinPoint joinPoint) {
//                RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
//        //从获取RequestAttributes中获取HttpServletRequest的信息
//        HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes
//                .REFERENCE_REQUEST);
//        String rqString = "";
//        try {
//            rqString = GetRequestJsonUtils.getRequestJsonString(request);
//        } catch (IOException e) {
//            throw new AppException(e);
//        }
//        logger.info(rqString);
////        //获取目标方法的参数信息
////        Object[] obj = joinPoint.getArgs();
////        //AOP代理类的信息
////        joinPoint.getThis();
////        //代理的目标对象
////        joinPoint.getTarget();
////        //用的最多 通知的签名
////        Signature signature = joinPoint.getSignature();
////        //代理的是哪一个方法
////        logger.info(signature.getName());
////        //AOP代理类的名字
////        logger.info(signature.getDeclaringTypeName());
////        //AOP代理类的类（class）信息
////        signature.getDeclaringType();
////        //获取RequestAttributes
////        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
////        //从获取RequestAttributes中获取HttpServletRequest的信息
////        HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes
////                .REFERENCE_REQUEST);
////        //如果要获取Session信息的话，可以这样写：
////        //HttpSession session = (HttpSession) requestAttributes.resolveReference(RequestAttributes
// .REFERENCE_SESSION);
////        Enumeration<String> enumeration = request.getParameterNames();
////        Map<String, String> parameterMap = new HashMap<>();
////        while (enumeration.hasMoreElements()) {
////            String parameter = enumeration.nextElement();
////            parameterMap.put(parameter, request.getParameter(parameter));
////        }
////        String str = JSON.toJSONString(parameterMap);
////        if (obj.length > 0) {
////            logger.info("请求的参数信息为：" + str);
////        }
//    }
//
//    /**
//     * 后置返回通知
//     * 这里需要注意的是:
//     * 如果参数中的第一个参数为JoinPoint，则第二个参数为返回值的信息
//     * 如果参数中的第一个参数不为JoinPoint，则第一个参数为returning中对应的参数
//     * returning 限定了只有目标方法返回值与通知方法相应参数类型时才能执行后置返回通知，否则不执行，对于returning对应的通知方法参数为Object类型将匹配任何目标返回值
//     *
//     * @param joinPoint
//     * @param keys
//     */
//    @AfterReturning(value = "execution(* com.hrtp.salesAppService.controller..*.*(..))", returning = "keys")
//    public void doAfterReturningAdvice1(JoinPoint joinPoint, Object keys) {
//        logger.info("第一个后置返回通知的返回值：" + keys);
//    }
//
//    /**
//     * 后置最终通知（目标方法只要执行完了就会执行后置通知方法）
//     * @param joinPoint
//     */
//    @After("executeService()")
//    public void doAfterAdvice(JoinPoint joinPoint) {
//        logger.info("后置通知执行了!!!!");
//    }
    @Around("executeService()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
//        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
//        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
//        HttpServletRequest request = sra.getRequest();
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse();


        String url = request.getRequestURL().toString();
        String method = request.getMethod();
        String uri = request.getRequestURI();
        String queryString = request.getQueryString();
        Enumeration<String> attributeNames = request.getAttributeNames();
        logger.info(attributeNames.toString());
        Enumeration<String> parameterNames = request.getParameterNames();
        logger.info(parameterNames.toString());
        logger.info("请求开始, 各个参数, url: {}, method: {}, uri: {}, params: {}", url, method, uri, queryString);
        ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
        try {
            InputStream inStream = request.getInputStream();
            byte[] buffer = new byte[1024];
            int len = -1;
            while ((len = inStream.read(buffer)) != -1) {
                outSteam.write(buffer, 0, len);
            }
            outSteam.close();
            inStream.close();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        String jsonString = "";
        try {
            jsonString = new String(outSteam.toByteArray(), request.getCharacterEncoding());
            logger.info(jsonString);
        } catch (UnsupportedEncodingException e) {
            logger.error("UnsupportedEncodingException " + new String(outSteam.toByteArray()));
        }
        // result的值就是被拦截方法的返回值
        Object result = pjp.proceed();

        JSONObject gson = new JSONObject();
        logger.info("请求结束，controller的返回值是 " + gson.toJSONString());
        return result;
    }
}
