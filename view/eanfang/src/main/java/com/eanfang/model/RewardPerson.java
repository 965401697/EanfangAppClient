package com.eanfang.model;


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
public class RewardPerson {

    /**
     * currPage : 1
     * list : [{"accId":"979985982284677121","accountNo":"15610251236","accountPhone":"15610251236","editTime":"2019-05-15 17:02:38","id":1,"moneyType":1,"payType":1,"realName":"管理员","withdrawalMoney":6,"withdrawalStatus":0},{"accId":"979985982284677121","accountNo":"15610251236","accountPhone":"15610251236","editTime":"2019-05-15 17:02:46","id":2,"moneyType":1,"payType":1,"realName":"管理员","withdrawalMoney":4,"withdrawalStatus":0},{"accId":"1033177203777540097","accountNo":"15600214587","accountPhone":"15600214587","editTime":"2019-05-14 16:38:16","id":3,"moneyType":1,"payType":2,"realName":"hehe","withdrawalMoney":6,"withdrawalStatus":0},{"accId":"979993411866378241","accountNo":"15600279979","accountPhone":"15600279977","editTime":"2019-05-14 16:38:17","id":12,"moneyType":0,"payType":0,"realName":"test1","withdrawalMoney":900,"withdrawalStatus":0},{"accId":"979993411866378241","accountNo":"15600279979","accountPhone":"15600279977","editTime":"2019-05-16 11:02:09","id":18,"moneyType":0,"payType":0,"realName":"test1","withdrawalMoney":300,"withdrawalStatus":0},{"accId":"979993411866378241","accountNo":"15600279979","accountPhone":"15600279977","editTime":"2019-05-16 11:09:50","id":19,"moneyType":0,"payType":0,"realName":"test1","withdrawalMoney":300,"withdrawalStatus":0}]
     * pageSize : 2147483647
     * totalCount : 6
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

        public int getWithdrawalMoney() {
            return withdrawalMoney / 100;
        }
    }
}
