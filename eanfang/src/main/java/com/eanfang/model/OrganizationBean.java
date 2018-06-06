package com.eanfang.model;

import com.baozi.treerecyclerview.base.BaseItemData;

import java.util.List;

/**
 * Created by O u r on 2018/5/30.
 */

public class OrganizationBean extends BaseItemData {

    /**
     * companyId : 981408170067369986
     * countStaff : 0
     * level : 1
     * orgCode : c
     * orgId : 981408170067369986
     * orgName : 一个一个又一个
     * orgType : 0
     * topCompanyId : 981408170067369986
     * updateTime : 2018-04-04 13:48:36
     * updateUser : 0
     * verifyStatus : 0
     */

    private String companyId;
    private int countStaff;
    private int level;
    private String orgCode;
    private String orgId;
    private String orgName;
    private int orgType;
    private String topCompanyId;
    private String updateTime;
    private String updateUser;
    private int verifyStatus;

    private List<SectionBean> sectionBeanList;


    public List<SectionBean> getSectionBeanList() {
        return sectionBeanList;
    }

    public void setSectionBeanList(List<SectionBean> sectionBeanList) {
        this.sectionBeanList = sectionBeanList;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public int getCountStaff() {
        return countStaff;
    }

    public void setCountStaff(int countStaff) {
        this.countStaff = countStaff;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public int getOrgType() {
        return orgType;
    }

    public void setOrgType(int orgType) {
        this.orgType = orgType;
    }

    public String getTopCompanyId() {
        return topCompanyId;
    }

    public void setTopCompanyId(String topCompanyId) {
        this.topCompanyId = topCompanyId;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public int getVerifyStatus() {
        return verifyStatus;
    }

    public void setVerifyStatus(int verifyStatus) {
        this.verifyStatus = verifyStatus;
    }
}