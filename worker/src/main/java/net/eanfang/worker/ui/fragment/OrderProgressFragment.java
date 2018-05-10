package net.eanfang.worker.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.annimon.stream.Stream;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.apiservice.RepairApi;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.OrderProgressBean;
import com.eanfang.ui.base.BaseFragment;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.QueryEntry;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.adapter.OrderProgressAdapter;

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
    private List<OrderProgressBean> mDataList;
    private Long ordernum;

    public static OrderProgressFragment getInstance(Long ordernum) {
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
        QueryEntry queryEntry = new QueryEntry();
        queryEntry.getEquals().put("orderId", ordernum + "");
        EanfangHttp.post(RepairApi.GET_REPAIR_FLOW)
                .upJson(JsonUtils.obj2String(queryEntry))
                .execute(new EanfangCallback<OrderProgressBean>(getActivity(), true, OrderProgressBean.class, true, (list) -> {
                    mDataList = Stream.of(list).sorted((o1, o2) -> -Integer.compare(o1.getNodeCode(), o2.getNodeCode())).toList();
//                    mDataList=list;
                    initAdapter();
                }));
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

