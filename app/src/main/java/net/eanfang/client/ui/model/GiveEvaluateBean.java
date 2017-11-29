package net.eanfang.client.ui.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yaosheng on 2017/6/28.
 */

public class GiveEvaluateBean implements Serializable {

    private List<AllBean> all;

    public List<AllBean> getAll() {
        return all;
    }

    public void setAll(List<AllBean> all) {
        this.all = all;
    }

    public static class AllBean {
        /**
         * companyname : 先发
         * createtime : 2017-05-24 14:28:06
         * headpic : http://eanfangx.oss-cn-beijing.aliyuncs.com/bnmmk
         * id : 2
         * ordernum : 123131313131
         * personclientuid : de1d14c618934394a6cf680ae84ab616
         * personworkeruid : b556ee600d47491aa49174fc1b323b76
         * prostandard : 5
         * realname : 刘龙2
         * resptime : 5
         * serviceattitude : 5
         * techlevel : 5
         * totalreview : 5
         * uid : b556ee600d47491aa49174fc1b323b761
         * workefficient : 5
         */

        private String companyname;
        private String createtime;
        private String headpic;
        private int id;
        private String ordernum;
        private String personclientuid;
        private String personworkeruid;
        private String prostandard;
        private String realname;
        private String resptime;
        private String serviceattitude;
        private String techlevel;
        private String totalreview;
        private String uid;
        private String workefficient;

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

        public String getHeadpic() {
            return headpic;
        }

        public void setHeadpic(String headpic) {
            this.headpic = headpic;
        }

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

        public String getPersonclientuid() {
            return personclientuid;
        }

        public void setPersonclientuid(String personclientuid) {
            this.personclientuid = personclientuid;
        }

        public String getPersonworkeruid() {
            return personworkeruid;
        }

        public void setPersonworkeruid(String personworkeruid) {
            this.personworkeruid = personworkeruid;
        }

        public String getProstandard() {
            return prostandard;
        }

        public void setProstandard(String prostandard) {
            this.prostandard = prostandard;
        }

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
        }

        public String getResptime() {
            return resptime;
        }

        public void setResptime(String resptime) {
            this.resptime = resptime;
        }

        public String getServiceattitude() {
            return serviceattitude;
        }

        public void setServiceattitude(String serviceattitude) {
            this.serviceattitude = serviceattitude;
        }

        public String getTechlevel() {
            return techlevel;
        }

        public void setTechlevel(String techlevel) {
            this.techlevel = techlevel;
        }

        public String getTotalreview() {
            return totalreview;
        }

        public void setTotalreview(String totalreview) {
            this.totalreview = totalreview;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getWorkefficient() {
            return workefficient;
        }

        public void setWorkefficient(String workefficient) {
            this.workefficient = workefficient;
        }
    }
}
