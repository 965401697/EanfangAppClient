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
import net.eanfang.client.ui.activity.worksapce.WorkReportListActivity;
import net.eanfang.client.ui.adapter.WorkReportListAdapter;
import net.eanfang.client.ui.base.BaseFragment;
import net.eanfang.client.ui.interfaces.OnDataReceivedListener;
import net.eanfang.client.ui.model.WorkReportListBean;
import net.eanfang.client.ui.widget.WorkReportInfoView;
import net.eanfang.client.util.GetConstDataUtils;
import net.eanfang.client.util.JsonUtils;
import net.eanfang.client.util.QueryEntry;

import java.util.List;

import static net.eanfang.client.config.EanfangConst.BOTTOM_REFRESH;
import static net.eanfang.client.config.EanfangConst.TOP_REFRESH;


/**
 * Created by Mr.hou
 *
 * @on 2017/9/5  9:29
 * @email houzhongzhou@yeah.net
 * @desc
 */

public class WorkReportListFragment extends BaseFragment implements
        OnDataReceivedListener, SwipyRefreshLayout.OnRefreshListener {
    TextView tvNoDatas;
    RecyclerView rvList;
    SwipyRefreshLayout swiprefresh;
    private List<WorkReportListBean.ListBean> mDataList;
    private String mTitle;
    private String mType;
    private WorkReportListAdapter mAdapter;

    private static int page = 1;

    public static WorkReportListFragment getInstance(String title, String type) {
        WorkReportListFragment sf = new WorkReportListFragment();
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
        return R.layout.fragment_work_report_list;
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

    private void initAdapter() {
        if (getActivity() == null) {
            return;
        }
        if (!(getActivity() instanceof WorkReportListActivity)) {
            return;
        }
        if (((WorkReportListActivity) getActivity()).getWorkReportListBean() == null) {
            return;
        }
        mDataList = ((WorkReportListActivity) getActivity()).getWorkReportListBean().getList();
        mAdapter = new WorkReportListAdapter(mDataList);
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

    OnItemClickListener onItemClickListener = new OnItemClickListener() {
        @Override
        public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
            if (mDataList.get(position).getStatus() == EanfangConst.WORK_TASK_STATUS_UNREAD) {
//                if (EanfangApplication.getApplication().getUserId().equals(mDataList.get(position).getAssigneeUserId())) {
                getFirstLookData(((WorkReportListActivity) getActivity()).getWorkReportListBean(), position);
//                }
            }
            new WorkReportInfoView(getActivity(), true, mDataList.get(position).getId()).show();
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        getData();
    }

    /**
     * 首次阅读，更新状态
     */
    private void getFirstLookData(WorkReportListBean beans, int position) {
        EanfangHttp.get(NewApiService.WORK_REPORT_FIRST_READ)
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

    /**
     * 获取工作任务列表
     */
    private void getData() {
        String status = null;
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

        EanfangHttp.post(NewApiService.GET_WORK_REPORT_LIST)
                .upJson(JsonUtils.obj2String(queryEntry))
                .execute(new EanfangCallback<WorkReportListBean>(getActivity(), true, WorkReportListBean.class, (bean) -> {
                            getActivity().runOnUiThread(() -> {
                                onDataReceived();
                            });
                        })
//                {
//                    @Override
//                    public void onSuccess(final WorkReportListBean bean) {
//                        ((WorkReportListActivity) getActivity()).setWorkReportListBean(bean);
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
//                                    WorkReportListBean bean = new WorkReportListBean();
//                                    bean.setAll(new ArrayList<WorkReportListBean.AllBean>());
//                                    ((WorkReportListActivity) getActivity()).setWorkReportListBean(bean);
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


    @Override
    public void onDataReceived() {
        initView();
        initAdapter();
        swiprefresh.setRefreshing(false);
    }
}
