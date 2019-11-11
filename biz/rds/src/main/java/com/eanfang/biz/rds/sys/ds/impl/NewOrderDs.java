package com.eanfang.biz.rds.sys.ds.impl;

import com.eanfang.base.network.callback.RequestCallback;
import com.eanfang.biz.model.PageBean;
import com.eanfang.biz.model.bean.QueryEntry;
import com.eanfang.biz.model.entity.OrderBean;
import com.eanfang.biz.rds.base.BaseRemoteDataSource;
import com.eanfang.biz.rds.base.BaseViewModel;
import com.eanfang.biz.rds.sys.api.NewOrderApi;
import com.eanfang.biz.rds.sys.ds.INewOrderDs;

import java.util.List;

/**
 * @author guanluocang
 * @data 2019/11/4
 * @description
 */
public class NewOrderDs extends BaseRemoteDataSource implements INewOrderDs {

    public NewOrderDs(BaseViewModel baseViewModel) {
        super(baseViewModel);
    }

    @Override
    public void getNewOrder(int type, RequestCallback<List<OrderBean>> callback) {
        execute(getService(NewOrderApi.class).getNewOrderList(type), callback);
    }

    @Override
    public void getHistoryRepairOrder(QueryEntry queryEntry, RequestCallback<PageBean<OrderBean>> callback) {
        execute(getService(NewOrderApi.class).getHistoryRepairOrderList(queryEntry), callback);
    }

    @Override
    public void getHistoryInstallOrder(QueryEntry queryEntry, RequestCallback<PageBean<OrderBean>> callback) {
        execute(getService(NewOrderApi.class).getHistoryInstallOrderList(queryEntry), callback);
    }

    @Override
    public void getHistoryMaintenanceOrder(QueryEntry queryEntry, RequestCallback<PageBean<OrderBean>> callback) {
        execute(getService(NewOrderApi.class).getHistoryMaintenanceOrderList(queryEntry), callback);
    }

    @Override
    public void getHistoryDeisgnOrder(QueryEntry queryEntry, RequestCallback<PageBean<OrderBean>> callback) {
        execute(getService(NewOrderApi.class).getHistoryDesignOrderList(queryEntry), callback);
    }

    @Override
    public void getHistoryTaskApplyOrder(QueryEntry queryEntry, RequestCallback<PageBean<OrderBean>> callback) {
        execute(getService(NewOrderApi.class).getHistoryTaskApplyOrderList(queryEntry), callback);
    }


}
