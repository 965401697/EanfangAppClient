package net.eanfang.worker.ui.activity.worksapce;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
    @BindView(R.id.tv_no_data)
    TextView tvNoData;


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
        queryEntry.getIsIn().put("bizIds", Arrays.asList(Config.get().getBaseIdByCode("2.1", 1, Constant.BIZ_TYPE) + ""));
        queryEntry.getIsIn().put("sysIds", businessId);
        queryEntry.getEquals().put("companyId", companyId + "");
        EanfangHttp.post(RepairApi.GET_REPAIR_SEARCH)
                .upJson(JsonUtils.obj2String(queryEntry))
                .execute(new EanfangCallback<WorkerEntity>(this, true, WorkerEntity.class, true, (list) -> {
                    if (list != null && list.size() > 0) {
                        mDataList = list;
                        initAdapter();
                        rvList.setVisibility(View.VISIBLE);
                        tvNoData.setVisibility(View.GONE);
                    } else {
                        rvList.setVisibility(View.GONE);
                        tvNoData.setVisibility(View.VISIBLE);
                    }
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

