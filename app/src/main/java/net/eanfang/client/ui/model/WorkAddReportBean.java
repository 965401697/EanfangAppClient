package net.eanfang.client.ui.model;

import java.io.Serializable;
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
        return firstLookTime;
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
        return assigneeOrgCode;
    }

    public void setAssigneeOrgCode(String assigneeOrgCode) {
        this.assigneeOrgCode = assigneeOrgCode;
    }

    public List<WorkReportDetailsBean> getWorkReportDetails() {
        return workReportDetails;
    }

    public void setWorkReportDetails(List<WorkReportDetailsBean> workReportDetails) {
        this.workReportDetails = workReportDetails;
    }

    public static class WorkReportDetailsBean implements Serializable {
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

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getField1() {
            return field1;
        }

        public void setField1(String field1) {
            this.field1 = field1;
        }

        public String getField2() {
            return field2;
        }

        public void setField2(String field2) {
            this.field2 = field2;
        }

        public String getField3() {
            return field3;
        }

        public void setField3(String field3) {
            this.field3 = field3;
        }

        public String getField4() {
            return field4;
        }

        public void setField4(String field4) {
            this.field4 = field4;
        }

        public String getField5() {
            return field5;
        }

        public void setField5(String field5) {
            this.field5 = field5;
        }

        public String getPictures() {
            return pictures;
        }

        public void setPictures(String pictures) {
            this.pictures = pictures;
        }
    }
}


