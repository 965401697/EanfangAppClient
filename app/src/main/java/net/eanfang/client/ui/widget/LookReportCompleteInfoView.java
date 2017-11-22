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
import net.eanfang.client.util.ImagePerviewUtil;
import net.eanfang.client.util.StringUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MrHou
 *
 * @on 2017/11/22  14:56
 * @email houzhongzhou@yeah.net
 * @desc
 */

public class LookReportCompleteInfoView extends BaseDialog implements View.OnClickListener {
    WorkReportInfoBean.BeanBean.DetailsBean detailsBean;
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


    public LookReportCompleteInfoView(Activity context, boolean isfull, WorkReportInfoBean.BeanBean.DetailsBean detailsBean) {
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
        ivPic1.setOnClickListener(this);
        ivPic2.setOnClickListener(this);
        ivPic3.setOnClickListener(this);

        tvLookCompleteContent.setText(detailsBean.getField1());
        tvLookCompletePerson.setText(detailsBean.getField2());
        tvLookCompleteLeave.setText(detailsBean.getField3());
        tvLookCompleteReason.setText(detailsBean.getField4());
        tvLookCompleteHandle.setText(detailsBean.getField5());

        if (!TextUtils.isEmpty(detailsBean.getPic1())) {
            ivPic1.setImageURI(Uri.parse(detailsBean.getPic1()));
            ivPic1.setVisibility(View.VISIBLE);
        } else {
            ivPic1.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(detailsBean.getPic2())) {
            ivPic2.setImageURI(Uri.parse(detailsBean.getPic2()));
            ivPic2.setVisibility(View.VISIBLE);
        } else {
            ivPic2.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(detailsBean.getPic3())) {
            ivPic3.setImageURI(Uri.parse(detailsBean.getPic3()));
            ivPic3.setVisibility(View.VISIBLE);
        } else {
            ivPic3.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        ArrayList<String> picList = new ArrayList<String>();
        switch (v.getId()) {
            case R.id.iv_pic1:
                if (!StringUtils.isEmpty(detailsBean.getPic1())) {
                    picList.add(detailsBean.getPic1());
                }
                break;
            case R.id.iv_pic2:
                if (!StringUtils.isEmpty(detailsBean.getPic2())) {
                    picList.add(detailsBean.getPic2());
                }
                break;
            case R.id.iv_pic3:
                if (!StringUtils.isEmpty(detailsBean.getPic3())) {
                    picList.add(detailsBean.getPic3());
                }
                break;
        }
        ImagePerviewUtil.perviewImage(mContext, picList);

    }
}
