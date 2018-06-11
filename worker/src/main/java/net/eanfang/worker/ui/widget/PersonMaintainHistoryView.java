package net.eanfang.worker.ui.widget;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.eanfang.apiservice.NewApiService;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.MainHistoryBean;
import com.eanfang.swipefresh.SwipyRefreshLayout;
import com.eanfang.ui.base.BaseDialog;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.QueryEntry;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.activity.worksapce.MaintenanceHistoryDetailActivity;
import net.eanfang.worker.ui.adapter.MainAdapter;
import net.eanfang.worker.ui.interfaces.OnDataReceivedListener;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.eanfang.config.EanfangConst.BOTTOM_REFRESH;
import static com.eanfang.config.EanfangConst.TOP_REFRESH;

/**
 * Created by MrHou
 *
 * @on 2017/11/28  16:49
 * @email houzhongzhou@yeah.net
 * @desc
 */

public class PersonMaintainHistoryView extends BaseDialog implements SwipyRefreshLayout.OnRefreshListener, OnDataReceivedListener {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rv_list)
    RecyclerView recyclerView;
    @BindView(R.id.swipre_fresh)
    SwipyRefreshLayout swiprefresh;
    @BindView(R.id.iv_left)
    ImageView ivLeft;
    private Activity mContext;
    private MainAdapter orderAdapter;
    private Long id;
    private int type;
    private static int page = 1;

    public PersonMaintainHistoryView(Activity context, boolean isfull, Long id, int type) {
        super(context, isfull);
        this.mContext = context;
        this.id = id;
        this.type = type;
    }

    @Override
    protected void initCustomView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main_list);
        ButterKnife.bind(this);
        initView();

    }

    private void initView() {
        tvTitle.setText("历史记录");
        swiprefresh.setOnRefreshListener(this);
        ivLeft.setOnClickListener(v -> dismiss());
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        queryHistory();
    }

    private void queryHistory() {
        QueryEntry queryEntry = new QueryEntry();
        if (type == 0) {
            queryEntry.getEquals().put("createUserId", id + "");
        } else {
            queryEntry.getEquals().put("createCompanyId", id + "");
        }
        queryEntry.setSize(10);
        queryEntry.setPage(page);
        EanfangHttp.post(NewApiService.QUERY_HISTORY_RECORD_LIST)
                .upJson(JsonUtils.obj2String(queryEntry))
                .execute(new EanfangCallback<MainHistoryBean>(mContext, true, MainHistoryBean.class, (bean) -> {
                    initAdapter(bean);
                    onDataReceived();
                }));
    }

    private void initAdapter(MainHistoryBean bean) {

        orderAdapter = new MainAdapter(bean.getList());
        orderAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            switch (view.getId()) {
                case R.id.tv_select:
                    Intent intent = new Intent(mContext, MaintenanceHistoryDetailActivity.class);
                    intent.putExtra("maintianId", bean.getList().get(position).getId());
                    mContext.startActivity(intent);
                    break;
                default:
                    break;
            }
        });
        recyclerView.setAdapter(orderAdapter);
    }


    @Override
    public void onDataReceived() {
        swiprefresh.setRefreshing(false);
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
                queryHistory();
                break;
            case BOTTOM_REFRESH:
                //上拉加载更多
                page++;
                queryHistory();
                break;
            default:
                break;
        }
    }

}
