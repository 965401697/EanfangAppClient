package net.eanfang.client.ui.activity.worksapce.contacts;

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

import net.eanfang.client.R;
import net.eanfang.client.base.ClientApplication;
import net.eanfang.client.databinding.ActivityAddStaffNextBinding;
import net.eanfang.client.ui.fragment.SelectOrganizationFragment;
import net.eanfang.client.ui.fragment.SelectRoleFragment;
import net.eanfang.client.viewmodel.CompanySelectViewModle;

/**
 * @author guanluocang
 * @data 2019/8/20  14:51
 * @description 选择部门 分配角色
 */

public class AddStaffNextActivity extends BaseActivity {


    private FriendListBean friendBean;

    /**
     * 正在公告 0  已过期 1
     */
    private String[] mTitles = {"选择部门", "分配角色"};
    private MyPagerAdapter mAdapter;


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
    protected void initView() {
        super.initView();
        setTitle("添加员工");
        setLeftBack(true);
        setRightClick("确定", (view) -> {
            companySelectViewModle.doSubmit(friendBean.getAccId(), friendBean.getMobile());
        });
        mAdapter = new MyPagerAdapter(getSupportFragmentManager(), mTitles);
        mAdapter.getFragments().add(SelectOrganizationFragment.getInstance(companySelectViewModle));
        mAdapter.getFragments().add(SelectRoleFragment.getInstance(companySelectViewModle));
        activityAddStaffNextBinding.vpSelect.setCurrentItem(0);
        activityAddStaffNextBinding.tlSelect.setViewPager(activityAddStaffNextBinding.vpSelect, mTitles, this, mAdapter.getFragments());
    }

    @Override
    protected ViewModel initViewModel() {
        companySelectViewModle = LViewModelProviders.of(this, CompanySelectViewModle.class);
        activityAddStaffNextBinding.setCompanySelectViewModle(companySelectViewModle);
        companySelectViewModle.setActivityAddStaffNextBinding(activityAddStaffNextBinding);
        companySelectViewModle.getSubmitSuccessData().observe(this, this::doSubmitSuccess);
        return companySelectViewModle;
    }

    private void doSubmitSuccess(Object o) {
        showToast("添加成功");
        finish();
        ClientApplication.get().closeActivity(AddStaffActivity.class);
        ClientApplication.get().closeActivity(AddStaffFriendActivity.class);
        ClientApplication.get().closeActivity(SearchStaffActivity.class);
    }

}
