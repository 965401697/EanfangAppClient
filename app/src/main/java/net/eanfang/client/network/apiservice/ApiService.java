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

}
