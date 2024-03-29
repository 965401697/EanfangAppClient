package net.eanfang.client.ui.fragment.evaluate;

import android.os.Bundle;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.apiservice.UserApi;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.biz.model.bean.EvaluateBean;
import com.eanfang.ui.base.BaseFragment;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.JumpItent;
import com.eanfang.biz.model.QueryEntry;

import net.eanfang.client.R;
import net.eanfang.client.base.ClientApplication;
import net.eanfang.client.ui.activity.worksapce.EvaluateShowActivity;
import net.eanfang.client.ui.adapter.EvaluateAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import static com.eanfang.config.EanfangConst.BOTTOM_REFRESH;
import static com.eanfang.config.EanfangConst.TOP_REFRESH;


/**
 * 客户端收到的评价
 * Created by Administrator on 2017/6/22.
 */

public class EvaluateFragment extends BaseFragment implements
        SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    public static EvaluateFragment getInstance() {
        EvaluateFragment sf = new EvaluateFragment();
        return sf;
    }

    private int page = 1;
    private List<EvaluateBean.ListBean> mDataList = new ArrayList<>();
    private EvaluateAdapter evaluateAdapter;

    @Override
    protected int setLayoutResouceId() {
        return R.layout.activity_evaluate;
    }

    @Override
    protected void initData(Bundle arguments) {


    }

    @Override
    protected void onLazyLoad() {
        super.onLazyLoad();
        QueryEntry queryEntry = new QueryEntry();
        queryEntry.getEquals().put("ownerId", ClientApplication.get().getUserId() + "");
        queryEntry.setPage(page);
        queryEntry.setSize(10);
        EanfangHttp.post(UserApi.GET_CILENT_EVALUATE_LIST)
                .upJson(JsonUtils.obj2String(queryEntry))
                .execute(new EanfangCallback<EvaluateBean>(getActivity(), true, EvaluateBean.class, (bean) -> {
                    if (bean != null) {
                        mDataList = bean.getList();
                        initAdapter();
                        onDataReceived();
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }));
    }

    @Override
    protected void initView() {
        mRecyclerView = findViewById(R.id.rv_list);
        mSwipeRefreshLayout = findViewById(R.id.swipre_fresh);
        evaluateAdapter = new EvaluateAdapter("receive");
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        evaluateAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        evaluateAdapter.setOnLoadMoreListener(this, mRecyclerView);
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    private void initAdapter() {

        evaluateAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("bean", evaluateAdapter.getData().get(position));
                bundle.putSerializable("status", "rec");
                JumpItent.jump(getActivity(), EvaluateShowActivity.class, bundle);
            }
        });
        mRecyclerView.setAdapter(evaluateAdapter);
    }

    @Override
    protected void setListener() {

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
            if (mDataList.size() == 0 || mDataList == null) {
                showToast("暂无数据");
                evaluateAdapter.getData().clear();
                evaluateAdapter.notifyDataSetChanged();
            } else {
                evaluateAdapter.getData().clear();
                evaluateAdapter.setNewData(mDataList);
                if (mDataList.size() < 10) {
                    evaluateAdapter.loadMoreEnd();
                }
            }
        } else {
            if (mDataList.size() == 0 || mDataList == null) {
                showToast("暂无更多数据");
                page = page - 1;
                evaluateAdapter.loadMoreEnd();
            } else {
                evaluateAdapter.addData(mDataList);
                evaluateAdapter.loadMoreComplete();
                if (mDataList.size() < 10) {
                    evaluateAdapter.loadMoreEnd();
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
                onLazyLoad();
                break;
            case BOTTOM_REFRESH:
                //上拉加载更多
                page++;
                onLazyLoad();
                break;
            default:
                break;
        }
    }

}
