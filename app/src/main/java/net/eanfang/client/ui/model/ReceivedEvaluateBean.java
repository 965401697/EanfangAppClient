package net.eanfang.client.ui.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/6/22.
 */

public class ReceivedEvaluateBean implements Serializable {

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
         * createtime : 2017-05-03 14:28:13
         * gzxzzc : 5
         * headpic : http://eanfangx.oss-cn-beijing.aliyuncs.com/bnmmk
         * id : 1
         * jsjs : 5
         * ordernum : 12313132
         * personclientuid : de1d14c618934394a6cf680ae84ab616
         * personworkeruid : b556ee600d47491aa49174fc1b323b76
         * realname : 刘龙2
         * tdrqyh : 5
         * totalreview : 5
         * uid : de1d14c618934394a6cf680ae84ab616
         * xchj : 5
         * zysbsxcd : 5
         */

        private String companyname;
        private String createtime;
        private String gzxzzc;
        private String headpic;
        private int id;
        private String jsjs;
        private String ordernum;
        private String personclientuid;
        private String personworkeruid;
        private String realname;
        private String tdrqyh;
        private String totalreview;
        private String uid;
        private String xchj;
        private String zysbsxcd;

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

        public String getGzxzzc() {
            return gzxzzc;
        }

        public void setGzxzzc(String gzxzzc) {
            this.gzxzzc = gzxzzc;
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

        public String getJsjs() {
            return jsjs;
        }

        public void setJsjs(String jsjs) {
            this.jsjs = jsjs;
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

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
        }

        public String getTdrqyh() {
            return tdrqyh;
        }

        public void setTdrqyh(String tdrqyh) {
            this.tdrqyh = tdrqyh;
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

        public String getXchj() {
            return xchj;
        }

        public void setXchj(String xchj) {
            this.xchj = xchj;
        }

        public String getZysbsxcd() {
            return zysbsxcd;
        }

        public void setZysbsxcd(String zysbsxcd) {
            this.zysbsxcd = zysbsxcd;
        }
    }
}
