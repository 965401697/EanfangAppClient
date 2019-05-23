package com.eanfang.biz.model;

import java.io.Serializable;
import java.util.List;

/**
 * 描述：
 * 故障库实体类
 *
 * @author Guanluocang
 * @date on 2018/5/28$  13:37$
 */
public class FaultListBean implements Serializable {

    /**
     * currPage : 1
     * list : [{"businessOneCode":"1.1","createTime":"2018-05-24 16:21:23","createUserId":"994780876913541122","description":"故障描述故障描述故障描述故障描述","deviceName":"HNB监控","failureNo":"FL1805241621761","failureTypeId":"6.1","headDeviceId":"949163877705711618","id":"999566013136412674","level":1,"modelCode":"5.5.6","pictures":"1_1.jpg,1_2.jpg","proneBackground":"故障易发背景","remarkInfo":"备注","sketch":"故障简述","status":1,"title":"故障标题"}]
     * pageSize : 10
     * totalCount : 1
     * totalPage : 1
     */

    private int currPage;
    private int pageSize;
    private int totalCount;
    private int totalPage;
    private List<ListBean> list;

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

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * businessOneCode : 1.1
         * createTime : 2018-05-24 16:21:23
         * createUserId : 994780876913541122
         * description : 故障描述故障描述故障描述故障描述
         * deviceName : HNB监控
         * failureNo : FL1805241621761
         * failureTypeId : 6.1
         * headDeviceId : 949163877705711618
         * id : 999566013136412674
         * level : 1
         * modelCode : 5.5.6
         * pictures : 1_1.jpg,1_2.jpg
         * proneBackground : 故障易发背景
         * remarkInfo : 备注
         * sketch : 故障简述
         * status : 1
         * title : 故障标题
         */

        private String businessOneCode;
        private String createTime;
        private String createUserId;
        private String description;
        private String deviceName;
        private String failureNo;
        private String failureTypeId;
        private String headDeviceId;
        private String id;
        private int level;
        private String modelCode;
        private String pictures;
        private String proneBackground;
        private String remarkInfo;
        private String sketch;
        private int status;
        private String title;

        public String getBusinessOneCode() {
            return businessOneCode;
        }

        public void setBusinessOneCode(String businessOneCode) {
            this.businessOneCode = businessOneCode;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getCreateUserId() {
            return createUserId;
        }

        public void setCreateUserId(String createUserId) {
            this.createUserId = createUserId;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getDeviceName() {
            return deviceName;
        }

        public void setDeviceName(String deviceName) {
            this.deviceName = deviceName;
        }

        public String getFailureNo() {
            return failureNo;
        }

        public void setFailureNo(String failureNo) {
            this.failureNo = failureNo;
        }

        public String getFailureTypeId() {
            return failureTypeId;
        }

        public void setFailureTypeId(String failureTypeId) {
            this.failureTypeId = failureTypeId;
        }

        public String getHeadDeviceId() {
            return headDeviceId;
        }

        public void setHeadDeviceId(String headDeviceId) {
            this.headDeviceId = headDeviceId;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public String getModelCode() {
            return modelCode;
        }

        public void setModelCode(String modelCode) {
            this.modelCode = modelCode;
        }

        public String getPictures() {
            return pictures;
        }

        public void setPictures(String pictures) {
            this.pictures = pictures;
        }

        public String getProneBackground() {
            return proneBackground;
        }

        public void setProneBackground(String proneBackground) {
            this.proneBackground = proneBackground;
        }

        public String getRemarkInfo() {
            return remarkInfo;
        }

        public void setRemarkInfo(String remarkInfo) {
            this.remarkInfo = remarkInfo;
        }

        public String getSketch() {
            return sketch;
        }

        public void setSketch(String sketch) {
            this.sketch = sketch;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
