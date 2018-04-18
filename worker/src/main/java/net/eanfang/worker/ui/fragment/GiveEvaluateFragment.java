package net.eanfang.worker.ui.fragment;

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
import com.eanfang.model.GiveEvaluateBean;
import com.eanfang.ui.base.BaseFragment;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.QueryEntry;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.adapter.GiveEvaluateAdapter;
import net.eanfang.worker.ui.widget.EvaluateRevDialog;

import java.util.List;


/**
 * 给出的评价
 * Created by Administrator on 2017/6/22.
 */

public class GiveEvaluateFragment extends BaseFragment {
    private RecyclerView mRecyclerView;


    public static GiveEvaluateFragment getInstance() {
        GiveEvaluateFragment sf = new GiveEvaluateFragment();
        return sf;
    }

    @Override
    protected int setLayoutResouceId() {
        return R.layout.activity_evaluate;
    }

    @Override
    protected void initData(Bundle arguments) {

    }

    @Override
    protected void onLazyLoad() {
        super.onLazyLoad();
        QueryEntry queryEntry = new QueryEntry();
        queryEntry.getEquals().put("createUserId", EanfangApplication.getApplication().getUserId() + "");
        queryEntry.setPage(1);
        queryEntry.setSize(5);
        EanfangHttp.post(UserApi.GET_CILENT_EVALUATE_LIST)
                .upJson(JsonUtils.obj2String(queryEntry))
                .execute(new EanfangCallback<GiveEvaluateBean>(getActivity(), false, GiveEvaluateBean.class, (bean) -> {
                    initAdapter(bean.getList());
                }));
    }

    @Override
    protected void initView() {
        mRecyclerView = findViewById(R.id.rv_list);
    }

    private void initAdapter(List<GiveEvaluateBean.ListBean> mDataList) {
        GiveEvaluateAdapter evaluateAdapter = new GiveEvaluateAdapter(R.layout.item_evaluate, mDataList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        evaluateAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                new EvaluateRevDialog(getActivity(), mDataList.get(position)).show();
            }
        });
        mRecyclerView.setAdapter(evaluateAdapter);
    }

    @Override
    protected void setListener() {

    }

}
