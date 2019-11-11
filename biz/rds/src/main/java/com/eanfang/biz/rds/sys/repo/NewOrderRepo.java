package com.eanfang.biz.rds.sys.repo;

import androidx.lifecycle.MutableLiveData;

import com.eanfang.biz.model.PageBean;
import com.eanfang.biz.model.bean.QueryEntry;
import com.eanfang.biz.model.entity.OrderBean;
import com.eanfang.biz.rds.base.BaseRepo;
import com.eanfang.biz.rds.sys.ds.impl.NewOrderDs;

import java.util.List;

/**
 * @author guanluocang
 * @data 2019/11/4
 * @description 新改版订单
 */
public class NewOrderRepo extends BaseRepo<NewOrderDs> {

    public NewOrderRepo(NewOrderDs remoteDataSource) {
        super(remoteDataSource);
    }

    /**
     * 首页订单
     */
    public MutableLiveData<List<OrderBean>> doGetHomeOrder(int type) {
        MutableLiveData<List<OrderBean>> homeOrderMutableLiveData = new MutableLiveData<>();
        remoteDataSource.getNewOrder(type, (val) -> {
            homeOrderMutableLiveData.setValue(val);
        });
        return homeOrderMutableLiveData;
    }

    /**
     * 历史订单 报修
     */
    public MutableLiveData<PageBean<OrderBean>> doGetHistroryRepairOrder(QueryEntry queryEntry) {
        MutableLiveData<PageBean<OrderBean>> historyRepairOrderMutableLiveData = new MutableLiveData<>();
        queryEntry.setSize(10);
        remoteDataSource.getHistoryRepairOrder(queryEntry, (val) -> {
            historyRepairOrderMutableLiveData.setValue(val);
        });
        return historyRepairOrderMutableLiveData;
    }

    /**
     * 历史订单 报装
     */
    public MutableLiveData<PageBean<OrderBean>> doGetHistroryInstallOrder(QueryEntry queryEntry) {
        MutableLiveData<PageBean<OrderBean>> historyInstallOrderMutableLiveData = new MutableLiveData<>();
        queryEntry.setSize(10);
        remoteDataSource.getHistoryInstallOrder(queryEntry, (val) -> {
            historyInstallOrderMutableLiveData.setValue(val);
        });
        return historyInstallOrderMutableLiveData;
    }

    /**
     * 历史订单 维保
     */
    public MutableLiveData<PageBean<OrderBean>> doGetHistroryMaintenanceOrder(QueryEntry queryEntry) {
        MutableLiveData<PageBean<OrderBean>> historyMaintenanceOrderMutableLiveData = new MutableLiveData<>();
        queryEntry.setSize(10);
        remoteDataSource.getHistoryMaintenanceOrder(queryEntry, (val) -> {
            historyMaintenanceOrderMutableLiveData.setValue(val);
        });
        return historyMaintenanceOrderMutableLiveData;
    }

    /**
     * 历史订单 设计
     */
    public MutableLiveData<PageBean<OrderBean>> doGetHistroryDesignOrder(QueryEntry queryEntry) {
        MutableLiveData<PageBean<OrderBean>> historyDesignOrderMutableLiveData = new MutableLiveData<>();
        queryEntry.setSize(10);
        remoteDataSource.getHistoryDeisgnOrder(queryEntry, (val) -> {
            historyDesignOrderMutableLiveData.setValue(val);
        });
        return historyDesignOrderMutableLiveData;
    }

    /**
     * 历史订单 用工
     */
    public MutableLiveData<PageBean<OrderBean>> doGetHistroryTaskApplyOrder(QueryEntry queryEntry) {
        MutableLiveData<PageBean<OrderBean>> historyTaskApplyOrderMutableLiveData = new MutableLiveData<>();
        queryEntry.setSize(10);
        remoteDataSource.getHistoryTaskApplyOrder(queryEntry, (val) -> {
            historyTaskApplyOrderMutableLiveData.setValue(val);
        });
        return historyTaskApplyOrderMutableLiveData;
    }
}
