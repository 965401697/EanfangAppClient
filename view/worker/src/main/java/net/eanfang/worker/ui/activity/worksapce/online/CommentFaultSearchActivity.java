package net.eanfang.worker.ui.activity.worksapce.online;

import android.content.Intent;
import android.os.Bundle;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.QueryEntry;
import com.yaf.base.entity.AskQuestionsListBean;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CommentFaultSearchActivity extends BaseWorkerActivity implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.tv_no_datas)
    TextView mTvNoData;
    @BindView(R.id.swipre_fresh)
    SwipeRefreshLayout mSwipeRefreshLayout;

    //获取数据源
    private int mPage = 1;
    private CommentFaultSearchAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_comment_fault_search);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        setTitle("常见故障");
        setLeftBack();

        initView();
    }

    //子条目操作
    private void initView() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        rvList.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new CommentFaultSearchAdapter();
        mAdapter.bindToRecyclerView(rvList);
        //rvList.setAdapter(mAdapter);
        mAdapter.setOnLoadMoreListener(this);
        //跳转详情
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //问题详情
                Intent intent = new Intent(CommentFaultSearchActivity.this, FaultExplainActivity.class);
                intent.putExtra("QuestionIdZ", mAdapter.getData().get(position).getQuestionId());
                startActivity(intent);
            }
        });
        mSwipeRefreshLayout.setRefreshing(true);
        //模糊查询
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!TextUtils.isEmpty(s)) {
                    searchData(s.toString());
                } else {
                    onRefresh();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        getData();
    }

    //列表展示
    private void getData() {
        QueryEntry queryEntry = new QueryEntry();
        queryEntry.setSize(10);
        queryEntry.setPage(mPage);

        EanfangHttp.post(NewApiService.COMMENT_FAULT_RECORD_LIST)
                .upJson(JsonUtils.obj2String(queryEntry))
                .execute(new EanfangCallback<AskQuestionsListBean>(this, true, AskQuestionsListBean.class) {
                    @Override
                    public void onSuccess(AskQuestionsListBean bean) {

                        if (mPage == 1) {
                            mAdapter.getData().clear();
                            mAdapter.setNewData(bean.getList());
                            mSwipeRefreshLayout.setRefreshing(false);
                            mAdapter.loadMoreComplete();
                            if (bean.getList().size() < 10) {
                                mAdapter.loadMoreEnd();
                            }
                            if (bean.getList().size() > 0) {
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

    //模糊查询
    private void searchData(String qustionSketch) {
        if (!TextUtils.isEmpty(qustionSketch)) {
            QueryEntry queryEntry = new QueryEntry();
            queryEntry.getLike().put("questionSketch", qustionSketch);

            EanfangHttp.post(NewApiService.COMMENT_FAULT_RECORD_LIST)
                    .upJson(JsonUtils.obj2String(queryEntry))
                    .execute(new EanfangCallback<AskQuestionsListBean>(this, false, AskQuestionsListBean.class) {
                        @Override
                        public void onSuccess(AskQuestionsListBean bean) {
                            mAdapter.getData().clear();
                            if (bean.getList().size() > 0) {
                                mTvNoData.setVisibility(View.GONE);
                            } else {
                                mTvNoData.setVisibility(View.VISIBLE);
                            }
                            mAdapter.setNewData(bean.getList());
                            mSwipeRefreshLayout.setRefreshing(false);
                            mAdapter.loadMoreComplete();
                            if (bean.getList().size() < 10) {
                                mAdapter.loadMoreEnd();
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
        } else {
            getData();
        }

    }


    @Override
    public void onRefresh() {
        mPage = 1;//下拉永远第一页
        getData();
    }

    @Override
    public void onLoadMoreRequested() {
        mPage++;
        getData();
    }

}
