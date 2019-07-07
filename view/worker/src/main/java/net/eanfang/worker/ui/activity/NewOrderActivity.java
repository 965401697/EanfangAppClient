package net.eanfang.worker.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.NewOrderBean;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.QueryEntry;
import com.eanfang.util.StringUtils;

import net.eanfang.worker.R;
import net.eanfang.worker.base.WorkerApplication;
import net.eanfang.worker.ui.activity.worksapce.OrderDetailActivity;
import net.eanfang.worker.ui.activity.worksapce.design.DesignOrderDetailActivity;
import net.eanfang.worker.ui.activity.worksapce.maintenance.MaintenanceDetailActivity;
import net.eanfang.worker.ui.activity.worksapce.tender.WorkTenderDetailActivity;
import net.eanfang.worker.ui.adapter.NewOrderAdapter;
import net.eanfang.worker.ui.widget.InstallCtrlItemView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liangkailun
 * Date ：2019-05-28
 * Describe :最新订单页面
 */
public class NewOrderActivity extends BaseActivity {
    private RecyclerView mRecyclerView;
    private NewOrderAdapter mNewOrderAdapter;
    private List<NewOrderBean.ListBean> mList;
    private QueryEntry mQueryEntry;
    private int mCurrent = 1;
    private Map<String, Class> mClassMap;

    {
        String type1 = "维修";
        String type2 = "维保";
        String type3 = "设计";
        String type4 = "用工找活";
        String type5 = "标讯";
        String type6 = "安装";
        mClassMap = new HashMap<>(5);
        mClassMap.put(type1, OrderDetailActivity.class);
        mClassMap.put(type2, MaintenanceDetailActivity.class);
        mClassMap.put(type3, DesignOrderDetailActivity.class);
        mClassMap.put(type4, OrderDetailActivity.class);
        mClassMap.put(type5, WorkTenderDetailActivity.class);
        mClassMap.put(type6, InstallCtrlItemView.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_order);
        String queryEntry = getIntent().getStringExtra("query");
        if (StringUtils.isEmpty(queryEntry)) {
            mQueryEntry = new QueryEntry();
        } else {
            mQueryEntry = JSONObject.toJavaObject(JSONObject.parseObject(queryEntry), QueryEntry.class);
        }
        initView();
        initData();

    }

    private void initData() {
        mQueryEntry.setPage(mCurrent);
        mQueryEntry.setSize(10);
        EanfangHttp.post(NewApiService.QUERY_LASTEST_ORDER)
                .upJson(JSON.toJSONString(mQueryEntry))
                .execute(new EanfangCallback<NewOrderBean>(this, true, NewOrderBean.class, bean -> {
                    if (1 == mCurrent) {
                        mNewOrderAdapter.getData().clear();
                        mNewOrderAdapter.setNewData(bean.getList());
                    } else {
                        mNewOrderAdapter.addData(bean.getList());
                    }
                    mNewOrderAdapter.loadMoreComplete();
                    if (mCurrent >= bean.getTotalPage()) {
                        mNewOrderAdapter.loadMoreEnd();
                    }
                }));
    }

    private void initView() {
        String typeTender = "标讯";
        String typeInstallCtrl = "安装";
        mRecyclerView = findViewById(R.id.rec_new_order);
        setLeftBack();
        setTitle("最新订单");
        setRightImageResId(R.drawable.icon_filtrate_white);
        setRightTitle("筛选");
        setRightImageOnClickListener(this::gotoScreen);
        setRightTitleOnClickListener(this::gotoScreen);
        mNewOrderAdapter = new NewOrderAdapter();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mNewOrderAdapter.bindToRecyclerView(mRecyclerView);
        mNewOrderAdapter.setOnLoadMoreListener(() -> {
            mCurrent++;
            initData();
        }, mRecyclerView);
        mNewOrderAdapter.setOnItemClickListener((adapter, view, position) -> {
            NewOrderBean.ListBean listBean = (NewOrderBean.ListBean) adapter.getData().get(position);
            if (listBean == null) {
                return;
            }
            String type = listBean.getOrderType();
            boolean isJumpAccId = listBean.getAccId() != null && listBean.getAccId().equals(String.valueOf(WorkerApplication.get().getAccId()));
            boolean couldJump = mClassMap.get(type) != null && (isJumpAccId || typeTender.equals(type));
            if (couldJump) {
                if (!typeInstallCtrl.equals(type)) {
                    Intent intent = new Intent(NewOrderActivity.this, mClassMap.get(listBean.getOrderType()));
                    intent.putExtra("id", listBean.getOrderId());
                    intent.putExtra("orderTime", listBean.getCreateTime());
                    startActivity(intent);
                } else {
                    new InstallCtrlItemView(this, true, listBean.getOrderId()).show();
                }
            }
        });
    }

    private void gotoScreen(View view) {
        startActivity(new Intent(this, NewOrderScreenActivity.class));
    }

}
