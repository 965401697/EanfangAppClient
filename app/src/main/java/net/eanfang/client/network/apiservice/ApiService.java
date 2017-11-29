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
     * 收藏技师列表
     * get
     */

    String COLLECTION_WORKER_LIST = BASE_URL + "/search/collectworker";
    /**
     * 收藏过的公司列表
     * get
     */
    String COLLECTION_COMPANY_LIST = BASE_URL + "/getCollectinstallcompany";

    /**
     * 客户端评价技师
     * post
     */
    String EVALUATE_CLIENT = BASE_URL + "/reviewclient";
    /**
     * 客户端收到的评价的protocol
     * get
     */
    String RECEVIED_EVALUATE = BASE_URL + "/myinfo/jtcreview";


    /**
     * 客户端给出的评价的protocol
     * get
     */
    String GIVE_EVALUATE = BASE_URL + "/myinfo/ctjreview";

    /**
     * 获取推送消息
     */

    String GET_JPUSH_MESSAGE = BASE_URL + "/getmessage";
    /**
     * 添加工作检查
     */

    String ADD_CHECK_WORK_INSPECT = BASE_URL + "/addWorkInspect";
    /**
     * 添加工作任务 url
     * 参数：工作任务 bean
     */

    String ADD_WORK_TASK = BASE_URL + "/addWorkTask";
    /**
     * 获得公司部门员工列表
     * 参数：depId 部门id
     */

    String GET_COMPANY_STAFF = BASE_URL + " /getCompanyStaff";
    /**
     * 添加汇报工作
     */

    String ADD_WORK_REPORT = BASE_URL + "/addWorkReport";
    /**
     * 添加免费设计单
     */
    String ADD_DESIGN_ORDER = BASE_URL + "/addDesignOrder";
    /**
     * 报装单确认
     * post
     */
    String INSTALL_ORDER_CONFIRM = BASE_URL + "/installorderconfirm";
    /**
     * 收藏公司
     * post
     */

    String COLLECTION_COMPANY_WORK = BASE_URL + "/collectinstallcompany";
    /**
     * 公司详情
     * get
     */

    String COMPANY_DETAIL = BASE_URL + "/getworkcompanydetail";
    /**
     * 选择公司的protocol
     * get
     */
    String SELECT_COMPANY = BASE_URL + "/installselectcompany";
    /**
     * 收藏技师
     * post
     */
    String COLLECTION_WORK = BASE_URL + "/collectworker";

    /**
     * 技师详情
     * get
     */
    String WORKER_DETAIL = BASE_URL + "/search/workerdetail";
    /**
     * 报修订单确认
     * post
     */
    String ORDER_CONFIRM = BASE_URL + "/orderconfirm";
    /**
     * 一键报修
     * post
     */
    String ONE_BUTON_REPAIR = BASE_URL + "/onetouchrepairselectworker";
    /**
     * 报修选择技师
     */
    String REPAIR_SELECT_WORKER = BASE_URL + "/repairselectworker";

    /**
     * 添加工作检查明细的处理
     */

    String ADD_WORK_INSPECT_DETAIL_DISPOSE = BASE_URL + "/addWorkInspectDetailDispose";

    /**
     * 审核 工作检查明细的处理
     */
    String GET_REVIEW_WORK_DETAIL_DISPOSE = BASE_URL + "/reviewWorkInspectDetailDispose";

    /**
     * 查看工作检查详细信息
     */

    String GET_WORK_INSPECT_INFO = BASE_URL + "/getWorkInspectInfo";
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

    /**
     * 报修管控-报修单列表
     *
     * @parms page
     * @parms rows
     * @parms status
     */
    String REPAIR_LIST = BASE_URL + "/repairlist";

    /**
     * 工作台-已报修-订单详情  reairorderdetail
     */
    String GET_REAIR_ORDER_DETAIL = BASE_URL + "/reairorderdetail";

    /**
     * 订单进度的protocol
     * get
     */

    String ORDER_PROGRESS = BASE_URL + "/reairorderstatus";
    /**
     * 马上回电
     */
    String REAIR_BY_PHONE_SOLVE = BASE_URL + "/reairbyphonesolve";
    /**
     * 客户端评价技师
     * post
     */
    String EVALUATE_WORKER = BASE_URL + "/reviewworker";
    /**
     * 电话解决确认
     * get
     */
    String TO_CONFIRM = BASE_URL + "/confirm";

    /**
     * 维修统计
     * get
     */

    String BUG_DETAIL_INFO = BASE_URL + "/bugDetailInfo";

    /**
     * 查看工作检查列表
     */

    String GET_WORK_INSPECT_LIST = BASE_URL + "/getWorkInspectList";
}
