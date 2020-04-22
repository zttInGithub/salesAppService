package com.hrtp.salesAppService.service;

import com.alibaba.fastjson.JSONObject;
import com.hrtp.salesAppService.dao.CheckRealtxnRepository;
import com.hrtp.salesAppService.dao.MerchantTerminalInfoRepository;
import com.hrtp.salesAppService.dao.UnnoMerInfoRepository;
import com.hrtp.salesAppService.dao.UnnoTxnFenRunRepository;
import com.hrtp.salesAppService.entity.appEntity.CheckRealtxnEntity;
import com.hrtp.salesAppService.entity.appEntity.ReqTxnDetailDataEntity;
import com.hrtp.salesAppService.entity.appEntity.RespEntity;
import com.hrtp.salesAppService.entity.appEntity.RespTxnDetailDataEntity;
import com.hrtp.salesAppService.entity.appEntity.TxnFenRunEntity;
import com.hrtp.salesAppService.entity.ormEntity.MerchantTerminalInfoModel;
import com.hrtp.salesAppService.entity.ormEntity.UnnoMerInfoModel;
import com.hrtp.system.common.GenericJpaDao;
import com.hrtp.system.costant.ReturnCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CheckRealtxnService {
    @Autowired
    private CheckRealtxnRepository checkRealtxnRepository;
    @Autowired
    private GenericJpaDao genericJpaDao;
    
    
    /**
     * @param checkRealtxnEntity
     * @return 实时交易查询（按商户汇总）
     */
    public RespEntity findRealtxn(CheckRealtxnEntity checkRealtxnEntity) {
        if (StringUtils.isEmpty(checkRealtxnEntity) || StringUtils.isEmpty(checkRealtxnEntity.getUnno())) {
        	return new RespEntity(ReturnCode.FALT, "参数错误");
        } 
        HashMap<String,Object> map = new HashMap<String,Object>();
        String sql = "select a.unname,a.rname,a.mid,count(1) txncount,sum(a.txnamount) txnamount from check_realtxn a where a.txnstate=0 and a.txnday='"+new SimpleDateFormat("yyyyMMdd").format(new Date())+"'";
        if(checkRealtxnEntity.getUnno()!=null&&!"".equals(checkRealtxnEntity.getUnno())&&!"110000".equals(checkRealtxnEntity.getUnno())) {
			sql += " and a.unno in (select unno from sys_unit start with unno=:unno connect by prior unno = upper_unit)";
			map.put("unno", checkRealtxnEntity.getUnno());
		}
        if(checkRealtxnEntity.getMid()!=null&&!"".equals(checkRealtxnEntity.getMid())) {
			sql += " and a.mid = :mid";
			map.put("mid", checkRealtxnEntity.getMid());
		}
        if(checkRealtxnEntity.getRname()!=null&&!"".equals(checkRealtxnEntity.getRname())) {
			sql += " and a.rname = :rname";
			map.put("rname", checkRealtxnEntity.getRname());
		}
        sql += " group by a.unname,a.rname,a.mid ";
        Integer count = genericJpaDao.getRowsCount(sql, map);
        sql += " order by a.unname,a.rname ";
        List<Object[]> list = genericJpaDao.queryByNativeSqlWithPageAndRows(sql,checkRealtxnEntity.getPage(),checkRealtxnEntity.getRows(), map);
        List<JSONObject> txnList = new ArrayList<>();
        CheckRealtxnEntity returnBody = new CheckRealtxnEntity();
        for(int i =0;i<list.size();i++) {
        	JSONObject obj = new JSONObject();
        	obj.put("unName", list.get(i)[0]);
        	obj.put("rname", list.get(i)[1]);
        	obj.put("mid", list.get(i)[2]);
        	obj.put("txnCount", list.get(i)[3]);
        	obj.put("txnAmount", list.get(i)[4]);
        	txnList.add(obj);
        }
        returnBody.setList(txnList);
        returnBody.setTotal(count);
        return new RespEntity(ReturnCode.SUCCESS, "查询成功", returnBody);
    }
}
