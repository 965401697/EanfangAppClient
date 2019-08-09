package com.eanfang.ui.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eanfang.BuildConfig;
import com.eanfang.R;
import com.eanfang.R2;
import com.eanfang.base.BaseApplication;
import com.eanfang.base.kit.rx.RxPerm;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.GlideUtil;
import com.eanfang.util.PhotoUtils;
import com.eanfang.util.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.hutool.core.util.StrUtil;

/**
 * @author guanluocang
 * @data 2018/10/16
 * @description 二维码展示页面
 */

public class QrCodeShowActivity extends BaseActivity {

    @BindView(R2.id.tv_qrcode_title)
    TextView tvQrcodeTitle;
    @BindView(R2.id.iv_qrcode)
    ImageView ivQrcode;
    @BindView(R2.id.tv_qrcode_message)
    TextView tvQrcodeMessage;
    @BindView(R2.id.lj_tv)
    TextView ljTv;
    @BindView(R2.id.rl_save_qrcode)
    RelativeLayout rlSaveQrcode;
    private String ljTvString;
    // 标题 个人/群组

    private String mTitle = "";

    // 二维码信息

    private String mMessage = "";


    // 二维码地址

    private String mQrcodeAddress = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_qr_code_show);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        supprotToolbar();
        setTitle("二维码名片");
        initView();
    }

    private void initView() {
        mTitle = getIntent().getStringExtra("qrcodeTitle");
        mMessage = getIntent().getStringExtra("qrcodeMessage");
        mQrcodeAddress = getIntent().getStringExtra("qrcodeAddress");

        if (!StrUtil.isEmpty(mTitle)) {
            tvQrcodeTitle.setText(mTitle);
        }
        if (mMessage.equals("personal")) {
            tvQrcodeMessage.setText("进易安防app，扫一扫加我为好友");
        } else if (mMessage.equals("group")) {
            tvQrcodeMessage.setText("进易安防app，扫一扫加入群聊");
        }
        if (!StrUtil.isEmpty(mQrcodeAddress)) {
            Log.d("QrCodeShowActivity_yh", "initView: " + Uri.parse(BuildConfig.OSS_SERVER + mQrcodeAddress));
            GlideUtil.intoImageView(this, Uri.parse(BuildConfig.OSS_SERVER + mQrcodeAddress), ivQrcode);
            initData();
        }
        rlSaveQrcode.setOnClickListener((v) -> doSaveQRCode());
    }

    private void initData() {
        ljTvString = BaseApplication.get().getQrCode();
    }

    public void doSaveQRCode() {
        RxPerm.get(this).storagePerm((success) -> {
            if (!StrUtil.isEmpty(mQrcodeAddress)) {
                String path = PhotoUtils.downloadImg(mQrcodeAddress);
                if (!StrUtil.isEmpty(path)) {
                    showToast(mTitle + "的易安防二维码已生成");
                } else {
                    showToast("图片无效");
                }
            }
        });
    }

    @OnClick(R2.id.lj_tv)
    public void onClick() {
        ClipData myClip = ClipData.newPlainText("text", ljTvString);
        ((ClipboardManager) this.getSystemService(CLIPBOARD_SERVICE)).setPrimaryClip(myClip);
        ToastUtil.get().showToast(this, "已复制完成:" + ljTvString);
    }

}
