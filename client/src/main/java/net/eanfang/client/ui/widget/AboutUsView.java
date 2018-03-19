package net.eanfang.client.ui.widget;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eanfang.ui.base.BaseDialog;
import com.eanfang.util.AppVersionUtil;

import net.eanfang.client.R;
import net.eanfang.client.util.UpdateManager;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MrHou
 *
 * @on 2017/11/17  16:14
 * @email houzhongzhou@yeah.net
 * @desc
 */

public class AboutUsView extends BaseDialog {
    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_version)
    TextView tvVersion;
    @BindView(R.id.ll_service_phone)
    LinearLayout llServicePhone;
    @BindView(R.id.ll_disclaimer)
    LinearLayout llDisclaimer;
    @BindView(R.id.ll_update)
    LinearLayout llUpdate;
    private Activity mContext;


    private String versionName;
//    private int versionCode;

    public AboutUsView(Activity context, boolean isfull) {
        super(context, isfull);
        this.mContext = context;
    }

    @Override
    protected void initCustomView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_about_us);
        ButterKnife.bind(this);
        initView();
        setTitle("关于我们");
    }

    private void initView() {

        tvVersion.setText("V " + AppVersionUtil.getAppVersionName(mContext));
        findViewById(R.id.iv_left).setOnClickListener(v -> dismiss());

        llServicePhone.setOnClickListener((v) -> {
            HelpLineView helpLineView = new HelpLineView(mContext, true);
            helpLineView.show();
        });
        llDisclaimer.setOnClickListener((v) -> {
            DisclaimerView disclaimerView = new DisclaimerView(mContext, true);
            disclaimerView.show();
        });
        llUpdate.setOnClickListener((v) -> {
            UpdateManager manager = new UpdateManager(mContext);
            if (manager.isUpdate()) {
                showToast("当前已是最新版本");
            } else {
                manager.checkUpdate();
            }
        });
    }


}
