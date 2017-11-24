package net.eanfang.client.ui.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Mr.hou
 *
 * @on 2017/9/6  14:07
 * @email houzhongzhou@yeah.net
 * @desc
 */

public class WorkAddCheckBean implements Serializable {

    /**
     * changeDeadline : 2017-09-05 13:29:19
     * changeRequire : 整改需求
     * companyName : 北京家乐福双井
     * createCompanyUid : 8c0f3a462d95410b81e20ee63fd63c41
     * createDate : 2017-09-05 13:29:19
     * createUser : 98b22d8a875444f4b454e5a215584dbc
     * details : [{"businessOne":"电视监控","businessThree":"模拟枪型摄象机","businessTwo":"摄像采集","createCompanyUid":"8c0f3a462d95410b81e20ee63fd63c41","createDate":"2017-09-05 13:29:20","createUser":"98b22d8a875444f4b454e5a215584dbc","id":1,"info":"工作检查内容1","pic1":"http://eanfangx.oss-cn-beijing.aliyuncs.com/5ee82a9a99b041d9b8edae4a340b8215.png","pic2":"http://eanfangx.oss-cn-beijing.aliyuncs.com/5ee82a9a99b041d9b8edae4a340b8215.png","pic3":"http://eanfangx.oss-cn-beijing.aliyuncs.com/5ee82a9a99b041d9b8edae4a340b8215.png","region":"电视监控区域","status":"0","title":"工作检查明细标题2","uid":"9db976d5f92249508ad1a236a5b0dc03","workInspectUid":"457aa731896a40659e0570180f580be9"}]
     * id : 1
     * receiveCompanyUid : 8c0f3a462d95410b81e20ee63fd63c41
     * receivePhone : 17600119928
     * receiveUser : 98b22d8a875444f4b454e5a215584dbc
     * status : 0
     * title : 工作检查标题
     * uid : 457aa731896a40659e0570180f580be9
     */

    private String changeDeadline;
    private String changeRequire;
    private String companyName;
    private String createCompanyUid;
    private String createDate;
    private String createUser;
    private int id;
    private String receiveCompanyUid;
    private String receivePhone;
    private String receiveUser;
    private String status;
    private String title;
    private String uid;
    private List<DetailsBean> details;

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

    public List<DetailsBean> getDetails() {
        return details;
    }

    public void setDetails(List<DetailsBean> details) {
        this.details = details;
    }

    public static class DetailsBean implements Serializable {
        /**
         * businessOne : 电视监控
         * businessThree : 模拟枪型摄象机
         * businessTwo : 摄像采集
         * createCompanyUid : 8c0f3a462d95410b81e20ee63fd63c41
         * createDate : 2017-09-05 13:29:20
         * createUser : 98b22d8a875444f4b454e5a215584dbc
         * id : 1
         * info : 工作检查内容1
         * pic1 : http://eanfangx.oss-cn-beijing.aliyuncs.com/5ee82a9a99b041d9b8edae4a340b8215.png
         * pic2 : http://eanfangx.oss-cn-beijing.aliyuncs.com/5ee82a9a99b041d9b8edae4a340b8215.png
         * pic3 : http://eanfangx.oss-cn-beijing.aliyuncs.com/5ee82a9a99b041d9b8edae4a340b8215.png
         * region : 电视监控区域
         * status : 0
         * title : 工作检查明细标题2
         * uid : 9db976d5f92249508ad1a236a5b0dc03
         * workInspectUid : 457aa731896a40659e0570180f580be9
         */

        private String businessOne;
        private String businessThree;
        private String businessTwo;
        private String createCompanyUid;
        private String createDate;
        private String createUser;
        private int id;
        private String info;
        private String pic1;
        private String pic2;
        private String pic3;
        private String region;
        private String status;
        private String title;
        private String uid;
        private String workInspectUid;

        public String getBusinessOne() {
            return businessOne;
        }

        public void setBusinessOne(String businessOne) {
            this.businessOne = businessOne;
        }

        public String getBusinessThree() {
            return businessThree;
        }

        public void setBusinessThree(String businessThree) {
            this.businessThree = businessThree;
        }

        public String getBusinessTwo() {
            return businessTwo;
        }

        public void setBusinessTwo(String businessTwo) {
            this.businessTwo = businessTwo;
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

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public String getPic1() {
            return pic1;
        }

        public void setPic1(String pic1) {
            this.pic1 = pic1;
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

        public String getRegion() {
            return region;
        }

        public void setRegion(String region) {
            this.region = region;
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

        public String getWorkInspectUid() {
            return workInspectUid;
        }

        public void setWorkInspectUid(String workInspectUid) {
            this.workInspectUid = workInspectUid;
        }
    }
}
