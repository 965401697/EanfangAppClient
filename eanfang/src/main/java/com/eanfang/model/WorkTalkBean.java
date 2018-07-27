package com.eanfang.model;

import java.io.Serializable;
import java.util.List;

/**
 * 描述：面谈员工
 *
 * @author Guanluocang
 * @date on 2018/7/26$  18:30$
 */
public class WorkTalkBean implements Serializable {

    /**
     * cacheKey : 840708d9a66f12f7c1671c073c3dda15
     * code : 20000
     * data : {"currPage":1,"list":[{"assigneeCompanyId":0,"assigneeOrgCode":"0","assigneePhone":"","assigneeTopCompanyId":0,"createTime":"2018-07-04 16:03:25","createUserId":0,"doImproveWorkspace":"","helpYou":"","id":2,"knowYou":"","missChance":"","notDo":"","outstandingPeople":"","ownerCompanyId":0,"ownerOrgCode":"211","ownerTopCompanyId":"944114800525914115","questionAndReason":"","solveFunction":"","status":1,"understand":"","unhappyMessage":"","unhappyWork":"","workerCompanyId":0,"workerId":0,"workerOrgCode":"0","workerTopCompanyId":0},{"assigneeCompanyId":0,"assigneeOrgCode":"0","assigneePhone":"","assigneeTopCompanyId":0,"createTime":"2018-07-04 18:03:42","createUserId":124692023,"doImproveWorkspace":"功夫就好吗","helpYou":"","id":3,"knowYou":"","missChance":"","notDo":"","outstandingPeople":"","ownerCompanyId":0,"ownerOrgCode":"0","ownerTopCompanyId":5689689,"questionAndReason":"颠覆性改变","solveFunction":"非常难","status":1,"understand":"","unhappyMessage":"法人股好处费你","unhappyWork":"","workerCompanyId":0,"workerId":0,"workerOrgCode":"0","workerTopCompanyId":0},{"assigneeCompanyId":5,"assigneeOrgCode":"7","assigneePhone":"15101139426","assigneeTopCompanyId":0,"createTime":"2018-07-04 17:03:25","createUserId":111222,"doImproveWorkspace":"应该很快i","helpYou":"带头人和头脑简单","id":4,"knowYou":"道具符合vxk","missChance":"igjoi不","notDo":"对方感受到","outstandingPeople":"","ownerCompanyId":46,"ownerOrgCode":"211","ownerTopCompanyId":9,"questionAndReason":" 好几个，看","solveFunction":"如果投入是不够","status":1,"understand":"j的结果vx","unhappyMessage":"很多","unhappyWork":"为了","workerCompanyId":25,"workerId":2558,"workerOrgCode":"11","workerTopCompanyId":44},{"assigneeCompanyId":5455588,"assigneeOrgCode":"789","assigneePhone":"15101139426","assigneeTopCompanyId":0,"createTime":"2018-07-04 17:03:25","createUserId":111222,"doImproveWorkspace":"应该很快i","helpYou":"带头人和头脑简单","id":5,"knowYou":"道具符合vxk","missChance":"igjoi不","notDo":"对方感受到","outstandingPeople":"","ownerCompanyId":44447788,"ownerOrgCode":"211","ownerTopCompanyId":"944114800525914115","questionAndReason":" 好几个，看","solveFunction":"如果投入是不够","status":1,"understand":"j的结果vx","unhappyMessage":"很多","unhappyWork":"为了","workerCompanyId":2544,"workerId":2558,"workerOrgCode":"11111","workerTopCompanyId":444988},{"assigneeCompanyId":"981406915978862593","assigneeOrgCode":"c","assigneePhone":"15940525612","assigneeTopCompanyId":"981406915978862593","createTime":"2018-07-26 17:38:01","createUserId":"981406915978862594","doImproveWorkspace":"sdfdsf","helpYou":"还挺好","id":6,"knowYou":"拒绝","missChance":"玩儿","notDo":"哥哥","outstandingPeople":"诉讼费","ownerCompanyId":"981406915978862593","ownerOrgCode":"981406915978862593","ownerTopCompanyId":"981406915978862593","questionAndReason":"收拾收拾","solveFunction":"搜索","status":1,"understand":"KIKI","unhappyMessage":"发发发","unhappyWork":"废废","workerCompanyId":"981406915978862593","workerId":"981406915978862594","workerOrgCode":"c","workerTopCompanyId":"981406915978862593"}],"pageSize":5,"totalCount":6,"totalPage":2}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * currPage : 1
         * list : [{"assigneeCompanyId":0,"assigneeOrgCode":"0","assigneePhone":"","assigneeTopCompanyId":0,"createTime":"2018-07-04 16:03:25","createUserId":0,"doImproveWorkspace":"","helpYou":"","id":2,"knowYou":"","missChance":"","notDo":"","outstandingPeople":"","ownerCompanyId":0,"ownerOrgCode":"211","ownerTopCompanyId":"944114800525914115","questionAndReason":"","solveFunction":"","status":1,"understand":"","unhappyMessage":"","unhappyWork":"","workerCompanyId":0,"workerId":0,"workerOrgCode":"0","workerTopCompanyId":0},{"assigneeCompanyId":0,"assigneeOrgCode":"0","assigneePhone":"","assigneeTopCompanyId":0,"createTime":"2018-07-04 18:03:42","createUserId":124692023,"doImproveWorkspace":"功夫就好吗","helpYou":"","id":3,"knowYou":"","missChance":"","notDo":"","outstandingPeople":"","ownerCompanyId":0,"ownerOrgCode":"0","ownerTopCompanyId":5689689,"questionAndReason":"颠覆性改变","solveFunction":"非常难","status":1,"understand":"","unhappyMessage":"法人股好处费你","unhappyWork":"","workerCompanyId":0,"workerId":0,"workerOrgCode":"0","workerTopCompanyId":0},{"assigneeCompanyId":5,"assigneeOrgCode":"7","assigneePhone":"15101139426","assigneeTopCompanyId":0,"createTime":"2018-07-04 17:03:25","createUserId":111222,"doImproveWorkspace":"应该很快i","helpYou":"带头人和头脑简单","id":4,"knowYou":"道具符合vxk","missChance":"igjoi不","notDo":"对方感受到","outstandingPeople":"","ownerCompanyId":46,"ownerOrgCode":"211","ownerTopCompanyId":9,"questionAndReason":" 好几个，看","solveFunction":"如果投入是不够","status":1,"understand":"j的结果vx","unhappyMessage":"很多","unhappyWork":"为了","workerCompanyId":25,"workerId":2558,"workerOrgCode":"11","workerTopCompanyId":44},{"assigneeCompanyId":5455588,"assigneeOrgCode":"789","assigneePhone":"15101139426","assigneeTopCompanyId":0,"createTime":"2018-07-04 17:03:25","createUserId":111222,"doImproveWorkspace":"应该很快i","helpYou":"带头人和头脑简单","id":5,"knowYou":"道具符合vxk","missChance":"igjoi不","notDo":"对方感受到","outstandingPeople":"","ownerCompanyId":44447788,"ownerOrgCode":"211","ownerTopCompanyId":"944114800525914115","questionAndReason":" 好几个，看","solveFunction":"如果投入是不够","status":1,"understand":"j的结果vx","unhappyMessage":"很多","unhappyWork":"为了","workerCompanyId":2544,"workerId":2558,"workerOrgCode":"11111","workerTopCompanyId":444988},{"assigneeCompanyId":"981406915978862593","assigneeOrgCode":"c","assigneePhone":"15940525612","assigneeTopCompanyId":"981406915978862593","createTime":"2018-07-26 17:38:01","createUserId":"981406915978862594","doImproveWorkspace":"sdfdsf","helpYou":"还挺好","id":6,"knowYou":"拒绝","missChance":"玩儿","notDo":"哥哥","outstandingPeople":"诉讼费","ownerCompanyId":"981406915978862593","ownerOrgCode":"981406915978862593","ownerTopCompanyId":"981406915978862593","questionAndReason":"收拾收拾","solveFunction":"搜索","status":1,"understand":"KIKI","unhappyMessage":"发发发","unhappyWork":"废废","workerCompanyId":"981406915978862593","workerId":"981406915978862594","workerOrgCode":"c","workerTopCompanyId":"981406915978862593"}]
         * pageSize : 5
         * totalCount : 6
         * totalPage : 2
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
             * assigneeCompanyId : 0
             * assigneeOrgCode : 0
             * assigneePhone :
             * assigneeTopCompanyId : 0
             * createTime : 2018-07-04 16:03:25
             * createUserId : 0
             * doImproveWorkspace :
             * helpYou :
             * id : 2
             * knowYou :
             * missChance :
             * notDo :
             * outstandingPeople :
             * ownerCompanyId : 0
             * ownerOrgCode : 211
             * ownerTopCompanyId : 944114800525914115
             * questionAndReason :
             * solveFunction :
             * status : 1
             * understand :
             * unhappyMessage :
             * unhappyWork :
             * workerCompanyId : 0
             * workerId : 0
             * workerOrgCode : 0
             * workerTopCompanyId : 0
             */

            private int assigneeCompanyId;
            private String assigneeOrgCode;
            private String assigneePhone;
            private int assigneeTopCompanyId;
            private String createTime;
            private int createUserId;
            private String doImproveWorkspace;
            private String helpYou;
            private int id;
            private String knowYou;
            private String missChance;
            private String notDo;
            private String outstandingPeople;
            private int ownerCompanyId;
            private String ownerOrgCode;
            private String ownerTopCompanyId;
            private String questionAndReason;
            private String solveFunction;
            private int status;
            private String understand;
            private String unhappyMessage;
            private String unhappyWork;
            private int workerCompanyId;
            private int workerId;
            private String workerOrgCode;
            private int workerTopCompanyId;

            public int getAssigneeCompanyId() {
                return assigneeCompanyId;
            }

            public void setAssigneeCompanyId(int assigneeCompanyId) {
                this.assigneeCompanyId = assigneeCompanyId;
            }

            public String getAssigneeOrgCode() {
                return assigneeOrgCode;
            }

            public void setAssigneeOrgCode(String assigneeOrgCode) {
                this.assigneeOrgCode = assigneeOrgCode;
            }

            public String getAssigneePhone() {
                return assigneePhone;
            }

            public void setAssigneePhone(String assigneePhone) {
                this.assigneePhone = assigneePhone;
            }

            public int getAssigneeTopCompanyId() {
                return assigneeTopCompanyId;
            }

            public void setAssigneeTopCompanyId(int assigneeTopCompanyId) {
                this.assigneeTopCompanyId = assigneeTopCompanyId;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public int getCreateUserId() {
                return createUserId;
            }

            public void setCreateUserId(int createUserId) {
                this.createUserId = createUserId;
            }

            public String getDoImproveWorkspace() {
                return doImproveWorkspace;
            }

            public void setDoImproveWorkspace(String doImproveWorkspace) {
                this.doImproveWorkspace = doImproveWorkspace;
            }

            public String getHelpYou() {
                return helpYou;
            }

            public void setHelpYou(String helpYou) {
                this.helpYou = helpYou;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getKnowYou() {
                return knowYou;
            }

            public void setKnowYou(String knowYou) {
                this.knowYou = knowYou;
            }

            public String getMissChance() {
                return missChance;
            }

            public void setMissChance(String missChance) {
                this.missChance = missChance;
            }

            public String getNotDo() {
                return notDo;
            }

            public void setNotDo(String notDo) {
                this.notDo = notDo;
            }

            public String getOutstandingPeople() {
                return outstandingPeople;
            }

            public void setOutstandingPeople(String outstandingPeople) {
                this.outstandingPeople = outstandingPeople;
            }

            public int getOwnerCompanyId() {
                return ownerCompanyId;
            }

            public void setOwnerCompanyId(int ownerCompanyId) {
                this.ownerCompanyId = ownerCompanyId;
            }

            public String getOwnerOrgCode() {
                return ownerOrgCode;
            }

            public void setOwnerOrgCode(String ownerOrgCode) {
                this.ownerOrgCode = ownerOrgCode;
            }

            public String getOwnerTopCompanyId() {
                return ownerTopCompanyId;
            }

            public void setOwnerTopCompanyId(String ownerTopCompanyId) {
                this.ownerTopCompanyId = ownerTopCompanyId;
            }

            public String getQuestionAndReason() {
                return questionAndReason;
            }

            public void setQuestionAndReason(String questionAndReason) {
                this.questionAndReason = questionAndReason;
            }

            public String getSolveFunction() {
                return solveFunction;
            }

            public void setSolveFunction(String solveFunction) {
                this.solveFunction = solveFunction;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getUnderstand() {
                return understand;
            }

            public void setUnderstand(String understand) {
                this.understand = understand;
            }

            public String getUnhappyMessage() {
                return unhappyMessage;
            }

            public void setUnhappyMessage(String unhappyMessage) {
                this.unhappyMessage = unhappyMessage;
            }

            public String getUnhappyWork() {
                return unhappyWork;
            }

            public void setUnhappyWork(String unhappyWork) {
                this.unhappyWork = unhappyWork;
            }

            public int getWorkerCompanyId() {
                return workerCompanyId;
            }

            public void setWorkerCompanyId(int workerCompanyId) {
                this.workerCompanyId = workerCompanyId;
            }

            public int getWorkerId() {
                return workerId;
            }

            public void setWorkerId(int workerId) {
                this.workerId = workerId;
            }

            public String getWorkerOrgCode() {
                return workerOrgCode;
            }

            public void setWorkerOrgCode(String workerOrgCode) {
                this.workerOrgCode = workerOrgCode;
            }

            public int getWorkerTopCompanyId() {
                return workerTopCompanyId;
            }

            public void setWorkerTopCompanyId(int workerTopCompanyId) {
                this.workerTopCompanyId = workerTopCompanyId;
            }
        }
    }
}
