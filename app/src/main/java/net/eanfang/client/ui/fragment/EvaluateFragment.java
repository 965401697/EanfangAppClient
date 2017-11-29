package net.eanfang.client.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;

import net.eanfang.client.R;
import net.eanfang.client.network.apiservice.ApiService;
import net.eanfang.client.network.request.EanfangCallback;
import net.eanfang.client.network.request.EanfangHttp;
import net.eanfang.client.ui.activity.worksapce.EvaluateClientActivity;
import net.eanfang.client.ui.adapter.EvaluateAdapter;
import net.eanfang.client.ui.base.BaseFragment;
import net.eanfang.client.ui.model.ReceivedEvaluateBean;

import java.util.List;


/**
 * 客户端收到的评价
 * Created by Administrator on 2017/6/22.
 */

public class EvaluateFragment extends BaseFragment {
    private RecyclerView mRecyclerView;
    private List<ReceivedEvaluateBean.AllBean> mDataList;

    private int id;

    public static EvaluateFragment getInstance(int id) {
        EvaluateFragment sf = new EvaluateFragment();
        sf.id = id;
        return sf;
    }

    @Override
    protected int setLayoutResouceId() {
        return R.layout.activity_evaluate;
    }

    @Override
    protected void initData(Bundle arguments) {
        EanfangHttp.get(ApiService.RECEVIED_EVALUATE)
                .tag(this)
                .execute(new EanfangCallback<ReceivedEvaluateBean>(getActivity(), false) {
                    @Override
                    public void onSuccess(ReceivedEvaluateBean bean) {
                        super.onSuccess(bean);
                        mDataList = bean.getAll();
                        initAdapter();
                    }
                });

    }

    @Override
    protected void initView() {
        mRecyclerView = findViewById(R.id.rv_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void initAdapter() {
        BaseQuickAdapter evaluateAdapter = new EvaluateAdapter(R.layout.item_evaluate, mDataList);
        evaluateAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(getActivity(), EvaluateClientActivity.class);
                intent.putExtra("flag", 1);
                intent.putExtra("gzxzzc", mDataList.get(position).getGzxzzc());
                intent.putExtra("jsjs", mDataList.get(position).getJsjs());
                intent.putExtra("tdrqyh", mDataList.get(position).getTdrqyh());
                intent.putExtra("xchj", mDataList.get(position).getXchj());
                intent.putExtra("zysbsxcd", mDataList.get(position).getZysbsxcd());
                intent.putExtra("title", "收到的评价");
                startActivity(intent);
            }
        });
        mRecyclerView.setAdapter(evaluateAdapter);
    }

    @Override
    protected void setListener() {

    }

}
