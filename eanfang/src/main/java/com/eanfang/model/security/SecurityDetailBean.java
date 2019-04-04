package com.eanfang.model.security;

import com.yaf.sys.entity.AccountEntity;
import com.yaf.sys.entity.OrgUnitEntity;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * @author guanluocang
 * @data 2019/2/27
 * @description
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SecurityDetailBean implements Serializable {


    private List<ListBean> list;
    private List<SpcListBean> spcList;

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class ListBean implements Serializable {

        private String asCompanyId;
        private int asId;
        private String asTopCompanyId;
        private String asUserId;
        private CommentUserBean commentUser;
        private CommentOrgBean commentOrg;
        private String commentsAnswerId;
        private String commentsCompanyId;
        private String commentsContent;
        private String commentsImg;
        private String commentsTopCompanyId;
        private String createTime;
        private int friend;
        private int id;
        private int status;
        private int type;
        private int verifyStatus;

        @AllArgsConstructor
        @NoArgsConstructor
        @Getter
        @Setter
        public static class CommentUserBean implements Serializable {

            private String accId;
            private AccountEntity accountEntity;
            private boolean companyAdmin;
            private boolean superAdmin;
            private boolean sysAdmin;
            private String topCompanyId;
            private String userId;

        }

        @AllArgsConstructor
        @NoArgsConstructor
        @Getter
        @Setter
        public static class CommentOrgBean implements Serializable {

            private String companyId;
            private int countStaff;
            private int level;
            private String orgCode;
            private String orgId;
            private String orgName;
            private OrgUnitEntity orgUnitEntity;
            private String topCompanyId;

        }
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class SpcListBean {
        private int readCount;
        private int commentCount;

    }
}