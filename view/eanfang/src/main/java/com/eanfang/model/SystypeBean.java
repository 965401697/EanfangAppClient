package com.eanfang.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by MrHou
 *
 * @on 2018/1/29  21:09
 * @email houzhongzhou@yeah.net
 * @desc
 */

public class SystypeBean implements Serializable {


    private List<Integer> expand;
    private List<ListBean> list;

    public List<Integer> getExpand() {
        if (expand == null) {
            return new ArrayList<>();
        }
        return expand;
    }

    public void setExpand(List<Integer> expand) {
        this.expand = expand;
    }

    public List<ListBean> getList() {
        if (list == null) {
            return new ArrayList<>();
        }
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean implements Serializable{
        /**
         * companyId : 956448410058706946
         * dataId : 10
         * dataType : 1
         * id : 139
         * status : 0
         */

        private String companyId;
        private Integer dataId;
        private int dataType;
        private Long id;
        private int status;

        public String getCompanyId() {
            return companyId == null ? "" : companyId;
        }

        public void setCompanyId(String companyId) {
            this.companyId = companyId;
        }

        public Integer getDataId() {
            return dataId;
        }

        public void setDataId(Integer dataId) {
            this.dataId = dataId;
        }

        public int getDataType() {
            return dataType;
        }

        public void setDataType(int dataType) {
            this.dataType = dataType;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}



