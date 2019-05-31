package com.eanfang.witget;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.eanfang.BuildConfig;
import com.eanfang.R;
import com.eanfang.util.GlideUtil;


/**
 * @author Guanluocang
 * @date on 2018/4/28  18:46
 * @decision 个人二维码Dialog
 */
public class PersonalQRCodeDialog extends Dialog {
    private Context context;
    private String mPath;

    public PersonalQRCodeDialog(Context context, String path) {
        super(context, R.style.CustomDialog);
        this.context = context;
        this.mPath = path;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_personal_qrcode, null);
        setContentView(view);
        initView(view, mPath);
    }

    private void initView(View view, String path) {
        ImageView mIvPersonalQRCode = view.findViewById(R.id.iv_personalQRCode);
        // 个人二维码
        GlideUtil.intoImageView(context,Uri.parse(BuildConfig.OSS_SERVER + path),mIvPersonalQRCode);
    }
}
