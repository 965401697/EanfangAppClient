package net.eanfang.client.ui.widget;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.eanfang.base.BaseDialog;

import net.eanfang.client.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MrHou
 *
 * @on 2017/11/17  10:19
 * @email houzhongzhou@yeah.net
 * @desc 邀请界面
 */

public class InviteView extends BaseDialog {
    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_share)
    ImageView ivShare;
    @BindView(R.id.tv_share)
    TextView tvShare;
    @BindView(R.id.iv_share2)
    ImageView ivShare2;
    @BindView(R.id.tv_share2)
    TextView tvShare2;
    private Activity mContext;

    public InviteView(Activity context, boolean isfull) {
        super(context, isfull);
        this.mContext = context;
    }


    @Override
    protected void initCustomView(Bundle savedInstanceState) {
        setContentView(R.layout.view_invite);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        tvTitle.setText("邀请");
        ivLeft.setOnClickListener(v -> dismiss());
        ivShare.setImageURI(getImgUri(1));
        tvShare.setOnClickListener((v) -> jump(1));
        ivShare2.setImageURI(getImgUri(2));
        tvShare2.setOnClickListener((v) -> jump(2));
    }

    private void jump(int curr) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_STREAM, getImgUri(curr));
        intent.setType("image/*");
        mContext.startActivity(Intent.createChooser(intent, "share"));
    }

    private Uri getImgUri(int curr) {
        int id = 0;
            if (curr == 2) {
                id = R.mipmap.client_app_qr_500;
            } else {
                id = R.mipmap.worker_app_qr_500;
            }

        Uri uri = Uri.parse("android.resource://" + mContext.getApplicationContext().getPackageName() + "/" + id);
        return uri;
    }

}
