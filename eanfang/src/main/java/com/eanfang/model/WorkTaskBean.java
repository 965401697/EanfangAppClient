package com.eanfang.model;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mr.hou
 *
 * @on 2017/8/29  14:26
 * @email houzhongzhou@yeah.net
 * @desc 工作任务
 */

public class WorkTaskBean implements Serializable {

    /**
     * title : 修改标题
     * firstLookTime : null
     * assigneeUserId : 1
     * assigneeOrgCode : c_c1
     */

    private String title;
    private String firstLookTime;
    private Long assigneeUserId;
    private String assigneeOrgCode;
    private List<WorkTaskDetailsBean> workTaskDetails;

    public String getTitle() {
        return title == null ? "" : title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public List<WorkTaskDetailsBean> getWorkTaskDetails() {
        if (workTaskDetails == null) {
            return new ArrayList<>();
        }
        return workTaskDetails;
    }

    public void setWorkTaskDetails(List<WorkTaskDetailsBean> workTaskDetails) {
        this.workTaskDetails = workTaskDetails;
    }

    public static class WorkTaskDetailsBean implements Serializable, MultiItemEntity {
        /**
         * title : 明细标题
         * instancyLevel : 0
         * first_look : 1
         * first_callback : 1
         * then_callback : 1
         * end_time : null
         * info : 明细内容
         * purpose : 有什么目的
         * criterion : 什么标准
         * joinPerson : 张三和李四
         * pictures : 照片地址
         */

        //多布局type
        public static final int FOLD = 1;//折叠
        public static final int EXPAND = 2; //展开

        private String title;
        private int instancyLevel;
        private int first_look;
        private int first_callback;
        private int then_callback;
        private String end_time;
        private String info;
        private String purpose;
        private String criterion;
        private String joinPerson;
        private String pictures;
        private String mp4_path;

        private int itemType;

        public String getTitle() {
            return title == null ? "" : title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getInstancyLevel() {
            return instancyLevel;
        }

        public void setInstancyLevel(int instancyLevel) {
            this.instancyLevel = instancyLevel;
        }

        public int getFirst_look() {
            return first_look;
        }

        public void setFirst_look(int first_look) {
            this.first_look = first_look;
        }

        public int getFirst_callback() {
            return first_callback;
        }

        public void setFirst_callback(int first_callback) {
            this.first_callback = first_callback;
        }

        public int getThen_callback() {
            return then_callback;
        }

        public void setThen_callback(int then_callback) {
            this.then_callback = then_callback;
        }

        public String getEnd_time() {
            return end_time == null ? "" : end_time;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }

        public String getInfo() {
            return info == null ? "" : info;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public String getPurpose() {
            return purpose == null ? "" : purpose;
        }

        public void setPurpose(String purpose) {
            this.purpose = purpose;
        }

        public String getCriterion() {
            return criterion == null ? "" : criterion;
        }

        public void setCriterion(String criterion) {
            this.criterion = criterion;
        }

        public String getJoinPerson() {
            return joinPerson == null ? "" : joinPerson;
        }

        public void setJoinPerson(String joinPerson) {
            this.joinPerson = joinPerson;
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

