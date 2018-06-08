package net.eanfang.worker.ui.activity.worksapce.contacts;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.eanfang.BuildConfig;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.config.Config;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.FriendListBean;
import com.eanfang.model.RoleBean;
import com.eanfang.model.SectionBean;
import com.eanfang.ui.activity.OrganizationContactActivity;
import com.eanfang.util.ToastUtil;
import com.facebook.drawee.view.SimpleDraweeView;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import org.greenrobot.eventbus.Subscribe;
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

    private FriendListBean friendBean;
    private SectionBean mSectionBean;
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
                Intent intent = new Intent(this, OrganizationContactActivity.class);
                Uri uri = Uri.parse("worker://yeah!");
//                Intent intent = new Intent("com.eanfang.intent.action.ORG1");
                intent.setData(uri);
                intent.putExtra("isRadio", "isRadio");//是否是单选
                startActivity(intent);
                break;
            case R.id.ll_role:
                startActivityForResult(new Intent(this, AolltRoleActivity.class), ROLE_FLAG);
                break;
            case R.id.tv_sure:

                ToastUtil.get().showToast(AddStaffNextActivity.this, "待开发");
                if (true) return;

                JSONObject object = new JSONObject();
                try {
                    object.put("mobile", friendBean.getMobile());
//                    object.put("realName", );
                    if (!TextUtils.isEmpty(friendBean.getMobile())) {
                        object.put("email", friendBean.getMobile());
                    }
//                    object.put("passwd", etNewName.getText().toString().trim());
                    object.put("avatar", friendBean.getAvatar());

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                EanfangHttp.post(NewApiService.ADD_STAFF)
                        .upJson(object)
                        .execute(new EanfangCallback<JSONObject>(AddStaffNextActivity.this, true, JSONObject.class, (bean) -> {

                            ToastUtil.get().showToast(AddStaffNextActivity.this, "添加成功");

                            finishSelf();

                        }));
                break;
        }
    }

    @Subscribe
    public void onEvent(SectionBean sectionBean) {

        if (sectionBean != null) {
            mSectionBean = sectionBean;
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
