package net.eanfang.client.ui.activity.worksapce;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.eanfang.BuildConfig;
import com.eanfang.apiservice.RepairApi;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.EvaluateWorkerBean;
import com.facebook.drawee.view.SimpleDraweeView;

import net.eanfang.client.R;
import net.eanfang.client.ui.base.BaseClientActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;

/**
 * Created by MrHou
 *
 * @on 2017/11/24  10:34
 * @email houzhongzhou@yeah.net
 * @desc 客户端评价技师
 */

public class EvaluateWorkerActivity extends BaseClientActivity implements RadioGroup.OnCheckedChangeListener {

    @BindView(R.id.rb_star1)
    MaterialRatingBar rbStar1;
    @BindView(R.id.rb_star2)
    MaterialRatingBar rbStar2;
    @BindView(R.id.rb_star3)
    MaterialRatingBar rbStar3;
    @BindView(R.id.rb_star4)
    MaterialRatingBar rbStar4;
    @BindView(R.id.rb_star5)
    MaterialRatingBar rbStar5;

    @BindView(R.id.tv_select)
    TextView tvSelect;

    @BindView(R.id.sdv_workerHead)
    SimpleDraweeView sdvWorkerHead;

    @BindView(R.id.rb_wonderful)
    RadioButton rbWonderful;
    @BindView(R.id.rb_good)
    RadioButton rbGood;
    @BindView(R.id.rb_bad)
    RadioButton rbBad;
    @BindView(R.id.rg_score)
    RadioGroup rgScore;


    @BindView(R.id.iv_anonyMous)
    ImageView ivAnonyMous;
    @BindView(R.id.ll_workerAnonymous)
    LinearLayout llWorkerAnonymous;
    private String ordernum;
    private Long orderId, assigneeUserId;

    //是否匿名
    private boolean isAnonymous = false;
    private int isAnonymousValue = 2;// 1 匿名 2 不匿名
    // 赞的等级
    private int mGoodStatus = 1;
    private static final int EVALUTAE_WORK_SUCCESS = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluate_worker);
        ButterKnife.bind(this);
        getData();
        initView();
    }

    private void getData() {
        Intent intent = getIntent();
        ordernum = intent.getStringExtra("ordernum");
        orderId = intent.getLongExtra("orderId", 0);
        assigneeUserId = intent.getLongExtra("workerUid", 0);

        sdvWorkerHead.setImageURI(Uri.parse(BuildConfig.OSS_SERVER + Uri.parse(getIntent().getStringExtra("avatar"))));
    }

    private void initView() {

        setTitle("评价技师");
        setLeftBack();
        tvSelect.setOnClickListener(v -> commit());
        rgScore.setOnCheckedChangeListener(this);
    }

    private void commit() {
        EvaluateWorkerBean evaluateWorkerBean = new EvaluateWorkerBean();
        evaluateWorkerBean.setOrderNum(ordernum);
        evaluateWorkerBean.setItem1(rbStar1.getProgress());
        evaluateWorkerBean.setItem2(rbStar2.getProgress());
        evaluateWorkerBean.setItem3(rbStar3.getProgress());
        evaluateWorkerBean.setItem4(rbStar4.getProgress());
        evaluateWorkerBean.setItem5(rbStar5.getProgress());
        evaluateWorkerBean.setOrderId(orderId);
        evaluateWorkerBean.setOwnerId(assigneeUserId);
        evaluateWorkerBean.setFavorable_rate(mGoodStatus);
        evaluateWorkerBean.setAnonymous_evaluation(isAnonymousValue);
        EanfangHttp.post(RepairApi.POST_WORKER_EVALUATE_CREATE)
                .upJson(JSON.toJSONString(evaluateWorkerBean))
                .execute(new EanfangCallback<JSONObject>(EvaluateWorkerActivity.this, true, JSONObject.class, (bean) -> {
                    showToast("评价成功");
                    setResult(EVALUTAE_WORK_SUCCESS);
                    finishSelf();
                }));
    }

    @OnClick(R.id.ll_workerAnonymous)
    public void onViewClicked() {

        if (!isAnonymous) {
            ivAnonyMous.setImageResource(R.mipmap.ic_evalute_worker_pressed);
            isAnonymous = true;
            isAnonymousValue = 1;
        } else {
            ivAnonyMous.setImageResource(R.mipmap.ic_evalute_worker);
            isAnonymous = false;
            isAnonymousValue = 2;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (group.getCheckedRadioButtonId()) {
            case R.id.rb_wonderful://超赞
                mGoodStatus = 1;
                break;
            case R.id.rb_good://一般
                mGoodStatus = 2;
                break;
            case R.id.rb_bad://差评
                mGoodStatus = 3;
                break;
            default:
                break;

        }
    }
}
