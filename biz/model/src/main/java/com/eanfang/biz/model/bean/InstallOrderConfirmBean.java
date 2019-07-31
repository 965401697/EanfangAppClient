package com.eanfang.biz.model.bean;

import java.io.Serializable;

/**
 * Created by yaosheng on 2017/4/27.
 */

public class InstallOrderConfirmBean implements Serializable {


    /**
     * clientCompanyName : 安防公司
     * longitude : 3243
     * latitude : 12313
     * zone : 3.11.1.5
     * detailPlace : 褡裢坡地铁站
     * connector : 张天赐
     * connectorPhone : 154823364853
     * revertTimeLimit : 2
     * businessOneCode : 1.1
     * predictTime : 1
     * budget : 1
     * description : 需要安装10个防盗报警
     */

    private String clientCompanyName;
    private String longitude;
    private String latitude;
    private String zone;
    private String detailPlace;
    private String connector;
    private String connectorPhone;
    private int revertTimeLimit;
    private String businessOneCode;
    private int predictTime;
    private int budget;
    private String description;
    private Long businessOneId;
    private Long zoneId;

    public String getClientCompanyName() {
        return clientCompanyName == null ? "" : clientCompanyName;
    }

    public void setClientCompanyName(String clientCompanyName) {
        this.clientCompanyName = clientCompanyName;
    }

    public String getLongitude() {
        return longitude == null ? "" : longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude == null ? "" : latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getZone() {
        return zone == null ? "" : zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getDetailPlace() {
        return detailPlace == null ? "" : detailPlace;
    }

    public void setDetailPlace(String detailPlace) {
        this.detailPlace = detailPlace;
    }

    public String getConnector() {
        return connector == null ? "" : connector;
    }

    public void setConnector(String connector) {
        this.connector = connector;
    }

    public String getConnectorPhone() {
        return connectorPhone == null ? "" : connectorPhone;
    }

    public void setConnectorPhone(String connectorPhone) {
        this.connectorPhone = connectorPhone;
    }

    public int getRevertTimeLimit() {
        return revertTimeLimit;
    }

    public void setRevertTimeLimit(int revertTimeLimit) {
        this.revertTimeLimit = revertTimeLimit;
    }

    public String getBusinessOneCode() {
        return businessOneCode == null ? "" : businessOneCode;
    }

    public void setBusinessOneCode(String businessOneCode) {
        this.businessOneCode = businessOneCode;
    }

    public int getPredictTime() {
        return predictTime;
    }

    public void setPredictTime(int predictTime) {
        this.predictTime = predictTime;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public String getDescription() {
        return description == null ? "" : description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getBusinessOneId() {
        return businessOneId;
    }

    public void setBusinessOneId(Long businessOneId) {
        this.businessOneId = businessOneId;
    }

    public Long getZoneId() {
        return zoneId;
    }

    public void setZoneId(Long zoneId) {
        this.zoneId = zoneId;
    }
}


