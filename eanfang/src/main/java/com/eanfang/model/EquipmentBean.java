package com.eanfang.model;

import com.yaf.base.entity.CustDeviceEntity;

import java.util.List;

/**
 * Created by O u r on 2018/6/22.
 */

public class EquipmentBean {

    /**
     * currPage : 1
     * list : [{"assigneeCompanyId":3688,"assigneeUserId":2878,"businessThreeCode":"1.1","buyTime":"2017-06-08 00:00:00","chargeOrg":"985770118343135234","chargeUser":"985770118343135235","createTime":"2018-06-06 16:07:30","createUserId":"958615131577647105","deviceInfo":"设备信息备注","deviceName":"枪机设备2","deviceNo":"DS-8859-1","devicePrice":299,"deviceStatusInfo":"设备状况备注信息","deviceVersion":8,"editTime":"2018-06-22 14:17:58","editUserId":"985770118343135235","headDeviceId":1,"id":"1004273562653917186","installDate":"2017-11-03 00:00:00","location":"货架上2","locationNumber":"4564564","locationPictures":"现场位置图","modelCode":"1.1","ownerCompanyId":"985770118343135234","ownerOrgCode":"28728","ownerTopCompanyId":"985770118343135234","ownerUserId":868,"picture":"等等2","producerName":"枪机厂2","producerPlace":"新加坡2","purchaseOrg":"65456a","purchaser":65465456,"repairCompany":"维修公司","repairUser":"维修人","sellUnit":"销售单位","serialNumber":"XLH6666666666","status":1,"unit":0,"warrantyPeriod":"2年","warrantyStatus":0}]
     * pageSize : 10
     * totalCount : 1
     * totalPage : 1
     */

    private int currPage;
    private int pageSize;
    private int totalCount;
    private int totalPage;
    private List<CustDeviceEntity> list;

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

    public List<CustDeviceEntity> getList() {
        return list;
    }

    public void setList(List<CustDeviceEntity> list) {
        this.list = list;
    }


}
