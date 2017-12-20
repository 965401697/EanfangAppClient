package net.eanfang.client.ui.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/6/27.
 */

public class MessageListBean implements Serializable {


    /**
     * currPage : 1
     * list : [{"account":"18500320187","contentInfo":"jornlin 您好： 您收到客户家乐福双井公司 (jornlin) 的维修申请，请及时处理 通知时间：2017-11-28 09:45:02","createTime":"2017-11-28 09:45","id":1,"orderId":"935323612758446081","status":1,"title":"报修成功","type":1},{"account":"18500320187","contentInfo":"jornlin 您好： 您收到客户家乐福北京公司 (jornlin) 的维修申请，请及时处理。 通知时间：2017-11-30 10:31:07","createTime":"2017-11-30 10:31","id":2,"orderId":"936059987556352001","status":1,"title":"报修成功","type":1},{"account":"18500320187","contentInfo":"jornlin 您好： 您收到客户易安防北京运营公司 (jornlin) 的维修申请，请及时处理。 通知时间：2017-12-07 16:56:18","createTime":"2017-12-07 16:56","id":10,"orderId":"938693640002256897","status":1,"title":"报修成功","type":1},{"account":"18500320187","contentInfo":"jornlin 您好： 您提交的报修单，技师张三 已确认，预约时间：2017-12-11 14:09:00。请做好相关准备。 通知时间：2017-12-11 14:11:14","createTime":"2017-12-11 14:11","id":16,"orderId":"938693640002256897","status":1,"title":"预约成功","type":2},{"account":"18500320187","contentInfo":"jornlin 您好： 您提交的报修单，技师张三 已经改约，预约时间：2017-12-11 15:09:45。请做好相关准备。 通知时间：2017-12-11 15:09:46","createTime":"2017-12-11 15:09","id":17,"orderId":"938693640002256897","status":0,"title":"改约提醒","type":4},{"account":"18500320187","contentInfo":"jornlin 您好： 您收到客户易安防北京运营公司 (jornlin) 的维修申请，请及时处理。 通知时间：2017-12-12 10:28:06","createTime":"2017-12-12 10:28","id":19,"orderId":"940407881491148802","status":0,"title":"报修成功","type":1},{"account":"18500320187","contentInfo":"jornlin 您好： 您提交的报修单，技师张三 已确认，预约时间：2017-12-12 10:40:01。请做好相关准备。 通知时间：2017-12-12 10:40:47","createTime":"2017-12-12 10:40","id":20,"orderId":"940407881491148802","status":0,"title":"预约成功","type":2},{"account":"18500320187","contentInfo":"jornlin 您好： 您提交的报修单，技师张三 已经改约，预约时间：2017-12-12 10:49:02。请做好相关准备。 通知时间：2017-12-12 10:44:00","createTime":"2017-12-12 10:44","id":21,"orderId":"940407881491148802","status":0,"title":"改约提醒","type":4},{"account":"18500320187","contentInfo":"jornlin 您好： 您提交的报修单，技师张三 已于2017-12-12 10:55:03上门签到，请及时处理。 通知时间：2017-12-12 10:45:24","createTime":"2017-12-12 10:45","id":22,"orderId":"940407881491148802","status":0,"title":"签到提醒","type":5},{"account":"18500320187","contentInfo":"jornlin 您好： 您收到易安防北京运营公司 (jornlin) 的工作检查，请及时处理 ","createTime":"2017-12-15 11:54","id":23,"orderId":"941516749743337474","status":1,"title":"工作检查提交成功","type":16}]
     * pageSize : 10
     * totalCount : 29
     * totalPage : 3
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

    public static class ListBean implements Serializable{
        /**
         * account : 18500320187
         * contentInfo : jornlin 您好： 您收到客户家乐福双井公司 (jornlin) 的维修申请，请及时处理 通知时间：2017-11-28 09:45:02
         * createTime : 2017-11-28 09:45
         * id : 1
         * orderId : 935323612758446081
         * status : 1
         * title : 报修成功
         * type : 1
         */

        private String account;
        private String contentInfo;
        private String createTime;
        private int id;
        private String orderId;
        private int status;
        private String title;
        private int type;

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getContentInfo() {
            return contentInfo;
        }

        public void setContentInfo(String contentInfo) {
            this.contentInfo = contentInfo;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
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


