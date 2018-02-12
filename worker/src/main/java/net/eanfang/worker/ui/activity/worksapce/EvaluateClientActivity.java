package net.eanfang.worker.ui.activity.worksapce;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.eanfang.apiservice.RepairApi;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.EvaluateWorkerBean;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;

/**
 * Created by MrHou
 *
 * @on 2017/11/25  16:39
 * @email houzhongzhou@yeah.net
 * @desc
 */

public class EvaluateClientActivity extends BaseWorkerActivity {
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
    private String ordernum;
    private Long orderId, ownerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluate_client);
        ButterKnife.bind(this);
        getData();
        initView();
    }


    private void getData() {
        Intent intent = getIntent();
        ordernum = intent.getStringExtra("ordernum");
        orderId = intent.getLongExtra("orderId", 0);
        ownerId = intent.getLongExtra("ownerId", 0);
    }

    private void initView() {

        setTitle("评价客户");
        setLeftBack();
        tvSelect.setOnClickListener(v -> commit());

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
        evaluateWorkerBean.setOwnerId(ownerId);
        EanfangHttp.post(RepairApi.POST_CLIENT_EVALUATE_CREATE)
                .upJson(JSON.toJSONString(evaluateWorkerBean))
                .execute(new EanfangCallback<JSONObject>(EvaluateClientActivity.this, false, JSONObject.class, (bean) -> {
                    showToast("评价成功");
                    finish();
                }));
    }
}

