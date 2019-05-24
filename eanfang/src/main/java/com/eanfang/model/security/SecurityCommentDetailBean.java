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
 * @data 2019/5/22
 * @description
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SecurityCommentDetailBean implements Serializable {
    private List<ListBean> list;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ListBean implements Serializable {

        private String asAccId;
        private String asCompanyId;
        private int asId;
        private String asTopCompanyId;
        private String asUserId;
        private CommentOrgBean commentOrg;
        private CommentUserBean commentUser;
        private String commentsAnswerAccId;
        private String commentsAnswerId;
        private String commentsCompanyId;
        private String commentsContent;
        private String commentsTopCompanyId;
        private String createTime;
        private int friend;
        private int id;
        private ParentCommentsEntityBean parentCommentsEntity;
        private int parentCommentsId;
        private int readStatus;
        private int status;
        private int topCommentsId;
        private int type;
        private int verifyStatus;

        @Getter
        @Setter
        @NoArgsConstructor
        @AllArgsConstructor
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

        @Getter
        @Setter
        @NoArgsConstructor
        @AllArgsConstructor
        public static class CommentUserBean implements Serializable {

            private String accId;
            private AccountEntity accountEntity;
            private boolean companyAdmin;
            private boolean superAdmin;
            private boolean sysAdmin;
            private String topCompanyId;
            private String userId;

        }

        @Getter
        @Setter
        @NoArgsConstructor
        @AllArgsConstructor
        public static class ParentCommentsEntityBean implements Serializable {

            private String asAccId;
            private String asCompanyId;
            private int asId;
            private String asTopCompanyId;
            private String asUserId;
            private CommentUserTwoBean commentUser;
            private String commentsAnswerAccId;
            private String commentsAnswerId;
            private String commentsCompanyId;
            private String commentsContent;
            private String commentsTopCompanyId;
            private String createTime;
            private int friend;
            private int id;
            private int parentCommentsId;
            private int readStatus;
            private int status;
            private int topCommentsId;
            private int type;
            private int verifyStatus;

            @Getter
            @Setter
            @NoArgsConstructor
            @AllArgsConstructor
            public static class CommentUserTwoBean implements Serializable {
                private String accId;
                private AccountEntity accountEntity;
                private boolean companyAdmin;
                private boolean superAdmin;
                private boolean sysAdmin;
                private String topCompanyId;
                private String userId;
            }
        }
    }
}
