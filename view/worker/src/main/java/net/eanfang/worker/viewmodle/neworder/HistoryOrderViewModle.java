package net.eanfang.worker.viewmodle.neworder;

import androidx.lifecycle.MutableLiveData;

import com.eanfang.biz.model.PageBean;
import com.eanfang.biz.model.bean.QueryEntry;
import com.eanfang.biz.model.entity.OrderBean;
import com.eanfang.biz.rds.base.BaseViewModel;
import com.eanfang.biz.rds.sys.ds.impl.NewOrderDs;
import com.eanfang.biz.rds.sys.repo.NewOrderRepo;

import lombok.Getter;

/**
 * @author guanluocang
 * @data 2019/11/7
 * @description 历史订单 viewmodle
 */
public class HistoryOrderViewModle extends BaseViewModel {
    private NewOrderRepo newOrderRepo;

    @Getter
    private MutableLiveData<PageBean<OrderBean>> historyRepairMutableLiveData;
    @Getter
    private MutableLiveData<PageBean<OrderBean>> historyInstallMutableLiveData;
    @Getter
    private MutableLiveData<PageBean<OrderBean>> historyMaintenanceMutableLiveData;
    @Getter
    private MutableLiveData<PageBean<OrderBean>> historyDesignMutableLiveData;
    @Getter
    private MutableLiveData<PageBean<OrderBean>> historyTaskApplyMutableLiveData;

    public QueryEntry mQueryEntry;
    public int mPage = 1;


    public HistoryOrderViewModle() {
        newOrderRepo = new NewOrderRepo(new NewOrderDs(this));
        historyRepairMutableLiveData = new MutableLiveData<>();
        historyInstallMutableLiveData = new MutableLiveData<>();
        historyMaintenanceMutableLiveData = new MutableLiveData<>();
        historyDesignMutableLiveData = new MutableLiveData<>();
        historyTaskApplyMutableLiveData = new MutableLiveData<>();
    }

    public void doGetHistroryOrder(String mType, int mPage) {
        if (mQueryEntry == null) {
            mQueryEntry = new QueryEntry();
        }
        //正在公告 0   已过期 1
//        mQueryEntry.getEquals().put("status", "1");
        mQueryEntry.setPage(mPage);
        switch (mType) {
            case "repair":
                newOrderRepo.doGetHistroryRepairOrder(mQueryEntry).observe(lifecycleOwner, tenderBean -> {
                    historyRepairMutableLiveData.setValue(tenderBean);
                });
                break;
            case "install":
                newOrderRepo.doGetHistroryInstallOrder(mQueryEntry).observe(lifecycleOwner, tenderBean -> {
                    historyInstallMutableLiveData.setValue(tenderBean);
                });
                break;
            case "design":
                newOrderRepo.doGetHistroryDesignOrder(mQueryEntry).observe(lifecycleOwner, tenderBean -> {
                    historyDesignMutableLiveData.setValue(tenderBean);
                });
                break;
            case "maintain":
                newOrderRepo.doGetHistroryMaintenanceOrder(mQueryEntry).observe(lifecycleOwner, tenderBean -> {
                    historyMaintenanceMutableLiveData.setValue(tenderBean);
                });
                break;
            case "tender":
                newOrderRepo.doGetHistroryTaskApplyOrder(mQueryEntry).observe(lifecycleOwner, tenderBean -> {
                    historyTaskApplyMutableLiveData.setValue(tenderBean);
                });
                break;
            default:
                break;
        }


    }
}
