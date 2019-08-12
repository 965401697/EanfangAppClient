package com.eanfang.biz.rds.sys.ds.impl;

import com.eanfang.base.network.callback.RequestCallback;
import com.eanfang.biz.model.bean.DesignOrderInfoBean;
import com.eanfang.biz.model.bean.InstallOrderConfirmBean;
import com.eanfang.biz.model.bean.OrderCountBean;
import com.eanfang.biz.model.bean.QueryEntry;
import com.eanfang.biz.model.entity.RepairOrderEntity;
import com.eanfang.biz.rds.base.BaseRemoteDataSource;
import com.eanfang.biz.rds.base.BaseViewModel;
import com.eanfang.biz.rds.base.CacheModel;
import com.eanfang.biz.rds.sys.api.QuickRepairApi;
import com.zchu.rxcache.stategy.CacheStrategy;

import org.json.JSONObject;


/**
 * @author liangkailun
 * Date ：2019-07-24
 * Describe :
 */
public class QuickRepairDs extends BaseRemoteDataSource {

    public QuickRepairDs(BaseViewModel baseViewModel) {
        super(baseViewModel);
    }

    /**
     * 订单数量
     */
    public void getRepairCount(RequestCallback<OrderCountBean> callback) {
        execute(getService(QuickRepairApi.class).getRepairCount(new QueryEntry()), callback, new CacheModel().setCacheStrategy(CacheStrategy.firstCacheTimeoutSync(1000 * 60 * 60)).setClazz(QuickRepairApi.class).setMethod("getRepairCount"));
    }

    /**
     * 报修订单
     *
     * @param repairOrderEntity
     * @param callback
     */
    public void createRepairOrder(RepairOrderEntity repairOrderEntity, RequestCallback<JSONObject> callback) {
        execute(getService(QuickRepairApi.class).createRepairOrder(repairOrderEntity), callback);
    }

    /**
     * 报装订单
     *
     * @param installOrderConfirmBean
     * @param callback
     */
    public void createInstallOrder(InstallOrderConfirmBean installOrderConfirmBean, RequestCallback<JSONObject> callback) {
        execute(getService(QuickRepairApi.class).createInstallOrder(installOrderConfirmBean), callback);
    }

    /**
     * 免费设计
     *
     * @param designOrderInfoBean
     * @param callback
     */
    public void createFreeDesign(DesignOrderInfoBean designOrderInfoBean, RequestCallback<JSONObject> callback) {
        execute(getService(QuickRepairApi.class).createFreeDesign(designOrderInfoBean), callback);
    }


}
