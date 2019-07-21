package net.eanfang.client.ui.activity.worksapce.contacts;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.apiservice.UserApi;
import com.eanfang.base.kit.cache.CacheKit;
import com.eanfang.base.kit.cache.CacheMod;
import com.eanfang.base.network.config.HttpConfig;
import com.eanfang.biz.model.bean.LoginBean;
import com.eanfang.biz.model.entity.OrgUnitEntity;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.sys.activity.LoginActivity;

import net.eanfang.client.R;
import net.eanfang.client.base.ClientApplication;
import net.eanfang.client.ui.base.BaseClientActivity;
import net.eanfang.client.ui.fragment.ContactsFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreatTeamDetailActivity extends BaseClientActivity {

    @BindView(R.id.tv_company_name)
    TextView tvCompanyName;
    private String mName;
    private long mOrgid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_creat_team_detail);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
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

        EanfangHttp.post(UserApi.GET_ORGUNIT_ENT_ADD)
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

                    CacheKit.get().put(LoginBean.class.getName(), bean, CacheMod.All);

                    EanfangHttp.setToken(ClientApplication.get().getLoginBean().getToken());
                    HttpConfig.get().setToken(ClientApplication.get().getLoginBean().getToken());
                    EanfangHttp.setClient();
                    updateData();
                }));
    }

    private void updateData() {
        EanfangHttp.get(UserApi.GET_USER_INFO)
                .execute(new EanfangCallback(this, true, LoginBean.class) {
                    @Override
                    public void onSuccess(Object bean) {
                        super.onSuccess(bean);
                        CacheKit.get().put(LoginBean.class.getName(), bean, CacheMod.All);

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
