package net.eanfang.client.ui.widget;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eanfang.BuildConfig;
import com.eanfang.biz.model.WorkReportInfoBean;
import com.eanfang.takevideo.PlayVideoActivity;
import com.eanfang.ui.base.BaseDialog;
import com.eanfang.util.GlideUtil;
import com.eanfang.util.JumpItent;
import com.eanfang.util.StringUtils;

import net.eanfang.client.R;
import net.eanfang.client.util.ImagePerviewUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by guanluocang
 * 2018年10月11日 11:07:14
 *
 * @desc 查看工作汇报 后续计划
 */

public class LookReportPlanInfoView extends BaseDialog {
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
    private WorkReportInfoBean.WorkReportDetailsBean detailBean;

    // 照片和短视频
    @BindView(R.id.iv_takevideo)
    ImageView ivTakevideo;
    @BindView(R.id.rl_thumbnail)
    RelativeLayout rlThumbnail;

    private Activity mActivity;

    public LookReportPlanInfoView(Activity context, boolean isfull, WorkReportInfoBean.WorkReportDetailsBean detailBean) {
        super(context, isfull);
        this.mContext = context;
        this.detailBean = detailBean;
        this.mActivity = context;
    }

    @Override
    protected void initCustomView(Bundle savedInstanceState) {
        setContentView(R.layout.view_work_report_paln_info);
        ButterKnife.bind(this);
        initView();
        initListener();
    }

    private void initView() {

        ArrayList<String> picList = new ArrayList<String>();

        ivLeft.setOnClickListener(v -> dismiss());
        tvTitle.setText("工作计划");

        tvLookCompleteContent.setText(detailBean.getField1());
        tvLookCompleteGoal.setText(detailBean.getField2());
        tvLookCompletePur.setText(detailBean.getField3());
        if (!TextUtils.isEmpty(detailBean.getField4())) {
            tvLookCompletePerson.setText(detailBean.getField4());
        } else {
            tvLookCompletePerson.setText("无");
        }
        tvLookCompleteEndtime.setText(detailBean.getField5());

        if (!StringUtils.isEmpty(detailBean.getPictures())) {
            String[] urls = detailBean.getPictures().split(",");

            if (urls.length >= 1) {
                GlideUtil.intoImageView(mContext,BuildConfig.OSS_SERVER + Uri.parse(urls[0]),ivPic1);
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
                GlideUtil.intoImageView(mContext,BuildConfig.OSS_SERVER + Uri.parse(urls[1]),ivPic2);
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
                GlideUtil.intoImageView(mContext,BuildConfig.OSS_SERVER + Uri.parse(urls[2]),ivPic3);
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
        if (!StringUtils.isEmpty(detailBean.getMp4_path())) {
            rlThumbnail.setVisibility(View.VISIBLE);
            GlideUtil.intoImageView(mContext,Uri.parse(BuildConfig.OSS_SERVER + detailBean.getMp4_path() + ".jpg"),ivTakevideo);
        }
    }

    private void initListener() {
        ivTakevideo.setOnClickListener((v) -> {
            Bundle bundle_takevideo = new Bundle();
            bundle_takevideo.putString("videoPath", BuildConfig.OSS_SERVER + detailBean.getMp4_path() + ".mp4");
            JumpItent.jump(mActivity, PlayVideoActivity.class, bundle_takevideo);
        });
    }

}
