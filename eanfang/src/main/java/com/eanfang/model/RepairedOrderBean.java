package com.eanfang.model;

import com.yaf.base.entity.RepairOrderEntity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wen on 2017/5/11.
 */

public class RepairedOrderBean implements Serializable {


    /**
     * currPage : 1
     * list : [{"address":"定福家园南里2号院2号楼","arriveTimeLimit":10,"assigneeOrg":{"belongCompany":{"companyId":2110,"level":3,"orgCode":"c.c1.c1","orgId":2110,"orgName":"家乐福双井公司","topCompanyId":2000},"belongTopCompany":{"companyId":2000,"level":1,"orgCode":"c","orgId":2000,"orgName":"家乐福总公司","topCompanyId":2000},"companyId":2110,"level":4,"orgCode":"c.c1.c1.1","orgId":2111,"orgName":"防损部","topCompanyId":2000},"assigneeOrgCode":"c.c1.c1.1","assigneeTopCompanyId":2000,"assigneeUser":{"accId":"936487014348365825","accountEntity":{"accId":"936487014348365825","accType":0,"avatar":"0","realName":"张三"},"companyAdmin":false,"superAdmin":false,"sysAdmin":false,"userId":"936487014465806338"},"assigneeUserId":"936487014465806338","bookTime":"2017-12-11 15:09","createTime":"2017-12-07 16:56","createUserId":1,"id":"938693640002256897","isPhoneSolve":0,"latitude":"39.928955","longitude":"116.575165","orderLevel":0,"orderNum":"MO1712071656231","orderType":1,"ownerOrgCode":"c.c1.2","ownerTopCompanyId":1100,"ownerUser":{"accId":1,"accountEntity":{"accId":1,"accType":0,"avatar":"avatar/30766a1474a447668771c985301e0ab3.jpg","realName":"锅子"},"companyAdmin":false,"superAdmin":false,"sysAdmin":false,"userId":1},"ownerUserId":1,"placeCode":"3.11.1.5","repairContactPhone":"15940525612","repairContacts":"lixu","repairWay":0,"replyTime":"2017-12-11 14:11","status":2},{"address":"朝阳北路褡裢坡地铁站烟酒超市","arriveTimeLimit":6,"assigneeTopCompanyId":2111,"assigneeUser":{"$ref":"$.data.list[0].assigneeUser"},"assigneeUserId":"936487014465806338","createTime":"2017-12-12 10:28","createUserId":1,"id":"940407881491148802","latitude":"39.929287","longitude":"116.576013","orderLevel":1,"orderNum":"MO1712121028320","orderType":1,"ownerOrgCode":"c.c1.2","ownerTopCompanyId":1100,"ownerUser":{"$ref":"$.data.list[0].ownerUser"},"ownerUserId":1,"placeCode":"3.11.1.5","repairContactPhone":"13800138000","repairContacts":"zhangsan","repairWay":0,"status":1,"workerEvaluateId":12},{"address":"朝阳北路青年路大悦城3层超市","arriveTimeLimit":3,"assigneeOrg":{"belongCompany":{"companyId":2120,"level":3,"orgCode":"c.c1.c2","orgId":2120,"orgName":"家乐福三元桥公司","topCompanyId":2000},"belongTopCompany":{"$ref":"$.data.list[0].assigneeOrg.belongTopCompany"},"companyId":2120,"level":4,"orgCode":"c.c1.c2.1","orgId":2121,"orgName":"防损部","topCompanyId":2000},"assigneeOrgCode":"c.c1.c2.1","assigneeTopCompanyId":2000,"assigneeUser":{"$ref":"$.data.list[0].assigneeUser"},"assigneeUserId":"936487014465806338","createTime":"2018-01-03 14:20","createUserId":1,"id":"948439010345230338","latitude":"39.929287","longitude":"116.576013","orderLevel":1,"orderNum":"MO1801031420237","orderType":1,"ownerOrg":{"belongCompany":{"companyId":1100,"level":2,"orgCode":"c.c1","orgId":1100,"orgName":"易安防北京运营公司","topCompanyId":1000},"belongTopCompany":{"companyId":1000,"level":1,"orgCode":"c","orgId":1000,"orgName":"易安防总平台","topCompanyId":1000},"companyId":1100,"level":3,"orgCode":"c.c1.2","orgId":1102,"orgName":"财务部","topCompanyId":1000},"ownerOrgCode":"c.c1.2","ownerTopCompanyId":1000,"ownerUser":{"$ref":"$.data.list[0].ownerUser"},"ownerUserId":1,"placeCode":"3.11.1.5","repairContactPhone":"13800138000","repairContacts":"zhangsan","repairWay":0,"status":1}]
     * pageSize : 10
     * totalCount : 3
     * totalPage : 1
     */

    private int currPage;
    private int pageSize;
    private int totalCount;
    private int totalPage;
    private List<RepairOrderEntity> list;

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

    public List<RepairOrderEntity> getList() {
        return list;
    }

    public void setList(List<RepairOrderEntity> list) {
        this.list = list;
    }

}

