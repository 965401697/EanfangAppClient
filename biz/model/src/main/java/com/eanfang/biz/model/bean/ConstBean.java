package com.eanfang.biz.model.bean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConstBean implements Serializable {
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
}