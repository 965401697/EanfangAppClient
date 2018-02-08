package com.eanfang.model;

import java.io.Serializable;

/**
 * Created by MrHou
 *
 * @on 2018/2/8  15:02
 * @email houzhongzhou@yeah.net
 * @desc
 */

public class SwithCompanyBean implements Serializable {

    /**
     * level : 1
     * orgCode : c
     * orgId : 958616139716362241
     * orgName : 褡裢坡修监控连锁3
     */

    private int level;
    private String orgCode;
    private Long orgId;
    private String orgName;

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

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }
}
