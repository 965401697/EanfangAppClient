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
     * 收藏过的公司列表
     * get
     */
    String COLLECTION_COMPANY_LIST = BASE_URL + "/getCollectinstallcompany";


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
     * 根据一级业务类型编码 获取二、三级及品牌型号
     */
    String GET_BUSINESS_BY_CODE = BASE_URL + "/getBusinessByCode";



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

}
