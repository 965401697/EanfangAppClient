package net.eanfang.worker.ui.activity.pay;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.eanfang.apiservice.BaseService;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.config.Constant;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.biz.model.WXPayBean;
import com.eanfang.base.kit.SDKManager;
import com.eanfang.base.kit.ali.alipay.IALiPayCallBack;
import com.eanfang.util.JumpItent;
import com.eanfang.util.MessageUtil;
import com.eanfang.util.ToastUtil;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.yaf.base.entity.PayLogEntity;

import net.eanfang.worker.R;
import net.eanfang.worker.base.WorkerApplication;
import net.eanfang.worker.ui.activity.worksapce.FaPiaoActivity;
import net.eanfang.worker.ui.activity.worksapce.StateChangeActivity;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import java.util.Map;

import butterknife.BindView;

import static com.eanfang.config.Constant.INVOICE_FEE;

/**
 * Created by MrHou
 *
 * @on 2017/11/22  9:55
 * @email houzhongzhou@yeah.net
 * @desc 支付
 */

public class PayActivity extends BaseWorkerActivity {
    public static final int INVOICE_SUCCESS = 47329;
    public static final int INVOICE_CALL_BACK = 3001;
    private static final int SDK_PAY_FLAG = 123456;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.rb_fapiao)
    CheckBox rbFapiao;
    @BindView(R.id.edit_fapiao)
    LinearLayout editFapiao;
    @BindView(R.id.tv_fapiao)
    TextView tvFapiao;
    @BindView(R.id.tv_number)
    TextView tvNumber;
    @BindView(R.id.rb_alipay)
    RadioButton rbAlipay;
    @BindView(R.id.rb_weixin_pay)
    RadioButton rbWeixinPay;
    @BindView(R.id.btn_to_pay)
    Button btnToPay;


    private Boolean isFaPiao = false;
    private WXPayBean wxPayBean;
    private PayLogEntity payLogEntity;
    private Handler mHandler;

    {
        mHandler = new Handler() {

            @Override
            @SuppressWarnings("unused")
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case SDK_PAY_FLAG:
                        PayResult payResult = new PayResult((Map<String, String>) msg.obj);

                        //对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                        // 同步返回需要验证的信息
                        String resultInfo = payResult.getResult();
                        String resultStatus = payResult.getResultStatus();
                        // 判断resultStatus 为9000则代表支付成功
                        if (TextUtils.equals(resultStatus, "9000")) {
                            // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                            ToastUtil.get().showToast(getApplicationContext(), "支付成功");
                            WorkerApplication.get().closeActivity(PayActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("message", MessageUtil.paySuccess());
                            JumpItent.jump(PayActivity.this, StateChangeActivity.class, bundle);
                        } else {
                            // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                            ToastUtil.get().showToast(getApplicationContext(), "支付失败");
                        }
                        break;
                    default:
                        break;
                }
            }

        };
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_pay);
        super.onCreate(savedInstanceState);
        initData();
        setListener();
        setTitle("支付中心");
    }

    private void initData() {
        Intent intent = getIntent();
        payLogEntity = (PayLogEntity) intent.getSerializableExtra("logEntity");
        tvMoney.setText((payLogEntity.getPayPrice() / 100) + "元");
        tvNumber.setText((payLogEntity.getPayPrice() / 100));
        rbAlipay.setChecked(true);
    }

    private void setListener() {
        btnToPay.setOnClickListener(v -> {
            if (rbAlipay.isChecked()) {
                //支付宝支付
                aliPay();
            } else {
                //微信支付
                wxPay();
            }
        });
        editFapiao.setOnClickListener(v -> {
            Intent intent = new Intent(PayActivity.this, FaPiaoActivity.class);
            intent.putExtra("orderId", payLogEntity.getOrderId());
            intent.putExtra("orderType", payLogEntity.getOrderType());
            startActivityForResult(intent, INVOICE_CALL_BACK);

        });
        rbAlipay.setOnClickListener(v -> {
            rbAlipay.setChecked(true);
            rbWeixinPay.setChecked(false);
        });
        rbWeixinPay.setOnClickListener(v -> {
            rbAlipay.setChecked(false);
            rbWeixinPay.setChecked(true);
        });

        rbFapiao.setOnClickListener(v -> {
            if (!isFaPiao) {
                showToast("请先填写发票信息");
                rbFapiao.setChecked(false);
                return;
            }
            if (rbFapiao.isChecked()) {
//                    rb_fapiao.setChecked(false);
                payLogEntity.setPayPrice(payLogEntity.getPayPrice() + INVOICE_FEE * 100);
            }
//                    rb_fapiao.setChecked(true);
            tvNumber.setText(payLogEntity.getPayPrice() / 100);

        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case INVOICE_SUCCESS:
                isFaPiao = true;
                break;
            case INVOICE_CALL_BACK:
                tvFapiao.setText(data.getStringExtra("title"));
                break;
            default:
                break;
        }

    }

    /**
     * 支付宝支付
     */
    private void aliPay() {
        EanfangHttp.post(BaseService.BASE_URL + "/" + getAliPayUrl(payLogEntity.getOrderType()))
                .upJson(JSON.toJSONString(payLogEntity))
                .execute(new EanfangCallback<JSONObject>(PayActivity.this, true, JSONObject.class) {
                    @Override
                    public void onSuccess(JSONObject bean) {
                        super.onSuccess(bean);
                        String sign = "";
                        sign = bean.getString("sign");
                        Log.e("info", sign);
                        //支付宝支付
                        String finalSign = sign;
                        aliP(finalSign);
                  /*      runOnUiThread(() -> {
                            PayTask payTask = new PayTask(PayActivity.this);
                            Map<String, String> result = payTask.payV2(finalSign, true);
                            Message msg = new Message();
                            msg.what = SDK_PAY_FLAG;
                            msg.obj = result;
                            mHandler.sendMessage(msg);
                        });*/
                    }
                });
    }

    private void aliP(String userInfo) {
        SDKManager.getALipay().aLiPay(PayActivity.this, userInfo, true, new IALiPayCallBack() {
            @Override
            public void onSuccess() {
                // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                ToastUtil.get().showToast(getApplicationContext(), "支付成功");
            }

            @Override
            public void onFail(String msg) {
                // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                ToastUtil.get().showToast(getApplicationContext(), "支付失败");
            }

            @Override
            public void onCancel() {
//                ToastUtil.get().showToast(getApplicationContext(), "支付取消");
            }
        });
    }
    /**
     * 微信支付
     */
    private void wxPay() {
        EanfangHttp.post(BaseService.BASE_URL + "/" + getWxPayUrl(payLogEntity.getOrderType()))
                .upJson(JSON.toJSONString(payLogEntity))
                .execute(new EanfangCallback<WXPayBean>(PayActivity.this, true, JSONObject.class) {
                    @Override
                    public void onSuccess(WXPayBean bean) {
                        super.onSuccess(bean);
                        wxPayBean = bean;
                        // Config.get().setAppId(wxPayBean.getAppid());
                 /*       final IWXAPI msgApi = WXAPIFactory.createWXAPI(PayActivity.this, null);
                        // 将该app注册到微信
                        msgApi.registerApp(wxPayBean.getAppid());
                        PayReq request = new PayReq();
                        request.appId = wxPayBean.getAppid();
//                        request.partnerId = wxPayBean.getPartnerid();
//                        request.prepayId = wxPayBean.getPrepayid();
//                        request.packageValue = wxPayBean.getPackageX();
//                        request.nonceStr = wxPayBean.getNoncestr();
//                        request.timeStamp = wxPayBean.getTimestamp();
                        request.sign = wxPayBean.getSign();
                        msgApi.sendReq(request);*/
                        IWXAPI iwxPayAPI=SDKManager.getWXPay().createWXAPI(PayActivity.this,null);
                        iwxPayAPI.registerApp(wxPayBean.getAppid());
                        SDKManager.getWXPay().wxPay(iwxPayAPI,bean.getAppid(),bean.getSign());
                    }
                });
    }

    public String getAliPayUrl(int orderType) {
        //报修单
        if (Constant.OrderType.REPAIR.ordinal() == orderType) {
            return NewApiService.ALIPAY_REPAIR;
        } else if (Constant.OrderType.QUOTE.ordinal() == orderType) {
            return NewApiService.ALIPAY_QUOTE;
        } else if (Constant.OrderType.PUBLISH.ordinal() == orderType) {
            return NewApiService.ALIPAY_PUBLISH;
        }
        return "";
    }

    public String getWxPayUrl(int orderType) {
        //报修单
        if (Constant.OrderType.REPAIR.ordinal() == orderType) {
            return NewApiService.WEI_XIN_REPAIR;
        } else if (Constant.OrderType.QUOTE.ordinal() == orderType) {
            return NewApiService.WEI_XIN_QUOTE;
        } else if (Constant.OrderType.PUBLISH.ordinal() == orderType) {
            return NewApiService.WEI_XIN_PUBLISH;
        }
        return "";
    }
}
