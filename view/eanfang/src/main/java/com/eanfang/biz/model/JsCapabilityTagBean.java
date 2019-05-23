package com.eanfang.biz.model;

import java.util.List;

public class JsCapabilityTagBean {

    private List<Integer> expand;
    private List<ListBean> list;

    public List<Integer> getExpand() {
        return expand;
    }

    public void setExpand(List<Integer> expand) {
        this.expand = expand;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "JsCapabilityTagBean{" +
                "expand=" + expand +
                ", list=" + list +
                '}';
    }

    public static class ListBean {
        /**
         * companyId : 0
         * dataId : 4690
         * dataType : 9
         * id : 937965926068820445
         * userId : 979993412327751682
         */

        private int companyId;
        private int dataId;
        private int dataType;
        private String id;
        private String dataName;
        private String userId;

        public String getDataName() {
            return dataName;
        }

        public void setDataName(String dataName) {
            this.dataName = dataName;
        }

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

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        @Override
        public String toString() {
            return "ListBean{" +
                    "companyId=" + companyId +
                    ", dataId=" + dataId +
                    ", dataType=" + dataType +
                    ", id='" + id + '\'' +
                    ", dataName='" + dataName + '\'' +
                    ", userId='" + userId + '\'' +
                    '}';
        }
    }
}
