package com.eanfang.biz.rds.sys.repo;

import androidx.lifecycle.MutableLiveData;

import com.eanfang.biz.model.bean.DesignOrderInfoBean;
import com.eanfang.biz.model.bean.InstallOrderConfirmBean;
import com.eanfang.biz.model.bean.OrderCountBean;
import com.eanfang.biz.model.entity.RepairOrderEntity;
import com.eanfang.biz.rds.base.BaseRepo;
import com.eanfang.biz.rds.sys.ds.impl.QuickRepairDs;

import org.json.JSONObject;

/**
 * @author liangkailun
 * Date ：2019-07-24
 * Describe :
 */
public class QuickRepairRepo extends BaseRepo<QuickRepairDs> {

    public QuickRepairRepo(QuickRepairDs remoteDataSource) {
        super(remoteDataSource);
    }


    /**
     * 订单数量
     */
    public MutableLiveData<OrderCountBean> getRepairCount() {
        MutableLiveData<OrderCountBean> mutableLiveData = new MutableLiveData<>();
        remoteDataSource.getRepairCount(mutableLiveData::setValue);
        return mutableLiveData;
    }

    /**
     * 报修订单
     * @param repairOrderEntity
     * @return
     */
    public MutableLiveData<JSONObject> createRepairOrder(RepairOrderEntity repairOrderEntity) {
        MutableLiveData<JSONObject> mutableLiveData = new MutableLiveData<>();
        remoteDataSource.createRepairOrder(repairOrderEntity, mutableLiveData::setValue);
        return mutableLiveData;
    }
    /**
     * 报修订单
     * @param installOrderConfirmBean
     * @return
     */
    public MutableLiveData<JSONObject> createInstallOrder(InstallOrderConfirmBean installOrderConfirmBean) {
        MutableLiveData<JSONObject> mutableLiveData = new MutableLiveData<>();
        remoteDataSource.createInstallOrder(installOrderConfirmBean, mutableLiveData::setValue);
        return mutableLiveData;
    }

    /**
     * 免费设计
     * @param designOrderInfoBean
     * @return
     */
    public MutableLiveData<JSONObject> createFreeDesign(DesignOrderInfoBean designOrderInfoBean) {
        MutableLiveData<JSONObject> mutableLiveData = new MutableLiveData<>();
        remoteDataSource.createFreeDesign(designOrderInfoBean, mutableLiveData::setValue);
        return mutableLiveData;
    }


}
