package com.eanfang.model.datastatistics;

import java.io.Serializable;
import java.util.List;

/**
 * 描述：
 *
 * @author Guanluocang
 * @date on 2018/7/30$  14:02$
 */
public class HomeDatastisticeBean implements Serializable {


    /**
     * all : 0
     * install : {"count":0}
     * design : {"count":0}
     * group : [{"count":77,"type":"0"},{"count":1,"type":"1"},{"count":5,"type":"2"},{"count":3,"type":"3"},{"count":1,"type":"4"}]
     */

    private int all;
    private InstallBean install;
    private DesignBean design;
    private List<GroupBean> group;

    public int getAll() {
        return all;
    }

    public void setAll(int all) {
        this.all = all;
    }

    public InstallBean getInstall() {
        return install;
    }

    public void setInstall(InstallBean install) {
        this.install = install;
    }

    public DesignBean getDesign() {
        return design;
    }

    public void setDesign(DesignBean design) {
        this.design = design;
    }

    public List<GroupBean> getGroup() {
        return group;
    }

    public void setGroup(List<GroupBean> group) {
        this.group = group;
    }

    public static class InstallBean {
        /**
         * count : 0
         */

        private int count;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }

    public static class DesignBean {
        /**
         * count : 0
         */

        private int count;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }

    public static class GroupBean {
        /**
         * count : 77
         * type : 0
         */

        private int count;
        private String type;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
