package net.eanfang.client.ui.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Mr.hou
 *
 * @on 2017/9/5  11:55
 * @email houzhongzhou@yeah.net
 * @desc
 */

public class WorkReportInfoBean implements Serializable {

        /**
         * bean : {"companyName":"北京修监控科技","createCompanyUid":"8c0f3a462d95410b81e20ee63fd63c41","createDate":"2017-08-31 16:04:08","createUser":"98b22d8a875444f4b454e5a215584dbc","createUserName":"林技师","departmentName":"技术部","details":[{"createCompanyUid":"8c0f3a462d95410b81e20ee63fd63c41","createDate":"2017-08-31 16:04:08","createUser":"98b22d8a875444f4b454e5a215584dbc","field1":"完成内容","field2":"同事协作","field3":"遗留问题","field4":"原因","field5":"处理","id":1,"pic1":"http://eanfangx.oss-cn-beijing.aliyuncs.com/7de48a39702d44f79773aee3df055f4d.png","status":"0","type":"0","workReportUid":"c2b7de4ab37c41de88c2ff1889c5021e"},{"createCompanyUid":"8c0f3a462d95410b81e20ee63fd63c41","createDate":"2017-08-31 16:04:08","createUser":"98b22d8a875444f4b454e5a215584dbc","field1":"发现内容","field2":"责任归属人","field3":"处理结果","id":2,"pic1":"http://eanfangx.oss-cn-beijing.aliyuncs.com/7de48a39702d44f79773aee3df055f4d.png","status":"0","type":"1","workReportUid":"c2b7de4ab37c41de88c2ff1889c5021e"}],"firstLookTime":"2017-08-31 16:10:56","id":1,"receiveCompanyUid":"8c0f3a462d95410b81e20ee63fd63c41","receivePhone":"17600119928","receiveUser":"98b22d8a875444f4b454e5a215584dbc","receiveUserName":"林技师","status":"1","type":"周报","uid":"c2b7de4ab37c41de88c2ff1889c5021e"}
         */

        private BeanBean bean;

        public BeanBean getBean() {
            return bean;
        }

        public void setBean(BeanBean bean) {
            this.bean = bean;
        }

        public static class BeanBean implements Serializable {
            /**
             * companyName : 北京修监控科技
             * createCompanyUid : 8c0f3a462d95410b81e20ee63fd63c41
             * createDate : 2017-08-31 16:04:08
             * createUser : 98b22d8a875444f4b454e5a215584dbc
             * createUserName : 林技师
             * departmentName : 技术部
             * details : [{"createCompanyUid":"8c0f3a462d95410b81e20ee63fd63c41","createDate":"2017-08-31 16:04:08","createUser":"98b22d8a875444f4b454e5a215584dbc","field1":"完成内容","field2":"同事协作","field3":"遗留问题","field4":"原因","field5":"处理","id":1,"pic1":"http://eanfangx.oss-cn-beijing.aliyuncs.com/7de48a39702d44f79773aee3df055f4d.png","status":"0","type":"0","workReportUid":"c2b7de4ab37c41de88c2ff1889c5021e"},{"createCompanyUid":"8c0f3a462d95410b81e20ee63fd63c41","createDate":"2017-08-31 16:04:08","createUser":"98b22d8a875444f4b454e5a215584dbc","field1":"发现内容","field2":"责任归属人","field3":"处理结果","id":2,"pic1":"http://eanfangx.oss-cn-beijing.aliyuncs.com/7de48a39702d44f79773aee3df055f4d.png","status":"0","type":"1","workReportUid":"c2b7de4ab37c41de88c2ff1889c5021e"}]
             * firstLookTime : 2017-08-31 16:10:56
             * id : 1
             * receiveCompanyUid : 8c0f3a462d95410b81e20ee63fd63c41
             * receivePhone : 17600119928
             * receiveUser : 98b22d8a875444f4b454e5a215584dbc
             * receiveUserName : 林技师
             * status : 1
             * type : 周报
             * uid : c2b7de4ab37c41de88c2ff1889c5021e
             */

            private String companyName;
            private String createCompanyUid;
            private String createDate;
            private String createUser;
            private String createUserName;
            private String departmentName;
            private String firstLookTime;
            private int id;
            private String receiveCompanyUid;
            private String receivePhone;
            private String receiveUser;
            private String receiveUserName;
            private String status;
            private String type;
            private String uid;
            private List<DetailsBean> details;

            public String getCompanyName() {
                return companyName;
            }

            public void setCompanyName(String companyName) {
                this.companyName = companyName;
            }

            public String getCreateCompanyUid() {
                return createCompanyUid;
            }

            public void setCreateCompanyUid(String createCompanyUid) {
                this.createCompanyUid = createCompanyUid;
            }

            public String getCreateDate() {
                return createDate;
            }

            public void setCreateDate(String createDate) {
                this.createDate = createDate;
            }

            public String getCreateUser() {
                return createUser;
            }

            public void setCreateUser(String createUser) {
                this.createUser = createUser;
            }

            public String getCreateUserName() {
                return createUserName;
            }

            public void setCreateUserName(String createUserName) {
                this.createUserName = createUserName;
            }

            public String getDepartmentName() {
                return departmentName;
            }

            public void setDepartmentName(String departmentName) {
                this.departmentName = departmentName;
            }

            public String getFirstLookTime() {
                return firstLookTime;
            }

            public void setFirstLookTime(String firstLookTime) {
                this.firstLookTime = firstLookTime;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getReceiveCompanyUid() {
                return receiveCompanyUid;
            }

            public void setReceiveCompanyUid(String receiveCompanyUid) {
                this.receiveCompanyUid = receiveCompanyUid;
            }

            public String getReceivePhone() {
                return receivePhone;
            }

            public void setReceivePhone(String receivePhone) {
                this.receivePhone = receivePhone;
            }

            public String getReceiveUser() {
                return receiveUser;
            }

            public void setReceiveUser(String receiveUser) {
                this.receiveUser = receiveUser;
            }

            public String getReceiveUserName() {
                return receiveUserName;
            }

            public void setReceiveUserName(String receiveUserName) {
                this.receiveUserName = receiveUserName;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }

            public List<DetailsBean> getDetails() {
                return details;
            }

            public void setDetails(List<DetailsBean> details) {
                this.details = details;
            }

            public static class DetailsBean implements Serializable {
                /**
                 * createCompanyUid : 8c0f3a462d95410b81e20ee63fd63c41
                 * createDate : 2017-08-31 16:04:08
                 * createUser : 98b22d8a875444f4b454e5a215584dbc
                 * field1 : 完成内容
                 * field2 : 同事协作
                 * field3 : 遗留问题
                 * field4 : 原因
                 * field5 : 处理
                 * id : 1
                 * pic1 : http://eanfangx.oss-cn-beijing.aliyuncs.com/7de48a39702d44f79773aee3df055f4d.png
                 * status : 0
                 * type : 0
                 * workReportUid : c2b7de4ab37c41de88c2ff1889c5021e
                 */

                private String createCompanyUid;
                private String createDate;
                private String createUser;
                private String field1;
                private String field2;
                private String field3;
                private String field4;
                private String field5;
                private int id;
                private String pic1;
                private String pic2;
                private String pic3;
                private String status;
                private String type;
                private String workReportUid;

                public String getCreateCompanyUid() {
                    return createCompanyUid;
                }

                public void setCreateCompanyUid(String createCompanyUid) {
                    this.createCompanyUid = createCompanyUid;
                }

                public String getPic2() {
                    return pic2;
                }

                public void setPic2(String pic2) {
                    this.pic2 = pic2;
                }

                public String getPic3() {
                    return pic3;
                }

                public void setPic3(String pic3) {
                    this.pic3 = pic3;
                }

                public String getCreateDate() {
                    return createDate;
                }

                public void setCreateDate(String createDate) {
                    this.createDate = createDate;
                }

                public String getCreateUser() {
                    return createUser;
                }

                public void setCreateUser(String createUser) {
                    this.createUser = createUser;
                }

                public String getField1() {
                    return field1;
                }

                public void setField1(String field1) {
                    this.field1 = field1;
                }

                public String getField2() {
                    return field2;
                }

                public void setField2(String field2) {
                    this.field2 = field2;
                }

                public String getField3() {
                    return field3;
                }

                public void setField3(String field3) {
                    this.field3 = field3;
                }

                public String getField4() {
                    return field4;
                }

                public void setField4(String field4) {
                    this.field4 = field4;
                }

                public String getField5() {
                    return field5;
                }

                public void setField5(String field5) {
                    this.field5 = field5;
                }

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getPic1() {
                    return pic1;
                }

                public void setPic1(String pic1) {
                    this.pic1 = pic1;
                }

                public String getStatus() {
                    return status;
                }

                public void setStatus(String status) {
                    this.status = status;
                }

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public String getWorkReportUid() {
                    return workReportUid;
                }

                public void setWorkReportUid(String workReportUid) {
                    this.workReportUid = workReportUid;
                }
            }
        }
    }

