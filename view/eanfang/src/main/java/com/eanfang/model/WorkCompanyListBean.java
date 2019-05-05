package com.eanfang.model;

import com.eanfang.model.sys.AccountEntity;
import com.eanfang.model.sys.OrgEntity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by MrHou
 *
 * @on 2018/1/23  21:11
 * @email houzhongzhou@yeah.net
 * @desc
 */

public class WorkCompanyListBean implements Serializable {

    /**
     * currPage : 1
     * list : [{"accountEntity":{"realName":"李旭"},"adminUserId":2,"aptitudePics":"图片","companyEntity":{"adminUserId":2,"areaCode":"3.11.1.5","createTime":"2017-12-21 17:31","intro":"安防运维服务第一平台","legalName":"祖蓝","licenseCode":"1.1","licensePic":"图片","logoPic":"图片","name":"安防公司","officeAddress":"北京海淀","orgId":1100,"registerAssets":"500万1元","scale":0,"status":1,"telPhone":"15873486758","tradeTypeCode":"1.1","unitType":3,"verifyMessage":"审核合格啊","verifyTime":"2017-12-21 17:31","verifyUserName":"管理员"},"createTime":"2017-12-21 17:31","goodRate":100,"honorPics":"图片","installCount":30,"item1":5,"item2":5,"item3":5,"item4":5,"item5":5,"legalAuthorizationPic":"图片","legalName":"祖蓝","orgEntity":{"level":0,"orgId":1100,"topCompanyId":1000},"orgId":1100,"publicPraise":500,"receiveCount":45,"repairCount":51,"scale":"0"}]
     * pageSize : 25
     * totalCount : 1
     * totalPage : 1
     */

    private int currPage;
    private int pageSize;
    private int totalCount;
    private int totalPage;
    private List<ListBean> list;

    public int getCurrPage() {
        return currPage;
    }

    public void setCurrPage(int currPage) {
        this.currPage = currPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public List<ListBean> getList() {
        if (list == null) {
            return new ArrayList<>();
        }
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean implements Serializable{
        /**
         * accountEntity : {"realName":"李旭"}
         * adminUserId : 2
         * aptitudePics : 图片
         * companyEntity : {"adminUserId":2,"areaCode":"3.11.1.5","createTime":"2017-12-21 17:31","intro":"安防运维服务第一平台","legalName":"祖蓝","licenseCode":"1.1","licensePic":"图片","logoPic":"图片","name":"安防公司","officeAddress":"北京海淀","orgId":1100,"registerAssets":"500万1元","scale":0,"status":1,"telPhone":"15873486758","tradeTypeCode":"1.1","unitType":3,"verifyMessage":"审核合格啊","verifyTime":"2017-12-21 17:31","verifyUserName":"管理员"}
         * createTime : 2017-12-21 17:31
         * goodRate : 100
         * honorPics : 图片
         * installCount : 30
         * item1 : 5
         * item2 : 5
         * item3 : 5
         * item4 : 5
         * item5 : 5
         * legalAuthorizationPic : 图片
         * legalName : 祖蓝
         * orgEntity : {"level":0,"orgId":1100,"topCompanyId":1000}
         * orgId : 1100
         * publicPraise : 500
         * receiveCount : 45
         * repairCount : 51
         * scale : 0
         */

        private AccountEntity accountEntity;
        private Long adminUserId;
        private String aptitudePics;
        private CompanyEntityBean companyEntity;
        private String createTime;
        private int goodRate;
        private String honorPics;
        private int installCount;
        private int item1;
        private int item2;
        private int item3;
        private int item4;
        private int item5;
        private String legalAuthorizationPic;
        private String legalName;
        private OrgEntity orgEntity;
        private Long orgId;
        private int publicPraise;
        private int receiveCount;
        private int repairCount;
        private String scale;

        public AccountEntity getAccountEntity() {
            return accountEntity;
        }

        public void setAccountEntity(AccountEntity accountEntity) {
            this.accountEntity = accountEntity;
        }

        public Long getAdminUserId() {
            return adminUserId;
        }

        public void setAdminUserId(Long adminUserId) {
            this.adminUserId = adminUserId;
        }

        public String getAptitudePics() {
            return aptitudePics == null ? "" : aptitudePics;
        }

        public void setAptitudePics(String aptitudePics) {
            this.aptitudePics = aptitudePics;
        }

        public CompanyEntityBean getCompanyEntity() {
            return companyEntity;
        }

        public void setCompanyEntity(CompanyEntityBean companyEntity) {
            this.companyEntity = companyEntity;
        }

        public String getCreateTime() {
            return createTime == null ? "" : createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getGoodRate() {
            return goodRate;
        }

        public void setGoodRate(int goodRate) {
            this.goodRate = goodRate;
        }

        public String getHonorPics() {
            return honorPics == null ? "" : honorPics;
        }

        public void setHonorPics(String honorPics) {
            this.honorPics = honorPics;
        }

        public int getInstallCount() {
            return installCount;
        }

        public void setInstallCount(int installCount) {
            this.installCount = installCount;
        }

        public int getItem1() {
            return item1;
        }

        public void setItem1(int item1) {
            this.item1 = item1;
        }

        public int getItem2() {
            return item2;
        }

        public void setItem2(int item2) {
            this.item2 = item2;
        }

        public int getItem3() {
            return item3;
        }

        public void setItem3(int item3) {
            this.item3 = item3;
        }

        public int getItem4() {
            return item4;
        }

        public void setItem4(int item4) {
            this.item4 = item4;
        }

        public int getItem5() {
            return item5;
        }

        public void setItem5(int item5) {
            this.item5 = item5;
        }

        public String getLegalAuthorizationPic() {
            return legalAuthorizationPic == null ? "" : legalAuthorizationPic;
        }

        public void setLegalAuthorizationPic(String legalAuthorizationPic) {
            this.legalAuthorizationPic = legalAuthorizationPic;
        }

        public String getLegalName() {
            return legalName == null ? "" : legalName;
        }

        public void setLegalName(String legalName) {
            this.legalName = legalName;
        }

        public OrgEntity getOrgEntity() {
            return orgEntity;
        }

        public void setOrgEntity(OrgEntity orgEntity) {
            this.orgEntity = orgEntity;
        }

        public Long getOrgId() {
            return orgId;
        }

        public void setOrgId(Long orgId) {
            this.orgId = orgId;
        }

        public int getPublicPraise() {
            return publicPraise;
        }

        public void setPublicPraise(int publicPraise) {
            this.publicPraise = publicPraise;
        }

        public int getReceiveCount() {
            return receiveCount;
        }

        public void setReceiveCount(int receiveCount) {
            this.receiveCount = receiveCount;
        }

        public int getRepairCount() {
            return repairCount;
        }

        public void setRepairCount(int repairCount) {
            this.repairCount = repairCount;
        }

        public String getScale() {
            return scale == null ? "" : scale;
        }

        public void setScale(String scale) {
            this.scale = scale;
        }

        public static class CompanyEntityBean {
            /**
             * adminUserId : 2
             * areaCode : 3.11.1.5
             * createTime : 2017-12-21 17:31
             * intro : 安防运维服务第一平台
             * legalName : 祖蓝
             * licenseCode : 1.1
             * licensePic : 图片
             * logoPic : 图片
             * name : 安防公司
             * officeAddress : 北京海淀
             * orgId : 1100
             * registerAssets : 500万1元
             * scale : 0
             * status : 1
             * telPhone : 15873486758
             * tradeTypeCode : 1.1
             * unitType : 3
             * verifyMessage : 审核合格啊
             * verifyTime : 2017-12-21 17:31
             * verifyUserName : 管理员
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
            private String verifyMessage;
            private String verifyTime;
            private String verifyUserName;

            public Long getAdminUserId() {
                return adminUserId;
            }

            public void setAdminUserId(Long adminUserId) {
                this.adminUserId = adminUserId;
            }

            public String getAreaCode() {
                return areaCode == null ? "" : areaCode;
            }

            public void setAreaCode(String areaCode) {
                this.areaCode = areaCode;
            }

            public String getCreateTime() {
                return createTime == null ? "" : createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getIntro() {
                return intro == null ? "" : intro;
            }

            public void setIntro(String intro) {
                this.intro = intro;
            }

            public String getLegalName() {
                return legalName == null ? "" : legalName;
            }

            public void setLegalName(String legalName) {
                this.legalName = legalName;
            }

            public String getLicenseCode() {
                return licenseCode == null ? "" : licenseCode;
            }

            public void setLicenseCode(String licenseCode) {
                this.licenseCode = licenseCode;
            }

            public String getLicensePic() {
                return licensePic == null ? "" : licensePic;
            }

            public void setLicensePic(String licensePic) {
                this.licensePic = licensePic;
            }

            public String getLogoPic() {
                return logoPic == null ? "" : logoPic;
            }

            public void setLogoPic(String logoPic) {
                this.logoPic = logoPic;
            }

            public String getName() {
                return name == null ? "" : name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getOfficeAddress() {
                return officeAddress == null ? "" : officeAddress;
            }

            public void setOfficeAddress(String officeAddress) {
                this.officeAddress = officeAddress;
            }

            public Long getOrgId() {
                return orgId;
            }

            public void setOrgId(Long orgId) {
                this.orgId = orgId;
            }

            public String getRegisterAssets() {
                return registerAssets == null ? "" : registerAssets;
            }

            public void setRegisterAssets(String registerAssets) {
                this.registerAssets = registerAssets;
            }

            public int getScale() {
                return scale;
            }

            public void setScale(int scale) {
                this.scale = scale;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getTelPhone() {
                return telPhone == null ? "" : telPhone;
            }

            public void setTelPhone(String telPhone) {
                this.telPhone = telPhone;
            }

            public String getTradeTypeCode() {
                return tradeTypeCode == null ? "" : tradeTypeCode;
            }

            public void setTradeTypeCode(String tradeTypeCode) {
                this.tradeTypeCode = tradeTypeCode;
            }

            public int getUnitType() {
                return unitType;
            }

            public void setUnitType(int unitType) {
                this.unitType = unitType;
            }

            public String getVerifyMessage() {
                return verifyMessage == null ? "" : verifyMessage;
            }

            public void setVerifyMessage(String verifyMessage) {
                this.verifyMessage = verifyMessage;
            }

            public String getVerifyTime() {
                return verifyTime == null ? "" : verifyTime;
            }

            public void setVerifyTime(String verifyTime) {
                this.verifyTime = verifyTime;
            }

            public String getVerifyUserName() {
                return verifyUserName == null ? "" : verifyUserName;
            }

            public void setVerifyUserName(String verifyUserName) {
                this.verifyUserName = verifyUserName;
            }
        }

    }
}

