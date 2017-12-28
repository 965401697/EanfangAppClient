package net.eanfang.client.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.eanfang.swipefresh.SwipyRefreshLayout;

import net.eanfang.client.R;
import net.eanfang.client.application.EanfangApplication;
import net.eanfang.client.config.EanfangConst;
import net.eanfang.client.network.apiservice.NewApiService;
import net.eanfang.client.network.request.EanfangCallback;
import net.eanfang.client.network.request.EanfangHttp;
import net.eanfang.client.ui.activity.worksapce.WorkTaskListActivity;
import net.eanfang.client.ui.adapter.WorkTaskListAdapter;
import net.eanfang.client.ui.base.BaseFragment;
import net.eanfang.client.ui.interfaces.OnDataReceivedListener;
import net.eanfang.client.ui.model.WorkTaskListBean;
import net.eanfang.client.ui.widget.WorkTaskInfoView;
import net.eanfang.client.util.GetConstDataUtils;
import net.eanfang.client.util.JsonUtils;
import net.eanfang.client.util.QueryEntry;

import java.util.List;

import static net.eanfang.client.config.EanfangConst.BOTTOM_REFRESH;
import static net.eanfang.client.config.EanfangConst.TOP_REFRESH;

/**
 * Created by MrHou
 *
 * @on 2017/11/22  17:37
 * @email houzhongzhou@yeah.net
 * @desc
 */

public class WorkTaskListFragment extends BaseFragment
        implements SwipyRefreshLayout.OnRefreshListener, OnDataReceivedListener {

    TextView tvNoDatas;
    RecyclerView rvList;
    SwipyRefreshLayout swiprefresh;
    private List<WorkTaskListBean.ListBean> mDataList;
    private String mTitle;
    private String mType;

    private static int page = 1;
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
    }

    @Override
    protected void initView() {
        tvNoDatas = (TextView) findViewById(R.id.tv_no_datas);
        swiprefresh = (SwipyRefreshLayout) findViewById(R.id.swiprefresh);
        swiprefresh.setOnRefreshListener(this);
        rvList = (RecyclerView) findViewById(R.id.rv_list);
        rvList.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    protected void setListener() {
    }

    private void initAdapter(List<WorkTaskListBean.ListBean> mDataList) {
        if (getActivity() == null) {
            return;
        }
        if (!(getActivity() instanceof WorkTaskListActivity)) {
            return;
        }
        if (((WorkTaskListActivity) getActivity()).getWorkTaskListBean() == null) {
            return;
        }
//        mDataList = ((WorkTaskListActivity) getActivity()).getWorkTaskListBean().getList();
        OnItemClickListener onItemClickListener = new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (mDataList.get(position).getStatus() == (EanfangConst.WORK_TASK_STATUS_UNREAD)) {
//                    if (EanfangApplication.getApplication().getUserId().equals(mDataList.get(position).getAssigneeUserId())) {
                    getFirstLookData(((WorkTaskListActivity) getActivity()).getWorkTaskListBean(), position);
//                    }
                }
                new WorkTaskInfoView(getActivity(), true, mDataList.get(position).getId()).show();
            }
        };

        mAdapter = new WorkTaskListAdapter(mDataList);
        rvList.addOnItemTouchListener(onItemClickListener);
        if (mDataList.size() > 0) {
            rvList.setAdapter(mAdapter);
            tvNoDatas.setVisibility(View.GONE);
            mAdapter.notifyDataSetChanged();
        } else {
            tvNoDatas.setVisibility(View.VISIBLE);
        }
        mAdapter.notifyDataSetChanged();
    }


    @Override
    public void onResume() {
        super.onResume();
        getData();
    }

    /**
     * 获取工作任务列表
     */
    private void getData() {
        String status = "";
        if (!mTitle.equals("全部")) {
            status = GetConstDataUtils.getTaskReadStatusByStr(getmTitle());
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
        queryEntry.setSize(5);

        EanfangHttp.post(NewApiService.GET_WORK_TASK_LIST)
                .upJson(JsonUtils.obj2String(queryEntry))
                .execute(new EanfangCallback<WorkTaskListBean>(getActivity(), true, WorkTaskListBean.class, (bean) -> {
                            getActivity().runOnUiThread(() -> {
                                initAdapter(bean.getList());
                                onDataReceived();
                            });
                        })
//                {
//                    @Override
//                    public void onSuccess(final WorkTaskListBean bean) {
//                        ((WorkTaskListActivity) getActivity()).setWorkTaskListBean(bean);
//                        getActivity().runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                onDataReceived();
//                            }
//                        });
//
//                    }
//
//                    @Override
//                    public void onError(String message) {
//                    }
//
//                    @Override
//                    public void onNoData(String message) {
//                        swiprefresh.setRefreshing(false);
//                        page--;
//
//                        getActivity().runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                //如果是第一页 没有数据了 则清空 bean
//                                if (page < 1) {
//                                    WorkTaskListBean bean = new WorkTaskListBean();
//                                    bean.setAll(new ArrayList<WorkTaskListBean.AllBean>());
//                                    ((WorkTaskListActivity) getActivity()).setWorkTaskListBean(bean);
//                                } else {
//                                    showToast("已经到底了");
//                                }
//                                onDataReceived();
//                            }
//                        });
//                    }
//                }
                );
    }

    /**
     * 首次阅读，更新状态
     */
    private void getFirstLookData(WorkTaskListBean beans, int position) {
        EanfangHttp.get(NewApiService.WORK_TASK_FIRST_READ)
                .params("id", beans.getList().get(position).getId())
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
    public void onDataReceived() {
        initView();
//        initAdapter();
        swiprefresh.setRefreshing(false);
    }
}
