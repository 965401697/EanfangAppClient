package net.eanfang.client.ui.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yaosheng on 2017/5/22.
 */

public class PayOrderListBean implements Serializable {

    private List<AllBean> all;

    public List<AllBean> getAll() {
        return all;
    }

    public void setAll(List<AllBean> all) {
        this.all = all;
    }

    public static class AllBean {
        /**
         * confirm : 0
         * createtime : 2017-03-29 22:51:32
         * itemname : 报价1
         * selfordernum : 32020170329223343951356
         * sum : 70
         * workercompanyname : 全能快修
         * workername : 小兰
         * workerphone : 15038147687
         */

        private String confirm;
        private String createtime;
        private String itemname;
        private String selfordernum;
        private double sum;
        private String workercompanyname;
        private String workername;
        private String workerphone;

        //业务图片
        private String pic;

        private String clientcompanyname;
        private String clientname;
        private String clientphone;
        private String clientcompanynamewr;

        private double quotefee;

        public String getConfirm() {
            return confirm;
        }

        public void setConfirm(String confirm) {
            this.confirm = confirm;
        }

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public String getItemname() {
            return itemname;
        }

        public void setItemname(String itemname) {
            this.itemname = itemname;
        }

        public String getSelfordernum() {
            return selfordernum;
        }

        public void setSelfordernum(String selfordernum) {
            this.selfordernum = selfordernum;
        }

        public double getSum() {
            return sum;
        }

        public void setSum(double sum) {
            this.sum = sum;
        }

        public String getWorkercompanyname() {
            return workercompanyname;
        }

        public void setWorkercompanyname(String workercompanyname) {
            this.workercompanyname = workercompanyname;
        }

        public String getWorkername() {
            return workername;
        }

        public void setWorkername(String workername) {
            this.workername = workername;
        }

        public String getWorkerphone() {
            return workerphone;
        }

        public void setWorkerphone(String workerphone) {
            this.workerphone = workerphone;
        }

        public String getClientcompanyname() {
            return clientcompanyname;
        }

        public void setClientcompanyname(String clientcompanyname) {
            this.clientcompanyname = clientcompanyname;
        }

        public String getClientname() {
            return clientname;
        }

        public void setClientname(String clientname) {
            this.clientname = clientname;
        }

        public String getClientphone() {
            return clientphone;
        }

        public void setClientphone(String clientphone) {
            this.clientphone = clientphone;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public double getQuotefee() {
            return quotefee;
        }

        public void setQuotefee(double quotefee) {
            this.quotefee = quotefee;
        }

        public String getClientcompanynamewr() {
            return clientcompanynamewr;
        }

        public void setClientcompanynamewr(String clientcompanynamewr) {
            this.clientcompanynamewr = clientcompanynamewr;
        }
    }
}
