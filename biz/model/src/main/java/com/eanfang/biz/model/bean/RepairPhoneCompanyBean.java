package com.eanfang.biz.model.bean;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author guanluocang
 * @data 2019/8/16
 * @description
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RepairPhoneCompanyBean implements Serializable {

    private String assigneeOrgId;
    private String assigneeTopCompanyId;
    private String beginTime;
    private int busType;
    private String businessOneCode;
    private String createTime;
    private String createUserId;
    private String endTime;
    private int id;
    private OwnerAdminAccountBean ownerAdminAccount;
    private String ownerOrgId;
    private OwnerOrgUnitBean ownerOrgUnit;
    private String ownerTopCompanyId;
    private int status;
    private List<String> bizList;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OwnerAdminAccountBean implements Serializable {
        /**
         * accId : 979993533085958145
         * areaCode : 3.11.1.5
         * intro : 北京法安视科技有限公司
         * mobile : 18614098179
         * realName : 吴江
         * simplePwd : false
         * status : 0
         */

        private String accId;
        private String areaCode;
        private String intro;
        private String mobile;
        private String realName;
        private boolean simplePwd;
        private int status;

    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OwnerOrgUnitBean implements Serializable {
        /**
         * accId : 979993533085958145
         * adminUserId : 979995434422681603
         * areaCode : 3.11.1.5
         * bizCertify : 0
         * claimStatus : 1
         * createTime : 2018-12-04 14:05:50
         * defaultAddress : 306路朝阳区沙窝(公交站)
         * defaultLat : 39.962006
         * defaultLon : 116.632103
         * defaultPlaceCode : 3.11.1.5
         * defaultProjectName : 111
         * establishDate : 2017-07-09
         * expirationDate : 2089
         * intro : 北京法安视科技有限公司
         * isEnable : 0
         * legalName : 吴江
         * licenseCode : 110110100202100105
         * licensePic : logo/66ab414f8ae348028a2c9f446644d1e4.jpg
         * logoPic : 62fb9a88b0bb4e2a9b131e1170be7f5c.png
         * name : 北京法安视科技有限公司
         * officeAddress : 朝阳区
         * orgId : 979995434422681602
         * registerAddress : 北京
         * registerAssets : 500
         * scale : 2
         * status : 0
         * telPhone : 18614098179
         * tradeTypeCode : 4.5.3
         * unitType : 3
         * updateTime : 2019-07-12 10:33:17
         * verifyMessage :
         * verifyTime : 2019-07-12 10:33:30
         * verifyUserName : 管理员
         */

        private String accId;
        private String adminUserId;
        private String areaCode;
        private int bizCertify;
        private int claimStatus;
        private String createTime;
        private String defaultAddress;
        private String defaultLat;
        private String defaultLon;
        private String defaultPlaceCode;
        private String defaultProjectName;
        private String establishDate;
        private String expirationDate;
        private String intro;
        private int isEnable;
        private String legalName;
        private String licenseCode;
        private String licensePic;
        private String logoPic;
        private String name;
        private String officeAddress;
        private String orgId;
        private String registerAddress;
        private String registerAssets;
        private int scale;
        private int status;
        private String telPhone;
        private String tradeTypeCode;
        private int unitType;
        private String updateTime;
        private String verifyMessage;
        private String verifyTime;
        private String verifyUserName;

    }
}
