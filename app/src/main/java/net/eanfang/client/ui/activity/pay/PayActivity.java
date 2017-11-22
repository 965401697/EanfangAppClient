package net.eanfang.client.ui.activity.pay;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import net.eanfang.client.R;
import net.eanfang.client.application.EanfangApplication;
import net.eanfang.client.network.apiservice.BaseService;
import net.eanfang.client.network.request.EanfangCallback;
import net.eanfang.client.network.request.EanfangHttp;
import net.eanfang.client.ui.activity.worksapce.FaPiaoActivity;
import net.eanfang.client.ui.activity.worksapce.StateChangeActivity;
import net.eanfang.client.ui.base.BaseActivity;
import net.eanfang.client.ui.model.WXPayBean;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * Created by MrHou
 *
 * @on 2017/11/22  9:55
 * @email houzhongzhou@yeah.net
 * @desc 支付
 */

public class PayActivity extends BaseActivity {


    private TextView tv_money;
    private CheckBox rb_fapiao;
    private LinearLayout edit_fapiao;
    private TextView tv_fapiao;
    private TextView tv_number;
    private RadioButton rb_alipay;
    private RadioButton rb_weixin_pay;
    private Button btn_to_pay;
    private static final int SDK_PAY_FLAG = 123456;
    private String orderInfo;
    private String ordernum;
    private String doorfee;
    private Boolean isFaPiao = false;
    private WXPayBean wxPayBean;
    private String orderType;

    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG:
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。

                        Toast.makeText(getApplicationContext(), "支付成功", Toast.LENGTH_LONG).show();
                        EanfangApplication.get().closeActivity(PayActivity.class.getName());

                        Intent intent = new Intent(PayActivity.this, StateChangeActivity.class);
                        Bundle bundle = new Bundle();
                        net.eanfang.client.ui.model.Message message = new  net.eanfang.client.ui.model.Message();
                        message.setTitle("支付成功");
                        message.setMsgTitle("您的订单已支付成功");
                        message.setMsgContent("稍后技师会和您取得联系,请保持电话畅通。");
                        message.setTip("");
                        message.setShowOkBtn(true);
                        message.setShowLogo(true);
                        bundle.putSerializable("message", message);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(PayActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                default:
                    break;
            }
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
    }

    private void initView() {
        tv_money = (TextView) findViewById(R.id.tv_money);
        rb_fapiao = (CheckBox) findViewById(R.id.rb_fapiao);
        edit_fapiao = (LinearLayout) findViewById(R.id.edit_fapiao);
        tv_fapiao = (TextView) findViewById(R.id.tv_fapiao);
        tv_number = (TextView) findViewById(R.id.tv_number);
        rb_alipay = (RadioButton) findViewById(R.id.rb_alipay);
        rb_weixin_pay = (RadioButton) findViewById(R.id.rb_weixin_pay);
        btn_to_pay = (Button) findViewById(R.id.btn_to_pay);
        tv_money.setText(doorfee + "元");
        tv_number.setText(doorfee);
        rb_alipay.setChecked(true);
    }

    private void setListener() {
        btn_to_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (rb_alipay.isChecked()) {
                    //支付宝支付
                    aliPay();
                } else {
                    //微信支付
                    wxPay();
                }
            }
        });
        edit_fapiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PayActivity.this, FaPiaoActivity.class);
                intent.putExtra("ordernum", ordernum);
                startActivityForResult(intent, 30001);
            }
        });
        rb_alipay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rb_alipay.setChecked(true);
                rb_weixin_pay.setChecked(false);
            }
        });
        rb_weixin_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rb_alipay.setChecked(false);
                rb_weixin_pay.setChecked(true);
            }
        });

        rb_fapiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isFaPiao) {
                    showToast("请先填写发票信息");
                    rb_fapiao.setChecked(false);
                    return;
                }
                if (rb_fapiao.isChecked()) {
//                    rb_fapiao.setChecked(false);
                    tv_number.setText(Double.parseDouble(doorfee) + 20.00 + "0");
                } else {
//                    rb_fapiao.setChecked(true);
                    tv_number.setText(doorfee);
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == 47329) {
            isFaPiao = true;
        }
    }

    private void getData() {
        Intent intent = getIntent();
        ordernum = intent.getStringExtra("ordernum");
        doorfee = intent.getStringExtra("doorfee");
        orderType = intent.getStringExtra("orderType");
    }

    /**
     * 支付宝参数
     */
    public String getAliPayJson() {
        double invoicefee = 0D;
        doorfee = "0.01";
        if (rb_fapiao.isChecked())
            invoicefee = 20;

        //支付宝支付
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("ordernum", ordernum);
            if (orderType.equals("报修")) {
                jsonObject.put("doorfee", doorfee);
                jsonObject.put("totalfee", Double.parseDouble(doorfee) + invoicefee);
            } else if (orderType.equals("报价")) {
                jsonObject.put("quotefee", doorfee);
                jsonObject.put("sum", Double.parseDouble(doorfee) + invoicefee);
            }
            jsonObject.put("invoicefee", invoicefee);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    /**
     * 支付宝url
     */
    public String getAliPayUrl() {
        //默认报修
        String url = "alipay";
        if (orderType.equals("报价")) {
            url = "quotealipay";
        } else if (orderType.equals("任务")) {
            url = "taskdepositalipay";
        }
        return url;
    }

    /**
     * 微信参数
     */
    public String getWxPayJson() {
        double invoicefee = 0D;
        doorfee = "0.01";
        if (rb_fapiao.isChecked())
            invoicefee = 20;
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("ordernum", ordernum);

            if (orderType.equals("报修")) {
                jsonObject.put("doorfee", doorfee);
                jsonObject.put("totalfee", Double.parseDouble(doorfee) + invoicefee);
            } else if (orderType.equals("报价")) {
                jsonObject.put("quotefee", doorfee);
                jsonObject.put("totalfee", Double.parseDouble(doorfee) + invoicefee);
            }
            jsonObject.put("invoicefee", invoicefee);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    /**
     * 微信url
     */
    public String getWxPayUrl() {
        //默认报修
        String url = "repair/wxpay";
        if (orderType.equals("报价")) {
            url = "quote/wxpay";
        } else if (orderType.equals("任务")) {
            url = "task/wxpay";
        }
        return url;
    }

    /**
     * 支付宝支付
     */
    private void aliPay() {
        EanfangHttp.post(BaseService.BASE_URL + "/" + getAliPayUrl())
                .params("json", getAliPayJson())
                .execute(new EanfangCallback<JSONObject>(PayActivity.this, false) {
                    @Override
                    public void onSuccess(JSONObject bean) {
                        super.onSuccess(bean);
                        try {
                            orderInfo = bean.getString("info");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.e("info", orderInfo);
                        showToast("获取info成功");
                        //支付宝支付
                        new Thread(() -> {
                            PayTask payTask = new PayTask(PayActivity.this);
                            Map<String, String> result = payTask.payV2(orderInfo, true);
                            Message msg = new Message();
                            msg.what = SDK_PAY_FLAG;
                            msg.obj = result;
                            mHandler.sendMessage(msg);
                        }).start();
                    }
                });
    }

    /**
     * 微信支付
     */
    private void wxPay() {
        EanfangHttp.post(BaseService.BASE_URL + "/" + getWxPayUrl())
                .params("json", getWxPayJson())
                .execute(new EanfangCallback<WXPayBean>(PayActivity.this, false) {
                    @Override
                    public void onSuccess(WXPayBean bean) {
                        super.onSuccess(bean);
                        wxPayBean = bean;
                        // Config.get().setAppId(wxPayBean.getAppid());

                        final IWXAPI msgApi = WXAPIFactory.createWXAPI(PayActivity.this, null);
                        // 将该app注册到微信
                        msgApi.registerApp(wxPayBean.getAppid());
                        PayReq request = new PayReq();
                        request.appId = wxPayBean.getAppid();
                        request.partnerId = wxPayBean.getPartnerid();
                        request.prepayId = wxPayBean.getPrepayid();
                        request.packageValue = wxPayBean.getPackageX();
                        request.nonceStr = wxPayBean.getNoncestr();
                        request.timeStamp = wxPayBean.getTimestamp();
                        request.sign = wxPayBean.getSign();
                        msgApi.sendReq(request);
                    }
                });
    }
}
