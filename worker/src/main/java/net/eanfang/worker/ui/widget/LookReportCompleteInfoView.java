package net.eanfang.worker.ui.widget;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.eanfang.BuildConfig;
import com.eanfang.model.WorkReportInfoBean;
import com.eanfang.ui.base.BaseDialog;
import com.eanfang.util.StringUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import net.eanfang.worker.R;
import net.eanfang.worker.util.ImagePerviewUtil;

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

    //    ArrayList<String> picList = new ArrayList<String>();
//    String[] urls = mDataList.get(position).getPictures().split(",");
//                    if (urls.length >= 1) {
//        picList.add(com.eanfang.BuildConfig.OSS_SERVER + urls[0]);
//    }
//                    if (urls.length >= 2) {
//        picList.add(com.eanfang.BuildConfig.OSS_SERVER + urls[1]);
//    }
//                    if (urls.length >= 3) {
//        picList.add(com.eanfang.BuildConfig.OSS_SERVER + urls[2]);
//    }
//                    if (picList.size() == 0) {
////                        showToast("当前没有图片");
//        return;
//    }
//                    ImagePerviewUtil.perviewImage(getActivity(), picList);
    private void initView() {

        ArrayList<String> picList = new ArrayList<String>();

        ivLeft.setOnClickListener(v -> dismiss());
        tvTitle.setText("完成工作");

        tvLookCompleteContent.setText(detailsBean.getField1());
        if (!TextUtils.isEmpty(detailsBean.getField2())) {
            tvLookCompletePerson.setText(detailsBean.getField2());
        } else {
            tvLookCompletePerson.setText("无");
        }
        tvLookCompleteLeave.setText(detailsBean.getField3());
        tvLookCompleteReason.setText(detailsBean.getField4());
        tvLookCompleteHandle.setText(detailsBean.getField5());

        if (!StringUtils.isEmpty(detailsBean.getPictures())) {
            String[] urls = detailsBean.getPictures().split(",");

            if (urls.length >= 1) {


                ivPic1.setImageURI(BuildConfig.OSS_SERVER + Uri.parse(urls[0]));
                ivPic1.setVisibility(View.VISIBLE);

                ivPic1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        picList.clear();
                        picList.add(BuildConfig.OSS_SERVER + Uri.parse(urls[0]));
                        ImagePerviewUtil.perviewImage(mContext, picList);
                    }
                });
            } else {
                ivPic1.setVisibility(View.GONE);
            }

            if (urls.length >= 2) {
                ivPic2.setImageURI(BuildConfig.OSS_SERVER + Uri.parse(urls[1]));
                ivPic2.setVisibility(View.VISIBLE);
                ivPic2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        picList.clear();
                        picList.add(BuildConfig.OSS_SERVER + Uri.parse(urls[1]));
                        ImagePerviewUtil.perviewImage(mContext, picList);
                    }
                });
            } else {
                ivPic2.setVisibility(View.GONE);
            }
            if (urls.length >= 3) {
                ivPic3.setImageURI(BuildConfig.OSS_SERVER + Uri.parse(urls[2]));
                ivPic3.setVisibility(View.VISIBLE);
                ivPic3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        picList.clear();
                        picList.add(BuildConfig.OSS_SERVER + Uri.parse(urls[2]));
                        ImagePerviewUtil.perviewImage(mContext, picList);
                    }
                });
            } else {
                ivPic3.setVisibility(View.GONE);
            }
        }
    }
}


