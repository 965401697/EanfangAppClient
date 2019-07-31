package com.eanfang.biz.model.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yaosheng on 2017/4/26.
 */

public class CompanyDetailBean implements Serializable {

    /**
     * businesstype : B,C
     * companyname : 全能快修
     * companyscale : 1-10人
     * companyverify : 1
     * goodpercent : 0.33
     * honorpic1 : http://eanfangx.oss-cn-beijing.aliyuncs.com/012fcb03b2894c89819bafd8aff060d1.png
     * honorpic2 : http://eanfangx.oss-cn-beijing.aliyuncs.com/03c34b1455a24f3494622abb1d66b269.png
     * honorpic3 : http://eanfangx.oss-cn-beijing.aliyuncs.com/04127f6e35e94ea082ed8f248655dcf5.png
     * installamount : 5
     * licencepic : http://eanfangx.oss-cn-beijing.aliyuncs.com/8ef641b3442d49059eba8bd70570f71c.png
     * logopic : http://eanfangx.oss-cn-beijing.aliyuncs.com/08fc6135ff564133a7b0631339f30f9e.png
     * praise : 3.4
     * registercity : 朝阳区
     * registerdetailplace : 日坛北路1号
     * registerprovince : 北京
     * repairamount : 14
     * reviewamount : 3
     * serviceplace : [{"city":"北京","companyuid":"1ac77e2c643444f2ac1966d8ec868157","id":1,"zone":"海淀"},{"city":"北京","companyuid":"1ac77e2c643444f2ac1966d8ec868157","id":2,"zone":"顺义"}]
     * servicetypeinstall : 1
     * servicetyperepair : 1
     * worklevel : 2级
     * workyear : 1-3年
     * zizhipic1 : http://eanfangx.oss-cn-beijing.aliyuncs.com/00ef869168e54ff79ee53a01eb66ab31.png
     * zizhipic2 : http://eanfangx.oss-cn-beijing.aliyuncs.com/010371e32cff4f659e6f3aab88879f67.png
     */

    private String businesstype;
    private String companyname;
    private String companyscale;
    private String companyverify;
    private String goodpercent;
    private String honorpic1;
    private String honorpic2;
    private String honorpic3;
    private int installamount;
    private String licencepic;
    private String logopic;
    private String praise;
    private String registercity;
    private String registerdetailplace;
    private String registerprovince;
    private int repairamount;
    private int reviewamount;
    private String servicetypeinstall;
    private String servicetyperepair;
    private String worklevel;
    private String workyear;
    private String zizhipic1;
    private String zizhipic2;
    private String description;
    private String adpic;
    private String collected;
    private List<ServiceplaceBean> serviceplace;

    @Override
    public String toString() {
        return "CompanyDetailBean{" +
                "businesstype='" + businesstype + '\'' +
                ", companyname='" + companyname + '\'' +
                ", companyscale='" + companyscale + '\'' +
                ", companyverify='" + companyverify + '\'' +
                ", goodpercent='" + goodpercent + '\'' +
                ", honorpic1='" + honorpic1 + '\'' +
                ", honorpic2='" + honorpic2 + '\'' +
                ", honorpic3='" + honorpic3 + '\'' +
                ", installamount=" + installamount +
                ", licencepic='" + licencepic + '\'' +
                ", logopic='" + logopic + '\'' +
                ", praise='" + praise + '\'' +
                ", registercity='" + registercity + '\'' +
                ", registerdetailplace='" + registerdetailplace + '\'' +
                ", registerprovince='" + registerprovince + '\'' +
                ", repairamount=" + repairamount +
                ", reviewamount=" + reviewamount +
                ", servicetypeinstall='" + servicetypeinstall + '\'' +
                ", servicetyperepair='" + servicetyperepair + '\'' +
                ", worklevel='" + worklevel + '\'' +
                ", workyear='" + workyear + '\'' +
                ", zizhipic1='" + zizhipic1 + '\'' +
                ", zizhipic2='" + zizhipic2 + '\'' +
                ", description='" + description + '\'' +
                ", serviceplace=" + serviceplace +
                '}';
    }

    public String getBusinesstype() {
        return businesstype == null ? "" : businesstype;
    }

    public void setBusinesstype(String businesstype) {
        this.businesstype = businesstype;
    }

    public String getCompanyname() {
        return companyname == null ? "" : companyname;
    }

    public void setCompanyname(String companyname) {
        this.companyname = companyname;
    }

    public String getCompanyscale() {
        return companyscale == null ? "" : companyscale;
    }

    public void setCompanyscale(String companyscale) {
        this.companyscale = companyscale;
    }

    public String getCompanyverify() {
        return companyverify == null ? "" : companyverify;
    }

    public void setCompanyverify(String companyverify) {
        this.companyverify = companyverify;
    }

    public String getGoodpercent() {
        return goodpercent == null ? "" : goodpercent;
    }

    public void setGoodpercent(String goodpercent) {
        this.goodpercent = goodpercent;
    }

    public String getHonorpic1() {
        return honorpic1 == null ? "" : honorpic1;
    }

    public void setHonorpic1(String honorpic1) {
        this.honorpic1 = honorpic1;
    }

    public String getHonorpic2() {
        return honorpic2 == null ? "" : honorpic2;
    }

    public void setHonorpic2(String honorpic2) {
        this.honorpic2 = honorpic2;
    }

    public String getHonorpic3() {
        return honorpic3 == null ? "" : honorpic3;
    }

    public void setHonorpic3(String honorpic3) {
        this.honorpic3 = honorpic3;
    }

    public int getInstallamount() {
        return installamount;
    }

    public void setInstallamount(int installamount) {
        this.installamount = installamount;
    }

    public String getLicencepic() {
        return licencepic == null ? "" : licencepic;
    }

    public void setLicencepic(String licencepic) {
        this.licencepic = licencepic;
    }

    public String getLogopic() {
        return logopic == null ? "" : logopic;
    }

    public void setLogopic(String logopic) {
        this.logopic = logopic;
    }

    public String getPraise() {
        return praise == null ? "" : praise;
    }

    public void setPraise(String praise) {
        this.praise = praise;
    }

    public String getRegistercity() {
        return registercity == null ? "" : registercity;
    }

    public void setRegistercity(String registercity) {
        this.registercity = registercity;
    }

    public String getRegisterdetailplace() {
        return registerdetailplace == null ? "" : registerdetailplace;
    }

    public void setRegisterdetailplace(String registerdetailplace) {
        this.registerdetailplace = registerdetailplace;
    }

    public String getRegisterprovince() {
        return registerprovince == null ? "" : registerprovince;
    }

    public void setRegisterprovince(String registerprovince) {
        this.registerprovince = registerprovince;
    }

    public int getRepairamount() {
        return repairamount;
    }

    public void setRepairamount(int repairamount) {
        this.repairamount = repairamount;
    }

    public int getReviewamount() {
        return reviewamount;
    }

    public void setReviewamount(int reviewamount) {
        this.reviewamount = reviewamount;
    }

    public String getServicetypeinstall() {
        return servicetypeinstall == null ? "" : servicetypeinstall;
    }

    public void setServicetypeinstall(String servicetypeinstall) {
        this.servicetypeinstall = servicetypeinstall;
    }

    public String getServicetyperepair() {
        return servicetyperepair == null ? "" : servicetyperepair;
    }

    public void setServicetyperepair(String servicetyperepair) {
        this.servicetyperepair = servicetyperepair;
    }

    public String getWorklevel() {
        return worklevel == null ? "" : worklevel;
    }

    public void setWorklevel(String worklevel) {
        this.worklevel = worklevel;
    }

    public String getWorkyear() {
        return workyear == null ? "" : workyear;
    }

    public void setWorkyear(String workyear) {
        this.workyear = workyear;
    }

    public String getZizhipic1() {
        return zizhipic1 == null ? "" : zizhipic1;
    }

    public void setZizhipic1(String zizhipic1) {
        this.zizhipic1 = zizhipic1;
    }

    public String getZizhipic2() {
        return zizhipic2 == null ? "" : zizhipic2;
    }

    public void setZizhipic2(String zizhipic2) {
        this.zizhipic2 = zizhipic2;
    }

    public String getDescription() {
        return description == null ? "" : description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAdpic() {
        return adpic == null ? "" : adpic;
    }

    public void setAdpic(String adpic) {
        this.adpic = adpic;
    }

    public String getCollected() {
        return collected == null ? "" : collected;
    }

    public void setCollected(String collected) {
        this.collected = collected;
    }

    public List<ServiceplaceBean> getServiceplace() {
        if (serviceplace == null) {
            return new ArrayList<>();
        }
        return serviceplace;
    }

    public void setServiceplace(List<ServiceplaceBean> serviceplace) {
        this.serviceplace = serviceplace;
    }

    public static class ServiceplaceBean {
        /**
         * city : 北京
         * companyuid : 1ac77e2c643444f2ac1966d8ec868157
         * id : 1
         * zone : 海淀
         */

        private String city;
        private String companyuid;
        private int id;
        private String zone;

        public String getCity() {
            return city == null ? "" : city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCompanyuid() {
            return companyuid == null ? "" : companyuid;
        }

        public void setCompanyuid(String companyuid) {
            this.companyuid = companyuid;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getZone() {
            return zone == null ? "" : zone;
        }

        public void setZone(String zone) {
            this.zone = zone;
        }
    }
}
