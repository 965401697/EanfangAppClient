package net.eanfang.client.ui.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yaosheng on 2017/6/23.
 */

public class CollectionWorkerListBean implements Serializable {

    private List<AllBean> all;

    public List<AllBean> getAll() {
        return all;
    }

    public void setAll(List<AllBean> all) {
        this.all = all;
    }

    public static class AllBean {
        /**
         * companyname : 全能快修
         * goodpercent : 0.90
         * headpic : http://note.youdao.com/favicon.ico
         * personuid : e99d1acc503d40b2a964cc4a83fa27a2
         * praise : 5
         * realname : 范师傅
         * workexp : 10年以上
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
