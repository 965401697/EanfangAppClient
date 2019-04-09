package net.eanfang.worker.ui.activity.worksapce.notice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.camera.util.LogUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.config.Constant;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.NoticeEntity;
import com.eanfang.model.NoticeListBean;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.QueryEntry;
import com.tengxunsdk.xingepush.IXGPushClickedResult;
import com.tengxunsdk.xingepush.XGPushFactory;
import com.tengxunsdk.xingepush.XGPushProxy;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.adapter.MessageListAdapter;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

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
        SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {
    //    @BindView(R.id.tv_no_data)
//    TextView tvNoData;
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.msg_refresh)
    SwipeRefreshLayout msgRefresh;
    @BindView(R.id.tv_right)
    TextView tvRight;
    private Activity activity = this;
    private int page = 1;
    private List<NoticeEntity> mDataList = new ArrayList<>();
    private MessageListAdapter messageListAdapter = null;


    // 消息数量
    private int mMessageCount = 0;
    private boolean isDelete = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        ButterKnife.bind(this);
        initView();
        initData();
        initListener();
    }


    private void initView() {
        setTitle("业务通知");
        tvRight.setText("全读");
        setLeftBack(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK);
                finishSelf();
            }
        });
        mMessageCount = getIntent().getIntExtra("mMessageCount", 0);
        // 如果等于0 则全删  反之全读
        if (mMessageCount == 0) {
            setRightTitle("全删");
        } else {
            setRightTitle("全读");
        }

    }

    private void initData() {

        messageListAdapter = new MessageListAdapter(R.layout.item_message_list);
        messageListAdapter.bindToRecyclerView(rvList);

        rvList.setLayoutManager(new LinearLayoutManager(this));
        rvList.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        msgRefresh.setOnRefreshListener(this);
        messageListAdapter.disableLoadMoreIfNotFullPage();
        messageListAdapter.setOnLoadMoreListener(this, rvList);
        rvList.setNestedScrollingEnabled(false);

        rvList.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
//                if (mDataList.size() <= position) {
//                    return;
//                }
                messageListAdapter.notifyItemChanged(position, 100);
                Intent intent = new Intent(MessageListActivity.this, MessageDetailActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                intent.putExtra("infoId", messageListAdapter.getData().get(position).getId());
                MessageListActivity.this.startActivity(intent);
            }
        });
        getJPushMessage();
    }

    private void initListener() {
        setRightTitleOnClickListener((v) -> {
            if (mMessageCount == 0 || isDelete) {
                doAllDelete();
            } else {
                doReadAll();
            }
        });
    }

    /**
     * 一键已读
     */
    private void doReadAll() {
        EanfangHttp.get(NewApiService.GET_PUSH_READ_ALL).execute(new EanfangCallback(this, true, JSONObject.class) {
            @Override
            public void onSuccess(Object bean) {
                super.onSuccess(bean);
                page = 1;
                messageListAdapter.getData().clear();
                getJPushMessage();
                setRightTitle("全删");
                mMessageCount = 0;
                isDelete = true;
            }
        });
    }

    /**
     * 一键删除
     */
    private void doAllDelete() {
        EanfangHttp.post(NewApiService.GET_PUSH_DELETE_ALL + Constant.NOTICE_BUSINESS)
                .upJson("[]")
                .execute(new EanfangCallback(this, true, org.json.JSONObject.class) {
                    @Override
                    public void onSuccess(Object bean) {
                        super.onSuccess(bean);
                        page = 1;
                        messageListAdapter.getData().clear();
                        getJPushMessage();
                    }
                });
    }

    // 获取数据
    private void getJPushMessage() {
        QueryEntry queryEntry = new QueryEntry();
        queryEntry.setPage(page);
        queryEntry.setSize(10);
        queryEntry.getEquals().put("noticeClasses", "1");

        EanfangHttp.post(NewApiService.GET_PUSH_MSG_LIST)
                .upJson(JsonUtils.obj2String(queryEntry))
                .execute(new EanfangCallback<NoticeListBean>(this, false, NoticeListBean.class, (bean) -> {
                            runOnUiThread(() -> {
                                mDataList = bean.getList();
                                onDataReceived();
                                msgRefresh.setRefreshing(false);
                            });
                        })
                );
    }

    public void onDataReceived() {
        if (page == 1) {
            if (mDataList.size() == 0 || mDataList == null) {
                showToast("暂无数据");
                setRightGone();
                messageListAdapter.getData().clear();
                messageListAdapter.notifyDataSetChanged();
            } else {
                messageListAdapter.getData().clear();
                messageListAdapter.setNewData(mDataList);
                if (mDataList.size() < 10) {
                    messageListAdapter.loadMoreEnd();
                }
                setRightVisible();
            }
        } else {
            if (mDataList.size() == 0 || mDataList == null) {
                showToast("暂无更多数据");
                page = page - 1;
//                messageListAdapter.notifyDataSetChanged();
                messageListAdapter.loadMoreEnd();
            } else {
                messageListAdapter.addData(mDataList);
                messageListAdapter.loadMoreComplete();
                if (mDataList.size() < 10) {
                    messageListAdapter.loadMoreEnd();
                }
            }
        }
    }

    /**
     * 下拉刷新，上拉加载更多
     */
    @Override
    public void onRefresh() {
//        page = 1;
        dataOption(TOP_REFRESH);
    }

    @Override
    public void onLoadMoreRequested() {
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


    @Override
    protected void onResume() {
        super.onResume();
//        page = 1;
        IXGPushClickedResult clickedResult=XGPushProxy.getInstance(this ,
                XGPushFactory.createXGPushManagerConfig()).onActivityStarted(this);
       /* XGPushManager.onActivityStarted(this);
        XGPushClickedResult clickedResult = XGPushManager.onActivityStarted(this);*/
        Log.d("TPush", "onResumeXGPushClickedResult:" + clickedResult);
        if (clickedResult != null) { // 判断是否来自信鸽的打开方式
            Toast.makeText(this, "通知被点击:" + clickedResult.toString(),
                    Toast.LENGTH_SHORT).show();
            if (clickedResult != null) {
                String title = clickedResult.getTitle();
                LogUtil.v("TPush", "title:" + title);
                String id = clickedResult.getMsgId() + "";
                LogUtil.v("TPush", "id:" + id);
                String content = clickedResult.getContent();
                LogUtil.v("TPush", "content:" + content);
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) { // 监控/拦截/屏蔽返回键
            setResult(RESULT_OK);
        }
        return super.onKeyDown(keyCode, event);
    }

}
