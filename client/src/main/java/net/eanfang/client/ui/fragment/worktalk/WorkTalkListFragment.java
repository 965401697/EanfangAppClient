package net.eanfang.client.ui.fragment.worktalk;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.config.Constant;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.WorkTalkListBean;
import com.eanfang.ui.base.BaseFragment;
import com.eanfang.util.GetConstDataUtils;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.QueryEntry;

import net.eanfang.client.R;
import net.eanfang.client.ui.adapter.WorkTalkAdapter;

import java.util.ArrayList;
import java.util.List;

import static com.eanfang.config.EanfangConst.BOTTOM_REFRESH;
import static com.eanfang.config.EanfangConst.TOP_REFRESH;

/**
 * @author Guanluocang
 * @date on 2018/7/11  19:08
 * @decision 面谈员工列表
 */
public class WorkTalkListFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    private String mTitle;
    private String mType;

    private int page = 1;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView rv_worktalk;
    private List<WorkTalkListBean.DataBean.ListBean> workTalkBeanList = new ArrayList<>();
    private WorkTalkAdapter workTalkAdapter;

    public static WorkTalkListFragment getInstance(String title, int type) {
        WorkTalkListFragment workTalkListFragment = new WorkTalkListFragment();
        workTalkListFragment.mTitle = title;
        workTalkListFragment.mType = String.valueOf(type);
        return workTalkListFragment;

    }

    public String getmTitle() {
        return mTitle;
    }

    @Override
    protected int setLayoutResouceId() {
        return R.layout.fragment_work_talk_list;
    }

    @Override
    protected void initData(Bundle arguments) {
        getData();
    }

    /**
     * 获取数据
     */
    public void getData() {
        QueryEntry queryEntry = new QueryEntry();
        if (!Constant.ALL.equals(mTitle)) {
            String status = GetConstDataUtils.getWorkTalkStatus().indexOf(getmTitle()) + "";
            queryEntry.getEquals().put(Constant.STATUS, status);
        }
        queryEntry.setPage(page);
        queryEntry.setSize(10);

        EanfangHttp.post(NewApiService.WORK_TALK)
                .upJson(JsonUtils.obj2String(queryEntry))
                .execute(new EanfangCallback<WorkTalkListBean>(getActivity(), true, WorkTalkListBean.class, (bean) -> {
                            getActivity().runOnUiThread(() -> {
                                workTalkBeanList = bean.getData().getList();
                                onDataReceived();
                            });
                        })
                );
    }

    @Override
    protected void initView() {
        swipeRefreshLayout = findViewById(R.id.srl_worktalk);
        swipeRefreshLayout.setOnRefreshListener(this);
        rv_worktalk = findViewById(R.id.rv_worktalk);
        workTalkAdapter = new WorkTalkAdapter();
        rv_worktalk.setLayoutManager(new LinearLayoutManager(getContext()));
        workTalkAdapter.bindToRecyclerView(rv_worktalk);
    }

    @Override
    protected void setListener() {
        workTalkAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                WorkTalkListBean.DataBean.ListBean workTalkBean = workTalkAdapter.getData().get(position);
                switch (view.getId()) {
                    // 查看详情
                    case R.id.tv_seedetail:
                        break;
                    //联系汇报人
                    case R.id.tv_contact:
                        break;
                }
            }
        });
    }

    private void dataOption(int option) {
        switch (option) {
            case TOP_REFRESH:
                //下拉刷新
                page--;
                if (page <= 0) {
                    page = 1;
                }
                getData();
                break;
            case BOTTOM_REFRESH:
                //上拉加载更多
                page++;
                getData();
                break;
            default:
                break;
        }
    }

    @Override
    public void onLoadMoreRequested() {
        dataOption(BOTTOM_REFRESH);
    }

    @Override
    public void onRefresh() {
        dataOption(TOP_REFRESH);
    }

    public void onDataReceived() {
        if (page == 1) {
            if (workTalkBeanList.size() == 0 || workTalkBeanList == null) {
                showToast("暂无数据");
                workTalkAdapter.getData().clear();
                workTalkAdapter.notifyDataSetChanged();
            } else {
                workTalkAdapter.getData().clear();
                workTalkAdapter.setNewData(workTalkBeanList);
            }
        } else {
            if (workTalkBeanList.size() == 0 || workTalkBeanList == null) {
                showToast("暂无更多数据");
                page = page - 1;
//                messageListAdapter.notifyDataSetChanged();
                workTalkAdapter.loadMoreEnd();
            } else {
                workTalkAdapter.addData(workTalkBeanList);
                workTalkAdapter.loadMoreComplete();
                if (workTalkBeanList.size() < 5) {
                    workTalkAdapter.loadMoreEnd();
                }
            }
        }
    }
}
