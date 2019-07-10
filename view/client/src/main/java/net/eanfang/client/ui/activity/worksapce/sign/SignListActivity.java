package net.eanfang.client.ui.activity.worksapce.sign;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.apiservice.UserApi;
import com.eanfang.biz.model.SignListBean;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.JumpItent;
import com.eanfang.util.QueryEntry;

import net.eanfang.client.R;
import net.eanfang.client.base.ClientApplication;
import net.eanfang.client.ui.activity.worksapce.online.DividerItemDecoration;
import net.eanfang.client.ui.adapter.SignSecondAdapter;

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

public class SignListActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {
    @BindView(R.id.rev_list)
    RecyclerView revList;
    @BindView(R.id.ll_sign_layout)
    LinearLayout llSignLayout;
    @BindView(R.id.ll_footer)
    LinearLayout llFooter;
    @BindView(R.id.tv_sign)
    TextView tvSign;
    @BindView(R.id.tv_nodata)
    TextView tvNodata;
    private String title;
    private int status;

    @BindView(R.id.swipre_fresh)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private SignSecondAdapter signListAdapter;

    private List<SignListBean.ListBean> signListBeanList = new ArrayList<>();
    private int mFirstPosition;

    private int page = 1;

    private static final int RESULT_FILTRATE = 1001;
    private static final int REQUEST_FILTRATE = 1002;

    private QueryEntry mQueryEntry = new QueryEntry();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_sign_list);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
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


        revList.setLayoutManager(new LinearLayoutManager(this));
        revList.addItemDecoration(new DividerItemDecoration(this));
        signListAdapter = new SignSecondAdapter();
        signListAdapter.bindToRecyclerView(revList);

        llSignLayout.setOnClickListener(v -> finishSelf());
        mSwipeRefreshLayout.setOnRefreshListener(this);
        signListAdapter.setOnLoadMoreListener(this, revList);

        setRightTitleOnClickListener((v) -> {
            JumpItent.jump(SignListActivity.this, SignFiltrateActivity.class, REQUEST_FILTRATE);
        });
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

    private void initData() {
        mQueryEntry.setPage(page);
        mQueryEntry.setSize(10);
        mQueryEntry.getEquals().put("createCompanyId", ClientApplication.get().getLoginBean().getAccount().getDefaultUser().getCompanyEntity().getCompanyId() + "");
        if (!mQueryEntry.getEquals().containsKey("createUserId") && !mQueryEntry.getEquals().containsKey("createOrgCode")) {
            mQueryEntry.getEquals().put("createUserId", ClientApplication.get().getUserId() + "");
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
            signListAdapter.getData().clear();
            mQueryEntry = (QueryEntry) data.getSerializableExtra("query_foot");
            initData();
        }
    }

}
