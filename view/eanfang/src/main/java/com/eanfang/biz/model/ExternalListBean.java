package com.eanfang.biz.model;

import java.io.Serializable;

/**
 * Created by MrHou
 *
 * @on 2018/1/23  19:03
 * @email houzhongzhou@yeah.net
 * @desc
 */

public class ExternalListBean implements Serializable{

    /**
     * level : 0
     * orgId : 955740210430304257
     * orgName : 地球环境公司
     */

    private int level;
    private Long orgId;
    private String orgName;

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName == null ? "" : orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }
}
