package com.eanfang.bean.security;

import com.eanfang.biz.model.entity.AccountEntity;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author guanluocang
 * @data 2019/3/25
 * @description 安防圈评论列表
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SecurityCommentListBean implements Serializable {

    private List<ListBean> list;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ListBean implements Serializable {

        private AccountEntity accountEntity;
        private CommentsEntityBean askSpCircleEntity;
        private String createTime;
        private String commentsContent;
        private int readStatus;


        @Getter
        @Setter
        @NoArgsConstructor
        @AllArgsConstructor
        public static class CommentsEntityBean implements Serializable {
            private String createTime;
            private String spcContent;
            private int spcId;
            private int type;
        }

    }
}
