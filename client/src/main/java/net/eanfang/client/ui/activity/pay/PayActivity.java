package net.eanfang.client.ui.activity.pay;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
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
import com.alipay.sdk.app.PayTask;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.application.EanfangApplication;
import com.eanfang.config.Constant;
import com.eanfang.config.EanfangConst;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.WXPayBean;
import com.eanfang.util.MessageUtil;
import com.eanfang.util.ToastUtil;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.yaf.base.entity.PayLogEntity;

import net.eanfang.client.R;
import net.eanfang.client.ui.activity.worksapce.FaPiaoActivity;
import net.eanfang.client.ui.activity.worksapce.StateChangeActivity;
import net.eanfang.client.ui.base.BaseClientActivity;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.eanfang.config.Constant.INVOICE_FEE;

/**
 * Created by MrHou
 *
 * @on 2017/11/22  9:55
 * @email houzhongzhou@yeah.net
 * @desc 支付
 */

public class PayActivity extends BaseClientActivity {
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
    @BindView(R.id.btn_to_pay_latter)
    Button btnToPayLatter;


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
                            EanfangApplication.get().closeActivity(PayActivity.class.getName());
                            Intent intent = new Intent(PayActivity.this, StateChangeActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("message", MessageUtil.paySuccess());
                            intent.putExtras(bundle);
                            startActivity(intent);
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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        ButterKnife.bind(this);
        initData();
        setListener();
        setLeftBack();
        setTitle("支付中心");
    }

    private void initData() {
        Intent intent = getIntent();
        payLogEntity = (PayLogEntity) intent.getSerializableExtra("payLogEntity");
        if (payLogEntity == null) {
            showToast("参数错误,请重试");
            finishSelf();
        }

        calcPrice();

        tvMoney.setText((payLogEntity.getPayPrice() / 100.0) + "元");
        tvNumber.setText((payLogEntity.getPayPrice() / 100.0) + "");
        rbAlipay.setChecked(true);
    }

    /**
     * 计算非用
     */
    private void calcPrice() {
        payLogEntity.setOriginPrice(1);
        payLogEntity.setReducedPrice(0);
        payLogEntity.setPayPrice(1);
    }

    private void setListener() {
        btnToPay.setOnClickListener(v -> {
            if (rbAlipay.isChecked()) {
                //支付宝支付
                payLogEntity.setPayType(0);
                aliPay();
            } else {
                //微信支付
                payLogEntity.setPayType(1);
                isWeixinAvilible(PayActivity.this);
            }
        });

        btnToPayLatter.setOnClickListener(v -> {
            EanfangApplication.get().closeActivity(PayActivity.class.getName());
            Intent intent = new Intent(PayActivity.this, StateChangeActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("message", MessageUtil.payLatter());
            intent.putExtras(bundle);
            startActivity(intent);
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
//            if (!isFaPiao) {
//                showToast("请先填写发票信息");
//                rbFapiao.setChecked(false);
//                return;
//            }
            if (rbFapiao.isChecked()) {
//                    rb_fapiao.setChecked(false);
                payLogEntity.setPayPrice(payLogEntity.getPayPrice() + INVOICE_FEE * 100);
            } else {
                payLogEntity.setPayPrice(payLogEntity.getPayPrice() - INVOICE_FEE * 100);
            }
//                    rb_fapiao.setChecked(true);
            tvNumber.setText(payLogEntity.getPayPrice() / 100.0 + "");

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
        EanfangHttp.post(getAliPayUrl(payLogEntity.getOrderType()))
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

                        Runnable payRunnable = () -> {
                            PayTask alipay = new PayTask(PayActivity.this);
                            Map<String, String> result = alipay.payV2(finalSign, true);
                            Message msg = new Message();
                            msg.what = SDK_PAY_FLAG;
                            msg.obj = result;
                            mHandler.sendMessage(msg);
                        };
                        // 必须异步调用
                        Thread payThread = new Thread(payRunnable);
                        payThread.start();
                    }
                });
    }

    /**
     * 微信支付
     */
    private void wxPay() {
        EanfangHttp.post(getWxPayUrl(payLogEntity.getOrderType()))
                .upJson(JSON.toJSONString(payLogEntity))
                .execute(new EanfangCallback<WXPayBean>(PayActivity.this, true, WXPayBean.class) {
                    @Override
                    public void onSuccess(WXPayBean bean) {
                        super.onSuccess(bean);
                        wxPayBean = bean;
                        // Config.get().setAppId(wxPayBean.getAppid());
                        new Thread(() -> {
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
                            boolean isOk = msgApi.sendReq(request);
                            System.err.println(isOk + "------------");
                        }).start();

                    }
                });
    }

    /**
     * 判断手机是否安装微信
     *
     * @param mContext
     * @return
     */
    private boolean isWeixinAvilible(Context mContext) {
        final PackageManager packageManager = mContext.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mm")) {
                    wxPay();
                    return true;
                }
            }
        }
        //  没有安装微信的
        showToast("您的手机没有安装微信");
        return false;
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
