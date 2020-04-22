package com.hrtp.salesAppService.entity.appEntity;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.multipart.MultipartFile;

/**
 * BillAgentTxnInfoEntity
 * description TODO
 * create by lxj 2018/8/27
 **/
public class BillAgentTxnInfoEntity {
    private String accType;
    private String areaType;
    private MultipartFile authUpLoadFile;
    private String bankAccName;
    private String bankAccNo;
    private String bankArea;
    private String bankBranch;
    private String bankType;
    private String buId;
    private MultipartFile legalAUpLoadFile;
    private MultipartFile legalBUpLoadFile;
    private MultipartFile legalHandUpLoadFile;
    private String unno;

    public String getAccType() {
        return accType;
    }

    public void setAccType(String accType) {
        this.accType = accType;
    }

    public String getAreaType() {
        return areaType;
    }

    public void setAreaType(String areaType) {
        this.areaType = areaType;
    }

    public MultipartFile getAuthUpLoadFile() {
        return authUpLoadFile;
    }

    public void setAuthUpLoadFile(MultipartFile authUpLoadFile) {
        this.authUpLoadFile = authUpLoadFile;
    }

    public String getBankAccName() {
        return bankAccName;
    }

    public void setBankAccName(String bankAccName) {
        this.bankAccName = bankAccName;
    }

    public String getBankAccNo() {
        return bankAccNo;
    }

    public void setBankAccNo(String bankAccNo) {
        this.bankAccNo = bankAccNo;
    }

    public String getBankArea() {
        return bankArea;
    }

    public void setBankArea(String bankArea) {
        this.bankArea = bankArea;
    }

    public String getBankBranch() {
        return bankBranch;
    }

    public void setBankBranch(String bankBranch) {
        this.bankBranch = bankBranch;
    }

    public String getBankType() {
        return bankType;
    }

    public void setBankType(String bankType) {
        this.bankType = bankType;
    }

    public String getBuId() {
        return buId;
    }

    public void setBuId(String buId) {
        this.buId = buId;
    }

    public MultipartFile getLegalAUpLoadFile() {
        return legalAUpLoadFile;
    }

    public void setLegalAUpLoadFile(MultipartFile legalAUpLoadFile) {
        this.legalAUpLoadFile = legalAUpLoadFile;
    }

    public MultipartFile getLegalBUpLoadFile() {
        return legalBUpLoadFile;
    }

    public void setLegalBUpLoadFile(MultipartFile legalBUpLoadFile) {
        this.legalBUpLoadFile = legalBUpLoadFile;
    }

    public MultipartFile getLegalHandUpLoadFile() {
        return legalHandUpLoadFile;
    }

    public void setLegalHandUpLoadFile(MultipartFile legalHandUpLoadFile) {
        this.legalHandUpLoadFile = legalHandUpLoadFile;
    }

    public String getUnno() {
        return unno;
    }

    public void setUnno(String unno) {
        this.unno = unno;
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
