package net.eanfang.worker.ui.activity.worksapce;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.eanfang.biz.model.bean.PuPiaoBean;
import com.eanfang.biz.model.bean.SelectAddressItem;
import com.eanfang.biz.model.bean.ZhuanPiaoBean;
import com.eanfang.ui.activity.SelectAddressActivity;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import cn.hutool.core.util.StrUtil;

/**
 * 填写发票
 * Created by yaosheng on 2017/4/16.
 */

public class FaPiaoActivity extends BaseWorkerActivity {

    private RadioButton rb_normal;
    private EditText et_head;
    private RadioButton rb_pro;
    private EditText et_company;
    private EditText et_number, et_number_normal;
    private EditText et_address;
    private EditText et_phone;
    private EditText et_bank;
    private EditText et_bank_number;
    private EditText et_name;
    private EditText et_mobile_phone;
    private LinearLayout ll_address;
    private EditText et_detail_address;
    private Button btn_complete;
    private TextView tv_address;
    private String ordernum;
    private String city;
    private String zone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_fapiao);
        super.onCreate(savedInstanceState);
        getData();
        initView();
        initData();
        setListener();
    }

    private void setListener() {
        btn_complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!rb_normal.isChecked() && !rb_pro.isChecked()) {
                    showToast("请选择一种发票");
                    return;
                }
                String json = "";
                if (rb_normal.isChecked()) {
                    if (StrUtil.isEmpty(et_head.getText().toString().trim())) {
                        showToast("请填写发票抬头");
                        return;
                    }
                    if (StrUtil.isEmpty(et_name.getText().toString().trim())) {
                        showToast("请填写姓名");
                        return;
                    }
                    if (StrUtil.isEmpty(et_mobile_phone.getText().toString().trim())) {
                        showToast("请填写电话");
                        return;
                    }
                    if (StrUtil.isEmpty(tv_address.getText().toString().trim())) {
                        showToast("请选择住址");
                        return;
                    }
                    if (StrUtil.isEmpty(et_detail_address.getText().toString().trim())) {
                        showToast("请填写详细地址");
                        return;
                    }
                    if (StrUtil.isEmpty(et_number_normal.getText().toString().trim())) {
                        showToast("请填写税号");
                        return;
                    }
                    PuPiaoBean puPiaoBean = new PuPiaoBean();
                    puPiaoBean.setOrdernum(ordernum);
                    puPiaoBean.setType("普票");
                    puPiaoBean.setUnitname(et_head.getText().toString().trim());
                    puPiaoBean.setPostname(et_name.getText().toString().trim());
                    puPiaoBean.setTaxno(et_number_normal.getText().toString().trim());
                    puPiaoBean.setCity(city);
                    puPiaoBean.setZone(zone);
                    puPiaoBean.setDetailplace(et_detail_address.getText().toString().trim());
                    puPiaoBean.setPostphone(et_mobile_phone.getText().toString().trim());
                    json = JSON.toJSONString(puPiaoBean);
                }
                if (rb_pro.isChecked()) {
                    if (StrUtil.isEmpty(et_company.getText().toString().trim())) {
                        showToast("请填写公司名称");
                        return;
                    }
                    if (StrUtil.isEmpty(et_number.getText().toString().trim())) {
                        showToast("请填写税号");
                        return;
                    }
                    if (StrUtil.isEmpty(et_address.getText().toString().trim())) {
                        showToast("请填写单位地址");
                        return;
                    }
                    if (StrUtil.isEmpty(et_phone.getText().toString().trim())) {
                        showToast("请填写单位座机");
                        return;
                    }
                    if (StrUtil.isEmpty(et_bank.getText().toString().trim())) {
                        showToast("请填写开户行");
                        return;
                    }
                    if (StrUtil.isEmpty(et_bank_number.getText().toString().trim())) {
                        showToast("请填写银行账号");
                        return;
                    }
                    if (StrUtil.isEmpty(et_name.getText().toString().trim())) {
                        showToast("请填写姓名");
                        return;
                    }
                    if (StrUtil.isEmpty(et_mobile_phone.getText().toString().trim())) {
                        showToast("请填写电话");
                        return;
                    }
                    if (StrUtil.isEmpty(tv_address.getText().toString().trim())) {
                        showToast("请选择住址");
                        return;
                    }
                    if (StrUtil.isEmpty(et_detail_address.getText().toString().trim())) {
                        showToast("请填写详细地址");
                        return;
                    }

                    ZhuanPiaoBean zhuanPiaoBean = new ZhuanPiaoBean();
                    zhuanPiaoBean.setOrdernum(ordernum);
                    zhuanPiaoBean.setType("专票");
                    zhuanPiaoBean.setUnitname(et_company.getText().toString().trim());
                    zhuanPiaoBean.setTaxno(et_number.getText().toString().trim());
                    zhuanPiaoBean.setUnitplace(et_address.getText().toString().trim());
                    zhuanPiaoBean.setUnitphone(et_phone.getText().toString().trim());
                    zhuanPiaoBean.setKaihuhang(et_bank.getText().toString().trim());
                    zhuanPiaoBean.setBankaccount(et_bank_number.getText().toString().trim());
                    zhuanPiaoBean.setPostname(et_name.getText().toString().trim());
                    zhuanPiaoBean.setCity(city);
                    zhuanPiaoBean.setZone(zone);
                    zhuanPiaoBean.setDetailplace(et_detail_address.getText().toString().trim());
                    zhuanPiaoBean.setPostphone(et_mobile_phone.getText().toString().trim());
                    json = JSON.toJSONString(zhuanPiaoBean);
                }

//                EanfangHttp.post(ApiService.FA_PIAO)
//                        .tag(this)
//                        .params("json", json.toString())
//                        .execute(new EanfangCallback(FaPiaoActivity.this, false) {
//                            @Override
//                            public void onSuccess(Object bean) {
//                                super.onSuccess(bean);
//                                showToast("发票信息上传成功");
//                                setResult(47329);
//                                finish();
//                            }
//
//                            @Override
//                            public void onFail(Integer code, String message, JSONObject jsonObject) {
//                                super.onFail(code, message, jsonObject);
//                                showToast(message);
//                            }
//                        });
            }
        });
        rb_normal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rb_pro.isChecked()) {
                    rb_pro.setChecked(false);
                }
            }
        });
        rb_pro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rb_normal.isChecked()) {
                    rb_normal.setChecked(false);
                }
            }
        });
        ll_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FaPiaoActivity.this, SelectAddressActivity.class);
                startActivityForResult(intent, 30000);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 30000) {
            SelectAddressItem selectAddressItem = (SelectAddressItem) data.getSerializableExtra("data");
            city = selectAddressItem.getCity();
            zone = selectAddressItem.getAddress();
            tv_address.setText(selectAddressItem.getProvince() + city + zone);
//            et_detail_address.setText(selectAddressItem.getZone());
            et_detail_address.setText(selectAddressItem.getName());
        }
    }

    private void initData() {
        JSONObject object = new JSONObject();

        object.put("ordernum", ordernum);

//        EanfangHttp.get(ApiService.LOOK_FA_PIAO)
//                .tag(this)
//                .params("json", object.toString())
//                .execute(new EanfangCallback<LookFaPiaoBean>(FaPiaoActivity.this, false) {
//                    @Override
//                    public void onSuccess(LookFaPiaoBean bean) {
//                        super.onSuccess(bean);
//                        LookFaPiaoBean lookFaPiaoBean = bean;
//                        if ("普票".equals(lookFaPiaoBean.getType())) {
//                            rb_normal.setChecked(true);
//                            et_head.setText(lookFaPiaoBean.getUnitname());
//                            et_number_normal.setText(lookFaPiaoBean.getTaxno());
//                        } else if ("专票".equals(lookFaPiaoBean.getType())) {
//                            rb_pro.setChecked(true);
//                            et_company.setText(lookFaPiaoBean.getUnitname());
//                            et_number.setText(lookFaPiaoBean.getTaxno());
//                            et_address.setText(lookFaPiaoBean.getUnitplace());
//                            et_phone.setText(lookFaPiaoBean.getUnitphone());
//                            et_bank.setText(lookFaPiaoBean.getKaihuhang());
//                            et_bank_number.setText(lookFaPiaoBean.getBankaccount());
//                        }
//                        et_name.setText(lookFaPiaoBean.getPostname());
//                        et_mobile_phone.setText(lookFaPiaoBean.getPostphone());
//                        tv_address.setText(lookFaPiaoBean.getCity() + lookFaPiaoBean.getZone());
//                        et_detail_address.setText(lookFaPiaoBean.getDetailplace());
//                    }
//
//                    @Override
//                    public void onFail(Integer code, String message, JSONObject jsonObject) {
//                        super.onFail(code, message, jsonObject);
//                        showToast(message);
//                    }
//                });
    }

    private void initView() {
        rb_normal = findViewById(R.id.rb_normal);
        et_head = findViewById(R.id.et_head);
        rb_pro = findViewById(R.id.rb_pro);
        et_company = findViewById(R.id.et_company);
        et_number = findViewById(R.id.et_number);
        et_number_normal = findViewById(R.id.et_number_normal);
        et_address = findViewById(R.id.et_address);
        et_phone = findViewById(R.id.et_phone);
        et_bank = findViewById(R.id.et_bank);
        et_bank_number = findViewById(R.id.et_bank_number);
        et_name = findViewById(R.id.et_name);
        et_mobile_phone = findViewById(R.id.et_mobile_phone);
        ll_address = findViewById(R.id.ll_address);
        et_detail_address = findViewById(R.id.et_detail_address);
        btn_complete = findViewById(R.id.btn_complete);
        tv_address = findViewById(R.id.tv_address);
        setTitle("填写发票信息");
        setLeftBack();
    }

    private void getData() {
        Intent intent = getIntent();
        ordernum = intent.getStringExtra("ordernum");
    }
}
