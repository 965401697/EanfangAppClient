package com.eanfang.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ConstAllBean implements Serializable {
    public ConstBean data;
    public String MD5;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
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

