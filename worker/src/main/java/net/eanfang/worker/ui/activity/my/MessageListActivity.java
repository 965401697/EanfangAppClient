package net.eanfang.worker.ui.activity.my;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.NoticeEntity;
import com.eanfang.model.NoticeListBean;
import com.eanfang.swipefresh.SwipyRefreshLayout;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.QueryEntry;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.adapter.MessageListAdapter;
import net.eanfang.worker.ui.base.BaseWorkerActivity;
import net.eanfang.worker.ui.interfaces.OnDataReceivedListener;
import net.eanfang.worker.ui.widget.MessageDetailView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.eanfang.config.EanfangConst.BOTTOM_REFRESH;
import static com.eanfang.config.EanfangConst.TOP_REFRESH;


/**
 * Created by MrHou
 *
 * @on 2017/11/16  13:57
 * @email houzhongzhou@yeah.net
 * @desc 推送消息列表
 */

public class MessageListActivity extends BaseWorkerActivity implements
        SwipyRefreshLayout.OnRefreshListener, OnDataReceivedListener {
    @BindView(R.id.tv_no_data)
    TextView tvNoData;
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.msg_refresh)
    SwipyRefreshLayout msgRefresh;
    private Activity activity = this;
    private int page = 1;
    private List<NoticeEntity> mDataList = new ArrayList<>();
    private MessageListAdapter messageListAdapter = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        setTitle("通知提醒");
        setLeftBack();
        rvList.setLayoutManager(new LinearLayoutManager(this));
        rvList.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        msgRefresh.setOnRefreshListener(this);

        rvList.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                EanfangHttp.post(NewApiService.GET_PUSH_MSG_INFO + mDataList.get(position).getId())
                        .execute(new EanfangCallback<NoticeEntity>(activity, false, NoticeEntity.class, (bean) -> {
                            new MessageDetailView(MessageListActivity.this, bean, new MessageDetailView.RefreshListener() {
                                @Override
                                public void refreshData() {
                                    getJPushMessage();
                                }
                            }).show();
                        }));
            }
        });
    }

    // 获取数据
    private void getJPushMessage() {
        QueryEntry queryEntry = new QueryEntry();
        queryEntry.setPage(page);
        queryEntry.setSize(10);

        EanfangHttp.post(NewApiService.GET_PUSH_MSG_LIST)
                .upJson(JsonUtils.obj2String(queryEntry))
                .execute(new EanfangCallback<NoticeListBean>(this, true, NoticeListBean.class, (bean) -> {
                            runOnUiThread(() -> {
                                mDataList = bean.getList();
                                onDataReceived();
                                msgRefresh.setRefreshing(false);
                            });
                        })
                );
    }

    @Override
    public void onDataReceived() {
        if (page == 1) {
            if (mDataList.size() == 0 || mDataList == null) {
                tvNoData.setVisibility(View.VISIBLE);
                rvList.setVisibility(View.GONE);
            } else {
                tvNoData.setVisibility(View.GONE);
                rvList.setVisibility(View.VISIBLE);
                messageListAdapter = new MessageListAdapter(R.layout.item_message_list, mDataList);
                rvList.setAdapter(messageListAdapter);
                showToast("已是最新数据");
            }
        } else {
            if (mDataList.size() == 0 || mDataList == null) {
                showToast("暂无更多数据");
            } else {
                tvNoData.setVisibility(View.GONE);
                rvList.setVisibility(View.VISIBLE);
                messageListAdapter = new MessageListAdapter(R.layout.item_message_list, mDataList);
                rvList.setAdapter(messageListAdapter);
            }
        }
    }

    /**
     * 下拉刷新，上拉加载更多
     */
    @Override
    public void onRefresh(int index) {
        page = 1;
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
//                page--;
//                if (page <= 0) {
//                    page = 1;
//                }
                getJPushMessage();
                break;
            case BOTTOM_REFRESH:
                //上拉加载更多
                page++;
                getJPushMessage();
                break;
            default:
                break;
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        getJPushMessage();
    }


}
