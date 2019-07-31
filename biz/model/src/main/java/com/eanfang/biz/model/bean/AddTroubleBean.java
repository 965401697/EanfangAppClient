package com.eanfang.biz.model.bean;

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
        return this.bugtwo;
    }

    public String getBugtwoname() {
        return this.bugtwoname;
    }

    public String getBugthree() {
        return this.bugthree;
    }

    public String getBugthreename() {
        return this.bugthreename;
    }

    public String getBugfour() {
        return this.bugfour;
    }

    public String getBugfourname() {
        return this.bugfourname;
    }

    public String getBugposition() {
        return this.bugposition;
    }

    public String getEquipnum() {
        return this.equipnum;
    }

    public String getBugpic1() {
        return this.bugpic1;
    }

    public String getBugpic2() {
        return this.bugpic2;
    }

    public String getBugpic3() {
        return this.bugpic3;
    }

    public String getBugdesc() {
        return this.bugdesc;
    }

    public String getDeviceFailureUid() {
        return this.deviceFailureUid;
    }

    public String getDeviceUid() {
        return this.deviceUid;
    }

    public String getCustomerDeviceUid() {
        return this.customerDeviceUid;
    }

    public String getCustomerDeviceName() {
        return this.customerDeviceName;
    }

    public void setBugtwo(String bugtwo) {
        this.bugtwo = bugtwo;
    }

    public void setBugtwoname(String bugtwoname) {
        this.bugtwoname = bugtwoname;
    }

    public void setBugthree(String bugthree) {
        this.bugthree = bugthree;
    }

    public void setBugthreename(String bugthreename) {
        this.bugthreename = bugthreename;
    }

    public void setBugfour(String bugfour) {
        this.bugfour = bugfour;
    }

    public void setBugfourname(String bugfourname) {
        this.bugfourname = bugfourname;
    }

    public void setBugposition(String bugposition) {
        this.bugposition = bugposition;
    }

    public void setEquipnum(String equipnum) {
        this.equipnum = equipnum;
    }

    public void setBugpic1(String bugpic1) {
        this.bugpic1 = bugpic1;
    }

    public void setBugpic2(String bugpic2) {
        this.bugpic2 = bugpic2;
    }

    public void setBugpic3(String bugpic3) {
        this.bugpic3 = bugpic3;
    }

    public void setBugdesc(String bugdesc) {
        this.bugdesc = bugdesc;
    }

    public void setDeviceFailureUid(String deviceFailureUid) {
        this.deviceFailureUid = deviceFailureUid;
    }

    public void setDeviceUid(String deviceUid) {
        this.deviceUid = deviceUid;
    }

    public void setCustomerDeviceUid(String customerDeviceUid) {
        this.customerDeviceUid = customerDeviceUid;
    }

    public void setCustomerDeviceName(String customerDeviceName) {
        this.customerDeviceName = customerDeviceName;
    }
}
