package com.eanfang.biz.model;

import java.io.Serializable;

/**
 * 描述：
 *
 * @author Guanluocang
 * @date on 2018/5/30$  16:13$
 */
public class RepairOpenAreaBean implements Serializable{

    private String baseDataId;
    private String createTime;
    private String createUserId;
    private int doorFee;
    private int id;
    private int status;

    public String getBaseDataId() {
        return this.baseDataId;
    }

    public String getCreateTime() {
        return this.createTime;
    }

    public String getCreateUserId() {
        return this.createUserId;
    }

    public int getDoorFee() {
        return this.doorFee;
    }

    public int getId() {
        return this.id;
    }

    public int getStatus() {
        return this.status;
    }

    public void setBaseDataId(String baseDataId) {
        this.baseDataId = baseDataId;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public void setDoorFee(int doorFee) {
        this.doorFee = doorFee;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
