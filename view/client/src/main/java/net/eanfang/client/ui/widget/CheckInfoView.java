package net.eanfang.client.ui.widget;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.eanfang.BuildConfig;
import com.eanfang.config.Config;
import com.eanfang.biz.model.WorkAddCheckBean;
import com.eanfang.ui.base.BaseDialog;
import com.eanfang.util.GlideUtil;
import com.eanfang.util.StringUtils;

import net.eanfang.client.R;

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
    ImageView ivPic1;
    @BindView(R.id.iv_pic2)
    ImageView ivPic2;
    @BindView(R.id.iv_pic3)
    ImageView ivPic3;
    private Activity mContext;
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
        tvOneName.setText(Config.get().getBusinessNameByCode(bean.getBusinessThreeCode(), 1));
//        tvTwoName.setText(bean.getBusinessTwo());
//        tvThreeName.setText(bean.getBusinessThree());
        etInputCheckContent.setText(bean.getInfo());
        if (!StringUtils.isEmpty(bean.getPictures())) {
            String[] urls = bean.getPictures().split(",");

            if (urls.length>=1) {
                GlideUtil.intoImageView(mContext,BuildConfig.OSS_SERVER + Uri.parse(urls[0]),ivPic1);
                ivPic1.setVisibility(View.VISIBLE);
            } else {
                ivPic1.setVisibility(View.GONE);
            }

            if (urls.length>=2) {
                GlideUtil.intoImageView(mContext,BuildConfig.OSS_SERVER + Uri.parse(urls[1]),ivPic2);
                ivPic2.setVisibility(View.VISIBLE);
            } else {
                ivPic2.setVisibility(View.GONE);
            }
            if (urls.length>=3) {
                GlideUtil.intoImageView(mContext,BuildConfig.OSS_SERVER + Uri.parse(urls[2]),ivPic3);
                ivPic3.setVisibility(View.VISIBLE);
            } else {
                ivPic3.setVisibility(View.GONE);
            }
        }
    }

}
