package com.eanfang.model;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by MrHou
 *
 * @on 2018/1/24  22:47
 * @email houzhongzhou@yeah.net
 * @desc
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignListBean implements Serializable {

    private String signDay;
    private List<ListBean> list;

    @Getter
    @Setter
    public static class ListBean implements Serializable {

        private CompanyBean company;
        private String createCompanyId;
        private String createOrgCode;
        private String createTime;
        private String createTopCompanyId;
        private CreateUserBean createUser;
        private String createUserId;
        private String detailPlace;
        private String id;
        private String latitude;
        private String longitude;
        private String pictures;
        private String remarkInfo;
        private String signDay;
        private String signTime;
        private int status;
        private int sum;
        private String visitorName;
        private String zoneCode;

        @Getter
        @Setter
        public static class CompanyBean implements Serializable {

            private String adminUserId;
            private String companyId;
            private int countStaff;
            private int level;
            private String orgCode;
            private String orgId;
            private String orgName;
            private int orgType;
            private int parentOrgId;
            private int sortNum;
            private String topCompanyId;
            private String updateTime;
            private String updateUser;
            private int verifyStatus;

        }

        @Getter
        @Setter
        public static class CreateUserBean implements Serializable {

            private String accId;
            private AccountEntityBean accountEntity;
            private boolean companyAdmin;
            private boolean superAdmin;
            private boolean sysAdmin;
            private String topCompanyId;
            private String userId;

            @Getter
            @Setter
            public static class AccountEntityBean implements Serializable {

                private String accId;
                private int accType;
                private String avatar;
                private String mobile;
                private String realName;

            }
        }
    }

}

