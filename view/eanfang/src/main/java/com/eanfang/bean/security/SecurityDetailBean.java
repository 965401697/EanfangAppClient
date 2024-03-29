package com.eanfang.bean.security;

import com.eanfang.biz.model.entity.AccountEntity;
import com.eanfang.biz.model.entity.OrgUnitEntity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
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

    private PageUtilBean pageUtil;
    private SpcListBean spcList;

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class PageUtilBean implements Serializable {

        private List<ListBean> list;

        @AllArgsConstructor
        @NoArgsConstructor
        @Getter
        @Setter
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
            private int readStatus;
            private int replyCount;
            private int status;
            private int type;
            private int verifyStatus;
            private List<CommentsEntityListBean> commentsEntityList;

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
            public static class CommentsEntityListBean implements Serializable {

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

                @AllArgsConstructor
                @NoArgsConstructor
                @Getter
                @Setter
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

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class SpcListBean implements Serializable {
        private int readCount;
        private int commentCount;
        private AccountEntity accountEntity;
        private HashMap<Long, String> atMap;
        private String atUserId;
        private int collectionCount;
        private CommentsEntityBean commentsEntity;
        private Date createTime;
        private int followCount;
        private int followsStatus;
        private int friend;
        private int likeStatus;
        private int likesCount;
        private Long publisherCompanyId;
        private PublisherOrgBean publisherOrg;
        private Long publisherTopCompanyId;
        private PublisherUserBean publisherUser;
        private Long publisherUserId;
        private Long publisherAccId;
        private int readStatus;
        private String spcContent;
        private int spcId;
        private String spcImg;
        private String spcVideo;
        private int type;
        private int verifyStatus;
        private WorkerEntityBean workerEntity;

        @AllArgsConstructor
        @NoArgsConstructor
        @Getter
        @Setter
        public static class CommentsEntityBean implements Serializable {

            private String createTime;
            private int friend;
            private int readStatus;
            private int type;
            private int verifyStatus;

        }

        @AllArgsConstructor
        @NoArgsConstructor
        @Getter
        @Setter
        public static class PublisherOrgBean implements Serializable {

            private int countStaff;
            private int level;
            private String orgName;
            private int verifyStatus;
        }

        @AllArgsConstructor
        @NoArgsConstructor
        @Getter
        @Setter
        public static class PublisherUserBean implements Serializable {

            private Long accId;
            private boolean companyAdmin;
            private String createTime;
            private boolean superAdmin;
            private boolean sysAdmin;

        }

        @AllArgsConstructor
        @NoArgsConstructor
        @Getter
        @Setter
        public static class WorkerEntityBean implements Serializable {
            private String accId;
            private int verifyStatus;
        }
    }


}
