package net.eanfang.client.network.apiservice;

import static net.eanfang.client.network.apiservice.BaseService.BASE_URL;

/**
 * Created by MrHou
 *
 * @on 2017/11/20  15:45
 * @email houzhongzhou@yeah.net
 * @desc
 */

public interface ApiService {
    /**
     * 根据一级业务类型编码 获取二、三级及品牌型号
     */
    String GET_BUSINESS_BY_CODE = BASE_URL + "/getBusinessByCode";

    /**
     * 工作台--报装管控的列表
     * get
     */
    String WORKSPACE_INSTALL = BASE_URL + "/installList";

    /**
     * 工作台报装详情的详情
     * get
     */
    String WORKSPACE_INSTALL_DETAIL = BASE_URL + "/installDetail";

    /**
     * 工作台-报价与支付-同意报价的protocol
     */
    String AGREE_PAY = BASE_URL + "/agreequote";

    /**
     * 工作台--报价与支付(企业客户)
     * get
     */
    String COMPANY_PAY_ORDER_LIST = BASE_URL + "/quoteshow";

    /**
     * 工作台-报价与支付-订单详情的protocol
     * post
     */
    String PAY_ORDER_DETAIL = BASE_URL + "/showquotedetail";

    /**
     * 查看发票
     * get
     */
    String LOOK_FA_PIAO = BASE_URL + "/getinvoicebyordernum";

    /**
     * 发票
     * post
     */
    String FA_PIAO = BASE_URL + "/addinvoice";
    /**
     * 获取工作汇报列表
     */

    String GET_WORK_REPORT_LIST = BASE_URL + "/getWorkReportList";

    /**
     * 首次查看工作汇报
     */
    String GET_FIRST_REPORT_LOOK = BASE_URL + "/firstLookReport";
    /**
     * 查看工作汇报详细信息即明细
     */
    String GET_WORK_REPORT_INFO = BASE_URL + "/getWorkReportInfo";

    /**
     * 获取工作任务列表
     *
     * @parms page
     * @parms rows
     * @parms type
     */
    String GET_WORK_TASK_LIST = BASE_URL + "/getWorkTaskList";
    /**
     * 首次阅读，更新状态
     */

    String GET_FIRST_LOOK_TASK = BASE_URL + "/firstLookTask";
    /**
     * 获取工作任务详细信息及明细
     */

    String GET_WORK_TASK_INFO = BASE_URL + "/getWorkeTaskInfo";

    /**
     * 免费设计订单 查看列表
     */
    String GET_DESIGN_ORDER_LIST = BASE_URL + "/getDesignOrderList";
    /**
     * 免费设计 明细
     */
    String GET_DESIGN_ORDER_INF = BASE_URL + "/getDesignOrderInfo";
}
