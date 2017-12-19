package net.eanfang.client.ui.activity.my;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.eanfang.swipefresh.SwipyRefreshLayout;

import net.eanfang.client.R;
import net.eanfang.client.application.EanfangApplication;
import net.eanfang.client.network.apiservice.NewApiService;
import net.eanfang.client.network.request.EanfangCallback;
import net.eanfang.client.network.request.EanfangHttp;
import net.eanfang.client.ui.adapter.MessageListAdapter;
import net.eanfang.client.ui.base.BaseActivity;
import net.eanfang.client.ui.interfaces.OnDataReceivedListener;
import net.eanfang.client.ui.model.MessageListBean;
import net.eanfang.client.util.JsonUtils;
import net.eanfang.client.util.QueryEntry;

import java.util.List;

import static net.eanfang.client.config.EanfangConst.BOTTOM_REFRESH;
import static net.eanfang.client.config.EanfangConst.TOP_REFRESH;

/**
 * Created by MrHou
 *
 * @on 2017/11/16  13:57
 * @email houzhongzhou@yeah.net
 * @desc 推送消息列表
 */

public class MessageListActivity extends BaseActivity implements
        SwipyRefreshLayout.OnRefreshListener, OnDataReceivedListener {

    private RecyclerView mRecyclerView;
    private List<MessageListBean.ListBean> mDataList;
    private SwipyRefreshLayout refreshLayout;
    private int page = 1;
    private MessageListBean messageListBean;


    public void setMessageListBean(MessageListBean messageListBean) {
        this.messageListBean = messageListBean;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        initView();
    }

    private void initView() {

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        setTitle("通知提醒");
        setLeftBack();

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
        queryEntry.getEquals().put("account", EanfangApplication.getApplication().getUser().getAccount().getMobile());
        queryEntry.setPage(1);
        queryEntry.setSize(10);

        EanfangHttp.post(NewApiService.GET_PUSH_MSG_LIST)
                .upJson(JsonUtils.obj2String(queryEntry))
                .execute(new EanfangCallback<MessageListBean>(this, true, MessageListBean.class, (bean) -> {
                            runOnUiThread(() -> {
                                initAdapter(bean.getList());
                                mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
                                    @Override
                                    public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                                        unReadOrRead(bean,position);
                                    }
                                });
                                onDataReceived();
                                refreshLayout.setRefreshing(false);
                            });
                        })
//                {
//
//
//                    @Override
//                    public void onSuccess(MessageListBean bean) {
//                        mDataList = bean.getList();
//                        initAdapter();
//                        refreshLayout.setRefreshing(false);
//                    }
//
//                    @Override
//                    public void onError(String message) {
//                        showToast(message);
//                        refreshLayout.setRefreshing(false);
//                    }
//
//                    @Override
//                    public void onNoData(String message) {
//                        super.onNoData(message);
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                MessageListBean bean = new MessageListBean();
//                                bean.setList(new ArrayList<MessageListBean.ListBean>());
//                                setMessageListBean(bean);
//                                onDataReceived();
//                            }
//                        });
//                    }
//                }
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

    private void initAdapter(List<MessageListBean.ListBean> mDataList) {
//        String currDate = GetDateUtils.dateToDateString(GetDateUtils.getDateNow());
//        for (int i = 0; i < mDataList.size(); i++) {
//            String thisDate = mDataList.get(i).getCreateTime();
//            //如果时间相等 则跳过
//            if (currDate.equals(thisDate)) {
//                continue;
//            }
//            //时间不等 插入
//            MessageListBean.ListBean rowsBean = new MessageListBean.ListBean();
//            rowsBean.setTitle(thisDate);
//            rowsBean.setContent("title");
//            mDataList.add(i, rowsBean);
//            i++;
//            currDate = thisDate;
//        }


        BaseQuickAdapter evaluateAdapter = new MessageListAdapter(R.layout.item_message_list, mDataList);

//        evaluateAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
//            @Override
//            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
//            }
//        });
        if (mDataList.size() != 0) {
            mRecyclerView.setAdapter(evaluateAdapter);
        } else {
            //无数据时显示“暂无数据”
            findViewById(R.id.tv_no_data).setVisibility(View.VISIBLE);
        }

    }

    private void unReadOrRead(MessageListBean listBean,int postion){
        EanfangHttp.get(NewApiService.GET_PUSH_READ_OR_UNREAD)
                .tag(this)
                .params("id",listBean.getList().get(postion).getId())
                .execute(new EanfangCallback(this,true, JSONObject.class,(bean)->{

                }));
    }


    @Override
    protected void onResume() {
        super.onResume();
        getJPushMessage();
    }

}

