package net.eanfang.worker.ui.activity.worksapce;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

/**
 * Created by MrHou
 *
 * @on 2017/11/25  16:34
 * @email houzhongzhou@yeah.net
 * @desc
 */

public class MaterialInfoActivity extends BaseWorkerActivity {
    private LinearLayout ll_business;
    private TextView tv_business;
    private LinearLayout ll_equipment;
    private TextView tv_equipment;
    private LinearLayout ll_model;
    private TextView tv_model;
    private LinearLayout ll_location;
    private LinearLayout ll_code;
    private TextView tv_right;
    private EditText et_desc;
    private Button btn_add;
    private String type;
    private String name;
    private String model;
    private String number;
    private String mark;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_material);
        getData();
        initView();
        initData();
    }

    private void getData() {
        Intent intent = getIntent();
        type = intent.getStringExtra("type");
        name = intent.getStringExtra("name");
        model = intent.getStringExtra("model");
        number = intent.getStringExtra("number");
        mark = intent.getStringExtra("mark");
    }

    private void initData() {
        tv_business.setText(type);
        tv_equipment.setText(name);
        tv_model.setText(model);
    }

    private void initView() {
        ll_business = findViewById(R.id.ll_business);
        tv_business = findViewById(R.id.tv_business);
        ll_equipment = findViewById(R.id.ll_equipment);
        tv_equipment = findViewById(R.id.tv_equipment);
        ll_model = findViewById(R.id.ll_model);
        tv_model = findViewById(R.id.tv_model);
        ll_location = findViewById(R.id.ll_location);
        ll_code = findViewById(R.id.ll_code);
        btn_add = findViewById(R.id.btn_add);
        btn_add.setVisibility(View.GONE);
        setTitle("耗材详情");
        setLeftBack();
    }
}

