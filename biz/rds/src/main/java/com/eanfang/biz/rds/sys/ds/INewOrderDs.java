package com.eanfang.biz.rds.sys.ds;

import com.eanfang.base.network.callback.RequestCallback;
import com.eanfang.biz.model.PageBean;
import com.eanfang.biz.model.bean.QueryEntry;
import com.eanfang.biz.model.entity.OrderBean;

import java.util.List;

/**
 * @author guanluocang
 * @data 2019/11/4
 * @description
 */
public interface INewOrderDs {

    /**
     * 获取订单
     *
     * @param type
     * @param callback
     */
    void getNewOrder(int type, RequestCallback<List<OrderBean>> callback);

    /**
     * 历史订单 报修
     *
     * @param queryEntry
     * @param callback
     */
    void getHistoryRepairOrder(QueryEntry queryEntry, RequestCallback<PageBean<OrderBean>> callback);

    /**
     * 历史订单 报装
     *
     * @param queryEntry
     * @param callback
     */
    void getHistoryInstallOrder(QueryEntry queryEntry, RequestCallback<PageBean<OrderBean>> callback);

    /**
     * 历史订单 维保
     *
     * @param queryEntry
     * @param callback
     */
    void getHistoryMaintenanceOrder(QueryEntry queryEntry, RequestCallback<PageBean<OrderBean>> callback);

    /**
     * 历史订单 设计
     *
     * @param queryEntry
     * @param callback
     */
    void getHistoryDeisgnOrder(QueryEntry queryEntry, RequestCallback<PageBean<OrderBean>> callback);

    /**
     * 历史订单 用工
     *
     * @param queryEntry
     * @param callback
     */
    void getHistoryTaskApplyOrder(QueryEntry queryEntry, RequestCallback<PageBean<OrderBean>> callback);
}
