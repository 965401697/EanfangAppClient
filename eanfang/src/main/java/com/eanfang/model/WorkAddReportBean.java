package com.eanfang.model;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mr.hou
 *
 * @on 2017/9/4  10:09
 * @email houzhongzhou@yeah.net
 * @desc
 */

public class WorkAddReportBean implements Serializable {

    /**
     * type : 0
     * firstLookTime : 2000-11-03
     * assigneeUserId : 1L
     * assigneeOrgCode : c.c1.2
     * workReportDetails : [{"type":0,"field1":"1112L","field2":"2233L","field3":"2233L","field4":"2233L","field5":"2233L","pictures":"2233L"},{"type":0,"field1":"2223","field2":"4455","field3":"2233L","field4":"2233L","field5":"2233L","pictures":"2233L"}]
     */

    private int type;
    private String firstLookTime;
    private Long assigneeUserId;
    private String assigneeOrgCode;
    private List<WorkReportDetailsBean> workReportDetails;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getFirstLookTime() {
        return firstLookTime == null ? "" : firstLookTime;
    }

    public void setFirstLookTime(String firstLookTime) {
        this.firstLookTime = firstLookTime;
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

    public List<WorkReportDetailsBean> getWorkReportDetails() {
        if (workReportDetails == null) {
            return new ArrayList<>();
        }
        return workReportDetails;
    }

    public void setWorkReportDetails(List<WorkReportDetailsBean> workReportDetails) {
        this.workReportDetails = workReportDetails;
    }

    public static class WorkReportDetailsBean implements Serializable, MultiItemEntity {
        //多布局type
        public static final int FOLD = 1;//折叠
        public static final int EXPAND = 2; //展开

        /**
         * type : 0
         * field1 : 1112L
         * field2 : 2233L
         * field3 : 2233L
         * field4 : 2233L
         * field5 : 2233L
         * pictures : 2233L
         */

        private int type;
        private String field1;
        private String field2;
        private String field3;
        private String field4;
        private String field5;
        private String pictures;
        private String mp4_path;

        private int itemType;

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getField1() {
            return field1 == null ? "" : field1;
        }

        public void setField1(String field1) {
            this.field1 = field1;
        }

        public String getField2() {
            return field2 == null ? "" : field2;
        }

        public void setField2(String field2) {
            this.field2 = field2;
        }

        public String getField3() {
            return field3 == null ? "" : field3;
        }

        public void setField3(String field3) {
            this.field3 = field3;
        }

        public String getField4() {
            return field4 == null ? "" : field4;
        }

        public void setField4(String field4) {
            this.field4 = field4;
        }

        public String getField5() {
            return field5 == null ? "" : field5;
        }

        public void setField5(String field5) {
            this.field5 = field5;
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

        public void setItemType(int itemType) {
            this.itemType = itemType;
        }

        @Override
        public int getItemType() {
            return itemType;
        }
    }
}


