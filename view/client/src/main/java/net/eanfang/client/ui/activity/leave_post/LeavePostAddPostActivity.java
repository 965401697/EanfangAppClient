package net.eanfang.client.ui.activity.leave_post;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

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
    private OAPersonAdaptet whoPlanAdapter1;
    private OAPersonAdaptet whoPlanAdapter2;
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
            setTitle("岗位详情");
            setRightBack(view -> {
                showToast("可编辑");
                setViewEnable(true);
            });
            setRightTitle("编辑");
            setViewEnable(false);
        }
        mBinding.tvLeavePostAddPostDevice.setOnClickListener(view -> mViewModel.addDevice(LeavePostAddPostActivity.this));
        mBinding.btnLeavePostAddCommit.setOnClickListener(view -> mViewModel.addPostCommit(mBinding));
        mBinding.tvLeavePostAddPostArea.setOnClickListener(view -> {
            mViewModel.gotoChooseArea(LeavePostAddPostActivity.this, SELECT_ADDRESS_CALL_BACK_CODE);
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
        mBinding.tvLeavePostAddPostStatus.setEnabled(enable);
        mBinding.tvLeavePostAddPostTime.setEnabled(enable);
    }

    private void initRecView() {
        mBinding.recLeavePostAddInDuty.setLayoutManager(new GridLayoutManager(this, 5));
        mBinding.recLeavePostAddInCharge.setLayoutManager(new GridLayoutManager(this, 5));
        whoPlanAdapter1 = new OAPersonAdaptet(this, new ArrayList<TemplateBean.Preson>(), 3);
        whoPlanAdapter2 = new OAPersonAdaptet(this, new ArrayList<TemplateBean.Preson>(), 2);
        mBinding.recLeavePostAddInDuty.setAdapter(whoPlanAdapter1);
        mBinding.recLeavePostAddInCharge.setAdapter(whoPlanAdapter2);
    }

    @Override
    protected ViewModel initViewModel() {
        mViewModel = LViewModelProviders.of(this, LeavePostAddPostViewModel.class);

        return mViewModel;
    }

    @Subscribe
    public void onEvent(List<TemplateBean.Preson> presonList) {
        whoList.clear();
        whoList.addAll(presonList);
        if (mFlag == 3) {
            whoPlanAdapter1.setNewData(whoList);
        } else {
            whoPlanAdapter2.setNewData(whoList);
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
