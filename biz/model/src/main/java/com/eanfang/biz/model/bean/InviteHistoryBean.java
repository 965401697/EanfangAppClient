package com.eanfang.biz.model.bean;

import lombok.Getter;
import lombok.Setter;

/**
 * @author liangkailun
 * Date ：2019/5/13
 * Describe :
 */
@Setter
public class InviteHistoryBean {
    /**
     * 邀请人数
     */
    @Getter
    private String count;
    /**
     * 提取成功金额
     */
    private int withdrawalsScAmount;
    /**
     * 剩余金额
     */
    private int withdrawalsAmount;

    /**
     * 失效金额
     */
    private int invalidAmount;
    public int getWithdrawalsScAmount() {
        return withdrawalsScAmount / 100;
    }
    public int getWithdrawalsAmount() {
        return withdrawalsAmount / 100;
    }
    public int getInvalidAmount() {
        return invalidAmount / 100;
    }

}
