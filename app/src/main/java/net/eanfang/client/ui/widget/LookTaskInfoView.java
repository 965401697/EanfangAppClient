package net.eanfang.client.ui.widget;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.eanfang.base.BaseDialog;
import com.eanfang.util.GetDateUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import net.eanfang.client.R;
import net.eanfang.client.ui.model.WorkTaskInfoBean;
import net.eanfang.client.util.ImagePerviewUtil;
import net.eanfang.client.util.StringUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MrHou
 *
 * @on 2017/11/22  18:58
 * @email houzhongzhou@yeah.net
 * @desc
 */

public class LookTaskInfoView extends BaseDialog implements View.OnClickListener{
    private Activity mContext;
    private WorkTaskInfoBean.BeanBean.DetailsBean detailBean;
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


    public LookTaskInfoView(Activity context, boolean isfull, WorkTaskInfoBean.BeanBean.DetailsBean detailBean) {
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
        tvTitle.setText("任务详情");
        ivLeft.setOnClickListener(v -> dismiss());
        ivPic1.setOnClickListener(this);
        ivPic2.setOnClickListener(this);
        ivPic3.setOnClickListener(this);

        tvOrders.setText(detailBean.getInstancyLevel());
        tvFirstFrequency.setText(detailBean.getFirstLook());
        tvSecondFrequency.setText(detailBean.getFirstCallback());
        tvThirdFrequency.setText(detailBean.getThenCallback());
        String endtime = detailBean.getEndTime();

        tvEndTimes.setText(GetDateUtils.dateToDateString(GetDateUtils.getDate(endtime)));
        etComment.setText(detailBean.getInfo());
        etWorker.setText(detailBean.getJoinPerson());
        etStandard.setText(detailBean.getCriterion());
        etGoal.setText(detailBean.getPurpose());
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
        }
        ImagePerviewUtil.perviewImage(mContext, picList);

    }

}
