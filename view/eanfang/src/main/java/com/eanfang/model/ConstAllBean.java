package com.eanfang.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class ConstAllBean implements Serializable {
    public ConstBean data;
    public String MD5;

    public ConstAllBean(ConstBean data, String MD5) {
        this.data = data;
        this.MD5 = MD5;
    }

    public ConstAllBean() {
    }

    public class ConstBean {
        private Map<String, List<String>> Const;
        private Map<String, List<String>> WorkReportConstant;
        private Map<String, List<String>> RepairConstant;
        private Map<String, List<String>> ShopConstant;
        private Map<String, List<String>> WorkInspectConstant;
        private Map<String, List<String>> DesignOrderConstant;
        private Map<String, List<String>> MainTainConstant;
        private Map<String, List<String>> DeviceConstant;
        private Map<String, List<String>> QuoteOrderConstant;
        private Map<String, List<String>> InstallOrderConstant;
        private Map<String, List<String>> TaskPublishConstant;
        private Map<String, List<String>> NoticeConst;
        private Map<String, List<String>> OAConst;
        private Map<String, List<String>> ExchangeLogConstant;

        public ConstBean(Map<String, List<String>> Const, Map<String, List<String>> WorkReportConstant, Map<String, List<String>> RepairConstant, Map<String, List<String>> ShopConstant, Map<String, List<String>> WorkInspectConstant, Map<String, List<String>> DesignOrderConstant, Map<String, List<String>> MainTainConstant, Map<String, List<String>> DeviceConstant, Map<String, List<String>> QuoteOrderConstant, Map<String, List<String>> InstallOrderConstant, Map<String, List<String>> TaskPublishConstant, Map<String, List<String>> NoticeConst, Map<String, List<String>> OAConst, Map<String, List<String>> ExchangeLogConstant) {
            this.Const = Const;
            this.WorkReportConstant = WorkReportConstant;
            this.RepairConstant = RepairConstant;
            this.ShopConstant = ShopConstant;
            this.WorkInspectConstant = WorkInspectConstant;
            this.DesignOrderConstant = DesignOrderConstant;
            this.MainTainConstant = MainTainConstant;
            this.DeviceConstant = DeviceConstant;
            this.QuoteOrderConstant = QuoteOrderConstant;
            this.InstallOrderConstant = InstallOrderConstant;
            this.TaskPublishConstant = TaskPublishConstant;
            this.NoticeConst = NoticeConst;
            this.OAConst = OAConst;
            this.ExchangeLogConstant = ExchangeLogConstant;
        }

        public ConstBean() {
        }

        public Map<String, List<String>> getConst() {
            return this.Const;
        }

        public Map<String, List<String>> getWorkReportConstant() {
            return this.WorkReportConstant;
        }

        public Map<String, List<String>> getRepairConstant() {
            return this.RepairConstant;
        }

        public Map<String, List<String>> getShopConstant() {
            return this.ShopConstant;
        }

        public Map<String, List<String>> getWorkInspectConstant() {
            return this.WorkInspectConstant;
        }

        public Map<String, List<String>> getDesignOrderConstant() {
            return this.DesignOrderConstant;
        }

        public Map<String, List<String>> getMainTainConstant() {
            return this.MainTainConstant;
        }

        public Map<String, List<String>> getDeviceConstant() {
            return this.DeviceConstant;
        }

        public Map<String, List<String>> getQuoteOrderConstant() {
            return this.QuoteOrderConstant;
        }

        public Map<String, List<String>> getInstallOrderConstant() {
            return this.InstallOrderConstant;
        }

        public Map<String, List<String>> getTaskPublishConstant() {
            return this.TaskPublishConstant;
        }

        public Map<String, List<String>> getNoticeConst() {
            return this.NoticeConst;
        }

        public Map<String, List<String>> getOAConst() {
            return this.OAConst;
        }

        public Map<String, List<String>> getExchangeLogConstant() {
            return this.ExchangeLogConstant;
        }

        public void setConst(Map<String, List<String>> Const) {
            this.Const = Const;
        }

        public void setWorkReportConstant(Map<String, List<String>> WorkReportConstant) {
            this.WorkReportConstant = WorkReportConstant;
        }

        public void setRepairConstant(Map<String, List<String>> RepairConstant) {
            this.RepairConstant = RepairConstant;
        }

        public void setShopConstant(Map<String, List<String>> ShopConstant) {
            this.ShopConstant = ShopConstant;
        }

        public void setWorkInspectConstant(Map<String, List<String>> WorkInspectConstant) {
            this.WorkInspectConstant = WorkInspectConstant;
        }

        public void setDesignOrderConstant(Map<String, List<String>> DesignOrderConstant) {
            this.DesignOrderConstant = DesignOrderConstant;
        }

        public void setMainTainConstant(Map<String, List<String>> MainTainConstant) {
            this.MainTainConstant = MainTainConstant;
        }

        public void setDeviceConstant(Map<String, List<String>> DeviceConstant) {
            this.DeviceConstant = DeviceConstant;
        }

        public void setQuoteOrderConstant(Map<String, List<String>> QuoteOrderConstant) {
            this.QuoteOrderConstant = QuoteOrderConstant;
        }

        public void setInstallOrderConstant(Map<String, List<String>> InstallOrderConstant) {
            this.InstallOrderConstant = InstallOrderConstant;
        }

        public void setTaskPublishConstant(Map<String, List<String>> TaskPublishConstant) {
            this.TaskPublishConstant = TaskPublishConstant;
        }

        public void setNoticeConst(Map<String, List<String>> NoticeConst) {
            this.NoticeConst = NoticeConst;
        }

        public void setOAConst(Map<String, List<String>> OAConst) {
            this.OAConst = OAConst;
        }

        public void setExchangeLogConstant(Map<String, List<String>> ExchangeLogConstant) {
            this.ExchangeLogConstant = ExchangeLogConstant;
        }
    }

    public ConstBean getData() {
        return data;
    }

    public void setData(ConstBean data) {
        this.data = data;
    }

    public String getMD5() {
        return MD5;
    }

    public void setMD5(String MD5) {
        this.MD5 = MD5;
    }

    @Override
    public String toString() {
        return "ConstAllBean{" +
                "data=" + data +
                ", MD5='" + MD5 + '\'' +
                '}';
    }
}

