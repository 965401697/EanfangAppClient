package net.eanfang.client.ui.widget;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.apiservice.UserApi;
import com.eanfang.application.EanfangApplication;
import com.eanfang.config.FastjsonConfig;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.LoginBean;
import com.eanfang.ui.base.BaseDialog;
import com.yaf.sys.entity.OrgUnitEntity;

import net.eanfang.client.R;
import net.eanfang.client.ui.activity.LoginActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MrHou
 *
 * @on 2018/1/22  14:32
 * @email houzhongzhou@yeah.net
 * @desc 创建团队
 */

public class CreateTeamView extends BaseDialog {
    @BindView(R.id.et_input_company)
    EditText etInputCompany;
    @BindView(R.id.tv_cancle)
    TextView tvCancle;
    @BindView(R.id.tv_confirm)
    TextView tvConfirm;
    @BindView(R.id.tl_close)
    RelativeLayout tlClose;
    private Activity mContext;

    //回调函数
    private RefreshListener mRefreshListener;

    public CreateTeamView(Activity context) {
        super(context);
        this.mContext = context;
    }

    public CreateTeamView(Activity context, RefreshListener refreshListener) {
        super(context);
        this.mRefreshListener = refreshListener;
        this.mContext = context;
    }

    // 回调监听函数
    public interface RefreshListener {
        void refreshData();
    }

    @Override
    protected void initCustomView(Bundle savedInstanceState) {
        setContentView(R.layout.view_create_team);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        tvCancle.setOnClickListener(v -> dismiss());
        tlClose.setOnClickListener(v -> dismiss());
        tvConfirm.setOnClickListener(v -> createCompany());
    }

    private void createCompany() {
        EanfangHttp.post(UserApi.GET_ORGUNIT_ENT_ADD)
                .params("name", etInputCompany.getText().toString().trim())
                .execute(new EanfangCallback<OrgUnitEntity>(mContext, true, OrgUnitEntity.class, (bean) -> {
                    SwitchCompany(bean.getOrgId());
                    updateData();
                    mRefreshListener.refreshData();
                    dismiss();
                }));
    }

    /**
     * @param companyid Go to another company
     */
    private void SwitchCompany(Long companyid) {
        EanfangHttp.get(NewApiService.SWITCH_COMPANY_ALL_LIST)
                .params("companyId", companyid)
                .execute(new EanfangCallback<LoginBean>(mContext, false, LoginBean.class, (bean) -> {
                    EanfangApplication.get().remove(LoginBean.class.getName());
                    EanfangApplication.get().set(LoginBean.class.getName(), JSONObject.toJSONString(bean, FastjsonConfig.config));

                    EanfangHttp.setToken(EanfangApplication.get().getUser().getToken());
                    EanfangHttp.setClient();
                    dismiss();
                }));
    }

    private void updateData() {
        EanfangHttp.get(UserApi.GET_USER_INFO)
                .execute(new EanfangCallback(mContext, false, LoginBean.class) {
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
                            mContext.startActivity(new Intent(mContext, LoginActivity.class));
                            dismiss();
                        }
                    }
                });
    }
}
