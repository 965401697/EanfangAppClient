package net.eanfang.client.ui.widget;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.eanfang.base.BaseDialog;
import com.eanfang.util.CallUtils;

import net.eanfang.client.BuildConfig;
import net.eanfang.client.R;
import net.eanfang.client.application.EanfangApplication;
import net.eanfang.client.network.apiservice.ApiService;
import net.eanfang.client.network.request.EanfangCallback;
import net.eanfang.client.network.request.EanfangHttp;
import net.eanfang.client.ui.adapter.PayOrderListAdapter;
import net.eanfang.client.ui.model.PayOrderListBean;
import net.eanfang.client.ui.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MrHou
 *
 * @on 2017/11/21  15:36
 * @email houzhongzhou@yeah.net
 * @desc 个人报价
 */

public class PersonQuoteView extends BaseDialog {
    private Activity mContext;
    private RecyclerView mRecyclerView;
    private List<PayOrderListBean.AllBean> mDataList = new ArrayList<>();

    public PersonQuoteView(Activity context, boolean isfull) {
        super(context, isfull);
        this.mContext = context;
    }

    @Override
    protected void initCustomView(Bundle savedInstanceState) {
        setContentView(R.layout.view_person_quote);
        initView();
        initData();
    }


    private void initData() {
//        new PayOrderListProtocol("").execute();
        EanfangHttp.get(ApiService.COMPANY_PAY_ORDER_LIST)
                .tag(this)
                .execute(new EanfangCallback<PayOrderListBean>(mContext, true) {
                    @Override
                    public void onSuccess(PayOrderListBean bean) {
                        super.onSuccess(bean);
                        mDataList = bean.getAll();

                        if (mDataList.size() <= 0)
                            showToast("暂无数据");

                        initAdapter();
                    }

                });
    }

    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));

    }


    private void initAdapter() {
        BaseQuickAdapter evaluateAdapter = new PayOrderListAdapter(R.layout.item_offer_pay, mDataList);
        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
//                new CompanyQuoteItemView(mContext, mDataList.get(position).getSelfordernum()).show();
                CompanyQuoteItemView companyQuoteItemView=new CompanyQuoteItemView(mContext, true,mDataList.get(position).getSelfordernum());
                companyQuoteItemView.show();
            }
        });
        evaluateAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                String confirm = mDataList.get(position).getConfirm();
                User user = EanfangApplication.getApplication().getUser();
                if ("0".equals(confirm)) {
                    switch (view.getId()) {
                        case R.id.tv_do_first:
                            if (BuildConfig.APP_TYPE == 0)
                                CallUtils.call(mContext, mDataList.get(position).getWorkerphone());
                                //如果汇报人等于当前登陆人 则取 提报人
                            else if (user.getAccount().equals(mDataList.get(position).getClientphone()))
                                CallUtils.call(mContext, mDataList.get(position).getWorkerphone());
                            else
                                CallUtils.call(mContext, mDataList.get(position).getClientphone());
                            break;
                        case R.id.tv_do_second:
                            // TODO: 2017/11/21 待修改 
//                            Intent intent = new Intent(OfferAndPayActivity.this, PayActivity.class);
//                            intent.putExtra("ordernum", mDataList.get(position).getSelfordernum());
//                            intent.putExtra("doorfee", mDataList.get(position).getSum() + "");
//                            intent.putExtra("orderType", "报价");
//                            startActivity(intent);
                            break;
                    }
                } else {
                    switch (view.getId()) {
                        case R.id.tv_do_first:
                            if (BuildConfig.APP_TYPE == 0)
                                CallUtils.call(mContext, mDataList.get(position).getWorkerphone());
                                //如果汇报人等于当前登陆人 则取 提报人
                            else if (user.getAccount().equals(mDataList.get(position).getClientphone()))
                                CallUtils.call(mContext, mDataList.get(position).getWorkerphone());
                            else
                                CallUtils.call(mContext, mDataList.get(position).getClientphone());
                            break;
                        case R.id.tv_do_second:
                            break;
                    }
                }
            }
        });
        if (mDataList.size() > 0) {
            mRecyclerView.setAdapter(evaluateAdapter);
        } else {
            findViewById(R.id.tv_nodata).setVisibility(View.VISIBLE);
        }

    }
}
