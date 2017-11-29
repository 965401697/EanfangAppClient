package net.eanfang.client.ui.model;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yaosheng on 2017/4/23.
 */

public class SelectCompanyBean implements Serializable {
    private List<All1Bean> all1;
    private List<All1Bean> all2;
    private List<All1Bean> all3;

    public List<All1Bean> getAll1() {
        return all1;
    }

    public void setAll1(List<All1Bean> all1) {
        this.all1 = all1;
    }

    public List<All1Bean> getAll2() {
        return all2;
    }

    public void setAll2(List<All1Bean> all2) {
        this.all2 = all2;
    }

    public List<All1Bean> getAll3() {
        return all3;
    }

    public void setAll3(List<All1Bean> all3) {
        this.all3 = all3;
    }

    public static class All1Bean implements MultiItemEntity,Serializable {
        /**
         * businesstypestr : 电视监控,门禁一卡通,防盗报警,可视对讲,公共广播
         * companyname : 北京腾讯公司
         * companyserviceplacelist : [{"city":"北京","companyuid":"8afb277aeed841888ee0e75c5d79e72b","id":9,"zone":"海淀区"},{"city":"北京","companyuid":"8afb277aeed841888ee0e75c5d79e72b","id":10,"zone":"朝阳区"},{"city":"北京","companyuid":"8afb277aeed841888ee0e75c5d79e72b","id":11,"zone":"昌平区"}]
         * companyuid : 8afb277aeed841888ee0e75c5d79e72b
         * detailplace : 浙江省杭州市牛逼
         * honorpic1 : http://eanfangx.oss-cn-beijing.aliyuncs.com/f6646ffaf32b4f428215cdb55f76f114.png
         * honorpic2 : http://eanfangx.oss-cn-beijing.aliyuncs.com/10e9e61225d347eda4f54e4ffb4ef9cc.png
         * logopic : http://eanfangx.oss-cn-beijing.aliyuncs.com/6db1861ae7424bff89c1e9499ee4ea70.png
         * registermoney : 388
         * servicetypestr : 维修--安装
         * worklevel : 2级
         * workyear : 3-5年
         * zizhipic1 : http://eanfangx.oss-cn-beijing.aliyuncs.com/82d3ea9ae9d847e89ed21c9495b2ed34.png
         * zizhipic2 : http://eanfangx.oss-cn-beijing.aliyuncs.com/2d356ab3e3ff4486804493ec06eae0d3.png
         * goodpercent : 1.00
         * praise : 5.0
         * repairamount : 1
         */
        public static final int FIRST = 1;
        public static final int CONTENT = 2;
        private int itemType;
        private String businesstypestr;
        private String companyname;
        private String companyuid;
        private String detailplace;
        private String honorpic1;
        private String honorpic2;
        private String logopic;
        private String registermoney;
        private String servicetypestr;
        private String worklevel;
        private String workyear;
        private String zizhipic1;
        private String zizhipic2;
        private String goodpercent;
        private String praise;
        private int repairamount;
        private List<CompanyserviceplacelistBean> companyserviceplacelist;
        private int installamount;

        public int getInstallamount() {
            return installamount;
        }

        public void setInstallamount(int installamount) {
            this.installamount = installamount;
        }

        public All1Bean(int itemType) {
            this.itemType = itemType;
        }

        @Override
        public int getItemType() {
            return itemType;
        }

        public void setItemType(int itemType) {
            this.itemType = itemType;
        }

        public String getBusinesstypestr() {
            return businesstypestr;
        }

        public void setBusinesstypestr(String businesstypestr) {
            this.businesstypestr = businesstypestr;
        }

        public String getCompanyname() {
            return companyname;
        }

        public void setCompanyname(String companyname) {
            this.companyname = companyname;
        }

        public String getCompanyuid() {
            return companyuid;
        }

        public void setCompanyuid(String companyuid) {
            this.companyuid = companyuid;
        }

        public String getDetailplace() {
            return detailplace;
        }

        public void setDetailplace(String detailplace) {
            this.detailplace = detailplace;
        }

        public String getHonorpic1() {
            return honorpic1;
        }

        public void setHonorpic1(String honorpic1) {
            this.honorpic1 = honorpic1;
        }

        public String getHonorpic2() {
            return honorpic2;
        }

        public void setHonorpic2(String honorpic2) {
            this.honorpic2 = honorpic2;
        }

        public String getLogopic() {
            return logopic;
        }

        public void setLogopic(String logopic) {
            this.logopic = logopic;
        }

        public String getRegistermoney() {
            return registermoney;
        }

        public void setRegistermoney(String registermoney) {
            this.registermoney = registermoney;
        }

        public String getServicetypestr() {
            return servicetypestr;
        }

        public void setServicetypestr(String servicetypestr) {
            this.servicetypestr = servicetypestr;
        }

        public String getWorklevel() {
            return worklevel;
        }

        public void setWorklevel(String worklevel) {
            this.worklevel = worklevel;
        }

        public String getWorkyear() {
            return workyear;
        }

        public void setWorkyear(String workyear) {
            this.workyear = workyear;
        }

        public String getZizhipic1() {
            return zizhipic1;
        }

        public void setZizhipic1(String zizhipic1) {
            this.zizhipic1 = zizhipic1;
        }

        public String getZizhipic2() {
            return zizhipic2;
        }

        public void setZizhipic2(String zizhipic2) {
            this.zizhipic2 = zizhipic2;
        }

        public String getGoodpercent() {
            return goodpercent;
        }

        public void setGoodpercent(String goodpercent) {
            this.goodpercent = goodpercent;
        }

        public String getPraise() {
            return praise;
        }

        public void setPraise(String praise) {
            this.praise = praise;
        }

        public int getRepairamount() {
            return repairamount;
        }

        public void setRepairamount(int repairamount) {
            this.repairamount = repairamount;
        }

        public List<CompanyserviceplacelistBean> getCompanyserviceplacelist() {
            return companyserviceplacelist;
        }

        public void setCompanyserviceplacelist(List<CompanyserviceplacelistBean> companyserviceplacelist) {
            this.companyserviceplacelist = companyserviceplacelist;
        }

        public static class CompanyserviceplacelistBean implements Serializable {
            /**
             * city : 北京
             * companyuid : 8afb277aeed841888ee0e75c5d79e72b
             * id : 9
             * zone : 海淀区
             */

            private String city;
            private String companyuid;
            private int id;
            private String zone;

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getCompanyuid() {
                return companyuid;
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
                return zone;
            }

            public void setZone(String zone) {
                this.zone = zone;
            }
        }
    }

    public static class All2Bean {
        /**
         * businesstypestr : 电视监控,门禁一卡通,防盗报警,可视对讲,公共广播
         * companyname : 北京腾讯公司
         * companyserviceplacelist : [{"city":"北京","companyuid":"8afb277aeed841888ee0e75c5d79e72b","id":9,"zone":"海淀区"},{"city":"北京","companyuid":"8afb277aeed841888ee0e75c5d79e72b","id":10,"zone":"朝阳区"},{"city":"北京","companyuid":"8afb277aeed841888ee0e75c5d79e72b","id":11,"zone":"昌平区"}]
         * companyuid : 8afb277aeed841888ee0e75c5d79e72b
         * detailplace : 浙江省杭州市牛逼
         * honorpic1 : http://eanfangx.oss-cn-beijing.aliyuncs.com/f6646ffaf32b4f428215cdb55f76f114.png
         * honorpic2 : http://eanfangx.oss-cn-beijing.aliyuncs.com/10e9e61225d347eda4f54e4ffb4ef9cc.png
         * logopic : http://eanfangx.oss-cn-beijing.aliyuncs.com/6db1861ae7424bff89c1e9499ee4ea70.png
         * registermoney : 388
         * servicetypestr : 维修--安装
         * worklevel : 2级
         * workyear : 3-5年
         * zizhipic1 : http://eanfangx.oss-cn-beijing.aliyuncs.com/82d3ea9ae9d847e89ed21c9495b2ed34.png
         * zizhipic2 : http://eanfangx.oss-cn-beijing.aliyuncs.com/2d356ab3e3ff4486804493ec06eae0d3.png
         */

        private String businesstypestr;
        private String companyname;
        private String companyuid;
        private String detailplace;
        private String honorpic1;
        private String honorpic2;
        private String logopic;
        private String registermoney;
        private String servicetypestr;
        private String worklevel;
        private String workyear;
        private String zizhipic1;
        private String zizhipic2;
        private List<CompanyserviceplacelistBeanX> companyserviceplacelist;

        public String getBusinesstypestr() {
            return businesstypestr;
        }

        public void setBusinesstypestr(String businesstypestr) {
            this.businesstypestr = businesstypestr;
        }

        public String getCompanyname() {
            return companyname;
        }

        public void setCompanyname(String companyname) {
            this.companyname = companyname;
        }

        public String getCompanyuid() {
            return companyuid;
        }

        public void setCompanyuid(String companyuid) {
            this.companyuid = companyuid;
        }

        public String getDetailplace() {
            return detailplace;
        }

        public void setDetailplace(String detailplace) {
            this.detailplace = detailplace;
        }

        public String getHonorpic1() {
            return honorpic1;
        }

        public void setHonorpic1(String honorpic1) {
            this.honorpic1 = honorpic1;
        }

        public String getHonorpic2() {
            return honorpic2;
        }

        public void setHonorpic2(String honorpic2) {
            this.honorpic2 = honorpic2;
        }

        public String getLogopic() {
            return logopic;
        }

        public void setLogopic(String logopic) {
            this.logopic = logopic;
        }

        public String getRegistermoney() {
            return registermoney;
        }

        public void setRegistermoney(String registermoney) {
            this.registermoney = registermoney;
        }

        public String getServicetypestr() {
            return servicetypestr;
        }

        public void setServicetypestr(String servicetypestr) {
            this.servicetypestr = servicetypestr;
        }

        public String getWorklevel() {
            return worklevel;
        }

        public void setWorklevel(String worklevel) {
            this.worklevel = worklevel;
        }

        public String getWorkyear() {
            return workyear;
        }

        public void setWorkyear(String workyear) {
            this.workyear = workyear;
        }

        public String getZizhipic1() {
            return zizhipic1;
        }

        public void setZizhipic1(String zizhipic1) {
            this.zizhipic1 = zizhipic1;
        }

        public String getZizhipic2() {
            return zizhipic2;
        }

        public void setZizhipic2(String zizhipic2) {
            this.zizhipic2 = zizhipic2;
        }

        public List<CompanyserviceplacelistBeanX> getCompanyserviceplacelist() {
            return companyserviceplacelist;
        }

        public void setCompanyserviceplacelist(List<CompanyserviceplacelistBeanX> companyserviceplacelist) {
            this.companyserviceplacelist = companyserviceplacelist;
        }

        public static class CompanyserviceplacelistBeanX {
            /**
             * city : 北京
             * companyuid : 8afb277aeed841888ee0e75c5d79e72b
             * id : 9
             * zone : 海淀区
             */

            private String city;
            private String companyuid;
            private int id;
            private String zone;

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getCompanyuid() {
                return companyuid;
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
                return zone;
            }

            public void setZone(String zone) {
                this.zone = zone;
            }
        }
    }
}
