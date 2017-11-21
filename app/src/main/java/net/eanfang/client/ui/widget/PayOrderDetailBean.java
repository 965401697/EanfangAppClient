package net.eanfang.client.ui.widget;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yaosheng on 2017/5/21.
 */

public class PayOrderDetailBean implements Serializable {

    /**
     * clientname : 王二
     * clientphone : 13544458889
     * confirm : 1
     * createtime : 2017-03-29 22:51:32
     * detailList : [{"amount":"2","bugfourname":"4级故障例1","bugfouruid":"96d4bf43390646a2abf5743811785152","bugonename":"电视监控","bugoneuid":"C","bugthreename":"模拟枪型摄像机","bugthreeuid":"b178c0b5171d4601aa6a8a110850be75","bugtwoname":"摄像采集","bugtwouid":"c1151267364d44fbaeade6e411db4428","description":"不太贵","factory":"工厂1","id":1,"sumx":70,"unit":"个","unitprice":35},{"amount":"1","description":"贵","factory":"工厂2","id":2,"sumx":35,"unit":"个","unitprice":35}]
     * id : 1
     * invoicefee : 0
     * itemname : 报价1
     * ordernum : 32020170325224954787062
     * quotefee : 105
     * selfordernum : 32020170329223343951356
     * sum : 105
     */

    private String clientname;
    private String clientphone;
    private String confirm;
    private String createtime;
    private int id;
    private double invoicefee;
    private String itemname;
    private String ordernum;
    private double quotefee;
    private String selfordernum;
    private double sum;
    private List<DetailListBean> detailList;
    //2017年7月5日
    private String clientcompanynamewr;
    //2017年7月6日 lin
    private String workername;
    private String workerphone;


    public String getClientname() {
        return clientname;
    }

    public void setClientname(String clientname) {
        this.clientname = clientname;
    }

    public String getClientphone() {
        return clientphone;
    }

    public void setClientphone(String clientphone) {
        this.clientphone = clientphone;
    }

    public String getConfirm() {
        return confirm;
    }

    public void setConfirm(String confirm) {
        this.confirm = confirm;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getInvoicefee() {
        return invoicefee;
    }

    public void setInvoicefee(double invoicefee) {
        this.invoicefee = invoicefee;
    }

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public String getOrdernum() {
        return ordernum;
    }

    public void setOrdernum(String ordernum) {
        this.ordernum = ordernum;
    }

    public double getQuotefee() {
        return quotefee;
    }

    public void setQuotefee(double quotefee) {
        this.quotefee = quotefee;
    }

    public String getSelfordernum() {
        return selfordernum;
    }

    public void setSelfordernum(String selfordernum) {
        this.selfordernum = selfordernum;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public List<DetailListBean> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<DetailListBean> detailList) {
        this.detailList = detailList;
    }

    public String getClientcompanynamewr() {
        return clientcompanynamewr;
    }

    public void setClientcompanynamewr(String clientcompanynamewr) {
        this.clientcompanynamewr = clientcompanynamewr;
    }

    public String getWorkername() {
        return workername;
    }

    public void setWorkername(String workername) {
        this.workername = workername;
    }

    public String getWorkerphone() {
        return workerphone;
    }

    public void setWorkerphone(String workerphone) {
        this.workerphone = workerphone;
    }

    public static class DetailListBean {
        /**
         * amount : 2
         * bugfourname : 4级故障例1
         * bugfouruid : 96d4bf43390646a2abf5743811785152
         * bugonename : 电视监控
         * bugoneuid : C
         * bugthreename : 模拟枪型摄像机
         * bugthreeuid : b178c0b5171d4601aa6a8a110850be75
         * bugtwoname : 摄像采集
         * bugtwouid : c1151267364d44fbaeade6e411db4428
         * description : 不太贵
         * factory : 工厂1
         * id : 1
         * sumx : 70
         * unit : 个
         * unitprice : 35
         */

        private String amount;
        private String bugfourname;
        private String bugfouruid;
        private String bugonename;
        private String bugoneuid;
        private String bugthreename;
        private String bugthreeuid;
        private String bugtwoname;
        private String bugtwouid;
        private String description;
        private String factory;
        private int id;
        private double sumx;
        private String unit;
        private double unitprice;

        //报价明细里的设备图片
        private String pic;

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getBugfourname() {
            return bugfourname;
        }

        public void setBugfourname(String bugfourname) {
            this.bugfourname = bugfourname;
        }

        public String getBugfouruid() {
            return bugfouruid;
        }

        public void setBugfouruid(String bugfouruid) {
            this.bugfouruid = bugfouruid;
        }

        public String getBugonename() {
            return bugonename;
        }

        public void setBugonename(String bugonename) {
            this.bugonename = bugonename;
        }

        public String getBugoneuid() {
            return bugoneuid;
        }

        public void setBugoneuid(String bugoneuid) {
            this.bugoneuid = bugoneuid;
        }

        public String getBugthreename() {
            return bugthreename;
        }

        public void setBugthreename(String bugthreename) {
            this.bugthreename = bugthreename;
        }

        public String getBugthreeuid() {
            return bugthreeuid;
        }

        public void setBugthreeuid(String bugthreeuid) {
            this.bugthreeuid = bugthreeuid;
        }

        public String getBugtwoname() {
            return bugtwoname;
        }

        public void setBugtwoname(String bugtwoname) {
            this.bugtwoname = bugtwoname;
        }

        public String getBugtwouid() {
            return bugtwouid;
        }

        public void setBugtwouid(String bugtwouid) {
            this.bugtwouid = bugtwouid;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getFactory() {
            return factory;
        }

        public void setFactory(String factory) {
            this.factory = factory;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public double getSumx() {
            return sumx;
        }

        public void setSumx(double sumx) {
            this.sumx = sumx;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public double getUnitprice() {
            return unitprice;
        }

        public void setUnitprice(double unitprice) {
            this.unitprice = unitprice;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }
    }
}
