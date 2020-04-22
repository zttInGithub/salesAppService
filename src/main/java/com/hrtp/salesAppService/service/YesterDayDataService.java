package com.hrtp.salesAppService.service;

import com.alibaba.fastjson.JSONObject;
import com.hrtp.salesAppService.dao.MerchantTerminalInfoRepository;
import com.hrtp.salesAppService.dao.UnnoMerInfoRepository;
import com.hrtp.salesAppService.dao.UnnoTxnFenRunRepository;
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

/**
 * YesterDayDataService
 * description 昨日交易服务
 * create by lxj 2018/8/24
 **/
@Service
public class YesterDayDataService {
    @Autowired
    private UnnoTxnFenRunRepository txnFenRunRepository;
    @Autowired
    private UnnoMerInfoRepository merInfoRepository;
    @Autowired
    private GenericJpaDao genericJpaDao;
    @Autowired
    private MerchantTerminalInfoRepository terminalInfoRepository;
    public RespEntity getTxnTotalData(TxnFenRunEntity txnFenRunEntity) {
        String beginDate = "";
        String endDate = "";
        if (StringUtils.isEmpty(txnFenRunEntity) || StringUtils.isEmpty(txnFenRunEntity.getUnno())) return new
                RespEntity(ReturnCode.FALT, "参数错误");
        if (StringUtils.isEmpty(txnFenRunEntity.getBeginDate()) && StringUtils.isEmpty(txnFenRunEntity.getEndDate())) {
            beginDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
            endDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
        } else {
            beginDate = txnFenRunEntity.getBeginDate();
            endDate = txnFenRunEntity.getEndDate();
        }
        // 交易总额，交易总笔数
        List<Object[]> frList = txnFenRunRepository.findByUnnoAndTxnDayBetween(txnFenRunEntity
                .getUnno(), beginDate, endDate);
        List<JSONObject> txnList = new ArrayList<>();
        TxnFenRunEntity returnBody = new TxnFenRunEntity();
        for (int i = 0; i < frList.size(); i++) {
            returnBody.setTxnAmtTotal(Double.valueOf(frList.get(0)[0] + ""));
            returnBody.setTxnCountTotal(Integer.valueOf(frList.get(0)[1] + ""));
        }
        // 昨日活跃商户列表
        List<UnnoMerInfoModel> sumList = merInfoRepository.findByUnnoAndMerDayBetweenOrderByMerDayDesc(txnFenRunEntity.getUnno(),
                beginDate,endDate);
        List<JSONObject> actMerList = new ArrayList<>();
        for (int i = 0; i < sumList.size(); i++) {
            JSONObject obj = new JSONObject();
            obj.put("actMerCount", Integer.parseInt(sumList.get(i).getActMerCount() + ""));
            obj.put("summaryDate", sumList.get(i).getMerDay() + "");
            actMerList.add(obj);
        }
        returnBody.setActMerList(actMerList);
        // 封装
        return new RespEntity(ReturnCode.SUCCESS, "查询成功", returnBody);
    }

    public RespEntity getTxnDetailData(ReqTxnDetailDataEntity txnDetailDataEntity) {
        String beginDate = "";
        String endDate = "";
        if (StringUtils.isEmpty(txnDetailDataEntity) || StringUtils.isEmpty(txnDetailDataEntity.getQueryType()) ||
                StringUtils.isEmpty(txnDetailDataEntity.getBeginDate()) || StringUtils.isEmpty(txnDetailDataEntity
                .getEndDate()) || StringUtils.isEmpty(txnDetailDataEntity.getQueryValue()) || StringUtils.isEmpty
                (txnDetailDataEntity.getPage()) || StringUtils.isEmpty(txnDetailDataEntity.getRows()) || StringUtils
                .isEmpty(txnDetailDataEntity.getUnno())) {
            return new RespEntity(ReturnCode.FALT, "参数错误");
        }
        if (!judgeMerchantExist(txnDetailDataEntity)) return new RespEntity(ReturnCode.FALT, "商户查询不存在");
        if (StringUtils.isEmpty(txnDetailDataEntity.getBeginDate()) && StringUtils.isEmpty(txnDetailDataEntity
                .getEndDate())) {
            beginDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
            endDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
        } else {
            beginDate = txnDetailDataEntity.getBeginDate();
            endDate = txnDetailDataEntity.getEndDate();
        }
        StringBuilder sql = new StringBuilder();
        HashMap<String, Object> param = new HashMap<>();
        List<Object[]> list = null;
        int count = 0;
        List<Object> list1 = new ArrayList<Object>();
        //商编
        if ("1".equals(txnDetailDataEntity.getQueryType())) {
            sql.append("select * from( select '4' txntype, m3.sn tusn, m2.txnday, m2.txnamount txnamt,m1.rname，m1.mid");
            sql.append(" from bill_merchantinfo m1 left join check_unitdealdetail m2 on m1.mid = m2.mid left join");
            sql.append(" bill_terminalinfo m3 on m2.tid = m3.termid where m1.mid = :mid and m2.txnday ");
            sql.append(" between :beginDate and :endDate union select k2.trantype txntype, '' tusn,to_char(k2.cdate,");
            sql.append(" 'yyyyMMdd') txnday, k2.txnamt, k1.rname, k1.mid from bill_merchantinfo k1 left join ");
            sql.append(" check_wechattxndetail k2 on (k1.mid = k2.mid and k2.trantype in (1, 2, 3)) where k1.mid = ");
            sql.append(" :mid and to_char(k2.cdate, 'yyyyMMdd') between :beginDate and :endDate) order by txnday desc");
            param.put("mid", txnDetailDataEntity.getQueryValue());
            param.put("beginDate", beginDate);
            param.put("endDate", endDate);
            list = genericJpaDao.queryByNativeSqlWithPageAndRows(sql.toString(), txnDetailDataEntity.getPage(),
                    txnDetailDataEntity.getRows(), param);
            // 查询商户信息
            String sqlout = "select rname,mid from bill_merchantinfo t where t.mid = :mid ";
            HashMap outMap = new HashMap();
            outMap.put("mid",txnDetailDataEntity.getQueryValue());
            list1 = genericJpaDao.queryByNativeSql(sqlout, outMap);
            if (list1.size() < 1) return new RespEntity(ReturnCode.FALT,"商户查询不存在");
        } else if ("2".equals(txnDetailDataEntity.getQueryType())) {
            sql.append("SELECT '4' txntype, b1.sn tusn,b2.txnday, b2.txnamount txnamt, b3.rname, b3.mid FROM ");
            sql.append("bill_terminalinfo b1 LEFT JOIN check_unitdealdetail b2 ON b1.termid = b2.tid LEFT JOIN ");
            sql.append("bill_merchantinfo b3 ON b2.mid = b3.mid WHERE b1.sn = :sn AND b2.txnday BETWEEN ");
            sql.append(":beginDate AND :endDate order by b2.txnday desc ");
            param.put("sn", txnDetailDataEntity.getQueryValue());
            param.put("beginDate", beginDate);
            param.put("endDate", endDate);
            list = genericJpaDao.queryByNativeSqlWithPageAndRows(sql.toString(), txnDetailDataEntity.getPage(),
                    txnDetailDataEntity.getRows(), param);
            // 查询商户信息
            String sqlout = "select t1.rname,t1.mid from bill_merchantinfo t1,BILL_MERCHANTTERMINALINFO t2 where t1" +
                    ".mid = t2.mid and t2.sn=:sn and t2.maintaintype <> 'D' ";
            HashMap outMap = new HashMap();
            outMap.put("sn",txnDetailDataEntity.getQueryValue());
            list1 = genericJpaDao.queryByNativeSql(sqlout, outMap);
            if (list1.size() < 1) return new RespEntity(ReturnCode.FALT,"商户查询不存在");
        } else {
            return new RespEntity(ReturnCode.FALT, "参数错误");
        }
        count = genericJpaDao.getRowsCount(sql.toString(), param);
        ArrayList<JSONObject> rsList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            JSONObject obj = new JSONObject();
            obj.put("txnType", list.get(i)[0]);
            obj.put("TUSN", list.get(i)[1]);
            obj.put("txnDate", list.get(i)[2]);
            obj.put("txnAmt", list.get(i)[3]);
            rsList.add(obj);
        }
        RespTxnDetailDataEntity returnBody = new RespTxnDetailDataEntity();
        Object[] o = (Object[]) list1.get(0);
        returnBody.setMerName(o[0] + "");
        returnBody.setMid(o[1] + "");
        returnBody.setTxnList(rsList);
        returnBody.setTotalCount(count);
        return new RespEntity(ReturnCode.SUCCESS, "查询成功", returnBody);
    }

    private Boolean judgeMerchantExist(ReqTxnDetailDataEntity txnDetailDataEntity) {
        String sql = "SELECT s.unno FROM sys_unit s WHERE s.unno = :unno start WITH s.unno = (SELECT unno " +
                "FROM bill_merchantinfo WHERE mid = :mid) connect by NOCYCLE s.unno = prior s.upper_unit";
        if ("1".equals(txnDetailDataEntity.getQueryType())) {//商编
            HashMap<String, Object> param = new HashMap<>();
            param.put("unno", txnDetailDataEntity.getUnno());
            param.put("mid", txnDetailDataEntity.getQueryValue());
            List list = genericJpaDao.queryByNativeSql(sql, param);
            if (list.size() < 1) return false;
        } else if ("2".equals(txnDetailDataEntity.getQueryType())) {//SN号
            // 查商户号
            MerchantTerminalInfoModel bySnAndMaintainType = terminalInfoRepository.findBySnAndMaintainType
                    (txnDetailDataEntity.getQueryValue());
            if (bySnAndMaintainType == null || "".equals(bySnAndMaintainType.getMid()) || null == bySnAndMaintainType
                    .getMid()) {
                return false;
            }
//            String sql = "SELECT s.unno FROM sys_unit s WHERE s.unno = :unno start WITH s.unno = (SELECT unno " +
//                    "FROM bill_merchantterminalinfo WHERE sn = :sn AND maintaintype <> 'D') connect by NOCYCLE s" +
//                    ".unno = prior s.upper_unit ";
            HashMap<String, Object> param = new HashMap<>();
            param.put("unno", txnDetailDataEntity.getUnno());
            param.put("mid", bySnAndMaintainType.getMid());
            List list = genericJpaDao.queryByNativeSql(sql, param);
            if (list.size() < 1) return false;
        } else {
            return false;
        }
        return true;
    }
}
