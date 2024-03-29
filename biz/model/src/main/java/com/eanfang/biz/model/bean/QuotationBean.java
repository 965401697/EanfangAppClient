package com.eanfang.biz.model.bean;

import com.eanfang.biz.model.entity.UserEntity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wen on 2017/4/15.
 */

public class QuotationBean implements Serializable {
    /**
     * orderId : 1
     * orderNum : QO201712201002
     * reportType : 0
     * repairOrderNum : MO201212120012
     * projectName : 维修门禁
     * quoteCost : 300
     * invoiceCost : 20
     * totalCost : 220
     * reporter : 李旭
     * reporterPhone : 15940525612
     * assigneeUserId : 1
     * assigneeTopCompanyId : 1100
     * assigneeOrgCode : c.c1.2
     * quoteDevices : [{"shopDeviceId":1,"modelCode":"1.1","unit":"台","count":4,"unitPrice":500,"sum":2000,"params":[{"shopDeviceParamId":1,"paramValue":"35V","paramName":"电压"},{"shopDeviceParamId":2,"paramValue":"35℃","paramName":"温度"}]}]
     * quoteParts : [{"shopAccessoriesPartId":1,"shopPartSpecificationId":22,"unit":"件","count":1,"unitPrice":500,"sum":500,"partSpeciication":"中型配件"},{"shopAccessoriesPartId":2,"shopPartSpecificationId":18,"unit":"个","count":1,"unitPrice":600,"sum":600,"partSpeciication":"小型配件"}]
     * quoteServices : [{"shopServiceId":1,"serviceName":"爬高服务","serviceContent":"安装摄像头时进行攀爬","servicePrice":500,"serviceTime":2,"serviceValue":"5米以下","shopServicePriceId":23,"sum":1000},{"shopServiceId":2,"serviceName":"清洁服务","serviceContent":"清理设备灰尘","servicePrice":400,"serviceTime":1,"serviceValue":"1小时","shopServicePriceId":13,"sum":400}]
     */

    private Long orderId;
    private String orderNum;
    private int reportType;
    private String repairOrderNum;
    private String projectName;
    private int quoteCost;
    private int invoiceCost;
    private int totalCost;
    private String reporter;
    private String reporterPhone;
    private Long assigneeUserId;
    private Long assigneeTopCompanyId;
    private String assigneeOrgCode;
    private String zone_code;
    private Long zone_id;
    private String longitude;
    private String latitude;
    private String detail_place;
    private Long owner_company_id;
    private Long assignee_company_id;
    private List<QuoteDevicesBean> quoteDevices;
    private List<QuotePartsBean> quoteParts;
    private List<QuoteServicesBean> quoteServices;
    private UserEntity offerer;
    //用户名称
    private String clientName;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getOrderNum() {
        return orderNum == null ? "" : orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public int getReportType() {
        return reportType;
    }

    public void setReportType(int reportType) {
        this.reportType = reportType;
    }

    public String getRepairOrderNum() {
        return repairOrderNum == null ? "" : repairOrderNum;
    }

    public void setRepairOrderNum(String repairOrderNum) {
        this.repairOrderNum = repairOrderNum;
    }

    public String getProjectName() {
        return projectName == null ? "" : projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public int getQuoteCost() {
        return quoteCost;
    }

    public void setQuoteCost(int quoteCost) {
        this.quoteCost = quoteCost;
    }

    public int getInvoiceCost() {
        return invoiceCost;
    }

    public void setInvoiceCost(int invoiceCost) {
        this.invoiceCost = invoiceCost;
    }

    public int getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(int totalCost) {
        this.totalCost = totalCost;
    }

    public String getReporter() {
        return reporter == null ? "" : reporter;
    }

    public void setReporter(String reporter) {
        this.reporter = reporter;
    }

    public String getReporterPhone() {
        return reporterPhone == null ? "" : reporterPhone;
    }

    public void setReporterPhone(String reporterPhone) {
        this.reporterPhone = reporterPhone;
    }

    public Long getAssigneeUserId() {
        return assigneeUserId;
    }

    public void setAssigneeUserId(Long assigneeUserId) {
        this.assigneeUserId = assigneeUserId;
    }

    public Long getAssigneeTopCompanyId() {
        return assigneeTopCompanyId;
    }

    public void setAssigneeTopCompanyId(Long assigneeTopCompanyId) {
        this.assigneeTopCompanyId = assigneeTopCompanyId;
    }

    public String getAssigneeOrgCode() {
        return assigneeOrgCode == null ? "" : assigneeOrgCode;
    }

    public void setAssigneeOrgCode(String assigneeOrgCode) {
        this.assigneeOrgCode = assigneeOrgCode;
    }

    public String getZone_code() {
        return zone_code == null ? "" : zone_code;
    }

    public void setZone_code(String zone_code) {
        this.zone_code = zone_code;
    }

    public Long getZone_id() {
        return zone_id;
    }

    public void setZone_id(Long zone_id) {
        this.zone_id = zone_id;
    }

    public String getLongitude() {
        return longitude == null ? "" : longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude == null ? "" : latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getDetail_place() {
        return detail_place == null ? "" : detail_place;
    }

    public void setDetail_place(String detail_place) {
        this.detail_place = detail_place;
    }

    public Long getOwner_company_id() {
        return owner_company_id;
    }

    public void setOwner_company_id(Long owner_company_id) {
        this.owner_company_id = owner_company_id;
    }

    public Long getAssignee_company_id() {
        return assignee_company_id;
    }

    public void setAssignee_company_id(Long assignee_company_id) {
        this.assignee_company_id = assignee_company_id;
    }

    public List<QuoteDevicesBean> getQuoteDevices() {
        if (quoteDevices == null) {
            return new ArrayList<>();
        }
        return quoteDevices;
    }

    public void setQuoteDevices(List<QuoteDevicesBean> quoteDevices) {
        this.quoteDevices = quoteDevices;
    }

    public List<QuotePartsBean> getQuoteParts() {
        if (quoteParts == null) {
            return new ArrayList<>();
        }
        return quoteParts;
    }

    public void setQuoteParts(List<QuotePartsBean> quoteParts) {
        this.quoteParts = quoteParts;
    }

    public List<QuoteServicesBean> getQuoteServices() {
        if (quoteServices == null) {
            return new ArrayList<>();
        }
        return quoteServices;
    }

    public void setQuoteServices(List<QuoteServicesBean> quoteServices) {
        this.quoteServices = quoteServices;
    }

    public UserEntity getOfferer() {
        return offerer;
    }

    public void setOfferer(UserEntity offerer) {
        this.offerer = offerer;
    }

    public String getClientName() {
        return clientName == null ? "" : clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public static class QuoteDevicesBean implements Serializable {
        /**
         * shopDeviceId : 1
         * modelCode : 1.1
         * unit : 台
         * count : 4
         * unitPrice : 500
         * sum : 2000
         * params : [{"shopDeviceParamId":1,"paramValue":"35V","paramName":"电压"},{"shopDeviceParamId":2,"paramValue":"35℃","paramName":"温度"}]
         */

        private Long shopDeviceId;
        private String modelCode;
        private int unit;
        private int count;
        private int unitPrice;
        private int sum;
        private List<ParamsBean> params;
        private String producerName;
        private String producerPlace;
        private String remarkInfo;
        private String business_three_code;

        public Long getShopDeviceId() {
            return shopDeviceId;
        }

        public void setShopDeviceId(Long shopDeviceId) {
            this.shopDeviceId = shopDeviceId;
        }

        public String getModelCode() {
            return modelCode == null ? "" : modelCode;
        }

        public void setModelCode(String modelCode) {
            this.modelCode = modelCode;
        }

        public int getUnit() {
            return unit;
        }

        public void setUnit(int unit) {
            this.unit = unit;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public int getUnitPrice() {
            return unitPrice;
        }

        public void setUnitPrice(int unitPrice) {
            this.unitPrice = unitPrice;
        }

        public int getSum() {
            return sum;
        }

        public void setSum(int sum) {
            this.sum = sum;
        }

        public List<ParamsBean> getParams() {
            if (params == null) {
                return new ArrayList<>();
            }
            return params;
        }

        public void setParams(List<ParamsBean> params) {
            this.params = params;
        }

        public String getProducerName() {
            return producerName == null ? "" : producerName;
        }

        public void setProducerName(String producerName) {
            this.producerName = producerName;
        }

        public String getProducerPlace() {
            return producerPlace == null ? "" : producerPlace;
        }

        public void setProducerPlace(String producerPlace) {
            this.producerPlace = producerPlace;
        }

        public String getRemarkInfo() {
            return remarkInfo == null ? "" : remarkInfo;
        }

        public void setRemarkInfo(String remarkInfo) {
            this.remarkInfo = remarkInfo;
        }

        public String getBusiness_three_code() {
            return business_three_code == null ? "" : business_three_code;
        }

        public void setBusiness_three_code(String business_three_code) {
            this.business_three_code = business_three_code;
        }

        public static class ParamsBean implements Serializable {
            /**
             * shopDeviceParamId : 1
             * paramValue : 35V
             * paramName : 电压
             */

            private Long shopDeviceParamId;
            private String paramValue;
            private String paramName;

            public Long getShopDeviceParamId() {
                return shopDeviceParamId;
            }

            public void setShopDeviceParamId(Long shopDeviceParamId) {
                this.shopDeviceParamId = shopDeviceParamId;
            }

            public String getParamValue() {
                return paramValue == null ? "" : paramValue;
            }

            public void setParamValue(String paramValue) {
                this.paramValue = paramValue;
            }

            public String getParamName() {
                return paramName == null ? "" : paramName;
            }

            public void setParamName(String paramName) {
                this.paramName = paramName;
            }
        }
    }

    public static class QuotePartsBean implements Serializable {
        /**
         * shopAccessoriesPartId : 1
         * shopPartSpecificationId : 22
         * unit : 件
         * count : 1
         * unitPrice : 500
         * sum : 500
         * partSpeciication : 中型配件
         */

        private Long shopAccessoriesPartId;
        private Long shopPartSpecificationId;
        private int unit;
        private int count;
        private int unitPrice;
        private int sum;
        private String partSpeciication;
        //配件名称
        private String partName;

        public Long getShopAccessoriesPartId() {
            return shopAccessoriesPartId;
        }

        public void setShopAccessoriesPartId(Long shopAccessoriesPartId) {
            this.shopAccessoriesPartId = shopAccessoriesPartId;
        }

        public Long getShopPartSpecificationId() {
            return shopPartSpecificationId;
        }

        public void setShopPartSpecificationId(Long shopPartSpecificationId) {
            this.shopPartSpecificationId = shopPartSpecificationId;
        }

        public int getUnit() {
            return unit;
        }

        public void setUnit(int unit) {
            this.unit = unit;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public int getUnitPrice() {
            return unitPrice;
        }

        public void setUnitPrice(int unitPrice) {
            this.unitPrice = unitPrice;
        }

        public int getSum() {
            return sum;
        }

        public void setSum(int sum) {
            this.sum = sum;
        }

        public String getPartSpeciication() {
            return partSpeciication == null ? "" : partSpeciication;
        }

        public void setPartSpeciication(String partSpeciication) {
            this.partSpeciication = partSpeciication;
        }

        public String getPartName() {
            return partName == null ? "" : partName;
        }

        public void setPartName(String partName) {
            this.partName = partName;
        }
    }

    public static class QuoteServicesBean implements Serializable {
        /**
         * shopServiceId : 1
         * serviceName : 爬高服务
         * serviceContent : 安装摄像头时进行攀爬
         * servicePrice : 500
         * serviceTime : 2
         * serviceValue : 5米以下
         * shopServicePriceId : 23
         * sum : 1000
         */

        private Long shopServiceId;
        private String serviceName;
        private String serviceContent;
        private int servicePrice;
        private int serviceTime;
        private String serviceValue;
        private Long shopServicePriceId;
        private int sum;

        public Long getShopServiceId() {
            return shopServiceId;
        }

        public void setShopServiceId(Long shopServiceId) {
            this.shopServiceId = shopServiceId;
        }

        public String getServiceName() {
            return serviceName == null ? "" : serviceName;
        }

        public void setServiceName(String serviceName) {
            this.serviceName = serviceName;
        }

        public String getServiceContent() {
            return serviceContent == null ? "" : serviceContent;
        }

        public void setServiceContent(String serviceContent) {
            this.serviceContent = serviceContent;
        }

        public int getServicePrice() {
            return servicePrice;
        }

        public void setServicePrice(int servicePrice) {
            this.servicePrice = servicePrice;
        }

        public int getServiceTime() {
            return serviceTime;
        }

        public void setServiceTime(int serviceTime) {
            this.serviceTime = serviceTime;
        }

        public String getServiceValue() {
            return serviceValue == null ? "" : serviceValue;
        }

        public void setServiceValue(String serviceValue) {
            this.serviceValue = serviceValue;
        }

        public Long getShopServicePriceId() {
            return shopServicePriceId;
        }

        public void setShopServicePriceId(Long shopServicePriceId) {
            this.shopServicePriceId = shopServicePriceId;
        }

        public int getSum() {
            return sum;
        }

        public void setSum(int sum) {
            this.sum = sum;
        }
    }
}
