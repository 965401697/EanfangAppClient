package net.eanfang.client.ui.activity.leave_post;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.BuildConfig;
import com.eanfang.base.BaseActivity;
import com.eanfang.biz.rds.base.LViewModelProviders;
import com.eanfang.util.GlideUtil;

import net.eanfang.client.R;
import net.eanfang.client.databinding.ActivityLeavePostCheckDetailBinding;
import net.eanfang.client.ui.activity.leave_post.bean.LeavePostDeviceInfoBean;
import net.eanfang.client.ui.activity.leave_post.viewmodel.LeavePostCheckDetailViewModel;
import net.eanfang.client.ui.adapter.LeavePostCheckDetailAdapter;

import java.text.MessageFormat;
import java.util.ArrayList;

/**
 * @author liangkailun
 * Date ：2019-06-24
 * Describe :图像查岗详情
 */
public class LeavePostCheckDetailActivity extends BaseActivity {
    private ActivityLeavePostCheckDetailBinding mBinding;
    private LeavePostCheckDetailViewModel mViewModel;
    private LeavePostCheckDetailAdapter mAdapter;
    private boolean showTopContent;
    /**
     * 岗位id
     */
    private int mStationId;

    /**
     * 报警id
     */
    private long mAlertId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_leave_post_check_detail);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        setLeftBack(true);
        mStationId = getIntent().getIntExtra("stationId", 0);
        mAlertId = getIntent().getLongExtra("mAlertId", 0);
        showTopContent = getIntent().getBooleanExtra("isShowTopContent", true);
        if (showTopContent) {
            setTitle("图像查岗");
        } else {
            setTitle("联系责任人");
            mBinding.tvLeavePostCheckDetailTitle.setVisibility(View.GONE);
            mBinding.imgLeavePostCheckDetail.setVisibility(View.GONE);
        }
        mAdapter = new LeavePostCheckDetailAdapter();
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                mViewModel.callToPerson(LeavePostCheckDetailActivity.this, adapter, position);
            }
        });
        mBinding.recLeavePostCheckDetailPerson.setLayoutManager(new LinearLayoutManager(this));
        mAdapter.bindToRecyclerView(mBinding.recLeavePostCheckDetailPerson);
    }

    @Override
    protected ViewModel initViewModel() {
        mViewModel = LViewModelProviders.of(this, LeavePostCheckDetailViewModel.class);
        mViewModel.getLeavePostDeviceDetail().observe(this, this::initData);
        if (showTopContent) {
            mViewModel.getDeviceListData(mStationId);
        } else {
            mViewModel.getContactsListData(mAlertId);
        }
        return mViewModel;
    }

    private void initData(LeavePostDeviceInfoBean leavePostDeviceInfoBean) {
        if (leavePostDeviceInfoBean == null) {
            return;
        }
        if (showTopContent) {
            mBinding.tvLeavePostCheckDetailTitle.setText(MessageFormat.format("{0}\t({1})\t{2}", leavePostDeviceInfoBean.getStationName(), leavePostDeviceInfoBean.getStationCode(), leavePostDeviceInfoBean.getDeviceName()));
            GlideUtil.intoImageView(this, BuildConfig.OSS_SERVER + leavePostDeviceInfoBean.getDeviceEntity().getLivePic(), mBinding.imgLeavePostCheckDetail);
        }

        ArrayList<LeavePostDeviceInfoBean.ChargeStaffListBean> beans = new ArrayList<>(leavePostDeviceInfoBean.getChargeStaffList());
        LeavePostDeviceInfoBean.ChargeStaffListBean bean1 = new LeavePostDeviceInfoBean.ChargeStaffListBean();
        bean1.setType(0);
        bean1.setTitle("负责人");
        beans.add(0, bean1);
        if (leavePostDeviceInfoBean.getDutyStaffList().size() > 0) {
            LeavePostDeviceInfoBean.ChargeStaffListBean bean2 = new LeavePostDeviceInfoBean.ChargeStaffListBean();
            bean2.setType(0);
            bean2.setTitle("值班人");
            beans.add(leavePostDeviceInfoBean.getChargeStaffList().size(), bean2);
            beans.addAll(leavePostDeviceInfoBean.getDutyStaffList());
        }
        mAdapter.setNewData(beans);
    }
}
