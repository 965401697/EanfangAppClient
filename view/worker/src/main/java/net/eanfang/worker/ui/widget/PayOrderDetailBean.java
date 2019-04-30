package net.eanfang.worker.ui.widget;

import com.yaf.sys.entity.UserEntity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yaosheng on 2017/5/21.
 */

public class PayOrderDetailBean implements Serializable {
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
    private List<QuoteDevicesBean> quoteDevices;
    private List<QuotePartsBean> quoteParts;
    private List<QuoteServicesBean> quoteServices;
    private UserEntity offerer;
    //用户名称
    private String clientName;

    public UserEntity getOfferer() {
        return offerer;
    }

    public void setOfferer(UserEntity offerer) {
        this.offerer = offerer;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getOrderNum() {
        return orderNum;
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
        return repairOrderNum;
    }

    public void setRepairOrderNum(String repairOrderNum) {
        this.repairOrderNum = repairOrderNum;
    }

    public String getProjectName() {
        return projectName;
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
        return reporter;
    }

    public void setReporter(String reporter) {
        this.reporter = reporter;
    }

    public String getReporterPhone() {
        return reporterPhone;
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
        return assigneeOrgCode;
    }

    public void setAssigneeOrgCode(String assigneeOrgCode) {
        this.assigneeOrgCode = assigneeOrgCode;
    }

    public List<QuoteDevicesBean> getQuoteDevices() {
        return quoteDevices;
    }

    public void setQuoteDevices(List<QuoteDevicesBean> quoteDevices) {
        this.quoteDevices = quoteDevices;
    }

    public List<QuotePartsBean> getQuoteParts() {
        return quoteParts;
    }

    public void setQuoteParts(List<QuotePartsBean> quoteParts) {
        this.quoteParts = quoteParts;
    }

    public List<QuoteServicesBean> getQuoteServices() {
        return quoteServices;
    }

    public void setQuoteServices(List<QuoteServicesBean> quoteServices) {
        this.quoteServices = quoteServices;
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

        public String getProducerName() {
            return producerName;
        }

        public void setProducerName(String producerName) {
            this.producerName = producerName;
        }

        public String getProducerPlace() {
            return producerPlace;
        }

        public void setProducerPlace(String producerPlace) {
            this.producerPlace = producerPlace;
        }

        public String getRemarkInfo() {
            return remarkInfo;
        }

        public void setRemarkInfo(String remark_info) {
            this.remarkInfo = remark_info;
        }

        public Long getShopDeviceId() {
            return shopDeviceId;
        }

        public void setShopDeviceId(Long shopDeviceId) {
            this.shopDeviceId = shopDeviceId;
        }

        public String getModelCode() {
            return modelCode;
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
            return params;
        }

        public void setParams(List<ParamsBean> params) {
            this.params = params;
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
                return paramValue;
            }

            public void setParamValue(String paramValue) {
                this.paramValue = paramValue;
            }

            public String getParamName() {
                return paramName;
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

        public String getPartName() {
            return partName;
        }

        public void setPartName(String partName) {
            this.partName = partName;
        }

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
            return partSpeciication;
        }

        public void setPartSpeciication(String partSpeciication) {
            this.partSpeciication = partSpeciication;
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
            return serviceName;
        }

        public void setServiceName(String serviceName) {
            this.serviceName = serviceName;
        }

        public String getServiceContent() {
            return serviceContent;
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
            return serviceValue;
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

