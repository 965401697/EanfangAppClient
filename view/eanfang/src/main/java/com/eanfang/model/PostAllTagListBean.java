package com.eanfang.model;

import java.util.List;

public class PostAllTagListBean {

    /**
     * workerBizGrantChange : {"addIds":[32,33,34],"delIds":[4690,4695]}
     * workerSysGrantChange : {"addIds":[10,11],"delIds":[4690,4695]}
     * workerTagGrantChange : {"addIds":[4690,4695],"delIds":[4690,4695]}
     * techWorkerVerify : {"accId":979993411866378200,"userId":979993412327751700}
     */

    private WorkerBizGrantChangeBean workerBizGrantChange;
    private WorkerSysGrantChangeBean workerSysGrantChange;
    private WorkerTagGrantChangeBean workerTagGrantChange;
    private TechWorkerVerifyBean techWorkerVerify;

    public WorkerBizGrantChangeBean getWorkerBizGrantChange() {
        return workerBizGrantChange;
    }

    public void setWorkerBizGrantChange(WorkerBizGrantChangeBean workerBizGrantChange) {
        this.workerBizGrantChange = workerBizGrantChange;
    }

    public WorkerSysGrantChangeBean getWorkerSysGrantChange() {
        return workerSysGrantChange;
    }

    public void setWorkerSysGrantChange(WorkerSysGrantChangeBean workerSysGrantChange) {
        this.workerSysGrantChange = workerSysGrantChange;
    }

    public WorkerTagGrantChangeBean getWorkerTagGrantChange() {
        return workerTagGrantChange;
    }

    public void setWorkerTagGrantChange(WorkerTagGrantChangeBean workerTagGrantChange) {
        this.workerTagGrantChange = workerTagGrantChange;
    }

    public TechWorkerVerifyBean getTechWorkerVerify() {
        return techWorkerVerify;
    }

    public void setTechWorkerVerify(TechWorkerVerifyBean techWorkerVerify) {
        this.techWorkerVerify = techWorkerVerify;
    }

    public static class WorkerBizGrantChangeBean {
        private List<Integer> addIds;
        private List<Integer> delIds;

        public List<Integer> getAddIds() {
            return addIds;
        }

        public void setAddIds(List<Integer> addIds) {
            this.addIds = addIds;
        }

        public List<Integer> getDelIds() {
            return delIds;
        }

        public void setDelIds(List<Integer> delIds) {
            this.delIds = delIds;
        }
    }

    public static class WorkerSysGrantChangeBean {
        private List<Integer> addIds;
        private List<Integer> delIds;

        public List<Integer> getAddIds() {
            return addIds;
        }

        public void setAddIds(List<Integer> addIds) {
            this.addIds = addIds;
        }

        public List<Integer> getDelIds() {
            return delIds;
        }

        public void setDelIds(List<Integer> delIds) {
            this.delIds = delIds;
        }
    }

    public static class WorkerTagGrantChangeBean {
        private List<Integer> addIds;
        private List<Integer> delIds;

        public List<Integer> getAddIds() {
            return addIds;
        }

        public void setAddIds(List<Integer> addIds) {
            this.addIds = addIds;
        }

        public List<Integer> getDelIds() {
            return delIds;
        }

        public void setDelIds(List<Integer> delIds) {
            this.delIds = delIds;
        }
    }

    public static class TechWorkerVerifyBean {
        /**
         * accId : 979993411866378200
         * userId : 979993412327751700
         */

        private long accId;
        private long userId;

        public long getAccId() {
            return accId;
        }

        public void setAccId(long accId) {
            this.accId = accId;
        }

        public long getUserId() {
            return userId;
        }

        public void setUserId(long userId) {
            this.userId = userId;
        }
    }
}
