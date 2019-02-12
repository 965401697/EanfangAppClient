package net.eanfang.client.ui.activity.worksapce.contacts;

import android.content.Intent;
import android.os.Bundle;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.eanfang.BuildConfig;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.config.Config;

import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.FriendListBean;
import com.eanfang.model.OrganizationBean;
import com.eanfang.model.SectionBean;

import com.eanfang.ui.activity.SelectOrganizationActivity;
import com.eanfang.util.ToastUtil;
import com.facebook.drawee.view.SimpleDraweeView;
import com.yaf.sys.entity.AccountEntity;
import com.yaf.sys.entity.UserEntity;

import net.eanfang.client.R;
import net.eanfang.client.ui.base.BaseClientActivity;

import org.greenrobot.eventbus.Subscribe;


import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddStaffNextActivity extends BaseClientActivity {

    @BindView(R.id.iv_user_header)
    SimpleDraweeView ivUserHeader;
    @BindView(R.id.tv_name_phone)
    TextView tvNamePhone;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_section_name)
    TextView tvSectionName;
    @BindView(R.id.tv_role)
    TextView tvRole;

    private SectionBean mSectionBean;
    private SectionBean.ChildrenBean mChildrenBean;
    private FriendListBean friendBean;

    private ArrayList<String> roleIdList = new ArrayList<>();
    private ArrayList<String> roleNameList = new ArrayList<>();

    private final int ROLE_FLAG = 101;
    private OrganizationBean mOrganizationBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_staff_next);
        ButterKnife.bind(this);
        setTitle("添加员工");
        setLeftBack();
        friendBean = (FriendListBean) getIntent().getSerializableExtra("bean");

        ivUserHeader.setImageURI(BuildConfig.OSS_SERVER + friendBean.getAvatar());
        tvNamePhone.setText(friendBean.getNickName() + "(" + friendBean.getMobile() + ")");
        if (!TextUtils.isEmpty(friendBean.getAreaCode())) {
            tvAddress.setText(Config.get().getAddressByCode(friendBean.getAreaCode()) + friendBean.getAddress());
        }

        setRightTitle("确定");
        setRightTitleOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSure();
            }
        });
    }

    @OnClick({R.id.ll_section, R.id.ll_role})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_section:
                Intent intent = new Intent(this, SelectOrganizationActivity.class);
                intent.putExtra("isSection", "isSection");//是否是组织架构
                intent.putExtra("isAdd", "isAdd");//是否是组织架构
                startActivity(intent);
                break;
            case R.id.ll_role:
                Intent in = new Intent(this, AolltRoleActivity.class);
                in.putStringArrayListExtra("roleNameList", roleNameList);
                startActivityForResult(in, ROLE_FLAG);
                break;

        }
    }

    private void onSure() {
        if (TextUtils.isEmpty(tvSectionName.getText().toString().trim())) {
            ToastUtil.get().showToast(this, "部门不能为空");
            return;
        }

        if (TextUtils.isEmpty(tvRole.getText().toString().trim())) {
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

                                        endTransaction(true);
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
            tvSectionName.setText(mOrganizationBean.getOrgName());

        } else if (o instanceof SectionBean) {
            mSectionBean = (SectionBean) o;
            tvSectionName.setText(mSectionBean.getOrgName());

        } else if (o instanceof SectionBean.ChildrenBean) {
            mChildrenBean = (SectionBean.ChildrenBean) o;
            tvSectionName.setText(mChildrenBean.getOrgName());

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
                tvRole.setText(stringBuffer.toString().substring(0, stringBuffer.toString().length() - 1));
            }
        }
    }
}
