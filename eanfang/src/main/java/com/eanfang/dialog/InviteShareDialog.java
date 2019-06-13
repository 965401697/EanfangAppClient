package com.eanfang.dialog;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;

import com.eanfang.BuildConfig;
import com.eanfang.R;
import com.eanfang.application.EanfangApplication;
import com.eanfang.ui.activity.QrCodeShowActivity;
import com.eanfang.ui.base.BaseDialog;
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

import static android.content.Context.CLIPBOARD_SERVICE;


/**
 * @author liangkailun
 * Date ：2019-05-27
 * Describe :分享页面
 */
public class InviteShareDialog extends BaseDialog {
    private final Activity mContext;
    private ClipboardManager myClipboard;
    private ClipData myClip;
    private String mCopyText;

    private String uri = BuildConfig.OSS_SERVER + EanfangApplication.get().getUser().getAccount().getQrCode();

    public InviteShareDialog(Activity context) {
        super(context, true);
        this.mContext = context;
    }

    @Override
    protected void initCustomView(Bundle savedInstanceState) {
        setContentView(R.layout.dialog_invite_share);
        myClipboard = (ClipboardManager) mContext.getSystemService(CLIPBOARD_SERVICE);
        initData();
        initView();
    }

    private void initView() {
        findViewById(R.id.bg_share).setOnClickListener(v -> dismiss());
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
            bundle.putString("qrcodeTitle", EanfangApplication.get().getUser().getAccount().getRealName());
            bundle.putString("qrcodeAddress", EanfangApplication.get().getUser().getAccount().getQrCode());
            bundle.putString("qrcodeMessage", "personal");
            JumpItent.jump(mContext, QrCodeShowActivity.class, bundle);
        });
        //复制链接
        findViewById(R.id.img_copy_link).setOnClickListener(v -> {
            myClip = ClipData.newPlainText("text", mCopyText);
            myClipboard.setPrimaryClip(myClip);
            showToast(mCopyText + "\n 已复制完成");
        });
        findViewById(R.id.tv_invite_cancel).setOnClickListener(v -> {
            dismiss();
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
                Result result = parseInfoFromBitmap(bitmap);
                if (result != null) {
                    mCopyText = result.getText();
                }
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
                clz = Class.forName(mContext.getApplication().getPackageName() + ".wxapi.WXEntryActivity");
                Intent intent = new Intent(mContext, clz);
                intent.putExtra("flag", type);
                intent.putExtra("url", mCopyText);
                mContext.startActivity(intent);
            } catch (ClassNotFoundException e) {
                Log.e("InviteShareActivity", e.getMessage());
            }
        }
    }

    private boolean isWeixinAvilible() {
        // 获取packagemanager
        final PackageManager packageManager = mContext.getPackageManager();
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
