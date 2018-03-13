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
import com.eanfang.util.QueryEntry;

import net.eanfang.client.R;
import net.eanfang.client.ui.adapter.WorkspaceInstallAdapter;
import net.eanfang.client.ui.interfaces.OnDataReceivedListener;
import net.eanfang.client.ui.widget.InstallCtrlItemView;

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
        getData(1);
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

    private void initAdapter(List<WorkspaceInstallBean.ListBean> mDataList) {
        mAdapter = new WorkspaceInstallAdapter(mDataList);
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            switch (view.getId()) {
                case R.id.tv_look:
                    if (mDataList.get(position).getStatus() == 2) {
                        finishWork(mDataList, position);
                    } else {
                        new InstallCtrlItemView(getActivity(), mDataList.get(position).getId()).show();
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


    @Override
    public void onResume() {
        super.onResume();

    }


    private void getData(int page) {
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
        queryEntry.setSize(5);

        EanfangHttp.post(NewApiService.GET_WORK_INSTALL_LIST)
                .upJson(JsonUtils.obj2String(queryEntry))
                .execute(new EanfangCallback<WorkspaceInstallBean>(getActivity(), true, WorkspaceInstallBean.class, (bean) -> {
                            getActivity().runOnUiThread(() -> {
                                initAdapter(bean.getList());
                                onDataReceived();
                            });
                        })
                );
    }

    private void finishWork(List<WorkspaceInstallBean.ListBean> mDataList, int position) {
        EanfangHttp.get(NewApiService.INSTALL_FINISH_WORK)
                .params("id", mDataList.get(position).getId())
                .execute(new EanfangCallback<JSONObject>(getActivity(), false, JSONObject.class, (bean) -> {
                    showToast("成功");
                    getData(page);
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
        initView();
//        initAdapter();
        swiprefresh.setRefreshing(false);
    }
}
