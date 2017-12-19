package net.eanfang.client.ui.widget;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.eanfang.BuildConfig;
import com.eanfang.base.BaseDialog;
import com.facebook.drawee.view.SimpleDraweeView;

import net.eanfang.client.R;
import net.eanfang.client.config.Config;
import net.eanfang.client.ui.model.WorkAddCheckBean;
import net.eanfang.client.util.StringUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MrHou
 *
 * @on 2017/11/28  14:53
 * @email houzhongzhou@yeah.net
 * @desc
 */

public class CheckInfoView extends BaseDialog {
    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    private Activity mContext;
    @BindView(R.id.et_title)
    TextView etTitle;
    @BindView(R.id.et_position)
    TextView etPosition;
    @BindView(R.id.tv_one_name)
    TextView tvOneName;

    @BindView(R.id.tv_two_name)
    TextView tvTwoName;

    @BindView(R.id.tv_three_name)
    TextView tvThreeName;

    @BindView(R.id.et_input_check_content)
    TextView etInputCheckContent;
    @BindView(R.id.iv_pic1)
    SimpleDraweeView ivPic1;
    @BindView(R.id.iv_pic2)
    SimpleDraweeView ivPic2;
    @BindView(R.id.iv_pic3)
    SimpleDraweeView ivPic3;
    private WorkAddCheckBean.WorkInspectDetailsBean bean;

    public CheckInfoView(Activity context, boolean isfull, WorkAddCheckBean.WorkInspectDetailsBean bean) {
        super(context, isfull);
        this.mContext = context;
        this.bean = bean;
    }

    @Override
    protected void initCustomView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_check_detial_info);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        ivLeft.setOnClickListener(v -> dismiss());
        tvTitle.setText("检查明细");
        etTitle.setText(bean.getTitle());
        etPosition.setText(bean.getRegion());
        tvOneName.setText(Config.getConfig().getBusinessName(bean.getBusinessThreeCode()));
//        tvTwoName.setText(bean.getBusinessTwo());
//        tvThreeName.setText(bean.getBusinessThree());
        etInputCheckContent.setText(bean.getInfo());
        if (!StringUtils.isEmpty(bean.getPictures())) {
            String[] urls = bean.getPictures().split(",");

            if (!TextUtils.isEmpty(urls[0])) {
                ivPic1.setImageURI(BuildConfig.OSS_SERVER + Uri.parse(urls[0]));
                ivPic1.setVisibility(View.VISIBLE);
            } else {
                ivPic1.setVisibility(View.GONE);
            }

            if (!TextUtils.isEmpty(urls[1])) {
                ivPic2.setImageURI(BuildConfig.OSS_SERVER + Uri.parse(urls[1]));
                ivPic2.setVisibility(View.VISIBLE);
            } else {
                ivPic2.setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(urls[2])) {
                ivPic3.setImageURI(BuildConfig.OSS_SERVER + Uri.parse(urls[2]));
                ivPic3.setVisibility(View.VISIBLE);
            } else {
                ivPic3.setVisibility(View.GONE);
            }
        }
    }

}
