package com.eanfang.biz.model.datastatistics;

import java.io.Serializable;
import java.util.List;


/**
 * 描述：报修统计Bean
 *
 * @author Guanluocang
 * @date on 2018/7/10$  18:33$
 */
public class DataStatisticsBean implements Serializable {


    /**
     * cacheKey : 5e952f57a9f0d2d08614ff3d70407c70
     * code : 20000
     * data : {"repair":[{"bughandleStatus":0,"bughandleStatusTwoList":[{"bughandleStatusTwo":0,"count":3},{"bughandleStatusTwo":1,"count":1},{"bughandleStatusTwo":3,"count":3}],"count":7},{"bughandleStatus":1,"bughandleStatusTwoList":[{"bughandleStatusTwo":0,"count":2},{"bughandleStatusTwo":1,"count":1},{"bughandleStatusTwo":2,"count":1}],"count":4},{"bughandleStatus":2,"bughandleStatusTwoList":[{"bughandleStatusTwo":0,"count":2},{"bughandleStatusTwo":1,"count":13}],"count":15},{"bughandleStatus":3,"bughandleStatusTwoList":[{"bughandleStatusTwo":0,"count":1},{"bughandleStatusTwo":1,"count":5}],"count":6},{"bughandleStatus":4,"bughandleStatusTwoList":[{"bughandleStatusTwo":1,"count":1}],"count":1}],"failure":[{"count":0,"typeStr":"待修复"},{"count":540,"typeStr":"维修完成"},{"count":0,"typeStr":"遗留故障"}],"bussiness":[{"count":540,"typeStr":"电视监控"},{"count":0,"typeStr":"防盗报警"},{"count":0,"typeStr":"门禁、一卡通"},{"count":0,"typeStr":"可视对讲"},{"count":0,"typeStr":"公共广播"},{"count":0,"typeStr":"停车场"},{"count":0,"typeStr":"EAS"}],"five":[{"count":114,"repairCompany":"i","repairCompanyId":0},{"count":22,"repairCompany":"仔仔细细","repairCompanyId":"985770118343135234"},{"count":9,"repairCompany":"在人间","repairCompanyId":"981406915978862593"},{"count":8,"repairCompany":"褡裢坡烟酒连锁","repairCompanyId":"958589807934590978"},{"count":2,"repairCompany":"北京修监控科技","repairCompanyId":"993405421694017538"}],"device":[{"bussinessCode":"1.1","bussinessTwoCodeList":[{"count":387,"type":"1.1.1"},{"count":25,"type":"1.1.2"},{"count":1,"type":"1.1.3"},{"count":30,"type":"1.1.4"},{"count":8,"type":"1.1.5"}],"count":451},{"bussinessCode":"1.2","bussinessTwoCodeList":[{"count":10,"type":"1.2.1"},{"count":17,"type":"1.2.2"},{"count":2,"type":"1.2.3"},{"count":1,"type":"1.2.4"},{"count":2,"type":"1.2.5"}],"count":32},{"bussinessCode":"1.3","bussinessTwoCodeList":[{"count":2,"type":"1.3.1"},{"count":8,"type":"1.3.2"},{"count":2,"type":"1.3.3"},{"count":4,"type":"1.3.4"},{"count":2,"type":"1.3.5"}],"count":18},{"bussinessCode":"1.4","bussinessTwoCodeList":[{"count":3,"type":"1.4.1"},{"count":5,"type":"1.4.2"},{"count":9,"type":"1.4.3"}],"count":17},{"bussinessCode":"1.5","bussinessTwoCodeList":[{"count":1,"type":"1.5.1"},{"count":1,"type":"1.5.2"},{"count":5,"type":"1.5.3"},{"count":1,"type":"1.5.4"},{"count":1,"type":"1.5.5"}],"count":9},{"bussinessCode":"1.6","bussinessTwoCodeList":[{"count":1,"type":"1.6.2"},{"count":5,"type":"1.6.3"}],"count":6},{"bussinessCode":"1.7","bussinessTwoCodeList":[{"count":1,"type":"1.7.1"}],"count":1},{"bussinessCode":"3.11","bussinessTwoCodeList":[{"count":6,"type":"3.11.1"}],"count":6}]}
     * noticeCount : 7
     */


    private List<DataStatisticsBean.RepairBean> repair;
    private List<FailureBean> failure;
    private List<BussinessBean> bussiness;
    private List<FiveBean> five;
    private List<DeviceBean> device;

    public DataStatisticsBean(List<RepairBean> repair, List<FailureBean> failure, List<BussinessBean> bussiness, List<FiveBean> five, List<DeviceBean> device) {
        this.repair = repair;
        this.failure = failure;
        this.bussiness = bussiness;
        this.five = five;
        this.device = device;
    }

    public DataStatisticsBean() {
    }

    public List<DataStatisticsBean.RepairBean> getRepair() {
        return repair;
    }

    public void setRepair(List<DataStatisticsBean.RepairBean> repair) {
        this.repair = repair;
    }

    public List<FailureBean> getFailure() {
        return failure;
    }

    public void setFailure(List<FailureBean> failure) {
        this.failure = failure;
    }

    public List<BussinessBean> getBussiness() {
        return bussiness;
    }

    public void setBussiness(List<BussinessBean> bussiness) {
        this.bussiness = bussiness;
    }

    public List<FiveBean> getFive() {
        return five;
    }

    public void setFive(List<FiveBean> five) {
        this.five = five;
    }

    public List<DeviceBean> getDevice() {
        return device;
    }

    public void setDevice(List<DeviceBean> device) {
        this.device = device;
    }


    public static class RepairBean {
        /**
         * bughandleStatus : 0
         * bughandleStatusTwoList : [{"bughandleStatusTwo":0,"count":3},{"bughandleStatusTwo":1,"count":1},{"bughandleStatusTwo":3,"count":3}]
         * count : 7
         */

        private int bughandleStatus;
        private int count;
        private List<BughandleStatusTwoListBean> bughandleStatusTwoList;

        public int getBughandleStatus() {
            return bughandleStatus;
        }

        public void setBughandleStatus(int bughandleStatus) {
            this.bughandleStatus = bughandleStatus;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public List<BughandleStatusTwoListBean> getBughandleStatusTwoList() {
            return bughandleStatusTwoList;
        }

        public void setBughandleStatusTwoList(List<BughandleStatusTwoListBean> bughandleStatusTwoList) {
            this.bughandleStatusTwoList = bughandleStatusTwoList;
        }


        public static class BughandleStatusTwoListBean {
            /**
             * bughandleStatusTwo : 0
             * count : 3
             */

            private int bughandleStatusTwo;
            private int count;
            private int status;

            public int getBughandleStatusTwo() {
                return bughandleStatusTwo;
            }

            public void setBughandleStatusTwo(int bughandleStatusTwo) {
                this.bughandleStatusTwo = bughandleStatusTwo;
            }

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }
        }
    }

    public static class FailureBean {
        /**
         * count : 0
         * typeStr : 待修复
         */

        private int count;
        private String typeStr;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public String getTypeStr() {
            return typeStr;
        }

        public void setTypeStr(String typeStr) {
            this.typeStr = typeStr;
        }
    }

    public static class BussinessBean {
        /**
         * count : 540
         * typeStr : 电视监控
         */

        private int count;
        private String typeStr;


        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public String getTypeStr() {
            return typeStr;
        }

        public void setTypeStr(String typeStr) {
            this.typeStr = typeStr;
        }
    }

    public static class FiveBean {
        /**
         * count : 114
         * repairCompany : i
         * repairCompanyId : 0
         */

        private int count;
        private String repairCompany;
        private Long repairCompanyId;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public String getRepairCompany() {
            return repairCompany;
        }

        public void setRepairCompany(String repairCompany) {
            this.repairCompany = repairCompany;
        }

        public Long getRepairCompanyId() {
            return repairCompanyId;
        }

        public void setRepairCompanyId(Long repairCompanyId) {
            this.repairCompanyId = repairCompanyId;
        }
    }

    public static class DeviceBean {
        /**
         * bussinessCode : 1.1
         * bussinessTwoCodeList : [{"count":387,"type":"1.1.1"},{"count":25,"type":"1.1.2"},{"count":1,"type":"1.1.3"},{"count":30,"type":"1.1.4"},{"count":8,"type":"1.1.5"}]
         * count : 451
         */

        private String bussinessCode;
        private int num;
        private List<BussinessTwoCodeListBean> bussinessTwoCodeList;

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public String getBussinessCode() {
            return bussinessCode;
        }

        public void setBussinessCode(String bussinessCode) {
            this.bussinessCode = bussinessCode;
        }


        public List<BussinessTwoCodeListBean> getBussinessTwoCodeList() {
            return bussinessTwoCodeList;
        }

        public void setBussinessTwoCodeList(List<BussinessTwoCodeListBean> bussinessTwoCodeList) {
            this.bussinessTwoCodeList = bussinessTwoCodeList;
        }

        public static class BussinessTwoCodeListBean {
            /**
             * count : 387
             * type : 1.1.1
             */

            private int count;
            private String type;

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }
        }
    }
}
