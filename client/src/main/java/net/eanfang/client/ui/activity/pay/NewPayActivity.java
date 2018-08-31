package net.eanfang.client.ui.activity.pay;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.sdk.app.PayTask;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.application.EanfangApplication;
import com.eanfang.config.Constant;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.WXPayBean;
import com.eanfang.util.MessageUtil;
import com.eanfang.util.ToastUtil;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.yaf.base.entity.PayLogEntity;

import net.eanfang.client.R;
import net.eanfang.client.ui.activity.worksapce.StateChangeActivity;
import net.eanfang.client.ui.base.BaseClientActivity;
import net.eanfang.client.ui.base.ClientApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewPayActivity extends BaseClientActivity {

    public static final int INVOICE_SUCCESS = 47329;
    public static final int INVOICE_CALL_BACK = 3001;
    private static final int SDK_PAY_FLAG = 123456;

    @BindView(R.id.tv_edit_invoice)
    TextView tvEditInvoice;
    @BindView(R.id.tv_invoice_name)
    TextView tvInvoiceName;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.ll_price)
    LinearLayout llPrice;
    @BindView(R.id.tv_pay_type)
    TextView tvPayType;
    @BindView(R.id.tv_wx)
    TextView tvWx;
    @BindView(R.id.cb_weixin_pay)
    CheckBox cbWeixinPay;
    @BindView(R.id.tv_zfb)
    TextView tvZfb;
    @BindView(R.id.cb_alipay)
    CheckBox cbAlipay;
    @BindView(R.id.cb_invoice)
    CheckBox cbInvoice;
    @BindView(R.id.ll_edit_invoice)
    LinearLayout ll;

    private boolean mPayType = true;//支付宝 false


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
                            EanfangApplication.get().closeActivity(NewPayActivity.class.getName());
                            Intent intent = new Intent(NewPayActivity.this, StateChangeActivity.class);
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
        setContentView(R.layout.activity_pay1);
        ButterKnife.bind(this);
        setTitle("支付");
        setLeftBack();

        initData();

        startTransaction(true);
    }


    private void initData() {
        Intent intent = getIntent();
        payLogEntity = (PayLogEntity) intent.getSerializableExtra("payLogEntity");
        if (payLogEntity == null) {
            showToast("参数错误,请重试");
            finishSelf();
        }

        calcPrice();

        MoneyAdapter moneyAdapter = new MoneyAdapter(R.layout.item_money);
        List<MoneyBean> list = new ArrayList<>();
        MoneyBean bean = new MoneyBean();
        bean.money = (float) (payLogEntity.getPayPrice() / 100.0);
        bean.title = "上门费";
        list.add(bean);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        moneyAdapter.bindToRecyclerView(recyclerView);
        moneyAdapter.setNewData(list);

        tvPrice.setText(String.valueOf(payLogEntity.getPayPrice() / 100.0));


        cbWeixinPay.setChecked(mPayType);

        cbInvoice.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    ll.setVisibility(View.VISIBLE);
                    tvInvoiceName.setVisibility(View.VISIBLE);
                } else {
                    ll.setVisibility(View.GONE);
                    tvInvoiceName.setVisibility(View.GONE);
                }
            }
        });
    }

    /**
     * 计算非用  测试用的 后起会通过后台返回的数值进行计算
     */
    private void calcPrice() {
        payLogEntity.setOriginPrice(1);
        payLogEntity.setReducedPrice(0);
        payLogEntity.setPayPrice(1);
    }

    @OnClick({R.id.ll_edit_invoice, R.id.ll_wx, R.id.ll_alipay, R.id.tv_outline_pay, R.id.tv_pay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_edit_invoice:
                Intent in = new Intent(NewPayActivity.this, InvoiceActivity.class);
                startActivity(in);
                break;
            case R.id.ll_wx:
                mPayType = true;
                cbAlipay.setChecked(!mPayType);
                cbWeixinPay.setChecked(mPayType);
                break;
            case R.id.ll_alipay:
                mPayType = false;
                cbAlipay.setChecked(!mPayType);
                cbWeixinPay.setChecked(mPayType);
                break;
            case R.id.tv_outline_pay:

                EanfangApplication.get().closeActivity(NewPayActivity.class.getName());
                Intent intent = new Intent(NewPayActivity.this, StateChangeActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("message", MessageUtil.payLatter());
                intent.putExtras(bundle);
                startActivity(intent);

                break;
            case R.id.tv_pay:
                if (!mPayType) {
                    //支付宝支付
                    payLogEntity.setPayType(0);
                    aliPay();
                } else {
                    //微信支付
                    payLogEntity.setPayType(1);
                    isWeixinAvilible(NewPayActivity.this);
                }


                break;
        }
    }

    /**
     * 支付宝支付
     */
    private void aliPay() {
        EanfangHttp.post(getAliPayUrl(payLogEntity.getOrderType()))
                .upJson(JSON.toJSONString(payLogEntity))
                .execute(new EanfangCallback<JSONObject>(NewPayActivity.this, true, JSONObject.class) {
                    @Override
                    public void onSuccess(JSONObject bean) {
                        super.onSuccess(bean);
                        String sign = "";
                        sign = bean.getString("sign");
                        Log.e("info", sign);
                        //支付宝支付
                        String finalSign = sign;

                        Runnable payRunnable = () -> {
                            PayTask alipay = new PayTask(NewPayActivity.this);
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
                .execute(new EanfangCallback<WXPayBean>(NewPayActivity.this, true, WXPayBean.class) {
                    @Override
                    public void onSuccess(WXPayBean bean) {
                        super.onSuccess(bean);

                        PayReq request = new PayReq();
                        request.appId = bean.getAppid();
                        request.partnerId = bean.getPartnerid();
                        request.prepayId = bean.getPrepayid();
                        request.packageValue = "Sign=WXPay";
                        request.nonceStr = bean.getNoncestr();
                        request.timeStamp = bean.getTimestamp();
                        request.signType = "MD5";
                        request.sign = bean.getSign();
                        ClientApplication.getWxApi().sendReq(request);

                    }
                });
    }


    private boolean isWeixinAvilible(Context mContext) {
        final PackageManager packageManager = mContext.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mm")) {
                    wxPay();
//                    WxPaymentUtils wxPaymentUtils = new WxPaymentUtils(this);
//                    wxPaymentUtils.wxPay(PayActivity.this,payLogEntity);
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

    class MoneyBean {
        public float money;
        public String title;
    }
}
