package com.eanfang.biz.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/23.
 */

public class CollectionCompanyListBean implements Serializable {

    private List<AllBean> all;

    public List<AllBean> getAll() {
        if (all == null) {
            return new ArrayList<>();
        }
        return all;
    }

    public void setAll(List<AllBean> all) {
        this.all = all;
    }

    public static class AllBean {
        /**
         * companyname : 北京腾讯公司
         * companyverify : 1
         * installamount : 0
         * logopic : http://eanfangx.oss-cn-beijing.aliyuncs.com/6db1861ae7424bff89c1e9499ee4ea70.png
         * repairamount : 0
         * goodpercent : 1.00
         * praise : 5.0
         */

        private String companyname;
        private String companyverify;
        private int installamount;
        private String logopic;
        private int repairamount;
        private String goodpercent;
        private String praise;

        public String getCompanyname() {
            return companyname == null ? "" : companyname;
        }

        public void setCompanyname(String companyname) {
            this.companyname = companyname;
        }

        public String getCompanyverify() {
            return companyverify == null ? "" : companyverify;
        }

        public void setCompanyverify(String companyverify) {
            this.companyverify = companyverify;
        }

        public int getInstallamount() {
            return installamount;
        }

        public void setInstallamount(int installamount) {
            this.installamount = installamount;
        }

        public String getLogopic() {
            return logopic == null ? "" : logopic;
        }

        public void setLogopic(String logopic) {
            this.logopic = logopic;
        }

        public int getRepairamount() {
            return repairamount;
        }

        public void setRepairamount(int repairamount) {
            this.repairamount = repairamount;
        }

        public String getGoodpercent() {
            return goodpercent == null ? "" : goodpercent;
        }

        public void setGoodpercent(String goodpercent) {
            this.goodpercent = goodpercent;
        }

        public String getPraise() {
            return praise == null ? "" : praise;
        }

        public void setPraise(String praise) {
            this.praise = praise;
        }
    }
}
