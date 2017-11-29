package net.eanfang.client.ui.activity.worksapce;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;

import net.eanfang.client.R;
import net.eanfang.client.network.apiservice.ApiService;
import net.eanfang.client.network.request.EanfangCallback;
import net.eanfang.client.network.request.EanfangHttp;
import net.eanfang.client.ui.base.BaseActivity;
import net.eanfang.client.ui.model.EvaluateClientBean;

import org.json.JSONObject;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;

/**
 * Created by MrHou
 *
 * @on 2017/11/25  16:39
 * @email houzhongzhou@yeah.net
 * @desc
 */

public class EvaluateClientActivity extends BaseActivity {
    private MaterialRatingBar rb_star1;
    private MaterialRatingBar rb_star2;
    private MaterialRatingBar rb_star3;
    private MaterialRatingBar rb_star4;
    private MaterialRatingBar rb_star5;
    private TextView tv_select;
    private String ordernum;
    private int flag = 0;//0为提价评价，1为客户端查看收到的评价
    private String gzxzzc;
    private String jsjs;
    private String tdrqyh;
    private String xchj;
    private String zysbsxcd;
    private TextView tv_1;
    private TextView tv_2;
    private TextView tv_3;
    private TextView tv_4;
    private TextView tv_5;
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluate_client);
        getData();
        initView();
    }

    private void getData() {
        Intent intent = getIntent();
        setTitle("评价客户");
        if (intent.getIntExtra("flag",0) == 0){
            ordernum = intent.getStringExtra("ordernum");
        }else if (intent.getIntExtra("flag",0) == 1){
            flag = intent.getIntExtra("flag",0);
            gzxzzc = intent.getStringExtra("gzxzzc");
            jsjs = intent.getStringExtra("jsjs");
            tdrqyh = intent.getStringExtra("tdrqyh");
            xchj = intent.getStringExtra("xchj");
            zysbsxcd = intent.getStringExtra("zysbsxcd");
            title = intent.getStringExtra("title");
            setTitle(title);
        }else if (intent.getIntExtra("flag",0) == 2){
            flag = intent.getIntExtra("flag",0);
            gzxzzc = intent.getStringExtra("gzxzzc");
            jsjs = intent.getStringExtra("jsjs");
            tdrqyh = intent.getStringExtra("tdrqyh");
            xchj = intent.getStringExtra("xchj");
            zysbsxcd = intent.getStringExtra("zysbsxcd");
            setTitle(title);
        }


    }

    private void initView() {
        rb_star1 = (MaterialRatingBar) findViewById(R.id.rb_star1);
        rb_star2 = (MaterialRatingBar) findViewById(R.id.rb_star2);
        rb_star3 = (MaterialRatingBar) findViewById(R.id.rb_star3);
        rb_star4 = (MaterialRatingBar) findViewById(R.id.rb_star4);
        rb_star5 = (MaterialRatingBar) findViewById(R.id.rb_star5);
        tv_select = (TextView) findViewById(R.id.tv_select);
        tv_1 = (TextView) findViewById(R.id.tv_1);
        tv_2 = (TextView) findViewById(R.id.tv_2);
        tv_3 = (TextView) findViewById(R.id.tv_3);
        tv_4 = (TextView) findViewById(R.id.tv_4);
        tv_5 = (TextView) findViewById(R.id.tv_5);
        if (flag == 1){
            tv_select.setVisibility(View.GONE);
            rb_star1.setNumStars(Integer.parseInt(zysbsxcd));
            rb_star2.setNumStars(Integer.parseInt(gzxzzc));
            rb_star3.setNumStars(Integer.parseInt(jsjs));
            rb_star4.setNumStars(Integer.parseInt(tdrqyh));
            rb_star5.setNumStars(Integer.parseInt(xchj));
            rb_star1.setIsIndicator(true);
            rb_star2.setIsIndicator(true);
            rb_star3.setIsIndicator(true);
            rb_star4.setIsIndicator(true);
            rb_star5.setIsIndicator(true);
        }else if (flag == 2){
            tv_select.setVisibility(View.GONE);
            rb_star1.setNumStars(Integer.parseInt(zysbsxcd));
            rb_star2.setNumStars(Integer.parseInt(gzxzzc));
            rb_star3.setNumStars(Integer.parseInt(jsjs));
            rb_star4.setNumStars(Integer.parseInt(tdrqyh));
            rb_star5.setNumStars(Integer.parseInt(xchj));
            rb_star1.setIsIndicator(true);
            rb_star2.setIsIndicator(true);
            rb_star3.setIsIndicator(true);
            rb_star4.setIsIndicator(true);
            rb_star5.setIsIndicator(true);
            tv_1.setText("工作效率");
            tv_2.setText("服务态度");
            tv_3.setText("技术水平");
            tv_4.setText("响应时间");
            tv_5.setText("过程规范");
        }
        setLeftBack();
        tv_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EvaluateClientBean evaluateClientBean = new EvaluateClientBean();
                evaluateClientBean.setOrdernum(ordernum);
                evaluateClientBean.setXchj(rb_star5.getProgress());
                evaluateClientBean.setTdrqyh(rb_star4.getProgress());
                evaluateClientBean.setJsjs(rb_star3.getProgress());
                evaluateClientBean.setGzxzzc(rb_star2.getProgress());
                evaluateClientBean.setZysbsxcd(rb_star1.getProgress());
                Gson gson = new Gson();
                String json = gson.toJson(evaluateClientBean);
                EanfangHttp.post(ApiService.EVALUATE_CLIENT)
                        .tag(this)
                        .params("json",json.toString())
                        .execute(new EanfangCallback(EvaluateClientActivity.this,false){
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

