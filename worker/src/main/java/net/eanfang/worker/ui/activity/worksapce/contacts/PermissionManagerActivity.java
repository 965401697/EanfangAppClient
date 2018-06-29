package net.eanfang.worker.ui.activity.worksapce.contacts;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.eanfang.BuildConfig;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.apiservice.UserApi;
import com.eanfang.config.Config;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.RoleBean;
import com.eanfang.model.TemplateBean;
import com.eanfang.model.device.User;
import com.eanfang.ui.activity.SelectOrganizationActivity;
import com.eanfang.util.ToastUtil;
import com.facebook.drawee.view.SimpleDraweeView;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PermissionManagerActivity extends BaseWorkerActivity {

    @BindView(R.id.iv_user_header)
    SimpleDraweeView ivUserHeader;
    @BindView(R.id.tv_name_phone)
    TextView tvNamePhone;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_role)
    TextView tvRole;
    @BindView(R.id.ll_select_staff)
    LinearLayout llSelectStaff;
    @BindView(R.id.ll_role)
    LinearLayout llRole;
    @BindView(R.id.rl_checked_staff)
    RelativeLayout rlCheckedStaff;

    private User mBean;

    private ArrayList<String> roleIdList = new ArrayList<>();
    private ArrayList<String> roleNameList = new ArrayList<>();

    private final int ROLE_FLAG = 101;
    private String departmentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission_manager);
        ButterKnife.bind(this);
        setTitle("权限管理");
        setLeftBack();

        setRightTitle("确认授权");
        setRightTitleOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subMermission();
            }
        });
    }


    @OnClick({R.id.rl_checked_staff, R.id.ll_select_staff, R.id.ll_role})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_checked_staff:
            case R.id.ll_select_staff:
                Intent intent = new Intent(this, SelectOrganizationActivity.class);
                intent.putExtra("isRadio", "isRadio");
                startActivity(intent);
                break;

            case R.id.ll_role:
                Intent in = new Intent(this, AolltRoleActivity.class);
                in.putStringArrayListExtra("roleNameList", roleNameList);
                startActivityForResult(in, ROLE_FLAG);
                break;
        }
    }

    private void subMermission() {
        if (TextUtils.isEmpty(departmentId)) {
            ToastUtil.get().showToast(this, "员工不能为空");
            return;
        }
        if (TextUtils.isEmpty(tvRole.getText().toString().trim())) {
            ToastUtil.get().showToast(this, "角色不能为空");
            return;
        }

        //添加角色
        EanfangHttp.post(NewApiService.ADD_STAFF_ROLE + "/" + mBean.getAccId())
                .upJson(JSON.toJSONString(roleIdList))
                .execute(new EanfangCallback<JSONObject>(PermissionManagerActivity.this, true, JSONObject.class) {

                    @Override
                    public void onSuccess(JSONObject bean) {
                        ToastUtil.get().showToast(PermissionManagerActivity.this, "授权成功");

                        endTransaction(true);
                    }

                });


    }

    @Subscribe
    public void onEvent(List<TemplateBean.Preson> presonList) {


        if (presonList.size() > 0) {
            TemplateBean.Preson bean = (TemplateBean.Preson) presonList.get(0);
            departmentId = bean.getDepartmentId();
            llSelectStaff.setVisibility(View.GONE);
            rlCheckedStaff.setVisibility(View.VISIBLE);
            llRole.setVisibility(View.VISIBLE);


            EanfangHttp.get(UserApi.POST_USER_INFO + bean.getId())
                    .execute(new EanfangCallback<User>(this, true, User.class, (b) -> {
                        mBean = b;

                        ivUserHeader.setImageURI(BuildConfig.OSS_SERVER + b.getAvatar());
                        tvNamePhone.setText(b.getNickName() + "(" + b.getMobile() + ")");
                        tvAddress.setText(Config.get().getAddressByCode(b.getAreaCode()) + b.getAddress());


                        EanfangHttp.get(NewApiService.MY_CURREMT_LIST_ROLE + mBean.getAccId())
                                .execute(new EanfangCallback<RoleBean>(PermissionManagerActivity.this, true, RoleBean.class, true, (list) -> {
                                    for (RoleBean roleBean : list) {
                                        roleNameList.add(roleBean.getRoleName());
                                    }
                                }));

                    }));


        } else {
            llSelectStaff.setVisibility(View.VISIBLE);
            rlCheckedStaff.setVisibility(View.GONE);
            llRole.setVisibility(View.GONE);
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
