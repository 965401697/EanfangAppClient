package net.eanfang.client.ui.model;

import java.io.Serializable;
import java.util.List;


/**
 * @author Mr.hou
 * @desc 用户信息实体类
 * @date 2017/12/4
 * <p>
 * schema：告知GreenDao当前实体属于哪个schema
 * active：标记一个实体处于活动状态，活动实体有更新、删除和刷新方法  无论是更新生成都刷新
 * nameInDb：在数据中使用的别名，默认使用的是实体的类名
 * indexes：定义索引，可以跨越多个列
 * createInDb：标记创建数据库表**  是否创建表，默认为true,false时不创建
 * <p>
 * 基础属性注解
 * @Id :主键 Long型，可以通过@Id(autoincrement = true)设置自增长
 * @Property：设置一个非默认关系映射所对应的列名，默认是的使用字段名举例：
 * @Property (nameInDb="name")
 * @NotNul：设置数据库表当前列不能为空
 * @Transient：添加次标记之后不会生成数据库表的列 索引注解
 * @Index：使用@Index作为一个属性来创建一个索引，通过name设置索引别名，也可以通过unique给索引添加约束
 * @Unique：向数据库列添加了一个唯一的约束 关系注解
 * @ToOne：定义与另一个实体（一个实体对象）的关系
 * @ToMany：定义与多个实体对象的关系
 */
public class User implements Serializable, Cloneable {

    /**
     * account : {"accId":1,"accType":0,"avatar":"0","defaultUser":{"accId":1,"id":1,"subSystem":0,"user":{"accId":1,"accountEntity":{"$ref":"$.data.account"},"companyId":-1,"createTime":"2017-11-23 12:00","createUser":1,"departmentEntity":{"belongCompany":{"$ref":"@"},"belongTopCompany":{"$ref":"@"},"companyId":-1,"isVerify":0,"orgCode":"c","orgId":-1,"orgName":"系统保留","orgType":0,"parentOrgId":0,"sortNum":0,"topCompanyId":-1},"departmentId":-1,"regType":0,"status":1,"topCompanyId":-1,"updateTime":"2017-11-23 12:00","updateUser":1,"userId":1},"userId":1},"email":"29698868@qq.com","lastLoginTime":"2017-12-04 10:10","loginCount":141,"mobile":"13011054008","nickName":"一灯","passwd":"0DPiKuNIrrVmD8IUCuw1hQxNqZc=","realName":"徐定1","regTime":"2017-11-23 12:00","status":0,"subsystemAdmin":false,"superAdmin":true}
     * perms : ["sys:basedata:insert","sys:basedata:update","sys:bug:delete","sys:account","sys:bug:insert","sys:bug:update","sys:bug:list","sys:log:delete","sys:zoning:delete","sys:role:list","sys:zoning:update","sys:log:update","sys:dimension:list","sys:role","sys:org:info","sys:resource:update","sys:org:update","sys:role:update","sys:setting","sys:org:list","sys:basedata:delete","sys:log","sys:dimension:delete","sys:account:insert","sys:dimension:insert","sys:account:update","sys:resource:info","sys:dimension:update","sys","sys:zoning:list","sys:account:delete","sys:user:delete","sys:account:list","sys:user:update","sys:basedata:list","sys:role:grant","sys:zoning:info","sys:org","sys:resource:delete","sys:user:list","sys:account:info","sys:account:lock","sys:role:info","sys:org:delete","sys:resource:insert","sys:log:info","sys:org:insert","sys:user:lock","sys:role:insert","sys:dimension:info","sys:user:info","sys:log:insert","sys:role:delete","sys:bug:info","sys:user:insert","sys:resource:list","sys:zoning:insert","sys:log:list"]
     * token : 66962f6d-5af7-4faa-a4af-ce4945b951d9
     */

    private AccountBean account;
    private String token;
    private List<String> perms;

    public AccountBean getAccount() {
        return account;
    }

    public void setAccount(AccountBean account) {
        this.account = account;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<String> getPerms() {
        return perms;
    }

    public void setPerms(List<String> perms) {
        this.perms = perms;
    }

    public static class AccountBean implements Serializable {
        /**
         * accId : 1
         * accType : 0
         * avatar : 0
         * defaultUser : {"accId":1,"id":1,"subSystem":0,"user":{"accId":1,"accountEntity":{"$ref":"$.data.account"},"companyId":-1,"createTime":"2017-11-23 12:00","createUser":1,"departmentEntity":{"belongCompany":{"$ref":"@"},"belongTopCompany":{"$ref":"@"},"companyId":-1,"isVerify":0,"orgCode":"c","orgId":-1,"orgName":"系统保留","orgType":0,"parentOrgId":0,"sortNum":0,"topCompanyId":-1},"departmentId":-1,"regType":0,"status":1,"topCompanyId":-1,"updateTime":"2017-11-23 12:00","updateUser":1,"userId":1},"userId":1}
         * email : 29698868@qq.com
         * lastLoginTime : 2017-12-04 10:10
         * loginCount : 141
         * mobile : 13011054008
         * nickName : 一灯
         * passwd : 0DPiKuNIrrVmD8IUCuw1hQxNqZc=
         * realName : 徐定1
         * regTime : 2017-11-23 12:00
         * status : 0
         * subsystemAdmin : false
         * superAdmin : true
         */

        private Long accId;
        private int accType;
        private String avatar;
        private UserBean defaultUser;
        private String email;
        private String lastLoginTime;
        private int loginCount;
        private String mobile;
        private String nickName;
        private String passwd;
        private String realName;
        private String regTime;
        private int status;
        private boolean subsystemAdmin;
        private boolean superAdmin;

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

        public UserBean getDefaultUser() {
            return defaultUser;
        }

        public void setDefaultUser(UserBean defaultUser) {
            this.defaultUser = defaultUser;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getLastLoginTime() {
            return lastLoginTime;
        }

        public void setLastLoginTime(String lastLoginTime) {
            this.lastLoginTime = lastLoginTime;
        }

        public int getLoginCount() {
            return loginCount;
        }

        public void setLoginCount(int loginCount) {
            this.loginCount = loginCount;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getPasswd() {
            return passwd;
        }

        public void setPasswd(String passwd) {
            this.passwd = passwd;
        }

        public String getRealName() {
            return realName;
        }

        public void setRealName(String realName) {
            this.realName = realName;
        }

        public String getRegTime() {
            return regTime;
        }

        public void setRegTime(String regTime) {
            this.regTime = regTime;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public boolean isSubsystemAdmin() {
            return subsystemAdmin;
        }

        public void setSubsystemAdmin(boolean subsystemAdmin) {
            this.subsystemAdmin = subsystemAdmin;
        }

        public boolean isSuperAdmin() {
            return superAdmin;
        }

        public void setSuperAdmin(boolean superAdmin) {
            this.superAdmin = superAdmin;
        }

        public static class UserBean implements Serializable {
            /**
             * accId : 1
             * accountEntity : {"$ref":"$.data.account"}
             * companyId : -1
             * createTime : 2017-11-23 12:00
             * createUser : 1
             * departmentEntity : {"belongCompany":{"$ref":"@"},"belongTopCompany":{"$ref":"@"},"companyId":-1,"isVerify":0,"orgCode":"c","orgId":-1,"orgName":"系统保留","orgType":0,"parentOrgId":0,"sortNum":0,"topCompanyId":-1}
             * departmentId : -1
             * regType : 0
             * status : 1
             * topCompanyId : -1
             * updateTime : 2017-11-23 12:00
             * updateUser : 1
             * userId : 1
             */

            private Long accId;
            private AccountBean accountEntity;
            private Long companyId;
            private String createTime;
            private int createUser;
            private DepartmentEntityBean departmentEntity;
            private Long departmentId;
            private int regType;
            private int status;
            private Long topCompanyId;
            private String updateTime;
            private int updateUser;
            private Long userId;

            public Long getAccId() {
                return accId;
            }

            public void setAccId(Long accId) {
                this.accId = accId;
            }

            public AccountBean getAccountEntity() {
                return accountEntity;
            }

            public void setAccountEntity(AccountBean accountEntity) {
                this.accountEntity = accountEntity;
            }

            public Long getCompanyId() {
                return companyId;
            }

            public void setCompanyId(Long companyId) {
                this.companyId = companyId;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public int getCreateUser() {
                return createUser;
            }

            public void setCreateUser(int createUser) {
                this.createUser = createUser;
            }

            public DepartmentEntityBean getDepartmentEntity() {
                return departmentEntity;
            }

            public void setDepartmentEntity(DepartmentEntityBean departmentEntity) {
                this.departmentEntity = departmentEntity;
            }

            public Long getDepartmentId() {
                return departmentId;
            }

            public void setDepartmentId(Long departmentId) {
                this.departmentId = departmentId;
            }

            public int getRegType() {
                return regType;
            }

            public void setRegType(int regType) {
                this.regType = regType;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
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

            public int getUpdateUser() {
                return updateUser;
            }

            public void setUpdateUser(int updateUser) {
                this.updateUser = updateUser;
            }

            public Long getUserId() {
                return userId;
            }

            public void setUserId(Long userId) {
                this.userId = userId;
            }

            public static class DepartmentEntityBean implements Serializable {
                /**
                 * belongCompany : {"$ref":"@"}
                 * belongTopCompany : {"$ref":"@"}
                 * companyId : -1
                 * isVerify : 0
                 * orgCode : c
                 * orgId : -1
                 * orgName : 系统保留
                 * orgType : 0
                 * parentOrgId : 0
                 * sortNum : 0
                 * topCompanyId : -1
                 */

                private DepartmentEntityBean belongCompany;
                private DepartmentEntityBean belongTopCompany;
                private Long companyId;
                private int isVerify;
                private String orgCode;
                private int orgId;
                private String orgName;
                private int orgType;
                private Long parentOrgId;
                private int sortNum;
                private Long topCompanyId;

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

                public String getOrgCode() {
                    return orgCode;
                }

                public void setOrgCode(String orgCode) {
                    this.orgCode = orgCode;
                }

                public int getOrgId() {
                    return orgId;
                }

                public void setOrgId(int orgId) {
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

            }
        }
    }
}


