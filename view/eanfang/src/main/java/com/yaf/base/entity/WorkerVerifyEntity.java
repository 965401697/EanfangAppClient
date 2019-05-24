package com.yaf.base.entity;

import com.eanfang.biz.model.entity.AccountEntity;

import java.io.Serializable;
import java.util.List;


/**
 * 技师认证信息
 *
 * @author jornlin
 * @email jornlin@foxmail.com
 * @date 2017-11-30 20:02:44
 */
public class WorkerVerifyEntity extends TechWorkerVerifyEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 所属的account
     */
    private AccountEntity accountEntity;
    /**
     * 技师服务的 业务类型 id
     */
    private List<Long> businessList;
    /**
     * 技师的 服务类型 id
     */
    private List<Long> serviceList;
    /**
     * 技师服务的 区域 id
     */
    private List<Long> regionList;

    public AccountEntity getAccountEntity() {
        return this.accountEntity;
    }

    public List<Long> getBusinessList() {
        return this.businessList;
    }

    public List<Long> getServiceList() {
        return this.serviceList;
    }

    public List<Long> getRegionList() {
        return this.regionList;
    }

    public void setAccountEntity(AccountEntity accountEntity) {
        this.accountEntity = accountEntity;
    }

    public void setBusinessList(List<Long> businessList) {
        this.businessList = businessList;
    }

    public void setServiceList(List<Long> serviceList) {
        this.serviceList = serviceList;
    }

    public void setRegionList(List<Long> regionList) {
        this.regionList = regionList;
    }




    /*
     *===================================================================================================================================================
     *-----------------------------------------------------------------华丽的分割线------------------------------------------------------------------------
     *===================================================================================================================================================
     */


}
