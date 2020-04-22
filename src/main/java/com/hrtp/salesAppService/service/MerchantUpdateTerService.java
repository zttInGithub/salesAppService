package com.hrtp.salesAppService.service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.hrtp.system.costant.SysParam;
import com.hrtp.system.util.PictureWaterMarkUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.hrtp.salesAppService.controller.HomePageController;
import com.hrtp.salesAppService.dao.MerchantTerminalInfoRepository;
import com.hrtp.salesAppService.dao.MerchantUpdateTerRepository;
import com.hrtp.salesAppService.dao.SysParamRepository;
import com.hrtp.salesAppService.dao.TerminalInfoRepository;
import com.hrtp.salesAppService.entity.appEntity.MerchantUpdateTerEntity;
import com.hrtp.salesAppService.entity.appEntity.RespEntity;
import com.hrtp.salesAppService.entity.ormEntity.MerchantTerminalInfoModel;
import com.hrtp.salesAppService.entity.ormEntity.MerchantUpdateTerModel;
import com.hrtp.salesAppService.entity.ormEntity.SysParamModel;
import com.hrtp.salesAppService.entity.ormEntity.TerminalInfoModel;
import com.hrtp.salesAppService.exception.AppException;
import com.hrtp.system.costant.ReturnCode;
import com.hrtp.system.util.HttpXmlClient;

@Service
public class MerchantUpdateTerService {
	private static final Logger logger = LoggerFactory.getLogger(HomePageController.class);
	@Autowired
	private MerchantUpdateTerRepository merchantUpdateTerRepository;
	@Autowired
	private SysParamRepository sysParamRepository;
	@Autowired
	private TerminalInfoRepository terminalInfoRepository;
	@Value("${admAppIp}")
	private  String admAppIp;
	
	public RespEntity saveMerchantUpdateTer(MerchantUpdateTerEntity mute) throws Exception {
		Integer i = merchantUpdateTerRepository.findCountByApproveType(mute.getMid());
		if(i>0) {
			return new RespEntity(ReturnCode.FALT, "该商户存在未审核申请，请耐心等待");
		}
		MerchantUpdateTerModel model = new MerchantUpdateTerModel();
		BeanUtils.copyProperties(mute, model);
		if("1".equals(mute.getType())) {//换机
			//SN照片保存
			String imageDay=new SimpleDateFormat("yyyyMMdd").format(new Date()).toString();
			if(mute.getSnImg() != null && !StringUtils.isEmpty(mute.getSnImg().getOriginalFilename())){
				StringBuffer fName = new StringBuffer();
				fName.append(model.getMid());
				fName.append(".");
				fName.append(imageDay);
				fName.append(".snImg");
				fName.append(mute.getSnImg().getOriginalFilename().substring(mute.getSnImg().getOriginalFilename().lastIndexOf(".")).toLowerCase());
				uploadFile(mute.getSnImg(), fName.toString(),imageDay);
				model.setSnImg(fName.toString());
			}
			model.setApproveType("W");
			model.setMaintainDate(new Date());
			model.setMaintainType("A");
			merchantUpdateTerRepository.save(model);
		}else if("2".equals(mute.getType())) {//撤机
			model.setApproveType("Y");
			model.setMaintainDate(new Date());
			model.setMaintainType("A");
			TerminalInfoModel model2 = terminalInfoRepository.findTerminalInfoByTermID(model.getTid());
			if(model2==null) {
				return new RespEntity(ReturnCode.FALT, "提交失败");
			}
			//推送综合
			Map<String,String> map2 = new HashMap<String, String>();
			map2.put("type", "3");
			map2.put("mid", model.getMid());
			map2.put("tid", model.getTid());
			map2.put("sn", model2.getSn());
			String post = HttpXmlClient.post(admAppIp+"/AdmApp/merchacc/merchant_receiveUpBD.action", map2);
			boolean flag = (Boolean) ((Map<String, Object>) JSONObject.parse(post)).get("success");
			if(!flag){
				logger.info("撤机申请推送综合失败,mid="+model.getMid()+",tid="+model.getTid());
				throw new AppException(ReturnCode.FALT,"提交失败");
			}
			MerchantUpdateTerModel terModel = merchantUpdateTerRepository.save(model);
			model.setMaintainType("A");
		}else if("3".equals(mute.getType())) {//关闭商户
			model.setApproveType("Y");
			model.setMaintainDate(new Date());
			model.setMaintainType("A");
			//推送综合
			Map<String,String> map2 = new HashMap<String, String>();
			map2.put("type", "1");
			map2.put("mid", model.getMid());
			String post = HttpXmlClient.post(admAppIp+"/AdmApp/merchacc/merchant_receiveUpBD.action", map2);
			boolean flag = (Boolean) ((Map<String, Object>) JSONObject.parse(post)).get("success");
			if(!flag){
				logger.info("关闭商户推送综合失败,mid="+model.getMid());
				throw new AppException(ReturnCode.FALT,"提交失败");
			}
			merchantUpdateTerRepository.save(model);
		}else {
			return new RespEntity(ReturnCode.FALT, "提交失败");
		}
		return new RespEntity(ReturnCode.SUCCESS, "提交成功");
	}
	private void uploadFile(MultipartFile upload, String fName, String imageDay) throws Exception {
        try {
        	 Optional<SysParamModel> paramModel = sysParamRepository.findById("HrtFrameWork");
             if(!paramModel.isPresent()){
                 throw new AppException(ReturnCode.FALT,"换机申请图片保存路径错误");
             }
            // 放开
	        String savePath = paramModel.get().getUploadPath();
//            String savePath = "D://u01/hrtwork/HrtFrameWork";
            String realPath = savePath + File.separator + imageDay;
            File dir = new File(realPath);
            if(!dir.exists()){
                dir.mkdirs();
            }
            String fPath = realPath + File.separator + fName;
//            File destFile = new File(fPath);
			// @date:20190125 换机申请上传的图片添加水印
			PictureWaterMarkUtil.addWatermark(upload.getInputStream(),fPath, SysParam.PICTURE_WATER_MARK,
					fName.substring(fName.lastIndexOf(".")+1));
//            upload.transferTo(destFile);
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException(ReturnCode.FALT,"换机申请图片保存路径错误");
        }
    }
}
