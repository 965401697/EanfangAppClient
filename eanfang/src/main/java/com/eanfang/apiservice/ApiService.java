package com.eanfang.apiservice;

import static com.eanfang.apiservice.BaseService.BASE_URL;

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
     * 一键报修
     * post
     */
    String ONE_BUTON_REPAIR = BASE_URL + "/onetouchrepairselectworker";


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


}
