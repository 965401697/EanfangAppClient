package net.eanfang.worker.ui.activity.worksapce;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.application.EanfangApplication;
import com.eanfang.config.Constant;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.WorkspaceInstallBean;
import com.eanfang.swipefresh.SwipyRefreshLayout;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.JumpItent;
import com.eanfang.util.QueryEntry;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.adapter.WorkspaceInstallAdapter;
import net.eanfang.worker.ui.interfaces.OnDataReceivedListener;
import net.eanfang.worker.ui.widget.InstallCtrlItemView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.eanfang.config.EanfangConst.BOTTOM_REFRESH;
import static com.eanfang.config.EanfangConst.TOP_REFRESH;


/**
 * Created by MrHou
 *
 * @on 2018/1/18  21:51
 * @email houzhongzhou@yeah.net
 * @desc 报装管控
 */

public class InstallOrderListActivity extends BaseActivity implements SwipyRefreshLayout.OnRefreshListener, OnDataReceivedListener {
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.swiprefresh)
    SwipyRefreshLayout swiprefresh;
    private String mTitle;
    private int mType;
    private static int page = 1;
    private WorkspaceInstallAdapter mAdapter;
    private WorkspaceInstallBean workspaceInstallBean;
    private List<WorkspaceInstallBean.ListBean> mDataList;

    public WorkspaceInstallBean getWorkspaceInstallBean() {
        return workspaceInstallBean;
    }

    public synchronized void setWorkspaceInstallBean(WorkspaceInstallBean workspaceInstallBean) {
        this.workspaceInstallBean = workspaceInstallBean;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_list);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mTitle = getIntent().getStringExtra("title");
        mType = getIntent().getIntExtra("type", 0);
        swiprefresh.setOnRefreshListener(this);
        setTitle(mTitle);
        setLeftBack();
        getData();
    }

    private void getData() {
        QueryEntry queryEntry = new QueryEntry();
        if (mType == 0) {
            queryEntry.getEquals().put(Constant.ASSIGNEE_USER_ID, EanfangApplication.getApplication().getUserId() + "");
        } else if (mType == 1) {
            queryEntry.getEquals().put(Constant.ASSIGNEE_COMPANY_ID, EanfangApplication.getApplication().getCompanyId() + "");
        }

        queryEntry.setPage(page);
        queryEntry.setSize(10);

        EanfangHttp.post(NewApiService.GET_WORK_INSTALL_LIST)
                .upJson(JsonUtils.obj2String(queryEntry))
                .execute(new EanfangCallback<WorkspaceInstallBean>(this, true, WorkspaceInstallBean.class) {
                    @Override
                    public void onSuccess(WorkspaceInstallBean bean) {
                        super.onSuccess(bean);
                        workspaceInstallBean = bean;
                        setWorkspaceInstallBean(bean);
                        onDataReceived();
                        EanfangApplication.get().set(WorkspaceInstallBean.class.getName(), bean);
                    }

                    @Override
                    public void onNoData(String message) {
                        super.onNoData(message);
                        page--;
                        runOnUiThread(() -> {
                            //如果是第一页 没有数据了 则清空 bean
                            if (page < 1) {
                                WorkspaceInstallBean bean = new WorkspaceInstallBean();
                                bean.setList(new ArrayList<>());
                                setWorkspaceInstallBean(bean);
                            } else {
                                showToast("已经到底了");
                            }
                            onDataReceived();
                        });
                    }

                });

    }

    private void initAdapter() {
        mDataList = getWorkspaceInstallBean().getList();
        mAdapter = new WorkspaceInstallAdapter(mDataList);
        rvList.setLayoutManager(new LinearLayoutManager(this));

        rvList.setAdapter(mAdapter);
        rvList.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                new InstallCtrlItemView(InstallOrderListActivity.this, true, mDataList.get(position).getId()).show();
            }
        });
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
//        initView();
        initAdapter();
        swiprefresh.setRefreshing(false);
    }
}
