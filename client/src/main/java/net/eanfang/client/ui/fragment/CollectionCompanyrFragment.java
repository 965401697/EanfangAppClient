package net.eanfang.client.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.eanfang.apiservice.ApiService;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.CollectionCompanyListBean;
import com.eanfang.ui.base.BaseFragment;

import net.eanfang.client.R;
import net.eanfang.client.ui.adapter.CollectionCompanyListAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * 收藏过的公司
 * Created by Administrator on 2017/6/22.
 */

public class CollectionCompanyrFragment extends BaseFragment {
    private RecyclerView mRecyclerView;
    private List<CollectionCompanyListBean.AllBean> mDataList = new ArrayList<>();
    private int id;

    public static CollectionCompanyrFragment getInstance() {
        CollectionCompanyrFragment sf = new CollectionCompanyrFragment();
//        sf.id = id;
        return sf;
    }

    @Override
    protected int setLayoutResouceId() {
        return R.layout.activity_evaluate;
    }

    @Override
    protected void initData(Bundle arguments) {
        EanfangHttp.get(ApiService.COLLECTION_COMPANY_LIST)
                .tag(this)
                .execute(new EanfangCallback<CollectionCompanyListBean>(getActivity(), false) {
                    @Override
                    public void onSuccess(CollectionCompanyListBean bean) {
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
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL));
    }

    private void initAdapter() {
        BaseQuickAdapter evaluateAdapter = new CollectionCompanyListAdapter(R.layout.item_collection_company_list, mDataList);
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
