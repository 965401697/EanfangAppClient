package com.eanfang.model;

import com.eanfang.model.sys.UserEntity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by MrHou
 *
 * @on 2018/1/23  20:30
 * @email houzhongzhou@yeah.net
 * @desc
 */

public class OuterListBean implements Serializable {


    /**
     * currPage : 1
     * list : [{"accId":"955365992588922882","accountEntity":{"accId":"955365992588922882","accType":0,"avatar":"0","email":"13099029332@qq.com","mobile":"13099029332","nickName":"张研","realName":"张研","status":0},"companyAdmin":false,"companyEntity":{"companyId":1100,"isVerify":1,"level":2,"orgCode":"c.c1","orgId":1100,"orgName":"易安防北京运营公司","orgType":1,"parentOrgId":0,"sortNum":0,"topCompanyId":1000,"updateTime":"2017-12-05 13:34","updateUser":1},"companyId":1100,"createTime":"2018-01-23 18:40","createUser":1,"departmentEntity":{"companyId":1100,"isVerify":0,"level":4,"orgCode":"c.c1.6.1","orgId":"955749652261605378","orgName":"宇宙起重机公司","orgType":3,"parentOrgId":"955749651158503426","sortNum":0,"topCompanyId":1000,"updateTime":"2018-01-23 18:30","updateUser":1},"departmentId":"955749652261605378","status":4,"superAdmin":false,"sysAdmin":false,"topCompanyId":1000,"updateUser":1,"userId":"955752033577598978","userType":10}]
     * pageSize : 2147483647
     * totalCount : 1
     * totalPage : 1
     */

    private int currPage;
    private int pageSize;
    private int totalCount;
    private int totalPage;
    private List<UserEntity> list;

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

    public List<UserEntity> getList() {
        if (list == null) {
            return new ArrayList<>();
        }
        return list;
    }

    public void setList(List<UserEntity> list) {
        this.list = list;
    }
}

