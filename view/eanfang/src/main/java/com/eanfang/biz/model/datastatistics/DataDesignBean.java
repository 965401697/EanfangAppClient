package com.eanfang.biz.model.datastatistics;

import java.io.Serializable;
import java.util.List;

/**
 * @author guanluocang
 * @data 2018/12/14
 * @description 设计 统计
 */

public class DataDesignBean implements Serializable {

    private List<DesignBean> design;
    private List<BussinessBean> bussiness;
    private List<FiveBean> five;

    public DataDesignBean(List<DesignBean> design, List<BussinessBean> bussiness, List<FiveBean> five) {
        this.design = design;
        this.bussiness = bussiness;
        this.five = five;
    }

    public DataDesignBean() {
    }

    public List<DesignBean> getDesign() {
        return this.design;
    }

    public List<BussinessBean> getBussiness() {
        return this.bussiness;
    }

    public List<FiveBean> getFive() {
        return this.five;
    }

    public void setDesign(List<DesignBean> design) {
        this.design = design;
    }

    public void setBussiness(List<BussinessBean> bussiness) {
        this.bussiness = bussiness;
    }

    public void setFive(List<FiveBean> five) {
        this.five = five;
    }

    public static class DesignBean {
        /**
         * count : 1
         * status : 0
         */

        private int num;
        private int status;

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }

    public static class FiveBean {
        /**
         * count : 114
         * repairCompany : i
         * repairCompanyId : 0
         */

        private int num;
        private String userName;

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }
    }

    public static class BussinessBean {
        private int newOrder;
        private int num;
        private String typeStr;

        public int getNewOrder() {
            return newOrder;
        }

        public void setNewOrder(int newOrder) {
            this.newOrder = newOrder;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public String getTypeStr() {
            return typeStr;
        }

        public void setTypeStr(String typeStr) {
            this.typeStr = typeStr;
        }
    }
}
