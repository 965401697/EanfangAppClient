package com.eanfang.model.device;

import java.util.List;

/**
 * Created by jornl on 2017/9/27.
 */

public class GetDeviceFailureOptionBean {


    private List<BeanBean> bean;

    public List<BeanBean> getBean() {
        return bean;
    }

    public void setBean(List<BeanBean> bean) {
        this.bean = bean;
    }

    public static class BeanBean {

        private String uid;
        private String deviceUid;
        private String title;
        private String failureNo;
        private String description;
        private String pic1;
        private String pic2;
        private String pic3;
        private String remark;


        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getDeviceUid() {
            return deviceUid;
        }

        public void setDeviceUid(String deviceUid) {
            this.deviceUid = deviceUid;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getFailureNo() {
            return failureNo;
        }

        public void setFailureNo(String failureNo) {
            this.failureNo = failureNo;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getPic1() {
            return pic1;
        }

        public void setPic1(String pic1) {
            this.pic1 = pic1;
        }

        public String getPic2() {
            return pic2;
        }

        public void setPic2(String pic2) {
            this.pic2 = pic2;
        }

        public String getPic3() {
            return pic3;
        }

        public void setPic3(String pic3) {
            this.pic3 = pic3;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }
    }
}
