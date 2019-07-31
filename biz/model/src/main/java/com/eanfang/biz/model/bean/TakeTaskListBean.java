package com.eanfang.biz.model.bean;

import android.content.Intent;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wen on 2017/6/2.
 */

public class TakeTaskListBean implements Serializable {

    private String[] mTitles = {
            "待确认", "待支付", "待完工", "待验收", "已完成"
    };

    private List<AllBean> all;

    public List<AllBean> getAll() {
        return all;
    }

    public void setAll(List<AllBean> all) {
        this.all = all;
    }

    public List<AllBean> getOrders(int state) {

        if (state == 5) {
            return getAll();
        }

        return getGroupMap().get(mTitles[state]);
    }

    public List<AllBean> getOrders(String title) {
        if ("全部".equals(title)) {
            return getAll();
        }
        return getGroupMap().get(title);
    }

    public Map<String, List> getGroupMap() {
        Map<String, List> groupMap = new HashMap<String, List>();
        for (AllBean allBean : all) {
            if (groupMap.get(mTitles[allBean.getStatus()]) == null) {
                List<AllBean> list = new ArrayList<AllBean>();
                list.add(allBean);
                groupMap.put(mTitles[allBean.getStatus()], list);
            } else {
                groupMap.get(mTitles[allBean.getStatus()]).add(allBean);
            }
        }

        return groupMap;
    }

    public static class AllBean implements Serializable {
        /**
         * budget : 2000.0
         * clientcompanyname : 项目单位名称1
         * companyuid : 123
         * createtime : 2017-05-02 15:36:50
         * description : 电视监控
         * doortodoortime : 2017-06-06 12:12:12
         * id : 23
         * itemcity : 北京
         * itemdetaillocation : 详细地址123
         * itemtype : 2
         * itemzone : 昌平
         * latitude : 99.9
         * longitude : 99.9
         * personname : 李四
         * personphone : 13512341234
         * pic1 : http://note.youdao.com/favicon.ico
         * pic2 : http://note.youdao.com/favicon.ico
         * pic3 : http://note.youdao.com/favicon.ico
         * pic4 : http://note.youdao.com/favicon.ico
         * pic5 : http://note.youdao.com/favicon.ico
         * preworkduration : 三天
         * publishstatus : 1
         * servicetype : C
         * status : 0
         * uid : 496c84bb817548ebbdd9266aa0841231
         * workercompanyname : 北京腾讯公司
         * workeruid : 8dfsfss89dsfdfs9f89s8d9f
         */

        private String budget;
        private String clientcompanyname;
        private String companyuid;
        private String createtime;
        private String description;
        private String doortodoortime;
        private int id;
        private String itemcity;
        private String itemdetaillocation;
        private String itemtype;
        private String itemzone;
        private String latitude;
        private String longitude;
        private String personname;
        private String personphone;
        private String pic1;
        private String pic2;
        private String pic3;
        private String pic4;
        private String pic5;
        private String preworkduration;
        private String publishstatus;
        private String servicetype;
        private String status;
        private String uid;
        private String workercompanyname;
        private String workeruid;
        private String servicename;
        private String taskapplymoney;
        private String taskapplyuid;
        //申请人电话
        private String taskApplyPhone;


        public String getTaskapplymoney() {
            return taskapplymoney;
        }

        public void setTaskapplymoney(String taskapplymoney) {
            this.taskapplymoney = taskapplymoney;
        }

        public String getBudget() {
            return budget;
        }

        public void setBudget(String budget) {
            this.budget = budget;
        }

        public String getClientcompanyname() {
            return clientcompanyname;
        }

        public void setClientcompanyname(String clientcompanyname) {
            this.clientcompanyname = clientcompanyname;
        }

        public String getCompanyuid() {
            return companyuid;
        }

        public void setCompanyuid(String companyuid) {
            this.companyuid = companyuid;
        }

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getDoortodoortime() {
            return doortodoortime;
        }

        public void setDoortodoortime(String doortodoortime) {
            this.doortodoortime = doortodoortime;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getItemcity() {
            return itemcity;
        }

        public void setItemcity(String itemcity) {
            this.itemcity = itemcity;
        }

        public String getItemdetaillocation() {
            return itemdetaillocation;
        }

        public void setItemdetaillocation(String itemdetaillocation) {
            this.itemdetaillocation = itemdetaillocation;
        }

        public String getItemtype() {
            return itemtype;
        }

        public void setItemtype(String itemtype) {
            this.itemtype = itemtype;
        }

        public String getItemzone() {
            return itemzone;
        }

        public void setItemzone(String itemzone) {
            this.itemzone = itemzone;
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

        public String getPersonname() {
            return personname;
        }

        public void setPersonname(String personname) {
            this.personname = personname;
        }

        public String getPersonphone() {
            return personphone;
        }

        public void setPersonphone(String personphone) {
            this.personphone = personphone;
        }

        public String getPic1() {
            return pic1;
        }

        public void setPic1(String pic1) {
            this.pic1 = pic1;
        }

        public String getPic2() {
            return pic2;
        }

        public void setPic2(String pic2) {
            this.pic2 = pic2;
        }

        public String getPic3() {
            return pic3;
        }

        public void setPic3(String pic3) {
            this.pic3 = pic3;
        }

        public String getPic4() {
            return pic4;
        }

        public void setPic4(String pic4) {
            this.pic4 = pic4;
        }

        public String getPic5() {
            return pic5;
        }

        public void setPic5(String pic5) {
            this.pic5 = pic5;
        }

        public String getPreworkduration() {
            return preworkduration;
        }

        public void setPreworkduration(String preworkduration) {
            this.preworkduration = preworkduration;
        }

        public String getPublishstatus() {
            return publishstatus;
        }

        public void setPublishstatus(String publishstatus) {
            this.publishstatus = publishstatus;
        }

        public String getServicetype() {
            return servicetype;
        }

        public void setServicetype(String servicetype) {
            this.servicetype = servicetype;
        }

        public int getStatus() {
            if (status == null) {
                return 0;
            }
            return Integer.parseInt(status);
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getWorkercompanyname() {
            return workercompanyname;
        }

        public void setWorkercompanyname(String workercompanyname) {
            this.workercompanyname = workercompanyname;
        }

        public String getWorkeruid() {
            return workeruid;
        }

        public void setWorkeruid(String workeruid) {
            this.workeruid = workeruid;
        }

        public String getServicename() {
            return servicename;
        }

        public void setServicename(String servicename) {
            this.servicename = servicename;
        }

        public String getTaskapplyuid() {
            return taskapplyuid;
        }

        public void setTaskapplyuid(String taskapplyuid) {
            this.taskapplyuid = taskapplyuid;
        }

        public String getTaskApplyPhone() {
            return taskApplyPhone;
        }

        public void setTaskApplyPhone(String taskApplyPhone) {
            this.taskApplyPhone = taskApplyPhone;
        }
    }
}
