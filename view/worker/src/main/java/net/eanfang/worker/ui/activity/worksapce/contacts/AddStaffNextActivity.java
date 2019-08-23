package net.eanfang.worker.ui.activity.worksapce.contacts;

import android.os.Bundle;
import android.text.TextUtils;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModel;

import com.eanfang.BuildConfig;
import com.eanfang.base.BaseActivity;
import com.eanfang.biz.model.bean.FriendListBean;
import com.eanfang.biz.rds.base.LViewModelProviders;
import com.eanfang.config.Config;
import com.eanfang.util.GlideUtil;

import net.eanfang.worker.R;
import net.eanfang.worker.base.WorkerApplication;
import net.eanfang.worker.databinding.ActivityAddStaffNextBinding;
import net.eanfang.worker.ui.fragment.SelectOrganizationFragment;
import net.eanfang.worker.ui.fragment.SelectRoleFragment;
import net.eanfang.worker.viewmodle.CompanySelectViewModle;

public class AddStaffNextActivity extends BaseActivity {

    private FriendListBean friendBean;

    /**
     * 正在公告 0  已过期 1
     */
    private String[] mTitles = {"选择部门", "分配角色"};
    private BaseActivity.MyPagerAdapter mAdapter;


    private ActivityAddStaffNextBinding activityAddStaffNextBinding;
    private CompanySelectViewModle companySelectViewModle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        activityAddStaffNextBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_staff_next);
        super.onCreate(savedInstanceState);

        friendBean = (FriendListBean) getIntent().getSerializableExtra("bean");
        GlideUtil.intoImageView(this, BuildConfig.OSS_SERVER + friendBean.getAvatar(), activityAddStaffNextBinding.includeInfo.ivUserHeader);
        activityAddStaffNextBinding.includeInfo.tvNamePhone.setText(friendBean.getNickName() + "(" + friendBean.getMobile() + ")");
        if (!TextUtils.isEmpty(friendBean.getAreaCode())) {
            activityAddStaffNextBinding.includeInfo.tvAddress.setText(Config.get().getAddressByCode(friendBean.getAreaCode()) + friendBean.getAddress());
        }

    }

    @Override
    protected ViewModel initViewModel() {
        companySelectViewModle = LViewModelProviders.of(this, CompanySelectViewModle.class);
        activityAddStaffNextBinding.setCompanySelectViewModle(companySelectViewModle);
        companySelectViewModle.setActivityAddStaffNextBinding(activityAddStaffNextBinding);
        companySelectViewModle.getSubmitSuccessData().observe(this, this::doSubmitSuccess);
        return companySelectViewModle;
    }

    @Override
    protected void initView() {
        super.initView();
        setTitle("添加员工");
        setLeftBack(true);
        setRightClick("确定", (view) -> {
            companySelectViewModle.doSubmit(friendBean.getAccId(), friendBean.getMobile());
        });
        mAdapter = new BaseActivity.MyPagerAdapter(getSupportFragmentManager(), mTitles);
        mAdapter.getFragments().add(SelectOrganizationFragment.getInstance(companySelectViewModle));
        mAdapter.getFragments().add(SelectRoleFragment.getInstance(companySelectViewModle));
        activityAddStaffNextBinding.vpSelect.setCurrentItem(0);
        activityAddStaffNextBinding.tlSelect.setViewPager(activityAddStaffNextBinding.vpSelect, mTitles, this, mAdapter.getFragments());
    }

    private void doSubmitSuccess(Object o) {
        showToast("添加成功");
        finish();
        WorkerApplication.get().closeActivity(AddStaffActivity.class);
        WorkerApplication.get().closeActivity(AddStaffFriendActivity.class);
        WorkerApplication.get().closeActivity(SearchStaffActivity.class);
    }


}
