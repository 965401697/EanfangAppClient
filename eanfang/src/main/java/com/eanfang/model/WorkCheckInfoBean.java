package com.eanfang.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Mr.hou
 *
 * @on 2017/9/6  20:37
 * @email houzhongzhou@yeah.net
 * @desc
 */

public class WorkCheckInfoBean implements Serializable {

    /**
     * assigneeOrgCode : c.c1.2
     * assigneeUser : {"accId":1,"accountEntity":{"accId":1,"mobile":"18500320187","realName":"jornlin"},"companyAdmin":false,"superAdmin":false,"sysAdmin":false,"userId":1}
     * assigneeUserId : 1
     * changeDeadlineTime : 2020-11-03 00:00:00
     * changeInfo : 设备移位
     * companyName : 北京法案视
     * createCompanyId : 1100
     * createOrgCode : c.c1.2
     * createTime : 2017-12-15 10:19:53
     * createTopCompanyId : 1100
     * createUser : {"accId":2,"accountEntity":{"accId":2,"realName":"李旭"},"companyAdmin":false,"superAdmin":false,"sysAdmin":false,"userId":2}
     * createUserId : 2
     * id : 941492977858588673
     * status : 0
     * title : 检查设备
     * workInspectDetails : [{"businessThreeCode":"1.11.13","id":"941492980165455874","info":"检查设备是否摆放成功","pictures":"这里有多张图片地址","region":"超市门口","status":0,"sysWorkInspectId":"941492977858588673","title":"整改设备摆放","workInspectDetailDisposes":[{"disposeInfo":"处理信息","id":6846544,"pictures":"这里有很多的图片地址","remarkInfo":"备注一下","status":0,"sysWorkInspectDetailId":"941492980165455874"}]},{"businessThreeCode":"1.11.13","id":"941492980958179329","info":"检查设备是否运行正常","pictures":"这里有多张图片地址","region":"厂库内","status":0,"sysWorkInspectId":"941492977858588673","title":"设备是否运行"}]
     */

    private String assigneeOrgCode;
    private AssigneeUserBean assigneeUser;
    private Long assigneeUserId;
    private String changeDeadlineTime;
    private String changeInfo;
    private String companyName;
    private Long createCompanyId;
    private String createOrgCode;
    private String createTime;
    private Long createTopCompanyId;
    private CreateUserBean createUser;
    private Long createUserId;
    private Long id;
    private int status;
    private String title;
    private List<WorkInspectDetailsBean> workInspectDetails;

    public String getAssigneeOrgCode() {
        return assigneeOrgCode;
    }

    public void setAssigneeOrgCode(String assigneeOrgCode) {
        this.assigneeOrgCode = assigneeOrgCode;
    }

    public AssigneeUserBean getAssigneeUser() {
        return assigneeUser;
    }

    public void setAssigneeUser(AssigneeUserBean assigneeUser) {
        this.assigneeUser = assigneeUser;
    }

    public Long getAssigneeUserId() {
        return assigneeUserId;
    }

    public void setAssigneeUserId(Long assigneeUserId) {
        this.assigneeUserId = assigneeUserId;
    }

    public String getChangeDeadlineTime() {
        return changeDeadlineTime;
    }

    public void setChangeDeadlineTime(String changeDeadlineTime) {
        this.changeDeadlineTime = changeDeadlineTime;
    }

    public String getChangeInfo() {
        return changeInfo;
    }

    public void setChangeInfo(String changeInfo) {
        this.changeInfo = changeInfo;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<WorkInspectDetailsBean> getWorkInspectDetails() {
        return workInspectDetails;
    }

    public void setWorkInspectDetails(List<WorkInspectDetailsBean> workInspectDetails) {
        this.workInspectDetails = workInspectDetails;
    }

    public static class AssigneeUserBean implements Serializable {
        /**
         * accId : 1
         * accountEntity : {"accId":1,"mobile":"18500320187","realName":"jornlin"}
         * companyAdmin : false
         * superAdmin : false
         * sysAdmin : false
         * userId : 1
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

        public static class AccountEntityBean implements Serializable {
            /**
             * accId : 1
             * mobile : 18500320187
             * realName : jornlin
             */

            private Long accId;
            private String mobile;
            private String realName;

            public Long getAccId() {
                return accId;
            }

            public void setAccId(Long accId) {
                this.accId = accId;
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

    public static class CreateUserBean implements Serializable {
        /**
         * accId : 2
         * accountEntity : {"accId":2,"realName":"李旭"}
         * companyAdmin : false
         * superAdmin : false
         * sysAdmin : false
         * userId : 2
         */

        private Long accId;
        private AccountEntityBeanX accountEntity;
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

        public AccountEntityBeanX getAccountEntity() {
            return accountEntity;
        }

        public void setAccountEntity(AccountEntityBeanX accountEntity) {
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

        public static class AccountEntityBeanX implements Serializable {
            /**
             * accId : 2
             * realName : 李旭
             */

            private Long accId;
            private String realName;

            public Long getAccId() {
                return accId;
            }

            public void setAccId(Long accId) {
                this.accId = accId;
            }

            public String getRealName() {
                return realName;
            }

            public void setRealName(String realName) {
                this.realName = realName;
            }
        }
    }

    public static class WorkInspectDetailsBean implements Serializable {
        /**
         * businessThreeCode : 1.11.13
         * id : 941492980165455874
         * info : 检查设备是否摆放成功
         * pictures : 这里有多张图片地址
         * region : 超市门口
         * status : 0
         * sysWorkInspectId : 941492977858588673
         * title : 整改设备摆放
         * workInspectDetailDisposes : [{"disposeInfo":"处理信息","id":6846544,"pictures":"这里有很多的图片地址","remarkInfo":"备注一下","status":0,"sysWorkInspectDetailId":"941492980165455874"}]
         */

        private String businessThreeCode;
        private Long id;
        private String info;
        private String pictures;
        private String region;
        private int status;
        private Long sysWorkInspectId;
        private String title;
        private List<WorkInspectDetailDisposesBean> workInspectDetailDisposes;

        public String getBusinessThreeCode() {
            return businessThreeCode;
        }

        public void setBusinessThreeCode(String businessThreeCode) {
            this.businessThreeCode = businessThreeCode;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public String getPictures() {
            return pictures;
        }

        public void setPictures(String pictures) {
            this.pictures = pictures;
        }

        public String getRegion() {
            return region;
        }

        public void setRegion(String region) {
            this.region = region;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public Long getSysWorkInspectId() {
            return sysWorkInspectId;
        }

        public void setSysWorkInspectId(Long sysWorkInspectId) {
            this.sysWorkInspectId = sysWorkInspectId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<WorkInspectDetailDisposesBean> getWorkInspectDetailDisposes() {
            return workInspectDetailDisposes;
        }

        public void setWorkInspectDetailDisposes(List<WorkInspectDetailDisposesBean> workInspectDetailDisposes) {
            this.workInspectDetailDisposes = workInspectDetailDisposes;
        }

        public static class WorkInspectDetailDisposesBean implements Serializable {
            /**
             * disposeInfo : 处理信息
             * id : 6846544
             * pictures : 这里有很多的图片地址
             * remarkInfo : 备注一下
             * status : 0
             * sysWorkInspectDetailId : 941492980165455874
             */

            private String disposeInfo;
            private Long id;
            private String pictures;
            private String remarkInfo;
            private int status;
            private Long sysWorkInspectDetailId;

            public String getDisposeInfo() {
                return disposeInfo;
            }

            public void setDisposeInfo(String disposeInfo) {
                this.disposeInfo = disposeInfo;
            }

            public Long getId() {
                return id;
            }

            public void setId(Long id) {
                this.id = id;
            }

            public String getPictures() {
                return pictures;
            }

            public void setPictures(String pictures) {
                this.pictures = pictures;
            }

            public String getRemarkInfo() {
                return remarkInfo;
            }

            public void setRemarkInfo(String remarkInfo) {
                this.remarkInfo = remarkInfo;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public Long getSysWorkInspectDetailId() {
                return sysWorkInspectDetailId;
            }

            public void setSysWorkInspectDetailId(Long sysWorkInspectDetailId) {
                this.sysWorkInspectDetailId = sysWorkInspectDetailId;
            }
        }
    }

}



