package com.eanfang.biz.model.bean;

import java.util.List;

public class SecurityCompanyDetailsBean {


    @Override
    public String toString() {
        return "SecurityCompanyDetailsBean{" +
                "cases=" + cases +
                ", verifyStatus=" + verifyStatus +
                ", orgUnit=" + orgUnit +
                ", orderCounts=" + orderCounts +
                ", sysList=" + sysList +
                ", aptitudeList=" + aptitudeList +
                ", areaList=" + areaList +
                ", toolList=" + toolList +
                ", bizList=" + bizList +
                ", abilityList=" + abilityList +
                ", gloryList=" + gloryList +
                '}';
    }

    /**
     * cases : {"currPage":1,"list":[{"assigneeCompanyId":"979995434422681602","assigneeOrgCode":"c.2","assigneeTopCompanyId":"979995434422681602","assigneeUserId":"980025258942709762","budget":1,"businessOneCode":"1.2","businessOneId":11,"clientCompanyName":"莽荒纪","connector":"莽荒纪","connectorPhone":"18600000015","createTime":"2019-04-26 14:09:41","createUserId":"1064807882139119617","description":"各个","detailPlace":"西快速路辅路东150米秦皇岛市政协","id":"1121657579092639745","latitude":"39.888699","longitude":"119.5182","newOrder":0,"orderNo":"EO1904261409225","orderTag":0,"ownerCompanyId":0,"ownerOrgCode":"c","ownerTopCompanyId":0,"ownerUserId":"1064807882139119617","predictTime":4,"revertTimeLimit":4,"status":2,"zone":"3.13.3.2","zoneId":180,"editTime":"2019-04-26 10:38:27"},{"assigneeCompanyId":"979995434422681602","assigneeOrgCode":"c.2","assigneeTopCompanyId":"979995434422681602","assigneeUserId":"980025258942709762","budget":2,"businessOneCode":"1.3","businessOneId":12,"clientCompanyName":"莽荒纪","connector":"莽荒纪","connectorPhone":"18600000015","createTime":"2019-04-26 10:53:57","createUserId":"1064807882139119617","description":"需求换个号根黄瓜","detailPlace":"卢龙镇肥子路职中门市8号北京烤鸭","id":"1121608319982698497","latitude":"39.892101","longitude":"118.882594","newOrder":0,"orderNo":"EO1904261053292","orderTag":0,"ownerCompanyId":0,"ownerOrgCode":"c","ownerTopCompanyId":0,"ownerUserId":"1064807882139119617","predictTime":3,"revertTimeLimit":4,"status":2,"zone":"3.13.3.24","zoneId":186},{"assigneeCompanyId":"979995434422681602","assigneeOrgCode":"c.2","assigneeTopCompanyId":"979995434422681602","assigneeUserId":"979993412327751682","budget":3,"businessOneCode":"1.5","businessOneId":14,"clientCompanyName":"易安防18151","connector":"易安防","connectorPhone":"13089990999","createTime":"2019-04-26 10:36:29","createUserId":"1121599525605146626","description":"湖广会馆","detailPlace":"燕山路与祖山路交叉口东南100米北京烧烤","editTime":"2019-04-26 10:38:27","id":"1121603924243054593","latitude":"40.408034","longitude":"118.934125","newOrder":0,"orderNo":"EO1904261036557","orderTag":1,"ownerCompanyId":0,"ownerOrgCode":"c","ownerTopCompanyId":0,"ownerUserId":"1121599525605146626","predictTime":4,"revertTimeLimit":4,"status":2,"zone":"3.13.3.21","zoneId":183}],"pageSize":3,"totalCount":50,"totalPage":17}
     * verifyStatus : 2
     * sysList : ["电视监控","防盗报警","门禁、一卡通","可视对讲","公共广播","停车场"]
     * aptitudeList : [{"awardOrg":"颁发机构","beginDate":"2019-04-27","beginTime":"2019-04-27 13:26:11","certificateName":"证书名称","certificatePics":"default/default_avatar.png,default/default_avatar.png,default/default_avatar.png","endDate":"2019-04-27","endTime":"2019-04-27 13:26:12"}]
     * orgUnit : {"intro":"北京法安视科技有限公司","logoPic":"62fb9a88b0bb4e2a9b131e1170be7f5c.png","name":"北京法安视科技有限公司"}
     * areaList : ["北京","天津","河北省","山西省","内蒙古自治区","辽宁省","吉林省","黑龙江省","上海","江苏省","浙江省","安徽省","福建省","江西省","山东省","河南省","湖北省","湖南省","广东省","广西壮族自治区","海南省","重庆","四川省","贵州省","云南省","西藏自治区","陕西省","甘肃省","青海省","宁夏回族自治区","新疆维吾尔自治区","台湾省","香港特别行政区","澳门特别行政区"]
     * toolList : [{"companyId":"979995434422681602","dataId":4682,"dataName":"熔纤机","dataType":8,"id":6743,"remark":"3","status":0,"units":"台"},{"companyId":"979995434422681602","dataId":4683,"dataName":"小汽车","dataType":8,"id":6744,"remark":"4","status":0,"units":"台"}]
     * bizList : ["维修","安装","设计","监理","分发包","保养"]
     * abilityList : [{"companyId":"979995434422681602","dataId":4677,"dataName":"注册建造师","dataType":7,"id":6728,"remark":"11","status":0,"units":"人"},{"companyId":"979995434422681602","dataId":4678,"dataName":"初级安防员","dataType":7,"id":6729,"remark":"22","status":0,"units":"人"}]
     * orderCounts : {"aptitudePics":"logo/66ab414f8ae348028a2c9f446644d1e4.jpg","designCount":74,"evaluateNum":30,"goodRate":35000,"installCount":53,"publicPraise":171,"repairCount":172}
     * gloryList : [{"awardDate":"2019-04-27","awardOrg":"测试颁发机构","awardTime":"2019-04-27 13:28:03","honorName":"测试荣誉名称","honorPics":"default/default_avatar.png,default/default_avatar.png"}]
     */

    private CasesBean cases;
    private int verifyStatus;
    private OrgUnitBean orgUnit;
    private OrderCountsBean orderCounts;
    private List<String> sysList;
    private List<AptitudeListBean> aptitudeList;
    private List<String> areaList;
    private List<ToolListBean> toolList;
    private List<String> bizList;
    private List<AbilityListBean> abilityList;
    private List<GloryListBean> gloryList;

    public CasesBean getCases() {
        return cases;
    }

    public void setCases(CasesBean cases) {
        this.cases = cases;
    }

    public int getVerifyStatus() {
        return verifyStatus;
    }

    public void setVerifyStatus(int verifyStatus) {
        this.verifyStatus = verifyStatus;
    }

    public OrgUnitBean getOrgUnit() {
        return orgUnit;
    }

    public void setOrgUnit(OrgUnitBean orgUnit) {
        this.orgUnit = orgUnit;
    }

    public OrderCountsBean getOrderCounts() {
        return orderCounts;
    }

    public void setOrderCounts(OrderCountsBean orderCounts) {
        this.orderCounts = orderCounts;
    }

    public List<String> getSysList() {
        return sysList;
    }

    public void setSysList(List<String> sysList) {
        this.sysList = sysList;
    }

    public List<AptitudeListBean> getAptitudeList() {
        return aptitudeList;
    }

    public void setAptitudeList(List<AptitudeListBean> aptitudeList) {
        this.aptitudeList = aptitudeList;
    }

    public List<String> getAreaList() {
        return areaList;
    }

    public void setAreaList(List<String> areaList) {
        this.areaList = areaList;
    }

    public List<ToolListBean> getToolList() {
        return toolList;
    }

    public void setToolList(List<ToolListBean> toolList) {
        this.toolList = toolList;
    }

    public List<String> getBizList() {
        return bizList;
    }

    public void setBizList(List<String> bizList) {
        this.bizList = bizList;
    }

    public List<AbilityListBean> getAbilityList() {
        return abilityList;
    }

    public void setAbilityList(List<AbilityListBean> abilityList) {
        this.abilityList = abilityList;
    }

    public List<GloryListBean> getGloryList() {
        return gloryList;
    }

    public void setGloryList(List<GloryListBean> gloryList) {
        this.gloryList = gloryList;
    }

    public static class CasesBean {
        @Override
        public String toString() {
            return "CasesBean{" +
                    "currPage=" + currPage +
                    ", pageSize=" + pageSize +
                    ", totalCount=" + totalCount +
                    ", totalPage=" + totalPage +
                    ", list=" + list +
                    '}';
        }

        /**
         * currPage : 1
         * list : [{"assigneeCompanyId":"979995434422681602","assigneeOrgCode":"c.2","assigneeTopCompanyId":"979995434422681602","assigneeUserId":"980025258942709762","budget":1,"businessOneCode":"1.2","businessOneId":11,"clientCompanyName":"莽荒纪","connector":"莽荒纪","connectorPhone":"18600000015","createTime":"2019-04-26 14:09:41","createUserId":"1064807882139119617","description":"各个","detailPlace":"西快速路辅路东150米秦皇岛市政协","id":"1121657579092639745","latitude":"39.888699","longitude":"119.5182","newOrder":0,"orderNo":"EO1904261409225","orderTag":0,"ownerCompanyId":0,"ownerOrgCode":"c","ownerTopCompanyId":0,"ownerUserId":"1064807882139119617","predictTime":4,"revertTimeLimit":4,"status":2,"zone":"3.13.3.2","zoneId":180},{"assigneeCompanyId":"979995434422681602","assigneeOrgCode":"c.2","assigneeTopCompanyId":"979995434422681602","assigneeUserId":"980025258942709762","budget":2,"businessOneCode":"1.3","businessOneId":12,"clientCompanyName":"莽荒纪","connector":"莽荒纪","connectorPhone":"18600000015","createTime":"2019-04-26 10:53:57","createUserId":"1064807882139119617","description":"需求换个号根黄瓜","detailPlace":"卢龙镇肥子路职中门市8号北京烤鸭","id":"1121608319982698497","latitude":"39.892101","longitude":"118.882594","newOrder":0,"orderNo":"EO1904261053292","orderTag":0,"ownerCompanyId":0,"ownerOrgCode":"c","ownerTopCompanyId":0,"ownerUserId":"1064807882139119617","predictTime":3,"revertTimeLimit":4,"status":2,"zone":"3.13.3.24","zoneId":186},{"assigneeCompanyId":"979995434422681602","assigneeOrgCode":"c.2","assigneeTopCompanyId":"979995434422681602","assigneeUserId":"979993412327751682","budget":3,"businessOneCode":"1.5","businessOneId":14,"clientCompanyName":"易安防18151","connector":"易安防","connectorPhone":"13089990999","createTime":"2019-04-26 10:36:29","createUserId":"1121599525605146626","description":"湖广会馆","detailPlace":"燕山路与祖山路交叉口东南100米北京烧烤","editTime":"2019-04-26 10:38:27","id":"1121603924243054593","latitude":"40.408034","longitude":"118.934125","newOrder":0,"orderNo":"EO1904261036557","orderTag":1,"ownerCompanyId":0,"ownerOrgCode":"c","ownerTopCompanyId":0,"ownerUserId":"1121599525605146626","predictTime":4,"revertTimeLimit":4,"status":2,"zone":"3.13.3.21","zoneId":183}]
         * pageSize : 3
         * totalCount : 50
         * totalPage : 17
         */

        private int currPage;
        private int pageSize;
        private int totalCount;
        private int totalPage;
        private List<ListBean> list;

        public int getCurrPage() {
            return currPage;
        }

        public void setCurrPage(int currPage) {
            this.currPage = currPage;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(int totalCount) {
            this.totalCount = totalCount;
        }

        public int getTotalPage() {
            return totalPage;
        }

        public void setTotalPage(int totalPage) {
            this.totalPage = totalPage;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * assigneeCompanyId : 979995434422681602
             * assigneeOrgCode : c.2
             * assigneeTopCompanyId : 979995434422681602
             * assigneeUserId : 980025258942709762
             * budget : 1
             * businessOneCode : 1.2
             * businessOneId : 11
             * clientCompanyName : 莽荒纪
             * connector : 莽荒纪
             * connectorPhone : 18600000015
             * createTime : 2019-04-26 14:09:41
             * createUserId : 1064807882139119617
             * description : 各个
             * detailPlace : 西快速路辅路东150米秦皇岛市政协
             * id : 1121657579092639745
             * latitude : 39.888699
             * longitude : 119.5182
             * newOrder : 0
             * orderNo : EO1904261409225
             * orderTag : 0
             * ownerCompanyId : 0
             * ownerOrgCode : c
             * ownerTopCompanyId : 0
             * ownerUserId : 1064807882139119617
             * predictTime : 4
             * revertTimeLimit : 4
             * status : 2
             * zone : 3.13.3.2
             * zoneId : 180
             * editTime : 2019-04-26 10:38:27
             */

            private String assigneeCompanyId;
            private String assigneeOrgCode;
            private String assigneeTopCompanyId;
            private String assigneeUserId;
            private int budget;
            private String businessOneCode;
            private int businessOneId;
            private String clientCompanyName;
            private String connector;
            private String connectorPhone;
            private String createTime;
            private String createUserId;
            private String description;
            private String detailPlace;
            private String id;
            private String latitude;
            private String longitude;
            private int newOrder;
            private String orderNo;
            private int orderTag;
            private int ownerCompanyId;
            private String ownerOrgCode;
            private int ownerTopCompanyId;
            private String ownerUserId;
            private int predictTime;
            private int revertTimeLimit;
            private int status;
            private String zone;
            private int zoneId;
            private String editTime;

            public String getAssigneeCompanyId() {
                return assigneeCompanyId;
            }

            public void setAssigneeCompanyId(String assigneeCompanyId) {
                this.assigneeCompanyId = assigneeCompanyId;
            }

            public String getAssigneeOrgCode() {
                return assigneeOrgCode;
            }

            public void setAssigneeOrgCode(String assigneeOrgCode) {
                this.assigneeOrgCode = assigneeOrgCode;
            }

            public String getAssigneeTopCompanyId() {
                return assigneeTopCompanyId;
            }

            public void setAssigneeTopCompanyId(String assigneeTopCompanyId) {
                this.assigneeTopCompanyId = assigneeTopCompanyId;
            }

            public String getAssigneeUserId() {
                return assigneeUserId;
            }

            public void setAssigneeUserId(String assigneeUserId) {
                this.assigneeUserId = assigneeUserId;
            }

            public int getBudget() {
                return budget;
            }

            public void setBudget(int budget) {
                this.budget = budget;
            }

            public String getBusinessOneCode() {
                return businessOneCode;
            }

            public void setBusinessOneCode(String businessOneCode) {
                this.businessOneCode = businessOneCode;
            }

            public int getBusinessOneId() {
                return businessOneId;
            }

            public void setBusinessOneId(int businessOneId) {
                this.businessOneId = businessOneId;
            }

            public String getClientCompanyName() {
                return clientCompanyName;
            }

            public void setClientCompanyName(String clientCompanyName) {
                this.clientCompanyName = clientCompanyName;
            }

            public String getConnector() {
                return connector;
            }

            public void setConnector(String connector) {
                this.connector = connector;
            }

            public String getConnectorPhone() {
                return connectorPhone;
            }

            public void setConnectorPhone(String connectorPhone) {
                this.connectorPhone = connectorPhone;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getCreateUserId() {
                return createUserId;
            }

            public void setCreateUserId(String createUserId) {
                this.createUserId = createUserId;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getDetailPlace() {
                return detailPlace;
            }

            public void setDetailPlace(String detailPlace) {
                this.detailPlace = detailPlace;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getLatitude() {
                return latitude;
            }

            public void setLatitude(String latitude) {
                this.latitude = latitude;
            }

            public String getLongitude() {
                return longitude;
            }

            public void setLongitude(String longitude) {
                this.longitude = longitude;
            }

            public int getNewOrder() {
                return newOrder;
            }

            public void setNewOrder(int newOrder) {
                this.newOrder = newOrder;
            }

            public String getOrderNo() {
                return orderNo;
            }

            public void setOrderNo(String orderNo) {
                this.orderNo = orderNo;
            }

            public int getOrderTag() {
                return orderTag;
            }

            public void setOrderTag(int orderTag) {
                this.orderTag = orderTag;
            }

            public int getOwnerCompanyId() {
                return ownerCompanyId;
            }

            public void setOwnerCompanyId(int ownerCompanyId) {
                this.ownerCompanyId = ownerCompanyId;
            }

            public String getOwnerOrgCode() {
                return ownerOrgCode;
            }

            public void setOwnerOrgCode(String ownerOrgCode) {
                this.ownerOrgCode = ownerOrgCode;
            }

            public int getOwnerTopCompanyId() {
                return ownerTopCompanyId;
            }

            public void setOwnerTopCompanyId(int ownerTopCompanyId) {
                this.ownerTopCompanyId = ownerTopCompanyId;
            }

            public String getOwnerUserId() {
                return ownerUserId;
            }

            public void setOwnerUserId(String ownerUserId) {
                this.ownerUserId = ownerUserId;
            }

            public int getPredictTime() {
                return predictTime;
            }

            public void setPredictTime(int predictTime) {
                this.predictTime = predictTime;
            }

            public int getRevertTimeLimit() {
                return revertTimeLimit;
            }

            public void setRevertTimeLimit(int revertTimeLimit) {
                this.revertTimeLimit = revertTimeLimit;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getZone() {
                return zone;
            }

            public void setZone(String zone) {
                this.zone = zone;
            }

            public int getZoneId() {
                return zoneId;
            }

            public void setZoneId(int zoneId) {
                this.zoneId = zoneId;
            }

            public String getEditTime() {
                return editTime;
            }

            public void setEditTime(String editTime) {
                this.editTime = editTime;
            }
        }
    }

    public static class OrgUnitBean {
        @Override
        public String toString() {
            return "OrgUnitBean{" +
                    "intro='" + intro + '\'' +
                    ", logoPic='" + logoPic + '\'' +
                    ", name='" + name + '\'' +
                    '}';
        }

        /**
         * intro : 北京法安视科技有限公司
         * logoPic : 62fb9a88b0bb4e2a9b131e1170be7f5c.png
         * name : 北京法安视科技有限公司
         */

        private String intro;
        private String logoPic;
        private String name;

        public String getIntro() {
            return intro;
        }

        public void setIntro(String intro) {
            this.intro = intro;
        }

        public String getLogoPic() {
            return logoPic;
        }

        public void setLogoPic(String logoPic) {
            this.logoPic = logoPic;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class OrderCountsBean {
        @Override
        public String toString() {
            return "OrderCountsBean{" +
                    "aptitudePics='" + aptitudePics + '\'' +
                    ", designCount=" + designCount +
                    ", evaluateNum=" + evaluateNum +
                    ", goodRate=" + goodRate +
                    ", installCount=" + installCount +
                    ", publicPraise=" + publicPraise +
                    ", repairCount=" + repairCount +
                    '}';
        }

        /**
         * aptitudePics : logo/66ab414f8ae348028a2c9f446644d1e4.jpg
         * designCount : 74
         * evaluateNum : 30
         * goodRate : 35000
         * installCount : 53
         * publicPraise : 171
         * repairCount : 172
         */

        private String aptitudePics;
        private int designCount;
        private int evaluateNum;
        private int goodRate;
        private int installCount;
        private int publicPraise;
        private int repairCount;

        public String getAptitudePics() {
            return aptitudePics;
        }

        public void setAptitudePics(String aptitudePics) {
            this.aptitudePics = aptitudePics;
        }

        public int getDesignCount() {
            return designCount;
        }

        public void setDesignCount(int designCount) {
            this.designCount = designCount;
        }

        public int getEvaluateNum() {
            return evaluateNum;
        }

        public void setEvaluateNum(int evaluateNum) {
            this.evaluateNum = evaluateNum;
        }

        public int getGoodRate() {
            return goodRate;
        }

        public void setGoodRate(int goodRate) {
            this.goodRate = goodRate;
        }

        public int getInstallCount() {
            return installCount;
        }

        public void setInstallCount(int installCount) {
            this.installCount = installCount;
        }

        public int getPublicPraise() {
            return publicPraise;
        }

        public void setPublicPraise(int publicPraise) {
            this.publicPraise = publicPraise;
        }

        public int getRepairCount() {
            return repairCount;
        }

        public void setRepairCount(int repairCount) {
            this.repairCount = repairCount;
        }
    }

    public static class AptitudeListBean {
        @Override
        public String toString() {
            return "AptitudeListBean{" +
                    "awardOrg='" + awardOrg + '\'' +
                    ", beginDate='" + beginDate + '\'' +
                    ", beginTime='" + beginTime + '\'' +
                    ", certificateName='" + certificateName + '\'' +
                    ", certificatePics='" + certificatePics + '\'' +
                    ", endDate='" + endDate + '\'' +
                    ", endTime='" + endTime + '\'' +
                    '}';
        }

        /**
         * awardOrg : 颁发机构
         * beginDate : 2019-04-27
         * beginTime : 2019-04-27 13:26:11
         * certificateName : 证书名称
         * certificatePics : default/default_avatar.png,default/default_avatar.png,default/default_avatar.png
         * endDate : 2019-04-27
         * endTime : 2019-04-27 13:26:12
         */

        private String awardOrg;
        private String beginDate;
        private String beginTime;
        private String certificateName;
        private String certificatePics;
        private String endDate;
        private String endTime;

        public String getAwardOrg() {
            return awardOrg;
        }

        public void setAwardOrg(String awardOrg) {
            this.awardOrg = awardOrg;
        }

        public String getBeginDate() {
            return beginDate;
        }

        public void setBeginDate(String beginDate) {
            this.beginDate = beginDate;
        }

        public String getBeginTime() {
            return beginTime;
        }

        public void setBeginTime(String beginTime) {
            this.beginTime = beginTime;
        }

        public String getCertificateName() {
            return certificateName;
        }

        public void setCertificateName(String certificateName) {
            this.certificateName = certificateName;
        }

        public String getCertificatePics() {
            return certificatePics;
        }

        public void setCertificatePics(String certificatePics) {
            this.certificatePics = certificatePics;
        }

        public String getEndDate() {
            return endDate;
        }

        public void setEndDate(String endDate) {
            this.endDate = endDate;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }
    }

    public static class ToolListBean {
        @Override
        public String toString() {
            return "ToolListBean{" +
                    "companyId='" + companyId + '\'' +
                    ", dataId=" + dataId +
                    ", dataName='" + dataName + '\'' +
                    ", dataType=" + dataType +
                    ", id=" + id +
                    ", remark='" + remark + '\'' +
                    ", status=" + status +
                    ", units='" + units + '\'' +
                    '}';
        }

        /**
         * companyId : 979995434422681602
         * dataId : 4682
         * dataName : 熔纤机
         * dataType : 8
         * id : 6743
         * remark : 3
         * status : 0
         * units : 台
         */

        private String companyId;
        private int dataId;
        private String dataName;
        private int dataType;
        private int id;
        private String remark;
        private int status;
        private String units;

        public String getCompanyId() {
            return companyId;
        }

        public void setCompanyId(String companyId) {
            this.companyId = companyId;
        }

        public int getDataId() {
            return dataId;
        }

        public void setDataId(int dataId) {
            this.dataId = dataId;
        }

        public String getDataName() {
            return dataName;
        }

        public void setDataName(String dataName) {
            this.dataName = dataName;
        }

        public int getDataType() {
            return dataType;
        }

        public void setDataType(int dataType) {
            this.dataType = dataType;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getUnits() {
            return units;
        }

        public void setUnits(String units) {
            this.units = units;
        }
    }

    public static class AbilityListBean {
        @Override
        public String toString() {
            return "AbilityListBean{" +
                    "companyId='" + companyId + '\'' +
                    ", dataId=" + dataId +
                    ", dataName='" + dataName + '\'' +
                    ", dataType=" + dataType +
                    ", id=" + id +
                    ", remark='" + remark + '\'' +
                    ", status=" + status +
                    ", units='" + units + '\'' +
                    '}';
        }

        /**
         * companyId : 979995434422681602
         * dataId : 4677
         * dataName : 注册建造师
         * dataType : 7
         * id : 6728
         * remark : 11
         * status : 0
         * units : 人
         */

        private String companyId;
        private int dataId;
        private String dataName;
        private int dataType;
        private int id;
        private String remark;
        private int status;
        private String units;

        public String getCompanyId() {
            return companyId;
        }

        public void setCompanyId(String companyId) {
            this.companyId = companyId;
        }

        public int getDataId() {
            return dataId;
        }

        public void setDataId(int dataId) {
            this.dataId = dataId;
        }

        public String getDataName() {
            return dataName;
        }

        public void setDataName(String dataName) {
            this.dataName = dataName;
        }

        public int getDataType() {
            return dataType;
        }

        public void setDataType(int dataType) {
            this.dataType = dataType;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getUnits() {
            return units;
        }

        public void setUnits(String units) {
            this.units = units;
        }
    }

    public static class GloryListBean {
        @Override
        public String toString() {
            return "GloryListBean{" +
                    "awardDate='" + awardDate + '\'' +
                    ", awardOrg='" + awardOrg + '\'' +
                    ", awardTime='" + awardTime + '\'' +
                    ", honorName='" + honorName + '\'' +
                    ", honorPics='" + honorPics + '\'' +
                    '}';
        }

        /**
         * awardDate : 2019-04-27
         * awardOrg : 测试颁发机构
         * awardTime : 2019-04-27 13:28:03
         * honorName : 测试荣誉名称
         * honorPics : default/default_avatar.png,default/default_avatar.png
         */

        private String awardDate;
        private String awardOrg;
        private String awardTime;
        private String honorName;
        private String honorPics;

        public String getAwardDate() {
            return awardDate;
        }

        public void setAwardDate(String awardDate) {
            this.awardDate = awardDate;
        }

        public String getAwardOrg() {
            return awardOrg;
        }

        public void setAwardOrg(String awardOrg) {
            this.awardOrg = awardOrg;
        }

        public String getAwardTime() {
            return awardTime;
        }

        public void setAwardTime(String awardTime) {
            this.awardTime = awardTime;
        }

        public String getHonorName() {
            return honorName;
        }

        public void setHonorName(String honorName) {
            this.honorName = honorName;
        }

        public String getHonorPics() {
            return honorPics;
        }

        public void setHonorPics(String honorPics) {
            this.honorPics = honorPics;
        }
    }
}
