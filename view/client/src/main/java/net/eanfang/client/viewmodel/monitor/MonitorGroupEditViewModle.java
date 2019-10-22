package net.eanfang.client.viewmodel.monitor;

import android.app.Activity;

import androidx.lifecycle.MutableLiveData;

import com.eanfang.base.kit.rx.RxDialog;
import com.eanfang.biz.rds.base.BaseViewModel;
import com.eanfang.biz.rds.sys.ds.impl.MonitorDs;
import com.eanfang.biz.rds.sys.repo.MonitorRepo;

import net.eanfang.client.databinding.ActivityMonitorGroupEditGroupBinding;

import org.json.JSONObject;

import cn.hutool.core.util.StrUtil;
import io.reactivex.android.schedulers.AndroidSchedulers;
import lombok.Getter;
import lombok.Setter;

/**
 * @author guanluocang
 * @data 2019/9/24
 * @description 分组设置
 */
public class MonitorGroupEditViewModle extends BaseViewModel {
    @Getter
    @Setter
    private ActivityMonitorGroupEditGroupBinding monitorGroupEditGroupBinding;
    private MonitorRepo monitorRepo;

    public String mGroupId;

    @Getter
    private MutableLiveData<JSONObject> editNameLiveData;

    public MonitorGroupEditViewModle() {
        editNameLiveData = new MutableLiveData<>();
        monitorRepo = new MonitorRepo(new MonitorDs(this));
    }


    /**
     * 删除分组
     */
    public void doDeleteGroup() {
        new RxDialog((Activity)monitorGroupEditGroupBinding.getRoot().getContext())
                .setTitle("提示")
                .setMessage("退出后将无法查看数据，您确定退出吗？")
                .setPositiveText("确定")
                .setNegativeText("取消")
                .dialogToObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(code -> {
                    if (code.equals(RxDialog.POSITIVE)) {
                        doDelete();
                    }
                });
    }

    private void doDelete() {
        if (!StrUtil.isEmpty(mGroupId)) {
            monitorRepo.doDeleteGroup(mGroupId).observe(lifecycleOwner, deleteSuccess -> {
                editNameLiveData.setValue(deleteSuccess);
            });
        } else {
            showToast("分组信息为空");
        }

    }


}
