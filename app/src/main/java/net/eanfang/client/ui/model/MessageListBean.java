package net.eanfang.client.ui.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/6/27.
 */

public class MessageListBean implements Serializable {

    /**
     * pages : 13
     * rows : [{"content":"【林】您好：您收到【姓名】的【电视监控】维修申请，请及时处理。推送时间：2017-06-26 22:25:42","createtime":1498487142000,"id":138,"phonenum":"176001199280","title":"报修成功","type":1},{"content":"【林】您好：您收到【姓名】的【电视监控】维修申请，请及时处理。推送时间：2017-06-26 13:21:57","createtime":1498454517000,"id":128,"phonenum":"176001199280","title":"报修成功","type":1},{"content":"【林】您好：您收到【姓名】的【电视监控】维修申请，请及时处理。推送时间：2017-06-26 13:16:01","createtime":1498454161000,"id":126,"phonenum":"176001199280","title":"报修成功","type":1}]
     * total : 39
     */

    private int pages;
    private int total;
    private List<RowsBean> rows;

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<RowsBean> getRows() {
        return rows;
    }

    public void setRows(List<RowsBean> rows) {
        this.rows = rows;
    }

    public static class RowsBean {
        /**
         * content : 【林】您好：您收到【姓名】的【电视监控】维修申请，请及时处理。推送时间：2017-06-26 22:25:42
         * createtime : 1498487142000
         * id : 138
         * phonenum : 176001199280
         * title : 报修成功
         * type : 1
         */

        private String content;
        private long createtime;
        private int id;
        private String phonenum;
        private String title;
        private int type;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public long getCreatetime() {
            return createtime;
        }

        public void setCreatetime(long createtime) {
            this.createtime = createtime;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getPhonenum() {
            return phonenum;
        }

        public void setPhonenum(String phonenum) {
            this.phonenum = phonenum;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }
}
