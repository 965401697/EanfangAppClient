package net.eanfang.client.ui.widget;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.eanfang.BuildConfig;
import com.eanfang.model.WorkTaskBean;
import com.eanfang.ui.base.BaseDialog;
import com.eanfang.util.GetConstDataUtils;
import com.eanfang.util.StringUtils;
import com.facebook.drawee.view.SimpleDraweeView;

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
    SimpleDraweeView ivPic1;
    @BindView(R.id.iv_pic2)
    SimpleDraweeView ivPic2;
    @BindView(R.id.iv_pic3)
    SimpleDraweeView ivPic3;
    private Activity mContext;
    private WorkTaskBean.WorkTaskDetailsBean detailBean;

    public TaskInfoView(Activity context, boolean isfull, WorkTaskBean.WorkTaskDetailsBean detailBean) {
        super(context, isfull);
        this.mContext = context;
        this.detailBean = detailBean;
    }

    @Override
    protected void initCustomView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_look_info_detial);
        ButterKnife.bind(this);
        initView();
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

            if (urls.length>=1) {
                ivPic1.setImageURI(BuildConfig.OSS_SERVER + Uri.parse(urls[0]));
                ivPic1.setVisibility(View.VISIBLE);
            } else {
                ivPic1.setVisibility(View.GONE);
            }

            if (urls.length>=2) {
                ivPic2.setImageURI(BuildConfig.OSS_SERVER + Uri.parse(urls[1]));
                ivPic2.setVisibility(View.VISIBLE);
            } else {
                ivPic2.setVisibility(View.GONE);
            }
            if (urls.length>=3) {
                ivPic3.setImageURI(BuildConfig.OSS_SERVER + Uri.parse(urls[2]));
                ivPic3.setVisibility(View.VISIBLE);
            } else {
                ivPic3.setVisibility(View.GONE);
            }
        }
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