package com.eanfang.model.security;

import com.yaf.sys.entity.AccountEntity;
import com.yaf.sys.entity.OrgUnitEntity;
import com.yaf.sys.entity.UserEntity;

import java.io.Serializable;
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
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SecurityFoucsListBean implements Serializable {

    private List<ListBean> list;

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class ListBean implements Serializable {
        private AccountEntity accountEntity;
        private AskSpCircleEntityBean askSpCircleEntity;
        private int friend;
        private OrgUnitEntity orgUnitEntity;
        private UserEntity userEntity;

        @AllArgsConstructor
        @NoArgsConstructor
        @Getter
        @Setter
        public static class AskSpCircleEntityBean implements Serializable {
            private int collectionCount;
            private int commentCount;
            private String createTime;
            private int followCount;
            private int likesCount;
            private int readCount;
            private String spcContent;
            private String spcImg;

        }


    }
}
