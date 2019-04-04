package com.eanfang.model.security;

import com.yaf.base.entity.WorkerEntity;
import com.yaf.sys.entity.AccountEntity;
import com.yaf.sys.entity.UserEntity;

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

        private AccountEntity accountEntity;
        private String asUserId;
        private AskSpCircleEntityBean askSpCircleEntity;
        private int followsStatus;
        private int friend;
        private OrgEntityBean orgEntity;
        private UserEntity userEntity;
        private WorkerEntity workerEntity;

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
