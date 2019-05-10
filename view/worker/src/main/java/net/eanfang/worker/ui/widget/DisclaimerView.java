package net.eanfang.worker.ui.widget;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.eanfang.ui.base.BaseDialog;

import net.eanfang.worker.R;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MrHou
 *
 * @on 2017/11/17  16:33
 * @email houzhongzhou@yeah.net
 * @desc
 */

public class DisclaimerView extends BaseDialog {
    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_disclaimer)
    TextView tvDisclaimer;
    private String disclaimerText;

    public DisclaimerView(Activity context, boolean isfull) {
        super(context, isfull);
    }

    @Override
    protected void initCustomView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_about_us_disclaimer);
        ButterKnife.bind(this);
        initData();
        tvDisclaimer.setText(disclaimerText);
        ivLeft.setOnClickListener(v -> dismiss());
        tvTitle.setText("免责声明");
    }

    private void initData() {
        try {
            InputStream is = null;
            is = context.getResources().openRawResource(R.raw.disclaimer);
            int size = 0;
            size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            disclaimerText = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

}
