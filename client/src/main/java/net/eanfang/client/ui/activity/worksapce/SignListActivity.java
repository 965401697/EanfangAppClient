package net.eanfang.client.ui.activity.worksapce;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.eanfang.apiservice.UserApi;
import com.eanfang.application.EanfangApplication;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.SignListBean;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.JumpItent;
import com.eanfang.util.QueryEntry;
import com.photopicker.com.util.BGASpaceItemDecoration;

import net.eanfang.client.R;
import net.eanfang.client.ui.adapter.SignListAdapter;

import java.io.Serializable;
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

public class SignListActivity extends BaseActivity implements SignListAdapter.onSecondClickListener, SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {
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

    @BindView(R.id.swipre_fresh)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private SignListAdapter signListAdapter;

    private List<SignListBean> signListBeanList = new ArrayList<>();
    private int mFirstPosition;

    private int page = 1;

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
        title = getIntent().getStringExtra("title");
        status = getIntent().getIntExtra("status", 0);
        if (status == 1) tvSign.setText("签退");


        revList.setLayoutManager(new LinearLayoutManager(this));
        revList.addItemDecoration(new BGASpaceItemDecoration(30));
        signListAdapter = new SignListAdapter(this);
        signListAdapter.bindToRecyclerView(revList);

        llSignLayout.setOnClickListener(v -> finishSelf());
        mSwipeRefreshLayout.setOnRefreshListener(this);

        signListAdapter.setOnLoadMoreListener(this, revList);

    }

    private void initData() {
        QueryEntry queryEntry = new QueryEntry();
        queryEntry.setPage(page);
        queryEntry.setSize(10);
        queryEntry.getEquals().put("createUserId", EanfangApplication.getApplication().getUserId() + "");
        EanfangHttp.post(UserApi.SIGN_LIST)
                .upJson(JsonUtils.obj2String(queryEntry))
                .execute(new EanfangCallback<SignListBean>(this, true, SignListBean.class, true, (bean) -> {
                    signListBeanList = bean;
                    initAdapter();
                    onDataReceived();
                    mSwipeRefreshLayout.setRefreshing(false);
                }));
    }

    private void initAdapter() {

        revList.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                mFirstPosition = position;
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
                setRightGone();
                signListAdapter.getData().clear();
                signListAdapter.notifyDataSetChanged();
            } else {
                signListAdapter.getData().clear();
                signListAdapter.setNewData(signListBeanList);
                setRightVisible();
            }
        } else {
            if (signListBeanList.size() == 0 || signListBeanList == null) {
                showToast("暂无更多数据");
                page = page - 1;
//                messageListAdapter.notifyDataSetChanged();
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

    private void dataOption(int option) {
        switch (option) {
            case TOP_REFRESH:
                //下拉刷新
                page--;
                if (page <= 0) {
                    page = 1;
                }
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
    public void onSecondClick(int position) {
        Bundle bundle = new Bundle();
        bundle.putString("id", signListBeanList.get(mFirstPosition).getList().get(position).getId());
        bundle.putInt("status", status);
        bundle.putSerializable("bean", (Serializable) signListBeanList.get(mFirstPosition).getList().get(position));
        JumpItent.jump(SignListActivity.this, SignListDetailActivity.class, bundle);
    }
}
