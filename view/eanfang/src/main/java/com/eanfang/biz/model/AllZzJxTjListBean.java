package com.eanfang.biz.model;

import java.util.List;

public class AllZzJxTjListBean {

    @Override
    public String toString() {
        return "AllZzJxTjListBean{" +
                "companyAbilityGrantChange=" + companyAbilityGrantChange +
                ", companyToolGrantChange=" + companyToolGrantChange +
                ", orgUnit=" + orgUnit +
                '}';
    }

    /**
     * companyAbilityGrantChange : {"addList":[{"dataId":4677,"remark":"11","units":"人"},{"dataId":4678,"remark":"22","units":"人"}],"delList":[{"dataId":4681,"remark":"33","units":"人"},{"dataId":4679,"remark":"44","units":"人"}]}
     * companyToolGrantChange : {"addList":[{"dataId":4677,"remark":"11","units":"台"},{"dataId":4678,"remark":"22","units":"台"}],"delList":[{"dataId":4681,"remark":"33","units":"台"},{"dataId":4679,"remark":"44","units":"台"}]}
     * orgUnit : {"orgId":986092404613361700}
     */

    private CompanyAbilityGrantChangeBean companyAbilityGrantChange;
    private CompanyToolGrantChangeBean companyToolGrantChange;
    private OrgUnitBean orgUnit;

    public CompanyAbilityGrantChangeBean getCompanyAbilityGrantChange() {
        return companyAbilityGrantChange;
    }

    public void setCompanyAbilityGrantChange(CompanyAbilityGrantChangeBean companyAbilityGrantChange) {
        this.companyAbilityGrantChange = companyAbilityGrantChange;
    }

    public CompanyToolGrantChangeBean getCompanyToolGrantChange() {
        return companyToolGrantChange;
    }

    public void setCompanyToolGrantChange(CompanyToolGrantChangeBean companyToolGrantChange) {
        this.companyToolGrantChange = companyToolGrantChange;
    }

    public OrgUnitBean getOrgUnit() {
        return orgUnit;
    }

    public void setOrgUnit(OrgUnitBean orgUnit) {
        this.orgUnit = orgUnit;
    }

    public static class CompanyAbilityGrantChangeBean {
        private List<AddListBean> addList;
        private List<DelListBean> delList;

        @Override
        public String toString() {
            return "CompanyAbilityGrantChangeBean{" +
                    "addList=" + addList +
                    ", delList=" + delList +
                    '}';
        }

        public List<AddListBean> getAddList() {
            return addList;
        }

        public void setAddList(List<AddListBean> addList) {
            this.addList = addList;
        }

        public List<DelListBean> getDelList() {
            return delList;
        }

        public void setDelList(List<DelListBean> delList) {
            this.delList = delList;
        }

        public static class AddListBean {
            @Override
            public String toString() {
                return "AddListBean{" +
                        "dataId=" + dataId +
                        ", remark='" + remark + '\'' +
                        ", units='" + units + '\'' +
                        '}';
            }

            public AddListBean(int dataId, String remark, String units) {
                this.dataId = dataId;
                this.remark = remark;
                this.units = units;
            }

            /**
             * dataId : 4677
             * remark : 11
             * units : 人
             */

            private int dataId;
            private String remark;
            private String units;

            public int getDataId() {
                return dataId;
            }

            public void setDataId(int dataId) {
                this.dataId = dataId;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public String getUnits() {
                return units;
            }

            public void setUnits(String units) {
                this.units = units;
            }
        }

        public static class DelListBean {
            @Override
            public String toString() {
                return "DelListBean{" +
                        "dataId=" + dataId +
                        ", remark='" + remark + '\'' +
                        ", units='" + units + '\'' +
                        '}';
            }

            public DelListBean(int dataId, String remark, String units) {
                this.dataId = dataId;
                this.remark = remark;
                this.units = units;
            }

            /**
             * dataId : 4681
             * remark : 33
             * units : 人
             */

            private int dataId;
            private String remark;
            private String units;

            public int getDataId() {
                return dataId;
            }

            public void setDataId(int dataId) {
                this.dataId = dataId;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public String getUnits() {
                return units;
            }

            public void setUnits(String units) {
                this.units = units;
            }
        }
    }

    public static class CompanyToolGrantChangeBean {
        private List<AddListBeanX> addList;
        private List<DelListBeanX> delList;

        @Override
        public String toString() {
            return "CompanyToolGrantChangeBean{" +
                    "addList=" + addList +
                    ", delList=" + delList +
                    '}';
        }

        public List<AddListBeanX> getAddList() {
            return addList;
        }

        public void setAddList(List<AddListBeanX> addList) {
            this.addList = addList;
        }

        public List<DelListBeanX> getDelList() {
            return delList;
        }

        public void setDelList(List<DelListBeanX> delList) {
            this.delList = delList;
        }

        public static class AddListBeanX {
            @Override
            public String toString() {
                return "AddListBeanX{" +
                        "dataId=" + dataId +
                        ", remark='" + remark + '\'' +
                        ", units='" + units + '\'' +
                        '}';
            }

            public AddListBeanX(int dataId, String remark, String units) {
                this.dataId = dataId;
                this.remark = remark;
                this.units = units;
            }

            /**
             * dataId : 4677
             * remark : 11
             * units : 台
             */

            private int dataId;
            private String remark;
            private String units;

            public int getDataId() {
                return dataId;
            }

            public void setDataId(int dataId) {
                this.dataId = dataId;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public String getUnits() {
                return units;
            }

            public void setUnits(String units) {
                this.units = units;
            }
        }

        public static class DelListBeanX {
            @Override
            public String toString() {
                return "DelListBeanX{" +
                        "dataId=" + dataId +
                        ", remark='" + remark + '\'' +
                        ", units='" + units + '\'' +
                        '}';
            }

            public DelListBeanX(int dataId, String remark, String units) {
                this.dataId = dataId;
                this.remark = remark;
                this.units = units;
            }

            /**
             * dataId : 4681
             * remark : 33
             * units : 台
             */

            private int dataId;
            private String remark;
            private String units;

            public int getDataId() {
                return dataId;
            }

            public void setDataId(int dataId) {
                this.dataId = dataId;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public String getUnits() {
                return units;
            }

            public void setUnits(String units) {
                this.units = units;
            }
        }
    }

    public static class OrgUnitBean {
        @Override
        public String toString() {
            return "OrgUnitBean{" +
                    "orgId=" + orgId +
                    '}';
        }

        /**
         * orgId : 986092404613361700
         */

        private long orgId;

        public long getOrgId() {
            return orgId;
        }

        public void setOrgId(long orgId) {
            this.orgId = orgId;
        }
    }
}
