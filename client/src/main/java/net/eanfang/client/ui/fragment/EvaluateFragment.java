package net.eanfang.client.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.eanfang.apiservice.UserApi;
import com.eanfang.application.EanfangApplication;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.ReceivedEvaluateBean;
import com.eanfang.ui.base.BaseFragment;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.QueryEntry;

import net.eanfang.client.R;
import net.eanfang.client.ui.adapter.EvaluateAdapter;
import net.eanfang.client.ui.widget.EvaluateClientDialog;

import java.util.List;


/**
 * 客户端收到的评价
 * Created by Administrator on 2017/6/22.
 */

public class EvaluateFragment extends BaseFragment {
    private RecyclerView mRecyclerView;

    public static EvaluateFragment getInstance() {
        EvaluateFragment sf = new EvaluateFragment();
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
        queryEntry.setPage(1);
        queryEntry.setSize(5);
        EanfangHttp.post(UserApi.GET_CILENT_EVALUATE_LIST)
                .upJson(JsonUtils.obj2String(queryEntry))
                .execute(new EanfangCallback<ReceivedEvaluateBean>(getActivity(), false, ReceivedEvaluateBean.class, (bean) -> {
                    initAdapter(bean.getList());
                }));

    }

    @Override
    protected void initView() {
        mRecyclerView = findViewById(R.id.rv_list);
    }

    private void initAdapter(List<ReceivedEvaluateBean.ListBean> mDataList) {
        EvaluateAdapter evaluateAdapter = new EvaluateAdapter(R.layout.item_evaluate, mDataList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        evaluateAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                new EvaluateClientDialog(getActivity(), mDataList.get(position)).show();
            }
        });
        mRecyclerView.setAdapter(evaluateAdapter);
    }

    @Override
    protected void setListener() {

    }

}