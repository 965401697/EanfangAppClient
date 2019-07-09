package com.eanfang.ui.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.eanfang.BuildConfig;
import com.eanfang.R;
import com.eanfang.base.BaseApplication;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.JumpItent;
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
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * @author liangkailun
 * Date ：2019-05-27
 * Describe :分享页面
 */
public class InviteShareActivity extends BaseActivity {
    private ClipboardManager myClipboard;
    private ClipData myClip;
    private String mCopyText;

    String uri = BuildConfig.OSS_SERVER + BaseApplication.get().getLoginBean().getAccount().getQrCode();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.dialog_invite_share);
        super.onCreate(savedInstanceState);
        myClipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        initData();
        initView();
    }

    private void initView() {
        //分享到微信
        findViewById(R.id.img_share_weixin).setOnClickListener(v -> {
            shareToWx(0);
        });
        //分享到朋友圈
        findViewById(R.id.img_share_circle_friend).setOnClickListener(v -> {
            shareToWx(1);
        });
        //跳转二维码页面
        findViewById(R.id.img_share_qr_code).setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("qrcodeTitle", BaseApplication.get().getLoginBean().getAccount().getRealName());
            bundle.putString("qrcodeAddress", BaseApplication.get().getLoginBean().getAccount().getQrCode());
            bundle.putString("qrcodeMessage", "personal");
            JumpItent.jump(this, QrCodeShowActivity.class, bundle);
        });
        //复制链接
        findViewById(R.id.img_copy_link).setOnClickListener(v -> {
            myClip = ClipData.newPlainText("text", mCopyText);
            myClipboard.setPrimaryClip(myClip);
            showToast(mCopyText + "\n 已复制完成");
        });
        findViewById(R.id.tv_invite_cancel).setOnClickListener(v -> {
            finishSelf();
        });
    }

    private void initData() {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(uri).build();
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
                        mCopyText = result.getText();
                    }
                });

            }
        });
    }

    public Result parseInfoFromBitmap(Bitmap bitmap) {
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

    private void shareToWx(int type) {
        if (isWeixinAvilible()) {
            Class clz = null;
            try {
                clz = Class.forName(getApplication().getPackageName() + ".wxapi.WXEntryActivity");
                Intent intent = new Intent(this, clz);
                intent.putExtra("flag", type);
                intent.putExtra("url", mCopyText);
                startActivity(intent);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isWeixinAvilible() {
        // 获取packagemanager
        final PackageManager packageManager = this.getPackageManager();
        // 获取所有已安装程序的包信息
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if ("com.tencent.mm".equals(pn)) {
                    return true;
                }
            }
        }
        //  没有安装微信的
        showToast("您的手机没有安装微信");
        return false;
    }
}
