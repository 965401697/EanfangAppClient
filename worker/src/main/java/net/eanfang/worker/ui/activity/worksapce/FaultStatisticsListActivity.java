package net.eanfang.worker.ui.activity.worksapce;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.FaultTotleBean;
import com.eanfang.util.GetDateUtils;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.QueryEntry;
import com.eanfang.util.ToastUtil;
import com.picker.DoubleDatePickerDialog;


import net.eanfang.worker.R;
import net.eanfang.worker.ui.adapter.FaultStatisticsAdapter;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FaultStatisticsListActivity extends BaseWorkerActivity implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {


    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.tv_no_datas)
    TextView mTvNoData;
    @BindView(R.id.swipre_fresh)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.radio_group)
    RadioGroup radioGroup;


    private int mPage = 1;
    private int mCheckedId;
    private FaultStatisticsAdapter mAdapter;

    private String mStartTime;
    private String mEndTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fault_statistics);
        ButterKnife.bind(this);
        setTitle("故障统计");
        setLeftBack();


        mPage = 1;


        setRightImageOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mEndTime = GetDateUtils.dateToDateString(GetDateUtils.getDateNow());
        mStartTime = GetDateUtils.dateToDateString(GetDateUtils.lastDayEndDate(GetDateUtils.getDateNow()));
        mCheckedId = R.id.rb_yesterday;//初始化默认值
        initView();
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {


                if (checkedId == R.id.rb_yesterday) {
                    ((RadioButton) group.findViewById(R.id.rb_pick_day)).setText("开始时间-结束时间");
                    mCheckedId = checkedId;
                    mStartTime = GetDateUtils.dateToDateString(GetDateUtils.lastDayEndDate(GetDateUtils.getDateNow()));
                    refresh();
                } else if (checkedId == R.id.rb_week) {
                    ((RadioButton) group.findViewById(R.id.rb_pick_day)).setText("开始时间-结束时间");
                    mCheckedId = checkedId;
                    mStartTime = GetDateUtils.dateToDateString(GetDateUtils.getLastWeek(GetDateUtils.dateToDateString(GetDateUtils.getDateNow())));
                    refresh();
                } else if (checkedId == R.id.rb_thirty_day) {
                    ((RadioButton) group.findViewById(R.id.rb_pick_day)).setText("开始时间-结束时间");
                    mCheckedId = checkedId;
                    mStartTime = GetDateUtils.dateToDateString(GetDateUtils.getLastMonth(GetDateUtils.dateToDateString(GetDateUtils.getDateNow())));
                    refresh();
                } else if (checkedId == R.id.rb_pick_day) {
                    Calendar c = Calendar.getInstance();
                    // 最后一个false表示不显示日期，如果要显示日期，最后参数可以是true或者不用输入
                    new DoubleDatePickerDialog(FaultStatisticsListActivity.this, 0, new DoubleDatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker startDatePicker, int startYear, int startMonthOfYear,
                                              int startDayOfMonth, DatePicker endDatePicker, int endYear, int endMonthOfYear,
                                              int endDayOfMonth) {
                            String textString = String.format("%d-%d-%d～%d-%d-%d", startYear,
                                    startMonthOfYear + 1, startDayOfMonth, endYear, endMonthOfYear + 1, endDayOfMonth);

                            String startTime = String.format("%d-%d-%d", startYear, startMonthOfYear + 1, startDayOfMonth);
                            String endTime = String.format("%d-%d-%d", endYear, endMonthOfYear + 1, endDayOfMonth);

                            if (GetDateUtils.getTimeStamp(startTime, "yyyy-MM-dd") > GetDateUtils.getTimeStamp(endTime, "yyyy-MM-dd")) {
                                ToastUtil.get().showToast(FaultStatisticsListActivity.this, "开始时间不能大于结束时间");
                                ((RadioButton) group.findViewById(mCheckedId)).setChecked(true);
                                return;
                            }


                            mStartTime = String.format("%d-%d-%d", startYear, startMonthOfYear + 1, startDayOfMonth);
                            mEndTime = String.format("%d-%d-%d", endYear, endMonthOfYear + 1, endDayOfMonth);


                            refresh();
                            ((RadioButton) group.findViewById(checkedId)).setText(textString);

                        }

                        @Override
                        public void cancle() {
                            ((RadioButton) group.findViewById(mCheckedId)).setChecked(true);
                        }


                    }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE), true).show();
                }
            }
        });

    }


    private void initView() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        rvList.setLayoutManager(new LinearLayoutManager(this));


        mAdapter = new FaultStatisticsAdapter(R.layout.item_fault_statistics);
        mAdapter.bindToRecyclerView(rvList);
        mAdapter.setOnLoadMoreListener(this);
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

                FaultTotleBean bean = (FaultTotleBean) adapter.getData().get(position);

                Intent intent = new Intent(FaultStatisticsListActivity.this, FaultRecordListActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("startTime", mStartTime);
                bundle.putString("endTime", mEndTime);
                bundle.putString("bugOneCode", bean.getBugOneCode());
                if (view.getId() == R.id.ll_wait) {
                    bundle.putString("status", "0");
                } else if (view.getId() == R.id.ll_compelet) {
                    bundle.putString("status", "1");
                } else if (view.getId() == R.id.ll_down) {
                    bundle.putString("status", "2");
                }
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        getData(mStartTime, mEndTime);
    }


    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {
        refresh();
    }

    public void refresh() {
        mPage = 1;//下拉永远第一页
        getData(mStartTime, mEndTime);
    }

    /**
     * 加载更多
     */
    @Override
    public void onLoadMoreRequested() {
        mPage++;
        getData(mStartTime, mEndTime);
    }


    private void getData(String startTime, String endTime) {
        QueryEntry queryEntry = new QueryEntry();
        queryEntry.setSize(10);
        queryEntry.setPage(mPage);
        queryEntry.getGtEquals().put("createTime", startTime);
        queryEntry.getLt().put("createTime", endTime);
        EanfangHttp.post(NewApiService.FAULT_RECORD_TOTAL)
                .upJson(JsonUtils.obj2String(queryEntry))
//                .execute(new EanfangCallback<FaultTotleBean>(this, true, FaultTotleBean.class ,true) {
                .execute(new EanfangCallback<FaultTotleBean>(this, true, FaultTotleBean.class, true, (list) -> {
//                    @Override
//                    public void onSuccess(List<FaultTotleBean> bean) {

                    if (mPage == 1) {
                        mAdapter.getData().clear();
                        mAdapter.setNewData(list);
                        mSwipeRefreshLayout.setRefreshing(false);
                        mAdapter.loadMoreComplete();
                        if (list.size() < 10) {
                            mAdapter.loadMoreEnd();
                        }

                        if (list.size() > 0) {
                            mTvNoData.setVisibility(View.GONE);
                        } else {
                            mTvNoData.setVisibility(View.VISIBLE);
                        }


                    } else {
                        mAdapter.addData(list);
                        mAdapter.loadMoreComplete();
                        if (list.size() < 10) {
                            mAdapter.loadMoreEnd();
                        }
                    }

//                    }

//                    @Override
//                    public void onNoData(String message) {
//                        mSwipeRefreshLayout.setRefreshing(false);
//                        mAdapter.loadMoreEnd();//没有数据了
//                        if (mAdapter.getData().size() == 0) {
//                            mTvNoData.setVisibility(View.VISIBLE);
//                        } else {
//                            mTvNoData.setVisibility(View.GONE);
//                        }
//
//                    }
//
//                    @Override
//                    public void onCommitAgain() {
//                        mSwipeRefreshLayout.setRefreshing(false);
//                    }
                }));
    }

}

