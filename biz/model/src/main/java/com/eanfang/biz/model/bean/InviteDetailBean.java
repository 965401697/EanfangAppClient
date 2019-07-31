package com.eanfang.biz.model.bean;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author liangkailun
 * Date ：2019/5/13
 * Describe :
 */
@NoArgsConstructor
@Data
public class InviteDetailBean {
    /**
     * currPage : 1
     * list : [{"accId":"979985982284677121","accountNo":"15610251236","accountPhone":"15610251236","editTime":"2019-05-15 17:02:38","id":1,"moneyType":1,"payType":1,"realName":"管理员","withdrawalMoney":6,"withdrawalStatus":0},{"accId":"979985982284677121","accountNo":"15610251236","accountPhone":"15610251236","editTime":"2019-05-15 17:02:46","id":2,"moneyType":1,"payType":1,"realName":"管理员","withdrawalMoney":4,"withdrawalStatus":0}]
     * pageSize : 2147483647
     * totalCount : 2
     * totalPage : 1
     */

    private int currPage;
    private int pageSize;
    private int totalCount;
    private int totalPage;
    private List<ListBean> list;

    @NoArgsConstructor
    @Data
    public static class ListBean {
        /**
         * accId : 979985982284677121
         * accountNo : 15610251236
         * accountPhone : 15610251236
         * editTime : 2019-05-15 17:02:38
         * id : 1
         * moneyType : 1
         * payType : 1
         * realName : 管理员
         * withdrawalMoney : 6
         * withdrawalStatus : 0
         */

        private String accId;
        private String accountNo;
        private String accountPhone;
        private String editTime;
        private int id;
        private int moneyType;
        private int payType;
        private String realName;
        private int withdrawalMoney;
        private int withdrawalStatus;

        /**
         * 金额
         */
        private int bountyAmount;
        /**
         * 创建时间
         */
        private String createTime;
        private int detailedCategory;
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
