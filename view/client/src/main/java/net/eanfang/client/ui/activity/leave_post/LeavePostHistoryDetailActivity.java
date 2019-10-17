package net.eanfang.client.ui.activity.leave_post;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.eanfang.BuildConfig;
import com.eanfang.base.BaseActivity;
import com.eanfang.biz.rds.base.LViewModelProviders;
import com.eanfang.config.Config;
import com.eanfang.util.DateKit;
import com.eanfang.util.GlideUtil;

import net.eanfang.client.R;
import net.eanfang.client.databinding.ActivityLeavePostHistoryDetailBinding;
import net.eanfang.client.ui.activity.leave_post.bean.LeavePostHistoryDayBean;
import net.eanfang.client.ui.activity.leave_post.viewmodel.LeavePostHistoryDetailViewModel;
import net.eanfang.client.ui.adapter.LeavePostHistoryDayAdapter;

import java.text.MessageFormat;

import cn.hutool.core.date.DateUtil;

/**
 * @author liangkailun
 * Date ：2019-06-28
 * Describe :报警记录页面
 */
public class LeavePostHistoryDetailActivity extends BaseActivity {
    private ActivityLeavePostHistoryDetailBinding mBinding;
    private LeavePostHistoryDayAdapter mAdapter;
    private LeavePostHistoryDetailViewModel mViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_leave_post_history_detail);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        setTitle("报警记录");
        setLeftBack(true);
        long stationId = getIntent().getLongExtra("stationId", 0);
        String date = getIntent().getStringExtra("date");
        mViewModel.historyDayData(DateUtil.parse(date), String.valueOf(stationId));
        mBinding.tvLeavePostHistoryDetailDate.setText(MessageFormat.format("{0}\t\t{1}", DateUtil.parse(date).toString("yyyy年MM月dd日"), DateKit.get(date).weekName()));
        mBinding.imgLeavePostHistoryDetailLeft.setOnClickListener(view -> mViewModel.setLastDay(mBinding.tvLeavePostHistoryDetailDate));
        mBinding.imgLeavePostHistoryDetailRight.setOnClickListener(view -> mViewModel.setNextDay(mBinding.tvLeavePostHistoryDetailDate));
        mAdapter = new LeavePostHistoryDayAdapter(R.layout.item_leave_post_history_detail);
        mBinding.recLeavePostHistoryDetail.setLayoutManager(new LinearLayoutManager(this));
        mAdapter.bindToRecyclerView(mBinding.recLeavePostHistoryDetail);
        mAdapter.setOnItemClickListener((adapter, view, position) -> mViewModel.gotoAlarmDetailPage(LeavePostHistoryDetailActivity.this, adapter, position));
    }

    @Override
    protected ViewModel initViewModel() {
        mViewModel = LViewModelProviders.of(this, LeavePostHistoryDetailViewModel.class);
        mViewModel.getHistoryDayData().observe(this, this::setData);
        return mViewModel;
    }

    private void setData(LeavePostHistoryDayBean leavePostHistoryDayBean) {
        if (leavePostHistoryDayBean == null || leavePostHistoryDayBean.getStation() == null) {
            return;
        }
        LeavePostHistoryDayBean.StationBean stationBean = leavePostHistoryDayBean.getStation();
        ImageView imageView = findViewById(R.id.img_item_leave_post_manage_detail);
        if (stationBean.getDeviceEntity() != null) {
            GlideUtil.intoImageView(this, BuildConfig.OSS_SERVER + stationBean.getDeviceEntity().getLivePic(), imageView);
        }

        TextView name = findViewById(R.id.tv_item_leave_post_manage_detail_name);
        name.setText(stationBean.getStationName());
        TextView area = findViewById(R.id.tv_item_leave_post_manage_detail_area);
        area.setText(Config.get().getAddressByCode(stationBean.getStationPlaceCode()));
        TextView position = findViewById(R.id.tv_item_leave_post_manage_detail_position);
        findViewById(R.id.img_item_leave_post_manage_detail_next).setVisibility(View.GONE);
        position.setText(stationBean.getStationArea());
        TextView count = findViewById(R.id.tv_item_leave_post_manage_detail_count);
        count.setText(getString(R.string.text_leave_post_detail_count, leavePostHistoryDayBean.getAlertCount()));
        count.setVisibility(View.VISIBLE);
        findViewById(R.id.tv_item_leave_post_manage_detail_status).setVisibility(View.GONE);
        mAdapter.setNewData(leavePostHistoryDayBean.getAlertList());
    }


}
