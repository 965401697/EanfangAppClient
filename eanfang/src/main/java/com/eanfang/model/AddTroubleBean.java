package com.eanfang.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/4/11.
 */

public class AddTroubleBean implements Serializable {

    /**
     * bugtwo : 业务小类，bugtwo uid
     * bugtwoname : bugtwo name
     * bugthree : 设备名称 uid
     * bugthreename : 设备名称 name
     * bugfour : 品牌型号 uid
     * bugfourname : 品牌型号 name
     * bugposition : 故障位置
     * equipnum : 设备编码
     * bugpic1 : 故障图url
     * bugpic2 : 故障图url
     * bugpic3 : 故障图url
     * bugdesc : 故障描述
     */

    private String bugtwo;
    private String bugtwoname;
    private String bugthree;
    private String bugthreename;
    private String bugfour;
    private String bugfourname;
    private String bugposition;
    private String equipnum;
    private String bugpic1;
    private String bugpic2;
    private String bugpic3;
    private String bugdesc;

    /**
     * 关联的系统故障处理uid（如果客户选择了故障描述参考）
     */
    private String deviceFailureUid;

    /**
     * 关联的系统设备库设备（如果客户设备库选择了系统设备）
     */
    private String deviceUid;

    /**
     * 客户设备库uid（如果报修时选择了客户设备库设备）
     */
    private String customerDeviceUid;

    /**
     * 客户设备库设备名称（如果报修时选择了客户设备库设备）
     */
    private String customerDeviceName;


    public String getBugtwo() {
        return bugtwo == null ? "" : bugtwo;
    }

    public void setBugtwo(String bugtwo) {
        this.bugtwo = bugtwo;
    }

    public String getBugtwoname() {
        return bugtwoname == null ? "" : bugtwoname;
    }

    public void setBugtwoname(String bugtwoname) {
        this.bugtwoname = bugtwoname;
    }

    public String getBugthree() {
        return bugthree == null ? "" : bugthree;
    }

    public void setBugthree(String bugthree) {
        this.bugthree = bugthree;
    }

    public String getBugthreename() {
        return bugthreename == null ? "" : bugthreename;
    }

    public void setBugthreename(String bugthreename) {
        this.bugthreename = bugthreename;
    }

    public String getBugfour() {
        return bugfour == null ? "" : bugfour;
    }

    public void setBugfour(String bugfour) {
        this.bugfour = bugfour;
    }

    public String getBugfourname() {
        return bugfourname == null ? "" : bugfourname;
    }

    public void setBugfourname(String bugfourname) {
        this.bugfourname = bugfourname;
    }

    public String getBugposition() {
        return bugposition == null ? "" : bugposition;
    }

    public void setBugposition(String bugposition) {
        this.bugposition = bugposition;
    }

    public String getEquipnum() {
        return equipnum == null ? "" : equipnum;
    }

    public void setEquipnum(String equipnum) {
        this.equipnum = equipnum;
    }

    public String getBugpic1() {
        return bugpic1 == null ? "" : bugpic1;
    }

    public void setBugpic1(String bugpic1) {
        this.bugpic1 = bugpic1;
    }

    public String getBugpic2() {
        return bugpic2 == null ? "" : bugpic2;
    }

    public void setBugpic2(String bugpic2) {
        this.bugpic2 = bugpic2;
    }

    public String getBugpic3() {
        return bugpic3 == null ? "" : bugpic3;
    }

    public void setBugpic3(String bugpic3) {
        this.bugpic3 = bugpic3;
    }

    public String getBugdesc() {
        return bugdesc == null ? "" : bugdesc;
    }

    public void setBugdesc(String bugdesc) {
        this.bugdesc = bugdesc;
    }

    public String getDeviceFailureUid() {
        return deviceFailureUid == null ? "" : deviceFailureUid;
    }

    public void setDeviceFailureUid(String deviceFailureUid) {
        this.deviceFailureUid = deviceFailureUid;
    }

    public String getDeviceUid() {
        return deviceUid == null ? "" : deviceUid;
    }

    public void setDeviceUid(String deviceUid) {
        this.deviceUid = deviceUid;
    }

    public String getCustomerDeviceUid() {
        return customerDeviceUid == null ? "" : customerDeviceUid;
    }

    public void setCustomerDeviceUid(String customerDeviceUid) {
        this.customerDeviceUid = customerDeviceUid;
    }

    public String getCustomerDeviceName() {
        return customerDeviceName == null ? "" : customerDeviceName;
    }

    public void setCustomerDeviceName(String customerDeviceName) {
        this.customerDeviceName = customerDeviceName;
    }
}
