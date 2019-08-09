package net.eanfang.worker.ui.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.annimon.stream.Stream;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.apiservice.RepairApi;
import com.eanfang.base.BaseFragment;
import com.eanfang.biz.model.QueryEntry;
import com.eanfang.biz.model.bean.OrderProgressBean;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.util.DateKit;
import com.eanfang.util.JsonUtils;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.adapter.OrderProgressAdapter;

import java.util.List;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;


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
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        if (mRootView == null) {
            mRootView = inflater.inflate(R.layout.activity_order_progress, container, false);
            initView();
            initData();
        }
        ViewGroup parent = (ViewGroup) mRootView.getParent();
        if (parent != null) {
            parent.removeView(mRootView);
        }
        return mRootView;
    }

    protected void initData() {
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

    protected void initView() {
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mTvData = findViewById(R.id.tv_date);
        mTvWeek = findViewById(R.id.tv_weeks);
        mTvTime = findViewById(R.id.tv_time);
        if (!StrUtil.isEmpty(mOrderTime)) {
            mTvData.setText(DateUtil.parse(mOrderTime).toString("MM-dd"));
            mTvTime.setText(DateUtil.parse(mOrderTime).toTimeStr());
            mTvWeek.setText(DateKit.get(mOrderTime).weekName());
        }
    }

    private void initAdapter() {
        BaseQuickAdapter orderProgressAdapter = new OrderProgressAdapter(R.layout.item_order_progress, mDataList);
        mRecyclerView.setAdapter(orderProgressAdapter);
    }

    @Override
    protected ViewModel initViewModel() {
        return null;
    }

}

