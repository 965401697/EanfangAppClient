package com.eanfang.ui.activity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eanfang.BuildConfig;
import com.eanfang.R;
import com.eanfang.R2;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.FileUtils;
import com.eanfang.util.StringUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author guanluocang
 * @data 2018/10/16
 * @description 二维码展示页面
 */

public class QrCodeShowActivity extends BaseActivity {

    @BindView(R2.id.tv_qrcode_title)
    TextView tvQrcodeTitle;
    @BindView(R2.id.iv_qrcode)
    SimpleDraweeView ivQrcode;
    @BindView(R2.id.tv_qrcode_message)
    TextView tvQrcodeMessage;
    @BindView(R2.id.rl_save_qrcode)
    RelativeLayout rlSaveQrcode;

    // 标题 个人/群组
    private String mTitle = "";
    // 二维码信息
    private String mMessage = "";
    // 保存地址
    private String mSave = "";
    // 二维码地址
    private String mQrcodeAddress = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_code_show);
        ButterKnife.bind(this);
        supprotToolbar();
        setTitle("二维码名片");
        initView();
    }

    private void initView() {
        mTitle = getIntent().getStringExtra("qrcodeTitle");
        mMessage = getIntent().getStringExtra("qrcodeMessage");
        mQrcodeAddress = getIntent().getStringExtra("qrcodeAddress");

        if (!StringUtils.isEmpty(mTitle)) {
            tvQrcodeTitle.setText(mTitle);
        }
        if (mMessage.equals("personal")) {
            tvQrcodeMessage.setText("进易安防app，扫一扫加我为好友");
            mSave = "personal";
        } else if (mMessage.equals("group")) {
            tvQrcodeMessage.setText("进易安防app，扫一扫加入群聊");
            mSave = "group";
        }
        if (!StringUtils.isEmpty(mQrcodeAddress)) {
            ivQrcode.setImageURI(Uri.parse(BuildConfig.OSS_SERVER + mQrcodeAddress));
        }
        rlSaveQrcode.setOnClickListener((v) -> {
            doSaveQRCode();
        });
    }

    public void doSaveQRCode() {
        if (!StringUtils.isEmpty(mQrcodeAddress)) {
            String path = FileUtils.bitmapToFile(FileUtils.returnBitMap(BuildConfig.OSS_SERVER + mQrcodeAddress), new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "_qrcode_" + mSave);
            if (!StringUtils.isEmpty(path)) {
                showToast("已保存至" + path);
            } else {
                showToast("图片无效");
            }

        }
    }

}
