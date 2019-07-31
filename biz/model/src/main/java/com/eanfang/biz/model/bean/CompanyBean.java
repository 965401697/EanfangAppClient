package com.eanfang.biz.model.bean;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author liangkailun
 * Date ：2019-06-18
 * Describe :安防公司网络请求
 */
@NoArgsConstructor
@Data
public class CompanyBean {

    private int currPage;
    private int pageSize;
    private int totalCount;
    private int totalPage;
    private List<ListBean> list;

    @NoArgsConstructor
    @Data
    public static class ListBean {

        private String adminUserId;
        private CompanyEntityBean companyEntity;
        private String createTime;
        private int designCount;
        private int goodRate;
        private int goodReputation;
        private int installCount;
        private int item1;
        private int item2;
        private int item3;
        private int item4;
        private int item5;
        private OrgEntityBean orgEntity;
        private String orgId;
        private int publicPraise;
        private int receiveCount;
        private int repairCount;
        private int workingLevel;
        private int workingYear;
        private List<BaseDataEntityListBean> baseDataEntityList;

        @NoArgsConstructor
        @Data
        public static class CompanyEntityBean {
            /**
             * adminUserId : 1086179092560191490
             * areaCode : 3.11.1.5
             * createTime : 2019-01-18 16:30:51
             * intro : 啊啊啊啊
             * legalName : 几
             * licenseCode : 223
             * licensePic : org/01e0480700714020881693a854e60f0d.png
             * logoPic : org/a49d8378490f4ae7b30db66ce8feb433.png
             * name : 测试技师公司1
             * officeAddress : 中润通财富(朝阳北路)
             * orgId : 1086179092560191489
             * registerAssets : 123
             * scale : 0
             * status : 2
             * telPhone : 123
             * tradeTypeCode : 4.1.1
             * unitType : 3
             * verifyMessage :
             * verifyTime : 2019-01-18 16:32:48
             * verifyUserName : 成虎测试
             */

            private String adminUserId;
            private String areaCode;
            private String createTime;
            private String intro;
            private String legalName;
            private String licenseCode;
            private String licensePic;
            private String logoPic;
            private String name;
            private String officeAddress;
            private String orgId;
            private String registerAssets;
            private int scale;
            private int status;
            private String telPhone;
            private String tradeTypeCode;
            private int unitType;
            private String verifyMessage;
            private String verifyTime;
            private String verifyUserName;
        }

        @NoArgsConstructor
        @Data
        public static class OrgEntityBean {
            /**
             * adminUserId : 1086179092560191490
             * countStaff : 0
             * level : 0
             * orgId : 1086179092560191489
             */

            private String adminUserId;
            private int countStaff;
            private int level;
            private String orgId;
        }

        @NoArgsConstructor
        @Data
        public static class BaseDataEntityListBean {
            /**
             * dataCode : 2.1
             * dataId : 31
             * dataName : 维修
             * dataType : 2
             * leaf : true
             * level : 2
             * remarkInfo :
             * sortNum : 0
             */

            private String dataCode;
            private int dataId;
            private String dataName;
            private int dataType;
            private boolean leaf;
            private int level;
            private String remarkInfo;
            private int sortNum;
        }
    }
}
