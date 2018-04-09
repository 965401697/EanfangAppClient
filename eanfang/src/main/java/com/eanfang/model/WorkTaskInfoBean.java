package com.eanfang.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mr.hou
 *
 * @on 2017/8/31  16:09
 * @email houzhongzhou@yeah.net
 * @desc 查看任务详情
 */

public class WorkTaskInfoBean implements Serializable {

    /**
     * assigneeOrg : {"orgCode":"c.c1.2","orgName":"维修部"}
     * assigneeOrgCode : c.c1.2
     * assigneeUser : {"accId":1,"accountEntity":{"accId":1,"mobile":"18500320187","realName":"jornlin"},"companyAdmin":false,"superAdmin":false,"sysAdmin":false,"userId":1}
     * assigneeUserId : 1
     * createCompany : {"orgId":1100,"orgName":"易安防北京运营公司"}
     * createCompanyId : 1100
     * createOrg : {"orgCode":"c.c1.2","orgName":"维修部"}
     * createOrgCode : c.c1.2
     * createTime : 2017-12-07 10:19:22
     * createTopCompanyId : 1000
     * createUser : {"accId":"937871078913511425","accountEntity":{"accId":"937871078913511425","realName":"侯"},"companyAdmin":false,"superAdmin":false,"sysAdmin":false,"userId":"937871079119032321"}
     * createUserId : 937871079119032321
     * firstLookTime : 2017-12-13 11:12:18
     * id : 938593747519250433
     * status : 1
     * title : 修改标题
     * workTaskDetails : [{"criterion":"完美完成","endTime":"2017-12-07 09:48:57","firstCallback":0,"firstLook":0,"id":1,"info":"沏壶龙井","instancyLevel":0,"joinPerson":"李四","purpose":"完成以上目标","sysWorkTaskId":"938593747519250433","thenCallback":0,"title":"倒茶"},{"criterion":"完美完成","endTime":"2017-12-07 09:48:55","firstCallback":0,"firstLook":0,"id":2,"info":"去招生","instancyLevel":0,"joinPerson":"张三","purpose":"完成以上目标","sysWorkTaskId":"938593747519250433","thenCallback":0,"title":"小任务啊"},{"criterion":"完美完成","endTime":"2017-12-07 09:48:59","firstCallback":0,"firstLook":0,"id":3,"info":"铺地板","instancyLevel":0,"joinPerson":"王五","purpose":"完成以上目标","sysWorkTaskId":"938593747519250433","thenCallback":0,"title":"装修"},{"criterion":"什么标准","firstCallback":1,"firstLook":1,"id":"938593747926097921","info":"明细内容","instancyLevel":0,"joinPerson":"张三和李四","pictures":"照片地址","purpose":"有什么目的","sysWorkTaskId":"938593747519250433","thenCallback":1,"title":"明细标题"},{"criterion":"757676","endTime":"2017-12-11 00:00:00","firstCallback":0,"firstLook":0,"id":"940136459984760833","info":"757676","instancyLevel":0,"joinPerson":"757676","purpose":"757676","sysWorkTaskId":"938593747519250433","thenCallback":0,"title":"754676"},{"criterion":"何炅赵丽颖","endTime":"2017-12-12 00:00:00","firstCallback":0,"firstLook":0,"id":"940459428556644353","info":"79979797","instancyLevel":0,"joinPerson":"侯还需要一屋老友记","pictures":"","purpose":"up嬉水湾山庄","sysWorkTaskId":"938593747519250433","thenCallback":0,"title":"4名您"},{"criterion":"啥标准","endTime":"2017-12-12 00:00:00","firstCallback":0,"firstLook":0,"id":"940461011436642305","info":"啥玩意","instancyLevel":0,"joinPerson":"还有谁","pictures":"","purpose":"想啥呐","sysWorkTaskId":"938593747519250433","thenCallback":0,"title":"您上晚自习"},{"criterion":"提子一晚上","endTime":"2017-12-14 00:00:00","firstCallback":2,"firstLook":4,"id":"940461576413585410","info":"仔仔细细","instancyLevel":2,"joinPerson":"后悔中","pictures":"","purpose":"刚下自习一下","sysWorkTaskId":"938593747519250433","thenCallback":2,"title":"轰轰轰"}]
     */

    private AssigneeOrgBean assigneeOrg;
    private String assigneeOrgCode;
    private AssigneeUserBean assigneeUser;
    private Long assigneeUserId;
    private CreateCompanyBean createCompany;
    private Long createCompanyId;
    private CreateOrgBean createOrg;
    private String createOrgCode;
    private String createTime;
    private Long createTopCompanyId;
    private CreateUserBean createUser;
    private String createUserId;
    private String firstLookTime;
    private Long id;
    private int status;
    private String title;
    private List<WorkTaskDetailsBean> workTaskDetails;

    public AssigneeOrgBean getAssigneeOrg() {
        return assigneeOrg;
    }

    public void setAssigneeOrg(AssigneeOrgBean assigneeOrg) {
        this.assigneeOrg = assigneeOrg;
    }

    public String getAssigneeOrgCode() {
        return assigneeOrgCode == null ? "" : assigneeOrgCode;
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

    public CreateCompanyBean getCreateCompany() {
        return createCompany;
    }

    public void setCreateCompany(CreateCompanyBean createCompany) {
        this.createCompany = createCompany;
    }

    public Long getCreateCompanyId() {
        return createCompanyId;
    }

    public void setCreateCompanyId(Long createCompanyId) {
        this.createCompanyId = createCompanyId;
    }

    public CreateOrgBean getCreateOrg() {
        return createOrg;
    }

    public void setCreateOrg(CreateOrgBean createOrg) {
        this.createOrg = createOrg;
    }

    public String getCreateOrgCode() {
        return createOrgCode == null ? "" : createOrgCode;
    }

    public void setCreateOrgCode(String createOrgCode) {
        this.createOrgCode = createOrgCode;
    }

    public String getCreateTime() {
        return createTime == null ? "" : createTime;
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

    public String getCreateUserId() {
        return createUserId == null ? "" : createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public String getFirstLookTime() {
        return firstLookTime == null ? "" : firstLookTime;
    }

    public void setFirstLookTime(String firstLookTime) {
        this.firstLookTime = firstLookTime;
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
        return title == null ? "" : title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<WorkTaskDetailsBean> getWorkTaskDetails() {
        if (workTaskDetails == null) {
            return new ArrayList<>();
        }
        return workTaskDetails;
    }

    public void setWorkTaskDetails(List<WorkTaskDetailsBean> workTaskDetails) {
        this.workTaskDetails = workTaskDetails;
    }

    public static class AssigneeOrgBean implements Serializable {
        /**
         * orgCode : c.c1.2
         * orgName : 维修部
         */

        private String orgCode;
        private String orgName;

        public String getOrgCode() {
            return orgCode == null ? "" : orgCode;
        }

        public void setOrgCode(String orgCode) {
            this.orgCode = orgCode;
        }

        public String getOrgName() {
            return orgName == null ? "" : orgName;
        }

        public void setOrgName(String orgName) {
            this.orgName = orgName;
        }
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
                return mobile == null ? "" : mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            public String getRealName() {
                return realName == null ? "" : realName;
            }

            public void setRealName(String realName) {
                this.realName = realName;
            }
        }
    }

    public static class CreateCompanyBean implements Serializable {
        /**
         * orgId : 1100
         * orgName : 易安防北京运营公司
         */

        private Long orgId;
        private String orgName;

        public Long getOrgId() {
            return orgId;
        }

        public void setOrgId(Long orgId) {
            this.orgId = orgId;
        }

        public String getOrgName() {
            return orgName == null ? "" : orgName;
        }

        public void setOrgName(String orgName) {
            this.orgName = orgName;
        }
    }

    public static class CreateOrgBean implements Serializable {
        /**
         * orgCode : c.c1.2
         * orgName : 维修部
         */

        private String orgCode;
        private String orgName;

        public String getOrgCode() {
            return orgCode == null ? "" : orgCode;
        }

        public void setOrgCode(String orgCode) {
            this.orgCode = orgCode;
        }

        public String getOrgName() {
            return orgName == null ? "" : orgName;
        }

        public void setOrgName(String orgName) {
            this.orgName = orgName;
        }
    }

    public static class CreateUserBean implements Serializable {
        /**
         * accId : 937871078913511425
         * accountEntity : {"accId":"937871078913511425","realName":"侯"}
         * companyAdmin : false
         * superAdmin : false
         * sysAdmin : false
         * userId : 937871079119032321
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
             * accId : 937871078913511425
             * realName : 侯
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

    public static class WorkTaskDetailsBean implements Serializable {
        /**
         * criterion : 完美完成
         * endTime : 2017-12-07 09:48:57
         * firstCallback : 0
         * firstLook : 0
         * id : 1
         * info : 沏壶龙井
         * instancyLevel : 0
         * joinPerson : 李四
         * purpose : 完成以上目标
         * sysWorkTaskId : 938593747519250433
         * thenCallback : 0
         * title : 倒茶
         * pictures : 照片地址
         */

        private String criterion;
        private String endTime;
        private int firstCallback;
        private int firstLook;
        private Long id;
        private String info;
        private int instancyLevel;
        private String joinPerson;
        private String purpose;
        private String sysWorkTaskId;
        private int thenCallback;
        private String title;
        private String pictures;

        public String getCriterion() {
            return criterion == null ? "" : criterion;
        }

        public void setCriterion(String criterion) {
            this.criterion = criterion;
        }

        public String getEndTime() {
            return endTime == null ? "" : endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public int getFirstCallback() {
            return firstCallback;
        }

        public void setFirstCallback(int firstCallback) {
            this.firstCallback = firstCallback;
        }

        public int getFirstLook() {
            return firstLook;
        }

        public void setFirstLook(int firstLook) {
            this.firstLook = firstLook;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getInfo() {
            return info == null ? "" : info;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public int getInstancyLevel() {
            return instancyLevel;
        }

        public void setInstancyLevel(int instancyLevel) {
            this.instancyLevel = instancyLevel;
        }

        public String getJoinPerson() {
            return joinPerson == null ? "" : joinPerson;
        }

        public void setJoinPerson(String joinPerson) {
            this.joinPerson = joinPerson;
        }

        public String getPurpose() {
            return purpose == null ? "" : purpose;
        }

        public void setPurpose(String purpose) {
            this.purpose = purpose;
        }

        public String getSysWorkTaskId() {
            return sysWorkTaskId == null ? "" : sysWorkTaskId;
        }

        public void setSysWorkTaskId(String sysWorkTaskId) {
            this.sysWorkTaskId = sysWorkTaskId;
        }

        public int getThenCallback() {
            return thenCallback;
        }

        public void setThenCallback(int thenCallback) {
            this.thenCallback = thenCallback;
        }

        public String getTitle() {
            return title == null ? "" : title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPictures() {
            return pictures == null ? "" : pictures;
        }

        public void setPictures(String pictures) {
            this.pictures = pictures;
        }
    }
}




