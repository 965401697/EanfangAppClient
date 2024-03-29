package net.eanfang.client.ui.widget;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.eanfang.BuildConfig;
import com.eanfang.biz.model.bean.WorkAddReportBean;
import com.eanfang.ui.base.BaseDialog;
import com.eanfang.util.GlideUtil;

import net.eanfang.client.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.hutool.core.util.StrUtil;

/**
 * Created by MrHou
 *
 * @on 2017/11/28  13:47
 * @email houzhongzhou@yeah.net
 * @desc
 */

public class WorkPlanView extends BaseDialog {
    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_look_complete_content)
    TextView tvLookCompleteContent;
    @BindView(R.id.tv_look_complete_goal)
    TextView tvLookCompleteGoal;
    @BindView(R.id.tv_look_complete_pur)
    TextView tvLookCompletePur;
    @BindView(R.id.tv_look_complete_person)
    TextView tvLookCompletePerson;
    @BindView(R.id.tv_look_complete_endtime)
    TextView tvLookCompleteEndtime;
    @BindView(R.id.iv_pic1)
    ImageView ivPic1;
    @BindView(R.id.iv_pic2)
    ImageView ivPic2;
    @BindView(R.id.iv_pic3)
    ImageView ivPic3;
    private Activity mContext;
    private WorkAddReportBean.WorkReportDetailsBean detailBean;

    public WorkPlanView(Activity context, boolean isfull, WorkAddReportBean.WorkReportDetailsBean detailBean) {
        super(context, isfull);
        this.mContext = context;
        this.detailBean = detailBean;
    }

    @Override
    protected void initCustomView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_look_report_plan_info);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        ivLeft.setOnClickListener(v -> dismiss());
        tvTitle.setText("工作计划");


        tvLookCompleteContent.setText(detailBean.getField1());
        tvLookCompleteGoal.setText(detailBean.getField2());
        tvLookCompletePur.setText(detailBean.getField3());
        tvLookCompletePerson.setText(detailBean.getField4());
        tvLookCompleteEndtime.setText(detailBean.getField5());
        if (!StrUtil.isEmpty(detailBean.getPictures())) {
            String[] urls = detailBean.getPictures().split(",");

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
