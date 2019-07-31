package com.eanfang.biz.model.entity;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author guanluocang
 * @data 2019/7/31
 * @description
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SelectWorkEntitity implements Serializable {

    private int currPage;
    private int pageSize;
    private int totalCount;
    private int totalPage;
    private List<ListBean> list;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ListBean {

        private String accId;
        private AccountEntity accountEntity;
        private OrgEntity companyEntity;
        private String companyUserId;
        private OrgEntity departmentEntity;
        private int designNum;
        private int evaluateNum;
        private int goodRate;
        private double goodReputation;
        private int id;
        private int installNum;
        private int item1;
        private int item2;
        private int item3;
        private int item4;
        private int item5;
        private String lat;
        private String lon;
        private String placeCode;
        private int publicPraise;
        private int qualification;
        private int repairCount;
        private int trainStatus;
        private WorkerVerifyEntity verifyEntity;
        private String verifyId;

    }
}
