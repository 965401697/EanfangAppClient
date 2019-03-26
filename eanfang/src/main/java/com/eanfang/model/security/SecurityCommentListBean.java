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
 * @data 2019/3/25
 * @description 安防圈评论列表
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SecurityCommentListBean implements Serializable {


    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ListBean {

        private AskSpCircleEntityBean askSpCircleEntity;
        private AccountEntity accountEntity;
        private String commentsContent;
        private String createTime;

        @Getter
        @Setter
        @NoArgsConstructor
        @AllArgsConstructor
        public static class AskSpCircleEntityBean {
            private String createTime;
            private String spcContent;

        }
    }
}
