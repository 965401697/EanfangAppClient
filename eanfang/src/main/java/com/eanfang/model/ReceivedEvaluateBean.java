package com.eanfang.model;

import com.yaf.sys.entity.UserEntity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/6/22.
 */

public class ReceivedEvaluateBean implements Serializable {

    /**
     * currPage : 1
     * list : [{"createTime":"2017-12-20 17:53","createUserId":1,"id":6,"item1":5,"item2":5,"item3":5,"item4":5,"item5":5,"orderId":"940407881491148801","orderNum":"MO1712121028320","ownerId":"936487014465806338"},{"createTime":"2017-12-20 17:55","createUserId":1,"id":7,"item1":5,"item2":5,"item3":5,"item4":5,"item5":5,"orderId":"940407881491148800","orderNum":"MO1712121028320","ownerId":"936487014465806338"},{"createTime":"2017-12-20 18:06","createUserId":1,"id":8,"item1":5,"item2":5,"item3":5,"item4":5,"item5":5,"orderId":"940407881491148803","orderNum":"MO1712121028320","ownerId":"936487014465806338"},{"createTime":"2017-12-20 19:43","createUserId":1,"id":9,"item1":5,"item2":5,"item3":5,"item4":5,"item5":5,"orderId":"940407881491148804","orderNum":"MO1712121028320","ownerId":"936487014465806338"},{"createTime":"2017-12-20 19:50","createUserId":1,"id":10,"item1":5,"item2":5,"item3":5,"item4":5,"item5":5,"orderId":"940407881491148805","orderNum":"MO1712121028320","ownerId":"936487014465806338"},{"createTime":"2017-12-20 19:54","createUserId":1,"id":11,"item1":2,"item2":3,"item3":4,"item4":5,"item5":1,"orderId":"940407881491148806","orderNum":"MO1712121028320","ownerId":"936487014465806338"},{"createTime":"2017-12-20 19:57","createUserId":1,"id":12,"item1":2,"item2":3,"item3":4,"item4":5,"item5":1,"orderId":"940407881491148802","orderNum":"MO1712121028320","ownerId":"936487014465806338"}]
     * pageSize : 10
     * totalCount : 7
     * totalPage : 1
     */

    private int currPage;
    private int pageSize;
    private int totalCount;
    private int totalPage;
    private List<ListBean> list;

    public int getCurrPage() {
        return currPage;
    }

    public void setCurrPage(int currPage) {
        this.currPage = currPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean implements Serializable {
        /**
         * createTime : 2017-12-20 17:53
         * createUserId : 1
         * id : 6
         * item1 : 5
         * item2 : 5
         * item3 : 5
         * item4 : 5
         * item5 : 5
         * orderId : 940407881491148801
         * orderNum : MO1712121028320
         * ownerId : 936487014465806338
         */

        private String createTime;
        private int createUserId;
        private Long id;
        private int item1;
        private int item2;
        private int item3;
        private int item4;
        private int item5;
        private Long orderId;
        private String orderNum;
        private Long ownerId;
        private UserEntity createUser;
        private UserEntity ownerUser;

        public UserEntity getCreateUser() {
            return createUser;
        }

        public void setCreateUser(UserEntity createUser) {
            this.createUser = createUser;
        }

        public UserEntity getOwnerUser() {
            return ownerUser;
        }

        public void setOwnerUser(UserEntity ownerUser) {
            this.ownerUser = ownerUser;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getCreateUserId() {
            return createUserId;
        }

        public void setCreateUserId(int createUserId) {
            this.createUserId = createUserId;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public int getItem1() {
            return item1;
        }

        public void setItem1(int item1) {
            this.item1 = item1;
        }

        public int getItem2() {
            return item2;
        }

        public void setItem2(int item2) {
            this.item2 = item2;
        }

        public int getItem3() {
            return item3;
        }

        public void setItem3(int item3) {
            this.item3 = item3;
        }

        public int getItem4() {
            return item4;
        }

        public void setItem4(int item4) {
            this.item4 = item4;
        }

        public int getItem5() {
            return item5;
        }

        public void setItem5(int item5) {
            this.item5 = item5;
        }

        public Long getOrderId() {
            return orderId;
        }

        public void setOrderId(Long orderId) {
            this.orderId = orderId;
        }

        public String getOrderNum() {
            return orderNum;
        }

        public void setOrderNum(String orderNum) {
            this.orderNum = orderNum;
        }

        public Long getOwnerId() {
            return ownerId;
        }

        public void setOwnerId(Long ownerId) {
            this.ownerId = ownerId;
        }
    }
}

