package net.eanfang.client.ui.fragment.selectworker;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.annimon.stream.Stream;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.eanfang.apiservice.RepairApi;
import com.eanfang.application.EanfangApplication;
import com.eanfang.config.Config;
import com.eanfang.config.Constant;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.CollectionWorkerListBean;
import com.eanfang.ui.base.BaseFragment;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.QueryEntry;
import com.yaf.base.entity.RepairOrderEntity;
import com.yaf.base.entity.WorkerEntity;

import net.eanfang.client.R;
import net.eanfang.client.ui.activity.worksapce.SelectWorkerActivity;
import net.eanfang.client.ui.activity.worksapce.WorkerDetailActivity;
import net.eanfang.client.ui.adapter.CollectionWorkerListAdapter;
import net.eanfang.client.ui.adapter.SelectWorkerAdapter;
import net.eanfang.client.ui.fragment.evaluate.EvaluateFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Guanluocang
 * @date on 2018/4/27  14:01
 * @decision 全部技师
 */
public class AllWorkerFragment extends BaseFragment {

    private RecyclerView mRecyclerView;
    private TextView mTvNoData;
    private List<WorkerEntity> selectWorkerList = new ArrayList<>();

    private RepairOrderEntity toRepairBean;
    private ArrayList<String> businessIds = new ArrayList<>();
    private int mDoorFee;
    private SelectWorkerAdapter selectWorkerAdapter;

    private Long mOwnerOrgId;

    public static AllWorkerFragment getInstance(RepairOrderEntity toRepairBean, ArrayList<String> businessIds, int doorfee, Long ownerOrgId) {
        AllWorkerFragment allWorkerFragment = new AllWorkerFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("toRepairBean", toRepairBean);
        bundle.putStringArrayList("bussinsList", businessIds);
        bundle.putInt("doorFee", doorfee);
        bundle.putLong("mOwnerOrgId", ownerOrgId);
        allWorkerFragment.setArguments(bundle);
        return allWorkerFragment;
    }

    @Override
    protected int setLayoutResouceId() {
        return R.layout.fragment_all_worker;
    }

    @Override
    protected void initData(Bundle arguments) {

        Bundle bundle = getArguments();
        toRepairBean = (RepairOrderEntity) bundle.getSerializable("toRepairBean");
        businessIds = bundle.getStringArrayList("bussinsList");
        mDoorFee = bundle.getInt("doorFee", 0);
        mOwnerOrgId = bundle.getLong("mOwnerOrgId", 0);
    }

    @Override
    protected void initView() {
        mRecyclerView = findViewById(R.id.rv_allWorker);
        mTvNoData = findViewById(R.id.tv_noData);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL));

        selectWorkerAdapter = new SelectWorkerAdapter(R.layout.item_collection_worker, selectWorkerList);
        selectWorkerAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        mRecyclerView.setAdapter(selectWorkerAdapter);
    }

    @Override
    protected void setListener() {
        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(getActivity(), WorkerDetailActivity.class);
                intent.putExtra("toRepairBean", toRepairBean);
                intent.putExtra("companyUserId", selectWorkerList.get(position).getCompanyUserId() + "");
                intent.putExtra("workerId", selectWorkerList.get(position).getId() + "");
                intent.putExtra("doorFee", mDoorFee);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onLazyLoad() {
        super.onLazyLoad();
        // 获取全部技师
        initWorker(0, 0);
    }

    //加载技师
    private void initWorker(int serviceId, int collectId) {
        QueryEntry queryEntry = new QueryEntry();
        queryEntry.getEquals().put("regionCode", toRepairBean.getPlaceCode());
        queryEntry.getIsIn().put("serviceId", Arrays.asList(Config.get().getBaseIdByCode("2.1", 1, Constant.BIZ_TYPE) + ""));
        queryEntry.getIsIn().put("businessId", Stream.of(businessIds).distinct().toList());
        queryEntry.getEquals().put("served", serviceId + "");
        queryEntry.getEquals().put("collect", collectId + "");
        if (mOwnerOrgId != 0) {
            queryEntry.getEquals().put("companyId", mOwnerOrgId + "");
        }
        queryEntry.getEquals().put("userId", EanfangApplication.getApplication().getUserId() + "");
        EanfangHttp.post(RepairApi.GET_REPAIR_SEARCH)
                .upJson(JsonUtils.obj2String(queryEntry))
                .execute(new EanfangCallback<WorkerEntity>(getActivity(), true, WorkerEntity.class, true, (list) -> {
                    selectWorkerList = list;
                    initAdapter();
//                    initMarker();
                }));
    }

    private void initAdapter() {
        if (selectWorkerList == null || selectWorkerList.size() == 0) {
            mRecyclerView.setVisibility(View.GONE);
            mTvNoData.setVisibility(View.VISIBLE);
        } else {
            mRecyclerView.setVisibility(View.VISIBLE);
            mTvNoData.setVisibility(View.GONE);
            selectWorkerAdapter.refreshList(selectWorkerList);
            selectWorkerAdapter.notifyDataSetChanged();
        }
    }
}
