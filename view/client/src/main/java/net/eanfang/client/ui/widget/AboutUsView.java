package net.eanfang.client.ui.widget;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModel;

import com.eanfang.base.BaseDialog;
import com.eanfang.base.kit.utils.ApkUtils;
import com.eanfang.util.UpdateAppManager;

import net.eanfang.client.BuildConfig;
import net.eanfang.client.R;
import net.eanfang.client.databinding.ActivityAboutUsBinding;

/**
 * Created by MrHou
 *
 * @on 2017/11/17  16:14
 * @email houzhongzhou@yeah.net
 * @desc
 */

public class AboutUsView extends BaseDialog {

    private ActivityAboutUsBinding binding;

    public static AboutUsView getInstance() {
        return new AboutUsView();
    }

//    @Override
//    public void initView() {
//        setContentView(R.layout.activity_about_us);
//        ButterKnife.bind(this);
//        super.onCreate(savedInstanceState);
//
//    }

    @Override
    protected ViewModel initViewModel() {
        return null;
    }

    @Override
    protected View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        binding = ActivityAboutUsBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        initListener();
        setLeftBack(true);
        setTitle("关于我们");
    }

    private void initListener() {
        binding.tvVersion.setText("V " + ApkUtils.getAppVersionName(mActivity));
        findViewById(R.id.iv_left).setOnClickListener(v -> dismiss());
        //版本号
        binding.tvVersion.setOnClickListener((v) -> {
            showToast("versionCode：" + ApkUtils.getAppVersionCode(getContext()));
        });
        //客服热线
        binding.llServicePhone.setOnClickListener((v) -> {
            HelpLineView helpLineView = new HelpLineView(mActivity, true);
            helpLineView.show();
        });
        // 免责声明
        binding.llDisclaimer.setOnClickListener((v) -> {
            DisclaimerView disclaimerView = new DisclaimerView(mActivity, true);
            disclaimerView.show();
        });
        // 版本更新
        binding.llUpdate.setOnClickListener((v) -> {
            UpdateAppManager.update(mActivity, BuildConfig.APP_TYPE, true);
//            UpdateManager manager = new UpdateManager(mContext, BuildConfig.TYPE);
//            manager.checkUpdate();
//            if (manager.isUpdate()) {
////                showToast("当前已是最新版本");
////            } else {
////                manager.checkUpdate();
////            }
        });
    }


}
