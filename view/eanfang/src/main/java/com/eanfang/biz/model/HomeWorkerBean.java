package com.eanfang.biz.model;

import com.eanfang.biz.model.security.HomeCompanyBean;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author liangkailun
 * Date ：2019-06-20
 * Describe :找技师网络请求
 */
@NoArgsConstructor
@Data
public class HomeWorkerBean {

    private int currPage;
    private int pageSize;
    private int totalCount;
    private int totalPage;
    private List<ListBean> list;

    @NoArgsConstructor
    @Data
    public static class ListBean {

        private String accId;
        private AccountEntityBean accountEntity;
        private CompanyEntityBean companyEntity;
        private String companyUserId;
        private int designNum;
        private int evaluateNum;
        private int goodRate;
        private double goodReputation;
        private int id;
        private int installNum;
        private int item1;
        private int item2;
        private int item3;
        private int item4;
        private int item5;
        private String lat;
        private String lon;
        private String placeCode;
        private int publicPraise;
        private int qualification;
        private int repairCount;
        private int trainStatus;
        private VerifyEntityBean verifyEntity;
        private String verifyId;
        private HomeCompanyBean mHomeCompanyBean;

        public HomeCompanyBean getHomeCompanyBean(){
            mHomeCompanyBean = new HomeCompanyBean();
            mHomeCompanyBean.setAreaCode(getPlaceCode());
            mHomeCompanyBean.setDesignCount(getDesignNum());
            mHomeCompanyBean.setGoodRate(getGoodRate());
            mHomeCompanyBean.setInstallCount(getInstallNum());
            if (getAccountEntity() != null) {
                mHomeCompanyBean.setLogoPic(getAccountEntity().getAvatar());
                mHomeCompanyBean.setName(getAccountEntity().getRealName());
            }
            mHomeCompanyBean.setRepairCount(getRepairCount());
            if (getVerifyEntity() != null) {
                mHomeCompanyBean.setLevel(getVerifyEntity().getWorkingLevel());
                mHomeCompanyBean.setPractitionerYears(getVerifyEntity().getWorkingYear());
            }
            mHomeCompanyBean.setPageType(1);
            mHomeCompanyBean.setId(id);
            mHomeCompanyBean.setCompanyUserId(companyUserId);
            return mHomeCompanyBean;
        }

        @NoArgsConstructor
        @Data
        public static class AccountEntityBean {
            /**
             * accId : 979993810832769026
             * address : 朝阳大悦城青年路1号
             * areaCode : 3.11.1.5
             * avatar : device/980015764053475330/14.1.jpg
             * mobile : 15632908792
             * realName : 孙亚伟
             * simplePwd : false
             */

            private String accId;
            private String address;
            private String areaCode;
            private String avatar;
            private String mobile;
            private String realName;
            private boolean simplePwd;
        }

        @NoArgsConstructor
        @Data
        public static class CompanyEntityBean {
            /**
             * countStaff : 0
             * level : 0
             * orgId : 979995434422681602
             * orgName : 北京法安视科技有限公司
             * topCompanyId : 979995434422681602
             */

            private int countStaff;
            private int level;
            private String orgId;
            private String orgName;
            private String topCompanyId;
        }

        @NoArgsConstructor
        @Data
        public static class VerifyEntityBean {
            /**
             * accId : 979993810832769026
             * avatarPhoto : account/c71d9278e25a4b91aa8611f8a75b19c3.png
             * userId : 980025258942709762
             * verifyTime : 2018-03-31 16:26:09
             * workingLevel : 3
             * workingYear : 3
             */

            private String accId;
            private String avatarPhoto;
            private String userId;
            private String verifyTime;
            private int workingLevel;
            private int workingYear;
        }
    }
}
