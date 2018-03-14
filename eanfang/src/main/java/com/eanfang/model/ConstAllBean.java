package com.eanfang.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by MrHou
 *
 * @on 2017/12/11  17:36
 * @email houzhongzhou@yeah.net
 * @desc
 */
@Setter
@Getter
public class ConstAllBean implements Serializable {

    private ConstBean data;

    private String MD5;

    @Getter
    @Setter
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
    }
}

