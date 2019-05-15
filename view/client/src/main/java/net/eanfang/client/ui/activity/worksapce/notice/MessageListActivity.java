package net.eanfang.client.ui.activity.worksapce.notice;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.camera.util.LogUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.config.Constant;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.biz.model.NoticeEntity;
import com.eanfang.biz.model.NoticeListBean;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.QueryEntry;
import com.tencent.android.tpush.XGPushClickedResult;
import com.tencent.android.tpush.XGPushManager;

import net.eanfang.client.R;
import net.eanfang.client.ui.adapter.MessageListAdapter;
import net.eanfang.client.ui.base.BaseClientActivity;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.eanfang.config.EanfangConst.BOTTOM_REFRESH;
import static com.eanfang.config.EanfangConst.TOP_REFRESH;

/**
 * Created by MrHou
 *
 * @on 2017/11/16  13:57
 * @email houzhongzhou@yeah.net
 * @desc 业务通知
 */

public class MessageListActivity extends BaseClientActivity implements
        SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    //    @BindView(R.id.tv_no_data)
//    TextView tvNoData;
    @BindView(R.id.msg_refresh)
    SwipeRefreshLayout msgRefresh;
    private int page = 1;
    @BindView(R.id.tv_right)
    TextView tvRight;
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
        // rv 和 scrollview 滑动冲突
        rvList.setNestedScrollingEnabled(false);

        rvList.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
//                if (mDataList.size() <= position) {
//                    return;
//                }
                messageListAdapter.notifyItemChanged(position, 100);
                Intent intent = new Intent(MessageListActivity.this, MessageDetailActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
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
     * 一键已读
     */
    private void doReadAll() {
        EanfangHttp.post(NewApiService.GET_PUSH_READ_ALL)
                .params("noticeClasses", Constant.NOTICE_BUSINESS)
                .execute(new EanfangCallback(this, true, JSONObject.class) {
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
                .execute(new EanfangCallback(this, true, JSONObject.class) {
                    @Override
                    public void onSuccess(Object bean) {
                        super.onSuccess(bean);
                        page = 1;
                        messageListAdapter.getData().clear();
                        getJPushMessage();
                    }
                });
    }


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

    /**
     * 下拉刷新，上拉加载更多
     */
    @Override
    public void onRefresh() {
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
//        getJPushMessage();
        XGPushManager.onActivityStarted(this);
        XGPushClickedResult clickedResult = XGPushManager.onActivityStarted(this);
        if (clickedResult != null) {
            String title = clickedResult.getTitle();
            LogUtil.v("TPush", "title:" + title);
            String id = clickedResult.getMsgId() + "";
            LogUtil.v("TPush", "id:" + id);
            String content = clickedResult.getContent();
            LogUtil.v("TPush", "content:" + content);
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

