package com.eanfang.biz.model.bean.datastatistics;

import com.eanfang.biz.model.entity.OrgEntity;

import java.io.Serializable;

/**
 * 描述：
 *
 * @author Guanluocang
 * @date on 2018/7/17$  18:23$
 */
public class DataStatisticsCompany implements Serializable {


    /**
     * countStaff : 0
     * level : 2
     * orgCode : c.c2
     * orgId : 1072683738892140545
     * orgName : 我的子公司
     * orgUnitEntity : {"orgId":"1072683738892140545","unitType":2}
     * parentOrgId : 985770118343135234
     */

    private int countStaff;
    private int level;
    private String orgCode;
    private String orgId;
    private String orgName;
    private OrgEntity orgUnitEntity;
    private String parentOrgId;

    public int getCountStaff() {
        return this.countStaff;
    }

    public int getLevel() {
        return this.level;
    }

    public String getOrgCode() {
        return this.orgCode;
    }

    public String getOrgId() {
        return this.orgId;
    }

    public String getOrgName() {
        return this.orgName;
    }

    public OrgEntity getOrgUnitEntity() {
        return this.orgUnitEntity;
    }

    public String getParentOrgId() {
        return this.parentOrgId;
    }

    public void setCountStaff(int countStaff) {
        this.countStaff = countStaff;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public void setOrgUnitEntity(OrgEntity orgUnitEntity) {
        this.orgUnitEntity = orgUnitEntity;
    }

    public void setParentOrgId(String parentOrgId) {
        this.parentOrgId = parentOrgId;
    }
}
