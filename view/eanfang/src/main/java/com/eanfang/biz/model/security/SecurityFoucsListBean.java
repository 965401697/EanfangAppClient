package com.eanfang.biz.model.security;

import com.eanfang.R;
import com.eanfang.biz.model.entity.AccountEntity;
import com.eanfang.witget.mentionedittext.edit.modle.FormatRange;
import com.eanfang.witget.mentionedittext.edit.mylistener.InsertData;
import com.yaf.base.entity.WorkerEntity;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author guanluocang
 * @data 2019/4/3
 * @description 安防圈关注人列表
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SecurityFoucsListBean implements Serializable {
    private List<ListBean> list;

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class ListBean {

        private String asUserId;
        private AskSpCircleEntityBean askSpCircleEntity;
        private int followsStatus;
        private int friend;
        private OrgEntityBean orgEntity;
        private UserEntityBean userEntity;
        private WorkerEntity workerEntity;

        @NoArgsConstructor
        @AllArgsConstructor
        @Getter
        @Setter
        public static class UserEntityBean implements Serializable, InsertData {
            private AccountEntity accountEntity;
            private CharSequence userId;
            private Long mLUseId;
            private String mSUserName;

            public UserEntityBean(Long mUsrId, String mUserName) {
                this.mSUserName = mUserName;
                this.mLUseId = mUsrId;
            }

            @Override
            public boolean equals(Object o) {
                if (this == o) {
                    return true;
                }
                if (o == null || getClass() != o.getClass()) {
                    return false;
                }
                UserEntityBean user = (UserEntityBean) o;

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
                return new UserEntityConvert(this);
            }

            @Override
            public int color() {
                return R.color.colorPrimary;
            }

            private class UserEntityConvert implements FormatRange.FormatData {

                public static final String USER_FORMART = "<user id='%s'></user>";
                private final UserEntityBean accountEntityBean;

                public UserEntityConvert(UserEntityBean mAccountEntityBean) {
                    this.accountEntityBean = mAccountEntityBean;
                }

                @Override
                public CharSequence formatCharSequence() {
                    Long accId = -1L;
                    if (accountEntity != null){
                        accId = accountEntity.getAccId();
                    }
                    return String.format(USER_FORMART, String.valueOf(accId));
                }
            }
        }

        @NoArgsConstructor
        @AllArgsConstructor
        @Getter
        @Setter
        public static class AskSpCircleEntityBean {
            private Long publisherUserId;
            private Long publisherCompanyId;
            private Long publisherTopCompanyId;
            private int followsStatus;
            private int verifyStatus;
        }

        @NoArgsConstructor
        @AllArgsConstructor
        @Getter
        @Setter
        public static class OrgEntityBean {
            private String orgName;
        }
    }

}
