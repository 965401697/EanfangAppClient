package net.eanfang.worker.ui.activity.worksapce.contacts;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.apiservice.UserApi;
import com.eanfang.application.EanfangApplication;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.biz.model.bean.LoginBean;
import com.eanfang.base.network.config.HttpConfig;
import com.eanfang.sys.activity.LoginActivity;
import com.eanfang.biz.model.entity.OrgUnitEntity;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.base.BaseWorkerActivity;
import net.eanfang.worker.ui.fragment.ContactsFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreatTeamDetailActivity extends BaseWorkerActivity {

    @BindView(R.id.tv_company_name)
    TextView tvCompanyName;
    private String mName;
    private long mOrgid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creat_team_detail);
        ButterKnife.bind(this);
        setTitle("创建团队");
        setLeftBack();

        mName = getIntent().getStringExtra("name");
        tvCompanyName.setText(mName);
    }

    @OnClick(R.id.tv_created)
    public void onViewClicked() {
        createCompany();
    }


    /**
     * 创建团队
     */
    private void createCompany() {

        EanfangHttp.post(UserApi.GET_ORGUNIT_SHOP_ADD)
                .params("name", mName)
                .execute(new EanfangCallback<OrgUnitEntity>(this, true, OrgUnitEntity.class, (bean) -> {
                    SwitchCompany(bean.getOrgId());
                }));
    }


    /**
     * @param companyid Go to another company
     */
    private void SwitchCompany(Long companyid) {
        EanfangHttp.get(NewApiService.SWITCH_COMPANY_ALL_LIST)
                .params("companyId", companyid)
                .execute(new EanfangCallback<LoginBean>(this, true, LoginBean.class, (bean) -> {

                    mOrgid = companyid;

                    EanfangApplication.get().remove(LoginBean.class.getName());
                    EanfangApplication.get().set(LoginBean.class.getName(), bean);

                    EanfangHttp.setToken(EanfangApplication.get().getUser().getToken());
                    HttpConfig.get().setToken(EanfangApplication.get().getUser().getToken());
                    EanfangHttp.setWorker();
                    updateData();
                }));
    }

    private void updateData() {
        EanfangHttp.get(UserApi.GET_USER_INFO)
                .execute(new EanfangCallback(this, true, LoginBean.class) {
                    @Override
                    public void onSuccess(Object bean) {
                        super.onSuccess(bean);
                        LoginBean loginBean = (LoginBean) bean;
                        EanfangApplication.get().set(LoginBean.class.getName(),loginBean);

                        startActivity(new Intent(CreatTeamDetailActivity.this, CreatTeamStatusHintActivity.class).
                                putExtra("orgName", mName).putExtra("orgid", mOrgid));
                        ContactsFragment.isRefresh = true;
                        endTransaction(true);
                    }

                    @Override
                    public void onFail(Integer code, String message, JSONObject jsonObject) {
                        super.onFail(code, message, jsonObject);
                        if (code == 50014) {
                            showToast("token已失效,请重新登录");
                            startActivity(new Intent(CreatTeamDetailActivity.this, LoginActivity.class));
                            finish();
                        }
                    }
                });
    }
}
