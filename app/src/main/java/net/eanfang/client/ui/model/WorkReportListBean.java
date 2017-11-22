package net.eanfang.client.ui.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Mr.hou
 *
 * @on 2017/9/5  9:20
 * @email houzhongzhou@yeah.net
 * @desc  汇报列表
 */

public class WorkReportListBean implements Serializable {

        private List<AllBean> all;

        public List<AllBean> getAll() {
            return all;
        }

        public void setAll(List<AllBean> all) {
            this.all = all;
        }

        public static class AllBean {
            /**
             * companyName : 北京修监控科技
             * creatUserName : 林技师
             * createCompanyUid : 8c0f3a462d95410b81e20ee63fd63c41
             * createDate : 2017-08-31 16:04:08
             * createUser : 98b22d8a875444f4b454e5a215584dbc
             * departmentName : 技术部
             * firstLookTime : 2017-08-31 16:10:56
             * id : 1
             * pic1 : http://eanfangx.oss-cn-beijing.aliyuncs.com/7de48a39702d44f79773aee3df055f4d.png
             * receiveCompanyUid : 8c0f3a462d95410b81e20ee63fd63c41
             * receivePhone : 17600119928
             * receiveUser : 98b22d8a875444f4b454e5a215584dbc
             * receiveUserName : 林技师
             * status : 1
             * type : 周报
             * uid : c2b7de4ab37c41de88c2ff1889c5021e
             */

            private String companyName;
            private String creatUserName;
            private String createCompanyUid;
            private String createDate;
            private String createUser;
            private String departmentName;
            private String firstLookTime;
            private int id;
            private String pic1;
            private String receiveCompanyUid;
            private String receivePhone;
            private String receiveUser;
            private String receiveUserName;
            private String status;
            private String type;
            private String uid;

            public String getCompanyName() {
                return companyName;
            }

            public void setCompanyName(String companyName) {
                this.companyName = companyName;
            }

            public String getCreatUserName() {
                return creatUserName;
            }

            public void setCreatUserName(String creatUserName) {
                this.creatUserName = creatUserName;
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

            public String getPic1() {
                return pic1;
            }

            public void setPic1(String pic1) {
                this.pic1 = pic1;
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
        }
    }

