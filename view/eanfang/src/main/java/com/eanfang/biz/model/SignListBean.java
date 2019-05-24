package com.eanfang.biz.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by MrHou
 *
 * @on 2018/1/24  22:47
 * @email houzhongzhou@yeah.net
 * @desc
 */
public class SignListBean implements Serializable {

    private List<ListBean> list;

    public SignListBean(List<ListBean> list) {
        this.list = list;
    }

    public SignListBean() {
    }

    public List<ListBean> getList() {
        return this.list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean implements Serializable {

        private CompanyBean company;
        private String createCompanyId;
        private String createOrgCode;
        private String createTime;
        private String createTopCompanyId;
        private CreateUserBean createUser;
        private String createUserId;
        private String detailPlace;
        private String id;
        private String latitude;
        private String longitude;
        private String pictures;
        private String remarkInfo;
        private String signDay;
        private String signTime;
        private int status;
        private int sum;
        private String visitorName;
        private String zoneCode;

        public ListBean(CompanyBean company, String createCompanyId, String createOrgCode, String createTime, String createTopCompanyId, CreateUserBean createUser, String createUserId, String detailPlace, String id, String latitude, String longitude, String pictures, String remarkInfo, String signDay, String signTime, int status, int sum, String visitorName, String zoneCode) {
            this.company = company;
            this.createCompanyId = createCompanyId;
            this.createOrgCode = createOrgCode;
            this.createTime = createTime;
            this.createTopCompanyId = createTopCompanyId;
            this.createUser = createUser;
            this.createUserId = createUserId;
            this.detailPlace = detailPlace;
            this.id = id;
            this.latitude = latitude;
            this.longitude = longitude;
            this.pictures = pictures;
            this.remarkInfo = remarkInfo;
            this.signDay = signDay;
            this.signTime = signTime;
            this.status = status;
            this.sum = sum;
            this.visitorName = visitorName;
            this.zoneCode = zoneCode;
        }

        public ListBean() {
        }

        public CompanyBean getCompany() {
            return this.company;
        }

        public String getCreateCompanyId() {
            return this.createCompanyId;
        }

        public String getCreateOrgCode() {
            return this.createOrgCode;
        }

        public String getCreateTime() {
            return this.createTime;
        }

        public String getCreateTopCompanyId() {
            return this.createTopCompanyId;
        }

        public CreateUserBean getCreateUser() {
            return this.createUser;
        }

        public String getCreateUserId() {
            return this.createUserId;
        }

        public String getDetailPlace() {
            return this.detailPlace;
        }

        public String getId() {
            return this.id;
        }

        public String getLatitude() {
            return this.latitude;
        }

        public String getLongitude() {
            return this.longitude;
        }

        public String getPictures() {
            return this.pictures;
        }

        public String getRemarkInfo() {
            return this.remarkInfo;
        }

        public String getSignDay() {
            return this.signDay;
        }

        public String getSignTime() {
            return this.signTime;
        }

        public int getStatus() {
            return this.status;
        }

        public int getSum() {
            return this.sum;
        }

        public String getVisitorName() {
            return this.visitorName;
        }

        public String getZoneCode() {
            return this.zoneCode;
        }

        public void setCompany(CompanyBean company) {
            this.company = company;
        }

        public void setCreateCompanyId(String createCompanyId) {
            this.createCompanyId = createCompanyId;
        }

        public void setCreateOrgCode(String createOrgCode) {
            this.createOrgCode = createOrgCode;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public void setCreateTopCompanyId(String createTopCompanyId) {
            this.createTopCompanyId = createTopCompanyId;
        }

        public void setCreateUser(CreateUserBean createUser) {
            this.createUser = createUser;
        }

        public void setCreateUserId(String createUserId) {
            this.createUserId = createUserId;
        }

        public void setDetailPlace(String detailPlace) {
            this.detailPlace = detailPlace;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public void setPictures(String pictures) {
            this.pictures = pictures;
        }

        public void setRemarkInfo(String remarkInfo) {
            this.remarkInfo = remarkInfo;
        }

        public void setSignDay(String signDay) {
            this.signDay = signDay;
        }

        public void setSignTime(String signTime) {
            this.signTime = signTime;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public void setSum(int sum) {
            this.sum = sum;
        }

        public void setVisitorName(String visitorName) {
            this.visitorName = visitorName;
        }

        public void setZoneCode(String zoneCode) {
            this.zoneCode = zoneCode;
        }

        public static class CompanyBean  implements Serializable{

            private String adminUserId;
            private String companyId;
            private int countStaff;
            private int level;
            private String orgCode;
            private String orgId;
            private String orgName;
            private int orgType;
            private int parentOrgId;
            private int sortNum;
            private String topCompanyId;
            private String updateTime;
            private String updateUser;
            private int verifyStatus;

            public CompanyBean(String adminUserId, String companyId, int countStaff, int level, String orgCode, String orgId, String orgName, int orgType, int parentOrgId, int sortNum, String topCompanyId, String updateTime, String updateUser, int verifyStatus) {
                this.adminUserId = adminUserId;
                this.companyId = companyId;
                this.countStaff = countStaff;
                this.level = level;
                this.orgCode = orgCode;
                this.orgId = orgId;
                this.orgName = orgName;
                this.orgType = orgType;
                this.parentOrgId = parentOrgId;
                this.sortNum = sortNum;
                this.topCompanyId = topCompanyId;
                this.updateTime = updateTime;
                this.updateUser = updateUser;
                this.verifyStatus = verifyStatus;
            }

            public CompanyBean() {
            }

            public String getAdminUserId() {
                return this.adminUserId;
            }

            public String getCompanyId() {
                return this.companyId;
            }

            public int getCountStaff() {
                return this.countStaff;
            }

            public int getLevel() {
                return this.level;
            }

            public String getOrgCode() {
                return this.orgCode;
            }

            public String getOrgId() {
                return this.orgId;
            }

            public String getOrgName() {
                return this.orgName;
            }

            public int getOrgType() {
                return this.orgType;
            }

            public int getParentOrgId() {
                return this.parentOrgId;
            }

            public int getSortNum() {
                return this.sortNum;
            }

            public String getTopCompanyId() {
                return this.topCompanyId;
            }

            public String getUpdateTime() {
                return this.updateTime;
            }

            public String getUpdateUser() {
                return this.updateUser;
            }

            public int getVerifyStatus() {
                return this.verifyStatus;
            }

            public void setAdminUserId(String adminUserId) {
                this.adminUserId = adminUserId;
            }

            public void setCompanyId(String companyId) {
                this.companyId = companyId;
            }

            public void setCountStaff(int countStaff) {
                this.countStaff = countStaff;
            }

            public void setLevel(int level) {
                this.level = level;
            }

            public void setOrgCode(String orgCode) {
                this.orgCode = orgCode;
            }

            public void setOrgId(String orgId) {
                this.orgId = orgId;
            }

            public void setOrgName(String orgName) {
                this.orgName = orgName;
            }

            public void setOrgType(int orgType) {
                this.orgType = orgType;
            }

            public void setParentOrgId(int parentOrgId) {
                this.parentOrgId = parentOrgId;
            }

            public void setSortNum(int sortNum) {
                this.sortNum = sortNum;
            }

            public void setTopCompanyId(String topCompanyId) {
                this.topCompanyId = topCompanyId;
            }

            public void setUpdateTime(String updateTime) {
                this.updateTime = updateTime;
            }

            public void setUpdateUser(String updateUser) {
                this.updateUser = updateUser;
            }

            public void setVerifyStatus(int verifyStatus) {
                this.verifyStatus = verifyStatus;
            }
        }

        public static class CreateUserBean  implements Serializable{

            private String accId;
            private AccountEntityBean accountEntity;
            private boolean companyAdmin;
            private boolean superAdmin;
            private boolean sysAdmin;
            private String topCompanyId;
            private String userId;

            public CreateUserBean(String accId, AccountEntityBean accountEntity, boolean companyAdmin, boolean superAdmin, boolean sysAdmin, String topCompanyId, String userId) {
                this.accId = accId;
                this.accountEntity = accountEntity;
                this.companyAdmin = companyAdmin;
                this.superAdmin = superAdmin;
                this.sysAdmin = sysAdmin;
                this.topCompanyId = topCompanyId;
                this.userId = userId;
            }

            public CreateUserBean() {
            }

            public String getAccId() {
                return this.accId;
            }

            public AccountEntityBean getAccountEntity() {
                return this.accountEntity;
            }

            public boolean isCompanyAdmin() {
                return this.companyAdmin;
            }

            public boolean isSuperAdmin() {
                return this.superAdmin;
            }

            public boolean isSysAdmin() {
                return this.sysAdmin;
            }

            public String getTopCompanyId() {
                return this.topCompanyId;
            }

            public String getUserId() {
                return this.userId;
            }

            public void setAccId(String accId) {
                this.accId = accId;
            }

            public void setAccountEntity(AccountEntityBean accountEntity) {
                this.accountEntity = accountEntity;
            }

            public void setCompanyAdmin(boolean companyAdmin) {
                this.companyAdmin = companyAdmin;
            }

            public void setSuperAdmin(boolean superAdmin) {
                this.superAdmin = superAdmin;
            }

            public void setSysAdmin(boolean sysAdmin) {
                this.sysAdmin = sysAdmin;
            }

            public void setTopCompanyId(String topCompanyId) {
                this.topCompanyId = topCompanyId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

            public static class AccountEntityBean implements Serializable{
                /**
                 * accId : 979177961461190657
                 * accType : 3
                 * avatar : account/avatar/3543634438de44e5936d3ff185910da2.png
                 * mobile : 17600738557
                 * realName : 管管
                 * simplePwd : false
                 */

                private String accId;
                private int accType;
                private String avatar;
                private String mobile;
                private String realName;
                private boolean simplePwd;

                public AccountEntityBean(String accId, int accType, String avatar, String mobile, String realName, boolean simplePwd) {
                    this.accId = accId;
                    this.accType = accType;
                    this.avatar = avatar;
                    this.mobile = mobile;
                    this.realName = realName;
                    this.simplePwd = simplePwd;
                }

                public AccountEntityBean() {
                }

                public String getAccId() {
                    return this.accId;
                }

                public int getAccType() {
                    return this.accType;
                }

                public String getAvatar() {
                    return this.avatar;
                }

                public String getMobile() {
                    return this.mobile;
                }

                public String getRealName() {
                    return this.realName;
                }

                public boolean isSimplePwd() {
                    return this.simplePwd;
                }

                public void setAccId(String accId) {
                    this.accId = accId;
                }

                public void setAccType(int accType) {
                    this.accType = accType;
                }

                public void setAvatar(String avatar) {
                    this.avatar = avatar;
                }

                public void setMobile(String mobile) {
                    this.mobile = mobile;
                }

                public void setRealName(String realName) {
                    this.realName = realName;
                }

                public void setSimplePwd(boolean simplePwd) {
                    this.simplePwd = simplePwd;
                }
            }
        }
    }
}

