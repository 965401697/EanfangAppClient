package net.eanfang.client.ui.activity.worksapce.contacts;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModel;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.eanfang.BuildConfig;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.base.BaseActivity;
import com.eanfang.biz.model.bean.FriendListBean;
import com.eanfang.biz.model.bean.OrganizationBean;
import com.eanfang.biz.model.bean.SectionBean;
import com.eanfang.biz.model.entity.AccountEntity;
import com.eanfang.biz.model.entity.UserEntity;
import com.eanfang.biz.rds.base.LViewModelProviders;
import com.eanfang.config.Config;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.ui.activity.SelectOrganizationActivity;
import com.eanfang.util.GlideUtil;
import com.eanfang.util.ToastUtil;

import net.eanfang.client.R;
import net.eanfang.client.databinding.ActivityAddStaffNextBinding;
import net.eanfang.client.ui.fragment.SelectOrganizationFragment;
import net.eanfang.client.ui.fragment.SelectRoleFragment;
import net.eanfang.client.viewmodel.CompanySelectViewModle;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import butterknife.OnClick;

/**
 * @author guanluocang
 * @data 2019/8/20  14:51
 * @description 选择部门 分配角色
 */

public class AddStaffNextActivity extends BaseActivity {


    private SectionBean mSectionBean;
    private SectionBean.ChildrenBean mChildrenBean;
    private FriendListBean friendBean;

    private ArrayList<String> roleIdList = new ArrayList<>();
    private ArrayList<String> roleNameList = new ArrayList<>();

    private final int ROLE_FLAG = 101;
    private OrganizationBean mOrganizationBean;

    /**
     * 正在公告 0  已过期 1
     */
    private String[] mTitles = {"选择部门", "分配角色"};
    private ViewPager mTenderViewPager;
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
            onSure();
        });
        mTenderViewPager = activityAddStaffNextBinding.vpSelect;
        mAdapter = new MyPagerAdapter(getSupportFragmentManager(), mTitles);
        mAdapter.getFragments().add(SelectOrganizationFragment.getInstance(companySelectViewModle));
        mAdapter.getFragments().add(SelectRoleFragment.getInstance(companySelectViewModle));
        mTenderViewPager.setAdapter(mAdapter);
        mTenderViewPager.setCurrentItem(0);
    }

    @Override
    protected ViewModel initViewModel() {
        companySelectViewModle = LViewModelProviders.of(this, CompanySelectViewModle.class);
        activityAddStaffNextBinding.setCompanySelectViewModle(companySelectViewModle);
        companySelectViewModle.setActivityAddStaffNextBinding(activityAddStaffNextBinding);
        return companySelectViewModle;
    }

    @OnClick({R.id.ll_section, R.id.ll_role})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            // 选择部门
            case R.id.ll_section:
                Intent intent = new Intent(this, SelectOrganizationActivity.class);
                //是否是组织架构
                intent.putExtra("isSection", "isSection");
                //是否是组织架构
                intent.putExtra("isAdd", "isAdd");
                startActivity(intent);
                break;
            // 分配角色
            case R.id.ll_role:
                Intent in = new Intent(this, AolltRoleActivity.class);
                in.putStringArrayListExtra("roleNameList", roleNameList);
                startActivityForResult(in, ROLE_FLAG);
                break;
            default:
                break;
        }
    }

    private void onSure() {
        if (TextUtils.isEmpty(activityAddStaffNextBinding.tvSectionName.getText().toString().trim())) {
            ToastUtil.get().showToast(this, "部门不能为空");
            return;
        }

        if (TextUtils.isEmpty(activityAddStaffNextBinding.tvRole.getText().toString().trim())) {
            ToastUtil.get().showToast(this, "角色不能为空");
            return;
        }


        UserEntity userEntity = new UserEntity();


        if (mSectionBean != null) {
            userEntity.setDepartmentId(Long.parseLong(mSectionBean.getOrgId()));
        } else if (mChildrenBean != null) {
            userEntity.setDepartmentId(Long.parseLong(mChildrenBean.getOrgId()));
        } else {
            userEntity.setDepartmentId(Long.parseLong(mOrganizationBean.getOrgId()));
        }

        AccountEntity accountEntity = new AccountEntity();

        accountEntity.setAccId(Long.parseLong(friendBean.getAccId()));
        accountEntity.setMobile(friendBean.getMobile());
        userEntity.setAccountEntity(accountEntity);

        com.alibaba.fastjson.JSONObject jsonObject = (com.alibaba.fastjson.JSONObject) JSON.toJSON(userEntity);


        EanfangHttp.post(NewApiService.ADD_STAFF)
                .upJson(jsonObject.toJSONString())
                .execute(new EanfangCallback<UserEntity>(AddStaffNextActivity.this, true, UserEntity.class) {
                    @Override
                    public void onSuccess(UserEntity bean) {

                        //添加角色
                        EanfangHttp.post(NewApiService.ADD_STAFF_ROLE + "/" + bean.getUserId())
                                .upJson(JSON.toJSONString(roleIdList))
                                .execute(new EanfangCallback<JSONObject>(AddStaffNextActivity.this, true, JSONObject.class) {

                                    @Override
                                    public void onSuccess(JSONObject bean) {
                                        ToastUtil.get().showToast(AddStaffNextActivity.this, "添加成功");

//                                        endTransaction(true);
                                    }

                                });
                    }


                });
    }

    @Subscribe
    public void onEvent(Object o) {

        mSectionBean = null;
        mChildrenBean = null;

        if (o instanceof OrganizationBean) {
            mOrganizationBean = (OrganizationBean) o;
            activityAddStaffNextBinding.tvSectionName.setText(mOrganizationBean.getOrgName());

        } else if (o instanceof SectionBean) {
            mSectionBean = (SectionBean) o;
            activityAddStaffNextBinding.tvSectionName.setText(mSectionBean.getOrgName());

        } else if (o instanceof SectionBean.ChildrenBean) {
            mChildrenBean = (SectionBean.ChildrenBean) o;
            activityAddStaffNextBinding.tvSectionName.setText(mChildrenBean.getOrgName());

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == ROLE_FLAG) {
                roleIdList.clear();
                roleNameList.clear();

                roleIdList = data.getStringArrayListExtra("roleIdList");
                roleNameList = data.getStringArrayListExtra("roleNameList");

                StringBuffer stringBuffer = new StringBuffer();
                for (String s : roleNameList) {
                    stringBuffer.append(s + ",");
                }
                activityAddStaffNextBinding.tvRole.setText(stringBuffer.toString().substring(0, stringBuffer.toString().length() - 1));

            }
        }
    }
}
