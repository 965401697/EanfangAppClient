package com.eanfang.biz.model.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mr.hou
 *
 * @on 2017/9/6  14:07
 * @email houzhongzhou@yeah.net
 * @desc
 */

public class WorkAddCheckBean implements Serializable {


    /**
     * companyName : 北京法案视
     * title : 检查设备
     * changeDeadlineTime : 2020-11-03
     * changeInfo : 设备移位
     * assigneeUserId : 1
     * assigneeOrgCode : c.c1.2
     * workInspectDetails : [{"title":"整改设备摆放","region":"超市门口","businessThreeCode":"1.11.13","info":"检查设备是否摆放成功","pictures":"这里有多张图片地址"},{"title":"设备是否运行","region":"厂库内","businessThreeCode":"1.11.13","info":"检查设备是否运行正常","pictures":"这里有多张图片地址"}]
     */

    private String companyName;
    private String title;
    private String changeDeadlineTime;
    private String changeInfo;
    private Long assigneeUserId;
    private String assigneeOrgCode;
    private List<WorkInspectDetailsBean> workInspectDetails;

    public String getCompanyName() {
        return companyName == null ? "" : companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getTitle() {
        return title == null ? "" : title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getChangeDeadlineTime() {
        return changeDeadlineTime == null ? "" : changeDeadlineTime;
    }

    public void setChangeDeadlineTime(String changeDeadlineTime) {
        this.changeDeadlineTime = changeDeadlineTime;
    }

    public String getChangeInfo() {
        return changeInfo == null ? "" : changeInfo;
    }

    public void setChangeInfo(String changeInfo) {
        this.changeInfo = changeInfo;
    }

    public Long getAssigneeUserId() {
        return assigneeUserId;
    }

    public void setAssigneeUserId(Long assigneeUserId) {
        this.assigneeUserId = assigneeUserId;
    }

    public String getAssigneeOrgCode() {
        return assigneeOrgCode == null ? "" : assigneeOrgCode;
    }

    public void setAssigneeOrgCode(String assigneeOrgCode) {
        this.assigneeOrgCode = assigneeOrgCode;
    }

    public List<WorkInspectDetailsBean> getWorkInspectDetails() {
        if (workInspectDetails == null) {
            return new ArrayList<>();
        }
        return workInspectDetails;
    }

    public void setWorkInspectDetails(List<WorkInspectDetailsBean> workInspectDetails) {
        this.workInspectDetails = workInspectDetails;
    }

    public static class WorkInspectDetailsBean implements Serializable, MultiItemEntity {
        //多布局type
        public static final int FOLD = 1;//折叠
        public static final int EXPAND = 2; //展开
        /**
         * title : 整改设备摆放
         * region : 超市门口
         * businessThreeCode : 1.11.13
         * info : 检查设备是否摆放成功
         * pictures : 这里有多张图片地址
         */

        private String title;
        private String region;
        private String businessThreeCode;
        private String info;
        private String pictures;
        private String mp4_path;
        private int itemType;


        public String getTitle() {
            return title == null ? "" : title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getRegion() {
            return region == null ? "" : region;
        }

        public void setRegion(String region) {
            this.region = region;
        }

        public String getBusinessThreeCode() {
            return businessThreeCode == null ? "" : businessThreeCode;
        }

        public void setBusinessThreeCode(String businessThreeCode) {
            this.businessThreeCode = businessThreeCode;
        }

        public String getInfo() {
            return info == null ? "" : info;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public String getPictures() {
            return pictures == null ? "" : pictures;
        }

        public void setPictures(String pictures) {
            this.pictures = pictures;
        }

        public String getMp4_path() {
            return mp4_path;
        }

        public void setMp4_path(String mp4_path) {
            this.mp4_path = mp4_path;
        }

        public int getItemType() {
            return itemType;
        }

        public void setItemType(int itemType) {
            this.itemType = itemType;
        }
    }
}
