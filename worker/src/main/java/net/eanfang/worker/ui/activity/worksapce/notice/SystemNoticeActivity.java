package net.eanfang.worker.ui.activity.worksapce.notice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.eanfang.model.NoticeEntity;
import com.eanfang.swipefresh.SwipyRefreshLayout;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.V;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.adapter.MessageListAdapter;
import net.eanfang.worker.ui.interfaces.OnDataReceivedListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Guanluocang
 * @date on 2018/4/23  18:21
 * @decision 系统通知
 */
public class SystemNoticeActivity extends BaseActivity implements
        SwipyRefreshLayout.OnRefreshListener, OnDataReceivedListener {

    @BindView(R.id.rv_systemNotice)
    RecyclerView mRvSystemNotice;
    @BindView(R.id.srl_systemNotice)
    SwipyRefreshLayout msgRefresh;
    @BindView(R.id.tv_no_data)
    TextView tvNoData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_notice);
        ButterKnife.bind(this);
        initView();
    }

    /**
     * @date on 2018/4/24  10:09
     * @decision 初始化数据
     */
    private void initView() {
        setTitle("系统消息");
        setLeftBack();

        tvNoData.setVisibility(View.VISIBLE);
        msgRefresh.setVisibility(View.GONE);
        mRvSystemNotice.setLayoutManager(new LinearLayoutManager(this));
        mRvSystemNotice.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        msgRefresh.setOnRefreshListener(this);
        mRvSystemNotice.setNestedScrollingEnabled(false);

        mRvSystemNotice.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
//                startActivity(new Intent(SystemNoticeActivity.this, SystemNoticeDetailActivity.class).putExtra("infoId", mDataList.get(position).getId()));
            }
        });
    }

    @Override
    public void onDataReceived() {

    }

    @Override
    public void onRefresh(int index) {
        showToast("暂无更多");
        msgRefresh.setRefreshing(false);
    }

    @Override
    public void onLoad(int index) {
        showToast("暂无更多");
        msgRefresh.setRefreshing(false);
    }
}
