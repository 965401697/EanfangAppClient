package com.eanfang.biz.model.bean;

import java.util.List;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author liangkailun
 * Date ：2019-05-28
 * Describe :
 */
@NoArgsConstructor
@Data
@Setter
@Getter
public class NewOrderBean {

    private int currPage;
    private int pageSize;
    private int totalCount;
    private int totalPage;
    private List<ListBean> list;

    @NoArgsConstructor
    @Data
    public static class ListBean {
        /**
         * budgetScale : 0
         * bussinessOneCode : 2.3
         * createTime : 2019-05-31 16:14:21
         * finishTime : 2019-05-31 16:14:21
         * orderId : 654646464324651321
         * orderType : 维修
         * placeCode : 3.12.1.2
         * placeOrderName : test3
         * projectName : 1
         * technician : 小何姐
         * tenderingUnit : 1
         */

        /**
         *规模
         */
        private int budgetScale;
        /**
         * 系统类型
         */
        private String bussinessOneCode;
        /**
         * 下单时间
         */
        private String createTime;
        /**
         * 计划时间
         */
        private String finishTime;
        /**
         * 订单号
         */
        private Long orderId;
        /**
         * 订单类型
         */
        private String orderType;
        /**
         * 区域
         */
        private String placeCode;
        /**
         * 下单人名称
         */
        private String placeOrderName;
        /**
         * 项目名称
         */
        private String projectName;
        /**
         * 技师
         */
        private String technician;
        /**
         *招标单位
         */
        private String tenderingUnit;
        /**
         * 公司名
         */
        private String companyName;

        private String accId;
    }
}
