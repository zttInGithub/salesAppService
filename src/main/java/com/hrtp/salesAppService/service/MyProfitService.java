package com.hrtp.salesAppService.service;

import com.alibaba.fastjson.JSONObject;
import com.hrtp.salesAppService.dao.*;
import com.hrtp.salesAppService.entity.appEntity.*;
import com.hrtp.salesAppService.entity.ormEntity.*;
import com.hrtp.system.costant.ReturnCode;
import com.hrtp.system.costant.SysParam;
import com.hrtp.system.util.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * MyProfitService
 * description 我的分润服务
 * create by lxj 2018/8/22
 **/
@Service
public class MyProfitService {
    @Autowired
    private UnnoRepayRepository unnoRepayRepository;
    @Autowired
    private UnnoScanRepository unnoScanRepository;
    @Autowired
    private UnnoQrpyRepository unnoQrpyRepository;
    @Autowired
    private UnnoPosRepository unnoPosRepository;
    @Autowired
    private UnnoMdaoRepository mdaoRepository;
    @Autowired
    private UnnoTxnFenRunRepository txnFenRunRepository;
    @Autowired
    private UnnoCashBackRepository unnoCashBackRepository;
    @Autowired
    private UnnoMerInfoRepository merInfoRepository;
    @Autowired
    private FileUtilsRepository fileUtilsRepository;
    @Autowired
    private SysParamRepository sysParamRepository;
    @Autowired
    private UnnoSytRepository sytRepository;

    public RespEntity getCreditCardList(UnnoRepayEntity repayEntity) {
        String beginDate = "";
        String endDate = "";
        if (StringUtils.isEmpty(repayEntity.getUnno())) return new RespEntity(ReturnCode.FALT, "参数失败");
        if (StringUtils.isEmpty(repayEntity.getBeginDate()) && StringUtils.isEmpty(repayEntity.getEndDate())) {
            beginDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
            endDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
        } else {
            beginDate = repayEntity.getBeginDate();
            endDate = repayEntity.getEndDate();
        }
        // 明细
        List<UnnoRepayModel> repayList = unnoRepayRepository.findByUnnoAndTxndayBetween(repayEntity
                .getUnno(), beginDate, endDate);
        List<JSONObject> txnList = new ArrayList<>();
        for (int i = 0; i < repayList.size(); i++) {
            JSONObject obj = new JSONObject();
            obj.put("enterAmt", repayList.get(i).getEnterAmt());
            obj.put("txnBfee", repayList.get(i).getTxnBfee());
            obj.put("txnday", repayList.get(i).getTxnday());
            txnList.add(obj);
        }
        // 汇总
        List<Object[]> sumList = unnoRepayRepository.findSumByUnno(repayEntity.getUnno(), beginDate, endDate);
        UnnoRepayEntity returnBody = new UnnoRepayEntity();
        returnBody.setBfeeFenrunTotal(Double.valueOf(sumList.get(0)[0] + ""));
        returnBody.setEnterCountTotal(Double.valueOf(sumList.get(0)[1] + ""));
        returnBody.setEnterAmtTotal(Double.valueOf(sumList.get(0)[2] + ""));
        returnBody.setTxnList(txnList);
        // 封装
        return new RespEntity(ReturnCode.SUCCESS, "查询成功", returnBody);
    }

    public RespEntity getQRCodeList(UnnoScanEntity scanEntity) {
        String beginDate = "";
        String endDate = "";
        if (StringUtils.isEmpty(scanEntity.getUnno())) return new RespEntity(ReturnCode.FALT, "参数失败");
        if (StringUtils.isEmpty(scanEntity.getBeginDate()) && StringUtils.isEmpty(scanEntity.getEndDate())) {
            beginDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
            endDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
        } else {
            beginDate = scanEntity.getBeginDate();
            endDate = scanEntity.getEndDate();
        }
        // 明细
        List<UnnoScanModel> scanList = unnoScanRepository.findByUnnoAndTxnDayBetween(scanEntity
                .getUnno(), beginDate, endDate);
        List<JSONObject> txnList = new ArrayList<>();
        for (int i = 0; i < scanList.size(); i++) {
            JSONObject obj = new JSONObject();
            obj.put("txnAmt", scanList.get(i).getTxnAmt());
            obj.put("txnBfee", scanList.get(i).getTxnProfit());
            obj.put("txnTransfer", scanList.get(i).getCashProfit());
            obj.put("txnType", scanList.get(i).getTxnType());
            txnList.add(obj);
        }
        // 汇总
        List<Object[]> sumList = unnoScanRepository.findSumByUnno(scanEntity.getUnno(), beginDate, endDate);
        UnnoScanEntity returnBody = new UnnoScanEntity();
        returnBody.setBfeeFenrunTotal(Double.valueOf(sumList.get(0)[0] + ""));
        returnBody.setTransferFenrunTotal(Double.valueOf(sumList.get(0)[1] + ""));
        returnBody.setTxnAmtTotal(Double.valueOf(sumList.get(0)[2] + ""));
        returnBody.setTxnCountTotal(Integer.valueOf(sumList.get(0)[3] + ""));
        returnBody.setTxnList(txnList);
        // 封装
        return new RespEntity(ReturnCode.SUCCESS, "查询成功", returnBody);
    }

    public RespEntity getQuickPayList(UnnoQrpyEntity qrpyEntity) {
        String beginDate = "";
        String endDate = "";
        if (StringUtils.isEmpty(qrpyEntity.getUnno())) return new RespEntity(ReturnCode.FALT, "参数失败");
        if (StringUtils.isEmpty(qrpyEntity.getBeginDate()) && StringUtils.isEmpty(qrpyEntity.getEndDate())) {
            beginDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
            endDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
        } else {
            beginDate = qrpyEntity.getBeginDate();
            endDate = qrpyEntity.getEndDate();
        }
        // 明细
        List<UnnoQrpyModel> qrpyList = unnoQrpyRepository.findByUnnoAndTxnDayBetween(qrpyEntity
                .getUnno(), beginDate, endDate);
        List<JSONObject> txnList = new ArrayList<>();
        for (int i = 0; i < qrpyList.size(); i++) {
            JSONObject obj = new JSONObject();
            obj.put("txnAmt", qrpyList.get(i).getTxnAmt());
            obj.put("txnBfee", qrpyList.get(i).getTxnProfit());
            obj.put("txnTransfer", qrpyList.get(i).getCasheProfit());
            obj.put("txnType", qrpyList.get(i).getTxnType());
            txnList.add(obj);
        }
        // 汇总
        List<Object[]> sumList = unnoQrpyRepository.findSumByUnno(qrpyEntity.getUnno(), beginDate, endDate);
        UnnoQrpyEntity returnBody = new UnnoQrpyEntity();
        returnBody.setBfeeFenrunTotal(Double.valueOf(sumList.get(0)[0] + ""));
        returnBody.setTransferFenrunTotal(Double.valueOf(sumList.get(0)[1] + ""));
        returnBody.setTxnAmtTotal(Double.valueOf(sumList.get(0)[2] + ""));
        returnBody.setTxnCountTotal(Integer.valueOf(sumList.get(0)[3] + ""));
        returnBody.setTxnList(txnList);
        // 封装
        return new RespEntity(ReturnCode.SUCCESS, "查询成功", returnBody);
    }

    public RespEntity getTraditionPosList(UnnoPosEntity posEntity) {
        if (StringUtils.isEmpty(posEntity) || StringUtils.isEmpty(posEntity.getUnno()) || StringUtils.isEmpty(posEntity
                .getTxnType()))
            return new RespEntity(ReturnCode.FALT, "参数失败");
        if (5 != posEntity.getTxnType()) {
            return getTraditionPosList1TO4(posEntity);
        } else {
            return getTraditionPosList5(posEntity);
        }
    }

    /**
     * 1-借记卡大额，2-借记卡小额，3-贷记卡，4-扫码
     *
     * @param posEntity
     * @return
     */
    private RespEntity getTraditionPosList1TO4(UnnoPosEntity posEntity) {
        String beginDate = "";
        String endDate = "";
        if (StringUtils.isEmpty(posEntity.getBeginDate()) && StringUtils.isEmpty(posEntity.getEndDate())) {
            beginDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
            endDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
        } else {
            beginDate = posEntity.getBeginDate();
            endDate = posEntity.getEndDate();
        }
        // 明细
        List<UnnoPosModel> posList = unnoPosRepository.findByUnnoAndPTypeAndTxnDayBetween
                (posEntity.getUnno(), posEntity.getTxnType(), beginDate,
                        endDate);
        List<JSONObject> rsList = new ArrayList<>();
        for (int i = 0; i < posList.size(); i++) {
            JSONObject obj = new JSONObject();
            obj.put("txnAmt", posList.get(i).getTxnAmt());
            obj.put("txnCount", posList.get(i).getTxnCount());
            obj.put("txnFenrun", posList.get(i).getTxnProfit());
            obj.put("txnType", posList.get(i).getpType());
            rsList.add(obj);
        }
        // 汇总
        List<Object[]> sumList = unnoPosRepository.findSumByUnnoAndPTypeAndTxnDayBetween(posEntity.getUnno(),
                posEntity.getTxnType(), posEntity.getBeginDate(), posEntity.getEndDate());
        UnnoPosEntity returnBody = new UnnoPosEntity();
        returnBody.setBfeeTotal(Double.valueOf(sumList.get(0)[0] + ""));
        returnBody.setTxnAmtTotal(Double.valueOf(sumList.get(0)[1] + ""));
        returnBody.setTxnCountTotal(Integer.valueOf(sumList.get(0)[2] + ""));
        returnBody.setTxnList(rsList);
        return new RespEntity(ReturnCode.SUCCESS, "查询成功", returnBody);
    }

    /**
     * 5-提现
     *
     * @param posEntity
     * @return
     */
    private RespEntity getTraditionPosList5(UnnoPosEntity posEntity) {
        String beginDate = "";
        String endDate = "";
        if (StringUtils.isEmpty(posEntity.getBeginDate()) && StringUtils.isEmpty(posEntity.getEndDate())) {
            beginDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
            endDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
        } else {
            beginDate = posEntity.getBeginDate();
            endDate = posEntity.getEndDate();
        }

        // 明细
        List<UnnoPosModel> posList = unnoPosRepository.findByUnnoAndPTypeAndTxnDayBetween(posEntity.getUnno(),
                beginDate, endDate);
        List<JSONObject> rsList = new ArrayList<>();
        for (int i = 0; i < posList.size(); i++) {
            JSONObject obj = new JSONObject();
            obj.put("txnCash", posList.get(i).getTxnAmt());
            obj.put("txnCount", posList.get(i).getTxnCount());
            obj.put("txnFenrun", posList.get(i).getCashProfit());
            obj.put("txnType", posList.get(i).getpType());
            rsList.add(obj);
        }
        // 汇总
        List<Object[]> sumList = unnoPosRepository.findSumByUnnoAndPTypeAndTxnDayBetween(posEntity.getUnno(),
                posEntity.getBeginDate(), posEntity.getEndDate());
        UnnoPosEntity returnBody = new UnnoPosEntity();
        returnBody.setFenrunTotal(Double.valueOf(sumList.get(0)[0] + ""));
        returnBody.setTxnAmtTotal(Double.valueOf(sumList.get(0)[1] + ""));
        returnBody.setTxnCountTotal(Integer.valueOf(sumList.get(0)[2] + ""));
        returnBody.setTxnList(rsList);
        return new RespEntity(ReturnCode.SUCCESS, "查询成功", returnBody);
    }

    public RespEntity getMiaoDaoList(UnnoMdaoEntity mdaoEntity) {
        String beginDate = "";
        String endDate = "";
        if (StringUtils.isEmpty(mdaoEntity.getUnno())) return new RespEntity(ReturnCode.FALT, "参数失败");
        if (StringUtils.isEmpty(mdaoEntity.getBeginDate()) && StringUtils.isEmpty(mdaoEntity.getEndDate())) {
            beginDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
            endDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
        } else {
            beginDate = mdaoEntity.getBeginDate();
            endDate = mdaoEntity.getEndDate();
        }
        // 明细
        List<UnnoMdaoModel> mdaoList = mdaoRepository.findByUnnoAndTxnDayBetween(mdaoEntity
                .getUnno(), beginDate, endDate);
        List<JSONObject> txnList = new ArrayList<>();
        for (int i = 0; i < mdaoList.size(); i++) {
            JSONObject obj = new JSONObject();
            obj.put("txnAmt", mdaoList.get(i).getTxnAmt());
            obj.put("txnBfee", mdaoList.get(i).getTxnProfit());
            obj.put("txnTransfer", mdaoList.get(i).getCashProfit());
            obj.put("txnType", mdaoList.get(i).getTxnType());
            txnList.add(obj);
        }
        // 汇总
        List<Object[]> sumList = mdaoRepository.findSumByUnno(mdaoEntity.getUnno(), beginDate, endDate);
        UnnoMdaoEntity returnBody = new UnnoMdaoEntity();
        returnBody.setBfeeFenrunTotal(Double.valueOf(sumList.get(0)[0] + ""));
        returnBody.setTransferFenrunTotal(Double.valueOf(sumList.get(0)[1] + ""));
        returnBody.setTxnAmtTotal(Double.valueOf(sumList.get(0)[2] + ""));
        returnBody.setTxnCountTotal(Integer.valueOf(sumList.get(0)[3] + ""));
        returnBody.setTxnList(txnList);
        // 封装
        return new RespEntity(ReturnCode.SUCCESS, "查询成功", returnBody);
    }

    public RespEntity getYesterdayFenrun(UnnoTxnFenRunEntity txnEntity) {
        if (StringUtils.isEmpty(txnEntity.getUnno())) return new RespEntity(ReturnCode.FALT, "参数失败");
        // 查询参数昨日数据
        Optional<SysParamModel> salesAppProfitTime = sysParamRepository.findById("salesAppProfitTime");
        String[] days = TimeUtils.getYdyAndFday();
        if (salesAppProfitTime.isPresent()){
            days[0] = salesAppProfitTime.get().getUploadPath().replaceAll("-","");
        }
        UnnoTxnFenRunModel model = txnFenRunRepository.findByUnnoAndTxnDay(txnEntity.getUnno(), days[0]);
        List<Double> sumByUnno = txnFenRunRepository.findSumByUnno(txnEntity.getUnno(), days[1], days[0]);
        //封装
        UnnoTxnFenRunEntity returnBody = new UnnoTxnFenRunEntity();
        for (int i = 0; i < sumByUnno.size(); i++)
            returnBody.setTotalFenrun(sumByUnno.get(0));
        returnBody.setYdayFenrun(model.getTxnProfit());
        returnBody.setYdayTotalCount(model.getTxnCount());
        returnBody.setYdayTotalAmt(model.getTxnAmt());
        returnBody.setUnno(txnEntity.getUnno());
        return new RespEntity(ReturnCode.SUCCESS, "查询成功", returnBody);
    }

    public RespEntity getCashAmtList(UnnoCashBackEntity cashEntity) {
        if (StringUtils.isEmpty(cashEntity.getUnno())) return new RespEntity(ReturnCode.FALT, "参数失败");
        String[] days = TimeUtils.getYdyAndFday();
        // 查询参数昨日数据
        Optional<SysParamModel> salesAppProfitTime = sysParamRepository.findById("salesAppProfitTime");
        if (salesAppProfitTime.isPresent()){
            days[0] = salesAppProfitTime.get().getUploadPath().replaceAll("-","");
        }
        List<Object[]> cashList = unnoCashBackRepository.findByUnnoAndAndCashBackDay(cashEntity
                .getUnno(), days[0], days[1]);
        UnnoCashBackEntity returnBody = new UnnoCashBackEntity();
        for (int i = 0; i < cashList.size(); i++) {
            returnBody.setTotalFanXianAmt(Double.valueOf(cashList.get(0)[0] + ""));
            returnBody.setYdayFanXianAmt(Double.valueOf(cashList.get(0)[1] + ""));
        }
        returnBody.setUnno(cashEntity.getUnno());
        //封装
        return new RespEntity(ReturnCode.SUCCESS, "查询成功", returnBody);
    }

    public RespEntity getYesterDayMerList(UnnoMerInfoEntity merEntity) {
        if (StringUtils.isEmpty(merEntity.getUnno())) return new RespEntity(ReturnCode.FALT, "参数错误");
        // 查询参数昨日数据
        Optional<SysParamModel> salesAppProfitTime = sysParamRepository.findById("salesAppProfitTime");
        String days = TimeUtils.getYdyAndFday()[0];
        if (salesAppProfitTime.isPresent()){
            days = salesAppProfitTime.get().getUploadPath().replaceAll("-","");
        }
        return Optional.ofNullable(merInfoRepository.findByUnnoAndMerDay(merEntity.getUnno(), days))
                .map(unnoMerInfoModel -> {
                    return new RespEntity(ReturnCode.SUCCESS, "查询成功", new UnnoMerInfoEntity(unnoMerInfoModel
                            .getActMerCount(), unnoMerInfoModel.getAddMerCount(), unnoMerInfoModel.getJhuoMerCount()));
                }).orElseGet(() -> new RespEntity(ReturnCode.SUCCESS, "查询为空", new UnnoMerInfoEntity(0, 0, 0)));
    }
    
    public RespEntity getProfitDate(){
    	Optional<FileUtilsModel> paramModel = fileUtilsRepository.findById(SysParam.PROFIT_TITLE);
        if(!paramModel.isPresent()){
        	return new RespEntity(ReturnCode.FALT, "查询失败,未查询到相应数据","");
        }
        return new RespEntity(ReturnCode.SUCCESS, "查询成功",paramModel.get().getUpload_path());
    }

	/**
	 * 收银台分润
	 * @param mdaoEntity
	 * @return
	 */
	@SuppressWarnings("all")
	public RespEntity getSytList(UnnoMdaoEntity mdaoEntity) {
		String beginDate = "";
		String endDate = "";
		if (StringUtils.isEmpty(mdaoEntity.getUnno())) return new RespEntity(ReturnCode.FALT, "参数失败");
		if (StringUtils.isEmpty(mdaoEntity.getBeginDate()) && StringUtils.isEmpty(mdaoEntity.getEndDate())) {
			beginDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
			endDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
		} else {
			beginDate = mdaoEntity.getBeginDate();
			endDate = mdaoEntity.getEndDate();
		}
		// 明细
		List<UnnoSytModel> sytList = sytRepository.findByUnnoAndTxnDayBetween(mdaoEntity
				.getUnno(), beginDate, endDate);
		List<JSONObject> txnList = new ArrayList<>();
		for (int i = 0; i < sytList.size(); i++) {
			JSONObject obj = new JSONObject();
			obj.put("txnAmt", sytList.get(i).getTxnAmt());
			obj.put("txnBfee", sytList.get(i).getTxnProfit());
			obj.put("txnTransfer", sytList.get(i).getCashProfit());
			obj.put("txnType", sytList.get(i).getTxnType());
			txnList.add(obj);
		}
		// 汇总
		List<Object[]> sumList = sytRepository.findSumByUnno(mdaoEntity.getUnno(), beginDate, endDate);
		UnnoMdaoEntity returnBody = new UnnoMdaoEntity();
		returnBody.setBfeeFenrunTotal(Double.valueOf(sumList.get(0)[0] + ""));
		returnBody.setTransferFenrunTotal(Double.valueOf(sumList.get(0)[1] + ""));
		returnBody.setTxnAmtTotal(Double.valueOf(sumList.get(0)[2] + ""));
		returnBody.setTxnCountTotal(Integer.valueOf(sumList.get(0)[3] + ""));
		returnBody.setTxnList(txnList);
		// 封装
		return new RespEntity(ReturnCode.SUCCESS, "查询成功", returnBody);
	}
}
