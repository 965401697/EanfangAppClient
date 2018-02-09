package net.eanfang.worker.ui.activity.worksapce;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.apiservice.RepairApi;
import com.eanfang.config.Config;
import com.eanfang.config.Constant;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.QueryEntry;
import com.yaf.base.entity.WorkerEntity;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.adapter.PutUpSelectWorkerAdapter;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import java.util.Arrays;
import java.util.List;

/**
 * Created by MrHou
 *
 * @on 2017/11/25  16:20
 * @email houzhongzhou@yeah.net
 * @desc
 */

public class PutUpSelectWorkerActivity extends BaseWorkerActivity {

    private RecyclerView mRecyclerView;
    private List<WorkerEntity> mDataList;
    private List<String> businessId;
    private Long companyId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_put_up_select_worker);
        getData();
        initView();
        initData();
    }

    private void initData() {
        QueryEntry queryEntry = new QueryEntry();
        queryEntry.getIsIn().put("serviceId", Arrays.asList(Config.get().getBaseIdByCode("2.1",1, Constant.BIZ_TYPE)));
        queryEntry.getIsIn().put("businessId", businessId);
        queryEntry.getEquals().put("companyId", companyId + "");
        EanfangHttp.post(RepairApi.GET_REPAIR_SEARCH)
                .upJson(JsonUtils.obj2String(queryEntry))
                .execute(new EanfangCallback<WorkerEntity>(this, true, WorkerEntity.class, true, (list) -> {
                    mDataList = list;
                    initAdapter();
                }));
    }

    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        setTitle("选择技师");
        setLeftBack();

    }

    private void getData() {
        businessId = getIntent().getStringArrayListExtra("businessId");
        companyId = getIntent().getLongExtra("companyId", 0);
    }

    private void initAdapter() {
        BaseQuickAdapter evaluateAdapter = new PutUpSelectWorkerAdapter(R.layout.item_worker_list, mDataList);
        evaluateAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        evaluateAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent();
                intent.putExtra("id", mDataList.get(position).getCompanyUserId());
                intent.putExtra("name", mDataList.get(position).getAccountEntity().getRealName());
                setResult(32313, intent);
                finish();
            }
        });
        mRecyclerView.setAdapter(evaluateAdapter);
    }


}

