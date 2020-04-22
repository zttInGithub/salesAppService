package com.hrtp.salesAppService.controller.v2;

import cn.jiguang.common.utils.StringUtils;
import com.hrtp.salesAppService.entity.appEntity.v2.BillTerminalConfigEntity;
import com.hrtp.salesAppService.entity.appEntity.v2.CommonReqEntity;
import com.hrtp.salesAppService.entity.appEntity.RespEntity;
import com.hrtp.salesAppService.service.v2.PlusService;
import com.hrtp.system.costant.ReturnCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * PlusTerminalController
 * <p>
 * This is description
 * </p>
 *
 * @author xuegangliu 2019/07/12
 * @since 1.8
 **/
@RestController
@RequestMapping(value = "/app/terminal")
public class PlusTerminalController extends BaseController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PlusService plusService;

    /**
     * 上级机构信息获取
     * @param commonReqEntity
     * @return
     */
    @RequestMapping("/getParentUnnoInfo")
    public RespEntity getParentUnno(@RequestBody CommonReqEntity commonReqEntity){
        if(checkParamsHashNull(commonReqEntity.getUnno(),commonReqEntity.getUnLvl(),commonReqEntity.getUserId())){
            return new RespEntity(ReturnCode.FALT,"缺少请求参数");
        }
        Map parent = plusService.getParentUnno(commonReqEntity);
        if(null!=parent) {
            return new RespEntity(ReturnCode.SUCCESS,"查询上级代理成功",parent);
        }else{
            return new RespEntity(ReturnCode.FALT,"未查询到上级代理");
        }
    }

    /**
     * 下级代理查询
     * @param commonReqEntity
     * @return
     */
    @RequestMapping("/getSubUnnoList")
    public RespEntity getSubUnnoList(@RequestBody CommonReqEntity commonReqEntity){
        if(checkParamsHashNull(commonReqEntity.getUnno(),commonReqEntity.getUnLvl(),commonReqEntity.getUserId(),commonReqEntity.getPage(),commonReqEntity.getSize())){
            return new RespEntity(ReturnCode.FALT,"缺少请求参数");
        }
        Map map = plusService.getSubUnnoList(commonReqEntity);
        if(map.size()>0) {
            return new RespEntity(ReturnCode.SUCCESS,"查询下级代理成功",map);
        }else{
            return new RespEntity(ReturnCode.SUCCESS,"未查询到下级代理",map);
        }
    }

    /**
     * 终端下发/回拨
     * @param commonReqEntity
     */
    @RequestMapping("/sendSubUnnoTerminal")
    public RespEntity sendSubUnnoTerminal(@RequestBody CommonReqEntity commonReqEntity){
        if(checkParamsHashNull(new Object[] {commonReqEntity.getUnno(),commonReqEntity.getUnLvl(),
                commonReqEntity.getType(), commonReqEntity.getStartTermid(),commonReqEntity.getEndTermid(),
                commonReqEntity.getTermidCount(),commonReqEntity.getUserId()})){
            return new RespEntity(ReturnCode.FALT,"缺少请求参数");
        }else if(commonReqEntity.getType().intValue()==2 && StringUtils.isNotEmpty(commonReqEntity.getUpperUnno())){
            return new RespEntity(ReturnCode.FALT,"此功能暂未开放!");
        }
        return plusService.sendSubUnnoTerminal(commonReqEntity);
    }

    /**
     * 终端下发/回拨记录
     * @param commonReqEntity
     */
    @RequestMapping("/getUnnoTerminalRecord")
    public RespEntity getUnnoTerminalRecord(@RequestBody CommonReqEntity commonReqEntity){
        if(checkParamsHashNull(commonReqEntity.getUnno(),commonReqEntity.getUnLvl(),commonReqEntity.getType(),commonReqEntity.getUserId())){
            return new RespEntity(ReturnCode.FALT,"缺少请求参数");
        }
        List<BillTerminalConfigEntity> list = plusService.getUnnoTerminalRecord(commonReqEntity);
        if(list.size()>0) {
            return new RespEntity(ReturnCode.SUCCESS,"查询记录成功",list);
        }else{
            return new RespEntity(ReturnCode.SUCCESS,"未查询到记录信息",list);
        }
    }

    /**
     * 终端下发/回拨详情
     * @param commonReqEntity
     */
    @RequestMapping("/getTerminalDetail")
    public RespEntity getTerminalDetail(@RequestBody CommonReqEntity commonReqEntity){
        if(checkParamsHashNull(commonReqEntity.getBtcId(),commonReqEntity.getUnLvl(),commonReqEntity.getUserId(),commonReqEntity.getUnno())){
            return new RespEntity(ReturnCode.FALT,"缺少请求参数");
        }
        BillTerminalConfigEntity billTerminalConfigEntity = plusService.getTerminalConfigById(commonReqEntity);
        if(null!=billTerminalConfigEntity) {
            return new RespEntity(ReturnCode.SUCCESS,"查询详情成功",billTerminalConfigEntity);
        }else{
            return new RespEntity(ReturnCode.SUCCESS,"未查询到详情信息",billTerminalConfigEntity);
        }
    }
}
