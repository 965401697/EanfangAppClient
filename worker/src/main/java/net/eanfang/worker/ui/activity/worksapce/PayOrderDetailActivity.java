package net.eanfang.worker.ui.activity.worksapce;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.QuotationBean;
import com.eanfang.ui.base.BaseActivity;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.adapter.QuotationDetailAdapter;
import net.eanfang.worker.ui.adapter.QuotationPartsAdapter;
import net.eanfang.worker.ui.adapter.QuotationServiceAdapter;
import net.eanfang.worker.ui.widget.QuotationDetailView;
import net.eanfang.worker.ui.widget.QuotationPartsView;
import net.eanfang.worker.ui.widget.QuotationServiceView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MrHou
 *
 * @on 2017/11/27  11:31
 * @email houzhongzhou@yeah.net
 * @desc
 */

public class PayOrderDetailActivity extends BaseActivity {
    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_project_name)
    TextView tvProjectName;
    @BindView(R.id.tv_client_company_name_wr)
    TextView tvClientCompanyNameWr;
    @BindView(R.id.tv_order)
    TextView tvOrder;
    @BindView(R.id.rv_list)
    RecyclerView mRecyclerView;
    @BindView(R.id.tv_count_money)
    TextView tvCountMoney;
    @BindView(R.id.tv_submitter)
    TextView tvSubmitter;
    @BindView(R.id.tv_submitter_phone)
    TextView tvSubmitterPhone;
    @BindView(R.id.tv_reporter)
    TextView tvReporter;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.btn_pay)
    Button btnPay;
    @BindView(R.id.rv_list1)
    RecyclerView rvList1;
    @BindView(R.id.rv_list2)
    RecyclerView rvList2;
    private Long id;
    private LinearLayoutManager llm;
    private List<QuotationBean.QuoteDevicesBean> quoteDevicesBeanList = new ArrayList<>();
    private List<QuotationBean.QuotePartsBean> quotePartsBeanList = new ArrayList<>();
    private List<QuotationBean.QuoteServicesBean> quoteServicesBeanList = new ArrayList<>();
    private QuotationDetailAdapter quotationDetailAdapter;
    private QuotationPartsAdapter quotationPartsAdapter;
    private QuotationServiceAdapter quotationServiceAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_detail);
        ButterKnife.bind(this);
        initView();
        initData();
    }


    private void initView() {
        id = getIntent().getLongExtra("id", 0);
        llm = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(llm);
        btnPay.setVisibility(View.INVISIBLE);
        setTitle("订单详情");
        setLeftBack();

    }


    private void initData() {

        EanfangHttp.post(NewApiService.QUOTE_ORDER_DETAIL)
                .params("id", id)
                .execute(new EanfangCallback<QuotationBean>(this, false, QuotationBean.class, (bean) -> {

                    fillData(bean);
                }));
    }

    private void fillData(QuotationBean payOrderDetailBean) {
        quoteDevicesBeanList = payOrderDetailBean.getQuoteDevices();
        quotePartsBeanList = payOrderDetailBean.getQuoteParts();
        quoteServicesBeanList = payOrderDetailBean.getQuoteServices();
        tvProjectName.setText(payOrderDetailBean.getProjectName());
        tvOrder.setText(payOrderDetailBean.getRepairOrderNum());
        tvReporter.setText(payOrderDetailBean.getReporter());
        tvPhone.setText(payOrderDetailBean.getReporterPhone());
        tvCountMoney.setText("¥" + payOrderDetailBean.getTotalCost()/100);
        //2017年7月5日
        tvClientCompanyNameWr.setText(payOrderDetailBean.getClientName());
        //2017年7月6日 lin
        tvSubmitter.setText(payOrderDetailBean.getOfferer().getAccountEntity().getRealName());
        tvSubmitterPhone.setText(payOrderDetailBean.getOfferer().getAccountEntity().getMobile());
        initAdapter();

    }

    private void initAdapter() {

        quotationDetailAdapter = new QuotationDetailAdapter(R.layout.item_quotation_detail, quoteDevicesBeanList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(quotationDetailAdapter);
        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                new QuotationDetailView(PayOrderDetailActivity.this, quoteDevicesBeanList.get(position)).show();
            }
        });

        quotationPartsAdapter = new QuotationPartsAdapter(R.layout.item_quotation_detail, quotePartsBeanList);
        rvList1.setLayoutManager(new LinearLayoutManager(this));
        rvList1.setAdapter(quotationPartsAdapter);
        rvList1.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                new QuotationPartsView(PayOrderDetailActivity.this, quotePartsBeanList.get(position)).show();
            }
        });

        quotationServiceAdapter = new QuotationServiceAdapter(R.layout.item_quotation_detail, quoteServicesBeanList);
        rvList2.setLayoutManager(new LinearLayoutManager(this));
        rvList2.setAdapter(quotationServiceAdapter);
        rvList2.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                new QuotationServiceView(PayOrderDetailActivity.this, quoteServicesBeanList.get(position)).show();
            }
        });
    }
}
