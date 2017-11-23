package net.eanfang.client.ui.model;

import java.util.List;

/**
 * Created by yaosheng on 2017/5/15.
 */

public class OrderProgressBean {

    private List<AllBean> all;

    public List<AllBean> getAll() {
        return all;
    }

    public void setAll(List<AllBean> all) {
        this.all = all;
    }

    public static class AllBean {
        /**
         * id : 1
         * ordernum : 23423424435433
         * status : 0
         * time : 2017-03-13 23:21:57
         * uid : dgyer7yyy879th98487593ht984t98u
         */

        private int id;
        private String ordernum;
        private String status;
        private String time;
        private String uid;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getOrdernum() {
            return ordernum;
        }

        public void setOrdernum(String ordernum) {
            this.ordernum = ordernum;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }
    }
}
