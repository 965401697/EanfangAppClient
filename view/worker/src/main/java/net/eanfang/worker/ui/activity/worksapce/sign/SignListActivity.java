package net.eanfang.worker.ui.activity.worksapce.sign;

import android.content.Intent;
import android.os.Bundle;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.apiservice.UserApi;
import com.eanfang.application.EanfangApplication;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.SignListBean;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.JumpItent;
import com.eanfang.util.QueryEntry;

import net.eanfang.worker.R;
import net.eanfang.worker.base.WorkerApplication;
import net.eanfang.worker.ui.adapter.SignListAdapter;
import net.eanfang.worker.ui.adapter.SignSecondAdapter;
import net.eanfang.worker.util.WrapContentLinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.eanfang.config.EanfangConst.BOTTOM_REFRESH;
import static com.eanfang.config.EanfangConst.TOP_REFRESH;


/**
 * Created by MrHou
 *
 * @on 2018/1/24  22:18
 * @email houzhongzhou@yeah.net
 * @desc 签到列表
 */

public class SignListActivity extends BaseActivity implements SignListAdapter.onSecondClickListener,
        SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {
    @BindView(R.id.rev_list)
    RecyclerView revList;
    @BindView(R.id.ll_sign_layout)
    LinearLayout llSignLayout;
    @BindView(R.id.ll_footer)
    LinearLayout llFooter;
    @BindView(R.id.tv_sign)
    TextView tvSign;
    private String title;
    private int status;

    private SignSecondAdapter signListAdapter;

    private List<SignListBean.ListBean> signListBeanList = new ArrayList<>();

    @BindView(R.id.swipre_fresh)
    SwipeRefreshLayout mSwipeRefreshLayout;
    private int page = 1;

    private static final int RESULT_FILTRATE = 1001;
    private static final int REQUEST_FILTRATE = 1002;

    private int mFirstPosition;

    private QueryEntry mQueryEntry = new QueryEntry();

    /**
     * 第一次 和 上拉加载的 signDay
     */
    private String mFirstSingDay = "";
    private String mSecondSingDay = "";

    /**
     * 是否是第一页
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_list);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initView() {
        setTitle("足迹");
        setLeftBack();
        setRightTitle("查找");
        title = getIntent().getStringExtra("title");
        status = getIntent().getIntExtra("status", 0);
        if (status == 1) {
            tvSign.setText("签退");
        }

        revList.setLayoutManager(new WrapContentLinearLayoutManager(this));
//        revList.addItemDecoration(new BGASpaceItemDecoration(30));
        signListAdapter = new SignSecondAdapter();
        signListAdapter.bindToRecyclerView(revList);
        llSignLayout.setOnClickListener(v -> finishSelf());

        mSwipeRefreshLayout.setOnRefreshListener(this);
        signListAdapter.setOnLoadMoreListener(this, revList);
        signListAdapter.disableLoadMoreIfNotFullPage();
        revList.setNestedScrollingEnabled(false);

        setRightTitleOnClickListener((v) -> {
            JumpItent.jump(SignListActivity.this, SignFiltrateActivity.class, REQUEST_FILTRATE);
        });
        initAdapter();
    }

    private void initData() {
        mQueryEntry.setPage(page);
        mQueryEntry.setSize(10);
        mQueryEntry.getEquals().put("createCompanyId", WorkerApplication.get().getLoginBean().getAccount().getDefaultUser().getCompanyEntity().getCompanyId() + "");
        if (!mQueryEntry.getEquals().containsKey("createUserId") && !mQueryEntry.getEquals().containsKey("createOrgCode")) {
            mQueryEntry.getEquals().put("createUserId", WorkerApplication.get().getUserId() + "");
        }
        EanfangHttp.post(UserApi.SIGN_LIST)
                .upJson(JsonUtils.obj2String(mQueryEntry))
                .execute(new EanfangCallback<SignListBean>(this, true, SignListBean.class) {
                    @Override
                    public void onSuccess(SignListBean bean) {
                        if (page == 1) {
                            signListAdapter.getData().clear();
                            signListAdapter.setNewData(bean.getList());
                            mSwipeRefreshLayout.setRefreshing(false);
                            signListAdapter.loadMoreComplete();
                            revList.stopScroll();
                            if (bean.getList().size() < 10) {
                                signListAdapter.loadMoreEnd();
                                //释放对象
                                mQueryEntry = null;
                            }

                        } else {
                            signListAdapter.addData(bean.getList());
                            signListAdapter.loadMoreComplete();
                            revList.stopScroll();
                            if (bean.getList().size() < 10) {
                                signListAdapter.loadMoreEnd();
                            }
                        }
                    }

                    @Override
                    public void onNoData(String message) {
                        mSwipeRefreshLayout.setRefreshing(false);
                        signListAdapter.loadMoreEnd();//没有数据了
                    }

                    @Override
                    public void onCommitAgain() {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                });
    }


    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {
        dataOption(TOP_REFRESH);
    }


    /**
     * 加载更多
     */
    @Override
    public void onLoadMoreRequested() {
        dataOption(BOTTOM_REFRESH);
    }

    public void onDataReceived() {
        if (page == 1) {
            if (signListBeanList.size() == 0 || signListBeanList == null) {
                showToast("暂无数据");
                signListAdapter.getData().clear();
                signListAdapter.notifyDataSetChanged();
            } else {
                signListAdapter.getData().clear();
                signListAdapter.setNewData(signListBeanList);
                signListAdapter.notifyDataSetChanged();
                if (signListBeanList.size() < 10) {
                    signListAdapter.loadMoreEnd();
                }
            }
        } else {
            if (signListBeanList.size() == 0 || signListBeanList == null) {
                showToast("暂无更多数据");
                page = page - 1;
                signListAdapter.loadMoreEnd();
            } else {
                signListAdapter.addData(signListBeanList);
                signListAdapter.loadMoreComplete();
                if (signListBeanList.size() < 10) {
                    signListAdapter.loadMoreEnd();
                }
            }
        }
    }

    private void initAdapter() {
        signListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("id", signListAdapter.getData().get(mFirstPosition).getId());
                bundle.putInt("status", signListAdapter.getData().get(mFirstPosition).getStatus());
                bundle.putSerializable("bean", signListAdapter.getData().get(position));
                JumpItent.jump(SignListActivity.this, SignListDetailActivity.class, bundle);
            }
        });
    }

    private void dataOption(int option) {
        switch (option) {
            case TOP_REFRESH:
                //下拉刷新
                mQueryEntry = new QueryEntry();
                page = 1;
                initData();
                break;
            case BOTTOM_REFRESH:
                //上拉加载更多
                page++;
                initData();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        if (requestCode == REQUEST_FILTRATE && resultCode == RESULT_FILTRATE) {
            page = 1;
            signListAdapter.getMTimeMap().clear();
            signListAdapter.getData().clear();
            mQueryEntry = (QueryEntry) data.getSerializableExtra("query_foot");
            initData();
        }
    }

    @Override
    public void onSecondClick(int position) {

    }
}
