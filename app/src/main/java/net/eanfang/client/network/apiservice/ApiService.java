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
}
