package com.hrtp.system.config;

import com.hrtp.system.filter.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.util.StringUtils;

/**
 * MyWebSecurityConfig
 * description
 * create by lxj 2018/8/23
 **/
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class MyWebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${PUSH_IP_WHITE}")
    private String PUSH_IP_WHITE;

    private static final String[] AUTH_WHITELIST = {
            // system url
            "/app/doLogin",
            "/app/login/returnStatus",
            "/app/login/returnUser",
            "/app/checkMsg",
            "/app/sendMsgForProfit",
            "/app/sendPasswordMsg",
            "/app/checkPasswordMsg",
            "/app/myInfo/sendMsg",
            "/app/myInfo/updateNewUnno",
            "/app/homePage/getVersion",
            "/h5",
            "/loginOk",
            "/**/*.js",
            "/**/*.css",
            "/**/*.gif",
            "/**/*.png",
    };

    // 设置扩展功能ip白名单
    private static final String[] ADMIN_PATHLIST = {
            // druid url
            "/druid/**",
            // pushmsg
            "/push/**",
            // swagger static
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**"
    };

    // 设置 HTTP 验证规则
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                .antMatchers(AUTH_WHITELIST).permitAll()
//                .antMatchers(ADMIN_PATHLIST).access("hasIpAddress('10.51.130.155') or hasIpAddress('10.51.130.169')")
                .antMatchers(ADMIN_PATHLIST).access(parseString(PUSH_IP_WHITE))
//                .antMatchers(ADMIN_PATHLIST).hasIpAddress(PUSH_IP_WHITE)
                .anyRequest().not().authenticated()  // 所有请求需要身份认证
                .and()
                .addFilter(new JwtAuthenticationFilter(authenticationManager()));
    }

    private static String parseString(String whiteIps) {
        if (StringUtils.isEmpty(whiteIps)) {
            return "hasIpAddress('127.0.0.1')";
        }
        String[] ips = whiteIps.split("\\|");
        int len = ips.length;
        String ipstr = "";
        for (int i = 0; i < len; i++) {
            ipstr += "hasIpAddress('" + ips[i] + "')";
            if (i != len - 1) {
                ipstr += " or ";
            }
        }
        return ipstr;
    }
}
