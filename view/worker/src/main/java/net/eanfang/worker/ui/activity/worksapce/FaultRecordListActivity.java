package net.eanfang.worker.ui.activity.worksapce;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.biz.model.FaultListsBean;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.PermKit;
import com.eanfang.util.QueryEntry;
import com.eanfang.util.ToastUtil;
import com.yaf.base.entity.RepairFailureEntity;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.adapter.FaultRecordAdapter;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FaultRecordListActivity extends BaseWorkerActivity implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.tv_no_datas)
    TextView mTvNoData;
    @BindView(R.id.swipre_fresh)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.et_search)
    EditText etSearch;

    private int mPage = 1;
    private FaultRecordAdapter mAdapter;
    private Bundle mBundle;

    private static final int MSG_SEARCH = 1;
    private static final int RESULT_ADDRESS_CALLBACK_CODE = 1002;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fault_record_list);
        ButterKnife.bind(this);
        setTitle("故障列表");
        setLeftBack();
        mPage = 1;

        mBundle = getIntent().getExtras();
        if (mBundle == null) {
            setRightImageResId(R.mipmap.ic_fualt_totle);
            setRightImageOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(FaultRecordListActivity.this, FaultStatisticsListActivity.class);
                    startActivity(intent);
                }
            });
        }
        initView();
    }


    private void initView() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        rvList.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new FaultRecordAdapter(R.layout.item_fault_record);
        mAdapter.bindToRecyclerView(rvList);
        mAdapter.setOnLoadMoreListener(this);

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (!PermKit.get().getFailureDetailPerm()) {
                    return;
                }
                RepairFailureEntity bean = (RepairFailureEntity) adapter.getData().get(position);
//                if (bean.getRepairOrderEntity() == null || bean.getRepairOrderEntity().getStatus() == 0) {//为什么那order的状态
                if (bean.getRepairOrderEntity() == null || bean.getStatus() == 0) {
                    ToastUtil.get().showToast(FaultRecordListActivity.this, "该故障未处理，不能查看详情");
                    return;
                }


                int isPhoneSolve = bean.getRepairOrderEntity().getIsPhoneSolve();

//                String status = "";
//
//                if (bean.getRepairOrderEntity().getStatus() == 5) {
//                    status = "完成";
//                } else if (bean.getRepairOrderEntity().getStatus() == 5) {
//                    status = "待确认";
//                }

                new TroubleDetalilListActivity(FaultRecordListActivity.this, true, bean.getBusRepairOrderId(), isPhoneSolve, false).show();

            }
        });
        mSwipeRefreshLayout.setRefreshing(true);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //文字变动 ， 有未发出的搜索请求，应取消
                if (mHandler.hasMessages(MSG_SEARCH)) {
                    mHandler.removeMessages(MSG_SEARCH);
                }
                //否则延迟500ms开始搜索
                mHandler.sendEmptyMessageDelayed(MSG_SEARCH, 500);
            }
        });
        getData();
    }


    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {
        refresh();
    }

    public void refresh() {
        if (!TextUtils.isEmpty(etSearch.getText().toString())) {
            etSearch.setText("");
        }
        mPage = 1;//下拉永远第一页
        getData();
    }

    /**
     * 加载更多
     */
    @Override
    public void onLoadMoreRequested() {
        mPage++;
        getData();
    }


    private void getData() {
        QueryEntry queryEntry = new QueryEntry();
        queryEntry.setSize(10);
        queryEntry.setPage(mPage);
        if (mBundle != null) {
            queryEntry.getGtEquals().put("createTime", (String) mBundle.get("startTime"));
            queryEntry.getLt().put("createTime", mBundle.get("endTime") + " 23:59:59");
            queryEntry.getLike().put("businessThreeCode", mBundle.get("bugOneCode") + "%");
            queryEntry.getEquals().put("status", (String) mBundle.get("status"));
        }

        EanfangHttp.post(NewApiService.FAULT_RECORD_LIST)
                .upJson(JsonUtils.obj2String(queryEntry))
                .execute(new EanfangCallback<FaultListsBean>(this, true, FaultListsBean.class) {
                    @Override
                    public void onSuccess(FaultListsBean bean) {

                        if (mPage == 1) {
                            mAdapter.getData().clear();
                            mAdapter.setNewData(bean.getList());
                            mSwipeRefreshLayout.setRefreshing(false);
                            mAdapter.loadMoreComplete();
                            if (bean.getList() == null) {
                                mAdapter.loadMoreEnd();
                            }

                            if (bean.getList() != null) {
                                mTvNoData.setVisibility(View.GONE);
                            } else {
                                mTvNoData.setVisibility(View.VISIBLE);
                            }


                        } else {
                            mAdapter.addData(bean.getList());
                            mAdapter.loadMoreComplete();
                            if (bean.getList().size() < 10) {
                                mAdapter.loadMoreEnd();
                            }
                        }

                    }

                    @Override
                    public void onNoData(String message) {
                        mSwipeRefreshLayout.setRefreshing(false);
                        mAdapter.loadMoreEnd();//没有数据了
                        if (mAdapter.getData().size() == 0) {
                            mTvNoData.setVisibility(View.VISIBLE);
                        } else {
                            mTvNoData.setVisibility(View.GONE);
                        }

                    }

                    @Override
                    public void onCommitAgain() {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                });
    }

    private void searchData(String locationNum) {
        QueryEntry queryEntry = new QueryEntry();
        queryEntry.getLike().put("locationNumber", locationNum);

        EanfangHttp.post(NewApiService.FAULT_RECORD_LIST)
                .upJson(JsonUtils.obj2String(queryEntry))
                .execute(new EanfangCallback<FaultListsBean>(this, false, FaultListsBean.class) {
                    @Override
                    public void onSuccess(FaultListsBean bean) {


                        mAdapter.getData().clear();
                        mAdapter.setNewData(bean.getList());
                        mSwipeRefreshLayout.setRefreshing(false);
                        mAdapter.loadMoreComplete();

                        mAdapter.loadMoreEnd();


                        if (bean.getList().size() > 0) {
                            mTvNoData.setVisibility(View.GONE);
                        } else {
                            mTvNoData.setVisibility(View.VISIBLE);
                        }


                    }

                    @Override
                    public void onNoData(String message) {
                        mSwipeRefreshLayout.setRefreshing(false);
                        mAdapter.loadMoreEnd();//没有数据了
                        if (mAdapter.getData().size() == 0) {
                            mTvNoData.setVisibility(View.VISIBLE);
                        } else {
                            mTvNoData.setVisibility(View.GONE);
                        }

                    }

                    @Override
                    public void onCommitAgain() {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                });
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String message = etSearch.getText().toString().trim();
            searchData(message);
        }
    };

}



