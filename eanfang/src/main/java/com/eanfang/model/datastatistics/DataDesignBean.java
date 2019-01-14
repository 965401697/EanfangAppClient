package com.eanfang.model.datastatistics;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author guanluocang
 * @data 2018/12/14
 * @description 设计 统计
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DataDesignBean implements Serializable {

    private List<DesignBean> design;
    private List<BussinessBean> bussiness;
    private List<FiveBean> five;

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
