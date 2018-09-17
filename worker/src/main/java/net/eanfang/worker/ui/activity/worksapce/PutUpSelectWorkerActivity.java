package net.eanfang.worker.ui.activity.worksapce;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.eanfang.apiservice.RepairApi;
import com.eanfang.config.Config;
import com.eanfang.config.Constant;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.QueryEntry;
import com.yaf.base.entity.WorkerEntity;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.adapter.SelectWorkerAdapter;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MrHou
 *
 * @on 2017/11/25  16:20
 * @email houzhongzhou@yeah.net
 * @desc
 */

public class PutUpSelectWorkerActivity extends BaseWorkerActivity {

    @BindView(R.id.rv_list)
    RecyclerView rvList;

    private List<WorkerEntity> mDataList = new ArrayList<>();
    private List<String> businessId;
    private Long companyId;

    private SelectWorkerAdapter selectWorkerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_put_up_select_worker);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initView() {
        businessId = getIntent().getStringArrayListExtra("businessId");
        companyId = getIntent().getLongExtra("companyId", 0);
        setTitle("选择技师");
        setLeftBack();

        rvList.setLayoutManager(new LinearLayoutManager(PutUpSelectWorkerActivity.this));
        rvList.addItemDecoration(new DividerItemDecoration(PutUpSelectWorkerActivity.this, DividerItemDecoration.VERTICAL));

    }

    private void initData() {
        QueryEntry queryEntry = new QueryEntry();
        queryEntry.getIsIn().put("serviceId", Arrays.asList(Config.get().getBaseIdByCode("2.1", 1, Constant.BIZ_TYPE) + ""));
        queryEntry.getIsIn().put("businessId", businessId);
        queryEntry.getEquals().put("companyId", companyId + "");
        EanfangHttp.post(RepairApi.GET_REPAIR_SEARCH)
                .upJson(JsonUtils.obj2String(queryEntry))
                .execute(new EanfangCallback<WorkerEntity>(this, true, WorkerEntity.class, true, (list) -> {
                    mDataList = list;
                    initAdapter();
                }));
    }


    private void initAdapter() {

        selectWorkerAdapter = new SelectWorkerAdapter(R.layout.item_collection_worker, mDataList);
        selectWorkerAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        rvList.setAdapter(selectWorkerAdapter);

        rvList.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent();
                intent.putExtra("id", mDataList.get(position).getCompanyUserId());
                intent.putExtra("name", mDataList.get(position).getAccountEntity().getRealName());
                setResult(200, intent);
                finishSelf();
            }
        });
    }


}

