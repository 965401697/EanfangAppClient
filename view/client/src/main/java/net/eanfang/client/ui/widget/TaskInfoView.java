package net.eanfang.client.ui.widget;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eanfang.BuildConfig;
import com.eanfang.biz.model.WorkTaskBean;
import com.eanfang.takevideo.PlayVideoActivity;
import com.eanfang.ui.base.BaseDialog;
import com.eanfang.util.GetConstDataUtils;
import com.eanfang.util.GlideUtil;
import com.eanfang.util.JumpItent;
import com.eanfang.util.StringUtils;

import net.eanfang.client.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MrHou
 *
 * @on 2017/11/28  14:35
 * @email houzhongzhou@yeah.net
 * @desc
 */

public class TaskInfoView extends BaseDialog implements View.OnClickListener {
    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_orders)
    TextView tvOrders;
    @BindView(R.id.tv_first_frequency)
    TextView tvFirstFrequency;
    @BindView(R.id.tv_second_frequency)
    TextView tvSecondFrequency;
    @BindView(R.id.tv_third_frequency)
    TextView tvThirdFrequency;
    @BindView(R.id.tv_end_times)
    TextView tvEndTimes;
    @BindView(R.id.et_comment)
    TextView etComment;
    @BindView(R.id.et_goal)
    TextView etGoal;
    @BindView(R.id.et_standard)
    TextView etStandard;
    @BindView(R.id.et_worker)
    TextView etWorker;
    @BindView(R.id.iv_pic1)
    ImageView ivPic1;
    @BindView(R.id.iv_pic2)
    ImageView ivPic2;
    @BindView(R.id.iv_pic3)
    ImageView ivPic3;
    private Activity mContext;
    private WorkTaskBean.WorkTaskDetailsBean detailBean;
    // 照片和短视频
    @BindView(R.id.iv_takevideo)
    ImageView ivTakevideo;
    @BindView(R.id.rl_thumbnail)
    RelativeLayout rlThumbnail;

    private Activity mActivity;

    public TaskInfoView(Activity context, boolean isfull, WorkTaskBean.WorkTaskDetailsBean detailBean) {
        super(context, isfull);
        this.mContext = context;
        this.detailBean = detailBean;
        this.mActivity = context;
    }

    @Override
    protected void initCustomView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_look_info_detial);
        ButterKnife.bind(this);
        initView();
        initListener();
    }

    private void initView() {
        tvTitle.setText("任务明细");
        ivLeft.setOnClickListener(v -> dismiss());
        tvOrders.setText(GetConstDataUtils.getInstancyList().get(detailBean.getInstancyLevel()));
        tvFirstFrequency.setText(GetConstDataUtils.getInstancyList().get(detailBean.getFirst_look()));
        tvSecondFrequency.setText(GetConstDataUtils.getInstancyList().get(detailBean.getFirst_callback()));
        tvThirdFrequency.setText(GetConstDataUtils.getInstancyList().get(detailBean.getThen_callback()));
        tvEndTimes.setText(detailBean.getEnd_time());
        etComment.setText(detailBean.getInfo());
        etWorker.setText(detailBean.getJoinPerson());
        etStandard.setText(detailBean.getCriterion());
        etGoal.setText(detailBean.getPurpose());

        if (!StringUtils.isEmpty(detailBean.getPictures())) {
            String[] urls = detailBean.getPictures().split(",");

            if (urls.length >= 1) {
                GlideUtil.intoImageView(context,BuildConfig.OSS_SERVER + Uri.parse(urls[0]),ivPic1);
                ivPic1.setVisibility(View.VISIBLE);
            } else {
                ivPic1.setVisibility(View.GONE);
            }

            if (urls.length >= 2) {
                GlideUtil.intoImageView(context,BuildConfig.OSS_SERVER + Uri.parse(urls[1]),ivPic2);
                ivPic2.setVisibility(View.VISIBLE);
            } else {
                ivPic2.setVisibility(View.GONE);
            }
            if (urls.length >= 3) {
                GlideUtil.intoImageView(context,BuildConfig.OSS_SERVER + Uri.parse(urls[2]),ivPic3);
                ivPic3.setVisibility(View.VISIBLE);
            } else {
                ivPic3.setVisibility(View.GONE);
            }
        }
        if (!StringUtils.isEmpty(detailBean.getMp4_path())) {
            rlThumbnail.setVisibility(View.VISIBLE);
            GlideUtil.intoImageView(context,Uri.parse(BuildConfig.OSS_SERVER + detailBean.getMp4_path() + ".jpg"),ivTakevideo);
        }

    }
    private void initListener() {
        ivTakevideo.setOnClickListener((v) -> {
            Bundle bundle_takevideo = new Bundle();
            bundle_takevideo.putString("videoPath", BuildConfig.OSS_SERVER + detailBean.getMp4_path() + ".mp4");
            JumpItent.jump(mActivity, PlayVideoActivity.class, bundle_takevideo);
        });
    }

    @Override
    public void onClick(View v) {
//        ArrayList<String> picList = new ArrayList<String>();
//        switch (v.getId()) {
//            case R.id.iv_pic1:
//                if (!StringUtils.isEmpty(detailBean.getPic1())) {
//                    picList.add(detailBean.getPic1());
//                }
//                break;
//            case R.id.iv_pic2:
//                if (!StringUtils.isEmpty(detailBean.getPic2())) {
//                    picList.add(detailBean.getPic2());
//                }
//                break;
//            case R.id.iv_pic3:
//                if (!StringUtils.isEmpty(detailBean.getPic3())) {
//                    picList.add(detailBean.getPic3());
//                }
//                break;
//            default:
//                break;
//        }
//        ImagePerviewUtil.perviewImage(mContext, picList);

    }
}
