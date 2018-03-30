package net.eanfang.worker.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.application.EanfangApplication;
import com.eanfang.config.EanfangConst;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.WorkTaskListBean;
import com.eanfang.swipefresh.SwipyRefreshLayout;
import com.eanfang.ui.base.BaseFragment;
import com.eanfang.util.GetConstDataUtils;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.QueryEntry;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.adapter.WorkTaskListAdapter;
import net.eanfang.worker.ui.interfaces.OnDataReceivedListener;
import net.eanfang.worker.ui.widget.WorkTaskInfoView;

import java.util.List;

import static com.eanfang.config.EanfangConst.BOTTOM_REFRESH;
import static com.eanfang.config.EanfangConst.TOP_REFRESH;


/**
 * Created by MrHou
 *
 * @on 2017/11/22  17:37
 * @email houzhongzhou@yeah.net
 * @desc
 */

public class WorkTaskListFragment extends BaseFragment
        implements SwipyRefreshLayout.OnRefreshListener, OnDataReceivedListener {

    private static int page = 1;
    RecyclerView rvList;
    SwipyRefreshLayout swiprefresh;
    private String mTitle;
    private String mType;
    private WorkTaskListAdapter mAdapter;

    public static WorkTaskListFragment getInstance(String title, String type) {
        WorkTaskListFragment sf = new WorkTaskListFragment();
        sf.mTitle = title;
        page = 1;
        sf.mType = type;
        return sf;

    }

    public String getmTitle() {
        return mTitle;
    }

    @Override
    protected int setLayoutResouceId() {
        return R.layout.fragment_work_task_list;
    }

    @Override
    protected void initData(Bundle arguments) {
        getData(page);
    }

    @Override
    protected void initView() {
        swiprefresh = (SwipyRefreshLayout) findViewById(R.id.swiprefresh);
        swiprefresh.setOnRefreshListener(this);
        rvList = (RecyclerView) findViewById(R.id.rv_list);
        rvList.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    protected void setListener() {
    }

    private void initAdapter(List<WorkTaskListBean.ListBean> mDataList) {

        OnItemClickListener onItemClickListener = new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (mDataList.get(position).getStatus() == (EanfangConst.WORK_TASK_STATUS_UNREAD)) {
                    getFirstLookData(mDataList, position);
                }
                new WorkTaskInfoView(getActivity(), true, mDataList.get(position).getId()).show();
            }
        };

        mAdapter = new WorkTaskListAdapter(mDataList);
        rvList.addOnItemTouchListener(onItemClickListener);
        rvList.setAdapter(mAdapter);

    }


    @Override
    public void onResume() {
        super.onResume();
    }

    /**
     * 获取工作任务列表
     */
    private void getData(int page) {
        String status = "";
        if (!mTitle.equals("全部")) {
            status = GetConstDataUtils.getWorkTaskStatus().indexOf(getmTitle()) + "";
        }

        QueryEntry queryEntry = new QueryEntry();
        if ("0".equals(mType)) {
            queryEntry.getEquals().put("createCompanyId", EanfangApplication.getApplication().getCompanyId() + "");
        } else if ("1".equals(mType)) {
            queryEntry.getEquals().put("createUserId", EanfangApplication.getApplication().getUserId() + "");
        } else if ("2".equals(mType)) {
            queryEntry.getEquals().put("assigneeUserId", EanfangApplication.getApplication().getUserId() + "");
        }
        if (!mTitle.equals("全部")) {
            queryEntry.getEquals().put("status", status);
        }

        queryEntry.setPage(page);
        queryEntry.setSize(50);

        EanfangHttp.post(NewApiService.GET_WORK_TASK_LIST)
                .upJson(JsonUtils.obj2String(queryEntry))
                .execute(new EanfangCallback<WorkTaskListBean>(getActivity(), true, WorkTaskListBean.class, (bean) -> {
                            getActivity().runOnUiThread(() -> {
                                initAdapter(bean.getList());
                                onDataReceived();
                            });
                        })
                );
    }

    /**
     * 首次阅读，更新状态
     */
    private void getFirstLookData(List<WorkTaskListBean.ListBean> mDataList, int position) {
        EanfangHttp.get(NewApiService.WORK_TASK_FIRST_READ)
                .params("id", mDataList.get(position).getId())
                .execute(new EanfangCallback(getActivity(), true, JSONObject.class, (bean) -> {

                }));
    }

    /**
     * 刷新
     */
    @Override
    public void onRefresh(int index) {
        dataOption(TOP_REFRESH);

    }

    @Override
    public void onLoad(int index) {
        dataOption(BOTTOM_REFRESH);
    }

    private void dataOption(int option) {
        switch (option) {
            case TOP_REFRESH:
                //下拉刷新
                page--;
                if (page <= 0) {
                    page = 1;
                }
                getData(page);
                break;
            case BOTTOM_REFRESH:
                //上拉加载更多
                page++;
                getData(page);
                break;
            default:
                break;
        }
    }


    @Override
    public void onDataReceived() {
//        initView();
//        initAdapter();
        swiprefresh.setRefreshing(false);
    }
}
