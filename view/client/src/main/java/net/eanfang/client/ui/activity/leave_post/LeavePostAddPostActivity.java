package net.eanfang.client.ui.activity.leave_post;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.eanfang.base.BaseActivity;
import com.eanfang.biz.model.bean.SelectAddressItem;
import com.eanfang.biz.model.bean.TemplateBean;
import com.eanfang.biz.rds.base.LViewModelProviders;
import com.eanfang.config.Config;

import net.eanfang.client.R;
import net.eanfang.client.databinding.ActivityLeavePostAddPostBinding;
import net.eanfang.client.ui.activity.ChooseAlertTimeDialog;
import net.eanfang.client.ui.activity.leave_post.bean.LeavePostAddPostPostBean;
import net.eanfang.client.ui.activity.leave_post.bean.LeavePostDeviceInfoBean;
import net.eanfang.client.ui.activity.leave_post.viewmodel.LeavePostAddPostViewModel;
import net.eanfang.client.ui.adapter.LeavePostDetailInfoAdapter;
import net.eanfang.client.ui.adapter.LeavePostPersonAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author liangkailun
 * Date ：2019-06-24
 * Describe :添加岗位
 */
public class LeavePostAddPostActivity extends BaseActivity {
    private static final int SELECT_ADDRESS_CALL_BACK_CODE = 1;
    private static final int SELECT_DEVICE_CALL_BACK_CODE = 2;
    private ActivityLeavePostAddPostBinding mBinding;
    private LeavePostAddPostViewModel mViewModel;
    private LeavePostPersonAdapter mChargeStaffAdapter;
    private LeavePostPersonAdapter mDutyStaffAdapter;
    private int mFlag;
    private LeavePostDetailInfoAdapter mAdapter;
    private List<TemplateBean.Preson> mDutyStaffData;
    private List<TemplateBean.Preson> mChargeStaff;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_leave_post_add_post);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        super.initView();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        //岗位类型 0：添加 1只读
        int postType = getIntent().getIntExtra("postType", 0);
        setLeftBack(true);
        if (postType == 0) {
            setTitle("添加岗位");
        } else {
            int stationId = getIntent().getIntExtra("stationId", 0);
            mBinding.btnLeavePostAddCommit.setVisibility(View.GONE);
            mBinding.viewBottomBg.setVisibility(View.GONE);
            mViewModel.getPostInfo(stationId);
            setTitle("岗位详情");
            mBinding.contentInfo.setVisibility(View.GONE);
            mBinding.recLeavePostDetailInfo.setVisibility(View.VISIBLE);
            setRightClick("编辑", view -> {
                mChargeStaffAdapter.setCanClick(true);
                mDutyStaffAdapter.setCanClick(true);
                mBinding.contentInfo.setVisibility(View.VISIBLE);
                mBinding.recLeavePostDetailInfo.setVisibility(View.GONE);
                setDefaultData();
                setRightClick("确定", view1 -> {
                    mViewModel.addPostCommit(mBinding, 1, stationId);
                });
            });
        }
        mAdapter = new LeavePostDetailInfoAdapter();
        mBinding.recLeavePostDetailInfo.setLayoutManager(new LinearLayoutManager(this));
        mBinding.tvLeavePostAddPostDevice.setOnClickListener(view -> {
            mViewModel.addDevice(LeavePostAddPostActivity.this, SELECT_DEVICE_CALL_BACK_CODE);
        });
        mBinding.btnLeavePostAddCommit.setOnClickListener(view -> {
            mViewModel.addPostCommit(mBinding, 0, null);
        });
        mBinding.tvLeavePostAddPostArea.setOnClickListener(view -> {
            mViewModel.gotoChooseArea(LeavePostAddPostActivity.this, SELECT_ADDRESS_CALL_BACK_CODE);
        });
        mAdapter.bindToRecyclerView(mBinding.recLeavePostDetailInfo);
//        mBinding.switchBtnLeavePostAddPostStatus.setOnClickListener((view) -> {
//            if (StrUtil.isEmpty(mBinding.tvLeavePostAddPostDevice.getText().toString())) {
//                showToast("请先选择绑定设备");
//            }
//        });
        mBinding.tvLeavePostAddPostTime.setOnClickListener(view -> {
            ChooseAlertTimeDialog dialog = new ChooseAlertTimeDialog(mBinding.tvLeavePostAddPostTime.getText().toString());
            dialog.setStyle(DialogFragment.STYLE_NORMAL, R.style.Dialog_FullScreen);
            dialog.setMChooseTimeListener(time -> mBinding.tvLeavePostAddPostTime.setText(time));
            dialog.show(getSupportFragmentManager(), "addPost");
        });
        initRecView(postType == 0);
    }

    /**
     * 只读变成编辑状态设置数据
     */
    private void setDefaultData() {
        LeavePostDeviceInfoBean leavePostDeviceInfoBean = mViewModel.getDeviceInfo().getValue();
        if (leavePostDeviceInfoBean != null) {
            mBinding.edtLeavePostAddPostName.setText(leavePostDeviceInfoBean.getStationName());
            mBinding.edtLeavePostAddPostPosition.setText(leavePostDeviceInfoBean.getStationArea());
            mBinding.edtLeavePostAddPostNumber.setText(leavePostDeviceInfoBean.getStationCode());
            mBinding.tvLeavePostAddPostArea.setText(Config.get().getAddressByCode(leavePostDeviceInfoBean.getStationPlaceCode()));
            mBinding.tvLeavePostAddPostDevice.setText(leavePostDeviceInfoBean.getDeviceName());
            mBinding.tvLeavePostAddPostTime.setText(MessageFormat.format("{0}分钟", leavePostDeviceInfoBean.getIntervalLength()));
            mBinding.switchBtnLeavePostAddPostStatus.setChecked(leavePostDeviceInfoBean.getStatus() == 1);
        }
        mChargeStaffAdapter.setCanClick(true);
        mChargeStaffAdapter.setNewData(mChargeStaff == null ? new ArrayList<>() : mChargeStaff);
        mDutyStaffAdapter.setCanClick(true);
        mDutyStaffAdapter.setNewData(mDutyStaffData == null ? new ArrayList<>() : mDutyStaffData);

    }

    private void initRecView(boolean itemCanClienk) {
        mBinding.recLeavePostAddInDuty.setLayoutManager(new GridLayoutManager(this, 5));
        mBinding.recLeavePostAddInCharge.setLayoutManager(new GridLayoutManager(this, 5));
        mChargeStaffAdapter = new LeavePostPersonAdapter(this, new ArrayList<TemplateBean.Preson>(), 3);
        mDutyStaffAdapter = new LeavePostPersonAdapter(this, new ArrayList<TemplateBean.Preson>(), 2);
        mChargeStaffAdapter.setCanClick(itemCanClienk);
        mDutyStaffAdapter.setCanClick(itemCanClienk);
        mBinding.recLeavePostAddInDuty.setAdapter(mChargeStaffAdapter);
        mBinding.recLeavePostAddInCharge.setAdapter(mDutyStaffAdapter);
    }

    @Override
    protected ViewModel initViewModel() {
        mViewModel = LViewModelProviders.of(this, LeavePostAddPostViewModel.class);
        mViewModel.getLeavePostInfo().observe(this, this::initPostInfo);
        mViewModel.getChargeStaffList().observe(this, this::setChargeStaffData);
        mViewModel.getDutyStaffList().observe(this, this::setDutyStaffData);
        return mViewModel;
    }

    private void initPostInfo(List<String> infoList) {
        mAdapter.setNewData(infoList);
    }

    private void setDutyStaffData(List<TemplateBean.Preson> presons) {
        if (presons == null) {
            return;
        }
        this.mDutyStaffData = presons;
        mChargeStaffAdapter.setNewData(presons);
    }

    private void setChargeStaffData(List<TemplateBean.Preson> presons) {
        if (presons == null) {
            return;
        }
        this.mChargeStaff = presons;
        mDutyStaffAdapter.setNewData(presons);
    }

    @Subscribe
    public void onEvent(List<TemplateBean.Preson> presonList) {
        if (mFlag == 3) {
            mViewModel.setChargeStaff(presonList, 0);
            mChargeStaffAdapter.setNewData(presonList);
        } else {
            mViewModel.setChargeStaff(presonList, 1);
            mDutyStaffAdapter.setNewData(presonList);
        }
    }

    public void setFlag(int flag) {
        this.mFlag = flag;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        if (requestCode == SELECT_ADDRESS_CALL_BACK_CODE) {
            SelectAddressItem item = (SelectAddressItem) data.getSerializableExtra("data");
            String placeCode = Config.get().getAreaCodeByName(item.getCity(), item.getAddress());
            mBinding.tvLeavePostAddPostArea.setText(Config.get().getAddressByCode(placeCode));
            mViewModel.setAreaInfo(item);
        }
        if (requestCode == SELECT_DEVICE_CALL_BACK_CODE) {
            LeavePostAddPostPostBean.DeviceEntityBean deviceEntityBean = (LeavePostAddPostPostBean.DeviceEntityBean) data.getSerializableExtra("DeviceEntityBean");
            mViewModel.setDeviceResult(deviceEntityBean);
            if (deviceEntityBean != null) {
                mBinding.tvLeavePostAddPostDevice.setText(deviceEntityBean.getDeviceName());
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //取消订阅
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}
