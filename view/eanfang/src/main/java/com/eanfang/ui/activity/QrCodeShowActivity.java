package com.eanfang.ui.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.eanfang.BuildConfig;
import com.eanfang.R;
import com.eanfang.R2;
import com.eanfang.base.kit.rx.RxPerm;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.GlideUtil;
import com.eanfang.util.PhotoUtils;
import com.eanfang.util.StringUtils;
import com.eanfang.util.ToastUtil;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.common.GlobalHistogramBinarizer;
import com.google.zxing.qrcode.QRCodeReader;

import java.io.IOException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.hutool.core.util.StrUtil;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

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
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(BuildConfig.OSS_SERVER + mQrcodeAddress).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) {
                InputStream inputStream = response.body().byteStream();
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                runOnUiThread(() -> {
                    Result result = parseInfoFromBitmap(bitmap);
                    if (result != null) {
                        Log.d("###_WQ", " info=B=" + result.getText());
                        ljTvString = result.getText();
                    }
                });

            }
        });
    }

    public void doSaveQRCode() {
        RxPerm.get(this).storagePerm((success) -> {
            if (!StrUtil.isEmpty(mQrcodeAddress)) {
                String path = PhotoUtils.downloadImg(mQrcodeAddress).getPath();
                if (!StrUtil.isEmpty(path)) {
                    showToast(mTitle + "的易安防二维码已生成");
                } else {
                    showToast("图片无效");
                }

            }
        });
    }

    public Result parseInfoFromBitmap(Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }
        int[] pixels = new int[bitmap.getWidth() * bitmap.getHeight()];
        bitmap.getPixels(pixels, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());
        RGBLuminanceSource source = new RGBLuminanceSource(bitmap.getWidth(), bitmap.getHeight(), pixels);
        GlobalHistogramBinarizer binarizer = new GlobalHistogramBinarizer(source);
        BinaryBitmap image = new BinaryBitmap(binarizer);
        Result result = null;
        try {
            result = new QRCodeReader().decode(image);
            return result;
        } catch (NotFoundException e) {
            e.printStackTrace();
        } catch (ChecksumException e) {
            e.printStackTrace();
        } catch (FormatException e) {
            e.printStackTrace();
        }
        return null;
    }

    @OnClick(R2.id.lj_tv)
    public void onClick() {
        ljTvString = ljTv.getText().toString();
        ClipData myClip = ClipData.newPlainText("text", ljTvString);
        ((ClipboardManager) this.getSystemService(CLIPBOARD_SERVICE)).setPrimaryClip(myClip);
        ToastUtil.get().showToast(this, "已复制完成:" + ljTvString);
    }

}
