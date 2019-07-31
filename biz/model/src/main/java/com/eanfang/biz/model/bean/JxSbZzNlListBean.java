package com.eanfang.biz.model.bean;

import java.util.List;

public class JxSbZzNlListBean {

    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "JxSbZzNlListBean{" +
                "list=" + list +
                '}';
    }

    public static class ListBean {
        @Override
        public String toString() {
            return "ListBean{" +
                    "company2baseDataEntity=" + company2baseDataEntity +
                    ", dataCode='" + dataCode + '\'' +
                    ", dataId=" + dataId +
                    ", dataName='" + dataName + '\'' +
                    ", dataType=" + dataType +
                    ", leaf=" + leaf +
                    ", level=" + level +
                    ", sortNum=" + sortNum +
                    '}';
        }

        /**
         * company2baseDataEntity : {"companyId":"986092404613361666","dataId":4677,"dataType":7,"id":6730,"remark":"11","status":0,"units":"人"}
         * dataCode : 7.1
         * dataId : 4677
         * dataName : 注册建造师
         * dataType : 7
         * leaf : true
         * level : 2
         * sortNum : 0
         */

        private Company2baseDataEntityBean company2baseDataEntity;
        private String dataCode;
        private int dataId;
        private String dataName;
        private int dataType;
        private boolean leaf;
        private int level;
        private int sortNum;

        public Company2baseDataEntityBean getCompany2baseDataEntity() {
            return company2baseDataEntity;
        }

        public void setCompany2baseDataEntity(Company2baseDataEntityBean company2baseDataEntity) {
            this.company2baseDataEntity = company2baseDataEntity;
        }

        public String getDataCode() {
            return dataCode;
        }

        public void setDataCode(String dataCode) {
            this.dataCode = dataCode;
        }

        public int getDataId() {
            return dataId;
        }

        public void setDataId(int dataId) {
            this.dataId = dataId;
        }

        public String getDataName() {
            return dataName;
        }

        public void setDataName(String dataName) {
            this.dataName = dataName;
        }

        public int getDataType() {
            return dataType;
        }

        public void setDataType(int dataType) {
            this.dataType = dataType;
        }

        public boolean isLeaf() {
            return leaf;
        }

        public void setLeaf(boolean leaf) {
            this.leaf = leaf;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public int getSortNum() {
            return sortNum;
        }

        public void setSortNum(int sortNum) {
            this.sortNum = sortNum;
        }

        public static class Company2baseDataEntityBean {
            @Override
            public String toString() {
                return "Company2baseDataEntityBean{" +
                        "companyId='" + companyId + '\'' +
                        ", dataId=" + dataId +
                        ", dataType=" + dataType +
                        ", id=" + id +
                        ", remark='" + remark + '\'' +
                        ", status=" + status +
                        ", units='" + units + '\'' +
                        '}';
            }

            /**
             * companyId : 986092404613361666
             * dataId : 4677
             * dataType : 7
             * id : 6730
             * remark : 11
             * status : 0
             * units : 人
             */

            private String companyId;
            private int dataId;
            private int dataType;
            private int id;
            private String remark;
            private int status;
            private String units;

            public String getCompanyId() {
                return companyId;
            }

            public void setCompanyId(String companyId) {
                this.companyId = companyId;
            }

            public int getDataId() {
                return dataId;
            }

            public void setDataId(int dataId) {
                this.dataId = dataId;
            }

            public int getDataType() {
                return dataType;
            }

            public void setDataType(int dataType) {
                this.dataType = dataType;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getUnits() {
                return units;
            }

            public void setUnits(String units) {
                this.units = units;
            }
        }
    }
}
