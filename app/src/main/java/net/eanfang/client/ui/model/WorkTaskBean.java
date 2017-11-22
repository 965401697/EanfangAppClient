package net.eanfang.client.ui.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Mr.hou
 *
 * @on 2017/8/29  14:26
 * @email houzhongzhou@yeah.net
 * @desc
 */

public class WorkTaskBean implements Serializable {

    /**
     * task : {"companyName":"北京修监控科技","createCompanyUid":"8c0f3a462d95410b81e20ee63fd63c41","createDate":1503659005000,"createUser":"98b22d8a875444f4b454e5a215584dbc","departmentName":"工程部","details":[{"createCompanyUid":"8c0f3a462d95410b81e20ee63fd63c41","createDate":1503659005000,"createUser":"98b22d8a875444f4b454e5a215584dbc","criterion":"criterion","endTime":1503659005000,"firstCallback":"6小时内","firstLook":"4小时内","id":1,"info":"这是一个测试任务详情1","instancyLevel":"紧急","joinPerson":"join#_person","pic1":"pic1","pic2":"pic2","pic3":"pic3","purpose":"purpose","status":"0","thenCallback":"8小时","title":"测试任务详情1","workerTaskUid":"1c913b11c97546cb89efe82584f53329"},{"createCompanyUid":"8c0f3a462d95410b81e20ee63fd63c41","createDate":1503659005000,"createUser":"98b22d8a875444f4b454e5a215584dbc","criterion":"criterion","endTime":1503659005000,"firstCallback":"6小时内","firstLook":"4小时内","id":2,"info":"这是一个测试任务详情2","instancyLevel":"紧急","joinPerson":"join#_person","pic1":"pic1","pic2":"pic2","pic3":"pic3","purpose":"purpose","status":"0","thenCallback":"8小时","title":"测试任务详情2","workerTaskUid":"1c913b11c97546cb89efe82584f53329"}],"firstLookTime":1503659162000,"id":1,"receiveCompanyUid":"8c0f3a462d95410b81e20ee63fd63c41","receivePhone":"17600119928","receiveUser":"98b22d8a875444f4b454e5a215584dbc","status":"1","title":"测试任务","uid":"1c913b11c97546cb89efe82584f53329"}
     */


    /**
     * companyName : 北京修监控科技
     * createCompanyUid : 8c0f3a462d95410b81e20ee63fd63c41
     * createDate : 1503659005000
     * createUser : 98b22d8a875444f4b454e5a215584dbc
     * departmentName : 工程部
     * details : [{"createCompanyUid":"8c0f3a462d95410b81e20ee63fd63c41","createDate":1503659005000,"createUser":"98b22d8a875444f4b454e5a215584dbc","criterion":"criterion","endTime":1503659005000,"firstCallback":"6小时内","firstLook":"4小时内","id":1,"info":"这是一个测试任务详情1","instancyLevel":"紧急","joinPerson":"join#_person","pic1":"pic1","pic2":"pic2","pic3":"pic3","purpose":"purpose","status":"0","thenCallback":"8小时","title":"测试任务详情1","workerTaskUid":"1c913b11c97546cb89efe82584f53329"},{"createCompanyUid":"8c0f3a462d95410b81e20ee63fd63c41","createDate":1503659005000,"createUser":"98b22d8a875444f4b454e5a215584dbc","criterion":"criterion","endTime":1503659005000,"firstCallback":"6小时内","firstLook":"4小时内","id":2,"info":"这是一个测试任务详情2","instancyLevel":"紧急","joinPerson":"join#_person","pic1":"pic1","pic2":"pic2","pic3":"pic3","purpose":"purpose","status":"0","thenCallback":"8小时","title":"测试任务详情2","workerTaskUid":"1c913b11c97546cb89efe82584f53329"}]
     * firstLookTime : 1503659162000
     * id : 1
     * receiveCompanyUid : 8c0f3a462d95410b81e20ee63fd63c41
     * receivePhone : 17600119928
     * receiveUser : 98b22d8a875444f4b454e5a215584dbc
     * status : 1
     * title : 测试任务
     * uid : 1c913b11c97546cb89efe82584f53329
     */

    private String companyName;
    private String createCompanyUid;
    private long createDate;
    private String createUser;
    private String departmentName;
    private long firstLookTime;
    private int id;
    private String receiveCompanyUid;
    private String receivePhone;
    private String receiveUser;
    private String status;
    private String title;
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

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
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

    public long getFirstLookTime() {
        return firstLookTime;
    }

    public void setFirstLookTime(long firstLookTime) {
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
         * createCompanyUid : 8c0f3a462d95410b81e20ee63fd63c41
         * createDate : 1503659005000
         * createUser : 98b22d8a875444f4b454e5a215584dbc
         * criterion : criterion
         * endTime : 1503659005000
         * firstCallback : 6小时内
         * firstLook : 4小时内
         * id : 1
         * info : 这是一个测试任务详情1
         * instancyLevel : 紧急
         * joinPerson : join#_person
         * pic1 : pic1
         * pic2 : pic2
         * pic3 : pic3
         * purpose : purpose
         * status : 0
         * thenCallback : 8小时
         * title : 测试任务详情1
         * workerTaskUid : 1c913b11c97546cb89efe82584f53329
         */

        private String createCompanyUid;
        private long createDate;
        private String createUser;
        private String criterion;
        private String endTime;
        private String firstCallback;
        private String firstLook;
        private int id;
        private String info;
        private String instancyLevel;
        private String joinPerson;
        private String pic1;
        private String pic2;
        private String pic3;
        private String purpose;
        private String status;
        private String thenCallback;
        private String title;
        private String workerTaskUid;

        public String getCreateCompanyUid() {
            return createCompanyUid;
        }

        public void setCreateCompanyUid(String createCompanyUid) {
            this.createCompanyUid = createCompanyUid;
        }

        public long getCreateDate() {
            return createDate;
        }

        public void setCreateDate(long createDate) {
            this.createDate = createDate;
        }

        public String getCreateUser() {
            return createUser;
        }

        public void setCreateUser(String createUser) {
            this.createUser = createUser;
        }

        public String getCriterion() {
            return criterion;
        }

        public void setCriterion(String criterion) {
            this.criterion = criterion;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getFirstCallback() {
            return firstCallback;
        }

        public void setFirstCallback(String firstCallback) {
            this.firstCallback = firstCallback;
        }

        public String getFirstLook() {
            return firstLook;
        }

        public void setFirstLook(String firstLook) {
            this.firstLook = firstLook;
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

        public String getInstancyLevel() {
            return instancyLevel;
        }

        public void setInstancyLevel(String instancyLevel) {
            this.instancyLevel = instancyLevel;
        }

        public String getJoinPerson() {
            return joinPerson;
        }

        public void setJoinPerson(String joinPerson) {
            this.joinPerson = joinPerson;
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

        public String getPurpose() {
            return purpose;
        }

        public void setPurpose(String purpose) {
            this.purpose = purpose;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getThenCallback() {
            return thenCallback;
        }

        public void setThenCallback(String thenCallback) {
            this.thenCallback = thenCallback;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getWorkerTaskUid() {
            return workerTaskUid;
        }

        public void setWorkerTaskUid(String workerTaskUid) {
            this.workerTaskUid = workerTaskUid;
        }
    }
}

