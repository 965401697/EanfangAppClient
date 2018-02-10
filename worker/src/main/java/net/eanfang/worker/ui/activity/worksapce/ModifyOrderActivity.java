package net.eanfang.worker.ui.activity.worksapce;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.apiservice.RepairApi;
import com.eanfang.application.EanfangApplication;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.RepairedOrderBean;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.QueryEntry;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.adapter.OrderAdapter;
import net.eanfang.worker.ui.base.BaseWorkerActivity;


/**
 * 更改订单的页面
 * Created by wen on 2017/4/4.
 */

public class ModifyOrderActivity extends BaseWorkerActivity {
    public static final String TAG = ModifyOrderActivity.class.getSimpleName();
    private RecyclerView recyclerView;
    private OrderAdapter orderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_order);
        supprotToolbar();
        setTitle("选择订单");
        recyclerView = (RecyclerView) findViewById(R.id.rcv_order);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        initData();

    }

    private void initData() {
        QueryEntry queryEntry = new QueryEntry();
        queryEntry.getEquals().put("assigneeUserId", EanfangApplication.getApplication().getUserId() + "");
        EanfangHttp.post(RepairApi.GET_REPAIR_LIST)
                .upJson(JsonUtils.obj2String(queryEntry))
                .execute(new EanfangCallback<RepairedOrderBean>(this, true, RepairedOrderBean.class, (bean) -> {
                    if (bean.getList().size() > 0) {
                        orderAdapter = new OrderAdapter(R.layout.item_order_list, bean.getList());
                        orderAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                            @Override
                            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                                switch (view.getId()) {
                                    case R.id.tv_select:
                                        setResult(RESULT_CANCELED, getIntent().putExtra("orderID", bean.getList().get(position).getOrderNum()));
                                        setResult(RESULT_CANCELED, getIntent().putExtra("clientName", bean.getList().get(position).getOwnerUser().getAccountEntity().getRealName()));
                                        setResult(RESULT_CANCELED, getIntent().putExtra("clientPhone", bean.getList().get(position).getOwnerUser().getAccountEntity().getMobile()));
                                        setResult(RESULT_CANCELED, getIntent().putExtra("orderid", bean.getList().get(position).getId()));
                                        setResult(RESULT_CANCELED, getIntent().putExtra("topId", bean.getList().get(position).getOwnerUser().getTopCompanyId()));
                                        setResult(RESULT_CANCELED, getIntent().putExtra("orgcode", bean.getList().get(position).getOwnerOrgCode()));
                                        setResult(RESULT_CANCELED, getIntent().putExtra("assignUid", bean.getList().get(position).getOwnerUserId()));
                                        setResult(RESULT_CANCELED, getIntent().putExtra("tvAddress", bean.getList().get(position).getAddress()));
                                        setResult(RESULT_CANCELED, getIntent().putExtra("placeCode", bean.getList().get(position).getPlaceCode()));
                                        setResult(RESULT_CANCELED, getIntent().putExtra("lat", bean.getList().get(position).getLatitude()));
                                        setResult(RESULT_CANCELED, getIntent().putExtra("lon", bean.getList().get(position).getLongitude()));
                                        setResult(RESULT_CANCELED, getIntent().putExtra("bean", bean));


                                        finishSelf();
                                        break;
                                    default:
                                        break;
                                }
                            }
                        });
                        recyclerView.setAdapter(orderAdapter);
                    }
                }));
    }
}
