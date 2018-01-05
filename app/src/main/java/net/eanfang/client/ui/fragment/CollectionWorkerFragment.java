package net.eanfang.client.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;

import net.eanfang.client.R;
import net.eanfang.client.application.EanfangApplication;
import net.eanfang.client.network.apiservice.RepairApi;
import net.eanfang.client.network.request.EanfangCallback;
import net.eanfang.client.network.request.EanfangHttp;
import net.eanfang.client.ui.adapter.CollectionWorkerListAdapter;
import net.eanfang.client.ui.base.BaseFragment;
import net.eanfang.client.ui.model.CollectionWorkerListBean;
import net.eanfang.client.util.JsonUtils;
import net.eanfang.client.util.QueryEntry;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2017/6/22.
 */

public class CollectionWorkerFragment extends BaseFragment {
    private RecyclerView mRecyclerView;
    private List<CollectionWorkerListBean.ListBean> mDataList = new ArrayList<>();
    private int id;

    public static CollectionWorkerFragment getInstance() {
        CollectionWorkerFragment sf = new CollectionWorkerFragment();
//        sf.id = id;
        return sf;
    }

    @Override
    protected int setLayoutResouceId() {
        return R.layout.activity_evaluate;
    }

    @Override
    protected void initData(Bundle arguments) {
        QueryEntry queryEntry = new QueryEntry();
        queryEntry.getEquals().put("ownerId", EanfangApplication.getApplication().getUserId() + "");
        queryEntry.setSize(10);
        queryEntry.setPage(1);
        EanfangHttp.post(RepairApi.GET_COLLECT_List)
                .upJson(JsonUtils.obj2String(queryEntry))
                .execute(new EanfangCallback<CollectionWorkerListBean>(getActivity(), true, CollectionWorkerListBean.class, (bean) -> {
                    mDataList = bean.getList();
                    initAdapter();
                }));

    }

    @Override
    protected void initView() {
        mRecyclerView = findViewById(R.id.rv_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL));

    }

    private void initAdapter() {
        BaseQuickAdapter evaluateAdapter = new CollectionWorkerListAdapter(R.layout.item_collection_worker, mDataList);
        evaluateAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
            }
        });
        mRecyclerView.setAdapter(evaluateAdapter);
    }

    @Override
    protected void setListener() {

    }

}
