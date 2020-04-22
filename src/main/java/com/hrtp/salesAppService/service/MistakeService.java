package com.hrtp.salesAppService.service;

import java.io.File;
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
import com.hrtp.salesAppService.dao.FileUtilsRepository;
import com.hrtp.salesAppService.dao.MistakeDao;
import com.hrtp.salesAppService.dao.MistakeRepository;
import com.hrtp.salesAppService.entity.appEntity.MistakeEntity;
import com.hrtp.salesAppService.entity.appEntity.RespEntity;
import com.hrtp.salesAppService.entity.ormEntity.FileUtilsModel;
import com.hrtp.salesAppService.entity.ormEntity.MistakeModel;
import com.hrtp.salesAppService.exception.AppException;
import com.hrtp.system.costant.ReturnCode;
import com.hrtp.system.costant.SysParam;

@Service
public class MistakeService {
	
	private static Logger logger = LoggerFactory.getLogger(MistakeService.class);
	
	@Autowired
	private MistakeDao mistakeDao;
	@Autowired
	private MistakeRepository mistakeRepository;
	@Autowired
	private FileUtilsRepository fileUtilsRepository;
	
	public RespEntity getQueryList(MistakeEntity mistakeEntity) {
		if (StringUtils.isEmpty(mistakeEntity.getUnno())) {
            return new RespEntity(ReturnCode.FALT,"参数错误");
        }
        if (StringUtils.isEmpty(mistakeEntity.getOrderType())) {
            return new RespEntity(ReturnCode.FALT,"参数错误");
        }
        if (StringUtils.isEmpty(mistakeEntity.getType())) {
            return new RespEntity(ReturnCode.FALT,"参数错误");
        }
        if (StringUtils.isEmpty(mistakeEntity.getPage())) {
            return new RespEntity(ReturnCode.FALT,"参数错误");
        }
        if (StringUtils.isEmpty(mistakeEntity.getRows())) {
            return new RespEntity(ReturnCode.FALT,"参数错误");
        }
        RespEntity rs = new RespEntity();
        try {
             String sql ="select * from ("+
             " select w.dpid,w.inportdate,w.mid,m.rname,w.cardno,nvl(w.samt,0)samt,w.transdate,w.rrn,w.finaldate,decode(m.ism35,1,1,0)ism35,"+
             " (case when to_char(sysdate,'yyyyMMdd') >= w.finaldate then '2' else w.status end)status, "+
             " w.rname as r_name,w.raddr as r_addr,w.contactphone,w.contactperson,w.agremarks "+
             " from check_dispatchOrder w left join bill_merchantinfo m on w.mid=m.mid  where  w.orderType=:ORDERTYPE "+
             " and m.unno in(select UNNO from sys_unit start with unno =:UNNO and status=1 connect by NOCYCLE prior  unno =  upper_unit)"+
             " order by w.dpid desc "+
             " ) where 1=1 ";
             if(mistakeEntity.getType()==0) sql+=" and status='0' ";
             if(mistakeEntity.getType()==1) sql+=" and status<>'0' ";
             HashMap<String, Object> param = new HashMap<String, Object>();
             param.put("UNNO", mistakeEntity.getUnno());
             param.put("ORDERTYPE", mistakeEntity.getOrderType());
             List<Object[]> list=mistakeDao.queryByNativeSqlWithPageAndRows(sql,mistakeEntity.getPage(),mistakeEntity.getRows(),param);
             List<JSONObject> queryList = new ArrayList<>();
             JSONObject resultJson = new JSONObject();
             for(Object[] obj : list){
             	JSONObject json = new JSONObject();
             	json.put("dpId", Integer.parseInt(obj[0]+""));
             	json.put("inportDate", obj[1]+"");
             	json.put("mid", obj[2]+"");
             	json.put("rname", obj[3]+"");
             	json.put("cardNo", obj[4]+"");
             	json.put("samt", Double.parseDouble(obj[5]+""));
             	json.put("transDate", obj[6]==null?"":obj[6].toString().length()<8?
             			obj[6].toString():obj[6].toString().substring(0,8));
             	json.put("rrn", obj[7]+"");
             	json.put("finalDate", obj[8]+"");
             	json.put("ism35", Integer.parseInt(obj[9]+""));
             	json.put("status", Integer.parseInt(obj[10]+""));
             	json.put("r_name", obj[11]==null?"":obj[11]);
             	json.put("r_addr", obj[12]==null?"":obj[12]);
             	json.put("contactPhone", obj[13]==null?"":obj[13]);
             	json.put("contactPerson", obj[14]==null?"":obj[14]);
             	json.put("agRemarks", obj[15]==null?"":obj[15]);
             	queryList.add(json);
             }
             Integer count=mistakeDao.getRowsCount(sql, param);
             resultJson.put("unno", mistakeEntity.getUnno());
             resultJson.put("orderType", mistakeEntity.getOrderType());
             resultJson.put("data", queryList);
             resultJson.put("totalCount", count);
             
             rs.setReturnCode(ReturnCode.SUCCESS);
             rs.setReturnMsg("查询成功");
             rs.setReturnBody(resultJson);
		} catch (Exception e) {
			logger.info("查询/调单列表查询异常："+e.getMessage());
			 rs.setReturnMsg("查询失败");
	         rs.setReturnCode(ReturnCode.FALT);
		}
        return rs;
    }
	
	/**
	 * 查询 回复接口
	 * @param reqsBody
	 * @return
	 */
	public RespEntity updateMistake3Info(MistakeEntity misBody) {
        if (StringUtils.isEmpty(misBody.getDpId())) return new RespEntity(ReturnCode.FALT, "参数错误");
        if (StringUtils.isEmpty(misBody.getOrderType())) return new RespEntity(ReturnCode.FALT, "参数错误");
        MistakeModel model = mistakeRepository.getOne(misBody.getDpId().toString());
        return Optional.ofNullable(model).map(mistakeModel -> {
            if (!StringUtils.isEmpty(misBody.getRname())) mistakeModel.setRname(misBody.getRname());
            if (!StringUtils.isEmpty(misBody.getRaddr())) mistakeModel.setRaddr(misBody.getRaddr());
            if (!StringUtils.isEmpty(misBody.getContactPhone())) mistakeModel.setContactPhone(misBody.getContactPhone());
            if (!StringUtils.isEmpty(misBody.getContactPerson())) mistakeModel.setContactPerson(misBody.getContactPerson());
            if (!StringUtils.isEmpty(misBody.getAgRemarks())) mistakeModel.setAgRemarks(misBody.getAgRemarks());
            mistakeModel.setStatus(1);
            mistakeModel.setMaintainDate(new Date());
            return new RespEntity(ReturnCode.SUCCESS, "上传成功");
        }).orElseGet(() -> new RespEntity(ReturnCode.FALT, "上传失败"));
    }
	
	/**
	 * 调单/欺诈交易回复接口
	 * @param reqsBody
	 * @return
	 */
	public RespEntity updateMistake2Info(MistakeEntity misBody) {
        if (StringUtils.isEmpty(misBody.getDpId())) return new RespEntity(ReturnCode.FALT, "参数错误");
        if (StringUtils.isEmpty(misBody.getOrderType())) return new RespEntity(ReturnCode.FALT, "参数错误");
        MistakeModel model = mistakeRepository.getOne(misBody.getDpId().toString());
        return Optional.ofNullable(model).map(mistakeModel -> {
            if (!StringUtils.isEmpty(misBody.getRname())) mistakeModel.setRname(misBody.getRname());
            if (!StringUtils.isEmpty(misBody.getRaddr())) mistakeModel.setRaddr(misBody.getRaddr());
            if (!StringUtils.isEmpty(misBody.getContactPhone())) mistakeModel.setContactPhone(misBody.getContactPhone());
            if (!StringUtils.isEmpty(misBody.getContactPerson())) mistakeModel.setContactPerson(misBody.getContactPerson());
            if (!StringUtils.isEmpty(misBody.getAgRemarks())) mistakeModel.setAgRemarks(misBody.getAgRemarks());
            //调单图片上传
            if(!StringUtils.isEmpty(misBody.getOrderUploadFile())){
        		String imageDay=new SimpleDateFormat("yyyyMMdd").format(new Date()).toString();
				StringBuffer fName1 = new StringBuffer();
				fName1.append(mistakeModel.getDpId());
				fName1.append(".");
				fName1.append(mistakeModel.getRrn());
				fName1.append(".");
				fName1.append(imageDay);
				fName1.append(".order");
				fName1.append(misBody.getOrderUploadFile().getOriginalFilename().substring(misBody.getOrderUploadFile().getOriginalFilename().lastIndexOf(".")).toLowerCase());
				uploadMultipartFile(misBody.getOrderUploadFile(),fName1.toString(),imageDay);
				mistakeModel.setOrderUpload(imageDay+File.separator+fName1.toString());
        	}
            mistakeModel.setStatus(1);
            mistakeModel.setMaintainDate(new Date());
            return new RespEntity(ReturnCode.SUCCESS, "上传成功");
        }).orElseGet(() -> new RespEntity(ReturnCode.FALT, "上传失败"));
    }
	/**
	 * 上传
	 */
	private void uploadMultipartFile(MultipartFile file,String fName, String imageDay) {
		if (file!=null) {// 判断上传的文件是否为空
			Optional<FileUtilsModel> sysParamModel = fileUtilsRepository.findById(SysParam.HRT_TITLE);
            if(!sysParamModel.isPresent()){
                throw new AppException(ReturnCode.FALT,"获取调单上传图片路径错误");
            }
			String path=null;// 文件路径
			//生产路径上线放开
			String realPath=sysParamModel.get().getUpload_path()+ File.separator + imageDay;
			//测试路径
//			String realPath="D:\\upload\\dispatchOrder"+ File.separator + imageDay;
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
				logger.info("图片上传失败："+e.getMessage());
			}
			logger.info("文件上传成功");
		}
	}
}
