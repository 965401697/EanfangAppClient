package net.eanfang.client.ui.activity.worksapce;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;

import net.eanfang.client.R;
import net.eanfang.client.network.apiservice.RepairApi;
import net.eanfang.client.network.request.EanfangCallback;
import net.eanfang.client.network.request.EanfangHttp;
import net.eanfang.client.ui.base.BaseActivity;
import net.eanfang.client.ui.model.EvaluateWorkerBean;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;

/**
 * Created by MrHou
 *
 * @on 2017/11/24  10:34
 * @email houzhongzhou@yeah.net
 * @desc 客户端评价技师
 */

public class EvaluateWorkerActivity extends BaseActivity {

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
    private Long orderId, assigneeUserId;

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
    }

    private void initView() {

        setTitle("评价技师");
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
        evaluateWorkerBean.setOwnerId(assigneeUserId);
        Gson gson = new Gson();
        String json = gson.toJson(evaluateWorkerBean);
        EanfangHttp.post(RepairApi.POST_CLIENT_EVALUATE_CREATE)
                .upJson(json.toString())
                .execute(new EanfangCallback<JSONObject>(EvaluateWorkerActivity.this, false, JSONObject.class, (bean) -> {
                    showToast("评价成功");
                    finish();
                }));
    }
}
