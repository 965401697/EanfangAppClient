package net.eanfang.worker.ui.activity.worksapce.contacts;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.eanfang.BuildConfig;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.config.Config;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.FriendListBean;
import com.eanfang.model.OrganizationBean;
import com.eanfang.model.RoleBean;
import com.eanfang.model.SectionBean;
import com.eanfang.model.device.User;
import com.eanfang.ui.activity.SelectOrganizationContactActivity;
import com.eanfang.util.ToastUtil;
import com.facebook.drawee.view.SimpleDraweeView;
import com.yaf.sys.entity.AccountEntity;
import com.yaf.sys.entity.UserEntity;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import org.greenrobot.eventbus.Subscribe;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddStaffNextActivity extends BaseWorkerActivity {

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
    private FriendListBean friendBean;
    private RoleBean roleBean;

    private final int ROLE_FLAG = 101;

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
        tvAddress.setText(Config.get().getAddressByCode(friendBean.getAreaCode()) + friendBean.getAddress());

    }

    @OnClick({R.id.ll_section, R.id.ll_role, R.id.tv_sure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_section:
                Intent intent = new Intent(this, SelectOrganizationContactActivity.class);
                Uri uri = Uri.parse("worker://yeah!");
                intent.setData(uri);
                intent.putExtra("isRadio", "isRadio");//是否是单选
                startActivity(intent);
                break;
            case R.id.ll_role:
                startActivityForResult(new Intent(this, AolltRoleActivity.class), ROLE_FLAG);
                break;
            case R.id.tv_sure:

                if (TextUtils.isEmpty(tvSectionName.getText().toString().trim())) {
                    ToastUtil.get().showToast(this, "部门不能为空");
                    return;
                }

                if (TextUtils.isEmpty(tvRole.getText().toString().trim())) {
                    ToastUtil.get().showToast(this, "角色不能为空");
                    return;
                }

                UserEntity userEntity = new UserEntity();

                userEntity.setDepartmentId(Long.parseLong(mSectionBean.getOrgId()));

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
                                com.alibaba.fastjson.JSONArray array = new com.alibaba.fastjson.JSONArray();
                                array.add(Long.parseLong(roleBean.getRoleId()));


                                //添加角色
                                EanfangHttp.post(NewApiService.ADD_STAFF_ROLE + "/" + bean.getAccountEntity().getAccId())
                                        .upJson(array.toJSONString())
                                        .execute(new EanfangCallback<com.alibaba.fastjson.JSONObject>(AddStaffNextActivity.this, true, com.alibaba.fastjson.JSONObject.class) {

                                            @Override
                                            public void onSuccess(com.alibaba.fastjson.JSONObject bean) {
                                                ToastUtil.get().showToast(AddStaffNextActivity.this, "添加成功");

                                                endTransaction(true);
                                            }

                                        });
                            }


                        });
                break;
        }
    }

    @Subscribe
    public void onEvent(Object o) {

        if (o instanceof OrganizationBean) {
            OrganizationBean organizationBean = (OrganizationBean) o;
            tvSectionName.setText(organizationBean.getOrgName());

        } else if (o instanceof SectionBean) {
            mSectionBean = (SectionBean) o;
            tvSectionName.setText(mSectionBean.getOrgName());

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == ROLE_FLAG) {
                roleBean = (RoleBean) data.getSerializableExtra("bean");
                tvRole.setText(roleBean.getRoleName());
            }
        }
    }
}
