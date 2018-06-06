package com.eanfang.model;

import com.baozi.treerecyclerview.base.BaseItemData;

import java.io.Serializable;
import java.util.List;

/**
 * Created by O u r on 2018/5/30.
 */

public class TemplateBean extends BaseItemData implements Serializable {


    private String orgName;
    private boolean isChecked;
    private boolean flag;
    private List<Preson> presons;


    public static class Preson extends BaseItemData implements Serializable {
        private String id;
        private String protraivat;
        private String name;
        private String mobile;
        private boolean isChecked;

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getProtraivat() {
            return protraivat;
        }

        public void setProtraivat(String protraivat) {
            this.protraivat = protraivat;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isChecked() {
            return isChecked;
        }

        public void setChecked(boolean checked) {
            isChecked = checked;
        }
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public List<Preson> getPresons() {
        return presons;
    }

    public void setPresons(List<Preson> presons) {
        this.presons = presons;
    }
}