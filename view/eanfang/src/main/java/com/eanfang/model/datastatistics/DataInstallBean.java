package com.eanfang.model.datastatistics;

import java.io.Serializable;
import java.util.List;

/**
 * @author guanluocang
 * @data 2018/9/19
 * @description 保修统计
 */

public class DataInstallBean implements Serializable {


    private List<InstallBean> install;
    private List<BussinessBean> bussiness;
    private List<FiveBean> five;

    public DataInstallBean(List<InstallBean> install, List<BussinessBean> bussiness, List<FiveBean> five) {
        this.install = install;
        this.bussiness = bussiness;
        this.five = five;
    }

    public DataInstallBean() {
    }

    public List<InstallBean> getInstall() {
        return install;
    }

    public void setInstall(List<InstallBean> repair) {
        this.install = repair;
    }

    public List<BussinessBean> getBussiness() {
        return bussiness;
    }

    public void setBussiness(List<BussinessBean> bussiness) {
        this.bussiness = bussiness;
    }

    public List<FiveBean> getFive() {
        return five;
    }

    public void setFive(List<FiveBean> five) {
        this.five = five;
    }

    public static class InstallBean {
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

    public static class BussinessBean {
        /**
         * count : 2
         * typeStr : 电视监控
         */

        private int num;
        private String typeStr;

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

    public static class FiveBean {
        /**
         * count : 114
         * repairCompany : i
         * repairCompanyId : 0
         */

        private int num;
        private String clientCompanyName;
        private String ownerCompanyId;

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public String getClientCompanyName() {
            return clientCompanyName;
        }

        public void setClientCompanyName(String clientCompanyName) {
            this.clientCompanyName = clientCompanyName;
        }

        public String getOwnerCompanyId() {
            return ownerCompanyId;
        }

        public void setOwnerCompanyId(String ownerCompanyId) {
            this.ownerCompanyId = ownerCompanyId;
        }
    }
}
