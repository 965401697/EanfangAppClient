package net.eanfang.client.ui.activity.worksapce.monitor;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModel;

import com.eanfang.base.BaseActivity;
import com.eanfang.biz.rds.base.LViewModelProviders;
import com.eanfang.util.JumpItent;

import net.eanfang.client.R;
import net.eanfang.client.base.ClientApplication;
import net.eanfang.client.databinding.ActivityMonitorListBinding;
import net.eanfang.client.ui.activity.worksapce.monitor.group.MonitorGroupManagerActivity;
import net.eanfang.client.viewmodel.monitor.MonitorHomeViewModle;

/**
 * @author guanluocang
 * @data 2019/9/9  10:21
 * @description 实时监控 列表
 */

public class MonitorListActivity extends BaseActivity {

    private ActivityMonitorListBinding monitorListBinding;
    private MonitorHomeViewModle monitorViewModle;

    private String mCompanyName;
    private String mCompanyId;

    private boolean isFirst = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        monitorListBinding = DataBindingUtil.setContentView(this, R.layout.activity_monitor_list);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        super.initView();
        setTitle("实时监控");
        setLeftBack(true);
        setRightClick(R.mipmap.ic_search_white, (v) -> {
            Bundle bundle_search = new Bundle();
            bundle_search.putString("mChangeCompanyId", monitorViewModle.mChangeCompanyId);
            JumpItent.jump(this, MonitorSearchActivity.class, bundle_search);
        });

        monitorListBinding.tvCompanyNameTwo.setOnClickListener((v) -> {
            Bundle bundle_group = new Bundle();
            bundle_group.putString("mChangeCompanyId", monitorViewModle.mChangeCompanyId);
            JumpItent.jump(this, MonitorGroupManagerActivity.class, bundle_group);
        });
        mCompanyName = ClientApplication.get().getLoginBean().getAccount().getDefaultUser().getCompanyEntity().getOrgName();
        mCompanyId = ClientApplication.get().getLoginBean().getAccount().getDefaultUser().getCompanyEntity().getCompanyId().toString();
        monitorListBinding.tvCompanyName.setText(mCompanyName);
        monitorListBinding.tvCompanyNameTwo.setText(mCompanyName);
        monitorViewModle.doGetMonitorList(mCompanyId);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!isFirst){
            monitorViewModle.doGetRightDeviceList(1,monitorViewModle.mLeftGroupId);
        }

    }

    @Override
    protected ViewModel initViewModel() {
        monitorViewModle = LViewModelProviders.of(this, MonitorHomeViewModle.class);
        monitorListBinding.setViewModle(monitorViewModle);
        monitorViewModle.setMonitorListBinding(monitorListBinding);
        return monitorViewModle;
    }

    @Override
    protected void onPause() {
        super.onPause();
        isFirst = false;
    }
}
