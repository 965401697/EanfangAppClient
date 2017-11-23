package net.eanfang.client.ui.model;

import com.eanfang.util.NumberUtil;

import java.util.List;

/**
 * Created by wen on 2017/5/11.
 */

public class RepairedOrderBean {
    private final String[] mTitles = {
            "预付费", "已报修", "待上门"
            , "待完工", "待确认", "待评价", "全部"
    };
    private List<AllBean> all;

    public List<AllBean> getAll() {
        return all;
    }

    public void setAll(List<AllBean> all) {
        this.all = all;
    }

    public static class AllBean {
        /**
         * account : 15038147687
         * arrivetime : 两小时之内
         * city : 湖南
         * clientcompanyuid : 1f9aa90b68894dac972e39b036fc1deb
         * clientconnector : 若曦
         * clientphone : 18720489973
         * companyname : 星巴克
         * createtime : 2017-03-13 23:21:57
         * detailplace : 山里热酒镇
         * doorfee : 30
         * endtime : 2024-08-13 23:21:57
         * id : 1
         * invoicefee : 40
         * latitude : 89.78
         * longitude : 68.88
         * ordernum : 23423424435433
         * pass : 1
         * paytype : 支付宝
         * status : 1
         * totalfee : 70
         * workeruid : c65838b399fb444f95b88a085f4bea1d
         * zone : 长沙
         */

        private String account;
        private String arrivetime;
        private String city;
        private String clientcompanyuid;
        private String clientconnector;
        private String clientphone;
        private String companyname;
        private String createtime;
        private String detailplace;
        private double doorfee;
        private String endtime;
        private int id;
        private double invoicefee;
        private String latitude;
        private String longitude;
        private String ordernum;
        private String pass;
        private String paytype;
        private String status;
        private double totalfee;
        private String workeruid;
        private String zone;

        //预约时间
        private String pretime;

        //对应业务类型的图片地址
        private String pic1;

        //列表中拨打的对应电话（客户/技师）
        private String workerPhone;
        private String clientPhone;

        //列表中标题（客户客户/安防公司）
        private String workerCompanyName;
        private String clientCompanyName;
        //列表中标题（客户/技师）
        private String workerName;
        private String clientName;

        //原始订单编号  判断挂单单据使用
        private String originordernum;

        private String bugonename;

        /**
         * 电话解决
         */
        private String phonesolve;


        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getArrivetime() {
            return arrivetime;
        }

        public void setArrivetime(String arrivetime) {
            this.arrivetime = arrivetime;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getClientcompanyuid() {
            return clientcompanyuid;
        }

        public void setClientcompanyuid(String clientcompanyuid) {
            this.clientcompanyuid = clientcompanyuid;
        }

        public String getClientconnector() {
            return clientconnector;
        }

        public void setClientconnector(String clientconnector) {
            this.clientconnector = clientconnector;
        }

        public String getClientphone() {
            return clientphone;
        }

        public void setClientphone(String clientphone) {
            this.clientphone = clientphone;
        }

        public String getCompanyname() {
            return companyname;
        }

        public void setCompanyname(String companyname) {
            this.companyname = companyname;
        }

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public String getDetailplace() {
            return detailplace;
        }

        public void setDetailplace(String detailplace) {
            this.detailplace = detailplace;
        }

        public double getDoorfee() {
            return doorfee;
        }

        public void setDoorfee(double doorfee) {
            this.doorfee = doorfee;
        }

        public String getEndtime() {
            return endtime;
        }

        public void setEndtime(String endtime) {
            this.endtime = endtime;
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

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getOrdernum() {
            return ordernum;
        }

        public void setOrdernum(String ordernum) {
            this.ordernum = ordernum;
        }

        public String getPass() {
            return pass;
        }

        public void setPass(String pass) {
            this.pass = pass;
        }

        public String getPaytype() {
            return paytype;
        }

        public void setPaytype(String paytype) {
            this.paytype = paytype;
        }

        public int getStatus() {
            return NumberUtil.parseInt(status, 0);
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public double getTotalfee() {
            return totalfee;
        }

        public void setTotalfee(double totalfee) {
            this.totalfee = totalfee;
        }

        public String getWorkeruid() {
            return workeruid;
        }

        public void setWorkeruid(String workeruid) {
            this.workeruid = workeruid;
        }

        public String getZone() {
            return zone;
        }

        public void setZone(String zone) {
            this.zone = zone;
        }

        public String getPretime() {
            return pretime;
        }

        public void setPretime(String pretime) {
            this.pretime = pretime;
        }

        public String getPic1() {
            return pic1;
        }

        public void setPic1(String pic1) {
            this.pic1 = pic1;
        }


        public String getOriginordernum() {
            return originordernum;
        }

        public void setOriginordernum(String originordernum) {
            this.originordernum = originordernum;
        }

        public String getWorkerPhone() {
            return workerPhone;
        }

        public void setWorkerPhone(String workerPhone) {
            this.workerPhone = workerPhone;
        }

        public String getClientPhone() {
            return clientPhone;
        }

        public void setClientPhone(String clientPhone) {
            this.clientPhone = clientPhone;
        }

        public String getWorkerCompanyName() {
            return workerCompanyName;
        }

        public void setWorkerCompanyName(String workerCompanyName) {
            this.workerCompanyName = workerCompanyName;
        }

        public String getClientCompanyName() {
            return clientCompanyName;
        }

        public void setClientCompanyName(String clientCompanyName) {
            this.clientCompanyName = clientCompanyName;
        }

        public String getWorkerName() {
            return workerName;
        }

        public void setWorkerName(String workerName) {
            this.workerName = workerName;
        }

        public String getClientName() {
            return clientName;
        }

        public void setClientName(String clientName) {
            this.clientName = clientName;
        }

        public String getBugonename() {
            return bugonename;
        }

        public void setBugonename(String bugonename) {
            this.bugonename = bugonename;
        }

        public String getPhonesolve() {
            return phonesolve;
        }

        public void setPhonesolve(String phonesolve) {
            this.phonesolve = phonesolve;
        }
    }


//    public List<RepairedOrderBean.AllBean> getOrders(int state) {
//
//        if (state == 6) {
//            return getAll();
//        }
//
//        return getOrders(mTitles[state]);
//    }
//
//    public List<RepairedOrderBean.AllBean> getOrders(String title) {
//        if ("全部".equals(title)) {
//            return getAll();
//        }
//        return getGroupMap().get(title) == null ? new ArrayList<AllBean>() : getGroupMap().get(title);
//    }
//
//
//    public Map<String, List> getGroupMap() {
//        Map<String, List> groupMap = new HashMap<String, List>();
//        for (AllBean allBean : all) {
//            if (groupMap.get(mTitles[allBean.getStatus()]) == null) {
//                List<RepairedOrderBean.AllBean> list = new ArrayList<AllBean>();
//                list.add(allBean);
//                groupMap.put(mTitles[allBean.getStatus()], list);
//            } else {
//                groupMap.get(mTitles[allBean.getStatus()]).add(allBean);
//            }
//        }
//
//        return groupMap;
//    }

}
