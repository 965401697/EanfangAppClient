package net.eanfang.client.ui.widget;

import android.app.Activity;
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
 * @on 2017/11/22  16:52
 * @email houzhongzhou@yeah.net
 * @desc
 */

public class LookReportPlanInfoView extends BaseDialog {
    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    private Activity mContext;
    private WorkReportInfoBean.WorkReportDetailsBean detailBean;
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
    SimpleDraweeView ivPic1;
    @BindView(R.id.iv_pic2)
    SimpleDraweeView ivPic2;
    @BindView(R.id.iv_pic3)
    SimpleDraweeView ivPic3;

    public LookReportPlanInfoView(Activity context, boolean isfull, WorkReportInfoBean.WorkReportDetailsBean detailBean) {
        super(context, isfull);
        this.mContext = context;
        this.detailBean = detailBean;
    }

    @Override
    protected void initCustomView(Bundle savedInstanceState) {
        setContentView(R.layout.view_work_report_paln_info);
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

        if (!StringUtils.isEmpty(detailBean.getPictures())) {
            String[] urls = detailBean.getPictures().split(",");

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
    }


}