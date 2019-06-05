package com.eanfang.model;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author liangkailun
 * Date ：2019-05-20
 * Describe :
 */
@NoArgsConstructor
@Data
public class InviteDetailBean2 {

    private int currPage;
    private int pageSize;
    private int totalCount;
    private int totalPage;
    private List<ListBean> list;

    @NoArgsConstructor
    @Data
    public static class ListBean {
        /**
         * bountyAmount : 300
         * createTime : 2019-05-16 10:48:59
         * detailedCategory : 2
         * id : 1
         * inviteAccId : 979993411866378241
         * openStatus : 0
         * registerAccId : 979993411866378242
         * updateTime : 2019-05-16 10:48:54
         */

        /**
         * 金额
         */
        private int bountyAmount;
        /**
         * 创建时间
         */
        private String createTime;
        private int detailedCategory;
        private int id;
        private String inviteAccId;
        /**
         * 电话号码
         */
        private String inviteMobile;
        private int openStatus;
        private String registerAccId;
        private String updateTime;
    }
}
