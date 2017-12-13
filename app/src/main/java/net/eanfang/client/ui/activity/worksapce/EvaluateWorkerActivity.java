package net.eanfang.client.ui.activity.worksapce;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;

import net.eanfang.client.R;
import net.eanfang.client.network.apiservice.ApiService;
import net.eanfang.client.network.request.EanfangCallback;
import net.eanfang.client.network.request.EanfangHttp;
import net.eanfang.client.ui.base.BaseActivity;
import net.eanfang.client.ui.model.EvaluateWorkerBean;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;

/**
 * Created by MrHou
 *
 * @on 2017/11/24  10:34
 * @email houzhongzhou@yeah.net
 * @desc
 */

public class EvaluateWorkerActivity extends BaseActivity {
    private MaterialRatingBar rb_star1;
    private MaterialRatingBar rb_star2;
    private MaterialRatingBar rb_star3;
    private MaterialRatingBar rb_star4;
    private MaterialRatingBar rb_star5;
    private TextView tv_select;
    private String ordernum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluate_worker);
        getData();
        initView();
    }

    private void getData() {
        Intent intent = getIntent();
        ordernum = intent.getStringExtra("ordernum");
    }

    private void initView() {
        rb_star1 = (MaterialRatingBar) findViewById(R.id.rb_star1);
        rb_star2 = (MaterialRatingBar) findViewById(R.id.rb_star2);
        rb_star3 = (MaterialRatingBar) findViewById(R.id.rb_star3);
        rb_star4 = (MaterialRatingBar) findViewById(R.id.rb_star4);
        rb_star5 = (MaterialRatingBar) findViewById(R.id.rb_star5);
        tv_select = (TextView) findViewById(R.id.tv_select);
        setTitle("评价技师");
        setLeftBack();
        tv_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EvaluateWorkerBean evaluateWorkerBean = new EvaluateWorkerBean();
                evaluateWorkerBean.setOrdernum(ordernum);
                evaluateWorkerBean.setProstandard(rb_star5.getProgress());
                evaluateWorkerBean.setResptime(rb_star4.getProgress());
                evaluateWorkerBean.setServiceattitude(rb_star2.getProgress());
                evaluateWorkerBean.setTechlevel(rb_star3.getProgress());
                evaluateWorkerBean.setWorkefficient(rb_star1.getProgress());
                Gson gson = new Gson();
                String json = gson.toJson(evaluateWorkerBean);
                EanfangHttp.post(ApiService.EVALUATE_WORKER)
                        .tag(this)
                        .params("json", json.toString())
                        .execute(new EanfangCallback(EvaluateWorkerActivity.this, false) {
                            @Override
                            public void onSuccess(Object bean) {
                                super.onSuccess(bean);
                                showToast("评价成功");
                                finish();
                            }

                            @Override
                            public void onFail(Integer code, String message, JSONObject jsonObject) {
                                super.onFail(code, message, jsonObject);
                                showToast(message);
                            }
                        });
            }
        });
    }
}
