package com.eanfang.bean.security;

import com.eanfang.R;
import com.eanfang.biz.model.entity.AccountEntity;
import com.eanfang.biz.model.entity.OrgUnitEntity;
import com.eanfang.witget.mentionedittext.edit.modle.FormatRange;
import com.eanfang.witget.mentionedittext.edit.mylistener.InsertData;

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
        private int type;
        private String spcVideo;
        private CountMapBean countMap;
        private int readStatus;
        private HashMap<Long, String> atMap;

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

        @NoArgsConstructor
        @AllArgsConstructor
        @Getter
        @Setter
        public static class PublisherUserBean implements Serializable, InsertData {
            private Long accId;
            private boolean companyAdmin;
            private boolean superAdmin;
            private boolean sysAdmin;
            private Long topCompanyId;
            private AccountEntity accountEntity;
            private CharSequence userId;
            private Long mLUseId;
            private String mSUserName;


            @Override
            public boolean equals(Object o) {
                if (this == o) {
                    return true;
                }
                if (o == null || getClass() != o.getClass()) {
                    return false;
                }
                PublisherUserBean user = (PublisherUserBean) o;

                if (userId != null ? !userId.equals(user.userId) : user.userId != null) {
                    return false;
                }
                return accountEntity.getRealName() != null ? !accountEntity.getRealName().equals(user.getAccountEntity().getRealName()) : user.getAccountEntity().getRealName() != null;
            }

            @Override
            public int hashCode() {
                int result = userId != null ? userId.hashCode() : 0;
                result = 31 * result + (getAccountEntity().getRealName() != null ? getAccountEntity().getRealName().hashCode() : 0);
                return result;
            }

            @Override
            public CharSequence charSequence() {
                return "@" + accountEntity.getRealName();
            }

            @Override
            public FormatRange.FormatData formatData() {
                return new PublisherConvert(this);
            }

            @Override
            public int color() {
                return R.color.colorPrimary;
            }

            private class PublisherConvert implements FormatRange.FormatData {

                public static final String USER_FORMART = "<user id='%s' name = '%s'></user>";
                private final PublisherUserBean accountEntityBean;

                public PublisherConvert(PublisherUserBean mAccountEntityBean) {
                    this.accountEntityBean = mAccountEntityBean;
                }

                @Override
                public CharSequence formatCharSequence() {
                    return String.format(USER_FORMART, accountEntityBean.getAccId());
                }
            }
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
