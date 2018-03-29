package net.eanfang.worker.ui.widget;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.eanfang.BuildConfig;
import com.eanfang.model.WorkTaskInfoBean;
import com.eanfang.ui.base.BaseDialog;
import com.eanfang.util.GetConstDataUtils;
import com.eanfang.util.StringUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import net.eanfang.worker.R;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.eanfang.util.V.v;

/**
 * Created by MrHou
 *
 * @on 2017/11/22  18:58
 * @email houzhongzhou@yeah.net
 * @desc 任务详情明细
 */

public class LookTaskInfoView extends BaseDialog {
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
    private WorkTaskInfoBean.WorkTaskDetailsBean detailBean;


    public LookTaskInfoView(Activity context, boolean isfull, WorkTaskInfoBean.WorkTaskDetailsBean detailBean) {
        super(context, isfull);
        this.mContext = context;
        this.detailBean = detailBean;
    }

    @Override
    protected void initCustomView(Bundle savedInstanceState) {
        setContentView(R.layout.view_look_task_info);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        tvTitle.setText("任务明细");
        ivLeft.setOnClickListener(v -> dismiss());


        tvOrders.setText(v(()->GetConstDataUtils.getInstancyList().get(detailBean.getInstancyLevel())));
        tvFirstFrequency.setText(v(()->GetConstDataUtils.getInstancyList().get(detailBean.getFirstLook())));
        tvSecondFrequency.setText(v(()->GetConstDataUtils.getInstancyList().get(detailBean.getFirstCallback())));
        tvThirdFrequency.setText(v(()->GetConstDataUtils.getInstancyList().get(detailBean.getThenCallback())));
        tvEndTimes.setText(v(()->detailBean.getEndTime()));
        etComment.setText(v(()->detailBean.getInfo()));
        etWorker.setText(v(()->detailBean.getJoinPerson()));
        etStandard.setText(v(()->detailBean.getCriterion()));
        etGoal.setText(v(()->detailBean.getPurpose()));
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


}
