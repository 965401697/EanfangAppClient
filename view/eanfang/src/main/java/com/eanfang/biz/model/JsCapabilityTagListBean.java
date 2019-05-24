package com.eanfang.biz.model;

import java.util.List;

public class JsCapabilityTagListBean {
    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "JsCapabilityTagListBean{" +
                "list=" + list +
                '}';
    }

    public static class ListBean {
        @Override
        public String toString() {
            return "ListBean{" +
                    "baseData2userEntity=" + baseData2userEntity +
                    ", dataCode='" + dataCode + '\'' +
                    ", dataId=" + dataId +
                    ", dataName='" + dataName + '\'' +
                    ", dataType=" + dataType +
                    ", leaf=" + leaf +
                    ", level=" + level +
                    ", selected=" + selected +
                    ", sortNum=" + sortNum +
                    '}';
        }

        /**
         * baseData2userEntity : {"companyId":0,"dataId":4689,"dataType":9,"userId":"979993412327751682"}
         * dataCode : 9.1
         * dataId : 4689
         * dataName : 项目管理
         * dataType : 9
         * leaf : true
         * level : 2
         * selected : 1
         * sortNum : 0
         */

        private BaseData2userEntityBean baseData2userEntity;
        private String dataCode;
        private int dataId;
        private String dataName;
        private int dataType;
        private boolean leaf;
        private int level;
        private boolean selected;
        private int sortNum;

        public BaseData2userEntityBean getBaseData2userEntity() {
            return baseData2userEntity;
        }

        public void setBaseData2userEntity(BaseData2userEntityBean baseData2userEntity) {
            this.baseData2userEntity = baseData2userEntity;
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

        public boolean isSelected() {
            return selected;
        }

        public void setSelected(boolean selected) {
            this.selected = selected;
        }

        public int getSortNum() {
            return sortNum;
        }

        public void setSortNum(int sortNum) {
            this.sortNum = sortNum;
        }

        public static class BaseData2userEntityBean {
            /**
             * companyId : 0
             * dataId : 4689
             * dataType : 9
             * userId : 979993412327751682
             */

            private int companyId;
            private int dataId;
            private int dataType;
            private String userId;

            public int getCompanyId() {
                return companyId;
            }

            public void setCompanyId(int companyId) {
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

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

            @Override
            public String toString() {
                return "BaseData2userEntityBean{" +
                        "companyId=" + companyId +
                        ", dataId=" + dataId +
                        ", dataType=" + dataType +
                        ", userId='" + userId + '\'' +
                        '}';
            }
        }
    }
}
