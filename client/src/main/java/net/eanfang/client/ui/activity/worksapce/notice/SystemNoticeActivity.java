package net.eanfang.client.ui.activity.worksapce.notice;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.config.Constant;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.NoticeEntity;
import com.eanfang.model.NoticeListBean;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.QueryEntry;

import net.eanfang.client.R;
import net.eanfang.client.ui.adapter.MessageListAdapter;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.eanfang.config.EanfangConst.BOTTOM_REFRESH;
import static com.eanfang.config.EanfangConst.TOP_REFRESH;

/**
 * @author Guanluocang
 * @date on 2018/4/23  18:21
 * @decision 系统通知
 */
public class SystemNoticeActivity extends BaseActivity implements
        SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    @BindView(R.id.rv_systemNotice)
    RecyclerView mRvSystemNotice;
    @BindView(R.id.srl_systemNotice)
    SwipeRefreshLayout msgRefresh;
    //    @BindView(R.id.tv_no_data)
//    TextView tvNoData;
    private int page = 1;
    private List<NoticeEntity> mDataList = new ArrayList<>();
    private MessageListAdapter messageListAdapter = null;
    // 消息数量
    private int mStystemCount = 0;
    private boolean isDelete = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_notice);
        ButterKnife.bind(this);
        initView();
        initData();
        initListener();
    }


    /**
     * @date on 2018/4/24  10:09
     * @decision 初始化数据
     */
    private void initView() {
        setTitle("系统消息");
        setLeftBack(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK);
                finishSelf();
            }
        });
        mStystemCount = getIntent().getIntExtra("mStystemCount", 0);
        // 如果等于0 则全删  反之全读
        if (mStystemCount == 0) {
            setRightTitle("全删");
        } else {
            setRightTitle("全读");
        }
    }

    private void initData() {
        messageListAdapter = new MessageListAdapter(R.layout.item_message_list);
        messageListAdapter.bindToRecyclerView(mRvSystemNotice);
        mRvSystemNotice.setLayoutManager(new LinearLayoutManager(this));
        mRvSystemNotice.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        msgRefresh.setOnRefreshListener(this);
        messageListAdapter.disableLoadMoreIfNotFullPage();
        messageListAdapter.setOnLoadMoreListener(this, mRvSystemNotice);
        // rv 和 scrollview 滑动冲突
        mRvSystemNotice.setNestedScrollingEnabled(false);

        mRvSystemNotice.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                messageListAdapter.notifyItemChanged(position, 100);
                Intent intent = new Intent(SystemNoticeActivity.this, SystemNoticeDetailActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                intent.putExtra("infoId", messageListAdapter.getData().get(position).getId());
                SystemNoticeActivity.this.startActivity(intent);
            }
        });
        getJPushMessage();
    }


    private void initListener() {
        setRightTitleOnClickListener((v) -> {
            if (mStystemCount == 0 || isDelete) {
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

    private void getJPushMessage() {
        QueryEntry queryEntry = new QueryEntry();
        queryEntry.setPage(page);
        queryEntry.setSize(10);
        queryEntry.getEquals().put("noticeClasses", "0");

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

    /**
     * 一键已读
     */
    private void doReadAll() {
        EanfangHttp.post(NewApiService.GET_PUSH_READ_ALL)
                .params("noticeClasses", Constant.NOTICE_SYSTEM)
                .execute(new EanfangCallback(this, true, JSONObject.class) {
                    @Override
                    public void onSuccess(Object bean) {
                        super.onSuccess(bean);
                        page = 1;
                        messageListAdapter.getData().clear();
                        getJPushMessage();
                        setRightTitle("全删");
                        mStystemCount = 0;
                        isDelete = true;
                    }
                });
    }

    /**
     * 一键删除
     */
    private void doAllDelete() {
        EanfangHttp.post(NewApiService.GET_PUSH_DELETE_ALL + Constant.NOTICE_SYSTEM)
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
    }

}
