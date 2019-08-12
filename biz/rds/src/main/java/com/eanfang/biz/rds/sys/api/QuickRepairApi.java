package com.eanfang.biz.rds.sys.api;

import com.eanfang.base.network.model.BaseResponseBody;
import com.eanfang.biz.model.bean.DesignOrderInfoBean;
import com.eanfang.biz.model.bean.InstallOrderConfirmBean;
import com.eanfang.biz.model.bean.OrderCountBean;
import com.eanfang.biz.model.bean.QueryEntry;
import com.eanfang.biz.model.entity.RepairOrderEntity;

import org.json.JSONObject;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * @author liangkailun
 * Date ：2019-07-24
 * Describe :
 */
public interface QuickRepairApi {

    /**
     * 订单数量
     *
     * @param queryEntry
     * @return
     */
    @POST("/yaf_sys/notice/count/user/orderNum")
    Observable<BaseResponseBody<OrderCountBean>> getRepairCount(@Body QueryEntry queryEntry);

    /**
     * 报修订单
     *
     * @param repairOrderEntity
     * @return
     */
    @POST("/yaf_site/siteRepairOrder/create")
    Observable<BaseResponseBody<JSONObject>> createRepairOrder(@Body RepairOrderEntity repairOrderEntity);

    /**
     * 快速报装
     *
     * @param installOrderConfirmBean
     * @return
     */
    @POST("/yaf_site/siteInstallOrder/create")
    Observable<BaseResponseBody<JSONObject>> createInstallOrder(@Body InstallOrderConfirmBean installOrderConfirmBean);

    /**
     * 免费设计
     *
     * @param designOrderInfoBean
     * @return
     */
    @POST("/yaf_site/freeDesign/create")
    Observable<BaseResponseBody<JSONObject>> createFreeDesign(@Body DesignOrderInfoBean designOrderInfoBean);

}
