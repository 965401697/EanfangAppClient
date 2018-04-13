package net.eanfang.worker.ui.activity.worksapce;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.application.EanfangApplication;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.MineTaskListBean;
import com.eanfang.swipefresh.SwipyRefreshLayout;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.QueryEntry;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.adapter.TakeTaskAdapter;
import net.eanfang.worker.ui.interfaces.OnDataReceivedListener;
import net.eanfang.worker.ui.widget.TaskPublishDetailView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.eanfang.config.EanfangConst.BOTTOM_REFRESH;
import static com.eanfang.config.EanfangConst.TOP_REFRESH;


/**
 * Created by MrHou
 *
 * @on 2018/1/18  16:03
 * @email houzhongzhou@yeah.net
 * @desc 我要接包
 */

public class TakeTaskListActivity extends BaseActivity implements OnDataReceivedListener, SwipyRefreshLayout.OnRefreshListener {
    @BindView(R.id.tv_no_datas)
    TextView tvNoDatas;
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    private static int page = 1;
    @BindView(R.id.swiprefresh)
    SwipyRefreshLayout swiprefresh;
    private List<MineTaskListBean.ListBean> mDataList;
    private TakeTaskAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_list);
        ButterKnife.bind(this);

        initView();
        initData();
    }

    private void initView() {
        setTitle("我要接包");
        setLeftBack();
        swiprefresh.setOnRefreshListener(this);
    }

    private void initData() {
        QueryEntry queryEntry = new QueryEntry();
        queryEntry.getEquals().put("createCompanyId", EanfangApplication.getApplication().getCompanyId() + "");
        queryEntry.getEquals().put("assigneeCompanyId", EanfangApplication.getApplication().getCompanyId() + "");
        queryEntry.setPage(page);
        queryEntry.setSize(5);

        EanfangHttp.post(NewApiService.ACCEPT_TASK_PUBLISH_LIST)
                .upJson(JsonUtils.obj2String(queryEntry))
                .execute(new EanfangCallback<MineTaskListBean>(this, true, MineTaskListBean.class, (bean) -> {
                            mDataList = bean.getList();
                            onDataReceived();
                        })
                );
    }

    private void initAdapter() {
        adapter = new TakeTaskAdapter(mDataList);
        rvList.setLayoutManager(new LinearLayoutManager(this));
        rvList.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                new TaskPublishDetailView(TakeTaskListActivity.this, true, mDataList.get(position), true).show();
            }
        });
        adapter.setOnItemChildClickListener((adapter, view, position) -> {
            switch (view.getId()) {
                case R.id.tv_select:
                    new TaskPublishDetailView(TakeTaskListActivity.this, true, mDataList.get(position), true).show();
                    break;
                default:
                    break;
            }
        });
        if (mDataList.size() > 0) {
            rvList.setAdapter(adapter);
            tvNoDatas.setVisibility(View.GONE);
            adapter.notifyDataSetChanged();
        } else {
            tvNoDatas.setVisibility(View.VISIBLE);
        }
        adapter.notifyDataSetChanged();
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
    public void onDataReceived() {
        initView();
        initAdapter();
        swiprefresh.setRefreshing(false);
    }
}
