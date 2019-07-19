package net.eanfang.worker.ui.fragment;

import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.TextView;

import com.annimon.stream.Stream;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.apiservice.RepairApi;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.biz.model.OrderProgressBean;
import com.eanfang.ui.base.BaseFragment;
import com.eanfang.util.DateKit;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.QueryEntry;
import com.eanfang.util.StringUtils;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.adapter.OrderProgressAdapter;

import java.util.List;

import cn.hutool.core.date.DateUtil;


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
    private String mOrderTime = "";
    private TextView mTvData, mTvWeek, mTvTime;

    public static OrderProgressFragment getInstance(Long ordernum, String orderTime) {
        OrderProgressFragment sf = new OrderProgressFragment();
        sf.ordernum = ordernum;
        sf.mOrderTime = orderTime;
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
        mTvData = findViewById(R.id.tv_date);
        mTvWeek = findViewById(R.id.tv_weeks);
        mTvTime = findViewById(R.id.tv_time);
        if (!StringUtils.isEmpty(mOrderTime)) {
            mTvTime.setText(DateUtil.parse(mOrderTime).toTimeStr());
            mTvData.setText(DateUtil.parse(mOrderTime).toDateStr());
            mTvWeek.setText(DateKit.get(mOrderTime).weekName());
        }
    }

    @Override
    protected void setListener() {

    }

    private void initAdapter() {
        BaseQuickAdapter orderProgressAdapter = new OrderProgressAdapter(R.layout.item_order_progress, mDataList);
        mRecyclerView.setAdapter(orderProgressAdapter);
    }

}

