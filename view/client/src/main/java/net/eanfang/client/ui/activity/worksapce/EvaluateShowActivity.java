package net.eanfang.client.ui.activity.worksapce;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eanfang.BuildConfig;
import com.eanfang.biz.model.bean.EvaluateBean;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.GlideUtil;

import net.eanfang.client.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;

/**
 * @author guanluocang
 * @data 2018/12/5
 * @description 评价展示页面
 */

public class EvaluateShowActivity extends BaseActivity {

    @BindView(R.id.rb_star1)
    MaterialRatingBar rbStar1;
    @BindView(R.id.rb_star2)
    MaterialRatingBar rbStar2;
    @BindView(R.id.rb_star3)
    MaterialRatingBar rbStar3;
    @BindView(R.id.sdv_workerHead)
    ImageView sdvWorkerHead;
    @BindView(R.id.tv_wonderful)
    TextView tvWonderful;
    @BindView(R.id.rb_worker_star1)
    MaterialRatingBar rbWorkerStar1;
    @BindView(R.id.rb_worker_star2)
    MaterialRatingBar rbWorkerStar2;
    @BindView(R.id.rb_worker_star3)
    MaterialRatingBar rbWorkerStar3;
    @BindView(R.id.rb_worker_star4)
    MaterialRatingBar rbWorkerStar4;
    @BindView(R.id.rb_worker_star5)
    MaterialRatingBar rbWorkerStar5;
    @BindView(R.id.ll_worker)
    LinearLayout llWorker;
    @BindView(R.id.ll_client)
    LinearLayout llClient;

    private EvaluateBean.ListBean revBean;

    private String mStatus = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_evaluate_client);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        initView();

    }


    private void initView() {
        setTitle("评价信息");
        setLeftBack();
        mStatus = getIntent().getStringExtra("status");
        revBean = (EvaluateBean.ListBean) getIntent().getSerializableExtra("bean");
        if (mStatus.equals("giv")) {//评价技师
            llWorker.setVisibility(View.VISIBLE);
            llClient.setVisibility(View.GONE);
            GlideUtil.intoImageView(this,Uri.parse(BuildConfig.OSS_SERVER + revBean.getOwnerUser().getAccountEntity().getAvatar()),sdvWorkerHead);
        } else {// 我收到的评价
            llWorker.setVisibility(View.GONE);
            llClient.setVisibility(View.VISIBLE);
            GlideUtil.intoImageView(this,Uri.parse(BuildConfig.OSS_SERVER + revBean.getCreateUser().getAccountEntity().getAvatar()),sdvWorkerHead);
        }

        rbStar1.setNumStars(revBean.getItem1());
        rbStar2.setNumStars(revBean.getItem2());
        rbStar3.setNumStars(revBean.getItem3());

        rbWorkerStar1.setNumStars(revBean.getItem1());
        rbWorkerStar2.setNumStars(revBean.getItem2());
        rbWorkerStar3.setNumStars(revBean.getItem3());
        rbWorkerStar4.setNumStars(revBean.getItem4());
        rbWorkerStar5.setNumStars(revBean.getItem5());

        if (revBean.getFavorableRate() == 1) {//超赞 1 一般 2 差评 3
            tvWonderful.setText("超赞");
        } else if (revBean.getFavorableRate() == 2) {
            tvWonderful.setText("一般");
        } else if (revBean.getFavorableRate() == 3) {
            tvWonderful.setText("差评");
        }
        rbStar1.setIsIndicator(true);
        rbStar2.setIsIndicator(true);
        rbStar3.setIsIndicator(true);
        rbWorkerStar1.setIsIndicator(true);
        rbWorkerStar2.setIsIndicator(true);
        rbWorkerStar3.setIsIndicator(true);
        rbWorkerStar4.setIsIndicator(true);
        rbWorkerStar5.setIsIndicator(true);
    }
}
