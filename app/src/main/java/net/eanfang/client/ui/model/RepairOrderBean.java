package net.eanfang.client.ui.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/4/19.
 */

public class RepairOrderBean implements Serializable {

    /**
     * account : 报修人账号手机号
     * clientcompanyuid : 报修人所在公司的uid
     * companyname : 单位名称
     * city : 城市
     * zone : 地区
     * detailplace : 报修详细地址
     * longitude : 经度
     * latitude : 纬度
     * clientconnector : 报修联系人姓名
     * clientphone : 报修联系人电话
     * arrivetime : 到达时限的文字描述
     * bugone : 业务大类，bugone的uid
     * bugonename : 业务大类，bugone的name
     * workeruid : 技师的uid
     * bugdetails : [{"bugtwo":"业务小类，bugtwo uid","bugtwoname":"bugtwo name","bugthree":"设备名称 uid","bugthreename":"设备名称 name","bugfour":"品牌型号 uid","bugfourname":"品牌型号 name","bugposition":"故障位置","equipnum":"设备编码","bugpic1":"故障图url","bugpic2":"故障图url","bugpic3":"故障图url","bugdesc":"故障描述"},{"bugtwo":"业务小类，bugtwo uid","bugtwoname":"bugtwo name","bugthree":"设备名称 uid","bugthreename":"设备名称 name","bugfour":"品牌型号 uid","bugfourname":"品牌型号 name","bugposition":"故障位置","equipnum":"设备编码","bugpic1":"故障图url","bugpic2":"故障图url","bugpic3":"故障图url","bugdesc":"故障描述"}]
     */

    private String account;
    private String clientcompanyuid;
    private String companyname;
    private String province;
    private String city;
    private String zone;
    private String detailplace;
    private String longitude;
    private String latitude;
    private String clientconnector;
    private String clientphone;
    private String arrivetime;
    private String bugone;
    private String bugonename;
    private String workeruid;
//    /**
//     * 选择技师的安防公司的uid
//     */
//    private String workerCompanyUid;

    private List<AddTroubleBean> bugdetails;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getClientcompanyuid() {
        return clientcompanyuid;
    }

    public void setClientcompanyuid(String clientcompanyuid) {
        this.clientcompanyuid = clientcompanyuid;
    }

    public String getCompanyname() {
        return companyname;
    }

    public void setCompanyname(String companyname) {
        this.companyname = companyname;
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

    public String getWorkeruid() {
        return workeruid;
    }

    public void setWorkeruid(String workeruid) {
        this.workeruid = workeruid;
    }

    public List<AddTroubleBean> getBugdetails() {
        return bugdetails;
    }

    public void setBugdetails(List<AddTroubleBean> bugdetails) {
        this.bugdetails = bugdetails;
    }


    /**
     * 省份
     */
    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }
}
