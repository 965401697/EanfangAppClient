package net.eanfang.client.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.application.EanfangApplication;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.WorkspaceInstallBean;
import com.eanfang.swipefresh.SwipyRefreshLayout;
import com.eanfang.ui.base.BaseFragment;
import com.eanfang.util.GetConstDataUtils;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.JumpItent;
import com.eanfang.util.PermKit;
import com.eanfang.util.QueryEntry;

import net.eanfang.client.R;
import net.eanfang.client.ui.activity.worksapce.install.InstallOrderActivity;
import net.eanfang.client.ui.activity.worksapce.install.InstallOrderDetailActivity;
import net.eanfang.client.ui.adapter.WorkspaceInstallAdapter;
import net.eanfang.client.ui.interfaces.OnDataReceivedListener;

import java.util.ArrayList;
import java.util.List;

import static com.eanfang.config.EanfangConst.BOTTOM_REFRESH;
import static com.eanfang.config.EanfangConst.TOP_REFRESH;


/**
 * Created by MrHou
 *
 * @on 2017/12/21  14:12
 * @email houzhongzhou@yeah.net
 * @desc
 */

public class WorkInstallListFragment extends BaseFragment
        implements SwipyRefreshLayout.OnRefreshListener, OnDataReceivedListener {

    TextView tvNoDatas;
    RecyclerView rvList;
    SwipyRefreshLayout swiprefresh;
    private String mTitle;
    private int mType;
    private List<WorkspaceInstallBean.ListBean> mDataList;
    private static int page = 1;
    private WorkspaceInstallAdapter mAdapter;

    public static WorkInstallListFragment getInstance(String title, int type) {
        WorkInstallListFragment sf = new WorkInstallListFragment();
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
        getData();
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
        if (!(getActivity() instanceof InstallOrderActivity)) {
            return;
        }
        mDataList = ((InstallOrderActivity) getActivity()).getWorkspaceInstallBean().getList();
        mAdapter = new WorkspaceInstallAdapter(mDataList);
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            switch (view.getId()) {
                case R.id.tv_look:
                    if (mDataList.get(position).getStatus() == 2) {
                        if (PermKit.get().getInstallFinishPrem()) {
                            finishWork(mDataList, position);
                        }
                    } else {

                        if (PermKit.get().getInstallDetailPrem()) {
                            Bundle bundle = new Bundle();
                            bundle.putLong("orderId", mDataList.get(position).getId());
                            JumpItent.jump(getActivity(), InstallOrderDetailActivity.class, bundle);
                        }
                    }
                    break;
                default:
                    break;
            }
        });
        if (mDataList.size() > 0) {
            rvList.setAdapter(mAdapter);
            tvNoDatas.setVisibility(View.GONE);
            mAdapter.notifyDataSetChanged();
        } else {
            tvNoDatas.setVisibility(View.VISIBLE);
        }
        mAdapter.notifyDataSetChanged();
    }

    private void getData() {
        String status = null;
        if (!"全部".equals(mTitle)) {
            status = GetConstDataUtils.getInstallStatus().indexOf(getmTitle()) + "";
        }

        QueryEntry queryEntry = new QueryEntry();
        if ("0".equals(mType)) {
            queryEntry.getEquals().put("ownerCompanyId", EanfangApplication.getApplication().getCompanyId() + "");
        } else if ("1".equals(mType)) {
            queryEntry.getEquals().put("createUserId", EanfangApplication.getApplication().getUserId() + "");
        }
        if (!mTitle.equals("全部")) {
            queryEntry.getEquals().put("status", status);
        }

        queryEntry.setPage(page);
        queryEntry.setSize(10);

        EanfangHttp.post(NewApiService.GET_WORK_INSTALL_LIST)
                .upJson(JsonUtils.obj2String(queryEntry))
                .execute(new EanfangCallback<WorkspaceInstallBean>(getActivity(), true, WorkspaceInstallBean.class) {
                    @Override
                    public void onSuccess(WorkspaceInstallBean bean) {
                        super.onSuccess(bean);
                        ((InstallOrderActivity) getActivity()).setWorkspaceInstallBean(bean);
                        getActivity().runOnUiThread(() ->
                                onDataReceived()
                        );
                    }

                    @Override
                    public void onNoData(String message) {
                        super.onNoData(message);
                        page--;
                        getActivity().runOnUiThread(() -> {
                            //如果是第一页 没有数据了 则清空 bean
                            if (page < 1) {
                                WorkspaceInstallBean bean = new WorkspaceInstallBean();
                                bean.setList(new ArrayList<>());
                                ((InstallOrderActivity) getActivity()).setWorkspaceInstallBean(bean);
                            } else {
                                showToast("已经到底了");
                            }
                            onDataReceived();
                        });
                    }

                });
    }

    private void finishWork(List<WorkspaceInstallBean.ListBean> mDataList, int position) {
        EanfangHttp.get(NewApiService.INSTALL_FINISH_WORK)
                .params("id", mDataList.get(position).getId())
                .execute(new EanfangCallback<JSONObject>(getActivity(), true, JSONObject.class, (bean) -> {
                    showToast("成功");
                    getData();
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
        initAdapter();
        swiprefresh.setRefreshing(false);
    }
}
