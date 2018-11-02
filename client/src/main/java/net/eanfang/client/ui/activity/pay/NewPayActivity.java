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
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
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
import com.eanfang.util.StringUtils;
import com.eanfang.util.ToastUtil;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.yaf.base.entity.InvoiceEntity;
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
    @BindView(R.id.tv_coupon)
    TextView tvCoupon;
    @BindView(R.id.et_coupon)
    EditText etCoupon;
    @BindView(R.id.cb_coupon)
    CheckBox cbCoupon;
    @BindView(R.id.ll_coupons)
    LinearLayout llCoupons;

    private int mPayType = 1;//微信 1 支付宝 0 优惠券 3
    private String mPayPrice;

    private Boolean isFaPiao = false;
    private WXPayBean wxPayBean;

    private PayLogEntity payLogEntity;
    private Handler mHandler;
    private InvoiceEntity mInvoiceEntity;
    private final int INVOCIE_REQUEST_CODE = 1;//发票的code

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

                            subInvoice();


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

//        getInvoiceInfo();

        if (payLogEntity == null) {
            showToast("参数错误,请重试");
            finishSelf();
        }

//        calcPrice();

        MoneyAdapter moneyAdapter = new MoneyAdapter(R.layout.item_money);
        List<MoneyBean> list = new ArrayList<>();
        MoneyBean bean = new MoneyBean();
        bean.money = (float) (payLogEntity.getPayPrice() / 100.0);
        mPayPrice = String.valueOf(payLogEntity.getPayPrice() / 100.0);
        bean.title = "上门费";
        list.add(bean);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        moneyAdapter.bindToRecyclerView(recyclerView);
        moneyAdapter.setNewData(list);

        tvPrice.setText(String.valueOf(payLogEntity.getPayPrice() / 100.0));


        cbWeixinPay.setChecked(true);
        tvWx.setTextColor(getResources().getColor(R.color.color_service_title));

        cbInvoice.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (mInvoiceEntity == null) {
                        ll.setVisibility(View.VISIBLE);
                    } else {

                        tvEditInvoice.setText("修改发票信息");
                        tvInvoiceName.setText(mInvoiceEntity.getTitle());

                        ll.setVisibility(View.VISIBLE);
                        tvInvoiceName.setVisibility(View.VISIBLE);
                    }
                } else {
                    ll.setVisibility(View.GONE);
                    tvInvoiceName.setVisibility(View.GONE);
                }
            }
        });

        // 获取优惠券焦点
        etCoupon.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // 获得焦点
                    mPayType = 3;
                    cbCoupon.setChecked(mPayType == 3 ? true : false);
                    cbAlipay.setChecked(!(mPayType == 3 ? true : false));
                    cbWeixinPay.setChecked(!(mPayType == 3 ? true : false));
                    tvZfb.setTextColor(getResources().getColor(R.color.color_client_neworder));
                    tvWx.setTextColor(getResources().getColor(R.color.color_client_neworder));
                    tvCoupon.setTextColor(getResources().getColor(R.color.color_service_title));
                    tvPrice.setText(0 + "");
                }
            }
        });

    }


    @OnClick({R.id.ll_edit_invoice, R.id.ll_wx, R.id.ll_alipay, R.id.tv_outline_pay, R.id.tv_pay, R.id.ll_coupons})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_edit_invoice:
                Intent in = new Intent(NewPayActivity.this, InvoiceActivity.class);
                in.putExtra("orderId", payLogEntity.getOrderId());
                in.putExtra("orderType", payLogEntity.getOrderType());
                in.putExtra("bean", mInvoiceEntity);
                startActivityForResult(in, INVOCIE_REQUEST_CODE);
                break;
            case R.id.ll_wx:
                mPayType = 1;
                cbWeixinPay.setChecked(mPayType == 1 ? true : false);
                cbAlipay.setChecked(!(mPayType == 1 ? true : false));
                cbCoupon.setChecked(!(mPayType == 1 ? true : false));
                tvWx.setTextColor(getResources().getColor(R.color.color_service_title));
                tvZfb.setTextColor(getResources().getColor(R.color.color_client_neworder));
                tvCoupon.setTextColor(getResources().getColor(R.color.color_client_neworder));
                etCoupon.setVisibility(View.GONE);
                tvPrice.setText(mPayPrice);
                doGoneCoupon();
                break;
            case R.id.ll_alipay:
                mPayType = 0;
                cbAlipay.setChecked(mPayType == 0 ? true : false);
                cbWeixinPay.setChecked(!(mPayType == 0 ? true : false));
                cbCoupon.setChecked(!(mPayType == 0 ? true : false));
                tvZfb.setTextColor(getResources().getColor(R.color.color_service_title));
                tvWx.setTextColor(getResources().getColor(R.color.color_client_neworder));
                tvCoupon.setTextColor(getResources().getColor(R.color.color_client_neworder));
                tvPrice.setText(mPayPrice);
                etCoupon.setVisibility(View.GONE);
                doGoneCoupon();
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
                if (mPayType == 0) {
                    //支付宝支付
                    payLogEntity.setPayType(0);
                    doPayType(mPayType);
                } else if (mPayType == 1) {
                    //微信支付
                    payLogEntity.setPayType(1);
                    doPayType(mPayType);
                } else if (mPayType == 3) {
                    //优惠券支付
                    payLogEntity.setPayType(3);
                    doPayType(mPayType);
                }
                break;
            case R.id.ll_coupons:
                etCoupon.setVisibility(View.VISIBLE);
                //将光标定位
                etCoupon.requestFocus();
                etCoupon.setFocusable(true);
                etCoupon.setFocusableInTouchMode(true);
                mPayType = 3;
                cbCoupon.setChecked(mPayType == 3 ? true : false);
                cbAlipay.setChecked(!(mPayType == 3 ? true : false));
                cbWeixinPay.setChecked(!(mPayType == 3 ? true : false));
                tvZfb.setTextColor(getResources().getColor(R.color.color_client_neworder));
                tvWx.setTextColor(getResources().getColor(R.color.color_client_neworder));
                tvCoupon.setTextColor(getResources().getColor(R.color.color_service_title));
                tvPrice.setText(0 + "");
                break;
        }
    }

    public void doVaiCoupons() {
        String coupon = etCoupon.getText().toString().trim();
        if (StringUtils.isEmpty(coupon)) {
            showToast("请输入优惠码");
            return;
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("couponNum", coupon);
        payLogEntity.setPayJson(jsonObject.toJSONString());

        isCouponsPay();
    }

    /**
     * 优惠券支付
     */
    private void isCouponsPay() {
        EanfangHttp.post(NewApiService.REPAIR_PAY_COUPON)
                .upJson(JSON.toJSONString(payLogEntity))
                .execute(new EanfangCallback<JSONObject>(NewPayActivity.this, true, JSONObject.class) {
                    @Override
                    public void onSuccess(JSONObject bean) {
                        super.onSuccess(bean);
                        EanfangApplication.get().closeActivity(NewPayActivity.class.getName());
                        Intent intent = new Intent(NewPayActivity.this, StateChangeActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("message", MessageUtil.paySuccess());
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });

    }

    /**
     * 判断支付方式 进行修改价格
     */
    public void doPayType(int type) { // 支付方式  0、1：微信和支付宝：价格原始不变    3： 优惠券 实际价格:0
        if (type == 1) {
            isWeixinAvilible(NewPayActivity.this);
        } else if (type == 0) {
            aliPay();
        } else if (type == 3) {
            payLogEntity.setReducedPrice(payLogEntity.getOriginPrice());//OriginPrice() 原始价格 ReducedPrice 优惠价格(默认为0)
            payLogEntity.setPayPrice(0);// PayPrice支付价格
            doVaiCoupons();
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == INVOCIE_REQUEST_CODE) {

            mInvoiceEntity = (InvoiceEntity) data.getSerializableExtra("bean");

            tvEditInvoice.setText("修改发票信息");
            tvInvoiceName.setText(mInvoiceEntity.getTitle());

            ll.setVisibility(View.VISIBLE);
            tvInvoiceName.setVisibility(View.VISIBLE);
        }
    }

//    private void getInvoiceInfo() {
//        EanfangHttp.post(NewApiService.GET_INVOICE_INFO)
//                .params("orderId", String.valueOf(payLogEntity.getOrderId()))
//                .execute(new EanfangCallback<InvoiceEntity>(NewPayActivity.this, true, InvoiceEntity.class) {
//                    @Override
//                    public void onSuccess(InvoiceEntity bean) {
//                        mInvoiceEntity = bean;
//                    }
//                });
//    }

    public void subInvoice() {

        if (!cbInvoice.isChecked()) {
            if (mPayType == 1) {//支付宝支付 优惠券支付
                EanfangApplication.get().closeActivity(NewPayActivity.class.getName());
                Intent intent = new Intent(NewPayActivity.this, StateChangeActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("message", MessageUtil.paySuccess());
                intent.putExtras(bundle);
                startActivity(intent);
            } else if (mPayType == 0) {
                //微信支付
                endTransaction(true);
            }
            return;
        }

        EanfangHttp.post(NewApiService.FA_PIAO)
                .upJson(JSON.toJSONString(mInvoiceEntity))
                .execute(new EanfangCallback(NewPayActivity.this, false, JSONObject.class) {
                    @Override
                    public void onSuccess(Object bean) {
                        if (mPayType == 1) {       //支付宝支付 优惠券支付
                            EanfangApplication.get().closeActivity(NewPayActivity.class.getName());
                            Intent intent = new Intent(NewPayActivity.this, StateChangeActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("message", MessageUtil.paySuccess());
                            intent.putExtras(bundle);
                            startActivity(intent);
                        } else if (mPayType == 0) {
                            //微信支付
                            endTransaction(true);
                        }
                    }

                    @Override
                    public void onFail(Integer code, String message, JSONObject jsonObject) {
                        super.onFail(code, message, jsonObject);

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

    class MoneyBean {
        public float money;
        public String title;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        transactionActivities.remove(this);
    }

    /**
     * 取消优惠码输入焦点
     */
    public void doGoneCoupon() {
        etCoupon.clearFocus();//取消焦点
        ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);//关闭输入法
    }
}

