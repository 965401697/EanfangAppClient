package net.eanfang.client.ui.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/4/14.
 */

public class WorkerDetailsBean implements Serializable {

    /**
     * businesstype : C,V
     * companyname : 全能快修
     * goodpercent : 0.90
     * honor1 : http://note.youdao.com/favicon.ico
     * honor2 : http://note.youdao.com/favicon.ico
     * honor3 : http://note.youdao.com/favicon.ico
     * id : 8
     * install : 1
     * praise : 5
     * prostandard : 5
     * realname : 范师傅
     * repair : 1
     * resptime : 5
     * serviceattitude : 5
     * serviceplace : [{"city":"北京","id":5,"personuid":"e99d1acc503d40b2a964cc4a83fa27a2","zone":"海淀"},{"city":"北京","id":6,"personuid":"e99d1acc503d40b2a964cc4a83fa27a2","zone":"海淀1"},{"city":"北京","id":7,"personuid":"e99d1acc503d40b2a964cc4a83fa27a2","zone":"海淀2"}]
     * techlevel : 5
     * workdetailplace : 东大桥路2号
     * workefficient : 5
     * workexp : 5
     * worklevel : 1
     */

    private String headpic;
    private String repairtimes;
    private String collected;

    public String getCollected() {
        return collected;
    }

    public void setCollected(String collected) {
        this.collected = collected;
    }

    public String getRepairtimes() {
        return repairtimes;
    }

    public void setRepairtimes(String repairtimes) {
        this.repairtimes = repairtimes;
    }

    public String getHeadpic() {
        return headpic;
    }

    public void setHeadpic(String headpic) {
        this.headpic = headpic;
    }

    private String businesstype;
    private String companyname;
    private String goodpercent;
    private String honor1;
    private String honor2;
    private String honor3;
    private String honor4;

    public String getHonor4() {
        return honor4;
    }

    public void setHonor4(String honor4) {
        this.honor4 = honor4;
    }

    private int id;
    private String install;
    private String praise;
    private String prostandard;
    private String realname;
    private String repair;
    private String resptime;
    private String serviceattitude;
    private String techlevel;
    private String workdetailplace;
    private String workefficient;
    private String workexp;
    private String worklevel;
    private List<ServiceplaceBean> serviceplace;

    public String getBusinesstype() {
        return businesstype;
    }

    public void setBusinesstype(String businesstype) {
        this.businesstype = businesstype;
    }

    public String getCompanyname() {
        return companyname;
    }

    public void setCompanyname(String companyname) {
        this.companyname = companyname;
    }

    public String getGoodpercent() {
        return goodpercent;
    }

    public void setGoodpercent(String goodpercent) {
        this.goodpercent = goodpercent;
    }

    public String getHonor1() {
        return honor1;
    }

    public void setHonor1(String honor1) {
        this.honor1 = honor1;
    }

    public String getHonor2() {
        return honor2;
    }

    public void setHonor2(String honor2) {
        this.honor2 = honor2;
    }

    public String getHonor3() {
        return honor3;
    }

    public void setHonor3(String honor3) {
        this.honor3 = honor3;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInstall() {
        return install;
    }

    public void setInstall(String install) {
        this.install = install;
    }

    public String getPraise() {
        return praise;
    }

    public void setPraise(String praise) {
        this.praise = praise;
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

    public String getRepair() {
        return repair;
    }

    public void setRepair(String repair) {
        this.repair = repair;
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

    public String getWorkdetailplace() {
        return workdetailplace;
    }

    public void setWorkdetailplace(String workdetailplace) {
        this.workdetailplace = workdetailplace;
    }

    public String getWorkefficient() {
        return workefficient;
    }

    public void setWorkefficient(String workefficient) {
        this.workefficient = workefficient;
    }

    public String getWorkexp() {
        return workexp;
    }

    public void setWorkexp(String workexp) {
        this.workexp = workexp;
    }

    public String getWorklevel() {
        return worklevel;
    }

    public void setWorklevel(String worklevel) {
        this.worklevel = worklevel;
    }

    public List<ServiceplaceBean> getServiceplace() {
        return serviceplace;
    }

    public void setServiceplace(List<ServiceplaceBean> serviceplace) {
        this.serviceplace = serviceplace;
    }

    public static class ServiceplaceBean {
        /**
         * city : 北京
         * id : 5
         * personuid : e99d1acc503d40b2a964cc4a83fa27a2
         * zone : 海淀
         */

        private String city;
        private int id;
        private String personuid;
        private String zone;

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getPersonuid() {
            return personuid;
        }

        public void setPersonuid(String personuid) {
            this.personuid = personuid;
        }

        public String getZone() {
            return zone;
        }

        public void setZone(String zone) {
            this.zone = zone;
        }
    }
}
