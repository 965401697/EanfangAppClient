package net.eanfang.client.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;

import net.eanfang.client.R;
import net.eanfang.client.network.apiservice.ApiService;
import net.eanfang.client.network.request.EanfangCallback;
import net.eanfang.client.network.request.EanfangHttp;
import net.eanfang.client.ui.adapter.OrderProgressAdapter;
import net.eanfang.client.ui.base.BaseFragment;
import net.eanfang.client.ui.model.OrderProgressBean;

import java.util.List;

/**
 * Created by MrHou
 *
 * @on 2017/11/23  19:49
 * @email houzhongzhou@yeah.net
 * @desc
 */

public class OrderProgressFragment extends BaseFragment {

    private RecyclerView mRecyclerView;
    private List<OrderProgressBean.AllBean> mDataList;
    private String ordernum;

    public static OrderProgressFragment getInstance(String ordernum) {
        OrderProgressFragment sf = new OrderProgressFragment();
        sf.ordernum = ordernum;
        return sf;
    }

    @Override
    protected int setLayoutResouceId() {
        return R.layout.activity_order_progress;
    }

    @Override
    protected void initData(Bundle arguments) {

        EanfangHttp.get(ApiService.ORDER_PROGRESS)
                .tag(this)
                .params("orderNum",ordernum)
                .execute(new EanfangCallback<OrderProgressBean>(getActivity(),false){
                    @Override
                    public void onSuccess(OrderProgressBean bean) {
                        super.onSuccess(bean);
                        mDataList = bean.getAll();
                        initAdapter();
                    }
                });
    }

    @Override
    protected void initView() {
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    protected void setListener() {

    }

    private void initAdapter() {
        BaseQuickAdapter orderProgressAdapter = new OrderProgressAdapter(R.layout.item_order_progress, mDataList);
        mRecyclerView.setAdapter(orderProgressAdapter);
    }

}
