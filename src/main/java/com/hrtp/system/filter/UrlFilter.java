package com.hrtp.system.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * UrlFilter
 * description 系统Url过滤
 * create by lxj 2018/8/20
 **/
@Deprecated
public class UrlFilter extends HttpServlet implements Filter {
    private static Logger log = LoggerFactory.getLogger(UrlFilter.class);
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws
            IOException, ServletException {
        HttpServletResponse hresponse = (HttpServletResponse) response;
        HttpServletRequest hrequest = (HttpServletRequest) request;
        HttpSession session = hrequest.getSession(true);
        Object user = session.getAttribute("salesUser");
        String url = hrequest.getRequestURI();
        String xcode=hrequest.getHeader("xcode");

        //TODO 上线去掉xcode判断，本地测试需要
        if (user == null) {
            if (url.indexOf("/app") > 0 && !isTrue(url)) {
                log.info("手机端请求url：==>" + url);
                hresponse.getWriter().write("{\"NEED_LOGIN\":true}");
                return;
            }
        }
        if (url.toLowerCase().indexOf("/dologin") > 0 && isTrue(url)) {
            session.setMaxInactiveInterval(6 * 60 * 60);//秒
        }
        filterChain.doFilter(request, response);
    }

    private Boolean isTrue(String url) {
        //过滤空值
        if (url == null) {
            log.info("null");
            return false;
        }
        if ("".equals(url)) {
            return false;
        }
        //过滤jsp
        if (url.toLowerCase().indexOf(".jsp") > 0) {
            return false;
        }
        //过滤html
        if (url.toLowerCase().indexOf(".html") > 0) {
            return false;
        }

        //app登录
        if (url.toLowerCase().indexOf("/dologin") > 0) {
            return true;
        }

        return false;
    }

    @Override
    public void destroy() {
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }
}
