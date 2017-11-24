package net.eanfang.client.ui.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Mr.hou
 *
 * @on 2017/9/6  18:46
 * @email houzhongzhou@yeah.net
 * @desc
 */

public class WorkCheckListBean implements Serializable {

        private List<AllBean> all;

        public List<AllBean> getAll() {
            return all;
        }

        public void setAll(List<AllBean> all) {
            this.all = all;
        }

        public static class AllBean implements Serializable {
            /**
             * changeDeadline : 2017-09-05 13:29:19
             * changeRequire : 整改需求
             * companyName : 北京家乐福双井
             * creatUserName : 林技师
             * createCompanyUid : 8c0f3a462d95410b81e20ee63fd63c41
             * createDate : 2017-09-05 13:29:19
             * createUser : 98b22d8a875444f4b454e5a215584dbc
             * id : 1
             * pic1 : http://eanfangx.oss-cn-beijing.aliyuncs.com/5ee82a9a99b041d9b8edae4a340b8215.png
             * receiveCompanyUid : 8c0f3a462d95410b81e20ee63fd63c41
             * receivePhone : 17600119928
             * receiveUser : 98b22d8a875444f4b454e5a215584dbc
             * receiveUserName : 林技师
             * status : 0
             * title : 工作检查标题
             * uid : 457aa731896a40659e0570180f580be9
             */

            private String changeDeadline;
            private String changeRequire;
            private String companyName;
            private String creatUserName;
            private String createCompanyUid;
            private String createDate;
            private String createUser;
            private int id;
            private String pic1;
            private String receiveCompanyUid;
            private String receivePhone;
            private String receiveUser;
            private String receiveUserName;
            private String status;
            private String title;
            private String uid;

            public String getChangeDeadline() {
                return changeDeadline;
            }

            public void setChangeDeadline(String changeDeadline) {
                this.changeDeadline = changeDeadline;
            }

            public String getChangeRequire() {
                return changeRequire;
            }

            public void setChangeRequire(String changeRequire) {
                this.changeRequire = changeRequire;
            }

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

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }
        }
    }

