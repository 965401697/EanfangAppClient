package net.eanfang.worker.viewmodle.neworder;

import android.view.Gravity;

import androidx.lifecycle.MutableLiveData;

import com.eanfang.biz.model.entity.OrderBean;
import com.eanfang.biz.rds.base.BaseViewModel;
import com.eanfang.biz.rds.sys.ds.impl.NewOrderDs;
import com.eanfang.biz.rds.sys.repo.NewOrderRepo;

import net.eanfang.worker.base.WorkerApplication;
import net.eanfang.worker.databinding.FragmentOrderBinding;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import q.rorbin.badgeview.QBadgeView;

/**
 * @author guanluocang
 * @data 2019/10/29
 * @description
 */
public class HomeOrderViewModle extends BaseViewModel {

    private NewOrderRepo newOrderRepo;
    @Getter
    private MutableLiveData<List<OrderBean>> progressMutableLiveData;

    @Getter
    private MutableLiveData<List<OrderBean>> pendingMutableLiveData;
    @Setter
    private FragmentOrderBinding fragmentOrderBinding;

    private QBadgeView qBadgeViewPending = new QBadgeView(WorkerApplication.get().getApplicationContext());
    private QBadgeView qBadgeViewProgress = new QBadgeView(WorkerApplication.get().getApplicationContext());

    public HomeOrderViewModle() {
        newOrderRepo = new NewOrderRepo(new NewOrderDs(this));
        progressMutableLiveData = new MutableLiveData<>();
        pendingMutableLiveData = new MutableLiveData<>();

    }

    /**
     * 获取数据
     */
    public void doGetProgressData(int type) {
        newOrderRepo.doGetHomeOrder(type).observe(lifecycleOwner, newOrderList -> {
            //  0 待处理  1  进行中
            if (type == 0) {
                pendingMutableLiveData.setValue(newOrderList);
                qBadgeViewPending.bindTarget(fragmentOrderBinding.tvPending)
                        .setBadgeBackgroundColor(0xFFFF0000)
                        .setBadgePadding(5, true)
                        .setBadgeGravity(Gravity.END | Gravity.TOP)
                        .setGravityOffset(0, 0, true)
                        .setBadgeTextSize(10, true)
                        .setBadgeNumber(newOrderList.size());
            } else {
                progressMutableLiveData.setValue(newOrderList);
                qBadgeViewProgress.bindTarget(fragmentOrderBinding.tvHaveIn)
                        .setBadgeBackgroundColor(0xFFFF0000)
                        .setBadgePadding(5, true)
                        .setBadgeGravity(Gravity.END | Gravity.TOP)
                        .setGravityOffset(0, 0, true)
                        .setBadgeTextSize(10, true)
                        .setBadgeNumber(newOrderList.size());
            }

        });
    }
}
