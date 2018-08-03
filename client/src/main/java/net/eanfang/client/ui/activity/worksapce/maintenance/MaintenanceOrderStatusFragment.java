package net.eanfang.client.ui.activity.worksapce.maintenance;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.annimon.stream.Stream;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.OrderProgressBean;
import com.eanfang.ui.base.BaseFragment;
import com.eanfang.util.GetDateUtils;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.QueryEntry;
import com.eanfang.util.StringUtils;

import net.eanfang.client.R;

import java.util.List;

/**
 * Created by O u r on 2018/7/16.
 */

public class MaintenanceOrderStatusFragment extends BaseFragment {

    private RecyclerView mRecyclerView;
    private List<OrderProgressBean> mDataList;
    private Long mId;
    private String mOrderTime = "";
    private TextView mTvData, mTvWeek, mTvTime;

    public static MaintenanceOrderStatusFragment newInstance(long id) {
        MaintenanceOrderStatusFragment f = new MaintenanceOrderStatusFragment();
        f.mId = id;
        return f;
    }


    @Override
    protected int setLayoutResouceId() {
        return R.layout.fragment_maintenance_order_status;
    }


    @Override
    protected void initData(Bundle arguments) {
        QueryEntry queryEntry = new QueryEntry();
        queryEntry.getEquals().put("orderId", String.valueOf(mId));
        EanfangHttp.post(NewApiService.MAINTENANCE_DETAIL_STATUS)
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
            mTvTime.setText(mOrderTime.substring(11));
            mTvData.setText(mOrderTime.substring(5, 10));
            mTvWeek.setText(GetDateUtils.dateToWeek(mOrderTime.substring(0, 10)));
        }
    }

    @Override
    protected void setListener() {

    }

    private void initAdapter() {
        MaintenanceOrderStatusAdapter orderProgressAdapter = new MaintenanceOrderStatusAdapter(R.layout.item_order_progress, mDataList);
        mRecyclerView.setAdapter(orderProgressAdapter);
    }

}
