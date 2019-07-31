//package net.eanfang.client.ui.widget;
//
//import android.app.Activity;
//import android.os.Bundle;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//import android.view.View;
//
//import com.alibaba.fastjson.JSONException;
//import com.alibaba.fastjson.JSONObject;
//import com.chad.library.adapter.base.BaseQuickAdapter;
//import com.chad.library.adapter.base.listener.OnItemClickListener;
//import BaseDialog;
//import com.eanfang.http.EanfangCallback;
//import com.eanfang.http.EanfangHttp;
//import com.eanfang.biz.model.bean.PayOrderListBean;
//import com.eanfang.util.CallUtils;
//
//import net.eanfang.client.R;
//import com.eanfang.apiservice.ApiService;
//import net.eanfang.client.ui.adapter.CompanyPayOrderListAdapter;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Created by MrHou
// *
// * @on 2017/11/21  15:34
// * @email houzhongzhou@yeah.net
// * @desc 企业报价
// */
//
//public class CompanyQuoteView extends BaseDialog {
//    private Activity mContext;
//    private RecyclerView mRecyclerView;
//    private List<PayOrderListBean.AllBean> mDataList = new ArrayList<>();
//
//    public CompanyQuoteView(Activity context, boolean isfull) {
//        super(context, isfull);
//        this.mContext = context;
//    }
//
//    @Override
//    protected void initCustomView(Bundle savedInstanceState) {
//        setContentView(R.layout.view_company_quote);
//        initView();
//        companyOrderListProtocol();
//    }
//
//
//    private void companyOrderListProtocol() {
//        EanfangHttp.get(ApiService.COMPANY_PAY_ORDER_LIST)
//                .tag(this)
//                .execute(new EanfangCallback<PayOrderListBean>(mContext, true) {
//                    @Override
//                    public void onSuccess(PayOrderListBean bean) {
//                        super.onSuccess(bean);
//                        mDataList = bean.getAll();
//                        initAdapter();
//                    }
//
//                    @Override
//                    public void onFail(Integer code, String message, JSONObject jsonObject) {
//                        super.onFail(code, message, jsonObject);
//                        showToast(message);
//                    }
//                });
//    }
//
//    private void initView() {
//        mRecyclerView = (RecyclerView) findViewById(R.id.rv_list);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
//
//    }
//
//    private void initAdapter() {
//        BaseQuickAdapter evaluateAdapter = new CompanyPayOrderListAdapter(R.layout.item_company_offer_pay, mDataList);
//        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
//            @Override
//            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
//                CompanyQuoteItemView companyQuoteItemView = new CompanyQuoteItemView(mContext, true, mDataList.get(position).getSelfordernum());
//                companyQuoteItemView.show();
//            }
//        });
//        evaluateAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
//            @Override
//            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
//                String confirm = mDataList.get(position).getConfirm();
//                if ("0".equals(confirm)) {
//                    switch (view.getId()) {
//                        case R.id.tv_do_first:
//                            CallUtils.call(mContext, mDataList.get(position).getWorkerphone());
//                            break;
//                        case R.id.tv_do_second:
//                            agreePay(position);
//                            break;
//                    }
//                } else {
//                    switch (view.getId()) {
//                        case R.id.tv_do_first:
//                            CompanyQuoteItemView companyQuoteItemView = new CompanyQuoteItemView(mContext, true, mDataList.get(position).getSelfordernum());
//                            companyQuoteItemView.show();
//                            break;
//                        case R.id.tv_do_second:
//                            CallUtils.call(mContext, mDataList.get(position).getWorkerphone());
//                            break;
//                    }
//                }
//
//            }
//        });
//        if (mDataList.size() > 0) {
//            mRecyclerView.setAdapter(evaluateAdapter);
//        } else {
//            findViewById(R.id.tv_nodata).setVisibility(View.VISIBLE);
//        }
//    }
//
//    private void agreePay(int pos) {
//        JSONObject object = new JSONObject();
//        try {
//            object.put("selfordernum", mDataList.get(pos).getSelfordernum());
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        EanfangHttp.post(ApiService.AGREE_PAY)
//                .tag(this)
//                .params("json", object.toString())
//                .execute(new EanfangCallback(mContext, true) {
//                    @Override
//                    public void onSuccess(Object bean) {
//                        super.onSuccess(bean);
//                        showToast("成功");
//                        companyOrderListProtocol();
//                    }
//
//                    @Override
//                    public void onFail(Integer code, String message, JSONObject jsonObject) {
//                        super.onFail(code, message, jsonObject);
//                        showToast(message);
//                    }
//                });
//    }
//}
