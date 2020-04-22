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
import com.hrtp.salesAppService.dao.MerchantTwoUploadRepository;
import com.hrtp.salesAppService.dao.SysParamRepository;
import com.hrtp.salesAppService.entity.appEntity.MerchantTwoUploadEntity;
import com.hrtp.salesAppService.entity.appEntity.RespEntity;
import com.hrtp.salesAppService.entity.ormEntity.MerchManagerModel;
import com.hrtp.salesAppService.entity.ormEntity.MerchantTwoUploadModel;
import com.hrtp.salesAppService.entity.ormEntity.SysParamModel;
import com.hrtp.salesAppService.exception.AppException;
import com.hrtp.system.costant.ReturnCode;
import com.hrtp.system.costant.SysParam;
import org.slf4j.Logger;

@Service
public class MerchantTwoUploadService {
	
	private static Logger logger = LoggerFactory.getLogger(MerchantTwoUploadService.class);
	
	@Autowired
	private MerchantTwoUploadRepository merchantTwoUploadRepository;
	@Autowired
    private SysParamRepository sysParamRepository;
	@Autowired
	private MerchManagerRepository merchManagerRepository;
	
	
	public RespEntity saveMerchantTwoUploadInfo(MerchantTwoUploadEntity mtueEntity) {
		
		if(StringUtils.isEmpty(mtueEntity.getMid())){
			return new RespEntity(ReturnCode.FALT,"商户编号不可为空");
		}
		
		MerchManagerModel merModel = merchManagerRepository.findMerByMid(mtueEntity.getMid());
		if (StringUtils.isEmpty(merModel.getMid())) return new RespEntity(ReturnCode.FALT, "商户号错误，请重新填写");
		
		List<MerchManagerModel> merList = merchManagerRepository.findMerByUnnoAdnMid(mtueEntity.getUnno(),mtueEntity.getMid());
        if (merList.size() <= 0) return new RespEntity(ReturnCode.FALT, "当前机构名下未发现该商户信息，请核实");
        
		List<MerchantTwoUploadModel> exsitList = merchantTwoUploadRepository.findByMidExsit(mtueEntity.getMid());
        if (exsitList.size() > 0) return new RespEntity(ReturnCode.FALT, "已上传资料，请耐心等待审核结果");
		
		MerchantTwoUploadModel mtum = new MerchantTwoUploadModel();
		try {
			mtum = uploadImg(mtueEntity,mtum);
        } catch (Exception e) {
        	logger.info("二次上传资料失败");
            throw new AppException(ReturnCode.FALT,"上传图片失败");
        }
		mtum.setMid(mtueEntity.getMid());
		mtum.setMaintaindate(new Date());
		mtum.setMaintaintype("A");
		mtum.setApproveStatus("W");
		merchantTwoUploadRepository.save(mtum);
		return new RespEntity("00","资料上传成功");
	}
	private MerchantTwoUploadModel uploadImg(MerchantTwoUploadEntity mtue, MerchantTwoUploadModel mtue1) throws Exception {
		String imageDay = new SimpleDateFormat("yyyyMMdd").format(new Date()).toString();
		//上传文件
		if(mtue.getLegalNameFile() != null){
			StringBuffer fName1 = new StringBuffer();
			fName1.append(mtue.getMid());
			fName1.append(".");
			fName1.append(imageDay);
			fName1.append(".21");//二次上传专用名称
			fName1.append(mtue.getLegalNameFile().getOriginalFilename().substring(mtue.getLegalNameFile().getOriginalFilename().lastIndexOf(".")).toLowerCase());
			uploadFile(mtue.getLegalNameFile(),fName1.toString(),imageDay);
			mtue1.setLegalName(fName1.toString());
		}
		if(mtue.getLegalName2File() != null){
			StringBuffer fName2 = new StringBuffer();
			fName2.append(mtue.getMid());
			fName2.append(".");
			fName2.append(imageDay);
			fName2.append(".22");
			fName2.append(mtue.getLegalName2File().getOriginalFilename().substring(mtue.getLegalName2File().getOriginalFilename().lastIndexOf(".")).toLowerCase());
			uploadFile(mtue.getLegalName2File(),fName2.toString(),imageDay);
			mtue1.setLegalName2(fName2.toString());
		}
		if(mtue.getBupLoadFile() != null){
			StringBuffer fName3 = new StringBuffer();
			fName3.append(mtue.getMid());
			fName3.append(".");
			fName3.append(imageDay);
			fName3.append(".23");
			fName3.append(mtue.getBupLoadFile().getOriginalFilename().substring(mtue.getBupLoadFile().getOriginalFilename().lastIndexOf(".")).toLowerCase());
			uploadFile(mtue.getBupLoadFile(),fName3.toString(),imageDay);
			mtue1.setBupLoad(fName3.toString());
		}
		if(mtue.getBigdealUpLoadFile() != null){
			StringBuffer fName4 = new StringBuffer();
			fName4.append(mtue.getMid());
			fName4.append(".");
			fName4.append(imageDay);
			fName4.append(".24");
			fName4.append(mtue.getBigdealUpLoadFile().getOriginalFilename().substring(mtue.getBigdealUpLoadFile().getOriginalFilename().lastIndexOf(".")).toLowerCase());
			uploadFile(mtue.getBigdealUpLoadFile(),fName4.toString(),imageDay);
			mtue1.setBigdealUpLoad(fName4.toString());
		}
		if(mtue.getLaborContractImgFile() != null){
			StringBuffer fName4 = new StringBuffer();
			fName4.append(mtue.getMid());
			fName4.append(".");
			fName4.append(imageDay);
			fName4.append(".25");
			fName4.append(mtue.getLaborContractImgFile().getOriginalFilename().substring(mtue.getLaborContractImgFile().getOriginalFilename().lastIndexOf(".")).toLowerCase());
			uploadFile(mtue.getLaborContractImgFile(),fName4.toString(),imageDay);
			mtue1.setLaborContractImg(fName4.toString());
		}
		logger.info("资料二次上传成功:"+mtue.getMid());
        return mtue1;
    }
	
	/**
	 * 上传
	 */
	private void uploadFile(MultipartFile upload, String fName, String imageDay) throws Exception {
        try {
            Optional<SysParamModel> paramModel = sysParamRepository.findById(SysParam.HRT_TITLE);
            if(!paramModel.isPresent()){
                throw new AppException(ReturnCode.FALT,"获取二次上传图片路径错误");
            }
            // 放开
            String savePath = paramModel.get().getUploadPath();
//            String savePath = "D:\\upload\\HrtFrameWork";
            String realPath = savePath + File.separator + imageDay;
            File dir = new File(realPath);
            if(!dir.exists()){
                dir.mkdirs();
            }
            String fPath = realPath + File.separator + fName;
//            File destFile = new File(fPath);
            // @date:20180125 二次资料上传添加水印
			PictureWaterMarkUtil.addWatermark(upload.getInputStream(),fPath, SysParam.PICTURE_WATER_MARK,
					fName.substring(fName.lastIndexOf(".")+1));
//            upload.transferTo(destFile);
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException(ReturnCode.FALT,"商户二次资料上传保存错误");
        }
    }
}
