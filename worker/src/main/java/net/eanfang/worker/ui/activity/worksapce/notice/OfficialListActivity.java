package net.eanfang.worker.ui.activity.worksapce.notice;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.OfficialListBean;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.QueryEntry;
import com.yaf.base.entity.NoticePushEntity;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.adapter.OfficialAdapter;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OfficialListActivity extends BaseWorkerActivity implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.tv_no_datas)
    TextView mTvNoData;
    @BindView(R.id.swipre_fresh)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.et_search)
    EditText etSearch;


    private int mPage = 1;
    private OfficialAdapter mAdapter;
    private boolean read = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_official_list);
        ButterKnife.bind(this);
        setTitle("官方通知");
        setLeftBack(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (read)
                    setResult(RESULT_OK);
                finishSelf();
            }
        });
        mPage = 1;

        initView();
    }


    private void initView() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        rvList.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new OfficialAdapter();
        mAdapter.bindToRecyclerView(rvList);
        mAdapter.setOnLoadMoreListener(this);

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                read = true;
                NoticePushEntity entity = mAdapter.getData().get(position);

                Intent intent = new Intent(OfficialListActivity.this, OfficialDetailActivity.class);
                intent.putExtra("title", entity.getNoticeTitle());
                intent.putExtra("url", entity.getUrl());
                intent.putExtra("id", entity.getId());
                startActivity(intent);
                //刷新状态
                entity.setStatus(1);
                adapter.notifyItemChanged(position);
            }
        });
        mSwipeRefreshLayout.setRefreshing(true);
        getData();

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s)) {
                    searchData(s.toString().trim());
                } else {
                    //清空搜索的数据
                    mAdapter.getData().clear();

                    refresh();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
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
        EanfangHttp.post(NewApiService.GET_OFFICIAL_MSG)
                .upJson(JsonUtils.obj2String(queryEntry))
                .execute(new EanfangCallback<OfficialListBean>(this, true, OfficialListBean.class) {
                    @Override
                    public void onSuccess(OfficialListBean bean) {

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


    private void searchData(String title) {
        QueryEntry queryEntry = new QueryEntry();
        queryEntry.getLike().put("noticeTitle", title);

        EanfangHttp.post(NewApiService.GET_OFFICIAL_MSG)
                .upJson(JsonUtils.obj2String(queryEntry))
                .execute(new EanfangCallback<OfficialListBean>(this, false, OfficialListBean.class) {
                    @Override
                    public void onSuccess(OfficialListBean bean) {

                        if (bean.getList().size() > 0) {
                            mAdapter.getData().clear();
                            mAdapter.setNewData(bean.getList());
                            mTvNoData.setVisibility(View.GONE);
                            mAdapter.loadMoreEnd();
                        } else {
                            mAdapter.getData().clear();
                            mAdapter.notifyDataSetChanged();
                            mTvNoData.setVisibility(View.VISIBLE);
                        }

                    }


                });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) { // 监控/拦截/屏蔽返回键
            if (read)
                setResult(RESULT_OK);
        }
        return super.onKeyDown(keyCode, event);
    }
}

