//package net.eanfang.client.ui.activity.worksapce;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.TextView;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import com.eanfang.apiservice.ApiService;
//import com.eanfang.application.EanfangApplication;
//import com.eanfang.http.EanfangCallback;
//import com.eanfang.http.EanfangHttp;
//import com.eanfang.model.InstallOrderConfirmBean;
//import com.eanfang.model.Message;
//
//import net.eanfang.client.R;
//import net.eanfang.client.ui.base.BaseClientActivity;
//
//
///**
// * 报装订单确认
// * Created by yaosheng on 2017/4/23.
// */
//// TODO: 2017/12/21 待修改
//
//public class OrderConfirmActivitys extends BaseClientActivity {
//    private TextView tv_number;
//    private TextView tv_company_name;
//    private TextView tv_contract;
//    private TextView tv_contract_phone;
//    private TextView tv_time;
//    private TextView tv_address;
//    private TextView tv_business;
//    private TextView tv_limit;
//    private TextView tv_money;
//    private TextView tv_desc;
//    private TextView tv_select;
//    private InstallOrderConfirmBean installOrderConfirmBean;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.actvity_order_confirm);
//        setLeftBack();
//        getData();
//        initView();
//        initData();
//        setListener();
//
//        setTitle("确认订单");
//    }
//
//    private void initData() {
//        /**防报错注释*/
////        tv_company_name.setText(installOrderConfirmBean.getClientcompanyname());
////        tv_contract.setText(installOrderConfirmBean.getClientconnector());
////        tv_contract_phone.setText(installOrderConfirmBean.getClientphone());
////        tv_time.setText(installOrderConfirmBean.getArrivetime());
////        tv_address.setText(installOrderConfirmBean.getCity() + installOrderConfirmBean.getZone() + installOrderConfirmBean.getDetailplace());
////        tv_business.setText(installOrderConfirmBean.getBugonename());
////        tv_limit.setText(installOrderConfirmBean.getPredicttime());
//        tv_money.setText(installOrderConfirmBean.getBudget());
//        tv_desc.setText(installOrderConfirmBean.getDescription());
//    }
//
//    private void getData() {
//        Intent intent = getIntent();
//        installOrderConfirmBean = (InstallOrderConfirmBean) intent.getSerializableExtra("bean");
//
//    }
//
//    private void setListener() {
//        tv_select.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                tv_select.setEnabled(false);
//
//                EanfangHttp.post(ApiService.INSTALL_ORDER_CONFIRM)
//                        .params("json", JSON.toJSONString(installOrderConfirmBean))
//                        .tag(this)
//                        .execute(new EanfangCallback(OrderConfirmActivitys.this, false) {
//                            @Override
//                            public void onSuccess(Object bean) {
//                                super.onSuccess(bean);
//                                showToast("下单成功");
//                                Intent intent = new Intent(OrderConfirmActivitys.this, StateChangeActivity.class);
//                                Message message = new Message();
//                                message.setTitle("下单成功");
//                                message.setMsgTitle("您的报装单已下单成功");
//                                message.setMsgContent("稍后安防公司负责人会和您取得联系，请保持电话畅通");
//                                message.setShowOkBtn(true);
//                                message.setShowLogo(true);
//
//                                Bundle bundle = new Bundle();
//                                bundle.putSerializable("message", message);
//                                intent.putExtras(bundle);
//                                startActivity(intent);
//                                EanfangApplication.get().closeActivity(InstallActivity.class.getName());
//                                EanfangApplication.get().closeActivity(CompanyDetailActivity.class.getName());
//                                finish();
//                            }
//
//                            @Override
//                            public void onFail(Integer code, String message, JSONObject jsonObject) {
//                                super.onFail(code, message, jsonObject);
//                                tv_select.setEnabled(true);
//                                showToast(message);
//                            }
//                        });
//            }
//        });
//    }
//
//    private void initView() {
//        tv_number = (TextView) findViewById(R.id.tv_number);
//        tv_company_name = (TextView) findViewById(R.id.tv_company_name);
//        tv_contract = (TextView) findViewById(R.id.tv_contract);
//        tv_contract_phone = (TextView) findViewById(R.id.tv_contract_phone);
//        tv_time = (TextView) findViewById(R.id.tv_time);
//        tv_address = (TextView) findViewById(R.id.tv_address);
//        tv_business = (TextView) findViewById(R.id.tv_business);
//        tv_limit = (TextView) findViewById(R.id.tv_limit);
//        tv_money = (TextView) findViewById(R.id.tv_money);
//        tv_desc = (TextView) findViewById(R.id.tv_desc);
//        tv_select = (TextView) findViewById(R.id.tv_select);
//
//    }
//
//
//}
