package com.eanfang.model;

/**
 * @author WQ
 */
public class BusinessManagementData {
    /**
     * cacheKey : 32a7eb5fd783dbf9aa9626c57e6c37ca
     * code : 20000
     * data : {"logoPic":"default/default_avatar.png","name":"538666245","orgId":"1115902668586598402","status":0,"unitType":3}
     * noticeCount : 0
     */

    private String cacheKey;
    private int code;
    private DataBean data;
    private int noticeCount;

    public String getCacheKey() {
        return cacheKey;
    }

    public void setCacheKey(String cacheKey) {
        this.cacheKey = cacheKey;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getNoticeCount() {
        return noticeCount;
    }

    public void setNoticeCount(int noticeCount) {
        this.noticeCount = noticeCount;
    }

    @Override
    public String toString() {
        return "BusinessManagementData{" +
                "cacheKey='" + cacheKey + '\'' +
                ", code=" + code +
                ", data=" + data +
                ", noticeCount=" + noticeCount +
                '}';
    }

    public static class DataBean {
        @Override
        public String toString() {
            return "DataBean{" +
                    "logoPic='" + logoPic + '\'' +
                    ", name='" + name + '\'' +
                    ", orgId='" + orgId + '\'' +
                    ", status=" + status +
                    ", unitType=" + unitType +
                    '}';
        }

        /**
         * logoPic : default/default_avatar.png
         * name : 538666245
         * orgId : 1115902668586598402
         * status : 状态:  0草稿 1认证中，2认证通过，3认证拒绝，4禁用/删除
         * unitType : 单位类型: 0平台总公司 1城市平台公司 2企事业单位 3安防公司
         */

        private String logoPic;
        private String name;
        private String intro;
        private String orgId;
        private int status;
        private int unitType;

        public String getIntro() {
            return intro;
        }

        public void setIntro(String intro) {
            this.intro = intro;
        }

        public String getLogoPic() {
            return logoPic;
        }

        public void setLogoPic(String logoPic) {
            this.logoPic = logoPic;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getOrgId() {
            return orgId;
        }

        public void setOrgId(String orgId) {
            this.orgId = orgId;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getUnitType() {
            return unitType;
        }

        public void setUnitType(int unitType) {
            this.unitType = unitType;
        }
    }
}
