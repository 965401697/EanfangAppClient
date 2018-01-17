package net.eanfang.client.ui.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by MrHou
 *
 * @on 2017/12/11  17:36
 * @email houzhongzhou@yeah.net
 * @desc
 */

public class ConstAllBean implements Serializable {
    private Map<String, List<String>> Const;
    private Map<String, List<String>> WorkReportConstant;
    private Map<String, List<String>> RepairConstant;
    private Map<String, List<String>> ShopConstant;
    private Map<String, List<String>> DeviceConstan;
    private Map<String, List<String>> WorkInspectConstant;
    private Map<String, List<String>> DesignOrderConstant;
    private Map<String, List<String>> MainTainConstant;
    private Map<String, List<String>> DeviceConstant;
    private Map<String, List<String>> QuoteOrderConstant;

    public Map<String, List<String>> getQuoteOrderConstant() {
        return QuoteOrderConstant;
    }

    public void setQuoteOrderConstant(Map<String, List<String>> quoteOrderConstant) {
        QuoteOrderConstant = quoteOrderConstant;
    }

    public Map<String, List<String>> getDeviceConstant() {
        return DeviceConstant;
    }

    public void setDeviceConstant(Map<String, List<String>> deviceConstant) {
        DeviceConstant = deviceConstant;
    }

    public Map<String, List<String>> getMainTainConstant() {
        return MainTainConstant;
    }

    public void setMainTainConstant(Map<String, List<String>> mainTainConstant) {
        MainTainConstant = mainTainConstant;
    }

    public Map<String, List<String>> getDesignOrderConstant() {
        return DesignOrderConstant;
    }

    public void setDesignOrderConstant(Map<String, List<String>> designOrderConstant) {
        DesignOrderConstant = designOrderConstant;
    }

    public Map<String, List<String>> getWorkReportConstant() {
        return WorkReportConstant;
    }

    public void setWorkReportConstant(Map<String, List<String>> workReportConstant) {
        WorkReportConstant = workReportConstant;
    }

    public Map<String, List<String>> getRepairConstant() {
        return RepairConstant;
    }

    public void setRepairConstant(Map<String, List<String>> repairConstant) {
        RepairConstant = repairConstant;
    }

    public Map<String, List<String>> getShopConstant() {
        return ShopConstant;
    }

    public void setShopConstant(Map<String, List<String>> shopConstant) {
        ShopConstant = shopConstant;
    }

    public Map<String, List<String>> getDeviceConstan() {
        return DeviceConstan;
    }

    public void setDeviceConstan(Map<String, List<String>> deviceConstan) {
        DeviceConstan = deviceConstan;
    }

    public Map<String, List<String>> getWorkInspectConstant() {
        return WorkInspectConstant;
    }

    public void setWorkInspectConstant(Map<String, List<String>> workInspectConstant) {
        WorkInspectConstant = workInspectConstant;
    }

    public Map<String, List<String>> getConst() {
        return Const;
    }

    public void setConst(Map<String, List<String>> aConst) {
        Const = aConst;
    }
}

