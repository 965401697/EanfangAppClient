package net.eanfang.client.ui.widget;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.eanfang.base.BaseDialog;

import net.eanfang.client.R;
import net.eanfang.client.network.apiservice.ApiService;
import net.eanfang.client.network.request.EanfangCallback;
import net.eanfang.client.network.request.EanfangHttp;
import net.eanfang.client.ui.adapter.PayOrderDetailAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MrHou
 *
 * @on 2017/11/21  16:07
 * @email houzhongzhou@yeah.net
 * @desc
 */

public class CompanyQuoteItemView extends BaseDialog {
    private TextView tv_project_name;
    private TextView tv_order;
    private RecyclerView mRecyclerView;
    private TextView tv_reporter;
    private TextView tv_phone;
    private TextView tv_count_money;
    private LinearLayoutManager llm;
    private List<PayOrderDetailBean.DetailListBean> mDataList = new ArrayList<>();
    private String ordernum;
    //2017年7月5日 lin
    private TextView tv_client_company_name_wr;
    //2017年7月6日 lin
    private TextView tv_submitter;
    private TextView tv_submitter_phone;
    private Activity mContext;

    public CompanyQuoteItemView(Activity context, boolean isfull,String ordernum) {
        super(context, isfull);
        this.mContext=context;
        this.ordernum=ordernum;
    }


    @Override
    protected void initCustomView(Bundle savedInstanceState) {
        setContentView(R.layout.view_company_quote_item);
        initView();
        initData();

    }
    private void initView() {
        tv_project_name = (TextView) findViewById(R.id.tv_project_name);
        tv_order = (TextView) findViewById(R.id.tv_order);
        // mRecyclerView = (RecyclerView) findViewById(R.id.rv_list);
        tv_reporter = (TextView) findViewById(R.id.tv_reporter);
        tv_phone = (TextView) findViewById(R.id.tv_phone);
        tv_count_money = (TextView) findViewById(R.id.tv_count_money);
        //2017年7月5日 lin
        tv_client_company_name_wr = (TextView) findViewById(R.id.tv_client_company_name_wr);
        //2017年7月6日 lin
        tv_submitter = (TextView) findViewById(R.id.tv_submitter);
        tv_submitter_phone = (TextView) findViewById(R.id.tv_submitter_phone);

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_list);
        llm = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(llm);
    }

    private void initData() {

        JSONObject object = new JSONObject();
        try {
            object.put("selfordernum", ordernum);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        EanfangHttp.post(ApiService.PAY_ORDER_DETAIL)
                .tag(this)
                .params("json", object.toString())
                .execute(new EanfangCallback<PayOrderDetailBean>(mContext, false) {
                    @Override
                    public void onSuccess(PayOrderDetailBean bean) {
                        super.onSuccess(bean);
                        PayOrderDetailBean payOrderDetailBean = bean;
                        mDataList = payOrderDetailBean.getDetailList();
                        tv_project_name.setText(payOrderDetailBean.getItemname());
                        tv_order.setText(payOrderDetailBean.getOrdernum());
                        tv_reporter.setText(payOrderDetailBean.getClientname());
                        tv_phone.setText(payOrderDetailBean.getClientphone());
                        tv_count_money.setText("¥" + payOrderDetailBean.getSum());
                        //2017年7月5日
                        tv_client_company_name_wr.setText(payOrderDetailBean.getClientcompanynamewr());
                        //2017年7月6日 lin
                        tv_submitter.setText(payOrderDetailBean.getWorkername());
                        tv_submitter_phone.setText(payOrderDetailBean.getWorkerphone());
                        initAdapter();
                    }
                });
    }

    private void initAdapter() {
        BaseQuickAdapter payOrderDetailAdapter = new PayOrderDetailAdapter(R.layout.item_pay_detail, mDataList);
        payOrderDetailAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        mRecyclerView.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                View secondItem = llm.findViewByPosition(position).findViewById(R.id.second_item);

                if (secondItem.getVisibility() == View.VISIBLE) {
                    secondItem.setVisibility(View.GONE);
                } else {
                    secondItem.setVisibility(View.VISIBLE);
                }
            }
        });
        mRecyclerView.setAdapter(payOrderDetailAdapter);
    }

}
