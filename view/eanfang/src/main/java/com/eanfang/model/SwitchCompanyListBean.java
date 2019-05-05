package com.eanfang.model;

import com.eanfang.model.sys.OrgEntity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by MrHou
 *
 * @on 2017/12/7  14:57
 * @email houzhongzhou@yeah.net
 * @desc
 */
public class SwitchCompanyListBean implements Serializable {
    private Long id;

    /**
     * code : 20000
     * data : [{"companyId":"938220805202989058","isVerify":0,"level":2,"orgCode":"c.c1","orgId":"938220805202989058","orgName":"测试公司","orgType":1,"parentOrgId":-1,"sortNum":0,"topCompanyId":-1,"updateTime":"2017-12-06 09:37","updateUser":1,"updateUserEntity":{"accId":1,"accountEntity":{"accId":1,"accType":0,"avatar":"0","companyAdmin":false,"email":"29698868@qq.com","lastLoginTime":"2017-12-07 09:23","loginCount":170,"mobile":"13011054008","nickName":"一灯","passwd":"0DPiKuNIrrVmD8IUCuw1hQxNqZc=","realName":"徐定1","regTime":"2017-11-23 12:00","status":0,"superAdmin":true,"sysAdmin":true},"companyId":-1,"createTime":"2017-11-23 12:00","createUser":1,"departmentId":-1,"regType":0,"status":1,"topCompanyId":-1,"updateTime":"2017-11-23 12:00","updateUser":1,"userId":1}}]
     */

    private List<OrgEntity> data;

    public Long getId() {
        return this.id;
    }

    public List<OrgEntity> getData() {
        return this.data;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setData(List<OrgEntity> data) {
        this.data = data;
    }
}
