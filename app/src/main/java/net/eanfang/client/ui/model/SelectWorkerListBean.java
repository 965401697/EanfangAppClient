package net.eanfang.client.ui.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yaosheng on 2017/4/18.
 */

public class SelectWorkerListBean implements Serializable {

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

    public static class All1Bean {
        /**
         * companyname : 全能快修
         * goodpercent : 0.98
         * headpic : http://eanfangx.oss-cn-beijing.aliyuncs.com/387669945699owlj
         * personuid : e99d1acc503d40b2a964cc4a83fa27a2
         * praise : 4.9
         * realname : 毛总
         * workexp : 2
         */

        private String companyname;
        private String goodpercent;
        private String headpic;
        private String personuid;
        private String praise;
        private String realname;
        private String workexp;

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

        public String getHeadpic() {
            return headpic;
        }

        public void setHeadpic(String headpic) {
            this.headpic = headpic;
        }

        public String getPersonuid() {
            return personuid;
        }

        public void setPersonuid(String personuid) {
            this.personuid = personuid;
        }

        public String getPraise() {
            return praise;
        }

        public void setPraise(String praise) {
            this.praise = praise;
        }

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
        }

        public String getWorkexp() {
            return workexp;
        }

        public void setWorkexp(String workexp) {
            this.workexp = workexp;
        }
    }
}
