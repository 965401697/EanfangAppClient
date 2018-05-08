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
import com.eanfang.apiservice.NewApiService;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.NoticeEntity;
import com.eanfang.model.NoticeListBean;
import com.eanfang.swipefresh.SwipyRefreshLayout;
import com.eanfang.ui.base.BaseActivity;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.QueryEntry;
import com.eanfang.util.V;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.adapter.MessageListAdapter;
import net.eanfang.worker.ui.interfaces.OnDataReceivedListener;

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
        SwipyRefreshLayout.OnRefreshListener, OnDataReceivedListener {

    @BindView(R.id.rv_systemNotice)
    RecyclerView mRvSystemNotice;
    @BindView(R.id.srl_systemNotice)
    SwipyRefreshLayout msgRefresh;
//    @BindView(R.id.tv_no_data)
//    TextView tvNoData;

    private int page = 1;
    private List<NoticeEntity> mDataList = new ArrayList<>();
    private MessageListAdapter messageListAdapter = null;

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

        mRvSystemNotice.setLayoutManager(new LinearLayoutManager(this));
        mRvSystemNotice.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        msgRefresh.setOnRefreshListener(this);
        mRvSystemNotice.setNestedScrollingEnabled(false);

        // rv 和 scrollview 滑动冲突
        mRvSystemNotice.setNestedScrollingEnabled(false);

        mRvSystemNotice.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(SystemNoticeActivity.this, SystemNoticeDetailActivity.class).putExtra("infoId", mDataList.get(position).getId()));
            }
        });
    }

    @Override
    public void onDataReceived() {
        if (page == 1) {
            if (mDataList.size() == 0 || mDataList == null) {
                showToast("暂无数据");
            } else {
                messageListAdapter = new MessageListAdapter(R.layout.item_message_list, mDataList);
                mRvSystemNotice.setAdapter(messageListAdapter);
                messageListAdapter.notifyDataSetChanged();
            }
        } else {
            if (mDataList.size() == 0 || mDataList == null) {
                showToast("暂无更多数据");
                page = page - 1;
            } else {
                messageListAdapter = new MessageListAdapter(R.layout.item_message_list, mDataList);
                messageListAdapter.notifyDataSetChanged();
                mRvSystemNotice.setAdapter(messageListAdapter);
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

    @Override
    protected void onResume() {
        super.onResume();
        getJPushMessage();
    }
}
