package com.eanfang.model;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 描述：面谈员工列表
 *
 * @author Guanluocang
 * @date on 2018/8/2$  19:51$
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorkTalkListBean implements Serializable {


    /**
     * currPage : 1
     * list : [{"assigneeUserEntity":{"accId":"984353134128418818","accountEntity":{"accId":"984353134128418818","accType":0,"address":"北京农商银行24小时自助银行(定福家园分理处)","areaCode":"3.11.1.5","avatar":"ecbd2972163f425bacfbfed943e71553.png","email":"","gender":1,"idCard":"410926199005104491","mobile":"18611154430","nickName":"啊武30","qrCode":"3b523fe5a1be4c4b8124b6862d44bc36.png","realName":"义乌","regTime":"2018-04-12 16:50:50","status":0},"companyAdmin":false,"companyEntity":{"companyId":"1005473308202094594","countStaff":0,"level":1,"orgCode":"c","orgId":"1005473308202094594","orgName":"四组","orgType":0,"topCompanyId":"1005473308202094594","updateTime":"2018-06-15 15:37:04","updateUser":"1005473308202094595","verifyStatus":0},"companyId":"1005473308202094594","departmentEntity":{"companyId":"1005473308202094594","countStaff":0,"level":2,"orgCode":"c.2","orgId":"1005473367119482882","orgName":"一组","orgType":2,"topCompanyId":"1005473308202094594","updateTime":"2018-06-15 15:37:04","updateUser":"1005473308202094595"},"departmentId":"1005473367119482882","status":0,"superAdmin":false,"sysAdmin":false,"topCompanyEntity":{"companyId":"1005473308202094594","countStaff":0,"level":1,"orgCode":"c","orgId":"1005473308202094594","orgName":"四组","orgType":0,"topCompanyId":"1005473308202094594","updateTime":"2018-06-15 15:37:04","updateUser":"1005473308202094595","verifyStatus":0},"topCompanyId":"1005473308202094594","updateTime":"2018-06-15 15:37:04","updateUser":"1005473308202094595","userId":"1007527392761348098","userType":6},"assigneeUserId":"1007527392761348098","createTime":"2018-08-02 10:21:34","id":6,"orderNum":"MO1808021021150","ownerDepartmentId":"1005473367119482882","ownerTopCompanyId":"1005473308202094594","ownerUserId":"1011801951627210753","question1":"问题1（如果还有进步空间，应从哪方面入手？）","question10":"问题10（如何能更容易理解我想要表达的意思？）","question11":"问题11（你觉得我们错失的最大机遇是什么？）","question2":"问题2（所在部门存在的最大问题及原因？）","question3":"问题3（你会如何解决这些问题？）","question4":"问题4（工作中，不愉快的事情？）","question5":"问题5（公司中，最优秀的人？最敬佩的人？进步最大的人？）","question6":"问题6（针对当前工作，哪些方面不尽人意？）","question7":"问题7（哪些事情，应该做而没有去做？）","question8":"问题8（如何才能帮助到你？）","question9":"问题9（如何知道你需要帮助？）","status":0,"workerUserId":"1007528673584349186"},{"assigneeUserEntity":{"accId":"958589123373846529","accountEntity":{"accId":"958589123373846529","accType":3,"address":"幻眼国际(朝阳北路与高安屯路交叉口东150米)","areaCode":"","avatar":"2c00d1b320d74bdaa66d7773c056989b.png","birthday":"2014-06-03 00:00:00","email":"101@qq.com","gender":1,"idCard":"110101200001015778","mobile":"13800138010","nickName":"烟酒梁还不知","qrCode":"b4e6ecd7946c432085b63ccef5bb75d9.png","realName":"烟酒梁","regTime":"2018-01-31 14:33:51","status":0},"companyAdmin":true,"companyEntity":{"companyId":"981406915978862593","countStaff":0,"level":1,"orgCode":"c","orgId":"981406915978862593","orgName":"在人间","orgType":0,"topCompanyId":"981406915978862593","updateTime":"2018-06-01 20:25:38","updateUser":"958589123440955393","verifyStatus":1},"companyId":"981406915978862593","departmentEntity":{"companyId":"981406915978862593","countStaff":0,"level":3,"orgCode":"c.2.2","orgId":"1001659535615930369","orgName":"地狱波二级","orgType":2,"topCompanyId":"981406915978862593","updateTime":"2018-06-01 20:25:38","updateUser":"958589123440955393"},"departmentId":"1001659535615930369","status":0,"superAdmin":false,"sysAdmin":false,"topCompanyEntity":{"companyId":"981406915978862593","countStaff":0,"level":1,"orgCode":"c","orgId":"981406915978862593","orgName":"在人间","orgType":0,"topCompanyId":"981406915978862593","updateTime":"2018-06-01 20:25:38","updateUser":"958589123440955393","verifyStatus":1},"topCompanyId":"981406915978862593","updateTime":"2018-06-01 20:25:38","updateUser":"958589123440955393","userId":"981406915978862594","userType":3},"assigneeUserId":"981406915978862594","createTime":"2018-08-02 19:07:54","id":"1024975069451476994","orderNum":"MO1808021907566","ownerDepartmentId":"999932500837478402","ownerTopCompanyId":"981406915978862593","ownerUserEntity":{"accId":"958589123373846529","accountEntity":{"accId":"958589123373846529","accType":3,"address":"幻眼国际(朝阳北路与高安屯路交叉口东150米)","areaCode":"","avatar":"2c00d1b320d74bdaa66d7773c056989b.png","birthday":"2014-06-03 00:00:00","email":"101@qq.com","gender":1,"idCard":"110101200001015778","mobile":"13800138010","nickName":"烟酒梁还不知","qrCode":"b4e6ecd7946c432085b63ccef5bb75d9.png","realName":"烟酒梁","regTime":"2018-01-31 14:33:51","status":0},"companyAdmin":true,"companyEntity":{"companyId":"981406915978862593","countStaff":0,"level":1,"orgCode":"c","orgId":"981406915978862593","orgName":"在人间","orgType":0,"topCompanyId":"981406915978862593","updateTime":"2018-06-01 20:25:38","updateUser":"958589123440955393","verifyStatus":1},"companyId":"981406915978862593","departmentEntity":{"companyId":"981406915978862593","countStaff":0,"level":3,"orgCode":"c.2.2","orgId":"1001659535615930369","orgName":"地狱波二级","orgType":2,"topCompanyId":"981406915978862593","updateTime":"2018-06-01 20:25:38","updateUser":"958589123440955393"},"departmentId":"1001659535615930369","status":0,"superAdmin":false,"sysAdmin":false,"topCompanyEntity":{"companyId":"981406915978862593","countStaff":0,"level":1,"orgCode":"c","orgId":"981406915978862593","orgName":"在人间","orgType":0,"topCompanyId":"981406915978862593","updateTime":"2018-06-01 20:25:38","updateUser":"958589123440955393","verifyStatus":1},"topCompanyId":"981406915978862593","updateTime":"2018-06-01 20:25:38","updateUser":"958589123440955393","userId":"981406915978862594","userType":3},"ownerUserId":"981406915978862594","question1":"11119","question2":"1222，","status":0,"workerUserId":"999932500837478402"}]
     * pageSize : 5
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

    public static class ListBean {
        /**
         * assigneeUserEntity : {"accId":"984353134128418818","accountEntity":{"accId":"984353134128418818","accType":0,"address":"北京农商银行24小时自助银行(定福家园分理处)","areaCode":"3.11.1.5","avatar":"ecbd2972163f425bacfbfed943e71553.png","email":"","gender":1,"idCard":"410926199005104491","mobile":"18611154430","nickName":"啊武30","qrCode":"3b523fe5a1be4c4b8124b6862d44bc36.png","realName":"义乌","regTime":"2018-04-12 16:50:50","status":0},"companyAdmin":false,"companyEntity":{"companyId":"1005473308202094594","countStaff":0,"level":1,"orgCode":"c","orgId":"1005473308202094594","orgName":"四组","orgType":0,"topCompanyId":"1005473308202094594","updateTime":"2018-06-15 15:37:04","updateUser":"1005473308202094595","verifyStatus":0},"companyId":"1005473308202094594","departmentEntity":{"companyId":"1005473308202094594","countStaff":0,"level":2,"orgCode":"c.2","orgId":"1005473367119482882","orgName":"一组","orgType":2,"topCompanyId":"1005473308202094594","updateTime":"2018-06-15 15:37:04","updateUser":"1005473308202094595"},"departmentId":"1005473367119482882","status":0,"superAdmin":false,"sysAdmin":false,"topCompanyEntity":{"companyId":"1005473308202094594","countStaff":0,"level":1,"orgCode":"c","orgId":"1005473308202094594","orgName":"四组","orgType":0,"topCompanyId":"1005473308202094594","updateTime":"2018-06-15 15:37:04","updateUser":"1005473308202094595","verifyStatus":0},"topCompanyId":"1005473308202094594","updateTime":"2018-06-15 15:37:04","updateUser":"1005473308202094595","userId":"1007527392761348098","userType":6}
         * assigneeUserId : 1007527392761348098
         * createTime : 2018-08-02 10:21:34
         * id : 6
         * orderNum : MO1808021021150
         * ownerDepartmentId : 1005473367119482882
         * ownerTopCompanyId : 1005473308202094594
         * ownerUserId : 1011801951627210753
         * question1 : 问题1（如果还有进步空间，应从哪方面入手？）
         * question10 : 问题10（如何能更容易理解我想要表达的意思？）
         * question11 : 问题11（你觉得我们错失的最大机遇是什么？）
         * question2 : 问题2（所在部门存在的最大问题及原因？）
         * question3 : 问题3（你会如何解决这些问题？）
         * question4 : 问题4（工作中，不愉快的事情？）
         * question5 : 问题5（公司中，最优秀的人？最敬佩的人？进步最大的人？）
         * question6 : 问题6（针对当前工作，哪些方面不尽人意？）
         * question7 : 问题7（哪些事情，应该做而没有去做？）
         * question8 : 问题8（如何才能帮助到你？）
         * question9 : 问题9（如何知道你需要帮助？）
         * status : 0
         * workerUserId : 1007528673584349186
         * ownerUserEntity : {"accId":"958589123373846529","accountEntity":{"accId":"958589123373846529","accType":3,"address":"幻眼国际(朝阳北路与高安屯路交叉口东150米)","areaCode":"","avatar":"2c00d1b320d74bdaa66d7773c056989b.png","birthday":"2014-06-03 00:00:00","email":"101@qq.com","gender":1,"idCard":"110101200001015778","mobile":"13800138010","nickName":"烟酒梁还不知","qrCode":"b4e6ecd7946c432085b63ccef5bb75d9.png","realName":"烟酒梁","regTime":"2018-01-31 14:33:51","status":0},"companyAdmin":true,"companyEntity":{"companyId":"981406915978862593","countStaff":0,"level":1,"orgCode":"c","orgId":"981406915978862593","orgName":"在人间","orgType":0,"topCompanyId":"981406915978862593","updateTime":"2018-06-01 20:25:38","updateUser":"958589123440955393","verifyStatus":1},"companyId":"981406915978862593","departmentEntity":{"companyId":"981406915978862593","countStaff":0,"level":3,"orgCode":"c.2.2","orgId":"1001659535615930369","orgName":"地狱波二级","orgType":2,"topCompanyId":"981406915978862593","updateTime":"2018-06-01 20:25:38","updateUser":"958589123440955393"},"departmentId":"1001659535615930369","status":0,"superAdmin":false,"sysAdmin":false,"topCompanyEntity":{"companyId":"981406915978862593","countStaff":0,"level":1,"orgCode":"c","orgId":"981406915978862593","orgName":"在人间","orgType":0,"topCompanyId":"981406915978862593","updateTime":"2018-06-01 20:25:38","updateUser":"958589123440955393","verifyStatus":1},"topCompanyId":"981406915978862593","updateTime":"2018-06-01 20:25:38","updateUser":"958589123440955393","userId":"981406915978862594","userType":3}
         */

        private AssigneeUserEntityBean assigneeUserEntity;
        private String assigneeUserId;
        private String createTime;
        private String id;
        private String orderNum;
        private String ownerDepartmentId;
        private String ownerTopCompanyId;
        private String ownerUserId;
        private String question1;
        private String question10;
        private String question11;
        private String question2;
        private String question3;
        private String question4;
        private String question5;
        private String question6;
        private String question7;
        private String question8;
        private String question9;
        private int status;
        private String workerUserId;
        private OwnerUserEntityBean ownerUserEntity;

        public AssigneeUserEntityBean getAssigneeUserEntity() {
            return assigneeUserEntity;
        }

        public void setAssigneeUserEntity(AssigneeUserEntityBean assigneeUserEntity) {
            this.assigneeUserEntity = assigneeUserEntity;
        }

        public String getAssigneeUserId() {
            return assigneeUserId;
        }

        public void setAssigneeUserId(String assigneeUserId) {
            this.assigneeUserId = assigneeUserId;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getOrderNum() {
            return orderNum;
        }

        public void setOrderNum(String orderNum) {
            this.orderNum = orderNum;
        }

        public String getOwnerDepartmentId() {
            return ownerDepartmentId;
        }

        public void setOwnerDepartmentId(String ownerDepartmentId) {
            this.ownerDepartmentId = ownerDepartmentId;
        }

        public String getOwnerTopCompanyId() {
            return ownerTopCompanyId;
        }

        public void setOwnerTopCompanyId(String ownerTopCompanyId) {
            this.ownerTopCompanyId = ownerTopCompanyId;
        }

        public String getOwnerUserId() {
            return ownerUserId;
        }

        public void setOwnerUserId(String ownerUserId) {
            this.ownerUserId = ownerUserId;
        }

        public String getQuestion1() {
            return question1;
        }

        public void setQuestion1(String question1) {
            this.question1 = question1;
        }

        public String getQuestion10() {
            return question10;
        }

        public void setQuestion10(String question10) {
            this.question10 = question10;
        }

        public String getQuestion11() {
            return question11;
        }

        public void setQuestion11(String question11) {
            this.question11 = question11;
        }

        public String getQuestion2() {
            return question2;
        }

        public void setQuestion2(String question2) {
            this.question2 = question2;
        }

        public String getQuestion3() {
            return question3;
        }

        public void setQuestion3(String question3) {
            this.question3 = question3;
        }

        public String getQuestion4() {
            return question4;
        }

        public void setQuestion4(String question4) {
            this.question4 = question4;
        }

        public String getQuestion5() {
            return question5;
        }

        public void setQuestion5(String question5) {
            this.question5 = question5;
        }

        public String getQuestion6() {
            return question6;
        }

        public void setQuestion6(String question6) {
            this.question6 = question6;
        }

        public String getQuestion7() {
            return question7;
        }

        public void setQuestion7(String question7) {
            this.question7 = question7;
        }

        public String getQuestion8() {
            return question8;
        }

        public void setQuestion8(String question8) {
            this.question8 = question8;
        }

        public String getQuestion9() {
            return question9;
        }

        public void setQuestion9(String question9) {
            this.question9 = question9;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getWorkerUserId() {
            return workerUserId;
        }

        public void setWorkerUserId(String workerUserId) {
            this.workerUserId = workerUserId;
        }

        public OwnerUserEntityBean getOwnerUserEntity() {
            return ownerUserEntity;
        }

        public void setOwnerUserEntity(OwnerUserEntityBean ownerUserEntity) {
            this.ownerUserEntity = ownerUserEntity;
        }

        public static class AssigneeUserEntityBean {
            /**
             * accId : 984353134128418818
             * accountEntity : {"accId":"984353134128418818","accType":0,"address":"北京农商银行24小时自助银行(定福家园分理处)","areaCode":"3.11.1.5","avatar":"ecbd2972163f425bacfbfed943e71553.png","email":"","gender":1,"idCard":"410926199005104491","mobile":"18611154430","nickName":"啊武30","qrCode":"3b523fe5a1be4c4b8124b6862d44bc36.png","realName":"义乌","regTime":"2018-04-12 16:50:50","status":0}
             * companyAdmin : false
             * companyEntity : {"companyId":"1005473308202094594","countStaff":0,"level":1,"orgCode":"c","orgId":"1005473308202094594","orgName":"四组","orgType":0,"topCompanyId":"1005473308202094594","updateTime":"2018-06-15 15:37:04","updateUser":"1005473308202094595","verifyStatus":0}
             * companyId : 1005473308202094594
             * departmentEntity : {"companyId":"1005473308202094594","countStaff":0,"level":2,"orgCode":"c.2","orgId":"1005473367119482882","orgName":"一组","orgType":2,"topCompanyId":"1005473308202094594","updateTime":"2018-06-15 15:37:04","updateUser":"1005473308202094595"}
             * departmentId : 1005473367119482882
             * status : 0
             * superAdmin : false
             * sysAdmin : false
             * topCompanyEntity : {"companyId":"1005473308202094594","countStaff":0,"level":1,"orgCode":"c","orgId":"1005473308202094594","orgName":"四组","orgType":0,"topCompanyId":"1005473308202094594","updateTime":"2018-06-15 15:37:04","updateUser":"1005473308202094595","verifyStatus":0}
             * topCompanyId : 1005473308202094594
             * updateTime : 2018-06-15 15:37:04
             * updateUser : 1005473308202094595
             * userId : 1007527392761348098
             * userType : 6
             */

            private String accId;
            private AccountEntityBean accountEntity;
            private boolean companyAdmin;
            private CompanyEntityBean companyEntity;
            private String companyId;
            private DepartmentEntityBean departmentEntity;
            private String departmentId;
            private int status;
            private boolean superAdmin;
            private boolean sysAdmin;
            private TopCompanyEntityBean topCompanyEntity;
            private String topCompanyId;
            private String updateTime;
            private String updateUser;
            private String userId;
            private int userType;

            public String getAccId() {
                return accId;
            }

            public void setAccId(String accId) {
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

            public CompanyEntityBean getCompanyEntity() {
                return companyEntity;
            }

            public void setCompanyEntity(CompanyEntityBean companyEntity) {
                this.companyEntity = companyEntity;
            }

            public String getCompanyId() {
                return companyId;
            }

            public void setCompanyId(String companyId) {
                this.companyId = companyId;
            }

            public DepartmentEntityBean getDepartmentEntity() {
                return departmentEntity;
            }

            public void setDepartmentEntity(DepartmentEntityBean departmentEntity) {
                this.departmentEntity = departmentEntity;
            }

            public String getDepartmentId() {
                return departmentId;
            }

            public void setDepartmentId(String departmentId) {
                this.departmentId = departmentId;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
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

            public TopCompanyEntityBean getTopCompanyEntity() {
                return topCompanyEntity;
            }

            public void setTopCompanyEntity(TopCompanyEntityBean topCompanyEntity) {
                this.topCompanyEntity = topCompanyEntity;
            }

            public String getTopCompanyId() {
                return topCompanyId;
            }

            public void setTopCompanyId(String topCompanyId) {
                this.topCompanyId = topCompanyId;
            }

            public String getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(String updateTime) {
                this.updateTime = updateTime;
            }

            public String getUpdateUser() {
                return updateUser;
            }

            public void setUpdateUser(String updateUser) {
                this.updateUser = updateUser;
            }

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

            public int getUserType() {
                return userType;
            }

            public void setUserType(int userType) {
                this.userType = userType;
            }

            public static class AccountEntityBean {
                /**
                 * accId : 984353134128418818
                 * accType : 0
                 * address : 北京农商银行24小时自助银行(定福家园分理处)
                 * areaCode : 3.11.1.5
                 * avatar : ecbd2972163f425bacfbfed943e71553.png
                 * email :
                 * gender : 1
                 * idCard : 410926199005104491
                 * mobile : 18611154430
                 * nickName : 啊武30
                 * qrCode : 3b523fe5a1be4c4b8124b6862d44bc36.png
                 * realName : 义乌
                 * regTime : 2018-04-12 16:50:50
                 * status : 0
                 */

                private String accId;
                private int accType;
                private String address;
                private String areaCode;
                private String avatar;
                private String email;
                private int gender;
                private String idCard;
                private String mobile;
                private String nickName;
                private String qrCode;
                private String realName;
                private String regTime;
                private int status;

                public String getAccId() {
                    return accId;
                }

                public void setAccId(String accId) {
                    this.accId = accId;
                }

                public int getAccType() {
                    return accType;
                }

                public void setAccType(int accType) {
                    this.accType = accType;
                }

                public String getAddress() {
                    return address;
                }

                public void setAddress(String address) {
                    this.address = address;
                }

                public String getAreaCode() {
                    return areaCode;
                }

                public void setAreaCode(String areaCode) {
                    this.areaCode = areaCode;
                }

                public String getAvatar() {
                    return avatar;
                }

                public void setAvatar(String avatar) {
                    this.avatar = avatar;
                }

                public String getEmail() {
                    return email;
                }

                public void setEmail(String email) {
                    this.email = email;
                }

                public int getGender() {
                    return gender;
                }

                public void setGender(int gender) {
                    this.gender = gender;
                }

                public String getIdCard() {
                    return idCard;
                }

                public void setIdCard(String idCard) {
                    this.idCard = idCard;
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

                public String getQrCode() {
                    return qrCode;
                }

                public void setQrCode(String qrCode) {
                    this.qrCode = qrCode;
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
            }

            public static class CompanyEntityBean {
                /**
                 * companyId : 1005473308202094594
                 * countStaff : 0
                 * level : 1
                 * orgCode : c
                 * orgId : 1005473308202094594
                 * orgName : 四组
                 * orgType : 0
                 * topCompanyId : 1005473308202094594
                 * updateTime : 2018-06-15 15:37:04
                 * updateUser : 1005473308202094595
                 * verifyStatus : 0
                 */

                private String companyId;
                private int countStaff;
                private int level;
                private String orgCode;
                private String orgId;
                private String orgName;
                private int orgType;
                private String topCompanyId;
                private String updateTime;
                private String updateUser;
                private int verifyStatus;

                public String getCompanyId() {
                    return companyId;
                }

                public void setCompanyId(String companyId) {
                    this.companyId = companyId;
                }

                public int getCountStaff() {
                    return countStaff;
                }

                public void setCountStaff(int countStaff) {
                    this.countStaff = countStaff;
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

                public String getOrgId() {
                    return orgId;
                }

                public void setOrgId(String orgId) {
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

                public String getTopCompanyId() {
                    return topCompanyId;
                }

                public void setTopCompanyId(String topCompanyId) {
                    this.topCompanyId = topCompanyId;
                }

                public String getUpdateTime() {
                    return updateTime;
                }

                public void setUpdateTime(String updateTime) {
                    this.updateTime = updateTime;
                }

                public String getUpdateUser() {
                    return updateUser;
                }

                public void setUpdateUser(String updateUser) {
                    this.updateUser = updateUser;
                }

                public int getVerifyStatus() {
                    return verifyStatus;
                }

                public void setVerifyStatus(int verifyStatus) {
                    this.verifyStatus = verifyStatus;
                }
            }

            public static class DepartmentEntityBean {
                /**
                 * companyId : 1005473308202094594
                 * countStaff : 0
                 * level : 2
                 * orgCode : c.2
                 * orgId : 1005473367119482882
                 * orgName : 一组
                 * orgType : 2
                 * topCompanyId : 1005473308202094594
                 * updateTime : 2018-06-15 15:37:04
                 * updateUser : 1005473308202094595
                 */

                private String companyId;
                private int countStaff;
                private int level;
                private String orgCode;
                private String orgId;
                private String orgName;
                private int orgType;
                private String topCompanyId;
                private String updateTime;
                private String updateUser;

                public String getCompanyId() {
                    return companyId;
                }

                public void setCompanyId(String companyId) {
                    this.companyId = companyId;
                }

                public int getCountStaff() {
                    return countStaff;
                }

                public void setCountStaff(int countStaff) {
                    this.countStaff = countStaff;
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

                public String getOrgId() {
                    return orgId;
                }

                public void setOrgId(String orgId) {
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

                public String getTopCompanyId() {
                    return topCompanyId;
                }

                public void setTopCompanyId(String topCompanyId) {
                    this.topCompanyId = topCompanyId;
                }

                public String getUpdateTime() {
                    return updateTime;
                }

                public void setUpdateTime(String updateTime) {
                    this.updateTime = updateTime;
                }

                public String getUpdateUser() {
                    return updateUser;
                }

                public void setUpdateUser(String updateUser) {
                    this.updateUser = updateUser;
                }
            }

            public static class TopCompanyEntityBean {
                /**
                 * companyId : 1005473308202094594
                 * countStaff : 0
                 * level : 1
                 * orgCode : c
                 * orgId : 1005473308202094594
                 * orgName : 四组
                 * orgType : 0
                 * topCompanyId : 1005473308202094594
                 * updateTime : 2018-06-15 15:37:04
                 * updateUser : 1005473308202094595
                 * verifyStatus : 0
                 */

                private String companyId;
                private int countStaff;
                private int level;
                private String orgCode;
                private String orgId;
                private String orgName;
                private int orgType;
                private String topCompanyId;
                private String updateTime;
                private String updateUser;
                private int verifyStatus;

                public String getCompanyId() {
                    return companyId;
                }

                public void setCompanyId(String companyId) {
                    this.companyId = companyId;
                }

                public int getCountStaff() {
                    return countStaff;
                }

                public void setCountStaff(int countStaff) {
                    this.countStaff = countStaff;
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

                public String getOrgId() {
                    return orgId;
                }

                public void setOrgId(String orgId) {
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

                public String getTopCompanyId() {
                    return topCompanyId;
                }

                public void setTopCompanyId(String topCompanyId) {
                    this.topCompanyId = topCompanyId;
                }

                public String getUpdateTime() {
                    return updateTime;
                }

                public void setUpdateTime(String updateTime) {
                    this.updateTime = updateTime;
                }

                public String getUpdateUser() {
                    return updateUser;
                }

                public void setUpdateUser(String updateUser) {
                    this.updateUser = updateUser;
                }

                public int getVerifyStatus() {
                    return verifyStatus;
                }

                public void setVerifyStatus(int verifyStatus) {
                    this.verifyStatus = verifyStatus;
                }
            }
        }

        public static class OwnerUserEntityBean {
            /**
             * accId : 958589123373846529
             * accountEntity : {"accId":"958589123373846529","accType":3,"address":"幻眼国际(朝阳北路与高安屯路交叉口东150米)","areaCode":"","avatar":"2c00d1b320d74bdaa66d7773c056989b.png","birthday":"2014-06-03 00:00:00","email":"101@qq.com","gender":1,"idCard":"110101200001015778","mobile":"13800138010","nickName":"烟酒梁还不知","qrCode":"b4e6ecd7946c432085b63ccef5bb75d9.png","realName":"烟酒梁","regTime":"2018-01-31 14:33:51","status":0}
             * companyAdmin : true
             * companyEntity : {"companyId":"981406915978862593","countStaff":0,"level":1,"orgCode":"c","orgId":"981406915978862593","orgName":"在人间","orgType":0,"topCompanyId":"981406915978862593","updateTime":"2018-06-01 20:25:38","updateUser":"958589123440955393","verifyStatus":1}
             * companyId : 981406915978862593
             * departmentEntity : {"companyId":"981406915978862593","countStaff":0,"level":3,"orgCode":"c.2.2","orgId":"1001659535615930369","orgName":"地狱波二级","orgType":2,"topCompanyId":"981406915978862593","updateTime":"2018-06-01 20:25:38","updateUser":"958589123440955393"}
             * departmentId : 1001659535615930369
             * status : 0
             * superAdmin : false
             * sysAdmin : false
             * topCompanyEntity : {"companyId":"981406915978862593","countStaff":0,"level":1,"orgCode":"c","orgId":"981406915978862593","orgName":"在人间","orgType":0,"topCompanyId":"981406915978862593","updateTime":"2018-06-01 20:25:38","updateUser":"958589123440955393","verifyStatus":1}
             * topCompanyId : 981406915978862593
             * updateTime : 2018-06-01 20:25:38
             * updateUser : 958589123440955393
             * userId : 981406915978862594
             * userType : 3
             */

            private String accId;
            private AccountEntityBeanX accountEntity;
            private boolean companyAdmin;
            private String companyId;
            private DepartmentEntityBeanX departmentEntity;
            private String departmentId;
            private int status;
            private boolean superAdmin;
            private boolean sysAdmin;
            private String topCompanyId;
            private String updateTime;
            private String updateUser;
            private String userId;
            private int userType;

            public String getAccId() {
                return accId;
            }

            public void setAccId(String accId) {
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

            public String getCompanyId() {
                return companyId;
            }

            public void setCompanyId(String companyId) {
                this.companyId = companyId;
            }

            public DepartmentEntityBeanX getDepartmentEntity() {
                return departmentEntity;
            }

            public void setDepartmentEntity(DepartmentEntityBeanX departmentEntity) {
                this.departmentEntity = departmentEntity;
            }

            public String getDepartmentId() {
                return departmentId;
            }

            public void setDepartmentId(String departmentId) {
                this.departmentId = departmentId;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
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


            public String getTopCompanyId() {
                return topCompanyId;
            }

            public void setTopCompanyId(String topCompanyId) {
                this.topCompanyId = topCompanyId;
            }

            public String getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(String updateTime) {
                this.updateTime = updateTime;
            }

            public String getUpdateUser() {
                return updateUser;
            }

            public void setUpdateUser(String updateUser) {
                this.updateUser = updateUser;
            }

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

            public int getUserType() {
                return userType;
            }

            public void setUserType(int userType) {
                this.userType = userType;
            }

            public static class AccountEntityBeanX {
                /**
                 * accId : 958589123373846529
                 * accType : 3
                 * address : 幻眼国际(朝阳北路与高安屯路交叉口东150米)
                 * areaCode :
                 * avatar : 2c00d1b320d74bdaa66d7773c056989b.png
                 * birthday : 2014-06-03 00:00:00
                 * email : 101@qq.com
                 * gender : 1
                 * idCard : 110101200001015778
                 * mobile : 13800138010
                 * nickName : 烟酒梁还不知
                 * qrCode : b4e6ecd7946c432085b63ccef5bb75d9.png
                 * realName : 烟酒梁
                 * regTime : 2018-01-31 14:33:51
                 * status : 0
                 */

                private String accId;
                private int accType;
                private String address;
                private String areaCode;
                private String avatar;
                private String birthday;
                private String email;
                private int gender;
                private String idCard;
                private String mobile;
                private String nickName;
                private String qrCode;
                private String realName;
                private String regTime;
                private int status;

                public String getAccId() {
                    return accId;
                }

                public void setAccId(String accId) {
                    this.accId = accId;
                }

                public int getAccType() {
                    return accType;
                }

                public void setAccType(int accType) {
                    this.accType = accType;
                }

                public String getAddress() {
                    return address;
                }

                public void setAddress(String address) {
                    this.address = address;
                }

                public String getAreaCode() {
                    return areaCode;
                }

                public void setAreaCode(String areaCode) {
                    this.areaCode = areaCode;
                }

                public String getAvatar() {
                    return avatar;
                }

                public void setAvatar(String avatar) {
                    this.avatar = avatar;
                }

                public String getBirthday() {
                    return birthday;
                }

                public void setBirthday(String birthday) {
                    this.birthday = birthday;
                }

                public String getEmail() {
                    return email;
                }

                public void setEmail(String email) {
                    this.email = email;
                }

                public int getGender() {
                    return gender;
                }

                public void setGender(int gender) {
                    this.gender = gender;
                }

                public String getIdCard() {
                    return idCard;
                }

                public void setIdCard(String idCard) {
                    this.idCard = idCard;
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

                public String getQrCode() {
                    return qrCode;
                }

                public void setQrCode(String qrCode) {
                    this.qrCode = qrCode;
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
            }


            public static class DepartmentEntityBeanX {
                /**
                 * companyId : 981406915978862593
                 * countStaff : 0
                 * level : 3
                 * orgCode : c.2.2
                 * orgId : 1001659535615930369
                 * orgName : 地狱波二级
                 * orgType : 2
                 * topCompanyId : 981406915978862593
                 * updateTime : 2018-06-01 20:25:38
                 * updateUser : 958589123440955393
                 */

                private String companyId;
                private int countStaff;
                private int level;
                private String orgCode;
                private String orgId;
                private String orgName;
                private int orgType;
                private String topCompanyId;
                private String updateTime;
                private String updateUser;

                public String getCompanyId() {
                    return companyId;
                }

                public void setCompanyId(String companyId) {
                    this.companyId = companyId;
                }

                public int getCountStaff() {
                    return countStaff;
                }

                public void setCountStaff(int countStaff) {
                    this.countStaff = countStaff;
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

                public String getOrgId() {
                    return orgId;
                }

                public void setOrgId(String orgId) {
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

                public String getTopCompanyId() {
                    return topCompanyId;
                }

                public void setTopCompanyId(String topCompanyId) {
                    this.topCompanyId = topCompanyId;
                }

                public String getUpdateTime() {
                    return updateTime;
                }

                public void setUpdateTime(String updateTime) {
                    this.updateTime = updateTime;
                }

                public String getUpdateUser() {
                    return updateUser;
                }

                public void setUpdateUser(String updateUser) {
                    this.updateUser = updateUser;
                }
            }

        }
    }
}
