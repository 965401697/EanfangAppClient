package net.eanfang.client.ui.activity.leave_post;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.GridLayoutManager;

import com.eanfang.base.BaseActivity;
import com.eanfang.biz.model.SelectAddressItem;
import com.eanfang.biz.model.TemplateBean;
import com.eanfang.biz.rds.base.LViewModelProviders;
import com.eanfang.config.Config;

import net.eanfang.client.R;
import net.eanfang.client.databinding.ActivityLeavePostAddPostBinding;
import net.eanfang.client.ui.activity.leave_post.bean.LeavePostDeviceInfoBean;
import net.eanfang.client.ui.activity.leave_post.viewmodel.LeavePostAddPostViewModel;
import net.eanfang.client.ui.activity.worksapce.oa.workreport.OAPersonAdaptet;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liangkailun
 * Date ：2019-06-24
 * Describe :添加岗位
 */
public class LeavePostAddPostActivity extends BaseActivity {
    private static final int SELECT_ADDRESS_CALL_BACK_CODE = 1;
    private ActivityLeavePostAddPostBinding mBinding;
    private LeavePostAddPostViewModel mViewModel;
    private List<TemplateBean.Preson> whoList = new ArrayList<>();
    private OAPersonAdaptet mChargeStaffAdapter;
    private OAPersonAdaptet mDutyStaffAdapter;
    private int mFlag;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_leave_post_add_post);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        //岗位类型 0：添加 1只读
        int postType = getIntent().getIntExtra("postType", 0);
        setLeftBack(true);
        if (postType == 0) {
            setTitle("添加岗位");
        } else {
            int stationId = getIntent().getIntExtra("stationId", 0);
            setTitle("岗位详情");
            setRightClick(view -> {
                setViewEnable(true);
                setRightTitle("确定");
                setRightClick(view1 -> {
                    mViewModel.updatePostInfo();
                });
            });
            setRightTitle("编辑");
            setViewEnable(false);
            mViewModel.getPostInfo(stationId);
        }
        mBinding.tvLeavePostAddPostDevice.setOnClickListener(view -> mViewModel.addDevice(LeavePostAddPostActivity.this));
        mBinding.btnLeavePostAddCommit.setOnClickListener(view -> mViewModel.addPostCommit(mBinding));
        mBinding.tvLeavePostAddPostArea.setOnClickListener(view -> {
            mViewModel.gotoChooseArea(LeavePostAddPostActivity.this, SELECT_ADDRESS_CALL_BACK_CODE);
        });
        mBinding.switchBtnLeavePostAddPostStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String defaultDevice = "请选择";
                if (defaultDevice.equals(mBinding.tvLeavePostAddPostDevice.getText().toString()) && !mBinding.switchBtnLeavePostAddPostStatus.isChecked()){
                    showToast("请先选择绑定设备");
                    mBinding.switchBtnLeavePostAddPostStatus.setChecked(false);
                }
            }
        });
        initRecView();
    }

    /**
     * 设置view是否可编辑
     *
     * @param enable
     */
    private void setViewEnable(boolean enable) {
        mBinding.edtLeavePostAddPostName.setEnabled(enable);
        mBinding.edtLeavePostAddPostPosition.setEnabled(enable);
        mBinding.edtLeavePostAddPostNumber.setEnabled(enable);
        mBinding.tvLeavePostAddPostDevice.setEnabled(enable);
        mBinding.tvLeavePostAddPostArea.setEnabled(enable);
        mBinding.switchBtnLeavePostAddPostStatus.setEnabled(enable);
        mBinding.tvLeavePostAddPostTime.setEnabled(enable);
    }

    private void initRecView() {
        mBinding.recLeavePostAddInDuty.setLayoutManager(new GridLayoutManager(this, 5));
        mBinding.recLeavePostAddInCharge.setLayoutManager(new GridLayoutManager(this, 5));
        mChargeStaffAdapter = new OAPersonAdaptet(this, new ArrayList<TemplateBean.Preson>(), 3);
        mDutyStaffAdapter = new OAPersonAdaptet(this, new ArrayList<TemplateBean.Preson>(), 2);
        mBinding.recLeavePostAddInDuty.setAdapter(mChargeStaffAdapter);
        mBinding.recLeavePostAddInCharge.setAdapter(mDutyStaffAdapter);
    }

    @Override
    protected ViewModel initViewModel() {
        mViewModel = LViewModelProviders.of(this, LeavePostAddPostViewModel.class);
        mViewModel.getLeavePostDeviceDetail().observe(this, this::initPostInfo);
        mViewModel.getChargeStaffList().observe(this, this:: setChargeStaffData);
        mViewModel.getDutyStaffList().observe(this, this:: setDutyStaffData);
        return mViewModel;
    }

    private void setDutyStaffData(List<TemplateBean.Preson> presons) {
        mChargeStaffAdapter.setNewData(presons);
    }

    private void setChargeStaffData(List<TemplateBean.Preson> presons) {
        mDutyStaffAdapter.setNewData(presons);
    }

    private void initPostInfo(LeavePostDeviceInfoBean leavePostDeviceInfoBean) {
        mBinding.edtLeavePostAddPostName.setText(leavePostDeviceInfoBean.getStationName());
        mBinding.edtLeavePostAddPostPosition.setText(leavePostDeviceInfoBean.getStationArea());
        mBinding.edtLeavePostAddPostNumber.setText(leavePostDeviceInfoBean.getStationCode());
        mBinding.tvLeavePostAddPostArea.setText(leavePostDeviceInfoBean.getStationPlaceCode());
        mBinding.tvLeavePostAddPostDevice.setText(leavePostDeviceInfoBean.getDeviceName());
//        mBinding.tvLeavePostAddPostTime.setText(leavePostDeviceInfoBean.get);
        mBinding.switchBtnLeavePostAddPostStatus.setChecked(leavePostDeviceInfoBean.getStatus() == 0);
    }

    @Subscribe
    public void onEvent(List<TemplateBean.Preson> presonList) {
        whoList.clear();
        whoList.addAll(presonList);
        if (mFlag == 3) {
            mChargeStaffAdapter.setNewData(whoList);
        } else {
            mDutyStaffAdapter.setNewData(whoList);
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
            Log.e("address", item.toString());
            String placeCode = Config.get().getAreaCodeByName(item.getCity(), item.getAddress());
            mBinding.tvLeavePostAddPostArea.setText(Config.get().getAddressByCode(placeCode));
            mViewModel.setAreaInfo(item);
        }
    }
}
