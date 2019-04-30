package net.eanfang.client.ui.activity.worksapce.online;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.apiservice.UserApi;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.model.ExpertListBean;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.QueryEntry;
import com.yaf.sys.entity.BaseDataEntity;

import net.eanfang.client.R;
import net.eanfang.client.ui.base.BaseClientActivity;

import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.rong.imkit.RongIM;
import io.rong.imlib.model.Conversation;

public class ExpertListActivity extends BaseClientActivity implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    @BindView(R.id.rv_list)
    RecyclerView recyclerView;
    @BindView(R.id.tv_no_datas)
    TextView mTvNoData;
    @BindView(R.id.swipre_fresh)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.iv_left)
    ImageView ivLeft;
    private BaseDataEntity mBrand;

    private int mPage = 1;
    private ExpertListAdapter mExpertListAdapter;
    private List<ExpertListBean.ListBean> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expert_list);
        ButterKnife.bind(this);
        ivLeft.setVisibility(View.GONE);
        mBrand = (BaseDataEntity) getIntent().getSerializableExtra("brand");
        if (mBrand != null) {
            setTitle(mBrand.getDataName());
        } else {
            setTitle("专家在线");
        }
        setLeftBack();

        initViews();

    }

    private void initViews() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mExpertListAdapter = new ExpertListAdapter();
        mExpertListAdapter.bindToRecyclerView(recyclerView);
        mExpertListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(ExpertListActivity.this, AskExpertActivity.class);
                intent.putExtra("com", list.get(position).getUserId());
                intent.putExtra("com2", list.get(position).getCompany());
                intent.putExtra("com3", list.get(position).getAccId());
                intent.putExtra("com4", list.get(position).getExpertName());
                startActivity(intent);
            }
        });


        //专家对话
        mExpertListAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                //if (mExpertListAdapter.getData().get(position).getPrice()>0){
                //Toast.makeText(ExpertListActivity.this,"需充值支付",Toast.LENGTH_SHORT).show();
                //}else{
                String accId = list.get(position).getAccId();
                String expertName = list.get(position).getExpertName();
                RongIM.getInstance().startConversation(ExpertListActivity.this, Conversation.ConversationType.PRIVATE, accId, expertName);
                //}
            }
        });


        mSwipeRefreshLayout.setRefreshing(true);

        getData();

    }

    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {
        refresh();
    }

    public void refresh() {
        mPage = 1;//下拉永远第一页
        getData();
    }

    /**
     * 加载更多
     */
    @Override
    public void onLoadMoreRequested() {
        mPage++;
        getData();
    }


    private void getData() {
        QueryEntry queryEntry = new QueryEntry();
        queryEntry.setSize(10);
        queryEntry.setPage(mPage);

        if (mBrand != null) {
            queryEntry.getEquals().put("dataCode", mBrand.getDataCode());
        } else {
            Intent intent = getIntent();
            String brand_code = intent.getStringExtra("brand_code");
            String brand_name = intent.getStringExtra("brand_name");
            queryEntry.getEquals().put("dataCode", brand_code);
            if (!TextUtils.isEmpty(brand_name)) {
                setTitle(brand_name);
            } else {
                setTitle("专家在线");
            }

        }

        EanfangHttp.post(UserApi.EXPERT_LIST)
                .upJson(JsonUtils.obj2String(queryEntry))
                .execute(new EanfangCallback<ExpertListBean>(this, true, ExpertListBean.class) {
                    @Override
                    public void onSuccess(ExpertListBean bean) {
                        if (mPage == 1) {
                            mExpertListAdapter.getData().clear();
                            list = bean.getList();
                            mExpertListAdapter.setNewData(list);
                            mSwipeRefreshLayout.setRefreshing(false);
                            mExpertListAdapter.loadMoreComplete();
                            //List<ExpertsCertificationEntity> list = bean.getList();
                            if (bean.getList().size() < 10) {
                                mExpertListAdapter.loadMoreEnd();
                            }

                            if (bean.getList().size() > 0) {
                                mTvNoData.setVisibility(View.GONE);
                            } else {
                                mTvNoData.setVisibility(View.VISIBLE);
                            }


                        } else {
                            mExpertListAdapter.addData(bean.getList());
                            mExpertListAdapter.loadMoreComplete();
                            if (bean.getList().size() < 10) {
                                mExpertListAdapter.loadMoreEnd();
                            }
                        }
                    }

                    @Override
                    public void onNoData(String message) {
                        mSwipeRefreshLayout.setRefreshing(false);
                        mExpertListAdapter.loadMoreEnd();//没有数据了
                        if (mExpertListAdapter.getData().size() == 0) {
                            mTvNoData.setVisibility(View.VISIBLE);
                        } else {
                            mTvNoData.setVisibility(View.GONE);
                        }

                    }

                    @Override
                    public void onCommitAgain() {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount()==0){
            Intent intent = new Intent(ExpertListActivity.this, ExpertOnlineActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    @OnClick(R.id.tiao_zhuan)
    public void onViewClicked() {
        Intent intent = new Intent(ExpertListActivity.this, ExpertOnlineActivity.class);
        startActivity(intent);
        finish();
    }
}
