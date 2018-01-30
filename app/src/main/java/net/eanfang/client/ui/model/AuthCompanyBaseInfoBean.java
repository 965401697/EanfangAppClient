package net.eanfang.client.ui.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by MrHou
 *
 * @on 2018/1/25  14:57
 * @email houzhongzhou@yeah.net
 * @desc
 */
@Setter
@Getter
public class AuthCompanyBaseInfoBean implements Serializable {

    /**
     * adminUserId : 954273972575383553
     * areaCode : 3.12.1.2
     * createTime : 2018-01-19 16:46:59
     * intro : 安防公司简单介绍
     * legalName : 天天
     * licenseCode : 88888888
     * licensePic :
     * logoPic :
     * name : 安防公司单位
     * officeAddress : 天津天河区街道办事处
     * orgId : 954273972571189249
     * registerAssets : 1000
     * scale : 1
     * status : 0
     * telPhone : 13819838998
     * tradeTypeCode :
     * unitType : 3
     */

    private Long adminUserId;
    private String areaCode;
    private String createTime;
    private String intro;
    private String legalName;
    private String licenseCode;
    private String licensePic;
    private String logoPic;
    private String name;
    private String officeAddress;
    private Long orgId;
    private String registerAssets;
    private int scale;
    private int status;
    private String telPhone;
    private String tradeTypeCode;
    private int unitType;
    private Long accId;

}
