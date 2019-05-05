package net.eanfang.client.ui.activity.my;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.alibaba.fastjson.JSONObject;
import com.eanfang.apiservice.UserApi;
import com.eanfang.application.EanfangApplication;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.kit.cache.CacheKit;
import com.eanfang.sys.activity.LoginActivity;
import com.eanfang.util.CleanMessageUtil;
import com.eanfang.util.JumpItent;
import com.eanfang.util.PermKit;
import com.eanfang.util.ToastUtil;
import com.eanfang.witget.SwitchButton;
import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGPushManager;

import net.eanfang.client.R;
import net.eanfang.client.ui.activity.worksapce.setting.ChangePhoneActivity;
import net.eanfang.client.ui.activity.worksapce.setting.UpdatePasswordActivity;
import net.eanfang.client.ui.base.BaseClientActivity;
import net.eanfang.client.ui.widget.AboutUsView;
import net.eanfang.client.ui.widget.MessageStateView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.rong.imkit.RongIM;


/**
 * 设置页
 * Created by Administrator on 2017/3/15.
 */

public class SettingActivity extends BaseClientActivity {

    @BindView(R.id.ll_about_us)
    LinearLayout ll_about_us;
    @BindView(R.id.ll_cache)
    LinearLayout ll_cache;
    @BindView(R.id.btn_logout)
    Button btn_logout;
    @BindView(R.id.tv_cache)
    TextView tv_cache;
    @BindView(R.id.ll_msg_setting)
    LinearLayout llMsgSetting;
    @BindView(R.id.ll_change_phone)
    LinearLayout llChangePhone;
    @BindView(R.id.ll_updata_password)
    LinearLayout llUpdataPassword;
    @BindView(R.id.sb_voice)
    SwitchButton sbVoice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        try {
            tv_cache.setText(CleanMessageUtil.getTotalCacheSize(EanfangApplication.get()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        initView();
    }

    private void initView() {
        setLeftBack();
        setTitle("设置");
        //修改密码
        llUpdataPassword.setOnClickListener(v -> {
            JumpItent.jump(this, UpdatePasswordActivity.class);
        });
        //修改手机号
        llChangePhone.setOnClickListener(v -> {
            JumpItent.jump(this, ChangePhoneActivity.class);
        });
        //关于我们
        ll_about_us.setOnClickListener(v -> new AboutUsView(this, true).show());
        ll_cache.setOnClickListener((v) -> {
            ToastUtil.get().showToast(this, "缓存已成功清除");
            CleanMessageUtil.clearAllCache(EanfangApplication.get());
            try {
                tv_cache.setText(CleanMessageUtil.getTotalCacheSize(EanfangApplication.get()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        btn_logout.setOnClickListener(v -> logout());
        llMsgSetting.setOnClickListener(v -> new MessageStateView(SettingActivity.this).show());

        boolean isOpen = true;
//        try {
//            isOpen = (Boolean) SharePreferenceUtil.get().get("XGNoticeVoice", true);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        isOpen = CacheKit.get().getBool("XGNoticeVoice", true);
        if (isOpen) {
            sbVoice.setChecked(true);
        } else {
            sbVoice.setChecked(false);
        }
        sbVoice.setOnCheckedChangeListener((view, isChecked) -> {
            CacheKit.get().put("XGNoticeVoice", isChecked);
        });
    }

    /**
     * 是否退出
     */
    private void logout() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("退出后将无法查看数据，您确定退出吗？");
        builder.setTitle("");
        builder.setPositiveButton("确定", (dialog, which) -> {
            signout();
            dialog.dismiss();
        });
        builder.setNegativeButton("取消", (dialog, which) -> {
            dialog.dismiss();
        });
        builder.create().show();
    }

    private void signout() {
        EanfangHttp.get(UserApi.APP_LOGOUT)
                .execute(new EanfangCallback<JSONObject>(this, true, JSONObject.class, (bean) -> {
                    XGPushManager.delAccount(SettingActivity.this, EanfangApplication.get().getUser().getAccount().getMobile(), new XGIOperateCallback() {
                        @Override
                        public void onSuccess(Object o, int i) {
                            Log.e("GG", "信鸽退出Success");
                        }

                        @Override
                        public void onFail(Object o, int i, String s) {
                            Log.e("GG", "信鸽退出Fail");
                        }
                    });
                    RongIM.getInstance().logout();//退出融云
                    PermKit.permList.clear();//清空权限
//                    SharePreferenceUtil.get().clear();
                    CleanMessageUtil.clearAllCache(EanfangApplication.get());
                    finishSelf();
                    startActivity(new Intent(SettingActivity.this, LoginActivity.class));
                    showToast("退出成功");
                }));
    }


}
