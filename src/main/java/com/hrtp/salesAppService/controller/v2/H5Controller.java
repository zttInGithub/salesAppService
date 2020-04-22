package com.hrtp.salesAppService.controller.v2;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * H5Controller
 * <p>
 * This is description
 * </p>
 *
 * @author xuegangliu 2019/07/25
 * @since 1.8
 **/
@Controller
@RequestMapping(value = "")
public class H5Controller {

    @RequestMapping(value = "/test")
    public String test(ModelMap map){
        map.addAttribute("name","SalesAppService");
        return "index";
    }

    @RequestMapping(value = "/h5",method = RequestMethod.GET)
    public String index(HttpServletRequest request,ModelMap map){
        map.addAttribute("unno",request.getParameter("unno"));
        map.addAttribute("userId",request.getParameter("userId"));
        map.addAttribute("agentName",request.getParameter("agentName"));
        return "login";
    }

    @RequestMapping(value = "/loginOk")
    public String ok(){
        return "loginOk";
    }
}
