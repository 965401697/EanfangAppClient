package net.eanfang.client.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;

import net.eanfang.client.R;
import net.eanfang.client.network.apiservice.ApiService;
import net.eanfang.client.network.request.EanfangCallback;
import net.eanfang.client.network.request.EanfangHttp;
import net.eanfang.client.ui.widget.EvaluateClientDialog;
import net.eanfang.client.ui.adapter.GivenEvaluateAdapter;
import net.eanfang.client.ui.base.BaseFragment;
import net.eanfang.client.ui.model.GiveEvaluateBean;

import java.util.List;


/**
 * 给出的评价
 * Created by Administrator on 2017/6/22.
 */

public class GiveEvaluateFragment extends BaseFragment {
    private RecyclerView mRecyclerView;
    private List<GiveEvaluateBean.AllBean> mDataList;


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
        EanfangHttp.get(ApiService.GIVE_EVALUATE)
                .tag(this)
                .execute(new EanfangCallback<GiveEvaluateBean>(getActivity(), false) {
                    @Override
                    public void onSuccess(GiveEvaluateBean bean) {
                        super.onSuccess(bean);
                        mDataList = bean.getAll();
                        initAdapter();
                    }

                    @Override
                    public void onFail(Integer code, String message, JSONObject jsonObject) {
                        super.onFail(code, message, jsonObject);
                        showToast(message);
                    }
                });
    }

    @Override
    protected void initView() {
        mRecyclerView = findViewById(R.id.rv_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL));

    }

    private void initAdapter() {
        BaseQuickAdapter evaluateAdapter = new GivenEvaluateAdapter(R.layout.item_evaluate, mDataList);
        evaluateAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(getActivity(), EvaluateClientDialog.class);
                intent.putExtra("flag", 2);
                intent.putExtra("gzxzzc", mDataList.get(position).getServiceattitude());
                intent.putExtra("jsjs", mDataList.get(position).getTechlevel());
                intent.putExtra("tdrqyh", mDataList.get(position).getResptime());
                intent.putExtra("xchj", mDataList.get(position).getProstandard());
                intent.putExtra("zysbsxcd", mDataList.get(position).getWorkefficient());
                intent.putExtra("title", "给出的评价");
                startActivity(intent);
            }
        });
        mRecyclerView.setAdapter(evaluateAdapter);
    }

    @Override
    protected void setListener() {

    }

}
