package com.eanfang.model.device;

import java.util.List;

/**
 * Created by jornl on 2017/9/27.
 */

public class GetDeviceOptionBean {


    private List<BeanBean> bean;

    public List<BeanBean> getBean() {
        return bean;
    }

    public void setBean(List<BeanBean> bean) {
        this.bean = bean;
    }

    public static class BeanBean {
        /**
         * brand : AB
         * businessOne : 电视监控
         * businessThree : 模拟枪型摄像机
         * businessTwo : 摄像采集
         * deviceName : 01模拟枪机
         * deviceNo : df-13564754748
         * location : 消防门顶部
         * model : AB-1573
         * uid : 1bb56ac4e32b4cd0b24ac7dbe5008312
         */

        private String brand;
        private String businessOne;
        private String businessThree;
        private String businessTwo;
        private String customerDeviceName;
        private String deviceUid;
        private String customerDeviceNo;
        private String location;
        private String model;
        private String uid;

        public String getBrand() {
            return brand;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }

        public String getBusinessOne() {
            return businessOne;
        }

        public void setBusinessOne(String businessOne) {
            this.businessOne = businessOne;
        }

        public String getBusinessThree() {
            return businessThree;
        }

        public void setBusinessThree(String businessThree) {
            this.businessThree = businessThree;
        }

        public String getBusinessTwo() {
            return businessTwo;
        }

        public void setBusinessTwo(String businessTwo) {
            this.businessTwo = businessTwo;
        }


        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }


        public String getCustomerDeviceName() {
            return customerDeviceName;
        }

        public void setCustomerDeviceName(String customerDeviceName) {
            this.customerDeviceName = customerDeviceName;
        }

        public String getDeviceUid() {
            return deviceUid;
        }

        public void setDeviceUid(String deviceUid) {
            this.deviceUid = deviceUid;
        }

        public String getCustomerDeviceNo() {
            return customerDeviceNo;
        }

        public void setCustomerDeviceNo(String customerDeviceNo) {
            this.customerDeviceNo = customerDeviceNo;
        }
    }
}
