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
import com.eanfang.ui.base.BaseDialog;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.QueryEntry;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.activity.worksapce.MaintenanceHistoryDetailActivity;
import net.eanfang.worker.ui.adapter.MainAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MrHou
 *
 * @on 2017/11/28  16:49
 * @email houzhongzhou@yeah.net
 * @desc
 */

public class PersonMaintainHistoryView extends BaseDialog {
    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rcv_order)
    RecyclerView recyclerView;
    private Activity mContext;
    private MainAdapter orderAdapter;
    private Long id;
    private int type;

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
        queryEntry.setPage(1);
        EanfangHttp.post(NewApiService.QUERY_HISTORY_RECORD_LIST)
                .upJson(JsonUtils.obj2String(queryEntry))
                .execute(new EanfangCallback<MainHistoryBean>(mContext, true, MainHistoryBean.class, (bean) -> {
                    initAdapter(bean);
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


}
