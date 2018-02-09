package net.eanfang.worker.ui.activity.my;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.fastjson.JSONObject;
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

import java.util.List;

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

    private RecyclerView mRecyclerView;
    private SwipyRefreshLayout refreshLayout;
    private int page = 1;
    private NoticeListBean messageListBean;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        initView();
    }

    private void initView() {
        setTitle("通知提醒");
        setLeftBack();
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        refreshLayout = (SwipyRefreshLayout) findViewById(R.id.msg_refresh);
        refreshLayout.setOnRefreshListener(this);
    }


    @Override
    public void onDataReceived() {
        initView();

        refreshLayout.setRefreshing(false);
    }

    private void getJPushMessage() {
        QueryEntry queryEntry = new QueryEntry();
        queryEntry.setPage(1);
        queryEntry.setSize(10);

        EanfangHttp.post(NewApiService.GET_PUSH_MSG_LIST)
                .upJson(JsonUtils.obj2String(queryEntry))
                .execute(new EanfangCallback<NoticeListBean>(this, true, NoticeListBean.class, (bean) -> {
                            runOnUiThread(() -> {
                                messageListBean = bean;
                                initAdapter(bean.getList());
                                onDataReceived();
                                refreshLayout.setRefreshing(false);
                            });
                        })
                );
    }

    /**
     * 下拉刷新，上拉加载更多
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

    private void initAdapter(List<NoticeEntity> mDataList) {
        BaseQuickAdapter evaluateAdapter = new MessageListAdapter(R.layout.item_message_list, mDataList);
        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (mDataList.get(position).getStatus() == 0) {
                    unReadOrRead(messageListBean, position);
                }
                new MessageDetailView(MessageListActivity.this, mDataList.get(position)).show();
            }
        });
        if (mDataList.size() != 0) {
            mRecyclerView.setAdapter(evaluateAdapter);
        } else {
            //无数据时显示“暂无数据”
            findViewById(R.id.tv_no_data).setVisibility(View.VISIBLE);
        }

    }

    private void unReadOrRead(NoticeListBean listBean, int postion) {
        EanfangHttp.post(NewApiService.GET_PUSH_READ_OR_UNREAD + listBean.getList().get(postion).getId())
                .execute(new EanfangCallback<>(this, false, JSONObject.class, (bean) -> {
                }));
    }


    @Override
    protected void onResume() {
        super.onResume();
        getJPushMessage();
    }


}
