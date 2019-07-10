package net.eanfang.worker.ui.activity.my;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.alibaba.fastjson.JSONObject;
import com.eanfang.apiservice.UserApi;
import com.eanfang.base.BaseApplication;
import com.eanfang.base.kit.cache.CacheKit;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.util.CleanMessageUtil;
import com.eanfang.util.JumpItent;
import com.eanfang.util.PermKit;
import com.eanfang.util.ToastUtil;
import com.eanfang.witget.SwitchButton;
import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGPushManager;

import net.eanfang.worker.R;
import net.eanfang.worker.base.WorkerApplication;
import net.eanfang.worker.ui.activity.MainActivity;
import net.eanfang.worker.ui.activity.SplashActivity;
import net.eanfang.worker.ui.activity.worksapce.setting.ChangePhoneActivity;
import net.eanfang.worker.ui.activity.worksapce.setting.UpdatePasswordActivity;
import net.eanfang.worker.ui.base.BaseWorkerActivity;
import net.eanfang.worker.ui.widget.AboutUsView;
import net.eanfang.worker.ui.widget.MessageStateView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.rong.imkit.RongIM;


/**
 * 设置页
 * Created by Administrator on 2017/3/15.
 */

public class SettingActivity extends BaseWorkerActivity {

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
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        try {
            tv_cache.setText(CleanMessageUtil.getTotalCacheSize(WorkerApplication.get()));
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
        ll_about_us.setOnClickListener(v -> new AboutUsView(this, true).show());
        ll_cache.setOnClickListener((v) -> {
            ToastUtil.get().showToast(this, "缓存已成功清除");
            CleanMessageUtil.clearAllCache(WorkerApplication.get());
            try {
                tv_cache.setText(CleanMessageUtil.getTotalCacheSize(WorkerApplication.get()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        btn_logout.setOnClickListener(v -> logout());
        llMsgSetting.setOnClickListener(v -> new MessageStateView(SettingActivity.this).show());

        boolean isOpen = CacheKit.get().getBool("XGNoticeVoice", true);
        if (isOpen) {
            sbVoice.setChecked(true);
        } else {
            sbVoice.setChecked(false);
        }
        sbVoice.setOnCheckedChangeListener((view, isChecked) -> CacheKit.get().put("XGNoticeVoice", isChecked));
    }

    /**
     * 是否放弃修改
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
                    XGPushManager.delAccount(SettingActivity.this, WorkerApplication.get().getLoginBean().getAccount().getMobile(), new XGIOperateCallback() {
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
//                    CleanMessageUtil.clearAllCache(WorkerApplication.get());
//                    SharePreferenceUtil.get().clear();
                    finishSelf();
                    startActivity(new Intent(SettingActivity.this, SplashActivity.class));
                    showToast("退出成功");
                    BaseApplication.get().closeActivity(MainActivity.class);
                }));
    }

}
