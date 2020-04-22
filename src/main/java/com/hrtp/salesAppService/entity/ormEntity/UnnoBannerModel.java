package com.hrtp.salesAppService.entity.ormEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * UnnoBannerModel
 * description banner
 * create by lxj 2018/8/28
 **/
@Entity
@Table(name = "PG_UNNO_BANNER")
public class UnnoBannerModel implements Serializable{
    private static final long version = 1L;
    @Id
    @Column(name = "ID")
    private String id;
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "IMAGE_URL")
    private String imgUrl;
    @Column(name = "ENABLED")
    private Integer enabled;
    @Column(name = "IMAGE_ORDER")
    private Integer imageOrder;
    @Column(name = "VALID_TIME")
    private Date validTime;
    @Column(name = "CREATE_TIME")
    private Date createTime;
    @Column(name = "TYPE")
    private Integer type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Integer getEnabled() {
        return enabled;
    }

    public void setEnabled(Integer enabled) {
        this.enabled = enabled;
    }

    public Integer getImageOrder() {
        return imageOrder;
    }

    public void setImageOrder(Integer imageOrder) {
        this.imageOrder = imageOrder;
    }

    public Date getValidTime() {
        return validTime;
    }

    public void setValidTime(Date validTime) {
        this.validTime = validTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
