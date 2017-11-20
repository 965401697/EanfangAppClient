package net.eanfang.client.ui.model;

import java.io.Serializable;

/**
 * Created by yaosheng on 2017/5/19.
 */

public class WorkspaceInstallDetailBean implements Serializable {

    /**
     * installorder : {"arrivetime":"2小时内","budget":"1万元以内","bugone":"1","bugonename":"电视监控","city":"北京","clientcompanyuid":"1ac77e2c643444f2ac1966d8ec868157","clientconnector":"李晨星","clientpersonuid":"abcdefg","clientphone":"123456789","createtime":"2017-04-11 17:25:21","description":"详情描述","detailplace":"回龙观东大街","endtime":"2017-04-12 17:25:25","id":1,"latitude":"12","longitude":"12","ordernum":"123213","predicttime":"3天内","uid":"121321","workercompanyuid":"1ac77e2c643444f2ac1966d8ec868157","zone":"昌平"}
     * workerCompanyAdminname : 赵要生
     * workerCompanyname : 全能快修
     * workerPhone : 15038147687
     */

    private InstallorderBean installorder;
    private String workerCompanyAdminname;
    private String workerCompanyname;
    private String workerPhone;
    //private String workerCompanyAdminpic;
    //安防公司宣传图
    private String workerCompanyPic;

    //客户公司名称
    private String clientCompanyname;
    //客户公司宣传图
    private String clientCompanyPic;
    //客户联系电话
    private String clientPhone;
    //客户姓名
    private String clientname;


//    public String getWorkerCompanyAdminpic() {
//        return workerCompanyAdminpic;
//    }
//
//    public void setWorkerCompanyAdminpic(String workerCompanyAdminpic) {
//        this.workerCompanyAdminpic = workerCompanyAdminpic;
//    }

    public InstallorderBean getInstallorder() {
        return installorder;
    }

    public void setInstallorder(InstallorderBean installorder) {
        this.installorder = installorder;
    }

    public String getWorkerCompanyAdminname() {
        return workerCompanyAdminname;
    }

    public void setWorkerCompanyAdminname(String workerCompanyAdminname) {
        this.workerCompanyAdminname = workerCompanyAdminname;
    }

    public String getWorkerCompanyname() {
        return workerCompanyname;
    }

    public void setWorkerCompanyname(String workerCompanyname) {
        this.workerCompanyname = workerCompanyname;
    }

    public String getWorkerPhone() {
        return workerPhone;
    }

    public void setWorkerPhone(String workerPhone) {
        this.workerPhone = workerPhone;
    }

    public String getWorkerCompanyPic() {
        return workerCompanyPic;
    }

    public void setWorkerCompanyPic(String workerCompanyPic) {
        this.workerCompanyPic = workerCompanyPic;
    }

    public String getClientCompanyname() {
        return clientCompanyname;
    }

    public void setClientCompanyname(String clientCompanyname) {
        this.clientCompanyname = clientCompanyname;
    }

    public String getClientCompanyPic() {
        return clientCompanyPic;
    }

    public void setClientCompanyPic(String clientCompanyPic) {
        this.clientCompanyPic = clientCompanyPic;
    }

    public String getClientPhone() {
        return clientPhone;
    }

    public void setClientPhone(String clientPhone) {
        this.clientPhone = clientPhone;
    }

    public String getClientname() {
        return clientname;
    }

    public void setClientname(String clientname) {
        this.clientname = clientname;
    }

    public static class InstallorderBean {
        /**
         * arrivetime : 2小时内
         * budget : 1万元以内
         * bugone : 1
         * bugonename : 电视监控
         * city : 北京
         * clientcompanyuid : 1ac77e2c643444f2ac1966d8ec868157
         * clientconnector : 李晨星
         * clientpersonuid : abcdefg
         * clientphone : 123456789
         * createtime : 2017-04-11 17:25:21
         * description : 详情描述
         * detailplace : 回龙观东大街
         * endtime : 2017-04-12 17:25:25
         * id : 1
         * latitude : 12
         * longitude : 12
         * ordernum : 123213
         * predicttime : 3天内
         * uid : 121321
         * workercompanyuid : 1ac77e2c643444f2ac1966d8ec868157
         * zone : 昌平
         */

        private String arrivetime;
        private String budget;
        private String bugone;
        private String bugonename;
        private String city;
        private String clientcompanyuid;
        private String clientconnector;
        private String clientpersonuid;
        private String clientphone;
        private String createtime;
        private String description;
        private String detailplace;
        private String endtime;
        private int id;
        private String latitude;
        private String longitude;
        private String ordernum;
        private String predicttime;
        private String uid;
        private String workercompanyuid;
        private String zone;
        private String clientcompanyname;

        public String getClientcompanyname() {
            return clientcompanyname;
        }

        public void setClientcompanyname(String clientcompanyname) {
            this.clientcompanyname = clientcompanyname;
        }

        public String getArrivetime() {
            return arrivetime;
        }

        public void setArrivetime(String arrivetime) {
            this.arrivetime = arrivetime;
        }

        public String getBudget() {
            return budget;
        }

        public void setBudget(String budget) {
            this.budget = budget;
        }

        public String getBugone() {
            return bugone;
        }

        public void setBugone(String bugone) {
            this.bugone = bugone;
        }

        public String getBugonename() {
            return bugonename;
        }

        public void setBugonename(String bugonename) {
            this.bugonename = bugonename;
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

        public String getClientpersonuid() {
            return clientpersonuid;
        }

        public void setClientpersonuid(String clientpersonuid) {
            this.clientpersonuid = clientpersonuid;
        }

        public String getClientphone() {
            return clientphone;
        }

        public void setClientphone(String clientphone) {
            this.clientphone = clientphone;
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

        public String getDetailplace() {
            return detailplace;
        }

        public void setDetailplace(String detailplace) {
            this.detailplace = detailplace;
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

        public String getPredicttime() {
            return predicttime;
        }

        public void setPredicttime(String predicttime) {
            this.predicttime = predicttime;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getWorkercompanyuid() {
            return workercompanyuid;
        }

        public void setWorkercompanyuid(String workercompanyuid) {
            this.workercompanyuid = workercompanyuid;
        }

        public String getZone() {
            return zone;
        }

        public void setZone(String zone) {
            this.zone = zone;
        }
    }
}
