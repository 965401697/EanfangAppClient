package com.eanfang.biz.rds.sys.api;

import com.eanfang.base.network.model.BaseResponseBody;
import com.eanfang.biz.model.PageBean;
import com.eanfang.biz.model.bean.QueryEntry;
import com.eanfang.biz.model.entity.OrderBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @author guanluocang
 * @data 2019/11/4
 * @description
 */
public interface NewOrderApi {
    /**
     * 改版订单 首页
     *
     * @param type
     * @return
     */
    @POST("/yaf_order/order/v1/app/list")
    Observable<BaseResponseBody<List<OrderBean>>> getNewOrderList(@Query("type") int type);

    /**
     * 历史订单  报修
     *
     * @param queryMap
     * @return
     */
    @POST("/yaf_order/repair/v1/app/list")
    Observable<BaseResponseBody<PageBean<OrderBean>>> getHistoryRepairOrderList(@Body QueryEntry queryMap);

    /**
     * 历史订单  报装
     *
     * @param queryMap
     * @return
     */
    @POST("/yaf_order/install/v1/app/list")
    Observable<BaseResponseBody<PageBean<OrderBean>>> getHistoryInstallOrderList(@Body QueryEntry queryMap);

    /**
     * 历史订单  维保
     *
     * @param queryMap
     * @return
     */
    @POST("/yaf_order/maintenance/v1/app/list")
    Observable<BaseResponseBody<PageBean<OrderBean>>> getHistoryMaintenanceOrderList(@Body QueryEntry queryMap);

    /**
     * 历史订单  设计
     *
     * @param queryMap
     * @return
     */
    @POST("/yaf_order/design/v1/app/list")
    Observable<BaseResponseBody<PageBean<OrderBean>>> getHistoryDesignOrderList(@Body QueryEntry queryMap);

    /**
     * 历史订单  用工
     *
     * @param queryMap
     * @return
     */
    @POST("/yaf_order/taskApply/v1/app/list")
    Observable<BaseResponseBody<PageBean<OrderBean>>> getHistoryTaskApplyOrderList(@Body QueryEntry queryMap);
}
