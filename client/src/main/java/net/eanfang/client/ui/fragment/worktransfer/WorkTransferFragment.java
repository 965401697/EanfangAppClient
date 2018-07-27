package net.eanfang.client.ui.fragment.worktransfer;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.config.Constant;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.WorkTalkBean;
import com.eanfang.ui.base.BaseFragment;
import com.eanfang.util.GetConstDataUtils;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.QueryEntry;

import net.eanfang.client.R;
import net.eanfang.client.ui.adapter.WorkTalkAdapter;
import net.eanfang.client.ui.fragment.worktalk.WorkTalkListFragment;

import static com.eanfang.config.EanfangConst.BOTTOM_REFRESH;
import static com.eanfang.config.EanfangConst.TOP_REFRESH;

/**
 * @author Guanluocang
 * @date on 2018/7/27  16:23
 * @decision 交接班列表
 */
public class WorkTransferFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    private String mTitle;

    private int page = 1;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView rv_worktalk;

    public static WorkTransferFragment getInstance(String title) {
        WorkTransferFragment workTransferFragment = new WorkTransferFragment();
        workTransferFragment.mTitle = title;
        return workTransferFragment;

    }

    public String getmTitle() {
        return mTitle;
    }

    @Override
    protected int setLayoutResouceId() {
        return R.layout.fragment_work_transfer;
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
        queryEntry.setSize(5);

        EanfangHttp.post(NewApiService.WORK_TALK)
                .upJson(JsonUtils.obj2String(queryEntry))
                .execute(new EanfangCallback<WorkTalkBean>(getActivity(), true, WorkTalkBean.class, (bean) -> {
                            getActivity().runOnUiThread(() -> {
//                                workTalkBeanList = bean.getData().getList();
//                                onDataReceived();
                            });
                        })
                );
    }

    @Override
    protected void initView() {
        swipeRefreshLayout = findViewById(R.id.srl_worktransfer);
        swipeRefreshLayout.setOnRefreshListener(this);
        rv_worktalk = findViewById(R.id.rv_worktransfer);
//        workTalkAdapter = new WorkTalkAdapter();
        rv_worktalk.setLayoutManager(new LinearLayoutManager(getContext()));
//        workTalkAdapter.bindToRecyclerView(rv_worktalk);
    }


    @Override
    protected void setListener() {

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
}
