package com.hrtp.salesAppService.service;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.hrtp.salesAppService.dao.BackOrderDao;
import com.hrtp.salesAppService.dao.BackOrderRepository;
import com.hrtp.salesAppService.dao.FileUtilsRepository;
import com.hrtp.salesAppService.entity.appEntity.BackOrderEntity;
import com.hrtp.salesAppService.entity.appEntity.RespEntity;
import com.hrtp.salesAppService.entity.ormEntity.BackOrderModel;
import com.hrtp.salesAppService.entity.ormEntity.FileUtilsModel;
import com.hrtp.salesAppService.exception.AppException;
import com.hrtp.system.costant.ReturnCode;
import com.hrtp.system.costant.SysParam;

@Service
public class BackOrderService {
	
	private static Logger logger = LoggerFactory.getLogger(BackOrderService.class);
	
	@Autowired
	private BackOrderDao backOrderDao;
	@Autowired
	private BackOrderRepository backOrderRepository;
	@Autowired
	private FileUtilsRepository fileUtilsRepository;
	
	public RespEntity getBOrderList(BackOrderEntity backOrderEntity) {
		if (StringUtils.isEmpty(backOrderEntity.getUnno())) {
            return new RespEntity(ReturnCode.FALT,"参数错误");
		}
		if (StringUtils.isEmpty(backOrderEntity.getType())) {
            return new RespEntity(ReturnCode.FALT,"参数错误");
        }
		if (StringUtils.isEmpty(backOrderEntity.getPage())) {
            return new RespEntity(ReturnCode.FALT,"参数错误");
        }
		if (StringUtils.isEmpty(backOrderEntity.getRows())) {
            return new RespEntity(ReturnCode.FALT,"参数错误");
        }
        RespEntity rs = new RespEntity();
        try {
             String sql ="select * from ("+
             " select r.roid,r.mid,r.tid,m.rname,r.cardpan,r.samt,r.txnday,r.rrn,r.refundType,decode(m.ism35,1,1,0)ism35,"+
             " r.refunddate,(case when trunc(sysdate)-trunc(to_date(r.refunddate,'yyyyMMdd'))>=25 then '4' else r.status end)status,"+
             " r.processcontext,r.commentcontext from check_reOrder r left join bill_merchantinfo m on r.mid=m.mid where "+
             " m.unno in(select UNNO from sys_unit start with unno =:UNNO and status=1 connect by NOCYCLE prior  unno =  upper_unit) "+
             " order by r.roid desc "+
             " ) where 1=1 ";
             if(backOrderEntity.getType()==0) sql+=" and status='0' ";
             if(backOrderEntity.getType()==1) sql+=" and status<>'0' ";
             HashMap<String, Object> param = new HashMap<String, Object>();
             param.put("UNNO", backOrderEntity.getUnno());
             List<Object[]> list=backOrderDao.queryByNativeSqlWithPageAndRows(sql,backOrderEntity.getPage(),backOrderEntity.getRows(),param);
             List<JSONObject> queryList = new ArrayList<>();
             JSONObject resultJson = new JSONObject();
             for(Object[] obj : list){
             	JSONObject json = new JSONObject();
             	json.put("roId", Integer.parseInt(obj[0]+""));
             	json.put("mid", obj[1]+"");
             	json.put("tid", obj[2]+"");
             	json.put("rname", obj[3]+"");
             	json.put("cardPan", obj[4]+"");
             	json.put("samt", Double.parseDouble(obj[5]+""));
             	json.put("txnday", obj[6]+"");
             	json.put("rrn", obj[7]+"");
             	json.put("refundType", Integer.parseInt(obj[8]+""));
             	json.put("ism35", Integer.parseInt(obj[9]+""));
             	json.put("refunddate", obj[10]+"");
             	json.put("status", obj[11]==null?"":obj[11]+"");
             	json.put("processContext", obj[12]==null?"":obj[12]+"");
             	json.put("commentContext", obj[13]==null?"":obj[13]+"");
             	queryList.add(json);
             }
             Integer count=backOrderDao.getRowsCount(sql, param);
             resultJson.put("unno", backOrderEntity.getUnno());
             resultJson.put("data", queryList);
             resultJson.put("totalCount", count);
             
             rs.setReturnCode(ReturnCode.SUCCESS);
             rs.setReturnMsg("查询成功");
             rs.setReturnBody(resultJson);
		} catch (Exception e) {
			logger.info("退单列表查询异常："+e.getMessage());
			 rs.setReturnMsg("查询失败");
	         rs.setReturnCode(ReturnCode.FALT);
		}
        return rs;
    }
	
	/**
	 * 退单 回复接口
	 * @param reqsBody
	 * @return
	 */
	public RespEntity updateBackOrderInfo(BackOrderEntity backOrderEntity) {
        if (StringUtils.isEmpty(backOrderEntity.getRoId())) return new RespEntity(ReturnCode.FALT, "参数错误");
        BackOrderModel model = backOrderRepository.getOne(backOrderEntity.getRoId().toString());
		long m = 0;
		try {
			//计算当前时间和退单上传时间之差
			String refundDate = model.getRefundDate();
			String reF1 = refundDate.substring(0, 4)+"-"+refundDate.substring(4,6)+"-"+refundDate.substring(6);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			m = sdf.parse(new SimpleDateFormat("yyyy-MM-dd").format(new Date()).toString()).getTime()-sdf.parse(reF1).getTime();
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		if(m/(24*3600*1000) >= 25) return new RespEntity(ReturnCode.FALT, "已逾期，不能上传");
        return Optional.ofNullable(model).map( backOrderModel -> {
            if (!StringUtils.isEmpty(backOrderEntity.getCommentContext())) backOrderModel.setCommentContext(backOrderEntity.getCommentContext());
//            if (!StringUtils.isEmpty(backOrderEntity.getProcessContext())) backOrderModel.setProcessContext(backOrderEntity.getProcessContext());
            try {
				uploadImg(backOrderEntity, backOrderModel);
				logger.info("图片上传成功");
			} catch (Exception e) {
				logger.info("退单图片上传失败:"+e.getMessage());
				throw new AppException(ReturnCode.FALT,"上传图片失败");
			}
            backOrderModel.setStatus(1);
            backOrderModel.setMaintainDate(new Date());
            return new RespEntity(ReturnCode.SUCCESS, "上传成功");
        }).orElseGet(() -> new RespEntity(ReturnCode.FALT, "上传失败"));
    }
	private BackOrderModel uploadImg(BackOrderEntity backOrderEntity, BackOrderModel backOrderModel) throws Exception {
        String imageDay = new SimpleDateFormat("yyyy-MM-dd").format(new Date()).toString();
        //签购单
        if (backOrderEntity.getReorDeruploadFile() != null && !StringUtils.isEmpty(backOrderEntity.getReorDeruploadFile())) {
        	StringBuffer fName = new StringBuffer();
			fName.append(backOrderModel.getRoId());
			fName.append(".");
			fName.append(backOrderModel.getRrn());
			fName.append(".");
			fName.append(imageDay);
			fName.append(".reOrder");
			fName.append(backOrderEntity.getReorDeruploadFile().getOriginalFilename().substring(backOrderEntity.getReorDeruploadFile().getOriginalFilename().lastIndexOf(".")).toLowerCase());
            uploadMultipartFile(backOrderEntity.getReorDeruploadFile(), fName.toString(), imageDay);
            backOrderModel.setReorDerupload(imageDay+File.separator+fName.toString());
        }
        //交易流水
        if (backOrderEntity.getReorDerupload1File() != null && !StringUtils.isEmpty(backOrderEntity.getReorDerupload1File())) {
        	StringBuffer fName1 = new StringBuffer();
			fName1.append(backOrderModel.getRoId());
			fName1.append(".");
			fName1.append(backOrderModel.getRrn());
			fName1.append(".");
			fName1.append(imageDay);
			fName1.append(".reOrder1");
			fName1.append(backOrderEntity.getReorDerupload1File().getOriginalFilename().substring(backOrderEntity.getReorDerupload1File().getOriginalFilename().lastIndexOf(".")).toLowerCase());
            uploadMultipartFile(backOrderEntity.getReorDerupload1File(), fName1.toString(), imageDay);
            backOrderModel.setReorDerupload1(imageDay+File.separator+fName1.toString());
        }
        //身份证正面
        if (backOrderEntity.getReorDerupload2File() != null && !StringUtils.isEmpty(backOrderEntity.getReorDerupload2File())) {
        	StringBuffer fName2 = new StringBuffer();
        	fName2.append(backOrderModel.getRoId());
        	fName2.append(".");
        	fName2.append(backOrderModel.getRrn());
        	fName2.append(".");
        	fName2.append(imageDay);
        	fName2.append(".reOrder2");
        	fName2.append(backOrderEntity.getReorDerupload2File().getOriginalFilename().substring(backOrderEntity.getReorDerupload2File().getOriginalFilename().lastIndexOf(".")).toLowerCase());
            uploadMultipartFile(backOrderEntity.getReorDerupload2File(), fName2.toString(), imageDay);
            backOrderModel.setReorDerupload2(imageDay+File.separator+fName2.toString());
        }
        //身份证反面
        if (backOrderEntity.getReorDerupload3File() != null && !StringUtils.isEmpty(backOrderEntity.getReorDerupload3File())) {
        	StringBuffer fName3 = new StringBuffer();
        	fName3.append(backOrderModel.getRoId());
        	fName3.append(".");
        	fName3.append(backOrderModel.getRrn());
        	fName3.append(".");
        	fName3.append(imageDay);
        	fName3.append(".reOrder3");
        	fName3.append(backOrderEntity.getReorDerupload3File().getOriginalFilename().substring(backOrderEntity.getReorDerupload3File().getOriginalFilename().lastIndexOf(".")).toLowerCase());
            uploadMultipartFile(backOrderEntity.getReorDerupload3File(), fName3.toString(), imageDay);
            backOrderModel.setReorDerupload3(imageDay+File.separator+fName3.toString());
        }
        //交易卡正面
        if (backOrderEntity.getReorDerupload4File() != null && !StringUtils.isEmpty(backOrderEntity.getReorDerupload4File())) {
        	StringBuffer fName4 = new StringBuffer();
        	fName4.append(backOrderModel.getRoId());
        	fName4.append(".");
        	fName4.append(backOrderModel.getRrn());
        	fName4.append(".");
        	fName4.append(imageDay);
        	fName4.append(".reOrder4");
        	fName4.append(backOrderEntity.getReorDerupload4File().getOriginalFilename().substring(backOrderEntity.getReorDerupload4File().getOriginalFilename().lastIndexOf(".")).toLowerCase());
            uploadMultipartFile(backOrderEntity.getReorDerupload4File(), fName4.toString(), imageDay);
            backOrderModel.setReorDerupload4(imageDay+File.separator+fName4.toString());
        }
        //持卡人撤销退单声明
        if (backOrderEntity.getReorDerupload5File() != null && !StringUtils.isEmpty(backOrderEntity.getReorDerupload5File())) {
        	StringBuffer fName5 = new StringBuffer();
        	fName5.append(backOrderModel.getRoId());
        	fName5.append(".");
        	fName5.append(backOrderModel.getRrn());
        	fName5.append(".");
        	fName5.append(imageDay);
        	fName5.append(".reOrder5");
        	fName5.append(backOrderEntity.getReorDerupload5File().getOriginalFilename().substring(backOrderEntity.getReorDerupload5File().getOriginalFilename().lastIndexOf(".")).toLowerCase());
            uploadMultipartFile(backOrderEntity.getReorDerupload5File(), fName5.toString(), imageDay);
            backOrderModel.setReorDerupload5(imageDay+File.separator+fName5.toString());
        }
        return backOrderModel;
    }
	/**
	 * 上传
	 */
	private void uploadMultipartFile(MultipartFile file,String fName, String imageDay) {
		if (file!=null) {// 判断上传的文件是否为空
			Optional<FileUtilsModel> sysParamModel = fileUtilsRepository.findById(SysParam.HRT_TITLE);
            if(!sysParamModel.isPresent()){
                throw new AppException(ReturnCode.FALT,"获取退单上传图片路径错误");
            }
			String path=null;// 文件路径
			//生产路径上线放开
			String realPath=sysParamModel.get().getUpload_path()+ File.separator + imageDay;
			//测试路径
//			String realPath="D:\\upload\\reOrderUpload"+ File.separator + imageDay;
			File dir = new File(realPath);
			if(!dir.exists()){
                dir.mkdirs();
            }
			// 设置存放图片文件的路径
			path=realPath+ File.separator +fName;
			// 转存文件到指定的路径
			try {
				file.transferTo(new File(path));
			} catch (Exception e) {
				logger.info("退单图片上传失败："+e.getMessage());
			}
		}
	}
}