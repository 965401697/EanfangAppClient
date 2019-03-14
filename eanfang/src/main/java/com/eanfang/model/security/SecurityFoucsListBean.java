package com.eanfang.model.security;

import com.yaf.sys.entity.AccountEntity;
import com.yaf.sys.entity.OrgUnitEntity;
import com.yaf.sys.entity.UserEntity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author guanluocang
 * @data 2019/2/19
 * @description
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SecurityFoucsListBean implements Serializable {

    private List<SecurityFoucsListBean.ListBean> list;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ListBean implements Serializable {

        private AccountEntity accountEntity;
        private AskSpCircleEntityBean askSpCircleEntity;
        private int followsStatus;
        private int friend;
        private OrgUnitEntity orgUnitEntity;
        private UserEntity userEntity;

        @Getter
        @Setter
        @AllArgsConstructor
        @NoArgsConstructor
        public static class AskSpCircleEntityBean implements Serializable {

            private int collectionCount;
            private int commentCount;
            private Date createTime;
            private String editTime;
            private int followCount;
            private int followsStatus;
            private int friend;
            private int likeStatus;
            private int likesCount;
            private Long publisherCompanyId;
            private String publisherOrgCode;
            private Long publisherTopCompanyId;
            private Long publisherUserId;
            private int readCount;
            private String spcContent;
            private Long spcId;
            private String spcImg;
            private int verifyStatus;

        }

    }
}
