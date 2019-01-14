package com.eanfang.model.datastatistics;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

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

    @Getter
    @Setter
    public static class InstallBean {
        /**
         * count : 0
         */

        private int num;

    }

    @Getter
    @Setter
    public static class DesignBean {
        /**
         * count : 0
         */

        private int num;

    }

    @Getter
    @Setter
    public static class GroupBean {
        /**
         * count : 77
         * type : 0
         */

        private int num;
        private String typeNum;


        public String getType() {
            return typeNum;
        }

        public void setType(String typeNum) {
            this.typeNum = typeNum;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }
    }
}
