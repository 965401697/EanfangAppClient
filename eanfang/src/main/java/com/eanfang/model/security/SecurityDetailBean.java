package com.eanfang.model.security;

import com.yaf.sys.entity.AccountEntity;

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

    private List<SecurityDetailBean.ListBean> list;

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class ListBean implements Serializable {

        private int asCompanyId;
        private int asId;
        private int asTopCompanyId;
        private String asUserId;
        private CommentUserBean commentUser;
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
    }
}
