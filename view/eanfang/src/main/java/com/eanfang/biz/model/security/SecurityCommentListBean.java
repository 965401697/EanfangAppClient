package com.eanfang.biz.model.security;

import com.eanfang.biz.model.entity.AccountEntity;
import com.yaf.base.entity.WorkerEntity;

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
    public static class ListBean implements Serializable{

        private AccountEntity accountEntity;
        private CommentsEntityBean commentsEntity;
        private String createTime;
        private PublisherUserBean publisherUser;
        private String spcContent;
        private int readStatus;
        private int spcId;
        private int type;
        private WorkerEntity workerEntity;

        @Getter
        @Setter
        @NoArgsConstructor
        @AllArgsConstructor
        public static class CommentsEntityBean implements Serializable{

            private String commentsContent;
            private String createTime;
            private int type;
        }

        @Getter
        @Setter
        @NoArgsConstructor
        @AllArgsConstructor
        public static class PublisherUserBean implements Serializable{

            private String accId;
            private boolean companyAdmin;
            private String createTime;
            private boolean superAdmin;
            private boolean sysAdmin;

        }

    }
}
