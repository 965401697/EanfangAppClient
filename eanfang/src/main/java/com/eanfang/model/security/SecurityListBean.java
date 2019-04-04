package com.eanfang.model.security;

import com.yaf.sys.entity.AccountEntity;
import com.yaf.sys.entity.OrgUnitEntity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author guanluocang
 * @data 2019/2/13
 * @description
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SecurityListBean implements Serializable {

    private List<ListBean> list;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ListBean implements Serializable {
        private AccountEntity accountEntity;
        private int collectionCount;
        private int commentCount;
        private Date createTime;
        private String editTime;
        private int followCount;
        private int followsStatus;
        private int friend;
        private int likesCount;
        private int likeStatus;
        private String publisherCompanyName;
        private PublisherOrgBean publisherOrg;
        private String publisherOrgCode;
        private Long publisherUserId;
        private Long publisherCompanyId;
        private Long publisherTopCompanyId;
        private PublisherUserBean publisherUser;
        private int readCount;
        private String spcContent;
        private Long spcId;
        private String spcImg;
        private int verifyStatus;
        private String questionContent;
        private String questionId;
        private String type;
        private String spcVideo;
        private CountMapBean countMap;
        private int readStatus;

        @Getter
        @Setter
        @AllArgsConstructor
        @NoArgsConstructor
        public static class PublisherOrgBean implements Serializable {

            private Long companyId;
            private int countStaff;
            private int level;
            private String orgCode;
            private Long orgId;
            private String orgName;
            private OrgUnitEntity orgUnitEntity;
            private Long topCompanyId;
        }

        @Getter
        @Setter
        @AllArgsConstructor
        @NoArgsConstructor
        public static class PublisherUserBean implements Serializable {
            private Long accId;
            private boolean companyAdmin;
            private boolean superAdmin;
            private boolean sysAdmin;
            private Long topCompanyId;
            private Long userId;

        }

        @Getter
        @Setter
        @AllArgsConstructor
        @NoArgsConstructor
        public static class CountMapBean implements Serializable {
            private int noReadCount;
            private int commentNoRead;
        }
    }
}
