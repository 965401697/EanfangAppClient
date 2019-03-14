package net.eanfang.worker.ui.activity.worksapce.online;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.eanfang.apiservice.NewApiService;
import com.eanfang.http.EanfangCallback;
import com.eanfang.http.EanfangHttp;
import com.eanfang.util.JsonUtils;
import com.eanfang.util.QueryEntry;
import com.yaf.base.entity.CommonFaultListBeanEntity;

import net.eanfang.worker.R;
import net.eanfang.worker.ui.base.BaseWorkerActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.rong.imkit.RongIM;
import io.rong.imlib.model.Conversation;

public class CommonFaultListActivity extends BaseWorkerActivity implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    @BindView(R.id.recycler_view_fault)
    RecyclerView recyclerViewFault;
    @BindView(R.id.recycler_view_expert)
    RecyclerView recyclerViewExpert;
    @BindView(R.id.tv_no_datas)
    TextView tvNoDatas;
    @BindView(R.id.see_more)
    TextView seeMore;
    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.titles_bar)
    RelativeLayout titlesBar;
    @BindView(R.id.ll_more)
    RelativeLayout llMore;
    @BindView(R.id.tv_no_datas_zhuanjia)
    TextView tvNoDatasZhuanjia;
    //@BindView(R.id.swipre_fresh)
    //SwipeRefreshLayout swipreFresh;
    private CommonFaultAdapter mCommonFaultAdapter;
    private MyExpertListAdapter myExpertListAdapter;
    private int mPage = 1;
    private List<CommonFaultListBeanEntity.QuestionListBean.ListBean> qList;
    private int size;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_fault_list);
        ButterKnife.bind(this);
        Intent intent = getIntent();

        if (!TextUtils.isEmpty(intent.getStringExtra("QuestionSketch"))) {
            setTitle(intent.getStringExtra("QuestionSketch"));
        } else {
            setTitle("常见故障");
        }
        setLeftBack();
        initViews();
    }

    private void initViews() {
        //swipreFresh.setOnRefreshListener(this);
        //问题
        recyclerViewFault.setLayoutManager(new LinearLayoutManager(this));
        //推荐专家
        recyclerViewExpert.setLayoutManager(new LinearLayoutManager(this));

        mCommonFaultAdapter = new CommonFaultAdapter();
        mCommonFaultAdapter.bindToRecyclerView(recyclerViewFault);

        myExpertListAdapter = new MyExpertListAdapter();
        myExpertListAdapter.bindToRecyclerView(recyclerViewExpert);
        //问题解答
        mCommonFaultAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(CommonFaultListActivity.this, FaultExplainActivity.class);
                intent.putExtra("QuestionId", mCommonFaultAdapter.getData().get(position).getQuestionId());
                startActivity(intent);
            }
        });
        //专家详情
        myExpertListAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

                //if (myExpertListAdapter.getData().get(position).getPrice()>0){
                //    Toast.makeText(CommonFaultListActivity.this,"需充值支付",Toast.LENGTH_SHORT).show();
                //}else {
                String userId = String.valueOf(myExpertListAdapter.getData().get(position).getAccId());
                String expertName = myExpertListAdapter.getData().get(position).getExpertName();
                RongIM.getInstance().startConversation(CommonFaultListActivity.this, Conversation.ConversationType.PRIVATE, userId, expertName);
                //}

            }
        });
        //swipreFresh.setRefreshing(true);
        //getExpertListData();
        getData();
        myExpertListAdapter.loadMoreComplete();
    }

    private void getData() {
        QueryEntry queryEntry = new QueryEntry();
        queryEntry.setSize(3);
        queryEntry.setPage(mPage);
        EanfangHttp.post(NewApiService.COMMENT_FAULT_RECORD_TYPE)
                .upJson(JsonUtils.obj2String(queryEntry))
                .execute(new EanfangCallback<CommonFaultListBeanEntity>(this, true, CommonFaultListBeanEntity.class) {
                    @Override
                    public void onSuccess(CommonFaultListBeanEntity bean) {
                        size = bean.getQuestionList().getList().size();
                        if (mPage == 1) {
                                if (bean.getQuestionList().getList().size() > 0) {
                                    recyclerViewFault.setVisibility(View.VISIBLE);
                                    tvNoDatas.setVisibility(View.GONE);
                                    mCommonFaultAdapter.getData().clear();
                                    qList = bean.getQuestionList().getList();
                                    mCommonFaultAdapter.setNewData(qList);
                                    mCommonFaultAdapter.loadMoreComplete();
                                } else {
                                    tvNoDatas.setVisibility(View.VISIBLE);
                                    recyclerViewFault.setVisibility(View.GONE);
                                }
                                if (bean.getExpertList().getList().size() > 0) {
                                    recyclerViewExpert.setVisibility(View.VISIBLE);
                                    tvNoDatasZhuanjia.setVisibility(View.GONE);
                                    myExpertListAdapter.getData().clear();
                                    myExpertListAdapter.setNewData(bean.getExpertList().getList());
                                    myExpertListAdapter.loadMoreComplete();
                                } else {
                                    recyclerViewExpert.setVisibility(View.GONE);
                                    tvNoDatasZhuanjia.setVisibility(View.VISIBLE);
                                }
                                //底部暂无数据
                                if (bean.getQuestionList().getList().size() < 10) {
                                    mCommonFaultAdapter.loadMoreEnd();
                                }
                                if (bean.getExpertList().getList().size() < 10) {
                                    myExpertListAdapter.loadMoreEnd();
                                }

                            } else {
                                mCommonFaultAdapter.addData(bean.getQuestionList().getList());
                                myExpertListAdapter.addData(bean.getExpertList().getList());
                                mCommonFaultAdapter.loadMoreComplete();
                                myExpertListAdapter.loadMoreComplete();
                                if (bean.getQuestionList().getList().size() < 10) {
                                    mCommonFaultAdapter.loadMoreEnd();
                                }
                                if (bean.getExpertList().getList().size() < 10) {
                                    myExpertListAdapter.loadMoreEnd();
                                }
                            }
                        }


                    @Override
                    public void onNoData(String message) {
                        //swipreFresh.setRefreshing(false);
                        mCommonFaultAdapter.loadMoreEnd();//没有数据了
                        myExpertListAdapter.loadMoreEnd();//没有数据了
                        if (mCommonFaultAdapter.getData().size() <= 0) {
                            //tvNoDatas.setVisibility(View.VISIBLE);
                            mCommonFaultAdapter.loadMoreEnd();
                            seeMore.setVisibility(View.GONE);
                        } else {
                            //tvNoDatas.setVisibility(View.GONE);
                            seeMore.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onCommitAgain() {
                        // swipreFresh.setRefreshing(false);
                    }
                });
    }


    @OnClick({R.id.ll_more/*, R.id.tv_nodata*/})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_more:
                if (size<3){
                    llMore.setVisibility(View.GONE);
                }else {
                    onLoadMoreRequested();
                }

                break;
        }
    }

    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {
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


}
