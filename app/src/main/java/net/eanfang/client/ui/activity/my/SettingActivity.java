package net.eanfang.client.ui.activity.my;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eanfang.util.CleanMessageUtil;
import com.eanfang.util.SharePreferenceUtil;
import com.eanfang.util.ToastUtil;

import net.eanfang.client.R;
import net.eanfang.client.application.EanfangApplication;
import net.eanfang.client.ui.activity.LoginActivity;
import net.eanfang.client.ui.base.BaseActivity;
import net.eanfang.client.ui.widget.AboutUsView;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 设置页
 * Created by Administrator on 2017/3/15.
 */

public class SettingActivity extends BaseActivity {

    @BindView(R.id.ll_about_us)
    LinearLayout ll_about_us;
    @BindView(R.id.ll_cache)
    LinearLayout ll_cache;
    @BindView(R.id.btn_logout)
    Button btn_logout;
    @BindView(R.id.tv_cache)
    TextView tv_cache;


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
        setLeftBack();
        setTitle("设置");
        initView();
    }

    private void initView() {
        ll_about_us.setOnClickListener((v) -> {
            AboutUsView aboutUsView=new AboutUsView(this,true);
            aboutUsView.show();
        });

        ll_cache.setOnClickListener((v) -> {
            ToastUtil.get().showToast(this, "缓存已成功清除");
            CleanMessageUtil.clearAllCache(EanfangApplication.get());
            try {
                tv_cache.setText(CleanMessageUtil.getTotalCacheSize(EanfangApplication.get()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        btn_logout.setOnClickListener((v) -> {
            logout();
        });
    }

    /**
     * 是否放弃修改
     */
    private void logout() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("退出后将无法查看数据，您确定退出吗？");
        builder.setTitle("");
        builder.setPositiveButton("确定", (dialog, which) -> {
            dialog.dismiss();
            CleanMessageUtil.clearAllCache(EanfangApplication.get());
            ToastUtil.get().showToast(SettingActivity.this, "退出登录成功");
            SharePreferenceUtil.get().clear();
            startActivity(new Intent(SettingActivity.this, LoginActivity.class));
            SettingActivity.this.finish();
        });
        builder.setNegativeButton("取消", (dialog, which) -> {
            dialog.dismiss();
        });
        builder.create().show();
    }

}
