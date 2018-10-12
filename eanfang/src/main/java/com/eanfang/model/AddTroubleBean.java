package com.eanfang.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Administrator on 2017/4/11.
 */
@Getter
@Setter
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

}
