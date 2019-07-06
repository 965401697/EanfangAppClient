package net.eanfang.client.ui.activity.leave_post;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModel;

import com.eanfang.base.BaseActivity;
import com.eanfang.biz.rds.base.LViewModelProviders;

import net.eanfang.client.R;
import net.eanfang.client.databinding.ActivityLeavePostScreenBinding;
import net.eanfang.client.ui.activity.leave_post.viewmodel.LeavePostScreenViewModel;

/**
 * @author liangkailun
 * Date ：2019-06-25
 * Describe :脱岗筛选页面
 */
public class LeavePostScreenActivity extends BaseActivity {
    private ActivityLeavePostScreenBinding mBinding;
    private LeavePostScreenViewModel mViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_leave_post_screen);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        setLeftBack(true);
        setTitle("筛选");
        mBinding.leavePostScreenOk.setOnClickListener(view -> mViewModel.setScreenResult(LeavePostScreenActivity.this));
        mBinding.tvLeavePostScreenArea.setOnClickListener(view -> mViewModel.gotoChoosePage(LeavePostScreenActivity.this, 0));
        mBinding.tvLeavePostScreenPostName.setOnClickListener(view -> mViewModel.gotoChoosePage(LeavePostScreenActivity.this, 1));
        mBinding.leavePostScreenStatusAll.setOnClickListener(view -> mViewModel.chooseStatus(0));
        mBinding.leavePostScreenStatusProcessed.setOnClickListener(view -> mViewModel.chooseStatus(1));
        mBinding.leavePostScreenStatusUntreated.setOnClickListener(view -> mViewModel.chooseStatus(2));
        mViewModel.chooseStatus(0);
    }

    @Override
    protected ViewModel initViewModel() {
        mViewModel = LViewModelProviders.of(this, LeavePostScreenViewModel.class);
        mViewModel.setBinding(mBinding);
        return mViewModel;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && resultCode == RESULT_OK) {
            mViewModel.getResult(data);
        }
    }
}
