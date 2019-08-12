package net.eanfang.client.ui.activity.my;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModel;

import com.eanfang.base.BaseActivity;
import com.eanfang.base.BaseApplication;
import com.eanfang.base.kit.cache.CacheKit;
import com.eanfang.base.kit.rx.RxDialog;
import com.eanfang.biz.model.bean.LoginBean;
import com.eanfang.biz.rds.base.BaseViewModel;
import com.eanfang.biz.rds.sys.ds.impl.LoginDs;
import com.eanfang.biz.rds.sys.repo.LoginRepo;
import com.eanfang.util.CleanMessageUtil;
import com.eanfang.util.JumpItent;
import com.eanfang.util.PermKit;
import com.eanfang.util.ToastUtil;
import com.eanfang.witget.SwitchButton;
import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGPushManager;

import net.eanfang.client.R;
import net.eanfang.client.base.ClientApplication;
import net.eanfang.client.ui.activity.MainActivity;
import net.eanfang.client.ui.activity.SplashActivity;
import net.eanfang.client.ui.activity.worksapce.setting.ChangePhoneActivity;
import net.eanfang.client.ui.activity.worksapce.setting.UpdatePasswordActivity;
import net.eanfang.client.ui.widget.AboutUsView;
import net.eanfang.client.ui.widget.MessageStateView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.rong.imkit.RongIM;


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
    @BindView(R.id.ll_msg_setting)
    LinearLayout llMsgSetting;
    @BindView(R.id.ll_change_phone)
    LinearLayout llChangePhone;
    @BindView(R.id.ll_updata_password)
    LinearLayout llUpdataPassword;
    @BindView(R.id.sb_voice)
    SwitchButton sbVoice;

    private LoginRepo loginRepo;
    private BaseViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        try {
            tv_cache.setText(CleanMessageUtil.getTotalCacheSize(ClientApplication.get()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        initView();
        loginRepo = new LoginRepo(new LoginDs(viewModel));
    }

    @Override
    protected ViewModel initViewModel() {
        return viewModel = new BaseViewModel();
    }

    protected void initView() {
        super.initView();
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
        ll_about_us.setOnClickListener(v -> {
            AboutUsView view = AboutUsView.getInstance();
            view.setStyle(DialogFragment.STYLE_NORMAL, R.style.Dialog_FullScreen);
            view.show(getSupportFragmentManager(), "about");
        });
        ll_cache.setOnClickListener((v) -> {
            ToastUtil.get().showToast(this, "缓存已成功清除");
            CleanMessageUtil.clearAllCache(ClientApplication.get());
            try {
                tv_cache.setText(CleanMessageUtil.getTotalCacheSize(ClientApplication.get()));
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
        sbVoice.setOnCheckedChangeListener((view, isChecked) -> {
            CacheKit.get().put("XGNoticeVoice", isChecked);
        });
    }

    /**
     * 是否退出
     */
    private void logout() {
        new RxDialog(this)
                .setTitle("提示")
                .setMessage("退出后将无法查看数据，您确定退出吗？")
                .setPositiveText("确定")
                .setNegativeText("取消")
                .dialogToObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(code -> {
                    if (code.equals(RxDialog.POSITIVE)) {
                        signout();
                    }
                });
    }

    private void signout() {
        loginRepo.logout().observe(SettingActivity.this, (d) -> {
            XGPushManager.delAccount(SettingActivity.this, BaseApplication.get().getAccount().getMobile(), new XGIOperateCallback() {
                        @Override
                        public void onSuccess(Object o, int i) {

                        }

                        @Override
                        public void onFail(Object o, int i, String s) {

                        }
                    });
            //退出融云
            RongIM.getInstance().logout();
            //清空权限
            PermKit.permList.clear();
            CacheKit.get().remove(LoginBean.class.getName());
            // 切换账号后 立即技师认证  地区为空
//            CleanMessageUtil.clearAllCache(ClientApplication.get());
            finish();
            startActivity(new Intent(SettingActivity.this, SplashActivity.class));
            BaseApplication.get().closeActivity(MainActivity.class);
        });
    }


}
