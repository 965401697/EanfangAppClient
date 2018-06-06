package net.eanfang.client.ui.activity.worksapce;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;

import com.eanfang.apiservice.NewApiService;
import com.eanfang.apiservice.UserApi;
import com.eanfang.application.EanfangApplication;
import com.eanfang.dialog.TrueFalseDialog;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.OrganizationBean;
import com.eanfang.model.SectionBean;
import com.eanfang.model.TemplateBean;
import com.eanfang.ui.activity.OrganizationContactActivity;
import com.eanfang.ui.items.OrgOneLevelItem;
import com.eanfang.util.CleanMessageUtil;
import com.eanfang.util.SharePreferenceUtil;
import com.eanfang.util.ToastUtil;

import net.eanfang.client.R;
import net.eanfang.client.ui.activity.LoginActivity;
import net.eanfang.client.ui.activity.im.AddFriendActivity;
import net.eanfang.client.ui.activity.my.SettingActivity;
import net.eanfang.client.ui.base.BaseClientActivity;

import org.greenrobot.eventbus.Subscribe;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.rong.imkit.RongIM;

public class AdministratorSetActivity extends BaseClientActivity {

    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_sure)
    TextView tvSure;
    private TemplateBean.Preson bean;
    private String molibe;//设置人的手机号
    private String name = "ceshi";//设置人的手机号

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrator_set);
        ButterKnife.bind(this);
        setTitle("管理员设置");
        setLeftBack();
    }

    @OnClick({R.id.tv_sure, R.id.ll_transfer})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.tv_sure:

                if (Long.parseLong(bean.getId()) == EanfangApplication.get().getAccId()) {
                    ToastUtil.get().showToast(AdministratorSetActivity.this, "自己不能转让自己");
                    return;
                }

                new TrueFalseDialog(this, "转让管理员提示", "您的定将管理员身份转让给" + name + "?", () -> {
                    EanfangHttp.get(NewApiService.SET_TRANS_ADMIN)
                            .params("mobile", molibe)
                            .execute(new EanfangCallback<JSONObject>(AdministratorSetActivity.this, true, JSONObject.class, (bean) -> {

                                ToastUtil.get().showToast(AdministratorSetActivity.this, "转让成功，请重新登录");

                                logout();

                                // TODO: 2018/6/1  重登陆

                            }));

                }).showDialog();


                break;
            case R.id.ll_transfer:
                Intent intent = new Intent("com.eanfang.intent.action.ORG1");
//                intent.putExtra("isRadio", "isRadio");//是否是单选
                startActivity(intent);
                break;
        }

    }

    @Subscribe
    public void onEvent(List<TemplateBean.Preson> presonList) {

        if (presonList.size() > 0) {
            tvSure.setVisibility(View.VISIBLE);
            bean = (TemplateBean.Preson) presonList.get(0);
            name = bean.getName();
            molibe = bean.getMobile();
            tvName.setText(bean.getName());
        }
    }


    /**
     * 是否放弃修改
     */
    private void logout() {

        signout();
        CleanMessageUtil.clearAllCache(EanfangApplication.get());
        ToastUtil.get().showToast(AdministratorSetActivity.this, "退出登录成功");
        SharePreferenceUtil.get().clear();
        startActivity(new Intent(AdministratorSetActivity.this, LoginActivity.class));
        finishSelf();

    }

    private void signout() {
        EanfangHttp.get(UserApi.APP_LOGOUT)
                .execute(new EanfangCallback<com.alibaba.fastjson.JSONObject>(this, true, com.alibaba.fastjson.JSONObject.class, (bean) -> {
                    RongIM.getInstance().logout();//退出融云
                    showToast("退出成功");
                }));
    }
}
