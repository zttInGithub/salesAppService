package com.hrtp.salesAppService.controller.v2;

import org.springframework.util.StringUtils;

/**
 * BaseController
 * <p>
 * This is description
 * </p>
 *
 * @author xuegangliu 2019/07/10
 * @since 1.8
 **/
public class BaseController {

    /**
     * 校验必填参数是否为空
     * @param args
     * @return
     */
    public boolean checkParamsHashNull(Object... args){
        for(Object t:args){
            if(null==t){
                return true;
            }
            if(t instanceof String){
                if(StringUtils.isEmpty((String)t)){
                    return true;
                }
            }
        }
        return false;
    }
}
