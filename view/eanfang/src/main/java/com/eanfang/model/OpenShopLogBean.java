package com.eanfang.model;

import com.yaf.base.entity.OpenShopLogEntity;

import java.util.List;

/**
 * Created by O u r on 2018/5/23.
 */

public class OpenShopLogBean {

    /**
     * currPage : 1
     * list : [{"assigneeCompanyId":"958589807934590978","assigneeOrgCode":"c","assigneePhone":"李旭","assigneeTopCompanyId":"958589807934590978","assigneeUser":{"accId":"980695065603649538","accountEntity":{"accId":"980695065603649538","accType":0,"avatar":"0","mobile":"15900000005","realName":"李旭"},"companyAdmin":false,"superAdmin":false,"sysAdmin":false,"topCompanyId":"958589807934590978","userId":"980695066010497026"},"assigneeUserId":"980695066010497026","createOrg":{"companyId":"958589807934590978","countStaff":0,"level":1,"orgCode":"c","orgName":"褡裢坡烟酒连锁"},"createTime":"2018-05-23 14:23:05","createUser":{"accId":"958589123373846529","accountEntity":{"accId":"958589123373846529","accType":0,"avatar":"bc3513f9fab74ac0aef2db4be9d0ce40.png","mobile":"13800138010","realName":"烟酒梁"},"companyAdmin":false,"superAdmin":false,"sysAdmin":false,"topCompanyId":"958589807934590978","userId":"958589807934590979"},"cusEntryTime":"2018-05-23 14:22:39","cusExitTime":"2018-05-23 14:22:41","empEntryTime":"2018-05-23 14:22:36","empExitTime":"2018-05-23 14:22:37","id":"999173851722813441","orderNumber":"OS1805231423875","ownerCompanyId":"958589807934590978","ownerOrgCode":"c","ownerTopCompanyId":"958589807934590978","ownerUserId":"958589807934590979","recYardEndTime":"2018-05-23 14:22:44","recYardStaTime":"2018-05-23 14:22:42","remarkInfo":"测试","status":0}]
     * pageSize : 10
     * totalCount : 1
     * totalPage : 1
     */

    private int currPage;
    private int pageSize;
    private int totalCount;
    private int totalPage;
    private List<OpenShopLogEntity> list;

    public int getCurrPage() {
        return currPage;
    }

    public void setCurrPage(int currPage) {
        this.currPage = currPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public List<OpenShopLogEntity> getList() {
        return list;
    }

    public void setList(List<OpenShopLogEntity> list) {
        this.list = list;
    }
}
