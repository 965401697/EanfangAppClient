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
import net.eanfang.client.ui.model.WorkAddReportBean;
import net.eanfang.client.util.ImagePerviewUtil;
import net.eanfang.client.util.StringUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MrHou
 *
 * @on 2017/11/28  13:47
 * @email houzhongzhou@yeah.net
 * @desc
 */

public class CompleteWorkView extends BaseDialog implements View.OnClickListener {
    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    private Activity mContext;
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
    private WorkAddReportBean.DetailsBean detailBean;

    public CompleteWorkView(Activity context, boolean isfull,WorkAddReportBean.DetailsBean detailBean) {
        super(context, isfull);
        this.mContext = context;
        this.detailBean=detailBean;
    }

    @Override
    protected void initCustomView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_look_report_complete_info);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {

        ivLeft.setOnClickListener(v -> dismiss());
        tvTitle.setText("完成工作");
        ivPic1.setOnClickListener(this);
        ivPic2.setOnClickListener(this);
        ivPic3.setOnClickListener(this);

        tvLookCompleteContent.setText(detailBean.getField1());
        tvLookCompleteLeave.setText(detailBean.getField2());
        tvLookCompletePerson.setText(detailBean.getField3());
        tvLookCompleteReason.setText(detailBean.getField4());
        tvLookCompleteHandle.setText(detailBean.getField5());

        if (!TextUtils.isEmpty(detailBean.getPic1())) {
            ivPic1.setImageURI(Uri.parse(detailBean.getPic1()));
            ivPic1.setVisibility(View.VISIBLE);
        } else {
            ivPic1.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(detailBean.getPic2())) {
            ivPic2.setImageURI(Uri.parse(detailBean.getPic2()));
            ivPic2.setVisibility(View.VISIBLE);
        } else {
            ivPic2.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(detailBean.getPic3())) {
            ivPic3.setImageURI(Uri.parse(detailBean.getPic3()));
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
                if (!StringUtils.isEmpty(detailBean.getPic1())) {
                    picList.add(detailBean.getPic1());
                }
                break;
            case R.id.iv_pic2:
                if (!StringUtils.isEmpty(detailBean.getPic2())) {
                    picList.add(detailBean.getPic2());
                }
                break;
            case R.id.iv_pic3:
                if (!StringUtils.isEmpty(detailBean.getPic3())) {
                    picList.add(detailBean.getPic3());
                }
                break;
            default:
                break;
        }
        ImagePerviewUtil.perviewImage(mContext, picList);

    }
}
