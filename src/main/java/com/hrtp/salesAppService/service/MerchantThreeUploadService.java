package com.hrtp.salesAppService.service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.hrtp.system.util.PictureWaterMarkUtil;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import com.hrtp.salesAppService.dao.MerchManagerRepository;
import com.hrtp.salesAppService.dao.MerchantThreeUploadRepository;
import com.hrtp.salesAppService.dao.SysParamRepository;
import com.hrtp.salesAppService.entity.appEntity.MerchantThreeUploadEntity;
import com.hrtp.salesAppService.entity.appEntity.RespEntity;
import com.hrtp.salesAppService.entity.ormEntity.MerchManagerModel;
import com.hrtp.salesAppService.entity.ormEntity.MerchantThreeUploadModel;
import com.hrtp.salesAppService.entity.ormEntity.SysParamModel;
import com.hrtp.salesAppService.exception.AppException;
import com.hrtp.system.costant.ReturnCode;
import com.hrtp.system.costant.SysParam;
import org.slf4j.Logger;

@Service
public class MerchantThreeUploadService {
	
	private static Logger logger = LoggerFactory.getLogger(MerchantThreeUploadService.class);
	
	@Autowired
	private MerchantThreeUploadRepository merchantThreeUploadRepository;
	@Autowired
    private SysParamRepository sysParamRepository;
	@Autowired
	private MerchManagerRepository merchManagerRepository;
	
	
	public RespEntity saveMerchantThreeUploadInfo(MerchantThreeUploadEntity mtueEntity) {
		
		if(StringUtils.isEmpty(mtueEntity.getMid())){
			return new RespEntity(ReturnCode.FALT,"商户编号不可为空");
		}
		if(StringUtils.isEmpty(mtueEntity.getTid())){
			return new RespEntity(ReturnCode.FALT,"终端编号不可为空");
		}
		if(StringUtils.isEmpty(mtueEntity.getLicense_Name())){
			return new RespEntity(ReturnCode.FALT,"商户营业执照名称不可为空");
		}
		if(StringUtils.isEmpty(mtueEntity.getR_Name())){
			return new RespEntity(ReturnCode.FALT,"门店经营名称不可为空");
		}
		if(StringUtils.isEmpty(mtueEntity.getR_Addr())){
			return new RespEntity(ReturnCode.FALT,"门店经营地址不可为空");
		}
	
		//限制图片大小1-7 。视频文件暂不限制
		if(mtueEntity.getMerUpload1File() != null && mtueEntity.getMerUpload1File().getSize()>1024*512){
			return new RespEntity(ReturnCode.FALT,"您上传的非接交易小票图片大于512KB，请重新添加！");
		}
		if(mtueEntity.getMerUpload2File() != null && mtueEntity.getMerUpload2File().getSize()>1024*512){
			return new RespEntity(ReturnCode.FALT,"您上传的银联二维码交易小票图片大于512KB，请重新添加！");
		}
		if(mtueEntity.getMerUpload3File() != null && mtueEntity.getMerUpload3File().getSize()>1024*512){
			return new RespEntity(ReturnCode.FALT,"您上传的pos机反面图片大于512KB，请重新添加！");
		}
		if(mtueEntity.getMerUpload4File() != null && mtueEntity.getMerUpload4File().getSize()>1024*512){
			return new RespEntity(ReturnCode.FALT,"您上传的门店图片大于512KB，请重新添加！");
		}
		if(mtueEntity.getMerUpload5File() != null && mtueEntity.getMerUpload5File().getSize()>1024*512){
			return new RespEntity(ReturnCode.FALT,"您上传的店内经营图片大于512KB，请重新添加！");
		}
		if(mtueEntity.getMerUpload6File() != null && mtueEntity.getMerUpload6File().getSize()>1024*512){
			return new RespEntity(ReturnCode.FALT,"您上传的云闪付标识图片大于512KB，请重新添加！");
		}
		if(mtueEntity.getMerUpload7File() != null && mtueEntity.getMerUpload7File().getSize()>1024*512){
			return new RespEntity(ReturnCode.FALT,"您上传的银联标识图片大于512KB，请重新添加！");
		}
		
		MerchManagerModel merModel = merchManagerRepository.findMerByMid(mtueEntity.getMid());
		if (StringUtils.isEmpty(merModel)) return new RespEntity(ReturnCode.FALT, "商户号错误，请重新填写");
		
		List<MerchManagerModel> merList = merchManagerRepository.findMerByUnnoAdnMid(mtueEntity.getUnno(),mtueEntity.getMid());
        if (merList.size() <= 0) return new RespEntity(ReturnCode.FALT, "当前机构名下未发现该商户信息，请核实");
        
		List<MerchantThreeUploadModel> exsitList = merchantThreeUploadRepository.findByMidExsit(mtueEntity.getMid());
        if (exsitList.size() > 0) return new RespEntity(ReturnCode.FALT, "资料已上传，请耐心等待审核结果");
		
		MerchantThreeUploadModel mtum = new MerchantThreeUploadModel();
		if(mtueEntity.getSn()!=null && !"".equals(mtueEntity.getSn())) {
			mtum.setSn(mtueEntity.getSn());
		}
		mtum.setMid(mtueEntity.getMid());
		mtum.setTid(mtueEntity.getTid());
		mtum.setLicense_name(mtueEntity.getLicense_Name());
		mtum.setR_name(mtueEntity.getR_Name());
		mtum.setR_addr(mtueEntity.getR_Addr());
		mtum.setIsConnect(mtueEntity.getIsConnect());
		mtum.setIsImmunity(mtueEntity.getIsImmunity());
		mtum.setIsUnionPay(mtueEntity.getIsUnionPay());
		mtum.setMaintainDate(new Date());
		mtum.setMaintainType("A");
		mtum.setApproveStatus("W");
		try {
			mtum = uploadImg(mtueEntity,mtum);
			logger.info("云闪付资料上传成功:"+mtueEntity.getMid());
        } catch (Exception e) {
        	logger.info("云闪付资料上传失败");
            throw new AppException(ReturnCode.FALT,"图片上传失败");
        }
		merchantThreeUploadRepository.save(mtum);
		return new RespEntity("00","资料上传成功");
	}
	private MerchantThreeUploadModel uploadImg(MerchantThreeUploadEntity mtue, MerchantThreeUploadModel mtue1) throws Exception {
		String imageDay = new SimpleDateFormat("yyyyMMdd").format(new Date()).toString();
		//非接交易小票
		if(mtue.getMerUpload1File() != null){
			StringBuffer fName1 = new StringBuffer();
			fName1.append(mtue.getMid());
			fName1.append(".");
			fName1.append(imageDay);
			fName1.append(".ysf");//云闪付上传专用名称
			fName1.append(".1");
			fName1.append(mtue.getMerUpload1File().getOriginalFilename().substring(mtue.getMerUpload1File().getOriginalFilename().lastIndexOf(".")).toLowerCase());
			uploadFile(mtue.getMerUpload1File(),fName1.toString(),imageDay);
			mtue1.setMerUpload1(fName1.toString());
		}
		//银联二维码交易
		if(mtue.getMerUpload2File() != null){
			StringBuffer fName2 = new StringBuffer();
			fName2.append(mtue.getMid());
			fName2.append(".");
			fName2.append(imageDay);
			fName2.append(".ysf");
			fName2.append(".2");
			fName2.append(mtue.getMerUpload2File().getOriginalFilename().substring(mtue.getMerUpload2File().getOriginalFilename().lastIndexOf(".")).toLowerCase());
			uploadFile(mtue.getMerUpload2File(),fName2.toString(),imageDay);
			mtue1.setMerUpload2(fName2.toString());
		}
		//pos机反面照片
		if(mtue.getMerUpload3File() != null){
			StringBuffer fName3 = new StringBuffer();
			fName3.append(mtue.getMid());
			fName3.append(".");
			fName3.append(imageDay);
			fName3.append(".ysf");
			fName3.append(".3");
			fName3.append(mtue.getMerUpload3File().getOriginalFilename().substring(mtue.getMerUpload3File().getOriginalFilename().lastIndexOf(".")).toLowerCase());
			uploadFile(mtue.getMerUpload3File(),fName3.toString(),imageDay);
			mtue1.setMerUpload3(fName3.toString());
		}
		//门店照片
		if(mtue.getMerUpload4File() != null){
			StringBuffer fName4 = new StringBuffer();
			fName4.append(mtue.getMid());
			fName4.append(".");
			fName4.append(imageDay);
			fName4.append(".ysf");
			fName4.append(".4");
			fName4.append(mtue.getMerUpload4File().getOriginalFilename().substring(mtue.getMerUpload4File().getOriginalFilename().lastIndexOf(".")).toLowerCase());
			uploadFile(mtue.getMerUpload4File(),fName4.toString(),imageDay);
			mtue1.setMerUpload4(fName4.toString());
		}
		//店内经营照片
		if(mtue.getMerUpload5File() != null){
			StringBuffer fName5 = new StringBuffer();
			fName5.append(mtue.getMid());
			fName5.append(".");
			fName5.append(imageDay);
			fName5.append(".ysf");
			fName5.append(".5");
			fName5.append(mtue.getMerUpload5File().getOriginalFilename().substring(mtue.getMerUpload5File().getOriginalFilename().lastIndexOf(".")).toLowerCase());
			uploadFile(mtue.getMerUpload5File(),fName5.toString(),imageDay);
			mtue1.setMerUpload5(fName5.toString());
		}
		//云闪付标识照片
		if(mtue.getMerUpload6File() != null){
			StringBuffer fName6 = new StringBuffer();
			fName6.append(mtue.getMid());
			fName6.append(".");
			fName6.append(imageDay);
			fName6.append(".ysf");
			fName6.append(".6");
			fName6.append(mtue.getMerUpload6File().getOriginalFilename().substring(mtue.getMerUpload6File().getOriginalFilename().lastIndexOf(".")).toLowerCase());
			uploadFile(mtue.getMerUpload6File(),fName6.toString(),imageDay);
			mtue1.setMerUpload6(fName6.toString());
		}
		//银联标识照片
		if(mtue.getMerUpload7File() != null){
			StringBuffer fName7 = new StringBuffer();
			fName7.append(mtue.getMid());
			fName7.append(".");
			fName7.append(imageDay);
			fName7.append(".ysf");
			fName7.append(".7");
			fName7.append(mtue.getMerUpload7File().getOriginalFilename().substring(mtue.getMerUpload7File().getOriginalFilename().lastIndexOf(".")).toLowerCase());
			uploadFile(mtue.getMerUpload7File(),fName7.toString(),imageDay);
			mtue1.setMerUpload7(fName7.toString());
		}
		//非接改造视频
		if(mtue.getMerUpload8File() != null){
			StringBuffer fName8 = new StringBuffer();
			fName8.append(mtue.getMid());
			fName8.append(".");
			fName8.append(imageDay);
			fName8.append(".ysf");
			fName8.append(".8");
			fName8.append(mtue.getMerUpload8File().getOriginalFilename().substring(mtue.getMerUpload8File().getOriginalFilename().lastIndexOf(".")).toLowerCase());
			uploadFile(mtue.getMerUpload8File(),fName8.toString(),imageDay);
			mtue1.setMerUpload8(fName8.toString());
		}
        return mtue1;
    }
	
	/**
	 * 上传
	 */
	private void uploadFile(MultipartFile upload, String fName, String imageDay) throws Exception {
        try {
            Optional<SysParamModel> paramModel = sysParamRepository.findById(SysParam.YSF_TITLE);
            if(!paramModel.isPresent()){
                throw new AppException(ReturnCode.FALT,"获取云闪付资料上传路径错误");
            }
            // 放开
            String savePath = paramModel.get().getUploadPath();
//            String savePath = "D:\\upload\\YSFUpload";
            String realPath = savePath + File.separator + imageDay;
            File dir = new File(realPath);
            if(!dir.exists()){
                dir.mkdirs();
            }
            String fPath = realPath + File.separator + fName;
//            File destFile = new File(fPath);
			// @date:20190125 云闪付资料上传的图片添加水印
			PictureWaterMarkUtil.addWatermark(upload.getInputStream(),fPath, SysParam.PICTURE_WATER_MARK,
					fName.substring(fName.lastIndexOf(".")+1));
//            upload.transferTo(destFile);
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException(ReturnCode.FALT,"云闪付资料保存错误");
        }
    }
}
