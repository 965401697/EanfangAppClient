package com.eanfang.model;

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

    /**
     * currPage : 1
     * list : [{"company":{"companyId":1100,"isVerify":1,"level":2,"orgCode":"c.c1","orgId":1100,"orgName":"易安防北京运营公司","orgType":1,"parentOrgId":0,"sortNum":0,"topCompanyId":1000,"updateTime":"2017-12-05 13:34","updateUser":1},"createCompanyId":1100,"createOrgCode":"c.c1.2","createTime":"2018-01-09 11:28","createTopCompanyId":1100,"createUser":{"accId":2,"accountEntity":{"accId":2,"accType":5,"avatar":"1","mobile":"15940525612","realName":"李旭"},"companyAdmin":false,"superAdmin":false,"sysAdmin":false,"userId":2},"createUserId":2,"detailPlace":"日本家乐福","id":2,"latitude":"7814.54","longitude":"7814.54","remarkInfo":"陌拜","signTime":"2018-01-09 17:30","status":0,"sum":2,"visitorName":"小张","zoneCode":"c1.c2.3"},{"company":{"$ref":"$.data.list[0].company"},"createCompanyId":1100,"createOrgCode":"c.c1.2","createTime":"2018-01-09 11:28","createTopCompanyId":1100,"createUser":{"$ref":"$.data.list[0].createUser"},"createUserId":2,"detailPlace":"北京家乐福2好远","id":1,"latitude":"9845.125","longitude":"4846.145","remarkInfo":"上门维修","signTime":"2018-01-09 17:30","status":0,"sum":2,"visitorName":"小李","zoneCode":"c1.c2.2"}]
     * pageSize : 2147483647
     * totalCount : 2
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
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean implements Serializable{
        /**
         * company : {"companyId":1100,"isVerify":1,"level":2,"orgCode":"c.c1","orgId":1100,"orgName":"易安防北京运营公司","orgType":1,"parentOrgId":0,"sortNum":0,"topCompanyId":1000,"updateTime":"2017-12-05 13:34","updateUser":1}
         * createCompanyId : 1100
         * createOrgCode : c.c1.2
         * createTime : 2018-01-09 11:28
         * createTopCompanyId : 1100
         * createUser : {"accId":2,"accountEntity":{"accId":2,"accType":5,"avatar":"1","mobile":"15940525612","realName":"李旭"},"companyAdmin":false,"superAdmin":false,"sysAdmin":false,"userId":2}
         * createUserId : 2
         * detailPlace : 日本家乐福
         * id : 2
         * latitude : 7814.54
         * longitude : 7814.54
         * remarkInfo : 陌拜
         * signTime : 2018-01-09 17:30
         * status : 0
         * sum : 2
         * visitorName : 小张
         * zoneCode : c1.c2.3
         */

        private CompanyBean company;
        private Long createCompanyId;
        private String createOrgCode;
        private String createTime;
        private Long createTopCompanyId;
        private CreateUserBean createUser;
        private Long createUserId;
        private String detailPlace;
        private Long id;
        private String latitude;
        private String longitude;
        private String remarkInfo;
        private String signTime;
        private int status;
        private int sum;
        private String visitorName;
        private String zoneCode;
        private String pictures;

        public String getPictures() {
            return pictures;
        }

        public void setPictures(String pictures) {
            this.pictures = pictures;
        }

        public CompanyBean getCompany() {
            return company;
        }

        public void setCompany(CompanyBean company) {
            this.company = company;
        }

        public Long getCreateCompanyId() {
            return createCompanyId;
        }

        public void setCreateCompanyId(Long createCompanyId) {
            this.createCompanyId = createCompanyId;
        }

        public String getCreateOrgCode() {
            return createOrgCode;
        }

        public void setCreateOrgCode(String createOrgCode) {
            this.createOrgCode = createOrgCode;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public Long getCreateTopCompanyId() {
            return createTopCompanyId;
        }

        public void setCreateTopCompanyId(Long createTopCompanyId) {
            this.createTopCompanyId = createTopCompanyId;
        }

        public CreateUserBean getCreateUser() {
            return createUser;
        }

        public void setCreateUser(CreateUserBean createUser) {
            this.createUser = createUser;
        }

        public Long getCreateUserId() {
            return createUserId;
        }

        public void setCreateUserId(Long createUserId) {
            this.createUserId = createUserId;
        }

        public String getDetailPlace() {
            return detailPlace;
        }

        public void setDetailPlace(String detailPlace) {
            this.detailPlace = detailPlace;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getRemarkInfo() {
            return remarkInfo;
        }

        public void setRemarkInfo(String remarkInfo) {
            this.remarkInfo = remarkInfo;
        }

        public String getSignTime() {
            return signTime;
        }

        public void setSignTime(String signTime) {
            this.signTime = signTime;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getSum() {
            return sum;
        }

        public void setSum(int sum) {
            this.sum = sum;
        }

        public String getVisitorName() {
            return visitorName;
        }

        public void setVisitorName(String visitorName) {
            this.visitorName = visitorName;
        }

        public String getZoneCode() {
            return zoneCode;
        }

        public void setZoneCode(String zoneCode) {
            this.zoneCode = zoneCode;
        }

        public static class CompanyBean implements Serializable{
            /**
             * companyId : 1100
             * isVerify : 1
             * level : 2
             * orgCode : c.c1
             * orgId : 1100
             * orgName : 易安防北京运营公司
             * orgType : 1
             * parentOrgId : 0
             * sortNum : 0
             * topCompanyId : 1000
             * updateTime : 2017-12-05 13:34
             * updateUser : 1
             */

            private Long companyId;
            private int isVerify;
            private int level;
            private String orgCode;
            private Long orgId;
            private String orgName;
            private int orgType;
            private Long parentOrgId;
            private int sortNum;
            private Long topCompanyId;
            private String updateTime;
            private Long updateUser;

            public Long getCompanyId() {
                return companyId;
            }

            public void setCompanyId(Long companyId) {
                this.companyId = companyId;
            }

            public int getIsVerify() {
                return isVerify;
            }

            public void setIsVerify(int isVerify) {
                this.isVerify = isVerify;
            }

            public int getLevel() {
                return level;
            }

            public void setLevel(int level) {
                this.level = level;
            }

            public String getOrgCode() {
                return orgCode;
            }

            public void setOrgCode(String orgCode) {
                this.orgCode = orgCode;
            }

            public Long getOrgId() {
                return orgId;
            }

            public void setOrgId(Long orgId) {
                this.orgId = orgId;
            }

            public String getOrgName() {
                return orgName;
            }

            public void setOrgName(String orgName) {
                this.orgName = orgName;
            }

            public int getOrgType() {
                return orgType;
            }

            public void setOrgType(int orgType) {
                this.orgType = orgType;
            }

            public Long getParentOrgId() {
                return parentOrgId;
            }

            public void setParentOrgId(Long parentOrgId) {
                this.parentOrgId = parentOrgId;
            }

            public int getSortNum() {
                return sortNum;
            }

            public void setSortNum(int sortNum) {
                this.sortNum = sortNum;
            }

            public Long getTopCompanyId() {
                return topCompanyId;
            }

            public void setTopCompanyId(Long topCompanyId) {
                this.topCompanyId = topCompanyId;
            }

            public String getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(String updateTime) {
                this.updateTime = updateTime;
            }

            public Long getUpdateUser() {
                return updateUser;
            }

            public void setUpdateUser(Long updateUser) {
                this.updateUser = updateUser;
            }
        }

        public static class CreateUserBean implements Serializable{
            /**
             * accId : 2
             * accountEntity : {"accId":2,"accType":5,"avatar":"1","mobile":"15940525612","realName":"李旭"}
             * companyAdmin : false
             * superAdmin : false
             * sysAdmin : false
             * userId : 2
             */

            private Long accId;
            private AccountEntityBean accountEntity;
            private boolean companyAdmin;
            private boolean superAdmin;
            private boolean sysAdmin;
            private Long userId;

            public Long getAccId() {
                return accId;
            }

            public void setAccId(Long accId) {
                this.accId = accId;
            }

            public AccountEntityBean getAccountEntity() {
                return accountEntity;
            }

            public void setAccountEntity(AccountEntityBean accountEntity) {
                this.accountEntity = accountEntity;
            }

            public boolean isCompanyAdmin() {
                return companyAdmin;
            }

            public void setCompanyAdmin(boolean companyAdmin) {
                this.companyAdmin = companyAdmin;
            }

            public boolean isSuperAdmin() {
                return superAdmin;
            }

            public void setSuperAdmin(boolean superAdmin) {
                this.superAdmin = superAdmin;
            }

            public boolean isSysAdmin() {
                return sysAdmin;
            }

            public void setSysAdmin(boolean sysAdmin) {
                this.sysAdmin = sysAdmin;
            }

            public Long getUserId() {
                return userId;
            }

            public void setUserId(Long userId) {
                this.userId = userId;
            }

            public static class AccountEntityBean implements Serializable{
                /**
                 * accId : 2
                 * accType : 5
                 * avatar : 1
                 * mobile : 15940525612
                 * realName : 李旭
                 */

                private Long accId;
                private int accType;
                private String avatar;
                private String mobile;
                private String realName;

                public Long getAccId() {
                    return accId;
                }

                public void setAccId(Long accId) {
                    this.accId = accId;
                }

                public int getAccType() {
                    return accType;
                }

                public void setAccType(int accType) {
                    this.accType = accType;
                }

                public String getAvatar() {
                    return avatar;
                }

                public void setAvatar(String avatar) {
                    this.avatar = avatar;
                }

                public String getMobile() {
                    return mobile;
                }

                public void setMobile(String mobile) {
                    this.mobile = mobile;
                }

                public String getRealName() {
                    return realName;
                }

                public void setRealName(String realName) {
                    this.realName = realName;
                }
            }
        }
    }
}

