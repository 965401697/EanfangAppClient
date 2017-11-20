package net.eanfang.client.ui.model;

import java.io.Serializable;

/**
 * Created by yaosheng on 2017/4/27.
 */

public class InstallOrderConfirmBean implements Serializable {

    /**
     * longitude : 经度
     * latitude : 纬度
     * city : 报装地址市
     * zone : 报装区
     * detailplace : 报装详细地址
     * clientconnector : 报装联系人姓名
     * clientphone : 报装联系人电话
     * arrivetime : 回复时限，文字描述
     * bugone : 业务类型bugone的uid
     * bugonename : 业务类型名称
     * predicttime : 预计工期，文字描述
     * budget : 预算金额，文字描述
     * description : 需求描述
     * workercompanyuid : 安防公司的uid
     */

    private String longitude;
    private String latitude;
    private String city;
    private String zone;
    private String detailplace;
    private String clientconnector;
    private String clientphone;
    private String arrivetime;
    private String bugone;
    private String bugonename;
    private String predicttime;
    private String budget;
    private String description;
    private String workercompanyuid;
    private String clientcompanyname;

    public String getClientcompanyname() {
        return clientcompanyname;
    }

    public void setClientcompanyname(String clientcompanyname) {
        this.clientcompanyname = clientcompanyname;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getDetailplace() {
        return detailplace;
    }

    public void setDetailplace(String detailplace) {
        this.detailplace = detailplace;
    }

    public String getClientconnector() {
        return clientconnector;
    }

    public void setClientconnector(String clientconnector) {
        this.clientconnector = clientconnector;
    }

    public String getClientphone() {
        return clientphone;
    }

    public void setClientphone(String clientphone) {
        this.clientphone = clientphone;
    }

    public String getArrivetime() {
        return arrivetime;
    }

    public void setArrivetime(String arrivetime) {
        this.arrivetime = arrivetime;
    }

    public String getBugone() {
        return bugone;
    }

    public void setBugone(String bugone) {
        this.bugone = bugone;
    }

    public String getBugonename() {
        return bugonename;
    }

    public void setBugonename(String bugonename) {
        this.bugonename = bugonename;
    }

    public String getPredicttime() {
        return predicttime;
    }

    public void setPredicttime(String predicttime) {
        this.predicttime = predicttime;
    }

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWorkercompanyuid() {
        return workercompanyuid;
    }

    public void setWorkercompanyuid(String workercompanyuid) {
        this.workercompanyuid = workercompanyuid;
    }
}
