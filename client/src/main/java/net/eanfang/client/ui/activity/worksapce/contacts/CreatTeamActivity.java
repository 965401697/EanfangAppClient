package net.eanfang.client.ui.activity.worksapce.contacts;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import com.alibaba.fastjson.JSONObject;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.apiservice.UserApi;
import com.eanfang.application.EanfangApplication;
import com.eanfang.config.FastjsonConfig;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.LoginBean;
import com.yaf.sys.entity.OrgUnitEntity;

import net.eanfang.client.R;
import net.eanfang.client.ui.activity.LoginActivity;
import net.eanfang.client.ui.base.BaseClientActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreatTeamActivity extends BaseClientActivity {

    @BindView(R.id.et_input_company)
    EditText etInputCompany;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creat_team);
        ButterKnife.bind(this);
        setLeftBack();
        setTitle("创建团队");

    }

    private void createCompany() {
        EanfangHttp.post(UserApi.GET_ORGUNIT_ENT_ADD)
                .params("name", etInputCompany.getText().toString().trim())
                .execute(new EanfangCallback<OrgUnitEntity>(this, true, OrgUnitEntity.class, (bean) -> {
                    SwitchCompany(bean.getOrgId());
                    updateData();
//                    mRefreshListener.refreshData();
                    finish();
                }));
    }

    /**
     * @param companyid Go to another company
     */
    private void SwitchCompany(Long companyid) {
        EanfangHttp.get(NewApiService.SWITCH_COMPANY_ALL_LIST)
                .params("companyId", companyid)
                .execute(new EanfangCallback<LoginBean>(this, true, LoginBean.class, (bean) -> {
                    EanfangApplication.get().remove(LoginBean.class.getName());
                    EanfangApplication.get().set(LoginBean.class.getName(), JSONObject.toJSONString(bean, FastjsonConfig.config));

                    EanfangHttp.setToken(EanfangApplication.get().getUser().getToken());
                    EanfangHttp.setClient();

                    finish();
                }));
    }

    private void updateData() {
        EanfangHttp.get(UserApi.GET_USER_INFO)
                .execute(new EanfangCallback(this, true, LoginBean.class) {
                    @Override
                    public void onSuccess(Object bean) {
                        super.onSuccess(bean);
                        LoginBean loginBean = (LoginBean) bean;
                        EanfangApplication.get().set(LoginBean.class.getName(), JSONObject.toJSONString(loginBean, FastjsonConfig.config));

                    }

                    @Override
                    public void onFail(Integer code, String message, JSONObject jsonObject) {
                        super.onFail(code, message, jsonObject);
                        if (code == 50014) {
                            showToast("token已失效,请重新登录");
                            startActivity(new Intent(CreatTeamActivity.this, LoginActivity.class));
                            finish();
                        }
                    }
                });
    }

    @OnClick(R.id.tv_confirm)
    public void onViewClicked() {
        createCompany();
    }
}
