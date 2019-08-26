package com.eanfang.biz.model.bean;

import java.util.List;

public class SgZzNlBean {

    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "SgZzNlBean{" +
                "list=" + list +
                '}';
    }

    public static class ListBean {
        /**
         * baseDataEntity : {"dataCode":"7.1","dataId":4677,"dataName":"注册建造师","dataType":7,"leaf":true,"level":2}
         * companyId : 1115176769342722000
         * dataId : 4677
         * dataType : 7
         * id : 6723
         * remark : 22
         * status : 0
         * units : 人
         */

        private BaseDataEntityBean baseDataEntity;
        private String companyId;
        private int dataId;
        private int dataType;
        private int id;
        private String remark;
        private String dataName;
        private int status;
        private String units;

        @Override
        public String toString() {
            return "ListBean{" +
                    "baseDataEntity=" + baseDataEntity +
                    ", companyId='" + companyId + '\'' +
                    ", dataId=" + dataId +
                    ", dataType=" + dataType +
                    ", id=" + id +
                    ", remark='" + remark + '\'' +
                    ", status=" + status +
                    ", units='" + units + '\'' +
                    '}';
        }

        public BaseDataEntityBean getBaseDataEntity() {
            return baseDataEntity;
        }

        public void setBaseDataEntity(BaseDataEntityBean baseDataEntity) {
            this.baseDataEntity = baseDataEntity;
        }

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

        public String getDataName() {
            return dataName;
        }

        public void setDataName(String dataName) {
            this.dataName = dataName;
        }

        public static class BaseDataEntityBean {
            @Override
            public String toString() {
                return "BaseDataEntityBean{" +
                        "dataCode='" + dataCode + '\'' +
                        ", dataId=" + dataId +
                        ", dataName='" + dataName + '\'' +
                        ", dataType=" + dataType +
                        ", leaf=" + leaf +
                        ", level=" + level +
                        '}';
            }

            /**
             * dataCode : 7.1
             * dataId : 4677
             * dataName : 注册建造师
             * dataType : 7
             * leaf : true
             * level : 2
             */

            private String dataCode;
            private int dataId;
            private String dataName;
            private int dataType;
            private boolean leaf;
            private int level;

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
        }
    }
}
