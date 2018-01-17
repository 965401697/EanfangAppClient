package net.eanfang.client.ui.model;

import com.yaf.sys.entity.OrgEntity;
import com.yaf.sys.entity.UserEntity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yaosheng on 2017/5/22.
 */

public class PayOrderListBean implements Serializable {
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

        private String assigneeOrgCode;
        private int assigneeTopCompanyId;
        private Long assigneeUserId;
        private String createTime;
        private Long createUserId;
        private String editTime;
        private Long editUserId;
        private Long id;
        private int invoiceCost;
        private UserEntity offerer;
        private int orderId;
        private String orderNum;
        private String ownerOrgCode;
        private Long ownerTopCompanyId;
        private Long ownerUserId;
        private String projectName;
        private int quoteCost;
        private String repairOrderNum;
        private int reportType;
        private UserEntity reportUser;
        private String reporter;
        private String reporterPhone;
        private int status;
        private int totalCost;
        private OrgEntity assigneeCompanyOrg;
        private OrgEntity ownerCompanyOrg;

        public UserEntity getOfferer() {
            return offerer;
        }

        public void setOfferer(UserEntity offerer) {
            this.offerer = offerer;
        }

        public UserEntity getReportUser() {
            return reportUser;
        }

        public void setReportUser(UserEntity reportUser) {
            this.reportUser = reportUser;
        }

        public OrgEntity getAssigneeCompanyOrg() {
            return assigneeCompanyOrg;
        }

        public void setAssigneeCompanyOrg(OrgEntity assigneeCompanyOrg) {
            this.assigneeCompanyOrg = assigneeCompanyOrg;
        }

        public OrgEntity getOwnerCompanyOrg() {
            return ownerCompanyOrg;
        }

        public void setOwnerCompanyOrg(OrgEntity ownerCompanyOrg) {
            this.ownerCompanyOrg = ownerCompanyOrg;
        }

        public String getAssigneeOrgCode() {
            return assigneeOrgCode;
        }

        public void setAssigneeOrgCode(String assigneeOrgCode) {
            this.assigneeOrgCode = assigneeOrgCode;
        }

        public int getAssigneeTopCompanyId() {
            return assigneeTopCompanyId;
        }

        public void setAssigneeTopCompanyId(int assigneeTopCompanyId) {
            this.assigneeTopCompanyId = assigneeTopCompanyId;
        }

        public Long getAssigneeUserId() {
            return assigneeUserId;
        }

        public void setAssigneeUserId(Long assigneeUserId) {
            this.assigneeUserId = assigneeUserId;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public Long getCreateUserId() {
            return createUserId;
        }

        public void setCreateUserId(Long createUserId) {
            this.createUserId = createUserId;
        }

        public String getEditTime() {
            return editTime;
        }

        public void setEditTime(String editTime) {
            this.editTime = editTime;
        }

        public Long getEditUserId() {
            return editUserId;
        }

        public void setEditUserId(Long editUserId) {
            this.editUserId = editUserId;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public int getInvoiceCost() {
            return invoiceCost;
        }

        public void setInvoiceCost(int invoiceCost) {
            this.invoiceCost = invoiceCost;
        }


        public int getOrderId() {
            return orderId;
        }

        public void setOrderId(int orderId) {
            this.orderId = orderId;
        }

        public String getOrderNum() {
            return orderNum;
        }

        public void setOrderNum(String orderNum) {
            this.orderNum = orderNum;
        }

        public String getOwnerOrgCode() {
            return ownerOrgCode;
        }

        public void setOwnerOrgCode(String ownerOrgCode) {
            this.ownerOrgCode = ownerOrgCode;
        }

        public Long getOwnerTopCompanyId() {
            return ownerTopCompanyId;
        }

        public void setOwnerTopCompanyId(Long ownerTopCompanyId) {
            this.ownerTopCompanyId = ownerTopCompanyId;
        }

        public Long getOwnerUserId() {
            return ownerUserId;
        }

        public void setOwnerUserId(Long ownerUserId) {
            this.ownerUserId = ownerUserId;
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

        public String getRepairOrderNum() {
            return repairOrderNum;
        }

        public void setRepairOrderNum(String repairOrderNum) {
            this.repairOrderNum = repairOrderNum;
        }

        public int getReportType() {
            return reportType;
        }

        public void setReportType(int reportType) {
            this.reportType = reportType;
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

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getTotalCost() {
            return totalCost;
        }

        public void setTotalCost(int totalCost) {
            this.totalCost = totalCost;
        }
    }
}
