package com.eanfang.witget;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.eanfang.R;
import com.eanfang.apiservice.NewApiService;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * @author Guanluocang
 * @date on 2018/4/28  18:46
 * @decision 个人二维码Dialog
 */
public class PersonalQRCodeDialog extends Dialog {
    private Context context;

    public PersonalQRCodeDialog(Context context) {
        super(context, R.style.CustomDialog);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_personal_qrcode, null);
        setContentView(view);
        initView(view);
    }

    private void initView(View view) {
        SimpleDraweeView mIvPersonalQRCode = (SimpleDraweeView) view.findViewById(R.id.iv_personalQRCode);
        // 个人二维码
        Uri uri = Uri.parse(NewApiService.PERSONAL_QRCODE);
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(uri)
                .setAutoPlayAnimations(true)
                .build();
        mIvPersonalQRCode.setController(controller);
    }
}
