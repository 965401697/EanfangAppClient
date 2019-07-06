package net.eanfang.client.ui.activity.leave_post;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.eanfang.base.BaseActivity;
import com.eanfang.biz.rds.base.LViewModelProviders;

import net.eanfang.client.R;
import net.eanfang.client.databinding.ActivityLeavePostDetailBinding;
import net.eanfang.client.ui.activity.leave_post.bean.LeavePostAlertInfoDetailBean;
import net.eanfang.client.ui.activity.leave_post.bean.LeavePostKeyValueBean;
import net.eanfang.client.ui.activity.leave_post.viewmodel.LeavePostDetailViewModel;
import net.eanfang.client.ui.adapter.LeavePostDetailImageAdapter;
import net.eanfang.client.ui.adapter.LeavePostDetailInfoAdapter;

import java.util.List;

/**
 * @author liangkailun
 * Date ：2019-06-30
 * Describe :报警详情
 */
public class LeavePostDetailActivity extends BaseActivity {
    private ActivityLeavePostDetailBinding mBinding;
    private LeavePostDetailViewModel mViewModel;
    private LeavePostDetailInfoAdapter mInfoAdapter;
    private LeavePostDetailImageAdapter mImageAdapter;
    private LeavePostDetailImageAdapter mVideoAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_leave_post_detail);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        setLeftBack(true);
        setTitle("报警详情");
        setRightBack(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        setRightTitle("图像查岗");
        int alertId = getIntent().getIntExtra("alertId", 0);
        mViewModel.getAlertInfoData(String.valueOf(alertId));
        mInfoAdapter = new LeavePostDetailInfoAdapter(R.layout.item_leave_post_detail_info);
        mBinding.recLeavePostDetailInfo.setLayoutManager(new LinearLayoutManager(this));
        mInfoAdapter.bindToRecyclerView(mBinding.recLeavePostDetailInfo);

        mBinding.btnLeavePoetDetail.setOnClickListener(view -> mViewModel.gotoCallPersonPage(LeavePostDetailActivity.this));

        mBinding.recLeavePostDetailImg.setLayoutManager(new GridLayoutManager(this, 3));
        mBinding.recLeavePostDetailAudio.setLayoutManager(new GridLayoutManager(this, 3));
        mImageAdapter = new LeavePostDetailImageAdapter();
        mVideoAdapter = new LeavePostDetailImageAdapter();
        mImageAdapter.bindToRecyclerView(mBinding.recLeavePostDetailImg);
        mVideoAdapter.bindToRecyclerView(mBinding.recLeavePostDetailAudio);
    }

    @Override
    protected ViewModel initViewModel() {
        mViewModel = LViewModelProviders.of(this, LeavePostDetailViewModel.class);
        mViewModel.getLeavePostAlertInfoData().observe(this, this::setData);
        mViewModel.getLeavePostAlertList().observe(this, this::setInfoData);
        return mViewModel;
    }

    private void setInfoData(List<LeavePostKeyValueBean> leavePostKeyValueBeans) {
        mInfoAdapter.setNewData(leavePostKeyValueBeans);
    }

    private void setData(LeavePostAlertInfoDetailBean leavePostAlertInfoDetailBean) {
        mImageAdapter.setNewData(leavePostAlertInfoDetailBean.getAlertImgList());
        mVideoAdapter.setNewData(leavePostAlertInfoDetailBean.getAlertVideoList());
    }

}
