package net.eanfang.worker.ui.activity.worksapce.maintenance;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.annimon.stream.Stream;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.base.BaseFragment;
import com.eanfang.biz.model.QueryEntry;
import com.eanfang.biz.model.bean.OrderProgressBean;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.util.DateKit;
import com.eanfang.util.JsonUtils;

import net.eanfang.worker.R;

import java.util.ArrayList;
import java.util.List;

import cn.hutool.core.date.DateUtil;

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
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        if (mRootView == null) {
            mRootView = inflater.inflate(R.layout.fragment_maintenance_order_status, container, false);
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
        queryEntry.getEquals().put("orderId", String.valueOf(mId));
        EanfangHttp.post(NewApiService.MAINTENANCE_DETAIL_STATUS)
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
//        if (!StrUtil.isEmpty(mOrderTime)) {
//            mTvTime.setText(mOrderTime.substring(11));
//            mTvData.setText(mOrderTime.substring(5, 10));
//            mTvWeek.setText(GetDateUtils.dateToWeek(mOrderTime.substring(0, 10)));
//        }
    }
    private void initAdapter() {

        List<OrderProgressBean> list = new ArrayList<>();

        for (OrderProgressBean bean : mDataList) {
            if (bean.getNodeCode() == 0 || bean.getNodeCode() == 1) {
                mTvTime.setText(DateUtil.parse(bean.getCreateTime()).toTimeStr());
                mTvData.setText(DateUtil.parse(bean.getCreateTime()).toString("MM-dd"));
                mTvWeek.setText(DateKit.get(bean.getCreateTime()).weekName());
            } else {
                list.add(bean);
            }
        }
        if (list.size() > 0) {
            MaintenanceOrderStatusAdapter orderProgressAdapter = new MaintenanceOrderStatusAdapter(R.layout.item_maintenance_progress, list);
            mRecyclerView.setAdapter(orderProgressAdapter);
        }
    }

    @Override
    protected ViewModel initViewModel() {
        return null;
    }

}
