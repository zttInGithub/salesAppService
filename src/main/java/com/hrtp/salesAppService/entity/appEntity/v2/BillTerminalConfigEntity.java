package com.hrtp.salesAppService.entity.appEntity.v2;

import com.hrtp.salesAppService.entity.ormEntity.v2.BillTerminalConfigModel;

/**
 * BillTerminalConfigEntity
 * <p>
 * This is description
 * </p>
 *
 * @author xuegangliu 2019/07/12
 * @since 1.8
 **/
public class BillTerminalConfigEntity extends BillTerminalConfigModel {
    private String unName;

    private String subName;

    private String upperName;

    public String getUnName() {
        return unName;
    }

    public void setUnName(String unName) {
        this.unName = unName;
    }

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public String getUpperName() {
        return upperName;
    }

    public void setUpperName(String upperName) {
        this.upperName = upperName;
    }
}
