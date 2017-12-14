package net.eanfang.client.ui.widget;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.eanfang.base.BaseDialog;
import com.facebook.drawee.view.SimpleDraweeView;

import net.eanfang.client.R;
import net.eanfang.client.ui.model.WorkReportInfoBean;
import net.eanfang.client.util.StringUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MrHou
 *
 * @on 2017/11/22  14:56
 * @email houzhongzhou@yeah.net
 * @desc
 */

public class LookReportCompleteInfoView extends BaseDialog {
    WorkReportInfoBean.WorkReportDetailsBean detailsBean;
    Context mContext;
    @BindView(R.id.tv_look_complete_content)
    TextView tvLookCompleteContent;
    @BindView(R.id.tv_look_complete_person)
    TextView tvLookCompletePerson;
    @BindView(R.id.tv_look_complete_leave)
    TextView tvLookCompleteLeave;
    @BindView(R.id.tv_look_complete_reason)
    TextView tvLookCompleteReason;
    @BindView(R.id.tv_look_complete_handle)
    TextView tvLookCompleteHandle;
    @BindView(R.id.iv_pic1)
    SimpleDraweeView ivPic1;
    @BindView(R.id.iv_pic2)
    SimpleDraweeView ivPic2;
    @BindView(R.id.iv_pic3)
    SimpleDraweeView ivPic3;
    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.tv_title)
    TextView tvTitle;


    public LookReportCompleteInfoView(Activity context, boolean isfull, WorkReportInfoBean.WorkReportDetailsBean detailsBean) {
        super(context, isfull);
        this.detailsBean = detailsBean;
        this.mContext = context;
    }

    @Override
    protected void initCustomView(Bundle savedInstanceState) {
        setContentView(R.layout.view_look_report_complete_info);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        ivLeft.setOnClickListener(v -> dismiss());
        tvTitle.setText("完成工作");

        tvLookCompleteContent.setText(detailsBean.getField1());
        tvLookCompletePerson.setText(detailsBean.getField2());
        tvLookCompleteLeave.setText(detailsBean.getField3());
        tvLookCompleteReason.setText(detailsBean.getField4());
        tvLookCompleteHandle.setText(detailsBean.getField5());

        if (!StringUtils.isEmpty(detailsBean.getPictures())) {
            String[] urls = detailsBean.getPictures().split(",");

            if (!TextUtils.isEmpty(urls[0])) {
                ivPic1.setImageURI(Uri.parse(urls[0]));
                ivPic1.setVisibility(View.VISIBLE);
            } else {
                ivPic1.setVisibility(View.GONE);
            }

            if (!TextUtils.isEmpty(urls[1])) {
                ivPic2.setImageURI(Uri.parse(urls[1]));
                ivPic2.setVisibility(View.VISIBLE);
            } else {
                ivPic2.setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(urls[2])) {
                ivPic3.setImageURI(Uri.parse(urls[2]));
                ivPic3.setVisibility(View.VISIBLE);
            } else {
                ivPic3.setVisibility(View.GONE);
            }
        }
        ivPic3.setVisibility(View.GONE);
    }
}


